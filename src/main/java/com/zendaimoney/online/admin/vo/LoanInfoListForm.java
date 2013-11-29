package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * @author Administrator
 * 借款信息参数
 */
public class LoanInfoListForm {
	//借款信息Id
	private BigDecimal loanId;
	// 借款人姓名
	private String realName;
	//借款人昵称
	private String loginName;
	//借款人手机号码
	private String phoneNo;
	//审核状态
	private BigDecimal status;
	
	private String channelFId;
	private String channelCId;
	
	
	@DateTimeFormat(iso=ISO.DATE)
	private Date interestStartMin;
	@DateTimeFormat(iso=ISO.DATE)
	private Date interestStartMax;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public BigDecimal getStatus() {
		return status;
	}
	public void setStatus(BigDecimal status) {
		this.status = status;
	}
	public BigDecimal getLoanId() {
		return loanId;
	}
	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}
	public Date getInterestStartMin() {
		return interestStartMin;
	}
	public void setInterestStartMin(Date interestStartMin) {
		this.interestStartMin = interestStartMin;
	}
	public Date getInterestStartMax() {
		return interestStartMax;
	}
	public void setInterestStartMax(Date interestStartMax) {
		this.interestStartMax = interestStartMax;
	}
	public String getChannelFId() {
		return channelFId;
	}
	public void setChannelFId(String channelFId) {
		this.channelFId = channelFId;
	}
	public String getChannelCId() {
		return channelCId;
	}
	public void setChannelCId(String channelCId) {
		this.channelCId = channelCId;
	}
	
}
