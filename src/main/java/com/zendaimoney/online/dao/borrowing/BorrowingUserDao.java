package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.borrowing.BorrowingUsers;

public interface BorrowingUserDao  extends PagingAndSortingRepository<BorrowingUsers, Long>{
	
	BorrowingUsers findByLoginName(String loginName);
	
	BorrowingUsers findByUserId(BigDecimal userId);
	
}
