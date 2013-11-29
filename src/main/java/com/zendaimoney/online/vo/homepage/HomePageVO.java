package com.zendaimoney.online.vo.homepage;

public class HomePageVO {
	
	private String userName;// 用户昵称
	private String registerTime;// 注册时间
	private String currPlace;// 现所在地
	private String grade;// 信用等级
	private String accAmount;// 账户余额
	private String freezeAmount;// 冻结金额
	private String loanCount;// 借款笔数
	private String bidCount;// 投标笔数

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getCurrPlace() {
		return currPlace;
	}

	public void setCurrPlace(String currPlace) {
		this.currPlace = currPlace;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAccAmount() {
		return accAmount;
	}

	public void setAccAmount(String accAmount) {
		this.accAmount = accAmount;
	}

	public String getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(String freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public String getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(String loanCount) {
		this.loanCount = loanCount;
	}

	public String getBidCount() {
		return bidCount;
	}

	public void setBidCount(String bidCount) {
		this.bidCount = bidCount;
	}
}
