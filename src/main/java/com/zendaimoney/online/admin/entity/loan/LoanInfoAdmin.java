package com.zendaimoney.online.admin.entity.loan;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.util.DateFormatUtils;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;

/**
 * LoanInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOAN_INFO")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class LoanInfoAdmin implements java.io.Serializable {

	private static final long serialVersionUID = 2616211533221320420L;
	private BigDecimal loanId;
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
	// private BigDecimal ledgerLoanId;
	private AcTLedgerLoanAdmin loanAcTLedgerLoan;
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
	private Set<LoanNoteAdmin> loanNotes = new HashSet<LoanNoteAdmin>();
	private Set<InvestInfoAdmin> investInfos = new HashSet<InvestInfoAdmin>();
	private Date startInvestTime;
	private AccountUserInfoPersonAdmin userInfoPerson;
	private AccountUsersAdmin accountUsers;
	//private ChannelInfoVO channelInfo;
	// Constructors

	/** default constructor */
	public LoanInfoAdmin() {
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

	@OneToMany(mappedBy = "loanInfo", fetch = FetchType.LAZY)
	public Set<InvestInfoAdmin> getInvestInfos() {
		return investInfos;
	}

	public void setInvestInfos(Set<InvestInfoAdmin> investInfos) {
		this.investInfos = investInfos;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public AccountUsersAdmin getAccountUsers() {
		return accountUsers;
	}

	public void setAccountUsers(AccountUsersAdmin accountUsers) {
		this.accountUsers = accountUsers;
	}

	@OneToMany(mappedBy = "loanInfo", fetch = FetchType.LAZY)
	public Set<LoanNoteAdmin> getLoanNotes() {
		return loanNotes;
	}

	public void setLoanNotes(Set<LoanNoteAdmin> loanNotes) {
		this.loanNotes = loanNotes;
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

	@Column(name = "RELEASE_TIME", nullable = false)
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

	@Column(name = "START_INVEST_TIME", nullable = false)
	public Date getStartInvestTime() {
		return startInvestTime;
	}

	public void setStartInvestTime(Date startInvestTime) {
		this.startInvestTime = startInvestTime;
	}

	@OneToOne
	@JoinColumn(name = "LEDGER_LOAN_ID")
	public AcTLedgerLoanAdmin getLoanAcTLedgerLoan() {
		return loanAcTLedgerLoan;
	}

	public void setLoanAcTLedgerLoan(AcTLedgerLoanAdmin loanAcTLedgerLoan) {
		this.loanAcTLedgerLoan = loanAcTLedgerLoan;
	}

	// @Column(name = "LEDGER_LOAN_ID", precision = 22, scale = 0)
	// public BigDecimal getLedgerLoanId() {
	// return ledgerLoanId;
	// }
	//
	// public void setLedgerLoanId(BigDecimal ledgerLoanId) {
	// this.ledgerLoanId = ledgerLoanId;
	// }

	@Transient
	public String getLoginName() {
		return accountUsers.getLoginName();
	}

	@Transient
	public String getRealName() {
		return accountUsers.getRealName();
	}

	@Transient
	public String getPhoneNo() {
		return accountUsers.getPhoneNo();
	}

	@Transient
	public String getReleaseTimeFormatt() {
		return this.releaseTime.toString().substring(0, 10);
	}

	@Transient
	public String getLoanUseFormatt() {
		if (loanUse == null) {
			return "";
		}

		switch (Integer.parseInt(loanUse.toString())) {
		case 1:
			return "短期周转";
		case 2:
			return "教育培训";
		case 3:
			return "购房借款";
		case 4:
			return "购车借款";
		case 5:
			return "装修基金";
		case 6:
			return "婚礼筹备";
		case 7:
			return "投资创业";
		case 8:
			return "医疗支出";
		case 9:
			return "个人消费";

		case 10:
			return "其他";

		default:
			return "";
		}

	}

	@Transient
	public String getLoanUseImage() {
		if (loanUse == null) {
			return "";
		}
		switch (Integer.parseInt(loanUse.toString())) {
		case 1:
			return "img31.jpg";
		case 2:
			return "img001.jpg";
		case 3:
			return "img30.jpg";
		case 4:
			return "img32.jpg";
		case 5:
			return "img35.jpg";
		case 6:
			return "img34.jpg";
		case 7:
			return "img36.jpg";
		case 8:
			return "img33.jpg";
		case 9:
			return "img37.jpg";

		case 10:
			return "img38.jpg";

		default:
			return "";
		}
	}

	@Transient
	public String getPaymentMethodFormatt() {

		switch (Integer.parseInt(paymentMethod.toString())) {
		case 1:
			return "等额本息";
		default:
			return "";
		}

	}

	@Transient
	public String getProgress() {
		double progress = 0;
		for (InvestInfoAdmin investInfo : investInfos) {
			progress = ArithUtil.add(progress, investInfo.getHavaScale());
		}
		return ObjectFormatUtil.numberFormat(new BigDecimal(progress * 100), "##.##");
	}

	@Transient
	public String getYearRateFormatt() {
		return ObjectFormatUtil.formatPercent(this.yearRate, "#,###.00%");

	}

	@Transient
	public BigDecimal getLedgerLoanId() {
		if (loanAcTLedgerLoan != null) {
			return loanAcTLedgerLoan.getId();
		}
		return null;

	}

	@Transient
	public String getNextExpiry() {
		if (loanAcTLedgerLoan != null && loanAcTLedgerLoan.getNextExpiry() != null) {
			return loanAcTLedgerLoan.getNextExpiry().toString();
		} else {
			return "";
		}
	}

	/**
	 * @author Ray
	 * @date 2012-11-30 上午10:02:02
	 * @return description:添加放款时间，后台还款列表中用
	 */
	@Transient
	public String getInterestStart() {
		if (loanAcTLedgerLoan != null && loanAcTLedgerLoan.getInterestStart() != null) {
			return loanAcTLedgerLoan.getInterestStart().toString();
		} else {
			return "";
		}
	}

	@Transient
	public int getPastDueDays() {
		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		return DateFormatUtils.countDays(getNextExpiry().toString(), dateFormate.format(new Date()));
	}

	@Transient
	public String getFormattLoanAmount() {
		return ObjectFormatUtil.formatCurrency(this.loanAmount);
	}

	@Transient
	public String getFormattMonthReturnPrincipalandinter() {
		return ObjectFormatUtil.formatCurrency(this.monthReturnPrincipalandinter);
	}

	@Transient
	public String getFormattMonthManageCost() {
		return ObjectFormatUtil.formatCurrency(this.monthManageCost);
	}
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	public AccountUserInfoPersonAdmin getUserInfoPerson() {
		return userInfoPerson;
	}

	public void setUserInfoPerson(AccountUserInfoPersonAdmin userInfoPerson) {
		this.userInfoPerson = userInfoPerson;
	}

	@Transient
	public BigDecimal getLoanUserId() {
		return accountUsers.getUserId();
	}
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "CHANNELINFO_ID")
//	public ChannelInfoVO getChannelInfo() {
//		return channelInfo;
//	}
//
//	public void setChannelInfo(ChannelInfoVO channelInfo) {
//		this.channelInfo = channelInfo;
//	}
}