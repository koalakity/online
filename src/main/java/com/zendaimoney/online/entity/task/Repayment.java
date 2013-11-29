package com.zendaimoney.online.entity.task;

import java.math.BigDecimal;



public class Repayment {
	
	private BigDecimal loanId;
	private BigDecimal userId;
	private Double monthReturnPrincipalandinter;
	private Double monthManageCost;
	private String totalAcct;
	
	public BigDecimal getLoanId() {
		return loanId;
	}
	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public Double getMonthReturnPrincipalandinter() {
		return monthReturnPrincipalandinter;
	}
	public void setMonthReturnPrincipalandinter(Double monthReturnPrincipalandinter) {
		this.monthReturnPrincipalandinter = monthReturnPrincipalandinter;
	}
	public Double getMonthManageCost() {
		return monthManageCost;
	}
	public void setMonthManageCost(Double monthManageCost) {
		this.monthManageCost = monthManageCost;
	}
	public String getTotalAcct() {
		return totalAcct;
	}
	public void setTotalAcct(String totalAcct) {
		this.totalAcct = totalAcct;
	}
	
}
