package com.zendaimoney.online.entity.fundDetail;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * FundsAcc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FUNDS_ACC")
public class FundsAcc implements java.io.Serializable {

	// Fields

	private BigDecimal fundsAccId;
	private BigDecimal customerId;
	private BigDecimal accAmount;
	private String memo;

	// Constructors

	/** default constructor */
	public FundsAcc() {
	}

	/** minimal constructor */
	public FundsAcc(BigDecimal customerId, BigDecimal accAmount) {
		this.customerId = customerId;
		this.accAmount = accAmount;
	}

	/** full constructor */
	public FundsAcc(BigDecimal customerId, BigDecimal accAmount, String memo) {
		this.customerId = customerId;
		this.accAmount = accAmount;
		this.memo = memo;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "FUNDS_ACC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFundsAccId() {
		return this.fundsAccId;
	}

	public void setFundsAccId(BigDecimal fundsAccId) {
		this.fundsAccId = fundsAccId;
	}

	@Column(name = "CUSTOMER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "ACC_AMOUNT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getAccAmount() {
		return this.accAmount;
	}

	public void setAccAmount(BigDecimal accAmount) {
		this.accAmount = accAmount;
	}

	@Column(name = "MEMO", length = 100)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}