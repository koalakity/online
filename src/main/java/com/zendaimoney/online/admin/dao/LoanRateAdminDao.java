package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.LoanRateAdminVO;

public interface LoanRateAdminDao extends PagingAndSortingRepository<LoanRateAdminVO, Long> {
	LoanRateAdminVO findById(Long id);
}
