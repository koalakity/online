package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprovePro;

public interface BorrowingUserApproveDao  extends PagingAndSortingRepository<BorrowingUserApprove, Long>{
	
	List<BorrowingUserApprove> findByUserIdAndProId(BigDecimal userid, BorrowingUserApprovePro proid);
	
	List<BorrowingUserApprove> findByUserId(BigDecimal userid);
	
	@Query("select u from BorrowingUserApprove u where u.userId=:userId and u.proId.proId in(1,2,3,4)")
	List<BorrowingUserApprove> findPro(@Param("userId")BigDecimal userId);
	
	@Query("select u from BorrowingUserApprove u where u.userId=:userId")
	List<BorrowingUserApprove> findAllPro(@Param("userId")BigDecimal userId);
}
