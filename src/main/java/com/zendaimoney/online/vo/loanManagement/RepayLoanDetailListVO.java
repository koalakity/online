package com.zendaimoney.online.vo.loanManagement;

import java.math.BigDecimal;
import java.util.List;

public class RepayLoanDetailListVO {

	//还款中的列表
	private List<RepayLoanDetailVO>  repayLoanDetailList;
	
	//本期共计还款(String)
	private String currentPaymentTotal;
	
	//本期共计还款(double)
	private double currentPaymentTotalDouble;

	//我的余额
	private String myAvailableBalance;
	
	//当前借款信息表id
	private BigDecimal currentloanId;
	
	//当前待还还款期数
	private Short currentTermLoan;
	
	//总还款期数
	private int  currentTermLoanTotal;

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

	public List<RepayLoanDetailVO> getRepayLoanDetailList() {
		return repayLoanDetailList;
	}

	public void setRepayLoanDetailList(List<RepayLoanDetailVO> repayLoanDetailList) {
		this.repayLoanDetailList = repayLoanDetailList;
	}

	public String getCurrentPaymentTotal() {
		return currentPaymentTotal;
	}

	public void setCurrentPaymentTotal(String currentPaymentTotal) {
		this.currentPaymentTotal = currentPaymentTotal;
	}

	public String getMyAvailableBalance() {
		return myAvailableBalance;
	}

	public void setMyAvailableBalance(String myAvailableBalance) {
		this.myAvailableBalance = myAvailableBalance;
	}
	
	public double getCurrentPaymentTotalDouble() {
		return currentPaymentTotalDouble;
	}
	
	public void setCurrentPaymentTotalDouble(double currentPaymentTotalDouble) {
		this.currentPaymentTotalDouble = currentPaymentTotalDouble;
	}

	public int getCurrentTermLoanTotal() {
		return currentTermLoanTotal;
	}

	public void setCurrentTermLoanTotal(int currentTermLoanTotal) {
		this.currentTermLoanTotal = currentTermLoanTotal;
	}
	
}
