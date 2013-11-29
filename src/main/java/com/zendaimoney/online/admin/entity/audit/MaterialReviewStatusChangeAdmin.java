package com.zendaimoney.online.admin.entity.audit;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.IdEntity;
import com.zendaimoney.online.admin.entity.Staff;

@Entity
@Table(name="MATERIAL_REVIEW_STATUS_CHANGE")
public class MaterialReviewStatusChangeAdmin extends IdEntity {
	 private  Staff staff;
	 private  Date operateTime;
	 private  String operateNode;
	 private  String cause;
	 private  BigDecimal userId;
	 private  String kind;
	
	@ManyToOne
	@JoinColumn(name="OPERATE_USER_ID")
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateNode() {
		return operateNode;
	}
	public void setOperateNode(String operateNode) {
		this.operateNode = operateNode;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}   
	
	@Transient
	public String getOpearateUser() {
		return staff.getName();
	}
	
	@Transient
	public String getOperateTimeFormatt() {
		return operateTime==null?"":operateTime.toString().substring(0, 16);
	}
	 
}
