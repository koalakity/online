package com.zendaimoney.online.admin.entity;

// default package

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OverdueClaims entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OVERDUE_CLAIMS")
public class OverdueClaimsAdminVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2230877477006099122L;
	// Fields

	private OverdueClaimsAdminId id;
	private Long loanId;
	private BigDecimal payAmount;
	private BigDecimal overDueFineAmount;
	private BigDecimal overDueInterestAmount;
	private Long status;
	private BigDecimal managerFree;
	private Long overDueDays;
	private Date createTime;
	private Date editTime;
	private Long isAdvanced;

	// Constructors

	/** default constructor */
	public OverdueClaimsAdminVO() {
	}

	/** minimal constructor */
	public OverdueClaimsAdminVO(OverdueClaimsAdminId id, Long id_1, Long loanId) {
		this.id = id;
		//this.id_1 = id_1;
		this.loanId = loanId;
	}

	/** full constructor */
	public OverdueClaimsAdminVO(OverdueClaimsAdminId id, Long id_1, Long loanId,
			BigDecimal payAmount, BigDecimal overDueFineAmount,
			BigDecimal overDueInterestAmount, Long status,
			BigDecimal managerFree, Long overDueDays, Date createTime,
			Date editTime, Long isAdvanced) {
		this.id = id;
		//this.id_1 = id_1;
		this.loanId = loanId;
		this.payAmount = payAmount;
		this.overDueFineAmount = overDueFineAmount;
		this.overDueInterestAmount = overDueInterestAmount;
		this.status = status;
		this.managerFree = managerFree;
		this.overDueDays = overDueDays;
		this.createTime = createTime;
		this.editTime = editTime;
		this.isAdvanced = isAdvanced;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "investId", column = @Column(name = "INVEST_ID", nullable = false, precision = 11, scale = 0)),
			@AttributeOverride(name = "num", column = @Column(name = "NUM", nullable = false, precision = 3, scale = 0)) })
	public OverdueClaimsAdminId getId() {
		return this.id;
	}

	public void setId(OverdueClaimsAdminId id) {
		this.id = id;
	}

//	@Column(name = "ID", nullable = false, precision = 11, scale = 0)
//	public Long getId_1() {
//		return this.id_1;
//	}
//
//	public void setId_1(Long id_1) {
//		this.id_1 = id_1;
//	}

	@Column(name = "LOAN_ID", nullable = false, precision = 11, scale = 0)
	public Long getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	@Column(name = "PAY_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name = "OVER_DUE_FINE_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getOverDueFineAmount() {
		return this.overDueFineAmount;
	}

	public void setOverDueFineAmount(BigDecimal overDueFineAmount) {
		this.overDueFineAmount = overDueFineAmount;
	}

	@Column(name = "OVER_DUE_INTEREST_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getOverDueInterestAmount() {
		return this.overDueInterestAmount;
	}

	public void setOverDueInterestAmount(BigDecimal overDueInterestAmount) {
		this.overDueInterestAmount = overDueInterestAmount;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "MANAGER_FREE", precision = 22, scale = 7)
	public BigDecimal getManagerFree() {
		return this.managerFree;
	}

	public void setManagerFree(BigDecimal managerFree) {
		this.managerFree = managerFree;
	}

	@Column(name = "OVER_DUE_DAYS", precision = 22, scale = 0)
	public Long getOverDueDays() {
		return this.overDueDays;
	}

	public void setOverDueDays(Long overDueDays) {
		this.overDueDays = overDueDays;
	}
	
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "EDIT_TIME")
	public Date getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	@Column(name = "IS_ADVANCED", precision = 1, scale = 0)
	public Long getIsAdvanced() {
		return this.isAdvanced;
	}

	public void setIsAdvanced(Long isAdvanced) {
		this.isAdvanced = isAdvanced;
	}

}