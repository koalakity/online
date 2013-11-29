package com.zendaimoney.online.vo.loanManagement;

import java.math.BigDecimal;

public class RepaymentLoanInfoDetailVO {
	private String loanTitle;
	//借款id
	private BigDecimal loanId;
	//借款金额
	private String loanAmount;
	//年利率
	private String yearRate;
	//期限
	private String loanDuration;
	//下一还款日
	private String nextPaymentDate;
	//月还款额
	private String repaymentAmountByMonth;
	//月缴纳管理费
	private String managementFeeByMonth;
	//逾期天数
	private int overdueDays;
	//逾期罚息
	private String overdueInterest;
	//逾期违约金
	private String overdueFines;
	
	public String getLoanTitle() {
		return loanTitle;
	}
	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}
	public BigDecimal getLoanId() {
		return loanId;
	}
	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getLoanDuration() {
		return loanDuration;
	}
	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}
	public String getNextPaymentDate() {
		return nextPaymentDate;
	}
	public void setNextPaymentDate(String nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}
	public String getRepaymentAmountByMonth() {
		return repaymentAmountByMonth;
	}
	public void setRepaymentAmountByMonth(String repaymentAmountByMonth) {
		this.repaymentAmountByMonth = repaymentAmountByMonth;
	}
	public String getManagementFeeByMonth() {
		return managementFeeByMonth;
	}
	public void setManagementFeeByMonth(String managementFeeByMonth) {
		this.managementFeeByMonth = managementFeeByMonth;
	}
	public int getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}
	public String getOverdueInterest() {
		return overdueInterest;
	}
	public void setOverdueInterest(String overdueInterest) {
		this.overdueInterest = overdueInterest;
	}
	public String getOverdueFines() {
		return overdueFines;
	}
	public void setOverdueFines(String overdueFines) {
		this.overdueFines = overdueFines;
	}
	
}
