package com.zendaimoney.online.dao.homepage;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageUserInfoPerson;

public interface HomepageUserInfoPersonDAO extends PagingAndSortingRepository<HomepageUserInfoPerson, Long> {
//	HomepageUserInfoPerson findByUserId(BigDecimal userId);
}
