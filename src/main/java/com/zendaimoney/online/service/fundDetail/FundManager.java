package com.zendaimoney.online.service.fundDetail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.dao.borrowing.BorrowingFreezeFundsDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserCreditNoteDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserDao;
import com.zendaimoney.online.dao.borrowing.ReleaseLoanDao;
import com.zendaimoney.online.dao.fundDetail.FundDao;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.fundDetail.FundUserCreditNote;
import com.zendaimoney.online.vo.fundDetail.FundDetailVO;
import com.zendaimoney.online.vo.fundDetail.FundFlowVO;

/**
 * 查询资金详情信息
 * 
 * @author mayb
 * 
 */
// Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class FundManager {

	@Autowired
	private FundDao dao;

	@Autowired
	private BorrowingUserDao borrowingUserDao;

	@Autowired
	private ReleaseLoanDao releaseLoanDao;

	@Autowired
	private BorrowingFreezeFundsDao borrowingFreezeFundsDao;
	@Autowired
	private BorrowingUserCreditNoteDao borrowingUserCreditNoteDao;

	/**
	 * 查询资金详情
	 * 
	 * @param userId
	 * @return
	 */
	public FundDetailVO queryFundDetail(BigDecimal userId) {
		BorrowingUsers borrower = borrowingUserDao.findByUserId(userId);

		FundDetailVO VO = new FundDetailVO();

		double amount_total = dao.getBalance(userId);// 查询账户余额
		FundUserCreditNote userCreditNote = dao.getUserCreditNoteByUserId(userId);// 查询用户信用信息
		double amount_loan = dao.getLoanInfoByUserId(userId);// 用户贷款总额
		double amount_success_recharge = dao.getAmountSuccessRecharge(userId);// 成功充值总金额
		double freeze_lc = dao.getFreezeFundsFinancialByUserId(userId);// 投标冻结金额
		double freeze_tx = dao.getFreezeFundsWithdrawByUserId(userId);// 提现冻结金额
		double amount_success_withdraw = dao.getAmountSuccessWithdraw(userId);// 成功提现总额
		double amount_invest_tatol = dao.getTotalAmountInvest(userId);// 总借出金额：=当前用户作为理财人，（还款中的）∑投标金额
		double principal_interest_recv = dao.getInvestPrincipalInterest_new(userId);// 查询
																						// 已收回本息：=所有借款人对当前用户已归还本息求和
		double principal_interest_unrecv = dao.getWaitInvestPrincipalInterest(userId);// 待收回本息
		double amount_loan_tatol = dao.getLoanAmountTotal(userId);// 已还清,还在款总额
		double amount_loan_paid = dao.getLoanPrincipalInterest(userId, true);// 已还本息
		double amount_loan_unpay = dao.getLoanPrincipalInterest(userId, false);// 未还本息息

		// 可提现金额只显示小数点后2位，后面的数据都不显示不四舍五入
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		String amount = formater.format(amount_total);

		VO.setZhye(ObjectFormatUtil.formatCurrency(Calculator.add(Calculator.add(freeze_lc, freeze_tx), Double.parseDouble(amount))));// 账户余额
		VO.setLcdjzy(ObjectFormatUtil.formatCurrency(freeze_lc));// 理财冻结
		VO.setTxdjzy(ObjectFormatUtil.formatCurrency(freeze_tx));// 提现冻结
		VO.setDjzy(ObjectFormatUtil.formatCurrency(Calculator.add(freeze_lc, freeze_tx)));// 冻结总额
		// VO.setKyye(ObjectFormatUtil.formatCurrency(Calculator.sub(Calculator.sub(amount_total,
		// freeze_lc), freeze_tx)));//可用余额
		VO.setKyye(ObjectFormatUtil.formatCurrency(Double.parseDouble(amount)));// 可用余额
		if (userCreditNote != null && userCreditNote.getCreditAmount() != null) {
			VO.setKyed(ObjectFormatUtil.formatCurrency(showAvailableMoney(borrower.getUserId()).doubleValue()));// 可用额度
		}
		VO.setCgczze(ObjectFormatUtil.formatCurrency(amount_success_recharge));// 成功充值总额
		VO.setCgtxze(ObjectFormatUtil.formatCurrency(amount_success_withdraw));// 成功提现金额
		VO.setZjcje(ObjectFormatUtil.formatCurrency(amount_invest_tatol));// 总借出金额
		VO.setYshbx(ObjectFormatUtil.formatCurrency(principal_interest_recv));// 已收回本息
		VO.setDshbx(ObjectFormatUtil.formatCurrency(principal_interest_unrecv));// 待收回本息
		VO.setZjkje(ObjectFormatUtil.formatCurrency(amount_loan_tatol));// 已还清,还在款总额
		VO.setYhbx(ObjectFormatUtil.formatCurrency(amount_loan_paid));// 已还本息
		VO.setDhbx(ObjectFormatUtil.formatCurrency(amount_loan_unpay));// 未还本息

		return VO;
	}

	// 初始化页面,查看可用额度
	public BigDecimal showAvailableMoney(BigDecimal userid) {
		// List<BorrowingFreezeFunds> freezefundList =
		// borrowingFreezeFundsDao.findByUserIdAndFreezeStatus(userid,"3");
		BorrowingUserCreditNote creditNote = borrowingUserCreditNoteDao.findByUserId(userid);
		List<BorrowingLoanInfo> loanList = releaseLoanDao.findByUserUserId(userid);
		double freezeSum = 0;
		// 查询冻结资金
		for (BorrowingLoanInfo loan : loanList) {
			// 借款状态为招标、满标、还款、逾期、高级逾期的资金为冻结资金
			if (loan.getStatus().equals(new BigDecimal(1)) || loan.getStatus().equals(new BigDecimal(2)) || loan.getStatus().equals(new BigDecimal(4)) || loan.getStatus().equals(new BigDecimal(6)) || loan.getStatus().equals(new BigDecimal(7))) {
				freezeSum += loan.getLoanAmount();
			}
		}
		BigDecimal edu = BigDecimal.ZERO;
		if (creditNote != null) {/*
								 * if(freezefundList.size() != 0){ BigDecimal
								 * djzj = BigDecimal.ZERO;
								 * for(BorrowingFreezeFunds ff:freezefundList){
								 * djzj.add(ff.getFreezeMoney()); } edu =
								 * creditNote.getCreditAmount().subtract(djzj);
								 * }else{ edu = creditNote.getCreditAmount(); }
								 */
			// 可用额度由总额度减去冻结资金
			edu = creditNote.getCreditAmount().subtract(new BigDecimal(freezeSum));
		}
		return edu;
	}

}
