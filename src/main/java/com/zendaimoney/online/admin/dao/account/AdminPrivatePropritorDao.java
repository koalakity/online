package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountPrivatePropritorAdmin;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-10-23 下午3:40:16
 * operation by:
 * description:
 */
public interface AdminPrivatePropritorDao extends PagingAndSortingRepository<AccountPrivatePropritorAdmin, BigDecimal> ,JpaSpecificationExecutor<AccountPrivatePropritorAdmin>{

	/**
	 * @author Ray
	 * @date 2012-10-24 上午10:32:36
	 * @param id
	 * @return
	 * description:通过ID获取私营业主对象
	 */
	List<AccountPrivatePropritorAdmin> findById(BigDecimal id);
	
	List<AccountPrivatePropritorAdmin> findByUserId(BigDecimal userId);
	
}
