package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zendaimoney.online.admin.dao.account.AdminMemoNoteDao;
import com.zendaimoney.online.admin.dao.account.MemoNoteAdminDao;
import com.zendaimoney.online.admin.dao.account.UserInfoDao;
import com.zendaimoney.online.admin.dao.audit.AuditNoteDao;
import com.zendaimoney.online.admin.dao.extract.ExtractNoteDao;
import com.zendaimoney.online.admin.dao.loan.LoanManagerDao;
import com.zendaimoney.online.admin.dao.loan.LoanNoteMangerDao;
import com.zendaimoney.online.admin.entity.RepayFlowDetailRepVO;
import com.zendaimoney.online.admin.entity.account.AccountMemoNoteAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.account.MemoNoteAdmin;
import com.zendaimoney.online.admin.entity.audit.ReviewMemoNoteAdmin;
import com.zendaimoney.online.admin.entity.extract.ExtractNoteAdmin;
import com.zendaimoney.online.admin.entity.fundDetail.ActVirtualCashFlow;
import com.zendaimoney.online.admin.entity.loan.AcTLedgerLoanAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanNoteAdmin;
import com.zendaimoney.online.admin.util.FundsMigrate;
import com.zendaimoney.online.admin.vo.FundDetailVOAdmin;
import com.zendaimoney.online.admin.vo.MemoNoteList;
import com.zendaimoney.online.admin.vo.RepayLoanDetailLate;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.dao.AcTFlowClassifyDAO;
import com.zendaimoney.online.dao.borrowing.BorrowingUserDao;
import com.zendaimoney.online.dao.borrowing.ReleaseLoanDao;
import com.zendaimoney.online.dao.fundDetail.FundDao;
import com.zendaimoney.online.dao.fundDetail.OldFlowShowDao;
import com.zendaimoney.online.dao.homepage.HomepageInvestInfoDao;
import com.zendaimoney.online.dao.homepage.HomepageLoanInfoDao;
import com.zendaimoney.online.entity.AcTFlowClassifyVO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.entity.fundDetail.FundUserCreditNote;
import com.zendaimoney.online.entity.homepage.HomepageInvestInfo;
import com.zendaimoney.online.entity.homepage.HomepageLoanInfo;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.service.fundDetail.FundManager;
import com.zendaimoney.online.vo.fundDetail.FundFlowVO;
import com.zendaimoney.online.web.DTO.AcTFlowClassifyDTO;

@Service
@Transactional
public class UserInfoService {

	private static Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	private static final String[] str=new String[]{"时间","类型","存入","支出","结余","支出方昵称","收入方昵称","备注"};
	private static final String[] strOld=new String[]{"时间","类型","存入","支出","备注"};
	
	@Autowired
	private AcTFlowClassifyDAO acTFlowClassifyDAO;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private FundDao dao;
	@Autowired
	private BorrowingUserDao borrowingUserDao;
	@Autowired
	private MemoNoteAdminDao memoNoteAdminDao;

	@Autowired
	private LoanManagerDao loanManagerDao;

//	@Autowired
//	private CommonDao commonDao;
	@Autowired
	private RateCommonUtil rateCommonUtil;

	@Autowired
	private AuditNoteDao auditNoteDao;

	@Autowired
	private LoanNoteMangerDao loanNoteDao;

	@Autowired
	private ExtractNoteDao extractNoteDao;

	@Autowired
	private FundManager fundManager;

	@Autowired
	private AdminMemoNoteDao adminMemoNoteDao;

	@Autowired
	private ReleaseLoanDao releaseLoanDao;

	@Autowired
	HomepageInvestInfoDao investInfoDao;
	@Autowired
	HomepageLoanInfoDao loanInfoDao;
	@Autowired
	private OldFlowShowDao oldFlowShowDao;

	public FundDetailVOAdmin userInfo(BigDecimal userId) {
		FundDetailVOAdmin VO = new FundDetailVOAdmin();
		int successLoanNumber = 0; // 成功借款笔数
		int successLoanOutNumber = 0; // 成功借出笔数
		BigDecimal overdueCount = userInfoDao.getOverdueCount(userId);// 逾期次数

		List<HomepageLoanInfo> loanList = loanInfoDao.findByUserIdOrderByReleaseTimeDesc(userId);
		for (HomepageLoanInfo loanInfo : loanList) {
			// 发布借款笔数
			int status = loanInfo.getStatus().intValue();
			if (status == 4 || status == 5 || status == 6 || status == 7) {
				// 成功借款笔数
				successLoanNumber++;

			}
		}
		List<HomepageInvestInfo> investList = investInfoDao.findByUserIdOrderByInvestTimeDesc(userId);
		for (HomepageInvestInfo investInfo : investList) {
			// 投标笔数
			String status = investInfo.getStatus();
			if (status.equals("3") || status.equals("4")) {
				// 成功投标笔数
				successLoanOutNumber++;

			}
		}
		BorrowingUsers borrower = borrowingUserDao.findByUserId(userId);
		double amount_total = dao.getBalance(userId);// 查询账户余额
		FundUserCreditNote userCreditNote = dao.getUserCreditNoteByUserId(userId);// 查询用户信用信息
		double amount_loan = dao.getLoanInfoByUserId(userId);// 用户贷款总额
		double amount_success_recharge = dao.getAmountSuccessRecharge(userId);// 成功充值总金额
		double freeze_lc = dao.getFreezeFundsFinancialByUserId(userId);// 投标冻结金额
		double freeze_tx = dao.getFreezeFundsWithdrawByUserId(userId);// 提现冻结金额
		double amount_success_withdraw = dao.getAmountSuccessWithdraw(userId);// 成功提现总额
		double amount_invest_tatol = dao.getTotalAmountInvest(userId);// 总借出金额：=当前用户作为理财人，（还款中的）∑投标金额
		double principal_interest_recv = dao.getInvestPrincipalInterest(userId, true);// 查询
																						// 已收回本息：=所有借款人对当前用户已归还本息求和
		double principal_interest_unrecv = dao.getInvestPrincipalInterest(userId, false);// 待收回本息
		double amount_loan_tatol = dao.getLoanAmountTotal(userId);// 已还清,还在款总额
		double amount_loan_paid = dao.getLoanPrincipalInterest(userId, true);// 已还本息
		double amount_loan_unpay = dao.getLoanPrincipalInterest(userId, false);// 未还本息息

		BigDecimal xyed = releaseLoanDao.getUserAvailableCredit(borrower.getUserId());
		double kyed = 0;

		String loginName = userInfoDao.getUserLoginName(userId);
		String realName = userInfoDao.getUserRealName(userId);

		// 账户余额只显示小数点后2位，后面的数据都不显示不四舍五入
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		String amount = formater.format(amount_total);
		VO.setLoginName(loginName);
		VO.setRealName(realName);
		VO.setUserId(userId);
		VO.setZhye(ObjectFormatUtil.formatCurrency(Calculator.add(Calculator.add(freeze_lc, freeze_tx), Double.parseDouble(amount))));// 账户余额
		VO.setKyye(ObjectFormatUtil.formatCurrency(Double.parseDouble(amount)));// 可用余额
		VO.setDjzy(ObjectFormatUtil.formatCurrency(Calculator.add(freeze_lc, freeze_tx)));// 冻结总额
		VO.setXyed(ObjectFormatUtil.formatCurrency(xyed.doubleValue()));
		if (userCreditNote != null && userCreditNote.getCreditAmount() != null) {
			kyed = fundManager.showAvailableMoney(borrower.getUserId()).doubleValue();
			VO.setKyed(ObjectFormatUtil.formatCurrency(kyed));// 可用额度
		}
		VO.setUsedAmount(ObjectFormatUtil.formatCurrency(Calculator.sub(xyed.doubleValue(), kyed)));
		VO.setLoanTotle(ObjectFormatUtil.formatCurrency(amount_loan));
		VO.setSuccessLoanNum(new BigDecimal(successLoanNumber).toString());
		VO.setOverdueCount(overdueCount.toString());// 待定
		VO.setZjcje(ObjectFormatUtil.formatCurrency(amount_invest_tatol));
		VO.setSuccessLoanOutNum(new BigDecimal(successLoanOutNumber).toString());
		VO.setDhbx(ObjectFormatUtil.formatCurrency(principal_interest_unrecv));

		return VO;
	}

	public List<MemoNoteAdmin> findUserInfoMemo(BigDecimal userId) {
		AccountUsersAdmin accountUsersAdmin = new AccountUsersAdmin();
		accountUsersAdmin.setUserId(userId);
		return memoNoteAdminDao.findByAccountUsersAdmin(accountUsersAdmin);
	}

	public Page<MemoNoteList> findUserInfoMemoPag(PageRequest pageRequest, final BigDecimal userId) {
		AccountUsersAdmin accountUsersAdmin = new AccountUsersAdmin();
		accountUsersAdmin.setUserId(userId);
		List<MemoNoteList> memoList = new ArrayList<MemoNoteList>();
		// 审核备注
		List<ReviewMemoNoteAdmin> reviewMemoList = auditNoteDao.findByUserIdOrderByOperateTimeDesc(userId);
		for (ReviewMemoNoteAdmin reviewMemo : reviewMemoList) {
			MemoNoteList memo = new MemoNoteList();
			memo.setMemoText(reviewMemo.getMemo());
			memo.setName(reviewMemo.getStaff().getName());
			memo.setOperateTime(reviewMemo.getOperateTime());
			memoList.add(memo);
		}
		// 借款备注
		List<LoanNoteAdmin> loanNoteList = loanNoteDao.findByLoanInfoAccountUsersUserId(userId);
		for (LoanNoteAdmin loanNote : loanNoteList) {
			MemoNoteList memo = new MemoNoteList();
			memo.setMemoText(loanNote.getMemoText());
			memo.setName(loanNote.getStaff().getName());
			memo.setOperateTime(loanNote.getOperateTime());
			memoList.add(memo);
		}
		// 提现备注
		List<ExtractNoteAdmin> extractNoteList = extractNoteDao.findByAccountUserUserId(userId);
		for (ExtractNoteAdmin extractNote : extractNoteList) {
			MemoNoteList memo = new MemoNoteList();
			memo.setMemoText(extractNote.getDescription());
			memo.setOperateTime(extractNote.getExtractTime());
			memoList.add(memo);
		}
		// 用户信息备注
		List<AccountMemoNoteAdmin> memoNoteList = adminMemoNoteDao.findByUserId(userId);
		for (AccountMemoNoteAdmin memoNote : memoNoteList) {
			MemoNoteList memo = new MemoNoteList();
			memo.setMemoText(memoNote.getMemoText());
			memo.setName(memoNote.getStaff().getName());
			memo.setOperateTime(memoNote.getOperateTime());
			memoList.add(memo);
		}
		Collections.sort(memoList, new Comparator<MemoNoteList>() {
			public int compare(MemoNoteList b1, MemoNoteList b2) {
				return b2.getOperateTime().compareTo(b1.getOperateTime());
			}
		});

		List<MemoNoteList> memoListP = new ArrayList<MemoNoteList>();
		int pageNum = pageRequest.getPageNumber() + 1;
		int s = pageNum * pageRequest.getPageSize() - pageRequest.getPageSize();
		int acc = 0;
		for (int m = s; m < memoList.size(); m++) {
			memoListP.add(memoList.get(m));
			acc++;
			if (acc == pageRequest.getPageSize()) {
				break;
			}
		}
		return new PageImpl<MemoNoteList>(memoListP, pageRequest, memoList.size());
	}

	/**
	 * @author Ray
	 * @date 2012-12-11 上午10:57:30修改
	 * @param pageRequest
	 * @param userId
	 * @return description:
	 */
	public Page<RepayLoanDetailLate> findLoanHisLate(PageRequest pageRequest, final BigDecimal userId) {
		List<LoanInfoAdmin> loanInfoList = loanManagerDao.findByAccountUsersUserId(userId);
		
		// 逾期天数
		int overdueDays = 0;
		//罚息比例
		double overdueRate = 0;
		// 逾期罚息
		double currentOverdueInterest = 0;
		// 逾期违约金
		double currentOverdueFines = 0;
		// 剩余未还期数
		int surplusTerm = 0;
		List<RepayLoanDetailLate> repayLoanLate = new ArrayList<RepayLoanDetailLate>();
		for (LoanInfoAdmin loanInfo : loanInfoList) {
			LoanRateVO rate = rateCommonUtil.getLoanCoRate(loanInfo.getLoanId().longValue());
			/* 2012-12-11 Ray 修改bug:OL-981 start */
			Long loanId = 0L;
			if (null != loanInfo.getLoanId()) {
				loanId = Long.parseLong(String.valueOf(loanInfo.getLoanId()));
			}
			/* end */
			if (loanInfo.getReleaseStatus().equals(BigDecimal.ONE) && !loanInfo.getStatus().equals(new BigDecimal(8))) {
				List<ActVirtualCashFlow> list = loanManagerDao.getRepayLoanDetailByLoanid(loanInfo.getLoanAcTLedgerLoan().getId().longValue());
				for (ActVirtualCashFlow virtualCah : list) {
					Long id = virtualCah.getLoanId();
					if (null != virtualCah.getOverDueDays() && virtualCah.getOverDueDays() > 0) {
						RepayLoanDetailLate repayLoan = new RepayLoanDetailLate();
						repayLoan.setLoanId(loanId); // 变更为loadId
						if (virtualCah.getRepayStatus().equals(RepayStatus.未还款)) {
							repayLoan.setRepayLoanDate(virtualCah.getRepayDay().toString());
						} else {
							repayLoan.setRepayLoanDate(virtualCah.getEditDate().toString());
						}
						repayLoan.setOverdueDays(virtualCah.getOverDueDays().intValue());
						repayLoan.setRepayLoanStatus(virtualCah.getRepayStatus().toString());
						// 逾期罚息
						repayLoan.setOverdueInterest(ObjectFormatUtil.formatCurrency(virtualCah.getOverDueInterestAmount()));
						// 逾期违约金
						repayLoan.setOverdueFines(ObjectFormatUtil.formatCurrency(virtualCah.getOverDueFineAmount()));
						// 月还本息
						repayLoan.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(ArithUtil.add(virtualCah.getInterestAmt(), virtualCah.getPrincipalAmt())));
						repayLoanLate.add(repayLoan);
					} else {
						RepayLoanDetailLate repayLoan = new RepayLoanDetailLate();
						repayLoan.setLoanId(loanId);// 变更为loadId
						List<AcTLedgerLoanAdmin> loanLedgerList = loanManagerDao.getLoanInfoByLoanid(new BigDecimal(id));
						if (loanLedgerList.size() > 0) {
							AcTLedgerLoanAdmin loanLedger = loanLedgerList.get(0);
							// 剩余未还期数
							surplusTerm = loanLedger.getTotalNum().intValue() - loanLedger.getCurrNum().intValue() + 1;
							// 计划还款日期
							Date currMaturityDate = virtualCah.getRepayDay();
							// 实际还款日期
							Date nextPaymentday = virtualCah.getEditDate();

							if (virtualCah.getRepayStatus().equals(RepayStatus.未还款) || virtualCah.getRepayStatus().equals(RepayStatus.已垫付)) {
								Date currDate = DateUtil.getCurrentDate();
								overdueDays = FormulaSupportUtil.getOverdueDays(currMaturityDate, currDate);
								// 还款日期
								repayLoan.setRepayLoanDate(virtualCah.getRepayDay().toString());
							} else {
								// 逾期天数
								overdueDays = FormulaSupportUtil.getOverdueDays(currMaturityDate, nextPaymentday);
								// 还款日期
								repayLoan.setRepayLoanDate(nextPaymentday.toString());
							}

							if (overdueDays > 0) {
								repayLoan.setOverdueDays(overdueDays);
								repayLoan.setRepayLoanStatus(virtualCah.getRepayStatus().toString());
								// 逾期罚息
								if (overdueDays <= 30) {
									overdueRate = rate.getOverdueInterest().doubleValue();
								} else {
									overdueRate = rate.getOverdueSeriousInterest().doubleValue();
								}
								currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overdueDays, surplusTerm, ArithUtil.add(virtualCah.getInterestAmt(), virtualCah.getPrincipalAmt()), overdueRate);
								repayLoan.setOverdueInterest(ObjectFormatUtil.formatCurrency(currentOverdueInterest));
								// 逾期违约金
								currentOverdueFines = FormulaSupportUtil.getOverdueFines(overdueDays, surplusTerm, loanInfo.getMonthManageCost(), rate.getOverdueFines().doubleValue());
								repayLoan.setOverdueFines(ObjectFormatUtil.formatCurrency(currentOverdueFines));

								// 月还本息
								repayLoan.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(ArithUtil.add(virtualCah.getInterestAmt(), virtualCah.getPrincipalAmt())));
								repayLoanLate.add(repayLoan);
							}
						}

					}
				}
			}
		}
		List<RepayLoanDetailLate> repayLoanLateP = new ArrayList<RepayLoanDetailLate>();
		int pageNum = pageRequest.getPageNumber() + 1;
		int s = pageNum * pageRequest.getPageSize() - pageRequest.getPageSize();
		int acc = 0;
		for (int m = s; m < repayLoanLate.size(); m++) {
			repayLoanLateP.add(repayLoanLate.get(m));
			acc++;
			if (acc == pageRequest.getPageSize()) {
				break;
			}
		}
		return new PageImpl<RepayLoanDetailLate>(repayLoanLateP, pageRequest, repayLoanLate.size());
	}
	
	

		


	/**
	 * 资金流水导出
	 * 2013-4-11 上午11:05:07 by HuYaHui
	 * @param request
	 * @param response
	 * @param userId
	 * @param type
	 * @param date_start
	 * @param date_end
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> exportExcelFundFlowTbl(HttpServletRequest request,HttpServletResponse response,
			long userId,int type, String date_start, String date_end) throws Exception{	
		//查询
		List<AcTFlowClassifyVO> list=acTFlowClassifyDAO.findTypeAndUserId(date_start,date_end,type,userId,0,0);
				
		Map<String,Object> dataMap=new HashMap<String, Object>();
		if(list==null || list.size()==0){
			return dataMap;
		}
		String fileName = DateUtil.formatDate(new Date(), DateUtil.YYYYMMDDHHMMSS)+"_资金流水数据".concat(".xls");//设置下载时客户端Excel的名称
		List<String[]> fileDataList=new ArrayList<String[]>();
		fileDataList.add(str);
		for(AcTFlowClassifyVO vo:list){
			String[] _valStr=new String[str.length];
			//"时间","类型","存入","支出","结余","支出方昵称","收入方昵称","备注"
			//时间
			_valStr[0]=DateUtil.formatDate(vo.getGreateTime(), DateUtil.YYYYMMDDHH_MM_SS);
			
			//类型
			String tradeType=TradeTypeConstants.getValueByType(vo.getTradeType());
			if(tradeType.trim().equals("偿还本金")){
				tradeType="偿还本金/回收本金";
			}else if(tradeType.trim().equals("偿还利息")){
				tradeType="偿还利息/回收利息";
			}else if(TypeConstants.TZ==vo.getType()){
				tradeType="调账";
			}
			//子类型
			_valStr[1]=tradeType;
			//投标/借款成功
			if(vo.getInUserId()!=null && vo.getInUserId().equals(userId)){
				//如果当前用户是收入用户
				BigDecimal in=BigDecimalUtil.compareTo(vo.getTradeAmount(), new BigDecimal("0"))==0?new BigDecimal("0"):vo.getTradeAmount();
				_valStr[2]=BigDecimalUtil.formatCurrencyIgnoreSymbol(in);				
				//账户余额
				_valStr[4]=BigDecimalUtil.formatCurrencyIgnoreSymbol(vo.getInCashAccount());
			}else if(vo.getOutUserId()!=null && vo.getOutUserId().equals(userId)){
				//如果当前用户是输出用户
				BigDecimal out=BigDecimalUtil.compareTo(vo.getTradeAmount(), new BigDecimal("0"))==0?new BigDecimal("0"):vo.getTradeAmount();
				_valStr[3]=BigDecimalUtil.formatCurrencyIgnoreSymbol(out);
				//账户余额
				_valStr[4]=BigDecimalUtil.formatCurrencyIgnoreSymbol(vo.getOutCashAccount());
			}
			
			if(vo.getOutUserId()!=null){
				_valStr[5]=getUsersVOByUserId(vo.getOutUserId()).getLoginName();
			}
			if(vo.getInUserId()!=null ){
				_valStr[6]=getUsersVOByUserId(vo.getInUserId()).getLoginName();
			}
			
			if(vo.getType()==2){
				_valStr[7]="充值单号："+vo.getTradeCode();
			}else if(vo.getType()==1){
				_valStr[7]="提现单号："+vo.getTradeCode();
			}else if(vo.getType()==3 || vo.getType()==4 || vo.getType()==5){
				_valStr[7]="借款编号："+vo.getTradeCode();
			}
			
			fileDataList.add(_valStr);
		}
		String filePath = FundsMigrate.getFilePath("repayFlowDetail");
		String path=(filePath + fileName);
		dataMap.put("path", path);
		dataMap.put("data", fileDataList);
		return dataMap;	
	}
	
	/**
	 * 资金流水查询 2013-3-4 下午5:01:57 by HuYaHui
	 * 
	 * @param pageRequest
	 * @param userId
	 * @param type
	 * @param date_start
	 * @param date_end
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> findFundFlow(PageRequest pageRequest, final long userId, int type, String date_start, String date_end) {
		FundFlowVO fundFlowVO = new FundFlowVO();
		List<HashMap> voList = new ArrayList<HashMap>();
		// 初始化
		fundFlowVO.setDate_start(date_start);
		fundFlowVO.setDate_end(date_end);
		fundFlowVO.setType_fund(type+"");
		long pageCount=acTFlowClassifyDAO.countTypeAndUserId(date_start,date_end,type,userId);
		logger.info("-->根据条件，查询总记录数："+pageCount);
		if(pageCount==0){
			return new PageImpl<HashMap>(voList, pageRequest, pageCount);
		}
		//分页查询
		List<AcTFlowClassifyVO> rtnList=acTFlowClassifyDAO.findTypeAndUserId(date_start,date_end,type,userId,(pageRequest.getPageNumber()-1)*pageRequest.getPageSize(),pageRequest.getPageSize());
		for(AcTFlowClassifyVO vo:rtnList){
			HashMap map = new HashMap();
			//时间
			map.put("date", DateUtil.formatDate(vo.getGreateTime(), DateUtil.YYYYMMDDHH_MM_SS));
			//类型
			String tradeType=TradeTypeConstants.getValueByType(vo.getTradeType());
			if(tradeType.trim().equals("偿还本金")){
				tradeType="偿还本金/回收本金";
			}else if(tradeType.trim().equals("偿还利息")){
				tradeType="偿还利息/回收利息";
			}else if(TypeConstants.TZ==vo.getType()){
				tradeType="调账";
			}
			//子类型
			map.put("tradeType", tradeType);
			map.put("type", vo.getType());
			map.put("code", vo.getTradeCode());
			//投标/借款成功
			if(vo.getInUserId()!=null && vo.getInUserId().equals(userId)){
				//如果当前用户是收入用户
				BigDecimal in=BigDecimalUtil.compareTo(vo.getTradeAmount(), new BigDecimal("0"))==0?new BigDecimal("0"):vo.getTradeAmount();
				map.put("in", BigDecimalUtil.formatCurrencyIgnoreSymbol(in));
				
				//账户余额
				map.put("amount", BigDecimalUtil.formatCurrencyIgnoreSymbol(vo.getInCashAccount()));
			}else if(vo.getOutUserId()!=null && vo.getOutUserId().equals(userId)){
				//如果当前用户是输出用户
				BigDecimal out=BigDecimalUtil.compareTo(vo.getTradeAmount(), new BigDecimal("0"))==0?new BigDecimal("0"):vo.getTradeAmount();
				map.put("out", BigDecimalUtil.formatCurrencyIgnoreSymbol(out));
				
				//账户余额
				map.put("amount", BigDecimalUtil.formatCurrencyIgnoreSymbol(vo.getOutCashAccount()));
			}
			
			if(vo.getOutUserId()!=null){
				map.put("outUserName",getUsersVOByUserId(vo.getOutUserId()).getLoginName());				
			}
			if(vo.getInUserId()!=null ){
				map.put("inUserName",getUsersVOByUserId(vo.getInUserId()).getLoginName());	
			}
			
			
			voList.add(map);
		}
		return new PageImpl<HashMap>(voList, pageRequest, pageCount);
	}
	Map<Long, UsersVO> userMap = new HashMap<Long, UsersVO>();

	private UsersVO getUsersVOByUserId(long userId) {
		UsersVO vo = userMap.get(userId);
		if (vo == null) {
			vo = userInfoDao.getUsersVOByUserId(userId);
			userMap.put(userId, vo);
			return vo;
		} else {
			return vo;
		}
	}
	
	/**
	 * 旧资金流水导出
	 * @param request
	 * @param response
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> exportExcelFindOldFundFlow(HttpServletRequest request,HttpServletResponse response,
			final BigDecimal userId,String start,String end) throws Exception{	
		// 初始化
		FundFlowVO fundFlowVO = new FundFlowVO();
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		curCal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = curCal.getTime();
		fundFlowVO = oldFlowShowDao.getFlowInfo(userId, "10001", start, end, 0);
		List temp = (List) fundFlowVO.getFlowList();

		
		Map<String,Object> dataMap=new HashMap<String, Object>();
		if(temp==null || temp.size()==0){
			return dataMap;
		}
		String fileName = DateUtil.formatDate(new Date(), DateUtil.YYYYMMDDHHMMSS)+"_旧资金流水数据".concat(".xls");//设置下载时客户端Excel的名称
		List<String[]> fileDataList=new ArrayList<String[]>();
		//"时间","类型","存入","支出","备注"
		fileDataList.add(strOld);
		for (int i = 0;i<temp.size();i++){
			String[] _valStr=new String[str.length];
			Map map = (Map)temp.get(i);
			//时间
			_valStr[0]=map.get("date").toString();
			//类型
			_valStr[1]=map.get("type").toString();
			//存入
			_valStr[2]=map.get("in")==null?"":map.get("in").toString();
			//支出
			_valStr[3]=map.get("out")==null?"":map.get("out").toString();
			//备注
			_valStr[4]=map.get("code").toString();
			fileDataList.add(_valStr);
		}
		String filePath = FundsMigrate.getFilePath("repayFlowDetail");
		String path=(filePath + fileName);
		dataMap.put("path", path);
		dataMap.put("data", fileDataList);
		return dataMap;
	}
	/**
	 * @param pageRequest
	 * @param userId
	 * @return
	 */
	public Page<HashMap> findOldFundFlow(PageRequest pageRequest, final BigDecimal userId,String start,String end) {
		// 初始化
		FundFlowVO fundFlowVO = new FundFlowVO();
		List voList = new ArrayList();
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		;
		curCal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = curCal.getTime();
		String sTime = datef.format(beginTime);
		fundFlowVO = oldFlowShowDao.getFlowInfo(userId, "10001", start, end, 0);
		int pageNum = pageRequest.getPageNumber() + 1;
		int s = pageNum * pageRequest.getPageSize() - pageRequest.getPageSize();
		int acc = 0;
		List temp = (List) fundFlowVO.getFlowList();
		for (int i = s;i<temp.size();i++){
			voList.add(temp.get(i));
			acc++;
			if (acc == pageRequest.getPageSize()) {
				break;
			}
		}
//		for (Iterator iterator = fundFlowVO.getFlowList().iterator(); iterator.hasNext();) {
//			voList.add(iterator.next());
//			acc++;
//			if (acc == pageRequest.getPageSize()) {
//				break;
//			}
//		}
		return new PageImpl<HashMap>(voList, pageRequest, fundFlowVO.getFlowList().size());
	}


}
