package com.zendaimoney.online.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * RepayFlowDetailRep entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REPAY_FLOW_DETAIL_REP")
public class RepayFlowDetailRepVO implements java.io.Serializable {
	public static final String SEQUENCENAME="REPAYFLOWDETAILREP_SEQ";
	// Fields
	//主键
	private Long id;
	//借款ID
	private Long loanId;
	//用户ID
	private Long userId;
	//用户姓名
	private String realeName;
	//身份证号码
	private String identityNo;
	//手机号码
	private String phoneNo;
	//借款用途:1“短期周转”、2“教育培训”、3“购房借款”、4“购车借款”、5“装修基金”、6“婚礼筹备”、7“投资创业”、8“医疗支出”、9“个人消费”、10“其他借款”
	private String loanUse;
	//借款金额
	private BigDecimal loanAmount;
	//借款期限
	private String loanDuration;
	//当前期数
	private String currNum;
	//本月应还款日
	private Date shouldRepayDay;
	//本期应还总额
	private BigDecimal totalPayAmt;
	//本期应还本金
	private BigDecimal currShouldPayPrincipe;
	//本期应还利息
	private BigDecimal currShouldPayInterest;
	//应还管理费
	private BigDecimal shouldPayManagerFee;
	//实际还款日
	private Date editDate;
	//实际还款总额
	private BigDecimal payAmt;
	//还款性质  备注：0未还款 ；1正常还款；2初级逾期还款；3高级逾期还款；4一次性提前还款；5证大垫付
	private String repayStatus;
	//逾期罚息
	private BigDecimal overdueFineInterest;
	//逾期管理费
	private BigDecimal overdueManagerFee;
	//逾期利息
	private BigDecimal overdueInterest;
	//逾期本金
	private BigDecimal overduePrincipal;
	//逾期违约金
	private BigDecimal overdueFineAmt;
	//当期管理费
	private BigDecimal currManagerFee;
	//当期本金
	private BigDecimal principal;
	//当期利息
	private BigDecimal interest;
	//提前还款本金
	private BigDecimal oncePayPrincipal;
	//提前还款违约金
	private BigDecimal onceBreachPenalty;
	//渠道
	private String channelName;

	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(this.getChannelName()).append(",");
		sb.append(this.getLoanId()).append(",");
		sb.append(this.getUserId()).append(",");
		sb.append(this.getRealeName()).append(",");
		sb.append(this.getIdentityNo()).append(",");
		sb.append(this.getPhoneNo()==null?"":this.getPhoneNo()).append(",");
		if(this.getLoanUse()==null){
			sb.append(this.getLoanUse()).append(",");
		}else{
			if(this.getLoanUse().equals("1")){
				sb.append("短期周转");
			}else if(this.getLoanUse().equals("2")){
				sb.append("教育培训");
			}else if(this.getLoanUse().equals("3")){
				sb.append("购房借款");
			}else if(this.getLoanUse().equals("4")){
				sb.append("购车借款");
			}else if(this.getLoanUse().equals("5")){
				sb.append("装修基金");
			}else if(this.getLoanUse().equals("6")){
				sb.append("婚礼筹备");
			}else if(this.getLoanUse().equals("7")){
				sb.append("投资创业");
			}else if(this.getLoanUse().equals("8")){
				sb.append("医疗支出");
			}else if(this.getLoanUse().equals("9")){
				sb.append("个人消费");
			}else if(this.getLoanUse().equals("10")){
				sb.append("其他借款");
			}else{
				sb.append("");
			}
			sb.append(",");
		}
		sb.append("个人信用贷款").append(",");
		sb.append(this.getLoanAmount()).append(",");
		sb.append(this.getLoanDuration()).append(",");
		sb.append(this.getCurrNum()).append(",");
		sb.append(this.getShouldRepayDay().toLocaleString()).append(",");
		sb.append(this.getTotalPayAmt()).append(",");
		sb.append(this.getCurrShouldPayPrincipe()).append(",");
		sb.append(this.getCurrShouldPayInterest()).append(",");
		sb.append(this.getShouldPayManagerFee()).append(",");
		sb.append((this.getEditDate()!=null?this.getEditDate().toLocaleString():"")).append(",");
		sb.append(this.getPayAmt()).append(",");
		
		if(this.getRepayStatus().equals("0")){
			sb.append("未还款").append(",");
		}else if(this.getRepayStatus().equals("1")){
			sb.append("正常还款").append(",");
		}else if(this.getRepayStatus().equals("2")){
			sb.append("初级逾期还款").append(",");
		}else if(this.getRepayStatus().equals("3")){
			sb.append("高级逾期还款").append(",");
		}else if(this.getRepayStatus().equals("4")){
			sb.append("一次性提前还款").append(",");
		}else if(this.getRepayStatus().equals("5")){
			sb.append("证大垫付").append(",");
		}else{
			sb.append(this.getRepayStatus()).append(",");
		}
		
		
		
		sb.append(this.getOverdueFineInterest()).append(",");
		sb.append(this.getOverdueManagerFee()).append(",");
		sb.append(this.getOverdueInterest()).append(",");
		sb.append(this.getOverduePrincipal()).append(",");
		sb.append(this.getOverdueFineAmt()).append(",");
		sb.append(this.getCurrManagerFee()).append(",");
		sb.append(this.getPrincipal()).append(",");
		sb.append(this.getInterest()).append(",");
		sb.append(this.getOncePayPrincipal()).append(",");
		sb.append(this.getOnceBreachPenalty()).append(",");
		return sb.toString();
	}
	
	// Constructors

	/** default constructor */
	public RepayFlowDetailRepVO() {
	}

	/** minimal constructor */
	public RepayFlowDetailRepVO(Long id) {
		this.id = id;
	}

	/** full constructor */
	public RepayFlowDetailRepVO(Long id, Long loanId,
			Long userId, String realeName, String identityNo,
			String phoneNo, String loanUse, BigDecimal loanAmount,
			String loanDuration, String currNum, Date shouldRepayDay,
			BigDecimal totalPayAmt, BigDecimal currShouldPayPrincipe,
			BigDecimal currShouldPayInterest, BigDecimal shouldPayManagerFee,
			Date editDate, BigDecimal payAmt, String repayStatus,
			BigDecimal overdueFineInterest, BigDecimal overdueManagerFee,
			BigDecimal overdueInterest, BigDecimal overduePrincipal,
			BigDecimal overdueFineAmt, BigDecimal currManagerFee, BigDecimal principal,
			BigDecimal interest, BigDecimal oncePayPrincipal, BigDecimal onceBreachPenalty,
			String channelName) {
		this.id = id;
		this.loanId = loanId;
		this.userId = userId;
		this.realeName = realeName;
		this.identityNo = identityNo;
		this.phoneNo = phoneNo;
		this.loanUse = loanUse;
		this.loanAmount = loanAmount;
		this.loanDuration = loanDuration;
		this.currNum = currNum;
		this.shouldRepayDay = shouldRepayDay;
		this.totalPayAmt = totalPayAmt;
		this.currShouldPayPrincipe = currShouldPayPrincipe;
		this.currShouldPayInterest = currShouldPayInterest;
		this.shouldPayManagerFee = shouldPayManagerFee;
		this.editDate = editDate;
		this.payAmt = payAmt;
		this.repayStatus = repayStatus;
		this.overdueFineInterest = overdueFineInterest;
		this.overdueManagerFee = overdueManagerFee;
		this.overdueInterest = overdueInterest;
		this.overduePrincipal = overduePrincipal;
		this.overdueFineAmt = overdueFineAmt;
		this.currManagerFee = currManagerFee;
		this.principal = principal;
		this.interest = interest;
		this.oncePayPrincipal = oncePayPrincipal;
		this.onceBreachPenalty = onceBreachPenalty;
		this.channelName = channelName;
	}
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name="generator",sequenceName=SEQUENCENAME,allocationSize=1)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LOAN_ID", precision = 22, scale = 0)
	public Long getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "REALE_NAME", length = 40)
	public String getRealeName() {
		return this.realeName;
	}

	public void setRealeName(String realeName) {
		this.realeName = realeName;
	}

	@Column(name = "IDENTITY_NO", length = 20)
	public String getIdentityNo() {
		return this.identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	@Column(name = "PHONE_NO", length = 15)
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "LOAN_USE", length = 2)
	public String getLoanUse() {
		return this.loanUse;
	}

	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}

	@Column(name = "LOAN_AMOUNT", precision = 22, scale = 7)
	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "LOAN_DURATION", length = 2)
	public String getLoanDuration() {
		return this.loanDuration;
	}

	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}

	@Column(name = "CURR_NUM", length = 2)
	public String getCurrNum() {
		return this.currNum;
	}

	public void setCurrNum(String currNum) {
		this.currNum = currNum;
	}

	@Column(name = "SHOULD_REPAY_DAY", length = 7)
	public Date getShouldRepayDay() {
		return this.shouldRepayDay;
	}

	public void setShouldRepayDay(Date shouldRepayDay) {
		this.shouldRepayDay = shouldRepayDay;
	}

	@Column(name = "TOTAL_PAY_AMT", precision = 22, scale = 7)
	public BigDecimal getTotalPayAmt() {
		return this.totalPayAmt;
	}

	public void setTotalPayAmt(BigDecimal totalPayAmt) {
		this.totalPayAmt = totalPayAmt;
	}

	@Column(name = "CURR_SHOULD_PAY_PRINCIPE", precision = 22, scale = 7)
	public BigDecimal getCurrShouldPayPrincipe() {
		return this.currShouldPayPrincipe;
	}

	public void setCurrShouldPayPrincipe(BigDecimal currShouldPayPrincipe) {
		this.currShouldPayPrincipe = currShouldPayPrincipe;
	}

	@Column(name = "CURR_SHOULD_PAY_INTEREST", precision = 22, scale = 7)
	public BigDecimal getCurrShouldPayInterest() {
		return this.currShouldPayInterest;
	}

	public void setCurrShouldPayInterest(BigDecimal currShouldPayInterest) {
		this.currShouldPayInterest = currShouldPayInterest;
	}

	@Column(name = "SHOULD_PAY_MANAGER_FEE", precision = 22, scale = 7)
	public BigDecimal getShouldPayManagerFee() {
		return this.shouldPayManagerFee;
	}

	public void setShouldPayManagerFee(BigDecimal shouldPayManagerFee) {
		this.shouldPayManagerFee = shouldPayManagerFee;
	}

	@Column(name = "EDIT_DATE", length = 7)
	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	@Column(name = "PAY_AMT", precision = 22, scale = 7)
	public BigDecimal getPayAmt() {
		return this.payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	@Column(name = "REPAY_STATUS", length = 2)
	public String getRepayStatus() {
		return this.repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	@Column(name = "OVERDUE_FINE_INTEREST", precision = 22, scale = 7)
	public BigDecimal getOverdueFineInterest() {
		return this.overdueFineInterest;
	}

	public void setOverdueFineInterest(BigDecimal overdueFineInterest) {
		this.overdueFineInterest = overdueFineInterest;
	}

	@Column(name = "OVERDUE_MANAGER_FEE", precision = 22, scale = 7)
	public BigDecimal getOverdueManagerFee() {
		return this.overdueManagerFee;
	}

	public void setOverdueManagerFee(BigDecimal overdueManagerFee) {
		this.overdueManagerFee = overdueManagerFee;
	}

	@Column(name = "OVERDUE_INTEREST", precision = 22, scale = 7)
	public BigDecimal getOverdueInterest() {
		return this.overdueInterest;
	}

	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	@Column(name = "OVERDUE_PRINCIPAL", precision = 22, scale = 7)
	public BigDecimal getOverduePrincipal() {
		return this.overduePrincipal;
	}

	public void setOverduePrincipal(BigDecimal overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}

	@Column(name = "OVERDUE_FINE_AMT", precision = 22, scale = 7)
	public BigDecimal getOverdueFineAmt() {
		return this.overdueFineAmt;
	}

	public void setOverdueFineAmt(BigDecimal overdueFineAmt) {
		this.overdueFineAmt = overdueFineAmt;
	}

	@Column(name = "CURR_MANAGER_FEE", precision = 22, scale = 7)
	public BigDecimal getCurrManagerFee() {
		return this.currManagerFee;
	}

	public void setCurrManagerFee(BigDecimal currManagerFee) {
		this.currManagerFee = currManagerFee;
	}

	@Column(name = "PRINCIPAL", precision = 22, scale = 7)
	public BigDecimal getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	@Column(name = "INTEREST", precision = 22, scale = 7)
	public BigDecimal getInterest() {
		return this.interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	@Column(name = "ONCE_PAY_PRINCIPAL", precision = 22, scale = 7)
	public BigDecimal getOncePayPrincipal() {
		return this.oncePayPrincipal;
	}

	public void setOncePayPrincipal(BigDecimal oncePayPrincipal) {
		this.oncePayPrincipal = oncePayPrincipal;
	}

	@Column(name = "ONCE_BREACH_PENALTY", precision = 22, scale = 7)
	public BigDecimal getOnceBreachPenalty() {
		return this.onceBreachPenalty;
	}

	public void setOnceBreachPenalty(BigDecimal onceBreachPenalty) {
		this.onceBreachPenalty = onceBreachPenalty;
	}

	@Column(name = "CHANNEL_NAME", length = 62)
	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}