package com.zendaimoney.online.vo.loanManagement;

import java.util.List;

public class LoanInfoListVO {
   
    private  List<LoanInfoDetailVO> loanInfoList;

    public List<LoanInfoDetailVO> getLoanInfoList() {
        return loanInfoList;
    }

    public void setLoanInfoList(List<LoanInfoDetailVO> loanInfoList) {
        this.loanInfoList = loanInfoList;
    }
    
    
}
