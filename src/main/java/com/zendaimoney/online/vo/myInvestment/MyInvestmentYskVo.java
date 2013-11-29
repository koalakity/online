package com.zendaimoney.online.vo.myInvestment;


public class MyInvestmentYskVo{
	
	//借款编号
	private String loanNo;
	//借款人
	private String loanAcc;
	//借出日期
	private String loanDate;
	//借出金额
	private String loanAmount;
	//年利率
	private String rate;
	//借款期限
	private String loanDeadline;
	//回收金额
	private String recoverAmount;
	
	
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLoanAcc() {
		return loanAcc;
	}
	public void setLoanAcc(String loanAcc) {
		this.loanAcc = loanAcc;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getLoanDeadline() {
		return loanDeadline;
	}
	public void setLoanDeadline(String loanDeadline) {
		this.loanDeadline = loanDeadline;
	}
	public String getRecoverAmount() {
		return recoverAmount;
	}
	public void setRecoverAmount(String recoverAmount) {
		this.recoverAmount = recoverAmount;
	}
	
}
