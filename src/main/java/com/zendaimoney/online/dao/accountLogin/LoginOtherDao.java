package com.zendaimoney.online.dao.accountLogin;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.account.AccountUsers;

public interface LoginOtherDao extends PagingAndSortingRepository<AccountUsers, Long>{
	AccountUsers findByLoginName(String loginName);
	
	AccountUsers findByEmail(String email);
}
