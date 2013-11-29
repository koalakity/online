package com.zendaimoney.online.dao.myInvestment;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentUsers;

public interface MyInvestmentUserDao extends PagingAndSortingRepository<MyInvestmentUsers, Long>{

	MyInvestmentUsers findByUserId(BigDecimal userId);

}