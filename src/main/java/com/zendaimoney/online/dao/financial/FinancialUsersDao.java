package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialUsers;

public interface FinancialUsersDao extends PagingAndSortingRepository<FinancialUsers, Long>,FinancialUsersSelfDao{
	
	FinancialUsers findByUserId(BigDecimal userId);
}
