package com.zendaimoney.online.vo.loanManagement;

import java.util.List;

public class RepaymentLoanInfoListVO {
	
	private List<RepaymentLoanInfoDetailVO> repayLoanInfoList;

	public List<RepaymentLoanInfoDetailVO> getRepayLoanInfoList() {
		return repayLoanInfoList;
	}

	public void setRepayLoanInfoList(
			List<RepaymentLoanInfoDetailVO> repayLoanInfoList) {
		this.repayLoanInfoList = repayLoanInfoList;
	}

}
