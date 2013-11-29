package com.zendaimoney.online.web.borrowing;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.common.FileUploadUtil;
import com.zendaimoney.online.entity.account.AccountUsers;
import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoJob;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.financial.FinancialUsers;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;
import com.zendaimoney.online.service.borrowing.ReleaseLoanManager;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.service.financial.BeLenderManager;
import com.zendaimoney.online.service.pay.PayManager;
import com.zendaimoney.online.vo.borrowing.BorrowingProCheckVO;
import com.zendaimoney.online.vo.borrowing.BorrowingProVO;

@Controller
@RequestMapping(value = "/borrowing/borrowing/")
public class InfoApproveController {

	private static Logger logger = LoggerFactory.getLogger(InfoApproveController.class);
	@Autowired
	private InfoApproveManager infoApproveManager;
	@Autowired
	private ReleaseLoanManager releaseLoanManager;
	@Autowired
	private PayManager payManager;
	@Autowired
	private BeLenderManager beLenderManager;
	@Autowired
	private RateCommonUtil rateCommon;

	// 点击e贷款认证
	@RequestMapping(value = "showUpdataInfo")
	public String showUpdataInfo(HttpServletRequest request, Model model) {
		BorrowingUsers user = infoApproveManager.getUsers(request);
		if (user == null) {
			return "redirect:/accountLogin/login/show";
		} else {
			BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(request);
			model.addAttribute("creditNote", creditNote);
			model.addAttribute("currUser", releaseLoanManager.getUsersByUserId(request));
			model.addAttribute("availableAmount", releaseLoanManager.getAvailableAmount(request));
			model.addAttribute("creditAmount", releaseLoanManager.getUserCreditAmount(request));
			if ("yes".equals(releaseLoanManager.isReleaseLoanInfo(request))) {
				model.addAttribute("loanInfoList", releaseLoanManager.getLoanInfoList(request));
				return "/releaseLoanDetail/ongoingloanInfo";
			} else {
				// 可用额度
				BigDecimal kyed = infoApproveManager.showAvailableMoney(request);
				BorrowingProVO appro = infoApproveManager.getUserAppro(user.getUserId());
				model.addAttribute("appro", appro);
				model.addAttribute("kyed", kyed);
				return "/borrowing/infoApprove";
			}
		}
	}

	// 头部跳转
	@RequestMapping(value = "borrowingHead")
	public String borrowingHead(HttpServletRequest request, Model model, @RequestParam("strUrl") String strUrl) {
		if (strUrl.equals("updataImg")) {
			// 上传资料
			return "";
		} else if (strUrl.equals("getLimit")) {
			// 获取额度
			return "";
		} else {
			// 发布借款 releaseLoan
			return "redirect:/borrowing/releaseLoan/getShow";
		}

	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-22 下午3:26:51
	 * @param req
	 * @param model
	 * @return description:上传资料界面
	 */
	@RequestMapping("showApprove")
	public String showApprove(HttpServletRequest req, Model model, HttpServletResponse rep) {
		BorrowingUsers user = infoApproveManager.getUsers(req);
		try {
			if (user != null) {
				model.addAttribute("userInfos", user);
			}
			BorrowingProCheckVO userPro = infoApproveManager.userProCheck(req);
			BorrowingProCheckVO userProStatus = infoApproveManager.userProStatus(req);
			BorrowingProVO appro = infoApproveManager.getUserAppro(user.getUserId());
			BorrowingUserInfoJob userJobInfo = infoApproveManager.showJobInfo(req);
			model.addAttribute("appro", appro);
			model.addAttribute("userPro", userPro);
			model.addAttribute("userProStatus", userProStatus);
			model.addAttribute("jobInfo", userJobInfo);

			setIdCardAttribute(req, model);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String flg = infoApproveManager.releaseLoan(req);
		// 当用户发布的某个借款处于在途借款，则不可以发布借款，借款列表中状态为“待审核” or “招标中”
		if ("yes".equals(releaseLoanManager.isReleaseLoanInfo(req))) {
			model.addAttribute("loanInfoList", releaseLoanManager.getLoanInfoList(req));
			return "/releaseLoanDetail/ongoingloanInfo";
		} else if (flg.equals("lock")) {
			BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(req);
			model.addAttribute("creditNote", creditNote);
			model.addAttribute("currUser", releaseLoanManager.getUsersByUserId(req));
			model.addAttribute("availableAmount", releaseLoanManager.getAvailableAmount(req));
			model.addAttribute("creditAmount", releaseLoanManager.getUserCreditAmount(req));
			// 可用额度
			BigDecimal kyed = infoApproveManager.showAvailableMoney(req);
			BorrowingProVO appro = infoApproveManager.getUserAppro(user.getUserId());
			model.addAttribute("appro", appro);
			model.addAttribute("kyed", kyed);
			model.addAttribute("showMsg", "lock");
			return "/borrowing/infoApprove";
		} else if (flg.equals("report")) {
			BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(req);
			model.addAttribute("creditNote", creditNote);
			model.addAttribute("currUser", releaseLoanManager.getUsersByUserId(req));
			model.addAttribute("availableAmount", releaseLoanManager.getAvailableAmount(req));
			model.addAttribute("creditAmount", releaseLoanManager.getUserCreditAmount(req));
			// 可用额度
			BigDecimal kyed = infoApproveManager.showAvailableMoney(req);
			BorrowingProVO appro = infoApproveManager.getUserAppro(user.getUserId());
			model.addAttribute("appro", appro);
			model.addAttribute("kyed", kyed);
			model.addAttribute("showMsg", "report");
			return "/borrowing/infoApprove";
		} else {
			return "/borrowing/infoApproveBorrow";
		}
	}

	private void setIdCardAttribute(HttpServletRequest req, Model model) {
		// 身份验证
		boolean flg = checkIdCard(req);
		model.addAttribute("checkIdCard", flg);
		if (!flg) {
			// 未验证
			FinancialUsers currUser = beLenderManager.getCurrentUser(req, null);
			// 根据渠道获取对应的费率的ID5验证费用 modify by jihui 2013-03-21
			Long channelId = currUser.getChannelInfoId();
			double idFee = rateCommon.getId5Fee(channelId);
			if (beLenderManager.getAvailableBalance(currUser) < idFee) {
				model.addAttribute("accountBanance", true);
				model.addAttribute("idFee", idFee);
			}
		} else {
			HttpSession session = req.getSession();
			BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
			// 身份已经验证，查询手机是否验证
			boolean flag = payManager.checkUserPhoneByUserId(userid);
			model.addAttribute("checkUserPhoneByUserId", flag);
		}
	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-22 下午3:26:34
	 * @param req
	 * @param model
	 * @return description:获取信用额度界面
	 */
	@RequestMapping("getCreditLimitJsp")
	public String getCreditLimitJsp(HttpServletRequest req, Model model) {
		try {
			BorrowingUsers user = infoApproveManager.getUsers(req);
			if (user != null) {
				model.addAttribute("userInfos", user);
			} else {
				model.addAttribute("userInfos", null);
			}
			BorrowingProCheckVO userPro = infoApproveManager.userProCheck(req);
			BorrowingProCheckVO userProStatus = infoApproveManager.userProStatus(req);
			BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(req);
			BorrowingProVO appro = infoApproveManager.getUserAppro(user.getUserId());
			// 可用额度
			BigDecimal kyed = infoApproveManager.showAvailableMoney(req);

			model.addAttribute("appro", appro);
			model.addAttribute("userPro", userPro);
			model.addAttribute("userProStatus", userProStatus);
			model.addAttribute("creditNote", creditNote);
			model.addAttribute("kyed", kyed);
		} catch (Exception e) {

		}
		return "/borrowing/creditLimit";
	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-22 下午3:27:12
	 * @param req
	 * @param model
	 * @return description:发布借款界面
	 */
	@RequestMapping("publishLoanJsp")
	public String publishLoanJsp(HttpServletRequest req, Model model) {
		try {
			BorrowingUsers user = infoApproveManager.getUsers(req);
			if (user != null) {
				model.addAttribute("userInfos", user);
			} else {
				model.addAttribute("userInfos", null);
			}
			BorrowingProCheckVO userPro = infoApproveManager.userProCheck(req);
			BorrowingProCheckVO userProStatus = infoApproveManager.userProStatus(req);
			BorrowingUserCreditNote creditNote = infoApproveManager.showCreditInfo(req);
			BorrowingProVO appro = infoApproveManager.getUserAppro(user.getUserId());
			model.addAttribute("availableAmount", releaseLoanManager.getAvailableAmount(req));
			model.addAttribute("creditAmount", releaseLoanManager.getUserCreditAmount(req));
			// 可用额度
			BigDecimal kyed = infoApproveManager.showAvailableMoney(req);
			model.addAttribute("appro", appro);
			model.addAttribute("userPro", userPro);
			model.addAttribute("userProStatus", userProStatus);
			model.addAttribute("creditNote", creditNote);
			model.addAttribute("kyed", kyed);
			model.addAttribute("borrowingLoanInfo", releaseLoanManager.getReleaseLoanInfo(req));
			String flg = infoApproveManager.releaseLoan(req);
			if ("yes".equals(infoApproveManager.isReleaseLoanInfo(req))) {
				model.addAttribute("loanInfoList", infoApproveManager.getLoanInfoList(req));
				return "/releaseLoanDetail/ongoingloanInfo";
			} else {
				if (flg.equals("releaseLoan")) {
					// 跳转到发布借款
					return "/borrowing/publishLoan";
				} else if (flg.equals("updataImg")) {
					setIdCardAttribute(req, model);
					BorrowingUserInfoJob userJobInfo = infoApproveManager.showJobInfo(req);
					model.addAttribute("jobInfo", userJobInfo);
					// 跳转到上传资料
					return "/borrowing/infoApproveBorrow";
				} else if (flg.equals("inAudit")) {
					model.addAttribute("showMsg", "true");
					return "/borrowing/publishLoan";
				} else if (flg.equals("lock")) {
					model.addAttribute("showMsg", "lock");
					return "/borrowing/infoApprove";
				} else if (flg.equals("report")) {
					model.addAttribute("showMsg", "report");
					return "/borrowing/infoApprove";
				} else if (flg.equals("noLimit")) {
					model.addAttribute("showMsg", "noLimit");
					return "/borrowing/publishLoan";
				} else {
					return "/borrowing/infoApproveBorrow";
				}
			}
		} catch (Exception e) {

		}
		return "redirect:/accountLogin/login/show";
	}

	// @RequestMapping(value = "showIdentity")
	// public String showIdentity() {
	// return "/borrowing/identity";
	// }

	// 上传头像
	@RequestMapping(value = "updataHeadPhoto")
	public String updataHeadPhoto(HttpServletRequest request) {
		String flag = "";
		try {
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			// 上传文件
			List<FileItem> items = FileUploadUtil.uploadFile(request, FileUploadUtil.FILEMAXSIZ_1_5M.longValue());
			logger.info("获取上传文件信息：" + (items != null ? items.size() : "上传文件为空！"));

			// 验证文件未空的情况
			flag = FileUploadUtil.checkFileSize(items);
			if (flag != null && !flag.equals("")) {
				flag = infoApproveManager.updataHeadPhoto(items, user);
				return "redirect:/head/showheadImg?flag=" + flag;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		// model.addAttribute("baseVO",path);
		return "redirect:/head/showheadImg?flag=" + flag;
	}

	/**
	 * 
	 * 2012-11-21 下午4:24:08 by HuYaHui
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private BorrowingUsers getUsers(HttpServletRequest request) throws Exception {
		javax.servlet.http.HttpSession session = request.getSession();
		AccountUsers currUser = (AccountUsers) session.getAttribute("curr_login_user");
		if (currUser == null || currUser.getUserId() == null || currUser.getUserId().equals("") || currUser.getEmail().equals("") || currUser.getEmail() == null) {
			BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
			BorrowingUsers userInfo = infoApproveManager.getUsers(userid);
			return userInfo;
		} else {
			BorrowingUsers userInfo = new BorrowingUsers();
			userInfo.setUserId(currUser.getUserId());
			userInfo.setLoginName(currUser.getLoginName());
			userInfo.setEmail(currUser.getEmail());
			return userInfo;
		}
	}

	/**
	 * 验证身份证是否验证成功 2012-12-20 下午4:35:17 by HuYaHui
	 * 
	 * @return 成功true|失败false
	 */
	private boolean checkIdCard(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		if (userid == null) {
			return false;
		}
		boolean flag = payManager.checkIdCard(userid);
		return flag;
	}
}
