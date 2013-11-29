/**
 * 
 */
package com.zendaimoney.online.admin.entity.loan;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;

/**
 * InvestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INVEST_INFO")
public class InvestInfoAdmin implements java.io.Serializable {

	// Fields

	private BigDecimal investId;
	private LoanInfoAdmin loanInfo;
	private Double investAmount;
	private Double havaScale;
	private String investTime;
	private String description;
	private String status;
	private BigDecimal ledgerFinanceId;
	private AccountUsersAdmin accountUser;
	private BigDecimal advanceStatus;//收购状态
	private String advancedAmount;



	/** default constructor */
	public InvestInfoAdmin() {
	}

	

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "INVEST_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getInvestId() {
		return this.investId;
	}

	public void setInvestId(BigDecimal investId) {
		this.investId = investId;
	}


	@ManyToOne
	@JoinColumn(name = "LOAN_ID")
	public LoanInfoAdmin getLoanInfo() {
		return loanInfo;
	}



	public void setLoanInfo(LoanInfoAdmin loanInfo) {
		this.loanInfo = loanInfo;
	}



	@Column(name = "INVEST_AMOUNT", nullable = false, precision = 22, scale = 7)
	public Double getInvestAmount() {
		return this.investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	@Column(name = "HAVA_SCALE", nullable = false, precision = 22, scale = 18)
	public Double getHavaScale() {
		return this.havaScale;
	}

	public void setHavaScale(Double havaScale) {
		this.havaScale = havaScale;
	}

	@Column(name = "INVEST_TIME", nullable = false)
	public String getInvestTime() {
		return this.investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	// Constructors
	@Column(name = "LEDGER_FINANCE_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLedgerFinanceId() {
		return ledgerFinanceId;
	}

	public void setLedgerFinanceId(BigDecimal ledgerFinanceId) {
		this.ledgerFinanceId = ledgerFinanceId;
	}
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public AccountUsersAdmin getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(AccountUsersAdmin accountUser) {
		this.accountUser = accountUser;
	}
	@Transient
	public String getRealName(){
		return accountUser.getLoginName();
	}
	/**
	 * 返回每月还本息
	 * @return
	 */
	@Transient
	public String getMonthRepayPrincipal() {
		double monthRepayPrincipal = ArithUtil.mul(loanInfo.getMonthReturnPrincipalandinter(), havaScale);
		
		return ObjectFormatUtil.formatCurrency(monthRepayPrincipal);
		
	}
	
	@Transient
	public String getInvestAmountFormatt(){
		return investAmount==null?ObjectFormatUtil.formatCurrency(0):ObjectFormatUtil.formatCurrency(investAmount);
	}
	@Transient
	public double getLeftPrincipal(){
		if(null == loanInfo.getLoanAcTLedgerLoan() || null == loanInfo.getLoanAcTLedgerLoan().getCurrNum()){
			return investAmount;
		}
		int crrNum = loanInfo.getLoanAcTLedgerLoan().getCurrNum().intValue();
		return investAmount-(investAmount/loanInfo.getLoanDuration().doubleValue())*(crrNum-1);
		
	}
	
	/**
	 * 剩余本金
	 * @return
	 */
	@Transient
	public String getInverestRemainderPrincipal() {
		Set<AcTVirtualCashFlowAdmin> actVirtualcashFlows = null;
		if(null != loanInfo.getLoanAcTLedgerLoan()&& null != loanInfo.getLoanAcTLedgerLoan().getCurrNum()){
			actVirtualcashFlows = loanInfo.getLoanAcTLedgerLoan().getAcTVirtualCashFlows();
			double principalAmtTotal =0;
			for(int i=1;i<=loanInfo.getLoanAcTLedgerLoan().getCurrNum().intValue()-1;i++){
				for(AcTVirtualCashFlowAdmin actVirtualCashFlow : actVirtualcashFlows){
					if(actVirtualCashFlow.getCurrNum()==i){
						principalAmtTotal = ArithUtil.add(principalAmtTotal, actVirtualCashFlow.getPrincipalAmt());
						break;
					}
				}
			}
			double remainderPrincipal = ArithUtil.sub(loanInfo.getLoanAmount(), principalAmtTotal);
			return ObjectFormatUtil.formatCurrency(ArithUtil.mul(remainderPrincipal, this.havaScale));
		}
		return ObjectFormatUtil.formatCurrency(this.getInvestAmount());
		
		
	}
	@Transient
	public String getHaveScaleFormatt(){
		return ObjectFormatUtil.formatPercent(this.havaScale,"#,###0.00%");
	}


	@Transient
	public BigDecimal getAdvanceStatus() {
		return advanceStatus;
	}


	@Transient
	public void setAdvanceStatus(BigDecimal advanceStatus) {
		this.advanceStatus = advanceStatus;
	}


	@Transient
	public String getAdvancedAmount() {
		return advancedAmount;
	}


	@Transient
	public void setAdvancedAmount(String advancedAmount) {
		this.advancedAmount = advancedAmount;
	}
}