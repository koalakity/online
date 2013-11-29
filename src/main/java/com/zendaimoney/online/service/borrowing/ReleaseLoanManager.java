package com.zendaimoney.online.service.borrowing;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.Formula;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.dao.borrowing.BorrowingInvestDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserCreditNoteDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserInfoJobDao;
import com.zendaimoney.online.dao.borrowing.ReleaseLoanDao;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.dao.fundDetail.FundDao;
import com.zendaimoney.online.dao.homepage.HomepageUserMovementDao;
import com.zendaimoney.online.dao.personal.PersonalUserMessageSetDao;
import com.zendaimoney.online.dao.stationMessage.StationMessageStationLetterDao;
import com.zendaimoney.online.dao.stationMessage.StationMessageUserDao;
import com.zendaimoney.online.entity.borrowing.BorrowingAcTLedgerLoan;
import com.zendaimoney.online.entity.borrowing.BorrowingInvestInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoJob;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.common.Rate;
import com.zendaimoney.online.entity.homepage.HomepageMovementWord;
import com.zendaimoney.online.entity.homepage.HomepageUserMovement;
import com.zendaimoney.online.entity.stationMessage.StationMessageStationLetter;
import com.zendaimoney.online.entity.stationMessage.StationMessageUsers;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.vo.borrowing.BorrowingCreditInfoVO;
import com.zendaimoney.online.vo.borrowing.BorrowingCreditRecordVO;
import com.zendaimoney.online.vo.borrowing.CurrentInvestInfoVO;
import com.zendaimoney.online.vo.borrowing.InvestInfoListVO;
import com.zendaimoney.online.vo.releaseLoan.LoanInfoDetailVO;
import com.zendaimoney.online.vo.releaseLoan.LoanInfoListVO;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-18 下午2:38:25 operation by: description:发布借款管理
 */
// Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class ReleaseLoanManager {
	private static Logger logger = LoggerFactory.getLogger(ReleaseLoanManager.class);
	private BigDecimal currUserId;

	private BorrowingLoanInfo currLoanInfo;

	@Autowired
	private ReleaseLoanDao releaseLoanDao;
	@Autowired
	private BorrowingUserDao borrowingUserDao;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private BorrowingUserInfoJobDao buserInfoJobDao;

	@Autowired
	private BorrowingUserCreditNoteDao userCreditNoteDao;

	@Autowired
	private BorrowingInvestDao investDao;

	@Autowired
	private StationMessageUserDao stationMessageUserDao;

	@Autowired
	private StationMessageStationLetterDao stationMessageStationLetterDao;

	@Autowired
	private FundDao fundDao;

	@Autowired
	MimeMailService mimeMailService;
	@Autowired
	FinancialSysMsgDao sysMsgDao;
	@Autowired
	PersonalUserMessageSetDao messageSetDao;

	@Autowired
	HomepageUserMovementDao movementDao;
	@Autowired
	RateCommonUtil rateCommon;

	// @Autowired
	// private StationMessageUserApproveDao stationMessageUserApproveDao;

	@Transactional(readOnly = false)
	public String saveReleaseLoanInfo(BorrowingLoanInfo loanInfo, HttpServletRequest req) {
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(getCurrentUserId(req));
		BorrowingLoanInfo newLoanInfo = setLoanInfo(req, loanInfo, bruser.getUserId());
		// 删除当前用户，已经存在的未发布的借款
		releaseLoanDao.deleteByUserIdAndStatus(bruser.getUserId());
		Long loanRateId = rateCommon.setLoanRate(bruser.getUserId().longValue());
		logger.info("借款对应的费率ID信息~~~~~~~~~~~~~~" + loanRateId);
		newLoanInfo.setLoanRateId(loanRateId);
		releaseLoanDao.save(newLoanInfo);
		if (newLoanInfo.getStatus().equals(BigDecimal.ONE)) {
			HomepageUserMovement movement = new HomepageUserMovement();
			HomepageMovementWord moWord = new HomepageMovementWord();
			moWord.setWordId(BigDecimal.ONE);
			movement.setUserId(bruser.getUserId());
			movement.setWordId(moWord);
			movement.setParameter1(loanInfo.getLoanTitle());
			movement.setUrl1("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
			movement.setMsgKind("1");
			movement.setHappenTime(new Date());
			movement.setIsDel("0");
			logger.info("SAVE: USER_MOVEMENT || msgKind=1 WordId=1 UserId=" + movement.getUserId() + " url=" + movement.getUrl1());
			movementDao.save(movement);
		}

		return "saveSuccess";
	}

	public BorrowingLoanInfo getReleaseLoanInfo(HttpServletRequest req) {
		BorrowingLoanInfo loanInfo = releaseLoanDao.findByUserId(getCurrentUserId(req));
		// loanInfo.setYearRate(ArithUtil.mul(loanInfo.getYearRate(),100));
		return loanInfo;
	}

	public BigDecimal getAvailableAmount(HttpServletRequest req) {
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(getCurrentUserId(req));
		// 查询信用额度
		BigDecimal userAmount = releaseLoanDao.getUserAvailableCredit(bruser.getUserId());
		// 查询冻结资金
		double frozenAmount = releaseLoanDao.getFrozenAmount(bruser);
		return new BigDecimal(ArithUtil.sub(userAmount.doubleValue(), frozenAmount));
	}

	public BigDecimal getAvailableAmountByUser(BigDecimal userId) {
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(userId);
		// 查询信用额度
		BigDecimal userAmount = releaseLoanDao.getUserAvailableCredit(bruser.getUserId());
		// 查询冻结资金
		double frozenAmount = releaseLoanDao.getFrozenAmount(bruser);
		return new BigDecimal(ArithUtil.sub(userAmount.doubleValue(), frozenAmount));
	}

	public BigDecimal getUserCreditAmount(HttpServletRequest req) {
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(getCurrentUserId(req));
		// 查询信用额度
		BigDecimal userAmount = releaseLoanDao.getUserAvailableCredit(bruser.getUserId());
		return userAmount;
	}

	// 发布借款时判断是否进入导航页面
	public String checkUserStatus(HttpServletRequest req) {
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(getCurrentUserId(req));
		BigDecimal amount = getAvailableAmount(req);
		String status = "";
		if (bruser.getUserStatus().intValue() <= 4) {
			status = "guide";
		} else if (bruser.getUserStatus().intValue() == 5 && amount.doubleValue() <= 0) {
			status = "guide";
		} else if (bruser.getUserStatus().intValue() == 6) {
			status = "lock";
		} else if (bruser.getUserStatus().intValue() == 7) {
			status = "report";
		} else {
			status = "release";
		}
		return status;
	}

	// 发布借款时 是否有在途借款
	public String isReleaseLoanInfo(HttpServletRequest req) {
		List<BorrowingLoanInfo> list1 = releaseLoanDao.findByUserAndStatus(getUsersByUserId(req), BigDecimal.ONE);
		List<BorrowingLoanInfo> list2 = releaseLoanDao.findByUserAndStatus(getUsersByUserId(req), new BigDecimal(8));
		if ((list1 != null && list1.size() != 0) || (list2 != null && list2.size() != 0)) {
			return "yes";
		} else {
			return "no";
		}
	}

	// 获取当前正在进行的借款信息
	public LoanInfoListVO getLoanInfoList(HttpServletRequest req) {
		LoanInfoListVO loanList = new LoanInfoListVO();
		List<BorrowingLoanInfo> list1 = releaseLoanDao.findByUserAndStatus(getUsersByUserId(req), BigDecimal.ONE);
		List<BorrowingLoanInfo> list2 = releaseLoanDao.findByUserAndStatus(getUsersByUserId(req), new BigDecimal(8));
		List<LoanInfoDetailVO> loanInfoList = new ArrayList<LoanInfoDetailVO>();
		for (BorrowingLoanInfo loanInfo1 : list1) {
			LoanInfoDetailVO vo1 = new LoanInfoDetailVO();
			double speedProgress = 0;
			try {
				PropertyUtils.copyProperties(vo1, loanInfo1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (BorrowingInvestInfo brInvestInfo1 : loanInfo1.getInvestInfoList()) {
				speedProgress += brInvestInfo1.getHavaScale();
			}
			vo1.setReleaseDateStr(DateUtil.getYMDTime(loanInfo1.getReleaseTime()));
			vo1.setReleaseTimeStr(DateUtil.getHMSTime(loanInfo1.getReleaseTime()));
			vo1.setSpeedProgress(ObjectFormatUtil.formatPercent(speedProgress, "##,#0.00%"));
			vo1.setAmount(ObjectFormatUtil.formatCurrency(loanInfo1.getLoanAmount()));
			vo1.setRate(ObjectFormatUtil.formatPercent(loanInfo1.getYearRate(), "##,#0.00%"));
			vo1.setBidNumber(loanInfo1.getInvestInfoList().size());
			loanInfoList.add(vo1);
		}
		for (BorrowingLoanInfo loanInfo2 : list2) {
			LoanInfoDetailVO vo2 = new LoanInfoDetailVO();
			double speedProgress = 0;
			try {
				PropertyUtils.copyProperties(vo2, loanInfo2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (BorrowingInvestInfo brInvestInfo2 : loanInfo2.getInvestInfoList()) {
				speedProgress += brInvestInfo2.getHavaScale();
			}
			vo2.setReleaseDateStr(DateUtil.getYMDTime(loanInfo2.getReleaseTime()));
			vo2.setReleaseTimeStr(DateUtil.getHMSTime(loanInfo2.getReleaseTime()));
			vo2.setSpeedProgress(ObjectFormatUtil.formatPercent(speedProgress, "##,#0.00%"));
			vo2.setAmount(ObjectFormatUtil.formatCurrency(loanInfo2.getLoanAmount()));
			vo2.setRate(ObjectFormatUtil.formatPercent(loanInfo2.getYearRate(), "##,#0.00%"));
			vo2.setBidNumber(loanInfo2.getInvestInfoList().size());
			loanInfoList.add(vo2);
		}
		loanList.setLoanInfoList(loanInfoList);
		return loanList;
	}

	public String checkReleaseStatus(BorrowingLoanInfo loanInfo, HttpServletRequest req) {
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(getCurrentUserId(req));
		// 可用额度
		BigDecimal amount = getAvailableAmount(req);
		String status = "";
		// 用户状态正常
		if (bruser.getUserStatus().intValue() == 5) {
			if (amount.doubleValue() >= loanInfo.getLoanAmount()) {
				status = "release";
			} else if (amount.doubleValue() > 0 && amount.doubleValue() < loanInfo.getLoanAmount()) {
				status = "finish";
			} else {
				status = "waitUpdate";
			}
		} else if (bruser.getUserStatus().intValue() == 6 || bruser.getUserStatus().intValue() == 7) {
			status = "lockOrReport";
		} else if (bruser.getUserStatus().intValue() == 1 || bruser.getUserStatus().intValue() == 2 || bruser.getUserStatus().intValue() == 3) {
			status = "isVerify";
		} else {
			status = "review";
		}
		return status;
	}

	// 当前登录用户信息
	public BigDecimal getCurrentUserId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		this.currUserId = new BigDecimal(session.getAttribute("curr_login_user_id").toString());
		return currUserId;
	}

	public BorrowingUsers getUsersByUserId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal userId = new BigDecimal(session.getAttribute("curr_login_user_id").toString());
		return borrowingUserDao.findByUserId(userId);
	}

	/**
	 * @author Ray
	 * @date 2012-12-11 下午4:13:08
	 * @param req
	 * @param loanInfo
	 * @param userId
	 * @return description:发布借款信息设定 2012-12-11
	 *         修改，新增开始筹标时间，如果进入状态1，招标中，需要存入开始筹标时间
	 */
	public BorrowingLoanInfo setLoanInfo(HttpServletRequest req, BorrowingLoanInfo loanInfo, BigDecimal userId) {
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(userId);
		BigDecimal amount = getAvailableAmount(req);
		BigDecimal status = new BigDecimal(1);
		BigDecimal releaseStatus = loanInfo.getReleaseStatus();
		if (loanInfo.getReleaseStatus().intValue() == 1) {
			if (bruser.getUserStatus().intValue() == 5) {
				if (amount.doubleValue() >= loanInfo.getLoanAmount()) {
					status = BigDecimal.ONE;
				} else if (amount.doubleValue() > 0 && amount.doubleValue() < loanInfo.getLoanAmount()) {
					status = new BigDecimal(8);
				} else {
					throw new RuntimeException("您当前的可用信用额度为0，请先上传资料获取信用额度！");
				}
			} else if (bruser.getUserStatus().intValue() == 6) {
				throw new RuntimeException("您的账户已被举报或锁定，暂时不能发布借款！");
			} else if (bruser.getUserStatus().intValue() == 1 || bruser.getUserStatus().intValue() == 2 || bruser.getUserStatus().intValue() == 3) {
				throw new RuntimeException("您当前的可用信用额度为0，请先上传资料获取信用额度！");
			} else {
				status = new BigDecimal(8);
			}
		} else {
			releaseStatus = new BigDecimal(2);
			status = BigDecimal.ZERO;

		}
		loanInfo.setUser(releaseLoanDao.getUserByUserId(userId));
		loanInfo.setYearRate(ArithUtil.div(loanInfo.getYearRate(), 100));
		loanInfo.setPaymentMethod(loanInfo.getPaymentMethod() != null ? loanInfo.getPaymentMethod() : new BigDecimal(1));
		loanInfo.setRaiseDuration(loanInfo.getRaiseDuration() != null ? loanInfo.getRaiseDuration() : new BigDecimal(7));
		loanInfo.setMonthReturnPrincipalandinter(loanInfo.getMonthReturnPrincipalandinter());
		loanInfo.setMonthManageCost(loanInfo.getMonthManageCost());
		loanInfo.setStatus(status);
		/*-----------------------新增开始筹标时间 start------------------------------*/
		if (status == BigDecimal.ONE) {
			loanInfo.setStartInvestTime(new Date());
		}
		/*-----------------------新增开始筹标时间 end------------------------------*/
		loanInfo.setLoanTitle(loanInfo.getLoanTitle().replace("—", "-"));
		loanInfo.setReleaseTime(DateUtil.getCurrentDate());
		loanInfo.setIsShowAge(loanInfo.getIsShowAge() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowSex(loanInfo.getIsShowSex() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowDegree(loanInfo.getIsShowDegree() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowSchool(loanInfo.getIsShowSchool() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowEntranceYear(loanInfo.getIsShowEntranceYear() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowWorkCity(loanInfo.getIsShowWorkCity() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowVocation(loanInfo.getIsShowVocation() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowCompanyScale(loanInfo.getIsShowCompanyScale() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowOffice(loanInfo.getIsShowOffice() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowWorkYear(loanInfo.getIsShowWorkYear() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowMarry(loanInfo.getIsShowMarry() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowHaveHouse(loanInfo.getIsShowHaveHouse() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowHouseLoan(loanInfo.getIsShowHouseLoan() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowHaveCar(loanInfo.getIsShowHaveCar() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setIsShowCarLoan(loanInfo.getIsShowCarLoan() != null ? BigDecimal.ONE : BigDecimal.ZERO);
		loanInfo.setReleaseStatus(releaseStatus);
		if (loanInfo.getReleaseStatus().intValue() == 1) {
			BorrowingAcTLedgerLoan actLedgerLoan = new BorrowingAcTLedgerLoan();
			actLedgerLoan.setAcctStatus("1");
			actLedgerLoan.setLedgerId(1L);
			loanInfo.setActLedgerLoan(actLedgerLoan);
		}
		loanInfo.setLoanAmount(ArithUtil.formatScale(loanInfo.getLoanAmount(), 2));

		return loanInfo;
	}

	// 是否有未完成的借款
	public BorrowingLoanInfo getLoanInfoDetail(BigDecimal loanId) {
		BorrowingLoanInfo brLoanInfo = releaseLoanDao.findByLoanId(loanId);
		return brLoanInfo;
	}

	// 计算月还本息
	public double getprincipanInterestMonth(String loanAmount, String yearRate, String loanPeriod) {
		double loanAmount_doubleValue = new BigDecimal(loanAmount).doubleValue();
		double yearRate_doubleValue = new BigDecimal(yearRate).doubleValue();
		int loan_period = Integer.parseInt(loanPeriod);
		Formula furmula = new Formula(loanAmount_doubleValue, loan_period, new Date(), yearRate_doubleValue, 0.005, 0, 0, 0);
		return furmula.getPrincipal_interest_month_first();
	}

	// 计算月还管理费
	// 计算月还管理费
	public double getManagementFeeEveryMonth(HttpServletRequest req, String loanAmount) {
		double loanAmount_doubleValue = new BigDecimal(loanAmount).doubleValue();
		BigDecimal userId = getCurrentUserId(req);
		Rate rate = rateCommon.getRateByUser(userId.longValue());
		return FormulaSupportUtil.getManagementFeeEveryMonth(loanAmount_doubleValue, rate.getMgmtFee().doubleValue());
	}

	// 借款详情及相关信息的设定
	public InvestInfoListVO setInvestInfo(BorrowingLoanInfo loanInfo, HttpServletRequest req) {
		currLoanInfo = loanInfo;
		InvestInfoListVO investInfoListVO = new InvestInfoListVO();
		// 当前借款详情
		// 借贷编号
		investInfoListVO.setLoanId(loanInfo.getLoanId());
		// 借款标题
		investInfoListVO.setLoanTitle(loanInfo.getLoanTitle());
		// 借款金额
		investInfoListVO.setLoanAmount(ObjectFormatUtil.formatCurrency(loanInfo.getLoanAmount()));
		// 借款利率
		if (loanInfo.getLoanId() != null) {
			if (loanInfo.getReleaseStatus().equals(BigDecimal.ZERO)) {
				investInfoListVO.setYearRate(ObjectFormatUtil.formatPercent(ArithUtil.div(loanInfo.getYearRate(), 100), "#,#0.00%"));
			} else {
				investInfoListVO.setYearRate(ObjectFormatUtil.formatPercent(loanInfo.getYearRate(), "#,#0.00%"));
			}
		} else {
			investInfoListVO.setYearRate(ObjectFormatUtil.formatPercent(ArithUtil.div(loanInfo.getYearRate(), 100), "#,#0.00%"));
		}
		// 借款期限
		investInfoListVO.setLoanPeriod(loanInfo.getLoanDuration());
		// 还款周期
		investInfoListVO.setPayTerm("每月还款");
		// 还款方式
		investInfoListVO.setPaymentMethod("等额本息");
		// 月还本息
		investInfoListVO.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(loanInfo.getMonthReturnPrincipalandinter()));
		// 投标笔数
		investInfoListVO.setBidNumber(loanInfo.getInvestInfoList() != null ? loanInfo.getInvestInfoList().size() : 0);
		// 用户信息
		BorrowingUsers bruser = loanInfo.getUser();
		;
		if (bruser != null) {
			investInfoListVO.setUser(bruser);
		} else {
			investInfoListVO.setUser(getUsersByUserId(req));
		}
		// 发布借款笔数
		int releaseLoanNumber = 0;
		// 当前借款剩余时间
		String surplusTime = null;
		// 目前投标总额
		double currentBidAmount = 0;
		// 投标笔数
		investInfoListVO.setBidNumber(loanInfo.getInvestInfoList() != null ? loanInfo.getInvestInfoList().size() : 0);
		List<BorrowingLoanInfo> loanInfoList = new ArrayList<BorrowingLoanInfo>();
		if (investInfoListVO.getUser() != null) {

			loanInfoList = investInfoListVO.getUser().getLoanInfoes();
		}
		if (loanInfoList.size() != 0 && loanInfoList != null) {
			for (BorrowingLoanInfo eachLoanInfo : loanInfoList) {
				releaseLoanNumber += 1;
			}
		}
		// 用户投标信息
		investInfoListVO.setInvestInfoList(loanInfo.getInvestInfoList());
		List<BorrowingInvestInfo> borrowingInvestInfoList = loanInfo.getInvestInfoList() != null ? loanInfo.getInvestInfoList() : new ArrayList<BorrowingInvestInfo>();
		for (BorrowingInvestInfo borrowingInvestInfo : borrowingInvestInfoList) {
			currentBidAmount += borrowingInvestInfo.getInvestAmount();

		}
		// 投标进度
		investInfoListVO.setSpeedProgress(ArithUtil.mul(ArithUtil.div(currentBidAmount, loanInfo.getLoanAmount(), 4), 100));
		// 目前投标剩余时间
		if (null != loanInfo.getStatus() && (BigDecimal.ONE).equals(loanInfo.getStatus()) || new BigDecimal(8).equals(loanInfo.getStatus())) {
			surplusTime = DateUtil.show(DateUtil.getCurrentDate(), loanInfo.getStartInvestTime() != null ? DateUtil.getSevenDaysDate(loanInfo.getStartInvestTime()) : DateUtil.getSevenDaysDate(DateUtil.getCurrentDate()));
		} else {
			surplusTime = "";
		}
		// 尚缺金额
		investInfoListVO.setSurplusAmount(ObjectFormatUtil.formatCurrency(ArithUtil.sub(loanInfo.getLoanAmount(), currentBidAmount)));
		investInfoListVO.setSurplusTime(surplusTime);
		// 借款用途(id)
		investInfoListVO.setLoanUse(currLoanInfo.getLoanUse());
		// 借款状态
		investInfoListVO.setStatus(currLoanInfo.getStatus() == null ? "" : currLoanInfo.getStatus().toString());
		// 借款用途(name)
		investInfoListVO.setLoanUseStr(commonDao.findByCodeId(currLoanInfo.getLoanUse()).getCodeName());
		return investInfoListVO;
	}

	// 借入者信用信息
	public BorrowingCreditInfoVO setBorrowerCreditInfo(BigDecimal userId, HttpServletRequest req) {
		// 发布借款笔数
		int releaseLoanNumber = 0;
		// 成功借款笔数
		int successLoanNumber = 0;
		// 还清笔数
		int payOffLoanNumber = 0;
		// 逾期次数
		int overdueCount = 0;
		// 严重逾期次数
		int seriousOverdueCount = 0;
		// 待还本息
		double dhAmount = fundDao.getLoanPrincipalInterest(userId, false);
		// 逾期金额
		double overdueAmount = 0;
		// 共计借出
		double lendTotalAmount = fundDao.getTotalAmountInvest(userId);
		// 待收本息
		double waitRecoverAmount = fundDao.getInvestPrincipalInterest(userId, false);
		// 月收入水平
		BigDecimal monthIncomeCode;
		// 共计借入
		double loanTotal = 0;
		List<BorrowingLoanInfo> loans = releaseLoanDao.findByUserUserId(userId);
		List<BorrowingInvestInfo> investinfos = investDao.findByUserUserId(userId);

		for (BorrowingLoanInfo loanInfo : loans) {
			// 发布借款笔数
			releaseLoanNumber++;
			int status = loanInfo.getStatus().intValue();
			if (status == 4 || status == 5 || status == 6 || status == 7) {
				// 成功借款笔数
				successLoanNumber++;
				// 共计借入
				loanTotal = Calculator.add(loanTotal, loanInfo.getLoanAmount());
				// 当前借款待还金额
				// double currDhAmount =
				// Calculator.mul(loanInfo.getMonthReturnPrincipalandinter(),
				// Calculator.sub(loanInfo.getLoanDuration().doubleValue(),
				// Double.longBitsToDouble(loanInfo.getActLedgerLoan().getCurrNum()-1))
				// );
				// dhAmount = Calculator.add(dhAmount, currDhAmount);
				// if(status == 6 || status == 7){
				// overdueAmount = Calculator.add(overdueAmount, currDhAmount);
				// }

			}
			if (status == 5) {
				// 还清笔数
				payOffLoanNumber++;
			}
		}

		for (BorrowingInvestInfo investInfo : investinfos) {
			int stauts = investInfo.getStatus().intValue();
			if (stauts == 4 || stauts == 6 || stauts == 7) {
				investInfo.getLoanInfo().getActLedgerLoan().getCurrNum();
				BorrowingLoanInfo loanInfo = investInfo.getLoanInfo();
				double currDhAmount = Calculator.mul(loanInfo.getMonthReturnPrincipalandinter(), Calculator.sub(loanInfo.getLoanDuration().doubleValue(), Double.longBitsToDouble(loanInfo.getActLedgerLoan().getCurrNum() - 1)));
				double currWaitRecoverAmount = Calculator.mul(currDhAmount, investInfo.getHavaScale());
				waitRecoverAmount = Calculator.add(waitRecoverAmount, currWaitRecoverAmount);
			}
		}
		BorrowingUserCreditNote userCreditNote = userCreditNoteDao.findByUserId(userId);
		// 逾期次数
		if (null != userCreditNote) {
			overdueCount = userCreditNote.getOverdueCount() == null ? 0 : userCreditNote.getOverdueCount().intValue();
			// 严重逾期次数
			seriousOverdueCount = userCreditNote.getSeriousOverdueCount() == null ? 0 : userCreditNote.getSeriousOverdueCount().intValue();
		}
		// 用户信息
		String workCity;
		BorrowingUsers bruser = releaseLoanDao.getUserByUserId(userId);
		BorrowingCreditInfoVO creditInfo = new BorrowingCreditInfoVO();
		List<BorrowingUserInfoJob> jobList = buserInfoJobDao.findByUserId(userId);
		BorrowingUserInfoJob userInfoJob = null;
		if (jobList != null && jobList.size() > 0) {
			userInfoJob = jobList.get(0);
		}
		if (userInfoJob != null) {
			if (userInfoJob.getCorporationScale() != null) {
				creditInfo.setCompanyScale(commonDao.findByCodeId(userInfoJob.getCorporationScale()) != null ? commonDao.findByCodeId(userInfoJob.getCorporationScale()).getCodeName() : "");
			}
			if (userInfoJob.getPresentCorporationWorkTime() != null) {
				creditInfo.setNowWorkCompanyYear(commonDao.findByCodeId(userInfoJob.getPresentCorporationWorkTime()) != null ? commonDao.findByCodeId(userInfoJob.getPresentCorporationWorkTime()).getCodeName() : "");
			}
			if (userInfoJob.getCorporationIndustry() != null) {
				creditInfo.setCompanyIndustry(commonDao.findByCodeId(userInfoJob.getCorporationIndustry()) != null ? commonDao.findByCodeId(userInfoJob.getCorporationIndustry()).getCodeName() : "");
			}
			if (userInfoJob.getJobProvince() != null && userInfoJob.getJobCity() != null) {
				workCity = commonDao.getAreaInfoById(userInfoJob.getJobProvince()).getName() + commonDao.getAreaInfoById(userInfoJob.getJobCity()).getName();
				creditInfo.setWorkCity(workCity);
			}
		}
		if (bruser.getUserInfoPerson() != null) {
			if (null != bruser.getUserInfoPerson().getSex()) {
				creditInfo.setSex(bruser.getUserInfoPerson().getSex().equals(BigDecimal.ONE) ? "男" : "女");
			}
			if (bruser.getUserInfoPerson().getBirthday() != null) {
				creditInfo.setAge(DateUtil.getAge(bruser.getUserInfoPerson().getBirthday()));
			}
			if (bruser.getUserInfoPerson().getIsapproveMarry() != null) {
				// creditInfo.setIsMarried(bruser.getUserInfoPerson().getIsapproveMarry().equals(BigDecimal.ZERO)?"未婚":"已婚");
				creditInfo.setIsMarried(commonDao.findByCodeId(bruser.getUserInfoPerson().getIsapproveMarry()) != null ? commonDao.findByCodeId(bruser.getUserInfoPerson().getIsapproveMarry()).getCodeName() : "");
			}
			creditInfo.setCarBrand(bruser.getUserInfoPerson().getCarBrand());
			if (bruser.getUserInfoPerson().getMonthIncome() != null) {
				if (bruser.getUserInfoPerson().getMonthIncome().doubleValue() < 1000) {
					monthIncomeCode = new BigDecimal(67);
				} else if (bruser.getUserInfoPerson().getMonthIncome().doubleValue() < 2000) {
					monthIncomeCode = new BigDecimal(68);
				} else if (bruser.getUserInfoPerson().getMonthIncome().doubleValue() < 3000) {
					monthIncomeCode = new BigDecimal(69);
				} else if (bruser.getUserInfoPerson().getMonthIncome().doubleValue() < 5000) {
					monthIncomeCode = new BigDecimal(70);
				} else {
					monthIncomeCode = new BigDecimal(71);
				}
				creditInfo.setWorkIncome(commonDao.findByCodeId(monthIncomeCode).getCodeName());
			}
			if (bruser.getUserInfoPerson().getGraduatSchool() != null && !"".equals(bruser.getUserInfoPerson().getGraduatSchool())) {
				creditInfo.setGraduationSchool(bruser.getUserInfoPerson().getGraduatSchool());
			}
			if (bruser.getUserInfoPerson().getMaxDegree() != null) {
				// creditInfo.setEducation(commonDao.findByCodeId(bruser.getUserInfoPerson().getMaxDegree()).getCodeName());
				creditInfo.setEducation(commonDao.findByCodeId(bruser.getUserInfoPerson().getMaxDegree()).getCodeName());
			}
			if (bruser.getUserInfoPerson().getInschoolTime() != null && !"".equals(bruser.getUserInfoPerson().getInschoolTime())) {
				creditInfo.setEntranceYear(bruser.getUserInfoPerson().getInschoolTime());
			}
			if (bruser.getUserInfoPerson().getIsapproveHaveHouse() != null) {
				creditInfo.setIsBuyHouse(bruser.getUserInfoPerson().getIsapproveHaveHouse() != BigDecimal.ZERO ? "是" : "否");
			}
			if (bruser.getUserInfoPerson().getIsapproveHaveCar() != null) {
				creditInfo.setIsBuyCar(bruser.getUserInfoPerson().getIsapproveHaveCar() != BigDecimal.ZERO ? "是" : "否");
			}

			if (bruser.getUserInfoPerson().getCarYears() != null) {
				creditInfo.setBuyCarYear(bruser.getUserInfoPerson().getCarYears());
			}
			if (bruser.getUserInfoPerson().getIsapproveHouseMortgage() != null) {
				creditInfo.setIsHasMortgage(bruser.getUserInfoPerson().getIsapproveHouseMortgage() != BigDecimal.ZERO ? "是" : "否");
			}
			if (bruser.getUserInfoPerson().getIsapproveCarMortgage() != null) {
				creditInfo.setIsHasCarLoan(bruser.getUserInfoPerson().getIsapproveCarMortgage() != BigDecimal.ZERO ? "是" : "否");
			}
			
			if (userInfoJob!= null) {
				creditInfo.setPosition(userInfoJob.getPosition()==null?"":userInfoJob.getPosition());
			}

		}
		creditInfo.setCreditGrade(bruser.getUserCreditNote() != null ? bruser.getUserCreditNote().getCreditGrade() : BigDecimal.ZERO);
		creditInfo.setCreditScoreSum(bruser.getUserCreditNote() != null ? bruser.getUserCreditNote().getCreditScoreSum() : BigDecimal.ZERO);
		creditInfo.setCreditAmount(bruser.getUserCreditNote() != null ? bruser.getUserCreditNote().getCreditAmount().doubleValue() : 0);
		creditInfo.setLaonCreditAmount(getAvailableAmountByUser(bruser.getUserId()).doubleValue());
		creditInfo.setReleaseLoanNumber(releaseLoanNumber);
		creditInfo.setSuccessLoanNumber(successLoanNumber);
		creditInfo.setPayOffLoanNumber(payOffLoanNumber);
		creditInfo.setOverdueCount(overdueCount);
		creditInfo.setSeriousOverdueCount(seriousOverdueCount);
		creditInfo.setDhAmount(ObjectFormatUtil.formatCurrency(dhAmount));
		creditInfo.setOverdueAmount(ObjectFormatUtil.formatCurrency(overdueAmount));
		creditInfo.setLoanTotal(ObjectFormatUtil.formatCurrency(loanTotal));
		creditInfo.setLendTotalAmount(ObjectFormatUtil.formatCurrency(lendTotalAmount));
		creditInfo.setWaitRecoverAmount(ObjectFormatUtil.formatCurrency(waitRecoverAmount));
		return creditInfo;
	}

	// 投标记录
	public CurrentInvestInfoVO getCurrInvestInfo(BigDecimal loanId) {
		CurrentInvestInfoVO currInvestInfo = new CurrentInvestInfoVO();
		double currentBidAmount = 0;
		double surplusAmount = 0;
		List<BorrowingInvestInfo> investInfoList = new ArrayList<BorrowingInvestInfo>();
		BorrowingLoanInfo InvestloanInfo = releaseLoanDao.findByLoanId((loanId));
		if (InvestloanInfo != null) {
			investInfoList = InvestloanInfo.getInvestInfoList();
		}
		for (BorrowingInvestInfo brInvestInfo : investInfoList) {
			currentBidAmount += brInvestInfo.getInvestAmount();
		}
		if (InvestloanInfo != null) {
			surplusAmount = ArithUtil.sub(InvestloanInfo.getLoanAmount(), currentBidAmount);
		}
		BorrowingLoanInfo loanInfo = releaseLoanDao.findByLoanId(loanId);
		currInvestInfo.setCurrentBidAmount(ObjectFormatUtil.formatCurrency(currentBidAmount));
		currInvestInfo.setSurplusAmount(ObjectFormatUtil.formatCurrency(surplusAmount));
		Date startInvestTime = loanInfo.getStartInvestTime() != null ? loanInfo.getStartInvestTime() : DateUtil.getSevenDaysDate(DateUtil.getCurrentDate());
		currInvestInfo.setSurplusTime(DateUtil.show(DateUtil.getCurrentDate(), startInvestTime));
		currInvestInfo.setInvestInfoList(investInfoList);
		return currInvestInfo;
	}

	// 用户认证信息
	public BorrowingCreditRecordVO getUserCreditRecord(BigDecimal userId) {
		BorrowingCreditRecordVO brCreditRecord = new BorrowingCreditRecordVO();
		brCreditRecord.setUserApproveList(releaseLoanDao.getApproveList(userId));
		return brCreditRecord;
	}

	// 借款详情介绍
	public String getLoanInfoDescription(BigDecimal loanId) {
		BorrowingLoanInfo InvestloanInfo = new BorrowingLoanInfo();
		// if (currLoanInfo != null && currLoanInfo.getLoanId() != null) {
		// InvestloanInfo =
		// releaseLoanDao.findByLoanId((currLoanInfo.getLoanId()));
		// }
		InvestloanInfo = releaseLoanDao.findByLoanId(loanId);
		return InvestloanInfo.getDescription();
	}

	// 查找借款用户
	public BorrowingUsers findBorrowUser(BigDecimal loanUserId) {
		BorrowingUsers borrowUser = borrowingUserDao.findByUserId(loanUserId);
		return borrowUser;
	}

	// // 留言板
	// public List<StationMessageStationLetter> findMsg(BigDecimal loanId) {
	// List<StationMessageStationLetter> msgList =
	// stationMessageStationLetterDao
	// .findByIsDelOrderByMsgPath(loanId);
	// return msgList;
	// }
	//
	// //对标进行留言
	// @Transactional(readOnly = false)
	// public void leaveMsg(BigDecimal userId,String message,BigDecimal loanId,
	// BigDecimal receiverId){
	// StationMessageStationLetter smsl = new StationMessageStationLetter();
	// smsl.setMsgKind("2");
	// StationMessageUsers user = new StationMessageUsers();
	// user.setUserId(userId);
	// smsl.setSenderId(user);
	//
	// StationMessageUsers user2 = new StationMessageUsers();
	// user2.setUserId(receiverId);
	// smsl.setReceiverId(user2);
	// smsl.setParentId(BigDecimal.ZERO);
	// smsl.setChildId(smsl.getLetterId());
	// smsl.setLoanId(loanId);
	// smsl.setMessage(message);
	// smsl.setIsDel("0");
	// Date date = new Date();
	// smsl.setSenderTime(new Timestamp(date.getTime()));
	// stationMessageStationLetterDao.save(smsl);
	// }
	//
	// 检查用户权限
	public boolean checkPower(BigDecimal userId, BigDecimal loanId) {
		BigDecimal loadUserId = releaseLoanDao.findByLoanId(loanId).getUser().getUserId();
		if (userId.equals(loadUserId)) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = false)
	public void sendMessage(String messageContext, BigDecimal loanUserId, BigDecimal userId) {
		StationMessageStationLetter smsl = new StationMessageStationLetter();
		smsl.setMsgKind("1");
		StationMessageUsers user = stationMessageUserDao.findOne(userId);
		smsl.setSenderId(user);

		StationMessageUsers user2 = stationMessageUserDao.findOne(loanUserId);
		smsl.setReceiverId(user2);
		smsl.setParentId(BigDecimal.ZERO);
		smsl.setMessage(messageContext);
		smsl.setIsDel("0");
		smsl.setSenderDelStatus("0");
		smsl.setReceiverDelStatus("0");
		Date date = new Date();
		smsl.setSenderTime(new Timestamp(date.getTime()));
		logger.info("SAVE: STATION_LETTER || kind=1 isdel=0 SenderDelStatus=0 ReceiverDelStatus=0 ParentId=0 userId=" + userId);
		stationMessageStationLetterDao.save(smsl);

	}

}
