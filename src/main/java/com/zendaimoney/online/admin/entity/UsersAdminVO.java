package com.zendaimoney.online.admin.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERS")
public class UsersAdminVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2066519516630808311L;
	private Long userId;
	private String loginName;
	private String loginPassword;
	private String email;
	private Long isapproveEmail;
	private String regIp;
	private Date regTime;
	private Long loginCount;
	private Long loginStatus;
	private Date loginTimeLast;
	private String loginIpLast;
	private Long statusView;
	private Long regType;
	private Long  isApprovePhone;
	private Long  isApproveCard;
	private Long userStatus;
	private Long  delStatus;
	private Long tCustomerId;
	public Long getDelStatus() {
		return delStatus;
	}
	
	public void setDelStatus(Long delStatus) {
		this.delStatus = delStatus;
	}
	// Constructors
	
    /** default constructor */
	public UsersAdminVO() {
	}

	/** minimal constructor */
	public UsersAdminVO(String loginName, String loginPassword, String email,
			Long isapproveEmail, String regIp, Date regTime,
			Long loginCount, Long loginStatus, Date loginTimeLast,
			String loginIpLast) {
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
	public UsersAdminVO(String loginName, String loginPassword, String email,
			Long isapproveEmail, String regIp, Date regTime,
			Long loginCount, Long loginStatus, Date loginTimeLast,
			String loginIpLast, Long statusView, Long regType,Long isApprovePhone,Long isApproveCard) {
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
	@SequenceGenerator(name = "generator", sequenceName="USERS_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
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
	public Long getIsapproveEmail() {
		return this.isapproveEmail;
	}

	public void setIsapproveEmail(Long isapproveEmail) {
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
	public Long getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "LOGIN_STATUS", nullable = false, precision = 22, scale = 0)
	public Long getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(Long loginStatus) {
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
	public Long getStatusView() {
		return this.statusView;
	}

	public void setStatusView(Long statusView) {
		this.statusView = statusView;
	}

	@Column(name = "REG_TYPE", precision = 22, scale = 0)
	public Long getRegType() {
		return this.regType;
	}

	public void setRegType(Long regType) {
		this.regType = regType;
	}
	

	@Column(name = "ISAPPROVE_PHONE" , precision = 22, scale = 0)
	public Long getIsApprovePhone() {
		return isApprovePhone;
	}

	public void setIsApprovePhone(Long isApprovePhone) {
		this.isApprovePhone = isApprovePhone;
	}

	@Column(name = "ISAPPROVE_CARD" , precision = 22, scale = 0)
	public Long getIsApproveCard() {
		return isApproveCard;
	}

	public void setIsApproveCard(Long isApproveCard) {
		this.isApproveCard = isApproveCard;
	}

	@Column(name = "USER_STATUS")
	public Long getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Long userStatus) {
		this.userStatus = userStatus;
	}
	@Column(name = "T_CUSTOMER_ID")
	public Long gettCustomerId() {
		return tCustomerId;
	}

	public void settCustomerId(Long tCustomerId) {
		this.tCustomerId = tCustomerId;
	}
	
}
