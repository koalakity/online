package com.zendaimoney.online.dao.borrowing;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;


public interface BorrowingUserInfoPersonDao extends PagingAndSortingRepository<BorrowingUserInfoPerson, Long>, BorrowingUserInfoPersonCustomDao{
//	@Query("select a from Users a where a.accountId = ?1") 
//	List<BorrowingUserInfoPerson> findByUserId(BigDecimal userid);
}
