package com.zendaimoney.online.entity.message;

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
 * SysMsg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_MSG")
public class MessageSysMsg implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1763421345868527515L;
	private BigDecimal id;
	private MessageUsers userId;
	private MessageMovementWord wordId;
	private String parameter1;
	private String parameter2;
	private String parameter3;
	private String parameter4;
	private String parameter5;
	private String url1;
	private String url2;
	private String url3;
	private String url4;
	private String url5;
	private Date happenTime;
	private String isDel;

	// Constructors

	/** default constructor */
	public MessageSysMsg() {
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

	@OneToOne
	@JoinColumn(name="USER_ID",referencedColumnName="USER_ID")
	public MessageUsers getUserId() {
		return this.userId;
	}

	public void setUserId(MessageUsers userId) {
		this.userId = userId;
	}

	@OneToOne
	@JoinColumn(name="WORD_ID",referencedColumnName="WORD_ID")
	public MessageMovementWord getWordId() {
		return this.wordId;
	}

	public void setWordId(MessageMovementWord wordId) {
		this.wordId = wordId;
	}

	@Column(name = "PARAMETER1", length = 500)
	public String getParameter1() {
		return this.parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	@Column(name = "PARAMETER2", length = 500)
	public String getParameter2() {
		return this.parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}

	@Column(name = "PARAMETER3", length = 500)
	public String getParameter3() {
		return this.parameter3;
	}

	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}

	@Column(name = "PARAMETER4", length = 500)
	public String getParameter4() {
		return this.parameter4;
	}

	public void setParameter4(String parameter4) {
		this.parameter4 = parameter4;
	}

	@Column(name = "PARAMETER5", length = 500)
	public String getParameter5() {
		return this.parameter5;
	}

	public void setParameter5(String parameter5) {
		this.parameter5 = parameter5;
	}

	@Column(name = "URL1", length = 500)
	public String getUrl1() {
		return this.url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	@Column(name = "URL2", length = 500)
	public String getUrl2() {
		return this.url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	@Column(name = "URL3", length = 500)
	public String getUrl3() {
		return this.url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	@Column(name = "URL4", length = 500)
	public String getUrl4() {
		return this.url4;
	}

	public void setUrl4(String url4) {
		this.url4 = url4;
	}

	@Column(name = "URL5", length = 500)
	public String getUrl5() {
		return this.url5;
	}

	public void setUrl5(String url5) {
		this.url5 = url5;
	}

	@Column(name = "HAPPEN_TIME", length = 7)
	public Date getHappenTime() {
		return this.happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}

	@Column(name = "IS_DEL", length = 1)
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

}