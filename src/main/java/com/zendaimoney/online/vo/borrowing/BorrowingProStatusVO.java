package com.zendaimoney.online.vo.borrowing;


public class BorrowingProStatusVO{
    
	//状态
	private String proStatus;
	//分数
	private String creditScore;
	//操作
	private String operate;
	//审核
	private String reviewStatus;
	

	public String getProStatus() {
		return proStatus;
	}
	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	
	public String getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	public String getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
	

	
}
