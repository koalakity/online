package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-10-23 下午3:40:16
 * operation by:
 * description:
 */
public interface AdminUserInfoPersonDao extends PagingAndSortingRepository<AccountUserInfoPersonAdmin, BigDecimal> ,JpaSpecificationExecutor<AccountUserInfoPersonAdmin>{

	
	 /**
	    * @author Ray
	    * @date 2012-10-23 下午2:46:35
	    * @param infoId
	    * @return
	    * description:通过infoId找到对应记录
	    */
	   List<AccountUserInfoPersonAdmin> findByInfoId(BigDecimal infoId);
	   
	   /**
	    * @author Ray
	    * @date 2012-11-1 上午10:47:41
	    * @param userId
	    * @return
	    * description:通过userId找到对应记录
	    */
	   List<AccountUserInfoPersonAdmin> findByUserId(BigDecimal userId);
	
}
