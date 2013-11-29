package com.zendaimoney.online.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.annotation.OperateKind;

@Entity
public class AdminLog extends IdEntity{
	private Staff staff;
	private OperateKind operateKind;
	private BigDecimal	operateObjectId;
	private String operateContent;
	private String ipAdd="0.0.0.0";
	private Date operateTime=new Date();
	
	@ManyToOne
	@JoinColumn(name="operate_user_id")
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public OperateKind getOperateKind() {
		return operateKind;
	}
	public void setOperateKind(OperateKind operateKind) {
		this.operateKind = operateKind;
	}
	public BigDecimal getOperateObjectId() {
		return operateObjectId;
	}
	public void setOperateObjectId(BigDecimal operateObjectId) {
		this.operateObjectId = operateObjectId;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getIpAdd() {
		return ipAdd;
	}
	public void setIpAdd(String ipAdd) {
		this.ipAdd = ipAdd;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	@Transient
	public String getStaffName(){
		return staff.getLoginName();
	}
	
	@Transient
	public String getOperateKindStr(){
		return operateKind.name();
	}
}
