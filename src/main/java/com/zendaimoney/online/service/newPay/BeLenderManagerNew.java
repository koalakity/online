package com.zendaimoney.online.service.newPay;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.zendaimoney.online.admin.dao.account.AdminID5CheckDao;
import com.zendaimoney.online.admin.entity.account.ID5Check;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.AcTCustomerDAO;
import com.zendaimoney.online.dao.AcTLedgerDAO;
import com.zendaimoney.online.dao.UserInfoPersonDAO;
import com.zendaimoney.online.dao.UsersDAO;
import com.zendaimoney.online.entity.AcTCustomerVO;
import com.zendaimoney.online.entity.AcTFlowVO;
import com.zendaimoney.online.entity.AcTLedgerVO;
import com.zendaimoney.online.entity.UserInfoPersonVO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.oii.id5.client.ID5WebServiceClient;
import com.zendaimoney.online.service.common.FlowUtils;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.vo.belender.LicaiUserVO;

@Component
@Transactional(readOnly = true)
public class BeLenderManagerNew {
	private static Logger logger = LoggerFactory.getLogger(BeLenderManagerNew.class);
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private AdminID5CheckDao id5CheckDao;
	@Autowired
	private AcTLedgerDAO acTLedgerDAO;
	@Autowired
	private UserInfoPersonDAO userInfoPersonDAO;
	@Autowired
	private AcTCustomerDAO acTCustomerDAO;
	@Autowired
	private FlowUtils flowUtils;
	@Autowired
	private RateCommonUtil rateCommon;

	/**
	 * 身份验证方法重构 2013-2-19 下午12:43:16 by HuYaHui
	 * 
	 * @param licaiUser
	 * @param req
	 * @param rep
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String checkAmountIsEnough(LicaiUserVO licaiUser, HttpServletRequest req, 
			HttpServletResponse rep, UserInfoPersonVO userInfoPerson, UsersVO user, String phoneValidateCode) {
		logger.info("进入checkAmountIsEnough");
		long userId = user.getUserId();

		// 个人账户
		AcTLedgerVO act_4 = getAvailableBalance(userId);
		Long channelId = user.getChannelInfoId();
		double id5Fee = rateCommon.getId5Fee(channelId);
		if (BigDecimalUtil.compareTo(act_4.getAmount(), id5Fee) < 0) {
			// 账户没有5元,回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("账户金额不足" + id5Fee + "元！");
			return "noEnough";
		}

		ID5Check id5Check = ID5WebServiceClient.checkID5All(userInfoPerson.getRealName(), userInfoPerson.getIdentityNo());
		if (id5Check.getCheckStatusCode() == null || "".equals(id5Check.getCheckStatusCode())) {
			logger.info("未获取ID5验证状态！可能调用webservice失败！");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "fail";
		}

		int checkStatus = Integer.valueOf(id5Check.getCheckStatusCode());
		logger.info("ID5验证完成，返回状态为：" + checkStatus + " userId:" + userId);

		/*
		 * checkStatus 1:库中午此号 2:验证不一致 3:验证一致
		 */
		if (checkStatus == 3 || checkStatus == -2 || checkStatus == 2 || checkStatus == 1) {
			// 从个人账户扣除手续费
			act_4.setAmount(BigDecimalUtil.sub(act_4.getAmount(), id5Fee));
			int actledger_count = acTLedgerDAO.updateLoanManagementAcTLedgerById(act_4.getAmount(), act_4.getId());
			logger.info("根据ID修改个人账户金额，ID为：" + act_4.getId() + ",金额：" + act_4.getAmount() + "执行结果为：" + actledger_count);

			// 证大收取id5手续费
			AcTLedgerVO act_3 = acTLedgerDAO.findByBusiTypeAndAccount("3", ZendaiAccountBank.zendai_acct11);
			act_3.setAmount(BigDecimalUtil.add(act_3.getAmount(), id5Fee));
			int act_count = acTLedgerDAO.updateLoanManagementAcTLedgerById(act_3.getAmount(), act_3.getId());
			logger.info("根据ID修改证大账户金额，ID为：" + act_3.getId() + ",金额：" + act_3.getAmount() + "执行结果为：" + act_count);

			if (actledger_count == 0 || act_count == 0) {
				logger.info("扣款失败，扣除个人账户金额返回结果：" + actledger_count + " 证大收取手续费返回结果：" + act_count);
				throw new RuntimeException("fail");
			}

			// 记录交易流水
			AcTCustomerVO acTCustomerVO = this.acTCustomerDAO.findOne(user.gettCustomerId());
			AcTFlowVO tmp_act = flowUtils.saveAcTFlowVOAndAcTFlowClassifyVOForZD(userId, TradeTypeConstants.ID5, userId, new BigDecimal(id5Fee), acTCustomerVO.getOpenacctTeller(), acTCustomerVO.getOpenacctOrgan(), act_4.getAccount(), NewConstSubject.id5_fee_out, NewConstSubject.id5_fee_in, new BigDecimal(id5Fee), act_3, act_4, TypeConstants.SFYZ);

			logger.info("保存id5手续费资金流水记录,ID为:" + tmp_act.getTradeNo());
			if (checkStatus == 3) {
//				int tmp_count = usersDAO.updateIsApproveCardByUserIdAndIsApproveCard(userId, 1);
//				if (tmp_count != 1) {
//					// 没有修改状态成功,数据回滚
//					throw new RuntimeException("fail");
//				} TODO 执行成功，但是数据未提交
				user.setIsApproveCard(1l);
				usersDAO.save(user);
				

				id5Check.setUserId(new BigDecimal(user.getUserId()));
				userInfoPersonDAO.save(userInfoPerson);
				logger.info("更新UserInfoPersonVO信息成功，ID：" + userInfoPerson.getIdentityNo());

				id5CheckDao.save(id5Check);
				logger.info("更新ID5Check信息成功，ID：" + id5Check.getCardId());
				acTLedgerDAO.save(act_3);
				logger.info("更新证大身份验证账户信息成功，ID：" + act_3.getId());
				acTLedgerDAO.save(act_4);
				logger.info("更新个人账户4信息成功，ID：" + act_4.getId());
			} else {
				id5Check.setUserId(new BigDecimal(user.getUserId()));
				userInfoPersonDAO.save(userInfoPerson);
				logger.info("更新UserInfoPersonVO信息成功，ID：" + userInfoPerson.getIdentityNo());

				id5CheckDao.save(id5Check);
				logger.info("更新ID5Check信息成功，ID：" + id5Check.getCardId());
				acTLedgerDAO.save(act_3);
				logger.info("更新证大身份验证账户信息成功，ID：" + act_3.getId());
				acTLedgerDAO.save(act_4);
				logger.info("更新个人账户4信息成功，ID：" + act_4.getId());
				return "fail";
			}
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "fail";
		}
		logger.info("结束checkAmountIsEnough");
		return "success";
	}

	// 当前用户的资金账户可用余额
	private AcTLedgerVO getAvailableBalance(Long userId) {
		// 我的余额(可用余额)
		AcTLedgerVO actledger = acTLedgerDAO.findByBusiTypeAndAccountLike("4", userId);
		return actledger;
	}

	// 当前登录用户信息
	@Transactional(readOnly = true)
	public UsersVO getCurrentUser(HttpServletRequest req, HttpServletResponse rep) {
		HttpSession session = req.getSession();
		BigDecimal currUserIdStr = (BigDecimal) session.getAttribute("curr_login_user_id");
		return usersDAO.findByUserId(currUserIdStr.longValue());
	}

	/**
	 * 根据用户ID查询认证记录 2013-2-22 上午11:52:24 by HuYaHui
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public UserInfoPersonVO findUserInfoPersonByUserId(long userId) {
		UserInfoPersonVO userInfoPerson = userInfoPersonDAO.findByUserId(userId);
		return userInfoPerson;
	}

	/**
	 * 根据身份证号码查询数据 2013-2-22 上午11:52:11 by HuYaHui
	 * 
	 * @param cardId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ID5Check> findID5CheckByCardId(String cardId) {
		List<ID5Check> id5List = id5CheckDao.findByCardId(cardId);
		return id5List;
	}

}
