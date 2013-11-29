package com.zendaimoney.online.oii.pay.gopay;

public class GoPayReqObj {
	
	private String version = "";// 版本号
	private String charset = "";// 字符集
	private String language = "";//语言种类
	private String signType = "";// 加密方式
	private String tranCode = "";// 交易代码
	private String merchantID = "";//商户ID
	private String merOrderNum = "";// 订单号
	private String tranAmt = "";// 交易金额
	private String feeAmt = "";// 手续费
	private String currencyType = "";//币种
	private String frontMerUrl = "";// 商户返回页面地址
	private String backgroundMerUrl = "";// 商户后台通知地址
	private String tranDateTime = "";// 交易时间
	private String virCardNoIn = "";// 国付宝转入账户
	private String tranIP= "";//用户浏览器IP
	private String isRepeatSubmit= "";//订单是否允许重复提交
	
	private String goodsName= "";//商品名称
	private String goodsDetail= "";//商品详情
	private String buyerName= "";//买方姓名
	private String buyerContact= "";//买方联系方式
	private String merRemark1= "";//商户备用信息字段1
	private String merRemark2= "";//商户备用信息字段2
	
	private String bankCode= "";//银行代码
	private String userType= "";//用户类型
	private String signValue= "";//密文串
	
	private String nodeAuthorizationURL="";
	
	
	
	public String getNodeAuthorizationURL() {
		return nodeAuthorizationURL;
	}
	public void setNodeAuthorizationURL(String nodeAuthorizationURL) {
		this.nodeAuthorizationURL = nodeAuthorizationURL;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getMerOrderNum() {
		return merOrderNum;
	}
	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getFrontMerUrl() {
		return frontMerUrl;
	}
	public void setFrontMerUrl(String frontMerUrl) {
		this.frontMerUrl = frontMerUrl;
	}
	public String getBackgroundMerUrl() {
		return backgroundMerUrl;
	}
	public void setBackgroundMerUrl(String backgroundMerUrl) {
		this.backgroundMerUrl = backgroundMerUrl;
	}
	public String getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getVirCardNoIn() {
		return virCardNoIn;
	}
	public void setVirCardNoIn(String virCardNoIn) {
		this.virCardNoIn = virCardNoIn;
	}
	public String getTranIP() {
		return tranIP;
	}
	public void setTranIP(String tranIP) {
		this.tranIP = tranIP;
	}
	public String getIsRepeatSubmit() {
		return isRepeatSubmit;
	}
	public void setIsRepeatSubmit(String isRepeatSubmit) {
		this.isRepeatSubmit = isRepeatSubmit;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public String getMerRemark1() {
		return merRemark1;
	}
	public void setMerRemark1(String merRemark1) {
		this.merRemark1 = merRemark1;
	}
	public String getMerRemark2() {
		return merRemark2;
	}
	public void setMerRemark2(String merRemark2) {
		this.merRemark2 = merRemark2;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	 
	public String getSignValue() {
		return signValue;
	}
	public void setSignValue(String signValue) {
		this.signValue = signValue;
	}
	
	
	 
}
