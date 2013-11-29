package com.zendaimoney.online.entity.financial;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * FreezeFunds entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FREEZE_FUNDS")
public class FinancialFreezeFunds implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8274361500480324600L;
	private BigDecimal freezeId;
	private String freezeKind;
	private BigDecimal userId;
	private BigDecimal loanId;
	private BigDecimal rechargeId;
	private BigDecimal freezeMoney;
	private Date freezeTime;
	private String freezeStatus;

	// Constructors

	/** default constructor */
	public FinancialFreezeFunds() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName="FREEZE_FUNDS_SEQ")
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

	@Column(name = "RECHARGE_ID", precision = 22, scale = 0)
	public BigDecimal getRechargeId() {
		return this.rechargeId;
	}

	public void setRechargeId(BigDecimal rechargeId) {
		this.rechargeId = rechargeId;
	}

	@Column(name = "FREEZE_MONEY", precision = 22, scale = 0)
	public BigDecimal getFreezeMoney() {
		return this.freezeMoney;
	}

	public void setFreezeMoney(BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	@Column(name = "FREEZE_TIME")
	public Date getFreezeTime() {
		return this.freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
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