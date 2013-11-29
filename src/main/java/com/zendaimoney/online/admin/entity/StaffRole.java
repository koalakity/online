package com.zendaimoney.online.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * StaffRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STAFF_ROLE")
public class StaffRole extends IdEntity{

	private Staff staff;
	private Role role;
	private Staff operateUser;
	private Date operateTime;

	 
	@ManyToOne
	@JoinColumn(name = "STAFF_ID", nullable = false)
	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = "OPERATE_USERID", nullable = false)
	public Staff getOperateUser() {
		return this.operateUser;
	}

	public void setOperateUser(Staff operateUser) {
		this.operateUser = operateUser;
	}

	@Column(name = "OPERATE_TIME", nullable = false)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}