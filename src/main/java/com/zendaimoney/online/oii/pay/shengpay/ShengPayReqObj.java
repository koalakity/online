
package com.zendaimoney.online.oii.pay.shengpay;


public class ShengPayReqObj {
	
	private String name="B2CPayment";//版本名称
	 
	/**
	 * 版本号
	 */
	private String version="V4.1.1.1.1";
	
	private String charset = "GBK";
	private String msgSender = "";//发送方标识
	private String sendTime = "";//发送支付请求时间
	/**
	 * 商户订单号
	 */
	private String orderNo= "";
	/**
	 * 支付金额
	 */
	private String orderAmount= "";
	
	/**
	 * 订单日期
	 */
	private String orderTime= "";
	private String payType= "PT001";//支付类型编码
	private String instCode= "";//银行编码
	private String pageUrl= "";//浏览器回调地址
	/**
	 * 发货通知地址
	 */
	private String notifyUrl= "";
	private String productName = "";//商品名称
	private String buyerContact= "";//支付人联系方式
	private String buyerIp = "";//
	private String ext1="";//扩展字段
	
	private String signType= "MD5";//签名类型
	private String signMsg= "";//签名结果
	
	private String nodeAuthorizationURL = "";//
	
	
	
	
	
	
	public String getNodeAuthorizationURL() {
		return nodeAuthorizationURL;
	}
	public void setNodeAuthorizationURL(String nodeAuthorizationURL) {
		this.nodeAuthorizationURL = nodeAuthorizationURL;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getMsgSender() {
		return msgSender;
	}
	public void setMsgSender(String msgSender) {
		this.msgSender = msgSender;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getInstCode() {
		return instCode;
	}
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public String getBuyerIp() {
		return buyerIp;
	}
	public void setBuyerIp(String buyerIp) {
		this.buyerIp = buyerIp;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	public String toSignString() {
		StringBuffer buf = new StringBuffer();
		buf.append(name != null ? name : "");
		buf.append(version != null ? version : "");
		buf.append(charset != null ? charset : "");
		buf.append(msgSender != null ? msgSender : "");
		buf.append(sendTime != null ? sendTime : "");
		buf.append(orderNo != null ? orderNo : "");		
		buf.append(orderAmount != null ? orderAmount : "");		
		buf.append(orderTime != null ? orderTime : "");
		buf.append(payType != null ? payType : "");
		buf.append(instCode != null ? instCode : "");
		buf.append(pageUrl != null ? pageUrl : "");
		buf.append(notifyUrl != null ? notifyUrl : "");
		buf.append(productName != null ? productName : "");		
		buf.append(buyerContact != null ? buyerContact : "");
		buf.append(buyerIp != null ? buyerIp : "");		
		buf.append(ext1 != null ? ext1 : "");		
		
		 
		buf.append(signType != null ? signType : "");
		 
		
		
		return buf.toString();

	}
	  

}
