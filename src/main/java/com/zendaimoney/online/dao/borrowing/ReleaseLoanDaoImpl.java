package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;

@Component
public class ReleaseLoanDaoImpl implements ReleaseLoanDaoCustom {
		
	private  static String queryReleaseLoanInfo = "select l from BorrowingLoanInfo l where l.user.userId = :userId and l.releaseStatus = 2 order by l.loanId desc";
	private static final String QUERY_USER_BY_APPROVE = "select bua from BorrowingUserApprove bua where bua.userId =:userId  order by bua.userApproveId ASC";
	private  static String queryUserAvailableCredit = "select u.credit_amount from user_credit_note u where u.user_id =?";
	private  static String queryFrozenAmountByUserId = "select sum(l.loanAmount)  from BorrowingLoanInfo l where l.user=:user and (l.status =1 or l.status=2  or l.status=4 or l.status =6 or l.status =7 )";
	@PersistenceContext
	private EntityManager em;
	
	public int deleteByUserIdAndStatus(BigDecimal userId){
		return em.createNativeQuery("delete from LOAN_INFO where status=0 and user_id=?").setParameter(1, userId).executeUpdate();
	}
	
	public BorrowingLoanInfo findByUserId(BigDecimal userId) {
		List<BorrowingLoanInfo>  loanInfoList= em.createQuery(queryReleaseLoanInfo).setParameter("userId", userId).getResultList();
		BorrowingLoanInfo loanInfo = null;
		if(loanInfoList!=null&&loanInfoList.size()>0){
			loanInfo = loanInfoList.get(0);
		}
		return loanInfo;
	}
	
	//统计逾期金额
	public double  getBeOverdueAmount(BigDecimal userId){
		return 0;
	}
	
	//统计待收金额
	public double getWaitRecoverAmount(BigDecimal userId){
		return 0;
	}

    //获取认证信息
    public List<BorrowingUserApprove> getApproveList(BigDecimal userId) {
        return em.createQuery(QUERY_USER_BY_APPROVE).setParameter("userId", userId).getResultList();
    }

	//信用额度
	public BigDecimal getUserAvailableCredit(BigDecimal userId) {
		List<BigDecimal> list= em.createNativeQuery(queryUserAvailableCredit).setParameter(1, userId).getResultList();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			return list.get(0);
		}else{
			return BigDecimal.ZERO;
		}
	}

	//冻结资金
	public double getFrozenAmount(BorrowingUsers user) {
		List<Double> list = em.createQuery(queryFrozenAmountByUserId).setParameter("user", user).getResultList();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			return list.get(0);
		}else{
			return 0d;
		}
	}
	
}
