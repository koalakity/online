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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserInfoPerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_INFO_PERSON")
public class FinancialUserInfoPerson implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2366881235285329189L;
	private BigDecimal infoId;
	private FinancialUsers user;
	private String realName;
	private BigDecimal sex;
	private String phoneNo;
	private String phoneValidateCode;
	private String identityNo;
	private Date birthday;
	private String degreeNo;
	private BigDecimal maxDegree;
	private String graduatSchool;
	private String inschoolTime;
	private BigDecimal isapproveMarry;
	private BigDecimal isapproveHavechild;
	private BigDecimal isapproveHaveHouse;
	private String housePropertyAddress;
	private BigDecimal isapproveHouseMortgage;
	private BigDecimal houseMonthMortgage;
	private BigDecimal isapproveHaveCar;
	private String carBrand;
	private String carYears;
	private String carNo;
	private BigDecimal isapproveCarMortgage;
	private BigDecimal carMonthMortgage;
	private String hkAddress;
	private String liveAddress;
	private String livePhoneArea;
	private String livePhoneNo;
	private String postCode;
	private String jobTitleOne;
	private String jobTitleTwo;
	private String sinaWeiboAccount;
	private String realApproveName;
	private String realApprovePhone;
	private String realApproveCorporationAdd;
	private String realApproveHomeAddress;
	private BigDecimal realApproveAssets;
	private String realApproveOperateTime;
	private BigDecimal realApproveMonthWater;
	private BigDecimal realApproveApplyMoney;
	private BigDecimal videoApprove;
	private String phoneApproveName;
	private String phoneApproveNo;
	private String phonNoBak;
	private Date validateCodeDate;
	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "USERINFOPERSON_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "INFO_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getInfoId() {
		return this.infoId;
	}

	public void setInfoId(BigDecimal infoId) {
		this.infoId = infoId;
	}

	@Column(name = "REAL_NAME", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "SEX", precision = 22, scale = 0)
	public BigDecimal getSex() {
		return this.sex;
	}

	public void setSex(BigDecimal sex) {
		this.sex = sex;
	}

	@Column(name = "PHONE_NO", length = 26)
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "PHONE_VALIDATE_CODE", length = 6)
	public String getPhoneValidateCode() {
		return this.phoneValidateCode;
	}

	public void setPhoneValidateCode(String phoneValidateCode) {
		this.phoneValidateCode = phoneValidateCode;
	}

	@Column(name = "IDENTITY_NO", length = 36)
	public String getIdentityNo() {
		return this.identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "DEGREE_NO", length = 18)
	public String getDegreeNo() {
		return this.degreeNo;
	}

	public void setDegreeNo(String degreeNo) {
		this.degreeNo = degreeNo;
	}

	@Column(name = "MAX_DEGREE", precision = 22, scale = 0)
	public BigDecimal getMaxDegree() {
		return this.maxDegree;
	}

	public void setMaxDegree(BigDecimal maxDegree) {
		this.maxDegree = maxDegree;
	}

	@Column(name = "GRADUAT_SCHOOL", length = 100)
	public String getGraduatSchool() {
		return this.graduatSchool;
	}

	public void setGraduatSchool(String graduatSchool) {
		this.graduatSchool = graduatSchool;
	}

	@Column(name = "INSCHOOL_TIME", length = 8)
	public String getInschoolTime() {
		return this.inschoolTime;
	}

	public void setInschoolTime(String inschoolTime) {
		this.inschoolTime = inschoolTime;
	}

	@Column(name = "ISAPPROVE_MARRY", precision = 22, scale = 0)
	public BigDecimal getIsapproveMarry() {
		return this.isapproveMarry;
	}

	public void setIsapproveMarry(BigDecimal isapproveMarry) {
		this.isapproveMarry = isapproveMarry;
	}

	@Column(name = "ISAPPROVE_HAVECHILD", precision = 22, scale = 0)
	public BigDecimal getIsapproveHavechild() {
		return this.isapproveHavechild;
	}

	public void setIsapproveHavechild(BigDecimal isapproveHavechild) {
		this.isapproveHavechild = isapproveHavechild;
	}

	@Column(name = "ISAPPROVE_HAVE_HOUSE", precision = 22, scale = 0)
	public BigDecimal getIsapproveHaveHouse() {
		return this.isapproveHaveHouse;
	}

	public void setIsapproveHaveHouse(BigDecimal isapproveHaveHouse) {
		this.isapproveHaveHouse = isapproveHaveHouse;
	}

	@Column(name = "HOUSE_PROPERTY_ADDRESS", length = 200)
	public String getHousePropertyAddress() {
		return this.housePropertyAddress;
	}

	public void setHousePropertyAddress(String housePropertyAddress) {
		this.housePropertyAddress = housePropertyAddress;
	}

	@Column(name = "ISAPPROVE_HOUSE_MORTGAGE", precision = 22, scale = 0)
	public BigDecimal getIsapproveHouseMortgage() {
		return this.isapproveHouseMortgage;
	}

	public void setIsapproveHouseMortgage(BigDecimal isapproveHouseMortgage) {
		this.isapproveHouseMortgage = isapproveHouseMortgage;
	}

	@Column(name = "HOUSE_MONTH_MORTGAGE", precision = 22, scale = 0)
	public BigDecimal getHouseMonthMortgage() {
		return this.houseMonthMortgage;
	}

	public void setHouseMonthMortgage(BigDecimal houseMonthMortgage) {
		this.houseMonthMortgage = houseMonthMortgage;
	}

	@Column(name = "ISAPPROVE_HAVE_CAR", precision = 22, scale = 0)
	public BigDecimal getIsapproveHaveCar() {
		return this.isapproveHaveCar;
	}

	public void setIsapproveHaveCar(BigDecimal isapproveHaveCar) {
		this.isapproveHaveCar = isapproveHaveCar;
	}

	@Column(name = "CAR_BRAND", length = 100)
	public String getCarBrand() {
		return this.carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	@Column(name = "CAR_YEARS")
	public String getCarYears() {
		return this.carYears;
	}

	public void setCarYears(String carYears) {
		this.carYears = carYears;
	}

	@Column(name = "CAR_NO")
	public String getCarNo() {
		return this.carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	@Column(name = "ISAPPROVE_CAR_MORTGAGE", precision = 22, scale = 0)
	public BigDecimal getIsapproveCarMortgage() {
		return this.isapproveCarMortgage;
	}

	public void setIsapproveCarMortgage(BigDecimal isapproveCarMortgage) {
		this.isapproveCarMortgage = isapproveCarMortgage;
	}

	@Column(name = "CAR_MONTH_MORTGAGE", precision = 22, scale = 0)
	public BigDecimal getCarMonthMortgage() {
		return this.carMonthMortgage;
	}

	public void setCarMonthMortgage(BigDecimal carMonthMortgage) {
		this.carMonthMortgage = carMonthMortgage;
	}

	@Column(name = "HK_ADDRESS", length = 200)
	public String getHkAddress() {
		return this.hkAddress;
	}

	public void setHkAddress(String hkAddress) {
		this.hkAddress = hkAddress;
	}

	@Column(name = "LIVE_ADDRESS", length = 200)
	public String getLiveAddress() {
		return this.liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	@Column(name = "LIVE_PHONE_AREA", length = 6)
	public String getLivePhoneArea() {
		return this.livePhoneArea;
	}

	public void setLivePhoneArea(String livePhoneArea) {
		this.livePhoneArea = livePhoneArea;
	}

	@Column(name = "LIVE_PHONE_NO", length = 15)
	public String getLivePhoneNo() {
		return this.livePhoneNo;
	}

	public void setLivePhoneNo(String livePhoneNo) {
		this.livePhoneNo = livePhoneNo;
	}

	@Column(name = "POST_CODE", length = 6)
	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "JOB_TITLE_ONE", length = 200)
	public String getJobTitleOne() {
		return this.jobTitleOne;
	}

	public void setJobTitleOne(String jobTitleOne) {
		this.jobTitleOne = jobTitleOne;
	}

	@Column(name = "JOB_TITLE_TWO", length = 200)
	public String getJobTitleTwo() {
		return this.jobTitleTwo;
	}

	public void setJobTitleTwo(String jobTitleTwo) {
		this.jobTitleTwo = jobTitleTwo;
	}

	@Column(name = "SINA_WEIBO_ACCOUNT", length = 200)
	public String getSinaWeiboAccount() {
		return this.sinaWeiboAccount;
	}

	public void setSinaWeiboAccount(String sinaWeiboAccount) {
		this.sinaWeiboAccount = sinaWeiboAccount;
	}

	@Column(name = "REAL_APPROVE_NAME", length = 50)
	public String getRealApproveName() {
		return this.realApproveName;
	}

	public void setRealApproveName(String realApproveName) {
		this.realApproveName = realApproveName;
	}

	@Column(name = "REAL_APPROVE_PHONE", length = 50)
	public String getRealApprovePhone() {
		return this.realApprovePhone;
	}

	public void setRealApprovePhone(String realApprovePhone) {
		this.realApprovePhone = realApprovePhone;
	}

	@Column(name = "REAL_APPROVE_CORPORATION_ADD", length = 200)
	public String getRealApproveCorporationAdd() {
		return this.realApproveCorporationAdd;
	}

	public void setRealApproveCorporationAdd(String realApproveCorporationAdd) {
		this.realApproveCorporationAdd = realApproveCorporationAdd;
	}

	@Column(name = "REAL_APPROVE_HOME_ADDRESS", length = 200)
	public String getRealApproveHomeAddress() {
		return this.realApproveHomeAddress;
	}

	public void setRealApproveHomeAddress(String realApproveHomeAddress) {
		this.realApproveHomeAddress = realApproveHomeAddress;
	}

	@Column(name = "REAL_APPROVE_ASSETS", precision = 22, scale = 0)
	public BigDecimal getRealApproveAssets() {
		return this.realApproveAssets;
	}

	public void setRealApproveAssets(BigDecimal realApproveAssets) {
		this.realApproveAssets = realApproveAssets;
	}

	@Column(name = "REAL_APPROVE_OPERATE_TIME", length = 4)
	public String getRealApproveOperateTime() {
		return this.realApproveOperateTime;
	}

	public void setRealApproveOperateTime(String realApproveOperateTime) {
		this.realApproveOperateTime = realApproveOperateTime;
	}

	@Column(name = "REAL_APPROVE_MONTH_WATER", precision = 22, scale = 0)
	public BigDecimal getRealApproveMonthWater() {
		return this.realApproveMonthWater;
	}

	public void setRealApproveMonthWater(BigDecimal realApproveMonthWater) {
		this.realApproveMonthWater = realApproveMonthWater;
	}

	@Column(name = "REAL_APPROVE_APPLY_MONEY", precision = 22, scale = 0)
	public BigDecimal getRealApproveApplyMoney() {
		return this.realApproveApplyMoney;
	}

	public void setRealApproveApplyMoney(BigDecimal realApproveApplyMoney) {
		this.realApproveApplyMoney = realApproveApplyMoney;
	}

	@Column(name = "VIDEO_APPROVE", precision = 22, scale = 0)
	public BigDecimal getVideoApprove() {
		return videoApprove;
	}

	public void setVideoApprove(BigDecimal videoApprove) {
		this.videoApprove = videoApprove;
	}

	@Column(name = "PHONE_APPROVE_NAME", length = 50)
	public String getPhoneApproveName() {
		return phoneApproveName;
	}

	public void setPhoneApproveName(String phoneApproveName) {
		this.phoneApproveName = phoneApproveName;
	}

	@Column(name = "PHONE_APPROVE_NO", length = 20)
	public String getPhoneApproveNo() {
		return phoneApproveNo;
	}

	public void setPhoneApproveNo(String phoneApproveNo) {
		this.phoneApproveNo = phoneApproveNo;
	}

	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@OneToOne(fetch = FetchType.LAZY)
	public FinancialUsers getUser() {
		return user;
	}

	public void setUser(FinancialUsers user) {
		this.user = user;
	}

	@Column(name = "PHONE_NO_BAK", length = 26)
	public String getPhonNoBak() {
		return phonNoBak;
	}

	public void setPhonNoBak(String phonNoBak) {
		this.phonNoBak = phonNoBak;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALIDATE_CODE_TIME")
	public Date getValidateCodeDate() {
		return validateCodeDate;
	}

	public void setValidateCodeDate(Date validateCodeDate) {
		this.validateCodeDate = validateCodeDate;
	}

}