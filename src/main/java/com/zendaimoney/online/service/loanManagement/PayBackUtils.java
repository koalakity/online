/*
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * CreateDate : 2013-1-22 下午3:42:06
 * CreateBy   : Administrator
 * Comment    : 
 */
package com.zendaimoney.online.service.loanManagement;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author 王腾飞
 * @date: 2013-1-22 下午3:42:06
 * operation by:
 * description:偿还借款辅助类
 */
@Component
public class PayBackUtils {
	@PersistenceContext
	private EntityManager em;
	/**
	 * 
	 * @author 王腾飞
	 * @date 2013-1-23 上午10:42:59
	 * @param amt
	 * @param editDate
	 * @param editUserId
	 * @param repayStatus
	 * @param id
	 * @return
	 * description:
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public int updateAcVirtualCashFlow(double amt,Date editDate,Long editUserId,int repayStatus,double currOverdueFines,double currOverdueInterest,int overdueDays,Long id){
		int count=0;
		Query update = em.createNativeQuery("update AC_T_VIRTUAL_CASH_FLOW set " +
											"AMT=? , " +
											"EDIT_DATE=? , " +
											"EDIT_USER_ID=? ," +
											"REPAY_STATUS=? ," +
											"over_Due_Fine_Amount=? " +
											",over_Due_Interest_Amount=?," +
											"over_Due_Days=?" +
											"where id=? " +
											"and REPAY_STATUS=0 and trim(EDIT_USER_ID) is null");
		update.setParameter(1, amt);
		update.setParameter(2, editDate);
		update.setParameter(3, editUserId);
		update.setParameter(4, repayStatus);
		update.setParameter(5, currOverdueFines);
		update.setParameter(6, currOverdueInterest);
		if(overdueDays==0){
			update.setParameter(7, "");
		}else{
			update.setParameter(7, overdueDays);
		}
		
		update.setParameter(8, id);
		count=update.executeUpdate();
		return count;
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2013-1-24 上午10:07:23
	 * @param status
	 * @param isAdvanced
	 * @param payAmount
	 * @param editTime
	 * @param investId
	 * @param num
	 * @return
	 * description:
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public int updateOverdueClaims(BigDecimal status,BigDecimal isAdvanced,double payAmount,Date editTime,BigDecimal investId, Long num){
		int count = 0;
		String sqlString = "update OVERDUE_CLAIMS set " +
										"STATUS=? , " +
										"IS_ADVANCED=? ," +
										"PAY_AMOUNT=? ," +
										"EDIT_TIME=? " +
										"where INVEST_ID=? and NUM=? and STATUS=1 and PAY_AMOUNT=0";
		Query update = em.createNativeQuery(sqlString);
		update.setParameter(1, status);
		update.setParameter(2, isAdvanced);
		update.setParameter(3, payAmount);
		update.setParameter(4, editTime);
		update.setParameter(5, investId);
		update.setParameter(6, num);
		count=update.executeUpdate();
		return count;
	}
}
