package com.zendaimoney.online.entity.myInvestment;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * LoanInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOAN_INFO")
public class MyInvestmentLoanInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336400510979533223L;
	private BigDecimal loanId;
	private MyInvestmentUsers userId;
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
	private BigDecimal releaseStatus;
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
	private BigDecimal isShowHavaHouse;
	private BigDecimal isShowHouseLoan;
	private BigDecimal isShowHavaCar;
	private BigDecimal isShowCarLoan;
	private Date startInvestTime;
	private List<MyInvestmentLateInfo> LateInfo;

	// Constructors

	/** default constructor */
	public MyInvestmentLoanInfo() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LOAN_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoanId() {
		return this.loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@OneToOne
	@JoinColumn( name="USER_ID", referencedColumnName="USER_ID")
	public MyInvestmentUsers getUserId() {
		return this.userId;
	}

	public void setUserId(MyInvestmentUsers userId) {
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
	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "LOAN_DURATION", nullable = false, precision = 22, scale = 0)
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

	public void setMonthReturnPrincipalandinter(
			Double monthReturnPrincipalandinter) {
		this.monthReturnPrincipalandinter = monthReturnPrincipalandinter;
	}

	@Column(name = "MONTH_MANAGE_COST", nullable = false, precision = 22, scale = 7)
	public Double getMonthManageCost() {
		return this.monthManageCost;
	}

	public void setMonthManageCost(Double monthManageCost) {
		this.monthManageCost = monthManageCost;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RELEASE_TIME", nullable = false)
	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
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

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "RELEASE_STATUS", precision = 22, scale = 0)
	public BigDecimal getReleaseStatus() {
		return this.releaseStatus;
	}

	public void setReleaseStatus(BigDecimal releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	@Column(name = "IS_SHOW_AGE", precision = 22, scale = 0)
	public BigDecimal getIsShowAge() {
		return this.isShowAge;
	}

	public void setIsShowAge(BigDecimal isShowAge) {
		this.isShowAge = isShowAge;
	}

	@Column(name = "IS_SHOW_SEX", precision = 22, scale = 0)
	public BigDecimal getIsShowSex() {
		return this.isShowSex;
	}

	public void setIsShowSex(BigDecimal isShowSex) {
		this.isShowSex = isShowSex;
	}

	@Column(name = "IS_SHOW_DEGREE", precision = 22, scale = 0)
	public BigDecimal getIsShowDegree() {
		return this.isShowDegree;
	}

	public void setIsShowDegree(BigDecimal isShowDegree) {
		this.isShowDegree = isShowDegree;
	}

	@Column(name = "IS_SHOW_SCHOOL", precision = 22, scale = 0)
	public BigDecimal getIsShowSchool() {
		return this.isShowSchool;
	}

	public void setIsShowSchool(BigDecimal isShowSchool) {
		this.isShowSchool = isShowSchool;
	}

	@Column(name = "IS_SHOW_ENTRANCE_YEAR", precision = 22, scale = 0)
	public BigDecimal getIsShowEntranceYear() {
		return this.isShowEntranceYear;
	}

	public void setIsShowEntranceYear(BigDecimal isShowEntranceYear) {
		this.isShowEntranceYear = isShowEntranceYear;
	}

	@Column(name = "IS_SHOW_WORK_CITY", precision = 22, scale = 0)
	public BigDecimal getIsShowWorkCity() {
		return this.isShowWorkCity;
	}

	public void setIsShowWorkCity(BigDecimal isShowWorkCity) {
		this.isShowWorkCity = isShowWorkCity;
	}

	@Column(name = "IS_SHOW_VOCATION", precision = 22, scale = 0)
	public BigDecimal getIsShowVocation() {
		return this.isShowVocation;
	}

	public void setIsShowVocation(BigDecimal isShowVocation) {
		this.isShowVocation = isShowVocation;
	}

	@Column(name = "IS_SHOW_COMPANY_SCALE", precision = 22, scale = 0)
	public BigDecimal getIsShowCompanyScale() {
		return this.isShowCompanyScale;
	}

	public void setIsShowCompanyScale(BigDecimal isShowCompanyScale) {
		this.isShowCompanyScale = isShowCompanyScale;
	}

	@Column(name = "IS_SHOW_OFFICE", precision = 22, scale = 0)
	public BigDecimal getIsShowOffice() {
		return this.isShowOffice;
	}

	public void setIsShowOffice(BigDecimal isShowOffice) {
		this.isShowOffice = isShowOffice;
	}

	@Column(name = "IS_SHOW_WORK_YEAR", precision = 22, scale = 0)
	public BigDecimal getIsShowWorkYear() {
		return this.isShowWorkYear;
	}

	public void setIsShowWorkYear(BigDecimal isShowWorkYear) {
		this.isShowWorkYear = isShowWorkYear;
	}

	@Column(name = "IS_SHOW_MARRY", precision = 22, scale = 0)
	public BigDecimal getIsShowMarry() {
		return this.isShowMarry;
	}

	public void setIsShowMarry(BigDecimal isShowMarry) {
		this.isShowMarry = isShowMarry;
	}

	@Column(name = "IS_SHOW_HAVA_HOUSE", precision = 22, scale = 0)
	public BigDecimal getIsShowHavaHouse() {
		return this.isShowHavaHouse;
	}

	public void setIsShowHavaHouse(BigDecimal isShowHavaHouse) {
		this.isShowHavaHouse = isShowHavaHouse;
	}

	@Column(name = "IS_SHOW_HOUSE_LOAN", precision = 22, scale = 0)
	public BigDecimal getIsShowHouseLoan() {
		return this.isShowHouseLoan;
	}

	public void setIsShowHouseLoan(BigDecimal isShowHouseLoan) {
		this.isShowHouseLoan = isShowHouseLoan;
	}

	@Column(name = "IS_SHOW_HAVA_CAR", precision = 22, scale = 0)
	public BigDecimal getIsShowHavaCar() {
		return this.isShowHavaCar;
	}

	public void setIsShowHavaCar(BigDecimal isShowHavaCar) {
		this.isShowHavaCar = isShowHavaCar;
	}

	@Column(name = "IS_SHOW_CAR_LOAN", precision = 22, scale = 0)
	public BigDecimal getIsShowCarLoan() {
		return this.isShowCarLoan;
	}

	public void setIsShowCarLoan(BigDecimal isShowCarLoan) {
		this.isShowCarLoan = isShowCarLoan;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "loanId")
	public List<MyInvestmentLateInfo> getLateInfo() {
		return LateInfo;
	}

	public void setLateInfo(List<MyInvestmentLateInfo> lateInfo) {
		LateInfo = lateInfo;
	}
	
	@Column(name = "START_INVEST_TIME", nullable = false)
	public Date getStartInvestTime() {
		return startInvestTime;
	}

	public void setStartInvestTime(Date startInvestTime) {
		this.startInvestTime = startInvestTime;
	}
}