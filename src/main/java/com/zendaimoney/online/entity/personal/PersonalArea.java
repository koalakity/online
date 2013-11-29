package com.zendaimoney.online.entity.personal;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Area entity. @author MyEclipse Persistence Tools
 
@Entity
@Table(name = "AREA")*/
public class PersonalArea implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private BigDecimal parentId;
	private String treepath;
	private BigDecimal treelevel;
	private String name;

	// Constructors

	/** default constructor */
	public PersonalArea() {
	}

	/** full constructor */
	public PersonalArea(BigDecimal parentId, String treepath, BigDecimal treelevel,
			String name) {
		this.parentId = parentId;
		this.treepath = treepath;
		this.treelevel = treelevel;
		this.name = name;
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

	@Column(name = "PARENT_ID", precision = 22, scale = 0)
	public BigDecimal getParentId() {
		return this.parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TREEPATH", length = 100)
	public String getTreepath() {
		return this.treepath;
	}

	public void setTreepath(String treepath) {
		this.treepath = treepath;
	}

	@Column(name = "TREELEVEL", precision = 22, scale = 0)
	public BigDecimal getTreelevel() {
		return this.treelevel;
	}

	public void setTreelevel(BigDecimal treelevel) {
		this.treelevel = treelevel;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}