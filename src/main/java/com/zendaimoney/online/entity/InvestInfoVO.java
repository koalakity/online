package com.zendaimoney.online.entity;

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
@Entity(name="InvestInfoVO")
@Table(name = "INVEST_INFO")
public class InvestInfoVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3683684875642153094L;
	private Long investId;
	private Long loanId;
	private Long userId;
	private BigDecimal investAmount;
	private BigDecimal havaScale;
	private Date investTime;
	private String description;
	private String status;
	//private Long ledgerFinanceId;
	private AcTLedgerFinanceVO acTLedgerFinance;

	@SequenceGenerator(name = "generator",sequenceName="INVESTINFO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "INVEST_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getInvestId() {
		return this.investId;
	}

	public void setInvestId(Long investId) {
		this.investId = investId;
	}

	@Column(name = "LOAN_ID", nullable = false, precision = 22, scale = 0)
	public Long getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "INVEST_AMOUNT", nullable = false, precision = 22, scale = 7)
	public BigDecimal getInvestAmount() {
		return this.investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	@Column(name = "HAVA_SCALE", nullable = false, precision = 22, scale = 18)
	public BigDecimal getHavaScale() {
		return this.havaScale;
	}

	public void setHavaScale(BigDecimal havaScale) {
		this.havaScale = havaScale;
	}

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
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "LEDGER_FINANCE_ID")
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="LEDGER_FINANCE_ID",referencedColumnName="ID")
	public AcTLedgerFinanceVO getAcTLedgerFinance() {
		return acTLedgerFinance;
	}

	public void setAcTLedgerFinance(AcTLedgerFinanceVO acTLedgerFinance) {
		this.acTLedgerFinance = acTLedgerFinance;
	}

//	@Column(name = "LEDGER_FINANCE_ID", precision = 22, scale = 0)
//	public Long getLedgerFinanceId() {
//		return this.ledgerFinanceId;
//	}
//
//	public void setLedgerFinanceId(Long ledgerFinanceId) {
//		this.ledgerFinanceId = ledgerFinanceId;
//	}

}