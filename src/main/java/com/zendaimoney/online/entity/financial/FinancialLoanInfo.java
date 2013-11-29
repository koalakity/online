package com.zendaimoney.online.entity.financial;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedgerLoan;

/**
 * LoanInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOAN_INFO")
public class FinancialLoanInfo implements java.io.Serializable {
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5163416181325490447L;
	private BigDecimal loanId;
	private FinancialUsers userId;
	private String loanTitle;
	private BigDecimal loanUse;
	private Double loanAmount;
	private BigDecimal loanDuration;
	private Double yearRate;
	private BigDecimal paymentMethod;
	private BigDecimal raiseDuration;
	private Double monthReturnPrincipalandinter;
	private Double monthManageCost;
	private Date releaseTime;
	private BigDecimal status;
	private String description;
	private BigDecimal isShowAge;
	private BigDecimal isShowSex;
	private BigDecimal isShowDegree;
	private BigDecimal isShowSchool;
	private BigDecimal isShowEntranceYear;
	private BigDecimal isShowWorkCity;
	private BigDecimal isShowVocation;
	private BigDecimal isShowCompanyScale;
	private BigDecimal isShowOffice;
	private BigDecimal isShowWorkYear;
	private BigDecimal isShowMarry;
	private BigDecimal isShowHaveHouse;
	private BigDecimal isShowHouseLoan;
	private BigDecimal isShowHaveCar;
	private BigDecimal isShowCarLoan;
	private BigDecimal releaseStatus;
	private String loanTitleStr;
	private LoanManagementAcTLedgerLoan actTLedgerId;
	private Date startInvestTime;

	// Constructors

	@OneToOne
	@JoinColumn(name = "LEDGER_LOAN_ID")
	public LoanManagementAcTLedgerLoan getActTLedgerId() {
		return actTLedgerId;
	}

	public void setActTLedgerId(LoanManagementAcTLedgerLoan actTLedgerId) {
		this.actTLedgerId = actTLedgerId;
	}

	@Transient
	public String getLoanTitleStr() {
		if (loanTitle.length() > 10) {
			loanTitleStr = loanTitle.substring(0, 10).concat("……");
			return loanTitleStr;
		} else {
			loanTitleStr = loanTitle;
			return loanTitleStr;
		}
	}

	public void setLoanTitleStr(String loanTitleStr) {
		this.loanTitleStr = loanTitleStr;
	}

	// Constructors

	/** default constructor */
	public FinancialLoanInfo() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "LOANINFO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LOAN_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoanId() {
		return this.loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	public FinancialUsers getUserId() {
		return this.userId;
	}

	public void setUserId(FinancialUsers userId) {
		this.userId = userId;
	}

	@Column(name = "LOAN_TITLE", nullable = false, length = 100)
	public String getLoanTitle() {
		return this.loanTitle;
	}

	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}

	@Column(name = "LOAN_USE", nullable = false)
	public BigDecimal getLoanUse() {
		return this.loanUse;
	}

	public void setLoanUse(BigDecimal loanUse) {
		this.loanUse = loanUse;
	}

	@Column(name = "LOAN_AMOUNT", nullable = false, precision = 22, scale = 7)
	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "LOAN_DURATION", nullable = false)
	public BigDecimal getLoanDuration() {
		return this.loanDuration;
	}

	public void setLoanDuration(BigDecimal loanDuration) {
		this.loanDuration = loanDuration;
	}

	@Column(name = "YEAR_RATE", nullable = false, precision = 22, scale = 18)
	public Double getYearRate() {
		return this.yearRate;
	}

	public void setYearRate(Double yearRate) {
		this.yearRate = yearRate;
	}

	@Column(name = "PAYMENT_METHOD", nullable = false, precision = 22, scale = 0)
	public BigDecimal getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(BigDecimal paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "RAISE_DURATION", nullable = false, precision = 22, scale = 0)
	public BigDecimal getRaiseDuration() {
		return this.raiseDuration;
	}

	public void setRaiseDuration(BigDecimal raiseDuration) {
		this.raiseDuration = raiseDuration;
	}

	@Column(name = "MONTH_RETURN_PRINCIPALANDINTER", nullable = false, precision = 22, scale = 7)
	public Double getMonthReturnPrincipalandinter() {
		return this.monthReturnPrincipalandinter;
	}

	public void setMonthReturnPrincipalandinter(Double monthReturnPrincipalandinter) {
		this.monthReturnPrincipalandinter = monthReturnPrincipalandinter;
	}

	@Column(name = "MONTH_MANAGE_COST", nullable = false, precision = 22, scale = 7)
	public Double getMonthManageCost() {
		return this.monthManageCost;
	}

	public void setMonthManageCost(Double monthManageCost) {
		this.monthManageCost = monthManageCost;
	}

	@Column(name = "RELEASE_TIME", nullable = false, length = 7)
	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Column(name = "STATUS", nullable = false, precision = 22, scale = 0)
	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	@Column(name = "RELEASE_STATUS")
	public BigDecimal getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(BigDecimal releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "IS_SHOW_AGE")
	public BigDecimal getIsShowAge() {
		return isShowAge;
	}

	public void setIsShowAge(BigDecimal isShowAge) {
		this.isShowAge = isShowAge;
	}

	@Column(name = "IS_SHOW_SEX")
	public BigDecimal getIsShowSex() {
		return isShowSex;
	}

	public void setIsShowSex(BigDecimal isShowSex) {
		this.isShowSex = isShowSex;
	}

	@Column(name = "IS_SHOW_DEGREE")
	public BigDecimal getIsShowDegree() {
		return isShowDegree;
	}

	public void setIsShowDegree(BigDecimal isShowDegree) {
		this.isShowDegree = isShowDegree;
	}

	@Column(name = "IS_SHOW_SCHOOL")
	public BigDecimal getIsShowSchool() {
		return isShowSchool;
	}

	public void setIsShowSchool(BigDecimal isShowSchool) {
		this.isShowSchool = isShowSchool;
	}

	@Column(name = "IS_SHOW_ENTRANCE_YEAR")
	public BigDecimal getIsShowEntranceYear() {
		return isShowEntranceYear;
	}

	public void setIsShowEntranceYear(BigDecimal isShowEntranceYear) {
		this.isShowEntranceYear = isShowEntranceYear;
	}

	@Column(name = "IS_SHOW_WORK_CITY")
	public BigDecimal getIsShowWorkCity() {
		return isShowWorkCity;
	}

	public void setIsShowWorkCity(BigDecimal isShowWorkCity) {
		this.isShowWorkCity = isShowWorkCity;
	}

	@Column(name = "IS_SHOW_VOCATION")
	public BigDecimal getIsShowVocation() {
		return isShowVocation;
	}

	public void setIsShowVocation(BigDecimal isShowVocation) {
		this.isShowVocation = isShowVocation;
	}

	@Column(name = "IS_SHOW_COMPANY_SCALE")
	public BigDecimal getIsShowCompanyScale() {
		return isShowCompanyScale;
	}

	public void setIsShowCompanyScale(BigDecimal isShowCompanyScale) {
		this.isShowCompanyScale = isShowCompanyScale;
	}

	@Column(name = "IS_SHOW_OFFICE")
	public BigDecimal getIsShowOffice() {
		return isShowOffice;
	}

	public void setIsShowOffice(BigDecimal isShowOffice) {
		this.isShowOffice = isShowOffice;
	}

	@Column(name = "IS_SHOW_WORK_YEAR")
	public BigDecimal getIsShowWorkYear() {
		return isShowWorkYear;
	}

	public void setIsShowWorkYear(BigDecimal isShowWorkYear) {
		this.isShowWorkYear = isShowWorkYear;
	}

	@Column(name = "IS_SHOW_MARRY")
	public BigDecimal getIsShowMarry() {
		return isShowMarry;
	}

	public void setIsShowMarry(BigDecimal isShowMarry) {
		this.isShowMarry = isShowMarry;
	}

	@Column(name = "IS_SHOW_HAVA_HOUSE")
	public BigDecimal getIsShowHaveHouse() {
		return isShowHaveHouse;
	}

	public void setIsShowHaveHouse(BigDecimal isShowHaveHouse) {
		this.isShowHaveHouse = isShowHaveHouse;
	}

	@Column(name = "IS_SHOW_HOUSE_LOAN")
	public BigDecimal getIsShowHouseLoan() {
		return isShowHouseLoan;
	}

	public void setIsShowHouseLoan(BigDecimal isShowHouseLoan) {
		this.isShowHouseLoan = isShowHouseLoan;
	}

	@Column(name = "IS_SHOW_HAVA_CAR")
	public BigDecimal getIsShowHaveCar() {
		return isShowHaveCar;
	}

	public void setIsShowHaveCar(BigDecimal isShowHaveCar) {
		this.isShowHaveCar = isShowHaveCar;
	}

	@Column(name = "IS_SHOW_CAR_LOAN")
	public BigDecimal getIsShowCarLoan() {
		return isShowCarLoan;
	}

	public void setIsShowCarLoan(BigDecimal isShowCarLoan) {
		this.isShowCarLoan = isShowCarLoan;
	}
	
	@Column(name = "START_INVEST_TIME", nullable = false)
	public Date getStartInvestTime() {
		return startInvestTime;
	}

	public void setStartInvestTime(Date startInvestTime) {
		this.startInvestTime = startInvestTime;
	}
}