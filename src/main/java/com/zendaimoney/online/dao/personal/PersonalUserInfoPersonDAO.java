package com.zendaimoney.online.dao.personal;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.personal.PersonalUserInfoPerson;

public interface PersonalUserInfoPersonDAO extends PagingAndSortingRepository<PersonalUserInfoPerson,Long>{
	public PersonalUserInfoPerson findByUserId(BigDecimal userId);
}
