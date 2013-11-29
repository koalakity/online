package com.zendaimoney.online.service.newLoanManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.AcTCustomerDAO;
import com.zendaimoney.online.dao.AcTLedgerDAO;
import com.zendaimoney.online.dao.AcTLedgerLoanDAO;
import com.zendaimoney.online.dao.InvestInfoDAO;
import com.zendaimoney.online.dao.LoanInfoDAO;
import com.zendaimoney.online.dao.OverdueClaimsDAO;
import com.zendaimoney.online.dao.SysMsgDAO;
import com.zendaimoney.online.dao.UserMessageSetDAO;
import com.zendaimoney.online.dao.UsersDAO;
import com.zendaimoney.online.dto.ConstsubjectDTO;
import com.zendaimoney.online.dto.PayBackInfoDTO;
import com.zendaimoney.online.dto.PayBackMessageDTO;
import com.zendaimoney.online.entity.AcTCustomerVO;
import com.zendaimoney.online.entity.AcTFlowVO;
import com.zendaimoney.online.entity.AcTLedgerLoanVO;
import com.zendaimoney.online.entity.AcTLedgerVO;
import com.zendaimoney.online.entity.InvestInfoVO;
import com.zendaimoney.online.entity.LoanInfoVO;
import com.zendaimoney.online.entity.OverdueClaimsId;
import com.zendaimoney.online.entity.OverdueClaimsVO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.service.common.FlowUtils;
import com.zendaimoney.online.threadPool.ThreadPool;

/**
 * 
 * @author 王腾飞 还款相关service层 包含 正常还款 逾期还款已经风险金代偿还款。
 */
@Component
public class PayBackService {
	@Autowired
	private LoanInfoDAO loanInfoDao;
	@Autowired
	private AcTLedgerDAO acTLedgerDao;
	@Autowired
	private InvestInfoDAO investInfoDao;
	@Autowired
	private PayBackSupporService payBackSupporService;
	@Autowired
	private AcTCustomerDAO acTCustomerDao;
	@Autowired
	private UsersDAO usersDao;
	@Autowired
	private FlowUtils flowUtils;
	@Autowired
	private OverdueClaimsDAO overdueClaimsDao;
	@Autowired
	private AcTLedgerLoanDAO acTLedgerLoanDao;
	@Autowired
	private MimeMailService mimeMailService;
	@Autowired
	private UserMessageSetDAO userMessageSetDao;
	@Autowired
	private SysMsgDAO sysMsgDao;
	//日志
	private static Logger logger = LoggerFactory.getLogger(PayBackService.class);
	/**
	 * 
	 * @param loanInfoId 借款id
	 * @param currentTermLoan
	 * @param payType 还款类型 分为 ：提前一次性还款(ahead)，正常分期还款( normalRepay)，自动还款（autoRepay）
	 * @param req
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public String payBackProcess(Long loanInfoId,String currentTermLoan, String payType, HttpServletRequest req) throws RuntimeException{
		//借款信息
		LoanInfoVO loanInfo = loanInfoDao.findOne(loanInfoId);
		AcTLedgerLoanVO acTLedgerLoan = acTLedgerLoanDao.findOne(loanInfo.getLedgerLoanId());
		List<PayBackMessageDTO> payBackMessages = null;
		String messageType = "rev_pay";
		if("autoRepay".equals(payType)){
			//自动还款
			payBackMessages = payBackByEachPeriod(loanInfo , acTLedgerLoan , 0, req);
		} else if("ahead".equals(payType)){
			logger.info("开始还款>>>>>>>>>>>>>还款类型：提前一次性还款");
			//提前一次性还款
			payBackMessages = payBackByOnce(loanInfo , acTLedgerLoan , req);
			logger.info("提前一次性还款结束................................");
			messageType = "rev_pay_advanced";
		}else{
			if(currentTermLoan.equals(acTLedgerLoan.getCurrNum().toString())){
				logger.info("开始还款>>>>>>>>>>>>>");
				payBackMessages = payBackByEachPeriod(loanInfo , acTLedgerLoan , 1, req);
				logger.info("还款结束................................");
			}else{
				return "payed";
			}
			
			}
		if(payBackMessages!=null){
			//使用另外的线程发短信
			PayBackMessageUtils messageTask = new PayBackMessageUtils(payBackMessages, loanInfo.getLoanTitle(),messageType,mimeMailService,userMessageSetDao,sysMsgDao);
			ThreadPool.getInstance().addTask(messageTask);
		}
		
		return null;

	}
	/**
	 * @descrip 一次性提前还款
	 * @param loanInfo
	 * @param acTLedgerLoan
	 * @param req
	 * @return
	 * @throws RuntimeException
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	private List<PayBackMessageDTO> payBackByOnce(LoanInfoVO loanInfo,AcTLedgerLoanVO acTLedgerLoan, HttpServletRequest req)throws RuntimeException{
		PayBackInfoDTO oncePayBackInfo = payBackSupporService.getOncePayBackDetailInfo(loanInfo);
		UsersVO user = usersDao.findOne(loanInfo.getUserId());
		//借款人  贷款分账
		AcTLedgerVO loanerAct2 = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "2");
		//借款人  资金分账
		AcTLedgerVO loanerAct4 = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "4");
		//借款人  投标冻结分账
		AcTLedgerVO loanerAct5 = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "5");
		// 借款人 核心账户
		AcTCustomerVO acTCustomer = acTCustomerDao.findOne(user.gettCustomerId());
		//借款人还款前金额
		BigDecimal loanerAct4OriginalAmount = loanerAct4.getAmount();
		//资金账户4扣除本次要还总额
		loanerAct4.setAmount(BigDecimalUtil.sub(loanerAct4.getAmount(), oncePayBackInfo.getTotalPayBackAmount()));
		if(!oncePayBackInfo.getCurrIsPayed()){
			//本金调账
			flowUtils.setActFlow(oncePayBackInfo.getPayBackPrincipal(), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctTeller(), loanerAct4.getAccount(), loanerAct2.getAccount(), NewConstSubject.once_pay_loaner_principal_adjust_out, NewConstSubject.once_pay_loaner_principal_adjust_in);
			//利息调账
			flowUtils.setActFlow(oncePayBackInfo.getPayBackInterest(), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctTeller(), loanerAct4.getAccount(), loanerAct2.getAccount(), NewConstSubject.once_pay_loaner_interest_adjust_out, NewConstSubject.once_pay_loaner_interest_adjust_in);
		}
		//剩余本金调账
		flowUtils.setActFlow(oncePayBackInfo.getSurplusPrincipal(),acTCustomer.getAuthTeller(), acTCustomer.getOpenacctTeller(), loanerAct4.getAccount(), loanerAct2.getAccount(), NewConstSubject.once_pay_loaner_surplusPrincipal_adjust_out, NewConstSubject.once_pay_loaner_surplusPrincipal_adjust_in);
		//一次性提前还款违约金
		flowUtils.setActFlow(oncePayBackInfo.getAdvanceOncePayBreachPenalty(),acTCustomer.getAuthTeller(), acTCustomer.getOpenacctTeller(), loanerAct4.getAccount(), loanerAct2.getAccount(), NewConstSubject.once_pay_loaner_penalty_adjust_out, NewConstSubject.once_pay_loaner_penalty_adjust_in);
		List<InvestInfoVO> investInfos = investInfoDao.findByLoanId(loanInfo.getLoanId());
		//借款人分账2维护
		payBackSupporService.maintainAcTLedger2(loanerAct2, oncePayBackInfo, 0);
		// 借款人贷款分户维护
		payBackSupporService.maintainAcTLedgerLoan(loanInfo, acTLedgerLoan,oncePayBackInfo.getNextAcTVirtualCashFlow(), 0);
		List<PayBackMessageDTO> messageList = new ArrayList<PayBackMessageDTO>();
		//偿还给理财人
		for(InvestInfoVO investInfo : investInfos){
			UsersVO investUser = usersDao.findOne(investInfo.getUserId());
			// 理财账户
			AcTLedgerVO inversterAct1 = acTLedgerDao.findByTotalAccountIdAndBusiType(investUser.gettCustomerId(), "1");
			//资金账户
			AcTLedgerVO inversterAct4 = acTLedgerDao.findByTotalAccountIdAndBusiType(investUser.gettCustomerId(), "4");
			//投标冻结账户
			AcTLedgerVO inversterAct5 = acTLedgerDao.findByTotalAccountIdAndBusiType(investUser.gettCustomerId(), "5");
			//理财人总账
			AcTCustomerVO inversterAcTCustomer = acTCustomerDao.findOne(investUser.gettCustomerId());
			if(!oncePayBackInfo.getCurrIsPayed()){
				/************偿还本金**********/
				//偿还本金后借款人的剩余金额
				loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, BigDecimalUtil.mul(oncePayBackInfo.getPayBackPrincipal(),investInfo.getHavaScale()));
				//偿还本金
				inversterAct4.setAmount(BigDecimalUtil.add(inversterAct4.getAmount(), BigDecimalUtil.mul(oncePayBackInfo.getPayBackPrincipal(),investInfo.getHavaScale())));
				//偿还本金流水
				AcTFlowVO repayPrincipalFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), oncePayBackInfo.getPayBackPrincipal()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct2.getAccount(), inversterAct1.getAccount(), NewConstSubject.once_pay_loaner_principal_out, NewConstSubject.once_pay_loaner_principal_in);
				/*******************小表流水***********************/
				flowUtils.setAcTFlowClassify(TradeTypeConstants.CHDQBJ, repayPrincipalFlow.getTradeNo(), loanInfo.getUserId(), loanerAct4OriginalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), investInfo.getUserId(), inversterAct4.getAmount(), inversterAct4.getPayBackAmt(), inversterAct5.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), oncePayBackInfo.getPayBackPrincipal()), loanInfo.getLoanId(),TypeConstants.ZJHS);
				//本金调账流水
				flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), oncePayBackInfo.getPayBackPrincipal()), inversterAcTCustomer.getAuthTeller(), inversterAcTCustomer.getOpenacctOrgan(), inversterAct1.getAccount(), inversterAct4.getAccount(), NewConstSubject.once_pay_invester_principal_adjust_out, NewConstSubject.once_pay_invester_principal_adjust_in);
				
				
				
				
				/*****************偿还利息****************************/
				//偿还利息后借款人的剩余金额
				loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, BigDecimalUtil.mul(oncePayBackInfo.getPayBackInterest(), investInfo.getHavaScale()));
				//偿还利息
				inversterAct4.setAmount(BigDecimalUtil.add(inversterAct4.getAmount(),  BigDecimalUtil.mul(oncePayBackInfo.getPayBackInterest(), investInfo.getHavaScale())));
				//偿还利息流水
				AcTFlowVO repayInterestFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), oncePayBackInfo.getPayBackInterest()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct2.getAccount(), inversterAct1.getAccount(), NewConstSubject.once_pay_interest_out, NewConstSubject.once_pay_interest_in);
				/*******************小表流水***********************/
				flowUtils.setAcTFlowClassify(TradeTypeConstants.CHDQLX, repayInterestFlow.getTradeNo(), loanInfo.getUserId(), loanerAct4OriginalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), investInfo.getUserId(), inversterAct4.getAmount(), inversterAct4.getPayBackAmt(), inversterAct5.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), oncePayBackInfo.getPayBackInterest()), loanInfo.getLoanId(),TypeConstants.ZJHS);
				// 利息调账
				flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), oncePayBackInfo.getPayBackInterest()), inversterAcTCustomer.getAuthTeller(), inversterAcTCustomer.getOpenacctOrgan(), inversterAct1.getAccount(), inversterAct4.getAccount(), NewConstSubject.once_pay_invester_interest_adjust_out, NewConstSubject.once_pay_invester_interest_adjust_in);
				/******************************/
				/*********流水 end**************/
				/******************************/
				
			}
			//最后一期剩余本金为0的情况不纪录流水
			if(oncePayBackInfo.getSurplusPrincipal().compareTo(BigDecimal.ZERO)==1){
				/************偿还剩余本金**********/
				//偿还剩余本金后借款人的剩余金额
				loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, BigDecimalUtil.mul(oncePayBackInfo.getSurplusPrincipal(), investInfo.getHavaScale()));
				//偿还剩余本金
				inversterAct4.setAmount(BigDecimalUtil.add(inversterAct4.getAmount(), BigDecimalUtil.mul(oncePayBackInfo.getSurplusPrincipal(),investInfo.getHavaScale())));
				//偿还剩余本金流水
				AcTFlowVO repaySurplusPrincipal = flowUtils.setActFlow(BigDecimalUtil.mul(oncePayBackInfo.getSurplusPrincipal(),investInfo.getHavaScale()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct2.getAccount(), inversterAct1.getAccount(), NewConstSubject.once_pay_surplusPrincipal_out, NewConstSubject.once_pay_surplusPrincipal_in);
				/*******************小表流水***********************/
				flowUtils.setAcTFlowClassify(TradeTypeConstants.CHSYBJ, repaySurplusPrincipal.getTradeNo(), loanInfo.getUserId(), loanerAct4OriginalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), investInfo.getUserId(), inversterAct4.getAmount(), inversterAct4.getPayBackAmt(), inversterAct5.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), oncePayBackInfo.getSurplusPrincipal()), loanInfo.getLoanId(),TypeConstants.ZJHS);
				//调账
				flowUtils.setActFlow(BigDecimalUtil.mul(oncePayBackInfo.getSurplusPrincipal(),investInfo.getHavaScale()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), inversterAct1.getAccount(), inversterAct4.getAccount(), NewConstSubject.once_pay_invester_surplusPrincipal_adjust_out, NewConstSubject.once_pay_invester_surplusPrincipal_adjust_in);
				
				/********偿还一次性提前还款违约金*******/
				//偿还一次性提前还款违约金后借款人的剩余金额
				loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, BigDecimalUtil.mul(oncePayBackInfo.getAdvanceOncePayBreachPenalty(), investInfo.getHavaScale()));
				inversterAct4.setAmount(BigDecimalUtil.add(inversterAct4.getAmount(),  BigDecimalUtil.mul(oncePayBackInfo.getAdvanceOncePayBreachPenalty(), investInfo.getHavaScale())));
				//偿还一次性提前还款违约金 流水
				AcTFlowVO repayOncePayBreachPenalty = flowUtils.setActFlow(BigDecimalUtil.mul(oncePayBackInfo.getAdvanceOncePayBreachPenalty(),investInfo.getHavaScale()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct2.getAccount(), inversterAct1.getAccount(), NewConstSubject.once_pay_penalty_out, NewConstSubject.once_pay_penalty_in);
				/*******************小表流水***********************/
				flowUtils.setAcTFlowClassify(TradeTypeConstants.TQHKWYJ, repayOncePayBreachPenalty.getTradeNo(), loanInfo.getUserId(), loanerAct4OriginalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), investInfo.getUserId(), inversterAct4.getAmount(), inversterAct4.getPayBackAmt(), inversterAct5.getAmount(), BigDecimalUtil.mul(oncePayBackInfo.getAdvanceOncePayBreachPenalty(),investInfo.getHavaScale()), loanInfo.getLoanId(),TypeConstants.ZJHS);
				//调账
				flowUtils.setActFlow(BigDecimalUtil.mul(oncePayBackInfo.getAdvanceOncePayBreachPenalty(),investInfo.getHavaScale()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), inversterAct1.getAccount(), inversterAct4.getAccount(), NewConstSubject.once_pay_invester_penalty_adjust_out, NewConstSubject.once_pay_invester_penalty_adjust_in);
			}

			//保存理财人信息
			acTLedgerDao.save(inversterAct4);
			// 理财人分账1维护
			payBackSupporService.maintainAcTLedger1(inversterAct1, oncePayBackInfo, investInfo.getHavaScale(), 0);
			//理财人 理财分户维护
			//最后一期还款
			if(oncePayBackInfo.getCurrNum().equals(loanInfo.getLoanDuration())){
				payBackSupporService.maintainAcTLedgerFinance(investInfo, "0", oncePayBackInfo.getNextAcTVirtualCashFlow(),loanInfo.getLoanDuration().intValue());
			}else{
				payBackSupporService.maintainAcTLedgerFinance(investInfo, "1", oncePayBackInfo.getNextAcTVirtualCashFlow(),loanInfo.getLoanDuration().intValue());
			}
			PayBackMessageDTO payBackMessage = payBackSupporService.generateMessage(investInfo, oncePayBackInfo.getCurrNum(), oncePayBackInfo);
			if(payBackMessage!=null){
				messageList.add(payBackMessage);
			}
		}
		
		//偿还月缴管理费
		if(!oncePayBackInfo.getCurrIsPayed()){
			//偿还月缴管理费后借款人的剩余金额
			loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, oncePayBackInfo.getManageFeeByMonth());
			payBackSupporService.payBackToZenDai(ZendaiAccountBank.zendai_acct1,oncePayBackInfo.getManageFeeByMonth());
			AcTFlowVO repayManageFeeByMonthFlow = flowUtils.setActFlow(oncePayBackInfo.getManageFeeByMonth(), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), ZendaiAccountBank.zendai_acct1, NewConstSubject.once_pay_month_manage_to_zendai_out, NewConstSubject.once_pay_month_manage_to_zendai_in);
			/*******************小表流水***********************/
			flowUtils.setAcTFlowClassify(TradeTypeConstants.YCXDQGLF, repayManageFeeByMonthFlow.getTradeNo(), loanInfo.getUserId(), loanerAct4OriginalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), null, null, null, null, oncePayBackInfo.getManageFeeByMonth(), loanInfo.getLoanId(),TypeConstants.ZJHS);
		}
		//维护虚拟现金流水表
		payBackSupporService.oncePayMaintainAcTVirtualCashFlow(oncePayBackInfo, loanInfo.getUserId());
		return messageList;
	}
	/**
	 * @descrip 分期还款
	 * @param loanInfoId 借款ID
	 * @param isAuto 是否为自动还款 0：代表自动还款 1：代表手动分期还款
	 * @param req
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	private List<PayBackMessageDTO> payBackByEachPeriod(LoanInfoVO loanInfo  , AcTLedgerLoanVO acTLedgerLoan , int isAuto , HttpServletRequest req)throws RuntimeException{
		
		UsersVO user = usersDao.findOne(loanInfo.getUserId());
		//借款人  贷款分账
		AcTLedgerVO loanerAct2 = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "2");
		//借款人  资金分账
		AcTLedgerVO loanerAct4 = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "4");
		//借款人  投标冻结分账
		AcTLedgerVO loanerAct5 = acTLedgerDao.findByTotalAccountIdAndBusiType(user.gettCustomerId(), "5");
		// 借款人 核心账户
		AcTCustomerVO acTCustomer = acTCustomerDao.findOne(user.gettCustomerId());
		BigDecimal loanerAct4OriginalAmount = loanerAct4.getAmount();
		List<PayBackMessageDTO> messageList = new ArrayList<PayBackMessageDTO>();
		PayBackInfoDTO payBackInfo =  payBackSupporService.getPayBackDetailInfo(loanInfo);
		if(isAuto==0){
			if(loanerAct4.getAmount().compareTo(payBackInfo.getTotalPayBackAmount())==-1){
				return null;
			}else if(payBackInfo.getCurrIsPayed()){
				logger.info("本期还款已经还清，不执行自动还款。借款ID："+loanInfo.getLoanId() + " 当前期数 ："+acTLedgerLoan.getCurrNum());
				return null;
			}else if(DateUtil.getDateyyyyMMdd().equals(DateUtil.getDateYYYYMMDD(payBackInfo.getCurrAcTVirtualCashFlow().getRepayDay(), "yyyy/MM/dd"))){
				logger.info("开始自动还款，借款ID："+loanInfo.getLoanId() +" 当前账户可用余额："+loanerAct4.getAmount()+" 当前期数："+payBackInfo.getCurrNum()+" 本期应还金额： "+payBackInfo.getTotalPayBackAmount());
			}else{
				logger.info("自动还款异常，借款ID："+loanInfo.getLoanId());
				return null;
			}
		} 
			BigDecimal repayScale = BigDecimal.ZERO;	
			ConstsubjectDTO subjectNO = ConstsubjectUtils.getSubjectNO(payBackInfo.getOverDueDays());
			List<InvestInfoVO> investInfos = investInfoDao.findByLoanId(loanInfo.getLoanId());
			if(payBackInfo.getOverDueDays()>30){
				repayScale = payBackSupporService.getRepayScale(investInfos, payBackInfo.getCurrNum());
			}
			//借款人本期应还金额
			loanerAct4.setAmount(BigDecimalUtil.sub(loanerAct4.getAmount(), payBackInfo.getTotalPayBackAmount()));
			
			//本金调账流水4-2
			BigDecimal principalTemp = BigDecimalUtil.mul(payBackInfo.getPayBackPrincipal(),BigDecimalUtil.sub(1, repayScale));
			flowUtils.setActFlow( principalTemp, acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), loanerAct2.getAccount(), subjectNO.getLoanerPrincipalAdjustOut(), subjectNO.getLoanerPrincipalAdjustIn());
			//利息调账流水4-2
			BigDecimal investTemp = BigDecimalUtil.mul(payBackInfo.getPayBackInterest(),BigDecimalUtil.sub(1, repayScale));
			flowUtils.setActFlow( investTemp, acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), loanerAct2.getAccount(), subjectNO.getLoanerInverestAdjustOut(), subjectNO.getLoanerInverestAdjustIn());
			if(payBackInfo.getOverDueDays()>0){
				//逾期罚息调账流水4-2
				BigDecimal overdueInterestTemp = BigDecimalUtil.mul(payBackInfo.getOverdueInterest(),BigDecimalUtil.sub(1, repayScale));
				flowUtils.setActFlow(overdueInterestTemp, acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), loanerAct2.getAccount(), subjectNO.getLoanerOverdueFreeAdjustOut(), subjectNO.getLoanerOverdueFreeAdjustIn());
			}
			
			
			//借款人分账2维护
			payBackSupporService.maintainAcTLedger2(loanerAct2, payBackInfo,1);
			//代偿统计
			int advanceCount = 0;
			//贷款分户维护
			payBackSupporService.maintainAcTLedgerLoan(loanInfo, acTLedgerLoan,payBackInfo.getNextAcTVirtualCashFlow(),1);
			for (InvestInfoVO investInfo : investInfos){
				Boolean isAdanced = false;
				//设置投资ID
				UsersVO investUser = usersDao.findOne(investInfo.getUserId());
				AcTLedgerVO inversterAct1 = acTLedgerDao.findByTotalAccountIdAndBusiType(investUser.gettCustomerId(), "1");
				AcTLedgerVO inversterAct4 = acTLedgerDao.findByTotalAccountIdAndBusiType(investUser.gettCustomerId(), "4");
				AcTLedgerVO inversterAct5 = acTLedgerDao.findByTotalAccountIdAndBusiType(investUser.gettCustomerId(), "5");
				AcTCustomerVO inversterAcTCustomer = acTCustomerDao.findOne(investUser.gettCustomerId());
				OverdueClaimsId overdueClaimsId = new OverdueClaimsId();
				overdueClaimsId.setInvestId(investInfo.getInvestId());
				overdueClaimsId.setNum(payBackInfo.getCurrNum());
				OverdueClaimsVO overdueClaims = overdueClaimsDao.findById(overdueClaimsId);
				if(payBackInfo.getOverDueDays()<=30){
					//包含正常还款，初级逾期还款
					messageList = normalRepay(loanerAct4OriginalAmount,investInfo, inversterAct4,inversterAct5, inversterAct1, loanInfo, loanerAct4,loanerAct5, loanerAct2, inversterAcTCustomer, inversterAcTCustomer, messageList, req, payBackInfo,isAdanced,subjectNO);
					
				}else{
					//查询当前投资的当期是否被代偿
					if(overdueClaims!=null&& overdueClaims.getIsAdvanced().intValue()==1){
						BigDecimal currAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, BigDecimalUtil.mul(investInfo.getHavaScale(),payBackInfo.getPayBackPrincipal()));
						advanceCount++;
						isAdanced = true;
						//偿还本金 和利息到 证大风险准备金账户：30000000000100000010011
						payBackSupporService.payBackToZenDai(ZendaiAccountBank.zendai_acct10, BigDecimalUtil.mul(investInfo.getHavaScale(), BigDecimalUtil.add(payBackInfo.getPayBackPrincipal(), payBackInfo.getPayBackInterest())));
						// 偿还本金 给证大 流水
						AcTFlowVO repayPrincipalToZendaiFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(),payBackInfo.getPayBackPrincipal()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), ZendaiAccountBank.zendai_acct10, NewConstSubject.pay_principal_to_zendai_out, NewConstSubject.pay_principal_to_zendai_in);
						/*******************小表流水***********************/
						flowUtils.setAcTFlowClassify(TradeTypeConstants.DCGJYQCHBJ, repayPrincipalToZendaiFlow.getTradeNo(), loanInfo.getUserId(), currAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), null, null, null, null,BigDecimalUtil.mul(investInfo.getHavaScale(),payBackInfo.getPayBackPrincipal()),loanInfo.getLoanId(),TypeConstants.ZJHS);
						
						currAmount = BigDecimalUtil.sub(currAmount, BigDecimalUtil.mul(investInfo.getHavaScale(),payBackInfo.getPayBackInterest()));
						// 偿还利息给证大 流水
						AcTFlowVO repayinterestToZendaiFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(),payBackInfo.getPayBackInterest()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), ZendaiAccountBank.zendai_acct10, NewConstSubject.pay_interest_to_zendai_out, NewConstSubject.pay_interest_to_zendai_in);
						/*******************小表流水***********************/
						flowUtils.setAcTFlowClassify(TradeTypeConstants.DCGJYQCHLX, repayinterestToZendaiFlow.getTradeNo(), loanInfo.getUserId(), currAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), null, null, null, null, BigDecimalUtil.mul(investInfo.getHavaScale(),payBackInfo.getPayBackInterest()),loanInfo.getLoanId(),TypeConstants.ZJHS);
						//偿还逾期罚息到 逾期罚息账户:30000000000100000010003
						payBackSupporService.payBackToZenDai(ZendaiAccountBank.zendai_acct2, BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest()));
						
						currAmount = BigDecimalUtil.sub(currAmount, BigDecimalUtil.mul(investInfo.getHavaScale(),payBackInfo.getOverdueInterest()));
						// 偿还逾期罚息给证大流水
						AcTFlowVO repayOverdueInterestToZendaiFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), ZendaiAccountBank.zendai_acct2, NewConstSubject.pay_overdueinterest_to_zendai_out, NewConstSubject.pay_overdueinterest_to_zendai_in);
						/*******************小表流水***********************/
						flowUtils.setAcTFlowClassify(TradeTypeConstants.DCGJYQCHYQFX, repayOverdueInterestToZendaiFlow.getTradeNo(), loanInfo.getUserId(), currAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), null, null, null, null, BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest()), loanInfo.getLoanId(),TypeConstants.ZJHS);
						subjectNO.setMonthManagerTradeTpye(TradeTypeConstants.DCGJYQCHGLF);
						subjectNO.setPenaltyTradeType(TradeTypeConstants.DCGJYQCHYQGLF);
						
					}else{
						messageList = normalRepay(loanerAct4OriginalAmount,investInfo, inversterAct4,inversterAct5, inversterAct1, loanInfo, loanerAct4,loanerAct5, loanerAct2, inversterAcTCustomer, inversterAcTCustomer, messageList, req, payBackInfo,isAdanced,subjectNO);
					}
				}
				loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, BigDecimalUtil.mul(investInfo.getHavaScale(), BigDecimalUtil.add(payBackInfo.getPayBackPrincipal(), BigDecimalUtil.add(payBackInfo.getPayBackInterest(), payBackInfo.getOverdueInterest()))));
				//维护分期债权信息
				if(payBackInfo.getOverDueDays()>0&&overdueClaims!=null){
					payBackSupporService.maintainOverdueClaims(overdueClaims, payBackInfo);
				}
			}
			//偿还月缴纳管理费后借款人剩余金额
			loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, payBackInfo.getManageFeeByMonth());
			//月缴纳管理费科目号
			String mouthMnaagerFreeOut = null;
			String mouthMnaagerFreeIn = null;
			//逾期违约金科目号
			String overdueFineOut = null;
			String overdueFineIn = null;
			if(advanceCount>0){
				mouthMnaagerFreeOut = NewConstSubject.pay_month_manage_to_zendai_out;
				mouthMnaagerFreeIn = NewConstSubject.pay_month_manage_to_zendai_in;
				overdueFineOut = NewConstSubject.pay_overduefine_to_zendai_out;
				overdueFineIn = NewConstSubject.pay_overduefine_to_zendai_in;
			}else {
				mouthMnaagerFreeOut = subjectNO.getMonthManageCostOut();
				mouthMnaagerFreeIn = subjectNO.getMonthManageCostIn();
				overdueFineOut = subjectNO.getPenaltyOut();
				overdueFineIn = subjectNO.getPenaltyIn();
			}
			//偿还月缴管理费
			payBackSupporService.payBackToZenDai(ZendaiAccountBank.zendai_acct1,payBackInfo.getManageFeeByMonth());
			AcTFlowVO repayManageFeeByMonthFlow = flowUtils.setActFlow(payBackInfo.getManageFeeByMonth(), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), ZendaiAccountBank.zendai_acct1, mouthMnaagerFreeOut, mouthMnaagerFreeIn);
			/*******************小表流水***********************/
			flowUtils.setAcTFlowClassify(subjectNO.getMonthManagerTradeTpye(), repayManageFeeByMonthFlow.getTradeNo(), loanInfo.getUserId(), loanerAct4OriginalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), null, null, null, null, payBackInfo.getManageFeeByMonth(), loanInfo.getLoanId(),TypeConstants.ZJHS);
		
			if(payBackInfo.getOverDueDays()>0){
				//偿还逾期违约金后借款人剩余金额
				loanerAct4OriginalAmount = BigDecimalUtil.sub(loanerAct4OriginalAmount, payBackInfo.getOverdueFines());
				//偿还逾期违约金 到证大逾期违约金账户：30000000000100000010004
				payBackSupporService.payBackToZenDai(ZendaiAccountBank.zendai_acct3,payBackInfo.getOverdueFines());
				//逾期违约金流水
				AcTFlowVO repayOverdueFinesFlow = flowUtils.setActFlow(payBackInfo.getOverdueFines(), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct4.getAccount(), ZendaiAccountBank.zendai_acct3, overdueFineOut, overdueFineIn);
				/*******************小表流水***********************/
				flowUtils.setAcTFlowClassify(subjectNO.getPenaltyTradeType(), repayOverdueFinesFlow.getTradeNo(), loanInfo.getUserId(), loanerAct4OriginalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), null, null, null, null, payBackInfo.getOverdueFines(), loanInfo.getLoanId(),TypeConstants.ZJHS);
			}
			
			//维护虚拟现金流水表
			payBackSupporService.maintainAcTVirtualCashFlow(payBackInfo, loanInfo.getUserId());
			//借款人信息
			acTLedgerDao.save(loanerAct4);
			
		
		
		return messageList;	
	}

	
	/** 正常还款 、初级逾期还款、高级逾期还款（不包含已经代偿的）
	 * @param investInfo
	 * @param inversterAct4
	 * @param inversterAct1
	 * @param loanInfo
	 * @param loanerAct4
	 * @param loanerAct2
	 * @param inversterAcTCustomer
	 * @param acTCustomer
	 * @param messageList
	 * @param req
	 * @param payBackInfo
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	private List<PayBackMessageDTO> normalRepay(BigDecimal originalAmount, InvestInfoVO investInfo,AcTLedgerVO inversterAct4,AcTLedgerVO inversterAct5,AcTLedgerVO inversterAct1,LoanInfoVO loanInfo,AcTLedgerVO loanerAct4 ,AcTLedgerVO loanerAct5 ,AcTLedgerVO loanerAct2,AcTCustomerVO inversterAcTCustomer,AcTCustomerVO  acTCustomer , List<PayBackMessageDTO> messageList,HttpServletRequest req,PayBackInfoDTO payBackInfo,Boolean isAdanced,ConstsubjectDTO subjectNO){
		/*******************偿还本金***********************/
		//偿还本金后借款人剩余金额
		originalAmount = BigDecimalUtil.sub(originalAmount, BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackPrincipal())); 
		// 正常还款 偿还本金
		inversterAct4.setAmount(BigDecimalUtil.add(inversterAct4.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackPrincipal())));
		//偿还本金流水
		AcTFlowVO repayPrincipalFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackPrincipal()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct2.getAccount(), inversterAct1.getAccount(), subjectNO.getPrincipalOut(), subjectNO.getPrincipalIn());
		/*******************小表流水***********************/
		flowUtils.setAcTFlowClassify(subjectNO.getPrincipalTradeType(), repayPrincipalFlow.getTradeNo(), loanInfo.getUserId(), originalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), investInfo.getUserId(), inversterAct4.getAmount(), inversterAct4.getPayBackAmt(), inversterAct5.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackPrincipal()), loanInfo.getLoanId(),TypeConstants.ZJHS);
		//本金调账
		flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackPrincipal()), inversterAcTCustomer.getAuthTeller(), inversterAcTCustomer.getOpenacctOrgan(), inversterAct1.getAccount(), inversterAct4.getAccount(), subjectNO.getInvesterPrincipalAdjustOut(), subjectNO.getInvesterPrincipalAdjustIn());
		
		/*******************偿还利息***********************/
		//偿还利息后借款人剩余金额
		originalAmount = BigDecimalUtil.sub(originalAmount, BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackInterest())); 
		//偿还利息
		inversterAct4.setAmount(BigDecimalUtil.add(inversterAct4.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackInterest())));
		//偿还利息
		AcTFlowVO repayInterestFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackInterest()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct2.getAccount(), inversterAct1.getAccount(), subjectNO.getInverestOut(), subjectNO.getInverestIn());
		/*******************小表流水***********************/
		flowUtils.setAcTFlowClassify(subjectNO.getInverestTradeType(), repayInterestFlow.getTradeNo(), loanInfo.getUserId(), originalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), investInfo.getUserId(), inversterAct4.getAmount(), inversterAct4.getPayBackAmt(), inversterAct5.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackInterest()),loanInfo.getLoanId(),TypeConstants.ZJHS);
		// 利息调账
		flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getPayBackInterest()), inversterAcTCustomer.getAuthTeller(), inversterAcTCustomer.getOpenacctOrgan(), inversterAct1.getAccount(), inversterAct4.getAccount(), subjectNO.getInvesterInverestAdjustOut(), subjectNO.getInvesterInverestAdjustIn());
		

		// 逾期还款 偿还逾期罚息
		if(payBackInfo.getOverDueDays()>0){
				//偿还逾期罚息后借款人剩余金额
				originalAmount = BigDecimalUtil.sub(originalAmount,  BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest()));
				//逾期罚息。
				inversterAct4.setAmount(BigDecimalUtil.add(inversterAct4.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest())));
				//偿还逾期罚息
				AcTFlowVO repayOverdueInterestFlow = flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest()), acTCustomer.getAuthTeller(), acTCustomer.getOpenacctOrgan(), loanerAct2.getAccount(), inversterAct1.getAccount(), subjectNO.getOverdueFreeOut(), subjectNO.getOverdueFreeIn());
				/*******************小表流水***********************/
				flowUtils.setAcTFlowClassify(subjectNO.getOverdueFreeTradeTpye(), repayOverdueInterestFlow.getTradeNo(), loanInfo.getUserId(), originalAmount, loanerAct4.getPayBackAmt(), loanerAct5.getAmount(), investInfo.getUserId(), inversterAct4.getAmount(), inversterAct4.getPayBackAmt(), inversterAct5.getAmount(), BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest()), loanInfo.getLoanId(),TypeConstants.ZJHS);
				//逾期罚息调账
				flowUtils.setActFlow(BigDecimalUtil.mul(investInfo.getHavaScale(), payBackInfo.getOverdueInterest()), inversterAcTCustomer.getAuthTeller(), inversterAcTCustomer.getOpenacctOrgan(), inversterAct1.getAccount(), inversterAct4.getAccount(), subjectNO.getInvesterOverdueFreeAdjustOut(), subjectNO.getInvesterOverdueFreeAdjustIn());
		}
		//理财人分账维护
		payBackSupporService.maintainAcTLedger1(inversterAct1, payBackInfo, investInfo.getHavaScale(),1);
		//最后一期还款
		if(payBackInfo.getCurrNum().equals(loanInfo.getLoanDuration())){
			payBackSupporService.maintainAcTLedgerFinance(investInfo, "0", payBackInfo.getNextAcTVirtualCashFlow(),1);
		}else{
			payBackSupporService.maintainAcTLedgerFinance(investInfo, "1", payBackInfo.getNextAcTVirtualCashFlow(),1);
		}
		// 保存理财人分账4
		acTLedgerDao.save(inversterAct4);
		PayBackMessageDTO payBackMessage = payBackSupporService.generateMessage(investInfo, payBackInfo.getCurrNum(), payBackInfo);
		if(payBackMessage!=null){
			messageList.add(payBackMessage);
		}
		return messageList;
	}
	
	/**从session中取当前登录用户的userId
	 * @param req
	 * @return
	 */
	public Long getUsersBySession(HttpServletRequest req) {
		if(req==null){
			return null;
		}
		HttpSession session = req.getSession();
		BigDecimal currUserIdStr = (BigDecimal) session
				.getAttribute("curr_login_user_id");
		return usersDao.findOne(currUserIdStr.longValue()).getUserId();
	}
}
