package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinanciaUserApprove;

public interface FinanciaUserApproveDao extends PagingAndSortingRepository<FinanciaUserApprove, Long>{
	
	List<FinanciaUserApprove> findByUserIdAndReviewStatus(BigDecimal userId,BigDecimal reviewStatus);

	List<FinanciaUserApprove> findByReviewStatus(BigDecimal reviewStatus);
	
	/**
	 * 根据用户ID和状态，查询认证集合
	 * 2013-1-9 下午1:27:23 by HuYaHui
	 * @param reviewStatus
	 * @param userId
	 * @return
	 */
	List<FinanciaUserApprove> findByReviewStatusAndUserId(BigDecimal reviewStatus,BigDecimal userId);
	
}
