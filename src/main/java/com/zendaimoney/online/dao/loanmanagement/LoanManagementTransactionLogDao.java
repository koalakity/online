package com.zendaimoney.online.dao.loanmanagement;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementTransactionLog;


public interface LoanManagementTransactionLogDao extends PagingAndSortingRepository<LoanManagementTransactionLog, Long>{
	
}
