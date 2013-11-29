package com.zendaimoney.online.dao.register;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.register.RegisterUsers;

public interface UserDao  extends PagingAndSortingRepository<RegisterUsers, Long>,UserCustomDao{
	
	RegisterUsers findByLoginName(String loginName);
	
	RegisterUsers findByUserId(BigDecimal userId);
	
	List<RegisterUsers> findByEmail(String email);
	
	RegisterUsers findByLoginNameAndEmail(String loginName,String email);
}
