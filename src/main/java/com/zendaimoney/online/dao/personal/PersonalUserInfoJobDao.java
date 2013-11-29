package com.zendaimoney.online.dao.personal;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.personal.PersonalUserInfoJob;

public interface PersonalUserInfoJobDao extends PagingAndSortingRepository<PersonalUserInfoJob,Long>{
	
	PersonalUserInfoJob findByUserId(BigDecimal userId);
}
