package com.zendaimoney.online.vo.loanManagement;

import java.math.BigDecimal;

public class RepayOffLoanInfoVO {

	private String loanId;
	//借款标题
	private String loanTitle;
	//借款金额
	private String loanAmount;
	//年利率
	private String yearRate;
	//借款期限
	private String loanDuration;
	//已还金额
	private String repayOffLoanAmount;
	//待还余额
	private String waitRepayBalanceAmount;
	//还款状态
	private BigDecimal repaymentStatus;
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanTitle() {
		return loanTitle;
	}
	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
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
	public String getRepayOffLoanAmount() {
		return repayOffLoanAmount;
	}
	public void setRepayOffLoanAmount(String repayOffLoanAmount) {
		this.repayOffLoanAmount = repayOffLoanAmount;
	}
	public String getWaitRepayBalanceAmount() {
		return waitRepayBalanceAmount;
	}
	public void setWaitRepayBalanceAmount(String waitRepayBalanceAmount) {
		this.waitRepayBalanceAmount = waitRepayBalanceAmount;
	}
	public BigDecimal getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(BigDecimal repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	
}
