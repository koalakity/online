package com.zendaimoney.online.web.fundDetail;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.service.fundDetail.FundManager;
import com.zendaimoney.online.service.newPay.FundManagerNew;
import com.zendaimoney.online.vo.fundDetail.FundFlowVO;
/**
 * 资金详情 Controller
 * @author mayb
 *
 */

@Controller
@RequestMapping(value="/fundDetail/fundDetail/")
public class FundDetailController {
	@Autowired
	FundManagerNew fundManagerNew;
	
	@Autowired
	FundManager fundDetailManager;
	
	private BigDecimal getUserId(HttpServletRequest req)
	{
		HttpSession session=req.getSession();
		BigDecimal  userId=((BigDecimal)session.getAttribute("curr_login_user_id"));
		return userId;
	}
	
	@RequestMapping(value="showFundDetailHead")
	public String showFundDetailHead(HttpServletRequest req,Model model){
		HttpSession session=req.getSession();
		BigDecimal  userId=getUserId(req);
		model.addAttribute("vo", fundDetailManager.queryFundDetail(userId));
		return "/fundDetail/fundDetailHead";
	}
	
	@RequestMapping(value="showFundDetail")
	public String showFundDetail(Model model,HttpServletRequest req){
		BigDecimal  userId=getUserId(req);
		model.addAttribute("vo", fundDetailManager.queryFundDetail(userId));
		return "/fundDetail/fundDetail";
	}
	/**
	 * 资金管理，流水查询
	 * 2013-3-28 下午1:16:48 by HuYaHui
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value="showFundFlow")
	public String showFundFlow(
			Model model,
			HttpServletRequest req){

		String date_start=req.getParameter("date_start");
		String date_end=req.getParameter("date_end");
		String type_fund=req.getParameter("type_fund");
		

		if(date_start==null || date_start.equals("") || date_start.trim().equals("undefined")){
			Date dt=new Date();
			dt.setDate(1);
			date_start=DateUtil.getYMDTime(dt);
		}
		if(date_end==null || date_end.equals("") || date_end.trim().equals("undefined")){
			date_end=DateUtil.getYMDTime(new Date());
		}
		int type=0;
		if(type_fund==null || type_fund.equals("") || !type_fund.trim().equals("undefined")){
			type=Integer.valueOf(type_fund);
		}
		String pager_offset=req.getParameter("pager.offset");
		int pager=0;
		if(pager_offset!=null && !pager_offset.equals("")){
			pager=Integer.valueOf(pager_offset);
		}
		BigDecimal userId=getUserId(req);
		req.setAttribute("fundFlowVO",fundManagerNew.queryFundFlow(userId.longValue(),date_start,date_end,pager, 10, type));
		return "/fundDetail/fundFlow";
	}
	
	
}
