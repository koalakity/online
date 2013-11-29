package com.zendaimoney.online.entity.financial;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zendaimoney.online.admin.entity.account.ID5Check;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERS")
public class FinancialUsers implements java.io.Serializable {

	// Fields

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
	private BigDecimal userStatus;
	private BigDecimal delStatus;
	private ID5Check id5Check;// ID5认证表

	private FinancialUserCreditNote userCreditNote;
	private FinancialAcTCustomer acTCustomer;
	private FinancialUserInfoPerson userInfoPerson;
	private Long channelInfoId;

	public BigDecimal getDelStatus() {
		return delStatus;
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

	public void setDelStatus(BigDecimal delStatus) {
		this.delStatus = delStatus;
	}

	// Constructors

	/** default constructor */
	public FinancialUsers() {
	}

	/** minimal constructor */
	public FinancialUsers(String loginName, String loginPassword, String email, BigDecimal isapproveEmail, String regIp, Date regTime, BigDecimal loginCount, BigDecimal loginStatus, Date loginTimeLast, String loginIpLast) {
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.email = email;
		this.isapproveEmail = isapproveEmail;
		this.regIp = regIp;
		this.regTime = regTime;
		this.loginCount = loginCount;
		this.loginStatus = loginStatus;
		this.loginTimeLast = loginTimeLast;
		this.loginIpLast = loginIpLast;
	}

	/** full constructor */
	public FinancialUsers(String loginName, String loginPassword, String email, BigDecimal isapproveEmail, String regIp, Date regTime, BigDecimal loginCount, BigDecimal loginStatus, Date loginTimeLast, String loginIpLast, BigDecimal statusView, BigDecimal regType, BigDecimal isApprovePhone, BigDecimal isApproveCard) {
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.email = email;
		this.isapproveEmail = isapproveEmail;
		this.regIp = regIp;
		this.regTime = regTime;
		this.loginCount = loginCount;
		this.loginStatus = loginStatus;
		this.loginTimeLast = loginTimeLast;
		this.loginIpLast = loginIpLast;
		this.statusView = statusView;
		this.regType = regType;
		this.isApprovePhone = isApprovePhone;
		this.isApproveCard = isApproveCard;
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

	@Column(name = "REG_TIME", nullable = false, length = 7)
	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
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

	@Column(name = "LOGIN_TIME_LAST", nullable = false, length = 7)
	public Date getLoginTimeLast() {
		return this.loginTimeLast;
	}

	public void setLoginTimeLast(Date loginTimeLast) {
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

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
	public FinancialUserCreditNote getUserCreditNote() {
		return userCreditNote;
	}

	public void setUserCreditNote(FinancialUserCreditNote userCreditNote) {
		this.userCreditNote = userCreditNote;
	}

	@Column(name = "USER_STATUS")
	public BigDecimal getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(BigDecimal userStatus) {
		this.userStatus = userStatus;
	}

	@OneToOne
	@JoinColumn(name = "T_CUSTOMER_ID", referencedColumnName = "ID")
	public FinancialAcTCustomer getAcTCustomer() {
		return acTCustomer;
	}

	public void setAcTCustomer(FinancialAcTCustomer acTCustomer) {
		this.acTCustomer = acTCustomer;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	public FinancialUserInfoPerson getUserInfoPerson() {
		return userInfoPerson;
	}

	public void setUserInfoPerson(FinancialUserInfoPerson userInfoPerson) {
		this.userInfoPerson = userInfoPerson;
	}

	@Column(name = "CHANNELINFO_ID")
	public Long getChannelInfoId() {
		return channelInfoId;
	}

	public void setChannelInfoId(Long channelInfoId) {
		this.channelInfoId = channelInfoId;
	}

}