package com.zendaimoney.online.dao.myInvestment;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTFlow;

public interface MyInvestmentAcTFlowDao extends PagingAndSortingRepository<MyInvestmentAcTFlow, Long>{

	List<MyInvestmentAcTFlow> findByAccountAndAcctTitle(String account, String acctTitle);

}