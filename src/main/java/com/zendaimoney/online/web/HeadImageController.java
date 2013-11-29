package com.zendaimoney.online.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zendaimoney.online.service.personal.PersonalManager;
import com.zendaimoney.online.vo.personal.PersonalBaseVO;

@Controller
@RequestMapping(value = "/head/")
public class HeadImageController {

	@Autowired
	PersonalManager personalManager;

	@RequestMapping(value = "showheadImg")
	public String showheadImg(HttpServletRequest request, String flag, Model model) {
		PersonalBaseVO path = personalManager.showImg(request);
		request.setAttribute("flag", flag);
		path.setFlag(flag);
		model.addAttribute("baseVO", path);
		return "/head/headImg";
	}
}
