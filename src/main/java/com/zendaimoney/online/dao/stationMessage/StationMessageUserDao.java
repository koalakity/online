package com.zendaimoney.online.dao.stationMessage;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.stationMessage.StationMessageUsers;

public interface StationMessageUserDao extends PagingAndSortingRepository<StationMessageUsers, BigDecimal> {
	StationMessageUsers findByUserId(BigDecimal userId);
}
