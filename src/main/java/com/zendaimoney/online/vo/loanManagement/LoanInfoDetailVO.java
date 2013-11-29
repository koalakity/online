package com.zendaimoney.online.vo.loanManagement;

import com.zendaimoney.online.entity.loanManagement.LoanManagementLoanInfo;

public class LoanInfoDetailVO extends LoanManagementLoanInfo {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1271108216645290046L;
	//进度
	private String speedProgress;
	//投标笔数
    private int bidNumber;
	//金额
    private String amount; 
    //年利率
    private String rate;
    //发布时间(年月日)
    private String releaseDateStr;
    //发布时间(时分秒)
    private String releaseTimeStr;
    
	public String getSpeedProgress() {
		return speedProgress;
	}
	public void setSpeedProgress(String speedProgress) {
		this.speedProgress = speedProgress;
	}
	public int getBidNumber() {
		return bidNumber;
	}
	public void setBidNumber(int bidNumber) {
		this.bidNumber = bidNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public String getReleaseTimeStr() {
		return releaseTimeStr;
	}
	public void setReleaseTimeStr(String releaseTimeStr) {
		this.releaseTimeStr = releaseTimeStr;
	}
}
