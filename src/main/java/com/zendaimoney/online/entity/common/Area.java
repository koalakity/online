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
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AREA")
public class Area implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private BigDecimal parentId;
	private String treePath;
	private BigDecimal treeLevel;
	private BigDecimal isLeaf;
	private String name;
	private String no;

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** full constructor */
	public Area(BigDecimal parentId, String treePath, BigDecimal treeLevel,
			BigDecimal isLeaf, String name, String no) {
		this.parentId = parentId;
		this.treePath = treePath;
		this.treeLevel = treeLevel;
		this.isLeaf = isLeaf;
		this.name = name;
		this.no = no;
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

	@Column(name = "TREE_PATH", length = 100)
	public String getTreePath() {
		return this.treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	@Column(name = "TREE_LEVEL", precision = 22, scale = 0)
	public BigDecimal getTreeLevel() {
		return this.treeLevel;
	}

	public void setTreeLevel(BigDecimal treeLevel) {
		this.treeLevel = treeLevel;
	}

	@Column(name = "IS_LEAF", precision = 22, scale = 0)
	public BigDecimal getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(BigDecimal isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NO", length = 100)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}