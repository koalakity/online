package com.zendaimoney.online.entity.personal;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * PrivateProprietor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PRIVATE_PROPRIETOR")
public class PersonalPrivateProprietor implements java.io.Serializable {

	private static final long serialVersionUID = 6874627876447927279L;
	private BigDecimal id;
	private BigDecimal userId;
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
	public PersonalPrivateProprietor() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "PRIVATE_PROPRIETOR_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
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