package com.zendaimoney.online.vo.toolsBox;

public class RepaymentPlanVO {
  //月份
    private int month;
  //月还本息
    private String principanInterestMonth;
  //月还本金
    private String principalMonth;
  //月还利息
    private String interestMonth;
  //借款管理费
   private  String managementFeeMonth;
  //本息余额
   private String principalInterestBalance;
   
public int getMonth() {
    return month;
}
public void setMonth(int month) {
    this.month = month;
}
public String getPrincipanInterestMonth() {
    return principanInterestMonth;
}
public void setPrincipanInterestMonth(String principanInterestMonth) {
    this.principanInterestMonth = principanInterestMonth;
}
public String getPrincipalMonth() {
    return principalMonth;
}
public void setPrincipalMonth(String principalMonth) {
    this.principalMonth = principalMonth;
}
public String getInterestMonth() {
    return interestMonth;
}
public void setInterestMonth(String interestMonth) {
    this.interestMonth = interestMonth;
}
public String getManagementFeeMonth() {
    return managementFeeMonth;
}
public void setManagementFeeMonth(String managementFeeMonth) {
    this.managementFeeMonth = managementFeeMonth;
}
public String getPrincipalInterestBalance() {
    return principalInterestBalance;
}
public void setPrincipalInterestBalance(String principalInterestBalance) {
    this.principalInterestBalance = principalInterestBalance;
}
}
