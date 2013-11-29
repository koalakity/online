package com.zendaimoney.online.admin.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.LoanInfoAdminVO;

/**
 * Copyright (c) 2013 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2013-2-20 下午2:24:00 operation by: Ray description: 借款信息DAO （资金流水优化新建）
 */
public interface LoanInfoAdminDAO extends PagingAndSortingRepository<LoanInfoAdminVO, Long> {

	public List<LoanInfoAdminVO> findByStatus(Long status);

	public LoanInfoAdminVO findByLoanId(Long loanId);
}
