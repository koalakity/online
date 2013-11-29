package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.dao.account.AdminUserCreditNoteDao;
import com.zendaimoney.online.admin.dao.account.AdminUserInfoPersonDao;
import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.account.UserCreditNoteAdmin;
import com.zendaimoney.online.common.CipherUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.register.AcTCustomerDao;
import com.zendaimoney.online.dao.register.UserDao;
import com.zendaimoney.online.entity.register.RegisterAcTCustomer;
import com.zendaimoney.online.entity.register.RegisterAcTLedger;
import com.zendaimoney.online.entity.register.RegisterUsers;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-11-21 上午10:31:09 operation by:
 *        description:此类用于此后台客服为客户新注册的Service层
 */
@Component
@Transactional(readOnly = true)
public class UserRegisterService {

	private static Logger logger = LoggerFactory.getLogger(UserRegisterService.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private AcTCustomerDao acTCustomerDao;
	@Autowired
	private AdminUserInfoPersonDao userInfoPersonDao;
	@Autowired
	private AdminUserCreditNoteDao userCreditNoteDao;
	@Autowired
	private MimeMailService mimeMailService;
	@Autowired
	private CommonDao commonDao;

	/**
	 * @author Ray
	 * @date 2012-11-21 上午10:42:21
	 * @param users
	 * @param serverPath
	 *            description:保存用户登录信息
	 */
	@Transactional(readOnly = false)
	public String save(AccountUsersAdmin ausers, String serverPath) {
		RegisterUsers users = new RegisterUsers();
		users.setChannelInfo(ausers.getChannelInfo());
		users.setLoginName(ausers.getLoginName());
		users.setLoginPassword(ausers.getLoginPassword());
		users.setEmail(ausers.getEmail());
		users.setRegType(BigDecimal.valueOf(2));// 后台注册默认为2
		users.setRegIp(ausers.getRegIp());// 注入注册IP
		users.setLoginPassword(ausers.getLoginPassword());// 注入注册密码
		Date date = new Date(); // 取系统时间
		users.setRegTime(new Timestamp(date.getTime()));
		users.setUserStatus(BigDecimal.ONE);
		int i = (int) Math.round(Math.random() * 100000000);
		String email = users.getEmail();
		users.setEmail(users.getEmail());
		users.setEmailacCode(String.valueOf(i));
		users.setIsapproveEmail(BigDecimal.ZERO);
		users.setIsApproveCard(BigDecimal.ZERO);
		users.setIsApprovePhone(BigDecimal.ZERO);
		users.setDelStatus(BigDecimal.ZERO);
		/*
		 * RegisterUserInfoPerson ruip = new RegisterUserInfoPerson();
		 * ruip.setUser(users);
		 */
		RegisterAcTCustomer rac = new RegisterAcTCustomer();
		String racl = acTCustomerDao.findByCustomerNoMaxVal();
		// BigDecimal customerNo = null;
		/* 2013-1-16 Ray 修复总账号重复，采用获取seq */
		Long customerNo = commonDao.getSequenceByName("CUSTOMER_NO_SEQ");
		rac.setCustomerNo(customerNo.toString());
		logger.info("后台  获取customer_NO：" + customerNo + " email:" + users.getEmail() + " userName:" + users.getLoginName());
		// if(null == racl){
		// customerNo = BigDecimal.valueOf(Long.parseLong("5000001"));
		// rac.setCustomerNo("5000001");
		// }else {
		// customerNo =
		// BigDecimal.valueOf(Long.parseLong(racl)).add(BigDecimal.ONE);
		// rac.setCustomerNo(customerNo.toString());
		// }
		rac.setType("3");
		rac.setName(users.getLoginName());
		rac.setPassword1(CipherUtil.generatePassword(users.getLoginPassword()));
		rac.setPwdDate(date);
		rac.setTotalAcct("3000000000001" + customerNo.toString());
		rac.setAcctStatus("1");
		rac.setOpenacctOrgan("3000000000001");
		rac.setOpenacctDate(date);
		rac.setUser(users);
		// 分账信息表
		Set<RegisterAcTLedger> ledger = new HashSet<RegisterAcTLedger>();
		RegisterAcTLedger acTLedger = new RegisterAcTLedger();

		/***** 2013-1-24 修正分账号1为 总账号+0001 ******/
		// int r = (int)(Math.random()*9000+1000);
		String account = rac.getTotalAcct() + "0001";
		acTLedger.setAccount(account);
		acTLedger.setTotalAccountId(rac);
		acTLedger.setOpenacctDate(new Date());
		acTLedger.setBusiType("1");
		// 现金
		acTLedger.setAmount(0d);
		// 当前投资金额
		acTLedger.setDebtAmount(0d);
		// 投资金额
		acTLedger.setInvestAmount(0d);
		// 应收利息
		acTLedger.setInterestReceivable(0d);
		// 其他应收款
		acTLedger.setOtherReceivale(0d);
		// 贷款本金
		acTLedger.setLoanAmount(0d);
		// 应付利息
		acTLedger.setInterestPayable(0d);
		// 其他应付款
		acTLedger.setToherPayable(0d);
		// 利息收入
		acTLedger.setInterestIncome(0d);
		// 其他收入
		acTLedger.setOtherIncome(0d);
		// 营业外收入
		acTLedger.setNonoperatIncome(0d);
		// 利息支出
		acTLedger.setInterestExpenditure(0d);
		// 其他支出
		acTLedger.setOtherExpenditure(0d);
		// 营业外支出
		acTLedger.setNonoperatExpenditure(0d);
		// 待回收金额
		acTLedger.setPayBackAmt(0d);

		RegisterAcTLedger acTLedger2 = new RegisterAcTLedger();
		/***** 2013-1-24 修正分账号2为 总账号+0002 ******/
		// int r2 = (int)(Math.random()*9000+1000);
		String account2 = rac.getTotalAcct() + "0002";
		acTLedger2.setAccount(account2);
		acTLedger2.setTotalAccountId(rac);
		acTLedger2.setOpenacctDate(new Date());
		acTLedger2.setBusiType("2");
		// 现金
		acTLedger2.setAmount(0d);
		// 当前投资金额
		acTLedger2.setDebtAmount(0d);
		// 投资金额
		acTLedger2.setInvestAmount(0d);
		// 应收利息
		acTLedger2.setInterestReceivable(0d);
		// 其他应收款
		acTLedger2.setOtherReceivale(0d);
		// 贷款本金
		acTLedger2.setLoanAmount(0d);
		// 应付利息
		acTLedger2.setInterestPayable(0d);
		// 其他应付款
		acTLedger2.setToherPayable(0d);
		// 利息收入
		acTLedger2.setInterestIncome(0d);
		// 其他收入
		acTLedger2.setOtherIncome(0d);
		// 营业外收入
		acTLedger2.setNonoperatIncome(0d);
		// 利息支出
		acTLedger2.setInterestExpenditure(0d);
		// 其他支出
		acTLedger2.setOtherExpenditure(0d);
		// 营业外支出
		acTLedger2.setNonoperatExpenditure(0d);
		// 待回收金额
		acTLedger2.setPayBackAmt(0d);
		RegisterAcTLedger acTLedger3 = new RegisterAcTLedger();
		/***** 2013-1-24 修正分账号4为 总账号+0004 ******/
		// int r3 = (int)(Math.random()*9000+1000);
		String account3 = rac.getTotalAcct() + "0004";
		acTLedger3.setAccount(account3);
		acTLedger3.setTotalAccountId(rac);
		acTLedger3.setOpenacctDate(new Date());
		acTLedger3.setBusiType("4");
		// 现金
		acTLedger3.setAmount(0d);
		// 当前投资金额
		acTLedger3.setDebtAmount(0d);
		// 投资金额
		acTLedger3.setInvestAmount(0d);
		// 应收利息
		acTLedger3.setInterestReceivable(0d);
		// 其他应收款
		acTLedger3.setOtherReceivale(0d);
		// 贷款本金
		acTLedger3.setLoanAmount(0d);
		// 应付利息
		acTLedger3.setInterestPayable(0d);
		// 其他应付款
		acTLedger3.setToherPayable(0d);
		// 利息收入
		acTLedger3.setInterestIncome(0d);
		// 其他收入
		acTLedger3.setOtherIncome(0d);
		// 营业外收入
		acTLedger3.setNonoperatIncome(0d);
		// 利息支出
		acTLedger3.setInterestExpenditure(0d);
		// 其他支出
		acTLedger3.setOtherExpenditure(0d);
		// 营业外支出
		acTLedger3.setNonoperatExpenditure(0d);
		// 待回收金额
		acTLedger3.setPayBackAmt(0d);
		RegisterAcTLedger acTLedger4 = new RegisterAcTLedger();
		/***** 2013-1-24 修正分账号5为 总账号+0005 ******/
		// int r4 = (int)(Math.random()*9000+1000);
		String account4 = rac.getTotalAcct() + "0005";
		acTLedger4.setAccount(account4);
		acTLedger4.setTotalAccountId(rac);
		acTLedger4.setOpenacctDate(new Date());
		acTLedger4.setBusiType("5");
		// 现金
		acTLedger4.setAmount(0d);
		// 当前投资金额
		acTLedger4.setDebtAmount(0d);
		// 投资金额
		acTLedger4.setInvestAmount(0d);
		// 应收利息
		acTLedger4.setInterestReceivable(0d);
		// 其他应收款
		acTLedger4.setOtherReceivale(0d);
		// 贷款本金
		acTLedger4.setLoanAmount(0d);
		// 应付利息
		acTLedger4.setInterestPayable(0d);
		// 其他应付款
		acTLedger4.setToherPayable(0d);
		// 利息收入
		acTLedger4.setInterestIncome(0d);
		// 其他收入
		acTLedger4.setOtherIncome(0d);
		// 营业外收入
		acTLedger4.setNonoperatIncome(0d);
		// 利息支出
		acTLedger4.setInterestExpenditure(0d);
		// 其他支出
		acTLedger4.setOtherExpenditure(0d);
		// 营业外支出
		acTLedger4.setNonoperatExpenditure(0d);
		// 待回收金额
		acTLedger4.setPayBackAmt(0d);
		ledger.add(acTLedger);
		ledger.add(acTLedger2);
		ledger.add(acTLedger3);
		ledger.add(acTLedger4);
		rac.setAcTLedger(ledger);

		users.settCustomerId(rac);
		acTCustomerDao.save(rac);
		userDao.save(users);

		BigDecimal userId = BigDecimal.ZERO;
		List<RegisterUsers> userLsit = findByEmail(users.getEmail());
		if (userLsit.size() > 0) {
			userId = userLsit.get(0).getUserId();// 获取新建用户的userId
		}

		// savePersonalBase(userId,Ausers);
		// saveUserCreditNote(userId,Ausers);

		// userInfoPersonDao.save(ruip); //新用户注册的时候，无需更改到user_info_person表
		String url = serverPath + "/register/register/activationEmail?username=" + users.getLoginName() + "&activationId=" + i;
		mimeMailService.sendNotificationMail(url, email, "邮箱激活");
		return userId.toString();
	}

	/**
	 * @author Ray
	 * @date 2012-11-21 上午11:17:32
	 * @param user
	 *            description:
	 */
	private void savePersonalBase(BigDecimal userId, AccountUsersAdmin user) {
		AccountUserInfoPersonAdmin userInfoPersion = user.getUserInfoPerson();
		userInfoPersion.setUserId(userId);// 注入UserId
		userInfoPersonDao.save(userInfoPersion);
	}

	/**
	 * @author Ray
	 * @date 2012-11-21 上午11:33:25
	 * @param userId
	 * @param user
	 *            description:保存用户信用等级
	 */
	private void saveUserCreditNote(BigDecimal userId, AccountUsersAdmin user) {
		user.setUserId(userId);
		UserCreditNoteAdmin userCreditNote = user.getUserCreditNote();
		userCreditNote.setUser(user);
		userCreditNoteDao.save(userCreditNote);

	}

	/**
	 * @author Ray
	 * @date 2012-11-21 上午10:33:26
	 * @param email
	 * @return description:根据Email查找数据库Email
	 */
	public List<RegisterUsers> findByEmail(String email) {
		List<RegisterUsers> user = userDao.findByEmail(email);
		return user;
	}

	/**
	 * @author Ray
	 * @date 2012-11-21 上午10:38:02
	 * @param loginName
	 * @return description:获取LoginName的List
	 */
	public RegisterUsers findByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	/**
	 * @author Ray
	 * @date 2012-11-22 下午3:03:02
	 * @param loginName
	 * @param serverPath
	 * @param email
	 * @return
	 * @throws Exception
	 *             description: 重新发送邮箱验证
	 */
	@Transactional(readOnly = false)
	public RegisterUsers againSendEmail(String loginName, String serverPath, String email) throws Exception {
		RegisterUsers users = userDao.findByLoginName(loginName);
		int i = (int) Math.round(Math.random() * 100000000);
		users.setEmail(email);
		users.setEmailacCode(String.valueOf(i));
		userDao.save(users);
		String url = serverPath + "/register/register/activationEmail?username=" + users.getLoginName() + "&activationId=" + i;
		mimeMailService.sendNotificationMail(url, email.trim(), "邮箱激活");
		return users;
	}

}
