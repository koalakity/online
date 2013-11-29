package com.zendaimoney.online.vo.homepage;

public class HomepageLoanInfoTempVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6798438323981185435L;
	// 借款id
	private String loanId;
	// 借款用途
	private String loanUse;
	// 借款类型
	private String loanKind;
	// 借款标题
	private String loanTitle;
	// 借款金额
	private String loanAmountStr;
	// 年利率
	private String lilv;
	// 期限
	private String loanQx;
	// 进度
	private String progress;
	// 笔数
	private String items;
	// 时间
	private String releaseDate;

	private String loanTitleStr;
	/**
	 * Add by jihui 2012-12-27 Description:圣诞活动要求
	 */
	// 放款时间
	private String loanDate;

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanTitleStr() {
		return loanTitleStr;
	}

	public void setLoanTitleStr(String loanTitleStr) {
		this.loanTitleStr = loanTitleStr;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getLoanAmountStr() {
		return loanAmountStr;
	}

	public void setLoanAmountStr(String loanAmountStr) {
		this.loanAmountStr = loanAmountStr;
	}

	public String getLilv() {
		return lilv;
	}

	public void setLilv(String lilv) {
		this.lilv = lilv;
	}

	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}

	public String getLoanTitle() {
		return loanTitle;
	}

	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}

	public String getLoanQx() {
		return loanQx;
	}

	public void setLoanQx(String loanQx) {
		this.loanQx = loanQx;
	}

	public String getLoanUse() {
		return loanUse;
	}

	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}

}
