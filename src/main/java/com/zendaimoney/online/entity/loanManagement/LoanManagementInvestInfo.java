package com.zendaimoney.online.entity.loanManagement;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class LoanManagementInvestInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5035459192569949875L;
	private BigDecimal investId;
	private LoanManagementUsers user;
	private Double investAmount;
	private Double havaScale;
	private Date investTime;
    private String description;
    private LoanManagementAcTLedgerFinance ledgerFinance;

    private LoanManagementLoanInfo loanInfo;
    private BigDecimal status;

	// Constructors

  
    /** default constructor */
	public LoanManagementInvestInfo() {
	}

	/** minimal constructor */
	public LoanManagementInvestInfo(Double investAmount, Double havaScale, Date investTime) {
		this.user = user;
		this.investAmount = investAmount;
		this.havaScale = havaScale;
		this.investTime = investTime;
	}

	/** full constructor */
	public LoanManagementInvestInfo(Double investAmount, Double havaScale, Date investTime,
			String description) {
		this.user = user;
		this.investAmount = investAmount;
		this.havaScale = havaScale;
		this.investTime = investTime;
		this.description = description;
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

    @OneToOne
    @JoinColumn(name = "USER_ID")
    public LoanManagementUsers getUser() {
        return user;
    }

    public void setUser(LoanManagementUsers user) {
        this.user = user;
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

	//@Temporal(TemporalType.DATE)
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
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="LOAN_ID") 
    public LoanManagementLoanInfo getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(LoanManagementLoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }
    
    @Column(name = "STATUS")
    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="LEDGER_FINANCE_ID",referencedColumnName="ID")
	public LoanManagementAcTLedgerFinance getLedgerFinance() {
		return ledgerFinance;
	}

	public void setLedgerFinance(LoanManagementAcTLedgerFinance ledgerFinance) {
		this.ledgerFinance = ledgerFinance;
	}

}