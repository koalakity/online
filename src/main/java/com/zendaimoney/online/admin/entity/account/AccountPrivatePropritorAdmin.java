package com.zendaimoney.online.admin.entity.account;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;




/**
 * Copyright (c) 2011 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-10-19 下午8:25:55
 * operation by:
 * description:私营业主资料实体
 */

@Entity
@Table(name = "PRIVATE_PROPRIETOR")
public class AccountPrivatePropritorAdmin implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4232034717739911469L;
	private BigDecimal id;
	private BigDecimal userId;
	private AccountUsersAdmin user;
	private String enterpriseName;
	private String enterpriseKind;
	@DateTimeFormat(iso = ISO.DATE)
	private Date establishDate;
	private String enterpriseAddress;
	private BigDecimal rent;
	private String lease;
	private BigDecimal taxationNo;
	private BigDecimal gsBookNo;
	private BigDecimal profitOrLossAmount;
	private BigDecimal employeeAmount;
	private String remarks;

	// Constructors

	/** default constructor */
	public AccountPrivatePropritorAdmin() {
	}

	/** full constructor */
	public AccountPrivatePropritorAdmin(String enterpriseName,
			String enterpriseKind, Timestamp establishDate,
			String enterpriseAddress, BigDecimal rent, String lease,
			BigDecimal taxationNo, BigDecimal gsBookNo,
			BigDecimal profitOrLossAmount, BigDecimal employeeAmount,
			String remarks) {
		this.enterpriseName = enterpriseName;
		this.enterpriseKind = enterpriseKind;
		this.establishDate = establishDate;
		this.enterpriseAddress = enterpriseAddress;
		this.rent = rent;
		this.lease = lease;
		this.taxationNo = taxationNo;
		this.gsBookNo = gsBookNo;
		this.profitOrLossAmount = profitOrLossAmount;
		this.employeeAmount = employeeAmount;
		this.remarks = remarks;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)       
	@OneToOne      
	public AccountUsersAdmin getUser() {
		return user;
	}

	public void setUser(AccountUsersAdmin user) {
		this.user = user;
	}

	
	@Column(name = "USER_ID", length = 22)
	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "ENTERPRISE_NAME", length = 100)
	public String getEnterpriseName() {
		return this.enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	@Column(name = "ENTERPRISE_KIND", length = 2)
	public String getEnterpriseKind() {
		return this.enterpriseKind;
	}

	public void setEnterpriseKind(String enterpriseKind) {
		this.enterpriseKind = enterpriseKind;
	}

	@Column(name = "ESTABLISH_DATE", length = 11)
	public Date getEstablishDate() {
		return this.establishDate;
	}
	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}



	@Column(name = "ENTERPRISE_ADDRESS", length = 2)
	public String getEnterpriseAddress() {
		return this.enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	@Column(name = "RENT", precision = 22, scale = 0)
	public BigDecimal getRent() {
		return this.rent;
	}

	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}

	@Column(name = "LEASE", length = 4)
	public String getLease() {
		return this.lease;
	}

	public void setLease(String lease) {
		this.lease = lease;
	}

	@Column(name = "TAXATION_NO", precision = 22, scale = 0)
	public BigDecimal getTaxationNo() {
		return this.taxationNo;
	}

	public void setTaxationNo(BigDecimal taxationNo) {
		this.taxationNo = taxationNo;
	}

	@Column(name = "GS_BOOK_NO", precision = 22, scale = 0)
	public BigDecimal getGsBookNo() {
		return this.gsBookNo;
	}

	public void setGsBookNo(BigDecimal gsBookNo) {
		this.gsBookNo = gsBookNo;
	}

	@Column(name = "PROFIT_OR_LOSS_AMOUNT", precision = 22, scale = 0)
	public BigDecimal getProfitOrLossAmount() {
		return this.profitOrLossAmount;
	}

	public void setProfitOrLossAmount(BigDecimal profitOrLossAmount) {
		this.profitOrLossAmount = profitOrLossAmount;
	}

	@Column(name = "EMPLOYEE_AMOUNT", precision = 22, scale = 0)
	public BigDecimal getEmployeeAmount() {
		return this.employeeAmount;
	}

	public void setEmployeeAmount(BigDecimal employeeAmount) {
		this.employeeAmount = employeeAmount;
	}

	@Column(name = "REMARKS", length = 200)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
