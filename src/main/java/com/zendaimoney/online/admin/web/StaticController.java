package com.zendaimoney.online.admin.web;

import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.service.StaticService;
import com.zendaimoney.online.common.ObjectFormatUtil;

/**
 * 统计管理
 * 
 * @author JiHui
 * 
 *         add 2012-12-03
 * **/
@Controller
@RequestMapping("/admin/static")
public class StaticController {

	@Autowired
	private StaticService statService;

	@RequestMapping("staticAdminJsp")
	public String staticAdminJsp(Model model) {
		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(Calendar.MONTH) + 1;
		long regCnt = statService.statRegisterCount();
		int loanCnt = statService.statLoanCount();
		double loanAmount = statService.statLoanAmount();
		double remainPrincipal = loanAmount - statService.statRemainPricipal();
		model.addAttribute("RegCnt", regCnt);
		model.addAttribute("loanCnt", loanCnt);
		model.addAttribute("loanAmount", ObjectFormatUtil.formatCurrency(loanAmount));
		model.addAttribute("remainPrincipal", ObjectFormatUtil.formatCurrency(remainPrincipal));
		model.addAttribute("month", currMonth);
		return "admin/statistics/staticPage";
	}

	@RequestMapping("initCombobox")
	@ResponseBody
	public JSONArray initCombobox() {
		JSONObject object = new JSONObject();
		JSONArray jsonArry = new JSONArray();
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < 3; i++) {
			if (i != 0) {
				calendar.add(Calendar.MONTH, -1); // 得到前一个月
			}
			int month = calendar.get(Calendar.MONTH) + 1;
			object.put("id", month);
			object.put("text", month);
			jsonArry.add(object);
		}
		return jsonArry;
	}

	@RequestMapping("getStaticData")
	public String getStaticData(Model model, String month) {
		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(Calendar.MONTH) + 1;
		long regCnt;
		int loanCnt;
		double loanAmount;
		double remainPrincipal;
		if (currMonth == Integer.parseInt(month)) {
			regCnt = statService.statRegisterCount();
			loanCnt = statService.statLoanCount();
			loanAmount = statService.getLoanAmountCond(Integer.parseInt(month));
			remainPrincipal = loanAmount - statService.getRemainPricipal(Integer.parseInt(month));
			// remainPrincipal = statService.statRemainPricipal();
		} else {
			regCnt = statService.getRegisterCount(Integer.parseInt(month));
			loanCnt = statService.getLoanCount(Integer.parseInt(month));
			loanAmount = statService.getLoanAmountCond(Integer.parseInt(month));
			remainPrincipal = loanAmount - statService.getRemainPricipal(Integer.parseInt(month));
		}
		model.addAttribute("RegCnt", regCnt);
		model.addAttribute("loanCnt", loanCnt);
		model.addAttribute("loanAmount", ObjectFormatUtil.formatCurrency(loanAmount));
		model.addAttribute("remainPrincipal", ObjectFormatUtil.formatCurrency(remainPrincipal));
		model.addAttribute("month", month);
		return "admin/statistics/staticPage";
	}
}
