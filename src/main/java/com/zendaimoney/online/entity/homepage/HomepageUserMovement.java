package com.zendaimoney.online.entity.homepage;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserMovement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_MOVEMENT")
public class HomepageUserMovement implements java.io.Serializable {

	// Fields

	private BigDecimal movementId;
	private BigDecimal userId;
	private HomepageMovementWord wordId;
	// private BigDecimal wordId;
	private String parameter1;
	private String parameter2;
	private String url1;
	private String url2;
	private Date happenTime;
	private String spareParameter;
	private String spareUrl;
	private String isDel;
	private String msgKind;

	// Constructors

	/** default constructor */
	public HomepageUserMovement() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "USER_MOVEMENT_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "MOVEMENT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getMovementId() {
		return this.movementId;
	}

	public void setMovementId(BigDecimal movementId) {
		this.movementId = movementId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@OneToOne
	@JoinColumn(name = "WORD_ID", referencedColumnName = "WORD_ID")
	public HomepageMovementWord getWordId() {
		return this.wordId;
	}

	public void setWordId(HomepageMovementWord wordId) {
		this.wordId = wordId;
	}

	/*
	 * @Column(name = "WORD_ID", precision = 22, scale = 0) public BigDecimal
	 * getWordId() { return wordId; }
	 * 
	 * public void setWordId(BigDecimal wordId) { this.wordId = wordId; }
	 */

	@Column(name = "PARAMETER1", length = 100)
	public String getParameter1() {
		return this.parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	@Column(name = "PARAMETER2", length = 100)
	public String getParameter2() {
		return this.parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}

	@Column(name = "URL1", length = 200)
	public String getUrl1() {
		return this.url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	@Column(name = "URL2", length = 200)
	public String getUrl2() {
		return this.url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	@Column(name = "HAPPEN_TIME")
	public Date getHappenTime() {
		return this.happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}

	@Column(name = "SPARE_PARAMETER", length = 100)
	public String getSpareParameter() {
		return this.spareParameter;
	}

	public void setSpareParameter(String spareParameter) {
		this.spareParameter = spareParameter;
	}

	@Column(name = "SPARE_URL", length = 200)
	public String getSpareUrl() {
		return this.spareUrl;
	}

	public void setSpareUrl(String spareUrl) {
		this.spareUrl = spareUrl;
	}

	@Column(name = "IS_DEL", length = 1)
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Column(name = "MSG_KIND", length = 2)
	public String getMsgKind() {
		return msgKind;
	}

	public void setMsgKind(String msgKind) {
		this.msgKind = msgKind;
	}

}