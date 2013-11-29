package com.zendaimoney.online.vo.toolsBox;

public class InverstVO {
	//理财人姓名
	private String investUserLoginName;
	//借出金额
	private String ivestAmount;
	// 每月应收本息
	private String  monthPrincipalInterest;
	
	public String getInvestUserLoginName() {
		return investUserLoginName;
	}
	public void setInvestUserLoginName(String investUserLoginName) {
		this.investUserLoginName = investUserLoginName;
	}
	public String getIvestAmount() {
		return ivestAmount;
	}
	public void setIvestAmount(String ivestAmount) {
		this.ivestAmount = ivestAmount;
	}
	public String getMonthPrincipalInterest() {
		return monthPrincipalInterest;
	}
	public void setMonthPrincipalInterest(String monthPrincipalInterest) {
		this.monthPrincipalInterest = monthPrincipalInterest;
	}
	
}
