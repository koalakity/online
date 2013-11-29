/**
 * 
 */
package com.zendaimoney.online.vo.toolsBox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangtf 借款协议范本
 * 
 */
public class BorrowContractVO {
	// 借款编号
	private String loanId;
	// 借款用途
	private String laonUse;
	// 借款本金数额
	private String loanAmount;
	// 月还本息
	private String monthRepayAmount;
	// 借款期限
	private String loanDuration;
	// 每月还款时间
	private String monthRepayTime;
	// 还款起止日期
	private String repayTimeStartAndEnd;
	// 放款日期
	private String loanPross;
	// 借款人姓名
	private String loanUserRealName;
	// 借款人登陆名
	private String loanUserLoginName;

	private String duration;
	// 身份证号
	private String identifyNo;

	private String email;

	private String address;
	// 风险基金
	private String fundMoney;
	// 借款手续费费率
	private String loan;
	// 网站管理费
	private String mgmtFee;
	// 初级逾期罚息费率
	private String overdueInterest;
	// 高级逾期罚息费率
	private String overdueSeriouInterest;
	// 逾期违约金
	private String overdueFines;
	// 提前还款违约金
	private String earlyFines;
	// 风险准备金费率
	private String reserveFund;

	public String getFundMoney() {
		return fundMoney;
	}

	public void setFundMoney(String fundMoney) {
		this.fundMoney = fundMoney;
	}

	public String getIdentifyNo() {
		return identifyNo;
	}

	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	// 借入信息
	private List<InverstVO> investList = new ArrayList<InverstVO>();

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getLaonUse() {
		return laonUse;
	}

	public void setLaonUse(String laonUse) {
		this.laonUse = laonUse;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getMonthRepayAmount() {
		return monthRepayAmount;
	}

	public void setMonthRepayAmount(String monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}

	public String getLoanDuration() {
		return loanDuration;
	}

	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}

	public String getMonthRepayTime() {
		return monthRepayTime;
	}

	public void setMonthRepayTime(String monthRepayTime) {
		this.monthRepayTime = monthRepayTime;
	}

	public String getRepayTimeStartAndEnd() {
		return repayTimeStartAndEnd;
	}

	public void setRepayTimeStartAndEnd(String repayTimeStartAndEnd) {
		this.repayTimeStartAndEnd = repayTimeStartAndEnd;
	}

	public String getLoanPross() {
		return loanPross;
	}

	public void setLoanPross(String loanPross) {
		this.loanPross = loanPross;
	}

	public String getLoanUserRealName() {
		return loanUserRealName;
	}

	public void setLoanUserRealName(String loanUserRealName) {
		this.loanUserRealName = loanUserRealName;
	}

	public List<InverstVO> getInvestList() {
		return investList;
	}

	public void setInvestList(List<InverstVO> investList) {
		this.investList = investList;
	}

	public String getLoanUserLoginName() {
		return loanUserLoginName;
	}

	public void setLoanUserLoginName(String loanUserLoginName) {
		this.loanUserLoginName = loanUserLoginName;
	}

	public String getLoan() {
		return loan;
	}

	public void setLoan(String loan) {
		this.loan = loan;
	}

	public String getMgmtFee() {
		return mgmtFee;
	}

	public void setMgmtFee(String mgmtFee) {
		this.mgmtFee = mgmtFee;
	}

	public String getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(String overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public String getOverdueSeriouInterest() {
		return overdueSeriouInterest;
	}

	public void setOverdueSeriouInterest(String overdueSeriouInterest) {
		this.overdueSeriouInterest = overdueSeriouInterest;
	}

	public String getOverdueFines() {
		return overdueFines;
	}

	public void setOverdueFines(String overdueFines) {
		this.overdueFines = overdueFines;
	}

	public String getEarlyFines() {
		return earlyFines;
	}

	public void setEarlyFines(String earlyFines) {
		this.earlyFines = earlyFines;
	}

	public String getReserveFund() {
		return reserveFund;
	}

	public void setReserveFund(String reserveFund) {
		this.reserveFund = reserveFund;
	}

}
