package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.UserCreditNoteAdmin;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-10-23 下午3:40:16
 * operation by:
 * description:
 */
public interface AdminUserCreditNoteDao extends PagingAndSortingRepository<UserCreditNoteAdmin, BigDecimal> ,JpaSpecificationExecutor<UserCreditNoteAdmin>{

	
	 /**
	    * @author Ray
	    * @date 2012-10-23 下午2:46:35
	    * @param infoId
	    * @return
	    * description:通过creditId找到对应记录
	    */
	   List<UserCreditNoteAdmin> findByCreditId(BigDecimal infoId);
	
}
