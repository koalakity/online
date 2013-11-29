package com.zendaimoney.online.dao.personal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.entity.personal.PersonalUsers;

@Component
public class PersonalDaoImpl implements PersonalCustomDao{
	
	private static String fandUserQuery = " select u from PersonalUsers u where u.userId=:userId  ";
	
	private static String fandAreaQuery=" select t.name,t.id from area t where t.parent_id=1 ";
	
	private static String fandCityQuery=" select t.name,t.id from area t where t.parent_id=:Provinceid order by t.id";

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 查询用户个人信息
	 * @param userId
	 * @return
	 */
	public PersonalUsers fandUsersInfo(BigDecimal userId){
		Query query =em.createQuery(fandUserQuery);
		query.setParameter("userId", userId);
		PersonalUsers u=(PersonalUsers) query.getResultList().get(0);
		return u;
	}
	/**
	 * 查询省信息
	 * @return
	 */
	public List fandArea(){
		Query query =em.createNativeQuery(fandAreaQuery);
		List  areaList= (ArrayList) query.getResultList();
		return areaList;
	}
	
	/**
	 * 查询市信息
	 * @return
	 */
	public List fandCity(BigDecimal Provinceid){
		Query query =em.createNativeQuery(fandCityQuery);
		query.setParameter("Provinceid", Provinceid);
		List  areaList= (ArrayList) query.getResultList();
		return areaList;
	}
	
}
