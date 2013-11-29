package com.zendaimoney.online.admin.entity.fundDetail;

import static javax.persistence.GenerationType.SEQUENCE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.loan.AcTLedgerLoanAdmin;
import com.zendaimoney.online.admin.entity.loan.AcTVirtualCashFlowAdmin;
import com.zendaimoney.online.admin.util.DateFormatUtils;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.entity.common.Rate;

@Entity
@Table(name = "AC_T_VIRTUAL_CASH_FLOW")
public class ActVirtualCashFlow implements java.io.Serializable{

	// Fields
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
	
	private Double overDueInterestAmount=0D;//逾期罚息
	private Double overDueFineAmount=0D;//逾期违约金
	private RepayStatus repayStatus;
	private Long overDueDays;
	 
	@Column(name = "over_Due_Days")
	public Long getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(Long overDueDays) {
		this.overDueDays = overDueDays;
	}

	@Column(name = "over_Due_Interest_Amount", precision = 22, scale = 7)
	public Double getOverDueInterestAmount() {
		return overDueInterestAmount;
	}

	public void setOverDueInterestAmount(Double overDueInterestAmount) {
		this.overDueInterestAmount = overDueInterestAmount;
	}
	 
	@Column(name = "over_Due_Fine_Amount", precision = 22, scale = 7)
	public Double getOverDueFineAmount() {
		return overDueFineAmount;
	}

	public void setOverDueFineAmount(Double overDueFineAmount) {
		this.overDueFineAmount = overDueFineAmount;
	}


	@Column(name = "REPAY_STATUS", precision = 3, scale = 0)
	public RepayStatus getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(RepayStatus repayStatus) {
		this.repayStatus = repayStatus;
	}

	// Constructors

	/** default constructor */
	public ActVirtualCashFlow() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName = "ACTVIRTUALCASHFLOW_SEQ")
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
