package com.zendaimoney.online.admin.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.UserMessageSetAdminVO;



/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-19 下午2:06:44
 * operation by: Ray
 * description:
 */
public interface UserMessageSetAdminDAO 	extends PagingAndSortingRepository<UserMessageSetAdminVO,Long>{
		   List<UserMessageSetAdminVO> findByUserId(Long userId); 
		   List<UserMessageSetAdminVO> findByUserIdAndKindId(Long userId,Long kindId);
}
