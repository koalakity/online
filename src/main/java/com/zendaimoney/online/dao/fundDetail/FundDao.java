package com.zendaimoney.online.dao.fundDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.ConstSubject;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.entity.fundDetail.AcTFlow;
import com.zendaimoney.online.entity.fundDetail.AcTLedger;
import com.zendaimoney.online.entity.fundDetail.AcTLedgerLoan;
import com.zendaimoney.online.entity.fundDetail.AcTVirtualCashFlow;
import com.zendaimoney.online.entity.fundDetail.FundUserCreditNote;
import com.zendaimoney.online.entity.fundDetail.InvestInfo;
import com.zendaimoney.online.entity.fundDetail.LoanInfo;
import com.zendaimoney.online.vo.fundDetail.FundFlowVO;

/**
 * 资金详情查询
 * 
 * @author mayb
 * 
 */
@Component
public class FundDao {
	private static Logger logger = LoggerFactory.getLogger(FundDao.class);
	@PersistenceContext
	private EntityManager em;

	// /**
	// * 查询账户余额
	// * @param userId
	// * @return
	// */
	// public FundsAcc getFundsAccByUserId(BigDecimal userId){
	// String sql="select t from FundsAcc t where t.customerId=:userId";
	// Query query=em.createQuery(sql);
	// query.setParameter("userId", userId);
	// List<FundsAcc> l=query.getResultList();
	// if(l!=null&&l.size()>0){
	// return (FundsAcc)(l.get(0));
	// }else{
	// return null;
	// }
	// }
	/**
	 * 查询提现冻结金额
	 * 
	 * @param userId
	 * @return
	 */
	public double getFreezeFundsWithdrawByUserId(BigDecimal userId) {
		String total_acc = getTotalAcctId(userId);
		String sql = "select act from AcTLedger act where act.busiType='4' and act.totalAccountId=" + total_acc;
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		if (l == null || l.size() == 0) {
			return 0;
		}
		// BigDecimal amount=(BigDecimal)l.get(0);
		AcTLedger acTLedger = (AcTLedger) l.get(0);
		if (acTLedger != null && acTLedger.getPayBackAmt() != null) {
			return acTLedger.getPayBackAmt().doubleValue();
		}
		return 0;

		/*
		 * String sql=
		 * "select sum(t.freezeMoney) as amount from FreezeFunds t where t.userId=:userId and t.freezeStatus=1 and t.freezeKind=2"
		 * ; Query query=em.createQuery(sql); query.setParameter("userId",
		 * userId); List l=query.getResultList();
		 * if(l!=null&&l.size()>0&&l.get(0)!=null){ BigDecimal
		 * amount=(BigDecimal)l.get(0); return amount.doubleValue(); } return 0;
		 */
	}

	/**
	 * 查询投标冻结金额
	 * 
	 * @param userId
	 * @return
	 */
	public double getFreezeFundsFinancialByUserId(BigDecimal userId) {
		String total_acc = getTotalAcctId(userId);
		String sql = "select act from AcTLedger act where act.busiType='5' and act.totalAccountId=" + total_acc;
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		if (l == null || l.size() == 0) {
			return 0;
		}
		// BigDecimal amount=(BigDecimal)l.get(0);
		AcTLedger acTLedger = (AcTLedger) l.get(0);
		if (acTLedger != null && acTLedger.getAmount() != null) {
			return acTLedger.getAmount().doubleValue();
		}
		return 0;

		// String
		// sql="select sum(t.freezeMoney) as amount from FreezeFunds t where t.userId=:userId and t.freezeStatus=1 and t.freezeKind=1";
		// Query query=em.createQuery(sql);
		// query.setParameter("userId", userId);
		// List l=query.getResultList();
		// if(l!=null&&l.size()>0&&l.get(0)!=null){
		// BigDecimal amount=(BigDecimal)l.get(0);
		// return amount.doubleValue();
		// }
		// return 0;
	}

	/**
	 * 查询用户信用额度
	 * 
	 * @param userId
	 * @return
	 */
	public FundUserCreditNote getUserCreditNoteByUserId(BigDecimal userId) {
		String sql = "select t from FundUserCreditNote t where t.userId=:userId";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List<FundUserCreditNote> l = query.getResultList();
		if (l != null && l.size() > 0) {
			return (FundUserCreditNote) l.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询用户贷款总额
	 * 
	 * @param userId
	 * @return
	 */
	public double getLoanInfoByUserId(BigDecimal userId) {
		String sql = "select sum(t.loanAmount) as amount from LoanInfo t where t.userId=:userId and t.status in(4,5,6,7)";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List l = query.getResultList();
		if (l != null && l.size() > 0) {
			Double amount = (Double) l.get(0);
			if (amount != null) {
				return amount.doubleValue();
			} else {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 查询成功充值总额
	 * 
	 * @param userId
	 * @return
	 */
	public double getAmountSuccessRecharge(BigDecimal userId) {
		String sql = "select sum(t.amount) as amount from RechargeNote t where userId=:userId and t.payRs='1'";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List l = query.getResultList();
		if (l != null && l.size() > 0) {
			BigDecimal amount = (BigDecimal) l.get(0);
			if (amount == null) {
				return 0;
			}
			return amount.doubleValue();
		}
		return 0;
	}

	/**
	 * 查询成功提现金额
	 * 
	 * @param userId
	 * @return
	 */
	public double getAmountSuccessWithdraw(BigDecimal userId) {
		String sql = "select sum(t.realAmount) as amount from ExtractNote t where t.userId=:userId and t.verifyStatus='2'";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List l = query.getResultList();
		if (l != null && l.size() > 0) {
			Double amount = (Double) l.get(0);
			if (amount == null) {
				return 0;
			}
			return amount.doubleValue();
		}
		return 0;

		/*
		 * String sql =
		 * "select sum(f.freeze_money) from freeze_funds f where f.user_id=" +
		 * userId + " and f.freeze_kind='2' and f.freeze_status='2'"; Query
		 * query = em.createNativeQuery(sql); List l = query.getResultList(); if
		 * (l != null && l.size() > 0) { BigDecimal amount = (BigDecimal)
		 * l.get(0); if (amount == null) { return 0; } return
		 * amount.doubleValue(); } return 0;
		 */
	}

	/**
	 * 总借出金额：当前用户作为理财人，（还款中的）∑投标金额
	 * 
	 * @param userId
	 * @return
	 */
	public double getTotalAmountInvest(BigDecimal userId) {
		String total_acc = getTotalAcctId(userId);
		String sql = "select act from AcTLedger act where act.busiType='1' and act.totalAccountId=" + total_acc;
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		if (l == null || l.size() == 0) {
			return 0;
		}
		// BigDecimal amount=(BigDecimal)l.get(0);
		AcTLedger acTLedger = (AcTLedger) l.get(0);
		if (acTLedger != null && acTLedger.getInvestAmount() != null) {
			return acTLedger.getInvestAmount().doubleValue();
		}
		return 0;

		/*
		 * String sql=
		 * "select sum(t.investAmount) as amount from InvestInfo t where userId=:userId and t.status in('3','4')"
		 * ; Query query=em.createQuery(sql); query.setParameter("userId",
		 * userId); List l=query.getResultList();
		 * if(l!=null&&l.size()>0&&l.get(0)!=null){ Double
		 * amount=(Double)l.get(0); return amount.doubleValue(); } return 0;
		 */
	}

	/**
	 * 查询 理财人 已收回本息、未收回本息
	 * 
	 * @param userId
	 * @param isPay
	 *            已还或未还
	 * @return
	 */
	public double getInvestPrincipalInterest(BigDecimal userId, boolean isPay) {
		/*
		 * if(isPay){ String total_acc=getTotalAcctId(userId); String sql=
		 * "select act from AcTLedger act where act.busiType='1' and act.totalAccountId="
		 * +total_acc; Query query=em.createQuery(sql); List
		 * l=query.getResultList(); if(l==null||l.size()==0){ return 0; }
		 * //BigDecimal amount=(BigDecimal)l.get(0); AcTLedger
		 * acTLedger=(AcTLedger)l.get(0); if(acTLedger!=null) { return
		 * (null==acTLedger.getInvestAmount()?0:acTLedger.getInvestAmount())
		 * -(null==acTLedger.getDebtAmount()?0:acTLedger.getDebtAmount())
		 * +(null==
		 * acTLedger.getInterestIncome()?0:acTLedger.getInterestIncome()); }
		 * return 0; }else{ String sql=
		 * "select t from InvestInfo t where t.userId=:userId  and t.status in('3','4')"
		 * ; Query query=em.createQuery(sql); query.setParameter("userId",
		 * userId); List investInfoList=query.getResultList(); double
		 * amount=0;//本息和 if(investInfoList!=null&&investInfoList.size()>0){
		 * for(int i=0;i<investInfoList.size();i++){ InvestInfo
		 * investInfo=(InvestInfo)investInfoList.get(i); if(investInfo==null){
		 * continue; } String status=investInfo.getStatus();//理财状态4:已还清 3：还款中
		 * double scale=investInfo.getHavaScale();//比例 if(!isPay){
		 * //计算未还本息总和时,跳过已还列表 if(status.equals("4")){ continue; } } List
		 * flowList
		 * =getAcTVirtualCashFlowListByLoanId(investInfo.getLoanId().longValue
		 * ());//还款列表 if(flowList!=null&&flowList.size()>0){ for(int
		 * j=0;j<flowList.size();j++){ AcTVirtualCashFlow
		 * flow=(AcTVirtualCashFlow)flowList.get(j); double principal=0;//本金
		 * double interest=0;//利息 if(flow!=null){ if(isPay){ //已结标
		 * if(flow.getRepayStatus
		 * ()==RepayStatus.一次性提前还款||flow.getRepayStatus()==RepayStatus.正常还款){
		 * //已还款计划表此字段为实际还款时间 principal=flow.getPrincipalAmt()*scale;//本金
		 * interest=flow.getInterestAmt()*scale;//利息 } }else{ //还款中
		 * if(flow.getRepayStatus()==RepayStatus.未还款){ //未还款计划此字段为空
		 * principal=flow.getPrincipalAmt()*scale;//本金
		 * interest=flow.getInterestAmt()*scale;//利息 } }
		 * amount=amount+principal+interest;//(本金+利息)之和 } } } } } return amount;
		 * }
		 */

		String sql = "select t from InvestInfo t where t.userId=:userId  and t.status in('3','4')";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List investInfoList = query.getResultList();
		double amount = 0;// 本息和
		if (investInfoList != null && investInfoList.size() > 0) {
			for (int i = 0; i < investInfoList.size(); i++) {
				InvestInfo investInfo = (InvestInfo) investInfoList.get(i);
				if (investInfo == null) {
					continue;
				}
				String status = investInfo.getStatus();// 理财状态4:已还清 3：还款中
				double scale = investInfo.getHavaScale();// 比例
				if (!isPay) {
					// 计算未还本息总和时,跳过已还列表
					if (status.equals("4")) {
						continue;
					}
				}
				List flowList = getAcTVirtualCashFlowListByLoanId(getLoan(investInfo.getLoanId()).getLedgerLoanId().longValue());// 还款列表
				if (flowList != null && flowList.size() > 0) {
					for (int j = 0; j < flowList.size(); j++) {
						AcTVirtualCashFlow flow = (AcTVirtualCashFlow) flowList.get(j);
						double principal = 0;// 本金
						double interest = 0;// 利息
						if (flow != null) {
							if (isPay) {
								// 已结标
								if (flow.getRepayStatus() == RepayStatus.正常还款) {
									// 已还款计划表此字段为实际还款时间
									principal = flow.getPrincipalAmt() * scale;// 本金
									interest = flow.getInterestAmt() * scale;// 利息
								}
								if (flow.getRepayStatus() == RepayStatus.一次性提前还款) {
									// 已还款计划表此字段为实际还款时间
									principal = flow.getPrincipalAmt() * scale;// 本金
								}
							} else {
								// 还款中
								if (flow.getRepayStatus() == RepayStatus.未还款) {
									// 未还款计划此字段为空
									principal = flow.getPrincipalAmt() * scale;// 本金
									interest = flow.getInterestAmt() * scale;// 利息
								}
							}
							amount = amount + principal + interest;// (本金+利息)之和
						}
					}
				}
			}
		}
		return amount;
	}
	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-5 上午10:47:46
	 * @param userId
	 * @return
	 * description: 修复bug1208,已收回本息
	 * 理财人已收回本息
	 */
	public double getInvestPrincipalInterest_new(BigDecimal userId) {
		String sql = "select sum(amt) from (select ii.user_id ,(atcf.principal_amt*ii.hava_scale + atcf.interest_amt*ii.hava_scale) as amt  from invest_info ii join loan_info li on li.loan_id=ii.loan_id join ac_t_virtual_cash_flow atcf on atcf.loan_id=li.ledger_loan_id  where  ii.user_id="
				+userId+" and atcf.repay_status in (1,4))  group by user_id ";
		Query query = em.createNativeQuery(sql);
		List amountList = query.getResultList();
		Double repay=0.0;
		if(amountList!= null && amountList.size() > 0){
			repay=Double.valueOf(amountList.get(0).toString());
		}
		return repay+getAlreadyCompensation(userId)+getEarlyrepay(userId);
	}
	
	/**
	 * @author Ray
	 * @date 2013-2-5 下午2:34:01
	 * @param userId
	 * @return
	 * description:提前还款的回收本金
	 */
	public double getEarlyrepay(BigDecimal userId) {
		String sql = "select sum(amt) from (select ii.user_id ,(atcf.principal_amt*ii.hava_scale) as amt  from invest_info ii join loan_info li on li.loan_id=ii.loan_id join ac_t_virtual_cash_flow atcf on atcf.loan_id=li.ledger_loan_id where  ii.user_id="
				+userId+" and atcf.repay_status=2)  group by user_id";
		Query query = em.createNativeQuery(sql);
		List amountList = query.getResultList();
		Double Earlyrepay=0.0;
		if(amountList!= null && amountList.size() > 0){
			Earlyrepay=Double.valueOf(amountList.get(0).toString());
		}
		return Earlyrepay;
	}
	
	
	
	/**
	 * @author Ray
	 * @date 2013-2-5 上午10:47:46
	 * @param userId
	 * @return
	 * description: 修复bug1208,待收回本息
	 * 理财人待收回本息
	 */
	public double getWaitInvestPrincipalInterest(BigDecimal userId) {
		String sql="select sum(amt) from (select ii.user_id ,(atcf.principal_amt*ii.hava_scale + atcf.interest_amt*ii.hava_scale) as amt  from invest_info ii join loan_info li on li.loan_id=ii.loan_id join ac_t_virtual_cash_flow atcf on atcf.loan_id=li.ledger_loan_id where  ii.user_id="
		+ userId+" and atcf.repay_status=0 and ii.status =3 )  group by user_id";
		Query query = em.createNativeQuery(sql);
		List amountList = query.getResultList();
		Double unrepay=0.0;
		if(amountList!= null && amountList.size() > 0){
			unrepay=Double.valueOf(amountList.get(0).toString());
		}
		//未还款累计金额
		return unrepay-getAlreadyCompensation(userId);
	}
	
	/**
	 * @author Ray
	 * @date 2013-2-5 下午2:10:52
	 * @param userId
	 * @return
	 * description:已代偿金额
	 */
	public double getAlreadyCompensation(BigDecimal userId) {
		String sql="select sum(amt) from (select ii.user_id ,(atcf.principal_amt*ii.hava_scale + atcf.interest_amt*ii.hava_scale) as amt  from invest_info ii join loan_info li on li.loan_id=ii.loan_id join ac_t_virtual_cash_flow atcf on atcf.loan_id=li.ledger_loan_id join OVERDUE_CLAIMS oc on oc.invest_id=ii.invest_id and oc.num=atcf.curr_num where  ii.user_id="
				+userId +" and atcf.repay_status=0 and is_advanced=1)  group by user_id";
		Query query = em.createNativeQuery(sql);
		List amountList = query.getResultList();
		Double alreadyCompensation=0.0;
		if(amountList!= null && amountList.size() > 0){
			alreadyCompensation=Double.valueOf(amountList.get(0).toString());
		}
		//未还款累计金额
		return alreadyCompensation;
	}
	

	public LoanInfo getLoan(BigDecimal id) {
		String sql = "select t from LoanInfo t where t.loanId=:id";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		List l = query.getResultList();
		if (l != null && l.size() > 0 && l.get(0) != null) {
			return (LoanInfo) l.get(0);
		}
		return null;
	}

	public double getLoanAmountTotal(BigDecimal userId) {
		String sql = "select sum(t.loanAmount) from LoanInfo t where t.userId=:userId  and t.status in ('4','5','6','7')";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List l = query.getResultList();
		if (l != null && l.size() > 0 && l.get(0) != null) {
			Double amount = (Double) l.get(0);
			return amount.doubleValue();
		}
		return 0;
	}

	/**
	 * 查询(借款人)已还本息、未还本息(包括已还款完成的贷款)
	 * 
	 * @param userId
	 * @param isPay true已还本息 false 未还本息
	 * @return
	 */
	public double getLoanPrincipalInterest(BigDecimal userId, boolean isPay) {
		String sql = "select t from LoanInfo t where t.userId=:userId  and t.status in ('4','5','6','7')";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List loanList = query.getResultList();
		double amount = 0;
		if (loanList != null && loanList.size() > 0) {
			for (int i = 0; i < loanList.size(); i++) {
				LoanInfo loanInfo = (LoanInfo) loanList.get(i);
				if (loanInfo == null) {
					continue;
				}
				if (loanInfo.getLedgerLoanId() == null) {
					// throw new RuntimeException("借款信息表未与核心库贷款分户信息表关联");
					continue;
				}
				Long ledger_loan_id = loanInfo.getLedgerLoanId().longValue();// 贷款ID
				List flowList = getAcTVirtualCashFlowListByLoanId(ledger_loan_id);
				if (flowList == null && flowList.size() == 0) {
					continue;
				}
				for (int j = 0; j < flowList.size(); j++) {
					AcTVirtualCashFlow flow = (AcTVirtualCashFlow) flowList.get(j);
					double principal = 0;// 本金
					double interest = 0;// 利息
					if (flow != null) {
						if (isPay) {
							// 已还
							if (flow.getRepayStatus() == RepayStatus.正常还款) {
								// 已还款计划表此字段为实际还款时间
								principal = flow.getPrincipalAmt();// 本金
								interest = flow.getInterestAmt();// 利息
							}
							if (flow.getRepayStatus() == RepayStatus.一次性提前还款) {
								// 已还款计划表此字段为实际还款时间
								principal = flow.getPrincipalAmt();// 本金
							}
							/*修复bug_1246 增加逾期还款的本息*/
							if(flow.getRepayStatus() == RepayStatus.逾期还款){
								principal = flow.getPrincipalAmt();// 本金
								interest = flow.getInterestAmt();// 利息
							}
						} else {
							// 未还
							if (flow.getRepayStatus() == RepayStatus.未还款) {
								// 未还款计划此字段为空
								principal = flow.getPrincipalAmt();// 本金
								interest = flow.getInterestAmt();// 利息
							}
						}
						amount = amount + principal + interest;// (本金+利息)之和
					}
				}
			}
		}
		return amount;
	}

	/**
	 * 查询用户流水
	 * 
	 * @param userId
	 * @return
	 */
	public List queryFundFlow(BigDecimal userId) {
		if (userId == null) {
			return null;
		}
		// 表头：时间、类型、存入、存出、结余、备注
		String total_acct = getTotalAcctId(userId);// 从核心库户表读取用户总账号
		String sql = "select f.trade_date,f.acct_title,f.trade_amount,l.amount,f.trade_no " + "from AC_T_FLOW f,AC_T_LEDGER l where f.account=l.account and l.busi_type='4' and f.account=:account";
		Query query = em.createQuery(sql);
		query.setParameter("account", total_acct);
		List list = query.getResultList();
		return list;
	}

	/**
	 * 查询用户总账号ID
	 * 
	 * @param userId
	 * @return
	 */
	private String getTotalAcctId(BigDecimal userId) {
		String sql="select u.TCustomerId from Users u where u.userId=?";
		Query query = em.createQuery(sql).setParameter(1, userId);
		List l = query.getResultList();
		if (l != null && l.size() > 0 && l.get(0) != null) {
			BigDecimal t_customer_id = (BigDecimal) l.get(0);
			if (t_customer_id == null) {
				// throw new RuntimeException("用户表与核心客户信息表数据不同步,用户ID："+userId);
			}
			return t_customer_id.toString();
		}
		return "";
	}

	/**
	 * 查询还款计划
	 * 
	 * @param loanId
	 * @return
	 */
	private List getAcTVirtualCashFlowListByLoanId(Long loanId) {
		String sql = "select t from AcTVirtualCashFlow t where t.loanId=:loanId";
		Query query = em.createQuery(sql);
		query.setParameter("loanId", loanId);
		return query.getResultList();
	}

	/**
	 * 查询贷款分户信息
	 * 
	 * @param id
	 * @return
	 */
	private AcTLedgerLoan getAcTLedgerLoanById(Long id) {
		String sql = "select t from AcTLedgerLoan t where t.id=:id";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		if (query.getResultList() != null && query.getResultList().size() > 0) {
			return (AcTLedgerLoan) query.getResultList().get(0);
		} else {
			// throw new RuntimeException("业务库借款信息表与核心库贷款分户信息表数据不同步,借款ID："+id);
			return null;
		}
	}

	/**
	 * 查询用户流水信息
	 * 
	 * @param userId
	 * @param type_fund
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	@Deprecated
	public FundFlowVO getFlowInfo(BigDecimal userId, String type_fund, String date_start, String date_end, double crrunAmount) {
		double amount = 0;
		Map<String,String> acounts = initActLedgerAcounts(userId);
		String selectAcounts ="";
		for (Map.Entry<String, String> entry:acounts.entrySet()){
			selectAcounts = selectAcounts+"'"+entry.getKey()+"',";
		}
		selectAcounts = selectAcounts.substring(0, selectAcounts.length()-1);
		String sql = "select f from AcTFlow f where ("
				+ "f.account in"
				+ "("
				+ selectAcounts
				+ ")"
				+ " or f.appoAcct in"
				+ "("
				+ selectAcounts
				+ "))";
		if (date_start != null && date_end != null) {
			sql = sql + " and " + "f.tradeDate>=to_date('" + date_start + " 00-00-00','yyyy-MM-dd HH24:mi:ss') " + " and " + "f.tradeDate<=to_date('" + date_end + " 23-59-59','yyyy-MM-dd HH24:mi:ss')";
		}

		if (!type_fund.equals(FundFlowVO.type_fund_01)) {
			sql = sql + " and ";
		}
		if (type_fund.equals(FundFlowVO.type_fund_02)) {
			type_fund = ConstSubject.prepaid;// 充值
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_03)) {
			type_fund = ConstSubject.withdraw_money;// 提现
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_04)) {
			type_fund = ConstSubject.bid_succ_out;// 投标成功
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_05)) {
			type_fund = ConstSubject.loan_succ_pass_out;// 借款成功
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_06)) {
			type_fund = ConstSubject.pay_principal_out;// 偿还本金
			sql = sql + "f.acctTitle in('" + type_fund + "'";
			type_fund = ConstSubject.pay_interest_out;// 偿还利息
			sql = sql + ",'" + type_fund + "')";
		} else if (type_fund.equals(FundFlowVO.type_fund_07)) {
			type_fund = ConstSubject.recv_principal_out;// 回收本金
			sql = sql + "f.acctTitle in('" + type_fund + "'";
			type_fund = ConstSubject.recv_interest_out;// 回收利息
			sql = sql + ",'" + type_fund + "')";
		} else if (type_fund.equals(FundFlowVO.type_fund_08)) {
			type_fund = ConstSubject.repayment_early_out;// 提前还款
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_09)) {
			type_fund = ConstSubject.recv_early_out;// 提前回收
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_10)) {
			type_fund = ConstSubject.withdraw_money_fee_out;// 提现手续费
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_11)) {
			type_fund = ConstSubject.recharge_fee_out;// 充值手续费
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_12)) {
			type_fund = ConstSubject.loan_man_fee_out;// 借款管理费
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_13)) {
			type_fund = ConstSubject.interest_overdue_out;// 逾期罚息
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_14)) {
			type_fund = ConstSubject.penalty_overdue_out;// 逾期违约金
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_15)) {
			type_fund = ConstSubject.advance_out;// 风险金垫付
			sql = sql + "f.acctTitle='" + type_fund + "'";
		} else if (type_fund.equals(FundFlowVO.type_fund_16)) {
			type_fund = ConstSubject.adjust_account_out;// 调账
			sql = sql + "f.acctTitle='" + type_fund + "'";
		}
		sql = sql + " order by f.tradeDate desc,f.id desc";
		logger.info("sql:"+sql);
		Query query = em.createQuery(sql);
		List<AcTFlow> l = query.getResultList();
		List voList = new ArrayList();
		double temp = 0;
		boolean inOutFlag = false;
		if (l != null && l.size() > 0) {
			for (int i = 0; i < l.size(); i++) {
				AcTFlow flow = (AcTFlow) l.get(i);
				String flowAcount = flow.getAccount();
				String flowApoAcct = flow.getAppoAcct();
				if(flow.getAcctTitle().equals("010201")&&flow.getAppoAcctTitle().equals("010202")){
					//System.out.println(flow.getMemo());
					continue;
				}
				if(acounts.get(flowAcount)!=null&&acounts.get(flowApoAcct)!=null){
					if(acounts.get(flowAcount).equals("4")){
						if(acounts.get(flowApoAcct).equals("1") || acounts.get(flowApoAcct).equals("2") ){
							continue;
						}
					}
					if(acounts.get(flowAcount).equals("2")&&acounts.get(flowApoAcct).equals("4")){
						continue;
					}
					if(acounts.get(flowAcount).equals("1")&&acounts.get(flowApoAcct).equals("4")){
						continue;
					}
				}
				
				Map map = new HashMap();
				map.put("date", flow.getTradeDate().toString().substring(0, 19));// 日期
				if (flow.getMemo() == null || flow.getMemo().length() < 6) {
					System.out.println("===================>> 数据库流水表id为" + flow.getId() + "操作科目格式应该为：操作科目：XXXXXXX");
					map.put("type", "");// 科目号
				} else {
					map.put("type", flow.getMemo() == null ? "" : flow.getMemo().substring(5, flow.getMemo().length()));// 科目号
				}

				if (i == 0) {
					if (checkIsOut(flow,acounts)) {
						map.put("out", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存出
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
					} else {
						map.put("in", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存入
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
					}
					temp = flow.getTradeAmount();
					inOutFlag = checkIsOut(flow,acounts);
				} else {

					if (checkIsOut(flow,acounts)) {
						map.put("out", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存出
					} else {
						map.put("in", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存入
					}
					if (inOutFlag) {
						amount = Calculator.add(amount, temp);
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
						temp = flow.getTradeAmount();
						inOutFlag = checkIsOut(flow,acounts);

					} else {
						amount = Calculator.sub(amount, temp);
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
						temp = flow.getTradeAmount();
						inOutFlag = checkIsOut(flow,acounts);

					}
				}
				map.put("doubleAmount", amount);
				map.put("code", flow.getTradeNo());
				voList.add(map);

			}
		}
		FundFlowVO fundFlowVO = new FundFlowVO();
		fundFlowVO.setFlowList(voList);
		return fundFlowVO;
	}

	/**
	 * 根据用户ID查询账户余额
	 * 
	 * @param userId
	 * @return
	 */
	public double getBalance(BigDecimal userId) {
		String total_acc = getTotalAcctId(userId);
		double amount = getBalance(total_acc);
		return amount;
	}

	/**
	 * 查询当前用户余额(业务类型为4的资金分账现金)
	 * 
	 * @param total_acct
	 *            总账号
	 * @return
	 */
	public double getBalance(String total_acct) {
		String sql = "select act from AcTLedger act where act.busiType='4' and act.totalAccountId=" + total_acct;
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		if (l == null || l.size() == 0) {
			return 0;
		}
		// BigDecimal amount=(BigDecimal)l.get(0);
		AcTLedger acTLedger = (AcTLedger) l.get(0);
		if (acTLedger != null && acTLedger.getAmount() != null) {
			return acTLedger.getAmount();
		}
		return 0;
	}

	/**
	 * 判断流水是否为 流出
	 */
	public boolean checkIsOut(AcTFlow flow, Map<String, String> acounts) {
		if (acounts.containsKey(flow.getAccount())) {
			if (acounts.containsKey(flow.getAppoAcct())) {
				// 4==>5操作科目：投标冻结
				if (acounts.get(flow.getAccount()).equals("4") && acounts.get(flow.getAppoAcct()).equals("5")) {
					return true;
					// 5==>4操作科目：投标解冻
				} else if (acounts.get(flow.getAccount()).equals("5") && acounts.get(flow.getAppoAcct()).equals("4")) {
					return false;
				}

			} else {
				// 包含提现冻结
				return true;

			}
		}
		// 包含提现解冻
		return false;
	}

	/**
	 * 根据总账号获取4个分账号
	 */
	@Deprecated
	private Map<String,String> initActLedgerAcounts(BigDecimal userId) {
		//KEY:账号,value:busitype
		Map<String,String> acounts = new HashMap<String,String>();		String sql = "select act.account||'+'||act.busiType from AcTLedger act where act.totalAccountId=(select u.TCustomerId from Users u where u.userId=?)";
		Query query = em.createQuery(sql).setParameter(1, userId);
		List result = query.getResultList();
		for (int i = 0, len = result.size(); i < len; i++) {
			String temp = (String) result.get(i);
			acounts.put(temp.substring(0, temp.length() - 2), temp.substring(temp.length() - 1, temp.length()));
		}
		return acounts;
	}
}
