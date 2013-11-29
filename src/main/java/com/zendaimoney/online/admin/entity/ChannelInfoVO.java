package com.zendaimoney.online.admin.entity;

//default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;

/**
 * Channelinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CHANNEL_INFO")
public class ChannelInfoVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3502755790930282769L;
	// Fields
	private Long id;
	private String code;
	private String name;
	private String description;
	private Long parentId;
	private String staffName;
	private Date createTime;
	private Date updateTime;
	private Long rateID;
	private Long isShowFront;
	//private LoanInfoAdmin loanInfo;
	
	
//	@ManyToOne
//	@JoinColumn(name="LOAN_ID")
//	public LoanInfoAdmin getLoanInfo() {
//		return loanInfo;
//	}
//	public void setLoanInfo(LoanInfoAdmin loanInfo) {
//		this.loanInfo = loanInfo;
//	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CODE", length = 6)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "PARENT_ID", precision = 22, scale = 0)
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "STAFF_NAME", nullable = false, length = 30)
	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	@Column(name = "CREATE_TIME", nullable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", nullable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "RATE_ID", precision = 22, scale = 0)
	public Long getRateID() {
		return rateID;
	}

	public void setRateID(Long rateID) {
		this.rateID = rateID;
	}

	@Column(name = "IS_SHOW_FRONT")
	public Long getIsShowFront() {
		return isShowFront;
	}

	public void setIsShowFront(Long isShowFront) {
		this.isShowFront = isShowFront;
	}

}