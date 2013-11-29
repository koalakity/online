package com.zendaimoney.online.dao.personal;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.personal.PersonalUsers;

public interface PersonalUsersDAO  extends PagingAndSortingRepository<PersonalUsers,Long>{
	public PersonalUsers findByUserId(BigDecimal userId);
}
