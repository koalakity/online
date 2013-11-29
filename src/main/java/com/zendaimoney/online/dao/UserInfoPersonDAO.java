package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.UserInfoPersonVO;

/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-19 下午2:17:42
 * operation by: Ray
 * description:
 */
public interface UserInfoPersonDAO extends PagingAndSortingRepository<UserInfoPersonVO, Long>{
	/**
	 * @author Ray
	 * @date 2013-2-19 下午2:19:30
	 * @param userId
	 * @return
	 * description:通过userId查找person对象
	 */
	UserInfoPersonVO findByUserId(Long userId);

}
