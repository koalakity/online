package com.zendaimoney.online.admin.entity.loan;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AcTLedgerFinance entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_LEDGER_FINANCE")
public class AcTLedgerFinanceAdmin implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7887351541682073924L;
	/**
	 * 
	 */
	private BigDecimal id;
	private String acctStatus;
	private Date dueDate;
	private Date intersetStart;
	private Date contractEnd;
	private Date maturity;
	private Double debtAmount;
	private Double debtProportion;
	private Double interestReceivable;
	private Long totalnumPayments;
	private Long remainNum;
	private Long currNum;
	private Double irr;
	private Date dateMemo;
	private Double amountMemo;
	private String memo;
	private Long ledgerId;

	// Constructors

	/** default constructor */
	public AcTLedgerFinanceAdmin() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName = "ACTLEDGERFINANCE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "ACCT_STATUS", length = 1)
	public String getAcctStatus() {
		return this.acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}


	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE", length = 7)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INTERSET_START", length = 7)
	public Date getIntersetStart() {
		return this.intersetStart;
	}

	public void setIntersetStart(Date intersetStart) {
		this.intersetStart = intersetStart;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONTRACT_END", length = 7)
	public Date getContractEnd() {
		return this.contractEnd;
	}

	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MATURITY", length = 7)
	public Date getMaturity() {
		return this.maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	@Column(name = "DEBT_AMOUNT", precision = 22, scale = 7)
	public Double getDebtAmount() {
		return this.debtAmount;
	}

	public void setDebtAmount(Double debtAmount) {
		this.debtAmount = debtAmount;
	}

	@Column(name = "DEBT_PROPORTION", precision = 22, scale = 18)
	public Double getDebtProportion() {
		return this.debtProportion;
	}

	public void setDebtProportion(Double debtProportion) {
		this.debtProportion = debtProportion;
	}

	@Column(name = "INTEREST_RECEIVABLE", precision = 22, scale = 7)
	public Double getInterestReceivable() {
		return this.interestReceivable;
	}

	public void setInterestReceivable(Double interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	@Column(name = "TOTALNUM_PAYMENTS", precision = 10, scale = 0)
	public Long getTotalnumPayments() {
		return this.totalnumPayments;
	}

	public void setTotalnumPayments(Long totalnumPayments) {
		this.totalnumPayments = totalnumPayments;
	}

	@Column(name = "REMAIN_NUM", precision = 10, scale = 0)
	public Long getRemainNum() {
		return this.remainNum;
	}

	public void setRemainNum(Long remainNum) {
		this.remainNum = remainNum;
	}

	@Column(name = "CURR_NUM", precision = 10, scale = 0)
	public Long getCurrNum() {
		return this.currNum;
	}

	public void setCurrNum(Long currNum) {
		this.currNum = currNum;
	}

	@Column(name = "IRR", precision = 22, scale = 18)
	public Double getIrr() {
		return this.irr;
	}

	public void setIrr(Double irr) {
		this.irr = irr;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_MEMO", length = 7)
	public Date getDateMemo() {
		return this.dateMemo;
	}

	public void setDateMemo(Date dateMemo) {
		this.dateMemo = dateMemo;
	}

	@Column(name = "AMOUNT_MEMO", precision = 22, scale = 7)
	public Double getAmountMemo() {
		return this.amountMemo;
	}

	public void setAmountMemo(Double amountMemo) {
		this.amountMemo = amountMemo;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	@Column( name="LEDGER_ID")
	public Long getLedgerId() {
		return ledgerId;
	}

	public void setLedgerId(Long ledgerId) {
		this.ledgerId = ledgerId;
	}
	
}