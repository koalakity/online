package com.zendaimoney.online.admin.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Function entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FUNCTION")
public class Function extends IdEntity{

	// Fields

	private String functionName;
	private String createTime;
	private String remarks;
	private String functionCode;
	@JsonIgnore
	private Function parent;
	private Set<Function> children=new HashSet<Function>();
	
	@ManyToOne(targetEntity=Function.class)
	@JoinColumn(name="parent_id")
	public Function getParent() {
		return parent;
	}

	public void setParent(Function parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	public Set<Function> getChildren() {
		return children;
	}

	public void setChildren(Set<Function> children) {
		this.children = children;
	}

	@Column(name = "FUNCTION_NAME", nullable = false, length = 50)
	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "CREATE_TIME", nullable = false)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "REMARKS", length = 200)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "FUNCTION_CODE", length = 50)
	public String getFunctionCode() {
		return this.functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	
	@Transient
	public String getText(){
		return functionName;
	}
	
	@Transient
	public String getState(){
		return "open";
	}
	
	private boolean checked=Boolean.FALSE;
	
	@Transient
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Transient
	public boolean getChecked(){
		return checked;
	}

}