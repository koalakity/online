package com.zendaimoney.online.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.dao.StaticDao;
import com.zendaimoney.online.admin.dao.loan.LoanManagerDao;

/**
 * 统计管理
 * 
 * @author JiHui
 * 
 *         add 2012-12-03
 * **/
@Service
@Transactional
public class StaticService {
	@Autowired
	private StaticDao statDao;

	@Autowired
	private LoanManagerDao loanManagerDao;

	/**
	 * 当前注册的会员数
	 * 
	 * */
	public long statRegisterCount() {
		return statDao.getStatRegister();
	}

	public long getRegisterCount(int month) {
		return statDao.getStatRegisterCond(month);
	}

	/**
	 * 当前在库放款笔数
	 * 
	 * */
	public int statLoanCount() {
		return statDao.getStatLoan();
	}

	public int getLoanCount(int month) {
		return statDao.getStatLoanCount(month);
	}

	/**
	 * 查询在库的合同金额
	 * 
	 * */
	public double statLoanAmount() {
		return statDao.getStatLoanAmount();
	}

	public double getLoanAmountCond(int month) {
		return statDao.getStatLoanAmountCond(month);
	}

	/**
	 * 查询在库的余额
	 * 
	 * */
	public double statRemainPricipal() {
		// Set<AcTVirtualCashFlowAdmin> actVirtualcashFlows = null;
		// List<LoanInfoAdmin> loanInfoLists =
		// loanManagerDao.getLoanInfoByStatus();
		// double remainderPrincipal = 0.0;
		// double allPrincipal = 0.0;
		// double principalAmtTotal = 0;
		// for (LoanInfoAdmin loanInfo : loanInfoLists) {
		// if (null != loanInfo.getLoanAcTLedgerLoan() && null !=
		// loanInfo.getLoanAcTLedgerLoan().getCurrNum()) {
		// actVirtualcashFlows =
		// loanInfo.getLoanAcTLedgerLoan().getAcTVirtualCashFlows();
		//
		// for (int i = 1; i <=
		// loanInfo.getLoanAcTLedgerLoan().getCurrNum().intValue() - 1; i++) {
		// for (AcTVirtualCashFlowAdmin actVirtualCashFlow :
		// actVirtualcashFlows) {
		// if (actVirtualCashFlow.getCurrNum() == i) {
		// principalAmtTotal = ArithUtil.add(principalAmtTotal,
		// actVirtualCashFlow.getPrincipalAmt());// 已经还款本金
		// break;
		// }
		// }
		// }
		// }
		// allPrincipal += loanInfo.getLoanAmount();//
		// 当前在库放款合同金额,借款状态为：还款中，逾期，高级逾期
		// }
		// remainderPrincipal = allPrincipal - principalAmtTotal;// 待收回的本金
		double remainderPrincipal = statDao.statRemainPricipal();
		return remainderPrincipal;

	}

	public double getRemainPricipal(int month) {
		return statDao.getRemainPricipal(month);
	}
}
