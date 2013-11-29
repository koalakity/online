package com.zendaimoney.online.service.financial;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.zendaimoney.online.admin.entity.account.ID5Check;
import com.zendaimoney.online.common.ConstSubject;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.dao.UsersDAO;
import com.zendaimoney.online.dao.accountLogin.LoginDao;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.financial.FinancialUserInfoPersonDao;
import com.zendaimoney.online.dao.financial.FinancialUsersDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementAcTLedgerDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementAcTFlowDao;
import com.zendaimoney.online.entity.UserInfoPersonVO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.entity.financial.FinancialUserInfoPerson;
import com.zendaimoney.online.entity.financial.FinancialUsers;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedger;
import com.zendaimoney.online.service.newPay.BeLenderManagerNew;
import com.zendaimoney.online.vo.belender.LicaiUserVO;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-18 下午2:39:17 operation by: description:成为理财人认证
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class BeLenderManager {
	private static Logger logger = LoggerFactory.getLogger(BeLenderManager.class);
	@Autowired
	private LoanManagementAcTLedgerDao loanManagementActLegerDao;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private FinancialUsersDao financialUsersDao;
	@Autowired
	LoanmanagementAcTFlowDao actFlowDao;
	@Autowired
	private FinancialUserInfoPersonDao financialUserInfoPersonDao;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private BeLenderManagerNew beLenderManagerNew;

	@Autowired
	private UsersDAO usersDAO;

	// 是否登录
	public boolean isLogin(HttpServletRequest req, HttpServletResponse rep) {
		FinancialUsers user = getCurrentUser(req, rep);
		return user != null && user.getUserId() != null;
	}

	// 是否理财人,手机没验证
	public boolean isLenderExceptPhone(HttpServletRequest req) {
		BigDecimal currUserIdStr = (BigDecimal) req.getSession().getAttribute("curr_login_user_id");
		return loginDao.queryUserIsLenderExceptPhone(currUserIdStr);
	}

	// 是否理财人
	public boolean isLender(HttpServletRequest req, HttpServletResponse rep) {
		FinancialUsers user = getCurrentUser(req, rep);
		return loginDao.findIsLender(user.getUserId());
	}

	// 是否理财人
	public FinancialUsers isLender(HttpServletRequest req) {
		FinancialUsers user = getCurrentUser(req, null);
		return user;
	}

	// 身份证和输入的姓名是否匹配
	public String checkIdCard(HttpServletRequest req, HttpServletResponse rep) {
		String idCardNumber = req.getParameter("idCardNumber");
		FinancialUsers user = getCurrentUser(req, rep);
		// TODO 调用身份证验证接口
		return "false";

	}

	// 手机号码是否已被绑定
	public Boolean checkPhone(String phoneNo, HttpServletRequest req, HttpServletResponse rep) {
		FinancialUsers currUser = getCurrentUser(req, rep);
		FinancialUserInfoPerson userInfoPerson = financialUserInfoPersonDao.getUserPhoneInof(phoneNo);
		if (userInfoPerson != null) {
			if (!userInfoPerson.getUser().getUserId().equals(currUser.getUserId())) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	// 发送手机验证码
	@Transactional(readOnly = false)
	public Map sendPhoneCode(String phoneNo, HttpServletRequest req, HttpServletResponse rep) {
		FinancialUsers currUser = getCurrentUser(req, rep);
		// 根据手机号查询是否有绑定的
		FinancialUserInfoPerson userInfoPerson = financialUserInfoPersonDao.getUserPhoneInof(phoneNo);
		Random random = new Random();
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		String randomCode = "";
		for (int i = 0; i < 6; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 将产生的6个随机数组合在一起。
			randomCode += strRand;
		}
		if (userInfoPerson != null) {
			// 已经绑定，且绑定对象与当前对象相同
			if (userInfoPerson.getUser().getUserId().equals(currUser.getUserId())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("0", randomCode);
				userInfoPerson.setPhonNoBak(phoneNo);
				userInfoPerson.setPhoneValidateCode(randomCode);
				userInfoPerson.setValidateCodeDate(DateUtil.getCurrentDate());
				logger.info("SAVE: USER_INFO_PERSON || userId=" + userInfoPerson.getUser().getUserId() + " PhoneValidateCode=" + randomCode);
				financialUserInfoPersonDao.save(userInfoPerson);
				return map;
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("2", "fail");
				return map;
				// throw new RuntimeException("手机号码已被占用，请重新发送手机号码。");
			}
		} else {
			FinancialUserInfoPerson currUserInfoPerson = null;
			if (currUser.getUserInfoPerson() != null) {
				currUserInfoPerson = currUser.getUserInfoPerson();
			} else {
				currUserInfoPerson = new FinancialUserInfoPerson();
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("0", randomCode);
			// currUserInfoPerson.setPhoneNo(phoneNo);
			currUserInfoPerson.setPhonNoBak(phoneNo);
			currUserInfoPerson.setPhoneValidateCode(randomCode);
			currUserInfoPerson.setValidateCodeDate(DateUtil.getCurrentDate());
			currUserInfoPerson.setUser(currUser);
			logger.info("SAVE: USER_INFO_PERSON || userId=" + currUser.getUserId() + " PhoneNo=" + phoneNo + " PhoneValidateCode=" + randomCode);
			financialUserInfoPersonDao.save(currUserInfoPerson);
			return map;
		}
	}

	// // 手机号码是否已被绑定(web)
	// @Transactional(readOnly = false)
	// public FinancialUserInfoPerson checkPhoneBackground(String phoneNo) {
	// return financialUserInfoPersonDao.getUserPhoneInof(phoneNo);
	// }

	// 当前登录用户信息
	public FinancialUsers getCurrentUser(HttpServletRequest req, HttpServletResponse rep) {
		HttpSession session = req.getSession();
		BigDecimal currUserIdStr = (BigDecimal) session.getAttribute("curr_login_user_id");
		return financialUsersDao.findByUserId(currUserIdStr);
	}

	// 当前用户的资金账户可用余额
	public Double getAvailableBalance(FinancialUsers currentUser) {
		// 我的余额(可用余额)
		LoanManagementAcTLedger payPrincipalActledger1 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", currentUser.getAcTCustomer().getTotalAcct() + "%");
		return payPrincipalActledger1 != null ? payPrincipalActledger1.getAmount() : 0;
	}

	/**
	 * @author Ray
	 * @date 2012-11-28 上午10:06:21
	 * @param licaiUser
	 * @param req
	 * @param rep
	 * @return description:修改验证用户ID5方法
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String checkAmountIsEnough(LicaiUserVO licaiUser, HttpServletRequest req, HttpServletResponse rep) {
		UsersVO user = beLenderManagerNew.getCurrentUser(req, rep);
		UserInfoPersonVO userInfoPerson = beLenderManagerNew.findUserInfoPersonByUserId(user.getUserId());

		String name = licaiUser.getRealName();
		String id_card_num = licaiUser.getIdCardNumber();
		String phoneValidateCode = licaiUser.getValidateCode();
		userInfoPerson.setPhoneNo(licaiUser.getPhoneNumber());
		userInfoPerson.setRealName(name);
		userInfoPerson.setIdentityNo(id_card_num);

		if (userInfoPerson.getPhoneValidateCode() == null) {
			// 回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("手机验证码为空！");
			return "noSend";
		} else if (userInfoPerson.getPhonNoBak() != null && !userInfoPerson.getPhonNoBak().equals(licaiUser.getPhoneNumber())) {
			// 回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("手机号不匹配！");
			return "noSend";
		}
		Date validateCodeDate = userInfoPerson.getValidateCodeDate();
		Date currDate = DateUtil.getCurrentDate();
		if (DateUtil.getDays(validateCodeDate, currDate) >= 1) {
			logger.info("超过24小时，验证码失效！");
			return "overtime";
		}
		// 手机验证码相等
		if (userInfoPerson.getPhoneValidateCode().equals(phoneValidateCode)) {
			logger.info("验证码正确！");

			// 更新手机验证已验证
			user.setIsApprovePhone(1l);
			usersDAO.save(user);
			logger.info("更新UsersVO信息成功，ID：" + user.getUserId());

			if (user.getIsApproveCard() != null && user.getIsApproveCard() == 1l) {
				return "success";
			}

			List<ID5Check> id5List = beLenderManagerNew.findID5CheckByCardId(userInfoPerson.getIdentityNo());
			// 查询数据库中此身份证是否已经验证通过，被占用
			boolean flag = true;// 身份证在ID5Check表中是否已存在
			if (id5List.size() > 0) {
				for (int i = 0; i < id5List.size(); i++) {
					if (user.getUserId().equals(id5List.get(i).getUserId())) {
						if ("3".equals(id5List.get(i).getCheckStatusCode())) {
							flag = false;
							return "success";
						}
					} else {
						if ("3".equals(id5List.get(i).getCheckStatusCode())) {
							flag = false;
							// 回滚事物
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							// 如果查到身份证在ID5check表中存在并状态为3，返回身份证号码已经占用
							logger.info("身份证号码已经占用！");
							return "idCardBind";
						}
					}
				}
			}
			if (!flag) {
				// 身份证被占用,回滚事物
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.info("身份证号码已经占用！");
				return "idCardBind";
			}
			String check_stauts = beLenderManagerNew.checkAmountIsEnough(licaiUser, req, rep, userInfoPerson, user, phoneValidateCode);
			logger.info("ID5处理结果为：" + check_stauts);
			return check_stauts;
		}

		return "error";

	}

	/**
	 * 生成交易流水
	 */
	public LoanManagementAcTFlow setActFlow(double tradeAmount, String teller, String Organ, String account, String appoAcc, String acctTitle, String appoAcctTitle) {
		LoanManagementAcTFlow actFlow = new LoanManagementAcTFlow();
		actFlow.setTradeDate(new Timestamp(System.currentTimeMillis()));// 交易日期
		actFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
		actFlow.setTradeAmount(tradeAmount);// 交易金额
		actFlow.setTradeType("1");// 交易类型:现金
		actFlow.setTeller(teller);// 柜员号
		actFlow.setOrgan(Organ);// 营业网点
		actFlow.setAccount(account);// 账号
		actFlow.setAppoAcct(appoAcc);// 对方账户
		actFlow.setAcctTitle(acctTitle);// 科目号
		actFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
		actFlow.setMemo("操作科目：" + ConstSubject.getNameBySubject(acctTitle));// 备注
		return actFlow;
	}

	@Transactional(readOnly = false)
	public String isBind(String phoneNo, String validateCode, HttpServletRequest req, HttpServletResponse rep) {
		FinancialUsers currUser = getCurrentUser(req, rep);
		List<FinancialUserInfoPerson> userInfoList = financialUserInfoPersonDao.getUserPhoneBakInof(phoneNo);
		FinancialUserInfoPerson userInfoPerson = currUser.getUserInfoPerson();
		// 手机号码在数据库里是否存在
		if (userInfoList.size() == 0) {
			return "resend";
		}
		if (userInfoPerson != null) {
			if (!userInfoPerson.getPhonNoBak().equals(phoneNo)) {
				return "noMatch";
			} else {
				if (!validateCode.equals(userInfoPerson.getPhoneValidateCode())) {
					return "error";
				}
				if (currUser.getIsApprovePhone() == null || !currUser.getIsApprovePhone().equals(BigDecimal.ONE)) {
					currUser.setUserInfoPerson(userInfoPerson);
					currUser.setIsApprovePhone(BigDecimal.ONE);
					logger.info("SAVE: USERS || userId=" + currUser.getUserId() + " PhoneNo=" + phoneNo + " tPhoneValidateCode=" + validateCode + " IsApprovePhone=1");
				}
				userInfoPerson.setPhoneNo(phoneNo);
				financialUsersDao.save(currUser);
				return "success";
			}
		} else {
			FinancialUserInfoPerson newUserInfoPerson = new FinancialUserInfoPerson();
			newUserInfoPerson.setPhoneNo(phoneNo);
			newUserInfoPerson.setPhoneValidateCode(validateCode);
			currUser.setUserInfoPerson(newUserInfoPerson);
			currUser.setIsApprovePhone(BigDecimal.ONE);
			logger.info("SAVE: USERS || userId=" + currUser.getUserId() + " PhoneNo=" + phoneNo + " tPhoneValidateCode=" + validateCode + " IsApprovePhone=1");
			financialUsersDao.save(currUser);
			return "match";
		}
		// 手机号码在数据库里存在
		// if (userInfoPerson != null) {
		// // 手机号码不输入自己
		// if
		// (!userInfoPerson.getUser().getUserId().equals(currUser.getUserId()))
		// {
		// return "noMatch";
		// } else {
		// // 手机验证码匹配
		// if (!validateCode.equals(userInfoPerson.getPhoneValidateCode())) {
		// return "error";
		// } else {
		// if (currUser.getUserInfoPerson() != null) {
		// if (currUser.getIsApprovePhone() == null ||
		// !currUser.getIsApprovePhone().equals(BigDecimal.ONE)) {
		// currUser.setUserInfoPerson(userInfoPerson);
		// currUser.setIsApprovePhone(BigDecimal.ONE);
		// logger.info("SAVE: USERS || userId=" + currUser.getUserId() +
		// " PhoneNo=" + phoneNo + " tPhoneValidateCode=" + validateCode +
		// " IsApprovePhone=1");
		// }
		// userInfoPerson.setPhoneNo(phoneNo);
		// financialUsersDao.save(currUser);
		// return "success";
		// } else {
		// FinancialUserInfoPerson newUserInfoPerson = new
		// FinancialUserInfoPerson();
		// newUserInfoPerson.setPhoneNo(phoneNo);
		// newUserInfoPerson.setPhoneValidateCode(validateCode);
		// currUser.setUserInfoPerson(newUserInfoPerson);
		// currUser.setIsApprovePhone(BigDecimal.ONE);
		// logger.info("SAVE: USERS || userId=" + currUser.getUserId() +
		// " PhoneNo=" + phoneNo + " tPhoneValidateCode=" + validateCode +
		// " IsApprovePhone=1");
		// financialUsersDao.save(currUser);
		// return "match";
		// }
		// }
		// }
		//
		// } else {
		// return "resend";
		// }
	}
}
