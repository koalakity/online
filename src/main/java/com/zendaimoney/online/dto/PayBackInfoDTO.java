/**
 * 
 */
package com.zendaimoney.online.dto;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.AcTVirtualCashFlowVO;

/**
 * @author 王腾飞 还款相关DTO
 * 
 */
public class PayBackInfoDTO {
	// 当前应还本金
	 BigDecimal payBackPrincipal = BigDecimal.ZERO;
	// 当前应还利息
	 private BigDecimal payBackInterest = BigDecimal.ZERO;
	// 当期应还 月缴管理费
	 private BigDecimal manageFeeByMonth = BigDecimal.ZERO;
	// 逾期违约金
	 private BigDecimal overdueFines = BigDecimal.ZERO;
	// 当前期逾期罚息
	 private BigDecimal overdueInterest = BigDecimal.ZERO;
	 // 应还总额
	 private BigDecimal totalPayBackAmount = BigDecimal.ZERO;
	 //剩余本金
	private BigDecimal surplusPrincipal = BigDecimal.ZERO;
	// 提前还款违约金
	private BigDecimal advanceOncePayBreachPenalty = BigDecimal.ZERO;
	//剩余利息
	private BigDecimal surplusInterest = BigDecimal.ZERO;
	//当期是否已还清
	private Boolean currIsPayed = false;
	 // 逾期天数
	 private int overDueDays = 0;
	 
	 private Long currNum = 0L;
	 //当期还款计划
	 private AcTVirtualCashFlowVO currAcTVirtualCashFlow;
	 // 下期还款计划
	 private AcTVirtualCashFlowVO nextAcTVirtualCashFlow;
	 //还款计划
	 List<AcTVirtualCashFlowVO> acTVirtualCashFlows ;
	
	 
	public BigDecimal getPayBackPrincipal() {
		return payBackPrincipal;
	}
	public void setPayBackPrincipal(BigDecimal payBackPrincipal) {
		this.payBackPrincipal = payBackPrincipal;
	}
	public BigDecimal getPayBackInterest() {
		return payBackInterest;
	}
	public void setPayBackInterest(BigDecimal payBackInterest) {
		this.payBackInterest = payBackInterest;
	}
	public BigDecimal getManageFeeByMonth() {
		return manageFeeByMonth;
	}
	public void setManageFeeByMonth(BigDecimal manageFeeByMonth) {
		this.manageFeeByMonth = manageFeeByMonth;
	}
	public BigDecimal getOverdueFines() {
		return overdueFines;
	}
	public void setOverdueFines(BigDecimal overdueFines) {
		this.overdueFines = overdueFines;
	}
	public BigDecimal getOverdueInterest() {
		return overdueInterest;
	}
	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}
	public BigDecimal getTotalPayBackAmount() {
		return totalPayBackAmount;
	}
	public void setTotalPayBackAmount(BigDecimal totalPayBackAmount) {
		this.totalPayBackAmount = totalPayBackAmount;
	}
	public int getOverDueDays() {
		return overDueDays;
	}
	public void setOverDueDays(int overDueDays) {
		this.overDueDays = overDueDays;
	}
	public Long getCurrNum() {
		return currNum;
	}
	public void setCurrNum(Long currNum) {
		this.currNum = currNum;
	}
	public AcTVirtualCashFlowVO getCurrAcTVirtualCashFlow() {
		return currAcTVirtualCashFlow;
	}
	public void setCurrAcTVirtualCashFlow(
			AcTVirtualCashFlowVO currAcTVirtualCashFlow) {
		this.currAcTVirtualCashFlow = currAcTVirtualCashFlow;
	}
	public AcTVirtualCashFlowVO getNextAcTVirtualCashFlow() {
		return nextAcTVirtualCashFlow;
	}
	public void setNextAcTVirtualCashFlow(
			AcTVirtualCashFlowVO nextAcTVirtualCashFlow) {
		this.nextAcTVirtualCashFlow = nextAcTVirtualCashFlow;
	}
	public BigDecimal getSurplusPrincipal() {
		return surplusPrincipal;
	}
	public void setSurplusPrincipal(BigDecimal surplusPrincipal) {
		this.surplusPrincipal = surplusPrincipal;
	}
	public BigDecimal getAdvanceOncePayBreachPenalty() {
		return advanceOncePayBreachPenalty;
	}
	public void setAdvanceOncePayBreachPenalty(
			BigDecimal advanceOncePayBreachPenalty) {
		this.advanceOncePayBreachPenalty = advanceOncePayBreachPenalty;
	}
	public Boolean getCurrIsPayed() {
		return currIsPayed;
	}
	public void setCurrIsPayed(Boolean currIsPayed) {
		this.currIsPayed = currIsPayed;
	}
	public List<AcTVirtualCashFlowVO> getAcTVirtualCashFlows() {
		return acTVirtualCashFlows;
	}
	public void setAcTVirtualCashFlows(
			List<AcTVirtualCashFlowVO> acTVirtualCashFlows) {
		this.acTVirtualCashFlows = acTVirtualCashFlows;
	}
	public BigDecimal getSurplusInterest() {
		return surplusInterest;
	}
	public void setSurplusInterest(BigDecimal surplusInterest) {
		this.surplusInterest = surplusInterest;
	}
	 
}
