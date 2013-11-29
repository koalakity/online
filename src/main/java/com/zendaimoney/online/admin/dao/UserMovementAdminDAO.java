package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.UserMovementAdminVO;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-18 上午9:47:28
 * operation by: Ray
 * description:
 */
public interface UserMovementAdminDAO  extends PagingAndSortingRepository<UserMovementAdminVO, Long>{

}
