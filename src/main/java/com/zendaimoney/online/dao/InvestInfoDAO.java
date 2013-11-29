package com.zendaimoney.online.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.InvestInfoVO;
/**
 * @author 王腾飞
 *
 * description：
 */
public interface InvestInfoDAO extends PagingAndSortingRepository<InvestInfoVO ,Long>{
	
	
	List<InvestInfoVO> findByLoanId(Long loanInfoId);
	
	List<InvestInfoVO> findByUserId(Long userId);
	
}
