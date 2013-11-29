package com.zendaimoney.online.dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.zendaimoney.online.entity.AcTLedgerVO;

public class AcTLedgerDAOImpl implements AcTLedgerDAOCustomer{
	@PersistenceContext
	private EntityManager em;

	/**
	 * 根据ID修改金额
	 * 2013-2-18 下午3:17:26 by HuYaHui
	 * @param amount
	 * @param id
	 * @return
	 */
	public int updateLoanManagementAcTLedgerById(BigDecimal amount,Long id){
		String sql="update AC_T_LEDGER set amount=? where id=?";
		return em.createNativeQuery(sql).setParameter(1, amount).setParameter(2, id).executeUpdate();
	}
	/**
	 * 根据类型和用户查询AcTLedgerVO信息(账户模糊查询)
	 * 2013-2-19 下午1:59:07 by HuYaHui 
	 * @param busiType
	 * @param userId
	 * @return
	 */
	public AcTLedgerVO findByBusiTypeAndAccountLike(String busiType,long userId){
		String sqlString="select * from ac_t_ledger ac where ac.busi_type=? and ac.account like (select a.total_acct from ac_t_customer a where id=(select u.t_customer_id from users u where u.user_id=?))||'%'";
		return (AcTLedgerVO) em.createNativeQuery(sqlString, AcTLedgerVO.class).setParameter(1, busiType).setParameter(2, userId).getSingleResult();
	}
}
