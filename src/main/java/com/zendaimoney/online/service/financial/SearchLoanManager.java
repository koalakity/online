package com.zendaimoney.online.service.financial;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.CodeUtil;
import com.zendaimoney.online.common.ConstSubject;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.Formula;
import com.zendaimoney.online.common.MemcacheCacheConstants;
import com.zendaimoney.online.common.MemcacheCacheHelper;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.financial.FinanciaUserApproveDao;
import com.zendaimoney.online.dao.financial.FinancialAcTFlowDao;
import com.zendaimoney.online.dao.financial.FinancialAcTLedgerDao;
import com.zendaimoney.online.dao.financial.FinancialFreezeFundsDao;
import com.zendaimoney.online.dao.financial.FinancialInvestInfoDao;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.dao.financial.FinancialUsersDao;
import com.zendaimoney.online.dao.financial.LoanInfoDao;
import com.zendaimoney.online.dao.homepage.HomepageUserMovementDao;
import com.zendaimoney.online.dao.personal.PersonalUserMessageSetDao;
import com.zendaimoney.online.entity.financial.FinanciaUserApprove;
import com.zendaimoney.online.entity.financial.FinancialAcTFlow;
import com.zendaimoney.online.entity.financial.FinancialAcTLedger;
import com.zendaimoney.online.entity.financial.FinancialAcTLedgerFinance;
import com.zendaimoney.online.entity.financial.FinancialFreezeFunds;
import com.zendaimoney.online.entity.financial.FinancialInvestInfo;
import com.zendaimoney.online.entity.financial.FinancialLoanInfo;
import com.zendaimoney.online.entity.financial.FinancialSysMsg;
import com.zendaimoney.online.entity.financial.FinancialUsers;
import com.zendaimoney.online.entity.homepage.HomepageMovementWord;
import com.zendaimoney.online.entity.homepage.HomepageUserMovement;
import com.zendaimoney.online.entity.personal.PersonalUserMessageSet;
import com.zendaimoney.online.oii.sms.SMSSender;
import com.zendaimoney.online.vo.financial.LoanInfoVO;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-18 下午2:39:55 operation by: description:借款查询&投标
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class SearchLoanManager {
	private static Logger logger = LoggerFactory.getLogger(SearchLoanManager.class);

	private static Logger performanceLogger = LoggerFactory.getLogger("performance");

	@Autowired
	LoanInfoDao loanInfoDao;
	@Autowired
	FinanciaUserApproveDao financiaUserApproveDao;
	@Autowired
	FinancialInvestInfoDao financialInvestInfoDao;
	@Autowired
	FinancialUsersDao financialUsersDao;
	@Autowired
	FinancialAcTLedgerDao financialAcTLedgerDao;
	@Autowired
	FinancialFreezeFundsDao freezeFundsDao;
	@Autowired
	FinancialAcTFlowDao acTFlowDao;
	@Autowired
	CommonDao commonDao;
	@Autowired
	CodeUtil codeUtil;
	@Autowired
	FinancialSysMsgDao sysMsgDao;
	@Autowired
	PersonalUserMessageSetDao messageSetDao;
	@Autowired
	MimeMailService mimeMailService;
	@Autowired
	HomepageUserMovementDao movementDao;

	// 理财计算机
	public double investCalculate(double investAmount, double yearRate, int loanDuration) {
		double receiveAmount = 0d;
		Formula furmula = new Formula(investAmount, loanDuration, new Date(), yearRate / 100, 0, 0, 0, 0);
		receiveAmount = furmula.getPrincipal_interest_balance_total();
		return receiveAmount;
	}

	/**
	 * 2012-12-3 上午9:26:02 by HuYaHui
	 * 
	 * @param column
	 *            要排序的列
	 * @param seq
	 *            升序|降序
	 * @param offset
	 *            从第几行开始
	 * @return
	 */
	public List<LoanInfoVO> allLoan(String column, String seq, int offset) {
		List<LoanInfoVO> voList = new ArrayList<LoanInfoVO>();
		List<FinancialLoanInfo> loanList = new ArrayList<FinancialLoanInfo>();
		long start1 = System.currentTimeMillis();
		loanList = loanInfoDao.findByStatusAndReleaseStatusOrderByRTime(offset, LoanInfoDao.PAGESIZE, null, BigDecimal.ONE);
		performanceLogger.info("-->查询借款信息LIST耗时: " + (System.currentTimeMillis() - start1) + " LIST size:" + (loanList != null ? loanList.size() : "0"));

		performanceLogger.info("-->开始处理借款信息");
		long start = System.currentTimeMillis();
		for (int i = 0; i < loanList.size(); i++) {
			FinancialLoanInfo loan = loanList.get(i);
			LoanInfoVO vo = new LoanInfoVO();
			try {
				PropertyUtils.copyProperties(vo, loan);
			} catch (Exception e) {
				e.printStackTrace();
				performanceLogger.error(e.getMessage(), e);
			}
			// KEY:用户ID:状态
			// 根据KEY获取对应的用户认证信息集合
			List<FinanciaUserApprove> appList = findUserApproveByUserId(loan.getUserId().getUserId());
			vo.setUserApproveList(appList);

			if (loan.getStatus().equals(BigDecimal.ONE)) {
				// 根据ID，获得InvestAmount累加SELECT sum(investAmount) FROM
				// FinancialInvestInfo where loanId=?
				start1 = System.currentTimeMillis();
				List<FinancialInvestInfo> invList = financialInvestInfoDao.findByLoanId(loan.getLoanId());
				performanceLogger.info("---->加载INVEST_INFO信息耗时: " + (System.currentTimeMillis() - start1) + " invList size:" + (invList != null ? invList.size() : "0"));
				double leaveMoney = 0d;
				for (FinancialInvestInfo invInfo : invList) {
					leaveMoney = leaveMoney + invInfo.getInvestAmount();
				}
				double schedule = 0d;
				if (leaveMoney != 0) {
					schedule = Calculator.div(leaveMoney, loan.getLoanAmount());
				}
				// 进度
				vo.setSchedule(ObjectFormatUtil.formatPercent(schedule, "##,#0.00%"));
				vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(loan.getLoanAmount() - leaveMoney));
				Date dates = loan.getStartInvestTime();
				vo.setLeavingTime(DateUtil.show(new Date(), DateUtil.getSevenDaysDateNoReplace(dates)));
				vo.setInvestmentCount(String.valueOf(invList.size()));
			} else if (loan.getStatus().equals(BigDecimal.valueOf(3)) || loan.getStatus().equals(BigDecimal.valueOf(8))) {
				vo.setSchedule("0%");
				vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(0d));
				Date dates = loan.getStartInvestTime();
				vo.setLeavingTime(DateUtil.show(new Date(), DateUtil.getSevenDaysDateNoReplace(dates)));
				vo.setInvestmentCount("0");
			} else {
				// 根据loanId 统计所有记录SELECT COUNT(Loan_Id) FROM INVEST_INFO
				Object count = findInvestTimeCount(loan.getLoanId());
				vo.setSchedule("100%");
				vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(0d));
				vo.setLeavingTime("");
				vo.setInvestmentCount(count + "");
			}
			vo.setYearRateStr(ObjectFormatUtil.formatPercent(loan.getYearRate(), "##,#0.00%"));
			voList.add(vo);
		}
		performanceLogger.info("-->结束处理借款信息，耗时allLoan：" + (System.currentTimeMillis() - start));
		return voList;
	}

	// 进行中的借款
	public List<LoanInfoVO> loanIng(String column, String seq, int offset) {
		List<LoanInfoVO> voList = new ArrayList<LoanInfoVO>();
		List<FinancialLoanInfo> loanList = new ArrayList<FinancialLoanInfo>();

		long start1 = System.currentTimeMillis();
		loanList = loanInfoDao.findByStatusAndReleaseStatusOrderByRTime(offset, LoanInfoDao.PAGESIZE, new BigDecimal[] { BigDecimal.ONE }, BigDecimal.ONE);
		performanceLogger.info("-->查询借款信息LIST耗时: " + (System.currentTimeMillis() - start1) + " LIST size:" + (loanList != null ? loanList.size() : "0"));

		performanceLogger.info("-->开始处理借款信息");
		long start = System.currentTimeMillis();
		for (FinancialLoanInfo loan : loanList) {
			LoanInfoVO vo = new LoanInfoVO();
			try {
				PropertyUtils.copyProperties(vo, loan);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 根据KEY获取对应的用户认证信息集合
			List<FinanciaUserApprove> appList = findUserApproveByUserId(loan.getUserId().getUserId());
			vo.setUserApproveList(appList);

			List<FinancialInvestInfo> invList = financialInvestInfoDao.findByLoanId(loan.getLoanId());
			double leaveMoney = 0d;
			for (FinancialInvestInfo invInfo : invList) {
				leaveMoney = leaveMoney + invInfo.getInvestAmount();
			}
			double schedule = 0d;
			if (leaveMoney != 0) {
				schedule = Calculator.div(leaveMoney, loan.getLoanAmount());
			}
			// 进度
			vo.setSchedule(ObjectFormatUtil.formatPercent(schedule, "##,#0.00%"));
			vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(loan.getLoanAmount() - leaveMoney));
			Date dates = loan.getStartInvestTime();
			vo.setLeavingTime(DateUtil.show(new Date(), DateUtil.getSevenDaysDateNoReplace(dates)));
			vo.setInvestmentCount(String.valueOf(invList.size()));
			vo.setYearRateStr(ObjectFormatUtil.formatPercent(loan.getYearRate(), "##,#0.00%"));
			voList.add(vo);
		}
		performanceLogger.info("-->结束处理借款信息，耗时LoanIng：" + (System.currentTimeMillis() - start));
		return voList;
	}

	// 查找不同状态的借款
	/**
	 * 2012-12-5 上午9:41:07 by HuYaHui
	 * 
	 * @param status
	 *            状态
	 * @param column
	 *            要排序的列
	 * @param seq
	 *            升序|降序
	 * @param offset
	 *            从第几行开始
	 * @return
	 */
	public List<LoanInfoVO> getLoanListByStatus(String status, String column, String seq, int offset) {
		List<LoanInfoVO> voList = new ArrayList<LoanInfoVO>();
		List<FinancialLoanInfo> loanList = new ArrayList<FinancialLoanInfo>();
		long start1 = System.currentTimeMillis();
		// 即将开始的借款 3
		if (status.equals("3")) {
			loanList = loanInfoDao.findByStatusAndReleaseStatusOrderByRTime(offset, LoanInfoDao.PAGESIZE, new BigDecimal[] { BigDecimal.valueOf(8) }, null);
		}
		// 已经结束的借款 缺省按照【从满标到流标】+【发布时间倒叙】
		if (status.equals("4")) {
			loanList = loanInfoDao.findByStatusAndReleaseStatusOrderByRTime(offset, LoanInfoDao.PAGESIZE, new BigDecimal[] { BigDecimal.valueOf(2), BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6), BigDecimal.valueOf(7) }, BigDecimal.ONE);
		}
		performanceLogger.info("-->查询借款信息耗时: " + (System.currentTimeMillis() - start1) + " loanList size:" + (loanList != null ? loanList.size() : "0"));

		performanceLogger.info("-->开始处理借款信息");
		start1 = System.currentTimeMillis();
		for (FinancialLoanInfo loan : loanList) {
			LoanInfoVO vo = new LoanInfoVO();
			try {
				PropertyUtils.copyProperties(vo, loan);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 根据KEY获取对应的用户认证信息集合
			List<FinanciaUserApprove> appList = findUserApproveByUserId(loan.getUserId().getUserId());
			vo.setUserApproveList(appList);
			FinancialLoanInfo finaLoanInfo = loanInfoDao.findByLoanId(loan.getLoanId());
			
			List<FinancialInvestInfo> invList = getLoanInfoListById(loan.getLoanId());
			
			double leaveMoney = 0d;
			for (FinancialInvestInfo invInfo : invList) {
				leaveMoney = leaveMoney + invInfo.getInvestAmount();
			}
			// 进度
			vo.setSchedule("100%");
			vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(loan.getLoanAmount() - leaveMoney));
			// vo.setLeavingTime("0天");
			Date dates = loan.getStartInvestTime();
			vo.setLeavingTime(DateUtil.show(new Date(), DateUtil.getSevenDaysDateNoReplace(dates)));
			vo.setInvestmentCount(String.valueOf(invList.size()));
			vo.setYearRateStr(ObjectFormatUtil.formatPercent(loan.getYearRate(), "##,#0.00%"));
			vo.setDateStr(DateUtil.getYMDTime(loan.getReleaseTime()));
			
			if (finaLoanInfo.getActTLedgerId().getInterestStart() != null) {
				vo.setLoanDate(finaLoanInfo.getActTLedgerId().getInterestStart().toString());
			}
			voList.add(vo);
		}
		performanceLogger.info("-->处理借款信息耗时getLoanListByStatus：" + (System.currentTimeMillis() - start1));
		return voList;
	}

	/**
	 * 缓存用户ID统计的数据 2012-12-11 下午1:31:55 by HuYaHui
	 * 
	 * @param userId
	 * @return
	 */
	private Object findInvestTimeCount(BigDecimal userId) {
		long start1 = System.currentTimeMillis();
		String key=MemcacheCacheConstants.USERAPPROVEKEY+"_findInvestTimeCount_"+userId;
		Object count=MemcacheCacheHelper.get(key);
		if(count!=null){
			return count; 
		}
		count = financialInvestInfoDao.findInvestTimeCount(userId);
		MemcacheCacheHelper.set(key, count, MemcacheCacheConstants.TIME30M);
		performanceLogger.info("---->加载统计所有记录INVEST_INFO信息耗时: " + (System.currentTimeMillis() - start1));
		return count;
	}

	/**
	 * 强制从数据库查询
	 * @return key:用户ID+":"+review_status，value：当前ID对应的认证记录集合
	 */
	private List<FinanciaUserApprove> findAllUserApprove(String cache,BigDecimal userId) {
		List<FinanciaUserApprove> rtnList=new ArrayList<FinanciaUserApprove>();
		long start=System.currentTimeMillis();
		//缓存key:前缀_用户ID_状态
		String key=MemcacheCacheConstants.USERAPPROVEKEY+"_"+userId+"_"+BigDecimal.ONE;
		List<FinanciaUserApprove> userApproveList=financiaUserApproveDao.findByReviewStatusAndUserId(BigDecimal.ONE,userId);
		rtnList.addAll(userApproveList);
		MemcacheCacheHelper.set(key, rtnList, MemcacheCacheConstants.TIME30M);
		logger.info("耗时findAllUserApprove:"+(System.currentTimeMillis()-start));
		return rtnList;
	
	}
	
	/**
	 * 根据用户ID获取认证的数据集合
	 * 2012-12-3 下午3:38:43 by HuYaHui
	 * @return
	 * 		key:用户ID+":"+review_status，value：当前ID对应的认证记录集合
	 */
	private List<FinanciaUserApprove> findUserApproveByUserId(BigDecimal userId){
		List<FinanciaUserApprove> rtnList=new ArrayList<FinanciaUserApprove>();
		//缓存key:前缀_用户ID_状态
		String key=MemcacheCacheConstants.USERAPPROVEKEY+"_"+userId+"_"+BigDecimal.ONE;
		long cacheTime=System.currentTimeMillis();
		rtnList=MemcacheCacheHelper.get(key,rtnList);
		performanceLogger.info("---->从缓存中获取用户认证数据耗时:"+(System.currentTimeMillis()-cacheTime)+" 大小:"+(rtnList!=null?rtnList.size():0));
		if(rtnList==null || rtnList.size()==0){
			rtnList=new ArrayList<FinanciaUserApprove>();
			List<FinanciaUserApprove> userApproveList=financiaUserApproveDao.findByReviewStatusAndUserId(BigDecimal.ONE,userId);
			rtnList.addAll(userApproveList);
			MemcacheCacheHelper.set(key, rtnList, MemcacheCacheConstants.TIME30M);
		}
		return rtnList;
	}

	private List<FinancialInvestInfo> getLoanInfoListById(BigDecimal loadnId){
		List<FinancialInvestInfo> invList=new ArrayList<FinancialInvestInfo>();
		String key=MemcacheCacheConstants.USERAPPROVEKEY+"_"+loadnId+"_LoanId";
		invList=MemcacheCacheHelper.get(key, invList);
		if(invList==null || invList.size()==0){
			invList = financialInvestInfoDao.findByLoanId(loadnId);
			MemcacheCacheHelper.set(key, invList, MemcacheCacheConstants.TIME30M);
		}
		return invList;
	}
	
	// 确认投标 confirmInvest
	public LoanInfoVO confirmInvest(BigDecimal loanId, HttpServletRequest req) {
		FinancialLoanInfo loanInfo = loanInfoDao.findByLoanId(loanId);
		LoanInfoVO vo = new LoanInfoVO();
		try {
			PropertyUtils.copyProperties(vo, loanInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<FinanciaUserApprove> appList = financiaUserApproveDao.findByUserIdAndReviewStatus(loanInfo.getUserId().getUserId(), BigDecimal.ONE);
		vo.setUserApproveList(appList);
		List<FinancialInvestInfo> invList = financialInvestInfoDao.findByLoanId(loanInfo.getLoanId());
		double leaveMoney = 0d;
		for (FinancialInvestInfo invInfo : invList) {
			leaveMoney = Calculator.add(leaveMoney, invInfo.getInvestAmount());
		}
		double schedule = 0d;
		if (leaveMoney != 0) {
			schedule = Calculator.div(leaveMoney, loanInfo.getLoanAmount());
		}
		// 年利率
		vo.setYearRateStr(ObjectFormatUtil.formatPercent(loanInfo.getYearRate(), "##,#0.00%"));
		// 进度
		vo.setSchedule(ObjectFormatUtil.formatPercent(schedule, "##,#0.00%"));
		vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(Calculator.sub(loanInfo.getLoanAmount(), leaveMoney)));
		vo.setInvestmentCount(String.valueOf(invList.size()));
		vo.setLoanAmountStr(ObjectFormatUtil.formatCurrency(loanInfo.getLoanAmount()));
		FinancialUsers user = getUsers(req);
		FinancialAcTLedger ledger = financialAcTLedgerDao.findByTotalAccountIdAndBusiType(user.getAcTCustomer().getId(), "4");
		vo.setMyMoney(ObjectFormatUtil.formatCurrency(ledger.getAmount().doubleValue()));
		return vo;

	}

	// 检验用户余额
	public double checkUserAomount(HttpServletRequest req) {
		FinancialUsers user = getUsers(req);
		FinancialAcTLedger ledger = financialAcTLedgerDao.findByTotalAccountIdAndBusiType(user.getAcTCustomer().getId(), "4");
		return ledger.getAmount();

	}

	// 投资剩余金额
	public double checkSurplusAmount(BigDecimal loanId, HttpServletRequest req) {
		FinancialLoanInfo loanInfo = loanInfoDao.findByLoanId(loanId);
		List<FinancialInvestInfo> invList = financialInvestInfoDao.findByLoanId(loanInfo.getLoanId());
		double leaveMoney = 0d;
		for (FinancialInvestInfo invInfo : invList) {
			leaveMoney = Calculator.add(leaveMoney, invInfo.getInvestAmount());
		}
		return Calculator.sub(loanInfo.getLoanAmount(), leaveMoney);
	}

	// 检测 是否是对自己投标
	public boolean checkOwner(BigDecimal loanId, HttpServletRequest req) {
		FinancialLoanInfo loanInfo = loanInfoDao.findByLoanId(loanId);
		FinancialUsers user = getUsers(req);
		if (loanInfo.getUserId().getUserId().equals(user.getUserId())) {
			// 是自己发布的标
			return true;
		} else {
			return false;
		}
	}

	// 投标
	@Transactional(readOnly = false)
	public boolean invest(double investAmount, BigDecimal loanId, HttpServletRequest req) {

		FinancialUsers user = getUsers(req);
		FinancialInvestInfo investInfo = new FinancialInvestInfo();
		investInfo.setLoanId(loanId);
		investInfo.setUserId(user.getUserId());
		investInfo.setInvestAmount(investAmount);
		FinancialLoanInfo loanInfo = loanInfoDao.findByLoanId(loanId);
		List<FinancialInvestInfo> invList = financialInvestInfoDao.findByLoanId(loanInfo.getLoanId());
		// 已经投资的金额
		double leaveMoney = 0d;
		// 所占比例
		double havaScale = 0d;
		for (FinancialInvestInfo invInfo : invList) {
			leaveMoney = Calculator.add(leaveMoney, invInfo.getInvestAmount());
			havaScale = Calculator.add(havaScale, invInfo.getHavaScale());
		}
		// 已经投资的金所占的比例
		double scale = havaScale;
		// 已经投资的金额 + 本次投资的金额 = 借款金额
		if (loanInfo.getLoanAmount().equals(Calculator.add(leaveMoney, investAmount))) {
			loanInfo.setStatus(BigDecimal.valueOf(2d));
			logger.info("SAVE: USER_MOVEMENT || userId=" + loanInfo.getUserId().getUserId() + " Status=2");
			// 满标的情况下 跟新借款列表
			loanInfoDao.save(loanInfo);
			// 最后一次投标的比例
			havaScale = Calculator.sub(1d, havaScale);
			HomepageUserMovement movement = new HomepageUserMovement();
			HomepageMovementWord moWord = new HomepageMovementWord();
			moWord.setWordId(new BigDecimal(2));
			movement.setUserId(loanInfo.getUserId().getUserId());
			movement.setParameter1(loanInfo.getLoanTitle());
			movement.setWordId(moWord);
			movement.setUrl1("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
			movement.setMsgKind("1");
			movement.setIsDel("0");
			movement.setHappenTime(new Date());
			logger.info("SAVE: USER_MOVEMENT || userId=" + loanInfo.getUserId().getUserId() + " Parameter1=" + loanInfo.getLoanTitle() + " WordId=" + moWord + " MsgKind=1 IsDel=0 movement=" + movement.getHappenTime());
			movementDao.save(movement);

		} else {
			havaScale = Calculator.div(investAmount, loanInfo.getLoanAmount());
		}

		investInfo.setHavaScale(havaScale);
		investInfo.setInvestTime(new Date());
		investInfo.setStatus("2");
		FinancialAcTLedgerFinance alf = new FinancialAcTLedgerFinance();
		// 状态是开户1
		alf.setAcctStatus("1");
		alf.setDebtAmount(investAmount);
		alf.setDebtProportion(havaScale);
		alf.setLedgerId(user.getAcTCustomer().getId());
		investInfo.setLedgerFinanceId(alf);
		logger.info("SAVE: INVEST_INFO || InvestId=" + investInfo.getInvestId() + " InvestTime=" + investInfo.getInvestTime() + " Status=2 FinancialAcTLedgerFinance.id=" + alf.getId());
		// 保存理财信息
		financialInvestInfoDao.save(investInfo);

		// leaveMoney = Calculator.add(leaveMoney,investAmount);

		// 冻结资金表
		FinancialFreezeFunds freezeFund = new FinancialFreezeFunds();
		freezeFund.setFreezeKind("1");
		freezeFund.setUserId(user.getUserId());
		freezeFund.setLoanId(loanId);
		freezeFund.setFreezeMoney(BigDecimal.valueOf(investAmount));
		freezeFund.setFreezeTime(new Date());
		freezeFund.setFreezeStatus("1");
		logger.info("SAVE: FREEZE_FUNDS || FreezeKind= 1 userId=" + user.getUserId() + " LoanId=" + loanId + " FreezeMoney=" + investAmount + " FreezeTime=" + freezeFund.getFreezeTime() + " FreezeStatus=1");
		freezeFundsDao.save(freezeFund);
		// FinancialAcTLedger licai =
		// financialAcTLedgerDao.findByTotalAccountIdAndBusiType(user.getAcTCustomer().getId(),
		// "1");
		List<FinancialInvestInfo> InvestInfos = financialInvestInfoDao.findByUserId(user.getUserId());
		double licai = 0;
		for (FinancialInvestInfo financialInvestInfo : InvestInfos) {
			if ("3".equals(financialInvestInfo.getStatus()) || "4".equals(financialInvestInfo.getStatus())) {
				licai = Calculator.add(licai, financialInvestInfo.getInvestAmount());
			}
		}
		FinancialAcTLedger ledger = financialAcTLedgerDao.findByTotalAccountIdAndBusiType(user.getAcTCustomer().getId(), "4");
		FinancialAcTLedger freezeLedger = financialAcTLedgerDao.findByTotalAccountIdAndBusiType(user.getAcTCustomer().getId(), "5");
		logger.info("SAVE: AC_T_FLOW || investAmount=" + investAmount + " code=3000000000001 Account=" + ledger.getAccount() + " 操作科目：投标冻结");
		// 流水表
		acTFlowDao.save(newFlow(investAmount, "", "3000000000001", ledger.getAccount(), freezeLedger.getAccount(), ConstSubject.bid_freeze_out, ConstSubject.bid_freeze_in, "操作科目：投标冻结"));
		// acTFlowDao.save(newFlow(investAmount, "", "3000000000001",
		// freezeLedger.getAccount(),ledger.getAccount() ,
		// ConstSubject.bid_freeze_in, ConstSubject.bid_freeze_out));

		// 分账信息表
		double amount = Calculator.sub(ledger.getAmount(), investAmount);
		ledger.setAmount(amount);
		logger.info("SAVE: AC_T_FLOW || ledgerId=" + ledger.getId() + " amount=" + amount);
		financialAcTLedgerDao.save(ledger);
		double freezeAmount = Calculator.add(freezeLedger.getAmount(), investAmount);
		freezeLedger.setAmount(freezeAmount);
		logger.info("SAVE: AC_T_FLOW || ledgerId=" + ledger.getId() + " amount=" + freezeAmount);
		financialAcTLedgerDao.save(freezeLedger);
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
		// 发送系统消息
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		sysMsg.setUserId(loanInfo.getUserId().getUserId());
		sysMsg.setWordId(BigDecimal.valueOf(8));
		sysMsg.setParameter1(loanInfo.getUserId().getLoginName());
		sysMsg.setParameter2(loanInfo.getLoanTitle());
		sysMsg.setParameter3(moneyFormat.format(investAmount));
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		logger.info("SAVE: AC_T_FLOW || userId=" + loanInfo.getUserId().getUserId() + " WordId=8 Parameter1=" + loanInfo.getUserId().getLoginName() + " Parameter2=" + loanInfo.getLoanTitle() + " Parameter3=" + investAmount + " HappenTime=" + sysMsg.getHappenTime() + " IsDel=0");
		sysMsgDao.save(sysMsg);
		// TODO 发送短信接口暂时注释

		// //给借款人发送短信、邮件 投标
		BigDecimal userId = loanInfo.getUserId().getUserId();
		List<PersonalUserMessageSet> messageList = messageSetDao.findByUserIdAndKindId(userId, new BigDecimal(2));
		for (PersonalUserMessageSet message : messageList) {
			if (message.getMannerId().equals(new BigDecimal(2))) {
				Map<String, String> map = new HashMap<String, String>();
				FinancialUsers loanUser = loanInfo.getUserId();
				map.put("0", loanUser.getLoginName());
				map.put("1", loanInfo.getLoanTitle());
				map.put("2", moneyFormat.format(investAmount));
				String messages = mimeMailService.transferMailContent("rev_bid", map);
				mimeMailService.sendNotifyMail(messages, loanUser.getEmail(), "有人向我的借款列表投标");
			}
			if (message.getMannerId().equals(new BigDecimal(3))) {
				Map<String, String> map = new HashMap<String, String>();
				FinancialUsers loanUser = loanInfo.getUserId();
				map.put("0", loanUser.getLoginName());
				map.put("1", loanInfo.getLoanTitle());
				map.put("2", moneyFormat.format(investAmount));
				SMSSender.sendMessage("rev_bid", loanUser.getUserInfoPerson().getPhoneNo(), map);
			}
		}
		// //发送短信 发送系统消息 借款列表完成度超过50%
		if (Calculator.add(scale, havaScale) >= 0.5) {
			FinancialSysMsg sysMsg2 = new FinancialSysMsg();
			sysMsg2.setUserId(loanInfo.getUserId().getUserId());
			sysMsg2.setWordId(BigDecimal.valueOf(11));
			sysMsg2.setParameter1(loanInfo.getUserId().getLoginName());
			sysMsg2.setParameter2(loanInfo.getLoanTitle());
			sysMsg2.setParameter3(moneyFormat.format(investAmount));
			String percent = ObjectFormatUtil.formatPercent(Calculator.add(scale, havaScale), "#,###.00%");
			sysMsg2.setParameter4(percent.substring(0, percent.length() - 1));
			sysMsg2.setHappenTime(new Date());
			sysMsg2.setIsDel("0");
			logger.info("SAVE: SYS_MSG || userId=" + loanInfo.getUserId().getUserId() + " WordId=11 Parameter1=" + loanInfo.getUserId().getLoginName() + " Parameter2=" + loanInfo.getLoanTitle() + " Parameter3=" + investAmount + " Parameter4=" + percent.substring(0, percent.length() - 1) + " HappenTime=" + sysMsg.getHappenTime() + " IsDel=0");
			sysMsgDao.save(sysMsg2);
			List<PersonalUserMessageSet> messageList2 = messageSetDao.findByUserIdAndKindId(userId, new BigDecimal(4));
			FinancialUsers loanUser = loanInfo.getUserId();
			for (PersonalUserMessageSet message : messageList2) {
				if (message.getMannerId().equals(new BigDecimal(2))) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("0", loanUser.getLoginName());
					map.put("1", loanInfo.getLoanTitle());
					map.put("2", moneyFormat.format(investAmount));
					map.put("3", percent);
					String messages = mimeMailService.transferMailContent("over_half", map);
					mimeMailService.sendNotifyMail(messages, loanUser.getEmail(), "我的借款列表完成度超过50%");
				}
				if (message.getMannerId().equals(new BigDecimal(3))) {
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("0", loanUser.getLoginName());
					map2.put("1", loanInfo.getLoanTitle());
					map2.put("2", moneyFormat.format(investAmount));
					map2.put("3", percent);
					SMSSender.sendMessage("over_half", loanUser.getUserInfoPerson().getPhoneNo(), map2);
				}
			}
		}

		// 发送系统消息 投标成功
		FinancialSysMsg sysMsg3 = new FinancialSysMsg();
		sysMsg3.setUserId(user.getUserId());
		sysMsg3.setWordId(BigDecimal.valueOf(12));
		sysMsg3.setParameter1(user.getLoginName());
		sysMsg3.setParameter2(loanInfo.getLoanTitle());
		sysMsg3.setParameter3(moneyFormat.format(investAmount));
		sysMsg3.setParameter4(moneyFormat.format(ledger.getAmount().doubleValue()));
		sysMsg3.setParameter5(moneyFormat.format(licai));
		sysMsg3.setHappenTime(new Date());
		sysMsg3.setIsDel("0");
		logger.info("SAVE: SYS_MSG || userId=" + loanInfo.getUserId().getUserId() + " WordId=12 Parameter1=" + user.getLoginName() + " Parameter2=" + loanInfo.getLoanTitle() + " Parameter3=" + investAmount + " Parameter4=" + ledger.getAmount() + " Parameter5=" + licai + " HappenTime=" + sysMsg.getHappenTime() + " IsDel=0");
		sysMsgDao.save(sysMsg3);

		// 投标最新动态
		HomepageUserMovement movement = new HomepageUserMovement();
		HomepageMovementWord moWord = new HomepageMovementWord();
		moWord.setWordId(new BigDecimal(6));
		movement.setUserId(user.getUserId());
		movement.setParameter1(loanInfo.getLoanTitle());
		movement.setParameter2(moneyFormat.format(investAmount));
		movement.setWordId(moWord);
		movement.setUrl1("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
		movement.setMsgKind("1");
		movement.setIsDel("0");
		movement.setHappenTime(new Date());
		logger.info("SAVE: USER_MOVEMENT || userId=" + loanInfo.getUserId().getUserId() + " WordId=" + moWord + " Parameter1=" + user.getLoginName() + " Parameter2=" + investAmount + " HappenTime=" + sysMsg.getHappenTime() + " MsgKind=1 IsDel=0");
		movementDao.save(movement);

		// //给理财人发送短信、邮件 投标成功
		List<PersonalUserMessageSet> messageInvestList = messageSetDao.findByUserIdAndKindId(user.getUserId(), new BigDecimal(5));
		for (PersonalUserMessageSet message : messageInvestList) {
			if (message.getMannerId().equals(new BigDecimal(2))) {
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("0", user.getLoginName());
				map3.put("1", loanInfo.getLoanTitle());
				map3.put("2", moneyFormat.format(investAmount));
				map3.put("3", moneyFormat.format(ledger.getAmount().doubleValue()));
				map3.put("4", moneyFormat.format(licai));
				String messages = mimeMailService.transferMailContent("bid_succ", map3);
				mimeMailService.sendNotifyMail(messages, user.getEmail(), "我的投标成功");
			}
			if (message.getMannerId().equals(new BigDecimal(3))) {
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("0", user.getLoginName());
				map3.put("1", loanInfo.getLoanTitle());
				map3.put("2", moneyFormat.format(investAmount));
				map3.put("3", moneyFormat.format(ledger.getAmount().doubleValue()));
				map3.put("4", moneyFormat.format(licai));
				SMSSender.sendMessage("bid_succ", user.getUserInfoPerson().getPhoneNo(), map3);
			}
		}

		return true;

	}

	// 搜索
	public List<LoanInfoVO> searchLoanBycondition(String creditGrade, String yearRate, String loanDuration, String column, String seq, String p, String tabFlag) {
		List<LoanInfoVO> voList = new ArrayList<LoanInfoVO>();
		List<FinancialLoanInfo> loanList = loanInfoDao.searchLoanBycondition(creditGrade, yearRate, loanDuration, column, seq, p, tabFlag);
		for (FinancialLoanInfo loan : loanList) {
			LoanInfoVO vo = new LoanInfoVO();
			try {
				PropertyUtils.copyProperties(vo, loan);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<FinanciaUserApprove> appList = financiaUserApproveDao.findByUserIdAndReviewStatus(loan.getUserId().getUserId(), BigDecimal.ONE);
			vo.setUserApproveList(appList);

			// if(loan.getStatus().equals( BigDecimal.ONE)){
			// List<FinancialInvestInfo> invList =
			// financialInvestInfoDao.findByLoanId(loan.getLoanId());
			// double leaveMoney = 0d;
			// for(FinancialInvestInfo invInfo:invList){
			// leaveMoney = leaveMoney+invInfo.getInvestAmount();
			// }
			// double schedule = 0d;
			// if(leaveMoney != 0){
			// schedule = Calculator.div(leaveMoney, loan.getLoanAmount());
			// }
			// //进度
			// vo.setSchedule(ObjectFormatUtil.formatPercent(schedule,
			// "##,#0.00%"));
			// vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(loan.getLoanAmount()
			// - leaveMoney));
			// vo.setLeavingTime(DateUtil.show( new Date() ,
			// DateUtil.getSevenDaysDate(loan.getReleaseTime())));
			// vo.setInvestmentCount(String.valueOf(invList.size()));
			// }else {
			// vo.setSchedule("0");
			// vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(loan.getLoanAmount()));
			// vo.setLeavingTime("");
			// vo.setInvestmentCount("0");
			// }

			List<FinancialInvestInfo> invList = financialInvestInfoDao.findByLoanId(loan.getLoanId());
			double leaveMoney = 0d;
			for (FinancialInvestInfo invInfo : invList) {
				leaveMoney = leaveMoney + invInfo.getInvestAmount();
			}
			double schedule = 0d;
			if (leaveMoney != 0) {
				schedule = Calculator.div(leaveMoney, loan.getLoanAmount());
			}
			// 进度
			vo.setSchedule(ObjectFormatUtil.formatPercent(schedule, "##,#0.00%"));
			vo.setLeavingMoney(ObjectFormatUtil.formatCurrency(loan.getLoanAmount() - leaveMoney));
			// add by jih
			Date dates = loan.getStartInvestTime();
			Date startDate = new java.util.Date();
			startDate.setDate(dates.getDate());
			startDate.setYear(dates.getYear());
			startDate.setMonth(dates.getMonth());
			startDate.setHours(dates.getHours());
			startDate.setMinutes(dates.getMinutes());
			startDate.setSeconds(dates.getSeconds());
			vo.setLeavingTime(DateUtil.show(new Date(), DateUtil.getSevenDaysDate(startDate)));
			// Date dates = loan.getReleaseTime();
			// vo.setLeavingTime(DateUtil.show(new Date(),
			// DateUtil.getSevenDaysDateNoReplace(dates)));
			vo.setInvestmentCount(String.valueOf(invList.size()));

			vo.setYearRateStr(ObjectFormatUtil.formatPercent(loan.getYearRate(), "##,#0.00%"));
			vo.setDateStr(DateUtil.getYMDTime(loan.getReleaseTime()));
			voList.add(vo);
		}
		return voList;

	}

	// 统计数量
	/**
	 * status状态:1招标中,2已满标,3流标,4还款中,5成功,6逾期,7高级逾期,8待审核,0保存 2012-11-30 下午5:25:11
	 * by HuYaHui
	 * 
	 * @return
	 */
	public LoanInfoVO loanCount() {
		long start = System.currentTimeMillis();
		LoanInfoVO countLoan = new LoanInfoVO();
		// 全部
		Object allLoan = loanInfoDao.countFinancialLoanInfoByStatusOrReleaseStatus(null, BigDecimal.ONE);
		countLoan.setAllLoanCount(String.valueOf(allLoan));
		// 进行中的数量
		Object ingLoanCount = loanInfoDao.countFinancialLoanInfoByStatusOrReleaseStatus(new BigDecimal[] { BigDecimal.ONE }, BigDecimal.ONE);
		countLoan.setIngLoanCount(String.valueOf(ingLoanCount));
		// 即将开始的数量 (待审核)
		Object futureLoanCount = loanInfoDao.countFinancialLoanInfoByStatusOrReleaseStatus(new BigDecimal[] { BigDecimal.valueOf(8) }, null);
		countLoan.setFutureLoanCount(String.valueOf(futureLoanCount));
		// 已经完成的数量 (2,4,5,6,7) and release_status=1
		Object loanList = loanInfoDao.countFinancialLoanInfoByStatusOrReleaseStatus(new BigDecimal[] { BigDecimal.valueOf(2), BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6), BigDecimal.valueOf(7) }, BigDecimal.valueOf(1));
		countLoan.setOldLoanCount(String.valueOf(Long.valueOf(loanList + "")));
		performanceLogger.info("查询借款信息统计数量耗时：" + (System.currentTimeMillis() - start));
		return countLoan;
	}

	// 取得userid
	public FinancialUsers getUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		FinancialUsers userInfo = financialUsersDao.findByUserId(userid);
		return userInfo;
	}

	/**
	 * 判断是否是理财人
	 */
	public boolean isFinancial(HttpServletRequest request) {
		FinancialUsers userInfo = getUsers(request);
		if (null != userInfo.getIsApprovePhone() && null != userInfo.getIsApproveCard() && userInfo.getIsApprovePhone().equals(BigDecimal.ONE) && userInfo.getIsApproveCard().equals(BigDecimal.ONE)) {
			return true;
		}
		return false;

	}

	public Boolean checkInvestTime(BigDecimal userId) {
		List<FinancialInvestInfo> investInfoList = financialInvestInfoDao.checkInvestTime(userId);
		if (investInfoList == null || investInfoList.size() == 0) {
			return true;
		}
		FinancialInvestInfo investInfo = investInfoList.get(0);
		if (investInfo == null || investInfo.getInvestTime() == null) {
			return true;
		} else {
			long minute = (new Date().getTime() - investInfo.getInvestTime().getTime()) / 30000;
			if (minute > 1 || minute < -1) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 新建流水
	 * 
	 * @return
	 */
	public FinancialAcTFlow newFlow(double tradeAmount, String teller, String Organ, String account, String appoAcc, String acctTitle, String appoAcctTitle) {
		FinancialAcTFlow newFlow = new FinancialAcTFlow();
		newFlow.setTradeDate(new Timestamp(System.currentTimeMillis()));// 交易日期
		newFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
		newFlow.setTradeAmount(tradeAmount);// 交易金额
		newFlow.setTradeType("1");// 交易类型:现金
		newFlow.setTeller(teller);// 柜员号
		newFlow.setOrgan(Organ);// 营业网点
		newFlow.setAccount(account);// 账号
		newFlow.setAppoAcct(appoAcc);// 对方账户
		newFlow.setAcctTitle(acctTitle);// 科目号
		newFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
		newFlow.setMemo("操作科目：" + ConstSubject.getNameBySubject(acctTitle));// 备注
		return newFlow;
	}

	/**
	 * 新建流水
	 * 
	 * @return
	 */
	public FinancialAcTFlow newFlow(double tradeAmount, String teller, String Organ, String account, String appoAcc, String acctTitle, String appoAcctTitle, String memo) {
		FinancialAcTFlow newFlow = new FinancialAcTFlow();
		newFlow.setTradeDate(new Timestamp(System.currentTimeMillis()));// 交易日期
		newFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
		newFlow.setTradeAmount(tradeAmount);// 交易金额
		newFlow.setTradeType("1");// 交易类型:现金
		newFlow.setTeller(teller);// 柜员号
		newFlow.setOrgan(Organ);// 营业网点
		newFlow.setAccount(account);// 账号
		newFlow.setAppoAcct(appoAcc);// 对方账户
		newFlow.setAcctTitle(acctTitle);// 科目号
		newFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
		newFlow.setMemo(memo);// 备注
		return newFlow;
	}

	public String listToJson(List<LoanInfoVO> list){
		JSONArray jsonArry=new JSONArray();
		for(LoanInfoVO vo:list){
			JSONObject obj=JSONObject.fromObject(vo);
			jsonArry.add(obj);
		}
		return jsonArry.toString();
	}
}
