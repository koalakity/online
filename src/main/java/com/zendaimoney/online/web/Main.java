package com.zendaimoney.online.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zendaimoney.online.service.IndexServiceBean;

@Controller
public class Main {

	@Autowired
	private IndexServiceBean indexservice;
	
	@RequestMapping(value="")
	public String index(HttpServletRequest req,HttpServletResponse rep,Model model){
		model.addAttribute("indexPage", indexservice.init());
		return "index";
	}
	
	@RequestMapping(value="getFooter")
	public String getFooter(HttpServletRequest req,HttpServletResponse rep,Model model){
	       model.addAttribute("indexPage", indexservice.getFooter());
	       return "/layouts/footerChild";
	}
	
}
