package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialLoanInfo;

public interface InverstInfoDao extends PagingAndSortingRepository<FinancialLoanInfo, Long>{
	List<FinancialLoanInfo> findByLoanId(BigDecimal loanId);
}
