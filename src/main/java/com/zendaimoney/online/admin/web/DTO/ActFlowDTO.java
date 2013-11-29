package com.zendaimoney.online.admin.web.DTO;

import java.math.BigDecimal;

public class ActFlowDTO {
	
	private String tradeNoAndMemo;
	private String outputStr;
	private String incomingStr;
	private String riskTypeStr;
	private Long id;
	private String tradeDate;
	private String tradeNo;
	private BigDecimal tradeAmount;
	private String tradeType;
	private String loginName;
	private String realName;
	private String phoneNo;
	private String email;
	public String getTradeNoAndMemo() {
		return tradeNoAndMemo;
	}
	public void setTradeNoAndMemo(String tradeNoAndMemo) {
		this.tradeNoAndMemo = tradeNoAndMemo;
	}
	public String getOutputStr() {
		return outputStr;
	}
	public void setOutputStr(String outputStr) {
		this.outputStr = outputStr;
	}
	public String getIncomingStr() {
		return incomingStr;
	}
	public void setIncomingStr(String incomingStr) {
		this.incomingStr = incomingStr;
	}
	public String getRiskTypeStr() {
		return riskTypeStr;
	}
	public void setRiskTypeStr(String riskTypeStr) {
		this.riskTypeStr = riskTypeStr;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
