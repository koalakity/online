/**
 * 
 */
package com.zendaimoney.online.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.AcTVirtualCashFlowVO;

/**
 * @author 王腾飞
 * 虚拟现金流水表DAO层
 *
 */
public interface AcTVirtualCashFlowDAO extends PagingAndSortingRepository<AcTVirtualCashFlowVO, Long>{
	/**
	 * 根据贷款分户ID查找还款计划
	 * @param acTLedgerLoanId
	 * @return 返回还款计划
	 */
	public List<AcTVirtualCashFlowVO> findByLoanId(Long acTLedgerLoanId);
}
