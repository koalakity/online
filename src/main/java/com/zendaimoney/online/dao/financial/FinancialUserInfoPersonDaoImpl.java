package com.zendaimoney.online.dao.financial;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.entity.financial.FinancialUserInfoPerson;
import com.zendaimoney.online.entity.financial.FinancialUsers;

@Component
public class FinancialUserInfoPersonDaoImpl implements FinancialUserInfoPersonDaoCustom {

	@PersistenceContext
	private EntityManager em;

	private String QUERY_PHONE_NO_HQL = "select p from FinancialUserInfoPerson p where  p.phoneNo =:phoneNo and p.user.isApprovePhone=1";
	private String QUERY_ID_CARD_HQL = "select p from FinancialUserInfoPerson p where  p.identityNo =:identityNo";
	private String QUERY_USER_INFO_PERSON_HQL = "select u from FinancialUsreInfoPerson u where u.user =:user";
	private String QUERY_PHONE_NO_BAK_HQL = "select p from FinancialUserInfoPerson p where  p.phonNoBak =:phonNoBak";

	// 手机号码是否被绑定
	public FinancialUserInfoPerson getUserPhoneInof(String phoneNo) {
		Query query = em.createQuery(QUERY_PHONE_NO_HQL);
		query.setParameter("phoneNo", phoneNo);
		if (query.getResultList().size() > 0) {
			return (FinancialUserInfoPerson) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	// 查看当前提交的手机号与接收验证码的手机是否一致
	public List<FinancialUserInfoPerson> getUserPhoneBakInof(String phonNoBak) {
		Query query = em.createQuery(QUERY_PHONE_NO_BAK_HQL);
		query.setParameter("phonNoBak", phonNoBak);
		if (query.getResultList().size() > 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	// 身份证号码是否被绑定
	public FinancialUserInfoPerson getUserIdCardInfo(String idCard) {
		Query query = em.createQuery(QUERY_ID_CARD_HQL);
		query.setParameter("identityNo", idCard);
		if (query.getResultList().size() > 0) {
			return (FinancialUserInfoPerson) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	// 根据userId查询用户个人信息
	public FinancialUserInfoPerson findByUserId(FinancialUsers user) {
		Query query = em.createQuery(QUERY_USER_INFO_PERSON_HQL);
		query.setParameter("user", user);
		return (FinancialUserInfoPerson) query.getResultList().get(0);
	}
}
