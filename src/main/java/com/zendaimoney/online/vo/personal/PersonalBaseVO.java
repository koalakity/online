package com.zendaimoney.online.vo.personal;

import javax.persistence.Column;

public class PersonalBaseVO {
	private String channelFName;// 一级渠道名称
	private String channelCName;// 二级渠道名称
	private String loginName;// 昵称
	private String email;// 邮箱
	private String iconPath;// 头象路径
	private String name;// 真实姓名
	private String sex;// 性别
	private String credentialType;// 证件类型
	private String credentialNum;// 身份证号
	private String mobile;// 手机号
	private String hometownArea;// 籍贯-省
	private String hometownCity;// 籍贯-市
	private String currAddr;// 现居住地
	private String liveAreaCode;// 区号
	private String liveTel;// 居住地电话号(不含区号)
	private String livePost;// 居住地邮编
	private String marriage;// 婚否
	private String child;// 子女
	private String monIncome;// 月收入
	private String immFamilyName;// 直系亲属姓名
	private String immFamilyRela;// 直系亲属关系
	private String immFamilyMobile;// 直系亲属手机
	private String otherContactName;// 其他联系人姓名
	private String otherContactRela;// 其他联系人关系
	private String otherContactMobile;// 其他联系人手机
	private String qq;// QQ
	private String msn;// MSN
	private String weibo;// 新浪微博
	private String token;// 令牌
	private String flag;
	/**
	 * 新增6个字段，为联系人三和 联系人四相关熟悉 外加1个备注字段
	 */
	private String threeContactName;
	private String threeContactRelation;
	private String threeContactPhone;
	private String fourthContactName;
	private String fourthContactRelation;
	private String fourthContactPhone;

	public String getChannelFName() {
		return channelFName;
	}

	public void setChannelFName(String channelFName) {
		this.channelFName = channelFName;
	}

	public String getChannelCName() {
		return channelCName;
	}

	public void setChannelCName(String channelCName) {
		this.channelCName = channelCName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCredentialNum() {
		return credentialNum;
	}

	public void setCredentialNum(String credentialNum) {
		this.credentialNum = credentialNum;
	}

	public String getLiveAreaCode() {
		return liveAreaCode;
	}

	public void setLiveAreaCode(String liveAreaCode) {
		this.liveAreaCode = liveAreaCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHometownArea() {
		return hometownArea;
	}

	public void setHometownArea(String hometownArea) {
		this.hometownArea = hometownArea;
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

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getHometownCity() {
		return hometownCity;
	}

	public void setHometownCity(String hometownCity) {
		this.hometownCity = hometownCity;
	}

	public String getCurrAddr() {
		return currAddr;
	}

	public void setCurrAddr(String currAddr) {
		this.currAddr = currAddr;
	}

	public String getLiveTel() {
		return liveTel;
	}

	public void setLiveTel(String liveTel) {
		this.liveTel = liveTel;
	}

	public String getLivePost() {
		return livePost;
	}

	public void setLivePost(String livePost) {
		this.livePost = livePost;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getMonIncome() {
		return monIncome;
	}

	public void setMonIncome(String monIncome) {
		this.monIncome = monIncome;
	}

	public String getImmFamilyName() {
		return immFamilyName;
	}

	public void setImmFamilyName(String immFamilyName) {
		this.immFamilyName = immFamilyName;
	}

	public String getImmFamilyRela() {
		return immFamilyRela;
	}

	public void setImmFamilyRela(String immFamilyRela) {
		this.immFamilyRela = immFamilyRela;
	}

	public String getImmFamilyMobile() {
		return immFamilyMobile;
	}

	public void setImmFamilyMobile(String immFamilyMobile) {
		this.immFamilyMobile = immFamilyMobile;
	}

	public String getOtherContactName() {
		return otherContactName;
	}

	public void setOtherContactName(String otherContactName) {
		this.otherContactName = otherContactName;
	}

	public String getOtherContactRela() {
		return otherContactRela;
	}

	public void setOtherContactRela(String otherContactRela) {
		this.otherContactRela = otherContactRela;
	}

	public String getOtherContactMobile() {
		return otherContactMobile;
	}

	public void setOtherContactMobile(String otherContactMobile) {
		this.otherContactMobile = otherContactMobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "THREE_CONTACT_NAME", length = 40)
	public String getThreeContactName() {
		return threeContactName;
	}

	public void setThreeContactName(String threeContactName) {
		this.threeContactName = threeContactName;
	}

	@Column(name = "THREE_CONTACT_RELATION", length = 40)
	public String getThreeContactRelation() {
		return threeContactRelation;
	}

	public void setThreeContactRelation(String threeContactRelation) {
		this.threeContactRelation = threeContactRelation;
	}

	@Column(name = "THREE_CONTACT_PHONE", length = 40)
	public String getThreeContactPhone() {
		return threeContactPhone;
	}

	public void setThreeContactPhone(String threeContactPhone) {
		this.threeContactPhone = threeContactPhone;
	}

	@Column(name = "FOURTH_CONTACT_NAME", length = 40)
	public String getFourthContactName() {
		return fourthContactName;
	}

	public void setFourthContactName(String fourthContactName) {
		this.fourthContactName = fourthContactName;
	}

	@Column(name = "FOURTH_CONTACT_RELATION", length = 40)
	public String getFourthContactRelation() {
		return fourthContactRelation;
	}

	public void setFourthContactRelation(String fourthContactRelation) {
		this.fourthContactRelation = fourthContactRelation;
	}

	@Column(name = "FOURTH_CONTACT_PHONE", length = 40)
	public String getFourthContactPhone() {
		return fourthContactPhone;
	}

	public void setFourthContactPhone(String fourthContactPhone) {
		this.fourthContactPhone = fourthContactPhone;
	}
}
