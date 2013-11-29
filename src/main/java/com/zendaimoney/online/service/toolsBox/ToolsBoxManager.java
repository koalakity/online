package com.zendaimoney.online.service.toolsBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.Formula;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.dao.rate.RateCommonDao;
import com.zendaimoney.online.entity.common.Rate;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.vo.toolsBox.CalculatorVO;
import com.zendaimoney.online.vo.toolsBox.RepaymentPlanVO;

/**
 * 理财工具箱管理类
 * 
 * @author yjch
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class ToolsBoxManager {
	@Autowired
	public RateCommonUtil rateCommon;
	@Autowired
	public RateCommonDao rateCommonDao;

	public CalculatorVO getRepaymentPlanList(double amount_loan, int term_loan, double rate_year, HttpServletRequest req) {
		CalculatorVO calculatorVO = new CalculatorVO();
		HttpSession session = req.getSession();
		Rate rate = new Rate();
		if (session == null) {
			rate = rateCommonDao.findById(1L);
		} else {
			BigDecimal userId = (BigDecimal) session.getAttribute("curr_login_user_id");
			if (userId == null) {
				rate = rateCommonDao.findById(1L);
			} else {
				rate = rateCommon.getRateByUser(userId.longValue());
			}
		}
		double mgtFee = rate.getMgmtFee().doubleValue();
		Formula furmula = new Formula(amount_loan, term_loan, new Date(), rate_year, mgtFee, 0, 0, 0);
		calculatorVO.setPrincipalInterestMonth(ObjectFormatUtil.formatCurrency(furmula.getPrincipal_interest_month_first()));
		calculatorVO.setTermLoan(term_loan);
		calculatorVO.setMonthRate(ObjectFormatUtil.formatPercent(furmula.getRate_month(), "##,#0.00%"));
		calculatorVO.setPrincipalInterestBalanceTotal(ObjectFormatUtil.formatCurrency(furmula.getPrincipal_interest_balance_total()));
		List<RepaymentPlanVO> repaymentPlanList = new ArrayList<RepaymentPlanVO>();
		RepaymentPlanVO repaymentPlanvo = null;
		List list = furmula.getRsList();
		Iterator it1 = list.iterator();
		while (it1.hasNext()) {
			Map map = (Map) it1.next();
			repaymentPlanvo = new RepaymentPlanVO();
			repaymentPlanvo.setInterestMonth(ObjectFormatUtil.formatCurrency(Double.parseDouble(map.get("yhbx").toString())));
			// TODO 还款日期
			// repaymentPlanvo.setMonth(new
			// Date(map.get("date_repayment").toString()).getMonth());
			repaymentPlanvo.setPrincipanInterestMonth(ObjectFormatUtil.formatCurrency(Double.parseDouble(map.get("yhbx").toString())));
			repaymentPlanvo.setPrincipalMonth(ObjectFormatUtil.formatCurrency(Double.parseDouble(map.get("yhbj").toString())));
			repaymentPlanvo.setInterestMonth(ObjectFormatUtil.formatCurrency(Double.parseDouble(map.get("yhlx").toString())));
			repaymentPlanvo.setManagementFeeMonth(ObjectFormatUtil.formatCurrency(Double.parseDouble(map.get("yjglf").toString())));
			repaymentPlanvo.setPrincipalInterestBalance(ObjectFormatUtil.formatCurrency(Double.parseDouble(map.get("bxye").toString())));
			repaymentPlanList.add(repaymentPlanvo);
		}
		calculatorVO.setRepayPlanList(repaymentPlanList);
		return calculatorVO;
	}

}
