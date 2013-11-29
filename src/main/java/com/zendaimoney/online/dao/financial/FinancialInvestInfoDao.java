package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialInvestInfo;

public interface FinancialInvestInfoDao extends PagingAndSortingRepository<FinancialInvestInfo, Long>{
	
	List<FinancialInvestInfo> findByLoanId(BigDecimal loanId);
	List<FinancialInvestInfo> findByUserId(BigDecimal userId);
	@Query("select invest from FinancialInvestInfo  invest where invest.userId =?1 order by invest.investTime desc")
	List<FinancialInvestInfo> checkInvestTime(BigDecimal userId);
	
	@Query("select count(loanId) from FinancialInvestInfo invest where invest.loanId =?1")
	Object findInvestTimeCount(BigDecimal userId);
}
