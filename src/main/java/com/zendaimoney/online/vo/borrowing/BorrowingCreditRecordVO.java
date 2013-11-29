package com.zendaimoney.online.vo.borrowing;

import java.util.List;

import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;

public class BorrowingCreditRecordVO {
    
    
    //认证信息列表
    private List<BorrowingUserApprove> userApproveList;

    public List<BorrowingUserApprove> getUserApproveList() {
        return userApproveList;
    }

    public void setUserApproveList(List<BorrowingUserApprove> userApproveList) {
        this.userApproveList = userApproveList;
    }
    
}
