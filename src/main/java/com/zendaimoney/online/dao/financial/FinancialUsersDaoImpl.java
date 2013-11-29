package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.entity.financial.FinancialUserCreditNote;
import com.zendaimoney.online.entity.financial.FinancialUsers;

@Component
public class FinancialUsersDaoImpl implements FinancialUsersSelfDao{
	
	@PersistenceContext
	private EntityManager em;
	

	/**
	 * 根据用户ID,更新isApproveCard状态为2
	 * 2013-2-18 下午3:37:13 by HuYaHui 
	 * @param userId
	 * @return
	 */
	public int updateIsApproveCardByUserIdAndIsApproveCard(BigDecimal userId){
		String sqlString="update users set isapprove_card=2 where (isapprove_card=0 or isapprove_card is null) and user_id=?";
		return em.createNativeQuery(sqlString).setParameter(1, userId).executeUpdate();
	}
	
	/**
	 * 查询所有用户信息，和关联的用户认证信息
	 * 2012-12-4 下午4:02:18 by HuYaHui
	 * @return
	 */
	@Override
	public List<FinancialUsers> findALLSQL() {
		String sql="select u.user_id,u.login_name,c.credit_grade from users u left join user_credit_note c on u.user_id=c.user_id";
		javax.persistence.Query query = em.createNativeQuery(sql);
		List<FinancialUsers> rtnList=new ArrayList<FinancialUsers>();
		List<Object[]> list=query.getResultList();
		for(Object [] objArrys:list){
			FinancialUsers user=new FinancialUsers();
			user.setUserId(BigDecimal.valueOf(Long.valueOf(objArrys[0]+"")));
			user.setLoginName(objArrys[1]+"");
			Object obj3=objArrys[2];
			if(obj3!=null && !obj3.toString().equals("")){
				FinancialUserCreditNote userCreditNote=new FinancialUserCreditNote();
				userCreditNote.setCreditGrade(BigDecimal.valueOf(Long.valueOf(obj3+"")));
				user.setUserCreditNote(userCreditNote);
			}
			rtnList.add(user);
		}
		return rtnList;
	}
}
