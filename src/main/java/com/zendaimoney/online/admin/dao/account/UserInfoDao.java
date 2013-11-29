package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.admin.entity.fundDetail.AcTLedgerAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementLoanDao;
import com.zendaimoney.online.dao.personal.BaseDAO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedgerLoan;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTVirtualCashFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementLoanInfo;




@Component
@SuppressWarnings({"unchecked","rawtypes"})
public class UserInfoDao  extends BaseDAO{
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private LoanmanagementLoanDao loanManagementDao;
	
	
	public UsersVO getUsersVOByUserId(long userId){
		return (UsersVO) em.createQuery("select u from UsersVO u where u.userId=?").setParameter(1, userId).getResultList().get(0);
	}
	
	/**
	 * 查询用户总账号ID
	 * @param userId
	 * @return
	 */
	private String getTotalAcctId(BigDecimal userId){
		//String sql="select c.total_acct from USERS u,AC_T_CUSTOMER c where  u.t_customer_id=c.id and u.user_id=:userId";
		String sql="select u.t_customer_id from users u where u.user_id='"+userId+"'";
		Query query=em.createNativeQuery(sql);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			BigDecimal t_customer_id=(BigDecimal)l.get(0);
			if(t_customer_id==null){
//				throw new RuntimeException("用户表与核心客户信息表数据不同步,用户ID："+userId);
			}
			return t_customer_id.toString();
		}
		return "";
	}
	/**
	 * 查询投标冻结金额
	 * @param userId
	 * @return
	 */
	public double getFreezeFundsFinancialByUserId(BigDecimal userId){
		String sql="select sum(t.freezeMoney) as amount from FreezeFundsAdmin t where t.userId=:userId and t.freezeStatus=1 and t.freezeKind=1";
		Query query=em.createQuery(sql);
		query.setParameter("userId", userId);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			BigDecimal amount=(BigDecimal)l.get(0);
			return amount.doubleValue();
		}
		return 0;
	}
	/**
	 * 查询提现冻结金额
	 * @param userId
	 * @return
	 */
	public double getFreezeFundsWithdrawByUserId(BigDecimal userId){
		String sql="select sum(t.freezeMoney) as amount from FreezeFundsAdmin t where t.userId=:userId and t.freezeStatus=1 and t.freezeKind=2";
		Query query=em.createQuery(sql);
		query.setParameter("userId", userId);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			BigDecimal amount=(BigDecimal)l.get(0);
			return amount.doubleValue();
		}
		return 0;
	}
	/**
	 * 根据用户ID查询账户余额
	 * @param userId
	 * @return
	 */
	public double getBalance(BigDecimal userId){
		String total_acc=getTotalAcctId(userId);
		if(!total_acc.equals("")){
			double  amount=getBalance(total_acc);
		 return amount;
		}else{
			return 0.0;
		}
	}
	/**
	 * 查询当前用户余额(业务类型为4的资金分账现金)
	 * @param total_acct 总账号
	 * @return
	 */
	public double getBalance(String total_acct){
		String sql="select act from AcTLedgerAdmin act where act.busiType='4' and act.financialAcTCustomerAdmin.id="+total_acct;
		Query query=em.createQuery(sql);
		List l=query.getResultList(); 
		if(l==null||l.size()==0){
			return 0;
		}
		//BigDecimal amount=(BigDecimal)l.get(0);
		AcTLedgerAdmin acTLedger=(AcTLedgerAdmin)l.get(0);
		if(acTLedger!=null&&acTLedger.getAmount()!=null)
		{
			return  acTLedger.getAmount().doubleValue();
		}
		return 0;
	}
	
	public BigDecimal getCreditAmount(BigDecimal userId){
		String sql="select t.creditAmount from UserCreditNoteAdmin t where t.user="+userId;
		Query query = em.createQuery(sql);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			BigDecimal amount = (BigDecimal)l.get(0);
		    return amount;
		}
		return BigDecimal.ZERO;
	}
	
	public List<LoanInfoAdmin> getLoanInfo(BigDecimal userId){
		String sql="select loan from LoanInfoAdmin loan where loan.accountUsers.userId="+userId;
		Query query=em.createQuery(sql);
		List loanInfo = query.getResultList();
		return loanInfo;
	}
	
	public BigDecimal getOverdueCount(BigDecimal userId){
		Long count = 0l;
		String sql = "select l from LoanManagementLoanInfo l where l.status in(4,5,6,7) and l.user.userId="+userId;
		// 借款信息
		Query query = em.createQuery(sql);
		List<LoanManagementLoanInfo> l=query.getResultList();
		for (LoanManagementLoanInfo loanManagementLoanInfo : l) {
			// 虚拟现金流水表
			LoanManagementAcTLedgerLoan ledgerLoan = loanManagementLoanInfo.getActLedgerLoan();
			List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList = loanManagementDao.getRepayLoanDetailByLoanId(ledgerLoan);
			for (LoanManagementAcTVirtualCashFlow loanManagementAcTVirtualCashFlow : actvirturalCashFlowList) {
				int overdays = loanManagementAcTVirtualCashFlow.getPastDueDays();
				if(overdays>0){
					count++;
				}
			}
		}
	 
				
				
				
				
		/*String sql="select t.overdueCount from UserCreditNoteAdmin t where t.user="+userId;
		Query query = em.createQuery(sql);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			BigDecimal overdueCount = (BigDecimal)l.get(0);
		    return overdueCount;
		}
		return BigDecimal.ZERO;*/
		return new BigDecimal(count);
	}
	
	public String getCustomId(BigDecimal userId){
		//String sql="select c.total_acct from USERS u,AC_T_CUSTOMER c where  u.t_customer_id=c.id and u.user_id=:userId";
		String sql="select u.t_customer_id from users u where u.user_id='"+userId+"'";
		Query query=em.createNativeQuery(sql);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			BigDecimal t_customer_id=(BigDecimal)l.get(0);
			if(t_customer_id==null){
				return "";
//				throw new RuntimeException("用户表与核心客户信息表数据不同步,用户ID："+userId);
			}else{
			  return t_customer_id.toString();
			}
		}
		return "";
	}
	
	
	public List<AcTLedgerAdmin> getUserFundAccount(Long customId){
		String sql ="select act from AcTLedgerAdmin act where  act.financialAcTCustomerAdmin.id="+customId;
		Query query = em.createQuery(sql);
		return query.getResultList();
	}
	
	public String getUserLoginName(BigDecimal userId){
		String sql="select l.loginName from AccountUsersAdmin l where l.userId="+userId;
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			String loginName = l.get(0).toString();
		    return loginName;
		}
		return "";
	}
	public String getUserRealName(BigDecimal userId){
		String sql="select l.realName from AccountUserInfoPersonAdmin l where l.user.userId="+userId;
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			String realName = l.get(0).toString();
		    return realName;
		}
		return "";
	}
	
	/**
	 * @author Ray
	 * @date 2012-10-26 下午1:41:37
	 * @param code
	 * @return
	 * description:
	 */
	public List queryAeraList(String code){
		if(StringUtils.isEmpty(code)){
			return null;
		}
		String sql="";
		if(code.equals("2")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/2/%'";//北京区级列表
		}else if(code.equals("23")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/23/%'";//天津区级列表
		}else if(code.equals("")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/863/%'";//上海区级列表
		}else if(code.equals("2475")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/2475/%'";//重庆区级列表
		}else{
			sql="select t from Area t where t.parentId = "+code;
		}
		return query(sql);
	}
	 
}
