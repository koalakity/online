/**
 * 
 */
package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.AcTLedgerVO;

/**
 * @author Administrator
 *
 */
public interface AcTLedgerDAO extends PagingAndSortingRepository<AcTLedgerVO, Long>,AcTLedgerDAOCustomer{
	
	
	/**
	 * 根据类型和账号查询信息
	 * 2013-2-19 下午1:59:07 by HuYaHui 
	 * @param busiType
	 * @param Account
	 * @return
	 */
	public AcTLedgerVO findByBusiTypeAndAccount(String busiType,String Account);
	/**
	 * @param userId
	 * @param busiType
	 * @return 根据总账ID 和业务类型查找分账
	 */
	public AcTLedgerVO findByTotalAccountIdAndBusiType(Long totalAccountId,String busiType);
	
	/**
	 * @param account
	 * @return 根据账号查询
	 */
	public AcTLedgerVO findByAccount(String account);
}
