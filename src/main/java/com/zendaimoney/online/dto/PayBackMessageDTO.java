package com.zendaimoney.online.dto;

import java.math.BigDecimal;



public class PayBackMessageDTO {

	//理财人Id
	private Long investId;
	//月还本息
	private String principanInterestMonth = "￥0.00";
	//逾期罚息
	private String overdueInterest = "￥0.00";
	//逾期违约金
	private String overdueFines = "￥0.00";
	//理财人手机 用于短信通知
	private String phoneNO;
	//理财人登陆名
	private String loginName;
	//理财人邮箱
	private String email;
	//理财人ID
	private Long userId;
	//剩余本金
	private String surplusPrincipal = "￥0.00";
	// 提前还款违约金
	private String advanceOncePayBreachPenalty = "￥0.00";
	public Long getInvestId() {
		return investId;
	}
	public void setInvestId(Long investId) {
		this.investId = investId;
	}
	public String getPrincipanInterestMonth() {
		return principanInterestMonth;
	}
	public void setPrincipanInterestMonth(String principanInterestMonth) {
		this.principanInterestMonth = principanInterestMonth;
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
	public String getPhoneNO() {
		return phoneNO;
	}
	public void setPhoneNO(String phoneNum) {
		this.phoneNO = phoneNum;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSurplusPrincipal() {
		return surplusPrincipal;
	}
	public void setSurplusPrincipal(String surplusPrincipal) {
		this.surplusPrincipal = surplusPrincipal;
	}
	public String getAdvanceOncePayBreachPenalty() {
		return advanceOncePayBreachPenalty;
	}
	public void setAdvanceOncePayBreachPenalty(
			String advanceOncePayBreachPenalty) {
		this.advanceOncePayBreachPenalty = advanceOncePayBreachPenalty;
	}
	
}
