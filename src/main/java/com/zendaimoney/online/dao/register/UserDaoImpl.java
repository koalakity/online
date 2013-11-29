package com.zendaimoney.online.dao.register;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.zendaimoney.online.entity.register.RegisterUsers;

public class UserDaoImpl  implements UserCustomDao{
	
	@PersistenceContext
	private EntityManager em;

	public List<RegisterUsers> findByEmailLike(String email){
		String nsql = "select u from RegisterUsers u where u.email like '"+email+"%'";
		List<RegisterUsers> list = em.createQuery(nsql).getResultList();
		return list;
	}

}
