package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountUserInfoJobAdmin;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-10-23 下午3:40:16
 * operation by:
 * description:
 */
public interface AdminUserInfoJobDao extends PagingAndSortingRepository<AccountUserInfoJobAdmin, BigDecimal> ,JpaSpecificationExecutor<AccountUserInfoJobAdmin>{

	
	/**
	 * @author Ray
	 * @date 2012-10-24 上午9:44:54
	 * @param workInfoId
	 * @return
	 * description:通过workInfoId找到对应记录
	 */
	List<AccountUserInfoJobAdmin> findByWorkInfoId(BigDecimal workInfoId);
	
}
