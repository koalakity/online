package com.zendaimoney.online.admin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedgerFinance;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTVirtualCashFlow;

/**
 * AcTLedgerLoan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_LEDGER_LOAN")
public class AcTLedgerLoanAdminVO implements java.io.Serializable {

	// Fields

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1817086230504878848L;
	private Long id;
	private String productCode;
	private String paymentMethod;
	private String acctStatus;
	private Date openacctDate;
	private String cycle;
	private Long loanTerm;
	private Date interestStart;
	private Date contractEnd;
	private Date maturity;
	private Date lastExpiry;
	private Date nextExpiry;
	private BigDecimal eachRepayment;
	private Long totalNum;
	private Long currNum;
	private Long intervalNum;
	private Long maxNum;
	private BigDecimal rate;
	private String rateType;
	private BigDecimal rateFloat;
	private BigDecimal strikeRate;
	private BigDecimal lateRate;
	private BigDecimal lateStrike;
	private BigDecimal loan;
	private BigDecimal outstanding;
	private BigDecimal interestPayable;
	private BigDecimal excessAmount;
	private BigDecimal excessAdmin;
	private BigDecimal excessInterest;
	private String pertainSys;
	private String protocoVer;
	private BigDecimal rateSpare;
	private Date dateSpare;
	private BigDecimal amountSpare;
	private String memo;
	private Date planningDate;
	private Long ledgerId;
	private List<AcTVirtualCashFlowAdminVO> acTVirtualCashFlows ;
	private List<AcTLedgerFinanceAdminVO> acTLedgerFinance;

	@SequenceGenerator(name = "generator",sequenceName="ACTLEDGERLOAN_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "actLedgerLoan")
	public List<AcTVirtualCashFlowAdminVO> getAcTVirtualCashFlows() {
		return acTVirtualCashFlows;
	}

	public void setAcTVirtualCashFlows(
			List<AcTVirtualCashFlowAdminVO> acTVirtualCashFlows) {
		this.acTVirtualCashFlows = acTVirtualCashFlows;
	}

	@Column(name = "PRODUCT_CODE", length = 30)
	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(name = "PAYMENT_METHOD", length = 1)
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "ACCT_STATUS", length = 1)
	public String getAcctStatus() {
		return this.acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	@Column(name = "OPENACCT_DATE")
	public Date getOpenacctDate() {
		return this.openacctDate;
	}

	public void setOpenacctDate(Date openacctDate) {
		this.openacctDate = openacctDate;
	}

	@Column(name = "CYCLE", length = 1)
	public String getCycle() {
		return this.cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	@Column(name = "LOAN_TERM", precision = 10, scale = 0)
	public Long getLoanTerm() {
		return this.loanTerm;
	}

	public void setLoanTerm(Long loanTerm) {
		this.loanTerm = loanTerm;
	}

	@Column(name = "INTEREST_START")
	public Date getInterestStart() {
		return this.interestStart;
	}

	public void setInterestStart(Date interestStart) {
		this.interestStart = interestStart;
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

	@Column(name = "LAST_EXPIRY")
	public Date getLastExpiry() {
		return this.lastExpiry;
	}

	public void setLastExpiry(Date lastExpiry) {
		this.lastExpiry = lastExpiry;
	}

	@Column(name = "NEXT_EXPIRY")
	public Date getNextExpiry() {
		return this.nextExpiry;
	}

	public void setNextExpiry(Date nextExpiry) {
		this.nextExpiry = nextExpiry;
	}

	@Column(name = "EACH_REPAYMENT", precision = 22, scale = 7)
	public BigDecimal getEachRepayment() {
		return this.eachRepayment;
	}

	public void setEachRepayment(BigDecimal eachRepayment) {
		this.eachRepayment = eachRepayment;
	}

	@Column(name = "TOTAL_NUM", precision = 10, scale = 0)
	public Long getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	@Column(name = "CURR_NUM", precision = 10, scale = 0)
	public Long getCurrNum() {
		return this.currNum;
	}

	public void setCurrNum(Long currNum) {
		this.currNum = currNum;
	}

	@Column(name = "INTERVAL_NUM", precision = 10, scale = 0)
	public Long getIntervalNum() {
		return this.intervalNum;
	}

	public void setIntervalNum(Long intervalNum) {
		this.intervalNum = intervalNum;
	}

	@Column(name = "MAX_NUM", precision = 10, scale = 0)
	public Long getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(Long maxNum) {
		this.maxNum = maxNum;
	}

	@Column(name = "RATE", precision = 22, scale = 18)
	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Column(name = "RATE_TYPE", length = 1)
	public String getRateType() {
		return this.rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	@Column(name = "RATE_FLOAT", precision = 22, scale = 18)
	public BigDecimal getRateFloat() {
		return this.rateFloat;
	}

	public void setRateFloat(BigDecimal rateFloat) {
		this.rateFloat = rateFloat;
	}

	@Column(name = "STRIKE_RATE", precision = 22, scale = 18)
	public BigDecimal getStrikeRate() {
		return this.strikeRate;
	}

	public void setStrikeRate(BigDecimal strikeRate) {
		this.strikeRate = strikeRate;
	}

	@Column(name = "LATE_RATE", precision = 22, scale = 18)
	public BigDecimal getLateRate() {
		return this.lateRate;
	}

	public void setLateRate(BigDecimal lateRate) {
		this.lateRate = lateRate;
	}

	@Column(name = "LATE_STRIKE", precision = 22, scale = 18)
	public BigDecimal getLateStrike() {
		return this.lateStrike;
	}

	public void setLateStrike(BigDecimal lateStrike) {
		this.lateStrike = lateStrike;
	}

	@Column(name = "LOAN", precision = 22, scale = 7)
	public BigDecimal getLoan() {
		return this.loan;
	}

	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}

	@Column(name = "OUTSTANDING", precision = 22, scale = 7)
	public BigDecimal getOutstanding() {
		return this.outstanding;
	}

	public void setOutstanding(BigDecimal outstanding) {
		this.outstanding = outstanding;
	}

	@Column(name = "INTEREST_PAYABLE", precision = 22, scale = 7)
	public BigDecimal getInterestPayable() {
		return this.interestPayable;
	}

	public void setInterestPayable(BigDecimal interestPayable) {
		this.interestPayable = interestPayable;
	}

	@Column(name = "EXCESS_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getExcessAmount() {
		return this.excessAmount;
	}

	public void setExcessAmount(BigDecimal excessAmount) {
		this.excessAmount = excessAmount;
	}

	@Column(name = "EXCESS_ADMIN", precision = 22, scale = 7)
	public BigDecimal getExcessAdmin() {
		return this.excessAdmin;
	}

	public void setExcessAdmin(BigDecimal excessAdmin) {
		this.excessAdmin = excessAdmin;
	}

	@Column(name = "EXCESS_INTEREST", precision = 22, scale = 7)
	public BigDecimal getExcessInterest() {
		return this.excessInterest;
	}

	public void setExcessInterest(BigDecimal excessInterest) {
		this.excessInterest = excessInterest;
	}

	@Column(name = "PERTAIN_SYS", length = 1)
	public String getPertainSys() {
		return this.pertainSys;
	}

	public void setPertainSys(String pertainSys) {
		this.pertainSys = pertainSys;
	}

	@Column(name = "PROTOCO_VER", length = 8)
	public String getProtocoVer() {
		return this.protocoVer;
	}

	public void setProtocoVer(String protocoVer) {
		this.protocoVer = protocoVer;
	}

	@Column(name = "RATE_SPARE", precision = 22, scale = 18)
	public BigDecimal getRateSpare() {
		return this.rateSpare;
	}

	public void setRateSpare(BigDecimal rateSpare) {
		this.rateSpare = rateSpare;
	}

	@Column(name = "DATE_SPARE")
	public Date getDateSpare() {
		return this.dateSpare;
	}

	public void setDateSpare(Date dateSpare) {
		this.dateSpare = dateSpare;
	}

	@Column(name = "AMOUNT_SPARE", precision = 22, scale = 7)
	public BigDecimal getAmountSpare() {
		return this.amountSpare;
	}

	public void setAmountSpare(BigDecimal amountSpare) {
		this.amountSpare = amountSpare;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "PLANNING_DATE")
	public Date getPlanningDate() {
		return this.planningDate;
	}

	public void setPlanningDate(Date planningDate) {
		this.planningDate = planningDate;
	}

	@Column(name = "LEDGER_ID", nullable = false, precision = 11, scale = 0)
	public Long getLedgerId() {
		return this.ledgerId;
	}

	public void setLedgerId(Long ledgerId) {
		this.ledgerId = ledgerId;
	}
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "loanAcct")
	public List<AcTLedgerFinanceAdminVO> getAcTLedgerFinance() {
		return acTLedgerFinance;
	}

	public void setAcTLedgerFinance(
			List<AcTLedgerFinanceAdminVO> acTLedgerFinance) {
		this.acTLedgerFinance = acTLedgerFinance;
	}

}