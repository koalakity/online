package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;


/**
 * 发布借款的Dao interface.
 * 
 * @author yijc
 */
public interface ReleaseLoanDao extends PagingAndSortingRepository<BorrowingLoanInfo, Long>,ReleaseLoanDaoCustom{
    BorrowingLoanInfo findByLoanId(BigDecimal loanId);
    @Query("select u from BorrowingUsers u where u.userId =?1")
    BorrowingUsers getUserByUserId(BigDecimal userId);
    
    List<BorrowingLoanInfo> findByUserAndStatus(BorrowingUsers user,BigDecimal status);
    List<BorrowingLoanInfo> findByUserUserId(BigDecimal userId);
    
}