package com.zendaimoney.online.web.borrowing;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.stationMessage.StationMessageStationLetter;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;
import com.zendaimoney.online.service.borrowing.ReleaseLoanManager;
import com.zendaimoney.online.service.financial.SearchLoanManager;
import com.zendaimoney.online.service.stationMessage.StationMessageManager;
import com.zendaimoney.online.vo.WebAjaxResult;
import com.zendaimoney.online.vo.borrowing.BorrowingProCheckVO;
import com.zendaimoney.online.vo.borrowing.BorrowingProVO;
import com.zendaimoney.online.vo.borrowing.InvestInfoListVO;

@Controller
@RequestMapping(value = "/borrowing/releaseLoan/")
public class ReleaseLoanController {

	@Autowired
	private ReleaseLoanManager releaseLoanManager;

	@Autowired
	private StationMessageManager stationMessageManager;

	@Autowired
	private SearchLoanManager searchLoanManager;

	@Autowired
	private InfoApproveManager infoApproveManager;

	// 跳转到导航页面
	@RequestMapping(value = "show")
	public String redirectGuide(HttpServletRequest req, HttpServletResponse rep, Model model) {
		// 跳转页面
		String redirectUrl = "";
		// 验证用户状态
		String status = releaseLoanManager.checkUserStatus(req);
		if ("guide".equals(status)) {
			redirectUrl = "/releaseLoanDetail/releaseLoanGuide";
		} else {
			BorrowingProCheckVO userPro = infoApproveManager.userProCheck(req);
			BorrowingProCheckVO userProStatus = infoApproveManager.userProStatus(req);
			BorrowingUsers user = infoApproveManager.getUsers(req);
			if (user != null) {
				model.addAttribute("userInfos", user);
			} else {
				model.addAttribute("userInfos", null);
			}
			model.addAttribute("currUser", releaseLoanManager.getUsersByUserId(req));
			model.addAttribute("availableAmount", releaseLoanManager.getAvailableAmount(req));
			model.addAttribute("creditAmount", releaseLoanManager.getUserCreditAmount(req));
			model.addAttribute("userProStatus", userProStatus);
			model.addAttribute("userPro", userPro);
			BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(req);
			model.addAttribute("creditNote", creditNote);
			if ("lock".equals(status)) {
				model.addAttribute("showMsg", "lock");
				redirectUrl = "/borrowing/infoApprove";
			} else if ("report".equals(status)) {
				model.addAttribute("showMsg", "report");
				redirectUrl = "/borrowing/infoApprove";
			} else if ("yes".equals(releaseLoanManager.isReleaseLoanInfo(req))) {
				model.addAttribute("loanInfoList", releaseLoanManager.getLoanInfoList(req));
				redirectUrl = "/releaseLoanDetail/ongoingloanInfo";
			} else {
				model.addAttribute("borrowingLoanInfo", releaseLoanManager.getReleaseLoanInfo(req));
				redirectUrl = "/borrowing/publishLoan";
			}
		}
		return redirectUrl;
	}

	// 跳转发布借款页面
	@RequestMapping(value = "getShow")
	public String getShow(HttpServletRequest req, HttpServletResponse rep, Model model) {
		model.addAttribute("currUser", releaseLoanManager.getUsersByUserId(req));
		model.addAttribute("availableAmount", releaseLoanManager.getAvailableAmount(req));
		model.addAttribute("creditAmount", releaseLoanManager.getUserCreditAmount(req));
		String flg = infoApproveManager.releaseLoan(req);
		if ("yes".equals(releaseLoanManager.isReleaseLoanInfo(req))) {
			model.addAttribute("loanInfoList", releaseLoanManager.getLoanInfoList(req));
			return "/releaseLoanDetail/ongoingloanInfo";
		} else {
			if (flg.equals("releaseLoan")) {
				model.addAttribute("borrowingLoanInfo", releaseLoanManager.getReleaseLoanInfo(req));
				return "/releaseLoanDetail/releaseLoan";
			} else if (flg.equals("updataImg")) {
				// 跳转到上传资料
				return "redirect:/borrowing/borrowing/showInfo";
			} else {
				// 本页面提示
				BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(req);
				// 可用额度
				BorrowingUsers user = infoApproveManager.getUsers(req);
				BigDecimal kyed = infoApproveManager.showAvailableMoney(req);
				BorrowingProVO appro = infoApproveManager.getUserAppro(user.getUserId());
				model.addAttribute("appro", appro);
				model.addAttribute("creditNote", creditNote);
				model.addAttribute("kyed", kyed);
				model.addAttribute("showMsg", "true");
				return "/borrowing/infoApprove";
			}
		}
	}

	// 计算本息及管理费
	@RequestMapping(value = "caculator")
	public String getResult(@RequestParam("yearRate") String yearRate, @RequestParam("loanAmount") String principal, String loanPeriod, HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = new JSONObject();
		try {
			json.put("principanInterestMonth", releaseLoanManager.getprincipanInterestMonth(principal, yearRate, loanPeriod));
			json.put("managementFeeEveryMonth", releaseLoanManager.getManagementFeeEveryMonth(req, principal));
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

	// 跳转到发布借款详情页面
	@RequestMapping(value = "redirectLoanInfo")
	public String redirectLoanInfo(@RequestParam("loanId") String loanId, HttpServletRequest req, HttpServletResponse rep, Model model) {
		InvestInfoListVO reViewLoanInfo = releaseLoanManager.setInvestInfo(releaseLoanManager.getLoanInfoDetail(new BigDecimal(loanId)), req);
		model.addAttribute("creditInfo", releaseLoanManager.setBorrowerCreditInfo(reViewLoanInfo.getUser().getUserId(), req));
		model.addAttribute("loanInfo", reViewLoanInfo);
		model.addAttribute("loanMessage", releaseLoanManager.getLoanInfoDetail(new BigDecimal(loanId)));
		model.addAttribute("random", Math.random());
		return "/releaseLoanDetail/loanInfo";
	}

	// 判断用户状态
	@RequestMapping(value = "getReleaseStatus")
	public String getReleaseStatus(@ModelAttribute("loanInfo") BorrowingLoanInfo loanInfo, RedirectAttributes redirectAttributes, HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = new JSONObject();
		try {
			json.put("releaseStatus", releaseLoanManager.checkReleaseStatus(loanInfo, req));
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

	// 发布或保存借款信息
	@RequestMapping(value = "saveReleaseInfo")
	public String saveInfo(@ModelAttribute("loanInfo") BorrowingLoanInfo loanInfo, HttpServletRequest req, HttpServletResponse rep, Model model) {
		JSONObject json = new JSONObject();
		try {
			// 防止前段获取url重复提交，所有校验在后台重新执行
			// 发布借款时 是否有在途借款
			if ("yes".equals(releaseLoanManager.isReleaseLoanInfo(req))) {
				model.addAttribute("loanInfoList", releaseLoanManager.getLoanInfoList(req));
				return "/releaseLoanDetail/ongoingloanInfo";
			}

			// 验证所有字段的长度，合法性，如果没成功跳转错误页
			if (loanInfo.getLoanTitle() == null || loanInfo.getLoanTitle().equals("") || loanInfo.getLoanTitle().length() < 4 || loanInfo.getLoanTitle().length() > 20) {
				// 标题不能为空
				throw new Exception("标题不能为空!");
			} else if (loanInfo.getLoanUse() == null || loanInfo.getLoanUse().compareTo(BigDecimal.ONE) == -1 || loanInfo.getLoanUse().compareTo(BigDecimal.TEN) > 1) {
				// 借款用途必须是1-10
				throw new Exception("借款用途不能为空!");
			} else if (loanInfo.getLoanAmount() == null || loanInfo.getLoanAmount().compareTo(3000D) < 0 || loanInfo.getLoanAmount().compareTo(500000D) > 1 || loanInfo.getLoanAmount() % 50 != 0) {
				// 借款金额
				throw new Exception("借款金额不能为空!");
				// throw new Exception("借款金额为3000元-500,000元且需为50的倍数");
			} else if (loanInfo.getLoanDuration() == null
					|| (loanInfo.getLoanDuration().compareTo(BigDecimal.valueOf(3)) != 0 && loanInfo.getLoanDuration().compareTo(BigDecimal.valueOf(6)) != 0 && loanInfo.getLoanDuration().compareTo(BigDecimal.valueOf(9)) != 0 && loanInfo.getLoanDuration().compareTo(BigDecimal.valueOf(12)) != 0 && loanInfo.getLoanDuration().compareTo(BigDecimal.valueOf(18)) != 0 && loanInfo
							.getLoanDuration().compareTo(BigDecimal.valueOf(24)) != 0)) {
				// 借款期限不能为空,3,6,9,12,18,24
				throw new Exception("借款期限不能为空!");
			} else if (loanInfo.getYearRate() == null || loanInfo.getYearRate().compareTo(6D) < 0 || loanInfo.getYearRate().compareTo(24.4D) > 0) {
				// 年利率不能为空，
				// 利率精确到小数点后两位，范围为6%-24.40%之间
				throw new Exception("年利率不能为空!");
			} else if (loanInfo.getDescription() == null || loanInfo.getDescription().equals("")) {
				// 描述
				throw new Exception("描述不能为空!");
			}
			// 年利率
			String yearRate = (loanInfo.getYearRate() / 100) + "";
			// 借款金额
			String principal = loanInfo.getLoanAmount() + "";
			// 期限
			String loanPeriod = loanInfo.getLoanDuration() + "";
			// 每月还本息
			Double monthReturnPrincipalandinter = releaseLoanManager.getprincipanInterestMonth(principal, yearRate, loanPeriod);
			BigDecimal b = new BigDecimal(monthReturnPrincipalandinter).setScale(2, BigDecimal.ROUND_HALF_UP);
			loanInfo.setMonthReturnPrincipalandinter(b.doubleValue());
			// 每月交借款管理费
			Double monthManageCost = releaseLoanManager.getManagementFeeEveryMonth(req, principal);
			b = new BigDecimal(monthManageCost).setScale(2, BigDecimal.ROUND_HALF_UP);
			loanInfo.setMonthManageCost(b.doubleValue());

			json.put("saveStatus", releaseLoanManager.saveReleaseLoanInfo(loanInfo, req));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			rep.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 预览借款信息
	@RequestMapping(value = "reViewLoanInfo")
	public String review(@ModelAttribute("loanInfo") BorrowingLoanInfo loanInfo, RedirectAttributes redirectAttributes, Model model, HttpServletRequest req, HttpServletResponse rep) {
		// 借贷详情设定
		InvestInfoListVO reViewLoanInfo = releaseLoanManager.setInvestInfo(loanInfo, req);

		// 留言板信息
		// List<StationMessageStationLetter> msgs =
		// releaseLoanManager.findMsg(loanInfo.getLoanId());
		// if(0 != msgs.size()){
		// model.addAttribute("msgs", msgs);
		// reViewLoanInfo.setIsShowMsg("1");
		// }else {
		// reViewLoanInfo.setIsShowMsg("0");
		// }
		// isShowMsg 0不显示 1显示
		if(loanInfo.getLoanId()!=null){
			model.addAttribute("loanMessage", releaseLoanManager.getLoanInfoDetail(loanInfo.getLoanId()));
		}else{
			model.addAttribute("loanMessage", loanInfo);
		}
		model.addAttribute("loanInfo", reViewLoanInfo);
		model.addAttribute("creditInfo", releaseLoanManager.setBorrowerCreditInfo(reViewLoanInfo.getUser().getUserId(), req));
		
		return "/releaseLoanDetail/reViewLoanInfo";
	}

	// 借入者信用信息
	@RequestMapping(value = "getCreditInfo")
	public String getBorrowerCreditInfo(@RequestParam("userId") String userIdStr, Long loanId,Model model, HttpServletRequest req, HttpServletResponse rep) {
		BigDecimal userId = new BigDecimal(userIdStr);
		model.addAttribute("creditInfo", releaseLoanManager.setBorrowerCreditInfo(userId, req));
		if(loanId!=null&&StringUtils.isNotEmpty(loanId.toString())){
			model.addAttribute("loanMessage", releaseLoanManager.getLoanInfoDetail(new BigDecimal(loanId)));
		}
		return "/releaseLoanDetail/loan_detail_r2_c1";
	}

	// e贷审核记录
	@RequestMapping(value = "getZendaiAuditRecord")
	public String getZendaiAuditRecord(@RequestParam("userId") String userIdStr, Model model, HttpServletRequest req, HttpServletResponse rep) {
		BigDecimal userId = new BigDecimal(userIdStr);
		model.addAttribute("brCreditRecord", releaseLoanManager.getUserCreditRecord(userId));
		return "/releaseLoanDetail/loan_detail_r2_c2";
	}

	// 借款详情介绍
	@RequestMapping(value = "getLoanInfoDetail")
	public String getLoanDetail(@RequestParam("loanId") String loanIdStr, Model model) {
		BigDecimal loanId = new BigDecimal(loanIdStr);
		model.addAttribute("description", releaseLoanManager.getLoanInfoDescription(loanId));
		return "/releaseLoanDetail/loan_detail_r2_c3";
	}

	// 投标记录
	@RequestMapping(value = "getInvestLoanInfo")
	public String getInvestInfoList(@RequestParam("loanId") String loanIdStr, Model model) {
		BigDecimal loanId = new BigDecimal(loanIdStr);
		model.addAttribute("currInvestInfo", releaseLoanManager.getCurrInvestInfo(loanId));
		return "/releaseLoanDetail/loan_detail_r2_c4";
	}

	@ExceptionHandler
	@ResponseBody
	public WebAjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		WebAjaxResult ajaxResult = new WebAjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}

	// //留言板
	@RequestMapping(value = "showLeavaMsg")
	public String showLeavaMsg(Model model, BigDecimal loanId) {
		List<StationMessageStationLetter> msgs = stationMessageManager.findMsg(loanId);
		model.addAttribute("msgs", msgs);
		return "stationMessage/leaveMessage";
	}

	// //对标留言
	// @RequestMapping( value = "leaveMsg")
	// public String leaveMsg(@RequestParam("message") String message,
	// @RequestParam("loanId") BigDecimal loanId,
	// @RequestParam("userId") BigDecimal userId,
	// @RequestParam("loanAmount") BigDecimal loanAmount,
	// @RequestParam("receiverId") BigDecimal receiverId){
	// //TODO 从用户session中去的用户userid
	// // BigDecimal userId = new BigDecimal("1750");
	// releaseLoanManager.leaveMsg(userId,message,loanId,receiverId);
	// return "redirect:/borrowing/releaseLoan/reViewLoanInfo";
	// }
	//
	// 验证用户身份
	@RequestMapping(value = "checkPower")
	@ResponseBody
	public String checkPower(BigDecimal loanId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal userId = ((BigDecimal) session.getAttribute("curr_login_user_id"));
		if (null == userId || StringUtils.isEmpty(userId.toString()) || loanId == null || StringUtils.isEmpty(loanId.toString())) {
			return "false";
		}
		if (searchLoanManager.isFinancial(req) || releaseLoanManager.checkPower(userId, loanId)) {
			return "true";
		} else {
			return "false";
		}

	}

	// 向用户发送消息
	@RequestMapping(value = "sendMessageToUser")
	public String sendMessageToUser(Model model, BigDecimal loanUserId) {
		String realName = releaseLoanManager.findBorrowUser(loanUserId).getUserInfoPerson().getRealName();
		String loginName = releaseLoanManager.findBorrowUser(loanUserId).getUserInfoPerson().getUser().getLoginName();
		model.addAttribute("realName", loginName);
		return "/releaseLoanDetail/sendMessage";
	}

	// 向用户发送消息
	@ResponseBody
	@RequestMapping(value = "sendMessage")
	public AjaxResult sendMessage(String messageContext, BigDecimal loanUserId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal userId = ((BigDecimal) session.getAttribute("curr_login_user_id"));
		releaseLoanManager.sendMessage(messageContext, loanUserId, userId);
		return new AjaxResult();
	}

}
