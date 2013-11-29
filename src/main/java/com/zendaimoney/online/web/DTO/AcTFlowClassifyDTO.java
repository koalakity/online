package com.zendaimoney.online.web.DTO;


// default package

import java.math.BigDecimal;
import java.util.Date;

import com.zendaimoney.online.entity.AcTFlowClassifyVO;

/**
 * AcTFlowClassify entity. @author MyEclipse Persistence Tools
 */
public class AcTFlowClassifyDTO implements java.io.Serializable{

	public String toString(){
		return "in:"+getIn()+" out:"+getOut()+" type:"+getType()+" amount:"+getAmount()+" date:"+getDate().toLocaleString()+" code:"+getCode();
	}
	
	//存入金额
	private BigDecimal in;
	//存入用户昵称
	private String inUserName;
	//支出金额
	private BigDecimal out;
	//支出用户昵称
	private String outUserName;
	//类型(对应TypeConstants)
	private String type;
	//账户总额
	private BigDecimal amount;
	//交易时间
	private Date date;
	//备注
	private Long code;
	private String tradeType;
	
	
	public String getInUserName() {
		return inUserName;
	}
	public void setInUserName(String inUserName) {
		this.inUserName = inUserName;
	}
	public String getOutUserName() {
		return outUserName;
	}
	public void setOutUserName(String outUserName) {
		this.outUserName = outUserName;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public BigDecimal getIn() {
		return in;
	}
	public void setIn(BigDecimal in) {
		this.in = in;
	}
	public BigDecimal getOut() {
		return out;
	}
	public void setOut(BigDecimal out) {
		this.out = out;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	
}