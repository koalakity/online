package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;

public class AuditNoteListForm {
   //用户
   private String userId;
   //操作人
   private BigDecimal operateUserId;
   //备注内容
	private String memo;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(BigDecimal operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
