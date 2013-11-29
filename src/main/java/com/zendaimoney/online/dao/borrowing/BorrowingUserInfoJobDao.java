package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoJob;


public interface BorrowingUserInfoJobDao extends PagingAndSortingRepository<BorrowingUserInfoJob, Long>{

    @Query("select b from BorrowingUserInfoJob b where b.user.userId =?1")
	List<BorrowingUserInfoJob> findByUserId(BigDecimal userid);
    
    
}
