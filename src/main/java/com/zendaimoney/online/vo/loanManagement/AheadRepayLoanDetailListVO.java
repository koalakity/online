package com.zendaimoney.online.vo.loanManagement;

import java.math.BigDecimal;
import java.util.List;

public class AheadRepayLoanDetailListVO {

	private List<RepayLoanDetailVO> repayLoanDetailVOList;
	//已还本息
	private String payOffprincipalInterest;
	//本期应还本息
	private String currentShouldPayprincipalInterest;
	//月缴管理费
	private String managementFeeByMonth;
	//剩余本金
	private String surplusPrincipal;
	//提前还款违约金
	private String aheadOverdueFines;
	//提前还款应还金额
	private String aheadShouldRepayAmount;
	//我的余额
	private String myAvailableBalance;
	//当前借款信息表id
	private BigDecimal currentloanId;
	//当前待还还款期数
	private Short currentTermLoan;
	//提前还款共计还款(String)
	private String currentPaymentTotal;
	//提前还款共计还款(double)
	private double currentPaymentTotalDouble;
	
	public String getMyAvailableBalance() {
		return myAvailableBalance;
	}
	public void setMyAvailableBalance(String myAvailableBalance) {
		this.myAvailableBalance = myAvailableBalance;
	}
	public String getPayOffprincipalInterest() {
		return payOffprincipalInterest;
	}
	public void setPayOffprincipalInterest(String payOffprincipalInterest) {
		this.payOffprincipalInterest = payOffprincipalInterest;
	}
	public String getCurrentShouldPayprincipalInterest() {
		return currentShouldPayprincipalInterest;
	}
	public void setCurrentShouldPayprincipalInterest(
			String currentShouldPayprincipalInterest) {
		this.currentShouldPayprincipalInterest = currentShouldPayprincipalInterest;
	}
	public String getManagementFeeByMonth() {
		return managementFeeByMonth;
	}
	public void setManagementFeeByMonth(String managementFeeByMonth) {
		this.managementFeeByMonth = managementFeeByMonth;
	}
	public String getSurplusPrincipal() {
		return surplusPrincipal;
	}
	public void setSurplusPrincipal(String surplusPrincipal) {
		this.surplusPrincipal = surplusPrincipal;
	}
	public String getAheadOverdueFines() {
		return aheadOverdueFines;
	}
	public void setAheadOverdueFines(String aheadOverdueFines) {
		this.aheadOverdueFines = aheadOverdueFines;
	}
	public String getAheadShouldRepayAmount() {
		return aheadShouldRepayAmount;
	}
	public void setAheadShouldRepayAmount(String aheadShouldRepayAmount) {
		this.aheadShouldRepayAmount = aheadShouldRepayAmount;
	}
	public List<RepayLoanDetailVO> getRepayLoanDetailVOList() {
		return repayLoanDetailVOList;
	}
	public void setRepayLoanDetailVOList(List<RepayLoanDetailVO> repayLoanDetailVOList) {
		this.repayLoanDetailVOList = repayLoanDetailVOList;
	}
	public BigDecimal getCurrentloanId() {
		return currentloanId;
	}
	public void setCurrentloanId(BigDecimal currentloanId) {
		this.currentloanId = currentloanId;
	}
	public Short getCurrentTermLoan() {
		return currentTermLoan;
	}
	public void setCurrentTermLoan(Short currentTermLoan) {
		this.currentTermLoan = currentTermLoan;
	}
	public String getCurrentPaymentTotal() {
		return currentPaymentTotal;
	}
	public void setCurrentPaymentTotal(String currentPaymentTotal) {
		this.currentPaymentTotal = currentPaymentTotal;
	}
	public double getCurrentPaymentTotalDouble() {
		return currentPaymentTotalDouble;
	}
	public void setCurrentPaymentTotalDouble(double currentPaymentTotalDouble) {
		this.currentPaymentTotalDouble = currentPaymentTotalDouble;
	}
	
}
