package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.ID5Check;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-10-26 下午3:40:16
 * operation by:
 * description:
 */
public interface AdminID5CheckDao extends PagingAndSortingRepository<ID5Check, BigDecimal> ,JpaSpecificationExecutor<ID5Check>{

	/**
	 * @author Ray
	 * @date 2012-10-26 下午2:21:12
	 * @param id
	 * @return
	 * description:
	 */
	List<ID5Check> findByUserId(BigDecimal userId);
	
	/**
	 * @author Ray
	 * @date 2012-11-6 下午4:52:23
	 * @param cardId
	 * @return
	 * description:通过身份证号码查询ID5记录
	 */
	List<ID5Check> findByCardId(String cardId);
	
}
