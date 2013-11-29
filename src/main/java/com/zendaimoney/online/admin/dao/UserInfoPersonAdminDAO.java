package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.UserInfoPersonAdminVO;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-19 下午2:17:42
 * operation by: Ray
 * description:
 */
public interface UserInfoPersonAdminDAO extends PagingAndSortingRepository<UserInfoPersonAdminVO, Long>{
	/**
	 * @author Ray
	 * @date 2013-2-19 下午2:19:30
	 * @param userId
	 * @return
	 * description:通过userId查找person对象
	 */
	UserInfoPersonAdminVO findByUserId(Long userId);

}
