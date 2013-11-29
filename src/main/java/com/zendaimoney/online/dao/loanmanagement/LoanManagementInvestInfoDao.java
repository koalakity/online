package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementInvestInfo;


public interface LoanManagementInvestInfoDao extends PagingAndSortingRepository<LoanManagementInvestInfo, BigDecimal >{
	LoanManagementInvestInfo findByInvestId(BigDecimal investId);
	
}
