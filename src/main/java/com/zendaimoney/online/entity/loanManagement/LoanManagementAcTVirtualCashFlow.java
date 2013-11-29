package com.zendaimoney.online.entity.loanManagement;

import static javax.persistence.GenerationType.SEQUENCE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.zendaimoney.online.admin.util.DateFormatUtils;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.entity.common.LoanRateVO;

/**
 * AcTVirtualCashFlow entity. @author MyEclipse Persistence Tools
 */
/**
 * @author bianxj
 *
 */
@Entity
@Table(name = "AC_T_VIRTUAL_CASH_FLOW")
public class LoanManagementAcTVirtualCashFlow{
 
	
	// Fields

	private Long id;
	private LoanManagementAcTLedgerLoan actLedgerLoan;
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
	private LoanRateVO rate;
	private Double overDueInterestAmount=0D;//逾期罚息
	private Double overDueFineAmount=0D;//逾期违约金
	private Integer overDueDays;//逾期天数
	
	 
	
	@Column(name = "over_Due_Days")
	public Integer getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(Integer overDueDays) {
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
	public LoanManagementAcTVirtualCashFlow() {
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

	
	
	 
	@ManyToOne
	@JoinColumn(name="LOAN_ID", referencedColumnName="ID")
	public LoanManagementAcTLedgerLoan getActLedgerLoan() {
		return actLedgerLoan;
	}
	 

	public void setActLedgerLoan(LoanManagementAcTLedgerLoan actLedgerLoan) {
		this.actLedgerLoan = actLedgerLoan;
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
	@Transient
	public LoanRateVO getRate() {
		return rate;
	}
	@Transient
	public void setRate(LoanRateVO rate) {
		this.rate = rate;
	}

	
	@Transient
	public String getRemainderPrincipal() {
		List<LoanManagementAcTVirtualCashFlow> actVirtualcashFlows = actLedgerLoan.getAcTVirtualCashFlows();
		double principalAmtTotal =0;
		for(int i=1;i<=currNum;i++){
			for(LoanManagementAcTVirtualCashFlow actVirtualCashFlow : actVirtualcashFlows){
				if(actVirtualCashFlow.getCurrNum()==i){
					principalAmtTotal = ArithUtil.add(principalAmtTotal, actVirtualCashFlow.getPrincipalAmt());
					break;
				}
			}
		}
		return ObjectFormatUtil.formatCurrency(ArithUtil.sub(actLedgerLoan.getLoanInfo().getLoanAmount(), principalAmtTotal));
	}

	
	@Transient
	public int getPastDueDays() {
		if(null!=overDueDays&&overDueDays>0){
			return overDueDays.intValue();
		}
		if (currNum < actLedgerLoan.getCurrNum()) {
			return 0;
		}
		 DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		 return DateFormatUtils.countDays(repayDay.toString(), dateFormate.format(new Date()));
	}
	
	@Transient
	public String getMonthManageCost(){
		return ObjectFormatUtil.formatCurrency(actLedgerLoan.getLoanInfo().getMonthManageCost());
	}
	
	@Transient
	public String getOverduePenaltyInterest(){
		
		// 剩余还款期数
		int leftRepayDuration = actLedgerLoan.getLoanInfo().getLoanDuration().shortValue() - currNum + 1;
		// 逾期罚息费率
		double rateOverduePenaltyInterest = 0;
		if(getPastDueDays()<=30){
			rateOverduePenaltyInterest = rate.getOverdueInterest().doubleValue();
		}else{
			rateOverduePenaltyInterest = rate.getOverdueSeriousInterest().doubleValue();
		}
		 // 逾期罚息：=剩余未还期数*每期应还本息*逾期天数*逾期罚息费率
		double overduePenaltyInterest = FormulaSupportUtil.calOverdueInterest(getPastDueDays(), leftRepayDuration, actLedgerLoan.getLoanInfo().getMonthReturnPrincipalandinter(), rateOverduePenaltyInterest);
		return ObjectFormatUtil.formatCurrency(overduePenaltyInterest);
	}
	
	@Transient
	public String getOverdueBreachPenalty(){
		
		// 逾期违约金费率
		double rateOverduePenaltyInterest = rate.getOverdueFines().doubleValue();
		// 剩余还款期数
		int leftRepayDuration = actLedgerLoan.getLoanInfo().getLoanDuration().shortValue() - currNum + 1;
		// 月缴管理费
		double monthManageCost = actLedgerLoan.getLoanInfo().getMonthManageCost();
		// 逾期违约金：=剩余未还期数*每期应缴月缴管理费*逾期天数*逾期罚息费率
		double overdueBreachPenalty = FormulaSupportUtil.getOverdueFines(getPastDueDays(), leftRepayDuration, monthManageCost, rateOverduePenaltyInterest);
		return ObjectFormatUtil.formatCurrency(overdueBreachPenalty);
	}
	
	@Transient
	public String getIsRepayed() {
		return this.repayStatus.toString();
	}
	@Transient
	public String getAmtFormatt(){
		return ObjectFormatUtil.formatCurrency(this.amt);
	}
	
	@Transient
	public String getEachRepayment(){
		return ObjectFormatUtil.formatCurrency(ArithUtil.add(this.interestAmt, this.principalAmt));
	}
	/**
	 * 子列表用
	 * @return
	 */
	@Transient
	public double getInterestOverduePenalty(){
		
		// 剩余还款期数
		int leftRepayDuration = actLedgerLoan.getLoanInfo().getLoanDuration().shortValue() - currNum+1;
		// 逾期罚息费率
		double rateOverduePenaltyInterest = 0;
		if(getPastDueDays()<=30){
			rateOverduePenaltyInterest = rate.getOverdueInterest().doubleValue();
		}else{
			rateOverduePenaltyInterest = rate.getOverdueSeriousInterest().doubleValue();
		}
		 // 逾期罚息：=剩余未还期数*每期应还本息*逾期天数*逾期罚息费率
		double overduePenaltyInterest = FormulaSupportUtil.calOverdueInterest(getPastDueDays(), leftRepayDuration, actLedgerLoan.getLoanInfo().getMonthReturnPrincipalandinter(), rateOverduePenaltyInterest);
		return overduePenaltyInterest;
	}
	@Transient
	public double getInverestRemainderPrincipal() {
		List<LoanManagementAcTVirtualCashFlow> actVirtualcashFlows = actLedgerLoan.getAcTVirtualCashFlows();
		double principalAmtTotal =0;
		for(int i=1;i<=currNum;i++){
			for(LoanManagementAcTVirtualCashFlow actVirtualCashFlow : actVirtualcashFlows){
				if(actVirtualCashFlow.getCurrNum()==i){
					principalAmtTotal = ArithUtil.add(principalAmtTotal, actVirtualCashFlow.getPrincipalAmt());
					break;
				}
			}
		}
		return ArithUtil.sub(actLedgerLoan.getLoanInfo().getLoanAmount(), principalAmtTotal);
	}
}