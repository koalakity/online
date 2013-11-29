package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;

public interface ReleaseLoanDaoCustom {

	BorrowingLoanInfo findByUserId(BigDecimal userId);
	List<BorrowingUserApprove>  getApproveList(BigDecimal userId);
	BigDecimal getUserAvailableCredit(BigDecimal userId);
	double getFrozenAmount(BorrowingUsers user);
	
	/**
	 * 根据用户ID删除状态为0的记录
	 * 2013-2-19 上午10:30:46 by HuYaHui 
	 * @param userId
	 * @return
	 */
	public int deleteByUserIdAndStatus(BigDecimal userId);
}
