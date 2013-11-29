package com.zendaimoney.online.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ExtractNote entity. @author MyEclipse Persistence Tools
 */
@Entity(name="ExtractNoteVO")
@Table(name = "EXTRACT_NOTE")
public class ExtractNoteVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8381442094447719865L;
	private Long extractId;
	private Long userId;
	private BigDecimal extractAmount;
	private BigDecimal extractCost;
	private BigDecimal realAmount;
	private String kaihuName;
	private String bankName;
	private String bankCardNo;
	private Date extractTime;
	private BigDecimal sysStatus;
	//0审核中,1处理中,2失败,3成功
	private BigDecimal verifyStatus;
	private String description;

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="EXTRACTNOTE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "EXTRACT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getExtractId() {
		return this.extractId;
	}

	public void setExtractId(Long extractId) {
		this.extractId = extractId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "EXTRACT_AMOUNT", nullable = false, precision = 126, scale = 0)
	public BigDecimal getExtractAmount() {
		return this.extractAmount;
	}

	public void setExtractAmount(BigDecimal extractAmount) {
		this.extractAmount = extractAmount;
	}

	@Column(name = "EXTRACT_COST", nullable = false, precision = 126, scale = 0)
	public BigDecimal getExtractCost() {
		return this.extractCost;
	}

	public void setExtractCost(BigDecimal extractCost) {
		this.extractCost = extractCost;
	}

	@Column(name = "REAL_AMOUNT", nullable = false, precision = 126, scale = 0)
	public BigDecimal getRealAmount() {
		return this.realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
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

	@Column(name = "EXTRACT_TIME", nullable = false)
	public Date getExtractTime() {
		return this.extractTime;
	}

	public void setExtractTime(Date extractTime) {
		this.extractTime = extractTime;
	}

	@Column(name = "SYS_STATUS", precision = 22, scale = 0)
	public BigDecimal getSysStatus() {
		return this.sysStatus;
	}

	public void setSysStatus(BigDecimal sysStatus) {
		this.sysStatus = sysStatus;
	}

	@Column(name = "VERIFY_STATUS", precision = 22, scale = 0)
	public BigDecimal getVerifyStatus() {
		return this.verifyStatus;
	}

	public void setVerifyStatus(BigDecimal verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}