/**
 * 
 */
package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.ChannelDAO;
import com.zendaimoney.online.admin.dao.loan.AcTLedgerLoanDao;
import com.zendaimoney.online.admin.dao.loan.AcTVirtualCashFlowDao;
import com.zendaimoney.online.admin.dao.loan.InvestManagerDao;
import com.zendaimoney.online.admin.dao.loan.LoanManagerDao;
import com.zendaimoney.online.admin.dao.loan.LoanNoteMangerDao;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.admin.entity.loan.AcTLedgerLoanAdmin;
import com.zendaimoney.online.admin.entity.loan.AcTVirtualCashFlowAdmin;
import com.zendaimoney.online.admin.entity.loan.InvestInfoAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanNoteAdmin;
import com.zendaimoney.online.admin.util.DateFormatUtils;
import com.zendaimoney.online.admin.vo.LoanInfoListForm;
import com.zendaimoney.online.admin.vo.LoanInfoStatisticsVo;
import com.zendaimoney.online.admin.vo.LoanNoteListForm;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementOverdueClaimsDao;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.entity.financial.FinancialSysMsg;
import com.zendaimoney.online.entity.loanManagement.LoanManagentOverdueClaims;
import com.zendaimoney.online.service.common.RateCommonUtil;

/**
 * @author Administrator 提供借款信息管理的操作
 */

@Service
@Transactional
public class LoanManagerService {
	private static Logger logger = LoggerFactory.getLogger(LoanManagerService.class);
	@Autowired
	private LoanManagerDao loanManagerDao;

	@Autowired
	private InvestManagerDao investManagerDao;

	@Autowired
	private LoanNoteMangerDao loanNoteMangerDao;

	@Autowired
	private StaffService staffService;

	@Autowired
	private AcTVirtualCashFlowDao acTVirtualCashFlowDao;

//	@Autowired
//	private CommonDao commonDao;
	@Autowired
	private RateCommonUtil rateCommonUtil;
	@Autowired
	private AcTLedgerLoanDao acTLedgerLoanDao;

	@Autowired
	FinancialSysMsgDao sysMsgDao;
	@Autowired
	private ChannelDAO channelSelfDAO;

	@Autowired
	private LoanManagementOverdueClaimsDao loanManagementOverdueClaimsDao;

	@Autowired
	private OverdueClaimsService overdueClaimsService;

	public Page<LoanInfoAdmin> findLoadPage(final LoanInfoListForm loanInfoListForm, Pageable pageable) {
		return loanManagerDao.findAll(new Specification<LoanInfoAdmin>() {
			@Override
			public Predicate toPredicate(Root<LoanInfoAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(loanInfoListForm.getLoginName())) {
					predicates.add(cb.like(cb.lower(root.<String> get("accountUsers").<String> get("loginName")), "%" + loanInfoListForm.getLoginName() + "%"));
				}
				if (StringUtils.isNotEmpty(loanInfoListForm.getRealName())) {
					predicates.add(cb.like(cb.lower(root.<String> get("accountUsers").<String> get("userInfoPerson").<String> get("realName")), "%" + loanInfoListForm.getRealName() + "%"));
				}
				if (StringUtils.isNotEmpty(loanInfoListForm.getPhoneNo())) {
					predicates.add(cb.equal(root.get("accountUsers").get("userInfoPerson").get("phoneNo"), loanInfoListForm.getPhoneNo()));
				}
				// 一级渠道名称不为空
				if (!"".equals(loanInfoListForm.getChannelFId()) && loanInfoListForm.getChannelFId() != null) {
					// 二级渠道不为空，根据二级渠道查询
					if (!"".equals(loanInfoListForm.getChannelCId()) && loanInfoListForm.getChannelCId() != null) {
						predicates.add(cb.equal(root.get("accountUsers").get("channelInfo").get("id"), loanInfoListForm.getChannelCId()));
					} else {
						// 二级渠道为空，则查询一级渠道下所有二级渠道
						List<ChannelInfoVO> channelInfoList = channelSelfDAO.findChildListByParentId(Long.valueOf(loanInfoListForm.getChannelFId()));
						List<Long> idList = new ArrayList<Long>();
						for (ChannelInfoVO vo : channelInfoList) {
							idList.add(vo.getId());
						}
						predicates.add(root.get("accountUsers").get("channelInfo").get("id").in(idList));
					}
				}
				if (StringUtils.isNotEmpty(loanInfoListForm.getStatus().toString())) {
					predicates.add(cb.equal(root.get("status"), loanInfoListForm.getStatus()));
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);

	}

	/**
	 * 根据借款Id查询借款信息
	 * 
	 * @param loanId
	 *            借款信息Id
	 * @return 返回一条借款信息或者空对象
	 */
	public LoanInfoAdmin findByLoanId(BigDecimal loanId) {
		return loanManagerDao.findByLoanId(loanId);
	}

	/**
	 * 更改借款信息的状态
	 * 
	 * @param loanId
	 *            借款信息Id
	 * @param status
	 *            借款信息要更改到的状态代码 2012-12-11 Ray修改 新增开始筹标时间 如果状态要变为1，则需要写入筹标时间
	 * @return
	 */
	@LogInfo(operateKind = OperateKind.修改, operateContent = "借款管理中借款信息状态更改成功")
	public boolean alertLoanStatus(BigDecimal loanId, BigDecimal status) {
		LoanInfoAdmin loanInfo = loanManagerDao.findByLoanId(loanId);
		loanInfo.setStatus(status);
		/* 新增加入筹标时间 start------------- */
		if (BigDecimal.valueOf(1).equals(status)) {
			loanInfo.setStartInvestTime(new Date());
		}
		/*---------------end-----------*/
		loanManagerDao.save(loanInfo);
		logger.info("SAVE: loan_info  修改状态添加筹款时间：loanId=" + loanId + "  status=" + status);
		if (loanInfo.getStatus() == status) {
			return true;
		}
		return false;
	}

	/**
	 * 查询借款信息投资列表
	 * 
	 * @param loanId
	 * @return
	 */
	public List<InvestInfoAdmin> seachInverstInfo(BigDecimal loanId) {
		return investManagerDao.findByLoanInfo_LoanId(loanId);
	}

	/**
	 * 根据还款期数查询借款信息投资列表
	 * 
	 * @param loanId
	 * @return
	 */
	public List<InvestInfoAdmin> seachInverstInfo(BigDecimal loanId, Long num) {
		List<InvestInfoAdmin> investInfos = investManagerDao.findByLoanInfo_LoanId(loanId);
		LoanManagentOverdueClaims overdueClaim = null;
		for (InvestInfoAdmin investInfo : investInfos) {
			overdueClaim = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), num);
			investInfo.setAdvanceStatus(overdueClaim == null ? BigDecimal.ZERO : overdueClaim.getStatus());
			investInfo.setAdvancedAmount(overdueClaim == null ? "￥0.00" : ObjectFormatUtil.formatCurrency(overdueClaim.getPayAmount()));
		}
		return investInfos;
	}

	/**
	 * 查询借款备注列表
	 * 
	 * @param loanId
	 * @return
	 */
	public List<LoanNoteAdmin> seachLoanNote(BigDecimal loanId) {
		return loanNoteMangerDao.findByLoanInfo_LoanId(loanId);

	}

	/**
	 * 保存借款备注信息
	 * 
	 * @param loanNoteListForm
	 */
	@LogInfo(operateKind = OperateKind.新增, operateContent = "借款管理中成功添加借款信息备注")
	public void saveLoanNote(LoanNoteListForm loanNoteListForm) {
		LoanNoteAdmin loanNote = new LoanNoteAdmin();
		LoanInfoAdmin loanInfo = new LoanInfoAdmin();
		loanInfo.setLoanId(loanNoteListForm.getLoanId());
		Staff staff = new Staff();
		staff.setId(staffService.getCurrentStaff().getId());
		loanNote.setMemoText(loanNoteListForm.getMemoText());
		loanNote.setStaff(staff);
		loanNote.setLoanInfo(loanInfo);
		loanNote.setOperateTime(new Date());
		logger.info("SAVE: LOAN_NOTE ||  staff=" + staff.getId() + " LoanInfo=" + loanInfo);
		loanNoteMangerDao.save(loanNote);

	}

	/**
	 * 查询还款信息
	 * 
	 * @param loanId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<AcTVirtualCashFlowAdmin> SearchLoanRepayList(BigDecimal loanId) {
		AcTLedgerLoanAdmin actledgerLoan = acTLedgerLoanDao.findOne(loanId);
		LoanRateVO rate = rateCommonUtil.getLoanCoRate(actledgerLoan.getLoanInfo().getLoanId().longValue());
		List<AcTVirtualCashFlowAdmin> acTVirtualCashFlows = acTVirtualCashFlowDao.findByAcTLedgerLoanAdmin_id(loanId);
		for (AcTVirtualCashFlowAdmin acTVirtualCashFlow : acTVirtualCashFlows) {
			double advancedAmount = overdueClaimsService.getAllAdvancedAmount(acTVirtualCashFlow.getAcTLedgerLoanAdmin().getLoanInfo().getLoanId(), acTVirtualCashFlow.getCurrNum().longValue());
			int advancedSize = loanManagementOverdueClaimsDao.findByLoanIdAndNumAndStatus(acTVirtualCashFlow.getAcTLedgerLoanAdmin().getLoanInfo().getLoanId(), acTVirtualCashFlow.getCurrNum().longValue(), new BigDecimal(2)).size();
			if (acTVirtualCashFlow.getRepayStatus().equals(RepayStatus.未还款)) {
				if (acTVirtualCashFlow.getAcTLedgerLoanAdmin().getLoanInfo().getInvestInfos().size() == advancedSize) {
					acTVirtualCashFlow.setRepayStatus(RepayStatus.已垫付);
				}
			}
			// 月还本息
			double interstAmtAndPrincipalAmt = ArithUtil.add(acTVirtualCashFlow.getInterestAmt(), acTVirtualCashFlow.getPrincipalAmt());
			acTVirtualCashFlow.setAdvancedAmount(ObjectFormatUtil.formatCurrency(advancedAmount));
			acTVirtualCashFlow.setNotAdvancedAmount(ObjectFormatUtil.formatCurrency(ArithUtil.sub(interstAmtAndPrincipalAmt, advancedAmount)));
			acTVirtualCashFlow.setRate(rate);
		}

		/** 排序类 */
		Comparator<AcTVirtualCashFlowAdmin> comp = new Comparator<AcTVirtualCashFlowAdmin>() {
			@Override
			public int compare(AcTVirtualCashFlowAdmin o1, AcTVirtualCashFlowAdmin o2) {
				AcTVirtualCashFlowAdmin ac1 = (AcTVirtualCashFlowAdmin) o1;
				AcTVirtualCashFlowAdmin ac2 = (AcTVirtualCashFlowAdmin) o2;
				if (ac1.getCurrNum() > ac2.getCurrNum())
					return 1;
				else
					return 0;
			}
		};
		Collections.sort(acTVirtualCashFlows, comp);
		return acTVirtualCashFlows;

	}

	/**
	 * 审核通过维护 创建贷款分户
	 */
	@LogInfo(operateKind = OperateKind.修改, operateContent = "借款管理中借款信息审核通过，成功创建借款分户信息")
	public LoanInfoAdmin aduitPassLoan(BigDecimal loanId) {
		LoanInfoAdmin loanInfo = loanManagerDao.findOne(loanId);
		if (!loanInfo.getStatus().equals(new BigDecimal(8))) {
			return null;
		}
		if (!BigDecimal.valueOf(5).equals(loanInfo.getAccountUsers().getUserStatus())) {
			throw new BusinessException("当前用户处于认证资料审核中！");
		}
		AcTLedgerLoanAdmin actLedgerLoan = new AcTLedgerLoanAdmin();
		actLedgerLoan.setAcctStatus("1");
		actLedgerLoan.setLedgerId(1L);
		acTLedgerLoanDao.save(actLedgerLoan);
		loanInfo.setLoanAcTLedgerLoan(actLedgerLoan);
		logger.info("SAVE: AC_T_LEDGER_LOAN || actLedgerLoan=" + actLedgerLoan.getLedgerId());
		loanInfo = loanManagerDao.save(loanInfo);

		// 发送系统消息
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		sysMsg.setUserId(loanInfo.getAccountUsers().getUserId());
		sysMsg.setWordId(BigDecimal.valueOf(15));
		sysMsg.setParameter1(loanInfo.getAccountUsers().getLoginName());
		sysMsg.setParameter2(loanInfo.getLoanTitle());
		sysMsg.setParameter3(loanInfo.getLoanAmount().toString());
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		logger.info("SAVE: SYS_MSG ||  WordId=15 Parameter1=" + loanInfo.getAccountUsers().getLoginName() + " Parameter2=" + loanInfo.getLoanTitle() + " Parameter3=" + loanInfo.getLoanAmount() + " IsDel=0");
		sysMsgDao.save(sysMsg);
		return loanInfo;
	}

	/**
	 * 检查还款日期 逾期1-30天 更改借款信息状态为6：初级逾期 逾期大于30天借款信息状态更改为7：高级逾期
	 */
	public void checkPaybackTimeIsOverTime() {
		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		List<LoanInfoAdmin> loanInfoList = loanManagerDao.findByStatusIn(new BigDecimal[] { BigDecimal.valueOf(4), BigDecimal.valueOf(6) });
		for (LoanInfoAdmin loanInfo : loanInfoList) {
			if ("2".equals(loanInfo.getLoanAcTLedgerLoan().getAcctStatus())) {
				int overDays = DateFormatUtils.countDays(loanInfo.getLoanAcTLedgerLoan().getNextExpiry().toString(), dateFormate.format(new Date()));
				if (overDays >= 1 && overDays <= 30) {
					loanInfo.setStatus(new BigDecimal(6));
				}
				if (overDays > 30) {
					loanInfo.setStatus(new BigDecimal(7));
				}
			}
		}
	}

	/**
	 * 更新和保存逾期分债权信信息
	 */
	public void checkOverDueInVirtualCashFlow() {
		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		List<LoanInfoAdmin> loanInfos = loanManagerDao.findByStatusIn(new BigDecimal[] { BigDecimal.valueOf(4), BigDecimal.valueOf(6), BigDecimal.valueOf(7) });
		for (LoanInfoAdmin loanInfo : loanInfos) {
			Set<AcTVirtualCashFlowAdmin> acTVirtualCashFlows = loanInfo.getLoanAcTLedgerLoan().getAcTVirtualCashFlows();
			for (AcTVirtualCashFlowAdmin acTVirtualCashFlow : acTVirtualCashFlows) {
				if (acTVirtualCashFlow.getCurrNum() >= loanInfo.getLoanAcTLedgerLoan().getCurrNum()) {
					int overDays = DateFormatUtils.countDays(acTVirtualCashFlow.getRepayDay().toString(), dateFormate.format(new Date()));
					if (overDays >= 1) {
						saveOverDueInverstInfo(loanInfo, overDays, acTVirtualCashFlow.getCurrNum().longValue());
					}
				}
			}
		}
	}

	// 保存逾期的分债权信息
	public void saveOverDueInverstInfo(LoanInfoAdmin loanInfo, int overDueDays, Long num) {
		Set<InvestInfoAdmin> investInfos = loanInfo.getInvestInfos();
		// 各种费率信息,取自费率表
		LoanRateVO rate = rateCommonUtil.getLoanCoRate(loanInfo.getLoanId().longValue());
		// 罚息比例
		double overdueRate = rate.getOverdueInterest().doubleValue();
		// 逾期违约金比例
		double overdueFinesRate = rate.getOverdueFines().doubleValue();
		// 剩余未还期数
		int SurplusTerm = 0;
		// 逾期罚息
		double currentOverdueInterest = 0;
		// 逾期违约金
		double currentOverdueFines = 0;
		// 剩余未还期数
		SurplusTerm = loanInfo.getLoanAcTLedgerLoan().getTotalNum().intValue() - loanInfo.getLoanAcTLedgerLoan().getCurrNum().intValue() + 1;
		// 逾期罚息
		if (overDueDays <= 30)
			currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overDueDays, SurplusTerm, loanInfo.getMonthReturnPrincipalandinter(), overdueRate);
		if (overDueDays > 30)
			currentOverdueInterest = FormulaSupportUtil.calOverdueInterest(overDueDays, SurplusTerm, loanInfo.getMonthReturnPrincipalandinter(), rate.getOverdueSeriousInterest().doubleValue());
		// 逾期违约金
		currentOverdueFines = FormulaSupportUtil.calOverdueInterest(overDueDays, SurplusTerm, loanInfo.getMonthManageCost(), overdueFinesRate);
		for (InvestInfoAdmin investInfo : investInfos) {
			LoanManagentOverdueClaims overdueClaims = loanManagementOverdueClaimsDao.findByInvestIdAndNum(investInfo.getInvestId(), num);
			if (overdueClaims == null) {
				overdueClaims = new LoanManagentOverdueClaims();
				// 理财信息ID
				overdueClaims.setInvestId(investInfo.getInvestId());
				// 借款信息ID
				overdueClaims.setLoanId(loanInfo.getLoanId());
				// 当前期数
				overdueClaims.setNum(num);
				// 月缴管理费
				overdueClaims.setManagerFree(loanInfo.getMonthManageCost());
				// 设置当前状态为“未还款”
				overdueClaims.setStatus(new BigDecimal(1));
				// 设置逾期天数
				overdueClaims.setOverDueDays(overDueDays);
				// 设置创建时间
				overdueClaims.setCreateTime(new Date());
				// 设置代偿状态为 未代偿
				overdueClaims.setIsAdvanced(BigDecimal.ZERO);
			} else {
				if (overdueClaims.getStatus().equals(new BigDecimal(3))) {
					continue;
				}
				overdueClaims.setEditTime(new Date());
			}
			overdueClaims.setOverDueDays(overDueDays);
			// 逾期罚息
			overdueClaims.setOverDueInterstAmount(ArithUtil.mul(currentOverdueInterest, investInfo.getHavaScale()));
			// 逾期违约金
			overdueClaims.setOverDueFineAmount(currentOverdueFines);
			loanManagementOverdueClaimsDao.save(overdueClaims);
		}
	}

	// 统计借款信息
	public LoanInfoStatisticsVo loanInfoStatistics(BigDecimal loanId) {
		// 月还本息统计
		double principalAndinterest = 0;
		// 逾期罚息统计
		double overDueInterestAmount = 0;
		// 逾期违约金统计
		double overDueFineAmount = 0;
		// 月缴管理费
		double monthManageCost = 0;
		// 逾期应还总额
		double totalPayBack = 0;
		// 垫付金额
		double advancedAmt = 0;
		// 未垫付金额
		double notAdvancedAmt = 0;
		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		LoanRateVO rate = rateCommonUtil.getLoanCoRate(loanId.longValue());
		LoanInfoStatisticsVo loanInfoStatisticsVo = new LoanInfoStatisticsVo();
		LoanInfoAdmin loanInfo = loanManagerDao.findOne(loanId);
		for (AcTVirtualCashFlowAdmin acTVirtualCashFlow : loanInfo.getLoanAcTLedgerLoan().getAcTVirtualCashFlows()) {
			if (acTVirtualCashFlow.getCurrNum() >= loanInfo.getLoanAcTLedgerLoan().getCurrNum()) {
				int overDays = DateFormatUtils.countDays(acTVirtualCashFlow.getRepayDay().toString(), dateFormate.format(new Date()));
				if (overDays >= 1) {
					// 月还本息统计
					principalAndinterest = ArithUtil.add(ArithUtil.add(principalAndinterest, acTVirtualCashFlow.getPrincipalAmt()), acTVirtualCashFlow.getInterestAmt());
					// 逾期罚息统计
					overDueInterestAmount = ArithUtil.add(overDueInterestAmount, getInterestOverduePenalty(acTVirtualCashFlow, overDays, rate));
					// 逾期违约金统计
					overDueFineAmount = ArithUtil.add(overDueFineAmount, getOverdueBreachPenalty(acTVirtualCashFlow, overDays, rate));
					// 月缴管理费
					monthManageCost = ArithUtil.add(monthManageCost, loanInfo.getMonthManageCost());
					// 垫付金额
					advancedAmt = ArithUtil.add(advancedAmt, overdueClaimsService.getAdvancedAmount(loanId, acTVirtualCashFlow.getCurrNum().longValue()));
				}
			}
		}
		// 未垫付金额
		notAdvancedAmt = ArithUtil.sub(principalAndinterest, advancedAmt);
		// 逾期应还总额
		totalPayBack = FormulaSupportUtil.currentTermShouldPayAmount(principalAndinterest, overDueInterestAmount, overDueFineAmount, monthManageCost);
		loanInfoStatisticsVo.setAdvancedAmt(ObjectFormatUtil.formatCurrency(advancedAmt));
		loanInfoStatisticsVo.setMonthManageCost(ObjectFormatUtil.formatCurrency(monthManageCost));
		loanInfoStatisticsVo.setNotAdvancedAmt(ObjectFormatUtil.formatCurrency(notAdvancedAmt));
		loanInfoStatisticsVo.setOverDueFineAmount(ObjectFormatUtil.formatCurrency(overDueFineAmount));
		loanInfoStatisticsVo.setOverDueInterestAmount(ObjectFormatUtil.formatCurrency(overDueInterestAmount));
		loanInfoStatisticsVo.setPrincipalAndinterest(ObjectFormatUtil.formatCurrency(principalAndinterest));
		loanInfoStatisticsVo.setTotalPayBack(ObjectFormatUtil.formatCurrency(totalPayBack));
		return loanInfoStatisticsVo;

	}

	// 计算逾期违约金
	public double getOverdueBreachPenalty(AcTVirtualCashFlowAdmin acTVirtualCashFlow, int overDays, LoanRateVO rate) {
		// 逾期违约金费率
		double rateOverduePenaltyInterest = rate.getOverdueFines().doubleValue();
		// 剩余还款期数
		int leftRepayDuration = acTVirtualCashFlow.getAcTLedgerLoanAdmin().getTotalNum().intValue() - acTVirtualCashFlow.getAcTLedgerLoanAdmin().getCurrNum().intValue() + 1;
		// 月缴管理费
		double monthManageCost = acTVirtualCashFlow.getAcTLedgerLoanAdmin().getLoanInfo().getMonthManageCost();
		// 逾期违约金：=剩余未还期数*每期应缴月缴管理费*逾期天数*逾期罚息费率
		double overdueBreachPenalty = FormulaSupportUtil.getOverdueFines(overDays, leftRepayDuration, monthManageCost, rateOverduePenaltyInterest);
		return overdueBreachPenalty;
	}

	// 计算逾期罚息
	public double getInterestOverduePenalty(AcTVirtualCashFlowAdmin acTVirtualCashFlow, int overDays, LoanRateVO rate) {

		// 剩余还款期数
		int leftRepayDuration = acTVirtualCashFlow.getAcTLedgerLoanAdmin().getTotalNum().intValue() - acTVirtualCashFlow.getAcTLedgerLoanAdmin().getCurrNum().intValue() + 1;
		// 逾期罚息费率
		double rateOverduePenaltyInterest = 0;
		if (overDays <= 30) {
			rateOverduePenaltyInterest = rate.getOverdueInterest().doubleValue();
		} else {
			rateOverduePenaltyInterest = rate.getOverdueSeriousInterest().doubleValue();
		}
		// 逾期罚息：=剩余未还期数*每期应还本息*逾期天数*逾期罚息费率
		double overduePenaltyInterest = FormulaSupportUtil.calOverdueInterest(overDays, leftRepayDuration, acTVirtualCashFlow.getAcTLedgerLoanAdmin().getLoanInfo().getMonthReturnPrincipalandinter(), rateOverduePenaltyInterest);
		return overduePenaltyInterest;
	}

	/**
	 * @author Ray
	 * @date 2012-12-6 上午10:05:10
	 * @param loanInfoListForm
	 * @param pageable
	 * @return description: 分页查询还款中列表
	 */
	public Page<LoanInfoAdmin> findLoadRepayingPage(final LoanInfoListForm loanInfoListForm, Pageable pageable) {
		return loanManagerDao.findAll(new Specification<LoanInfoAdmin>() {
			@Override
			public Predicate toPredicate(Root<LoanInfoAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(loanInfoListForm.getLoginName())) {
					predicates.add(cb.like(cb.lower(root.<String> get("accountUsers").<String> get("loginName")), "%" + loanInfoListForm.getLoginName() + "%"));
				}
				if (StringUtils.isNotEmpty(loanInfoListForm.getRealName())) {
					predicates.add(cb.like(cb.lower(root.<String> get("accountUsers").<String> get("userInfoPerson").<String> get("realName")), "%" + loanInfoListForm.getRealName() + "%"));
				}
				if (StringUtils.isNotEmpty(loanInfoListForm.getPhoneNo())) {
					predicates.add(cb.equal(root.get("accountUsers").get("userInfoPerson").get("phoneNo"), loanInfoListForm.getPhoneNo()));
				}
				
				// 一级渠道名称不为空
				if (!"".equals(loanInfoListForm.getChannelFId()) && loanInfoListForm.getChannelFId() != null) {
					// 二级渠道不为空，根据二级渠道查询
					if (!"".equals(loanInfoListForm.getChannelCId()) && loanInfoListForm.getChannelCId() != null) {
						predicates.add(cb.equal(root.get("accountUsers").get("channelInfo").get("id"), loanInfoListForm.getChannelCId()));
					} else {
						// 二级渠道为空，则查询一级渠道下所有二级渠道
						List<ChannelInfoVO> channelInfoList = channelSelfDAO.findChildListByParentId(Long.valueOf(loanInfoListForm.getChannelFId()));
						List<Long> idList = new ArrayList<Long>();
						for (ChannelInfoVO vo : channelInfoList) {
							idList.add(vo.getId());
						}
						predicates.add(root.get("accountUsers").get("channelInfo").get("id").in(idList));
					}
				}
				/*
				 * 2012-11-30 添加更加时间段查询
				 */

				Path<Date> interestStart = root.get("loanAcTLedgerLoan").get("interestStart");
				// 2个都不为空
				if (loanInfoListForm.getInterestStartMin() != null && loanInfoListForm.getInterestStartMax() != null) {
					predicates.add(cb.greaterThanOrEqualTo(interestStart, loanInfoListForm.getInterestStartMin()));
					loanInfoListForm.getInterestStartMax().setHours(23);
					loanInfoListForm.getInterestStartMax().setMinutes(59);
					loanInfoListForm.getInterestStartMax().setSeconds(59);
					predicates.add(cb.lessThanOrEqualTo(interestStart, loanInfoListForm.getInterestStartMax()));
					// start不为空，end为空
				} else if (loanInfoListForm.getInterestStartMin() != null && loanInfoListForm.getInterestStartMax() == null) {
					predicates.add(cb.greaterThanOrEqualTo(interestStart, loanInfoListForm.getInterestStartMin()));
					Calendar cal = Calendar.getInstance();
					cal.setTime(loanInfoListForm.getInterestStartMin());
					cal.add(Calendar.MONTH, 1);
					cal.add(Calendar.DATE, -1);
					Date interestStartDate = cal.getTime();
					interestStartDate.setHours(23);
					interestStartDate.setMinutes(59);
					interestStartDate.setSeconds(59);
					predicates.add(cb.lessThanOrEqualTo(interestStart, interestStartDate));
					// start为空，end不为空
				} else if (loanInfoListForm.getInterestStartMin() == null && loanInfoListForm.getInterestStartMax() != null) {
					predicates.add(cb.lessThanOrEqualTo(interestStart, loanInfoListForm.getInterestStartMax()));
					Calendar cal = Calendar.getInstance();
					cal.setTime(loanInfoListForm.getInterestStartMax());
					cal.add(Calendar.MONTH, -1);
					cal.add(Calendar.DATE, 1);
					predicates.add(cb.greaterThanOrEqualTo(interestStart, cal.getTime()));
					// 2个都为空
				} else {
					// 获取当前时间，将当前时间作为最大时间
					Calendar cal = new GregorianCalendar();
					Date interestStartDateMax = cal.getTime();
					loanInfoListForm.setInterestStartMax(interestStartDateMax);
					logger.info("max: " + loanInfoListForm.getInterestStartMax());
					predicates.add(cb.lessThanOrEqualTo(interestStart, loanInfoListForm.getInterestStartMax()));
					// 将当前时间减一个月，并且日期加上一天
					cal.add(Calendar.MONTH, -1);
					cal.add(Calendar.DATE, 1);
					Date interestStartDateMin = cal.getTime();
					interestStartDateMin.setHours(00);
					interestStartDateMin.setMinutes(00);
					interestStartDateMin.setSeconds(00);
					loanInfoListForm.setInterestStartMin(interestStartDateMin);
					logger.info("min: " + loanInfoListForm.getInterestStartMin());
					predicates.add(cb.greaterThanOrEqualTo(interestStart, loanInfoListForm.getInterestStartMin()));
				}
				if (StringUtils.isNotEmpty(loanInfoListForm.getStatus().toString())) {
					predicates.add(cb.equal(root.get("status"), loanInfoListForm.getStatus()));
				}

				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);

	}

	public Page<LoanInfoAdmin> findLoanRepay(PageRequest pageRequest, final LoanInfoListForm loanInfoListForm) {
		int pageNum = pageRequest.getPageNumber() + 1;
		int s = pageNum * pageRequest.getPageSize() - pageRequest.getPageSize();
		int acc = 0;
		List<LoanInfoAdmin> voList = new ArrayList<LoanInfoAdmin>();
		List<LoanInfoAdmin> loanList = loanManagerDao.findLoanRepay(loanInfoListForm);
		for (int m = s; m < loanList.size(); m++) {
			voList.add(loanList.get(m));
			acc++;
			if (acc == pageRequest.getPageSize()) {
				break;
			}
		}

		return new PageImpl<LoanInfoAdmin>(voList, pageRequest, loanList.size());
		
	}

	/**
	 * @author Ray
	 * @date 2012-12-3 上午10:21:04
	 * @param loanInfoListForm
	 *            description:
	 */
	public String totalAmount(String loginName, String realName, String phoneNo, String interestStartMin, String interestStartMax) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 2个都为空的情况
		if (("".equals(interestStartMin) || null == interestStartMin) && ("".equals(interestStartMax) || null == interestStartMax)) {
			Calendar cal = new GregorianCalendar();
			interestStartMax = df.format(cal.getTime());
			cal.add(Calendar.MONTH, -1);
			cal.add(Calendar.DATE, 1);
			interestStartMin = df.format(cal.getTime());
			// System.out.println("！！！interestStartMin="+interestStartMin+" interestStartMax="+interestStartMax);
		} else if (("".equals(interestStartMin) || null == interestStartMin) && (!"".equals(interestStartMax) && null != interestStartMax)) { // start为空，end不为空
			// 将interestStartMax转成date
			Date date = null;
			try {
				date = df.parse(interestStartMax);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, -1);
			cal.add(Calendar.DATE, 1);
			interestStartMin = df.format(cal.getTime());
			// System.out.println("！！！！interestStartMin="+interestStartMin+" interestStartMax="+interestStartMax);
		} else if ((!"".equals(interestStartMin) && null != interestStartMin) && ("".equals(interestStartMax) || null == interestStartMax)) {// start不为空，end为空
			Date date = null;
			try {
				date = df.parse(interestStartMin);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			interestStartMax = df.format(cal.getTime());
			// System.out.println("！！！interestStartMin="+interestStartMin+" interestStartMax="+interestStartMax);
		}
		return loanManagerDao.totalAmount(interestStartMin, interestStartMax, loginName, realName, phoneNo);
	}
}
