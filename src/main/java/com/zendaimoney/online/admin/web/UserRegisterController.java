package com.zendaimoney.online.admin.web;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.service.AccountUsersService;
import com.zendaimoney.online.admin.service.UserRegisterService;
import com.zendaimoney.online.common.CipherUtil;
import com.zendaimoney.online.entity.register.RegisterUsers;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-11-21 上午10:26:18
 * operation by:
 * description:此类用于后台客服为客户新注册
 */
@Controller
@RequestMapping("/admin/register")
public class UserRegisterController {
	
	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private AccountUsersService accountUsersService;
	
	
	/**
	 * @author Ray
	 * @date 2012-11-21 上午10:45:19
	 * @param users
	 * @param serverPath
	 * @param redirectAttributes
	 * @param model
	 * @param request
	 * @return
	 * description:
	 */
	@RequestMapping(value = "saveUser")
	@ResponseBody
	public String save(AccountUsersAdmin users, Model model, HttpServletRequest request) {
		String path = request.getContextPath();
		String serverPath = request.getScheme() + "://"	+ request.getServerName() + ":" + request.getServerPort() + path;
		String email = users.getEmail();
		List<RegisterUsers> userList = userRegisterService.findByEmail(email);
		RegisterUsers registerUser  = userRegisterService.findByLoginName(users.getLoginName());
		if(userList.size()>0){
			model.addAttribute("registerFlag", "该邮箱已经被注册，请更换邮箱！");
			return "register/accountInfo";
		}
		if(registerUser!=null){
			model.addAttribute("registerFlag", "用户名已经存在！");
			return "register/accountInfo";
		}
		users.setLoginPassword(CipherUtil.generatePassword(users.getLoginPassword()));
		users.setRegIp(getIpAddr(request));
		
		

		//2013-1-5增加渠道来源
		String channelInfo_ID=request.getParameter("channelInfo_ID");
		if(channelInfo_ID!=null && !channelInfo_ID.equals("")){
			ChannelInfoVO channelInfo=new ChannelInfoVO();
			channelInfo.setId(Long.valueOf(channelInfo_ID));
			users.setChannelInfo(channelInfo);
		}
		
		return userRegisterService.save(users, serverPath);
	}
	
	
	
	/**
	 * @author Ray
	 * @date 2012-11-21 上午10:41:28
	 * @param request
	 * @return
	 * description:获取注册机IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = null;
		Enumeration enu = request.getHeaderNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			if (name.equalsIgnoreCase("X-Forwarded-For")) {
				ip = request.getHeader(name);
			} else if (name.equalsIgnoreCase("Proxy-Client-IP")) {
				ip = request.getHeader(name);
			} else if (name.equalsIgnoreCase("WL-Proxy-Client-IP")) {
				ip = request.getHeader(name);
			}
			if ((ip != null) && (ip.length() != 0))
				break;
		}
		if ((ip == null) || (ip.length() == 0))
			ip = request.getRemoteAddr();
			return ip;
	}
	
	
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkName(String loginName, Model model, HttpServletResponse rep) {
		if (userRegisterService.findByLoginName(loginName) != null) {
			return "false";
		}else{
			return "true";
		}
	}

	
	@RequestMapping(value = "checkEmail")
	@ResponseBody
	public String checkEmail(String email, Model model, HttpServletResponse rep) {
		if (userRegisterService.findByEmail(email).size() != 0) {
			return "false";
		}else{
			return "true";
		}
	}

}
