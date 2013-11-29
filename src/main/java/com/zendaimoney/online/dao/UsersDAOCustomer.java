/**
 * 
 */
package com.zendaimoney.online.dao;


/**
 * @author 王腾飞
 *
 * description：
 */
public interface UsersDAOCustomer {
	

	/**
	 * 根据用户ID,更新isApproveCard状态
	 * 2013-2-18 下午3:37:13 by HuYaHui 
	 * @param userId
	 * @return
	 */
	public int updateIsApproveCardByUserIdAndIsApproveCard(Long userId,int isapprove_card);
}
