package com.zendaimoney.online.dao.personal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

@Component
public class BaseDAO {
	@PersistenceContext
	private EntityManager em;
	protected List query(String sql){
		Query query=em.createQuery(sql);
		return query.getResultList();
	}
}
