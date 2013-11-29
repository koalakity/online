package com.zendaimoney.online.entity.stationMessage;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserApprovePro entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_APPROVE_PRO")
public class StationMessageUserApprovePro implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5717443449725655478L;
	private BigDecimal proId;
	private String proName;

	// Constructors

	/** default constructor */
	public StationMessageUserApprovePro() {
	}

	/** full constructor */
	public StationMessageUserApprovePro(String proName) {
		this.proName = proName;
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName="USERAPPROVEPRO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "PRO_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getProId() {
		return this.proId;
	}

	public void setProId(BigDecimal proId) {
		this.proId = proId;
	}

	@Column(name = "PRO_NAME", nullable = false, length = 120)
	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

}