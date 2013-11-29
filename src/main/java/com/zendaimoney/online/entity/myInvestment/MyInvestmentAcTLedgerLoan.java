package com.zendaimoney.online.entity.myInvestment;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * AcTLedgerLoan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_LEDGER_LOAN")
public class MyInvestmentAcTLedgerLoan implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -360011795727602924L;
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
	private Double eachRepayment;
	private Long totalNum;
	private Long currNum;
	private Long intervalNum;
	private Long maxNum;
	private Double rate;
	private String rateType;
	private Double rateFloat;
	private Double strikeRate;
	private Double lateRate;
	private Double lateStrike;
	private Double loan;
	private Double outstanding;
	private Double interestPayable;
	private Double excessAmount;
	private Double excessAdmin;
	private Double excessInterest;
	private String pertainSys;
	private String protocoVer;
	private Double rateSpare;
	private Date dateSpare;
	private Double amountSpare;
	private String memo;
	private Date planningDate;
	private List<MyInvestmentAcTLedgerFinance> acTLedgerFinance;

	// Constructors

	/** default constructor */
	public MyInvestmentAcTLedgerLoan() {
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

	
	@Column(name = "OPENACCT_DATE", length = 7)
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

	
	@Column(name = "INTEREST_START", length = 7)
	public Date getInterestStart() {
		return this.interestStart;
	}

	public void setInterestStart(Date interestStart) {
		this.interestStart = interestStart;
	}

	
	@Column(name = "CONTRACT_END", length = 7)
	public Date getContractEnd() {
		return this.contractEnd;
	}

	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}

	
	@Column(name = "MATURITY", length = 7)
	public Date getMaturity() {
		return this.maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	
	@Column(name = "LAST_EXPIRY", length = 7)
	public Date getLastExpiry() {
		return this.lastExpiry;
	}

	public void setLastExpiry(Date lastExpiry) {
		this.lastExpiry = lastExpiry;
	}

	
	@Column(name = "NEXT_EXPIRY", length = 7)
	public Date getNextExpiry() {
		return this.nextExpiry;
	}

	public void setNextExpiry(Date nextExpiry) {
		this.nextExpiry = nextExpiry;
	}

	@Column(name = "EACH_REPAYMENT", precision = 22, scale = 7)
	public Double getEachRepayment() {
		return this.eachRepayment;
	}

	public void setEachRepayment(Double eachRepayment) {
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
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
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
	public Double getRateFloat() {
		return this.rateFloat;
	}

	public void setRateFloat(Double rateFloat) {
		this.rateFloat = rateFloat;
	}

	@Column(name = "STRIKE_RATE", precision = 22, scale = 18)
	public Double getStrikeRate() {
		return this.strikeRate;
	}

	public void setStrikeRate(Double strikeRate) {
		this.strikeRate = strikeRate;
	}

	@Column(name = "LATE_RATE", precision = 22, scale = 18)
	public Double getLateRate() {
		return this.lateRate;
	}

	public void setLateRate(Double lateRate) {
		this.lateRate = lateRate;
	}

	@Column(name = "LATE_STRIKE", precision = 22, scale = 18)
	public Double getLateStrike() {
		return this.lateStrike;
	}

	public void setLateStrike(Double lateStrike) {
		this.lateStrike = lateStrike;
	}

	@Column(name = "LOAN", precision = 22, scale = 7)
	public Double getLoan() {
		return this.loan;
	}

	public void setLoan(Double loan) {
		this.loan = loan;
	}

	@Column(name = "OUTSTANDING", precision = 22, scale = 7)
	public Double getOutstanding() {
		return this.outstanding;
	}

	public void setOutstanding(Double outstanding) {
		this.outstanding = outstanding;
	}

	@Column(name = "INTEREST_PAYABLE", precision = 22, scale = 7)
	public Double getInterestPayable() {
		return this.interestPayable;
	}

	public void setInterestPayable(Double interestPayable) {
		this.interestPayable = interestPayable;
	}

	@Column(name = "EXCESS_AMOUNT", precision = 22, scale = 7)
	public Double getExcessAmount() {
		return this.excessAmount;
	}

	public void setExcessAmount(Double excessAmount) {
		this.excessAmount = excessAmount;
	}

	@Column(name = "EXCESS_ADMIN", precision = 22, scale = 7)
	public Double getExcessAdmin() {
		return this.excessAdmin;
	}

	public void setExcessAdmin(Double excessAdmin) {
		this.excessAdmin = excessAdmin;
	}

	@Column(name = "EXCESS_INTEREST", precision = 22, scale = 7)
	public Double getExcessInterest() {
		return this.excessInterest;
	}

	public void setExcessInterest(Double excessInterest) {
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
	public Double getRateSpare() {
		return this.rateSpare;
	}

	public void setRateSpare(Double rateSpare) {
		this.rateSpare = rateSpare;
	}

	
	@Column(name = "DATE_SPARE", length = 7)
	public Date getDateSpare() {
		return this.dateSpare;
	}

	public void setDateSpare(Date dateSpare) {
		this.dateSpare = dateSpare;
	}

	@Column(name = "AMOUNT_SPARE", precision = 22, scale = 7)
	public Double getAmountSpare() {
		return this.amountSpare;
	}

	public void setAmountSpare(Double amountSpare) {
		this.amountSpare = amountSpare;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	@Column(name = "PLANNING_DATE", length = 7)
	public Date getPlanningDate() {
		return this.planningDate;
	}

	public void setPlanningDate(Date planningDate) {
		this.planningDate = planningDate;
	}

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "loanAcct")
	public List<MyInvestmentAcTLedgerFinance> getAcTLedgerFinance() {
		return acTLedgerFinance;
	}

	public void setAcTLedgerFinance(
			List<MyInvestmentAcTLedgerFinance> acTLedgerFinance) {
		this.acTLedgerFinance = acTLedgerFinance;
	}
	
}