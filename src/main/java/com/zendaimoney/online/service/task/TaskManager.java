package com.zendaimoney.online.service.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.dao.loanmanagement.LoanManagementAcTLedgerDao;
import com.zendaimoney.online.dao.personal.PersonalUserMessageSetDao;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedger;
import com.zendaimoney.online.entity.task.Repayment;

@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class TaskManager {

//	private static Logger logger = LoggerFactory.getLogger(TaskManager.class);
//
//	@Autowired
//	private LoanmanagementLoanDao loanManagementDao;
	@Autowired
	private LoanManagementAcTLedgerDao loanManagementActLegerDao;
//	@Autowired
//	private LoanmanagementLoanInfoDao LoanmanagementLoanInfoDao;
//	@Autowired
//	private LoanmanagementLoanDao loanmanagementLoanDao;
//	@Autowired
//	private LoanmanagementAcTCustomerDao loanmanagementAcTCustomerDao;
//	@Autowired
//	private LoanManagementInvestInfoDao investInfoDao;
	@Autowired
	PersonalUserMessageSetDao messageSetDao;
//	@Autowired
//	private LoanManagementManager loanManagementManager;
//	@Autowired
//	private RepayingPhoneMessageDao repayingPhoneMessageDao;

	/**
	 * @author Ray
	 * @date 2012-12-5 下午1:34:54
	 * @param paymentTotalDouble
	 *            还款总额（每月还本息+管理费，这里只是正常还款，非正常还款还要包含更多）
	 * @param paymentLoanId
	 *            借款ID
	 * @param userId
	 *            用户ID description:
	 */
//	@Transactional(readOnly = false)
//	public void paymentProcessing(String paymentTotalDouble, BigDecimal loanId, BigDecimal userId) {
//		// 获取借款人信息
//		LoanManagementUsers currUser = loanmanagementLoanDao.findByUserId(userId);
//		BigDecimal isApprovePhone = currUser.getIsApprovePhone();
//
//		// 借款信息
//		LoanManagementLoanInfo currRepayLoanInfo = loanManagementDao.getLoanInfoByLoanId(loanId, currUser).get(0);
//		// 虚拟现金流水表
//		LoanManagementAcTLedgerLoan ledgerLoan = currRepayLoanInfo.getActLedgerLoan();
//		List<LoanManagementAcTVirtualCashFlow> actvirturalCashFlowList = loanManagementDao.getRepayLoanDetailByLoanId(ledgerLoan);
//		// 借款人核心账户信息
//		LoanManagementAcTCustomer actCustomerLoan = loanmanagementAcTCustomerDao.findById(currUser.gettCustomerId());
//		// 贷款分户信息
//		LoanManagementAcTLedgerLoan currAcTLedgerLoan = loanManagementDao.getRepayLoanDetailByLedgerLoanId(ledgerLoan.getId()).get(0);
//		List<SendMessageToInvestVO> messageList = null;
//		/*************** 添加异常信息捕获,防止单次调用发生异常影响循环的执行 ************************/
//		try {
//			messageList = loanManagementManager.paymentTypeEachPeriod(currUser, paymentTotalDouble, currRepayLoanInfo, actCustomerLoan, actvirturalCashFlowList, currAcTLedgerLoan, 0);
//		} catch (RuntimeException e) {
//			logger.info("当前自动还款失败！时间：" + new Date().toLocaleString() + ",用户ID：" + userId + ",借款ID：" + loanId);
//		}
//		logger.info("还款完成！！！！！！！！！！！！！！");
//
//		if (messageList != null && messageList.size() != 0) {
//			if (BigDecimal.ONE == isApprovePhone) {
//				// 保存需要发送短消息，由自动任务于第二天发送
//				RepayingPhoneMessage rm = new RepayingPhoneMessage();
//				rm.setUserId(userId);
//				rm.setType(1);
//				rm.setDetail(paymentTotalDouble);
//				rm.setReDate(new Date());
//				rm.setIsSend(1);
//				RepayingPhoneMessage rm3 = repayingPhoneMessageDao.save(rm);
//				logger.info("短息保存借款人：" + rm3.getID());
//			} else {
//				logger.info("此借款人未绑定手机!userID=" + userId);
//			}
//			for (SendMessageToInvestVO messsage : messageList) {
//				LoanManagementInvestInfo investInfo = investInfoDao.findByInvestId(messsage.getInvestId());
//				List<PersonalUserMessageSet> messagePayList = messageSetDao.findByUserIdAndKindId(investInfo.getUser().getUserId(), new BigDecimal(7));
//				loanManagementManager.sendSysMsgToInvest(messsage);// 发送系统消息
//
//				RepayingPhoneMessage rm2 = null;
//				for (PersonalUserMessageSet message : messagePayList) {
//					if (message.getMannerId().equals(new BigDecimal(2))) {
//						// 邮件通知
//						loanManagementManager.sendMailToInvest(messsage);
//					}
//					if (message.getMannerId().equals(new BigDecimal(3))) {
//						// 短信通知
//						// loanManagementManager.sendMessageToInvest(messsage);
//
//						rm2 = new RepayingPhoneMessage();
//						rm2.setUserId(investInfo.getUser().getUserId());
//						rm2.setReDate(new Date());
//						rm2.setType(2);
//						rm2.setDetail(messsage.getPrincipanInterestMonth());
//						rm2.setDetail2(investInfo.getLoanInfo().getLoanTitle());
//						rm2.setIsSend(1);
//						RepayingPhoneMessage rm4 = repayingPhoneMessageDao.save(rm2);
//						logger.info("短息保存理财人：" + rm4.getID());
//
//					}
//				}
//			}
//		}
//	}

	public List<Repayment> findTotalAcct() {
		return loanManagementActLegerDao.findTotalAcct();
	}

	public LoanManagementAcTLedger findByBusiTypeAndAccountLike(String type, String account) {
		return loanManagementActLegerDao.findByBusiTypeAndAccountLike(type, account);
	}

}
