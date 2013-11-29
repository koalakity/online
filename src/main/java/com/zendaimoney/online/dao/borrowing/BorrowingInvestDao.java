package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.borrowing.BorrowingInvestInfo;

public interface BorrowingInvestDao  extends PagingAndSortingRepository<BorrowingInvestInfo, Long>{
	
	List<BorrowingInvestInfo> findByUserUserId(BigDecimal userId); 
	
}
