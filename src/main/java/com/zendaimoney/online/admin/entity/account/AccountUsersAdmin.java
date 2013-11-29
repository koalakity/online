package com.zendaimoney.online.admin.entity.account;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.extract.ExtractNoteAdmin;
import com.zendaimoney.online.admin.entity.fundDetail.FinancialAcTCustomerAdmin;
import com.zendaimoney.online.admin.entity.loan.InvestInfoAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.admin.util.AuditUserTools;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class AccountUsersAdmin implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2066519516630808311L;
	private BigDecimal userId;
	private String loginName;
	private String loginPassword;
	private String email;
	private BigDecimal isapproveEmail;
	private String regIp;
	private Date regTime;
	private BigDecimal loginCount;
	private BigDecimal loginStatus;
	private Date loginTimeLast;
	private String loginIpLast;
	private BigDecimal statusView;
	private BigDecimal regType;
	private BigDecimal isApprovePhone;
	private BigDecimal isApproveCard;
	private AccountUserInfoPersonAdmin userInfoPerson;
	private UserCreditNoteAdmin userCreditNote;
	private BigDecimal userStatus;
	private BigDecimal lockStatus;
	private BigDecimal returnStatus;
	private BigDecimal delStatus = BigDecimal.ZERO;
	private BigDecimal materialReviewStatus;
	private Date inclosureSubmitTime;
	private Set<InvestInfoAdmin> investInofs = new HashSet<InvestInfoAdmin>();
	private FinancialAcTCustomerAdmin financialAcTCustomerAdmin;
	private Set<AccountUserApproveAdmin> accountUserApproveAdmins = new HashSet<AccountUserApproveAdmin>();
	private Set<ExtractNoteAdmin> extracNotes = new HashSet<ExtractNoteAdmin>();
	private ChannelInfoVO channelInfo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHANNELINFO_ID")
	public ChannelInfoVO getChannelInfo() {
		return channelInfo;
	}

	public void setChannelInfo(ChannelInfoVO channelInfo) {
		this.channelInfo = channelInfo;
	}

	/**
	 * 添加关联3个实体对象 添加入：Ray 2012-10-20
	 */
	private AccountUserInfoJobAdmin userInfoJob; // 用户工作信息对象
	private AccountPrivatePropritorAdmin privatePropritor; // 私营业主资料
	private ID5Check id5Check;// ID5认证表

	@OneToOne
	@JoinColumn(name = "T_CUSTOMER_ID")
	public FinancialAcTCustomerAdmin getFinancialAcTCustomerAdmin() {
		return financialAcTCustomerAdmin;
	}

	public void setFinancialAcTCustomerAdmin(FinancialAcTCustomerAdmin financialAcTCustomerAdmin) {
		this.financialAcTCustomerAdmin = financialAcTCustomerAdmin;
	}

	public BigDecimal getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(BigDecimal delStatus) {
		this.delStatus = delStatus;
	}

	private Set<LoanInfoAdmin> loanInfos = new HashSet<LoanInfoAdmin>();

	// Constructors
	@OneToMany(mappedBy = "accountUsers", fetch = FetchType.LAZY)
	public Set<LoanInfoAdmin> getLoanInfos() {
		return loanInfos;
	}

	public void setLoanInfos(Set<LoanInfoAdmin> loanInfos) {
		this.loanInfos = loanInfos;
	}

	/** default constructor */
	public AccountUsersAdmin() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "USERS_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "LOGIN_NAME", nullable = false, length = 50)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "LOGIN_PASSWORD", nullable = false, length = 50)
	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name = "EMAIL", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ISAPPROVE_EMAIL", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIsapproveEmail() {
		return this.isapproveEmail;
	}

	public void setIsapproveEmail(BigDecimal isapproveEmail) {
		this.isapproveEmail = isapproveEmail;
	}

	@Column(name = "REG_IP", nullable = false, length = 40)
	public String getRegIp() {
		return this.regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_TIME", nullable = false, length = 7)
	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}

	@Column(name = "LOGIN_COUNT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(BigDecimal loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "LOGIN_STATUS", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(BigDecimal loginStatus) {
		this.loginStatus = loginStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_TIME_LAST", nullable = false, length = 7)
	public Date getLoginTimeLast() {
		return this.loginTimeLast;
	}

	public void setLoginTimeLast(Timestamp loginTimeLast) {
		this.loginTimeLast = loginTimeLast;
	}

	@Column(name = "LOGIN_IP_LAST", nullable = false, length = 50)
	public String getLoginIpLast() {
		return this.loginIpLast;
	}

	public void setLoginIpLast(String loginIpLast) {
		this.loginIpLast = loginIpLast;
	}

	@Column(name = "STATUS_VIEW", precision = 22, scale = 0)
	public BigDecimal getStatusView() {
		return this.statusView;
	}

	public void setStatusView(BigDecimal statusView) {
		this.statusView = statusView;
	}

	@Column(name = "REG_TYPE", precision = 22, scale = 0)
	public BigDecimal getRegType() {
		return this.regType;
	}

	public void setRegType(BigDecimal regType) {
		this.regType = regType;
	}

	@Column(name = "ISAPPROVE_PHONE", precision = 22, scale = 0)
	public BigDecimal getIsApprovePhone() {
		return isApprovePhone;
	}

	public void setIsApprovePhone(BigDecimal isApprovePhone) {
		this.isApprovePhone = isApprovePhone;
	}

	@Column(name = "ISAPPROVE_CARD", precision = 22, scale = 0)
	public BigDecimal getIsApproveCard() {
		return isApproveCard;
	}

	public void setIsApproveCard(BigDecimal isApproveCard) {
		this.isApproveCard = isApproveCard;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	public AccountUserInfoPersonAdmin getUserInfoPerson() {
		return userInfoPerson;
	}

	public void setUserInfoPerson(AccountUserInfoPersonAdmin userInfoPerson) {
		this.userInfoPerson = userInfoPerson;
	}

	/**
	 * @author Ray
	 * @date 2012-10-19 下午7:33:04
	 * @return description:增加关联用户工作信息表set/get方法
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	public AccountUserInfoJobAdmin getUserInfoJob() {
		return userInfoJob;
	}

	public void setUserInfoJob(AccountUserInfoJobAdmin userInfoJob) {
		this.userInfoJob = userInfoJob;
	}

	/**
	 * @author Ray
	 * @date 2012-10-20 上午10:33:04
	 * @return description:增加关联用户工作信息表set/get方法
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	public AccountPrivatePropritorAdmin getPrivatePropritor() {
		return privatePropritor;
	}

	public void setPrivatePropritor(AccountPrivatePropritorAdmin privatePropritor) {
		this.privatePropritor = privatePropritor;
	}

	/**
	 * @author Ray
	 * @date 2012-10-25 下午5:35:22
	 * @return description:增加关联用户ID5验证表
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	public ID5Check getId5Check() {
		return id5Check;
	}

	public void setId5Check(ID5Check id5Check) {
		this.id5Check = id5Check;
	}

	@OneToOne(mappedBy = "user")
	public UserCreditNoteAdmin getUserCreditNote() {
		return userCreditNote;
	}

	public void setUserCreditNote(UserCreditNoteAdmin userCreditNote) {
		this.userCreditNote = userCreditNote;
	}

	@Column(name = "USER_STATUS")
	public BigDecimal getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(BigDecimal userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "LOCK_STATUS")
	public BigDecimal getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(BigDecimal lockStatus) {
		this.lockStatus = lockStatus;
	}

	@Column(name = "RETURN_STATUS")
	public BigDecimal getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(BigDecimal returnStatus) {
		this.returnStatus = returnStatus;
	}

	@Transient
	public String getSex() {
		if (null == getUserInfoPerson()) {
			return "";
		}
		if (new BigDecimal(2).equals(getUserInfoPerson().getSex())) {
			return "女";
		}
		return "男";
	}

	@Transient
	public String getRealName() {
		return getUserInfoPerson() == null ? "" : getUserInfoPerson().getRealName();
	}

	@Transient
	public String getPhoneNo() {
		return getUserInfoPerson() == null ? "" : getUserInfoPerson().getPhoneNo();
	}

	@Transient
	public String getIdentityNo() {
		return getUserInfoPerson() == null ? "" : getUserInfoPerson().getIdentityNo();
	}

	@Transient
	public String getCreditGrade() {
		if (getUserCreditNote() == null) {
			return "";
		} else {
			if (getUserCreditNote().getCreditGrade() == null) {
				return "";
			} else {
				return getUserCreditNote().getCreditGrade().toString();
			}
		}
	}

	@Transient
	public String getCreditAmount() {
		if (getUserCreditNote() == null) {
			return "0";
		} else {
			if (getUserCreditNote().getCreditAmount() == null) {
				return "0";
			} else {
				return getUserCreditNote().getCreditAmount().toString();
			}
		}
	}

	@Transient
	public BigDecimal getCreditScoreSum() {
		if (getUserCreditNote() == null) {
			return BigDecimal.ZERO;
		} else {
			if (getUserCreditNote().getCreditScoreSum() == null) {
				return BigDecimal.ZERO;
			} else {
				return getUserCreditNote().getCreditScoreSum();
			}
		}
	}

	@Transient
	public String getTempCreditAmount() {
		if (getUserCreditNote() == null) {
			return "";
		} else if (getUserCreditNote().getTempCreditAmount() == null) {
			return "0";
		} else {
			return getUserCreditNote().getTempCreditAmount().toString();
		}
	}

	@Transient
	public BigDecimal getTempCreditScoreSum() {
		if (getUserCreditNote() == null) {
			return BigDecimal.ZERO;
		} else {
			if (getUserCreditNote().getTempCreditScoreSum() == null) {
				return BigDecimal.ZERO;
			} else {
				return getUserCreditNote().getTempCreditScoreSum();
			}
		}
	}

	@Transient
	public String getTempCreidtGrade() {
		if (getUserCreditNote() == null) {
			return "";
		} else {
			if (getUserCreditNote().getTempCreidtGrade() == null) {
				return "";
			} else {
				return getUserCreditNote().getTempCreidtGrade().toString();
			}
		}
	}

	public BigDecimal getMaterialReviewStatus() {
		return materialReviewStatus;
	}

	public void setMaterialReviewStatus(BigDecimal materialReviewStatus) {
		this.materialReviewStatus = materialReviewStatus;
	}

	@Column(name = "INCLOSURE_SUBMIT_TIME")
	public Date getInclosureSubmitTime() {
		return inclosureSubmitTime;
	}

	public void setInclosureSubmitTime(Date inclosureSubmitTime) {
		this.inclosureSubmitTime = inclosureSubmitTime;
	}

	@OneToMany(mappedBy = "accountUser", fetch = FetchType.LAZY)
	public Set<InvestInfoAdmin> getInvestInofs() {
		return investInofs;
	}

	public void setInvestInofs(Set<InvestInfoAdmin> investInofs) {
		this.investInofs = investInofs;
	}

	@Transient
	public String getUserStatusStr() {
		if (BigDecimal.ONE.equals(userStatus)) {
			return "未验证";
		} else if (new BigDecimal(2).equals(userStatus)) {
			return "已验证";
		} else if (new BigDecimal(3).equals(userStatus)) {
			return "已提交资料";
		} else if (new BigDecimal(4).equals(userStatus)) {
			return "已进入审核";
		} else if (new BigDecimal(5).equals(userStatus)) {
			return "正常";
		} else if (new BigDecimal(6).equals(userStatus)) {
			return "锁定";
		} else if (new BigDecimal(7).equals(userStatus)) {
			return "被举报";
		}
		return "";
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public void setLoginTimeLast(Date loginTimeLast) {
		this.loginTimeLast = loginTimeLast;
	}

	/*
	 * @Transient public BigDecimal getReviewStatus() {
	 * if(accountUserApproveAdmins!=null &&
	 * accountUserApproveAdmins.iterator().hasNext()){ return
	 * accountUserApproveAdmins.iterator().next().getReviewStatus(); } return
	 * BigDecimal.ZERO; }
	 */

	@OneToMany(mappedBy = "user")
	public Set<AccountUserApproveAdmin> getAccountUserApproveAdmins() {
		return accountUserApproveAdmins;
	}

	public void setAccountUserApproveAdmins(Set<AccountUserApproveAdmin> accountUserApproveAdmins) {
		this.accountUserApproveAdmins = accountUserApproveAdmins;
	}

	// 身份证认证
	@Transient
	public AccountUserApproveAdmin getCardUserApprove() {
		for (AccountUserApproveAdmin cardUserApprove : accountUserApproveAdmins) {
			if (BigDecimal.ONE.equals(cardUserApprove.getProId())) {
				cardUserApprove.setCardPath(AuditUserTools.getCardPath(1, this));
				return cardUserApprove;
			}
		}
		return null;
	}

	@OneToMany(mappedBy = "accountUser")
	public Set<ExtractNoteAdmin> getExtracNotes() {
		return extracNotes;
	}

	public void setExtracNotes(Set<ExtractNoteAdmin> extracNotes) {
		this.extracNotes = extracNotes;
	}

	// 工作认证
	@Transient
	public AccountUserApproveAdmin getWorkUserApprove() {
		for (AccountUserApproveAdmin workUserApprove : accountUserApproveAdmins) {
			if (workUserApprove.getProId().equals(new BigDecimal(2))) {
				workUserApprove.setCardPath(AuditUserTools.getCardPath(2, this));
				return workUserApprove;
			}
		}
		return null;
	}

	// 收入认证
	@Transient
	public AccountUserApproveAdmin getEarnUserApprove() {
		for (AccountUserApproveAdmin earnUserApprove : accountUserApproveAdmins) {
			earnUserApprove.setCardPath(AuditUserTools.getCardPath(3, this));
			if (earnUserApprove.getProId().equals(new BigDecimal(3))) {
				return earnUserApprove;
			}
		}
		return null;
	}

	// 信用报告认证
	@Transient
	public AccountUserApproveAdmin getCreditReportUserApprove() {
		for (AccountUserApproveAdmin creditReportUserApprove : accountUserApproveAdmins) {
			creditReportUserApprove.setCardPath(AuditUserTools.getCardPath(4, this));
			if (creditReportUserApprove.getProId().equals(new BigDecimal(4))) {
				return creditReportUserApprove;
			}
		}
		return null;
	}

	// 房产认证
	@Transient
	public AccountUserApproveAdmin getHouseUserApprove() {
		for (AccountUserApproveAdmin houseUserApprove : accountUserApproveAdmins) {
			houseUserApprove.setCardPath(AuditUserTools.getCardPath(5, this));
			if (houseUserApprove.getProId().equals(new BigDecimal(5))) {
				return houseUserApprove;
			}
		}
		return null;
	}

	// 技术职称认证
	@Transient
	public AccountUserApproveAdmin getTechUserApprove() {
		for (AccountUserApproveAdmin techUserApprove : accountUserApproveAdmins) {
			techUserApprove.setCardPath(AuditUserTools.getCardPath(6, this));
			if (techUserApprove.getProId().equals(new BigDecimal(6))) {
				return techUserApprove;
			}
		}
		return null;
	}

	// 购车认证
	@Transient
	public AccountUserApproveAdmin getCarUserApprove() {
		for (AccountUserApproveAdmin carUserApprove : accountUserApproveAdmins) {
			carUserApprove.setCardPath(AuditUserTools.getCardPath(7, this));
			if (carUserApprove.getProId().equals(new BigDecimal(7))) {
				return carUserApprove;
			}
		}
		return null;
	}

	// 结婚认证
	@Transient
	public AccountUserApproveAdmin getMarryUserApprove() {
		for (AccountUserApproveAdmin marryUserApprove : accountUserApproveAdmins) {
			marryUserApprove.setCardPath(AuditUserTools.getCardPath(8, this));
			if (marryUserApprove.getProId().equals(new BigDecimal(8))) {
				return marryUserApprove;
			}
		}
		return null;
	}

	// 居住的认证
	@Transient
	public AccountUserApproveAdmin getAddressUserApprove() {
		for (AccountUserApproveAdmin addressUserApprove : accountUserApproveAdmins) {
			addressUserApprove.setCardPath(AuditUserTools.getCardPath(9, this));
			if (addressUserApprove.getProId().equals(new BigDecimal(9))) {
				return addressUserApprove;
			}
		}
		return null;
	}

	// 视频认证
	@Transient
	public AccountUserApproveAdmin getVideoUserApprove() {
		for (AccountUserApproveAdmin videoUserApprove : accountUserApproveAdmins) {
			if (videoUserApprove.getProId().equals(new BigDecimal(10))) {
				return videoUserApprove;
			}
		}
		return null;
	}

	// 实地考察认证
	@Transient
	public AccountUserApproveAdmin getFieldVisitUserApprove() {
		for (AccountUserApproveAdmin fieldVisitUserApprove : accountUserApproveAdmins) {
			if (fieldVisitUserApprove.getProId().equals(new BigDecimal(11))) {
				return fieldVisitUserApprove;
			}
		}
		return null;
	}

	// 学历认证
	@Transient
	public AccountUserApproveAdmin getEducationUserApprove() {
		for (AccountUserApproveAdmin educationUserApprove : accountUserApproveAdmins) {
			if (educationUserApprove.getProId().equals(new BigDecimal(12))) {
				return educationUserApprove;
			}
		}
		return null;
	}

	// 手机实名认证
	@Transient
	public AccountUserApproveAdmin getPhoneUserApprove() {
		for (AccountUserApproveAdmin phoneUserApprove : accountUserApproveAdmins) {
			phoneUserApprove.setCardPath(AuditUserTools.getCardPath(13, this));
			if (phoneUserApprove.getProId().equals(new BigDecimal(13))) {
				return phoneUserApprove;
			}
		}
		return null;
	}

	// 微博认证
	@Transient
	public AccountUserApproveAdmin getBlogUserApprove() {
		for (AccountUserApproveAdmin blogUserApprove : accountUserApproveAdmins) {
			if (blogUserApprove.getProId().equals(new BigDecimal(14))) {
				return blogUserApprove;
			}
		}
		return null;
	}

	@Transient
	public AccountUserApproveAdmin getBasicInfoApprove() {
		for (AccountUserApproveAdmin basicInfoApprove : accountUserApproveAdmins) {
			if (basicInfoApprove.getProId().equals(new BigDecimal(16))) {
				return basicInfoApprove;
			}
		}
		return null;
	}

	@Transient
	public BigDecimal getCreditGradeTotle() {
		int creditGradeTotle = 0;
		for (AccountUserApproveAdmin userApproveAdmin : accountUserApproveAdmins) {
			creditGradeTotle += userApproveAdmin.getCreditScore() == null ? 0L : userApproveAdmin.getCreditScore().intValue();
		}
		return new BigDecimal(creditGradeTotle);
	}

	@Transient
	public String getInclosureSubmitTimeFormat() {
		return inclosureSubmitTime == null ? "" : inclosureSubmitTime.toString().substring(0, 16);
	}

}