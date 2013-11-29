package com.zendaimoney.online.admin.entity.account;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Userloginlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERLOGINLOG")
public class AccountUserloginlogAdmin implements java.io.Serializable {

	// Fields

	private BigDecimal ulogId;
	private BigDecimal userId;
	private Date loginTime;
	private String loginIp;
	private BigDecimal loginStatus;

	// Constructors

	/** default constructor */
	public AccountUserloginlogAdmin() {
	}

	/** full constructor */
	public AccountUserloginlogAdmin(BigDecimal userId, Date loginTime, String loginIp,
			BigDecimal loginStatu) {
		this.userId = userId;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.loginStatus = loginStatus;
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName="USERLOGINLOG_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ULOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getUlogId() {
		return this.ulogId;
	}

	public void setUlogId(BigDecimal ulogId) {
		this.ulogId = ulogId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_TIME", length = 7)
	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "LOGIN_IP", length = 50)
	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "LOGIN_STATUS", precision = 22, scale = 0)
	public BigDecimal getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(BigDecimal loginStatus) {
		this.loginStatus = loginStatus;
	}

}