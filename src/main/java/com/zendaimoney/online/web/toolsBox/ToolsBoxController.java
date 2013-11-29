package com.zendaimoney.online.web.toolsBox;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.service.toolsBox.ToolsBoxManager;
import com.zendaimoney.online.vo.toolsBox.CalculatorVO;

/**
 * 
 * @author yijc
 * 
 */
@Controller
@RequestMapping(value = "/toolsBox/calculator/")
public class ToolsBoxController {

	@Autowired
	private ToolsBoxManager tbMananger;

	@RequestMapping(value = "showCalculator")
	public String showCalculator() {
		return "/toolsBox/calculator";
	}

	@RequestMapping(value = "show")
	public String showToolsBox() {
		return "/toolsBox/getToolsBox";
	}

	@RequestMapping(value = "showLoanAgreementTemplate")
	public String showLoanAgreementTemplate() {
		return "/toolsBox/loanAgreementTemplate";
	}

	@RequestMapping(value = "getMobileLocation")
	public String getMobileLocation(@RequestParam("phone") String phone, HttpServletRequest req, HttpServletResponse rep) {
		return "/toolsBox/showMobileLocation";
	}

	@RequestMapping(value = "showMobileLocation")
	public String getShowMobileLocation() {
		return "/toolsBox/showMobileLocation";
	}

	@RequestMapping(value = "findIpAddress")
	public String getIpAddress() {
		return "/toolsBox/findIpAddress";
	}

	@RequestMapping(value = "getResultData")
	public String getResultData(@RequestParam("borrowAmount") String borrowAmount, @RequestParam("yearRate") String yearRate, @RequestParam("repayTime") String repayTime, @RequestParam("isShowLoanTime") String isShowLoanTime, Model model, HttpServletResponse rep, HttpServletRequest req) {

		double amount_loan = Double.parseDouble(borrowAmount);
		int term_loan = Integer.parseInt(repayTime);
		double rate_year = Double.parseDouble(yearRate);
		CalculatorVO repayPlanDetail = tbMananger.getRepaymentPlanList(amount_loan, term_loan, rate_year / 100, req);
		JSONObject json = new JSONObject();
		JSONArray jsonObject = JSONArray.fromObject(repayPlanDetail.getRepayPlanList());
		try {
			json.put("principalInterestMonth", repayPlanDetail.getPrincipalInterestMonth());
			json.put("monthRate", repayPlanDetail.getMonthRate());
			json.put("termLoan", repayPlanDetail.getTermLoan());
			json.put("principalInterestBalanceTotal", repayPlanDetail.getPrincipalInterestBalanceTotal());
			json.put("repaymentList", jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			rep.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
