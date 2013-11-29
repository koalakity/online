/**
 * 
 */
package com.zendaimoney.online.admin.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.LoanInfoAdminVO;
import com.zendaimoney.online.admin.entity.loan.AcTVirtualCashFlowAdmin;
import com.zendaimoney.online.admin.entity.loan.InvestInfoAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanNoteAdmin;
import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.admin.service.LoanManagerService;
import com.zendaimoney.online.admin.service.LoanprocessSupportService;
import com.zendaimoney.online.admin.service.RateService;
import com.zendaimoney.online.admin.util.ValidateCode;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.LoanInfoListForm;
import com.zendaimoney.online.admin.vo.LoanNoteListForm;
import com.zendaimoney.online.common.TokenUtil;
import com.zendaimoney.online.service.toolsBox.ContractManager;
import com.zendaimoney.online.vo.toolsBox.BorrowContractVO;

/**
 * @author 王腾飞 借款信息管理
 */

@Controller
@RequestMapping("/admin/loan")
public class LoanController {
	private static Logger logger = LoggerFactory.getLogger(LoanController.class);
	@Autowired
	private LoanManagerService laonManagerServic;
	@Autowired
	private ContractManager contractManager;
	// @Autowired
	// LoanManagementManager loanManagementManager;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private LoanprocessSupportService loanprocessSupportService;
	@Autowired
	private RateService rateService;

	//待审核借款列表
	@RequestMapping("waitingForAuditloanPageJsp")
	public String waitingForAuditloanPageJsp(HttpServletRequest request) {
		return "admin/loan/waitingForAuditloanPage";
	}

	/**
	 * 待审核借款列表
	 * 
	 * @param loanInfoListForm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("waitingForAuditloanPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin> waitingForAuditloanPage(LoanInfoListForm loanInfoListForm, Integer page, Integer rows) {

		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin> logininfos = new com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin>(laonManagerServic.findLoadPage(loanInfoListForm, new PageRequest(page - 1, rows, new Sort(Direction.DESC, "releaseTime"))));
		return logininfos;
	}

	@RequestMapping("loanTenderingPageJsp")
	public String tenderingloanPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/loan/loanTenderingPage";
	}
	
	
	/**
	 * 设置渠道信息到请求对象 2013-1-5 上午10:27:58 
	 * 
	 * @param request
	 */
	private void setChannelInfoToRequest(HttpServletRequest request) {
		// 所有一级渠道信息
		List<ChannelInfoVO> channelInfoParList = channelInfoService.findAllParentInfo();
		logger.info("查询一级渠道信息集合大小：" + channelInfoParList);
		// 设置到request返回对象
		request.setAttribute("channelInfoParList", channelInfoParList);

		// 查询一个一级渠道对应的二级集合
		if (channelInfoParList != null && channelInfoParList.size() > 0) {
			Long parentId = channelInfoParList.get(0).getId();
			logger.info("查询一级渠道ID：" + parentId);

			List<ChannelInfoVO> childChannelList = channelInfoService.findByParentIdOrderByCreateTimeDesc(parentId);
			logger.info("查询一级渠道对应的二级渠道集合大小为：" + childChannelList );
			request.setAttribute("childChannelList", childChannelList);
		}
	}

	/**
	 * 招标中借款列表
	 * 
	 * @param loanInfoListForm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("loanTenderingPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin> loanTenderingPage(LoanInfoListForm loanInfoListForm, Integer page, Integer rows) {

		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		return new com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin>(laonManagerServic.findLoadPage(loanInfoListForm, new PageRequest(page - 1, rows, new Sort(Direction.DESC, "releaseTime"))));
	}

	@RequestMapping("loanTenderOverPageJsp")
	public String loanTenderOverPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/loan/loanTenderOverPage";
	}

	/**
	 * 已满标借款列表
	 * 
	 * @param loanInfoListForm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("loanTenderOverPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin> loanTenderOverPage(LoanInfoListForm loanInfoListForm, Integer page, Integer rows) {

		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		return new com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin>(laonManagerServic.findLoadPage(loanInfoListForm, new PageRequest(page - 1, rows, new Sort(Direction.DESC, "releaseTime"))));
	}

	@RequestMapping("loanRepayingPageJsp")
	public String loanRepayingPageJsp(HttpServletRequest request,Model model) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String maxDate = df.format(date); // 最大日期

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.add(Calendar.DATE, 1);
		Date date2 = cal.getTime();
		String minDate = df.format(date2); // 最小日期
		String totalValue = laonManagerServic.totalAmount("", "", "", "", "");
		model.addAttribute("minDate", minDate);
		model.addAttribute("maxDate", maxDate);
		model.addAttribute("totalValue", totalValue);
		setChannelInfoToRequest(request);
		return "admin/loan/loanRepayingPage";
	}

	/**
	 * @author Ray
	 * @date 2012-12-11 上午9:51:28
	 * @param loanInfoListForm
	 * @param page
	 * @param rows
	 * @return description:还款中借款信息列表   findLoanRepay-->findLoadPage
	 */
	@RequestMapping("loanRepayingPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin> loanRepayingPage(LoanInfoListForm loanInfoListForm, Integer page, Integer rows) {

		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		return new com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin>(laonManagerServic.findLoadRepayingPage(loanInfoListForm, new PageRequest(page - 1, rows,new Sort(Direction.DESC, "releaseTime"))));
	}

	/**
	 * @author Ray
	 * @date 2012-12-3 上午10:19:01
	 * @param loanInfoListForm
	 * @return description:此方法用于统计当前查询时间段的放款值
	 */
	@RequestMapping("loanTotalAmount")
	@ResponseBody
	public String totalAmount(String loginName, String realName, String phoneNo, String interestStartMin, String interestStartMax) {
		return laonManagerServic.totalAmount(loginName, realName, phoneNo, interestStartMin, interestStartMax);
	}

	/**
	 * 已还清借款信息列表
	 * 
	 * @return
	 */
	@RequestMapping("loanRepayOverPageJsp")
	public String loanRepayOVerPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/loan/loanRepayOverPage";
	}

	/**
	 * 流标借款信息列表
	 * 
	 * @return
	 */
	@RequestMapping("loanAbortiveTenderPageJsp")
	public String loanAbortiveTenderPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/loan/loanAbortiveTenderPage";
	}

	/**
	 * 初级催收列表
	 * 
	 * @return
	 */
	@RequestMapping("loanJuniorHastenPageJsp")
	public String loanJuniorHastenPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/loan/loanJuniorHastenPage";
	}

	/**
	 * 高级催收列表
	 * 
	 * @return
	 */
	@RequestMapping("loanSeniorHastenPageJsp")
	public String loanSeniorHastenPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/loan/loanSeniorHastenPage";
	}

	/**
	 * 待审核借款信息查看
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("loanOverViewJsp")
	public String loanOverView(BigDecimal loanId, Model model) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanId);
		model.addAttribute("loanInfo", loanInfo);
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanId.longValue()));
		return "admin/loan/loanOverView";
	}

	/**
	 * 招标中借款信息查看
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("loanTenderingDetailPage")
	public String loanTenderingDetailPage(BigDecimal loanId, Model model) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanId);
		model.addAttribute("loanInfo", loanInfo);
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanId.longValue()));
		return "admin/loan/loanTenderingDetailPage";
	}

	/**
	 * 已满标借款信息查看
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("loanTenderOverDetailPage")
	public String loanTenderOverDetailPage(BigDecimal loanId, Model model, HttpServletRequest request) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanId);
		model.addAttribute("loanInfo", loanInfo);
		// 设置放款的token
		model.addAttribute("loanProess", TokenUtil.getToken(request, "loanProess"));
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanId.longValue()));
		return "admin/loan/loanTenderOverDetailPage";

	}

	/**
	 * 已还清借款信息查看
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("loanRepayOverDetailPage")
	public String loanRepayOverDetailPage(BigDecimal loanId, Model model) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanId);
		model.addAttribute("loanInfo", loanInfo);
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanId.longValue()));
		return "admin/loan/loanRepayOverDetailPage";

	}

	/**
	 * 流标借款信息查看
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("loanAbortiveTenderDetailPage")
	public String loanAbortiveTenderDetailPage(BigDecimal loanId, Model model) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanId);
		model.addAttribute("loanInfo", loanInfo);
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanId.longValue()));
		return "admin/loan/loanAbortiveTenderDetailPage";

	}

	/**
	 * 初级催收信息查看
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("loanJuniorHastenDetailPage")
	public String loanJuniorHastenDetailPage(BigDecimal loanId, Model model) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanId);
		model.addAttribute("loanInfo", loanInfo);
		model.addAttribute("loanInfoStatistics", laonManagerServic.loanInfoStatistics(loanId));
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanId.longValue()));
		return "admin/loan/loanJuniorHastenDetailPage";

	}

	/**
	 * 高级催收信息查看
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("loanSeniorHastenDetailPage")
	public String loanSeniorHastenDetailPage(BigDecimal loanId, Model model) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanId);
		model.addAttribute("loanInfo", loanInfo);
		model.addAttribute("loanInfoStatistics", laonManagerServic.loanInfoStatistics(loanId));
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanId.longValue()));
		return "admin/loan/loanSeniorHastenDetailPage";

	}

	/**
	 * 修改借款信息状态
	 * 
	 * @param loanInfoListForm
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping("alertLoanStatus")
	@ResponseBody
	public AjaxResult alertLoanStatus(LoanInfoListForm loanInfoListForm, Model model, String requestToken, HttpServletRequest request) {
		AjaxResult ajaxMessage = new AjaxResult();
		// 调用放款接口 状态修改为还款中
		if ("4".equals(loanInfoListForm.getStatus().toString())) {
			if (!TokenUtil.validateToken(request, requestToken, "loanProess")) {
				ajaxMessage.setMsg("放款失败，当前借款已放款或者刷新页面重试！");
				ajaxMessage.setSuccess(false);
				return ajaxMessage;
			}
			String processFlag = loanprocessSupportService.loanProcess(loanInfoListForm.getLoanId().longValue());
			if (processFlag != null) {
				ajaxMessage.setMsg(processFlag);
				ajaxMessage.setSuccess(false);
				return ajaxMessage;
			}
		}
		// 审核通过 新建贷款分户信息
		if ("1".equals(loanInfoListForm.getStatus().toString())) {
			LoanInfoAdmin loanInfo = laonManagerServic.aduitPassLoan(loanInfoListForm.getLoanId());
			if (loanInfo == null) {
				ajaxMessage.setMsg("已经通过审核，不可重复操作！");
				ajaxMessage.setSuccess(false);
				return ajaxMessage;
			}
		}
		if ("3".equals(loanInfoListForm.getStatus().toString())) {
			LoanInfoAdminVO loanInfo = loanprocessSupportService.abortiveTenderHandle(loanInfoListForm.getLoanId().longValue());
			if (loanInfo == null) {
//				ajaxMessage.setMsg("借款已经流标，不可重复操作！");
//				ajaxMessage.setSuccess(false);
				return ajaxMessage;
			}
			if (!"3".equals(loanInfo.getStatus().toString())) {
				ajaxMessage.setMsg("借款类型不可流标，请与管理员联系！");
				ajaxMessage.setSuccess(false);
				return ajaxMessage;
			}
			loanprocessSupportService.sendPhoneMessageToborrowerAndInvest(loanInfo, 0);
			return new AjaxResult();
		}
		laonManagerServic.alertLoanStatus(loanInfoListForm.getLoanId(), loanInfoListForm.getStatus());
		return new AjaxResult();
	}

	/**
	 * 查询借款信息投资列表
	 * 
	 * @param loanInfoListForm
	 * @return
	 */
	@RequestMapping("seachInvestinfo")
	@ResponseBody
	public List<InvestInfoAdmin> seachInvestinfo(LoanInfoListForm loanInfoListForm) {

		return laonManagerServic.seachInverstInfo(loanInfoListForm.getLoanId());

	}

	/**
	 * 根据当前期数查询借款信息投资列表
	 * 
	 * @param loanInfoListForm
	 * @return
	 */
	@RequestMapping("seachInvestinfoByNum")
	@ResponseBody
	public List<InvestInfoAdmin> seachInvestinfoByNum(LoanInfoListForm loanInfoListForm, Long num) {
		return laonManagerServic.seachInverstInfo(loanInfoListForm.getLoanId(), num);

	}

	/**
	 * 查询借款信息备注
	 * 
	 * @param loanInfoListForm
	 * @return
	 */
	@RequestMapping("seachLoanNote")
	@ResponseBody
	public List<LoanNoteAdmin> seachLoanNote(LoanInfoListForm loanInfoListForm) {
		return laonManagerServic.seachLoanNote(loanInfoListForm.getLoanId());

	}

	/**
	 * 保存借款备注信息
	 * 
	 * @param loanNoteListForm
	 */
	@RequestMapping("saveLoanNote")
	@ResponseBody
	public AjaxResult saveLoanNote(LoanNoteListForm loanNoteListForm) {
		laonManagerServic.saveLoanNote(loanNoteListForm);
		return new AjaxResult();
	}

	@RequestMapping("loanRepayingDetailPage")
	public String loanRepayingDetailPage(LoanInfoListForm loanInfoListForm, Model model) {
		LoanInfoAdmin loanInfo = laonManagerServic.findByLoanId(loanInfoListForm.getLoanId());
		model.addAttribute("loanInfo", loanInfo);
		ChannelInfoVO channel = loanInfo.getAccountUsers().getChannelInfo();
		if (channel != null) {
			String firstName = channelInfoService.cacheMapForParentData(channel.getParentId());
			String secondName = channel.getName();
			model.addAttribute("channelInfo", firstName + ">>" + secondName);
		}

		model.addAttribute("rateInfo", rateService.getLoanCoRate(loanInfoListForm.getLoanId().longValue()));
		return "admin/loan/loanRepayingDetailPage";
	}

	/**
	 * 借款信息——还款列表
	 * 
	 * @param loanInfoListForm
	 * @return
	 */
	@RequestMapping("seachLoanRepayList")
	@ResponseBody
	public List<AcTVirtualCashFlowAdmin> seachLoanRepayList(LoanInfoListForm loanInfoListForm) {
		return laonManagerServic.SearchLoanRepayList(loanInfoListForm.getLoanId());
	}

	/**
	 * 借款信息共用方法 根据传入的status返回不同状态的借款列表
	 * 
	 * @param loanInfoListForm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("loanInfoPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin> loanInfoPage(LoanInfoListForm loanInfoListForm, Integer page, Integer rows) {

		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		return new com.zendaimoney.online.admin.vo.Page<LoanInfoAdmin>(laonManagerServic.findLoadPage(loanInfoListForm, new PageRequest(page - 1, rows, new Sort(Direction.DESC, "releaseTime"))));
	}

	// 查看借款协议范本
	@RequestMapping(value = "showContract")
	public String showContract(@RequestParam("loanId") BigDecimal loanId, Model model) {
		BorrowContractVO borrowContractVO = contractManager.showBorrowContract(loanId);
		model.addAttribute("borrowContractVO", borrowContractVO);
		return "/admin/loan/showBorrowContract";
	}

	@RequestMapping(value = "showRiskContract")
	public String showRiskContract(@RequestParam("loanId") BigDecimal loanId, Model model) {
		BorrowContractVO borrowContractVO = contractManager.showBorrowContract(loanId);
		model.addAttribute("borrowContractVO", borrowContractVO);
		return "/admin/loan/showRiskFundContract";
	}

	/**
	 * 债权收购
	 * 
	 * @param investId
	 *            理财信息id
	 * @param loanId
	 *            借款信息id
	 * @return
	 */
	@RequestMapping("claimsAcquisition")
	@ResponseBody
	public AjaxResult claimsAcquisition(@RequestParam("investId") Long investId, @RequestParam("num") Long num, @RequestParam("acTVirtualCashFlowId") Long acTVirtualCashFlowId) {
		// loanManagementManager.acquisitionProgress(investId, num);
		loanprocessSupportService.acquisitionProgress(acTVirtualCashFlowId, investId, num);
		return new AjaxResult();
	}

	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-25 下午3:53:35
	 * @param req
	 * @param rep
	 * @return
	 * @throws IOException
	 *             description:获取图片验证
	 */
	@RequestMapping(value = "getValidatorImg")
	public String getValidatorImg(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		return "/admin/loan/randomCode";
	}

	@RequestMapping("imgValidate")
	@ResponseBody
	public AjaxResult imgValidate(HttpServletRequest request, @RequestParam("validateCode") String validateCode) {
		AjaxResult result = new AjaxResult();
		if (!ValidateCode.CodeValidate(request, "randomCode", validateCode)) {
			result.setSuccess(false);
		}
		return result;
	}

	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}
}
