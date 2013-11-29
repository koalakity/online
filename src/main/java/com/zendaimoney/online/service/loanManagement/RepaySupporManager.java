package com.zendaimoney.online.service.loanManagement;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.ConstSubject;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementAcTLedgerDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementOverdueClaimsDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementAcTCustomerDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementAcTFlowDao;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTCustomer;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedger;
import com.zendaimoney.online.entity.loanManagement.LoanManagementInvestInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagementLoanInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagentOverdueClaims;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-12-18 下午4:04:36
 * operation by:
 * description:高级逾期还款
 */
@Component
@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
public class RepaySupporManager {
	@Autowired
	private LoanmanagementAcTFlowDao loanManagementAcTFlowDao;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private LoanManagementOverdueClaimsDao loanManagementOverdueClaimsDao;
	@Autowired
	private LoanmanagementAcTCustomerDao loanmanagementAcTCustomerDao;
	@Autowired
	private LoanManagementAcTLedgerDao loanManagementActLegerDao;
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-11 下午3:19:56
	 * @param currRepayLoanInfo 借款信息
	 * @param currPrincipal     本期应还本金
	 * @param loanActCustomer   借款人核心账户
	 * @param amountActledger   借款人资金账户
	 * @param loanActLedger     借款人贷款账户
	 * description:偿还本金
	 */
	public void repayPrincipal(LoanManagementLoanInfo currRepayLoanInfo,
							double currPrincipal,
							LoanManagementAcTCustomer loanActCustomer,
							LoanManagementAcTLedger amountActledger,LoanManagementAcTLedger loanActLedger){
		double scale = getRepayScale(currRepayLoanInfo);
		//资金账户 资金减去当前应还还款本金
		amountActledger.setAmount(ArithUtil.sub(amountActledger.getAmount(), currPrincipal));
		//调账流水
		if(scale<0.99995){
			loanManagementAcTFlowDao.save(setActFlow(ArithUtil.mul(currPrincipal, ArithUtil.sub(1, scale)), loanActCustomer.getOpenacctTeller(), loanActCustomer.getOpenacctOrgan(), amountActledger.getAccount(), loanActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
		}
		//借款人 贷款分账维护 贷款本金
		loanActLedger.setLoanAmount(ArithUtil.sub(loanActLedger.getLoanAmount(), currPrincipal));
		loanManagementActLegerDao.save(loanActLedger);
		loanManagementActLegerDao.save(amountActledger);
		//投资列表
		List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
		for(LoanManagementInvestInfo investInfo : investInfolist){
			// 当前债权
			LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), currRepayLoanInfo.getActLedgerLoan().getCurrNum());
			// 当前债权应收本金
			double payPrinciPalByInvest = ArithUtil.mul(currPrincipal, investInfo.getHavaScale());
			LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
			LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
			LoanManagementAcTLedger investAmountActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerInvsert.getTotalAcct() + "%"); // 业务类别：4：资金账户(现金增加)
			//①债权没有被收购
			if(overdueClaims.getStatus().equals(new BigDecimal(1))){
				//现金账户金额维护
				investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), payPrinciPalByInvest));
				//理财人流水记录
				loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, "偿还本金/回收本金"));
				// 调账流水
				loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(),ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
				//理财账户维护
				// 当前投资
				investActLeger.setDebtAmount(ArithUtil.sub(investActLeger.getDebtAmount(), payPrinciPalByInvest));
				loanManagementActLegerDao.save(investAmountActLedger);
				loanManagementActLegerDao.save(investActLeger);
				
			//②债权被收购
			}else if(overdueClaims.getStatus().equals(new BigDecimal(2))){
				//证大风险准备金账户
				LoanManagementAcTLedger zendai_acct10 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct10);
				//借款人还 本金给证大
				zendai_acct10.setAmount(ArithUtil.add(zendai_acct10.getAmount(),payPrinciPalByInvest));
				//证大流水流水记录
				loanManagementAcTFlowDao.save(setActFlow(payPrinciPalByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), amountActledger.getAccount(), zendai_acct10.getAccount(), ConstSubject.pay_principal_out, ConstSubject.pay_principal_in, "偿还本金/回收本金"));
				loanManagementActLegerDao.save(zendai_acct10);
			}
		}
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-11 下午4:09:40
	 * @param currRepayLoanInfo 借款信息
	 * @param currInterest     	本期应还利息
	 * @param loanActCustomer   借款人核心账户
	 * @param amountActledger   借款人资金账户
	 * @param loanActLedger     借款人贷款账户
	 * description:偿还利息
	 */
	public void repayInterest(LoanManagementLoanInfo currRepayLoanInfo,
			double currInterest,
			LoanManagementAcTCustomer loanActCustomer,
			LoanManagementAcTLedger amountActledger,LoanManagementAcTLedger loanActLedger){
				double scale = getRepayScale(currRepayLoanInfo);
				//资金账户 资金减去当前应还还款利息
				amountActledger.setAmount(ArithUtil.sub(amountActledger.getAmount(), currInterest));
				//调账流水
				if(scale<0.99995){
					loanManagementAcTFlowDao.save(setActFlow(ArithUtil.mul(currInterest, ArithUtil.sub(1, scale)), loanActCustomer.getOpenacctTeller(), loanActCustomer.getOpenacctOrgan(), amountActledger.getAccount(), loanActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
				}
				
				//借款人 贷款分账维护 贷款本金
				// 应付利息
				loanActLedger.setInterestPayable(ArithUtil.sub(loanActLedger.getInterestPayable(), currInterest));
				// 利息支出
				loanActLedger.setInterestExpenditure(ArithUtil.add(loanActLedger.getInterestExpenditure(), currInterest));
				loanManagementActLegerDao.save(loanActLedger);
				loanManagementActLegerDao.save(amountActledger);
				//投资列表
				List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
				for(LoanManagementInvestInfo investInfo : investInfolist){
					// 当前债权
					LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), currRepayLoanInfo.getActLedgerLoan().getCurrNum());
					//当前债权应收利息
					double payCurrInterestByInvest = ArithUtil.mul(currInterest, investInfo.getHavaScale());
					LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
					LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
					LoanManagementAcTLedger investAmountActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerInvsert.getTotalAcct() + "%"); // 业务类别：4：资金账户(现金增加)
					//①债权没有被收购
					if(overdueClaims.getStatus().equals(new BigDecimal(1))){
						//现金账户金额维护 偿还利息
						investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), payCurrInterestByInvest));
						loanManagementAcTFlowDao.save(setActFlow(payCurrInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, "偿还利息/回收利息"));
						// 调账流水
						loanManagementAcTFlowDao.save(setActFlow(payCurrInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(),ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
						//理财账户维护
						// 应收利息
						investActLeger.setInterestReceivable(ArithUtil.sub(investActLeger.getInterestReceivable(), payCurrInterestByInvest));
						// 利息收入
						investActLeger.setInterestIncome(ArithUtil.add(investActLeger.getInterestIncome(), payCurrInterestByInvest));
						loanManagementActLegerDao.save(investAmountActLedger);
						loanManagementActLegerDao.save(investActLeger);
						
					//②债权被收购
					}else if(overdueClaims.getStatus().equals(new BigDecimal(2))){
						//证大风险准备金账户
						LoanManagementAcTLedger zendai_acct10 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct10);
						//借款人还 利息 给证大
						zendai_acct10.setAmount(ArithUtil.add(zendai_acct10.getAmount(), payCurrInterestByInvest));
						//证大流水流水记录 偿还利息
						loanManagementAcTFlowDao.save(setActFlow(payCurrInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), amountActledger.getAccount(), zendai_acct10.getAccount(), ConstSubject.pay_interest_out, ConstSubject.pay_interest_in, "偿还利息/回收利息"));
						loanManagementActLegerDao.save(zendai_acct10);
					}
				}
		
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-11 下午4:21:53
	  * @param currRepayLoanInfo  借款信息
	 * @param currOverdueInterest 本期应还逾期罚息
	 * @param loanActCustomer     借款人核心账户
	 * @param amountActledger     借款人资金账户
	 * @param loanActLedger       借款人贷款账户
	 * @param currOverdueFines    逾期违约金
	 * description:偿还逾期罚息
	 */
	public void repayOverdueInterest(LoanManagementLoanInfo currRepayLoanInfo,
			double currOverdueInterest,
			double currOverdueFines,
			LoanManagementAcTCustomer loanActCustomer,
			LoanManagementAcTLedger amountActledger,LoanManagementAcTLedger loanActLedger){
		double scale = getRepayScale(currRepayLoanInfo);
		//资金账户 资金减去当前应还逾期罚息
		amountActledger.setAmount(ArithUtil.sub(amountActledger.getAmount(), currOverdueInterest));
		//逾期罚息调账流水
		if(scale<0.99995){
			loanManagementAcTFlowDao.save(setActFlow(ArithUtil.mul(currOverdueInterest, ArithUtil.sub(1, scale)), loanActCustomer.getOpenacctTeller(), loanActCustomer.getOpenacctOrgan(), amountActledger.getAccount(), loanActLedger.getAccount(), ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
		}
		loanManagementActLegerDao.save(amountActledger);
		// 其他支出：＋逾期罚息
		loanActLedger.setOtherExpenditure(ArithUtil.add(loanActLedger.getOtherExpenditure(),currOverdueInterest));
		loanManagementActLegerDao.save(loanActLedger);
		
		//投资列表
		List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
		for(LoanManagementInvestInfo investInfo : investInfolist){
			// 当前债权
			LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), currRepayLoanInfo.getActLedgerLoan().getCurrNum());
			//当前债权应收逾期罚息
			double payCurrOverdueInterestByInvest = ArithUtil.mul(currOverdueInterest, investInfo.getHavaScale());
			LoanManagementAcTCustomer actCustomerInvsert = loanmanagementAcTCustomerDao.findById(investInfo.getUser().gettCustomerId()); // 理财人核心客户信息
			LoanManagementAcTLedger investActLeger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("1", actCustomerInvsert.getTotalAcct() + "%");// 业务类别：1：理财
			LoanManagementAcTLedger investAmountActLedger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", actCustomerInvsert.getTotalAcct() + "%"); // 业务类别：4：资金账户(现金增加)
			//①债权没有被收购
			if(overdueClaims.getStatus().equals(new BigDecimal(1))){
				//现金账户金额维护 偿还逾期罚息
				investAmountActLedger.setAmount(ArithUtil.add(investAmountActLedger.getAmount(), payCurrOverdueInterestByInvest));
				loanManagementAcTFlowDao.save(setActFlow(payCurrOverdueInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), loanActLedger.getAccount(), investActLeger.getAccount(), ConstSubject.interest_overdue_out, ConstSubject.interest_overdue_in, "逾期罚息"));
				// 调账流水
				loanManagementAcTFlowDao.save(setActFlow(payCurrOverdueInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), investActLeger.getAccount(), investAmountActLedger.getAccount(),ConstSubject.adjust_account_out, ConstSubject.adjust_account_in, "调账"));
				//理财账户维护
				// 其它收入
				investActLeger.setOtherIncome(ArithUtil.add(investActLeger.getOtherIncome(), payCurrOverdueInterestByInvest));
				loanManagementActLegerDao.save(investAmountActLedger);
				loanManagementActLegerDao.save(investActLeger);
//				overdueClaims.setStatus(new BigDecimal(3));
//				loanManagementOverdueClaimsDao.save(overdueClaims);
				
			//②债权被收购
			}else if(overdueClaims.getStatus().equals(new BigDecimal(2))){
				//证大逾期罚息账户
				LoanManagementAcTLedger zendai_acct2 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct2);
				// 借款人还 逾期罚息给证大
				zendai_acct2.setAmount(ArithUtil.add(zendai_acct2.getAmount(),payCurrOverdueInterestByInvest));
				//证大流水流水记录 偿还利息
				loanManagementAcTFlowDao.save(setActFlow(payCurrOverdueInterestByInvest, actCustomerInvsert.getOpenacctTeller(), actCustomerInvsert.getOpenacctOrgan(), amountActledger.getAccount(), zendai_acct2.getAccount(), ConstSubject.interest_overdue_out, ConstSubject.interest_overdue_in, "逾期罚息"));
				loanManagementActLegerDao.save(zendai_acct2);
			}
			overdueClaims.setStatus(new BigDecimal(3));
			loanManagementOverdueClaimsDao.save(overdueClaims);
		}
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2013-5-3 上午12:17:29
	 * description:更新分期债权逾期信息表
	 */
	public void updateOverdueClaims(LoanManagementLoanInfo currRepayLoanInfo){
		//投资列表
				List<LoanManagementInvestInfo> investInfolist = currRepayLoanInfo.getInvestInfoList();
				for(LoanManagementInvestInfo investInfo : investInfolist){
					// 当前债权
					LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), currRepayLoanInfo.getActLedgerLoan().getCurrNum());
					overdueClaims.setStatus(new BigDecimal(3));
					loanManagementOverdueClaimsDao.save(overdueClaims);
				}
	}
	public double getRepayScale(LoanManagementLoanInfo currRepayLoanInfo){
		List<LoanManagementInvestInfo> inverstList = currRepayLoanInfo.getInvestInfoList();
		double Scale = 0;
		for(LoanManagementInvestInfo investInfo:inverstList){
			// 当前债权
			LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), currRepayLoanInfo.getActLedgerLoan().getCurrNum());
			if(overdueClaims!=null&&overdueClaims.getStatus().equals(new BigDecimal(2))){
				Scale = ArithUtil.add(Scale, investInfo.getHavaScale());
			}
		}
		return Scale;
		
	}
	/**
	 * 生成交易流水
	 */
	public LoanManagementAcTFlow setActFlow(double tradeAmount, String teller, String Organ, String account, String appoAcc, String acctTitle, String appoAcctTitle, String memo) {
		LoanManagementAcTFlow actFlow = new LoanManagementAcTFlow();
		actFlow.setTradeDate(new Timestamp(System.currentTimeMillis()));// 交易日期
		actFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
		actFlow.setTradeAmount(tradeAmount);// 交易金额
		actFlow.setTradeType("1");// 交易类型:现金
		actFlow.setTeller(teller);// 柜员号
		actFlow.setOrgan(Organ);// 营业网点
		actFlow.setAccount(account);// 账号
		actFlow.setAppoAcct(appoAcc);// 对方账户
		actFlow.setAcctTitle(acctTitle);// 科目号
		actFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
		actFlow.setMemo("操作科目：" + memo);// 备注
		return actFlow;
	}
}
