package com.zendaimoney.online.dao.loanmanagement;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedger;


public interface LoanManagementAcTLedgerDao extends PagingAndSortingRepository<LoanManagementAcTLedger, Long>, LoanManagementAcTLedgerDaoP{
	LoanManagementAcTLedger findByBusiTypeAndAccountLike(String busiType,String Account);
}
