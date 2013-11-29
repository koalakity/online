package com.zendaimoney.online.web.accountLogin;

import java.io.IOException;

import javax.servlet.http.Cookie;
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

import com.zendaimoney.online.common.TokenGet;
import com.zendaimoney.online.entity.account.AccountUsers;
import com.zendaimoney.online.service.IndexServiceBean;
import com.zendaimoney.online.service.accountLogin.LoginManager;
import com.zendaimoney.online.vo.WebAjaxResult;

/**
 * 
 * @author yijc
 * 
 */
@Controller
@RequestMapping(value = "/accountLogin/login/")
public class LoginController {

	@Autowired
	private LoginManager loginManager;
	@Autowired
	private IndexServiceBean indexservice;

	@RequestMapping(value = "loginValidator")
	public String loginValidator(HttpServletRequest req, HttpServletResponse rep, @ModelAttribute("account") AccountUsers user) throws IOException {
		/*
		 * 安全性漏洞修复，清理会话标识 2012-10-19 修改人：Ray
		 */
		req.getSession().invalidate();// 清空session
		Cookie cookie = req.getCookies()[0];// 获取cookie
		cookie.setMaxAge(0);// 让cookie过期

		JSONObject json = new JSONObject();
		String status = loginManager.loginValidator(req, rep);
		String redirectUrl = "";
		String userEmail = "";
		if ("error".equals(status)) {
			req.setAttribute("message", "用户名或密码错误，登录失败！");
			redirectUrl = "fail";
		} else if ("lock".equals(status)) {
			req.setAttribute("message", "24小时内登录失败超过3次(含),账号" + req.getParameter("loginName") + "暂时被锁定！");
			redirectUrl = "lock";
		} else if ("notApproveEmail".equals(status)) {
			userEmail = loginManager.getUserEmail(req);
			redirectUrl = "notApproveEmail";
		} else if ("dealUserToRecycle".equals(status)) {
			req.setAttribute("message", "该用户不存在，请核对！");
			redirectUrl = "dealUserToRecycle";
		} else {

			req.setAttribute("message", "用户" + req.getParameter("loginName") + "成功！");
			redirectUrl = "success";
			String url = (String) req.getSession().getAttribute("oldUrl");
			if (url != null) {
				json.put("oldUrl", url);
			} else {
				json.put("oldUrl", "");
			}
			req.getSession().setAttribute("token", TokenGet.getToken());
		}
		try {
			json.put("status", redirectUrl);
			json.put("userEmail", userEmail);
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

	@RequestMapping(value = "show")
	public String redirectLogin() {
		return "/accountLogin/login";
	}

	@RequestMapping(value = "login_out")
	public String logout(HttpServletRequest req, Model model) {
		loginManager.logout(req);
		model.addAttribute("indexPage", indexservice.init());
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "checkLogin")
	public WebAjaxResult checkLogin(HttpServletRequest req) {
		return loginManager.checkLogin(req);
	}

	@RequestMapping(value = "redirectLogin")
	public String login() {
		return "/accountLogin/login";
	}

	@RequestMapping(value = "getValidatorImg")
	public String getValidatorImg(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		return "/accountLogin/randomCode";
	}

	@RequestMapping(value = "CheckValidatorImg")
	@ResponseBody
	public String checkValidateCode(@RequestParam("validatorImg") String validatorImg, HttpServletRequest req, HttpServletResponse rep) throws IOException {
		return loginManager.CheckValidatorImg(validatorImg, req, rep);
	}

	@RequestMapping(value = "forgetPwd")
	public String forgetPwd() {
		return "/accountLogin/forgetPassword";
	}

	@RequestMapping(value = "resetPwd")
	public String resetPwd(@ModelAttribute("remail") String remail,
			@ModelAttribute("userName") String userName, 
			Model model
			) {
		if (loginManager.resetPwd(remail)) {
			model.addAttribute("showFlg", "true");
		} else {
			model.addAttribute("showFlg", "false");
		}
		return "/accountLogin/forgetPassword";
	}

	@ResponseBody
	@RequestMapping(value = "checkEmail")
	public String checkEmail(@ModelAttribute("email") String email,HttpServletRequest req) {
		//验证码
		Object validateCode=req.getSession().getAttribute("validateCode");
		String validateCodeParam=req.getParameter("validateCode");
		if (!loginManager.checkEmail(email)) {
			//checkEmail
			return "false";
		}else if(validateCode==null || validateCodeParam==null|| !validateCodeParam.equalsIgnoreCase(validateCode+"")){
			return "codeError";
		} else {
			return "true";
		}
		
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
