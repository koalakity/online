package com.zendaimoney.online.web.homepage;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;
import com.zendaimoney.online.entity.financial.FinancialUsers;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;
import com.zendaimoney.online.service.financial.BeLenderManager;
import com.zendaimoney.online.service.homepage.HomepageManager;
import com.zendaimoney.online.service.pay.PayManager;
import com.zendaimoney.online.vo.borrowing.BorrowingProVO;
import com.zendaimoney.online.vo.homepage.HomepageLeaveMsgVO;
import com.zendaimoney.online.vo.homepage.HomepageLoanInfoTempVO;
import com.zendaimoney.online.vo.homepage.HomepageReturnPageInfoVO;
import com.zendaimoney.online.vo.homepage.HomepageUserMovementVO;

@Controller
@RequestMapping(value = "/homepage/homepage/")
public class HomepageController {

	@Autowired
	HomepageManager personalManager;
	@Autowired
	InfoApproveManager infoApproveManager;
	@Autowired
	private PayManager payManager;
	@Autowired
	private BeLenderManager beLenderManager;

	@RequestMapping(value = "homepageInfoList")
	public String personalInfoList() {
		return "/homepage/homepageInfoList";
	}

	// 点击我的主页 ,初始化用户个人信息
	@RequestMapping(value = "preUserList")
	public String preUserList(HttpServletRequest req, Model model) {
		HomepageReturnPageInfoVO returnPageInfoVO = personalManager.getUserInfo(req);
		List<HomepageUserMovementVO> mvList = personalManager.getUserMovement(personalManager.getUsers(req).getUserId(), req);
		model.addAttribute("mvList", mvList);
		model.addAttribute("returnPageInfoVO", returnPageInfoVO);
		model.addAttribute("random", Math.random());
		return "/homepage/homepageInfoList";
	}

	// 叶头部,最新动态
	@RequestMapping(value = "newInfo")
	public String newInfo(HttpServletRequest req, Model model) {
		// HomepageReturnPageInfoVO
		// returnPageInfoVO=personalManager.getHeadAndNewInfo(req);
		List<HomepageUserMovementVO> mvList = personalManager.getUserMovement(personalManager.getUsers(req).getUserId(), req);
		// model.addAttribute("returnPageInfoVO", returnPageInfoVO );
		model.addAttribute("mvList", mvList);
		return "/homepage/newInfo";
	}

	// 借款记录
	@RequestMapping(value = "showLoanNote")
	public String showLoanNote(HttpServletRequest req, Model model) {
		List<HomepageLoanInfoTempVO> loanList = personalManager.getLoanNote(req);
		model.addAttribute("loanList", loanList);
		return "/homepage/loanNote";
	}

	// 投标记录
	@RequestMapping(value = "showInvestNote")
	public String showInvestNote(HttpServletRequest req, Model model) {
		List<HomepageLoanInfoTempVO> invList = personalManager.getInvestNote(req);
		model.addAttribute("invList", invList);
		return "/homepage/invesertNote";
	}

	// 留言板
	@RequestMapping(value = "showLeaveMsg")
	public String showLeaveMsg(HttpServletRequest req, Model model) {
		List<HomepageLeaveMsgVO> msgList = personalManager.showLeaveMsg(req);
		model.addAttribute("msgList", msgList);
		return "/homepage/leaveMsg";
	}

	// 我的认证
	@RequestMapping(value = "showMyApprove")
	public String showMyApprove(HttpServletRequest req, Model model) {
		List<HomepageUserMovementVO> mvList = personalManager.getUserMovement(personalManager.getUsers(req).getUserId(), req);
		BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(req);
		model.addAttribute("mvList", mvList);
		BigDecimal kyed = infoApproveManager.showAvailableMoney(req);
		BorrowingProVO appro = infoApproveManager.getUserAppro(personalManager.getUsers(req).getUserId());
		model.addAttribute("appro", appro);
		model.addAttribute("kyed", kyed);
		model.addAttribute("creditNote", creditNote);
		setIdCardAttribute(req, model);
		String flg = infoApproveManager.releaseLoan(req);
		model.addAttribute("userStatus", flg);
		return "/homepage/myApprove";
	}

	private void setIdCardAttribute(HttpServletRequest req, Model model) {
		// 身份验证
		boolean flg = checkIdCard(req);
		model.addAttribute("checkIdCard", flg);
		if (!flg) {
			// 未验证
			FinancialUsers currUser = beLenderManager.getCurrentUser(req, null);
			if (beLenderManager.getAvailableBalance(currUser) < 5) {
				model.addAttribute("accountBanance", true);
			}
		}
	}

	/**
	 * 验证身份证是否验证成功 2012-12-20 下午4:35:17 by HuYaHui
	 * 
	 * @return 成功true|失败false
	 */
	private boolean checkIdCard(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		if (userid == null) {
			return false;
		}
		boolean flag = payManager.checkIdCard(userid);
		return flag;
	}
}
