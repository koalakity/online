package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.AcTLedgerAdminVO;


/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-2-20 下午3:18:34
 * operation by: Ray
 * description:
 */
public interface AcTLedgerAdminDAO extends PagingAndSortingRepository<AcTLedgerAdminVO, Long>{
	
	/**
	 * @author Ray
	 * @date 2013-2-20 下午3:17:51
	 * @param busiType
	 * @param totalAccountId
	 * @return
	 * description:根据总账ID和账户类型查询分账信息
	 */
	public AcTLedgerAdminVO findByBusiTypeAndTotalAccountId(String busiType,Long totalAccountId);
	
	/**
	 * @author Ray
	 * @date 2013-2-26 上午10:50:12
	 * @param busiType
	 * @param account
	 * @return
	 * description:
	 */
	public AcTLedgerAdminVO  findByBusiTypeAndAccountLike(String busiType,String Account);
	
	/** 根据用户账号查询
	 * @param account
	 * @return
	 */
	public AcTLedgerAdminVO findByAccount(String account);
	
}
