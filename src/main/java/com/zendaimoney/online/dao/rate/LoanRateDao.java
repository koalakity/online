package com.zendaimoney.online.dao.rate;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.common.LoanRateVO;

public interface LoanRateDao extends PagingAndSortingRepository<LoanRateVO, Long> {
	LoanRateVO findById(Long id);
}
