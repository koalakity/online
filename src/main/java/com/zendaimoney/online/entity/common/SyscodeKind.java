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
 * SyscodeKind entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYSCODE_KIND")
public class SyscodeKind implements java.io.Serializable {

	// Fields

	private BigDecimal codekindId;
	private String kindName;
	private String description;

	// Constructors

	/** default constructor */
	public SyscodeKind() {
	}

	/** full constructor */
	public SyscodeKind(String kindName, BigDecimal userId, String createtime,
			String description) {
		this.kindName = kindName;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "CODEKIND_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCodekindId() {
		return this.codekindId;
	}

	public void setCodekindId(BigDecimal codekindId) {
		this.codekindId = codekindId;
	}

	@Column(name = "KIND_NAME", length = 60)
	public String getKindName() {
		return this.kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	
	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}