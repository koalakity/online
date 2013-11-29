package com.zendaimoney.online.dao.personal;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.personal.PersonalPrivateProprietor;

public interface PersonalPrivateProprietorDao extends PagingAndSortingRepository<PersonalPrivateProprietor,Long>{
	
	PersonalPrivateProprietor findByUserId(BigDecimal userId);
}
