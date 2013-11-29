package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementLoanInfo;


/**
 * 后台放款的Dao interface.
 * 
 * @author yijc
 */
public interface LoanmanagementLoanInfoDao extends PagingAndSortingRepository<LoanManagementLoanInfo, BigDecimal>{
	public List<LoanManagementLoanInfo> findByStatus(BigDecimal status);
}