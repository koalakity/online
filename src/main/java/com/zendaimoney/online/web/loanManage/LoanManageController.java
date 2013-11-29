package com.zendaimoney.online.web.loanManage;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.service.loanManagement.LoanManagementManager;
import com.zendaimoney.online.service.newLoanManagement.PayBackService;
import com.zendaimoney.online.vo.WebAjaxResult;
import com.zendaimoney.online.vo.loanManagement.AheadRepayLoanDetailListVO;
import com.zendaimoney.online.vo.loanManagement.RepayLoanDetailListVO;

@Controller
@RequestMapping(value="/loanManage/loanManage/")
public class LoanManageController {
	
    @Autowired
	private LoanManagementManager loanManagementManager;
    @Autowired
    private PayBackService payBackService;
   // @Autowired
   // private LoanprocessSupportManager supportManager;
	
    //借款统计
    @RequestMapping( value="loanStatistics")
    public String getLoanStatisticsDetail(HttpServletRequest req,Model model){
	    model.addAttribute("loanStatisticsInfo", loanManagementManager.getLoanStatisticsInfo(req));
	  //  supportManager.loanProcess(new BigDecimal(3201));
	    return "/loanManage/loanStatistics";
    }
    
	//跳转发布借款页面
	@RequestMapping( value="redirectMyAccount")
	public String redirectMyAccount(HttpServletRequest req,HttpServletResponse rep,Model model){
			model.addAttribute("strUrl", "${ctx}/loanManage/loanManage/showLoanInfoListPage?pager.offset=0");
		return "${ctx}/myAccount/myAccount/showMyAccount?strUrlType=loan";
	}
    
    //我的借款页面(分页)
    @RequestMapping(value="showLoanInfoListPage")
    public String getLoanInfoListPage(@RequestParam("pager.offset") String offset,HttpServletRequest req,Model model){
        model.addAttribute("loanInfoVO", loanManagementManager.getMyLoanInfoList(req));
        
        return "/loanManage/myLoanList";
    }
    
    //进入偿还借款页面
    @RequestMapping(value="showMyRepayment")
    public String getMyRepayment(HttpServletRequest req,Model model){
        model.addAttribute("loanInfoVO", loanManagementManager.getLoanInfoList(req));
        return "/loanManage/myRepayment";
    }
    
    //我的借款列表(分页)
    @RequestMapping(value="findMyRepaymentList")
    public String geRepaymentList(@RequestParam("pager.offset") String offset,HttpServletRequest req,Model model){
        model.addAttribute("loanInfoVO", loanManagementManager.getLoanInfoList(req));
        return "/loanManage/repaymentList";
    }
    
    
    //已还清借款(分页)
    @RequestMapping(value="findMyRepayOffListPage")
    public String geRepayOffListPage(@RequestParam("pager.offset") String offset,HttpServletRequest req,Model model){
        model.addAttribute("myRepayOffLoanInfoList", loanManagementManager.getRepayOffLoanDetail(req));
        return "/loanManage/repayOffLoanList";
    }
    
    
    //跳转到发布借款详情页面
    @RequestMapping( value="redirectLoanInfo")
    public String redirectLoanInfo(@RequestParam("loanId") String loanId){
        return "redirect:/borrowing/releaseLoan/redirectLoanInfo?loanId="+loanId;
    }
    
    //获取还款详情
    @RequestMapping(value="getRepayLoanDetail")
    public String getRepayLoanDetail(@RequestParam("loanId") String loanId,HttpServletRequest req,Model model){
    	RepayLoanDetailListVO repayLoanDetailListVO = loanManagementManager.getCurrentRepayLoanDetail(req,loanId);
    	model.addAttribute("repayLoanDetail", repayLoanDetailListVO);
        return "/loanManage/repayLoanDetail";
     }
    
    //获取提前还款详情
    @RequestMapping(value="getInRepayLoanDetail")
    public String getInRepayLoanDetail(@RequestParam("loanId") String loanId,HttpServletRequest req,Model model){
    	AheadRepayLoanDetailListVO aheadRepayLoanDetailListVO = loanManagementManager.getCurrentInRepayLoanDetail(req,loanId);
    	model.addAttribute("aheadRepayLoanDetail", aheadRepayLoanDetailListVO);
        return "/loanManage/earlyRepayLoanDetail";
     }    
    
    //①	判断“我的余额”是否≥“本期共计还款金额”：
    @RequestMapping(value="getMyAvailableBalance")
    @ResponseBody
    public WebAjaxResult getMyAvailableBalance(@RequestParam("paymentTotalDouble") String paymentTotalDouble,HttpServletRequest req,HttpServletResponse rep){
    	boolean  status = loanManagementManager.myCapitalIsEnough(paymentTotalDouble,req) ;
		WebAjaxResult result = new WebAjaxResult();
		if(!status){
			result.setMsg("您的可用余额不足，请先充值！");
			result.setSuccess(Boolean.FALSE);
		}
		 return  result;
     }
    
    //②确认还款后，进行该笔还款
    @RequestMapping(value="payCurrentLoan")
    public String  payCurrentLoan(@RequestParam("paymentTotalDouble") String paymentTotalDouble,@RequestParam("paymentLoanId") String paymentLoanId,@RequestParam("currentTermLoan") String currentTermLoan,@RequestParam("payType") String payType,HttpServletRequest req,HttpServletResponse rep){
    	//String payResult = loanManagementManager.paymentProcessing(paymentTotalDouble,paymentLoanId,currentTermLoan,payType,req);	
    	String payResult = null;
    	try {
        	 payResult = payBackService.payBackProcess( new Long(paymentLoanId), currentTermLoan, payType, req);
		} catch (RuntimeException e) {
			payResult = "payed";
			e.printStackTrace();
		}
    		    	if(payResult==null){
    		payResult="";
    	}
    	JSONObject json = new JSONObject();  
        try {
            json.put("paymentTotalDouble", paymentTotalDouble);
            json.put("payResult", payResult);
            rep.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return null;
    }
    
    //提前还款判断是否有逾期未还
    @RequestMapping(value="checkIsOverdue")
    @ResponseBody
    public WebAjaxResult checkIsOverdue(@RequestParam("loanId") String loanId,HttpServletRequest req,Model model){
		WebAjaxResult result = new WebAjaxResult();
		if(loanManagementManager.checkIsOverdue(req,loanId)==false){
			result.setMsg("当前存在逾期未还，不能进行提前还款操作！");
			result.setSuccess(Boolean.FALSE);
		}
		 return  result;
    }
    
	@ExceptionHandler
	@ResponseBody
	public WebAjaxResult handleException(RuntimeException e){
		e.printStackTrace();
		WebAjaxResult ajaxResult = new WebAjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}
    
}
