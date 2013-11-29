package com.zendaimoney.online.dao.myInvestment;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTLedger;

public interface MyInvestmentAcTLedgerDao extends PagingAndSortingRepository<MyInvestmentAcTLedger, Long>{

	MyInvestmentAcTLedger findByTotalAccountIdAndBusiType(Long totalAccountId, String busiType);

}