/**
 * 
 */
package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 * 
 */
public class ExtractNoteForm {
	// 提款编号
	private BigDecimal extractId;
	private String realName;
	// 提款金额From
	private String extractAmountFrom;
	// 提款金额To
	private String extractAmountTo;
	// 提现状态
	private BigDecimal verifyStatus;
	
	private String description;

	public BigDecimal getExtractId() {
		return extractId;
	}

	public void setExtractId(BigDecimal extractId) {
		this.extractId = extractId;
	}

	

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getExtractAmountFrom() {
		return extractAmountFrom;
	}

	public void setExtractAmountFrom(String extractAmountFrom) {
		this.extractAmountFrom = extractAmountFrom;
	}

	public String getExtractAmountTo() {
		return extractAmountTo;
	}

	public void setExtractAmountTo(String extractAmountTo) {
		this.extractAmountTo = extractAmountTo;
	}

	public BigDecimal getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(BigDecimal verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
