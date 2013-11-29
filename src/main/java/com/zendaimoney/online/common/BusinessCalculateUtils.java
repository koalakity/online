package com.zendaimoney.online.common;

import java.math.BigDecimal;
import java.util.Date;

public class BusinessCalculateUtils {

	// 一天的毫秒数 60*60*1000*24
	private final static long DAY_MILLIS = 86400000;
	
	/**
	 * 计算月缴管理费（缴管理费=借款金额*月缴管理费费率= 10000*0.5%=50）
	 * 四舍五入，保留2位小数
	 */
	public static BigDecimal getManagementFeeEveryMonth(BigDecimal loanAmount,BigDecimal rateManagementFeeMonth){		
		return BigDecimalUtil.mul(loanAmount, rateManagementFeeMonth);
	}
	
	/**
	 * 计算逾期罚息
	 * @param overdue_day 逾期天数
	 * @param term 剩余未还期数
	 * @param principal_interest_month 每期应还本息
	 * @param rate 逾期罚息费率
	 * @return
	 */
	public static BigDecimal calOverdueInterest(int overdue_day,int term,BigDecimal principal_interest_month,BigDecimal rate){
		//逾期罚息=剩余未还期数*每期应还本息*天数*比例
		BigDecimal overdue_interest=BigDecimal.ZERO;
		overdue_interest=BigDecimalUtil.mul(term, principal_interest_month);
		overdue_interest=BigDecimalUtil.mul(overdue_interest, overdue_day);
		overdue_interest=BigDecimalUtil.mul(overdue_interest, rate);
		overdue_interest=BigDecimalUtil.round(overdue_interest, 2);
		return overdue_interest;
	}
	
	/**
	 * 计算逾期违约金（逾期违约金：=剩余未还期数*每期应还管理费*逾期天数*逾期罚息费率）	 
	 * * @param overdue_day 逾期天数
	 * @param term 剩余未还期数
	 * @param principal_interest_month 每期应还本息
	 * @param rate 罚息比例
	 */
	public static BigDecimal getOverdueFines(int overdue_day,int term,BigDecimal manageFeeByMonth,BigDecimal rate){
		BigDecimal overdue_Fines = BigDecimal.ZERO;
		overdue_Fines = BigDecimalUtil.mul(term,manageFeeByMonth);
		overdue_Fines = BigDecimalUtil.mul(overdue_Fines,overdue_day);
		overdue_Fines = BigDecimalUtil.mul(overdue_Fines,rate);
		return overdue_Fines;
	}
	
	/**
	 * 计算逾期天数 （ 逾期天数=下次结息日-当前日期）
	 */
	public static int getOverdueDays(Date repay_day,Date edit_date){
		long temp = 0;
		if(repay_day.getTime() >= edit_date.getTime()){
			return 0;
		}else{
			temp = edit_date.getTime() - repay_day.getTime();
		}
		return(int) (temp / DAY_MILLIS);
	}
	
	/**
	 * 判断是否是当期
	 * @param repay_day
	 * @param edit_date
	 * @return
	 */
	public static int getIsCurrTerm(Date crrDate,Date nextExpiry){
		if(crrDate.getTime() > nextExpiry.getTime()){
			return 0;
		}else{
			return 1;
		}
	}
	
	/**
	 * 判断某期还款中的还款日期
	 */
	 public static String getRepayDate(Date repay_day,Date edit_date){
		Date  repayDate  = null;
		if(edit_date!=null){
			//if(repay_day.getTime() >= edit_date.getTime()){
				repayDate = edit_date;
			//}else{
			//	repayDate = repay_day;
			//}
		}else{
			repayDate = repay_day;
		}

		 return DateUtil.getYMDTime(repayDate);
	 }
	 
	 /**
	  * 本期应还金额
	  * 
	  */
	 public static BigDecimal currentTermShouldPayAmount(BigDecimal currentManagementFeeByMonth,BigDecimal principanInterestMonth,BigDecimal currentOverdueInterest, BigDecimal currentOverdueFines){
		 BigDecimal currentPaymentTotal = BigDecimal.ZERO;
		 currentPaymentTotal = BigDecimalUtil.add(currentPaymentTotal,currentManagementFeeByMonth);
		 currentPaymentTotal = BigDecimalUtil.add(currentPaymentTotal,principanInterestMonth);
		 currentPaymentTotal = BigDecimalUtil.add(currentPaymentTotal,currentOverdueInterest);
		 currentPaymentTotal = BigDecimalUtil.add(currentPaymentTotal,currentOverdueFines);
		 	return currentPaymentTotal;		
	 }
	 
	 /**
	  * 一次性提前还款违约金
	  */
	 public static BigDecimal getOverdueFinesComplateTotal(BigDecimal surplusPrincipalTotal,BigDecimal rate){
		 return BigDecimalUtil.mul(surplusPrincipalTotal, rate);
	 }
	 
	 /**
	  * 一次性还款应还金额
	  */
	 /**
	 * @param principal 本金
	 * @param interest  利息
	 * @param currentManagementFeeByMonth 月缴管理费
	 * @param surplusPrincipal 剩余本金
	 * @param overdueFinesComplateTotal 一次性提前还款违约金
	 * @return
	 */
	public static BigDecimal getCurrentComplateShouldPayAmount(BigDecimal principal ,BigDecimal interest ,BigDecimal currentManagementFeeByMonth,BigDecimal surplusPrincipal,BigDecimal overdueFinesComplateTotal){
		 BigDecimal currentComplateShouldPayAmount = BigDecimal.ZERO;
		 currentComplateShouldPayAmount = BigDecimalUtil.add(currentComplateShouldPayAmount,principal);
		 currentComplateShouldPayAmount = BigDecimalUtil.add(currentComplateShouldPayAmount,interest);
		 currentComplateShouldPayAmount = BigDecimalUtil.add(currentComplateShouldPayAmount,currentManagementFeeByMonth);
		 currentComplateShouldPayAmount = BigDecimalUtil.add(currentComplateShouldPayAmount,surplusPrincipal);
		 currentComplateShouldPayAmount = BigDecimalUtil.add(currentComplateShouldPayAmount,overdueFinesComplateTotal);
	 	return currentComplateShouldPayAmount;	
	 }
	 
	 /**
	  * 计算月利率
	  */
	 public static BigDecimal getMonthRate(BigDecimal yearRate){
		 return BigDecimalUtil.div(yearRate, 12);
	 }
	 
	 /**
	  * 计算风险准备金
	  */
	 public static BigDecimal getReserveFoud(BigDecimal loanAmount,BigDecimal reserveFoudRate){
		 return BigDecimalUtil.mul(loanAmount, reserveFoudRate);
	 }
	 
}
