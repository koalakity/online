package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;

public class BorrowingUserInfoPersonDaoImpl implements BorrowingUserInfoPersonCustomDao{
	@PersistenceContext
	private EntityManager em;
	
	private String QUERY_USERID_HQL = "select u from  BorrowingUserInfoPerson u where u.user.userId = :userId";
	
	public List<BorrowingUserInfoPerson> findUserId(BigDecimal userId){
		Query  query = em.createQuery(QUERY_USERID_HQL);
		query.setParameter("userId", userId);
		return query.getResultList();
	}
}
