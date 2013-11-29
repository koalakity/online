package com.zendaimoney.online.admin.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedgerLoan;

/**
 * AcTLedgerFinance entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_LEDGER_FINANCE")
public class AcTLedgerFinanceAdminVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6124478262545850672L;
	private Long id;
	private String acctStatus;
	private AcTLedgerLoanAdminVO loanAcct;
//	private Long loanAcct;
	private Date dueDate;
	private Date intersetStart;
	private Date contractEnd;
	private Date maturity;
	private BigDecimal debtAmount;
	private BigDecimal debtProportion;
	private BigDecimal interestReceivable;
	private Long totalnumPayments;
	private Long remainNum;
	private Long currNum;
	private BigDecimal irr;
	private Date dateMemo;
	private BigDecimal amountMemo;
	private String memo;
	private Long ledgerId;
	private InvestInfoAdminVO investInfo;

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "ACTLEDGERFINANCE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ACCT_STATUS", length = 1)
	public String getAcctStatus() {
		return this.acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	@ManyToOne
	@JoinColumn(name="LOAN_ACCT", referencedColumnName="ID")
	public AcTLedgerLoanAdminVO getLoanAcct() {
		return this.loanAcct;
	}

	public void setLoanAcct(AcTLedgerLoanAdminVO loanAcct) {
		this.loanAcct = loanAcct;
	}

	@Column(name = "DUE_DATE")
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name = "INTERSET_START")
	public Date getIntersetStart() {
		return this.intersetStart;
	}

	public void setIntersetStart(Date intersetStart) {
		this.intersetStart = intersetStart;
	}

	@Column(name = "CONTRACT_END")
	public Date getContractEnd() {
		return this.contractEnd;
	}

	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}

	@Column(name = "MATURITY")
	public Date getMaturity() {
		return this.maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	@Column(name = "DEBT_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getDebtAmount() {
		return this.debtAmount;
	}

	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}

	@Column(name = "DEBT_PROPORTION", precision = 22, scale = 18)
	public BigDecimal getDebtProportion() {
		return this.debtProportion;
	}

	public void setDebtProportion(BigDecimal debtProportion) {
		this.debtProportion = debtProportion;
	}

	@Column(name = "INTEREST_RECEIVABLE", precision = 22, scale = 7)
	public BigDecimal getInterestReceivable() {
		return this.interestReceivable;
	}

	public void setInterestReceivable(BigDecimal interestReceivable) {
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
	public BigDecimal getIrr() {
		return this.irr;
	}

	public void setIrr(BigDecimal irr) {
		this.irr = irr;
	}

	@Column(name = "DATE_MEMO")
	public Date getDateMemo() {
		return this.dateMemo;
	}

	public void setDateMemo(Date dateMemo) {
		this.dateMemo = dateMemo;
	}

	@Column(name = "AMOUNT_MEMO", precision = 22, scale = 7)
	public BigDecimal getAmountMemo() {
		return this.amountMemo;
	}

	public void setAmountMemo(BigDecimal amountMemo) {
		this.amountMemo = amountMemo;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "LEDGER_ID", nullable = false, precision = 11, scale = 0)
	public Long getLedgerId() {
		return this.ledgerId;
	}

	public void setLedgerId(Long ledgerId) {
		this.ledgerId = ledgerId;
	}
//	@OneToOne(mappedBy="acTLedgerFinance")
//	public InvestInfoVO getInvestInfo() {
//		return investInfo;
//	}
//
//	public void setInvestInfo(InvestInfoVO investInfo) {
//		this.investInfo = investInfo;
//	}
	

}