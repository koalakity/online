package com.zendaimoney.online.web.financial;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.common.TokenUtil;
import com.zendaimoney.online.entity.financial.FinancialUsers;
import com.zendaimoney.online.service.financial.SearchLoanManager;
import com.zendaimoney.online.service.financial.TenderManager;
import com.zendaimoney.online.vo.financial.LoanInfoVO;

@Controller
@RequestMapping(value = "/financial/searchLoan/")
public class SerchLoanController {
	private static Logger performanceLogger = LoggerFactory.getLogger("performance");
	@Autowired
	private SearchLoanManager searchLoanManager;
	@Autowired
	private TenderManager TenderManager;

	// 全部借款
	@RequestMapping(value = "allLoan")
	public String allLoan(@RequestParam("column") String column, @RequestParam("seq") String seq, @RequestParam("p") String p, HttpServletRequest request, Model model) {
		performanceLogger.info("---------allLoan---------");
		long start = System.currentTimeMillis();
		String offset = request.getParameter("pager.offset");
		int _offset = 0;
		if (offset != null && !offset.equals("")) {
			_offset = Integer.valueOf(offset);
		}
		List<LoanInfoVO> loanList = searchLoanManager.allLoan(column, seq, _offset);
		LoanInfoVO count = searchLoanManager.loanCount();
		model.addAttribute("loanList", loanList);
		model.addAttribute("count", count);

		if (column.equals("loanAmount")) {
			model.addAttribute("loanAmountseq", seq);
		}
		if (column.equals("yearRate")) {
			model.addAttribute("yearRateseq", seq);
		}
		if (column.equals("creditGrade")) {
			model.addAttribute("creditGradeseq", seq);
		}
		performanceLogger.info("查询借款总耗时allLoan:" + (System.currentTimeMillis() - start));
		return "/financial/searchLoanAllLoan";
	}

	// 进行中借款
	@RequestMapping(value = "loanIng")
	public String LoanIng(@RequestParam("column") String column, @RequestParam("seq") String seq, HttpServletRequest request, @RequestParam("p") String p, Model model) {
		performanceLogger.info("---------loanIng---------");
		long start = System.currentTimeMillis();
		String offset = request.getParameter("pager.offset");
		int _offset = 0;
		if (offset != null && !offset.equals("")) {
			_offset = Integer.valueOf(offset);
		}
		List<LoanInfoVO> loanList = searchLoanManager.loanIng(column, seq, _offset);
		LoanInfoVO count = searchLoanManager.loanCount();
		model.addAttribute("loanList", loanList);
		model.addAttribute("count", count);
		if (column.equals("loanAmount")) {
			model.addAttribute("loanAmountseq", seq);
		}
		if (column.equals("yearRate")) {
			model.addAttribute("yearRateseq", seq);
		}
		if (column.equals("creditGrade")) {
			model.addAttribute("creditGradeseq", seq);
		}
		performanceLogger.info("查询借款总耗时loanIng:" + (System.currentTimeMillis() - start));
		return "/financial/searchLoanLoanIng";
	}

	// 即将开始借款
	@RequestMapping(value = "futureLoan")
	public String futureLoan(@RequestParam("column") String column, @RequestParam("seq") String seq, @RequestParam("p") String p, HttpServletRequest request, Model model) {
		performanceLogger.info("---------futureLoan---------");
		long start = System.currentTimeMillis();
		String offset = request.getParameter("pager.offset");
		int _offset = 0;
		if (offset != null && !offset.equals("")) {
			_offset = Integer.valueOf(offset);
		}

		List<LoanInfoVO> loanList = searchLoanManager.getLoanListByStatus("3", column, seq, _offset);
		LoanInfoVO count = searchLoanManager.loanCount();
		if (column.equals("loanAmount")) {
			model.addAttribute("loanAmountseq", seq);
		}
		if (column.equals("yearRate")) {
			model.addAttribute("yearRateseq", seq);
		}
		if (column.equals("creditGrade")) {
			model.addAttribute("creditGradeseq", seq);
		}
		model.addAttribute("loanList", loanList);
		model.addAttribute("count", count);
		performanceLogger.info("查询借款总耗时futureLoan:" + (System.currentTimeMillis() - start));
		return "/financial/searchLoanFutureLoan";
	}

	// 已经完成借款
	@RequestMapping(value = "loanEd")
	public String loanEd(@RequestParam("column") String column, @RequestParam("seq") String seq, @RequestParam("p") String p, HttpServletRequest request, Model model) {
		performanceLogger.info("---------loanEd---------");
		long start = System.currentTimeMillis();
		String offset = request.getParameter("pager.offset");
		int _offset = 0;
		if (offset != null && !offset.equals("")) {
			_offset = Integer.valueOf(offset);
		}
		List<LoanInfoVO> loanList = searchLoanManager.getLoanListByStatus("4", column, seq, _offset);
		LoanInfoVO count = searchLoanManager.loanCount();
		if (column.equals("loanAmount")) {
			model.addAttribute("loanAmountseq", seq);
		}
		if (column.equals("yearRate")) {
			model.addAttribute("yearRateseq", seq);
		}
		if (column.equals("creditGrade")) {
			model.addAttribute("creditGradeseq", seq);
		}
		model.addAttribute("loanList", loanList);
		model.addAttribute("count", count);
		performanceLogger.info("查询借款总耗时loanEd:" + (System.currentTimeMillis() - start));
		return "/financial/searchLoanEd";
	}

	/**
	 * 点击借款进入 2012-11-29 下午3:21:32 by HuYaHui
	 * 
	 * @param pager_offset
	 *            从第几条开始
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showLoan")
	public String showLoan(Long tabFlg, Model model) {
		performanceLogger.info("---------showLoan---------");
		long start = System.currentTimeMillis();
		List<LoanInfoVO> loanList = searchLoanManager.allLoan("none", "none", 0);
		LoanInfoVO count = searchLoanManager.loanCount();
		model.addAttribute("loanList", loanList);
		model.addAttribute("count", count);
		performanceLogger.info("查询借款总耗时showLoan:" + (System.currentTimeMillis() - start));
		if (tabFlg == null) {
			tabFlg = Long.valueOf(0);
		}
		model.addAttribute("tabCFlag", tabFlg);
		return "/financial/searchLoanHead";
	}

	// 理财计算机
	@ResponseBody
	@RequestMapping(value = "investCalculate")
	public String investCalculate(@RequestParam("investAmount") double investAmount, @RequestParam("yearRate") double yearRate, @RequestParam("loanDuration") int loanDuration) {
		double receiveAmount = searchLoanManager.investCalculate(investAmount, yearRate, loanDuration);
		return String.valueOf(receiveAmount);
	}

	// 搜索
	@RequestMapping(value = "searchLoanList")
	public String searchLoan(@RequestParam("pager.offset") String offset, @RequestParam("creditGrade") String creditGrade, @RequestParam("yearRate") String yearRate, @RequestParam("loanTime") String loanTime, @RequestParam("column") String column, @RequestParam("seq") String seq, @RequestParam("p") String p, String tabFlag, Model model) {
		List<LoanInfoVO> loanList = searchLoanManager.searchLoanBycondition(creditGrade, yearRate, loanTime, column, seq, p, tabFlag);
		model.addAttribute("loanList", loanList);
		if (column.equals("loanAmount")) {
			model.addAttribute("loanAmountseq", seq);
		}
		if (column.equals("yearRate")) {
			model.addAttribute("yearRateseq", seq);
		}
		if (column.equals("creditGrade")) {
			model.addAttribute("creditGradeseq", seq);
		}
		LoanInfoVO count = searchLoanManager.loanCount();
		if ("1".equals(tabFlag)) {
			count.setAllLoanCount(String.valueOf(loanList.size()));
			model.addAttribute("count", count);
			return "/financial/searchForLoanAllLoan";
		} else if ("2".equals(tabFlag)) {
			count.setIngLoanCount(String.valueOf(loanList.size()));
			model.addAttribute("count", count);
			return "/financial/searchForLoanLoanIng";
			// 即将开始的借款隐藏 modify by jihui 2013-02-08
			// } else if ("3".equals(tabFlag)) {
			// count.setFutureLoanCount(String.valueOf(loanList.size()));
			// model.addAttribute("count", count);
			// return "/financial/searchForLoanFutureLoan";
		} else if ("3".equals(tabFlag)) {
			count.setOldLoanCount(String.valueOf(loanList.size()));
			model.addAttribute("count", count);
			return "/financial/searchForLoanEd";
		} else {
			count.setAllLoanCount(String.valueOf(loanList.size()));
			model.addAttribute("count", count);
			return "/financial/searchForLoanAllLoan";
		}

		// return "/financial/searchLoanBlackList";
	}

	// //逾期黑名单
	// @RequestMapping( value = "blackList")
	// public String blackList(Model model){
	// return "/financial/searchLoanBlackList";
	// }

	// 确认投资金额
	@RequestMapping(value = "confirmInvest")
	public String confirmInvest(@RequestParam("loanId") BigDecimal loanId, Model model, HttpServletRequest req) {
		LoanInfoVO loanInfo = searchLoanManager.confirmInvest(loanId, req);
		model.addAttribute("loanInfo", loanInfo);
		model.addAttribute("tokenValue", TokenUtil.getToken(req, "confirmInvest"));
		return "/financial/confirmInvest";
	}

	// @RequestMapping ( value = "searchLoanBycondition")
	// public String searchLoanBycondition(Model model,
	// @RequestParam("creditGrade") String creditGrade,
	// @RequestParam("yearRate") String yearRate,
	// @RequestParam("loanDuration") String loanDuration){
	// searchLoanManager.searchLoanBycondition(creditGrade, yearRate,
	// loanDuration);
	//
	// return "/financial/searchLoan";
	// }

	// 检验用户是否登录
	@ResponseBody
	@RequestMapping(value = "checkUserLogin")
	public String checkUserLogin(HttpServletRequest req) {
		FinancialUsers user = searchLoanManager.getUsers(req);
		if (user != null) {
			boolean checkFinancial = searchLoanManager.isFinancial(req);
			if (!checkFinancial) {
				// TODO 跳转到成为理财人页面
				return "becomeFinancial";
			} else {
				if ((user.getUserStatus().equals(BigDecimal.valueOf(6L))) || (user.getUserStatus().equals(BigDecimal.valueOf(7L)))) {
					// 当前您已被举报或锁定，请邮件或电话客服咨询
					return "lockUser";
				} else {
					return "showInvesment";
				}
			}
		} else {
			return "notLogin";
		}
	}

	// 检验用户可用余额
	@ResponseBody
	@RequestMapping(value = "checkUserAomount")
	public String checkUserAomount(@RequestParam("investAmount") double investAmount, @RequestParam("loanId") BigDecimal loanId, HttpServletRequest req) {
		// 可用余额
		double myMoney = searchLoanManager.checkUserAomount(req);
		// 剩余投标金额
		double surplusAmount = searchLoanManager.checkSurplusAmount(loanId, req);
		if (myMoney >= investAmount) {
			if (investAmount > surplusAmount) {
				return "full";
			} else {
				return "true";
			}
		} else {
			return "false";
		}

	}

	/**
	 * @author Ray
	 * @date 2013-2-16 上午10:53:37
	 * @param investAmount
	 * @param loanId
	 * @param req
	 * @param requestToken
	 * @return
	 * description:投标
	 * 注：资金流水优化重写投标
	 */
	@ResponseBody
	@RequestMapping(value = "invest")
	public String confirmInvest(@RequestParam("investAmount") double investAmount, @RequestParam("loanId") BigDecimal loanId, HttpServletRequest req, String requestToken) {
		if (!TokenUtil.validateToken(req, requestToken, "confirmInvest")) {
			return "false";
		}
		boolean checkOwner = searchLoanManager.checkOwner(loanId, req);
		if (checkOwner) {
			return "cantInvest";
		} else {
			boolean flg = false;
			try {
				flg = TenderManager.invest(BigDecimal.valueOf(investAmount), loanId, req);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flg)
				return "true";
			else
				return "false";
		}

	}

	@RequestMapping(value = "loanIngData")
	@ResponseBody
	public String loanIngData(HttpServletRequest request, Model model) {
		String offset = request.getParameter("pager.offset");
		int _offset = 0;
		if (offset != null && !offset.equals("")) {
			_offset = Integer.valueOf(offset);
		}
		List<LoanInfoVO> loanList = searchLoanManager.loanIng("", "", _offset);
		LoanInfoVO count = searchLoanManager.loanCount();
		model.addAttribute("loanList", loanList);
		model.addAttribute("count", count);
		return searchLoanManager.listToJson(loanList);
	}

	/**
	 * 检查投标时间两次投标时间要间隔一分钟以上
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkInvestTime")
	public String checkInvestTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userId = (BigDecimal) session.getAttribute("curr_login_user_id");
		if (searchLoanManager.checkInvestTime(userId)) {
			return "true";
		}
		return "false";

	}

}
