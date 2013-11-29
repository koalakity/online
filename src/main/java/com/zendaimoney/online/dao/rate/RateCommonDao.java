package com.zendaimoney.online.dao.rate;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.common.Rate;

public interface RateCommonDao extends PagingAndSortingRepository<Rate, Long> {
	Rate findById(Long id);
}
