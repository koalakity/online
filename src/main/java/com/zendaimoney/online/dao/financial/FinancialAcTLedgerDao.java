package com.zendaimoney.online.dao.financial;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialAcTLedger;

public interface FinancialAcTLedgerDao  extends PagingAndSortingRepository<FinancialAcTLedger, Long>{

	FinancialAcTLedger findByTotalAccountIdAndBusiType(Long totalAccountId,String busiType);
}
