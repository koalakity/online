package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;

public class AuidtListForm {
	private BigDecimal proId;
	private BigDecimal creditScore;
	private BigDecimal reviewStatus;

	public BigDecimal getProId() {
		return proId;
	}

	public void setProId(BigDecimal proId) {
		this.proId = proId;
	}

	public BigDecimal getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(BigDecimal creditScore) {
		this.creditScore = creditScore;
	}

	public BigDecimal getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(BigDecimal reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

}
