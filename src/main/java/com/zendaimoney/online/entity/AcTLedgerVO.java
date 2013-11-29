package com.zendaimoney.online.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
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
@Entity(name="AcTLedgerVO")
@Table(name = "AC_T_LEDGER")
public class AcTLedgerVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5328206924420706963L;
	private Long id;
	private String account;
	private Long totalAccountId;
	private Date openacctDate;
	private String openacctTeller;
	private String busiType;
	private BigDecimal amount;
	private BigDecimal investAmount;
	private BigDecimal debtAmount;
	private BigDecimal interestReceivable;
	private BigDecimal otherReceivale;
	private BigDecimal loanAmount;
	private BigDecimal interestPayable;
	private BigDecimal toherPayable;
	private BigDecimal interestIncome;
	private BigDecimal otherIncome;
	private BigDecimal nonoperatIncome;
	private BigDecimal interestExpenditure;
	private BigDecimal otherExpenditure;
	private BigDecimal nonoperatExpenditure;
	private BigDecimal payBackAmt;
	private Date cancelacctDate;
	private String memo;
	private Long version;

	
	@SequenceGenerator(name = "generator",sequenceName="ACTLEDGER_SEQ")
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

	@Column(name = "OPENACCT_DATE")
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
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "INVEST_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getInvestAmount() {
		return this.investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	@Column(name = "DEBT_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getDebtAmount() {
		return this.debtAmount;
	}

	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}

	@Column(name = "INTEREST_RECEIVABLE", precision = 22, scale = 7)
	public BigDecimal getInterestReceivable() {
		return this.interestReceivable;
	}

	public void setInterestReceivable(BigDecimal interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	@Column(name = "OTHER_RECEIVALE", precision = 22, scale = 7)
	public BigDecimal getOtherReceivale() {
		return this.otherReceivale;
	}

	public void setOtherReceivale(BigDecimal otherReceivale) {
		this.otherReceivale = otherReceivale;
	}

	@Column(name = "LOAN_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "INTEREST_PAYABLE", precision = 22, scale = 7)
	public BigDecimal getInterestPayable() {
		return this.interestPayable;
	}

	public void setInterestPayable(BigDecimal interestPayable) {
		this.interestPayable = interestPayable;
	}

	@Column(name = "TOHER_PAYABLE", precision = 22, scale = 7)
	public BigDecimal getToherPayable() {
		return this.toherPayable;
	}

	public void setToherPayable(BigDecimal toherPayable) {
		this.toherPayable = toherPayable;
	}

	@Column(name = "INTEREST_INCOME", precision = 22, scale = 7)
	public BigDecimal getInterestIncome() {
		return this.interestIncome;
	}

	public void setInterestIncome(BigDecimal interestIncome) {
		this.interestIncome = interestIncome;
	}

	@Column(name = "OTHER_INCOME", precision = 22, scale = 7)
	public BigDecimal getOtherIncome() {
		return this.otherIncome;
	}

	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}

	@Column(name = "NONOPERAT_INCOME", precision = 22, scale = 7)
	public BigDecimal getNonoperatIncome() {
		return this.nonoperatIncome;
	}

	public void setNonoperatIncome(BigDecimal nonoperatIncome) {
		this.nonoperatIncome = nonoperatIncome;
	}

	@Column(name = "INTEREST_EXPENDITURE", precision = 22, scale = 7)
	public BigDecimal getInterestExpenditure() {
		return this.interestExpenditure;
	}

	public void setInterestExpenditure(BigDecimal interestExpenditure) {
		this.interestExpenditure = interestExpenditure;
	}

	@Column(name = "OTHER_EXPENDITURE", precision = 22, scale = 7)
	public BigDecimal getOtherExpenditure() {
		return this.otherExpenditure;
	}

	public void setOtherExpenditure(BigDecimal otherExpenditure) {
		this.otherExpenditure = otherExpenditure;
	}

	@Column(name = "NONOPERAT_EXPENDITURE", precision = 22, scale = 7)
	public BigDecimal getNonoperatExpenditure() {
		return this.nonoperatExpenditure;
	}

	public void setNonoperatExpenditure(BigDecimal nonoperatExpenditure) {
		this.nonoperatExpenditure = nonoperatExpenditure;
	}

	@Column(name = "PAY_BACK_AMT", precision = 22, scale = 7)
	public BigDecimal getPayBackAmt() {
		return this.payBackAmt;
	}

	public void setPayBackAmt(BigDecimal payBackAmt) {
		this.payBackAmt = payBackAmt;
	}

	@Column(name = "CANCELACCT_DATE")
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

	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	

}