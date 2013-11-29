package com.zendaimoney.online.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zendaimoney.online.constant.loanManagement.RepayStatus;

/**
 * AcTVirtualCashFlow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_VIRTUAL_CASH_FLOW")
public class AcTVirtualCashFlowVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -39902923614571693L;
	private Long id;
	private Long loanId;
	private Long currNum;
	private BigDecimal amt;
	private BigDecimal principalAmt;
	private BigDecimal interestAmt;
	private BigDecimal otherAmt;
	private Date repayDay;
	private Date createDate;
	private Long createUserId;
	private Date editDate;
	private Long editUserId;
	private String memo;
	private Long repayStatus;
	private BigDecimal overDueInterestAmount=BigDecimal.ZERO;//逾期罚息
	private BigDecimal overDueFineAmount=BigDecimal.ZERO;//逾期违约金
	private Integer overDueDays;//逾期天数
	

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="ACTVIRTUALCASHFLOW_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LOAN_ID", precision = 11, scale = 0)
	public Long getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	@Column(name = "CURR_NUM", precision = 3, scale = 0)
	public Long getCurrNum() {
		return this.currNum;
	}

	public void setCurrNum(Long currNum) {
		this.currNum = currNum;
	}

	@Column(name = "AMT", precision = 22, scale = 7)
	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	@Column(name = "PRINCIPAL_AMT", precision = 22, scale = 7)
	public BigDecimal getPrincipalAmt() {
		return this.principalAmt;
	}

	public void setPrincipalAmt(BigDecimal principalAmt) {
		this.principalAmt = principalAmt;
	}

	@Column(name = "INTEREST_AMT", precision = 22, scale = 7)
	public BigDecimal getInterestAmt() {
		return this.interestAmt;
	}

	public void setInterestAmt(BigDecimal interestAmt) {
		this.interestAmt = interestAmt;
	}

	@Column(name = "OTHER_AMT", precision = 22, scale = 7)
	public BigDecimal getOtherAmt() {
		return this.otherAmt;
	}

	public void setOtherAmt(BigDecimal otherAmt) {
		this.otherAmt = otherAmt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAY_DAY")
	public Date getRepayDay() {
		return this.repayDay;
	}

	public void setRepayDay(Date repayDay) {
		this.repayDay = repayDay;
	}

	
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_USER_ID", precision = 11, scale = 0)
	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	
	@Column(name = "EDIT_DATE")
	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	@Column(name = "EDIT_USER_ID", precision = 11, scale = 0)
	public Long getEditUserId() {
		return this.editUserId;
	}

	public void setEditUserId(Long editUserId) {
		this.editUserId = editUserId;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "REPAY_STATUS", precision = 3, scale = 0)
	public Long getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(Long repayStatus) {
		this.repayStatus = repayStatus;
	}
	@Column(name = "over_Due_Days")
	public Integer getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(Integer overDueDays) {
		this.overDueDays = overDueDays;
	}

	@Column(name = "over_Due_Interest_Amount", precision = 22, scale = 7)
	public BigDecimal getOverDueInterestAmount() {
		return overDueInterestAmount;
	}

	public void setOverDueInterestAmount(BigDecimal overDueInterestAmount) {
		this.overDueInterestAmount = overDueInterestAmount;
	}
	 
	@Column(name = "over_Due_Fine_Amount", precision = 22, scale = 7)
	public BigDecimal getOverDueFineAmount() {
		return overDueFineAmount;
	}

	public void setOverDueFineAmount(BigDecimal overDueFineAmount) {
		this.overDueFineAmount = overDueFineAmount;
	}
}