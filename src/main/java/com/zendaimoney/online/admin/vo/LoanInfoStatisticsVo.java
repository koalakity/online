package com.zendaimoney.online.admin.vo;

public class LoanInfoStatisticsVo {
	//月还本息统计
	private String principalAndinterest;
	//逾期罚息统计
	private String overDueInterestAmount;
	//逾期违约金统计
	private String overDueFineAmount;
	//月缴管理费
	private String monthManageCost;
	//逾期应还总额
	private String totalPayBack;
	//垫付金额
	private String advancedAmt;
	//未垫付金额
	private String notAdvancedAmt;
	public String getPrincipalAndinterest() {
		return principalAndinterest;
	}
	public void setPrincipalAndinterest(String principalAndinterest) {
		this.principalAndinterest = principalAndinterest;
	}
	public String getOverDueInterestAmount() {
		return overDueInterestAmount;
	}
	public void setOverDueInterestAmount(String overDueInterestAmount) {
		this.overDueInterestAmount = overDueInterestAmount;
	}
	public String getOverDueFineAmount() {
		return overDueFineAmount;
	}
	public void setOverDueFineAmount(String overDueFineAmount) {
		this.overDueFineAmount = overDueFineAmount;
	}
	public String getMonthManageCost() {
		return monthManageCost;
	}
	public void setMonthManageCost(String monthManageCost) {
		this.monthManageCost = monthManageCost;
	}
	public String getTotalPayBack() {
		return totalPayBack;
	}
	public void setTotalPayBack(String totalPayBack) {
		this.totalPayBack = totalPayBack;
	}
	public String getAdvancedAmt() {
		return advancedAmt;
	}
	public void setAdvancedAmt(String advancedAmt) {
		this.advancedAmt = advancedAmt;
	}
	public String getNotAdvancedAmt() {
		return notAdvancedAmt;
	}
	public void setNotAdvancedAmt(String notAdvancedAmt) {
		this.notAdvancedAmt = notAdvancedAmt;
	}
	
}
