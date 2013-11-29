package com.zendaimoney.online.web.financial;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.entity.financial.FinancialUserInfoPerson;
import com.zendaimoney.online.entity.financial.FinancialUsers;
import com.zendaimoney.online.oii.sms.SMSSender;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.service.financial.BeLenderManager;
import com.zendaimoney.online.vo.WebAjaxResult;
import com.zendaimoney.online.vo.belender.LicaiUserVO;

@Controller
@RequestMapping(value = "/financial/beLender/")
public class BeLenderController {

	@Autowired
	private BeLenderManager beLenderManager;
	@Autowired
	private RateCommonUtil rateCommon;

	@RequestMapping(value = "getLoginInfo")
	public String identityValidator(HttpServletRequest req, HttpServletResponse rep) {
		// 从上传页面跳转
		String channel = req.getParameter("channel");
		if (channel != null && !channel.equals("")) {
			// 修改页面显示信息
			req.setAttribute("showInfo", "身份证验证");
		}

		if (beLenderManager.isLogin(req, rep)) {
			if (beLenderManager.isLender(req, rep)) {
				// 是理财人
				return "/financial/beLender";
			} else if (beLenderManager.isLenderExceptPhone(req)) {
				// 身份验证通过,但是手机没有验证
				// 查询用户信息
				FinancialUsers user = beLenderManager.getCurrentUser(req, null);
				FinancialUserInfoPerson userInfoPerson = user.getUserInfoPerson();
				req.setAttribute("userInfoPerson", userInfoPerson);
				return "/financial/li_cr";
			} else {
				// 不是理财人
				return "/financial/li_cr";
			}
		} else {
			req.setAttribute("message", "您还没有登录，请先登录！");
			return "/accountLogin/login";
		}

	}

	@RequestMapping(value = "redirectBelender")
	public String redirectBelender(HttpServletRequest req, HttpServletResponse rep) {
		return "/financial/beLender";
	}

	@RequestMapping(value = "checkIdCard")
	public String validateIdCard(HttpServletRequest req, HttpServletResponse rep) {

		return beLenderManager.checkIdCard(req, rep);

	}

	@RequestMapping(value = "validatePhone")
	@ResponseBody
	public void validatePhoneIsUsed(@RequestParam("phoneNumber") String phoneNumber, HttpServletRequest req, HttpServletResponse rep) {
		if (!beLenderManager.checkPhone(phoneNumber, req, rep)) {
			try {
				rep.getWriter().write("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "bindPhoneNo")
	public String bindPhoneNo(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("validateCode") String validateCode, HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			json.put("result", beLenderManager.isBind(phoneNumber, validateCode, req, rep));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			out = rep.getWriter();
			out.println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

		return null;
	}

	@RequestMapping(value = "sendCode")
	public String sendTelValidateCode(@RequestParam("phoneNumber") String phoneNumber, HttpServletRequest req, HttpServletResponse rep) {

		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			Map<String, String> map = beLenderManager.sendPhoneCode(phoneNumber, req, rep);
			int status = 0;
			if (map.containsKey("2")) {
				status = 2;
			} else {
				status = SMSSender.sendMessage("tel_check", phoneNumber, map);
			}

			if (status == 0) {
				json.put("sendResult", "ok");
			} else if (status == 2) {
				json.put("sendResult", "binded");
			} else {
				json.put("sendResult", "fail");
			}
			out = rep.getWriter();
			out.println(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		return null;
	}

	@RequestMapping(value = "showfancy")
	public String showfancy() {
		return "/financial/bePrompt";
	}

	@RequestMapping(value = "validateIdCardAndPhone")
	public String checkAmountIsEnough(@ModelAttribute("licaiUser") LicaiUserVO licaiUser, HttpServletRequest req, HttpServletResponse rep) {
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			String realName = licaiUser.getRealName();
			String identityNo = licaiUser.getIdCardNumber();
			String phoneNo = licaiUser.getPhoneNumber();
			if (realName == null || "".equals(realName)) {
				licaiUser.setRealName(req.getParameter("realNameCopy"));
			}
			if (identityNo == null || "".equals(identityNo)) {
				licaiUser.setIdCardNumber(req.getParameter("idCardNumberCopy"));
			}
			if (phoneNo == null || "".equals(phoneNo)) {
				licaiUser.setPhoneNumber(req.getParameter("phoneNumber"));
			}
			json.put("status", beLenderManager.checkAmountIsEnough(licaiUser, req, rep));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			out = rep.getWriter();
			out.println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		return null;
	}

	// 获取用户可用余额
	@RequestMapping(value = "checkBalanceAmount")
	@ResponseBody
	public String checkBalanceAmount(HttpServletRequest req, HttpServletResponse rep, Model model) {
		FinancialUsers currUser = beLenderManager.getCurrentUser(req, rep);
		// 根据渠道获取对应的费率的ID5验证费用 modify by jihui 2013-03-21
		Long channelId = currUser.getChannelInfoId();
		double idFee = rateCommon.getId5Fee(channelId);
		JSONObject json = new JSONObject();
		json.put("id5Fee", idFee);
		model.addAttribute("idFee", idFee);
		req.setAttribute("id5Fee", idFee);
		if (beLenderManager.getAvailableBalance(currUser) >= idFee) {
			json.put("status", "true");
		} else if (currUser.getIsApproveCard().equals(BigDecimal.ZERO)) {
			json.put("status", "false");
		} else {
			json.put("status", "true");
		}
		try {
			rep.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

}
