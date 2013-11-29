package com.zendaimoney.online.dao.stationMessage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.stationMessage.StationMessageUserApprove;

public interface StationMessageUserApproveDao  extends PagingAndSortingRepository<StationMessageUserApprove, Long>{
	
	@Query("select a from StationMessageUserApprove a where a.userId = ?1 and a.proId = 1 or a.proId = 15")
	List<StationMessageUserApprove> findByUserIdAndProId(BigDecimal userId);
}
