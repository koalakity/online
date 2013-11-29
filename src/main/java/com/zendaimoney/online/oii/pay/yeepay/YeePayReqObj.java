package com.zendaimoney.online.oii.pay.yeepay;

public class YeePayReqObj {
	
	private String p0_Cmd = "";// 业务类型
	private String p1_MerId = "";// 商户编号
	private String p2_Order = "";// 商户订单号
	private String p3_Amt = "";// 支付金额
	private String p4_Cur = "";// 交易币种
	private String p5_Pid = "";// 商品名称
	private String p6_Pcat = "";// 商品种类
	private String p7_Pdesc = "";// 商品描述
	private String p8_Url = "";// 商户接收支付成功数据的地址
	private String p9_SAF = "";// 送货地址
	private String pa_MP = "";// 商户扩展信息
	private String pd_FrpId = "";// 支付通道编码
	private String pr_NeedResponse = "";// 应答机制
	private String hmac = "";// 签名数据
	private String nodeAuthorizationURL;//易宝服务URL
	private String keyValue;//商户密钥
	
	private String r9_BType;//
	
	
	

	public String getR9_BType() {
		return r9_BType;
	}

	public void setR9_BType(String r9_BType) {
		this.r9_BType = r9_BType;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getP0_Cmd() {
		return p0_Cmd;
	}

	public void setP0_Cmd(String p0_Cmd) {
		this.p0_Cmd = p0_Cmd;
	}

	public String getP1_MerId() {
		return p1_MerId;
	}

	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
	}

	public String getP2_Order() {
		return p2_Order;
	}

	public void setP2_Order(String p2_Order) {
		this.p2_Order = p2_Order;
	}

	public String getP3_Amt() {
		return p3_Amt;
	}

	public void setP3_Amt(String p3_Amt) {
		this.p3_Amt = p3_Amt;
	}

	public String getP4_Cur() {
		return p4_Cur;
	}

	public void setP4_Cur(String p4_Cur) {
		this.p4_Cur = p4_Cur;
	}

	public String getP5_Pid() {
		return p5_Pid;
	}

	public void setP5_Pid(String p5_Pid) {
		this.p5_Pid = p5_Pid;
	}

	public String getP6_Pcat() {
		return p6_Pcat;
	}

	public void setP6_Pcat(String p6_Pcat) {
		this.p6_Pcat = p6_Pcat;
	}

	public String getP7_Pdesc() {
		return p7_Pdesc;
	}

	public void setP7_Pdesc(String p7_Pdesc) {
		this.p7_Pdesc = p7_Pdesc;
	}

	public String getP8_Url() {
		return p8_Url;
	}

	public void setP8_Url(String p8_Url) {
		this.p8_Url = p8_Url;
	}

	public String getP9_SAF() {
		return p9_SAF;
	}

	public void setP9_SAF(String p9_SAF) {
		this.p9_SAF = p9_SAF;
	}

	public String getPa_MP() {
		return pa_MP;
	}

	public void setPa_MP(String pa_MP) {
		this.pa_MP = pa_MP;
	}

	public String getPd_FrpId() {
		return pd_FrpId;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	public String getPr_NeedResponse() {
		return pr_NeedResponse;
	}

	public void setPr_NeedResponse(String pr_NeedResponse) {
		this.pr_NeedResponse = pr_NeedResponse;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getNodeAuthorizationURL() {
		return nodeAuthorizationURL;
	}

	public void setNodeAuthorizationURL(String nodeAuthorizationURL) {
		this.nodeAuthorizationURL = nodeAuthorizationURL;
	}
}
