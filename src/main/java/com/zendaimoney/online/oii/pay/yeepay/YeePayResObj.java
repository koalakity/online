package com.zendaimoney.online.oii.pay.yeepay;

public class YeePayResObj {
	public String p1_MerId = "";// 商户编号
	public String r0_Cmd = "r0_Cmd";// 业务类型
	public String r1_Code = "r1_Code";// 支付结果
	public String r2_TrxId = "r2_TrxId";// 易宝支付交易流水号
	public String r3_Amt = "r3_Amt";// 支付金额
	public String r4_Cur = "r4_Cur";// 交易币种
	public String r5_Pid = "r5_Pid";// 商品名称
	public String r6_Order = "r6_Order";// 商户流水号
	public String r7_Uid = "r7_Uid";// 易宝支付会员ID
	public String r8_MP = "r8_MP";// 商户扩展信息
	public String r9_BType = "r9_BType";// 交易结果返回类型
	public String rb_BankId = "rb_BankId";// 银行编码
	public String ro_BankOrderId = "ro_BankOrderId";// 银行订单号
	public String rp_PayDate = "rp_PayDate";// 支付成功时间
	public String rq_CardNo = "rq_CardNo";// 神州行充值卡序列号
	public String ru_Trxtime = "ru_Trxtime";// 交易结果通知时间
	public String hmac = "";// 签名数据

	public String getP1_MerId() {
		return p1_MerId;
	}

	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
	}

	public String getR0_Cmd() {
		return r0_Cmd;
	}

	public void setR0_Cmd(String r0_Cmd) {
		this.r0_Cmd = r0_Cmd;
	}

	public String getR1_Code() {
		return r1_Code;
	}

	public void setR1_Code(String r1_Code) {
		this.r1_Code = r1_Code;
	}

	public String getR2_TrxId() {
		return r2_TrxId;
	}

	public void setR2_TrxId(String r2_TrxId) {
		this.r2_TrxId = r2_TrxId;
	}

	public String getR3_Amt() {
		return r3_Amt;
	}

	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	public String getR4_Cur() {
		return r4_Cur;
	}

	public void setR4_Cur(String r4_Cur) {
		this.r4_Cur = r4_Cur;
	}

	public String getR5_Pid() {
		return r5_Pid;
	}

	public void setR5_Pid(String r5_Pid) {
		this.r5_Pid = r5_Pid;
	}

	public String getR6_Order() {
		return r6_Order;
	}

	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}

	public String getR7_Uid() {
		return r7_Uid;
	}

	public void setR7_Uid(String r7_Uid) {
		this.r7_Uid = r7_Uid;
	}

	public String getR8_MP() {
		return r8_MP;
	}

	public void setR8_MP(String r8_MP) {
		this.r8_MP = r8_MP;
	}

	public String getR9_BType() {
		return r9_BType;
	}

	public void setR9_BType(String r9_BType) {
		this.r9_BType = r9_BType;
	}

	public String getRb_BankId() {
		return rb_BankId;
	}

	public void setRb_BankId(String rb_BankId) {
		this.rb_BankId = rb_BankId;
	}

	public String getRo_BankOrderId() {
		return ro_BankOrderId;
	}

	public void setRo_BankOrderId(String ro_BankOrderId) {
		this.ro_BankOrderId = ro_BankOrderId;
	}

	public String getRp_PayDate() {
		return rp_PayDate;
	}

	public void setRp_PayDate(String rp_PayDate) {
		this.rp_PayDate = rp_PayDate;
	}

	public String getRq_CardNo() {
		return rq_CardNo;
	}

	public void setRq_CardNo(String rq_CardNo) {
		this.rq_CardNo = rq_CardNo;
	}

	public String getRu_Trxtime() {
		return ru_Trxtime;
	}

	public void setRu_Trxtime(String ru_Trxtime) {
		this.ru_Trxtime = ru_Trxtime;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
