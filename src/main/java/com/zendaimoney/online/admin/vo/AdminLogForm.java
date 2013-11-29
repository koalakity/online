package com.zendaimoney.online.admin.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.zendaimoney.online.admin.annotation.OperateKind;

public class AdminLogForm {
	
	private String staffName;
	private OperateKind operateKind;
	@DateTimeFormat(iso=ISO.DATE)
	private Date operateTimeMin;
	@DateTimeFormat(iso=ISO.DATE)
	private Date operateTimeMax;
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public OperateKind getOperateKind() {
		return operateKind;
	}
	public void setOperateKind(OperateKind operateKind) {
		this.operateKind = operateKind;
	}
	public Date getOperateTimeMin() {
		return operateTimeMin;
	}
	public void setOperateTimeMin(Date operateTimeMin) {
		this.operateTimeMin = operateTimeMin;
	}
	public Date getOperateTimeMax() {
		return operateTimeMax;
	}
	public void setOperateTimeMax(Date operateTimeMax) {
		this.operateTimeMax = operateTimeMax;
	}
	
	
}
