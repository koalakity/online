package com.zendaimoney.online.dto;

public class ConstsubjectDTO {
	private String loanerPrincipalAdjustOut;//本金调账
	private String principalOut;//本金
	private String investerPrincipalAdjustOut;//本金调账
	
	private String loanerInverestAdjustOut;//利息调账
	private String InverestOut;//利息
	private String investerInverestAdjustOut;//利息调账
	
	private String loanerOverdueFreeAdjustOut;//逾期罚息调账
	private String overdueFreeOut;//逾期罚息
	private String investerOverdueFreeAdjustOut;//逾期罚息调账
	
	private String penaltyOut;//逾期违约金
	
	private String loanerSurplusPrincipalAdjustOut;//剩余本金调账
	private String surplusPrincipalOut;//剩余本金
	private String investerSurplusPrincipalAdjustOut;//剩余本金调账
	
	private String monthManageCostOut;//月缴管理费
	
	
	private String loanerPrincipalAdjustIn;//本金调账
	private String principalIn;//本金
	private String investerPrincipalAdjustIn;//本金调账
	
	private String loanerInverestAdjustIn;//利息调账
	private String InverestIn;//利息
	private String investerInverestAdjustIn;//利息调账
	
	private String loanerOverdueFreeAdjustIn;//逾期罚息调账
	private String overdueFreeIn;//逾期罚息
	private String investerOverdueFreeAdjustIn;//逾期罚息调账
	
	private String penaltyIn;//逾期违约金
	
	private String loanerSurplusPrincipalAdjustIn;//剩余本金调账
	private String surplusPrincipalIn;//剩余本金
	private String investerSurplusPrincipalAdjustIn;//剩余本金调账
	
	private String monthManageCostIn;//月缴管理费
	
	//本金类型
	private String principalTradeType;
	//利息类型
	private String inverestTradeType;
	//月缴纳管理费类型
	private String monthManagerTradeTpye;
	//逾期违约金
	private String overdueFreeTradeTpye;
	//逾期罚息
	private String penaltyTradeType;

	public String getLoanerPrincipalAdjustOut() {
		return loanerPrincipalAdjustOut;
	}

	public void setLoanerPrincipalAdjustOut(String loanerPrincipalAdjustOut) {
		this.loanerPrincipalAdjustOut = loanerPrincipalAdjustOut;
	}

	public String getPrincipalOut() {
		return principalOut;
	}

	public void setPrincipalOut(String principalOut) {
		this.principalOut = principalOut;
	}

	public String getInvesterPrincipalAdjustOut() {
		return investerPrincipalAdjustOut;
	}

	public void setInvesterPrincipalAdjustOut(String investerPrincipalAdjustOut) {
		this.investerPrincipalAdjustOut = investerPrincipalAdjustOut;
	}

	public String getLoanerInverestAdjustOut() {
		return loanerInverestAdjustOut;
	}

	public void setLoanerInverestAdjustOut(String loanerInverestAdjustOut) {
		this.loanerInverestAdjustOut = loanerInverestAdjustOut;
	}

	public String getInverestOut() {
		return InverestOut;
	}

	public void setInverestOut(String inverestOut) {
		InverestOut = inverestOut;
	}

	public String getInvesterInverestAdjustOut() {
		return investerInverestAdjustOut;
	}

	public void setInvesterInverestAdjustOut(String investerInverestAdjustOut) {
		this.investerInverestAdjustOut = investerInverestAdjustOut;
	}

	public String getLoanerOverdueFreeAdjustOut() {
		return loanerOverdueFreeAdjustOut;
	}

	public void setLoanerOverdueFreeAdjustOut(String loanerOverdueFreeAdjustOut) {
		this.loanerOverdueFreeAdjustOut = loanerOverdueFreeAdjustOut;
	}

	public String getOverdueFreeOut() {
		return overdueFreeOut;
	}

	public void setOverdueFreeOut(String overdueFreeOut) {
		this.overdueFreeOut = overdueFreeOut;
	}

	public String getInvesterOverdueFreeAdjustOut() {
		return investerOverdueFreeAdjustOut;
	}

	public void setInvesterOverdueFreeAdjustOut(String investerOverdueFreeAdjustOut) {
		this.investerOverdueFreeAdjustOut = investerOverdueFreeAdjustOut;
	}

	public String getPenaltyOut() {
		return penaltyOut;
	}

	public void setPenaltyOut(String penaltyOut) {
		this.penaltyOut = penaltyOut;
	}

	public String getLoanerSurplusPrincipalAdjustOut() {
		return loanerSurplusPrincipalAdjustOut;
	}

	public void setLoanerSurplusPrincipalAdjustOut(
			String loanerSurplusPrincipalAdjustOut) {
		this.loanerSurplusPrincipalAdjustOut = loanerSurplusPrincipalAdjustOut;
	}

	public String getSurplusPrincipalOut() {
		return surplusPrincipalOut;
	}

	public void setSurplusPrincipalOut(String surplusPrincipalOut) {
		this.surplusPrincipalOut = surplusPrincipalOut;
	}

	public String getInvesterSurplusPrincipalAdjustOut() {
		return investerSurplusPrincipalAdjustOut;
	}

	public void setInvesterSurplusPrincipalAdjustOut(
			String investerSurplusPrincipalAdjustOut) {
		this.investerSurplusPrincipalAdjustOut = investerSurplusPrincipalAdjustOut;
	}

	public String getMonthManageCostOut() {
		return monthManageCostOut;
	}

	public void setMonthManageCostOut(String monthManageCostOut) {
		this.monthManageCostOut = monthManageCostOut;
	}

	public String getLoanerPrincipalAdjustIn() {
		return loanerPrincipalAdjustIn;
	}

	public void setLoanerPrincipalAdjustIn(String loanerPrincipalAdjustIn) {
		this.loanerPrincipalAdjustIn = loanerPrincipalAdjustIn;
	}

	public String getPrincipalIn() {
		return principalIn;
	}

	public void setPrincipalIn(String principalIn) {
		this.principalIn = principalIn;
	}

	public String getInvesterPrincipalAdjustIn() {
		return investerPrincipalAdjustIn;
	}

	public void setInvesterPrincipalAdjustIn(String investerPrincipalAdjustIn) {
		this.investerPrincipalAdjustIn = investerPrincipalAdjustIn;
	}

	public String getLoanerInverestAdjustIn() {
		return loanerInverestAdjustIn;
	}

	public void setLoanerInverestAdjustIn(String loanerInverestAdjustIn) {
		this.loanerInverestAdjustIn = loanerInverestAdjustIn;
	}

	public String getInverestIn() {
		return InverestIn;
	}

	public void setInverestIn(String inverestIn) {
		InverestIn = inverestIn;
	}

	public String getInvesterInverestAdjustIn() {
		return investerInverestAdjustIn;
	}

	public void setInvesterInverestAdjustIn(String investerInverestAdjustIn) {
		this.investerInverestAdjustIn = investerInverestAdjustIn;
	}

	public String getLoanerOverdueFreeAdjustIn() {
		return loanerOverdueFreeAdjustIn;
	}

	public void setLoanerOverdueFreeAdjustIn(String loanerOverdueFreeAdjustIn) {
		this.loanerOverdueFreeAdjustIn = loanerOverdueFreeAdjustIn;
	}

	public String getOverdueFreeIn() {
		return overdueFreeIn;
	}

	public void setOverdueFreeIn(String overdueFreeIn) {
		this.overdueFreeIn = overdueFreeIn;
	}

	public String getInvesterOverdueFreeAdjustIn() {
		return investerOverdueFreeAdjustIn;
	}

	public void setInvesterOverdueFreeAdjustIn(String investerOverdueFreeAdjustIn) {
		this.investerOverdueFreeAdjustIn = investerOverdueFreeAdjustIn;
	}

	public String getPenaltyIn() {
		return penaltyIn;
	}

	public void setPenaltyIn(String penaltyIn) {
		this.penaltyIn = penaltyIn;
	}

	public String getLoanerSurplusPrincipalAdjustIn() {
		return loanerSurplusPrincipalAdjustIn;
	}

	public void setLoanerSurplusPrincipalAdjustIn(
			String loanerSurplusPrincipalAdjustIn) {
		this.loanerSurplusPrincipalAdjustIn = loanerSurplusPrincipalAdjustIn;
	}

	public String getSurplusPrincipalIn() {
		return surplusPrincipalIn;
	}

	public void setSurplusPrincipalIn(String surplusPrincipalIn) {
		this.surplusPrincipalIn = surplusPrincipalIn;
	}

	public String getInvesterSurplusPrincipalAdjustIn() {
		return investerSurplusPrincipalAdjustIn;
	}

	public void setInvesterSurplusPrincipalAdjustIn(
			String investerSurplusPrincipalAdjustIn) {
		this.investerSurplusPrincipalAdjustIn = investerSurplusPrincipalAdjustIn;
	}

	public String getMonthManageCostIn() {
		return monthManageCostIn;
	}

	public void setMonthManageCostIn(String monthManageCostIn) {
		this.monthManageCostIn = monthManageCostIn;
	}

	public String getPrincipalTradeType() {
		return principalTradeType;
	}

	public void setPrincipalTradeType(String principalTradeType) {
		this.principalTradeType = principalTradeType;
	}

	public String getInverestTradeType() {
		return inverestTradeType;
	}

	public void setInverestTradeType(String inverestTradeType) {
		this.inverestTradeType = inverestTradeType;
	}

	public String getMonthManagerTradeTpye() {
		return monthManagerTradeTpye;
	}

	public void setMonthManagerTradeTpye(String monthManagerTradeTpye) {
		this.monthManagerTradeTpye = monthManagerTradeTpye;
	}

	public String getOverdueFreeTradeTpye() {
		return overdueFreeTradeTpye;
	}

	public void setOverdueFreeTradeTpye(String overdueFreeTradeTpye) {
		this.overdueFreeTradeTpye = overdueFreeTradeTpye;
	}

	public String getPenaltyTradeType() {
		return penaltyTradeType;
	}

	public void setPenaltyTradeType(String penaltyTradeType) {
		this.penaltyTradeType = penaltyTradeType;
	}

	
}
