package com.zendaimoney.online.entity.financial;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

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
 * InvestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INVEST_INFO")
public class FinancialInvestInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5153438048235826013L;
	private BigDecimal investId;
	private BigDecimal userId;
	private Double investAmount;
	private Double havaScale;
	private Date investTime;
	private String description;
    private BigDecimal loanId;
    private String status;
    private FinancialAcTLedgerFinance ledgerFinanceId;


	// Constructors

	/** default constructor */
	public FinancialInvestInfo() {
	}


	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "INVESTINFO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "INVEST_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getInvestId() {
		return this.investId;
	}

	public void setInvestId(BigDecimal investId) {
		this.investId = investId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return userId;
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

	@Column(name = "INVEST_TIME", nullable = false, length = 7)
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
	
    @Column(name = "LOAN_ID")
	public BigDecimal getLoanId() {
		return loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="LEDGER_FINANCE_ID",referencedColumnName="ID")
	public FinancialAcTLedgerFinance getLedgerFinanceId() {
		return ledgerFinanceId;
	}

	public void setLedgerFinanceId(FinancialAcTLedgerFinance ledgerFinanceId) {
		this.ledgerFinanceId = ledgerFinanceId;
	}

	

}