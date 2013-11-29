package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.AcTLedgerLoanAdminVO;
import com.zendaimoney.online.admin.entity.AcTVirtualCashFlowAdminVO;

public interface AcTVirtualCashFlowAdminDAO extends PagingAndSortingRepository<AcTVirtualCashFlowAdminVO, Long>{
	AcTVirtualCashFlowAdminVO findByActLedgerLoanAndCurrNum(AcTLedgerLoanAdminVO actLedgerLoan, Long currNum);
}
