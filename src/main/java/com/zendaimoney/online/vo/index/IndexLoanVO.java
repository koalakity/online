package com.zendaimoney.online.vo.index;

import java.math.BigDecimal;

import javax.persistence.Transient;

public class IndexLoanVO {
	
	//借贷id
	private BigDecimal loanId;
	//借款标题
	private String loanTitle;
	//信用等级
	private int creditGrade;
	//借款金额
	private String loanAmount;
	//年利率
	private String yearRate;
	//借贷期限
	private String loanPeriod;
	//已筹集
	private String speedProgress;
	//还需金额
	private String alsoNeedAmount;
	//投标笔数
	private int bidNumber;
	//发布日期
	private String releaseYMD;
	public BigDecimal getLoanId() {
		return loanId;
	}
	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}
	@Transient
	public String getLoanTitle() {
		if(loanTitle.length()>12){
			return loanTitle.substring(0, 12).concat("...");
		}else{
			return loanTitle;
		}
	}
	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}
	public int getCreditGrade() {
		return creditGrade;
	}
	public void setCreditGrade(int creditGrade) {
		this.creditGrade = creditGrade;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public String getSpeedProgress() {
		return speedProgress;
	}
	public void setSpeedProgress(String speedProgress) {
		this.speedProgress = speedProgress;
	}
	public String getAlsoNeedAmount() {
		return alsoNeedAmount;
	}
	public void setAlsoNeedAmount(String alsoNeedAmount) {
		this.alsoNeedAmount = alsoNeedAmount;
	}
	public int getBidNumber() {
		return bidNumber;
	}
	public void setBidNumber(int bidNumber) {
		this.bidNumber = bidNumber;
	}
	public String getReleaseYMD() {
		return releaseYMD;
	}
	public void setReleaseYMD(String releaseYMD) {
		this.releaseYMD = releaseYMD;
	}
}
