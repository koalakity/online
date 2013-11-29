package com.zendaimoney.online.service.newLoanManagement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.BusinessCalculateUtils;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.dao.AcTLedgerDAO;
import com.zendaimoney.online.dao.AcTLedgerFinanceDAO;
import com.zendaimoney.online.dao.AcTLedgerLoanDAO;
import com.zendaimoney.online.dao.AcTVirtualCashFlowDAO;
import com.zendaimoney.online.dao.InvestInfoDAO;
import com.zendaimoney.online.dao.LoanInfoDAO;
import com.zendaimoney.online.dao.OverdueClaimsDAO;
import com.zendaimoney.online.dao.UserInfoPersonDAO;
import com.zendaimoney.online.dao.UsersDAO;
import com.zendaimoney.online.dao.pay.PayDao;
import com.zendaimoney.online.dto.PayBackInfoDTO;
import com.zendaimoney.online.dto.PayBackMessageDTO;
import com.zendaimoney.online.entity.AcTLedgerFinanceVO;
import com.zendaimoney.online.entity.AcTLedgerLoanVO;
import com.zendaimoney.online.entity.AcTLedgerVO;
import com.zendaimoney.online.entity.AcTVirtualCashFlowVO;
import com.zendaimoney.online.entity.InvestInfoVO;
import com.zendaimoney.online.entity.LoanInfoVO;
import com.zendaimoney.online.entity.OverdueClaimsId;
import com.zendaimoney.online.entity.OverdueClaimsVO;
import com.zendaimoney.online.entity.UserInfoPersonVO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.service.common.RateCommonUtil;


/**
 * @author 王腾飞
 *
 * description：还款辅助类
 */
@Component
@Transactional(readOnly=true)
public class PayBackSupporService {
	@Autowired
	private AcTLedgerDAO acTLedgerDao;
	@Autowired
	private AcTLedgerLoanDAO acTLedgerLoanDao;
	@Autowired
	private OverdueClaimsDAO overdueClaimsDao;
	@Autowired
	private AcTVirtualCashFlowDAO acTVirtualCashFlowDao;
	@Autowired
	private RateCommonUtil rateCommonUtil;
	@Autowired
	private AcTLedgerFinanceDAO acTLedgerFinanceDao;
	@Autowired
	private InvestInfoDAO investInfoDao;
	@Autowired
	private LoanInfoDAO loanInfoDao;
	@Autowired
	private UsersDAO usersDao;
	@Autowired
	private UserInfoPersonDAO userInfoPersonDao;
	@Autowired
	private PayDao payDao;
	private static Logger logger = LoggerFactory.getLogger(PayBackSupporService.class);
	

	/**
	 * 临时方法
	 * 获取分期还款详细信息
	 * @param loanInfoId 借款ID
	 * @param req
	 * @return
	 */
	@Deprecated
	public PayBackInfoDTO getPayBackDetailInfo(Long loanId){
		LoanInfoVO loanInfo=loanInfoDao.findByLoanId(loanId);
		PayBackInfoDTO payBackInfo = new PayBackInfoDTO();
		int overDueDays = 0;
		LoanRateVO rate = null;
		// 逾期罚息比例
		BigDecimal overdueRate = BigDecimal.ZERO;
		// 逾期违约金
		BigDecimal overdueFinesRate = BigDecimal.ZERO;
		AcTLedgerLoanVO acTLedgerLoan = acTLedgerLoanDao.findOne(loanInfo.getLedgerLoanId());
		Long currNum = acTLedgerLoan.getCurrNum();
		AcTVirtualCashFlowVO currAcTVirtualCashFlow = null;
		//当前借款的还款计划
		List<AcTVirtualCashFlowVO> acTVirtualCashFlows = acTVirtualCashFlowDao.findByLoanId(loanInfo.getLedgerLoanId());
		payBackInfo.setAcTVirtualCashFlows(acTVirtualCashFlows);
		//上期还款日期
		Date lastRepayDay = null;
		//获取当期应还的还款计划
		for(AcTVirtualCashFlowVO obj:acTVirtualCashFlows){
			if(currNum.equals(obj.getCurrNum())){
				currAcTVirtualCashFlow = obj;
				payBackInfo.setCurrAcTVirtualCashFlow(currAcTVirtualCashFlow);
			}else if(currNum.intValue()+1==obj.getCurrNum().intValue()){
				payBackInfo.setNextAcTVirtualCashFlow(obj);
			}else if(obj.getCurrNum().intValue()==currNum.intValue()-1){
					lastRepayDay = obj.getRepayDay();
				}
		}
		if(currNum!=1&&DateUtil.getDateyyyyMMdd().getTime()<=lastRepayDay.getTime()){
			//当前已换
			payBackInfo.setCurrIsPayed(true);
		}
		Date temp = DateUtil.getCurrentDate();
		//逾期天数
		overDueDays = FormulaSupportUtil.getOverdueDays(currAcTVirtualCashFlow.getRepayDay(), temp);
		BigDecimal principalAndInterest = BigDecimalUtil.add(currAcTVirtualCashFlow.getPrincipalAmt(), currAcTVirtualCashFlow.getInterestAmt());
		payBackInfo.setOverDueDays(overDueDays);
		payBackInfo.setPayBackPrincipal(currAcTVirtualCashFlow.getPrincipalAmt());
		payBackInfo.setPayBackInterest(currAcTVirtualCashFlow.getInterestAmt());
		payBackInfo.setManageFeeByMonth(loanInfo.getMonthManageCost());
		payBackInfo.setCurrNum(currAcTVirtualCashFlow.getCurrNum());
		if(overDueDays>0){
			rate = rateCommonUtil.getLoanCoRate(loanInfo.getLoanId());
			overdueFinesRate = rate.getOverdueFines();
			// 剩余期数 
			int term = loanInfo.getLoanDuration().intValue()-currAcTVirtualCashFlow.getCurrNum().intValue()+1;
			if(overDueDays>30){
				//高级逾期罚息费率
				overdueRate = rate.getOverdueSeriousInterest();
			}else{
				//初级逾期罚息费率
				overdueRate = rate.getOverdueInterest();
			}
			// 逾期违约金
			payBackInfo.setOverdueFines(BusinessCalculateUtils.getOverdueFines(overDueDays, term, loanInfo.getMonthManageCost(), overdueFinesRate));
			// 逾期罚息
			payBackInfo.setOverdueInterest(BusinessCalculateUtils.calOverdueInterest(overDueDays, term, principalAndInterest,  overdueRate));			
		}	
		//当期应还总额
		payBackInfo.setTotalPayBackAmount(BusinessCalculateUtils.currentTermShouldPayAmount(payBackInfo.getManageFeeByMonth(), principalAndInterest, payBackInfo.getOverdueFines(), payBackInfo.getOverdueInterest()));
		return payBackInfo;
		
	}

	/**
	 * 获取分期还款详细信息
	 * @param loanInfoId 借款ID
	 * @param req
	 * @return
	 */
	public PayBackInfoDTO getPayBackDetailInfo(LoanInfoVO loanInfo){
		PayBackInfoDTO payBackInfo = new PayBackInfoDTO();
		int overDueDays = 0;
		LoanRateVO rate = null;
		// 逾期罚息比例
		BigDecimal overdueRate = BigDecimal.ZERO;
		// 逾期违约金
		BigDecimal overdueFinesRate = BigDecimal.ZERO;
		AcTLedgerLoanVO acTLedgerLoan = acTLedgerLoanDao.findOne(loanInfo.getLedgerLoanId());
		Long currNum = acTLedgerLoan.getCurrNum();
		AcTVirtualCashFlowVO currAcTVirtualCashFlow = null;
		//当前借款的还款计划
		List<AcTVirtualCashFlowVO> acTVirtualCashFlows = acTVirtualCashFlowDao.findByLoanId(loanInfo.getLedgerLoanId());
		payBackInfo.setAcTVirtualCashFlows(acTVirtualCashFlows);
		//上期还款日期
		Date lastRepayDay = null;
		//获取当期应还的还款计划
		for(AcTVirtualCashFlowVO obj:acTVirtualCashFlows){
			if(currNum.equals(obj.getCurrNum())){
				currAcTVirtualCashFlow = obj;
				payBackInfo.setCurrAcTVirtualCashFlow(currAcTVirtualCashFlow);
			}else if(currNum.intValue()+1==obj.getCurrNum().intValue()){
				payBackInfo.setNextAcTVirtualCashFlow(obj);
			}else if(obj.getCurrNum().intValue()==currNum.intValue()-1){
					lastRepayDay = obj.getRepayDay();
				}
		}
		if(currNum!=1&&DateUtil.getDateyyyyMMdd().getTime()<=lastRepayDay.getTime()){
			//当前已换
			payBackInfo.setCurrIsPayed(true);
		}
		Date temp = DateUtil.getCurrentDate();
		//逾期天数
		overDueDays = FormulaSupportUtil.getOverdueDays(currAcTVirtualCashFlow.getRepayDay(), temp);
		BigDecimal principalAndInterest = BigDecimalUtil.add(currAcTVirtualCashFlow.getPrincipalAmt(), currAcTVirtualCashFlow.getInterestAmt());
		payBackInfo.setOverDueDays(overDueDays);
		payBackInfo.setPayBackPrincipal(currAcTVirtualCashFlow.getPrincipalAmt());
		payBackInfo.setPayBackInterest(currAcTVirtualCashFlow.getInterestAmt());
		payBackInfo.setManageFeeByMonth(loanInfo.getMonthManageCost());
		payBackInfo.setCurrNum(currAcTVirtualCashFlow.getCurrNum());
		if(overDueDays>0){
			rate = rateCommonUtil.getLoanCoRate(loanInfo.getLoanId());
			overdueFinesRate = rate.getOverdueFines();
			// 剩余期数 
			int term = loanInfo.getLoanDuration().intValue()-currAcTVirtualCashFlow.getCurrNum().intValue()+1;
			if(overDueDays>30){
				//高级逾期罚息费率
				overdueRate = rate.getOverdueSeriousInterest();
			}else{
				//初级逾期罚息费率
				overdueRate = rate.getOverdueInterest();
			}
			// 逾期违约金
			payBackInfo.setOverdueFines(BusinessCalculateUtils.getOverdueFines(overDueDays, term, loanInfo.getMonthManageCost(), overdueFinesRate));
			// 逾期罚息
			payBackInfo.setOverdueInterest(BusinessCalculateUtils.calOverdueInterest(overDueDays, term, principalAndInterest,  overdueRate));			
		}	
		//当期应还总额
		payBackInfo.setTotalPayBackAmount(BusinessCalculateUtils.currentTermShouldPayAmount(payBackInfo.getManageFeeByMonth(), principalAndInterest, payBackInfo.getOverdueFines(), payBackInfo.getOverdueInterest()));
		return payBackInfo;
		
	}
	
	/**一次性提前还款信息
	 * @param loanInfo
	 */
	public PayBackInfoDTO getOncePayBackDetailInfo(LoanInfoVO loanInfo){
		PayBackInfoDTO payBackInfo = new PayBackInfoDTO();
		// 贷款分户
		AcTLedgerLoanVO acTLedgerLoan = acTLedgerLoanDao.findOne(loanInfo.getLedgerLoanId());
		// 当前期数
		Long currNum = acTLedgerLoan.getCurrNum();
		//上期还款日期
		Date lastRepayDay = null;
		//剩余本金
		BigDecimal surplusPrincipal = BigDecimal.ZERO;
		//剩余利息
		BigDecimal surplusInterest = BigDecimal.ZERO;
		LoanRateVO rate = rateCommonUtil.getLoanCoRate(loanInfo.getLoanId());
		BigDecimal oncePayBackFineRate = rate.getEarlyFines();
		List<AcTVirtualCashFlowVO> acTVirtualCashFlows = acTVirtualCashFlowDao.findByLoanId(loanInfo.getLedgerLoanId());
		payBackInfo.setAcTVirtualCashFlows(acTVirtualCashFlows);
		for(AcTVirtualCashFlowVO acTVirtualCashFlow : acTVirtualCashFlows){
			if(acTVirtualCashFlow.getCurrNum().equals(currNum)){
				payBackInfo.setCurrAcTVirtualCashFlow(acTVirtualCashFlow);
				surplusInterest = BigDecimalUtil.add(surplusInterest, acTVirtualCashFlow.getInterestAmt());
			}
			if(acTVirtualCashFlow.getCurrNum().intValue()==currNum.intValue()-1){
				lastRepayDay = acTVirtualCashFlow.getRepayDay();
			}
			if(acTVirtualCashFlow.getCurrNum().intValue() > currNum.intValue()){
				surplusPrincipal = BigDecimalUtil.add(surplusPrincipal, acTVirtualCashFlow.getPrincipalAmt());
				surplusInterest = BigDecimalUtil.add(surplusInterest, acTVirtualCashFlow.getInterestAmt());
			}
			
		}
		payBackInfo.setSurplusInterest(surplusInterest);
		/***********判断当期是否已还*************/
		if(currNum!=1&&DateUtil.getDateyyyyMMdd().getTime()<=lastRepayDay.getTime()){
			payBackInfo.setCurrIsPayed(true);
			surplusPrincipal = BigDecimalUtil.add(surplusPrincipal, payBackInfo.getCurrAcTVirtualCashFlow().getPrincipalAmt());
			payBackInfo.setManageFeeByMonth(BigDecimal.ZERO);
		}else{
			payBackInfo.setPayBackPrincipal(payBackInfo.getCurrAcTVirtualCashFlow().getPrincipalAmt());
			payBackInfo.setPayBackInterest(payBackInfo.getCurrAcTVirtualCashFlow().getInterestAmt());
			payBackInfo.setManageFeeByMonth(loanInfo.getMonthManageCost());
		}
		payBackInfo.setSurplusPrincipal(surplusPrincipal);
		payBackInfo.setCurrNum(currNum);
		//一次性提前还款违约金
		payBackInfo.setAdvanceOncePayBreachPenalty(BusinessCalculateUtils.getOverdueFinesComplateTotal(surplusPrincipal, oncePayBackFineRate)) ;
		//应还总额
		payBackInfo.setTotalPayBackAmount(BusinessCalculateUtils.getCurrentComplateShouldPayAmount(payBackInfo.getPayBackPrincipal(), payBackInfo.getPayBackInterest(), payBackInfo.getManageFeeByMonth(), surplusPrincipal, payBackInfo.getAdvanceOncePayBreachPenalty()));
		return payBackInfo;
		
	}
	
	/**偿还证大应收的各种费用，包含（偿还风险金代偿还款）
	 * @param account
	 * @param overdueFines
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void payBackToZenDai (String account , BigDecimal fines){
		AcTLedgerVO act = acTLedgerDao.findByAccount(account);
		act.setAmount(BigDecimalUtil.add(act.getAmount(), fines)); 
		acTLedgerDao.save(act);
	}
	
	
	/**维护进款信息
	 * @param loanInfo
	 * @param nextAcTVirtualCashFlow
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	private void maintainLoanInfo(LoanInfoVO loanInfo ,AcTVirtualCashFlowVO nextAcTVirtualCashFlow ){
		//下一期是否逾期
		int overDays = FormulaSupportUtil.getOverdueDays(nextAcTVirtualCashFlow.getRepayDay(), DateUtil.getCurrentDate());
		if(overDays>0){
			if(overDays>30){
				//下一期仍然是高级逾期
				loanInfo.setStatus(7L);
			}else{
				//下一期是初级逾期
				loanInfo.setStatus(6L);
			}
		}else{
			//下一期不逾期
			loanInfo.setStatus(4L);
		}
	}
	/**借款款人分账2维护
	 * @param act2
	 * @param payBackInfoDTO
	 * @param payType  还款类型  1：正常还款 0：一次性提前还款
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void maintainAcTLedger2(AcTLedgerVO act2,PayBackInfoDTO payBackInfoDTO,int payType) {
		//贷款本金
		act2.setLoanAmount(BigDecimalUtil.sub(act2.getLoanAmount(), payBackInfoDTO.getPayBackPrincipal()));
		//一次性提前还款 剩余本金
		if(payType==0){
			//剩余本金
			act2.setLoanAmount(BigDecimalUtil.sub(act2.getLoanAmount(), payBackInfoDTO.getSurplusPrincipal()));
			//一次性提前还款违约金
			act2.setOtherExpenditure(BigDecimalUtil.add(act2.getOtherExpenditure(), payBackInfoDTO.getAdvanceOncePayBreachPenalty()));
			//应付利息
			act2.setInterestPayable(BigDecimalUtil.sub(act2.getInterestPayable(), payBackInfoDTO.getSurplusInterest()));
		}else{
			//应付利息
			act2.setInterestPayable(BigDecimalUtil.sub(act2.getInterestPayable(), payBackInfoDTO.getPayBackInterest()));
		}
		
		//利息支出
		act2.setInterestExpenditure(BigDecimalUtil.add(act2.getInterestExpenditure(), payBackInfoDTO.getPayBackInterest()));
		// 逾期罚息
		act2.setOtherExpenditure(BigDecimalUtil.add(act2.getOtherExpenditure(), payBackInfoDTO.getOverdueInterest()));
		//逾期违约金
		act2.setOtherExpenditure(BigDecimalUtil.add(act2.getOtherExpenditure(), payBackInfoDTO.getOverdueFines()));
		acTLedgerDao.save(act2);
	}
	/**贷款分户维护
	 * @param acTLedgerLoan
	 * @param loanInfo
	 * @param currAcTVirtualCashFlow
	 * @param nextAcTVirtualCashFlow
	 * @param payType  还款类型  1：正常还款 0：一次性提前还款
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void maintainAcTLedgerLoan(LoanInfoVO loanInfo ,AcTLedgerLoanVO acTLedgerLoan, AcTVirtualCashFlowVO nextAcTVirtualCashFlow,int payType){
		/**********贷款分户维护begin******************/
		//AcTLedgerLoanVO acTLedgerLoan = acTLedgerLoanDao.findOne(loanInfo.getLedgerLoanId());
		// 还款后贷款分户信息维护
		if (acTLedgerLoan.getCurrNum().intValue() == loanInfo
				.getLoanDuration().intValue()||payType==0) {
			// 最后一期还款成功，进行贷款分户信息销户
			// 账号状态
			acTLedgerLoan.setAcctStatus("9");
			if(payType==0){
				// 当前期数
				acTLedgerLoan.setCurrNum(loanInfo.getLoanDuration());
			}
			// 上次结息日
			acTLedgerLoan.setLastExpiry(DateUtil.getDateyyyyMMdd());
			// 下次结息日
			acTLedgerLoan.setNextExpiry(null);
			// 分期还款额
			acTLedgerLoan.setEachRepayment(BigDecimal.ZERO);
			// 贷款余额
			acTLedgerLoan.setOutstanding(BigDecimal.ZERO);
			loanInfo.setStatus(5L);
			loanInfoDao.save(loanInfo);
			
		} else {
			// 上次结息日
			acTLedgerLoan.setLastExpiry(DateUtil.getDateyyyyMMdd());
			// 下次结息日
			acTLedgerLoan.setNextExpiry(nextAcTVirtualCashFlow.getRepayDay());
			// 分期还款额
			acTLedgerLoan.setEachRepayment(BigDecimalUtil.add(nextAcTVirtualCashFlow.getPrincipalAmt(), nextAcTVirtualCashFlow.getInterestAmt()));
			// 当前期数
			acTLedgerLoan.setCurrNum(acTLedgerLoan.getCurrNum() + 1);
			
			acTLedgerLoan.setOutstanding(BigDecimalUtil.sub(
					acTLedgerLoan.getOutstanding(),
					nextAcTVirtualCashFlow.getPrincipalAmt()));
			//维护借款信息
			maintainLoanInfo(loanInfo, nextAcTVirtualCashFlow);
			
			}
			// 维护完成 保存贷款分户信息
		acTLedgerLoanDao.save(acTLedgerLoan);
		/**********贷款分户维护end******************/
	}
	
	/**理财分户信息表维护
	 * @param acTLedgerFinance
	 * @param flag 0：代表最后一期 
	 * @param payType  还款类型  1：正常还款 0：一次性提前还款
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void maintainAcTLedgerFinance(InvestInfoVO investInfo , String flag , AcTVirtualCashFlowVO nextAcTVirtualCashFlow,int payType){
		
			AcTLedgerFinanceVO acTLedgerFinance = investInfo.getAcTLedgerFinance();
			//最后一期还款
			if("0".equals(flag)||payType!=1){
				// 投资状态设置为已结标
				investInfo.setStatus("4");
				//当期应收利息
				acTLedgerFinance.setInterestReceivable(BigDecimal.ZERO);
				if(payType>1){
					acTLedgerFinance.setCurrNum(new Long(payType));
				}
				// 理财分户销户
				acTLedgerFinance.setAcctStatus("9");
				//未还期数
				acTLedgerFinance.setRemainNum(0l);
				investInfoDao.save(investInfo);
			}else{
				//当期应收利息
				acTLedgerFinance.setInterestReceivable(BigDecimalUtil.mul(nextAcTVirtualCashFlow.getInterestAmt(), investInfo.getHavaScale()));
				//未还期数
				acTLedgerFinance.setRemainNum(acTLedgerFinance.getRemainNum()-1);
				//当前期数
				acTLedgerFinance.setCurrNum(nextAcTVirtualCashFlow.getCurrNum());
			}
			acTLedgerFinanceDao.save(acTLedgerFinance);
		
		
	}
	

	/**理财人分账1维护
	 * @param act1 
	 * 				 理财人分账1
	 * @param payBackInfoDTO
	 * 						还款信息
	 * @param payType  还款类型  1：正常还款 0：一次性提前还款
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void maintainAcTLedger1(AcTLedgerVO act1 , PayBackInfoDTO payBackInfoDTO , BigDecimal haveSacle,int payType) {
		if(payType==0){
			// 剩余本金
			act1.setDebtAmount(BigDecimalUtil.sub(act1.getDebtAmount() , BigDecimalUtil.mul(BigDecimalUtil.add(payBackInfoDTO.getSurplusPrincipal(), payBackInfoDTO.getPayBackPrincipal()), haveSacle)));
			// 债务减去一次性提前还款违约金
			//act1.setDebtAmount(BigDecimalUtil.sub(act1.getDebtAmount() , BigDecimalUtil.mul(payBackInfoDTO.getAdvanceOncePayBreachPenalty(), haveSacle)));
			// 应收利息
			act1.setInterestReceivable(BigDecimalUtil.sub(act1.getInterestReceivable(), BigDecimalUtil.mul(payBackInfoDTO.getSurplusInterest(), haveSacle)));
			// 利息收入
			act1.setInterestIncome(BigDecimalUtil.add(act1.getInterestIncome(), BigDecimalUtil.mul(payBackInfoDTO.getPayBackInterest(), haveSacle)));
			//提前还款违约金收入
			act1.setOtherIncome(BigDecimalUtil.add(act1.getOtherIncome(),BigDecimalUtil.mul(payBackInfoDTO.getAdvanceOncePayBreachPenalty(), haveSacle)));
		}else{
			// 当前投资
			act1.setDebtAmount(BigDecimalUtil.sub(act1.getDebtAmount() , BigDecimalUtil.mul(payBackInfoDTO.getPayBackPrincipal(), haveSacle)));
			// 应收利息
			act1.setInterestReceivable(BigDecimalUtil.sub(act1.getInterestReceivable(), BigDecimalUtil.mul(payBackInfoDTO.getPayBackInterest(), haveSacle)));
			// 利息收入
			act1.setInterestIncome(BigDecimalUtil.add(act1.getInterestIncome(), BigDecimalUtil.mul(payBackInfoDTO.getPayBackInterest(), haveSacle)));
			// 逾期罚息
			act1.setOtherIncome(BigDecimalUtil.add(act1.getOtherIncome(),BigDecimalUtil.mul(payBackInfoDTO.getOverdueInterest(), haveSacle)));
		}
		
		
		acTLedgerDao.save(act1);
	}
	
	/**虚拟现金流水表维护
	 * @param currAcTVirtualCashFlow
	 * @param payBackInfoDTO
	 * @param userId
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void maintainAcTVirtualCashFlow(PayBackInfoDTO payBackInfoDTO ,Long userId)throws RuntimeException{
		AcTVirtualCashFlowVO currAcTVirtualCashFlow  = payBackInfoDTO.getCurrAcTVirtualCashFlow();
		currAcTVirtualCashFlow.setAmt(payBackInfoDTO.getTotalPayBackAmount());
		// 修改日期即还款日期
		currAcTVirtualCashFlow.setEditDate(new Date());
		// 修改人id
		currAcTVirtualCashFlow.setEditUserId(userId);
		if(payBackInfoDTO.getOverDueDays()>0){
			currAcTVirtualCashFlow.setRepayStatus(4L);//逾期还款
			currAcTVirtualCashFlow.setOverDueFineAmount(payBackInfoDTO.getOverdueFines());
			currAcTVirtualCashFlow.setOverDueInterestAmount(payBackInfoDTO.getOverdueInterest());
			currAcTVirtualCashFlow.setOverDueDays(payBackInfoDTO.getOverDueDays());
		}else {
			currAcTVirtualCashFlow.setRepayStatus(1L);//正常还款
		}
		//acTVirtualCashFlowDao.save(currAcTVirtualCashFlow);
		AcTVirtualCashFlowVO updateCondition = new AcTVirtualCashFlowVO();
		updateCondition.setAmt(BigDecimal.ZERO);
		updateCondition.setRepayStatus(0L);
		updateCondition.setId(currAcTVirtualCashFlow.getId());
		int updateResult = payDao.update(currAcTVirtualCashFlow, updateCondition);
		if(updateResult!=1){
			logger.info("还款失败.........................................");
			throw new RuntimeException("该期借款已还款，请刷新页面查看最新数据。");
		}
	}
	
	/**一次性提前还款虚拟现金流水表维护
	 * @param currAcTVirtualCashFlow
	 * @param payBackInfoDTO
	 * @param userId
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void oncePayMaintainAcTVirtualCashFlow(PayBackInfoDTO payBackInfo ,Long userId) throws RuntimeException{
		List<AcTVirtualCashFlowVO> acTVirtualCashFlows = payBackInfo.getAcTVirtualCashFlows();
		for(AcTVirtualCashFlowVO acTVirtualCashFlow : acTVirtualCashFlows){
			if(acTVirtualCashFlow.getCurrNum().equals(payBackInfo.getCurrNum())){
				//①当期已还
				if(payBackInfo.getCurrIsPayed()){
					acTVirtualCashFlow.setRepayStatus(2L);//一次性提前还款
					acTVirtualCashFlow.setAmt(BigDecimalUtil.add(acTVirtualCashFlow.getPrincipalAmt(), payBackInfo.getAdvanceOncePayBreachPenalty()));
				//②当期未还
				}else {
					acTVirtualCashFlow.setRepayStatus(1L);//正常还款
					acTVirtualCashFlow.setAmt(BigDecimalUtil.sub(payBackInfo.getTotalPayBackAmount(), payBackInfo.getSurplusPrincipal()));
				}
			}else if(acTVirtualCashFlow.getCurrNum().intValue() > payBackInfo.getCurrNum().intValue()){
				acTVirtualCashFlow.setRepayStatus(2L);//一次性提前还款
				acTVirtualCashFlow.setAmt(acTVirtualCashFlow.getPrincipalAmt());
				
			}else{
				continue;
			}
			acTVirtualCashFlow.setEditDate(DateUtil.getDateyyyyMMdd());
			acTVirtualCashFlow.setEditUserId(userId);
			//acTVirtualCashFlowDao.save(acTVirtualCashFlow);
			AcTVirtualCashFlowVO updateCondition = new AcTVirtualCashFlowVO();
			updateCondition.setAmt(BigDecimal.ZERO);
			updateCondition.setRepayStatus(0L);
			updateCondition.setId(acTVirtualCashFlow.getId());
			int updateResult = payDao.update(acTVirtualCashFlow, updateCondition);
			if(updateResult!=1){
				logger.info("还款失败.........................................");
				throw new RuntimeException("该期借款已还款，请刷新页面查看最新数据。");
			}
		}
	}
	/**
	 * @param investInfos
	 * @param num
	 * @return
	 */
	public BigDecimal getRepayScale(List<InvestInfoVO> investInfos ,Long num){
		BigDecimal Scale = BigDecimal.ZERO;
		for(InvestInfoVO investInfo:investInfos){
			// 当前债权
			OverdueClaimsVO overdueClaims = overdueClaimsDao.findById(new OverdueClaimsId(investInfo.getInvestId(), num));
			if(overdueClaims!=null&&overdueClaims.getStatus().intValue()==2){
				Scale = BigDecimalUtil.add(Scale, investInfo.getHavaScale());
			}
		}
		return Scale;
		
	}
	
	/**还款后维护分期债权
	 * @param overdueClaims
	 * @param payBackInfo
	 */
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void maintainOverdueClaims(OverdueClaimsVO overdueClaims,PayBackInfoDTO payBackInfo){
			//设置为逾期还款
			overdueClaims.setStatus(3L);
			
	}

	
	/**还款后保存要发给理财人的短信
	 * @param messageList
	 * @param investInfo
	 * @param num
	 * @param payBackInfo
	 * @return
	 */
	public PayBackMessageDTO generateMessage(InvestInfoVO investInfo,Long num,PayBackInfoDTO payBackInfo){
		UsersVO user = usersDao.findOne(investInfo.getUserId());
		UserInfoPersonVO userInfoPerson = userInfoPersonDao.findByUserId(investInfo.getUserId());
		OverdueClaimsId overdueClaimsId = new OverdueClaimsId();
		overdueClaimsId.setInvestId(investInfo.getInvestId());
		overdueClaimsId.setNum(num);
		OverdueClaimsVO overdueClaims = overdueClaimsDao.findById(overdueClaimsId);
		//查询当前投资的当期是否被代偿
		if(overdueClaims!=null&&BigDecimal.ONE.equals(overdueClaims.getIsAdvanced())){
			return null;
		}
		PayBackMessageDTO message = new PayBackMessageDTO();
		message.setInvestId(investInfo.getInvestId());
		message.setUserId(investInfo.getUserId());
		message.setPhoneNO(userInfoPerson.getPhoneNo());
		message.setLoginName(user.getLoginName());
		message.setEmail(user.getEmail());
		//逾期违约金
		message.setOverdueFines(BigDecimalUtil.formatCurrency(BigDecimalUtil.mul(payBackInfo.getOverdueFines(),investInfo.getHavaScale())));
		//月还本息
		message.setPrincipanInterestMonth(BigDecimalUtil.formatCurrency(BigDecimalUtil.mul(BigDecimalUtil.add(payBackInfo.getPayBackPrincipal(), payBackInfo.getPayBackInterest()), investInfo.getHavaScale())));
		//逾期罚息
		message.setOverdueInterest(BigDecimalUtil.formatCurrency(BigDecimalUtil.mul(payBackInfo.getOverdueInterest(), investInfo.getHavaScale())));
		//剩余本金
		message.setSurplusPrincipal(BigDecimalUtil.formatCurrency(BigDecimalUtil.mul(payBackInfo.getSurplusPrincipal(), investInfo.getHavaScale())));
		//一次性提前还款违约金
		message.setAdvanceOncePayBreachPenalty(BigDecimalUtil.formatCurrency(BigDecimalUtil.mul(payBackInfo.getAdvanceOncePayBreachPenalty(), investInfo.getHavaScale())));
		return message;
	}

}
