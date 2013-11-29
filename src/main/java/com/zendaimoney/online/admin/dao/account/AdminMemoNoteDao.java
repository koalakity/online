package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountMemoNoteAdmin;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-11-5 上午10:25:18
 * operation by:
 * description:
 */
public interface AdminMemoNoteDao extends PagingAndSortingRepository<AccountMemoNoteAdmin, BigDecimal> ,JpaSpecificationExecutor<AccountMemoNoteAdmin>{

	
	/**
	 * @author Ray
	 * @date 2012-11-5 上午10:26:54
	 * @param workInfoId
	 * @return
	 * description:通过userId找到对应记录
	 */
	List<AccountMemoNoteAdmin> findByUserId(BigDecimal userId);
	
}
