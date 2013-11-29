package com.zendaimoney.online.vo.financial;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.financial.FinanciaUserApprove;
import com.zendaimoney.online.entity.financial.FinancialLoanInfo;

public class LoanInfoVO extends FinancialLoanInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2495116253641109422L;

	// 全部借款列表数量
	private String allLoanCount;

	// 进行中的借款数量
	private String ingLoanCount;

	// 即将开始的借款数量
	private String futureLoanCount;

	// 已经完成的借款数量
	private String oldLoanCount;

	// 逾期黑名单数量
	private String blackLoanCount;

	// 姓名
	private String name;

	// 地址
	private String address;

	// 进度
	private String schedule;

	// 已投资数量
	private String investmentCount;

	// 还差金额
	private String leavingMoney;

	// 剩余时间
	private String leavingTime;

	// 信用等级
	private BigDecimal creditGrade;

	// 借款金额
	private String loanAmountStr;

	// 我的可用余额
	private String myMoney;

	// 认证项目
	private List<FinanciaUserApprove> userApproveList;

	// 年利率
	private String yearRateStr;

	private String dateStr;

	//
	private String loanDate;

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getYearRateStr() {
		return yearRateStr;
	}

	public void setYearRateStr(String yearRateStr) {
		this.yearRateStr = yearRateStr;
	}

	public String getAllLoanCount() {
		return allLoanCount;
	}

	public void setAllLoanCount(String allLoanCount) {
		this.allLoanCount = allLoanCount;
	}

	public String getIngLoanCount() {
		return ingLoanCount;
	}

	public void setIngLoanCount(String ingLoanCount) {
		this.ingLoanCount = ingLoanCount;
	}

	public String getFutureLoanCount() {
		return futureLoanCount;
	}

	public void setFutureLoanCount(String futureLoanCount) {
		this.futureLoanCount = futureLoanCount;
	}

	public String getOldLoanCount() {
		return oldLoanCount;
	}

	public void setOldLoanCount(String oldLoanCount) {
		this.oldLoanCount = oldLoanCount;
	}

	public String getBlackLoanCount() {
		return blackLoanCount;
	}

	public void setBlackLoanCount(String blackLoanCount) {
		this.blackLoanCount = blackLoanCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInvestmentCount() {
		return investmentCount;
	}

	public void setInvestmentCount(String investmentCount) {
		this.investmentCount = investmentCount;
	}

	public String getLeavingMoney() {
		return leavingMoney;
	}

	public void setLeavingMoney(String leavingMoney) {
		this.leavingMoney = leavingMoney;
	}

	public String getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(String leavingTime) {
		this.leavingTime = leavingTime;
	}

	public BigDecimal getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(BigDecimal creditGrade) {
		this.creditGrade = creditGrade;
	}

	public List<FinanciaUserApprove> getUserApproveList() {
		return userApproveList;
	}

	public void setUserApproveList(List<FinanciaUserApprove> userApproveList) {
		this.userApproveList = userApproveList;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getLoanAmountStr() {
		return loanAmountStr;
	}

	public void setLoanAmountStr(String loanAmountStr) {
		this.loanAmountStr = loanAmountStr;
	}

	public String getMyMoney() {
		return myMoney;
	}

	public void setMyMoney(String myMoney) {
		this.myMoney = myMoney;
	}

}
