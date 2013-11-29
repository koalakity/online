package com.zendaimoney.online.entity.pay;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

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
public class Users implements java.io.Serializable {

	// Fields

	private BigDecimal userId;
	private String loginName;
	private String loginPassword;
	private String email;
	private BigDecimal isapproveEmail;
	private BigDecimal isapprovePhone;
	private BigDecimal isapproveCard;
	private String regIp;
	private String regTime;
	private BigDecimal loginCount;
	private BigDecimal loginStatus;
	private String loginTimeLast;
	private String loginIpLast;
	private BigDecimal statusView;
	private BigDecimal regType;
	private BigDecimal TCustomerId;
	private BigDecimal userStatus;
	private BigDecimal  delStatus;



	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(BigDecimal userId, String loginName, String loginPassword) {
		this.userId = userId;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
	}
	public BigDecimal getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(BigDecimal delStatus) {
		this.delStatus = delStatus;
	}
	/** full constructor */
	public Users(BigDecimal userId, String loginName, String loginPassword,
			String email, BigDecimal isapproveEmail, BigDecimal isapprovePhone,
			BigDecimal isapproveCard, String regIp, String regTime,
			BigDecimal loginCount, BigDecimal loginStatus,
			String loginTimeLast, String loginIpLast, BigDecimal statusView,
			BigDecimal regType, BigDecimal TCustomerId) {
		this.userId = userId;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.email = email;
		this.isapproveEmail = isapproveEmail;
		this.isapprovePhone = isapprovePhone;
		this.isapproveCard = isapproveCard;
		this.regIp = regIp;
		this.regTime = regTime;
		this.loginCount = loginCount;
		this.loginStatus = loginStatus;
		this.loginTimeLast = loginTimeLast;
		this.loginIpLast = loginIpLast;
		this.statusView = statusView;
		this.regType = regType;
		this.TCustomerId = TCustomerId;
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
	public String getRegTime() {
		return this.regTime;
	}

	public void setRegTime(String regTime) {
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
	public String getLoginTimeLast() {
		return this.loginTimeLast;
	}

	public void setLoginTimeLast(String loginTimeLast) {
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
	// Constructors
	@Column(name = "USER_STATUS", precision = 22, scale = 0)
	public BigDecimal getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(BigDecimal userStatus) {
		this.userStatus = userStatus;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Users))
			return false;
		Users castOther = (Users) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(
				castOther.getUserId())))
				&& ((this.getLoginName() == castOther.getLoginName()) || (this
						.getLoginName() != null
						&& castOther.getLoginName() != null && this
						.getLoginName().equals(castOther.getLoginName())))
				&& ((this.getLoginPassword() == castOther.getLoginPassword()) || (this
						.getLoginPassword() != null
						&& castOther.getLoginPassword() != null && this
						.getLoginPassword()
						.equals(castOther.getLoginPassword())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getIsapproveEmail() == castOther.getIsapproveEmail()) || (this
						.getIsapproveEmail() != null
						&& castOther.getIsapproveEmail() != null && this
						.getIsapproveEmail().equals(
								castOther.getIsapproveEmail())))
				&& ((this.getIsapprovePhone() == castOther.getIsapprovePhone()) || (this
						.getIsapprovePhone() != null
						&& castOther.getIsapprovePhone() != null && this
						.getIsapprovePhone().equals(
								castOther.getIsapprovePhone())))
				&& ((this.getIsapproveCard() == castOther.getIsapproveCard()) || (this
						.getIsapproveCard() != null
						&& castOther.getIsapproveCard() != null && this
						.getIsapproveCard()
						.equals(castOther.getIsapproveCard())))
				&& ((this.getRegIp() == castOther.getRegIp()) || (this
						.getRegIp() != null
						&& castOther.getRegIp() != null && this.getRegIp()
						.equals(castOther.getRegIp())))
				&& ((this.getRegTime() == castOther.getRegTime()) || (this
						.getRegTime() != null
						&& castOther.getRegTime() != null && this.getRegTime()
						.equals(castOther.getRegTime())))
				&& ((this.getLoginCount() == castOther.getLoginCount()) || (this
						.getLoginCount() != null
						&& castOther.getLoginCount() != null && this
						.getLoginCount().equals(castOther.getLoginCount())))
				&& ((this.getLoginStatus() == castOther.getLoginStatus()) || (this
						.getLoginStatus() != null
						&& castOther.getLoginStatus() != null && this
						.getLoginStatus().equals(castOther.getLoginStatus())))
				&& ((this.getLoginTimeLast() == castOther.getLoginTimeLast()) || (this
						.getLoginTimeLast() != null
						&& castOther.getLoginTimeLast() != null && this
						.getLoginTimeLast()
						.equals(castOther.getLoginTimeLast())))
				&& ((this.getLoginIpLast() == castOther.getLoginIpLast()) || (this
						.getLoginIpLast() != null
						&& castOther.getLoginIpLast() != null && this
						.getLoginIpLast().equals(castOther.getLoginIpLast())))
				&& ((this.getStatusView() == castOther.getStatusView()) || (this
						.getStatusView() != null
						&& castOther.getStatusView() != null && this
						.getStatusView().equals(castOther.getStatusView())))
				&& ((this.getRegType() == castOther.getRegType()) || (this
						.getRegType() != null
						&& castOther.getRegType() != null && this.getRegType()
						.equals(castOther.getRegType())))
				&& ((this.getTCustomerId() == castOther.getTCustomerId()) || (this
						.getTCustomerId() != null
						&& castOther.getTCustomerId() != null && this
						.getTCustomerId().equals(castOther.getTCustomerId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getLoginName() == null ? 0 : this.getLoginName().hashCode());
		result = 37
				* result
				+ (getLoginPassword() == null ? 0 : this.getLoginPassword()
						.hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37
				* result
				+ (getIsapproveEmail() == null ? 0 : this.getIsapproveEmail()
						.hashCode());
		result = 37
				* result
				+ (getIsapprovePhone() == null ? 0 : this.getIsapprovePhone()
						.hashCode());
		result = 37
				* result
				+ (getIsapproveCard() == null ? 0 : this.getIsapproveCard()
						.hashCode());
		result = 37 * result
				+ (getRegIp() == null ? 0 : this.getRegIp().hashCode());
		result = 37 * result
				+ (getRegTime() == null ? 0 : this.getRegTime().hashCode());
		result = 37
				* result
				+ (getLoginCount() == null ? 0 : this.getLoginCount()
						.hashCode());
		result = 37
				* result
				+ (getLoginStatus() == null ? 0 : this.getLoginStatus()
						.hashCode());
		result = 37
				* result
				+ (getLoginTimeLast() == null ? 0 : this.getLoginTimeLast()
						.hashCode());
		result = 37
				* result
				+ (getLoginIpLast() == null ? 0 : this.getLoginIpLast()
						.hashCode());
		result = 37
				* result
				+ (getStatusView() == null ? 0 : this.getStatusView()
						.hashCode());
		result = 37 * result
				+ (getRegType() == null ? 0 : this.getRegType().hashCode());
		result = 37
				* result
				+ (getTCustomerId() == null ? 0 : this.getTCustomerId()
						.hashCode());
		return result;
	}

}