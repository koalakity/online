package com.zendaimoney.online.vo.pay;

public class PayWithdrawVO {

	private String rate_withdraw;// 提现手续费率
	private String real_amount;// 可用余额
	private String amount;// 提现金额
	private String customer_name;// 开户名
	private String bank_name; // 银行名称
	private String card_num;// 卡号
	private String realName;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRate_withdraw() {
		return rate_withdraw;
	}

	public void setRate_withdraw(String rate_withdraw) {
		this.rate_withdraw = rate_withdraw;
	}

	public String getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(String real_amount) {
		this.real_amount = real_amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

}
