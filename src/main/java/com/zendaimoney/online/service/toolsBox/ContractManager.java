package com.zendaimoney.online.service.toolsBox;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementUserInfoPersonDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementLoanInfoDao;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTVirtualCashFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementInvestInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagementLoanInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagementUserInfoPerson;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.vo.toolsBox.BorrowContractVO;
import com.zendaimoney.online.vo.toolsBox.InverstVO;

/**
 * 借款协议范本展示
 * 
 * @author wangtf
 */
@Component
public class ContractManager {
	@Autowired
	private LoanmanagementLoanInfoDao loanmanagementLoanInfoDao;
	@Autowired
	private LoanManagementUserInfoPersonDao loanManagementUserInfoPersonDao;
	@Autowired
	private RateCommonUtil rateCommonUtil;

	public BorrowContractVO showBorrowContract(BigDecimal loanId) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		BorrowContractVO contractVO = new BorrowContractVO();
		LoanManagementLoanInfo loanInfo = loanmanagementLoanInfoDao.findOne(loanId);
		LoanManagementUserInfoPerson loanUserInfo = loanManagementUserInfoPersonDao.findByUserId(loanInfo.getUser().getUserId());
		LoanRateVO loanRateInfo = rateCommonUtil.getLoanRateByRateId(loanInfo.getLoanRateId());
		contractVO.setLoanId(loanId.toString());
		contractVO.setInvestList(initInvestVO(loanInfo));
		contractVO.setLaonUse(initLoanUse(loanInfo.getLoanUse()));
		contractVO.setLoanAmount(currencyFormat.format(loanInfo.getLoanAmount()));
		contractVO.setLoanDuration(loanInfo.getLoanDuration() + "个月");
		contractVO.setDuration(loanInfo.getLoanDuration() + "");
		contractVO.setLoanUserRealName(loanUserInfo.getRealName());
		contractVO.setMonthRepayAmount(currencyFormat.format(loanInfo.getMonthReturnPrincipalandinter()));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy年MM月dd日");
		contractVO.setMonthRepayTime(formatter.format(loanInfo.getActLedgerLoan().getAcTVirtualCashFlows().get(0).getRepayDay()).substring(8, 10) + "号");
		contractVO.setLoanPross(dataFormat.format(loanInfo.getActLedgerLoan().getInterestStart()));
		contractVO.setRepayTimeStartAndEnd(getRepayTimeStartAndEnd(loanInfo));
		contractVO.setLoanUserLoginName(loanInfo.getUser().getLoginName());
		contractVO.setIdentifyNo(loanUserInfo.getIdentityNo());
		contractVO.setAddress(loanUserInfo.getLiveAddress());
		contractVO.setEmail(loanInfo.getUser().getEmail());
		DecimalFormat df = new DecimalFormat("##0.00");
		contractVO.setFundMoney(df.format(Calculator.mul(loanInfo.getLoanAmount(), loanRateInfo.getReserveFoud().doubleValue())));
		contractVO.setLoan(BigDecimalUtil.formatPercent(loanRateInfo.getLoan(), "0.##%"));
		contractVO.setMgmtFee(BigDecimalUtil.formatPercent(loanRateInfo.getMgmtFee(), "0.##%"));
		contractVO.setOverdueInterest(BigDecimalUtil.formatPercent(loanRateInfo.getOverdueInterest(), "0.##%"));
		contractVO.setOverdueSeriouInterest(BigDecimalUtil.formatPercent(loanRateInfo.getOverdueSeriousInterest(), "0.##%"));
		contractVO.setOverdueFines(BigDecimalUtil.formatPercent(loanRateInfo.getOverdueFines(), "0.##%"));
		contractVO.setEarlyFines(BigDecimalUtil.formatPercent(loanRateInfo.getEarlyFines(), "0.##%"));
		contractVO.setReserveFund(BigDecimalUtil.formatPercent(loanRateInfo.getReserveFoud(), "0.##%"));
		return contractVO;
	}

	private String initLoanUse(BigDecimal loanUse) {
		if (loanUse == null) {
			return "";
		}

		switch (Integer.parseInt(loanUse.toString())) {
		case 1:
			return "短期周转";
		case 2:
			return "教育培训";
		case 3:
			return "购房借款";
		case 4:
			return "购车借款";
		case 5:
			return "装修基金";
		case 6:
			return "婚礼筹备";
		case 7:
			return "投资创业";
		case 8:
			return "医疗支出";
		case 9:
			return "个人消费";

		case 10:
			return "其他";

		default:
			return "";
		}

	}

	/**
	 * @author Ray
	 * @date 2012-12-6 下午4:06:02
	 * @param loanInfo
	 * @return description:修改，进行list排序，从而获取准确的起止时间
	 */
	private String getRepayTimeStartAndEnd(LoanManagementLoanInfo loanInfo) {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy年MM月dd日");
		List<LoanManagementAcTVirtualCashFlow> acTVirtualCashFlowList = loanInfo.getActLedgerLoan().getAcTVirtualCashFlows();

		/* 进行期数排序 */
		Comparator<LoanManagementAcTVirtualCashFlow> comp = new Comparator<LoanManagementAcTVirtualCashFlow>() {
			@Override
			public int compare(LoanManagementAcTVirtualCashFlow o1, LoanManagementAcTVirtualCashFlow o2) {
				LoanManagementAcTVirtualCashFlow ac1 = (LoanManagementAcTVirtualCashFlow) o1;
				LoanManagementAcTVirtualCashFlow ac2 = (LoanManagementAcTVirtualCashFlow) o2;
				if (ac1.getCurrNum() > ac2.getCurrNum())
					return 1;
				else
					return 0;
			}
		};
		Collections.sort(acTVirtualCashFlowList, comp);

		return dataFormat.format(acTVirtualCashFlowList.get(0).getRepayDay()) + " - " + dataFormat.format(acTVirtualCashFlowList.get(acTVirtualCashFlowList.size() - 1).getRepayDay());

	}

	private List<InverstVO> initInvestVO(LoanManagementLoanInfo loanInfo) {
		LoanManagementAcTVirtualCashFlow acTVirtualCashFlow = loanInfo.getActLedgerLoan().getAcTVirtualCashFlows().get(0);
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		List<InverstVO> investVoList = new ArrayList<InverstVO>();
		List<LoanManagementInvestInfo> investInfoList = loanInfo.getInvestInfoList();
		for (LoanManagementInvestInfo investInfo : investInfoList) {
			InverstVO investVO = new InverstVO();
			investVO.setInvestUserLoginName(investInfo.getUser().getLoginName());
			investVO.setIvestAmount(currencyFormat.format(investInfo.getInvestAmount()));
			double monthPrincipalInterest = Calculator.mul(Calculator.add(acTVirtualCashFlow.getPrincipalAmt(), acTVirtualCashFlow.getInterestAmt()), investInfo.getHavaScale());
			investVO.setMonthPrincipalInterest(currencyFormat.format(monthPrincipalInterest));
			investVoList.add(investVO);
		}
		return investVoList;
	}
}
