/**
 * 
 */
package com.zendaimoney.online.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author 王腾飞
 *
 * description：
 */
public class UsersDAOImpl implements UsersDAOCustomer{
	
	@PersistenceContext
	private EntityManager em;
	

	/**
	 * 根据用户ID,更新isApproveCard状态
	 * 2013-2-18 下午3:37:13 by HuYaHui 
	 * @param userId
	 * @return
	 */
	public int updateIsApproveCardByUserIdAndIsApproveCard(Long userId,int isapprove_card){
		String sqlString="update users set isapprove_card=? where (isapprove_card=0 or isapprove_card is null) and user_id=?";
		return em.createNativeQuery(sqlString).setParameter(1, isapprove_card).setParameter(2, userId).executeUpdate();
	}
	
}
