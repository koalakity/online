/**
 * 
 */
package com.zendaimoney.online.entity.loanManagement;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.zendaimoney.online.admin.entity.IdEntity;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "OVERDUE_CLAIMS")
public class LoanManagentOverdueClaims extends IdEntity {
	private BigDecimal loanId;
	private BigDecimal investId;
	private Long num;
	private double payAmount;
	private double overDueFineAmount;
	private double overDueInterstAmount;
	private BigDecimal status;
	private double managerFree;
	private int overDueDays;
	private Date createTime;
	private Date editTime;
	private BigDecimal isAdvanced;
	@Column(name = "LOAN_ID")
	public BigDecimal getLoanId() {
		return loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}
	@Column(name = "INVEST_ID")
	public BigDecimal getInvestId() {
		return investId;
	}

	public void setInvestId(BigDecimal investId) {
		this.investId = investId;
	}
	@Column(name = "NUM")
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	@Column(name = "PAY_AMOUNT")
	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	@Column(name = "OVER_DUE_FINE_AMOUNT")
	public double getOverDueFineAmount() {
		return overDueFineAmount;
	}

	public void setOverDueFineAmount(double overDueFineAmount) {
		this.overDueFineAmount = overDueFineAmount;
	}
	@Column(name = "OVER_DUE_INTEREST_AMOUNT")
	public double getOverDueInterstAmount() {
		return overDueInterstAmount;
	}

	public void setOverDueInterstAmount(double overDueInterstAmount) {
		this.overDueInterstAmount = overDueInterstAmount;
	}
	@Column(name = "STATUS")
	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}
	@Column(name = "MANAGER_FREE")
	public double getManagerFree() {
		return managerFree;
	}

	public void setManagerFree(double managerFree) {
		this.managerFree = managerFree;
	}
	@Column(name = "OVER_DUE_DAYS")
	public int getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(int overDueDays) {
		this.overDueDays = overDueDays;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "EDIT_TIME")
	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	@Column(name = "IS_ADVANCED")
	public BigDecimal getIsAdvanced() {
		return isAdvanced;
	}

	public void setIsAdvanced(BigDecimal isAdvanced) {
		this.isAdvanced = isAdvanced;
	}

}
