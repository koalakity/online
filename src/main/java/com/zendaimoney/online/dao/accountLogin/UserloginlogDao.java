package com.zendaimoney.online.dao.accountLogin;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.account.AccountUserloginlog;

public interface UserloginlogDao extends PagingAndSortingRepository<AccountUserloginlog, Long>, UserloginlogDaoCustom {

}
