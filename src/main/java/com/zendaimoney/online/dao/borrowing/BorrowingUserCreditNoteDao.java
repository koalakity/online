package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;

public interface BorrowingUserCreditNoteDao  extends PagingAndSortingRepository<BorrowingUserCreditNote, Long>{

	@Query("select b from BorrowingUserCreditNote b where b.user.userId =?1")
	public BorrowingUserCreditNote findByUserId(BigDecimal userId);
}
