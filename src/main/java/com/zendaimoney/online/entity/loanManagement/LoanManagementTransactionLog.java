package com.zendaimoney.online.entity.loanManagement;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TransactionLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TRANSACTION_LOG")
public class LoanManagementTransactionLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2959773166739100985L;
	private BigDecimal transactionId;
	private BigDecimal transactionKind;
	private BigDecimal operateId;
	private String operateContext;
	private String operateTime;
	private String longinIp;
	private String description;

	// Constructors

	/** default constructor */
	public LoanManagementTransactionLog() {
	}

	/** minimal constructor */
	public LoanManagementTransactionLog(BigDecimal transactionKind, BigDecimal operateId,
			String operateContext, String operateTime, String longinIp) {
		this.transactionKind = transactionKind;
		this.operateId = operateId;
		this.operateContext = operateContext;
		this.operateTime = operateTime;
		this.longinIp = longinIp;
	}

	/** full constructor */
	public LoanManagementTransactionLog(BigDecimal transactionKind, BigDecimal operateId,
			String operateContext, String operateTime, String longinIp,
			String description) {
		this.transactionKind = transactionKind;
		this.operateId = operateId;
		this.operateContext = operateContext;
		this.operateTime = operateTime;
		this.longinIp = longinIp;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName = "TRANSACTIONLOG_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "TRANSACTION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name = "TRANSACTION_KIND", nullable = false, precision = 22, scale = 0)
	public BigDecimal getTransactionKind() {
		return this.transactionKind;
	}

	public void setTransactionKind(BigDecimal transactionKind) {
		this.transactionKind = transactionKind;
	}

	@Column(name = "OPERATE_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getOperateId() {
		return this.operateId;
	}

	public void setOperateId(BigDecimal operateId) {
		this.operateId = operateId;
	}

	@Column(name = "OPERATE_CONTEXT", nullable = false, length = 100)
	public String getOperateContext() {
		return this.operateContext;
	}

	public void setOperateContext(String operateContext) {
		this.operateContext = operateContext;
	}

	@Column(name = "OPERATE_TIME", nullable = false)
	public String getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "LONGIN_IP", nullable = false, length = 50)
	public String getLonginIp() {
		return this.longinIp;
	}

	public void setLonginIp(String longinIp) {
		this.longinIp = longinIp;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}