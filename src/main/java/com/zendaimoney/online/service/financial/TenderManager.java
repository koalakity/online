package com.zendaimoney.online.service.financial;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.dao.AcTLedgerDAO;
import com.zendaimoney.online.dao.FreezeFundsDAO;
import com.zendaimoney.online.dao.InvestInfoDAO;
import com.zendaimoney.online.dao.LoanInfoDAO;
import com.zendaimoney.online.dao.SysMsgDAO;
import com.zendaimoney.online.dao.UserInfoPersonDAO;
import com.zendaimoney.online.dao.UserMessageSetDAO;
import com.zendaimoney.online.dao.UserMovementDAO;
import com.zendaimoney.online.dao.UsersDAO;
import com.zendaimoney.online.dao.pay.PayDao;
import com.zendaimoney.online.entity.AcTLedgerFinanceVO;
import com.zendaimoney.online.entity.AcTLedgerVO;
import com.zendaimoney.online.entity.FreezeFundsVO;
import com.zendaimoney.online.entity.InvestInfoVO;
import com.zendaimoney.online.entity.LoanInfoVO;
import com.zendaimoney.online.entity.SysMsgVO;
import com.zendaimoney.online.entity.UserInfoPersonVO;
import com.zendaimoney.online.entity.UserMessageSetVO;
import com.zendaimoney.online.entity.UserMovementVO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.oii.sms.SMSSender;
import com.zendaimoney.online.service.common.FlowUtils;

/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-16 下午1:28:39
 * operation by: Ray
 * description: 投标方法
 */
@Component
@Transactional(readOnly = true)
public class TenderManager {
	
	
	private static Logger logger = LoggerFactory.getLogger(TenderManager.class);
	
	@Autowired
	private UsersDAO usersDao;
	
	@Autowired
	private LoanInfoDAO loanInfoDao;
	
	@Autowired
	private InvestInfoDAO investInfoDao;
	
	@Autowired
	private UserMovementDAO userMovementDao;
	
	@Autowired
	private FreezeFundsDAO freezeFundsDao;
	
	@Autowired
	private AcTLedgerDAO acTLedgerDao;
	
	@Autowired
	private FlowUtils flowUtils;
	
	@Autowired
	private SysMsgDAO sysMsgDao;
	
	@Autowired
	private UserMessageSetDAO userMessageSetDao;
	
	@Autowired
	MimeMailService mimeMailService;
	
	@Autowired
	private UserInfoPersonDAO UserInfoPersonDao;
	
	@Autowired
	private PayDao payDao;
	
	
	@Transactional(readOnly = false)
	public boolean invest(BigDecimal investAmount, BigDecimal loanId, HttpServletRequest req) throws RuntimeException{
		
		//获取理财人信息
		UsersVO user = getUsers(req);
		//新建理财信息对象
		InvestInfoVO investInfo = new InvestInfoVO();
		investInfo.setLoanId(loanId.longValue());
		investInfo.setUserId(user.getUserId());
		investInfo.setInvestAmount(investAmount);
		//获取借款信息表
		LoanInfoVO loanInfo = loanInfoDao.findByLoanId(loanId.longValue());
		//获取理财信息表List
		List<InvestInfoVO> invList = investInfoDao.findByLoanId(loanInfo.getLoanId());
		// 已经投资的金额
		BigDecimal leaveMoney = BigDecimal.ZERO;
		// 所占比例
		BigDecimal havaScale = BigDecimal.ZERO;
		for (InvestInfoVO invInfo : invList) {
			leaveMoney = BigDecimalUtil.add(leaveMoney, invInfo.getInvestAmount());
			havaScale = BigDecimalUtil.add(havaScale, invInfo.getHavaScale());
		}
		// 已经投资的金所占的比例
		BigDecimal scale = havaScale;
		// 已经投资的金额 + 本次投资的金额 = 借款金额
		//已投资金额+本次投标=总借款额（满标情况）
		if (loanInfo.getLoanAmount().compareTo(BigDecimalUtil.add(leaveMoney, investAmount))==0) {
			loanInfo.setStatus(2L);
			// 满标的情况下 跟新借款列表
			loanInfoDao.save(loanInfo);
			logger.info("SAVE: LOAN_INFO  满标，保存借款信息表  || userId=" + loanInfo.getUserId() +" 发布状态="+loanInfo.getReleaseStatus()+" 发布时间="+loanInfo.getReleaseTime());
			// 最后一次投标的比例
			havaScale = BigDecimalUtil.sub(1d, havaScale);
			UserMovementVO movement = new UserMovementVO();
//			HomepageMovementWord moWord = new HomepageMovementWord();
//			moWord.setWordId(new BigDecimal(2));
			movement.setUserId(loanInfo.getUserId());
			movement.setParameter1(loanInfo.getLoanTitle());
			movement.setWordId(2L); //使用模板2
			movement.setUrl1("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
			movement.setMsgKind("1");
			movement.setIsDel("0");
			movement.setHappenTime(new Date());
			logger.info("SAVE: USER_MOVEMENT || userId=" + loanInfo.getUserId() + " Parameter1=" + loanInfo.getLoanTitle() + " WordId=" + 2 + " MsgKind=1 IsDel=0 movement=" + movement.getHappenTime());
			userMovementDao.save(movement);
		} else {
			havaScale = BigDecimalUtil.div(investAmount, loanInfo.getLoanAmount());
		}

		investInfo.setHavaScale(havaScale);
		investInfo.setInvestTime(new Date());
		investInfo.setStatus("2"); //状态为2，招标中
		AcTLedgerFinanceVO alf = new AcTLedgerFinanceVO();
		alf.setAcctStatus("1");// 状态是开户1
		alf.setDebtAmount(investAmount);
		alf.setDebtProportion(havaScale);
		alf.setLedgerId(user.gettCustomerId()); //存入总账ID
		investInfo.setAcTLedgerFinance(alf);
		
		// 保存理财信息
		investInfoDao.save(investInfo);
		logger.info("SAVE: INVEST_INFO 投标成功，保存理财信息表 || InvestId=" + investInfo.getInvestId() + " InvestTime=" + investInfo.getInvestTime() + " Status=2 FinancialAcTLedgerFinance.id=" + alf.getId());

		// 冻结资金表
		FreezeFundsVO freezeFund = new FreezeFundsVO();
		freezeFund.setFreezeKind("1");
		freezeFund.setUserId(user.getUserId());
		freezeFund.setLoanId(loanId.longValue());
		freezeFund.setFreezeMoney(investAmount);
		freezeFund.setFreezeTime(new Date());
		freezeFund.setFreezeStatus("1");
		freezeFundsDao.save(freezeFund);
		logger.info("SAVE: FREEZE_FUNDS 冻结资金表 || FreezeKind= 1 userId=" + user.getUserId() + " LoanId=" + loanId + " FreezeMoney=" + investAmount + " FreezeTime=" + freezeFund.getFreezeTime() + " FreezeStatus=1");
		
		//获取理财信息表
		List<InvestInfoVO> InvestInfos = investInfoDao.findByUserId(user.getUserId());
		BigDecimal licai = BigDecimal.ZERO;
		for (InvestInfoVO financialInvestInfo : InvestInfos) {
			if ("3".equals(financialInvestInfo.getStatus()) || "4".equals(financialInvestInfo.getStatus())) {
				licai = BigDecimalUtil.add(licai, financialInvestInfo.getInvestAmount());
			}
		}
		
		AcTLedgerVO ledger = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "4");
		AcTLedgerVO freezeLedger = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "5");
//		logger.info("SAVE: AC_T_FLOW || investAmount=" + investAmount + " code=3000000000001 Account=" + ledger.getAccount() + " 操作科目：投标冻结");
		// 流水表
		
		flowUtils.setActFlow(investAmount, "", "3000000000001", ledger.getAccount(), freezeLedger.getAccount(), NewConstSubject.bid_freeze_out, NewConstSubject.bid_freeze_in);
		//查询分账4现在账户、提现冻结账户、分账5投标冻结账户

		// 分账4减去投标金额
		BigDecimal amount = BigDecimalUtil.sub(ledger.getAmount(), investAmount);
		if(amount.compareTo(BigDecimal.ZERO)<0){
			logger.info("投标金额大约剩余本金！账户金额："+ledger.getAccount()+"投标金额："+investAmount);
			return false;
		}
		BigDecimal contidionAmount = ledger.getAmount();
		ledger.setAmount(amount);
		AcTLedgerVO contidionParam = new AcTLedgerVO();
		contidionParam.setId(ledger.getId());
		contidionParam.setAmount(contidionAmount);
		int count = payDao.update(ledger, contidionParam);
		if(count!=1){
			throw new RuntimeException("投标失败");
		}
		acTLedgerDao.save(ledger);
		logger.info("更新分账表 账号4"+ledger.getAccount()+" 更新后值"+ledger.getAmount());
		
		//分账5加上冻结金额
		BigDecimal freezeAmount = BigDecimalUtil.add(freezeLedger.getAmount(), investAmount);
		freezeLedger.setAmount(freezeAmount);
		acTLedgerDao.save(freezeLedger);
		logger.info("更新分账表 账号5"+ledger.getAccount()+" 更新后值"+ledger.getAmount());
		
		
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
		//获取借款人信息
		UsersVO loanUser =usersDao.findByUserId(loanInfo.getUserId());
		UserInfoPersonVO loanPerson=UserInfoPersonDao.findByUserId(loanUser.getUserId());
		
		// TODO 发送短信接口暂时注释

		// //给借款人发送短信、邮件 投标
		List<UserMessageSetVO> messageList = userMessageSetDao.findByUserIdAndKindId(loanInfo.getUserId(), 2L);
		
		// //发送短信 发送系统消息 借款列表完成度超过50%
		if (BigDecimalUtil.add(scale, havaScale).compareTo(BigDecimal.valueOf(0.5)) >= 0) {
			SysMsgVO sysMsg2 = new SysMsgVO();
			sysMsg2.setUserId(loanInfo.getUserId());
			sysMsg2.setWordId(11L);
			sysMsg2.setParameter1(loanUser.getLoginName());
			sysMsg2.setParameter2(loanInfo.getLoanTitle());
			sysMsg2.setParameter3(moneyFormat.format(investAmount));
			String percent = BigDecimalUtil.formatPercent(BigDecimalUtil.add(scale, havaScale), "#,###.00%");
			sysMsg2.setParameter4(percent.substring(0, percent.length() - 1));
			sysMsg2.setHappenTime(new Date());
			sysMsg2.setIsDel("0");
			logger.info("SAVE: SYS_MSG || userId=" + loanInfo.getUserId() + " WordId=11 Parameter1=" + loanUser.getLoginName() + " Parameter2=" + loanInfo.getLoanTitle() + " Parameter3=" + investAmount + " Parameter4=" + percent.substring(0, percent.length() - 1) + " HappenTime=" + sysMsg2.getHappenTime() + " IsDel=0");
			sysMsgDao.save(sysMsg2);
			List<UserMessageSetVO> messageList2 = userMessageSetDao.findByUserIdAndKindId(loanInfo.getUserId(), 4L);
			for (UserMessageSetVO message : messageList2) {
				if (message.getMannerId().equals(2L)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("0", loanUser.getLoginName());
					map.put("1", loanInfo.getLoanTitle());
					map.put("2", moneyFormat.format(investAmount));
					map.put("3", percent);
					String messages = mimeMailService.transferMailContent("over_half", map);
					mimeMailService.sendNotifyMail(messages, loanUser.getEmail(), "我的借款列表完成度超过50%");
				}
				if (message.getMannerId().equals(3L)) {
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("0", loanUser.getLoginName());
					map2.put("1", loanInfo.getLoanTitle());
					map2.put("2", moneyFormat.format(investAmount));
					map2.put("3", percent);
					SMSSender.sendMessage("over_half", loanPerson.getPhoneNo(), map2);
				}
			}
		}else{
			// 发送系统消息
			SysMsgVO sysMsg = new SysMsgVO();
			sysMsg.setUserId(loanInfo.getUserId());
			sysMsg.setWordId(8L);
			sysMsg.setParameter1(loanUser.getLoginName());
			sysMsg.setParameter2(loanInfo.getLoanTitle());
			sysMsg.setParameter3(moneyFormat.format(investAmount));
			sysMsg.setHappenTime(new Date());
			sysMsg.setIsDel("0");
			sysMsgDao.save(sysMsg);
			logger.info("保存系统消息！用户ID为： "+loanInfo.getUserId());
			
			
			for (UserMessageSetVO message : messageList) {
				if (message.getMannerId().equals(2L)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("0", loanUser.getLoginName());
					map.put("1", loanInfo.getLoanTitle());
					map.put("2", moneyFormat.format(investAmount));
					String messages = mimeMailService.transferMailContent("rev_bid", map);
					mimeMailService.sendNotifyMail(messages, loanUser.getEmail(), "有人向我的借款列表投标");
				}
				if (message.getMannerId().equals(3L)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("0", loanUser.getLoginName());
					map.put("1", loanInfo.getLoanTitle());
					map.put("2", moneyFormat.format(investAmount));
					SMSSender.sendMessage("rev_bid", loanPerson.getPhoneNo(), map);
				}
			}
		}

		// 发送系统消息 投标成功
//		SysMsgVO sysMsg3 = new SysMsgVO();
//		sysMsg3.setUserId(user.getUserId());
//		sysMsg3.setWordId(12L);
//		sysMsg3.setParameter1(user.getLoginName());
//		sysMsg3.setParameter2(loanInfo.getLoanTitle());
//		sysMsg3.setParameter3(moneyFormat.format(investAmount));
//		sysMsg3.setParameter4(moneyFormat.format(ledger.getAmount().doubleValue()));
//		sysMsg3.setParameter5(moneyFormat.format(licai));
//		sysMsg3.setHappenTime(new Date());
//		sysMsg3.setIsDel("0");
//		logger.info("SAVE: SYS_MSG || userId=" + user.getUserId() + " WordId=12 Parameter1=" + user.getLoginName() + " Parameter2=" + loanInfo.getLoanTitle() + " Parameter3=" + investAmount + " Parameter4=" + ledger.getAmount() + " Parameter5=" + licai + " HappenTime=" + sysMsg3.getHappenTime() + " IsDel=0");
//		sysMsgDao.save(sysMsg3);

		// 投标最新动态
		UserMovementVO movement = new UserMovementVO();
		movement.setUserId(user.getUserId());
		movement.setParameter1(loanInfo.getLoanTitle());
		movement.setParameter2(moneyFormat.format(investAmount));
		movement.setWordId(6L);
		movement.setUrl1("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
		movement.setMsgKind("1");
		movement.setIsDel("0");
		movement.setHappenTime(new Date());
		logger.info("SAVE: USER_MOVEMENT || userId=" + user.getUserId() + " WordId = 6  Parameter1=" + user.getLoginName() + " Parameter2=" + investAmount + " HappenTime=" + movement.getHappenTime() + " MsgKind=1 IsDel=0");
		userMovementDao.save(movement);

		// //给理财人发送短信、邮件 投标成功
//		List<UserMessageSetVO> messageInvestList = userMessageSetDao.findByUserIdAndKindId(loanInfo.getUserId(), 5L);
//		for (UserMessageSetVO message : messageInvestList) {
//			if (message.getMannerId().equals(2L)) {
//				Map<String, String> map3 = new HashMap<String, String>();
//				map3.put("0", user.getLoginName());
//				map3.put("1", loanInfo.getLoanTitle());
//				map3.put("2", moneyFormat.format(investAmount));
//				map3.put("3", moneyFormat.format(ledger.getAmount().doubleValue()));
//				map3.put("4", moneyFormat.format(licai));
//				String messages = mimeMailService.transferMailContent("bid_succ", map3);
//				mimeMailService.sendNotifyMail(messages, user.getEmail(), "我的投标成功");
//			}
//			if (message.getMannerId().equals(3L)) {
//				Map<String, String> map3 = new HashMap<String, String>();
//				map3.put("0", user.getLoginName());
//				map3.put("1", loanInfo.getLoanTitle());
//				map3.put("2", moneyFormat.format(investAmount));
//				map3.put("3", moneyFormat.format(ledger.getAmount().doubleValue()));
//				map3.put("4", moneyFormat.format(licai));
//				UserInfoPersonVO person=UserInfoPersonDao.findByUserId(user.getUserId());
//				SMSSender.sendMessage("bid_succ", person.getPhoneNo(), map3);
//			}
//		}

		return true;

	}
	
	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-16 上午11:41:13
	 * @param request
	 * @return
	 * description:获取userId
	 */
	public UsersVO getUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		UsersVO userInfo = usersDao.findByUserId(userid.longValue());
		return userInfo;
	}

}
