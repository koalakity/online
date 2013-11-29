package com.zendaimoney.online.dao.rate;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.ChannelVO;

public interface ChannelInfoDao extends PagingAndSortingRepository<ChannelVO, Long> {
	ChannelVO findById(Long id);
}
