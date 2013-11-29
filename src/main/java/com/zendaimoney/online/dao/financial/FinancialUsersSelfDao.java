package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.financial.FinancialUsers;

public interface FinancialUsersSelfDao{
	
	/**
	 * 根据用户ID,更新isApproveCard状态为2
	 * 2013-2-18 下午3:37:13 by HuYaHui 
	 * @param userId
	 * @return
	 */
	public int updateIsApproveCardByUserIdAndIsApproveCard(BigDecimal userId);
	
	/**
	 * 查询所有用户信息，和关联的用户认证信息
	 * 2012-12-4 下午4:02:18 by HuYaHui
	 * @return
	 */
	public List<FinancialUsers> findALLSQL();
}
