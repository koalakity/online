package com.zendaimoney.online.entity.personal;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_MESSAGE_SET")
public class PersonalUserMessageSet implements java.io.Serializable{
	private BigDecimal messagesetId;
	private BigDecimal userId;
	private BigDecimal mannerId;
	private BigDecimal kindId;
	private String description;
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "MESSAGESET_ID", unique = true, nullable = false)
	public BigDecimal getMessagesetId() {
		return messagesetId;
	}

	public void setMessagesetId(BigDecimal messagesetId) {
		this.messagesetId = messagesetId;
	}

	@Column(name = "USER_ID")
	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@Column(name = "MANNER_ID")
	public BigDecimal getMannerId() {
		return mannerId;
	}

	public void setMannerId(BigDecimal mannerId) {
		this.mannerId = mannerId;
	}

	@Column(name = "KIND_ID")
	public BigDecimal getKindId() {
		return kindId;
	}

	public void setKindId(BigDecimal kindId) {
		this.kindId = kindId;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
