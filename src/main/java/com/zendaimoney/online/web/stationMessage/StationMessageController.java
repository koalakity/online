package com.zendaimoney.online.web.stationMessage;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.entity.stationMessage.StationMessageStationLetter;
import com.zendaimoney.online.service.stationMessage.StationMessageManager;

@Controller
@RequestMapping(value = "/stationMessage/stationMessage/")
public class StationMessageController {

	@Autowired
	private StationMessageManager stationMessageManager;

	// TODO 借款详情的留言页面

	@RequestMapping(value = "showLeavaMsg")
	public String showLeavaMsg(Model model, BigDecimal loanId) {
		List<StationMessageStationLetter> msgs = stationMessageManager.findMsg(loanId);
		model.addAttribute("loanId", loanId);
		model.addAttribute("msgs", msgs);
		return "stationMessage/leaveMessage";
	}

	// 对标留言
	@RequestMapping(value = "leaveMsg")
	@ResponseBody
	public AjaxResult leaveMsg(@RequestParam("message") String message, @RequestParam("loanId") BigDecimal loanId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal userId = ((BigDecimal) session.getAttribute("curr_login_user_id"));

		stationMessageManager.leaveMsg(userId, message, loanId, userId);
		return new AjaxResult();
	}

	// 验证用户身份
	@RequestMapping(value = "checkPower")
	@ResponseBody
	public String checkPower() {
		// TODO 从用户session中去的用户userid
		BigDecimal userId = new BigDecimal("1750");
		return stationMessageManager.checkPower(userId);

	}

	// 对借款信息留言回复
	@RequestMapping(value = "replyToLoanMessage")
	@ResponseBody
	public AjaxResult replyToLoanMessage(@RequestParam("message") String message, @RequestParam("loanId") BigDecimal loanId, @RequestParam("receiverId") BigDecimal receiverId, @RequestParam("letterId") BigDecimal letterId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal userId = ((BigDecimal) session.getAttribute("curr_login_user_id"));
		stationMessageManager.replyToLoanMessage(loanId, receiverId, letterId, message, userId);
		return new AjaxResult();
	}

	// 对用户进行留言
	@RequestMapping(value = "leaveMsgForUser")
	public String leaveMsgForUser(@RequestParam("message") String message, @RequestParam("loanId") BigDecimal loanId, @RequestParam("receiverId") BigDecimal receiverId, @RequestParam("letterId") BigDecimal letterId) {
		stationMessageManager.levaeMsgFroUser(loanId, receiverId, letterId, message);
		return "redirect:/stationMessage/stationMessage/showLeavaMsg";
	}

	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}
}
