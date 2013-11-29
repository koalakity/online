package com.zendaimoney.online.entity.fundDetail;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * FreezeFunds entity. @author MyEclipse Persistence Tools
 */
@Entity(name="FreezeFunds")
@Table(name = "FREEZE_FUNDS")
public class FreezeFunds implements java.io.Serializable {


	private FreezeFunds id;
	private BigDecimal freezeId;
	private String freezeKind;
	private BigDecimal userId;
	private BigDecimal loanId;
	private BigDecimal rechargeId;
	private BigDecimal freezeMoney;
	private Timestamp freezeTime;
	private String freezeStatus;

	// Constructors

	/** default constructor */
	public FreezeFunds() {
	}

	/** full constructor */
	public FreezeFunds(BigDecimal freezeId, String freezeKind,
			BigDecimal userId, BigDecimal loanId, BigDecimal rechargeId,
			BigDecimal freezeMoney, Timestamp freezeTime, String freezeStatus) {
		this.freezeId = freezeId;
		this.freezeKind = freezeKind;
		this.userId = userId;
		this.loanId = loanId;
		this.rechargeId = rechargeId;
		this.freezeMoney = freezeMoney;
		this.freezeTime = freezeTime;
		this.freezeStatus = freezeStatus;
	}

	// Property accessors

	@SequenceGenerator(name = "generator", sequenceName = "FREEZE_FUNDS_SEQ")
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FreezeFunds))
			return false;
		FreezeFunds castOther = (FreezeFunds) other;

		return ((this.getFreezeId() == castOther.getFreezeId()) || (this
				.getFreezeId() != null
				&& castOther.getFreezeId() != null && this.getFreezeId()
				.equals(castOther.getFreezeId())))
				&& ((this.getFreezeKind() == castOther.getFreezeKind()) || (this
						.getFreezeKind() != null
						&& castOther.getFreezeKind() != null && this
						.getFreezeKind().equals(castOther.getFreezeKind())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null
						&& castOther.getUserId() != null && this.getUserId()
						.equals(castOther.getUserId())))
				&& ((this.getLoanId() == castOther.getLoanId()) || (this
						.getLoanId() != null
						&& castOther.getLoanId() != null && this.getLoanId()
						.equals(castOther.getLoanId())))
				&& ((this.getRechargeId() == castOther.getRechargeId()) || (this
						.getRechargeId() != null
						&& castOther.getRechargeId() != null && this
						.getRechargeId().equals(castOther.getRechargeId())))
				&& ((this.getFreezeMoney() == castOther.getFreezeMoney()) || (this
						.getFreezeMoney() != null
						&& castOther.getFreezeMoney() != null && this
						.getFreezeMoney().equals(castOther.getFreezeMoney())))
				&& ((this.getFreezeTime() == castOther.getFreezeTime()) || (this
						.getFreezeTime() != null
						&& castOther.getFreezeTime() != null && this
						.getFreezeTime().equals(castOther.getFreezeTime())))
				&& ((this.getFreezeStatus() == castOther.getFreezeStatus()) || (this
						.getFreezeStatus() != null
						&& castOther.getFreezeStatus() != null && this
						.getFreezeStatus().equals(castOther.getFreezeStatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFreezeId() == null ? 0 : this.getFreezeId().hashCode());
		result = 37
				* result
				+ (getFreezeKind() == null ? 0 : this.getFreezeKind()
						.hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getLoanId() == null ? 0 : this.getLoanId().hashCode());
		result = 37
				* result
				+ (getRechargeId() == null ? 0 : this.getRechargeId()
						.hashCode());
		result = 37
				* result
				+ (getFreezeMoney() == null ? 0 : this.getFreezeMoney()
						.hashCode());
		result = 37
				* result
				+ (getFreezeTime() == null ? 0 : this.getFreezeTime()
						.hashCode());
		result = 37
				* result
				+ (getFreezeStatus() == null ? 0 : this.getFreezeStatus()
						.hashCode());
		return result;
	}

}