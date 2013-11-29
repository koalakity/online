package com.zendaimoney.online.dao.accountLogin;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.account.AccountUsers;


/**
 * 登录验证的Dao interface.
 * 
 * @author yijc
 */
public interface LoginDao extends PagingAndSortingRepository<AccountUsers, Long>, LoginDaoCustom {
	
	AccountUsers findByLoginName(String loginName);

}