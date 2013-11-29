package com.zendaimoney.online.dao.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 公共的Dao interface.
 * 
 * @author yijc
 */
public class CommonDaoImpl implements CommonCustomDao{
    
	@PersistenceContext
	private EntityManager em;
	
	private String QUERY_LOGS_BY_USER_ID="select FLOW_SEQ.nextval from dual";
	
	public Long getSequenceByName(String seqName){
		Query query = em.createNativeQuery("select "+seqName+".Nextval from dual");
		return Long.valueOf(query.getSingleResult()+"");
	}
	
	public String  getFlowSeq() {
		Query query = em.createNativeQuery(QUERY_LOGS_BY_USER_ID);
		return query.getResultList().get(0).toString();
	}
	
}
