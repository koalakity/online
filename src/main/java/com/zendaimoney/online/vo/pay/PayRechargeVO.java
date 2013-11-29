package com.zendaimoney.online.vo.pay;

import com.zendaimoney.online.common.ObjectFormatUtil;

public class PayRechargeVO {

	private double real_amount;// 可用余额
	
	private double rate;//充值费率



	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getReal_amount() {
		String realAmount = ObjectFormatUtil.formatCurrency(real_amount);
		return realAmount.substring(1, realAmount.length());
	}

	public void setReal_amount(double real_amount) {
		this.real_amount = real_amount;
	}

}
