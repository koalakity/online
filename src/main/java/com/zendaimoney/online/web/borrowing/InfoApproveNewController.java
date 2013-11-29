package com.zendaimoney.online.web.borrowing;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.common.Constants;
import com.zendaimoney.online.entity.account.AccountUsers;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoJob;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.upload.FileUploadVO;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;
import com.zendaimoney.online.service.borrowing.InfoApproveNewManager;
import com.zendaimoney.online.service.borrowing.ReleaseLoanManager;
import com.zendaimoney.online.service.upload.FileUploadManager;

@Controller
@RequestMapping(value = "/infoApproveNew/")
public class InfoApproveNewController {
	private static Logger logger = LoggerFactory.getLogger(InfoApproveNewController.class);
	@Autowired
	private InfoApproveNewManager borrowingManager;
	@Autowired
	private FileUploadManager fileUploadManager;
	@Autowired
	private ReleaseLoanManager releaseLoanManager;

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-21 上午10:29:42
	 * @param req
	 * @param model
	 * @return description: 跳出上传身份认证，工作，信用，收入的弹出层
	 */
	@RequestMapping("showIdentity")
	public String showIdentity(HttpServletRequest req, Model model, String way) {
		try {
			BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(getUsers(req).getUserId());
			int count = fileUploadManager.update(getUsers(req).getUserId() + "", Constants.FILEUPLOAD_TYPE_SFZ);
			logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);
			if (userInfo != null) {
				model.addAttribute("userInfo", userInfo);
			} else {
				model.addAttribute("userInfo", null);
			}
			req.setAttribute("type", Constants.FILEUPLOAD_TYPE_SFZ);
			StringBuilder sb = new StringBuilder(10);
			if (way.equals("0")) {
				sb = getUserFile(req, getUsers(req).getUserId() + "", BigDecimal.ONE);
			}
			model.addAttribute("sb", sb);
			model.addAttribute("way", way);
		} catch (Exception e) {

		}
		return "/borrowing/identity";
	}

	@RequestMapping("showWorkAuthJsp")
	public String workAuthJsp(HttpServletRequest req, Model model, String way) {
		try {
			BorrowingUserInfoJob userJobInfo = borrowingManager.showJobInfo(getUsers(req).getUserId());
			int count = fileUploadManager.update(getUsers(req).getUserId() + "", Constants.FILEUPLOAD_TYPE_GZRZ);
			logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);
			model.addAttribute("jobInfo", userJobInfo);
			req.setAttribute("type", Constants.FILEUPLOAD_TYPE_GZRZ);
			StringBuilder sb = new StringBuilder(10);
			if (way.equals("0")) {
				sb = getUserFile(req, getUsers(req).getUserId() + "", BigDecimal.valueOf(2));
			}
			model.addAttribute("sb", sb);
			model.addAttribute("way", way);
		} catch (Exception e) {

		}
		return "/borrowing/work";
	}

	@RequestMapping("showCreditAuthJsp")
	public String creditAuthJsp(HttpServletRequest req, Model model, String way) throws Exception {
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(req, getUsers(req).getUserId() + "", BigDecimal.valueOf(4));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		req.setAttribute("type", Constants.FILEUPLOAD_TYPE_XYBG);
		return "/borrowing/credit";
	}

	@RequestMapping("showIncomeAuthJsp")
	public String incomeAuthJsp(HttpServletRequest req, Model model, String way) {
		try {
			BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(getUsers(req).getUserId());
			int count = fileUploadManager.update(getUsers(req).getUserId() + "", Constants.FILEUPLOAD_TYPE_YSR);
			logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);
			if (userInfo != null) {
				model.addAttribute("userInfo", userInfo);
			} else {
				model.addAttribute("userInfo", null);
			}
			req.setAttribute("type", Constants.FILEUPLOAD_TYPE_YSR);
			StringBuilder sb = new StringBuilder(10);
			if (way.equals("0")) {
				sb = getUserFile(req, getUsers(req).getUserId() + "", BigDecimal.valueOf(3));
			}
			model.addAttribute("sb", sb);
			model.addAttribute("way", way);
		} catch (Exception e) {

		}

		return "/borrowing/income";
	}

	@RequestMapping("showInfoAnnounceJsp")
	public String showInfoAnnounceJsp(HttpServletRequest req, Model model) {
		try {
			BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(getUsers(req).getUserId());
			if (userInfo != null) {
				model.addAttribute("userInfo", userInfo);
			} else {
				model.addAttribute("userInfo", null);
			}
			model.addAttribute("borrowingLoanInfo", releaseLoanManager.getReleaseLoanInfo(req));
			req.setAttribute("type", Constants.FILEUPLOAD_TYPE_SFZ);
		} catch (Exception e) {

		}
		return "/borrowing/infoAnnounce";
	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-21 下午1:36:11
	 * @param userInfo
	 * @param model
	 * @param req
	 * @return description:提交身份证，工作证，信用报告和收入信息
	 * @throws Exception
	 */
	@RequestMapping("updataIndenty")
	@ResponseBody
	public String updataIndenty(@ModelAttribute("userInfo") BorrowingUserInfoPerson userInfo, Model model, HttpServletRequest req) throws Exception {
		try {
			BorrowingUsers user = getUsers(req);
			String flag = borrowingManager.updataIndenty(userInfo, user);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping("updataWork")
	@ResponseBody
	public String updataWork(@ModelAttribute("jobInfo") BorrowingUserInfoJob jobInfo, HttpServletRequest req) throws Exception {
		try {
			BorrowingUsers user = getUsers(req);
			String flag = borrowingManager.updataWork(jobInfo, user);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping("updataCredit")
	@ResponseBody
	public String updataCredit(HttpServletRequest req) throws Exception {
		try {
			BorrowingUsers user = getUsers(req);
			String flag = borrowingManager.updataCredit(user);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping("updataIncome")
	@ResponseBody
	public String updataIncome(@RequestParam("monthIncome") String monthIncome, HttpServletRequest req) throws Exception {
		try {
			BorrowingUsers user = getUsers(req);
			String flag = borrowingManager.updataIncome(monthIncome, user);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping("submitInfo")
	@ResponseBody
	public BorrowingLoanInfo submitInfo(@ModelAttribute("loanInfo") BorrowingLoanInfo loanInfo, Model model) {
		model.addAttribute("loanInfo", loanInfo);
		return loanInfo;
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
			BorrowingUsers userInfo = borrowingManager.getUsers(userid);
			return userInfo;
		} else {
			BorrowingUsers userInfo = new BorrowingUsers();
			userInfo.setUserId(currUser.getUserId());
			userInfo.setLoginName(currUser.getLoginName());
			userInfo.setEmail(currUser.getEmail());
			return userInfo;
		}
	}

	private StringBuilder getUserFile(HttpServletRequest req, String uerId, BigDecimal proId) throws Exception {
		StringBuilder sb = new StringBuilder(10);
		BorrowingUserApprove approveId = borrowingManager.getApproveId(getUsers(req).getUserId(), proId);
		List<FileUploadVO> fileList = fileUploadManager.findRecordByCondition(getUsers(req).getUserId() + "", proId + "", null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YTJ, null, approveId, null);
		List<FileUploadVO> fileListYsc = fileUploadManager.findRecordByCondition(getUsers(req).getUserId() + "", proId + "", null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YSC, null, approveId, null);
		List<FileUploadVO> fileListYsh = fileUploadManager.findRecordByCondition(getUsers(req).getUserId() + "", proId + "", null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YSH, null, approveId, null);
		fileList.addAll(fileListYsc);
		fileList.addAll(fileListYsh);
		for (FileUploadVO file : fileList) {
			String files = file.getRemark();
			sb.append(files);
			sb.append(";");
		}
		return sb;
	}

	private static String getFilePath() {
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = InfoApproveManager.class.getResourceAsStream("/filePath.properties");
			props.load(in);
			in.close();
			String value = props.getProperty("filePath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
