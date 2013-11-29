package com.zendaimoney.online.dao.loanmanagement;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTCustomer;


public interface LoanmanagementAcTCustomerDao extends PagingAndSortingRepository<LoanManagementAcTCustomer, Long>{

	LoanManagementAcTCustomer findById(Long id);
	
}
