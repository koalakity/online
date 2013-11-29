package com.zendaimoney.online.dao.loanmanagement;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementWebOperateLog;


public interface LoanManagementWebOperateLogDao extends PagingAndSortingRepository<LoanManagementWebOperateLog, Long>{
	
}
