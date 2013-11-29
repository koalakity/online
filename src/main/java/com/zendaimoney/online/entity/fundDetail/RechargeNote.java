package com.zendaimoney.online.entity.fundDetail;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * RechargeNote entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RECHARGE_NOTE")
public class RechargeNote implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private BigDecimal userId;
	private BigDecimal amount;
	private BigDecimal fee;
	private String payKind;
	private String payRs;
	private Date payDate;
	private String flowCode;
	private String bankCode;
	private String note;

	// Constructors

	/** default constructor */
	public RechargeNote() {
	}

	/** minimal constructor */
	public RechargeNote(BigDecimal userId, BigDecimal amount, BigDecimal fee,
			String payKind, String payRs, Date payDate, String flowCode,
			String bankCode) {
		this.userId = userId;
		this.amount = amount;
		this.fee = fee;
		this.payKind = payKind;
		this.payRs = payRs;
		this.payDate = payDate;
		this.flowCode = flowCode;
		this.bankCode = bankCode;
	}

	/** full constructor */
	public RechargeNote(BigDecimal userId, BigDecimal amount, BigDecimal fee,
			String payKind, String payRs, Date payDate, String flowCode,
			String bankCode, String note) {
		this.userId = userId;
		this.amount = amount;
		this.fee = fee;
		this.payKind = payKind;
		this.payRs = payRs;
		this.payDate = payDate;
		this.flowCode = flowCode;
		this.bankCode = bankCode;
		this.note = note;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "AMOUNT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "FEE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getFee() {
		return this.fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	@Column(name = "PAY_KIND", nullable = false, length = 1)
	public String getPayKind() {
		return this.payKind;
	}

	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}

	@Column(name = "PAY_RS", nullable = false, length = 1)
	public String getPayRs() {
		return this.payRs;
	}

	public void setPayRs(String payRs) {
		this.payRs = payRs;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PAY_DATE", nullable = false, length = 7)
	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "FLOW_CODE", nullable = false, length = 50)
	public String getFlowCode() {
		return this.flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	@Column(name = "BANK_CODE", nullable = false, length = 20)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "NOTE", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}