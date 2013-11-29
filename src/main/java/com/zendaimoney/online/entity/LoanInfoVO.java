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

/**
 * LoanInfo entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "LoanInfoVO")
@Table(name = "LOAN_INFO")
public class LoanInfoVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 9088598327383434013L;
	private Long loanId;
	private Long userId;
	private String loanTitle;
	private BigDecimal loanUse;
	private BigDecimal loanAmount;
	private Long loanDuration;
	private BigDecimal yearRate;
	private Long paymentMethod;
	private BigDecimal raiseDuration;
	private BigDecimal monthReturnPrincipalandinter;
	private BigDecimal monthManageCost;
	private Date releaseTime;
	private Long status;
	private String description;
	private Long releaseStatus;
	private Long ledgerLoanId;
	private Long isShowAge;
	private Long isShowSex;
	private Long isShowDegree;
	private Long isShowSchool;
	private Long isShowEntranceYear;
	private Long isShowWorkCity;
	private Long isShowVocation;
	private Long isShowCompanyScale;
	private Long isShowOffice;
	private Long isShowWorkYear;
	private Long isShowMarry;
	private Long isShowHavaHouse;
	private Long isShowHouseLoan;
	private Long isShowHavaCar;
	private Long isShowCarLoan;
	private Date startInvestTime;
	private Long loanRateId;

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "LOANINFO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LOAN_ID", unique = true, nullable = false, precision = 22, scale = 0)
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

	@Column(name = "LOAN_TITLE", nullable = false, length = 100)
	public String getLoanTitle() {
		return this.loanTitle;
	}

	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}

	@Column(name = "LOAN_USE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoanUse() {
		return this.loanUse;
	}

	public void setLoanUse(BigDecimal loanUse) {
		this.loanUse = loanUse;
	}

	@Column(name = "LOAN_AMOUNT", nullable = false, precision = 22, scale = 7)
	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "LOAN_DURATION", nullable = false, precision = 22, scale = 0)
	public Long getLoanDuration() {
		return this.loanDuration;
	}

	public void setLoanDuration(Long loanDuration) {
		this.loanDuration = loanDuration;
	}

	@Column(name = "YEAR_RATE", nullable = false, precision = 22, scale = 18)
	public BigDecimal getYearRate() {
		return this.yearRate;
	}

	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}

	@Column(name = "PAYMENT_METHOD", nullable = false, precision = 22, scale = 0)
	public Long getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(Long paymentMethod) {
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
	public BigDecimal getMonthReturnPrincipalandinter() {
		return this.monthReturnPrincipalandinter;
	}

	public void setMonthReturnPrincipalandinter(BigDecimal monthReturnPrincipalandinter) {
		this.monthReturnPrincipalandinter = monthReturnPrincipalandinter;
	}

	@Column(name = "MONTH_MANAGE_COST", nullable = false, precision = 22, scale = 7)
	public BigDecimal getMonthManageCost() {
		return this.monthManageCost;
	}

	public void setMonthManageCost(BigDecimal monthManageCost) {
		this.monthManageCost = monthManageCost;
	}

	@Column(name = "RELEASE_TIME", nullable = false)
	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Column(name = "STATUS", nullable = false, precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "RELEASE_STATUS", precision = 22, scale = 0)
	public Long getReleaseStatus() {
		return this.releaseStatus;
	}

	public void setReleaseStatus(Long releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	@Column(name = "LEDGER_LOAN_ID", precision = 22, scale = 0)
	public Long getLedgerLoanId() {
		return this.ledgerLoanId;
	}

	public void setLedgerLoanId(Long ledgerLoanId) {
		this.ledgerLoanId = ledgerLoanId;
	}

	@Column(name = "IS_SHOW_AGE", precision = 22, scale = 0)
	public Long getIsShowAge() {
		return this.isShowAge;
	}

	public void setIsShowAge(Long isShowAge) {
		this.isShowAge = isShowAge;
	}

	@Column(name = "IS_SHOW_SEX", precision = 22, scale = 0)
	public Long getIsShowSex() {
		return this.isShowSex;
	}

	public void setIsShowSex(Long isShowSex) {
		this.isShowSex = isShowSex;
	}

	@Column(name = "IS_SHOW_DEGREE", precision = 22, scale = 0)
	public Long getIsShowDegree() {
		return this.isShowDegree;
	}

	public void setIsShowDegree(Long isShowDegree) {
		this.isShowDegree = isShowDegree;
	}

	@Column(name = "IS_SHOW_SCHOOL", precision = 22, scale = 0)
	public Long getIsShowSchool() {
		return this.isShowSchool;
	}

	public void setIsShowSchool(Long isShowSchool) {
		this.isShowSchool = isShowSchool;
	}

	@Column(name = "IS_SHOW_ENTRANCE_YEAR", precision = 22, scale = 0)
	public Long getIsShowEntranceYear() {
		return this.isShowEntranceYear;
	}

	public void setIsShowEntranceYear(Long isShowEntranceYear) {
		this.isShowEntranceYear = isShowEntranceYear;
	}

	@Column(name = "IS_SHOW_WORK_CITY", precision = 22, scale = 0)
	public Long getIsShowWorkCity() {
		return this.isShowWorkCity;
	}

	public void setIsShowWorkCity(Long isShowWorkCity) {
		this.isShowWorkCity = isShowWorkCity;
	}

	@Column(name = "IS_SHOW_VOCATION", precision = 22, scale = 0)
	public Long getIsShowVocation() {
		return this.isShowVocation;
	}

	public void setIsShowVocation(Long isShowVocation) {
		this.isShowVocation = isShowVocation;
	}

	@Column(name = "IS_SHOW_COMPANY_SCALE", precision = 22, scale = 0)
	public Long getIsShowCompanyScale() {
		return this.isShowCompanyScale;
	}

	public void setIsShowCompanyScale(Long isShowCompanyScale) {
		this.isShowCompanyScale = isShowCompanyScale;
	}

	@Column(name = "IS_SHOW_OFFICE", precision = 22, scale = 0)
	public Long getIsShowOffice() {
		return this.isShowOffice;
	}

	public void setIsShowOffice(Long isShowOffice) {
		this.isShowOffice = isShowOffice;
	}

	@Column(name = "IS_SHOW_WORK_YEAR", precision = 22, scale = 0)
	public Long getIsShowWorkYear() {
		return this.isShowWorkYear;
	}

	public void setIsShowWorkYear(Long isShowWorkYear) {
		this.isShowWorkYear = isShowWorkYear;
	}

	@Column(name = "IS_SHOW_MARRY", precision = 22, scale = 0)
	public Long getIsShowMarry() {
		return this.isShowMarry;
	}

	public void setIsShowMarry(Long isShowMarry) {
		this.isShowMarry = isShowMarry;
	}

	@Column(name = "IS_SHOW_HAVA_HOUSE", precision = 22, scale = 0)
	public Long getIsShowHavaHouse() {
		return this.isShowHavaHouse;
	}

	public void setIsShowHavaHouse(Long isShowHavaHouse) {
		this.isShowHavaHouse = isShowHavaHouse;
	}

	@Column(name = "IS_SHOW_HOUSE_LOAN", precision = 22, scale = 0)
	public Long getIsShowHouseLoan() {
		return this.isShowHouseLoan;
	}

	public void setIsShowHouseLoan(Long isShowHouseLoan) {
		this.isShowHouseLoan = isShowHouseLoan;
	}

	@Column(name = "IS_SHOW_HAVA_CAR", precision = 22, scale = 0)
	public Long getIsShowHavaCar() {
		return this.isShowHavaCar;
	}

	public void setIsShowHavaCar(Long isShowHavaCar) {
		this.isShowHavaCar = isShowHavaCar;
	}

	@Column(name = "IS_SHOW_CAR_LOAN", precision = 22, scale = 0)
	public Long getIsShowCarLoan() {
		return this.isShowCarLoan;
	}

	public void setIsShowCarLoan(Long isShowCarLoan) {
		this.isShowCarLoan = isShowCarLoan;
	}

	@Column(name = "START_INVEST_TIME", nullable = false)
	public Date getStartInvestTime() {
		return startInvestTime;
	}

	public void setStartInvestTime(Date startInvestTime) {
		this.startInvestTime = startInvestTime;
	}

	@Column(name = "LOAN_RATE_ID")
	public Long getLoanRateId() {
		return loanRateId;
	}

	public void setLoanRateId(Long loanRateId) {
		this.loanRateId = loanRateId;
	}

}