package com.zendaimoney.online.dao.accountLogin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.entity.account.AccountUsers;

@Component
public class LoginDaoImpl implements LoginDaoCustom {

	private static String loginInfoQueryByNameOrEmail = "select u from AccountUsers u where  u.email =:email  and u.loginPassword =:loginPassword";
	private static String loginInfoQueryByTel = "select u from AccountUsers u,AccountUserInfoPerson p where u.userId = p.user.userId and p.phoneNo =:phoneNo and u.loginPassword =:loginPassword ";
	private static String queryUserByLoginName = "select u from AccountUsers u where   u.email =:email";
	private static String queryUserByPhoneNo = "select u from AccountUsers u,AccountUserInfoPerson p  where u.userId=p.user.userId  and p.phoneNo =:phoneNo";
	private  static String queryUserIsLender = "select u from AccountUsers u where u.userId = :userId and u.isApprovePhone = 1 and u.isApproveCard = 1";
	
	
	private  static String queryUserIsLenderExceptPhone = "select u from AccountUsers u where u.userId = :userId and (u.isApprovePhone = 0 or u.isApprovePhone is null) and u.isApproveCard = 1";
	
	/**
	 * 身份证验证，手机没验证
	 * 2013-1-6 上午9:41:31 by HuYaHui 
	 * @param userId
	 * @return
	 * 			true|false
	 */
	public boolean queryUserIsLenderExceptPhone(BigDecimal userId){
		return !em.createQuery(queryUserIsLenderExceptPhone).setParameter("userId", userId).getResultList().isEmpty();
	}

	
	@PersistenceContext
	private EntityManager em;
	
	public List<AccountUsers> getAccountInfoByName(String loginName, String loginPassword) {
		Query query = em.createQuery(loginInfoQueryByNameOrEmail);
		query.setParameter("email", loginName);
		query.setParameter("loginPassword", loginPassword);
		return (List<AccountUsers>) query.getResultList();
	}
	
	public List<AccountUsers> getAccountInfoByTel(String loginName, String loginPassword) {
		Query query = em.createQuery(loginInfoQueryByTel);
		query.setParameter("phoneNo", loginName);
		query.setParameter("loginPassword", loginPassword);
		return (List<AccountUsers>) query.getResultList();
	}

	public AccountUsers getAccountInfo(String loginName, String loginPassword) {
		
	    AccountUsers user = null;
		List accountList1 = new ArrayList<AccountUsers>();
		List accountList2 = new ArrayList<AccountUsers>();
		accountList1 = getAccountInfoByName(loginName,loginPassword);
		if(accountList1.size()==0 || accountList2 == null){
			accountList2 = getAccountInfoByTel(loginName,loginPassword);
			if(accountList2.size()==0 || accountList2 == null){
				return null;
			}else{
				return (AccountUsers)accountList2.get(0);
			}
		}else{
			user = (AccountUsers) accountList1.get(0);
		}
		return user;
	}

	/**
	 * 2012.11.15 
	 * 修改只能通过Email来登录，去除手机登录
	 * 原因：手机绑定不是唯一性
	 */
	public AccountUsers findByLoginName(String loginName) {
		Query query1 = em.createQuery(queryUserByLoginName);
		query1.setParameter("email", loginName);
		if(query1.getResultList().size()==0 ||query1.getResultList()==null ){
//			Query query2 = em.createQuery(queryUserByPhoneNo);
//			query2.setParameter("phoneNo", loginName);
//			if(query2.getResultList().size()==0 ||query1.getResultList()==null ){
				return null;
//			}else{
//				return (AccountUsers) query2.getResultList().get(0);
//			}
		}else{
			return (AccountUsers) query1.getResultList().get(0);
		}
	}
	
	public boolean findIsLender(BigDecimal userId){
		return !em.createQuery(queryUserIsLender).setParameter("userId", userId).getResultList().isEmpty();
	}

}
