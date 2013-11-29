package com.zendaimoney.online.entity.personal;

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

import com.zendaimoney.online.admin.entity.ChannelInfoVO;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERS")
public class PersonalUsers implements java.io.Serializable {

	// 1000 1000001
	// 2000 2000001

	private BigDecimal userId;
	private String loginName;
	private String loginPassword;
	private String email;
	private BigDecimal isapproveEmail;
	private BigDecimal isapprovePhone;
	private BigDecimal isapproveCard;
	private String regIp;
	private Date regTime;
	private BigDecimal loginCount;
	private BigDecimal loginStatus;
	private Date loginTimeLast;
	private String loginIpLast;
	private BigDecimal statusView;
	private BigDecimal regType;
	private BigDecimal TCustomerId;
	private BigDecimal userStatus;
	private BigDecimal lockStatus;
	private BigDecimal returnStatus;
	private BigDecimal delStatus;
	private String emailacCode;
	
	private Date inclosureSubmitTime;
	private ChannelInfoVO channelInfo;

	// Constructors

	/** default constructor */
	public PersonalUsers() {
	}



	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName="USERS_SEQ")
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

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ISAPPROVE_EMAIL", precision = 22, scale = 0)
	public BigDecimal getIsapproveEmail() {
		return this.isapproveEmail;
	}

	public void setIsapproveEmail(BigDecimal isapproveEmail) {
		this.isapproveEmail = isapproveEmail;
	}

	@Column(name = "ISAPPROVE_PHONE", precision = 22, scale = 0)
	public BigDecimal getIsapprovePhone() {
		return this.isapprovePhone;
	}

	public void setIsapprovePhone(BigDecimal isapprovePhone) {
		this.isapprovePhone = isapprovePhone;
	}

	@Column(name = "ISAPPROVE_CARD", precision = 22, scale = 0)
	public BigDecimal getIsapproveCard() {
		return this.isapproveCard;
	}

	public void setIsapproveCard(BigDecimal isapproveCard) {
		this.isapproveCard = isapproveCard;
	}

	@Column(name = "REG_IP", length = 40)
	public String getRegIp() {
		return this.regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	@Column(name = "REG_TIME")
	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column(name = "LOGIN_COUNT", precision = 22, scale = 0)
	public BigDecimal getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(BigDecimal loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "LOGIN_STATUS", precision = 22, scale = 0)
	public BigDecimal getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(BigDecimal loginStatus) {
		this.loginStatus = loginStatus;
	}

	@Column(name = "LOGIN_TIME_LAST")
	public Date getLoginTimeLast() {
		return this.loginTimeLast;
	}

	public void setLoginTimeLast(Date loginTimeLast) {
		this.loginTimeLast = loginTimeLast;
	}

	@Column(name = "LOGIN_IP_LAST", length = 50)
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

	@Column(name = "T_CUSTOMER_ID", precision = 22, scale = 0)
	public BigDecimal getTCustomerId() {
		return this.TCustomerId;
	}

	public void setTCustomerId(BigDecimal TCustomerId) {
		this.TCustomerId = TCustomerId;
	}

	@Column(name = "USER_STATUS", precision = 22, scale = 0)
	public BigDecimal getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(BigDecimal userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "LOCK_STATUS", precision = 22, scale = 0)
	public BigDecimal getLockStatus() {
		return this.lockStatus;
	}

	public void setLockStatus(BigDecimal lockStatus) {
		this.lockStatus = lockStatus;
	}

	@Column(name = "RETURN_STATUS", precision = 22, scale = 0)
	public BigDecimal getReturnStatus() {
		return this.returnStatus;
	}

	public void setReturnStatus(BigDecimal returnStatus) {
		this.returnStatus = returnStatus;
	}

	public BigDecimal getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(BigDecimal delStatus) {
		this.delStatus = delStatus;
	}

	@Column(name = "EMAILAC_CODE", length = 10)
	public String getEmailacCode() {
		return this.emailacCode;
	}

	public void setEmailacCode(String emailacCode) {
		this.emailacCode = emailacCode;
	}

	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CHANNELINFO_ID")
	public ChannelInfoVO getChannelInfo() {
		return channelInfo;
	}

	public void setChannelInfo(ChannelInfoVO channelInfo) {
		this.channelInfo = channelInfo;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "INCLOSURE_SUBMIT_TIME", length = 7)
	public Date getInclosureSubmitTime() {
		return this.inclosureSubmitTime;
	}

	public void setInclosureSubmitTime(Date inclosureSubmitTime) {
		this.inclosureSubmitTime = inclosureSubmitTime;
	}

}