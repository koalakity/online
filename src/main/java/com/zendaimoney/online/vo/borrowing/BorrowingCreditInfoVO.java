package com.zendaimoney.online.vo.borrowing;

import java.math.BigDecimal;

public class BorrowingCreditInfoVO{
    
    //e贷信用等级
    private BigDecimal creditGrade;
    //信用分数
    private BigDecimal creditScoreSum;
    //信用额度
    private double creditAmount;
    //借款后的可用额度
    private double laonCreditAmount;
    //性别
    private String sex;
    //年龄
    private int age;
    //是否结婚
    private String isMarried;
    //工作城市
    private String workCity;
    //公司行业
    private String companyIndustry;
    //公司规模
    private String companyScale;
    //工作收入
    private String workIncome;
    //现单位工作时间(年)
    private String nowWorkCompanyYear;
    //毕业学校
    private String graduationSchool;
    //学历
    private String education;
    //入学年份
    private String entranceYear;
    //有无购房
    private String isBuyHouse;
    //有无购车
    private String isBuyCar;
    //购车年份
    private String buyCarYear;
    //有无房贷
    private String isHasMortgage;
    //有无车贷
    private String isHasCarLoan;
    //汽车品牌
    private String carBrand;
	//发布借款笔数
    private int releaseLoanNumber;
    //成功借款笔数
    private int successLoanNumber;
    //还清笔数
    private int payOffLoanNumber;
    //逾期次数
    private int overdueCount;
    //严重逾期次数
    private int seriousOverdueCount;
    //待还金额
    private String dhAmount;
    //逾期金额
    private String overdueAmount;
    //共计借入
    private String loanTotal;
    //待收金额
    private String waitRecoverAmount;
    //共计借出
    private String lendTotalAmount;
    //职位
    private String position;
    
    public BigDecimal getCreditGrade() {
        return creditGrade;
    }
    public void setCreditGrade(BigDecimal creditGrade) {
        this.creditGrade = creditGrade;
    }
    public BigDecimal getCreditScoreSum() {
        return creditScoreSum;
    }
    public void setCreditScoreSum(BigDecimal creditScoreSum) {
        this.creditScoreSum = creditScoreSum;
    }
    public double getCreditAmount() {
        return creditAmount;
    }
    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }
    public double getLaonCreditAmount() {
        return laonCreditAmount;
    }
    public void setLaonCreditAmount(double laonCreditAmount) {
        this.laonCreditAmount = laonCreditAmount;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getIsMarried() {
        return isMarried;
    }
    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }
    public String getWorkCity() {
        return workCity;
    }
    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }
    public String getCompanyIndustry() {
        return companyIndustry;
    }
    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }
    public String getCompanyScale() {
        return companyScale;
    }
    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }
    public String getWorkIncome() {
        return workIncome;
    }
    public void setWorkIncome(String workIncome) {
        this.workIncome = workIncome;
    }
    public String getNowWorkCompanyYear() {
        return nowWorkCompanyYear;
    }
    public void setNowWorkCompanyYear(String nowWorkCompanyYear) {
        this.nowWorkCompanyYear = nowWorkCompanyYear;
    }
    public String getGraduationSchool() {
        return graduationSchool;
    }
    public void setGraduationSchool(String graduationSchool) {
        this.graduationSchool = graduationSchool;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getEntranceYear() {
        return entranceYear;
    }
    public void setEntranceYear(String entranceYear) {
        this.entranceYear = entranceYear;
    }
    public String getIsBuyHouse() {
        return isBuyHouse;
    }
    public void setIsBuyHouse(String isBuyHouse) {
        this.isBuyHouse = isBuyHouse;
    }
    public String getIsBuyCar() {
        return isBuyCar;
    }
    public void setIsBuyCar(String isBuyCar) {
        this.isBuyCar = isBuyCar;
    }
    public String getBuyCarYear() {
        return buyCarYear;
    }
    public void setBuyCarYear(String buyCarYear) {
        this.buyCarYear = buyCarYear;
    }
    public String getIsHasMortgage() {
        return isHasMortgage;
    }
    public void setIsHasMortgage(String isHasMortgage) {
        this.isHasMortgage = isHasMortgage;
    }
    public String getIsHasCarLoan() {
        return isHasCarLoan;
    }
    public void setIsHasCarLoan(String isHasCarLoan) {
        this.isHasCarLoan = isHasCarLoan;
    }
    public int getReleaseLoanNumber() {
        return releaseLoanNumber;
    }
    public void setReleaseLoanNumber(int releaseLoanNumber) {
        this.releaseLoanNumber = releaseLoanNumber;
    }
    public int getSuccessLoanNumber() {
        return successLoanNumber;
    }
    public void setSuccessLoanNumber(int successLoanNumber) {
        this.successLoanNumber = successLoanNumber;
    }
    public int getPayOffLoanNumber() {
        return payOffLoanNumber;
    }
    public void setPayOffLoanNumber(int payOffLoanNumber) {
        this.payOffLoanNumber = payOffLoanNumber;
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
    public String getDhAmount() {
        return dhAmount;
    }
    public void setDhAmount(String dhAmount) {
        this.dhAmount = dhAmount;
    }
    public String getOverdueAmount() {
        return overdueAmount;
    }
    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }
    public String getLoanTotal() {
        return loanTotal;
    }
    public void setLoanTotal(String loanTotal) {
        this.loanTotal = loanTotal;
    }
    public String getLendTotalAmount() {
        return lendTotalAmount;
    }
    public void setLendTotalAmount(String lendTotalAmount) {
        this.lendTotalAmount = lendTotalAmount;
    }
    public String getWaitRecoverAmount() {
        return waitRecoverAmount;
    }
    public void setWaitRecoverAmount(String waitRecoverAmount) {
        this.waitRecoverAmount = waitRecoverAmount;
    }
    public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
