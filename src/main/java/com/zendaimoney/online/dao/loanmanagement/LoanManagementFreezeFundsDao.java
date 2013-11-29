package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementFreezeFunds;


public interface LoanManagementFreezeFundsDao extends PagingAndSortingRepository<LoanManagementFreezeFunds, Long>{
	List<LoanManagementFreezeFunds> findByUserIdAndLoanIdAndFreezeKind(BigDecimal userId,BigDecimal loanId,String freezeKind);
}
