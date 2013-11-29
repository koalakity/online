/**
 * 
 */
package com.zendaimoney.online.dao;

import java.math.BigDecimal;

import com.zendaimoney.online.entity.AcTLedgerVO;


/**
 * @author Administrator
 *
 */
public interface AcTLedgerDAOCustomer {

	/**
	 * 根据类型和用户查询AcTLedgerVO信息(账户模糊查询)
	 * 2013-2-19 下午1:59:07 by HuYaHui 
	 * @param busiType
	 * @param userId
	 * @return
	 */
	public AcTLedgerVO findByBusiTypeAndAccountLike(String busiType,long userId);
	
	/**
	 * 根据ID修改金额
	 * 2013-2-18 下午3:17:26 by HuYaHui
	 * @param amount
	 * @param id
	 * @return
	 */
	public int updateLoanManagementAcTLedgerById(BigDecimal amount,Long id);
	
	
}
