package com.zendaimoney.online.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * FreezeFunds entity. @author MyEclipse Persistence Tools
 */
@Entity(name="FreezeFundsVO")
@Table(name = "FREEZE_FUNDS")
public class FreezeFundsVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6013055262175123310L;
	private Long freezeId;
	private Long userId;
	private Long loanId;
	private Long rechargeId;
	private String freezeKind;
	private BigDecimal freezeMoney;
	private Date freezeTime;
	private String freezeStatus;


	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="FREEZE_FUNDS_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "FREEZE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getFreezeId() {
		return this.freezeId;
	}

	public void setFreezeId(Long freezeId) {
		this.freezeId = freezeId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "LOAN_ID", precision = 22, scale = 0)
	public Long getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	@Column(name = "RECHARGE_ID", precision = 22, scale = 0)
	public Long getRechargeId() {
		return this.rechargeId;
	}

	public void setRechargeId(Long rechargeId) {
		this.rechargeId = rechargeId;
	}

	@Column(name = "FREEZE_KIND", length = 1)
	public String getFreezeKind() {
		return this.freezeKind;
	}

	public void setFreezeKind(String freezeKind) {
		this.freezeKind = freezeKind;
	}

	@Column(name = "FREEZE_MONEY", precision = 22, scale = 0)
	public BigDecimal getFreezeMoney() {
		return this.freezeMoney;
	}

	public void setFreezeMoney(BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	@Column(name = "FREEZE_TIME", length = 11)
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