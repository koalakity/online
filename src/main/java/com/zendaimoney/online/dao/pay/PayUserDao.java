package com.zendaimoney.online.dao.pay;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.account.AccountUsers;

public interface PayUserDao extends PagingAndSortingRepository<AccountUsers, BigDecimal> {
	AccountUsers findByUserId(BigDecimal userId);
}
