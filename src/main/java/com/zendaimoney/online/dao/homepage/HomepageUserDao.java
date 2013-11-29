package com.zendaimoney.online.dao.homepage;


import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageUsers;



public interface HomepageUserDao extends PagingAndSortingRepository<HomepageUsers, Long>,HomepageCustomDao {
	HomepageUsers findByUserId(BigDecimal userId);
}
