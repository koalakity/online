package com.zendaimoney.online.entity.fundDetail;

import static javax.persistence.GenerationType.SEQUENCE;

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
public class AcTVirtualCashFlow implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1319168171674650276L;
	private Long id;
	private Long loanId;
	private Short currNum;
	private Double amt=0D;
	private Double principalAmt;
	private Double interestAmt;
	private Double otherAmt;
	private Date repayDay;
	private Date createDate;
	private Long createUserId;
	private Date editDate;
	private Long editUserId;
	private String memo;
	private RepayStatus repayStatus;
	@Column(name = "REPAY_STATUS", precision = 3, scale = 0)
	public RepayStatus getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(RepayStatus repayStatus) {
		this.repayStatus = repayStatus;
	}
	// Constructors

	/** default constructor */
	public AcTVirtualCashFlow() {
	}

	/** full constructor */
	public AcTVirtualCashFlow(Long loanId, Short currNum, Double amt,
			Double principalAmt, Double interestAmt, Double otherAmt,
			Date repayDay, Date createDate, Long createUserId, Date editDate,
			Long editUserId, String memo) {
		this.loanId = loanId;
		this.currNum = currNum;
		this.amt = amt;
		this.principalAmt = principalAmt;
		this.interestAmt = interestAmt;
		this.otherAmt = otherAmt;
		this.repayDay = repayDay;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.editDate = editDate;
		this.editUserId = editUserId;
		this.memo = memo;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
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
	public Short getCurrNum() {
		return this.currNum;
	}

	public void setCurrNum(Short currNum) {
		this.currNum = currNum;
	}

	@Column(name = "AMT", precision = 22, scale = 7)
	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	@Column(name = "PRINCIPAL_AMT", precision = 22, scale = 7)
	public Double getPrincipalAmt() {
		return this.principalAmt;
	}

	public void setPrincipalAmt(Double principalAmt) {
		this.principalAmt = principalAmt;
	}

	@Column(name = "INTEREST_AMT", precision = 22, scale = 7)
	public Double getInterestAmt() {
		return this.interestAmt;
	}

	public void setInterestAmt(Double interestAmt) {
		this.interestAmt = interestAmt;
	}

	@Column(name = "OTHER_AMT", precision = 22, scale = 7)
	public Double getOtherAmt() {
		return this.otherAmt;
	}

	public void setOtherAmt(Double otherAmt) {
		this.otherAmt = otherAmt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAY_DAY", length = 7)
	public Date getRepayDay() {
		return this.repayDay;
	}

	public void setRepayDay(Date repayDay) {
		this.repayDay = repayDay;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "EDIT_DATE", length = 7)
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

}