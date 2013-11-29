package com.zendaimoney.online.entity.task;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "REPAYING_PHONE_MESSAGE")
public class RepayingPhoneMessage {
	
	private Long ID;
	private BigDecimal userId;
	private int type;
	private Date reDate;
	private String detail;
	private String detail2;
	private int isSend;
	
	
	
	@SequenceGenerator(name = "generator", sequenceName = "REPAYINGPHONEMESSAGE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	@Column(name = "USER_ID", nullable = false, length = 22)
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	
	@Column(name = "TYPE", nullable = false, length = 22)
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Column(name = "REDATE", nullable = false)
	public Date getReDate() {
		return reDate;
	}
	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}
	@Column(name = "DETAIL", nullable = false)
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Column(name = "DETAIL2", nullable = false)
	public String getDetail2() {
		return detail2;
	}
	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}
	@Column(name = "IS_SEND", nullable = false, length = 22)
	public int getIsSend() {
		return isSend;
	}
	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	
	

}
