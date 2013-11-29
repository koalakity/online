package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.zendaimoney.online.entity.task.Repayment;


public class LoanManagementAcTLedgerDaoImpl implements LoanManagementAcTLedgerDaoP{
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * 根据ID修改金额
	 * 2013-2-18 下午3:17:26 by HuYaHui
	 * @param amount
	 * @param id
	 * @return
	 */
	public int updateLoanManagementAcTLedgerById(Double amount,Long id){
		String sql="update AC_T_LEDGER set amount=? where id=?";
		return em.createNativeQuery(sql).setParameter(1, amount).setParameter(2, id).executeUpdate();
	}
	
	
	@Override
	public List<Repayment> findTotalAcct() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String now=df.format(new Date());
		String sql="select i.loan_id,i.user_id,i.month_return_principalandinter,i.month_manage_cost,ac.total_acct from loan_info i,users u,Ac_t_Customer ac,AC_T_VIRTUAL_CASH_FLOW af where af.loan_id=i.ledger_loan_id and i.user_id=u.user_id and u.t_customer_id=ac.id  and  i.status=4 and af.repay_day = to_timestamp('"+now+"00:00:00', 'yyyy-mm-dd HH24-MI-SS') and af.repay_status=0";
//		String sql="select i.loan_id,i.user_id,i.month_return_principalandinter,i.month_manage_cost,ac.total_acct from loan_info i,users u,Ac_t_Customer ac where  i.user_id=u.user_id and u.t_customer_id=ac.id  and  i.status=4";
		Query query=em.createNativeQuery(sql);
		List  list=query.getResultList();
		List<Repayment> totalAcctList=new ArrayList<Repayment>();
		Repayment repayment=null;
		for(int i=0;i<list.size();i++){
			repayment=new Repayment();
			Object[] o=(Object[])list.get(i);
			repayment.setLoanId((BigDecimal) o[0]);
			repayment.setUserId((BigDecimal) o[1]);
			repayment.setMonthReturnPrincipalandinter(Double.valueOf(o[2].toString()));
			repayment.setMonthManageCost(Double.valueOf(o[3].toString()));
			repayment.setTotalAcct((String)o[4]);
			totalAcctList.add(repayment);
		}
		return totalAcctList;
	}
	
	

}
