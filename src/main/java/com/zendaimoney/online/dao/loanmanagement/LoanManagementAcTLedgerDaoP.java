package com.zendaimoney.online.dao.loanmanagement;

import java.util.List;

import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedger;
import com.zendaimoney.online.entity.task.Repayment;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-12-5 上午9:58:00
 * operation by:
 * description: 自动还款任务接口
 */
public interface LoanManagementAcTLedgerDaoP {
	
	/**
	 * @author Ray
	 * @date 2012-12-5 上午10:01:57
	 * @return
	 * description:获取需要还款的总账号
	 */
	List<Repayment> findTotalAcct();
	
	
	/**
	 * 根据ID修改金额
	 * 2013-2-18 下午3:17:26 by HuYaHui
	 * @param amount
	 * @param id
	 * @return
	 */
	public int updateLoanManagementAcTLedgerById(Double amount,Long id);
	
	

}
