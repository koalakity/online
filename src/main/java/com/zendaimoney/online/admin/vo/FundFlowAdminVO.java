package com.zendaimoney.online.admin.vo;

/**
 * @author Administrator
 * 资金流水信息
 */
public class FundFlowAdminVO {
    private String tradeDate;
    private String tradeType;
    private Double storeAmount;
    private Double payAmount;
    private Double amount;
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public Double getStoreAmount() {
		return storeAmount;
	}
	public void setStoreAmount(Double storeAmount) {
		this.storeAmount = storeAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
    
}
