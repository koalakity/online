package com.zendaimoney.online.dao.loanmanagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class LoanmanagementLoanDaoImpl implements LoanmanagementLoanCustomDao{
	
	@PersistenceContext
	private EntityManager em;
	
	private String QUERY_LOGS_BY_USER_ID="select ACCOUNT_SEQ.nextval from dual";
	
	public String  getSequence() {
		Query query = em.createNativeQuery(QUERY_LOGS_BY_USER_ID);
		return query.getResultList().get(0).toString();
	}


}
