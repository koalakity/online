package com.zendaimoney.online.dao.myInvestment;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTLedgerFinance;

public interface MyInvestmentAcTLedgerFinanceDao extends PagingAndSortingRepository<MyInvestmentAcTLedgerFinance,Long>{
	MyInvestmentAcTLedgerFinance findById(Long id);
}
