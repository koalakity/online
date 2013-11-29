package com.zendaimoney.online.entity.myInvestment;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * InvestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INVEST_INFO")
public class MyInvestmentInvestInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2019299939884348701L;
	private BigDecimal investId;
	private MyInvestmentLoanInfo loanId;
	private BigDecimal userId;
	private Double investAmount;
	private Double havaScale;
	private Date investTime;
	private String description;
	private String status;
	private MyInvestmentAcTLedgerFinance ledgerFinanceId;

	// Constructors

	/** default constructor */
	public MyInvestmentInvestInfo() {
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

	@OneToOne
	@JoinColumn(name = "LOAN_ID" ,referencedColumnName = "LOAN_ID")
	public MyInvestmentLoanInfo getLoanId() {
		return this.loanId;
	}

	public void setLoanId(MyInvestmentLoanInfo loanId) {
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVEST_TIME", nullable = false)
	public Date getInvestTime() {
		return this.investTime;
	}

	public void setInvestTime(Date investTime) {
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
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@OneToOne
	@JoinColumn(name="LEDGER_FINANCE_ID", referencedColumnName="ID")
	public MyInvestmentAcTLedgerFinance getLedgerFinanceId() {
		return ledgerFinanceId;
	}

	public void setLedgerFinanceId(MyInvestmentAcTLedgerFinance ledgerFinanceId) {
		this.ledgerFinanceId = ledgerFinanceId;
	}


	
}