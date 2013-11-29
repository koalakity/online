package com.zendaimoney.online.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.admin.service.LoanManagerService;

@Component
public class AutoTask {

	private static Logger logger = LoggerFactory.getLogger(AutoTask.class);
//	@Autowired
//	private LoanprocessSupportManager loanprocessSupportManager;
	@Autowired
	private LoanManagerService loanManagerService;
//	@Autowired
//	private LoanmanagementLoanInfoDao loanInfoDao;
//	@Autowired
//	private TaskManager taskManager;


	/**
	 * 检查借款信息是否逾期
	 */
	public void checkPayBackTime() {
		logger.info("=============开始检查借款信息是否逾期=============");
		try {
			loanManagerService.checkPaybackTimeIsOverTime();
			loanManagerService.checkOverDueInVirtualCashFlow();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logger.info("=============结束检查借款信息是否逾期=============");
	}

	/**
	 * @author Ray
	 * @date 2012-12-5 上午11:23:34 description:自动还款
	 */
//	public void autoPayBack() {
//		logger.info(new Date() + "自动还款任务--开始执行！");
//		logger.info(new Date() + "自动还款任务--获取需还款列表");
//		// 获取需还款人的总账号
//		List<Repayment> totalAcctList = taskManager.findTotalAcct();
//		logger.info(new Date() + "自动还款任务--获取需还款列表完毕，总计" + totalAcctList.size() + "笔");
//		// 获取未还款（状态为4）的借款列表
//		// List<LoanManagementLoanInfo> LoanManagementLoanInfoList =
//		// LoanmanagementLoanInfoDao.findByStatus(BigDecimal.valueOf(4));
//		logger.info(new Date() + "自动还款任务--开始检查遍历！");
//		// 遍历需还款对象
//		for (Repayment repayment : totalAcctList) {
//			logger.info("loanId：" + repayment.getLoanId() + "  userId:  " + repayment.getUserId());
//			// 通过总账号获取分账号
//			LoanManagementAcTLedger payPrincipalActledger1 = taskManager.findByBusiTypeAndAccountLike("4", repayment.getTotalAcct() + "%");
//			// Double myAccount=payPrincipalActledger1.getAmount();
//			// String.valueOf(myAccount);
//			// 我的可用余额大于还款金额时，条件成立，进行还款
//			if (payPrincipalActledger1.getAmount() > (repayment.getMonthManageCost() + repayment.getMonthReturnPrincipalandinter())) {
//				String paymentTotalDouble = String.valueOf(repayment.getMonthManageCost() + repayment.getMonthReturnPrincipalandinter());
//				logger.info(repayment.getLoanId() + " " + repayment.getUserId() + "   有足够的钱，进行还款操作！！！！！！！！！！！！！！！！！！！！！！！！！");
//				taskManager.paymentProcessing(paymentTotalDouble, repayment.getLoanId(), repayment.getUserId());
//			} else {
//				logger.info(repayment.getLoanId() + " " + repayment.getUserId() + "   没钱，此笔不还！！！！！！！！！！！！！！！！！！！！！！！！！");
//			}
//
//		}
//
//	}

}
