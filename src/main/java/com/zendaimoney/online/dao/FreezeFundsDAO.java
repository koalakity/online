/**
 *Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 *This software is published under the terms of the ZENDAI
 *Software
 *@author Ray
 *@date 2013-2-18下午3:50:12
 *operation by:Ray
 *description:
 */
package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.FreezeFundsVO;

public interface FreezeFundsDAO extends PagingAndSortingRepository<FreezeFundsVO ,Long>{
	
	public FreezeFundsVO findByRechargeId(Long rechargeId);

}
