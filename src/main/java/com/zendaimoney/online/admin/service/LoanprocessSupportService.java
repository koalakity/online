package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.dao.AcTLedgerAdminDAO;
import com.zendaimoney.online.admin.dao.AcTVirtualCashFlowAdminDAO;
import com.zendaimoney.online.admin.dao.FreezeFundsAdminDAO;
import com.zendaimoney.online.admin.dao.InvestInfoAdminDAO;
import com.zendaimoney.online.admin.dao.LoanInfoAdminDAO;
import com.zendaimoney.online.admin.dao.LoanProcessSupportDao;
import com.zendaimoney.online.admin.dao.OverdueClaimsAdminDAO;
import com.zendaimoney.online.admin.dao.SysMsgAdminDAO;
import com.zendaimoney.online.admin.dao.UserInfoPersonAdminDAO;
import com.zendaimoney.online.admin.dao.UserMessageSetAdminDAO;
import com.zendaimoney.online.admin.dao.UserMovementAdminDAO;
import com.zendaimoney.online.admin.dao.UsersAdminDAO;
import com.zendaimoney.online.admin.entity.AcTCustomerAdminVO;
import com.zendaimoney.online.admin.entity.AcTLedgerAdminVO;
import com.zendaimoney.online.admin.entity.AcTLedgerFinanceAdminVO;
import com.zendaimoney.online.admin.entity.AcTLedgerLoanAdminVO;
import com.zendaimoney.online.admin.entity.AcTVirtualCashFlowAdminVO;
import com.zendaimoney.online.admin.entity.FreezeFundsAdminVO;
import com.zendaimoney.online.admin.entity.InvestInfoAdminVO;
import com.zendaimoney.online.admin.entity.LoanInfoAdminVO;
import com.zendaimoney.online.admin.entity.OverdueClaimsAdminId;
import com.zendaimoney.online.admin.entity.OverdueClaimsAdminVO;
import com.zendaimoney.online.admin.entity.SysMsgAdminVO;
import com.zendaimoney.online.admin.entity.UserInfoPersonAdminVO;
import com.zendaimoney.online.admin.entity.UserMessageSetAdminVO;
import com.zendaimoney.online.admin.entity.UserMovementAdminVO;
import com.zendaimoney.online.admin.entity.UsersAdminVO;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.Formula;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.entity.AcTFlowVO;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.oii.sms.SMSSender;
import com.zendaimoney.online.service.common.FlowUtils;
import com.zendaimoney.online.service.common.RateCommonUtil;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-20 下午2:16:41
 * operation by: Ray
 * description:借款信息流标处理
 */
@Component
@Transactional
public class LoanprocessSupportService {

	private static Logger logger = LoggerFactory.getLogger(LoanprocessSupportService.class);
	@Autowired
	private LoanInfoAdminDAO loanInfoDao;
	
	@Autowired
	private AcTLedgerAdminDAO acTLedgerDao;
	
	@Autowired
	private UsersAdminDAO usersDao;
	
	@Autowired
	private FlowUtils flowUtils;
	
	@Autowired
	private FreezeFundsAdminDAO freezeFundsDao;
	
	
	@Autowired
	private UserMessageSetAdminDAO userMessageSetDao;
	
	@Autowired
	private SysMsgAdminDAO sysMsgDao;
	
	@Autowired
	MimeMailService mimeMailService;
	
	@Autowired
	private UserInfoPersonAdminDAO userInfoPersonDao;
	
	@Autowired
	private	LoanProcessSupportDao loanProcessSupportDao;
	
	@Autowired
	private RateCommonUtil rateCommonUtil;
	
	@Autowired
	private UserMovementAdminDAO userMovementAdminDao;
	
	@Autowired
	private InvestInfoAdminDAO investInfoAdmindao;
	
	@Autowired
	private OverdueClaimsAdminDAO overdueClaimsAdminDao;
	
	@Autowired
	private AcTVirtualCashFlowAdminDAO acTVirtualCashFlowAdminDao;
	
	@Autowired
	private AcTLedgerAdminDAO acTLedgerAdminDao;
	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-20 下午2:16:00
	 * @param loanId
	 * @return
	 * description:借款信息流标处理
	 */
	public LoanInfoAdminVO abortiveTenderHandle(Long loanId) throws RuntimeException{
		LoanInfoAdminVO loanInfo = loanInfoDao.findOne(loanId);// 获取借款信息
		if (Long.valueOf(3L).equals(loanInfo.getStatus())) {
			throw new RuntimeException("该笔借款已流标，请勿重复提交。");
		}
		if (loanInfo.getStatus().equals(1L) || loanInfo.getStatus().equals(2L) || loanInfo.getStatus().equals(8L)) {
			// LoanManagementUsers user = loanInfo.getUser();// 获取用户
			// 1.调用maintainFreezeFoud方法维护冻结资金表
			// maintainFreezeFoud(user.getUserId(),loanInfo.getLoanId());
			// 2. 维护借款信息 ,理财信息,理财分户信息 ,分账信息
			LoanInfoAdminVO loanManagementLoanInfo = maitainInvestInfo(loanInfo);
			return loanManagementLoanInfo;// 保存借款信息
		} else {
			return loanInfo;
		}
	}

	
	
	/**
	 * @author Ray
	 * @date 2013-2-20 下午2:28:39
	 * @param loanInfo
	 * @return
	 * description:借款信息流标处理__借款信息表维护
	 */
	public LoanInfoAdminVO maitainInvestInfo(LoanInfoAdminVO loanInfo) throws RuntimeException{
		loanInfo.setStatus(3L);// 设置借款信息的状态为3：流标
		int count=loanProcessSupportDao.updateLoanInfoByIdAndStatus(loanInfo.getLoanId(),3L);
		logger.info("更新LOAN_INFO影响总行数："+count);
		if(count!=0){
			if (null != loanInfo.getActLedgerLoan()) {
				loanInfo.getActLedgerLoan().setAcctStatus("9");// 设置贷款分户信息为9：销户
			}
			if (null != loanInfo.getInvestInfoList()) {
				for (InvestInfoAdminVO investInfo : loanInfo.getInvestInfoList()) {
					maintainFreezeFoud(investInfo.getUserId(), loanInfo.getLoanId());// 调用maintainFreezeFoud方法维护冻结资金表
					investInfo.setStatus("1");// 设置理财信息状态为1：流标
					investInfo.getAcTLedgerFinance().setAcctStatus("9");// 设置理财分户状态为9：销户
					maitainActLeger(investInfo.getUserId(), investInfo); // 调用maitainActLeger
					// 维护分账信息表
				}
			}
		}else{
			throw new RuntimeException("该笔借款已流标，请勿重复提交。");
		}
		return loanInfo;
	}

	
	/**
	 * @author Ray
	 * @date 2013-2-20 下午2:57:17
	 * @param userId
	 * @param loanId
	 * description:冻结资金维护
	 */
	public void maintainFreezeFoud(Long userId, Long loanId) {
		List<FreezeFundsAdminVO> freezeFundsList = freezeFundsDao.findByUserIdAndLoanIdAndFreezeKind(userId, loanId, "1");
		for (FreezeFundsAdminVO freezeFunds : freezeFundsList) {
			freezeFunds.setFreezeStatus("0");
			freezeFundsDao.save(freezeFunds);
		}
	}

	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-20 下午3:48:14
	 * @param userId 理财人ID 
	 * @param investInfo 理财人投资信息
	 * description:借款信息流标处理__分账信息表维护
	 */
	public void maitainActLeger(Long userId, InvestInfoAdminVO investInfo) {
		BigDecimal investAmount = investInfo.getInvestAmount();
//		UsersAdminVO user=usersDao.findByUserId(userId);
//		AcTLedgerAdminVO freezeActledger = acTLedgerDao.findByBusiTypeAndTotalAccountId("5",user.gettCustomerId());// 业务类别：5：冻结资金账户
//		AcTLedgerAdminVO investAmountActLedger = acTLedgerDao.findByBusiTypeAndTotalAccountId("4", user.gettCustomerId()); // 业务类别：4：资金账户
		AcTLedgerAdminVO freezeActledger = loanProcessSupportDao.getAcTLedger(userId,"5");// 业务类别：5：冻结资金账户
		AcTLedgerAdminVO investAmountActLedger = loanProcessSupportDao.getAcTLedger(userId,"4"); // 业务类别：4：资金账户
		// LoanManagementAcTLedger investActLedger =
		// loanManagementActLegerDao.findByBusiTypeAndAccountLike("1",actCustomerInvsert.getTotalAcct()+"%");
		// //业务类别：1：理财账户
		// 冻结资金解冻（解冻金额等于当前投标金额）
		
		freezeActledger.setAmount(BigDecimalUtil.sub(freezeActledger.getAmount(), investInfo.getInvestAmount()));// 冻结账户
																											// 金额减去
																											// 本次流标投资金额
		investAmountActLedger.setAmount(BigDecimalUtil.add(investAmountActLedger.getAmount(), investInfo.getInvestAmount()));// 资金账户
																														// 加上
																														// 本次流标投资金额
		// investActLedger.setInvestAmount(ArithUtil.sub(investActLedger.getInvestAmount(),
		// investInfo.getInvestAmount()));// 理财账户 投资金额 减去本次流标投资金额
		// ⑴理财人当前投资冻结资金解冻，资金流入理财人资金账户
		flowUtils.setActFlow(investAmount, "", "3000000000001", freezeActledger.getAccount(), investAmountActLedger.getAccount(), NewConstSubject.bid_unfree_out, NewConstSubject.bid_freeze_in);
	}
	
	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-20 下午4:12:45
	 * @param loanInfo 借款信息
	 * @param bidFlag   流标类型 0：手动流标 1：自动流标
	 * description:流标处理时,放款时给借款人和投标人发送短信
	 */
	public void sendPhoneMessageToborrowerAndInvest(LoanInfoAdminVO loanInfo, int bidFlag) {
		Map<String, String> map = new HashMap<String, String>();
		UsersAdminVO user =usersDao.findByUserId(loanInfo.getUserId());// 获取借款人信息
		List<UserMessageSetAdminVO> messageList = userMessageSetDao.findByUserIdAndKindId(loanInfo.getUserId(), 3L);
		String loginName = user.getLoginName();
		String laonTitle = loanInfo.getLoanTitle();
		// 给借款人发送信息，根据通知设置里设置的条件发送信息
		// 发送系统消息
		SysMsgAdminVO sysMsg = new SysMsgAdminVO();
		sysMsg.setUserId(user.getUserId());
		if (bidFlag == 0) {
			sysMsg.setWordId(9L);
		} else {
			sysMsg.setWordId(10L);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			sysMsg.setParameter3(sdf.format(new Date()));
		}
		sysMsg.setParameter1(loginName);
		sysMsg.setParameter2(laonTitle);
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		sysMsgDao.save(sysMsg);
		for (UserMessageSetAdminVO message : messageList) {
			if (message.getMannerId().equals(2L)) {
				map.put("0", loginName);
				map.put("1", laonTitle);
				String bidType = null;
				// bigFlag 为0代表手动流标设置短信模板为flow_bid
				if (bidFlag == 0) {
					bidType = "flow_bid";
					// bigFlag 为0代表筹标时间大于七天设为自动流标设置短信模板为over_date
				} else {
					bidType = "flow_bid";
					DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("2", dateFormate.format(new Date()));
				}
				String messages = mimeMailService.transferMailContent(bidType, map);
				mimeMailService.sendNotifyMail(messages, user.getEmail(), "我的借款列表流标");
			}
			if (message.getMannerId().equals(3L)) {
				UserInfoPersonAdminVO userInfoPerson = userInfoPersonDao.findByUserId(user.getUserId());
				String loanUserphoneNo = userInfoPerson.getPhoneNo();
				map.put("0", loginName);
				map.put("1", laonTitle);
				String bidType = null;
				// bigFlag 为0代表手动流标设置短信模板为flow_bid
				if (bidFlag == 0) {
					bidType = "flow_bid";
					// bigFlag 为0代表筹标时间大于七天设为自动流标设置短信模板为over_date
				} else {
					bidType = "flow_bid";
					DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("2", dateFormate.format(new Date()));
				}
				SMSSender.sendMessage(bidType, loanUserphoneNo, map);
			}
		}
		
		// 给投资人发送短信
		List<InvestInfoAdminVO> investList = new ArrayList<InvestInfoAdminVO>();
		if (null != loanInfo.getInvestInfoList()) {
			Set<Long> hasSet = new HashSet<Long>();
			for (InvestInfoAdminVO investInfo : loanInfo.getInvestInfoList()) {
				if (hasSet.add(investInfo.getUserId())) {
					investList.add(investInfo);
				}
			}
			for (InvestInfoAdminVO invest : investList) {
				List<UserMessageSetAdminVO> messageList2 = userMessageSetDao.findByUserIdAndKindId(invest.getUserId(), 6L);
				sendSysMessageToInvestors(invest, laonTitle);
				for (UserMessageSetAdminVO message : messageList2) {
					if (message.getMannerId().equals(2L)) {
						sendMailMessageToInvestors(invest, laonTitle);
					}
					if (message.getMannerId().equals(3L)) {
						sendPhoneMessageToInvestors(invest, laonTitle);
					}
				}
			}
		}
	}
	
	
	public void sendSysMessageToInvestors(InvestInfoAdminVO investInfo, String laonTitle) {
		UsersAdminVO user =usersDao.findByUserId(investInfo.getUserId());// 获取理财人信息
		String loginName = user.getLoginName();
		SysMsgAdminVO sysMsg1 = new SysMsgAdminVO();
		sysMsg1.setUserId(user.getUserId());
		sysMsg1.setWordId(13L);
		sysMsg1.setParameter1(loginName);
		sysMsg1.setParameter2(laonTitle);
		sysMsg1.setHappenTime(new Date());
		sysMsg1.setIsDel("0");
		sysMsgDao.save(sysMsg1);
	}

	
	/**
	 * @author Ray
	 * @date 2013-3-11 下午12:24:04
	 * @param investInfo
	 * @param laonTitle
	 * description:借款流标 发送邮件给投标人
	 */
	public void sendMailMessageToInvestors(InvestInfoAdminVO investInfo, String laonTitle) {
		Map<String, String> map = new HashMap<String, String>();
		UsersAdminVO user =usersDao.findByUserId(investInfo.getUserId());// 获取理财人信息
		String loginName = user.getLoginName();
		map.put("0", loginName);
		map.put("1", laonTitle);
		String messages = mimeMailService.transferMailContent("invest_flow_bid", map);
		mimeMailService.sendNotifyMail(messages, user.getEmail(), "我的投标流标");
	}
	
	
	/**
	 * @author Ray
	 * @date 2013-3-11 下午12:25:00
	 * @param investInfo
	 * @param laonTitle
	 * description:借款流标 发送短信给投标人
	 */
	public void sendPhoneMessageToInvestors(InvestInfoAdminVO investInfo, String laonTitle) {
		Map<String, String> map = new HashMap<String, String>();
		UsersAdminVO user =usersDao.findByUserId(investInfo.getUserId());// 获取理财人信息
		UserInfoPersonAdminVO userInfoPerson = userInfoPersonDao.findByUserId(user.getUserId());
		String loanUserphoneNo = userInfoPerson.getPhoneNo();
		String loginName = user.getLoginName();
		map.put("0", loginName);
		map.put("1", laonTitle);
		SMSSender.sendMessage("invest_flow_bid", loanUserphoneNo, map);
	}
	
	/**
	 * 后台放款处理
	 */
	public synchronized String loanProcess(Long loanId) {
		LoanInfoAdminVO loanInfo = loanInfoDao.findOne(loanId);// 获取借款信息
		if(!Long.valueOf(2L).equals(loanInfo.getStatus())){
			if(Long.valueOf(4L).equals(loanInfo.getStatus())){
				return "当前借款已完成放款，不能重复操作！";
			}else{
				return "操作错误";
			}
			
		}
		BigDecimal amount_loan = loanInfo.getLoanAmount();
		int term_loan = loanInfo.getLoanDuration().intValue();
		Date date_loan = DateUtil.getCurrentDate();
		// 各种费率信息,取自费率表
//		Rate rate = commonDao.getRate(1l);
		/*浮动费率功能新增，获取费率直接根据loanId获取该笔借款的费率*/
		LoanRateVO rate=rateCommonUtil.getLoanCoRate(loanId);
		// 年利率
		BigDecimal rate_year = loanInfo.getYearRate();
		// 网站管理费率
		BigDecimal rate_management_fee_month = rate.getMgmtFee();
		// 提前还款违约金费率
		BigDecimal rate_early_repayment = rate.getEarlyFines();
		// 逾期罚息费率
		BigDecimal rate_overdue_interest = rate.getOverdueInterest();
		// 逾期违约金费率
		BigDecimal rate_overdue_fines = rate.getOverdueFines();
		// 借款手续费率
		BigDecimal loan_rate = rate.getLoan();
		// 风险准备金费率
		BigDecimal reserve_Foud = rate.getReserveFoud();
		// 等额本息计算公式
		Formula formula = new Formula(amount_loan.doubleValue(), term_loan, date_loan, rate_year.doubleValue(), rate_management_fee_month.doubleValue(), rate_early_repayment.doubleValue(), rate_overdue_interest.doubleValue(), rate_overdue_fines.doubleValue());
		// 借款手续费
		BigDecimal loanProceduresFee =new  BigDecimal(FormulaSupportUtil.getManagementFeeEveryMonth(amount_loan.doubleValue(), loan_rate.doubleValue()));
		// 风险准备金
		BigDecimal reserveFoud = FormulaSupportUtil.getReserveFoud(amount_loan, reserve_Foud);
		// 借款人核心客户id
//		Long tCustomerId = loanInfo.getUser().gettCustomerId();
		// 理财人核心客户信息
//		LoanManagementAcTCustomer actCustomerLoan = loanmanagementAcTCustomerDao.findById(tCustomerId);
		// 投标信息
		List<InvestInfoAdminVO> investInfoList = loanInfo.getInvestInfoList();
		// 后台放款
		setRepayAmount(investInfoList, loanInfo, formula, loanProceduresFee, reserveFoud);
		// //放款成功后信用额度维护
		// LoanmanagementUserCreditNote userCreditNote =
		// userCreditNoteDao.findByUserId(loanInfo.getUser().getUserId());
		// //用户信用额度
		// double newCreditAmount =
		// ArithUtil.sub(userCreditNote.getCreditAmount().doubleValue(),
		// loanInfo.getLoanAmount());
		// userCreditNote.setCreditAmount(new BigDecimal(newCreditAmount));
		// userCreditNoteDao.save(userCreditNote);
		// 发送系统消息
		UsersAdminVO user =usersDao.findByUserId(loanInfo.getUserId());// 获取借款人信息
		SysMsgAdminVO sysMsg = new SysMsgAdminVO();
		sysMsg.setUserId(loanInfo.getUserId());
		sysMsg.setWordId(16L);
		sysMsg.setParameter1(user.getLoginName());
		sysMsg.setParameter2(loanInfo.getLoanTitle());
		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=dateFormate.format(new Date());
		sysMsg.setParameter3(date);
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		
		//借款人放款短信和邮件2013-4-1新增 Ray
		Map<String, String> map = new HashMap<String, String>();
		map.put("0", user.getLoginName());
		map.put("1", loanInfo.getLoanTitle());
		map.put("2", date);
		String messages = mimeMailService.transferMailContent("loan_Process", map);
		mimeMailService.sendNotifyMail(messages, user.getEmail(), "我的借款成功");
		UserInfoPersonAdminVO userInfoPerson = userInfoPersonDao.findByUserId(user.getUserId());
		String loanUserphoneNo = userInfoPerson.getPhoneNo();
		SMSSender.sendMessage("loan_Process", loanUserphoneNo, map);
		
		UserMovementAdminVO movement = new UserMovementAdminVO();
		movement.setUserId(loanInfo.getUserId());
		movement.setWordId(4L);
		movement.setParameter1(loanInfo.getLoanTitle());
		movement.setUrl1("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
		movement.setMsgKind("1");
		movement.setHappenTime(new Date());
		movement.setIsDel("0");
		userMovementAdminDao.save(movement);
		sysMsgDao.save(sysMsg);
		return null;
	}
	
	/**
	 * @author Ray
	 * @date 2013-2-25 下午4:58:33
	 * @param investInfoList
	 * @param currRepayLoanInfo
	 * @param loanUserId 借款人ID
	 * @param formula
	 * @param loanProceduresFee
	 * @param reserveFoud
	 * description:后台放款后，理财人冻结资金变为理财投资金额，借款人得到放款金额=借款金额-借款手续费-风险准备金， 证大收取借款手续费和借款的风险准备金
	 */
	public void setRepayAmount(List<InvestInfoAdminVO> investInfoList, LoanInfoAdminVO currRepayLoanInfo, Formula formula, BigDecimal loanProceduresFee, BigDecimal reserveFoud) {
		
		//加锁
		int success=loanProcessSupportDao.updateLoanInfo(currRepayLoanInfo.getLoanId(), 4L);
		//更新影响行数不为0，则继续
		if(success!=0){
			// 投标金额统计
			BigDecimal loanAmountTotal = BigDecimal.ZERO;
			// 实际到帐现金
			BigDecimal actualToAccountAmount = BigDecimal.ZERO;
			// 借款期数
			int term_loan = currRepayLoanInfo.getLoanDuration().intValue();
			// 借款人分账
			AcTLedgerAdminVO loanActLedger =  loanProcessSupportDao.getAcTLedger(currRepayLoanInfo.getUserId(),"2"); // 业务类别：2：贷款账户
			AcTLedgerAdminVO loanAmountActLedger =  loanProcessSupportDao.getAcTLedger(currRepayLoanInfo.getUserId(),"4"); // 业务类别：4：资金账户
			AcTLedgerAdminVO loanFreezeActLedger =  loanProcessSupportDao.getAcTLedger(currRepayLoanInfo.getUserId(),"5"); // 业务类别：5：借款人投标冻结账户
			
			BigDecimal loanCashAmount =loanAmountActLedger.getAmount();
			logger.info("初始化借款人现金账户值："+loanAmountActLedger.getAmount());
			
			Map<Long,BigDecimal> investMap=new HashMap<Long,BigDecimal>();
			for (InvestInfoAdminVO investInfo : investInfoList) {
				// 该笔投资冻结金额
				BigDecimal investAmount = investInfo.getInvestAmount();
				
				
				//单个理财人投标总和
				BigDecimal investAllTender=BigDecimal.ZERO;
				if(null!=investMap.get(investInfo.getUserId())){
					//map中的值+当前笔的投标金额
					investAllTender=BigDecimalUtil.add(investMap.get(investInfo.getUserId()),investAmount);
				}else{
					investAllTender=investAmount;
				}
				//放进map    userId                  投标总和
				investMap.put(investInfo.getUserId(), investAllTender);
				
				
				
				// 放款后借款人获得金额
				loanAmountTotal = BigDecimalUtil.add(loanAmountTotal, investAmount);
				//理财人分账
				AcTLedgerAdminVO freezeActledger =  loanProcessSupportDao.getAcTLedger(investInfo.getUserId(),"5");// 业务类别：5：冻结资金账户
				logger.info("理财账户5的冻结金额原始值："+freezeActledger.getAmount());
				AcTLedgerAdminVO investAmountActLedger =  loanProcessSupportDao.getAcTLedger(investInfo.getUserId(),"4");// 业务类别：4：资金账户
				AcTLedgerAdminVO investActLedger = loanProcessSupportDao.getAcTLedger(investInfo.getUserId(), "1");// 业务类别：1：理财账户
				double[] intrest = formula.getInterest_month();
				double inte = 0d;
				for (int i = 0; i < intrest.length; i++) {
					inte += intrest[i];
				}
				
				//借款人现金账户的金额+此笔投标金额
				loanCashAmount=BigDecimalUtil.add(loanCashAmount, investAmount);
				logger.info("放款后借款人现金账户值："+loanCashAmount);
				
				// 冻结资金解冻（解冻金额等于当前投标金额）
				int count=loanProcessSupportDao.updateAcTLedger(freezeActledger.getId(), investAmount);
				if(count==0){
					logger.info("放款解冻失败，账户ID："+freezeActledger.getId()+"解冻金额："+investAmount);
				}
				// 理财人理财账户现金和当前投资金额增加（等于当前投标金额）
				investActLedger.setInvestAmount(BigDecimalUtil.add(investActLedger.getInvestAmount(), investAmount));
				investActLedger.setDebtAmount(BigDecimalUtil.add(investActLedger.getDebtAmount(), investAmount));
				investActLedger.setInterestReceivable(BigDecimalUtil.add(investActLedger.getInterestReceivable(), BigDecimalUtil.mul(inte, investInfo.getHavaScale())));
				acTLedgerDao.save(investActLedger);
				// 1.理财人当前投资冻结资金解冻，资金流入理财人资金账户
				//理5到理4
				flowUtils.setActFlow(investAmount, "", "3000000000001", freezeActledger.getAccount(), investAmountActLedger.getAccount(), NewConstSubject.bid_unfree_out_fk, NewConstSubject.bid_unfree_in_fk);
				// 2.理财人资金账户理财资金流出，流入理财账户
				//理4到理1
				flowUtils.setActFlow(investAmount, "", "3000000000001",  investAmountActLedger.getAccount(), investActLedger.getAccount(), NewConstSubject.adjust_account_out, NewConstSubject.adjust_account_in);
				// 3.理财人理财账户现金流出，流入借款人借款账户
				//理1到借2
				AcTFlowVO actf=flowUtils.setActFlow(investAmount, "", "3000000000001",  investActLedger.getAccount(), loanActLedger.getAccount(), NewConstSubject.bid_succ_out, NewConstSubject.bid_succ_in);
				flowUtils.setAcTFlowClassify(TradeTypeConstants.FK, actf.getTradeNo(), investInfo.getUserId(), investAmountActLedger.getAmount(), investAmountActLedger.getPayBackAmt(), BigDecimalUtil.sub(freezeActledger.getAmount(), investAllTender),
						currRepayLoanInfo.getUserId(), loanCashAmount, loanAmountActLedger.getPayBackAmt(), loanFreezeActLedger.getAmount(), investAmount, currRepayLoanInfo.getLoanId(),TypeConstants.JKCG);
				// 4.借款人债权转给理财人（借2到理1）【理财分户变化】
				//借2到理1
				flowUtils.setActFlow(BigDecimal.ZERO, "", "3000000000001", loanActLedger.getAccount(), investActLedger.getAccount(), NewConstSubject.loan_succ_transfer_out, NewConstSubject.loan_succ_transfer_in);
				// 5.借款人借款账户现金流出，流入借款人资金账户
				//借2到借4
				flowUtils.setActFlow(investAmount, "", "3000000000001", loanActLedger.getAccount(), loanAmountActLedger.getAccount(), NewConstSubject.loan_succ_pass_out, NewConstSubject.loan_succ_pass_in);
				// 借款金额流入资金账户，现金增加
				// 理财信息 维护
				setInvestInfo(investInfo, formula, term_loan, investActLedger.getId(), currRepayLoanInfo);
				// 理财人冻结资金维护
				Long investUserId = investInfo.getUserId();// 理财人userId
				maintainFreezeFoud(investUserId, currRepayLoanInfo.getLoanId());
			}
			loanActLedger.setLoanAmount(BigDecimalUtil.add(loanActLedger.getLoanAmount(), loanAmountTotal));
			double[] intrest = formula.getInterest_month();
			double inte = 0d;
			for (int i = 0; i < intrest.length; i++) {
				inte += intrest[i];
			}
			loanActLedger.setInterestPayable(BigDecimalUtil.add(loanActLedger.getInterestPayable(), inte));// 应付利息
			// 扣除借款手续费后实际到账资金
			actualToAccountAmount = BigDecimalUtil.sub(loanAmountTotal, loanProceduresFee);
			// 扣除风险准备金后实际到账户资金
			actualToAccountAmount = BigDecimalUtil.sub(actualToAccountAmount, reserveFoud);
			loanAmountActLedger.setAmount(BigDecimalUtil.add(loanAmountActLedger.getAmount(), actualToAccountAmount));
			// 证大收取借款手续费
			AcTLedgerAdminVO act1 = acTLedgerDao.findByBusiTypeAndAccountLike("3",ZendaiAccountBank.zendai_acct9);
			act1.setAmount(BigDecimalUtil.add(act1.getAmount(), loanProceduresFee));
			acTLedgerDao.save(act1);
			// 证大收取风险管理金
			AcTLedgerAdminVO act2 = acTLedgerDao.findByBusiTypeAndAccountLike("3",ZendaiAccountBank.zendai_acct10);
			act2.setAmount(BigDecimalUtil.add(act2.getAmount(), reserveFoud));
			acTLedgerDao.save(act2);
			acTLedgerDao.save(loanAmountActLedger);
			// 6.借款人支付借款手续费
			AcTFlowVO actFee=flowUtils.setActFlow(loanProceduresFee, "", "3000000000001", loanAmountActLedger.getAccount(),  ZendaiAccountBank.zendai_acct9, NewConstSubject.loan_man_fee_out, NewConstSubject.loan_man_fee_in);
			flowUtils.setAcTFlowClassify(TradeTypeConstants.FKFEE, actFee.getTradeNo(), currRepayLoanInfo.getUserId(), BigDecimalUtil.sub(loanCashAmount,loanProceduresFee), loanAmountActLedger.getPayBackAmt(), loanFreezeActLedger.getAmount(), null, null, null, null, loanProceduresFee, currRepayLoanInfo.getLoanId(),TypeConstants.JKCG);
			// 证大收取借款手续费
			// 7.借款人支付风险准备金
			AcTFlowVO actReserve=flowUtils.setActFlow(reserveFoud, "", "3000000000001", loanAmountActLedger.getAccount(), ZendaiAccountBank.zendai_acct10, NewConstSubject.loan_loss_provision_out, NewConstSubject.loan_loss_provision_in);
			flowUtils.setAcTFlowClassify(TradeTypeConstants.FKRESERVE, actReserve.getTradeNo(), currRepayLoanInfo.getUserId(), loanAmountActLedger.getAmount(), loanAmountActLedger.getPayBackAmt(), loanFreezeActLedger.getAmount(), null, null, null, null, reserveFoud, currRepayLoanInfo.getLoanId(),TypeConstants.JKCG);
			// 证大收取风险准备金
			// 借款信息维护
			maitainLoanInfo(formula, currRepayLoanInfo, term_loan, loanActLedger.getId(), investInfoList);
			
			//理财人投标放款短信
			sendPhoneMessageToInvest(currRepayLoanInfo);
		}
	}
	
	
	/**
	 * @author Ray
	 * @date 2013-2-20 下午4:12:45
	 * @param loanInfo 借款信息
	 * description:放款投标人发送短信
	 */
	public void sendPhoneMessageToInvest(LoanInfoAdminVO loanInfo) {
		Map<String, String> map = new HashMap<String, String>();
		// 给投资人发送短信
//		List<InvestInfoAdminVO> investList = new ArrayList<InvestInfoAdminVO>();
		if (null != loanInfo.getInvestInfoList()) {
//			Set<Long> hasSet = new HashSet<Long>();
//			for (InvestInfoAdminVO investInfo : loanInfo.getInvestInfoList()) {
//				if (hasSet.add(investInfo.getUserId())) {
//					investList.add(investInfo);
//				}
//			}
			for (InvestInfoAdminVO invest : loanInfo.getInvestInfoList()) {
				List<UserMessageSetAdminVO> messageList = userMessageSetDao.findByUserIdAndKindId(invest.getUserId(), 5L);
				
				//1，默认发系统信息
				UsersAdminVO user =usersDao.findByUserId(invest.getUserId());// 获取理财人信息
				String loginName = user.getLoginName();
				String laonTitle=loanInfo.getLoanTitle();
				String amount=String.valueOf(invest.getInvestAmount());
				
				SysMsgAdminVO sysMsg = new SysMsgAdminVO();
				sysMsg.setUserId(user.getUserId());
				sysMsg.setWordId(23L);
				sysMsg.setParameter1(loginName);
				sysMsg.setParameter2(laonTitle);
				sysMsg.setParameter3(amount);
				sysMsg.setHappenTime(new Date());
				sysMsg.setIsDel("0");
				sysMsgDao.save(sysMsg);
				
				//遍历
				for (UserMessageSetAdminVO message : messageList) {
					map.put("0", loginName);
					map.put("1", laonTitle);
					map.put("2", amount);
					//2，发email
					if (message.getMannerId().equals(2L)) {
						String messages = mimeMailService.transferMailContent("bid_succ2", map);
						mimeMailService.sendNotifyMail(messages, user.getEmail(), "我的投标成功");
					}
					//3，发短信
					if (message.getMannerId().equals(3L)) {
						UserInfoPersonAdminVO userInfoPerson = userInfoPersonDao.findByUserId(user.getUserId());
						String loanUserphoneNo = userInfoPerson.getPhoneNo();
						SMSSender.sendMessage("bid_succ2", loanUserphoneNo, map);
					}
				}
			}
		}
	}
	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-25 下午4:19:24
	 * @param investInfo
	 * @param formula
	 * @param term_loan
	 * @param ledgerId
	 * @param loanInfo
	 * @return
	 * description:理财信息维护
	 */
	public InvestInfoAdminVO setInvestInfo(InvestInfoAdminVO investInfo, Formula formula, int term_loan, Long ledgerId, LoanInfoAdminVO loanInfo) {
		investInfo.setStatus("3");
		investInfo.setAcTLedgerFinance(setActLedgerFinance(formula, investInfo, term_loan, ledgerId, loanInfo));
		return investInfo;

	}
	
	

	/**
	 * @author Ray
	 * @date 2013-2-26 下午1:19:50
	 * @param formula
	 * @param investInfo
	 * @param term_loan
	 * @param ledgerId
	 * @param currRepayLoanInfo
	 * @return
	 * description:理财分户信息 维护
	 */
	public AcTLedgerFinanceAdminVO setActLedgerFinance(Formula formula, InvestInfoAdminVO investInfo, int term_loan, Long ledgerId, LoanInfoAdminVO currRepayLoanInfo) {
		AcTLedgerFinanceAdminVO actLedgerFinance = investInfo.getAcTLedgerFinance();
		actLedgerFinance.setLedgerId(ledgerId);
		actLedgerFinance.setAcctStatus("2");
		actLedgerFinance.setDueDate(formula.getDate_repayment_current());
		actLedgerFinance.setIntersetStart(formula.getDate_repayment()[0]);
		actLedgerFinance.setContractEnd(formula.getDate_repayment()[term_loan - 1]);
		actLedgerFinance.setMaturity(formula.getDate_repayment_current());
		actLedgerFinance.setDebtAmount(investInfo.getInvestAmount());
		actLedgerFinance.setDebtProportion(investInfo.getHavaScale());
		// 理财人应收利息
		BigDecimal interestReceivable = BigDecimalUtil.mul(formula.getInterest_month()[0], investInfo.getHavaScale());
		actLedgerFinance.setInterestReceivable(interestReceivable);
		actLedgerFinance.setTotalnumPayments((long) term_loan);
		actLedgerFinance.setLoanAcct(currRepayLoanInfo.getActLedgerLoan());
		actLedgerFinance.setRemainNum(new Long(term_loan));
		actLedgerFinance.setCurrNum(1L);
		return actLedgerFinance;
	}
	
	
	/**
	 * @author Ray
	 * @date 2013-2-26 下午1:19:58
	 * @param formula
	 * @param loanInfo
	 * @param term_loan
	 * @param ledgerId
	 * @param investInfoList
	 * description:借款信息维护
	 */
	public void maitainLoanInfo(Formula formula, LoanInfoAdminVO loanInfo, int term_loan, Long ledgerId, List<InvestInfoAdminVO> investInfoList) {
		loanInfo.setStatus(4L);
		loanInfo.setActLedgerLoan(setActLedgerLoanInfo(formula, loanInfo, term_loan, ledgerId));
		loanInfo.setInvestInfoList(investInfoList);
		loanInfoDao.save(loanInfo);
		logger.info("修改Loan_info及ActLedgerLoan状态：借款ID"+loanInfo.getLoanId());
	}
	
	
	/**
	 * @author Ray
	 * @date 2013-2-26 上午11:11:39
	 * @param formula
	 * @param loanInfo
	 * @param term_loan
	 * @param ledgerId
	 * @return
	 * description:贷款分户信息维护
	 */
	public AcTLedgerLoanAdminVO setActLedgerLoanInfo(Formula formula, LoanInfoAdminVO loanInfo, int term_loan, Long ledgerId) {
		AcTLedgerLoanAdminVO actLedgerLoan = loanInfo.getActLedgerLoan();
		actLedgerLoan.setLedgerId(ledgerId);
		actLedgerLoan.setInterestStart(DateUtil.getDateyyyyMMdd());
		actLedgerLoan.setContractEnd(formula.getDate_repayment()[term_loan - 1]);
		actLedgerLoan.setMaturity(formula.getDate_repayment_current());
		actLedgerLoan.setNextExpiry(formula.getDate_repayment_current());
		actLedgerLoan.setEachRepayment(new BigDecimal(formula.getPrincipal_interest_month_first()));
		actLedgerLoan.setCurrNum(1l);
		actLedgerLoan.setRate(new BigDecimal(formula.getRate_month()));
		actLedgerLoan.setTotalNum((long) term_loan);
		actLedgerLoan.setRateType("1");
		actLedgerLoan.setAcctStatus("2");
		actLedgerLoan.setLoan(loanInfo.getLoanAmount());
		actLedgerLoan.setOutstanding(new BigDecimal(formula.getPrincipal_surplus()[0]));
		actLedgerLoan.setInterestPayable(new BigDecimal(formula.getInterest_month()[0]));
		actLedgerLoan.setPertainSys("3");
		actLedgerLoan.setMemo("放款维护贷款分户信息");
		actLedgerLoan.setAcTVirtualCashFlows(setVirtualCashFlow(formula, actLedgerLoan));
		actLedgerLoan.setPaymentMethod("1");
		return actLedgerLoan;
	}
	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-26 上午11:11:50
	 * @param formula
	 * @param actLedgerLoan
	 * @return
	 * description:生成虚拟现金流水
	 */
	public List<AcTVirtualCashFlowAdminVO> setVirtualCashFlow(Formula formula, AcTLedgerLoanAdminVO actLedgerLoan) {
		List<AcTVirtualCashFlowAdminVO> actVirtualCashFlowList = new ArrayList<AcTVirtualCashFlowAdminVO>();
		int term_loan = formula.getTerm_loan();
		for (int i = 0; i < term_loan; i++) {
			int currNum = i + 1;
			AcTVirtualCashFlowAdminVO actVirtualCashFlow = new AcTVirtualCashFlowAdminVO();
			actVirtualCashFlow.setCurrNum(Long.parseLong(String.valueOf(currNum)));
			actVirtualCashFlow.setCreateDate(DateUtil.getDateyyyyMMdd());
			actVirtualCashFlow.setRepayDay(formula.getDate_repayment()[i]);
			actVirtualCashFlow.setPrincipalAmt(new BigDecimal(formula.getPrincipal_month()[i]));
			actVirtualCashFlow.setInterestAmt(new BigDecimal(formula.getInterest_month()[i]));
			actVirtualCashFlow.setMemo("第" + currNum + "期还款");
			actVirtualCashFlow.setActLedgerLoan(actLedgerLoan);
			actVirtualCashFlow.setRepayStatus(RepayStatus.未还款);
			// actVirtualCashFlow.setCreateUserId(createUserId);
			actVirtualCashFlowList.add(actVirtualCashFlow);
		}
		return actVirtualCashFlowList;
	}
	
	/**
	 * @author wangtf
	 * @description 债权收购
	 * @param investId
	 * @param loanInfoId
	 */
	@Transactional(readOnly=false)
	public void acquisitionProgress(Long acTVirtualCashFlowId,Long investId,Long num) throws BusinessException{
		InvestInfoAdminVO investInfo = investInfoAdmindao.findOne(investId);
		OverdueClaimsAdminId OverdueClaimsId = new OverdueClaimsAdminId();
		OverdueClaimsId.setInvestId(investId);
		OverdueClaimsId.setNum(num);
		OverdueClaimsAdminVO overdueClaims = overdueClaimsAdminDao.findById(OverdueClaimsId);
		if(overdueClaims.getStatus()!=1||overdueClaims.getOverDueDays()<=30){
			throw new BusinessException("必需是未还款的而且逾期大于三十天的债权才能收购，请确认信息或刷新页面重试！");
		}

		// 当期还款计划
		AcTVirtualCashFlowAdminVO  currAcTLedgerLoan = acTVirtualCashFlowAdminDao.findOne(acTVirtualCashFlowId);
		//当期分债权应得本金
		BigDecimal currPayPrincipal = BigDecimalUtil.mul(currAcTLedgerLoan.getPrincipalAmt(),investInfo.getHavaScale());
		//当期分债权应得利息
		BigDecimal currPayInterest = BigDecimalUtil.mul(currAcTLedgerLoan.getInterestAmt(),investInfo.getHavaScale());
		AcTLedgerAdminVO investerAct5 =  loanProcessSupportDao.getAcTLedger(investInfo.getUserId(),"5");// 业务类别：5：冻结资金账户
		AcTLedgerAdminVO investerAct4 =  loanProcessSupportDao.getAcTLedger(investInfo.getUserId(),"4");// 业务类别：4：资金账户
		AcTLedgerAdminVO investerAct1 = loanProcessSupportDao.getAcTLedger(investInfo.getUserId(), "1");// 业务类别：1：理财账户
		AcTCustomerAdminVO investerAcTCustomer = loanProcessSupportDao.findAcTCustomerByUserid(investInfo.getUserId());
		BigDecimal act4CurrAmount = investerAct4.getAmount();
		//证大风险准备金账户
		AcTLedgerAdminVO zendActLedger10 = acTLedgerAdminDao.findByAccount(ZendaiAccountBank.zendai_acct10);
		//风险准备金账户余额不足 提示
		if(zendActLedger10.getAmount().compareTo(BigDecimalUtil.add(currPayPrincipal, currPayInterest))==-1){
			throw new BusinessException("风险金账户余额不足，无法垫付！");
		}else{
			//代偿本金
			zendActLedger10.setAmount(BigDecimalUtil.sub(zendActLedger10.getAmount(), currPayPrincipal));
			investerAct4.setAmount(BigDecimalUtil.add(investerAct4.getAmount(), currPayPrincipal));
			//本金代偿流水
			AcTFlowVO principalAdvanceFlow = flowUtils.setActFlow(currPayPrincipal, "", "", ZendaiAccountBank.zendai_acct10, investerAct1.getAccount(), NewConstSubject.principal_advance_out, NewConstSubject.principal_advance_in);
			//本金调账
			flowUtils.setActFlow(currPayPrincipal, investerAcTCustomer.getAuthTeller(), investerAcTCustomer.getOpenacctOrgan(), investerAct1.getAccount(), investerAct4.getAccount(), NewConstSubject.principal_advance_adjust_out, NewConstSubject.principal_advance_adjust_in);
			/*******小表流水******/
			flowUtils.setAcTFlowClassify(TradeTypeConstants.FXJDCBJ, principalAdvanceFlow.getTradeNo(), null, null, null, null, investInfo.getUserId(), investerAct4.getAmount(), investerAct4.getPayBackAmt(), investerAct5.getAmount(), currPayPrincipal, investInfo.getLoanInfo().getLoanId(), TypeConstants.ZJHS);
			//债权关系转移
			flowUtils.setActFlow(BigDecimal.ZERO, investerAcTCustomer.getAuthTeller(), investerAcTCustomer.getOpenacctOrgan(), investerAct1.getAccount(), ZendaiAccountBank.zendai_acct10, NewConstSubject.change_claims_relationship_out, NewConstSubject.change_claims_relationship_in);
			//代偿利息
			zendActLedger10.setAmount(BigDecimalUtil.sub(zendActLedger10.getAmount(), currPayInterest));
			investerAct4.setAmount(BigDecimalUtil.add(investerAct4.getAmount(), currPayInterest));
			//利息代偿流水
			AcTFlowVO interestAdvanceFlow = flowUtils.setActFlow(currPayInterest, "", "", ZendaiAccountBank.zendai_acct10, investerAct1.getAccount(), NewConstSubject.inverest_advance_out, NewConstSubject.inverest_advance_in);
			//利息调账
			flowUtils.setActFlow(currPayInterest, investerAcTCustomer.getAuthTeller(), investerAcTCustomer.getOpenacctOrgan(), investerAct1.getAccount(), investerAct4.getAccount(), NewConstSubject.inverest_advance_adjust_out, NewConstSubject.inverest_advance_adjust_in);
			/*******小表流水******/
			flowUtils.setAcTFlowClassify(TradeTypeConstants.FXJDCLX, interestAdvanceFlow.getTradeNo(), null, null, null, null, investInfo.getUserId(), investerAct4.getAmount(), investerAct4.getPayBackAmt(), investerAct5.getAmount(), currPayInterest, investInfo.getLoanInfo().getLoanId(), TypeConstants.ZJHS);
		}
		acTLedgerAdminDao.save(zendActLedger10);
		AcTLedgerAdminVO updateCondition = new AcTLedgerAdminVO();
		updateCondition.setAmount(act4CurrAmount);
		updateCondition.setAccount(investerAct4.getAccount());
		int updateResult = loanProcessSupportDao.update(investerAct4, updateCondition);
		if(updateResult!=1){
			throw new BusinessException("本期债权已收购，请刷新查看页面信息！");
		}
		//维护理财人分账1
		maintainAcTLedger1(investerAct1, currPayPrincipal, currPayInterest);
		//维护理财分户信息
		if(investInfo.getLoanInfo().getLoanDuration().equals(num)){
			maintainAcTLedgerFinance(investInfo, "0", null);
		}else{
			AcTVirtualCashFlowAdminVO nextAcTVirtualCashFlow = acTVirtualCashFlowAdminDao.findByActLedgerLoanAndCurrNum(investInfo.getLoanInfo().getActLedgerLoan(),num+1);
			maintainAcTLedgerFinance(investInfo, "1", nextAcTVirtualCashFlow);
		}
		//状态设为 已经代偿
		overdueClaims.setStatus(2L);
		//设置为代偿过
		overdueClaims.setIsAdvanced(1L);
		//设置修改时间
		overdueClaims.setEditTime(new Date());
		// 设置代偿金额 包含本金和利息
		overdueClaims.setPayAmount(BigDecimalUtil.add(currPayPrincipal, currPayInterest));
		//保存分债权信息
		overdueClaimsAdminDao.save(overdueClaims);
		
		//发送站内信
		SysMsgAdminVO sysMsg = new SysMsgAdminVO();
		sysMsg.setUserId(investInfo.getUserId());
		sysMsg.setWordId(22L);
		UsersAdminVO user=usersDao.findByUserId(investInfo.getUserId());
		sysMsg.setParameter1(user.getLoginName());
		String loanTitle=loanInfoDao.findOne(overdueClaims.getLoanId()).getLoanTitle();
		sysMsg.setParameter2(loanTitle);
		String  principanInterestMonth=BigDecimalUtil.formatCurrency(BigDecimalUtil.add(currPayPrincipal, currPayInterest));
		sysMsg.setParameter3(principanInterestMonth);
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		sysMsgDao.save(sysMsg);
		
		//发送邮件
		Map<String, String> map=new HashMap<String,String>();
		//借款人昵称
		map.put("0", user.getLoginName());
		// 借款标题
		map.put("1", loanTitle);
		// 当期应还本息
		map.put("2", principanInterestMonth);
		String messages =mimeMailService.transferMailContent("rev_pay_fxj", map);
		mimeMailService.sendNotifyMail(messages, user.getEmail(), "我收到一笔风险金代偿还款");
		//发送短信
		UserInfoPersonAdminVO userInfoPerson = userInfoPersonDao.findByUserId(user.getUserId());
		SMSSender.sendMessage("rev_pay_fxj",userInfoPerson.getPhoneNo(), map);
		
	}
	
	/**理财分户信息表维护
	 * @param acTLedgerFinance
	 * @param flag 0：代表最后一期  
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	private void maintainAcTLedgerFinance(InvestInfoAdminVO investInfo , String flag , AcTVirtualCashFlowAdminVO nextAcTVirtualCashFlow){
		
			AcTLedgerFinanceAdminVO acTLedgerFinance = investInfo.getAcTLedgerFinance();
			//最后一期还款
			if("0".equals(flag)){
				// 投资状态设置为已结标
				investInfo.setStatus("4");
				//当期应收利息
				acTLedgerFinance.setInterestReceivable(BigDecimal.ZERO);
				// 理财分户销户
				acTLedgerFinance.setAcctStatus("9");
				//未还期数
				acTLedgerFinance.setRemainNum(0l);
				
			}else{
				//当期应收利息
				acTLedgerFinance.setInterestReceivable(BigDecimalUtil.mul(nextAcTVirtualCashFlow.getInterestAmt(), investInfo.getHavaScale()));
				//未还期数
				System.out.println("acTLedgerFinance"+acTLedgerFinance+"remainNum"+acTLedgerFinance.getCurrNum());
				acTLedgerFinance.setRemainNum(acTLedgerFinance.getRemainNum()-1);
				//当前期数
				acTLedgerFinance.setCurrNum(nextAcTVirtualCashFlow.getCurrNum());
			}
			investInfo.setAcTLedgerFinance(acTLedgerFinance);
			investInfoAdmindao.save(investInfo);
		
	}
	

	/**理财人分账1维护
	 * @param act1 
	 * 				 理财人分账1
	 * @param payBackInfoDTO
	 * 						还款信息
	 * @param payType  还款类型  1：正常还款 0：一次性提前还款
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	private void maintainAcTLedger1(AcTLedgerAdminVO act1 , BigDecimal principal,BigDecimal interest ) {
		// 当前投资
		act1.setDebtAmount(BigDecimalUtil.sub(act1.getDebtAmount() , principal));
		// 应收利息
		act1.setInterestReceivable(BigDecimalUtil.sub(act1.getInterestReceivable(), interest));
		// 利息收入
		act1.setInterestIncome(BigDecimalUtil.add(act1.getInterestIncome(), interest));
		acTLedgerDao.save(act1);
	}
}
