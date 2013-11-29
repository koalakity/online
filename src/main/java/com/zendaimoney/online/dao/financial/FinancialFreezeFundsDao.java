package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialFreezeFunds;

public interface FinancialFreezeFundsDao extends PagingAndSortingRepository<FinancialFreezeFunds, Long>{

	List<FinancialFreezeFunds> findByUserId(BigDecimal userId);
}
