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
 * ExtractNote entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "EXTRACT_NOTE")
public class HomepageExtractNote implements java.io.Serializable {

	// Fields

	private BigDecimal extractId;
	private BigDecimal userId;
	private Double extractAmount;
	private Double extractCost;
	private Double realAmount;
	private String kaihuName;
	private String bankName;
	private String bankCardNo;
	private Date extractTime;
	private BigDecimal status;
	private String description;

	// Constructors

	/** default constructor */
	public HomepageExtractNote() {
	}

	/** minimal constructor */
	public HomepageExtractNote(BigDecimal userId, Double extractAmount,
			Double extractCost, Double realAmount, String kaihuName,
			String bankName, String bankCardNo, Date extractTime) {
		this.userId = userId;
		this.extractAmount = extractAmount;
		this.extractCost = extractCost;
		this.realAmount = realAmount;
		this.kaihuName = kaihuName;
		this.bankName = bankName;
		this.bankCardNo = bankCardNo;
		this.extractTime = extractTime;
	}

	/** full constructor */
	public HomepageExtractNote(BigDecimal userId, Double extractAmount,
			Double extractCost, Double realAmount, String kaihuName,
			String bankName, String bankCardNo, Date extractTime,
			BigDecimal status, String description) {
		this.userId = userId;
		this.extractAmount = extractAmount;
		this.extractCost = extractCost;
		this.realAmount = realAmount;
		this.kaihuName = kaihuName;
		this.bankName = bankName;
		this.bankCardNo = bankCardNo;
		this.extractTime = extractTime;
		this.status = status;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "EXTRACT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getExtractId() {
		return this.extractId;
	}

	public void setExtractId(BigDecimal extractId) {
		this.extractId = extractId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "EXTRACT_AMOUNT", nullable = false, precision = 126, scale = 0)
	public Double getExtractAmount() {
		return this.extractAmount;
	}

	public void setExtractAmount(Double extractAmount) {
		this.extractAmount = extractAmount;
	}

	@Column(name = "EXTRACT_COST", nullable = false, precision = 126, scale = 0)
	public Double getExtractCost() {
		return this.extractCost;
	}

	public void setExtractCost(Double extractCost) {
		this.extractCost = extractCost;
	}

	@Column(name = "REAL_AMOUNT", nullable = false, precision = 126, scale = 0)
	public Double getRealAmount() {
		return this.realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	@Column(name = "KAIHU_NAME", nullable = false, length = 50)
	public String getKaihuName() {
		return this.kaihuName;
	}

	public void setKaihuName(String kaihuName) {
		this.kaihuName = kaihuName;
	}

	@Column(name = "BANK_NAME", nullable = false, length = 100)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_CARD_NO", nullable = false, length = 40)
	public String getBankCardNo() {
		return this.bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXTRACT_TIME", nullable = false, length = 7)
	public Date getExtractTime() {
		return this.extractTime;
	}

	public void setExtractTime(Timestamp extractTime) {
		this.extractTime = extractTime;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}