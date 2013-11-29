package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.UsersAdminVO;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-20 下午3:20:39
 * operation by: Ray
 * description:
 */
public interface UsersAdminDAO extends PagingAndSortingRepository<UsersAdminVO, Long>{
	/**
	 * @author Ray
	 * @date 2013-2-20 下午3:20:36
	 * @param userId
	 * @return
	 * description:根据userId查询用户信息
	 */
	public UsersAdminVO findByUserId(Long userId);
}
