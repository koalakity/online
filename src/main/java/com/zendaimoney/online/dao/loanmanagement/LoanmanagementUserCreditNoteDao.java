package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanmanagementUserCreditNote;


public interface LoanmanagementUserCreditNoteDao extends PagingAndSortingRepository<LoanmanagementUserCreditNote, BigDecimal>{
	
	LoanmanagementUserCreditNote findByUserId(BigDecimal userId);
}
