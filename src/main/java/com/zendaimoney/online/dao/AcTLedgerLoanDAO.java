/**
 * 
 */
package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.AcTLedgerLoanVO;

/**
 * @author 王腾飞
 * 贷款分户DAO层
 */
public interface AcTLedgerLoanDAO extends PagingAndSortingRepository<AcTLedgerLoanVO, Long>{

}
