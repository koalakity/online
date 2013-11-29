package com.zendaimoney.online.oii.pay.yeepay;

public class PayResObj {
	public String orderno = "";// 订单编号
	public String amount = "";// 金额
	public String userId = "";// 用户Id
	public String payRs = "";// 支付结果
	public String flowno = "";// 支付平台交易流水号
	public String bankCode = "";// 银行编码
	//0易宝,1国付宝,2盛付通
	public String payKind = "";
	
	public String getPayKind() {
		return payKind;
	}
	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPayRs() {
		return payRs;
	}
	public void setPayRs(String payRs) {
		this.payRs = payRs;
	}
	public String getFlowno() {
		return flowno;
	}
	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	 
 
}
