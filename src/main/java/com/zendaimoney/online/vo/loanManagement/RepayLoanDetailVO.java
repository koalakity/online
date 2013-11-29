package com.zendaimoney.online.vo.loanManagement;

import com.zendaimoney.online.entity.common.LoanRateVO;

public class RepayLoanDetailVO {

	private LoanRateVO rate;

	// 还款日期
	private String repayLoanDate;
	// 实际还款日期
	private String repayActDate;
	// 计划还款日期
	private String repayPlanDate;
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

	public String getRepayLoanDate() {
		return repayLoanDate;
	}

	public void setRepayLoanDate(String repayLoanDate) {
		this.repayLoanDate = repayLoanDate;
	}

	public String getRepayActDate() {
		return repayActDate;
	}

	public void setRepayActDate(String repayActDate) {
		this.repayActDate = repayActDate;
	}

	public String getRepayPlanDate() {
		return repayPlanDate;
	}

	public void setRepayPlanDate(String repayPlanDate) {
		this.repayPlanDate = repayPlanDate;
	}

	public LoanRateVO getRate() {
		return rate;
	}

	public void setRate(LoanRateVO rate) {
		this.rate = rate;
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
