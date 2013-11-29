package com.zendaimoney.online.vo.loanManagement;

public class LoanStatisticsVO{
    
    //总借款额
	private  String loanTotle;
	
	//发布借款笔数
	private int releaseLoanNumber;
	
	//已还本息
	private String repayOffPrincipalInterestTotal;
	
	//成功借款笔数
	private int successLoanNumber;
	
	//待还本息
	private String waitRepayPrincipalInterestTotal;
	
	//正常还清笔数
	private int normalPayOffNumber;
	
	//提前还清笔数
	private int earlyRepayOffNumber;
	
	//待还管理费
	private String waitRepayManagementFee;
	
	//未还清笔数
	private int notRepayOffNumber;
	
	//逾期次数
	private int overdueCount;
	
	//严重逾期次数
	private int seriousOverdueCount;
	
	//加权平均借款利率
	private String weightedAverageLoanRate;
	
	//平均每笔借款金额
	private String averageEachLoanAmount;

    public String getLoanTotle() {
        return loanTotle;
    }

    public void setLoanTotle(String loanTotle) {
        this.loanTotle = loanTotle;
    }

    public int getReleaseLoanNumber() {
        return releaseLoanNumber;
    }

    public void setReleaseLoanNumber(int releaseLoanNumber) {
        this.releaseLoanNumber = releaseLoanNumber;
    }

    public String getRepayOffPrincipalInterestTotal() {
        return repayOffPrincipalInterestTotal;
    }

    public void setRepayOffPrincipalInterestTotal(
    		String repayOffPrincipalInterestTotal) {
        this.repayOffPrincipalInterestTotal = repayOffPrincipalInterestTotal;
    }

    public int getSuccessLoanNumber() {
        return successLoanNumber;
    }

    public void setSuccessLoanNumber(int successLoanNumber) {
        this.successLoanNumber = successLoanNumber;
    }

    public String getWaitRepayPrincipalInterestTotal() {
        return waitRepayPrincipalInterestTotal;
    }

    public void setWaitRepayPrincipalInterestTotal(
    		String waitRepayPrincipalInterestTotal) {
        this.waitRepayPrincipalInterestTotal = waitRepayPrincipalInterestTotal;
    }

    public int getNormalPayOffNumber() {
        return normalPayOffNumber;
    }

    public void setNormalPayOffNumber(int normalPayOffNumber) {
        this.normalPayOffNumber = normalPayOffNumber;
    }

    public int getEarlyRepayOffNumber() {
        return earlyRepayOffNumber;
    }

    public void setEarlyRepayOffNumber(int earlyRepayOffNumber) {
        this.earlyRepayOffNumber = earlyRepayOffNumber;
    }

    public String getWaitRepayManagementFee() {
        return waitRepayManagementFee;
    }

    public void setWaitRepayManagementFee(String waitRepayManagementFee) {
        this.waitRepayManagementFee = waitRepayManagementFee;
    }

    public int getNotRepayOffNumber() {
        return notRepayOffNumber;
    }

    public void setNotRepayOffNumber(int notRepayOffNumber) {
        this.notRepayOffNumber = notRepayOffNumber;
    }

    public int getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(int overdueCount) {
        this.overdueCount = overdueCount;
    }

    public int getSeriousOverdueCount() {
        return seriousOverdueCount;
    }

    public void setSeriousOverdueCount(int seriousOverdueCount) {
        this.seriousOverdueCount = seriousOverdueCount;
    }

    public String getWeightedAverageLoanRate() {
        return weightedAverageLoanRate;
    }

    public void setWeightedAverageLoanRate(String weightedAverageLoanRate) {
        this.weightedAverageLoanRate = weightedAverageLoanRate;
    }

    public String getAverageEachLoanAmount() {
        return averageEachLoanAmount;
    }

    public void setAverageEachLoanAmount(String averageEachLoanAmount) {
        this.averageEachLoanAmount = averageEachLoanAmount;
    }
	
}
