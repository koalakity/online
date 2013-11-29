package com.zendaimoney.online.dao.homepage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageUserMovement;

public interface HomepageUserMovementDao extends PagingAndSortingRepository<HomepageUserMovement, Long> {
	List<HomepageUserMovement> findByUserIdOrderByHappenTimeDesc(BigDecimal userId);
}
