package com.zendaimoney.online.admin.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE")
public class Role extends IdEntity{

	private String roleName;
	private Date createTime;
	private String remarks;
	private Set<RoleFunction> roleFunctions=new HashSet<RoleFunction>();
	private Set<Staff> staffs=new HashSet<Staff>();
	private boolean selected=false;
	
	

	@OneToMany(mappedBy="role")
	public Set<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	@OneToMany(mappedBy="role",cascade=CascadeType.REMOVE)
	public Set<RoleFunction> getRoleFunctions() {
		return roleFunctions;
	}

	public void setRoleFunctions(Set<RoleFunction> roleFunctions) {
		this.roleFunctions = roleFunctions;
	}

	@Column(name = "ROLE_NAME", nullable = false, length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "CREATE_TIME", nullable = false)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "REMARKS", length = 200)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	public void setSelected(boolean selected){
		this.selected=selected;
	}
	
	@Transient
	public boolean isSelected(){
		return selected;
	}

}