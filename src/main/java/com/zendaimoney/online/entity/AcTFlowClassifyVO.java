package com.zendaimoney.online.entity;

// default package

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sun.misc.Compare;

/**
 * AcTFlowClassify entity. @author MyEclipse Persistence Tools
 */
@Entity(name="AcTFlowClassifyVO")
@Table(name = "AC_T_FLOW_CLASSIFY")
public class AcTFlowClassifyVO implements java.io.Serializable,Comparable{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1889332755437944687L;
	private Long id;
	//引用TradeTypeConstants中的常量
	private String tradeType;
	//类型TypeConstants中的常量
	private int type;
	//流水号
	private String tradeNo;
	//出方用户ID
	private Long outUserId;
	//出方账户,金额
	private BigDecimal outCashAccount;
	//出方提现冻结金额
	private BigDecimal outCashFreeze;
	//出方投标冻结
	private BigDecimal outTenderFreeze;
	//入方用户ID
	private Long inUserId;
	//入方账户,金额
	private BigDecimal inCashAccount;
	//入方提现冻结金额
	private BigDecimal inCashFreeze;
	//入方投标冻结
	private BigDecimal inTenderFreeze;
	//交易金额
	private BigDecimal tradeAmount;
	//交易记录ID
	private Long tradeCode;
	//时间（年月日，时分秒）
	private Date greateTime;
	private String memo;
	

	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@SequenceGenerator(name = "generator", sequenceName="ACTFLOWCLASSIFY_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TRADE_TYPE", precision = 22, scale = 0)
	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "TRADE_NO", length = 20)
	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name = "OUT_USER_ID", precision = 22, scale = 0)
	public Long getOutUserId() {
		return this.outUserId;
	}

	public void setOutUserId(Long outUserId) {
		this.outUserId = outUserId;
	}

	@Column(name = "OUT_CASH_ACCOUNT", precision = 22, scale = 7)
	public BigDecimal getOutCashAccount() {
		return this.outCashAccount;
	}

	public void setOutCashAccount(BigDecimal outCashAccount) {
		this.outCashAccount = outCashAccount;
	}

	@Column(name = "OUT_CASH_FREEZE", precision = 22, scale = 7)
	public BigDecimal getOutCashFreeze() {
		return this.outCashFreeze;
	}

	public void setOutCashFreeze(BigDecimal outCashFreeze) {
		this.outCashFreeze = outCashFreeze;
	}

	@Column(name = "OUT_TENDER_FREEZE", precision = 22, scale = 7)
	public BigDecimal getOutTenderFreeze() {
		return this.outTenderFreeze;
	}

	public void setOutTenderFreeze(BigDecimal outTenderFreeze) {
		this.outTenderFreeze = outTenderFreeze;
	}

	@Column(name = "IN_USER_ID", precision = 22, scale = 0)
	public Long getInUserId() {
		return this.inUserId;
	}

	public void setInUserId(Long inUserId) {
		this.inUserId = inUserId;
	}

	@Column(name = "IN_CASH_ACCOUNT", precision = 22, scale = 7)
	public BigDecimal getInCashAccount() {
		return this.inCashAccount;
	}

	public void setInCashAccount(BigDecimal inCashAccount) {
		this.inCashAccount = inCashAccount;
	}

	@Column(name = "IN_CASH_FREEZE", precision = 22, scale = 7)
	public BigDecimal getInCashFreeze() {
		return this.inCashFreeze;
	}

	public void setInCashFreeze(BigDecimal inCashFreeze) {
		this.inCashFreeze = inCashFreeze;
	}

	@Column(name = "IN_TENDER_FREEZE", precision = 22, scale = 7)
	public BigDecimal getInTenderFreeze() {
		return this.inTenderFreeze;
	}

	public void setInTenderFreeze(BigDecimal inTenderFreeze) {
		this.inTenderFreeze = inTenderFreeze;
	}

	@Column(name = "TRADE_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	@Column(name = "TRADE_CODE", precision = 22, scale = 0)
	public Long getTradeCode() {
		return this.tradeCode;
	}

	public void setTradeCode(Long tradeCode) {
		this.tradeCode = tradeCode;
	}

	@Column(name = "GREATE_TIME")
	public Date getGreateTime() {
		return this.greateTime;
	}

	public void setGreateTime(Date greateTime) {
		this.greateTime = greateTime;
	}
	
	@Column(name = "TYPE")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	@Override
	public int compareTo(Object o) {
		AcTFlowClassifyVO vo=(AcTFlowClassifyVO)o;
		if(vo.getGreateTime().getTime()>this.getGreateTime().getTime()){
			return 1;
		}
		return 0;
	}

}