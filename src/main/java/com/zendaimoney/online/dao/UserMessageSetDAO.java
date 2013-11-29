package com.zendaimoney.online.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.UserMessageSetVO;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-19 下午2:06:44
 * operation by: Ray
 * description:
 */
public interface UserMessageSetDAO 	extends PagingAndSortingRepository<UserMessageSetVO,Long>{
		   List<UserMessageSetVO> findByUserId(Long userId); 
		   List<UserMessageSetVO> findByUserIdAndKindId(Long userId,Long kindId);
}
