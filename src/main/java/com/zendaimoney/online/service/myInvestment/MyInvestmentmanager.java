package com.zendaimoney.online.service.myInvestment;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.AreaUtil;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.Formula;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.dao.financial.FinancialInvestInfoDao;
import com.zendaimoney.online.dao.fundDetail.FundDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementOverdueClaimsDao;
import com.zendaimoney.online.dao.myInvestment.MyInvestmentAcTFlowDao;
import com.zendaimoney.online.dao.myInvestment.MyInvestmentAcTLedgerDao;
import com.zendaimoney.online.dao.myInvestment.MyInvestmentAcTLedgerFinanceDao;
import com.zendaimoney.online.dao.myInvestment.MyInvestmentAcTVirtualCashFlowDao;
import com.zendaimoney.online.dao.myInvestment.MyInvestmentInvestInfoDao;
import com.zendaimoney.online.dao.myInvestment.MyInvestmentLateInfoDao;
import com.zendaimoney.online.dao.myInvestment.MyInvestmentUserDao;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.entity.financial.FinancialInvestInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagentOverdueClaims;
import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTFlow;
import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTLedger;
import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTLedgerFinance;
import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTVirtualCashFlow;
import com.zendaimoney.online.entity.myInvestment.MyInvestmentInvestInfo;
import com.zendaimoney.online.entity.myInvestment.MyInvestmentUsers;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentLctjVo;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentSkzVo;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentVo;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentYskVo;

@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class MyInvestmentmanager {

	@Autowired
	MyInvestmentInvestInfoDao investInfoDao;
	@Autowired
	MyInvestmentAcTVirtualCashFlowDao acTVirtualCashFlowDao;
	@Autowired
	MyInvestmentLateInfoDao lateInfoDao;
	@Autowired
	MyInvestmentAcTFlowDao acTFlowDao;
	@Autowired
	MyInvestmentUserDao userDao;
	@Autowired
	MyInvestmentAcTLedgerDao acTLedgerDao;
	@Autowired
	private FundDao dao;
	@Autowired
	MyInvestmentAcTLedgerFinanceDao acTLedgerFinanceDao;
	@Autowired
	FinancialInvestInfoDao financialInvestInfoDao;
//	@Autowired
//	private CommonDao commonDao;
	@Autowired
	private RateCommonUtil rateCommonUtil;
	@Autowired
	private LoanManagementOverdueClaimsDao loanManagementOverdueClaimsDao;

	// 投标中的借款
	public List<MyInvestmentVo> getTbzLoan(HttpServletRequest req) {

		HttpSession session = req.getSession();
		BigDecimal userId = (BigDecimal) session.getAttribute("curr_login_user_id");
		// status = 2招标中
		String status = "2";
		List<MyInvestmentVo> voList = new ArrayList<MyInvestmentVo>();
		List<MyInvestmentInvestInfo> list = investInfoDao.findByUserIdAndStatus(userId, status);
		for (int i = 0; i < list.size(); i++) {
			MyInvestmentInvestInfo dao = list.get(i);
			MyInvestmentVo vo = new MyInvestmentVo();
			try {
				PropertyUtils.copyProperties(vo, dao);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			String area = AreaUtil.findArea(dao.getLoanId().getUserId().getMyInvestmentUserInfoPerson().getIdentityNo());
			vo.setArea(area);
			Date date = new Date();
			Date dates = dao.getLoanId().getStartInvestTime();

			Date startDate = new java.util.Date();
			startDate.setDate(dates.getDate());
			startDate.setYear(dates.getYear());
			startDate.setMonth(dates.getMonth());
			startDate.setHours(dates.getHours());
			startDate.setMinutes(dates.getMinutes());
			startDate.setSeconds(dates.getSeconds());
			date = DateUtil.getSevenDaysDate(startDate);

			List<FinancialInvestInfo> invList = financialInvestInfoDao.findByLoanId(dao.getLoanId().getLoanId());
			if (dao.getLoanId().getStatus().equals(BigDecimal.ONE)) {
				double leaveMoney = 0d;
				for (FinancialInvestInfo invInfo : invList) {
					leaveMoney = leaveMoney + invInfo.getInvestAmount();
				}
				double schedule = 0d;
				if (leaveMoney != 0) {
					schedule = Calculator.div(leaveMoney, dao.getLoanId().getLoanAmount());
				}

				// 进度
				vo.setTempo(ObjectFormatUtil.formatPercent(schedule * 100, "##,#0.00"));
				vo.setLeaveDate(DateUtil.show(new Date(), date));

			} else if (dao.getLoanId().getStatus().equals(BigDecimal.valueOf(3)) || dao.getLoanId().getStatus().equals(BigDecimal.valueOf(8))) {
				vo.setTempo("0");
				vo.setLeaveDate(DateUtil.show(new Date(), date));

			} else {
				vo.setTempo("100");
				vo.setLeaveDate("0 天");

			}

			voList.add(vo);
		}
		return voList;
	}

	/**
	 * @author Ray
	 * @date 2013-3-5 下午4:15:18
	 * @param userId
	 * @return description: 重写收款中的借款
	 */
	public List<MyInvestmentSkzVo> getSkzLoan(BigDecimal userId) {
		
		// 正常回收
		List<MyInvestmentSkzVo> normalList = new ArrayList<MyInvestmentSkzVo>();
		List<MyInvestmentInvestInfo> investInfolist = investInfoDao.findByUserIdAndStatus(userId, "3");
		MyInvestmentSkzVo skzVo = null;
		for (int i = 0; i < investInfolist.size(); i++) {
			LoanRateVO rateFree = rateCommonUtil.getLoanCoRate(investInfolist.get(i).getLoanId().getLoanId().longValue());
			MyInvestmentInvestInfo investInfo = investInfolist.get(i);
			// 获取期数
			int currNum = Integer.valueOf(String.valueOf(investInfo.getLedgerFinanceId().getLoanAcct().getCurrNum()));
			// 根据借款ID获取虚拟现金流水表List
			List<MyInvestmentAcTVirtualCashFlow> cashFlowList = acTVirtualCashFlowDao.findByLoanId(investInfo.getLedgerFinanceId().getLoanAcct().getId());
			// 根据时间重新整理List
			/** 排序类 */
			Comparator<MyInvestmentAcTVirtualCashFlow> comp = new Comparator<MyInvestmentAcTVirtualCashFlow>() {
				@Override
				public int compare(MyInvestmentAcTVirtualCashFlow o1, MyInvestmentAcTVirtualCashFlow o2) {
					MyInvestmentAcTVirtualCashFlow ac1 = (MyInvestmentAcTVirtualCashFlow) o1;
					MyInvestmentAcTVirtualCashFlow ac2 = (MyInvestmentAcTVirtualCashFlow) o2;
					if (ac1.getCurrNum() > ac2.getCurrNum())
						return 1;
					else
						return 0;
				}
			};
			Collections.sort(cashFlowList, comp);

			// 获取当前期的还款计划
			MyInvestmentAcTVirtualCashFlow cashFlow = cashFlowList.get(currNum - 1);
			if (cashFlow.getRepayDay().after(DateUtil.getDateyyyyMMdd())) {
				skzVo = new MyInvestmentSkzVo();
				skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
				skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
				skzVo.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
				String monthAmount = ObjectFormatUtil.formatCurrency((cashFlow.getPrincipalAmt() + cashFlow.getInterestAmt()) * investInfo.getHavaScale());
				skzVo.setMonthPrincipalInterest(monthAmount);

				double remainderPrincipal = 0.0;
				for (MyInvestmentAcTVirtualCashFlow af : cashFlowList) {
					// 假如期数大约当前期数，则加入剩余未还
					if (af.getCurrNum() > cashFlow.getCurrNum()) {
						remainderPrincipal = ArithUtil.add(remainderPrincipal, af.getPrincipalAmt());
					}
				}
				String surplusPrincipal = ObjectFormatUtil.formatCurrency(remainderPrincipal * investInfo.getHavaScale());
				skzVo.setSurplusPrincipal(surplusPrincipal);
				skzVo.setLateDate("0");
				String lateRate = ObjectFormatUtil.formatCurrency(0.0);
				skzVo.setLateRate(lateRate);
				normalList.add(skzVo);
			} else if (DateUtil.setDateyyyyMMDD(cashFlow.getRepayDay()).equals(DateUtil.getDateyyyyMMdd())) {
				skzVo = new MyInvestmentSkzVo();
				skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
				skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
				skzVo.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
				String monthAmount = ObjectFormatUtil.formatCurrency((cashFlow.getPrincipalAmt() + cashFlow.getInterestAmt()) * investInfo.getHavaScale());
				skzVo.setMonthPrincipalInterest(monthAmount);

				double remainderPrincipal = 0.0;
				for (MyInvestmentAcTVirtualCashFlow af : cashFlowList) {
					// 假如期数大约当前期数，则加入剩余未还
					if (af.getCurrNum() > cashFlow.getCurrNum()) {
						remainderPrincipal = ArithUtil.add(remainderPrincipal, af.getPrincipalAmt());
					}
				}
				String surplusPrincipal = ObjectFormatUtil.formatCurrency(remainderPrincipal * investInfo.getHavaScale());
				skzVo.setSurplusPrincipal(surplusPrincipal);
				skzVo.setLateDate("0");
				String lateRate = ObjectFormatUtil.formatCurrency(0.0);
				skzVo.setLateRate(lateRate);
				normalList.add(skzVo);
			} else {
				// 遍历整个虚拟现金流水表,从中获取逾期期数
				for (MyInvestmentAcTVirtualCashFlow actVirtualCashFlow : cashFlowList) {
					// 还款状态为未还款并且当前时间在还款时间之后 为逾期
					if (actVirtualCashFlow.getRepayStatus() == RepayStatus.未还款 && DateUtil.getDateyyyyMMdd().after(actVirtualCashFlow.getRepayDay())) {

						// 根据理财信息ID和分债权期数查询分债权信息
						LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), actVirtualCashFlow.getCurrNum().longValue());
						if (overdueClaims != null && overdueClaims.getStatus().equals(new BigDecimal(2))) {
							continue;
						}
						skzVo = new MyInvestmentSkzVo();
						skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
						skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
						skzVo.setReturnDate(DateUtil.getYMDTime(actVirtualCashFlow.getRepayDay()));
						String monthAmount = ObjectFormatUtil.formatCurrency((actVirtualCashFlow.getPrincipalAmt() + actVirtualCashFlow.getInterestAmt()) * investInfo.getHavaScale());
						skzVo.setMonthPrincipalInterest(monthAmount);

						double remainderPrincipal = 0.0;
						for (MyInvestmentAcTVirtualCashFlow af : cashFlowList) {
							// 假如期数大约当前期数，则加入剩余未还
							if (af.getCurrNum() > actVirtualCashFlow.getCurrNum()) {
								remainderPrincipal = ArithUtil.add(remainderPrincipal, af.getPrincipalAmt());
							}
						}
						// 剩余未还*占比=当前理财人剩余未还
						String surplusPrincipal = ObjectFormatUtil.formatCurrency(remainderPrincipal * investInfo.getHavaScale());
						skzVo.setSurplusPrincipal(surplusPrincipal);
						int lateDate = FormulaSupportUtil.getOverdueDays(DateUtil.getDateYYYYMMDD(actVirtualCashFlow.getRepayDay(), "yyyy-MM-dd"), DateUtil.getCurrentDate());
						skzVo.setLateDate(String.valueOf(lateDate));
						Long term = investInfo.getLedgerFinanceId().getLoanAcct().getTotalNum() - actVirtualCashFlow.getCurrNum().longValue() + 1;
						BigDecimal overdueRate = BigDecimal.ZERO;
						if (lateDate > 30) {
							overdueRate = rateFree.getOverdueSeriousInterest();
						} else {
							overdueRate = rateFree.getOverdueInterest();
						}
						double rate = Formula.calOverdueInterest(lateDate, Integer.valueOf(term.toString()), ArithUtil.add(actVirtualCashFlow.getPrincipalAmt(), actVirtualCashFlow.getInterestAmt()), overdueRate.doubleValue());
						String lateRate = ObjectFormatUtil.formatCurrency(ArithUtil.mul(investInfo.getHavaScale(), rate));
						skzVo.setLateRate(lateRate);
						normalList.add(skzVo);
					}
					// 还款状态为未还款且还款时间与当前时间相同，是正常的还款，不显示下一次的还款的 or 还款状态为未还款并且
					// 还款时间在当前时间之后，还款时间在当前时间加1个月之内的 为下一次正常的还款
					if (actVirtualCashFlow.getRepayStatus() == RepayStatus.未还款 && DateUtil.setDateyyyyMMDD(actVirtualCashFlow.getRepayDay()).equals(DateUtil.getDateyyyyMMdd())) {
						skzVo = new MyInvestmentSkzVo();
						skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
						skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
						skzVo.setReturnDate(DateUtil.getYMDTime(actVirtualCashFlow.getRepayDay()));
						String monthAmount = ObjectFormatUtil.formatCurrency((actVirtualCashFlow.getPrincipalAmt() + actVirtualCashFlow.getInterestAmt()) * investInfo.getHavaScale());
						skzVo.setMonthPrincipalInterest(monthAmount);
						double remainderPrincipal = 0.0;
						for (MyInvestmentAcTVirtualCashFlow af : cashFlowList) {
							// 假如期数大约当前期数，则加入剩余未还
							if (af.getCurrNum() > actVirtualCashFlow.getCurrNum()) {
								remainderPrincipal = ArithUtil.add(remainderPrincipal, af.getPrincipalAmt());
							}
						}
						String surplusPrincipal = ObjectFormatUtil.formatCurrency(remainderPrincipal * investInfo.getHavaScale());
						skzVo.setSurplusPrincipal(surplusPrincipal);
						skzVo.setLateDate("0");
						String lateRate = ObjectFormatUtil.formatCurrency(0.0);
						skzVo.setLateRate(lateRate);
						normalList.add(skzVo);
						break;
					} else if (actVirtualCashFlow.getRepayStatus() == RepayStatus.未还款 && actVirtualCashFlow.getRepayDay().after(DateUtil.getDateyyyyMMdd()) && DateUtil.getAfterMonth(DateUtil.getDateyyyyMMdd(), 1).getTime() >= actVirtualCashFlow.getRepayDay().getTime()) {
						skzVo = new MyInvestmentSkzVo();
						skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
						skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
						skzVo.setReturnDate(DateUtil.getYMDTime(actVirtualCashFlow.getRepayDay()));
						String monthAmount = ObjectFormatUtil.formatCurrency((actVirtualCashFlow.getPrincipalAmt() + actVirtualCashFlow.getInterestAmt()) * investInfo.getHavaScale());
						skzVo.setMonthPrincipalInterest(monthAmount);
						double remainderPrincipal = 0.0;
						for (MyInvestmentAcTVirtualCashFlow af : cashFlowList) {
							// 假如期数大约当前期数，则加入剩余未还
							if (af.getCurrNum() > actVirtualCashFlow.getCurrNum()) {
								remainderPrincipal = ArithUtil.add(remainderPrincipal, af.getPrincipalAmt());
							}
						}
						String surplusPrincipal = ObjectFormatUtil.formatCurrency(remainderPrincipal * investInfo.getHavaScale());
						skzVo.setSurplusPrincipal(surplusPrincipal);
						skzVo.setLateDate("0");
						String lateRate = ObjectFormatUtil.formatCurrency(0.0);
						skzVo.setLateRate(lateRate);
						normalList.add(skzVo);
					}
				}
			}
		}
		return normalList;
	}

	/**
	 * 收款中的借款
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	@Deprecated
	public List<MyInvestmentSkzVo> getSkzLoan2(BigDecimal userId) {
		

		// 正常回收
		List<MyInvestmentSkzVo> normalList = new ArrayList<MyInvestmentSkzVo>();
		// 逾期回收
		List<MyInvestmentSkzVo> lateList = new ArrayList<MyInvestmentSkzVo>();
		List<MyInvestmentInvestInfo> investInfolist = investInfoDao.findByUserIdAndStatus(userId, "3");
		for (int i = 0; i < investInfolist.size(); i++) {
			LoanRateVO rateFree = rateCommonUtil.getLoanCoRate(investInfolist.get(i).getLoanId().getLoanId().longValue());
			MyInvestmentInvestInfo investInfo = investInfolist.get(i);
			String currNum = String.valueOf(investInfo.getLedgerFinanceId().getLoanAcct().getCurrNum());
			MyInvestmentAcTVirtualCashFlow cashFlow = acTVirtualCashFlowDao.findByLoanIdAndCurrNum(investInfo.getLedgerFinanceId().getLoanAcct().getId(), Short.valueOf(currNum));

			if (cashFlow.getRepayDay().after(DateUtil.getDateyyyyMMdd())) {
				// 正常回收记录
				MyInvestmentSkzVo skzVo = new MyInvestmentSkzVo();
				skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
				skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
				skzVo.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
				String monthAmount = ObjectFormatUtil.formatCurrency((cashFlow.getPrincipalAmt() + cashFlow.getInterestAmt()) * investInfo.getHavaScale());
				skzVo.setMonthPrincipalInterest(monthAmount);
				String surplusPrincipal = ObjectFormatUtil.formatCurrency((investInfo.getLedgerFinanceId().getDebtAmount() - cashFlow.getPrincipalAmt() * investInfo.getHavaScale()));
				skzVo.setSurplusPrincipal(surplusPrincipal);
				skzVo.setLateDate("0");
				String lateRate = ObjectFormatUtil.formatCurrency(0.0);
				skzVo.setLateRate(lateRate);
				normalList.add(skzVo);
			} else {
				// 逾期记录
				List<MyInvestmentAcTVirtualCashFlow> cashFlowLaterList = acTVirtualCashFlowDao.findNoteByStatus(investInfo.getLedgerFinanceId().getLoanAcct().getId(), DateUtil.getDateyyyyMMdd());
				// 待回收
				// MyInvestmentAcTVirtualCashFlow cashFlowNormal =
				// acTVirtualCashFlowDao
				// .findNormalNote(investInfo.getLedgerFinanceId().getLoanAcct()
				// .getId(), DateUtil.getDateyyyyMMdd(), DateUtil
				// .getAfterMonth(DateUtil.getDateyyyyMMdd(),1)).get(0);
				List<MyInvestmentAcTVirtualCashFlow> acTVirtualCashFlows = acTVirtualCashFlowDao.findNormalNote(investInfo.getLedgerFinanceId().getLoanAcct().getId(), DateUtil.getDateyyyyMMdd(), DateUtil.getAfterMonth(DateUtil.getDateyyyyMMdd(), 1));
				for (int k = 0; k < cashFlowLaterList.size(); k++) {
					MyInvestmentAcTVirtualCashFlow cf = cashFlowLaterList.get(k);
					LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), cf.getCurrNum().longValue());
					if (overdueClaims != null && overdueClaims.getStatus().equals(new BigDecimal(2))) {
						continue;
					}
					MyInvestmentSkzVo skzVo = new MyInvestmentSkzVo();

					skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
					skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
					skzVo.setReturnDate(DateUtil.getYMDTime(cf.getRepayDay()));
					String monthAmount = ObjectFormatUtil.formatCurrency((cf.getPrincipalAmt() + cf.getInterestAmt()) * investInfo.getHavaScale());
					skzVo.setMonthPrincipalInterest(monthAmount);
					String surplusPrincipal = ObjectFormatUtil.formatCurrency((investInfo.getLedgerFinanceId().getDebtAmount() - cf.getPrincipalAmt() * investInfo.getHavaScale()));
					skzVo.setSurplusPrincipal(surplusPrincipal);
					int lateDate = DateUtil.getDays(cf.getRepayDay(), DateUtil.getDateyyyyMMdd());
					skzVo.setLateDate(String.valueOf(lateDate));
					Long term = investInfo.getLedgerFinanceId().getLoanAcct().getTotalNum() - cf.getCurrNum().longValue() + 1;
					BigDecimal overdueRate = BigDecimal.ZERO;
					if (lateDate > 30) {
						overdueRate = rateFree.getOverdueSeriousInterest();
					} else {
						overdueRate = rateFree.getOverdueInterest();
					}
					double rate = Formula.calOverdueInterest(lateDate, Integer.valueOf(term.toString()), ArithUtil.add(cf.getPrincipalAmt(), cf.getInterestAmt()), overdueRate.doubleValue());
					String lateRate = ObjectFormatUtil.formatCurrency(ArithUtil.mul(investInfo.getHavaScale(), rate));
					skzVo.setLateRate(lateRate);
					lateList.add(skzVo);
				}
				if (acTVirtualCashFlows.size() > 0) {
					MyInvestmentAcTVirtualCashFlow cashFlowNormal = acTVirtualCashFlows.get(0);
					MyInvestmentSkzVo skzVo = new MyInvestmentSkzVo();

					skzVo.setLoanNo(investInfo.getLoanId().getLoanId().toString());
					skzVo.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
					skzVo.setReturnDate(DateUtil.getYMDTime(cashFlowNormal.getRepayDay()));
					String monthAmount = ObjectFormatUtil.formatCurrency((cashFlowNormal.getPrincipalAmt() + cashFlowNormal.getInterestAmt()) * investInfo.getHavaScale());
					skzVo.setMonthPrincipalInterest(monthAmount);
					String surplusPrincipal = ObjectFormatUtil.formatCurrency((investInfo.getLedgerFinanceId().getDebtAmount() - cashFlowNormal.getPrincipalAmt() * investInfo.getHavaScale()));
					skzVo.setSurplusPrincipal(surplusPrincipal);
					skzVo.setLateDate("0");
					String lateRate = ObjectFormatUtil.formatCurrency(0.0);
					skzVo.setLateRate(lateRate);
					normalList.add(skzVo);
				}

			}
		}
		normalList.addAll(lateList);
		return normalList;
	}

	/**
	 * @author 不详
	 * @date 2012-12-11 上午9:55:07
	 * @param userId
	 * @return description:已收款 2013-1-11 Ray 修改bug 1151
	 *         修正收款总额的取值，增加逾期罚息和提前一次性还款违约金处理
	 */
	public List<MyInvestmentYskVo> getYsk(BigDecimal userId) {
		List<MyInvestmentYskVo> yskList = new ArrayList<MyInvestmentYskVo>();
//		Rate rate = commonDao.getRate(1l);
//		double overdueFinesComplateRate = rate.getEarlyFines().doubleValue();

		// 已经结标
		String status = "4";
		List<MyInvestmentInvestInfo> investInfolist = investInfoDao.findByUserIdAndStatus(userId, status);
		for (MyInvestmentInvestInfo investInfo : investInfolist) {
			LoanRateVO rateFree = rateCommonUtil.getLoanCoRate(investInfo.getLoanId().getLoanId().longValue());
			MyInvestmentYskVo ysk = new MyInvestmentYskVo();
			ysk.setLoanNo(investInfo.getLoanId().getLoanId().toString());
			ysk.setLoanAcc(investInfo.getLoanId().getUserId().getLoginName());
			ysk.setLoanDate(DateUtil.getYMDTime(investInfo.getLedgerFinanceId().getDueDate()));
			ysk.setLoanAmount(ObjectFormatUtil.formatCurrency(investInfo.getLedgerFinanceId().getDebtAmount()));
			ysk.setRate(ObjectFormatUtil.formatPercent(investInfo.getLoanId().getYearRate()));
			ysk.setLoanDeadline(investInfo.getLedgerFinanceId().getTotalnumPayments().toString());
			double recoverAmountBx = 0d; // 回收本息
			double recoverAmountFx = 0d; // 回收罚息
			List<MyInvestmentAcTVirtualCashFlow> cashFlowList = acTVirtualCashFlowDao.findByLoanId(investInfo.getLedgerFinanceId().getLoanAcct().getId());
			for (MyInvestmentAcTVirtualCashFlow cashFlow : cashFlowList) {
				// 在正常还款、逾期还款、和已垫付情况下 ，还款额=还款本金+还款利息+逾期违约金
				if (RepayStatus.正常还款.equals(cashFlow.getRepayStatus()) || RepayStatus.逾期还款.equals(cashFlow.getRepayStatus()) || RepayStatus.已垫付.equals(cashFlow.getRepayStatus())) {
					// 逾期还款有2种情况
					if (RepayStatus.逾期还款.equals(cashFlow.getRepayStatus())) {
						List<LoanManagentOverdueClaims> list = loanManagementOverdueClaimsDao.findByLoanIdAndNumAndIsAdvanced(investInfo.getLoanId().getLoanId(), Long.parseLong(cashFlow.getCurrNum().toString()), BigDecimal.ONE);
						// 如果List>0，查到有代偿记录，则进行减去代偿金
						if (list.size() > 0) {
							// 已代偿的，（本金+利息）*占比
							recoverAmountBx = (cashFlow.getPrincipalAmt() + cashFlow.getInterestAmt()) * investInfo.getHavaScale() + recoverAmountBx;
						} else {
							recoverAmountBx = (cashFlow.getPrincipalAmt() + cashFlow.getInterestAmt()) * investInfo.getHavaScale() + cashFlow.getOverDueInterestAmount() * investInfo.getHavaScale() + recoverAmountBx;
						}
					} else {
						recoverAmountBx = (cashFlow.getPrincipalAmt() + cashFlow.getInterestAmt()) * investInfo.getHavaScale() + cashFlow.getOverDueInterestAmount() * investInfo.getHavaScale() + recoverAmountBx;
					}
					// 一次性提前还款：还款额=还款本金+提前还款违约金
				} else if (RepayStatus.一次性提前还款.equals(cashFlow.getRepayStatus())) {
					// 本金 占比 本金 提前违约金费率 占比
					recoverAmountBx = recoverAmountBx + cashFlow.getPrincipalAmt() * investInfo.getHavaScale() + cashFlow.getPrincipalAmt() * rateFree.getEarlyFines().doubleValue() * investInfo.getHavaScale();
				}

			}

			// 逾期罚息
			// String kind = "1";
			// List<MyInvestmentLateInfo> lateList =
			// lateInfoDao.findByLoanIdAndKind(BigDecimal.valueOf(investInfo.getLedgerFinanceId().getLoanAcct().getId()),
			// kind);
			// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!list长度："+lateList.size());
			// for (MyInvestmentLateInfo lateInfo : lateList) {
			// recoverAmountFx = recoverAmountFx +
			// (Double.valueOf(lateInfo.getAmount().longValue())*investInfo.getHavaScale());
			// System.out.println(" 罚息金额："+recoverAmountFx);
			// }
			ysk.setRecoverAmount(ObjectFormatUtil.formatCurrency(recoverAmountBx + recoverAmountFx));
			yskList.add(ysk);
		}
		return yskList;
	}

	/**
	 * 回帐查询(详细列表)
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<MyInvestmentSkzVo> getHzcxDetailSearch(BigDecimal userId, String startDate, String endDate) {
		List<MyInvestmentSkzVo> voList = new ArrayList<MyInvestmentSkzVo>();
		// 还款中
		String status = "3";
		List<MyInvestmentInvestInfo> investInfoList = investInfoDao.findByUserIdAndStatus(userId, status);
		List<MyInvestmentAcTVirtualCashFlow> cfAllList = null;
		Double allAmountSearch = 0d;
		for (MyInvestmentInvestInfo invest : investInfoList) {
			MyInvestmentAcTLedgerFinance actLedegrFinace = invest.getLedgerFinanceId();
			// acTLedgerFinanceDao.findById(invest.getInvestId().longValue());
			Double debtProportion = actLedegrFinace.getDebtProportion();
			if (startDate == "" && endDate == "") {
				cfAllList = acTVirtualCashFlowDao.findFutureAllNote(invest.getLedgerFinanceId().getLoanAcct().getId(), new Date());
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}

			}
			if (startDate != "" && endDate == "") {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date r = null;
				try {
					r = formatter.parse(startDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cfAllList = acTVirtualCashFlowDao.findFutureAllNote(invest.getLedgerFinanceId().getLoanAcct().getId(), r);
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}
			}
			if (startDate == "" && endDate != "") {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date r = null;
				try {
					r = formatter.parse(endDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cfAllList = acTVirtualCashFlowDao.findNote(invest.getLedgerFinanceId().getLoanAcct().getId(), r);
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}
			}
			if (startDate != "" && endDate != "") {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date s = null;
				Date e = null;
				try {
					s = formatter.parse(startDate);
					e = formatter.parse(endDate);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				cfAllList = acTVirtualCashFlowDao.findNormalNote(invest.getLedgerFinanceId().getLoanAcct().getId(), s, e);
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}
			}

		}
		return voList;
	}

	/**
	 * 回帐查询(详细列表)
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Double getHzcxDetailSearchTotal(BigDecimal userId, String startDate, String endDate) {
		List<MyInvestmentSkzVo> voList = new ArrayList<MyInvestmentSkzVo>();
		// 还款中
		String status = "3";
		List<MyInvestmentInvestInfo> investInfoList = investInfoDao.findByUserIdAndStatus(userId, status);
		List<MyInvestmentAcTVirtualCashFlow> cfAllList = null;
		Double allAmountSearch = 0D;
		for (MyInvestmentInvestInfo invest : investInfoList) {
			MyInvestmentAcTLedgerFinance actLedegrFinace = invest.getLedgerFinanceId();
			// acTLedgerFinanceDao.findById(invest.getInvestId().longValue());
			Double debtProportion = actLedegrFinace.getDebtProportion();
			if (startDate == "" && endDate == "") {
				cfAllList = acTVirtualCashFlowDao.findFutureAllNote(invest.getLedgerFinanceId().getLoanAcct().getId(), new Date());
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}

			}
			if (startDate != "" && endDate == "") {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date r = null;
				try {
					r = formatter.parse(startDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cfAllList = acTVirtualCashFlowDao.findFutureAllNote(invest.getLedgerFinanceId().getLoanAcct().getId(), r);
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}
			}
			if (startDate == "" && endDate != "") {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date r = null;
				try {
					r = formatter.parse(endDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cfAllList = acTVirtualCashFlowDao.findNote(invest.getLedgerFinanceId().getLoanAcct().getId(), r);
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}
			}
			if (startDate != "" && endDate != "") {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date s = null;
				Date e = null;
				try {
					s = formatter.parse(startDate);
					e = formatter.parse(endDate);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				cfAllList = acTVirtualCashFlowDao.findNormalNote(invest.getLedgerFinanceId().getLoanAcct().getId(), s, e);
				for (MyInvestmentAcTVirtualCashFlow cashFlow : cfAllList) {
					MyInvestmentSkzVo skz = new MyInvestmentSkzVo();
					skz.setLoanAcc(invest.getLoanId().getUserId().getLoginName());
					skz.setLoanNo(invest.getLoanId().getLoanId().toString());
					skz.setReturnDate(DateUtil.getYMDTime(cashFlow.getRepayDay()));
					skz.setMonthPrincipalInterest(ObjectFormatUtil.formatCurrency(cashFlow.getPrincipalAmt() * debtProportion + cashFlow.getInterestAmt() * debtProportion));
					voList.add(skz);
				}
				for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
					allAmountSearch = allAmountSearch + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
				}
			}

		}
		return allAmountSearch;
	}

	/**
	 * 回帐查询(汇总)
	 * 
	 * @param userId
	 * @return description: 2012-12-24 Ray修改1014bug
	 */
	public Map<String, String> getHzcxTotal(BigDecimal userId) {
		Map<String, String> totalAmount = new HashMap<String, String>();
		double overdueDept = 0d;
		double oneMonthAmount = 0d;
		double threeMonthAmount = 0d;
		double oneYearAmount = 0d;
		double allAmount = 0d;
		Date date = new Date();
		// 还款中
		String status = "3";
		List<MyInvestmentInvestInfo> investInfoList = investInfoDao.findByUserIdAndStatus(userId, status);
		for (MyInvestmentInvestInfo investInfo : investInfoList) {
			MyInvestmentAcTLedgerFinance actLedegrFinace = investInfo.getLedgerFinanceId();
			// acTLedgerFinanceDao.findById(investInfo.getInvestId().longValue());
			Double debtProportion = actLedegrFinace.getDebtProportion();
			// String currNum = String.valueOf(investInfo.getLedgerFinanceId()
			// .getLoanAcct().getCurrNum());
			// MyInvestmentAcTVirtualCashFlow cashFlow =
			// acTVirtualCashFlowDao.findByLoanIdAndCurrNum(investInfo.getLedgerFinanceId().getLoanAcct().getId(),Short.valueOf(currNum));

			// 首先计算逾期未还的款项
			List<MyInvestmentAcTVirtualCashFlow> overdueDeptList = acTVirtualCashFlowDao.findNoteByStatus(investInfo.getLedgerFinanceId().getLoanAcct().getId(), date);
			// System.out.println("查到逾期信息："+overdueDeptList.size()+" 条！");
			for (MyInvestmentAcTVirtualCashFlow overdue : overdueDeptList) {
				overdueDept = overdueDept + overdue.getPrincipalAmt() * debtProportion + overdue.getInterestAmt() * debtProportion;
			}

			Date dateAfter = DateUtil.getAfterMonth(new Date(), 1);
			// 未来一个月
			List<MyInvestmentAcTVirtualCashFlow> cf1 = acTVirtualCashFlowDao.findNormalNotebyRepayStatus(investInfo.getLedgerFinanceId().getLoanAcct().getId(), date, dateAfter);
			if (cf1 != null && cf1.size() != 0) {
				oneMonthAmount = oneMonthAmount + cf1.get(0).getPrincipalAmt() * debtProportion + cf1.get(0).getInterestAmt() * debtProportion;
			}
			// 未来三个月
			dateAfter = DateUtil.getAfterMonth(new Date(), 3);
			List<MyInvestmentAcTVirtualCashFlow> cf3List = acTVirtualCashFlowDao.findNormalNotebyRepayStatus(investInfo.getLedgerFinanceId().getLoanAcct().getId(), date, dateAfter);
			for (MyInvestmentAcTVirtualCashFlow cf3 : cf3List) {
				threeMonthAmount = threeMonthAmount + cf3.getPrincipalAmt() * debtProportion + cf3.getInterestAmt() * debtProportion;
			}
			// 未来12月
			dateAfter = DateUtil.getAfterMonth(new Date(), 12);
			List<MyInvestmentAcTVirtualCashFlow> cf12List = acTVirtualCashFlowDao.findNormalNotebyRepayStatus(investInfo.getLedgerFinanceId().getLoanAcct().getId(), date, dateAfter);
			for (MyInvestmentAcTVirtualCashFlow cf12 : cf12List) {
				oneYearAmount = oneYearAmount + cf12.getPrincipalAmt() * debtProportion + cf12.getInterestAmt() * debtProportion;
			}
			// 未来所有
			List<MyInvestmentAcTVirtualCashFlow> cfAllList = acTVirtualCashFlowDao.findFutureAllNotebyRepayStatus(investInfo.getLedgerFinanceId().getLoanAcct().getId(), date);
			for (MyInvestmentAcTVirtualCashFlow cfAll : cfAllList) {
				allAmount = allAmount + cfAll.getPrincipalAmt() * debtProportion + cfAll.getInterestAmt() * debtProportion;
			}
		}
		// 每项都加上逾期未还金额（假如有的话）
		oneMonthAmount = oneMonthAmount + overdueDept;
		threeMonthAmount = threeMonthAmount + overdueDept;
		oneYearAmount = oneYearAmount + overdueDept;
		allAmount = allAmount + overdueDept;

		totalAmount.put("oneMonthAmount", ObjectFormatUtil.formatCurrency(oneMonthAmount));
		totalAmount.put("threeMonthAmount", ObjectFormatUtil.formatCurrency(threeMonthAmount));
		totalAmount.put("oneYearAmount", ObjectFormatUtil.formatCurrency(oneYearAmount));
		totalAmount.put("allAmount", ObjectFormatUtil.formatCurrency(allAmount));
		return totalAmount;
	}

	public MyInvestmentLctjVo getLctj(HttpServletRequest req) {
		// 已赚息
		double yzlx = 0d;
		// 已赚提前还款违约金
		double yztqhkwyj = 0d;
		// 已赚逾期罚息
		double yzyqfx = 0d;
		// 加权平均收益率
		double jqpjsyl = 0d;
		// 总借出金额
		double zjcje = 0d;
		// 总借出笔数
		int zjcbs = 0;
		// 已回收本息
		double yhsbx = 0d;
		// 已回收笔数
		int yhsbs = 0;
		// 待回收本息
		double dhsbx = 0d;
		// 待回收笔数
		int dhsbs = 0;
		MyInvestmentLctjVo vo = new MyInvestmentLctjVo();
		MyInvestmentUsers user = getUsers(req);
		MyInvestmentAcTLedger accLc = acTLedgerDao.findByTotalAccountIdAndBusiType(user.getTCustomerId().longValue(), "1");
		List<MyInvestmentAcTFlow> flowyzlxList = acTFlowDao.findByAccountAndAcctTitle(accLc.getId().toString(), "040101");
		for (MyInvestmentAcTFlow flow : flowyzlxList) {
			yzlx = Calculator.add(yzlx, flow.getTradeAmount());
		}
		List<MyInvestmentAcTFlow> flowyztqhkwyjList = acTFlowDao.findByAccountAndAcctTitle(accLc.getId().toString(), "040904");
		for (MyInvestmentAcTFlow flow : flowyztqhkwyjList) {
			yztqhkwyj = Calculator.add(yztqhkwyj, flow.getTradeAmount());
		}
		List<MyInvestmentAcTFlow> flowyzyqfx = acTFlowDao.findByAccountAndAcctTitle(accLc.getId().toString(), "040102");
		for (MyInvestmentAcTFlow flow : flowyzyqfx) {
			yzyqfx = Calculator.add(yzyqfx, flow.getTradeAmount());
		}
		List<MyInvestmentInvestInfo> investList1 = investInfoDao.findByUserIdAndStatus(user.getUserId(), "3");
		dhsbs = investList1.size();
		List<MyInvestmentInvestInfo> investList2 = investInfoDao.findByUserIdAndStatus(user.getUserId(), "4");
		investList1.addAll(investList2);
		for (MyInvestmentInvestInfo inv : investList1) {
			zjcje = Calculator.add(zjcje, inv.getInvestAmount());
		}
		zjcbs = investList1.size();
		double hsb = 0d;
		List<MyInvestmentAcTFlow> flowyhsbx = acTFlowDao.findByAccountAndAcctTitle(accLc.getId().toString(), "010105");
		for (MyInvestmentAcTFlow flow : flowyhsbx) {
			hsb = Calculator.add(hsb, flow.getTradeAmount());
		}
		yhsbx = Calculator.add(hsb, yzlx);
		yhsbs = investList2.size();
		dhsbx = dao.getInvestPrincipalInterest(user.getUserId(), false);// 待收回本息

		vo.setYzlx(ObjectFormatUtil.formatCurrency(yzlx));
		vo.setYztqhkwyj(ObjectFormatUtil.formatCurrency(yztqhkwyj));
		vo.setYzyqfx(ObjectFormatUtil.formatCurrency(yzyqfx));
		// TODO
		vo.setJqpjsyl("");
		vo.setZjcje(ObjectFormatUtil.formatCurrency(zjcje));
		vo.setZjcbs(String.valueOf(zjcbs));
		vo.setYhsbx(ObjectFormatUtil.formatCurrency(yhsbx));
		vo.setYhsbs(String.valueOf(yhsbs));
		vo.setDhsbx(ObjectFormatUtil.formatCurrency(dhsbx));
		vo.setDhsbs(String.valueOf(dhsbs));
		return vo;
	}

	// 取得userid
	public MyInvestmentUsers getUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		MyInvestmentUsers userInfo = userDao.findByUserId(userid);
		return userInfo;
	}

}
