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
 * InvestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INVEST_INFO")
public class InvestInfo implements java.io.Serializable {

	// Fields

	private BigDecimal investId;
	private BigDecimal loanId;
	private BigDecimal userId;
	private Double investAmount;
	private Double havaScale;
	private String investTime;
	private String description;
	private String status;
	private BigDecimal ledgerFinanceId;



	/** default constructor */
	public InvestInfo() {
	}

	/** minimal constructor */
	public InvestInfo(BigDecimal loanId, BigDecimal userId,
			Double investAmount, Double havaScale, String investTime) {
		this.loanId = loanId;
		this.userId = userId;
		this.investAmount = investAmount;
		this.havaScale = havaScale;
		this.investTime = investTime;
	}

	/** full constructor */
	public InvestInfo(BigDecimal loanId, BigDecimal userId,
			Double investAmount, Double havaScale, String investTime,
			String description, String status) {
		this.loanId = loanId;
		this.userId = userId;
		this.investAmount = investAmount;
		this.havaScale = havaScale;
		this.investTime = investTime;
		this.description = description;
		this.status = status;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "INVEST_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getInvestId() {
		return this.investId;
	}

	public void setInvestId(BigDecimal investId) {
		this.investId = investId;
	}

	@Column(name = "LOAN_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoanId() {
		return this.loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "INVEST_AMOUNT", nullable = false, precision = 22, scale = 7)
	public Double getInvestAmount() {
		return this.investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	@Column(name = "HAVA_SCALE", nullable = false, precision = 22, scale = 18)
	public Double getHavaScale() {
		return this.havaScale;
	}

	public void setHavaScale(Double havaScale) {
		this.havaScale = havaScale;
	}

	@Column(name = "INVEST_TIME", nullable = false)
	public String getInvestTime() {
		return this.investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	// Constructors
	@Column(name = "LEDGER_FINANCE_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLedgerFinanceId() {
		return ledgerFinanceId;
	}

	public void setLedgerFinanceId(BigDecimal ledgerFinanceId) {
		this.ledgerFinanceId = ledgerFinanceId;
	}

}