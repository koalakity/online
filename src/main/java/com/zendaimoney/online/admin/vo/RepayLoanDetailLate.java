package com.zendaimoney.online.admin.vo;

public class RepayLoanDetailLate {
	// 借款编号
	private Long loanId;
	// 还款日期
	private String repayLoanDate;
	// 月还本息
	private String principanInterestMonth;
	// 逾期天数
	private int overdueDays;
	// 逾期罚息
	private String overdueInterest;
	// 逾期违约金
	private String overdueFines;
	// 管理费
	private String managementFeeByMonth;
	// 状态
	private String repayLoanStatus;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getRepayLoanDate() {
		return repayLoanDate;
	}

	public void setRepayLoanDate(String repayLoanDate) {
		this.repayLoanDate = repayLoanDate;
	}

	public String getPrincipanInterestMonth() {
		return principanInterestMonth;
	}

	public void setPrincipanInterestMonth(String principanInterestMonth) {
		this.principanInterestMonth = principanInterestMonth;
	}

	public int getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(String overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public String getOverdueFines() {
		return overdueFines;
	}

	public void setOverdueFines(String overdueFines) {
		this.overdueFines = overdueFines;
	}

	public String getManagementFeeByMonth() {
		return managementFeeByMonth;
	}

	public void setManagementFeeByMonth(String managementFeeByMonth) {
		this.managementFeeByMonth = managementFeeByMonth;
	}

	public String getRepayLoanStatus() {
		return repayLoanStatus;
	}

	public void setRepayLoanStatus(String repayLoanStatus) {
		this.repayLoanStatus = repayLoanStatus;
	}
}
