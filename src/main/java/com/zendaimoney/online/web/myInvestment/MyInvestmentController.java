package com.zendaimoney.online.web.myInvestment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.service.myInvestment.MyInvestmentmanager;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentLctjVo;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentSkzVo;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentVo;
import com.zendaimoney.online.vo.myInvestment.MyInvestmentYskVo;


@Controller
@RequestMapping(value = "/myInvestment/myInvestment/")
public class MyInvestmentController {
	
	@Autowired
	MyInvestmentmanager myInvestmentmanager;

	@RequestMapping( value = "showIndx")
	public String showIndx(HttpServletRequest req,Model model){
		List<MyInvestmentVo> inverstInfoList = myInvestmentmanager.getTbzLoan(req);
		model.addAttribute("inverstInfoList", inverstInfoList);
		return "myInvestment/myInvestment_head";
	}
	
	@RequestMapping( value = "showPage")
	public String showPage(@RequestParam("pager.offset") String offset,HttpServletRequest req,Model model){
		List<MyInvestmentVo> inverstInfoList = myInvestmentmanager.getTbzLoan(req);
		model.addAttribute("inverstInfoList", inverstInfoList);
		return "myInvestment/myInvestment_t";
	}
	
	@RequestMapping( value = "showTbzLoan")
	public String showTbzLoan(HttpServletRequest req,Model model){
		List<MyInvestmentVo> inverstInfoList = myInvestmentmanager.getTbzLoan(req);
		model.addAttribute("inverstInfoList", inverstInfoList);
		return "myInvestment/myInvestment_t";
	}
	
	@RequestMapping (value = "showSkzLoan")
	public String showSkzLoan(@RequestParam("pager.offset") String offset, HttpServletRequest req,Model model){
		HttpSession session = req.getSession();
		BigDecimal userId =  (BigDecimal)session.getAttribute("curr_login_user_id");
		List<MyInvestmentSkzVo> skzVoList = myInvestmentmanager.getSkzLoan(userId);
		model.addAttribute("skzVoList", skzVoList);
		return "myInvestment/myInvestment_skz";
	}
	
	@RequestMapping (value = "showYskLoan")
	public String showYskLoan(@RequestParam("pager.offset") String offset,HttpServletRequest req,Model model){
		HttpSession session = req.getSession();
		BigDecimal userId =  (BigDecimal)session.getAttribute("curr_login_user_id");
		List<MyInvestmentYskVo> yskList = myInvestmentmanager.getYsk(userId);
		model.addAttribute("yskList", yskList);
		return "myInvestment/myInvestment_ysk";
	}
	
	@RequestMapping (value = "showHzcxLoan")
	public String showHzcxLoan(@RequestParam("pager.offset") String offset,HttpServletRequest req,Model model){
		HttpSession session = req.getSession();
		BigDecimal userId =  (BigDecimal)session.getAttribute("curr_login_user_id");
//		回帐查询(详细列表)
//		List<MyInvestmentYskVo> hzcxList = myInvestmentmanager.getHzcxDetail(userId);
		//回帐查询(汇总)
		Map<String,String> hzcxTotal = myInvestmentmanager.getHzcxTotal(userId);
		
		model.addAttribute("oneMonthAmount", hzcxTotal.get("oneMonthAmount"));
		model.addAttribute("threeMonthAmount", hzcxTotal.get("threeMonthAmount"));
		model.addAttribute("oneYearAmount", hzcxTotal.get("oneYearAmount"));
		model.addAttribute("allAmount", hzcxTotal.get("allAmount"));
		model.addAttribute("allAmountSearch", 0d);
//		model.addAttribute("hzcxList", hzcxList);
		
		return "myInvestment/myInvestment_hzcx";
	}
	
	/**
	 * @author 不详
	 * @date 2012-12-24 上午10:35:38
	 * @param offset 
	 * @param startDate 
	 * @param endDate
	 * @param req
	 * @param model
	 * @return
	 * description: 2012-12-24 Ray修改1014bug
	 */
	@RequestMapping (value = "showHzcxLoanSearch")
	public String showHzcxLoanSearch(@RequestParam("pager.offset") String offset,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,HttpServletRequest req,Model model){
		HttpSession session = req.getSession();
		BigDecimal userId =  (BigDecimal)session.getAttribute("curr_login_user_id");
//		回帐查询(详细列表)
		List<MyInvestmentSkzVo> hzcxList = myInvestmentmanager.getHzcxDetailSearch(userId,startDate,endDate);
		//回帐查询(汇总)
		Map<String,String> hzcxTotal = myInvestmentmanager.getHzcxTotal(userId);
		
		model.addAttribute("oneMonthAmount", hzcxTotal.get("oneMonthAmount"));
		model.addAttribute("threeMonthAmount", hzcxTotal.get("threeMonthAmount"));
		model.addAttribute("oneYearAmount", hzcxTotal.get("oneYearAmount"));
		model.addAttribute("allAmountSearch", ObjectFormatUtil.formatCurrency(myInvestmentmanager.getHzcxDetailSearchTotal(userId,startDate,endDate)));
		model.addAttribute("allAmount", hzcxTotal.get("allAmount"));
		model.addAttribute("hzcxList", hzcxList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "myInvestment/myInvestment_hzcx";
	}
	
	//show理财统计
	@RequestMapping( value="showLctj" )
	public String showLctj(HttpServletRequest req,Model model){
		MyInvestmentLctjVo lctjVo = myInvestmentmanager.getLctj(req);
		model.addAttribute("lctjVo", lctjVo);
		return "myInvestment/myInvestment_lctj";
	}
}
