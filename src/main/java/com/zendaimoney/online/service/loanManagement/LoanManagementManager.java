package com.zendaimoney.online.service.loanManagement;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.entity.loan.AcTVirtualCashFlowAdmin;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.ConstSubject;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.dao.homepage.HomepageUserMovementDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementAcTLedgerDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementInvestInfoDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementOverdueClaimsDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementUserInfoPersonDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementAcTCustomerDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementAcTFlowDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementLoanDao;
import com.zendaimoney.online.dao.personal.PersonalUserMessageSetDao;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.entity.financial.FinancialSysMsg;
import com.zendaimoney.online.entity.homepage.HomepageMovementWord;
import com.zendaimoney.online.entity.homepage.HomepageUserMovement;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTCustomer;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedger;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedgerLoan;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTVirtualCashFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementInvestInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagementLoanInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagementUserInfoPerson;
import com.zendaimoney.online.entity.loanManagement.LoanManagementUsers;
import com.zendaimoney.online.entity.loanManagement.LoanManagentOverdueClaims;
import com.zendaimoney.online.entity.loanManagement.LoanmanagementUserCreditNote;
import com.zendaimoney.online.oii.sms.SMSSender;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.vo.loanManagement.AheadRepayLoanDetailListVO;
import com.zendaimoney.online.vo.loanManagement.LoanInfoDetailVO;
import com.zendaimoney.online.vo.loanManagement.LoanInfoListVO;
import com.zendaimoney.online.vo.loanManagement.LoanStatisticsVO;
import com.zendaimoney.online.vo.loanManagement.RepayLoanDetailListVO;
import com.zendaimoney.online.vo.loanManagement.RepayLoanDetailVO;
import com.zendaimoney.online.vo.loanManagement.RepayOffLoanInfoListVO;
import com.zendaimoney.online.vo.loanManagement.RepayOffLoanInfoVO;
import com.zendaimoney.online.vo.loanManagement.RepaymentLoanInfoDetailVO;
import com.zendaimoney.online.vo.loanManagement.RepaymentLoanInfoListVO;
import com.zendaimoney.online.vo.loanManagement.SendMessageToInvestVO;

/**
 * 借款管理相关实体的管理类
 * 
 * @author yijc
 */
// Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class LoanManagementManager {

	private static Logger logger = LoggerFactory.getLogger(LoanManagementManager.class);
	@Autowired
	private LoanmanagementLoanDao loanManagementDao;
	@Autowired
	private LoanmanagementAcTFlowDao loanManagementAcTFlowDao;
	@Autowired
	private LoanManagementAcTLedgerDao loanManagementActLegerDao;
//	@Autowired
//	private LoanManagementTransactionLogDao loanManagementTransactionLogDao;
//	@Autowired
//	private LoanManagementWebOperateLogDao loanManagementWebOperateLogDao;
//	@Autowired
//	private LoanmanagementLoanInfoDao loanInfoDao;
	@Autowired
	private LoanManagementInvestInfoDao investInfoDao;
	@Autowired
	private LoanmanagementAcTCustomerDao loanmanagementAcTCustomerDao;
//	@Autowired
//	private LoanManagementAcTLedgerLoanDao loanManagementAcTLedgerLoanDao;
	@Autowired
	private LoanManagementUserInfoPersonDao loanManagementUserInfoPersonDao;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private RateCommonUtil rateCommonUtil;
	@Autowired
	PersonalUserMessageSetDao messageSetDao;
	@Autowired
	MimeMailService mimeMailService;
	@Autowired
	FinancialSysMsgDao sysMsgDao;
	@Autowired
	HomepageUserMovementDao movementDao;
	@Autowired
	private LoanManagementOverdueClaimsDao loanManagementOverdueClaimsDao;
//	@Autowired
//	private RepaySupporManager repaySupporManager;
//	@Autowired
//	private MessageUtils messageUtils;
//	@Autowired
//	private PayBackUtils payBackUtils;

	// 从session中取当前登录用户的userId
	public LoanManagementUsers getUsersBySession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal currUserIdStr = (BigDecimal) session.getAttribute("curr_login_user_id");
		return loanManagementDao.findByUserId(currUserIdStr);
	}

	public LoanStatisticsVO getLoanStatisticsInfo(HttpServletRequest req) {
		// 总借款额
		double loanTotle = 0;
		// 发布借款笔数
		int releaseLoanNumber = 0;
		// 已还本息
		double repayOffPrincipalInterestTotal = 0;
		// 成功借款笔数
		int successLoanNumber = 0;
		// 待还本息
		double waitRepayPrincipalInterestTotal = 0;
		// 正常还清笔数
		int normalPayOffNumber = 0;
		// 提前还清笔数
		int earlyRepayOffNumber = 0;
		// 待还管理费
		double waitRepayManagementFee = 0;
		// 未还清笔数
		int notRepayOffNumber = 0;
		// 逾期次数
		int overdueCount = 0;
		// 严重逾期次数
		int seriousOverdueCount = 0;
		// 加权平均借款利率
		double weightedAverageLoanRate = 0;
		// 借款总金额
		double loanTotalAmount = 0;
		// 借款金额*年利率
		double loanAmount_yearRate = 0;
		// 平均每笔借款金额
		double averageEachLoanAmount = 0;
		// 各种费率信息,取自费率表
		//Rate rate = commonDao.getRate(1l);
		// 月管理费比例
		//double rateManagementFeeMonth = rate.getMgmtFee().doubleValue();
		HttpSession session = req.getSession();
		BigDecimal currUserIdStr = new BigDecimal(session.getAttribute("curr_login_user_id").toString());
		LoanManagementUsers currUser = loanManagementDao.findByUserId(currUserIdStr);
		List<LoanManagementLoanInfo> loanInfoes = currUser.getLoanInfoes();
		for (LoanManagementLoanInfo currLoanInfo : loanInfoes) {
			LoanRateVO rate = rateCommonUtil.getLoanCoRate(currLoanInfo.getLoanId().longValue());
			// 虚拟现金流水表
			LoanManagementAcTLedgerLoan ledgerLoan = currLoanInfo.getActLedgerLoan();
			loanTotalAmount = ArithUtil.add(loanTotalAmount, currLoanInfo.getLoanAmount());
			List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList = loanManagementDao.getRepayLoanDetailByLoanId(ledgerLoan);
			for (LoanManagementAcTVirtualCashFlow actvCashFlow : actvirturalCashFlowList) {
				// 还款日不为空，则当前期为还款,否则未还款
				if (actvCashFlow.getRepayDay() != null) {
					repayOffPrincipalInterestTotal += ArithUtil.add(actvCashFlow.getPrincipalAmt(), actvCashFlow.getInterestAmt());
				} else {
					waitRepayPrincipalInterestTotal += ArithUtil.add(actvCashFlow.getPrincipalAmt(), actvCashFlow.getInterestAmt());
					waitRepayManagementFee += ArithUtil.mul(currLoanInfo.getLoanAmount(), rate.getMgmtFee().doubleValue());
				}
				// 虚拟现金流水表对应的还款计划期数小于借贷期限,则提前还款
				if (actvirturalCashFlowList.size() < currLoanInfo.getLoanDuration().intValue()) {
					earlyRepayOffNumber += 1;
				}
			}
			if (currLoanInfo.getStatus().doubleValue() == 4 || currLoanInfo.getStatus().doubleValue() == 5) {
				loanTotle += currLoanInfo.getLoanAmount();
				successLoanNumber += 1;
				if (currLoanInfo.getStatus().doubleValue() == 5) {
					normalPayOffNumber += 1;
				}
			}
			if (currLoanInfo.getReleaseStatus() == BigDecimal.ONE) {
				releaseLoanNumber += 1;
				// 加权平均借款利率
				weightedAverageLoanRate = loanTotalAmount != 0 ? ArithUtil.div(loanAmount_yearRate, loanTotalAmount) : 0;
			}
			loanAmount_yearRate += ArithUtil.mul(currLoanInfo.getLoanAmount(), currLoanInfo.getYearRate());
		}
		averageEachLoanAmount = successLoanNumber != 0 ? ArithUtil.div(loanTotalAmount, successLoanNumber) : 0;
		LoanStatisticsVO loanStatisticsVO = new LoanStatisticsVO();
		loanStatisticsVO.setLoanTotle(ObjectFormatUtil.formatCurrency(loanTotle));
		loanStatisticsVO.setRepayOffPrincipalInterestTotal(ObjectFormatUtil.formatCurrency(repayOffPrincipalInterestTotal));
		loanStatisticsVO.setWaitRepayPrincipalInterestTotal(ObjectFormatUtil.formatCurrency(waitRepayPrincipalInterestTotal));
		loanStatisticsVO.setEarlyRepayOffNumber(earlyRepayOffNumber);
		loanStatisticsVO.setWaitRepayManagementFee(ObjectFormatUtil.formatCurrency(waitRepayManagementFee));
		loanStatisticsVO.setOverdueCount(overdueCount);
		loanStatisticsVO.setSeriousOverdueCount(seriousOverdueCount);
		loanStatisticsVO.setNotRepayOffNumber(notRepayOffNumber);
		loanStatisticsVO.setSuccessLoanNumber(successLoanNumber);
		loanStatisticsVO.setNormalPayOffNumber(normalPayOffNumber);
		loanStatisticsVO.setWeightedAverageLoanRate(ObjectFormatUtil.formatPercent(weightedAverageLoanRate, "#,##0.00%"));
		loanStatisticsVO.setAverageEachLoanAmount(ObjectFormatUtil.formatCurrency(averageEachLoanAmount));
		LoanmanagementUserCreditNote loanmanagementUserCreditNote = loanManagementDao.getLoanmanagementUsercreditNote(currUserIdStr);
		if (loanmanagementUserCreditNote != null) {
			overdueCount = loanmanagementUserCreditNote.getOverdueCount() != null ? loanmanagementUserCreditNote.getOverdueCount().intValue() : 0;
			seriousOverdueCount = loanmanagementUserCreditNote.getOverdueCount() != null ? loanmanagementUserCreditNote.getSeriousOverdueCount().intValue() : 0;
		}
		loanStatisticsVO.setOverdueCount(seriousOverdueCount);
		loanStatisticsVO.setSeriousOverdueCount(seriousOverdueCount);
		return loanStatisticsVO;
	}

	// 获取还款列表
	public RepaymentLoanInfoListVO getLoanInfoList(HttpServletRequest req) {
		LoanManagementUsers currUser = getUsersBySession(req);
		RepaymentLoanInfoListVO repaymentLoanInfoList = new RepaymentLoanInfoListVO();
		List<LoanManagementLoanInfo> loanInfoList = loanManagementDao.getLoanInfoList(currUser);

//		// 各种费率信息,取自费率表
//		Rate rate = commonDao.getRate(1l);
//		// 月管理费比例
//		double rateManagementFeeMonth = rate.getMgmtFee().doubleValue();
//		// 罚息比例
//		double overdueRate = rate.getOverdueInterest().doubleValue();
//		// 逾期违约金比例
//		double overdueFinesRate = rate.getOverdueFines().doubleValue();
		int overdueDays = 0;
		Date nextPaymentday = DateUtil.getCurrentDate();
		// 逾期罚息
		double currentOverdueInterest = 0;
		// 剩余未还期数
		int SurplusTerm = 0;
		// 逾期违约金
		double currentOverdueFines = 0;
		List<RepaymentLoanInfoDetailVO> repaymentLoanInfoDetailList = new ArrayList<RepaymentLoanInfoDetailVO>();
		for (LoanManagementLoanInfo loanInfo : loanInfoList) {
			LoanRateVO rate = rateCommonUtil.getLoanCoRate(loanInfo.getLoanId().longValue());
			RepaymentLoanInfoDetailVO repaymentLoanInfoDetail = new RepaymentLoanInfoDetailVO();

			LoanManagementAcTLedgerLoan currAcTLedgerLoan = loanManagementDao.getRepayLoanDetailByLedgerLoanId(loanInfo.getActLedgerLoan().getId()).get(0);
			// 剩余未还期数
			SurplusTerm = currAcTLedgerLoan.getTotalNum().intValue() - currAcTLedgerLoan.getCurrNum().intValue() + 1;
			Date currMaturityDate = currAcTLedgerLoan.getNextExpiry();
			// 逾期天数
			overdueDays = FormulaSupportUtil.getOverdueDays(currMaturityDate, nextPaymentday);
			repaymentLoanInfoDetail.setOverdueDays(overdueDays);
			// 还款信息设定
			// 借贷id
			repaymentLoanInfoDetail.setLoanId(loanInfo.getLoanId());
			repaymentLoanInfoDetail.setLoanTitle(loanInfo.getLoanTitle());
			// 借款金额
			repaymentLoanInfoDetail.setLoanAmount(ObjectFormatUtil.formatCurrency(loanInfo.getLoanAmount()));
			// 年利率
			repaymentLoanInfoDetail.setYearRate(ObjectFormatUtil.formatPercent(loanInfo.getYearRate(), "#,###.00%"));
			// 月还款额
			repaymentLoanInfoDetail.setRepaymentAmountByMonth(ObjectFormatUtil.formatCurrency(loanInfo.getMonthReturnPrincipalandinter()));
			// 期限
			repaymentLoanInfoDetail.setLoanDuration(loanInfo.getLoanDuration() + "个月");
			// 计算月缴纳管理费
			repaymentLoanInfoDetail.setManagementFeeByMonth(ObjectFormatUtil.formatCurrency(ArithUtil.mul(loanInfo.getLoanAmount(), rate.getMgmtFee().doubleValue())));
			// 逾期罚息
			if (overdueDays <= 30)
				currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDays, SurplusTerm, loanInfo.getMonthReturnPrincipalandinter(), rate.getOverdueInterest().doubleValue());
			if (overdueDays > 30)
				currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDays, SurplusTerm, loanInfo.getMonthReturnPrincipalandinter(), rate.getOverdueSeriousInterest().doubleValue());
			repaymentLoanInfoDetail.setOverdueInterest(ObjectFormatUtil.formatCurrency(currentOverdueInterest));
			// 逾期违约金
			currentOverdueFines = FormulaSupportUtil.calOverdueInterest(overdueDays, SurplusTerm, loanInfo.getMonthManageCost(), rate.getOverdueFines().doubleValue());
			repaymentLoanInfoDetail.setOverdueFines(ObjectFormatUtil.formatCurrency(currentOverdueFines));
			// 下一还款日
			repaymentLoanInfoDetail.setNextPaymentDate(DateUtil.getYMDTime(currAcTLedgerLoan.getNextExpiry()));
			repaymentLoanInfoDetailList.add(repaymentLoanInfoDetail);
		}
		repaymentLoanInfoList.setRepayLoanInfoList(repaymentLoanInfoDetailList);
		return repaymentLoanInfoList;
	}

	// 获取已还清借款列表
	public RepayOffLoanInfoListVO getRepayOffLoanDetail(HttpServletRequest req) {
		LoanManagementUsers currUser = getUsersBySession(req);
		List<LoanManagementLoanInfo> loanInfoList = loanManagementDao.getRepayOffLoanInfoList(currUser);
		List<RepayOffLoanInfoVO> repayOffLoanInfoList = new ArrayList<RepayOffLoanInfoVO>();
		RepayOffLoanInfoListVO loanInfoVO = new RepayOffLoanInfoListVO();
		// 已还金额
		for (LoanManagementLoanInfo loanInfo : loanInfoList) {
			double repayOffTotalAmount = 0;
			// 虚拟现金流水表
			LoanManagementAcTLedgerLoan ledgerLoan = loanInfo.getActLedgerLoan();
			List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList = loanManagementDao.getRepayLoanDetailByLoanId(ledgerLoan);
			for (LoanManagementAcTVirtualCashFlow actvCashFlow : actvirturalCashFlowList) {
				repayOffTotalAmount += (actvCashFlow.getAmt() == null ? 0 : actvCashFlow.getAmt());
			}
			RepayOffLoanInfoVO repayOffLoanInfo = new RepayOffLoanInfoVO();
			repayOffLoanInfo.setLoanId(loanInfo.getLoanId().toString());
			repayOffLoanInfo.setLoanTitle(loanInfo.getLoanTitle());
			repayOffLoanInfo.setLoanAmount(ObjectFormatUtil.formatCurrency(loanInfo.getLoanAmount()));
			repayOffLoanInfo.setLoanDuration(loanInfo.getLoanDuration() + "个月");
			repayOffLoanInfo.setYearRate(ObjectFormatUtil.formatPercent(loanInfo.getYearRate(), "#,###.00%"));
			repayOffLoanInfo.setWaitRepayBalanceAmount(ObjectFormatUtil.formatCurrency(0));
			repayOffLoanInfo.setRepayOffLoanAmount(ObjectFormatUtil.formatCurrency(repayOffTotalAmount));
			repayOffLoanInfo.setRepaymentStatus(loanInfo.getStatus());
			repayOffLoanInfoList.add(repayOffLoanInfo);
		}
		loanInfoVO.setRepayOffLoanInfoList(repayOffLoanInfoList);
		return loanInfoVO;
	}

	// 获取借款列表
	public LoanInfoListVO getMyLoanInfoList(HttpServletRequest req) {
		LoanManagementUsers currUser = getUsersBySession(req);
		LoanInfoListVO loanInfoVO = new LoanInfoListVO();
		List<LoanManagementLoanInfo> list = loanManagementDao.getLoanInfoListByUser(currUser);
		List<LoanInfoDetailVO> loanInfoList = new ArrayList<LoanInfoDetailVO>();
		for (LoanManagementLoanInfo loanInfo : list) {
			LoanInfoDetailVO vo = new LoanInfoDetailVO();
			double speedProgress = 0;
			try {
				PropertyUtils.copyProperties(vo, loanInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (LoanManagementInvestInfo brInvestInfo : loanInfo.getInvestInfoList()) {
				speedProgress += brInvestInfo.getHavaScale();
			}
			vo.setReleaseDateStr(DateUtil.getYMDTime(loanInfo.getReleaseTime()));
			vo.setReleaseTimeStr(DateUtil.getHMSTime(loanInfo.getReleaseTime()));
			vo.setSpeedProgress(ObjectFormatUtil.formatPercent(speedProgress, "###0.00%"));
			vo.setAmount(ObjectFormatUtil.formatCurrency(loanInfo.getLoanAmount()));
			vo.setRate(ObjectFormatUtil.formatPercent(loanInfo.getYearRate(), "##,#0.00%"));
			vo.setBidNumber(loanInfo.getInvestInfoList().size());
			loanInfoList.add(vo);
		}
		loanInfoVO.setLoanInfoList(loanInfoList);
		return loanInfoVO;
	}

	// 获取当前期还款信息(根据loanId)
	public RepayLoanDetailListVO getCurrentRepayLoanDetail(HttpServletRequest req, String loanId) {
		LoanManagementUsers currUser = getUsersBySession(req);
		// 借款信息
		LoanManagementLoanInfo currRepayLoanInfo = loanManagementDao.getLoanInfoByLoanId(new BigDecimal(loanId), currUser).get(0);
		// 借款分户信息表id
		LoanManagementAcTLedgerLoan ledgerLoan = currRepayLoanInfo.getActLedgerLoan();
		// 贷款分户信息表的当前还款期数
		Short currentRepayLoanPeriodNumber = ledgerLoan.getCurrNum().shortValue();
		// 虚拟现金流水表
		List<AcTVirtualCashFlowAdmin> actvirturalCashFlowList = loanManagementDao.getRepayLoanDetailByLoanId2(new BigDecimal(ledgerLoan.getId()));
		// 还款计划表当期还款状态
		String actVFlag = "";
		// 逾期天数
		int overdueDay = 0;
		// 本期共计还款
		double currentPaymentTotal = 0;
		// 逾期罚息
		double currentOverdueInterest = 0;
		// 剩余未还期数
		int term = 0;
		// 月还本息
		double principanInterestMonth = 0;
		// 逾期违约金
		double currentOverdueFines = 0;
		// 各种费率信息,取自费率表
		LoanRateVO rate = rateCommonUtil.getLoanCoRate(Long.valueOf(loanId));
		// 月管理费比例
		double rateManagementFeeMonth = rate.getMgmtFee().doubleValue();
		// 罚息比例
		double overdueRate = rate.getOverdueInterest().doubleValue();
		// 高级逾期罚息比例
		double highOverdueRate = rate.getOverdueSeriousInterest().doubleValue();
		// 逾期违约金比例
		double overdueFinesRate = rate.getOverdueFines().doubleValue();
		// 管理费
		double currentManagementFeeByMonth = 0;
		RepayLoanDetailListVO repayLoanInfoListVO = new RepayLoanDetailListVO();
		List<RepayLoanDetailVO> repayLoanDetailList = new ArrayList<RepayLoanDetailVO>();
		for (AcTVirtualCashFlowAdmin actvCashFlow : actvirturalCashFlowList) {
			actvCashFlow.setRate(rate);
			// 当前时间
			Date currentDate = new Timestamp(System.currentTimeMillis());
			RepayLoanDetailVO repayLoanInfoDetail = new RepayLoanDetailVO();
			repayLoanInfoDetail.setRate(rate);
			// 剩余未还期数
			term = ledgerLoan.getTotalNum().shortValue() - actvCashFlow.getCurrNum() + 1;
			String editDate = "";
			if (actvCashFlow.getEditDate() != null) {
				editDate = DateUtil.getYMDTime(actvCashFlow.getEditDate());
			}
			// 状态
			if (currentRepayLoanPeriodNumber == actvCashFlow.getCurrNum()) {
				// 逾期天数
				repayLoanInfoDetail.setOverdueDays(actvCashFlow.getPastDueDays());
				// 还款日期
				// repayLoanInfoDetail.setRepayLoanDate(FormulaSupportUtil.getRepayDate(actvCashFlow.getRepayDay(),
				// actvCashFlow.getEditDate()));
				repayLoanInfoDetail.setRepayActDate(editDate);
				repayLoanInfoDetail.setRepayPlanDate(FormulaSupportUtil.getRepayDate(actvCashFlow.getRepayDay(), actvCashFlow.getEditDate()));
				// 逾期天数
				overdueDay = FormulaSupportUtil.getOverdueDays(actvCashFlow.getRepayDay(), currentDate);
				actVFlag = "wait";
			} else if (currentRepayLoanPeriodNumber > actvCashFlow.getCurrNum()) {
				// 逾期天数
				overdueDay = FormulaSupportUtil.getOverdueDays(actvCashFlow.getRepayDay(), actvCashFlow.getEditDate());
				// 还款日期
				// repayLoanInfoDetail.setRepayLoanDate(DateUtil.getYMDTime(actvCashFlow.getEditDate()));
				repayLoanInfoDetail.setRepayActDate(DateUtil.getYMDTime(actvCashFlow.getEditDate()));
				repayLoanInfoDetail.setRepayPlanDate(DateUtil.getYMDTime(actvCashFlow.getRepayDay()));
				actVFlag = "yes";
			} else {
				// 还款日期
				// repayLoanInfoDetail.setRepayLoanDate(DateUtil.getYMDTime(actvCashFlow.getRepayDay()));

				repayLoanInfoDetail.setRepayActDate(editDate);
				repayLoanInfoDetail.setRepayPlanDate(DateUtil.getYMDTime(actvCashFlow.getRepayDay()));
				// 逾期天数
				overdueDay = 0;
				actVFlag = "no";
			}
			repayLoanInfoDetail.setOverdueDays(actvCashFlow.getPastDueDays());

			// 管理费
			currentManagementFeeByMonth = FormulaSupportUtil.getManagementFeeEveryMonth(currRepayLoanInfo.getLoanAmount(), rateManagementFeeMonth);
			repayLoanInfoDetail.setManagementFeeByMonth(ObjectFormatUtil.formatCurrency(currentManagementFeeByMonth));

			// 逾期罚息
			overdueDay = FormulaSupportUtil.getOverdueDays(actvCashFlow.getRepayDay(), currentDate);
			if (overdueDay > 30) {
				currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDay, term, ArithUtil.add(actvCashFlow.getPrincipalAmt(), actvCashFlow.getInterestAmt()), highOverdueRate);
			} else {
				currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDay, term, ArithUtil.add(actvCashFlow.getPrincipalAmt(), actvCashFlow.getInterestAmt()), overdueRate);
			}
			repayLoanInfoDetail.setOverdueInterest(actvCashFlow.getOverduePenaltyInterest());

			// 逾期违约金
			currentOverdueFines = FormulaSupportUtil.calOverdueInterest(overdueDay, term, currentManagementFeeByMonth, overdueFinesRate);
			repayLoanInfoDetail.setOverdueFines(actvCashFlow.getOverdueBreachPenalty());

			// 月还本息
			principanInterestMonth = ArithUtil.add(actvCashFlow.getPrincipalAmt(), actvCashFlow.getInterestAmt());
			repayLoanInfoDetail.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(principanInterestMonth));

			// 本期应还金额
			if (currentRepayLoanPeriodNumber == actvCashFlow.getCurrNum()) {
				currentPaymentTotal = FormulaSupportUtil.currentTermShouldPayAmount(currentManagementFeeByMonth, principanInterestMonth, currentOverdueInterest, currentOverdueFines);
			}

			// 还款状态
			repayLoanInfoDetail.setRepayLoanStatus(actVFlag);

			repayLoanDetailList.add(repayLoanInfoDetail);
		}
		// 还款记录
		repayLoanInfoListVO.setRepayLoanDetailList(repayLoanDetailList);
		// 本期共计还款
		repayLoanInfoListVO.setCurrentPaymentTotalDouble(currentPaymentTotal);
		repayLoanInfoListVO.setCurrentPaymentTotal(ObjectFormatUtil.formatCurrency(currentPaymentTotal));
		// 当前借款信息表id
		repayLoanInfoListVO.setCurrentloanId(new BigDecimal(loanId));
		// 当前待还还款期数
		repayLoanInfoListVO.setCurrentTermLoan(currentRepayLoanPeriodNumber);
		LoanManagementAcTCustomer actCustomerLoan = loanmanagementAcTCustomerDao.findById(currUser.gettCustomerId());
		LoanManagementAcTLedger payPrincipalActledger1 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerLoan.getTotalAcct() + "%");
		// 我的余额(可用余额)
		repayLoanInfoListVO.setMyAvailableBalance(ObjectFormatUtil.formatCurrency(payPrincipalActledger1.getAmount()));
		repayLoanInfoListVO.setCurrentTermLoanTotal(currRepayLoanInfo.getLoanDuration().intValue());

		return repayLoanInfoListVO;
	}

	// 获取当前期一次性提前还款信息(根据loanId)
	public AheadRepayLoanDetailListVO getCurrentInRepayLoanDetail(HttpServletRequest req, String loanId) {
		LoanManagementUsers currUser = getUsersBySession(req);
		LoanManagementLoanInfo currRepayLoanInfo = loanManagementDao.getLoanInfoByLoanId(new BigDecimal(loanId), currUser).get(0);
		// 借款分户信息表id
		Long ledgerLoanId = currRepayLoanInfo.getActLedgerLoan().getId();
		LoanManagementAcTLedgerLoan currAcTLedgerLoan = loanManagementDao.getRepayLoanDetailByLedgerLoanId(ledgerLoanId).get(0);

		// 贷款分户信息表的当前还款期数
		Short currentRepayLoanPeriodNumber = currAcTLedgerLoan.getCurrNum().shortValue();
		// 虚拟现金流水表
		List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList = loanManagementDao.getRepayLoanDetailByLoanId(currRepayLoanInfo.getActLedgerLoan());
		// 逾期天数
		int overdueDay = 0;
		// 剩余本金
		double surplusPrincipal = 0;
		// 一次性还款共计还款
		double currentPaymentCompleteTotal = 0;
		// 已还本息
		double payOffprincipalInterest = 0;
		// 逾期罚息
		double currentOverdueInterest = 0;
		// 剩余未还期数
		int term = 0;
		// 月还本息
		double principanInterestMonth = 0;
		// 当前待还月还本息
		double currentPrincipanInterestMonth = 0;
		// 逾期违约金
		double currentOverdueFines = 0;
		// 一次性提前还款违约金
		double overdueFinesComplateTotal = 0;
		// 各种费率信息,取自费率表
		LoanRateVO rate = rateCommonUtil.getLoanCoRate(Long.valueOf(loanId));
		// 月管理费比例
		double rateManagementFeeMonth = rate.getMgmtFee().doubleValue();
		// 罚息比例
		double overdueRate = rate.getOverdueInterest().doubleValue();
		// 逾期违约金比例
		double overdueFinesRate = rate.getOverdueFines().doubleValue();
		// 一次性提前还款违约金费率
		double overdueFinesComplateRate = rate.getEarlyFines().doubleValue();
		// 管理费
		double currentManagementFeeByMonth = 0;
		List<RepayLoanDetailVO> repayLoanDetailList = new ArrayList<RepayLoanDetailVO>();
		// 状态
		for (LoanManagementAcTVirtualCashFlow actvCashFlow : actvirturalCashFlowList) {
			// 还款计划表当期还款状态
			String actVFlag = "";
			// 当前时间
			Date currentDate = new Timestamp(System.currentTimeMillis());
			RepayLoanDetailVO repayLoanInfoDetail = new RepayLoanDetailVO();
			// 月还本息
			principanInterestMonth = ArithUtil.add(actvCashFlow.getPrincipalAmt(), actvCashFlow.getInterestAmt());
			// 剩余未还期数
			term = currAcTLedgerLoan.getTotalNum().shortValue() - actvCashFlow.getCurrNum() + 1;

			if (currentRepayLoanPeriodNumber == actvCashFlow.getCurrNum()) {
				// 逾期记录
				repayLoanInfoDetail.setManagementFeeByMonth(ObjectFormatUtil.formatCurrency(currentManagementFeeByMonth));
				// 逾期天数
				repayLoanInfoDetail.setOverdueDays(FormulaSupportUtil.getOverdueDays(actvCashFlow.getRepayDay(), currentDate));
				// 还款日期
				repayLoanInfoDetail.setRepayLoanDate(FormulaSupportUtil.getRepayDate(actvCashFlow.getRepayDay(), actvCashFlow.getEditDate()));
				// 逾期天数
				overdueDay = FormulaSupportUtil.getOverdueDays(actvCashFlow.getRepayDay(), currentDate);
				// 逾期违约金
				repayLoanInfoDetail.setOverdueFines(ObjectFormatUtil.formatCurrency(0));
				// 2012-12-10 剩余本金只要统计未还款的记录，已经还款的不需要累加

				if (currAcTLedgerLoan.getLastExpiry() != null && DateUtil.getDateyyyyMMdd().compareTo(currAcTLedgerLoan.getNextExpiry()) <= 0) {
					// 当前日期等于下一次
					Date previousDate = DateUtil.getPreviousMonthDateyyyyMMdd(currAcTLedgerLoan.getNextExpiry());
					if (DateUtil.getDateyyyyMMdd().compareTo(previousDate) <= 0) {
						surplusPrincipal += actvCashFlow.getPrincipalAmt();
					} else {
						repayLoanInfoDetail.setManagementFeeByMonth(actvCashFlow.getMonthManageCost());
					}
				}

				actVFlag = "wait";
			} else if (currentRepayLoanPeriodNumber > actvCashFlow.getCurrNum()) {
				// 逾期违约金
				currentOverdueFines = actvCashFlow.getOverDueFineAmount() == null ? 0 : actvCashFlow.getOverDueFineAmount();
				// 逾期违约金
				repayLoanInfoDetail.setOverdueFines(ObjectFormatUtil.formatCurrency(currentOverdueFines));
				// 月缴管理费
				repayLoanInfoDetail.setManagementFeeByMonth(actvCashFlow.getMonthManageCost());
				// 逾期天数
				overdueDay = FormulaSupportUtil.getOverdueDays(actvCashFlow.getRepayDay(), actvCashFlow.getEditDate());
				// 还款日期
				repayLoanInfoDetail.setRepayLoanDate(DateUtil.getYMDTime(actvCashFlow.getEditDate()));
				// 已还本息
				payOffprincipalInterest += principanInterestMonth;
				actVFlag = "yes";
			} else {
				// 未还款的记录(不包括本期)
				repayLoanInfoDetail.setManagementFeeByMonth(ObjectFormatUtil.formatCurrency(0));
				// 剩余本金
				surplusPrincipal += actvCashFlow.getPrincipalAmt();
				// 还款日期
				repayLoanInfoDetail.setRepayLoanDate(DateUtil.getYMDTime(actvCashFlow.getRepayDay()));
				// 逾期违约金
				repayLoanInfoDetail.setOverdueFines(ObjectFormatUtil.formatCurrency(0));
				// 逾期天数
				overdueDay = 0;
				actVFlag = "no";
			}
			repayLoanInfoDetail.setOverdueDays(overdueDay);
			// repayLoanInfoDetail.setManagementFeeByMonth(ObjectFormatUtil.formatCurrency(currentManagementFeeByMonth));
			// 逾期罚息
			currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDay, term, ArithUtil.add(actvCashFlow.getPrincipalAmt(), actvCashFlow.getInterestAmt()), overdueRate);
			repayLoanInfoDetail.setOverdueInterest(ObjectFormatUtil.formatCurrency(currentOverdueInterest));
			// 逾期违约金
			// currentOverdueFines =
			// FormulaSupportUtil.calOverdueInterest(overdueDay, term,
			// currentManagementFeeByMonth, overdueFinesRate);
			// repayLoanInfoDetail.setOverdueFines(ObjectFormatUtil.formatCurrency(currentOverdueFines));
			repayLoanInfoDetail.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(principanInterestMonth));
			// 一次性还款应还金额
			if (currentRepayLoanPeriodNumber == actvCashFlow.getCurrNum()) {
				currentPrincipanInterestMonth = principanInterestMonth;
			}

			// 还款状态
			repayLoanInfoDetail.setRepayLoanStatus(actVFlag);

			repayLoanDetailList.add(repayLoanInfoDetail);
		}

		// 2012-12-13
		// 当前日期等于下一次
		// 当前大于上一次 上一次正常还款，显示管理费 当前日期：01-01 上一次12-01 下一次01-01
		// 当前等于上一次 上一次逾期还款，显示管理费 当前日期：01-01 上一次01-01 下一次01-01

		// 当前日期小于下一次
		// 当前小于等于上一次 上一次提前还款，不显示管理费 当前日期：01-11 上一次02-01 下一次03-01
		// 当前大于上一次 上一次正常还款，显示管理费 当前日期：01-11 上一次01-01 下一次02-01

		// 下一次还款日期
		Date nextExpiry = currAcTLedgerLoan.getNextExpiry();
		// 当前日期
		Date currDate = DateUtil.getDateyyyyMMdd();

		if (currAcTLedgerLoan.getLastExpiry() == null) {
			// 第一次
			currentManagementFeeByMonth = FormulaSupportUtil.getManagementFeeEveryMonth(currRepayLoanInfo.getLoanAmount(), rateManagementFeeMonth);
		} else {
			if (currDate.compareTo(nextExpiry) == 0) {
				// 当前日期等于下一次
				Date previousDate = DateUtil.getPreviousMonthDateyyyyMMdd(nextExpiry);
				if (currDate.compareTo(previousDate) >= 0) {
					currentManagementFeeByMonth = FormulaSupportUtil.getManagementFeeEveryMonth(currRepayLoanInfo.getLoanAmount(), rateManagementFeeMonth);
				}
			} else if (currDate.compareTo(nextExpiry) < 0) {
				// 当前日期小于下一次
				Date previousDate = DateUtil.getPreviousMonthDateyyyyMMdd(nextExpiry);
				if (currDate.compareTo(previousDate) <= 0) {
					// 当前小于上一次 上一次提前还款，不显示管理费
					currentPrincipanInterestMonth = 0;
					currentManagementFeeByMonth = 0;
				} else {
					currentManagementFeeByMonth = FormulaSupportUtil.getManagementFeeEveryMonth(currRepayLoanInfo.getLoanAmount(), rateManagementFeeMonth);
				}
			}
		}

		AheadRepayLoanDetailListVO aheadRepayLoanInfoListVO = new AheadRepayLoanDetailListVO();
		// 管理费
		// currentManagementFeeByMonth =
		// FormulaSupportUtil.getManagementFeeEveryMonth(currRepayLoanInfo.getLoanAmount(),
		// rateManagementFeeMonth);
		aheadRepayLoanInfoListVO.setManagementFeeByMonth(ObjectFormatUtil.formatCurrency(currentManagementFeeByMonth));
		// 还款记录
		aheadRepayLoanInfoListVO.setRepayLoanDetailVOList(repayLoanDetailList);
		// 提前还款违约金
		overdueFinesComplateTotal = FormulaSupportUtil.getOverdueFinesComplateTotal(surplusPrincipal, overdueFinesComplateRate);
		// 一次性还款应还金额
		currentPaymentCompleteTotal = FormulaSupportUtil.getCurrentComplateShouldPayAmount(currentPrincipanInterestMonth, currentManagementFeeByMonth, surplusPrincipal, overdueFinesComplateTotal);
		aheadRepayLoanInfoListVO.setCurrentPaymentTotalDouble(currentPaymentCompleteTotal);
		aheadRepayLoanInfoListVO.setCurrentPaymentTotal(ObjectFormatUtil.formatCurrency(currentPaymentCompleteTotal));
		// 提前还款应还金额
		aheadRepayLoanInfoListVO.setAheadShouldRepayAmount(ObjectFormatUtil.formatCurrency(currentPaymentCompleteTotal));
		// 剩余本金
		aheadRepayLoanInfoListVO.setSurplusPrincipal(ObjectFormatUtil.formatCurrency(surplusPrincipal));
		aheadRepayLoanInfoListVO.setAheadOverdueFines(ObjectFormatUtil.formatCurrency(overdueFinesComplateTotal));
		// 当前借款信息表id
		aheadRepayLoanInfoListVO.setCurrentloanId(new BigDecimal(loanId));
		// 已还本息
		aheadRepayLoanInfoListVO.setPayOffprincipalInterest(ObjectFormatUtil.formatCurrency(payOffprincipalInterest));
		// 本期应还本息
		aheadRepayLoanInfoListVO.setCurrentShouldPayprincipalInterest(ObjectFormatUtil.formatCurrency(currentPrincipanInterestMonth));
		// 当前待还还款期数(一次性还款的起始期数)
		aheadRepayLoanInfoListVO.setCurrentTermLoan(currentRepayLoanPeriodNumber);
		// 我的余额
		// 借款人核心账户信息
		LoanManagementAcTCustomer actCustomerLoan = loanmanagementAcTCustomerDao.findById(currUser.gettCustomerId());
		LoanManagementAcTLedger payPrincipalActledger1 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerLoan.getTotalAcct() + "%");
		aheadRepayLoanInfoListVO.setMyAvailableBalance(ObjectFormatUtil.formatCurrency(payPrincipalActledger1.getAmount()));

		return aheadRepayLoanInfoListVO;
	}

	// 判断"我的余额"是否>="本期共计还款金额”
	public boolean myCapitalIsEnough(String paymentTotalDouble, HttpServletRequest req) {
		LoanManagementUsers currUser = getUsersBySession(req);
		LoanManagementAcTCustomer actCustomerLoan = loanmanagementAcTCustomerDao.findById(currUser.gettCustomerId());
		LoanManagementAcTLedger payPrincipalActledger1 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerLoan.getTotalAcct() + "%");
		// 我的可用余额
		double myAvailableBalance = payPrincipalActledger1.getAmount();
		double paymentTotal = new BigDecimal(paymentTotalDouble).doubleValue();
		if (myAvailableBalance >= paymentTotal) {
			return true;
		} else {
			return false;
		}
	}

	// 进行正式还款流程
//	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//	public String paymentProcessing(String paymentTotalDouble, String paymentLoanId, String currentTermLoan, String payType, HttpServletRequest req) throws RuntimeException {
//		LoanManagementUsers currUser = getUsersBySession(req);
//		BigDecimal loanId = new BigDecimal(paymentLoanId);
//		// 借款信息
//		LoanManagementLoanInfo currRepayLoanInfo = loanManagementDao.getLoanInfoByLoanId(loanId, currUser).get(0);
//		// 虚拟现金流水表
//		LoanManagementAcTLedgerLoan ledgerLoan = currRepayLoanInfo.getActLedgerLoan();
//		List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList = loanManagementDao.getRepayLoanDetailByLoanId(ledgerLoan);
//		// 借款人核心账户信息
//		LoanManagementAcTCustomer actCustomerLoan = loanmanagementAcTCustomerDao.findById(currUser.gettCustomerId());
//		// 贷款分户信息
//		LoanManagementAcTLedgerLoan currAcTLedgerLoan = loanManagementDao.getRepayLoanDetailByLedgerLoanId(ledgerLoan.getId()).get(0);
//		if (!currAcTLedgerLoan.getCurrNum().toString().equals(currentTermLoan)) {
//			return "payed";
//		}
//		List<SendMessageToInvestVO> messageList = null;
//
//		if ("ahead".equals(payType)) {
//			messageList = paymentTypeComplete(currUser, paymentTotalDouble, currRepayLoanInfo, actCustomerLoan, actvirturalCashFlowList, currAcTLedgerLoan);
//		} else {
//			messageList = paymentTypeEachPeriod(currUser, paymentTotalDouble, currRepayLoanInfo, actCustomerLoan, actvirturalCashFlowList, currAcTLedgerLoan, 1);
//		}
//		if (messageList != null && messageList.size() != 0) {
//			for (SendMessageToInvestVO messsage : messageList) {
//				LoanManagementInvestInfo investInfo = investInfoDao.findByInvestId(messsage.getInvestId());
//				List<PersonalUserMessageSet> messagePayList = messageSetDao.findByUserIdAndKindId(investInfo.getUser().getUserId(), new BigDecimal(7));
//				// 一次性提前还款
//				if ("ahead".equals(payType)) {
//					messageUtils.sendMessage(messsage, messagePayList, mimeMailService);
//					// 正常还款
//				} else {
//					sendSysMsgToInvest(messsage);// 发送系统消息
//					for (PersonalUserMessageSet message : messagePayList) {
//						if (message.getMannerId().equals(new BigDecimal(2))) {
//							// 邮件通知
//							sendMailToInvest(messsage);
//						}
//						if (message.getMannerId().equals(new BigDecimal(3))) {
//							// 短信通知
//							sendMessageToInvest(messsage);
//						}
//
//					}
//				}
//			}
//		}
//		return null;
//	}

	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-14 上午10:15:20
	 * @param currUser
	 * @param paymentTotalDouble
	 * @param currRepayLoanInfo
	 * @param actCustomerLoan
	 * @param actvirturalCashFlowList
	 * @param currAcTLedgerLoan
	 * @param isAuto
	 *            0自动还款，1正常分期还款
	 * @return description:分期还款
	 * @throws Exception
	 */
//	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//	public List<SendMessageToInvestVO> paymentTypeEachPeriod(LoanManagementUsers currUser, String paymentTotalDouble, LoanManagementLoanInfo currRepayLoanInfo, LoanManagementAcTCustomer actCustomerLoan, List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList, LoanManagementAcTLedgerLoan currAcTLedgerLoan, int isAuto) throws RuntimeException {
//		LoanManagementAcTVirtualCashFlow currAcTVirtualCashFlow = new LoanManagementAcTVirtualCashFlow();
//		LoanManagementAcTVirtualCashFlow nextCurrAcTVirtualCashFlow = new LoanManagementAcTVirtualCashFlow();
//		// 各种费率信息,取自费率表
//		Rate rate = commonDao.getRate(1l);
//		// 罚息比例 （取自费率表）
//		double overdueRate = rate.getOverdueInterest().doubleValue();
//		// 严重逾期罚息比例 （取自费率表）
//		double overdueSerRate = rate.getOverdueSeriousInterest().doubleValue();
//		// 罚息比例（ 取自贷款分户信息表，逾期执行利率）
//		// double overdueRate = currAcTLedgerLoan.getStrikeRate();
//		// 逾期违约金比例
//		double overdueFinesRate = rate.getOverdueFines().doubleValue();
//		// 管理费
//		double monthManageFee = rate.getMgmtFee().doubleValue();
//		// 本期还款额
//		double amt = 0;
//		// 剩余未还期数
//		int term = 0;
//		// 下一还款日
//		Date NextExpiry = null;
//
//		for (int i = 0; i < actvirturalCashFlowList.size(); i++) {
//			LoanManagementAcTVirtualCashFlow actvCashFlow = actvirturalCashFlowList.get(i);
//			if (actvCashFlow.getCurrNum() == currAcTLedgerLoan.getCurrNum().shortValue()) {
//				currAcTVirtualCashFlow = actvCashFlow;
//				if (i != actvirturalCashFlowList.size() - 1)
//					nextCurrAcTVirtualCashFlow = actvirturalCashFlowList.get(i + 1);
//				term = currAcTLedgerLoan.getTotalNum().shortValue() - actvCashFlow.getCurrNum() + 1;
//			}
//			if (currAcTLedgerLoan.getCurrNum().intValue() != currRepayLoanInfo.getLoanDuration().intValue() && currAcTLedgerLoan.getCurrNum().intValue() == actvCashFlow.getCurrNum() - 1) {
//				NextExpiry = actvCashFlow.getRepayDay();
//			}
//
//		}
//
//		// 当前期应偿还本金
//		double currPrincipal = currAcTVirtualCashFlow.getPrincipalAmt();
//		// 当前期应偿还利息
//		double currInterest = currAcTVirtualCashFlow.getInterestAmt();
//		// 本息和
//		double principal_interest_month = ArithUtil.add(currPrincipal, currInterest);
//		// 下次结息日
//		Date currMaturityDate = currAcTLedgerLoan.getNextExpiry();
//		// 自动还款进入
//		if (isAuto == 0) {
//			// 前一次结息日
//			Date previousDate = DateUtil.getPreviousMonthDateyyyyMMdd(currMaturityDate);
//			// 当前日期
//			Date currDate = DateUtil.getDateYYYYMMDD(new Date(), "yyyy-MM-dd");
//
//			if (!(currDate.getTime() > DateUtil.getDateYYYYMMDD(previousDate, "yyyy-MM-dd").getTime() && currDate.getTime() <= DateUtil.getDateYYYYMMDD(currMaturityDate, "yyyy-MM-dd").getTime())) {
//				return null;
//			}
//
//		}
//		// 逾期天数=下次结息日-当前日期
//		int overdueDays = FormulaSupportUtil.getOverdueDays(currMaturityDate, DateUtil.getCurrentDate());
//		// 当前期逾期罚息(逾期天数小于等于30天)
//		double currOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDays, term, principal_interest_month, overdueRate);
//		// 当前期逾期罚息(逾期天数大于30天)
//		double currOverdueInterestToZendai = FormulaSupportUtil.calOverdueInterest(overdueDays, term, principal_interest_month, overdueSerRate);
//		// 月还管理费
//		double currmanageFeeByMonth = ArithUtil.mul(currRepayLoanInfo.getLoanAmount(), monthManageFee);
//		// 逾期违约金
//		double currOverdueFines = FormulaSupportUtil.getOverdueFines(overdueDays, term, currmanageFeeByMonth, overdueFinesRate);
//		// 借款人资金账户
//		LoanManagementAcTLedger payPrincipalActledger1 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerLoan.getTotalAcct() + "%");// 业务类别：4：资金账户
//		// 还逾期罚息和违约金
//		if (overdueDays <= 30) {
//			// 还本金
//			setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currPrincipal, ConstSubject.pay_principal_in, ConstSubject.pay_principal_out, 1, "偿还本金");
//			// 还利息
//			setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currInterest, ConstSubject.pay_interest_in, ConstSubject.pay_interest_out, 2, "偿还利息");
//			// 逾期罚息(判断是否存在逾期)
//			if (overdueDays > 0) {
//				// currAcTVirtualCashFlow.setOverDueFineAmount(currOverdueFines);
//				// currAcTVirtualCashFlow.setOverDueInterestAmount(currOverdueInterest);
//				// currAcTVirtualCashFlow.setOverDueDays(overdueDays);
//				// 逾期罚息
//				setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currOverdueInterest, ConstSubject.interest_overdue_in, ConstSubject.interest_overdue_out, 3, "逾期罚息");
//				// 还逾期违约金
//				setPayZendai(payPrincipalActledger1, ZendaiAccountBank.zendai_acct3, actCustomerLoan, currOverdueFines, ConstSubject.penalty_overdue_out, ConstSubject.penalty_overdue_in, currUser, "逾期违约金", 3);
//				// 更新分期债权逾期信息表
//				repaySupporManager.updateOverdueClaims(currRepayLoanInfo);
//			}
//		} else {
//			// currAcTVirtualCashFlow.setOverDueFineAmount(currOverdueFines);
//			// currAcTVirtualCashFlow.setOverDueInterestAmount(currOverdueInterestToZendai);
//			// currAcTVirtualCashFlow.setOverDueDays(overdueDays);
//			// 逾期大于三十天还款
//			// rePayToZendai(currRepayLoanInfo, currPrincipal, currInterest,
//			// currOverdueInterestToZendai, currOverdueFines,
//			// currmanageFeeByMonth, currUser);
//			// 借款人资金账户
//			LoanManagementAcTCustomer loanActCustomer = loanmanagementAcTCustomerDao.findById(currRepayLoanInfo.getUser().gettCustomerId()); // 理财人核心客户信息
//			LoanManagementAcTLedger amountActledger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", loanActCustomer.getTotalAcct() + "%");// 业务类别：4：资金账户
//			LoanManagementAcTLedger loanActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("2", loanActCustomer.getTotalAcct() + "%");// 业务类别：2：贷款；
//			// 偿还本金
//			repaySupporManager.repayPrincipal(currRepayLoanInfo, currPrincipal, loanActCustomer, amountActledger, loanActLedger);
//			// 偿还利息
//			repaySupporManager.repayInterest(currRepayLoanInfo, currInterest, loanActCustomer, amountActledger, loanActLedger);
//			// 偿还逾期罚息
//			repaySupporManager.repayOverdueInterest(currRepayLoanInfo, currOverdueInterestToZendai, currOverdueFines, loanActCustomer, amountActledger, loanActLedger);
//
//			// 还本金(TO证大)
//			// setPayZendai(payPrincipalActledger1,
//			// ZendaiAccountBank.zendai_acct10, actCustomerLoan, currPrincipal,
//			// ConstSubject.pay_principal_in, ConstSubject.pay_principal_out,
//			// currUser, "偿还本金",1);
//			// // 还利息(TO证大)
//			// setPayZendai(payPrincipalActledger1,
//			// ZendaiAccountBank.zendai_acct10, actCustomerLoan, currInterest,
//			// ConstSubject.pay_interest_in, ConstSubject.pay_interest_out,
//			// currUser, "偿还利息",2);
//			// // 逾期罚息 (归证大)
//			// setPayZendai(payPrincipalActledger1,
//			// ZendaiAccountBank.zendai_acct2, actCustomerLoan,
//			// currOverdueInterestToZendai, ConstSubject.interest_overdue_out,
//			// ConstSubject.interest_overdue_in, currUser, "逾期罚息",3);
//			// 还逾期违约金(归证大)
//			setPayZendai(payPrincipalActledger1, ZendaiAccountBank.zendai_acct3, actCustomerLoan, currOverdueFines, ConstSubject.penalty_overdue_out, ConstSubject.penalty_overdue_in, currUser, "逾期违约金", 3);
//		}
//
//		// 还每月网站管理费
//		setPayZendai(payPrincipalActledger1, ZendaiAccountBank.zendai_acct1, actCustomerLoan, currmanageFeeByMonth, ConstSubject.loan_man_fee_out, ConstSubject.loan_man_fee_in, currUser, "月缴管理费", 3);
//
//		// 还款后贷款分户信息维护
//		List<LoanManagementInvestInfo> investInfoList = currRepayLoanInfo.getInvestInfoList();
//		if (currAcTLedgerLoan.getCurrNum().intValue() != currRepayLoanInfo.getLoanDuration().intValue()) {
//			// 上次结息日
//			currAcTLedgerLoan.setLastExpiry(DateUtil.getDateyyyyMMdd());
//			// 下次结息日
//			currAcTLedgerLoan.setNextExpiry(NextExpiry);
//			// 分期还款额
//			currAcTLedgerLoan.setEachRepayment(principal_interest_month);
//			// 当前期数
//			currAcTLedgerLoan.setCurrNum(currAcTLedgerLoan.getCurrNum() + 1);
//			// 贷款余额
//			// currAcTLedgerLoan.setOutstanding(ArithUtil.sub(currAcTLedgerLoan.getOutstanding(),
//			// currPrincipal));
//			currAcTLedgerLoan.setOutstanding(ArithUtil.sub(currAcTLedgerLoan.getOutstanding(), nextCurrAcTVirtualCashFlow.getPrincipalAmt()));// bianxj
//																																				// edit
//			// 理财分户信息
//			List<LoanManagementAcTLedgerFinance> acTLedgerFinanceList = currAcTLedgerLoan.getAcTLedgerFinance();
//			for (LoanManagementAcTLedgerFinance loanManagementAcTLedgerFinance : acTLedgerFinanceList) {
//				// 当前应收利息
//				loanManagementAcTLedgerFinance.setInterestReceivable(ArithUtil.sub(loanManagementAcTLedgerFinance.getInterestReceivable(), currInterest));
//				// 未还期数
//				loanManagementAcTLedgerFinance.setRemainNum(loanManagementAcTLedgerFinance.getRemainNum() - 1);
//			}
//			currAcTLedgerLoan.setAcTLedgerFinance(acTLedgerFinanceList);
//			// 逾期天数=下次结息日-当前日期
//			int overdueDays2 = FormulaSupportUtil.getOverdueDays(NextExpiry, DateUtil.getCurrentDate());
//			if (overdueDays2 <= 30 && overdueDays2 > 0) {
//				currRepayLoanInfo.setStatus(new BigDecimal(6));
//			} else if (overdueDays2 > 30) {
//				currRepayLoanInfo.setStatus(new BigDecimal(7));
//			} else {
//				currRepayLoanInfo.setStatus(new BigDecimal(4));
//			}
//		} else {
//			// 最后一期还款成功，进行贷款分户信息销户
//			// 账号状态
//			currAcTLedgerLoan.setAcctStatus("9");
//			// 上次结息日
//			currAcTLedgerLoan.setLastExpiry(DateUtil.getDateyyyyMMdd());
//			// 下次结息日
//			currAcTLedgerLoan.setNextExpiry(null);
//			// 分期还款额
//			currAcTLedgerLoan.setEachRepayment(principal_interest_month);
//			// 贷款余额
//			currAcTLedgerLoan.setOutstanding(0d);
//			List<LoanManagementAcTLedgerFinance> acTLedgerFinanceList = currAcTLedgerLoan.getAcTLedgerFinance();
//			for (LoanManagementAcTLedgerFinance loanManagementAcTLedgerFinance : acTLedgerFinanceList) {
//				// 理财分户销户
//				loanManagementAcTLedgerFinance.setAcctStatus("9");
//				// 当前应收利息
//				loanManagementAcTLedgerFinance.setInterestReceivable(0d);
//				// 未还期数
//				loanManagementAcTLedgerFinance.setRemainNum(0l);
//			}
//			currAcTLedgerLoan.setAcTLedgerFinance(acTLedgerFinanceList);
//			// 还款成功借款信息维护
//			currRepayLoanInfo.setStatus(new BigDecimal(5));
//
//			for (LoanManagementInvestInfo investInfo : investInfoList) {
//				// 还款成功理财信息维护
//				investInfo.setStatus(new BigDecimal(4));
//			}
//			currRepayLoanInfo.setInvestInfoList(investInfoList);
//			sendMovement(currRepayLoanInfo);
//		}
//
//		currRepayLoanInfo.setActLedgerLoan(currAcTLedgerLoan);
//		logger.info("");
//		loanInfoDao.save(currRepayLoanInfo);
//		// 还款金额
//		if (overdueDays <= 30) {
//			amt = FormulaSupportUtil.currentTermShouldPayAmount(currmanageFeeByMonth, principal_interest_month, currOverdueInterest, currOverdueFines);
//		} else {
//			amt = FormulaSupportUtil.currentTermShouldPayAmount(currmanageFeeByMonth, principal_interest_month, currOverdueInterestToZendai, currOverdueFines);
//		}
//		// currAcTVirtualCashFlow.setAmt(amt);
//		// 修改日期即还款日期
//		// currAcTVirtualCashFlow.setEditDate(DateUtil.getDateyyyyMMdd());
//		// 修改人id
//		// currAcTVirtualCashFlow.setEditUserId(currUser.getUserId().longValue());
//		int repayStatus = 0;
//		if (overdueDays > 0) {
//			// 逾期还款
//			repayStatus = 4;
//		} else {
//			// 正常还款
//			repayStatus = 1;
//		}
//		// loanManagementAcTVirtualCashFlowDao.save(currAcTVirtualCashFlow);
//		double tempOverdueInterest = 0;
//		if (overdueDays <= 30) {
//			tempOverdueInterest = currOverdueInterest;
//		} else {
//			tempOverdueInterest = currOverdueInterestToZendai;
//		}
//
//		int count = payBackUtils.updateAcVirtualCashFlow(amt, DateUtil.getDateyyyyMMdd(), currUser.getUserId().longValue(), repayStatus, currOverdueFines, tempOverdueInterest, overdueDays, currAcTVirtualCashFlow.getId());
//		if (count <= 0) {
//			throw new RuntimeException("还款进行中.....请耐心等待。");
//		}
//		List<SendMessageToInvestVO> messageList = new ArrayList<SendMessageToInvestVO>();
//		for (LoanManagementInvestInfo investInfo : investInfoList) {
//			LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), currRepayLoanInfo.getActLedgerLoan().getCurrNum());
//			if (overdueClaims != null && BigDecimal.ONE.equals(overdueClaims.getIsAdvanced())) {
//				continue;
//			}
//			SendMessageToInvestVO message = new SendMessageToInvestVO();
//			message.setInvestId(investInfo.getInvestId());
//			message.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(ArithUtil.add(ArithUtil.mul(currPrincipal, investInfo.getHavaScale()), ArithUtil.mul(currInterest, investInfo.getHavaScale()))));
//			if (overdueDays <= 30) {
//				message.setOverdueInterest(ObjectFormatUtil.formatCurrency(ArithUtil.mul(currOverdueInterest, investInfo.getHavaScale())));
//			} else {
//				message.setOverdueInterest(ObjectFormatUtil.formatCurrency(ArithUtil.mul(currOverdueInterestToZendai, investInfo.getHavaScale())));
//			}
//			messageList.add(message);
//		}
//		return messageList;
//	}

	/**
	 * 提前还款 剩余本金和违约金
	 * 
	 * @param currUser
	 * @param currRepayLoanInfo
	 * @param actCustomerLoan
	 * @param currSurplusPrincipal
	 * @param overdueFinesComplateRate
	 * @throws Exception
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void setTQRepay(LoanManagementUsers currUser, LoanManagementLoanInfo currRepayLoanInfo, LoanManagementAcTCustomer actCustomerLoan, double currSurplusPrincipal, double overdueFinesComplateRate) {
		// 提前还款剩余本金
		setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currSurplusPrincipal, ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, 4, "剩余本金");
		// 提前还款违约金
		// double currEarlyOverFines =
		// FormulaSupportUtil.getOverdueFinesComplateTotal(ArithUtil.sub(currSurplusPrincipal,
		// currPrincipal), overdueFinesComplateRate);
		double currEarlyOverFines = FormulaSupportUtil.getOverdueFinesComplateTotal(currSurplusPrincipal, overdueFinesComplateRate);
		// 提前还款违约金
		setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currEarlyOverFines, ConstSubject.penalty_overdue_out, ConstSubject.penalty_overdue_in, 5, "提前还款违约金");
	}

	// 提前一次性还款
//	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//	public List<SendMessageToInvestVO> paymentTypeComplete(LoanManagementUsers currUser, String paymentTotalDouble, LoanManagementLoanInfo currRepayLoanInfo, LoanManagementAcTCustomer actCustomerLoan, List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList, LoanManagementAcTLedgerLoan currAcTLedgerLoan) {
//		LoanManagementAcTVirtualCashFlow currAcTVirtualCashFlow = new LoanManagementAcTVirtualCashFlow();
//		// 各种费率信息,取自费率表
//		Rate rate = commonDao.getRate(1l);
//		// 罚息比例
//		// double overdueRate = currAcTLedgerLoan.getStrikeRate();
//		double overdueRate = rate.getOverdueInterest().doubleValue();
//		// 逾期违约金比例
//		double overdueFinesRate = rate.getOverdueFines().doubleValue();
//		// 一次性提前还款违约金费率
//		double overdueFinesComplateRate = rate.getEarlyFines().doubleValue();
//		// 当前期还款后剩余本金
//		double currSurplusPrincipal = currAcTLedgerLoan.getOutstanding();
//		// 剩余未还期数
//		int term = 0;
//		for (LoanManagementAcTVirtualCashFlow actvCashFlow : actvirturalCashFlowList) {
//			if (actvCashFlow.getCurrNum() == currAcTLedgerLoan.getCurrNum().shortValue()) {
//				currAcTVirtualCashFlow = actvCashFlow;
//				term = currAcTLedgerLoan.getTotalNum().shortValue() - actvCashFlow.getCurrNum() + 1;
//			}
//		}
//		// 当前期应偿还本金
//		double currPrincipal = currAcTVirtualCashFlow.getPrincipalAmt();
//		// 当前期应偿还利息
//		double currInterest = currAcTVirtualCashFlow.getInterestAmt();
//		// 提前还款删除还款计划
//		boolean delFlag = false;
//		// 本息和
//		double principal_interest_month = currRepayLoanInfo.getMonthReturnPrincipalandinter();
//		// 本期还款额
//		double amt = 0;
//		// 下次结息日
//		Date nextExpiry = currAcTLedgerLoan.getNextExpiry();
//		// 上次结息日
//		Date lastExpiry = currAcTLedgerLoan.getLastExpiry();
//		// 逾期天数
//		int overdueDays = FormulaSupportUtil.getOverdueDays(nextExpiry, DateUtil.getCurrentDate());
//		// 当期还款判断 1是 2否
//		int currTermDays = FormulaSupportUtil.getIsCurrTerm(DateUtil.getCurrentDate(), nextExpiry);
//		// 当前期逾期罚息(逾期天数小于等于30天)
//		double currOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDays, term, principal_interest_month, overdueRate);
//		// 当前期逾期罚息(逾期天数大于30天)
//		double currOverdueInterestToZendai = FormulaSupportUtil.calOverdueInterest(overdueDays, term, principal_interest_month, overdueRate);
//		// 逾期违约金
//		double currOverdueFines = FormulaSupportUtil.getOverdueFines(overdueDays, term, principal_interest_month, overdueFinesRate);
//		// 月还管理费
//		double currmanageFeeByMonth = currRepayLoanInfo.getMonthManageCost();
//		// 借款人资金账户
//		LoanManagementAcTLedger payPrincipalActledger1 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerLoan.getTotalAcct() + "%");// 业务类别：4：资金账户
//		// 当前还款(第一期未还)
//		if (currAcTLedgerLoan.getLastExpiry() == null) {
//			// 是否当期
//			if (currTermDays >= 0) {
//				// 偿还本金
//				setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currPrincipal, ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, 1, "偿还本金");
//				// 偿还利息
//				setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currInterest, ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, 2, "偿还利息");
//				// 还每月网站管理费
//				setPayZendai(payPrincipalActledger1, ZendaiAccountBank.zendai_acct1, actCustomerLoan, currmanageFeeByMonth, ConstSubject.loan_man_fee_out, ConstSubject.loan_man_fee_in, currUser, "月缴管理费", 3);
//				// currSurplusPrincipal = ArithUtil.sub(currSurplusPrincipal,
//				// currPrincipal);//bianxj edit
//				setTQRepay(currUser, currRepayLoanInfo, actCustomerLoan, currSurplusPrincipal, overdueFinesComplateRate);
//
//				delFlag = false;
//			}
//			// //还逾期罚息
//			// if(overdueDays <=30){
//			// //逾期罚息(判断是否存在逾期罚息)
//			// if(overdueDays>0){
//			// //偿还罚息
//			// setRepayAmount(currUser,
//			// currRepayLoanInfo,
//			// actCustomerLoan,
//			// currOverdueInterest,
//			// ConstSubject.interest_overdue_out,
//			// ConstSubject.interest_overdue_in,
//			// 3);
//			// //偿还逾期违约金
//			// setPayZendai(payPrincipalActledger1,
//			// ZendaiAccountBank.zendai_acct3,
//			// actCustomerLoan,
//			// currOverdueFines,
//			// ConstSubject.penalty_overdue_out,
//			// ConstSubject.penalty_overdue_in,
//			// currUser);
//			// }
//			// }else{
//			// //偿还罚息(归证大)
//			// setPayZendai(payPrincipalActledger1,
//			// ZendaiAccountBank.zendai_acct2,
//			// actCustomerLoan,
//			// currOverdueInterestToZendai,
//			// ConstSubject.interest_overdue_out,
//			// ConstSubject.interest_overdue_in,
//			// currUser);
//			// //偿还逾期违约金
//			// setPayZendai(payPrincipalActledger1,
//			// ZendaiAccountBank.zendai_acct3,
//			// actCustomerLoan,
//			// currOverdueFines,
//			// ConstSubject.penalty_overdue_out,
//			// ConstSubject.penalty_overdue_in,
//			// currUser);
//			// }
//		} else {
//			// 当前还款(最后一期还款)
//			if (currAcTLedgerLoan.getCurrNum() == currRepayLoanInfo.getLoanDuration().longValue()) {
//				// “当前提前还款日期”<“下一个结息日”
//				if (currTermDays > 0) {
//					// “当前提前还款日期”=“上一个结息日”
//					if (DateUtil.getDateyyyyMMdd().equals(lastExpiry)) {
//						// 提前还款剩余本金
//						setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currSurplusPrincipal + currPrincipal, ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, 1, "剩余本金");
//						// 提前还款违约金
//						double currEarlyOverFines = FormulaSupportUtil.getOverdueFinesComplateTotal(currSurplusPrincipal + currPrincipal, overdueFinesComplateRate);
//						// 提前还款违约金
//
//						setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currEarlyOverFines, ConstSubject.penalty_overdue_in, ConstSubject.penalty_overdue_out, 5, "提前还款违约金");
//
//						delFlag = true;
//					}
//					// “当前提前还款日期”>“上一个结息日”
//					if (FormulaSupportUtil.getOverdueDays(lastExpiry, DateUtil.getDateyyyyMMdd()) > 0) {
//						// 偿还本金
//						setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currPrincipal, ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, 1, "偿还本金");
//						// 偿还利息
//						setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currInterest, ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, 2, "偿还利息");
//						// 还每月网站管理费
//						setPayZendai(payPrincipalActledger1, ZendaiAccountBank.zendai_acct1, actCustomerLoan, currmanageFeeByMonth, ConstSubject.loan_man_fee_out, ConstSubject.loan_man_fee_in, currUser, "月缴管理费", 3);
//						// currSurplusPrincipal =
//						// ArithUtil.sub(currSurplusPrincipal, currPrincipal);
//						setTQRepay(currUser, currRepayLoanInfo, actCustomerLoan, currSurplusPrincipal, overdueFinesComplateRate);
//						delFlag = false;
//						// //还逾期罚息
//						// if(overdueDays <=30){
//						// //逾期罚息(判断是否存在逾期罚息)
//						// if(overdueDays>0){
//						// //偿还罚息
//						// setRepayAmount(currUser,
//						// currRepayLoanInfo,
//						// actCustomerLoan,
//						// currOverdueInterest,
//						// ConstSubject.interest_overdue_out,
//						// ConstSubject.interest_overdue_in,
//						// 3);
//						// //偿还逾期违约金
//						// setPayZendai(payPrincipalActledger1,
//						// ZendaiAccountBank.zendai_acct3,
//						// actCustomerLoan,
//						// currOverdueFines,
//						// ConstSubject.penalty_overdue_out,
//						// ConstSubject.penalty_overdue_in,
//						// currUser);
//						// }
//						// }else{
//						// //偿还罚息(归证大)
//						// setPayZendai(payPrincipalActledger1,
//						// ZendaiAccountBank.zendai_acct2,
//						// actCustomerLoan,
//						// currOverdueInterestToZendai,
//						// ConstSubject.interest_overdue_out,
//						// ConstSubject.interest_overdue_in,
//						// currUser);
//						// //偿还逾期违约金
//						// setPayZendai(payPrincipalActledger1,
//						// ZendaiAccountBank.zendai_acct3,
//						// actCustomerLoan,
//						// currOverdueFines,
//						// ConstSubject.penalty_overdue_out,
//						// ConstSubject.penalty_overdue_in,
//						// currUser);
//						// }
//					}
//				}
//			} else {
//				// “当前提前还款日期”<=“下一个结息日”
//				if (currTermDays > 0) {
//					// “当前提前还款日期”=“上一个结息日”
//					// if (DateUtil.getDateyyyyMMdd().equals(lastExpiry)) {
//					// // 提前还款剩余本金
//					// setRepayAmount(currUser, currRepayLoanInfo,
//					// actCustomerLoan, currSurplusPrincipal,
//					// ConstSubject.pay_principal_out,
//					// ConstSubject.pay_principal_in, 1, "剩余本金");
//					// //偿还本金
//					// setRepayAmount(currUser, currRepayLoanInfo,
//					// actCustomerLoan, currPrincipal,
//					// ConstSubject.pay_principal_out,
//					// ConstSubject.pay_principal_in, 1, "偿还本金/回收本金");
//					// //偿还利息
//					// setRepayAmount(currUser, currRepayLoanInfo,
//					// actCustomerLoan, currInterest,
//					// ConstSubject.pay_interest_out,
//					// ConstSubject.pay_interest_in, 2, "偿还利息/回收利息");
//					//
//					// double currEarlyOverFines =
//					// FormulaSupportUtil.getOverdueFinesComplateTotal(currSurplusPrincipal,
//					// overdueFinesComplateRate);
//					// // 提前还款违约金
//					// setRepayAmount(currUser, currRepayLoanInfo,
//					// actCustomerLoan, currEarlyOverFines,
//					// ConstSubject.penalty_overdue_in,
//					// ConstSubject.penalty_overdue_out, 5, "提前还款违约金");
//					// // 还每月网站管理费
//					// setPayZendai(payPrincipalActledger1,
//					// ZendaiAccountBank.zendai_acct1, actCustomerLoan,
//					// currmanageFeeByMonth, ConstSubject.loan_man_fee_out,
//					// ConstSubject.loan_man_fee_in, currUser, "月缴管理费",3);
//					// delFlag = true;
//					// }
//					// “当前提前还款日期”>=“上一个结息日”
//					// if (FormulaSupportUtil.getOverdueDays(lastExpiry,
//					// DateUtil.getDateyyyyMMdd()) >=0) {
//					if (DateUtil.getDateyyyyMMdd().getTime() >= lastExpiry.getTime()) {
//						// 上次计划还款日期
//						Date previousDate = DateUtil.getPreviousMonthDateyyyyMMdd(nextExpiry);
//						// 当前日期
//						Date currDate = DateUtil.getDateyyyyMMdd();
//						// 如果当前日期处于当期还款日期期间：当前日期大于上次计划还款日期，并且小于等于当期还款日期
//						if (currDate.getTime() > previousDate.getTime() && currDate.getTime() <= nextExpiry.getTime()) {
//							// 偿还本金
//							setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currPrincipal, ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, 1, "偿还本金");
//							// 偿还利息
//							setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currInterest, ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, 2, "偿还利息");
//							// 还每月网站管理费
//							setPayZendai(payPrincipalActledger1, ZendaiAccountBank.zendai_acct1, actCustomerLoan, currmanageFeeByMonth, ConstSubject.loan_man_fee_out, ConstSubject.loan_man_fee_in, currUser, "月缴管理费", 3);
//							// 提前还款
//							setTQRepay(currUser, currRepayLoanInfo, actCustomerLoan, currSurplusPrincipal, overdueFinesComplateRate);
//						} else {
//							// 提前还款
//							setTQRepay(currUser, currRepayLoanInfo, actCustomerLoan, ArithUtil.add(currSurplusPrincipal, currPrincipal), overdueFinesComplateRate);
//							delFlag = true;
//						}
//						// // 偿还本金
//						// setRepayAmount(currUser, currRepayLoanInfo,
//						// actCustomerLoan, currPrincipal,
//						// ConstSubject.pay_principal_out,
//						// ConstSubject.pay_principal_in, 1, "偿还本金");
//						// // 偿还利息
//						// setRepayAmount(currUser, currRepayLoanInfo,
//						// actCustomerLoan, currInterest,
//						// ConstSubject.pay_interest_out,
//						// ConstSubject.pay_interest_in, 2, "偿还利息");
//						// // 还每月网站管理费
//						// setPayZendai(payPrincipalActledger1,
//						// ZendaiAccountBank.zendai_acct1, actCustomerLoan,
//						// currmanageFeeByMonth, ConstSubject.loan_man_fee_out,
//						// ConstSubject.loan_man_fee_in, currUser, "月缴管理费",3);
//						// currSurplusPrincipal =
//						// ArithUtil.sub(currSurplusPrincipal, currPrincipal);
//						// 提前还款
//						// setTQRepay(currUser, currRepayLoanInfo,
//						// actCustomerLoan, currSurplusPrincipal,
//						// overdueFinesComplateRate);
//						// delFlag = false;
//						// //还逾期罚息
//						// if(overdueDays <=30){
//						// //逾期罚息(判断是否存在逾期罚息)
//						// if(overdueDays>0){
//						// //偿还罚息
//						// setRepayAmount(currUser,
//						// currRepayLoanInfo,
//						// actCustomerLoan,
//						// currOverdueInterest,
//						// ConstSubject.interest_overdue_out,
//						// ConstSubject.interest_overdue_in,
//						// 3);
//						// //偿还逾期违约金
//						// setPayZendai(payPrincipalActledger1,
//						// ZendaiAccountBank.zendai_acct3,
//						// actCustomerLoan,
//						// currOverdueFines,
//						// ConstSubject.penalty_overdue_out,
//						// ConstSubject.penalty_overdue_in,
//						// currUser);
//						// }
//						// }else{
//						// //偿还罚息(归证大)
//						// setPayZendai(payPrincipalActledger1,
//						// ZendaiAccountBank.zendai_acct2,
//						// actCustomerLoan,
//						// currOverdueInterestToZendai,
//						// ConstSubject.interest_overdue_out,
//						// ConstSubject.interest_overdue_in,
//						// currUser);
//						// //偿还逾期违约金
//						// setPayZendai(payPrincipalActledger1,
//						// ZendaiAccountBank.zendai_acct3,
//						// actCustomerLoan,
//						// currOverdueFines,
//						// ConstSubject.penalty_overdue_out,
//						// ConstSubject.penalty_overdue_in,
//						// currUser);
//						// }
//					}
//				} else {
//
//					// 偿还本金
//					setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currPrincipal, ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, 1, "偿还本金");
//					// 偿还利息
//					setRepayAmount(currUser, currRepayLoanInfo, actCustomerLoan, currInterest, ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, 2, "偿还利息");
//					// 还每月网站管理费
//					setPayZendai(payPrincipalActledger1, ZendaiAccountBank.zendai_acct1, actCustomerLoan, currmanageFeeByMonth, ConstSubject.loan_man_fee_out, ConstSubject.loan_man_fee_in, currUser, "月缴管理费", 3);
//					// currSurplusPrincipal =
//					// ArithUtil.sub(currSurplusPrincipal, currPrincipal);
//					setTQRepay(currUser, currRepayLoanInfo, actCustomerLoan, currSurplusPrincipal, overdueFinesComplateRate);
//					delFlag = false;
//					// //还逾期罚息
//					// if(overdueDays <=30){
//					// //逾期罚息(判断是否存在逾期罚息)
//					// if(overdueDays>0){
//					// //偿还罚息
//					// setRepayAmount(currUser,
//					// currRepayLoanInfo,
//					// actCustomerLoan,
//					// currOverdueInterest,
//					// ConstSubject.interest_overdue_out,
//					// ConstSubject.interest_overdue_in,
//					// 3);
//					// //偿还逾期违约金
//					// setPayZendai(payPrincipalActledger1,
//					// ZendaiAccountBank.zendai_acct3,
//					// actCustomerLoan,
//					// currOverdueFines,
//					// ConstSubject.penalty_overdue_out,
//					// ConstSubject.penalty_overdue_in,
//					// currUser);
//					// }
//					// }else{
//					// //偿还罚息(归证大)
//					// setPayZendai(payPrincipalActledger1,
//					// ZendaiAccountBank.zendai_acct2,
//					// actCustomerLoan,
//					// currOverdueInterestToZendai,
//					// ConstSubject.interest_overdue_out,
//					// ConstSubject.interest_overdue_in,
//					// currUser);
//					// //偿还逾期违约金
//					// setPayZendai(payPrincipalActledger1,
//					// ZendaiAccountBank.zendai_acct3,
//					// actCustomerLoan,
//					// currOverdueFines,
//					// ConstSubject.penalty_overdue_out,
//					// ConstSubject.penalty_overdue_in,
//					// currUser);
//					// }
//
//				}
//
//			}
//		}
//
//		amt = FormulaSupportUtil.currentTermShouldPayAmount(currmanageFeeByMonth, principal_interest_month, ArithUtil.add(currOverdueInterest, currOverdueInterestToZendai), currOverdueFines);
//
//		LoanManagementAcTLedger loanActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("2", actCustomerLoan.getTotalAcct() + "%");// 业务类别：2：贷款；
//		for (Iterator it = actvirturalCashFlowList.iterator(); it.hasNext();) {
//			LoanManagementAcTVirtualCashFlow actvCashFlow = (LoanManagementAcTVirtualCashFlow) it.next();
//			// 还款状态
//			int repayStatus = 0;
//			// 还款金额
//			double repayAmt = 0;
//			if (actvCashFlow.getCurrNum() == currAcTLedgerLoan.getCurrNum().shortValue()) {
//				// 当期要正常还款删除还款计划
//				if (delFlag) {
//					// 提前还款违约金
//					double currEarlyOverFines = FormulaSupportUtil.getOverdueFinesComplateTotal(currSurplusPrincipal + currPrincipal, overdueFinesComplateRate);
//					// 一次性提前还款
//					repayStatus = 2;
//					// actvCashFlow.setRepayStatus(RepayStatus.一次性提前还款);
//					// 还款金额
//					repayAmt = ArithUtil.add(actvCashFlow.getPrincipalAmt(), currEarlyOverFines);
//					// actvCashFlow.setAmt(actvCashFlow.getPrincipalAmt() +
//					// currEarlyOverFines);
//					// loanManagementAcTVirtualCashFlowDao.save(currAcTVirtualCashFlow);
//				} else {
//					// 提前还款违约金
//					double currEarlyOverFines = FormulaSupportUtil.getOverdueFinesComplateTotal(currSurplusPrincipal, overdueFinesComplateRate);
//					// 正常还款
//					repayStatus = 1;
//					// actvCashFlow.setRepayStatus(RepayStatus.正常还款);
//					// 还款金额
//					repayAmt = ArithUtil.add(amt, currEarlyOverFines);
//					// actvCashFlow.setAmt(amt + currEarlyOverFines);
//					// loanManagementAcTVirtualCashFlowDao.save(currAcTVirtualCashFlow);
//				}
//				loanActLedger.setInterestPayable(ArithUtil.sub(loanActLedger.getInterestPayable(), actvCashFlow.getInterestAmt()));
//				// actvCashFlow.setEditDate(DateUtil.getDateyyyyMMdd());
//				// actvCashFlow.setEditUserId(currUser.getUserId().longValue());
//				// payBackUtils.updateAcVirtualCashFlow(amt, editDate,
//				// editUserId, repayStatus, id)
//				// loanManagementAcTVirtualCashFlowDao.save(actvCashFlow);
//				List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
//				// ①理财人收回本金（N个理财人）
//				for (LoanManagementInvestInfo investInfo : investInfolist) {
//					LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
//					LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
//					investActLeger.setInterestReceivable(ArithUtil.sub(investActLeger.getInterestReceivable(), ArithUtil.mul(actvCashFlow.getInterestAmt(), investInfo.getHavaScale())));
//					loanManagementActLegerDao.save(investActLeger);
//				}
//			}
//			if (actvCashFlow.getCurrNum() > currAcTLedgerLoan.getCurrNum().shortValue()) {
//				// 一次性提前还款
//				repayStatus = 2;
//				// actvCashFlow.setRepayStatus(RepayStatus.一次性提前还款);
//				repayAmt = actvCashFlow.getPrincipalAmt();
//				// actvCashFlow.setAmt(actvCashFlow.getPrincipalAmt());
//				// actvCashFlow.setEditDate(DateUtil.getDateyyyyMMdd());
//				// actvCashFlow.setEditUserId(currUser.getUserId().longValue());
//
//				loanActLedger.setInterestPayable(ArithUtil.sub(loanActLedger.getInterestPayable(), actvCashFlow.getInterestAmt()));
//				// loanManagementAcTVirtualCashFlowDao.save(actvCashFlow);
//				List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
//				// ①理财人收回本金（N个理财人）
//				for (LoanManagementInvestInfo investInfo : investInfolist) {
//					LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
//					LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
//					investActLeger.setInterestReceivable(ArithUtil.sub(investActLeger.getInterestReceivable(), ArithUtil.mul(actvCashFlow.getInterestAmt(), investInfo.getHavaScale())));
//					loanManagementActLegerDao.save(investActLeger);
//				}
//			}
//			// 更新 还款计划表
//			if (actvCashFlow.getCurrNum() >= currAcTLedgerLoan.getCurrNum().shortValue()) {
//				int count = payBackUtils.updateAcVirtualCashFlow(repayAmt, DateUtil.getDateyyyyMMdd(), currUser.getUserId().longValue(), repayStatus, currOverdueFines, currOverdueInterest, overdueDays, actvCashFlow.getId());
//				if (count <= 0) {
//					throw new RuntimeException("还款进行中.....请耐心等待。");
//				}
//			}
//
//		}
//
//		List<LoanManagementAcTLedgerFinance> acTLedgerFinanceList = currAcTLedgerLoan.getAcTLedgerFinance();
//		for (LoanManagementAcTLedgerFinance loanManagementAcTLedgerFinance : acTLedgerFinanceList) {
//			loanManagementAcTLedgerFinance.setAcctStatus("9");
//			loanManagementAcTLedgerFinance.setInterestReceivable(0d);
//			loanManagementAcTLedgerFinance.setRemainNum(0l);
//		}
//		currAcTLedgerLoan.setAcctStatus("9");
//		currAcTLedgerLoan.setLastExpiry(DateUtil.getDateyyyyMMdd());
//		currAcTLedgerLoan.setNextExpiry(null);
//		currAcTLedgerLoan.setOutstanding(currSurplusPrincipal);
//		currAcTLedgerLoan.setAcTLedgerFinance(acTLedgerFinanceList);
//		currRepayLoanInfo.setStatus(new BigDecimal(5));
//
//		loanManagementActLegerDao.save(loanActLedger);
//		List<LoanManagementInvestInfo> investInfoList = currRepayLoanInfo.getInvestInfoList();
//		for (LoanManagementInvestInfo investInfo : investInfoList) {
//			investInfo.setStatus(new BigDecimal(4));
//		}
//		currRepayLoanInfo.setInvestInfoList(investInfoList);
//		loanInfoDao.save(currRepayLoanInfo);
//		List<SendMessageToInvestVO> messageList = new ArrayList<SendMessageToInvestVO>();
//
//		if (overdueDays <= 30) {
//			for (LoanManagementInvestInfo investInfo : investInfoList) {
//				SendMessageToInvestVO message = new SendMessageToInvestVO();
//				message.setInvestId(investInfo.getInvestId());
//				// 剩余本金
//				double localSurplusPrincipal = 0;
//				// 当期已还
//				if (delFlag) {
//					// 月还本息
//					message.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(0));
//					// 剩余本金
//					localSurplusPrincipal = ArithUtil.mul(investInfo.getHavaScale(), ArithUtil.add(currSurplusPrincipal, currPrincipal));
//					message.setCurrSurplusPrincipal(ObjectFormatUtil.formatCurrency(localSurplusPrincipal));
//					// 逾期违约金
//					message.setCurrOverdueFines(ObjectFormatUtil.formatCurrency(ArithUtil.mul(localSurplusPrincipal, overdueFinesComplateRate)));
//
//				} else {
//					// 月还本息
//					message.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(ArithUtil.mul(investInfo.getHavaScale(), ArithUtil.add(currPrincipal, currInterest))));
//					// 剩余本金
//					localSurplusPrincipal = ArithUtil.mul(investInfo.getHavaScale(), currSurplusPrincipal);
//					message.setCurrSurplusPrincipal(ObjectFormatUtil.formatCurrency(localSurplusPrincipal));
//					// 逾期违约金
//					message.setCurrOverdueFines(ObjectFormatUtil.formatCurrency(ArithUtil.mul(localSurplusPrincipal, overdueFinesComplateRate)));
//				}
//				messageList.add(message);
//			}
//		}
//		sendMovement(currRepayLoanInfo);
//		return messageList;
//	}

	/**
	 * 证大收取各种管理费用
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void setPayZendai(LoanManagementAcTLedger act1, String act2, LoanManagementAcTCustomer actCustomerLoan, double amount, String km1, String km2, LoanManagementUsers currUser, String memo, int payType) {
		LoanManagementAcTLedger zendActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", act2);

		LoanManagementAcTLedger loanActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("2", actCustomerLoan.getTotalAcct() + "%");// 业务类别：2:贷款分户

		act1.setAmount(ArithUtil.sub(act1.getAmount(), amount));
		zendActLedger.setAmount(ArithUtil.add(zendActLedger.getAmount(), amount));
		if (payType == 1) {
			// 贷款本金
			loanActLedger.setLoanAmount(ArithUtil.sub(loanActLedger.getLoanAmount(), amount));
		} else if (payType == 2) {
			// 应付利息
			loanActLedger.setInterestPayable(ArithUtil.sub(loanActLedger.getInterestPayable(), amount));
			// 利息支出
			loanActLedger.setInterestExpenditure(ArithUtil.add(loanActLedger.getInterestExpenditure(), amount));
		} else if (payType == 3) {
			// 逾期罚息、逾期违约金、月缴管理费
			loanActLedger.setOtherExpenditure(ArithUtil.add(loanActLedger.getOtherExpenditure(), amount));

		} else if (payType == 4) {
			// 其它支出
			loanActLedger.setOtherExpenditure(ArithUtil.add(loanActLedger.getOtherExpenditure(), amount));
		}
		/*
		 * //借款人资金账户 LoanManagementAcTLedger loanActLedger=
		 * loanManagementActLegerDao
		 * .findByBusiTypeAndAccountLike("2",actCustomerLoan
		 * .getTotalAcct()+"%");//业务类别：2：贷款； //借款人偿还理财人本金后（1）借款人资金账户现金划入贷款账户
		 * 
		 * 
		 * if(payType==1){ //贷款本金
		 * loanActLedger.setLoanAmount(ArithUtil.sub(loanActLedger
		 * .getLoanAmount(), amount)); }else if(payType==2){ //应付利息
		 * loanActLedger
		 * .setInterestPayable(ArithUtil.sub(loanActLedger.getInterestPayable(),
		 * amount)); //利息支出
		 * loanActLedger.setInterestExpenditure(ArithUtil.add(loanActLedger
		 * .getInterestExpenditure(),amount)); }
		 * 
		 * loanManagementActLegerDao.save(loanActLedger);
		 */

		// 借款人支付证大借款管理费
		loanManagementAcTFlowDao.save(setActFlow(amount, actCustomerLoan.getOpenacctTeller(), actCustomerLoan.getOpenacctOrgan(), act1.getAccount(), zendActLedger.getAccount(), km1, km2, memo));

		// 证大收取借款管理费
		/*
		 * loanManagementAcTFlowDao.save( setActFlow(amount,
		 * actCustomerLoan.getOpenacctTeller(),
		 * actCustomerLoan.getOpenacctOrgan(), zendActLedger.getAccount(),
		 * act1.getAccount(), km2, km1));
		 */
		loanManagementActLegerDao.save(loanActLedger);
		loanManagementActLegerDao.save(act1);
		loanManagementActLegerDao.save(zendActLedger);

	}

	/**
	 * 
	 * @param currRepayLoanInfo
	 * @param currPrincipal
	 * @param currInterest
	 * @param currOverdueInterest
	 * @param currOverdueFines
	 * @param currmanageFeeByMonth
	 * @param currUser
	 */
	public void rePayToZendai(LoanManagementLoanInfo currRepayLoanInfo, double currPrincipal, double currInterest, double currOverdueInterest, double currOverdueFines, double currmanageFeeByMonth, LoanManagementUsers currUser) {
		// 借款人资金账户
		LoanManagementAcTCustomer loanActCustomer = loanmanagementAcTCustomerDao.findById(currRepayLoanInfo.getUser().gettCustomerId()); // 理财人核心客户信息
		LoanManagementAcTLedger amountActledger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", loanActCustomer.getTotalAcct() + "%");// 业务类别：4：资金账户
		LoanManagementAcTLedger loanActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("2", loanActCustomer.getTotalAcct() + "%");// 业务类别：2：贷款；
		// 证大逾期违约金账户
		LoanManagementAcTLedger zendActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct3);

		double totalRepay = ArithUtil.add(ArithUtil.add(currPrincipal, currInterest), ArithUtil.add(currOverdueInterest, currOverdueFines));
		amountActledger.setAmount(ArithUtil.sub(amountActledger.getAmount(), totalRepay));
		zendActLedger.setAmount(ArithUtil.add(zendActLedger.getAmount(), currOverdueFines));
		// 借款人调账流水 借款人把 当期应还的 本金、利息、逾期罚息和逾期违约金从自己账户划拨的贷款账户
		loanManagementAcTFlowDao.save(setActFlow(currPrincipal, loanActCustomer.getOpenacctTeller(), loanActCustomer.getOpenacctOrgan(), amountActledger.getAccount(), loanActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
		loanManagementAcTFlowDao.save(setActFlow(currInterest, loanActCustomer.getOpenacctTeller(), loanActCustomer.getOpenacctOrgan(), amountActledger.getAccount(), loanActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
		loanManagementAcTFlowDao.save(setActFlow(currOverdueInterest, loanActCustomer.getOpenacctTeller(), loanActCustomer.getOpenacctOrgan(), amountActledger.getAccount(), loanActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
		// 借款人 贷款分账维护
		// 贷款本金
		loanActLedger.setLoanAmount(ArithUtil.sub(loanActLedger.getLoanAmount(), currPrincipal));
		// 应付利息
		loanActLedger.setInterestPayable(ArithUtil.sub(loanActLedger.getInterestPayable(), currInterest));
		// 利息支出
		loanActLedger.setInterestExpenditure(ArithUtil.add(loanActLedger.getInterestExpenditure(), currInterest));
		// 其他支出：＋逾期罚息＋逾期违约金＋月缴管理费
		loanActLedger.setOtherExpenditure(ArithUtil.add(loanActLedger.getOtherExpenditure(), ArithUtil.add(currOverdueInterest, currOverdueFines)));
		loanManagementActLegerDao.save(amountActledger);
		loanManagementActLegerDao.save(loanActLedger);
		loanManagementActLegerDao.save(zendActLedger);
		List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
		for (LoanManagementInvestInfo investInfo : investInfolist) {
			// 当前债权
			LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), currRepayLoanInfo.getActLedgerLoan().getCurrNum());
			// 当前债权应收本金
			double payPrinciPalByInvest = ArithUtil.mul(currPrincipal, investInfo.getHavaScale());
			// 当前债权应收利息
			double payCurrInterestByInvest = ArithUtil.mul(currInterest, investInfo.getHavaScale());
			// 当前债权应收逾期罚息
			double payCurrOverdueInterestByInvest = ArithUtil.mul(currOverdueInterest, investInfo.getHavaScale());
			LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
			LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
			LoanManagementAcTLedger investAmountActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerInvsert.getTotalAcct() + "%"); // 业务类别：4：资金账户(现金增加)
			// ①如果状态为未还款
			if (overdueClaims.getStatus().equals(new BigDecimal(1))) {

				// 现金账户金额维护
				investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), payPrinciPalByInvest));
				investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), payCurrInterestByInvest));
				investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), payCurrOverdueInterestByInvest));
				// 理财人流水记录
				loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, "偿还本金/回收本金"));
				// 调账流水
				loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
				loanManagementAcTFlowDao.save(setActFlow(payCurrInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, "偿还利息/回收利息"));
				// 调账流水
				loanManagementAcTFlowDao.save(setActFlow(payCurrInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
				loanManagementAcTFlowDao.save(setActFlow(payCurrOverdueInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.interest_overdue_out, ConstSubject.interest_overdue_in, "逾期罚息"));
				// 调账流水
				loanManagementAcTFlowDao.save(setActFlow(payCurrOverdueInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
				// // 调账流水
				// loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest,
				// actCustomerInvsert.getOpenacctTeller(),
				// actCustomerInvsert.getOpenacctOrgan(),
				// loanActLedger.getAccount(),
				// investActLeger.getAccount(),ConstSubject.adjust_account_out,
				// ConstSubject.adjust_account_in, "调账"));
				// loanManagementAcTFlowDao.save(setActFlow(payCurrInterestByInvest,
				// actCustomerInvsert.getOpenacctTeller(),
				// actCustomerInvsert.getOpenacctOrgan(),
				// loanActLedger.getAccount(),
				// investActLeger.getAccount(),ConstSubject.adjust_account_out,
				// ConstSubject.adjust_account_in, "调账"));
				// loanManagementAcTFlowDao.save(setActFlow(payCurrOverdueInterestByInvest,
				// actCustomerInvsert.getOpenacctTeller(),
				// actCustomerInvsert.getOpenacctOrgan(),
				// loanActLedger.getAccount(),
				// investActLeger.getAccount(),ConstSubject.adjust_account_out,
				// ConstSubject.adjust_account_in, "调账"));
				// 理财账户维护
				// 当前投资
				investActLeger.setDebtAmount(ArithUtil.sub(investActLeger.getDebtAmount(), payPrinciPalByInvest));
				// 应收利息
				investActLeger.setInterestReceivable(ArithUtil.sub(investActLeger.getInterestReceivable(), payCurrInterestByInvest));
				// 利息收入
				investActLeger.setInterestIncome(ArithUtil.add(investActLeger.getInterestIncome(), payCurrInterestByInvest));
				// 其它收入
				investActLeger.setOtherIncome(ArithUtil.add(investActLeger.getOtherIncome(), payCurrOverdueInterestByInvest));
				loanManagementActLegerDao.save(investActLeger);
				loanManagementActLegerDao.save(investAmountActLedger);
				overdueClaims.setStatus(new BigDecimal(3));
				// ②如果状态为为 已代偿 还款给证大
			} else if (overdueClaims.getStatus().equals(new BigDecimal(2))) {
				// 证大风险准备金账户
				LoanManagementAcTLedger zendai_acct10 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct10);
				// 证大逾期罚息账户
				LoanManagementAcTLedger zendai_acct2 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct2);
				// 借款人还 本金+利息 给证大
				zendai_acct10.setAmount(ArithUtil.add(ArithUtil.add(zendai_acct10.getAmount(), payPrinciPalByInvest), payCurrInterestByInvest));
				// 借款人还 逾期罚息给证大
				zendai_acct2.setAmount(ArithUtil.add(zendai_acct2.getAmount(), payCurrOverdueInterestByInvest));
				// 证大流水流水记录
				loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), zendai_acct10.getAccount(), ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, "偿还本金/回收本金"));
				loanManagementAcTFlowDao.save(setActFlow(payCurrInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), zendai_acct10.getAccount(), ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, "偿还利息/回收利息"));
				loanManagementAcTFlowDao.save(setActFlow(payCurrOverdueInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), zendai_acct2.getAccount(), ConstSubject.interest_overdue_out, ConstSubject.interest_overdue_in, "逾期罚息"));
				loanManagementActLegerDao.save(zendai_acct10);
				loanManagementActLegerDao.save(zendai_acct2);
				overdueClaims.setStatus(new BigDecimal(3));
				// TODO
			} else {
				// TODO
			}
			loanManagementOverdueClaimsDao.save(overdueClaims);
		}
		// loanManagementAcTFlowDao.save(setActFlow(currOverdueFines,
		// loanActCustomer.getOpenacctTeller(),
		// loanActCustomer.getOpenacctOrgan(), amountActledger.getAccount(),
		// loanActLedger.getAccount(), ConstSubject.adjust_account_out,
		// ConstSubject.adjust_account_in, "调账"));
		// 借款人支逾期违约金
		loanManagementAcTFlowDao.save(setActFlow(currOverdueFines, loanActCustomer.getOpenacctTeller(), loanActCustomer.getOpenacctOrgan(), loanActLedger.getAccount(), zendActLedger.getAccount(), ConstSubject.loan_man_fee_out, ConstSubject.loan_man_fee_in, "逾期违约金"));
	}

	/**
	 * 借款人偿还本金，利息，逾期罚息，提前还款违约金，提前还款剩余本金
	 * 
	 * @throws Exception
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void setRepayAmount(LoanManagementUsers currUser, LoanManagementLoanInfo currRepayLoanInfo, LoanManagementAcTCustomer actCustomerLoan, double currPrincipal, String km_out, String km_in, int payType, String memo) {
		// 借款人资金账户
		LoanManagementAcTLedger amountActledger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerLoan.getTotalAcct() + "%");// 业务类别：4：资金账户
		LoanManagementAcTLedger loanActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("2", actCustomerLoan.getTotalAcct() + "%");// 业务类别：2：贷款；
		// 借款人偿还理财人本金后（1）借款人资金账户现金划入贷款账户

		amountActledger.setAmount(ArithUtil.sub(amountActledger.getAmount(), currPrincipal));
		if (payType == 1) {
			// 贷款本金
			loanActLedger.setLoanAmount(ArithUtil.sub(loanActLedger.getLoanAmount(), currPrincipal));
		} else if (payType == 2) {
			// 应付利息
			loanActLedger.setInterestPayable(ArithUtil.sub(loanActLedger.getInterestPayable(), currPrincipal));
			// 利息支出
			loanActLedger.setInterestExpenditure(ArithUtil.add(loanActLedger.getInterestExpenditure(), currPrincipal));
		} else if (payType == 3) {
			// 逾期罚息
			loanActLedger.setOtherExpenditure(ArithUtil.add(loanActLedger.getOtherExpenditure(), currPrincipal));
			// 逾期罚息
			// loanActLedger.setOtherIncome(ArithUtil.add(loanActLedger.getOtherIncome(),
			// currPrincipal));
		} else if (payType == 4) {
			// 其它支出
			loanActLedger.setOtherExpenditure(ArithUtil.add(loanActLedger.getOtherExpenditure(), currPrincipal));
		} else if (payType == 5) {
			// 其它支出
			loanActLedger.setOtherExpenditure(ArithUtil.add(loanActLedger.getOtherExpenditure(), currPrincipal));
			// loanActLedger.setLoanAmount(ArithUtil.sub(loanActLedger.getLoanAmount(),
			// currPrincipal));
		} else {
			// 暂无
		}

		loanManagementActLegerDao.save(amountActledger);
		loanManagementActLegerDao.save(loanActLedger);

		// 借款人偿还理财人本金后（1）借款人资金账户现金划入贷款账户
		loanManagementAcTFlowDao.save(setActFlow(currPrincipal, actCustomerLoan.getOpenacctTeller(), actCustomerLoan.getOpenacctOrgan(), amountActledger.getAccount(), loanActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));

		List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
		// ①理财人收回本金（N个理财人）
		for (LoanManagementInvestInfo investInfo : investInfolist) {

			double payPrinciPalByInvest = ArithUtil.mul(currPrincipal, investInfo.getHavaScale());
			LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
			LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
			LoanManagementAcTLedger investAmountActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerInvsert.getTotalAcct() + "%"); // 业务类别：4：资金账户(现金增加)

			loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerLoan.getOpenacctTeller(), actCustomerLoan.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), km_out, km_in, memo));

			loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));

			investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), payPrinciPalByInvest));
			if (payType == 1) {
				// 当前投资
				investActLeger.setDebtAmount(ArithUtil.sub(investActLeger.getDebtAmount(), payPrinciPalByInvest));
			} else if (payType == 2) {
				// 应收利息
				investActLeger.setInterestReceivable(ArithUtil.sub(investActLeger.getInterestReceivable(), payPrinciPalByInvest));
				// 利息收入
				investActLeger.setInterestIncome(ArithUtil.add(investActLeger.getInterestIncome(), payPrinciPalByInvest));
			} else if (payType == 3) {
				// 其它收入
				investActLeger.setOtherIncome(ArithUtil.add(investActLeger.getOtherIncome(), payPrinciPalByInvest));
			} else if (payType == 4) {
				// 其它收入
				investActLeger.setOtherIncome(ArithUtil.add(investActLeger.getOtherIncome(), payPrinciPalByInvest));
			} else if (payType == 5) {
				// 其它收入
				investActLeger.setOtherIncome(ArithUtil.add(investActLeger.getOtherIncome(), payPrinciPalByInvest));
				// 提前还款当前投资
				// investActLeger.setDebtAmount(ArithUtil.sub(investActLeger.getDebtAmount(),
				// payPrinciPalByInvest));
			} else {
			}
			loanManagementActLegerDao.save(investActLeger);
			loanManagementActLegerDao.save(investAmountActLedger);
		}
	}

//	/**
//	 * 生成交易流水
//	 */
//	public LoanManagementAcTFlow setActFlow(double tradeAmount, String teller, String Organ, String account, String appoAcc, String acctTitle, String appoAcctTitle) {
//		LoanManagementAcTFlow actFlow = new LoanManagementAcTFlow();
//		actFlow.setTradeDate(new Timestamp(System.currentTimeMillis()));// 交易日期
//		actFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
//		actFlow.setTradeAmount(tradeAmount);// 交易金额
//		actFlow.setTradeType("1");// 交易类型:现金
//		actFlow.setTeller(teller);// 柜员号
//		actFlow.setOrgan(Organ);// 营业网点
//		actFlow.setAccount(account);// 账号
//		actFlow.setAppoAcct(appoAcc);// 对方账户
//		actFlow.setAcctTitle(acctTitle);// 科目号
//		actFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
//		actFlow.setMemo("操作科目：" + ConstSubject.getNameBySubject(acctTitle));// 备注
//		return actFlow;
//	}

	/**
	 * 生成交易流水
	 */
	public LoanManagementAcTFlow setActFlow(double tradeAmount, String teller, String Organ, String account, String appoAcc, String acctTitle, String appoAcctTitle, String memo) {
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
		actFlow.setMemo("操作科目：" + memo);// 备注
		return actFlow;
	}

	/**
	 * 还款成功,发送系统消息给投标人
	 * 
	 * @param investInfo
	 */
	public void sendSysMsgToInvest(SendMessageToInvestVO investVO) {
		DecimalFormat moneyFormat = new DecimalFormat("￥###,###.00");
		BigDecimal investId = investVO.getInvestId();
		String principanInterestMonth = investVO.getPrincipanInterestMonth();
		String currOverdueInterest = investVO.getOverdueInterest();
		Map<String, String> map = new HashMap<String, String>();
		LoanManagementInvestInfo investInfo = investInfoDao.findByInvestId(investId);
		LoanManagementUsers user = investInfo.getUser();
		LoanManagementUserInfoPerson userInfoPerson = loanManagementUserInfoPersonDao.findByUserId(user.getUserId());
		String loginName = user.getLoginName();
		String laonTitle = investInfo.getLoanInfo().getLoanTitle();
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		sysMsg.setUserId(user.getUserId());
		sysMsg.setWordId(BigDecimal.valueOf(14));
		sysMsg.setParameter1(loginName);
		sysMsg.setParameter2(laonTitle);
		sysMsg.setParameter3(principanInterestMonth);
		sysMsg.setParameter4(currOverdueInterest);
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		sysMsgDao.save(sysMsg);
	}

	/**
	 * 还款成功,短信通知投标人
	 * 
	 * @param investInfo
	 */
	public void sendMessageToInvest(SendMessageToInvestVO investVO) {
		DecimalFormat moneyFormat = new DecimalFormat("￥###,###.00");
		BigDecimal investId = investVO.getInvestId();
		String principanInterestMonth = investVO.getPrincipanInterestMonth();
		String currOverdueInterest = investVO.getOverdueInterest();
		Map<String, String> map = new HashMap<String, String>();
		LoanManagementInvestInfo investInfo = investInfoDao.findByInvestId(investId);
		LoanManagementUsers user = investInfo.getUser();
		LoanManagementUserInfoPerson userInfoPerson = loanManagementUserInfoPersonDao.findByUserId(user.getUserId());
		String loanUserphoneNo = userInfoPerson.getPhoneNo();
		String loginName = user.getLoginName();
		String laonTitle = investInfo.getLoanInfo().getLoanTitle();
		String loanIdStr = investInfo.getLoanInfo().getLoanId().toString();
		map.put("0", loginName);
		map.put("1", laonTitle);
		map.put("2", principanInterestMonth);
		map.put("3", currOverdueInterest);
		SMSSender.sendMessage("rev_pay", loanUserphoneNo, map);
	}

	/**
	 * 还款成功,更新最新动态
	 * 
	 * @param investInfo
	 */
	public void sendMovement(LoanManagementLoanInfo loanInfo) {
		HomepageUserMovement movement = new HomepageUserMovement();
		HomepageMovementWord moWord = new HomepageMovementWord();
		moWord.setWordId(new BigDecimal(5));
		movement.setUserId(loanInfo.getUser().getUserId());
		movement.setWordId(moWord);
		movement.setParameter1(loanInfo.getLoanTitle());
		movement.setUrl1("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
		movement.setMsgKind("1");
		movement.setHappenTime(new Date());
		movement.setIsDel("0");
		movementDao.save(movement);
	}

	/**
	 * 还款成功,邮件通知投标人
	 * 
	 * @param investInfo
	 */
	public void sendMailToInvest(SendMessageToInvestVO investVO) {
		DecimalFormat moneyFormat = new DecimalFormat("￥###,###.00");
		BigDecimal investId = investVO.getInvestId();
		String principanInterestMonth = investVO.getPrincipanInterestMonth();
		String currOverdueInterest = investVO.getOverdueInterest();
		Map<String, String> map = new HashMap<String, String>();
		LoanManagementInvestInfo investInfo = investInfoDao.findByInvestId(investId);
		LoanManagementUsers user = investInfo.getUser();
		LoanManagementUserInfoPerson userInfoPerson = loanManagementUserInfoPersonDao.findByUserId(user.getUserId());
		String loanUserphoneNo = userInfoPerson.getPhoneNo();
		String loginName = user.getLoginName();
		String loanIdStr = investInfo.getLoanInfo().getLoanId().toString();
		String laonTitle = investInfo.getLoanInfo().getLoanTitle();
		map.put("0", loginName);
		map.put("1", laonTitle);
		map.put("2", principanInterestMonth);
		map.put("3", currOverdueInterest);
		String messages = mimeMailService.transferMailContent("rev_pay", map);
		mimeMailService.sendNotifyMail(messages, user.getEmail(), "我收到一笔还款");
	}

	public boolean checkIsOverdue(HttpServletRequest req, String loanId) {
		LoanManagementUsers currUser = getUsersBySession(req);
		LoanManagementLoanInfo currRepayLoanInfo = loanManagementDao.getLoanInfoByLoanId(new BigDecimal(loanId), currUser).get(0);
		// 借款分户信息表id
		LoanManagementAcTLedgerLoan ledgerLoan = currRepayLoanInfo.getActLedgerLoan();
		// 逾期天数=下次结息日-当前日期
		int overdueDays = 0;
		if (ledgerLoan.getNextExpiry() != null) {

			overdueDays = FormulaSupportUtil.getOverdueDays(ledgerLoan.getNextExpiry(), DateUtil.getCurrentDate());
		}
		if (overdueDays > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 债权收购
	 * 
	 * @param investId
	 * @param loanInfoId
	 */
//	@Transactional(readOnly = false)
//	public void acquisitionProgress(BigDecimal investId, Long num) {
//		LoanManagementInvestInfo investInfo = investInfoDao.findOne(investId);
//		// 当期期数
//		// Long num = loanInfo.getActLedgerLoan().getCurrNum();
//		LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investId, num);
//		if (!overdueClaims.getStatus().equals(new BigDecimal(1))) {
//			throw new BusinessException("必需是未还款的债权才能收购，请确认信息或刷新页面重试！");
//		}
//		// 还款计划
//		List<LoanManagementAcTVirtualCashFlow> acTVirtualCashFlows = investInfo.getLoanInfo().getActLedgerLoan().getAcTVirtualCashFlows();
//		// 当期还款计划
//		LoanManagementAcTVirtualCashFlow currAcTLedgerLoan = null;
//		for (LoanManagementAcTVirtualCashFlow acTVirtualCashFlow : acTVirtualCashFlows) {
//			if (num == acTVirtualCashFlow.getCurrNum().intValue()) {
//				currAcTLedgerLoan = acTVirtualCashFlow;
//			}
//		}
//		// 当期分债权应得本金
//		double currPayPrincipal = ArithUtil.mul(currAcTLedgerLoan.getPrincipalAmt(), investInfo.getHavaScale());
//		// 当期分债权应得利息
//		double currPayInterest = ArithUtil.mul(currAcTLedgerLoan.getInterestAmt(), investInfo.getHavaScale());
//		// 证大垫付金额（包含本金和利息）
//		double zendaiPayAmount = ArithUtil.add(currPayPrincipal, currPayInterest);
//		LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
//		LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
//		LoanManagementAcTLedger investAmountActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerInvsert.getTotalAcct() + "%"); // 业务类别：4：资金账户(现金增加)
//		// 证大风险准备金账户
//		LoanManagementAcTLedger zendActLedger10 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct10);
//		// 证大逾期罚息账户
//		LoanManagementAcTLedger zendActLedger2 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct2);
//		// 证大预期违约金账户
//		LoanManagementAcTLedger zendActLedger3 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct3);
//		double zend10PrincipalToUser = 0;
//		double zend2PrincipalToUser = 0;
//		double zend3PrincipalToUser = 0;
//		double zend10InterestToUser = 0;
//		double zend2InterestToUser = 0;
//		double zend3InterestToUser = 0;
//		// ①
//		if (zendActLedger10.getAmount() >= zendaiPayAmount) {
//			investActLeger.setAmount(ArithUtil.add(investActLeger.getAmount(), zendaiPayAmount));
//			zendActLedger10.setAmount(ArithUtil.sub(zendActLedger10.getAmount(), zendaiPayAmount));
//			loanManagementActLegerDao.save(zendActLedger10);
//			// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//			loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//			// 理财人债权关系转移流水 操作科目：风险金代偿（债权转移）
//			loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", investActLeger.getAccount(), zendActLedger10.getAccount(), ConstSubject.advance_succ_transfer_out, ConstSubject.advance_succ_transfer_in, "风险金代偿（债权转移）"));
//			// 本金调账流水
//			loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
//			// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//			loanManagementAcTFlowDao.save(setActFlow(currPayInterest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//
//		} else if (ArithUtil.add(zendActLedger10.getAmount(), zendActLedger2.getAmount()) >= zendaiPayAmount) {
//			if (zendActLedger10.getAmount() >= currPayPrincipal) {
//				zend10PrincipalToUser = currPayPrincipal;
//				zend10InterestToUser = ArithUtil.sub(zendActLedger10.getAmount(), currPayPrincipal);
//				zend2InterestToUser = ArithUtil.sub(currPayInterest, zend10InterestToUser);
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//				loanManagementAcTFlowDao.save(setActFlow(zend10PrincipalToUser, "", "", zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//				// 理财人债权关系转移流水 操作科目：风险金代偿（债权转移）
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", investActLeger.getAccount(), zendActLedger10.getAccount(), ConstSubject.advance_succ_transfer_out, ConstSubject.advance_succ_transfer_in, "风险金代偿（债权转移）"));
//				// 本金调账流水
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//				loanManagementAcTFlowDao.save(setActFlow(zend10InterestToUser, "", "", zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//				loanManagementAcTFlowDao.save(setActFlow(zend2InterestToUser, "", "", zendActLedger2.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//			} else {
//				zend2PrincipalToUser = ArithUtil.sub(currPayPrincipal, zendActLedger10.getAmount());
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//				loanManagementAcTFlowDao.save(setActFlow(zend2PrincipalToUser, "", "", zendActLedger2.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//				// 理财人债权关系转移流水 操作科目：风险金代偿（债权转移）
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", investActLeger.getAccount(), zendActLedger10.getAccount(), ConstSubject.advance_succ_transfer_out, ConstSubject.advance_succ_transfer_in, "风险金代偿（债权转移）"));
//				// 本金调账流水
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//				loanManagementAcTFlowDao.save(setActFlow(currPayInterest, "", "", zendActLedger2.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//			}
//			investActLeger.setAmount(ArithUtil.add(investActLeger.getAmount(), zendaiPayAmount));
//			zendActLedger2.setAmount(ArithUtil.sub(zendActLedger2.getAmount(), ArithUtil.sub(zendaiPayAmount, zendActLedger10.getAmount())));
//			zendActLedger10.setAmount(0.0);
//			loanManagementActLegerDao.save(zendActLedger10);
//			loanManagementActLegerDao.save(zendActLedger2);
//
//		} else if (ArithUtil.add(ArithUtil.add(zendActLedger10.getAmount(), zendActLedger2.getAmount()), zendActLedger3.getAmount()) >= zendaiPayAmount) {
//			if (zendActLedger10.getAmount() >= currPayPrincipal) {
//				zend10InterestToUser = ArithUtil.sub(zendActLedger10.getAmount(), currPayPrincipal);
//				zend3InterestToUser = ArithUtil.sub(ArithUtil.sub(currPayInterest, zendActLedger2.getAmount()), zend10InterestToUser);
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//				// 理财人债权关系转移流水 操作科目：风险金代偿（债权转移）
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", investActLeger.getAccount(), zendActLedger10.getAccount(), ConstSubject.advance_succ_transfer_out, ConstSubject.advance_succ_transfer_in, "风险金代偿（债权转移）"));
//				// 本金调账流水
//				loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//				loanManagementAcTFlowDao.save(setActFlow(zend10InterestToUser, "", "", zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//				loanManagementAcTFlowDao.save(setActFlow(zendActLedger2.getAmount(), "", "", zendActLedger2.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//				// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//				loanManagementAcTFlowDao.save(setActFlow(zend3InterestToUser, "", "", zendActLedger3.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//			} else {
//				if (ArithUtil.add(zendActLedger10.getAmount(), zendActLedger2.getAmount()) >= currPayPrincipal) {
//					zend2PrincipalToUser = ArithUtil.sub(currPayPrincipal, zendActLedger10.getAmount());
//					zend2InterestToUser = ArithUtil.sub(zendActLedger2.getAmount(), zend2PrincipalToUser);
//					zend3InterestToUser = ArithUtil.sub(currPayInterest, zend2InterestToUser);
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//					loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//					loanManagementAcTFlowDao.save(setActFlow(zend2PrincipalToUser, "", "", zendActLedger2.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//					// 理财人债权关系转移流水 操作科目：风险金代偿（债权转移）
//					loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", investActLeger.getAccount(), zendActLedger10.getAccount(), ConstSubject.advance_succ_transfer_out, ConstSubject.advance_succ_transfer_in, "风险金代偿（债权转移）"));
//					// 本金调账流水
//					loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//					loanManagementAcTFlowDao.save(setActFlow(zend2InterestToUser, "", "", zendActLedger2.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//					loanManagementAcTFlowDao.save(setActFlow(zend3InterestToUser, "", "", zendActLedger3.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//				} else {
//					zend3PrincipalToUser = ArithUtil.sub(ArithUtil.sub(currPayPrincipal, zendActLedger10.getAmount()), zendActLedger2.getAmount());
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//					loanManagementAcTFlowDao.save(setActFlow(zendActLedger10.getAmount(), "", "", zendActLedger10.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//					loanManagementAcTFlowDao.save(setActFlow(zendActLedger2.getAmount(), "", "", zendActLedger2.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（本金）
//					loanManagementAcTFlowDao.save(setActFlow(zend3PrincipalToUser, "", "", zendActLedger3.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（本金）"));
//					// 理财人债权关系转移流水 操作科目：风险金代偿（债权转移）
//					loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "", "", investActLeger.getAccount(), zendActLedger10.getAccount(), ConstSubject.advance_succ_transfer_out, ConstSubject.advance_succ_transfer_out, "：风险金代偿（债权转移）"));
//					// 本金调账流水
//					loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
//					// 证大风险金代偿流水记录 操作科目：风险金代偿（利息）
//					loanManagementAcTFlowDao.save(setActFlow(currPayInterest, "", "", zendActLedger3.getAccount(), investActLeger.getAccount(), ConstSubject.advance_out, ConstSubject.advance_in, "风险金代偿（利息）"));
//				}
//			}
//			investActLeger.setAmount(ArithUtil.add(investActLeger.getAmount(), zendaiPayAmount));
//			zendActLedger3.setAmount(ArithUtil.sub(zendActLedger3.getAmount(), ArithUtil.sub(zendaiPayAmount, ArithUtil.add(zendActLedger10.getAmount(), zendActLedger2.getAmount()))));
//			zendActLedger10.setAmount(0.0);
//			zendActLedger2.setAmount(0.0);
//			loanManagementActLegerDao.save(zendActLedger10);
//			loanManagementActLegerDao.save(zendActLedger2);
//			loanManagementActLegerDao.save(zendActLedger3);
//		} else {
//			throw new BusinessException("风险金账户余额不足，无法垫付！");
//		}
//		//
//		// loanManagementAcTFlowDao.save(setActFlow(zendaiPayAmount, "",
//		// "",investActLeger.getAccount(),zendActLedger10.getAccount(),
//		// ConstSubject.advance_succ_transfer_out,
//		// ConstSubject.advance_succ_transfer_in, "操作科目：风险金代偿（本金）"));
//		investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), zendaiPayAmount));
//		investActLeger.setAmount(ArithUtil.sub(investActLeger.getAmount(), zendaiPayAmount));
//		// 理财账户维护
//		// 当前投资
//		investActLeger.setDebtAmount(ArithUtil.sub(investActLeger.getDebtAmount(), currPayPrincipal));
//		// 应收利息
//		investActLeger.setInterestReceivable(ArithUtil.sub(investActLeger.getInterestReceivable(), currPayInterest));
//		// 利息收入
//		investActLeger.setInterestIncome(ArithUtil.add(investActLeger.getInterestIncome(), currPayInterest));
//
//		loanManagementActLegerDao.save(investAmountActLedger);
//		loanManagementActLegerDao.save(investActLeger);
//		// 本金调账流水
//		// loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal,
//		// actCustomerInvsert.getOpenacctTeller(),
//		// actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(),
//		// investAmountActLedger.getAccount(),ConstSubject.adjust_account_out,
//		// ConstSubject.adjust_account_in, "调账"));
//		// 利息调账
//		loanManagementAcTFlowDao.save(setActFlow(currPayInterest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
//
//		// 设置状态为2“已垫付”
//		// overdueClaims.setStatus(new BigDecimal(2));
//		// 设置当前债权为垫付过的分债权
//		// overdueClaims.setIsAdvanced(BigDecimal.ONE);
//		// overdueClaims.setPayAmount(zendaiPayAmount);
//		// 理财人债权关系转移流水 操作科目：风险金代偿（债权转移）
//		// loanManagementAcTFlowDao.save(setActFlow(currPayPrincipal, "",
//		// "",investActLeger.getAccount(),zendActLedger10.getAccount(),
//		// ConstSubject.advance_out, ConstSubject.advance_in,
//		// "操作科目：风险金代偿（债权转移）"));
//		// overdueClaims.setEditTime(new Date());
//		int count = payBackUtils.updateOverdueClaims(new BigDecimal(2), BigDecimal.ONE, zendaiPayAmount, new Date(), investId, num);
//		if (count <= 0) {
//			throw new BusinessException("必需是未还款的债权才能收购，请确认信息或刷新页面重试！");
//		}
//		// loanManagementOverdueClaimsDao.save(overdueClaims);
//	}
}
