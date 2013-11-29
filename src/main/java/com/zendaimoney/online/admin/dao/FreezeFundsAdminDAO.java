package com.zendaimoney.online.admin.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.FreezeFundsAdminVO;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-20 下午3:52:14
 * operation by: Ray
 * description:
 */
public interface FreezeFundsAdminDAO extends PagingAndSortingRepository<FreezeFundsAdminVO ,Long>{

	List<FreezeFundsAdminVO> findByUserIdAndLoanIdAndFreezeKind(Long userId,Long loanId,String freezeKind);
}
