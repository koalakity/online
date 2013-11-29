package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.AcTFlowClassifyVO;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-19 上午10:24:35
 * operation by: Ray
 * description:
 */
public interface AcTFlowClassifyDAO extends PagingAndSortingRepository<AcTFlowClassifyVO, Long>,AcTFlowClassifyCustomerDAO{

}
