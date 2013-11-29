package com.zendaimoney.online.entity.loanManagement;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * WebOperateLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WEB_OPERATE_LOG")
public class LoanManagementWebOperateLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2999751546370310094L;
	private BigDecimal logId;
	private BigDecimal operateId;
	private String operateContext;
	private String operateTime;
	private String loginIp;
	private String description;

	// Constructors

	/** default constructor */
	public LoanManagementWebOperateLog() {
	}

	/** full constructor */
	public LoanManagementWebOperateLog(BigDecimal operateId, String operateContext,
			String operateTime, String loginIp, String description) {
		this.operateId = operateId;
		this.operateContext = operateContext;
		this.operateTime = operateTime;
		this.loginIp = loginIp;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName = "WEBOPERATELOG_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLogId() {
		return this.logId;
	}

	public void setLogId(BigDecimal logId) {
		this.logId = logId;
	}

	@Column(name = "OPERATE_ID", precision = 22, scale = 0)
	public BigDecimal getOperateId() {
		return this.operateId;
	}

	public void setOperateId(BigDecimal operateId) {
		this.operateId = operateId;
	}

	@Column(name = "OPERATE_CONTEXT", length = 200)
	public String getOperateContext() {
		return this.operateContext;
	}

	public void setOperateContext(String operateContext) {
		this.operateContext = operateContext;
	}

	@Column(name = "OPERATE_TIME")
	public String getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "LOGIN_IP", length = 50)
	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}