package com.zendaimoney.online.entity.myInvestment;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * AcTLedger entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_LEDGER")
public class MyInvestmentAcTLedger implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8325621951278348338L;
	// Fields

	private Long id;
	private String account;
	private Long totalAccountId;
	private Date openacctDate;
	private String openacctTeller;
	private String busiType;
	private Double amount;
	private Double investAmount;
	private Double debtAmount;
	private Double interestReceivable;
	private Double otherReceivale;
	private Double loanAmount;
	private Double interestPayable;
	private Double toherPayable;
	private Double interestIncome;
	private Double otherIncome;
	private Double nonoperatIncome;
	private Double interestExpenditure;
	private Double otherExpenditure;
	private Double nonoperatExpenditure;
	private Date cancelacctDate;
	private String memo;
	private Long version;
	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	// Constructors

	/** default constructor */
	public MyInvestmentAcTLedger() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ACCOUNT", nullable = false, length = 30)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
	@Column(name = "TOTAL_ACCOUNT_ID", nullable = false, precision = 11, scale = 0)
	public Long getTotalAccountId() {
		return this.totalAccountId;
	}

	public void setTotalAccountId(Long totalAccountId) {
		this.totalAccountId = totalAccountId;
	}

	@Column(name = "OPENACCT_DATE", length = 7)
	public Date getOpenacctDate() {
		return this.openacctDate;
	}

	public void setOpenacctDate(Date openacctDate) {
		this.openacctDate = openacctDate;
	}

	@Column(name = "OPENACCT_TELLER", length = 20)
	public String getOpenacctTeller() {
		return this.openacctTeller;
	}

	public void setOpenacctTeller(String openacctTeller) {
		this.openacctTeller = openacctTeller;
	}

	@Column(name = "BUSI_TYPE", nullable = false, length = 1)
	public String getBusiType() {
		return this.busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	@Column(name = "AMOUNT", precision = 22, scale = 7)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "INVEST_AMOUNT", precision = 22, scale = 7)
	public Double getInvestAmount() {
		return this.investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	@Column(name = "DEBT_AMOUNT", precision = 22, scale = 7)
	public Double getDebtAmount() {
		return this.debtAmount;
	}

	public void setDebtAmount(Double debtAmount) {
		this.debtAmount = debtAmount;
	}

	@Column(name = "INTEREST_RECEIVABLE", precision = 22, scale = 7)
	public Double getInterestReceivable() {
		return this.interestReceivable;
	}

	public void setInterestReceivable(Double interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	@Column(name = "OTHER_RECEIVALE", precision = 22, scale = 7)
	public Double getOtherReceivale() {
		return this.otherReceivale;
	}

	public void setOtherReceivale(Double otherReceivale) {
		this.otherReceivale = otherReceivale;
	}

	@Column(name = "LOAN_AMOUNT", precision = 22, scale = 7)
	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "INTEREST_PAYABLE", precision = 22, scale = 7)
	public Double getInterestPayable() {
		return this.interestPayable;
	}

	public void setInterestPayable(Double interestPayable) {
		this.interestPayable = interestPayable;
	}

	@Column(name = "TOHER_PAYABLE", precision = 22, scale = 7)
	public Double getToherPayable() {
		return this.toherPayable;
	}

	public void setToherPayable(Double toherPayable) {
		this.toherPayable = toherPayable;
	}

	@Column(name = "INTEREST_INCOME", precision = 22, scale = 7)
	public Double getInterestIncome() {
		return this.interestIncome;
	}

	public void setInterestIncome(Double interestIncome) {
		this.interestIncome = interestIncome;
	}

	@Column(name = "OTHER_INCOME", precision = 22, scale = 7)
	public Double getOtherIncome() {
		return this.otherIncome;
	}

	public void setOtherIncome(Double otherIncome) {
		this.otherIncome = otherIncome;
	}

	@Column(name = "NONOPERAT_INCOME", precision = 22, scale = 7)
	public Double getNonoperatIncome() {
		return this.nonoperatIncome;
	}

	public void setNonoperatIncome(Double nonoperatIncome) {
		this.nonoperatIncome = nonoperatIncome;
	}

	@Column(name = "INTEREST_EXPENDITURE", precision = 22, scale = 7)
	public Double getInterestExpenditure() {
		return this.interestExpenditure;
	}

	public void setInterestExpenditure(Double interestExpenditure) {
		this.interestExpenditure = interestExpenditure;
	}

	@Column(name = "OTHER_EXPENDITURE", precision = 22, scale = 7)
	public Double getOtherExpenditure() {
		return this.otherExpenditure;
	}

	public void setOtherExpenditure(Double otherExpenditure) {
		this.otherExpenditure = otherExpenditure;
	}

	@Column(name = "NONOPERAT_EXPENDITURE", precision = 22, scale = 7)
	public Double getNonoperatExpenditure() {
		return this.nonoperatExpenditure;
	}

	public void setNonoperatExpenditure(Double nonoperatExpenditure) {
		this.nonoperatExpenditure = nonoperatExpenditure;
	}

	@Column(name = "CANCELACCT_DATE", length = 7)
	public Date getCancelacctDate() {
		return this.cancelacctDate;
	}

	public void setCancelacctDate(Date cancelacctDate) {
		this.cancelacctDate = cancelacctDate;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}