package com.zendaimoney.online.admin.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;
import com.zendaimoney.online.admin.service.AccountUsersService;

@Controller
@RequestMapping(value = "/admin/user/")
public class AdminHeadImageController {

	
	@Autowired
	AccountUsersService AccountUsersService;
	@RequestMapping(value = "showheadImg")
	public String showheadImg(HttpServletRequest request, String userId, String flag, Model model) {
		AccountUserInfoPersonAdmin userInfoPerson = AccountUsersService.showImg(userId);
		if(userInfoPerson==null){
			userInfoPerson=new AccountUserInfoPersonAdmin();
		}
		model.addAttribute("userInfoPerson", userInfoPerson);
		request.setAttribute("flag", flag);
		model.addAttribute("flag", flag);
		return "/admin/user/headImg";
	}
}
