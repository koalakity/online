package com.zendaimoney.online.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserApprovePro entity. @author MyEclipse Persistence Tools
 */
@Entity(name="UserApproveProVO")
@Table(name = "USER_APPROVE_PRO")
public class UserApproveProVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1053225325331074814L;
	private Long proId;
	private String proName;

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="USERAPPROVEPRO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "PRO_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getProId() {
		return this.proId;
	}

	public void setProId(Long proId) {
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