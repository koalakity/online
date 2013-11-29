package com.zendaimoney.online.vo.toolsBox;

import java.util.List;

public class CalculatorVO {
  //月还本息
   private String principalInterestMonth;
  //借款期限(总期数)
   private int termLoan;
  //月利率
   private String  monthRate;
  //总本息余额
   private String principalInterestBalanceTotal;
  //还款计划表
   private List <RepaymentPlanVO> repayPlanList;
   
public String getPrincipalInterestMonth() {
    return principalInterestMonth;
}
public void setPrincipalInterestMonth(String principalInterestMonth) {
    this.principalInterestMonth = principalInterestMonth;
}
public int getTermLoan() {
    return termLoan;
}
public void setTermLoan(int termLoan) {
    this.termLoan = termLoan;
}
public String getMonthRate() {
    return monthRate;
}
public void setMonthRate(String monthRate) {
    this.monthRate = monthRate;
}
public String getPrincipalInterestBalanceTotal() {
    return principalInterestBalanceTotal;
}
public void setPrincipalInterestBalanceTotal(
        String principalInterestBalanceTotal) {
    this.principalInterestBalanceTotal = principalInterestBalanceTotal;
}
public List<RepaymentPlanVO> getRepayPlanList() {
    return repayPlanList;
}
public void setRepayPlanList(List<RepaymentPlanVO> repayPlanList) {
    this.repayPlanList = repayPlanList;
}
}
