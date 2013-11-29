package com.zendaimoney.online.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.LoanInfoVO;
/**
 * 
 * @author 王腾飞
 * 借款信息相关DAO层接口，继承spring data jpa
 *
 */
public interface LoanInfoDAO extends PagingAndSortingRepository<LoanInfoVO, Long>{
	
	/**
	 * @author Ray
	 * @date 2013-2-16 下午2:08:07
	 * @param loanId
	 * @return
	 * description:
	 */
	public LoanInfoVO findByLoanId(Long loanId);
	
	/**
	 * @author wangtf
	 * description 
	 * @param status
	 * @return
	 */
	public List<LoanInfoVO> findByStatus(Long status);
}
