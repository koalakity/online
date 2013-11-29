/**
 * 
 */
package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 */
public class LoanNoteListForm {
	// 借款信息ID
	private BigDecimal loanId;
	// 操作者ID
	private BigDecimal operateUserId;
	// 备注内容
	private String memoText;
	public BigDecimal getLoanId() {
		return loanId;
	}
	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(BigDecimal operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getMemoText() {
		return memoText;
	}
	public void setMemoText(String memoText) {
		this.memoText = memoText;
	}
	
}
