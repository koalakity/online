package com.zendaimoney.online.dao.financial;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialSysMsg;

public interface FinancialSysMsgDao extends PagingAndSortingRepository<FinancialSysMsg, Long>{
	
}