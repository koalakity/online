package com.zendaimoney.online.admin.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RoleFunction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE_FUNCTION")
public class RoleFunction extends IdEntity{

	private Role role;
	private Function function;
	private Staff operateUser;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = "FUNCTION_ID")
	public Function getFunction() {
		return this.function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	@ManyToOne
	@JoinColumn(name = "OPERATE_USERID")
	public Staff getOperateUser() {
		return this.operateUser;
	}

	public void setOperateUser(Staff operateUser) {
		this.operateUser = operateUser;
	}

}