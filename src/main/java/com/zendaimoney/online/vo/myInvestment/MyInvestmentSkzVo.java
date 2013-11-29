package com.zendaimoney.online.vo.myInvestment;


public class MyInvestmentSkzVo{
	
	//借款编号
	private String loanNo;
	//借款人
	private String loanAcc;
	//最近还款日期
	private String returnDate;
	//月收本息
	private String monthPrincipalInterest;
	//剩余本金
	private String surplusPrincipal;
	//逾期天数
	private String lateDate;
	//罚息
	private String lateRate;
	
	
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
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getMonthPrincipalInterest() {
		return monthPrincipalInterest;
	}
	public void setMonthPrincipalInterest(String monthPrincipalInterest) {
		this.monthPrincipalInterest = monthPrincipalInterest;
	}
	public String getSurplusPrincipal() {
		return surplusPrincipal;
	}
	public void setSurplusPrincipal(String surplusPrincipal) {
		this.surplusPrincipal = surplusPrincipal;
	}
	public String getLateDate() {
		return lateDate;
	}
	public void setLateDate(String lateDate) {
		this.lateDate = lateDate;
	}
	public String getLateRate() {
		return lateRate;
	}
	public void setLateRate(String lateRate) {
		this.lateRate = lateRate;
	}
	
	
	
	
}
