package com.zendaimoney.online.entity.account;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserInfoPerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_INFO_PERSON")
public class AccountUserInfoPerson implements java.io.Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 382682890602989713L;
    // Fields

    private BigDecimal infoId;
    private AccountUsers user;
    private String headPath;
    private String realName;
    private BigDecimal sex;
    private String credentialKind;
    private String identityNo;
    private String phoneNo;
    private String phoneValidateCode;
    private BigDecimal hometownArea;
    private BigDecimal hometownCity;
    private String hkAddress;
    private String liveAddress;
    private String livePhoneArea;
    private String livePhoneNo;
    private String postCode;
    private BigDecimal isapproveMarry;
    private BigDecimal isapproveHavechild;
    private BigDecimal monthIncome;
    private String familyMembersName;
    private String familyRelation;
    private String familyMemberPhone;
    private String otherContactName;
    private String otherRelation;
    private String otherContactPhone;
    private String qqNo;
    private String msnNo;
    private String sinaWeiboAccount;
    private BigDecimal maxDegree;
    private String graduatSchool;
    private String birthday;
    private String degreeNo;
    private String inschoolTime;
    private String jobTitleOne;
    private String jobTitleTwo;
    private String jobTitleThr;
    private BigDecimal isapproveHaveHouse;
    private String housePropertyAddress;
    private BigDecimal isapproveHouseMortgage;
    private BigDecimal houseMonthMortgage;
    private BigDecimal isapproveHaveCar;
    private String carBrand;
    private String carYears;
    private String carNo;
    private BigDecimal isapproveCarMortgage;
    private BigDecimal monthHousingLoan;
    private BigDecimal monthCarLoan;
    private BigDecimal monthCreditCard;
    private BigDecimal monthMortgage;
    private BigDecimal carMonthMortgage;
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

    // Constructors

    /** default constructor */
    public AccountUserInfoPerson() {
    }

    /** minimal constructor */
    public AccountUserInfoPerson(BigDecimal infoId) {
        this.infoId = infoId;
    }

    /** full constructor */
    public AccountUserInfoPerson( String headPath, String realName,
            BigDecimal sex,String credentialKind,
            String identityNo, String phoneNo, String phoneValidateCode,
            BigDecimal hometownArea, BigDecimal hometownCity, String hkAddress,
            String liveAddress, String livePhoneArea, String livePhoneNo,
            String postCode, BigDecimal isapproveMarry,
            BigDecimal isapproveHavechild, BigDecimal monthIncome,
            String familyMembersName, String familyRelation,
            String familyMemberPhone, String otherContactName,
            String otherRelation, String otherContactPhone, String qqNo,
            String msnNo, String sinaWeiboAccount, BigDecimal maxDegree,
            String graduatSchool, String birthday, String degreeNo,
            String inschoolTime, String jobTitleOne, String jobTitleTwo,
            String jobTitleThr, BigDecimal isapproveHaveHouse,
            String housePropertyAddress, BigDecimal isapproveHouseMortgage,
            BigDecimal houseMonthMortgage, BigDecimal isapproveHaveCar,
            String carBrand, String carYears, String carNo,
            BigDecimal isapproveCarMortgage, BigDecimal monthHousingLoan,
            BigDecimal monthCarLoan, BigDecimal monthCreditCard,
            BigDecimal monthMortgage, BigDecimal carMonthMortgage,
            String realApproveName, String realApprovePhone,
            String realApproveCorporationAdd, String realApproveHomeAddress,
            BigDecimal realApproveAssets, String realApproveOperateTime,
            BigDecimal realApproveMonthWater, BigDecimal realApproveApplyMoney,
            BigDecimal videoApprove, String phoneApproveName,
            String phoneApproveNo) {
        this.headPath = headPath;
        this.realName = realName;
        this.sex = sex;
        this.credentialKind = credentialKind;
        this.identityNo = identityNo;
        this.phoneNo = phoneNo;
        this.phoneValidateCode = phoneValidateCode;
        this.hometownArea = hometownArea;
        this.hometownCity = hometownCity;
        this.hkAddress = hkAddress;
        this.liveAddress = liveAddress;
        this.livePhoneArea = livePhoneArea;
        this.livePhoneNo = livePhoneNo;
        this.postCode = postCode;
        this.isapproveMarry = isapproveMarry;
        this.isapproveHavechild = isapproveHavechild;
        this.monthIncome = monthIncome;
        this.familyMembersName = familyMembersName;
        this.familyRelation = familyRelation;
        this.familyMemberPhone = familyMemberPhone;
        this.otherContactName = otherContactName;
        this.otherRelation = otherRelation;
        this.otherContactPhone = otherContactPhone;
        this.qqNo = qqNo;
        this.msnNo = msnNo;
        this.sinaWeiboAccount = sinaWeiboAccount;
        this.maxDegree = maxDegree;
        this.graduatSchool = graduatSchool;
        this.birthday = birthday;
        this.degreeNo = degreeNo;
        this.inschoolTime = inschoolTime;
        this.jobTitleOne = jobTitleOne;
        this.jobTitleTwo = jobTitleTwo;
        this.jobTitleThr = jobTitleThr;
        this.isapproveHaveHouse = isapproveHaveHouse;
        this.housePropertyAddress = housePropertyAddress;
        this.isapproveHouseMortgage = isapproveHouseMortgage;
        this.houseMonthMortgage = houseMonthMortgage;
        this.isapproveHaveCar = isapproveHaveCar;
        this.carBrand = carBrand;
        this.carYears = carYears;
        this.carNo = carNo;
        this.isapproveCarMortgage = isapproveCarMortgage;
        this.monthHousingLoan = monthHousingLoan;
        this.monthCarLoan = monthCarLoan;
        this.monthCreditCard = monthCreditCard;
        this.monthMortgage = monthMortgage;
        this.carMonthMortgage = carMonthMortgage;
        this.realApproveName = realApproveName;
        this.realApprovePhone = realApprovePhone;
        this.realApproveCorporationAdd = realApproveCorporationAdd;
        this.realApproveHomeAddress = realApproveHomeAddress;
        this.realApproveAssets = realApproveAssets;
        this.realApproveOperateTime = realApproveOperateTime;
        this.realApproveMonthWater = realApproveMonthWater;
        this.realApproveApplyMoney = realApproveApplyMoney;
        this.videoApprove = videoApprove;
        this.phoneApproveName = phoneApproveName;
        this.phoneApproveNo = phoneApproveNo;
    }

    // Property accessors
    @SequenceGenerator(name = "generator")
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "generator")
    @Column(name = "INFO_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getInfoId() {
        return this.infoId;
    }

    public void setInfoId(BigDecimal infoId) {
        this.infoId = infoId;
    }

    @Column(name = "HEAD_PATH", length = 100)
    public String getHeadPath() {
        return this.headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
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
    
    @Column(name = "CREDENTIAL_KIND", length = 2)
    public String getCredentialKind() {
        return this.credentialKind;
    }

    public void setCredentialKind(String credentialKind) {
        this.credentialKind = credentialKind;
    }

    @Column(name = "IDENTITY_NO", length = 36)
    public String getIdentityNo() {
        return this.identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
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

    @Column(name = "HOMETOWN_AREA", precision = 22, scale = 0)
    public BigDecimal getHometownArea() {
        return this.hometownArea;
    }

    public void setHometownArea(BigDecimal hometownArea) {
        this.hometownArea = hometownArea;
    }

    @Column(name = "HOMETOWN_CITY", precision = 22, scale = 0)
    public BigDecimal getHometownCity() {
        return this.hometownCity;
    }

    public void setHometownCity(BigDecimal hometownCity) {
        this.hometownCity = hometownCity;
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

    @Column(name = "MONTH_INCOME", precision = 22, scale = 0)
    public BigDecimal getMonthIncome() {
        return this.monthIncome;
    }

    public void setMonthIncome(BigDecimal monthIncome) {
        this.monthIncome = monthIncome;
    }

    @Column(name = "FAMILY_MEMBERS_NAME", length = 40)
    public String getFamilyMembersName() {
        return this.familyMembersName;
    }

    public void setFamilyMembersName(String familyMembersName) {
        this.familyMembersName = familyMembersName;
    }

    @Column(name = "FAMILY_RELATION", length = 40)
    public String getFamilyRelation() {
        return this.familyRelation;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    @Column(name = "FAMILY_MEMBER_PHONE", length = 26)
    public String getFamilyMemberPhone() {
        return this.familyMemberPhone;
    }

    public void setFamilyMemberPhone(String familyMemberPhone) {
        this.familyMemberPhone = familyMemberPhone;
    }

    @Column(name = "OTHER_CONTACT_NAME", length = 40)
    public String getOtherContactName() {
        return this.otherContactName;
    }

    public void setOtherContactName(String otherContactName) {
        this.otherContactName = otherContactName;
    }

    @Column(name = "OTHER_RELATION", length = 40)
    public String getOtherRelation() {
        return this.otherRelation;
    }

    public void setOtherRelation(String otherRelation) {
        this.otherRelation = otherRelation;
    }

    @Column(name = "OTHER_CONTACT_PHONE", length = 26)
    public String getOtherContactPhone() {
        return this.otherContactPhone;
    }

    public void setOtherContactPhone(String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
    }

    @Column(name = "QQ_NO", length = 15)
    public String getQqNo() {
        return this.qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    @Column(name = "MSN_NO", length = 50)
    public String getMsnNo() {
        return this.msnNo;
    }

    public void setMsnNo(String msnNo) {
        this.msnNo = msnNo;
    }

    @Column(name = "SINA_WEIBO_ACCOUNT", length = 200)
    public String getSinaWeiboAccount() {
        return this.sinaWeiboAccount;
    }

    public void setSinaWeiboAccount(String sinaWeiboAccount) {
        this.sinaWeiboAccount = sinaWeiboAccount;
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

    @Column(name = "BIRTHDAY")
    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Column(name = "DEGREE_NO", length = 18)
    public String getDegreeNo() {
        return this.degreeNo;
    }

    public void setDegreeNo(String degreeNo) {
        this.degreeNo = degreeNo;
    }

    @Column(name = "INSCHOOL_TIME", length = 8)
    public String getInschoolTime() {
        return this.inschoolTime;
    }

    public void setInschoolTime(String inschoolTime) {
        this.inschoolTime = inschoolTime;
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

    @Column(name = "JOB_TITLE_THR", length = 200)
    public String getJobTitleThr() {
        return this.jobTitleThr;
    }

    public void setJobTitleThr(String jobTitleThr) {
        this.jobTitleThr = jobTitleThr;
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

    @Column(name = "MONTH_HOUSING_LOAN", precision = 22, scale = 0)
    public BigDecimal getMonthHousingLoan() {
        return this.monthHousingLoan;
    }

    public void setMonthHousingLoan(BigDecimal monthHousingLoan) {
        this.monthHousingLoan = monthHousingLoan;
    }

    @Column(name = "MONTH_CAR_LOAN", precision = 22, scale = 0)
    public BigDecimal getMonthCarLoan() {
        return this.monthCarLoan;
    }

    public void setMonthCarLoan(BigDecimal monthCarLoan) {
        this.monthCarLoan = monthCarLoan;
    }

    @Column(name = "MONTH_CREDIT_CARD", precision = 22, scale = 0)
    public BigDecimal getMonthCreditCard() {
        return this.monthCreditCard;
    }

    public void setMonthCreditCard(BigDecimal monthCreditCard) {
        this.monthCreditCard = monthCreditCard;
    }

    @Column(name = "MONTH_MORTGAGE", precision = 22, scale = 0)
    public BigDecimal getMonthMortgage() {
        return this.monthMortgage;
    }

    public void setMonthMortgage(BigDecimal monthMortgage) {
        this.monthMortgage = monthMortgage;
    }

    @Column(name = "CAR_MONTH_MORTGAGE", precision = 22, scale = 0)
    public BigDecimal getCarMonthMortgage() {
        return this.carMonthMortgage;
    }

    public void setCarMonthMortgage(BigDecimal carMonthMortgage) {
        this.carMonthMortgage = carMonthMortgage;
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
        return this.videoApprove;
    }

    public void setVideoApprove(BigDecimal videoApprove) {
        this.videoApprove = videoApprove;
    }

    @Column(name = "PHONE_APPROVE_NAME", length = 50)
    public String getPhoneApproveName() {
        return this.phoneApproveName;
    }

    public void setPhoneApproveName(String phoneApproveName) {
        this.phoneApproveName = phoneApproveName;
    }

    @Column(name = "PHONE_APPROVE_NO", length = 20)
    public String getPhoneApproveNo() {
        return this.phoneApproveNo;
    }

    public void setPhoneApproveNo(String phoneApproveNo) {
        this.phoneApproveNo = phoneApproveNo;
    }

    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)       
    @OneToOne      
    public AccountUsers getUser() {
        return user;
    }

    public void setUser(AccountUsers user) {
        this.user = user;
    }
    
	
}