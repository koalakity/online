package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.borrowing.BorrowingFreezeFunds;

public interface BorrowingFreezeFundsDao  extends PagingAndSortingRepository<BorrowingFreezeFunds, Long>{
	
	public List<BorrowingFreezeFunds> findByUserIdAndFreezeStatus(BigDecimal userId,String freezeStatus);

}
