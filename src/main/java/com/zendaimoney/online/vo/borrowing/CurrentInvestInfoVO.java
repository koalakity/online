package com.zendaimoney.online.vo.borrowing;

import java.util.List;

import com.zendaimoney.online.entity.borrowing.BorrowingInvestInfo;

public class CurrentInvestInfoVO {

    //目前投标总额
    private String currentBidAmount;
    //剩余投标金额
    private String surplusAmount;
    //剩余投标时间
    private String surplusTime;
    //当前标投标信息
    private List<BorrowingInvestInfo> investInfoList;
    public String getCurrentBidAmount() {
        return currentBidAmount;
    }
    public void setCurrentBidAmount(String currentBidAmount) {
        this.currentBidAmount = currentBidAmount;
    }
    public String getSurplusAmount() {
        return surplusAmount;
    }
    public void setSurplusAmount(String surplusAmount) {
        this.surplusAmount = surplusAmount;
    }
    public String getSurplusTime() {
        return surplusTime;
    }
    public void setSurplusTime(String surplusTime) {
        this.surplusTime = surplusTime;
    }
    public List<BorrowingInvestInfo> getInvestInfoList() {
        return investInfoList;
    }
    public void setInvestInfoList(List<BorrowingInvestInfo> investInfoList) {
        this.investInfoList = investInfoList;
    }
    
}
