package com.zendaimoney.online.vo.homepage;

public class HomepageLeaveMsgVO {

	private String loanId;

	// 标的种类 1借款 2理财
	private String loanKind;

	// 标题
	private String title;

	// 状态
	private String status;

	// 留言条数
	private String leaveCount;

	// 最后留言时间
	private String lastLeaveTime;

	// 发布时间
	private String releaseTime;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(String leaveCount) {
		this.leaveCount = leaveCount;
	}

	public String getLastLeaveTime() {
		return lastLeaveTime;
	}

	public void setLastLeaveTime(String lastLeaveTime) {
		this.lastLeaveTime = lastLeaveTime;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

}
