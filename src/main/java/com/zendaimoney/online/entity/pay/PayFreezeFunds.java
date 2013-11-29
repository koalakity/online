package com.zendaimoney.online.entity.pay;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * FreezeFunds entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FREEZE_FUNDS")
public class PayFreezeFunds implements java.io.Serializable {

	// Fields

	private BigDecimal freezeId;
	private String freezeKind;
	private BigDecimal userId;
	private BigDecimal loanId;
	private PayExtractNote rechargeId;
	private BigDecimal freezeMoney;
	private Timestamp freezeTime;
	private String freezeStatus;

	// Constructors

	/** default constructor */
	public PayFreezeFunds() {
	}


	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="FREEZE_FUNDS_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "FREEZE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFreezeId() {
		return this.freezeId;
	}

	public void setFreezeId(BigDecimal freezeId) {
		this.freezeId = freezeId;
	}

	@Column(name = "FREEZE_KIND", length = 1)
	public String getFreezeKind() {
		return this.freezeKind;
	}

	public void setFreezeKind(String freezeKind) {
		this.freezeKind = freezeKind;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "LOAN_ID", precision = 22, scale = 0)
	public BigDecimal getLoanId() {
		return this.loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="RECHARGE_ID",referencedColumnName="EXTRACT_ID")
	public PayExtractNote getRechargeId() {
		return this.rechargeId;
	}

	public void setRechargeId(PayExtractNote rechargeId) {
		this.rechargeId = rechargeId;
	}

	@Column(name = "FREEZE_MONEY", precision = 22, scale = 0)
	public BigDecimal getFreezeMoney() {
		return this.freezeMoney;
	}

	public void setFreezeMoney(BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	@Column(name = "FREEZE_TIME", length = 11)
	public Timestamp getFreezeTime() {
		return this.freezeTime;
	}

	public void setFreezeTime(Timestamp freezeTime) {
		this.freezeTime = freezeTime;
	}

	@Column(name = "FREEZE_STATUS", length = 1)
	public String getFreezeStatus() {
		return this.freezeStatus;
	}

	public void setFreezeStatus(String freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

}