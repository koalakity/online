package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class UserListForm {
	private String channelFId;
	private String channelCId;
	private BigDecimal creditGrade;
	private BigDecimal userState;
	private BigDecimal regType;

	@DateTimeFormat(iso = ISO.DATE)
	private Date regDateMin;
	@DateTimeFormat(iso = ISO.DATE)
	private Date regDateMax;
	@DateTimeFormat(iso = ISO.DATE)
	private Date lastLoginDateMin;
	@DateTimeFormat(iso = ISO.DATE)
	private Date lastLoginDateMax;

	private String loginName;
	private String email;
	private String phoneNo;
	private String identityNo;
	private String realName;

	private BigDecimal isApproveCard;

	
	public String getChannelFId() {
		return channelFId;
	}

	public void setChannelFId(String channelFId) {
		this.channelFId = channelFId;
	}

	public String getChannelCId() {
		return channelCId;
	}

	public void setChannelCId(String channelCId) {
		this.channelCId = channelCId;
	}

	public BigDecimal getIsApproveCard() {
		return isApproveCard;
	}

	public void setIsApproveCard(BigDecimal isApproveCard) {
		this.isApproveCard = isApproveCard;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public BigDecimal getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(BigDecimal creditGrade) {
		this.creditGrade = creditGrade;
	}

	public BigDecimal getUserState() {
		return userState;
	}

	public void setUserState(BigDecimal userState) {
		this.userState = userState;
	}

	public BigDecimal getRegType() {
		return regType;
	}

	public void setRegType(BigDecimal regType) {
		this.regType = regType;
	}

	public Date getRegDateMin() {
		return regDateMin;
	}

	public void setRegDateMin(Date regDateMin) {
		this.regDateMin = regDateMin;
	}

	public Date getRegDateMax() {
		return regDateMax;
	}

	public void setRegDateMax(Date regDateMax) {
		this.regDateMax = regDateMax;
	}

	public Date getLastLoginDateMin() {
		return lastLoginDateMin;
	}

	public void setLastLoginDateMin(Date lastLoginDateMin) {
		this.lastLoginDateMin = lastLoginDateMin;
	}

	public Date getLastLoginDateMax() {
		return lastLoginDateMax;
	}

	public void setLastLoginDateMax(Date lastLoginDateMax) {
		this.lastLoginDateMax = lastLoginDateMax;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

}
