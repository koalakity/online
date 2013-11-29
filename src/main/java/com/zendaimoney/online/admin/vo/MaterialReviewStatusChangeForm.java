package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;

public class MaterialReviewStatusChangeForm {
	 private  String operateNode;
	 private  String cause;
	 private  BigDecimal userId;
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
	 
}
