package com.zendaimoney.online.vo.borrowing;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.borrowing.BorrowingInvestInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;


public class InvestInfoListVO extends BorrowingUserCreditNote{
	
    /**
     * 
     */
    private static final long serialVersionUID = 91614695280027802L;
    
    //投标进度
    private double speedProgress;
	//投标笔数
    private int bidNumber;
    //借款用途
    private BigDecimal loanUse;
    //借款用途(str)
    private String loanUseStr;
    //剩余时间
    private String surplusTime;
    //用户信息
    private BorrowingUsers user;
    //是否显示留言
    private String isShowMsg;
    //借贷编号
    private BigDecimal loanId;
    //借款标题
    private String loanTitle;
    //借款金额
    private String loanAmount;
    //借款利率
    private String yearRate;
    //借款期限
    private BigDecimal loanPeriod;
    //还款周期
    private String payTerm;
    //还款方式
    private String paymentMethod;
    //月还本息
    private String principanInterestMonth;
    //还需
    private String surplusAmount;
    //借款状态
    private String status;
    
    
    //当前投标状态
    private List<BorrowingInvestInfo> investInfoList;
    
    public List<BorrowingInvestInfo> getInvestInfoList() {
        return investInfoList;
    }
    public void setInvestInfoList(List<BorrowingInvestInfo> investInfoList) {
        this.investInfoList = investInfoList;
    }
    public double getSpeedProgress() {
        return speedProgress;
    }
    public String getSurplusTime() {
        return surplusTime;
    }
   
    public void setSurplusTime(String surplusTime) {
        this.surplusTime = surplusTime;
    }
    public void setSpeedProgress(double speedProgress) {
        this.speedProgress = speedProgress;
    }
    public int getBidNumber() {
        return bidNumber;
    }
    public void setBidNumber(int bidNumber) {
        this.bidNumber = bidNumber;
    }
    public BigDecimal getLoanUse() {
        return loanUse;
    }
    public void setLoanUse(BigDecimal loanUse) {
        this.loanUse = loanUse;
    }
    public String getLoanUseStr() {
        return loanUseStr;
    }
    public void setLoanUseStr(String loanUseStr) {
        this.loanUseStr = loanUseStr;
    }
   
    public BorrowingUsers getUser() {
        return user;
    }
    public void setUser(BorrowingUsers user) {
        this.user = user;
    }
	public BigDecimal getLoanId() {
		return loanId;
	}
	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}
	public String getLoanTitle() {
		return loanTitle;
	}
	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public BigDecimal getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(BigDecimal loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getPayTerm() {
		return payTerm;
	}
	public void setPayTerm(String payTerm) {
		this.payTerm = payTerm;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPrincipanInterestMonth() {
		return principanInterestMonth;
	}
	public void setPrincipanInterestMonth(String principanInterestMonth) {
		this.principanInterestMonth = principanInterestMonth;
	}
	public String getSurplusAmount() {
		return surplusAmount;
	}
	public void setSurplusAmount(String surplusAmount) {
		this.surplusAmount = surplusAmount;
	}
	public String getIsShowMsg() {
		return isShowMsg;
	}
	public void setIsShowMsg(String isShowMsg) {
		this.isShowMsg = isShowMsg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
