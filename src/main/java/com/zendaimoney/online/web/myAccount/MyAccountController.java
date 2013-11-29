package com.zendaimoney.online.web.myAccount;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.service.homepage.HomepageManager;
import com.zendaimoney.online.service.message.MessageManager;
import com.zendaimoney.online.service.toolsBox.ContractManager;
import com.zendaimoney.online.vo.homepage.HomePageVO;
import com.zendaimoney.online.vo.toolsBox.BorrowContractVO;

@Controller
@RequestMapping(value = "/myAccount/myAccount/")
public class MyAccountController {

	@Autowired
	HomepageManager personalManager;

	@Autowired
	MessageManager messageManager;

	@Autowired
	private ContractManager contractManager;

	@RequestMapping(value = "showMyAccount")
	public String preUserList(@RequestParam("strUrlType") String strUrlType, HttpServletRequest req, Model model) {
		// HomepageReturnPageInfoVO returnPageInfoVO = personalManager
		// .getUserInfo(req);
		// model.addAttribute("returnPageInfoVO", returnPageInfoVO);
		HomePageVO homePageVO = personalManager.getHomePageVO(req);
		req.setAttribute("token", req.getParameter("token"));
		model.addAttribute("homePageVO", homePageVO);
		if ("loan".equals(strUrlType)) {
			model.addAttribute("strUrl", "/loanManage/loanManage/showLoanInfoListPage?pager.offset=0");
		} else if ("fundDetail".equals(strUrlType)) {
			model.addAttribute("strUrl", "/fundDetail/fundDetail/showFundDetailHead");
		} else if ("message".equals(strUrlType)) {
			model.addAttribute("strUrl", "/message/message/messagePageHead");
		} else if ("myAccount".equals(strUrlType)) {
			model.addAttribute("strUrl", "/homepage/homepage/preUserList");
		} else if ("myApprove".equals(strUrlType)) {
			model.addAttribute("strUrl", "/homepage/homepage/showMyApprove");
		} else if ("pay".equals(strUrlType)) {
			// 跳转充值页面
			model.addAttribute("strUrl", "/pay/pay/showPay?token=" + req.getParameter("token"));
		} else if ("repay".equals(strUrlType)) {
			// 跳转还款页面
			model.addAttribute("strUrl", "/loanManage/loanManage/showMyRepayment?pager.offset=0");
		} else if ("investment".equals(strUrlType)) {
			model.addAttribute("strUrl", "/myAccount/myAccount/showMyInvestment");
		} else if ("person".equals(strUrlType)) {
			model.addAttribute("strUrl", "/myAccount/myAccount/showPersonal");
		} else {
		}
		return "/myAccount/myAccount";
	}

	@RequestMapping(value = "managePersonal")
	public String managePersonal(@RequestParam("strUrlType") String strUrlType, HttpServletRequest req, Model model) {
		HomePageVO homePageVO = personalManager.getHomePageVO(req);
		model.addAttribute("homePageVO", homePageVO);
		if ("myAccount".equals(strUrlType)) {
			model.addAttribute("strUrl", "/personal/personal/personalHead");
		}
		return "/myAccount/myAccount";
	}

	@RequestMapping(value = "notifySetRedirect")
	public String notifySetRedirect(@RequestParam("strUrlType") String strUrlType, HttpServletRequest req, Model model) {
		HomePageVO homePageVO = personalManager.getHomePageVO(req);
		model.addAttribute("homePageVO", homePageVO);
		if ("myAccount".equals(strUrlType)) {
			model.addAttribute("strUrl", "/personal/personal/notifyMsgSet");
		}
		return "/myAccount/myAccount";
	}

	@RequestMapping(value = "myAccount")
	public String myAccount(Model model) {
		return "redirect:/homepage/homepage/preUserList";
	}

	@RequestMapping(value = "messageManagement")
	public String systemMessageInfo(HttpServletRequest req, Model model) {
		return "redirect:/message/message/messageInfo";
	}

	@RequestMapping(value = "showMyInvestment")
	public String showMyTz() {
		return "redirect:/myInvestment/myInvestment/showIndx";
	}

	@RequestMapping(value = "showPersonal")
	public String showPersonal() {
		return "redirect:/personal/personal/personalHead";
	}

	@RequestMapping(value = "showMessage")
	public String showMessage() {
		return "redirect:/message/message/messagePageHead";
	}

	@RequestMapping(value = "showFundDetail")
	public String showFundDetail() {
		return "redirect:/fundDetail/fundDetail/showFundDetailHead";
	}

	@RequestMapping(value = "modifyPassword")
	public String modifyPassword() {
		return "redirect:/personal/personal/showChangePassword";
	}

	@RequestMapping(value = "showLctj")
	public String showLctj() {
		return "redirect:/myInvestment/myInvestment/showLctj";
	}

	// 查看借款协议范本
	@RequestMapping(value = "showContract")
	public String showContract(@RequestParam("loanId") BigDecimal loanId, Model model) {
		BorrowContractVO borrowContractVO = contractManager.showBorrowContract(loanId);
		model.addAttribute("borrowContractVO", borrowContractVO);
		return "/toolsBox/showBorrowContract";
	}

	// 查看借款协议风险基金范本
	@RequestMapping(value = "showRiskContract")
	public String showRiskContract(@RequestParam("loanId") BigDecimal loanId, Model model) {
		BorrowContractVO borrowContractVO = contractManager.showBorrowContract(loanId);
		model.addAttribute("borrowContractVO", borrowContractVO);
		return "/toolsBox/showRiskFundContract";
	}
}
