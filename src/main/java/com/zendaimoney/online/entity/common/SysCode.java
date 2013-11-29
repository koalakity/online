package com.zendaimoney.online.entity.common;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * SysCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_CODE")
public class SysCode implements java.io.Serializable {

	// Fields

	private BigDecimal codeId;
	private SyscodeKind syscodeKind;
	private String codeName;
	private String description;

	// Constructors

	/** default constructor */
	public SysCode() {
	}

	/** full constructor */
	public SysCode(String codeName,
			BigDecimal isapprave, String creater, String description,
			String createtime) {
		this.codeName = codeName;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "CODE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCodeId() {
		return this.codeId;
	}

	public void setCodeId(BigDecimal codeId) {
		this.codeId = codeId;
	}

	@Column(name = "CODE_NAME", length = 100)
	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}