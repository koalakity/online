package com.zendaimoney.online.common;

import java.math.BigDecimal;
import java.util.Date;

public class FormulaSupportUtil {

	// 一天的毫秒数 60*60*1000*24
	private final static long DAY_MILLIS = 86400000;

	/**
	 * 计算月缴管理费（缴管理费=借款金额*月缴管理费费率= 10000*0.5%=50） 四舍五入，保留2位小数
	 */
	public static double getManagementFeeEveryMonth(double loanAmount, double rateManagementFeeMonth) {
		return Calculator.mul(loanAmount, rateManagementFeeMonth);
	}
	/**
	 * 计算逾期罚息
	 * 
	 * @param overdue_day
	 *            逾期天数
	 * @param term
	 *            剩余未还期数
	 * @param principal_interest_month
	 *            每期应还本息
	 * @param rate
	 *            逾期罚息费率
	 * @return
	 */
	public static double calOverdueInterest(int overdue_day, int term, double principal_interest_month, double rate) {
		// 逾期罚息=剩余未还期数*每期应还本息*天数*比例
		double overdue_interest = 0;
		overdue_interest = Calculator.mul(term, principal_interest_month);
		overdue_interest = Calculator.mul(overdue_interest, overdue_day);
		overdue_interest = Calculator.mul(overdue_interest, rate);
		overdue_interest = Calculator.round(overdue_interest, 2);
		return overdue_interest;
	}

	/**
	 * 计算逾期违约金（逾期罚息：=剩余未还期数*每期应还管理费*逾期天数*逾期罚息费率） * @param overdue_day 逾期天数
	 * 
	 * @param term
	 *            剩余未还期数
	 * @param principal_interest_month
	 *            每期应还本息
	 * @param rate
	 *            罚息比例
	 */
	public static double getOverdueFines(int overdue_day, int term, double principal_interest_month, double rate) {
		double overdue_Fines = 0;
		overdue_Fines = Calculator.mul(term, principal_interest_month);
		overdue_Fines = Calculator.mul(overdue_Fines, overdue_day);
		overdue_Fines = Calculator.mul(overdue_Fines, rate);
		return overdue_Fines;
	}

	/**
	 * 计算逾期天数 （ 逾期天数=下次结息日-当前日期）
	 */
	public static int getOverdueDays(Date repay_day, Date edit_date) {
		long temp = 0;
		if (repay_day.getTime() >= edit_date.getTime()) {
			return 0;
		} else {
			temp = edit_date.getTime() - repay_day.getTime();
		}
		return (int) (temp / DAY_MILLIS);
	}

	/**
	 * 判断是否是当期
	 * 
	 * @param repay_day
	 * @param edit_date
	 * @return
	 */
	public static int getIsCurrTerm(Date crrDate, Date nextExpiry) {
		if (crrDate.getTime() > nextExpiry.getTime()) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 判断某期还款中的还款日期
	 */
	public static String getRepayDate(Date repay_day, Date edit_date) {
		Date repayDate = null;
		if (edit_date != null) {
			// if(repay_day.getTime() >= edit_date.getTime()){
			repayDate = edit_date;
			// }else{
			// repayDate = repay_day;
			// }
		} else {
			repayDate = repay_day;
		}

		 return DateUtil.getYMDTime(repayDate);
	 }
	 
	 /**
	  * 本期应还金额
	  * 
	  */
	 public static double currentTermShouldPayAmount(double currentManagementFeeByMonth,double principanInterestMonth,double currentOverdueInterest, double currentOverdueFines){
		 double currentPaymentTotal = 0;
		 currentPaymentTotal = Calculator.add(currentPaymentTotal,currentManagementFeeByMonth);
		 currentPaymentTotal = Calculator.add(currentPaymentTotal,principanInterestMonth);
		 currentPaymentTotal = Calculator.add(currentPaymentTotal,currentOverdueInterest);
		 currentPaymentTotal = Calculator.add(currentPaymentTotal,currentOverdueFines);
		 	return currentPaymentTotal;		
	 }
	 
	 /**
	  * 一次性提前还款违约金
	  */
	 public static double getOverdueFinesComplateTotal(double surplusPrincipalTotal,double rate){
		 return Calculator.mul(surplusPrincipalTotal, rate);
	 }
	 
	 /**
	  * 一次性还款应还金额
	  */
	 public static double getCurrentComplateShouldPayAmount(double principanInterestMonth ,double currentManagementFeeByMonth,double surplusPrincipal,double overdueFinesComplateTotal){
		 double currentComplateShouldPayAmount = 0;
		 currentComplateShouldPayAmount = Calculator.add(currentComplateShouldPayAmount,principanInterestMonth);
		 currentComplateShouldPayAmount = Calculator.add(currentComplateShouldPayAmount,currentManagementFeeByMonth);
		 currentComplateShouldPayAmount = Calculator.add(currentComplateShouldPayAmount,surplusPrincipal);
		 currentComplateShouldPayAmount = Calculator.add(currentComplateShouldPayAmount,overdueFinesComplateTotal);
	 	return currentComplateShouldPayAmount;	
	 }
	 
	 /**
	  * 计算月利率
	  */
	 public static double getMonthRate(double yearRate){
		 return Calculator.div(yearRate, 12);
	 }
	 
	 /**
	  * 计算风险准备金
	  */
	 public static BigDecimal getReserveFoud(BigDecimal loanAmount,BigDecimal reserveFoudRate){
		 return BigDecimalUtil.mul(loanAmount, reserveFoudRate);
	 }
	 
	 /*
	  * test用
	  */
	 public static void main(String args[]){
		 System.out.println(getOverdueDays(new Date("2012/09/14"),(new Date("2012/09/15")))); ;
	 }
}
