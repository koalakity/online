package com.zendaimoney.online.vo.loanManagement;

import java.math.BigDecimal;

public class SendMessageToInvestVO {

	//理财人Id
	private BigDecimal investId;
	//月还本息
	private String principanInterestMonth;
	//逾期罚息
	private String overdueInterest;
	//剩余本金
	private String currSurplusPrincipal;
	//逾期违约金
	private String currOverdueFines;
	public BigDecimal getInvestId() {
		return investId;
	}
	public void setInvestId(BigDecimal investId) {
		this.investId = investId;
	}
	public String getPrincipanInterestMonth() {
		return principanInterestMonth;
	}
	public void setPrincipanInterestMonth(String principanInterestMonth) {
		this.principanInterestMonth = principanInterestMonth;
	}
	public String getOverdueInterest() {
		return overdueInterest;
	}
	public void setOverdueInterest(String overdueInterest) {
		this.overdueInterest = overdueInterest;
	}
	public String getCurrSurplusPrincipal() {
		return currSurplusPrincipal;
	}
	public void setCurrSurplusPrincipal(String currSurplusPrincipal) {
		this.currSurplusPrincipal = currSurplusPrincipal;
	}
	public String getCurrOverdueFines() {
		return currOverdueFines;
	}
	public void setCurrOverdueFines(String currOverdueFines) {
		this.currOverdueFines = currOverdueFines;
	}
	
}
