package com.zendaimoney.online.dao.financial;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialAcTLedger;

public interface LedgerInfoDao  extends PagingAndSortingRepository<FinancialAcTLedger, Long>{
	
}
