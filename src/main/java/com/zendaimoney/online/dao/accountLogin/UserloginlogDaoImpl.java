package com.zendaimoney.online.dao.accountLogin;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.zendaimoney.online.common.DateUtil;

public class UserloginlogDaoImpl implements UserloginlogDaoCustom{
	
	@PersistenceContext
	private EntityManager em;
	
	private String QUERY_LOGS_BY_USER_ID="select u from AccountUserloginlog u where u.userId =:userId and u.loginStatus = 1 and u.loginTime >=:logintime";
	
	public int getErrCount(BigDecimal userid) {
		Query query = em.createQuery(QUERY_LOGS_BY_USER_ID);
		query.setParameter("userId", userid);
		query.setParameter("logintime", DateUtil.getDateBeforeOneDay());
		return query.getResultList().size();
	}


}
