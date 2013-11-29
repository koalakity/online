package com.zendaimoney.online.entity.homepage;

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
 * InvestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INVEST_INFO")
public class HomepageInvestInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4091744891972034260L;
	private BigDecimal investId;
	private BigDecimal loanId;
	private BigDecimal userId;
	private Double investAmount;
	private Double havaScale;
	private Date investTime;
	private String description;
	private String status;

	// private HomepageLoanInfo loanInfo;

	// Constructors

	/** default constructor */
	public HomepageInvestInfo() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "INVESTINFO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "INVEST_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getInvestId() {
		return this.investId;
	}

	public void setInvestId(BigDecimal investId) {
		this.investId = investId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "LOAN_ID")
	public BigDecimal getLoanId() {
		return loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@Column(name = "INVEST_AMOUNT", nullable = false, precision = 22, scale = 7)
	public Double getInvestAmount() {
		return this.investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	@Column(name = "HAVA_SCALE", nullable = false, precision = 22, scale = 18)
	public Double getHavaScale() {
		return this.havaScale;
	}

	public void setHavaScale(Double havaScale) {
		this.havaScale = havaScale;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVEST_TIME", nullable = false, length = 7)
	public Date getInvestTime() {
		return this.investTime;
	}

	public void setInvestTime(Timestamp investTime) {
		this.investTime = investTime;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// @ManyToOne(cascade=CascadeType.ALL)
	// @JoinColumn(name="LOAN_ID")
	// public HomepageLoanInfo getLoanInfo() {
	// return loanInfo;
	// }
	//
	// public void setLoanInfo(HomepageLoanInfo loanInfo) {
	// this.loanInfo = loanInfo;
	// }

}