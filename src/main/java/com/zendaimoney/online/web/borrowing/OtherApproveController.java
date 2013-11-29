package com.zendaimoney.online.web.borrowing;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.common.Constants;
import com.zendaimoney.online.entity.account.AccountUsers;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.upload.FileUploadVO;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;
import com.zendaimoney.online.service.borrowing.InfoApproveNewManager;
import com.zendaimoney.online.service.borrowing.OtherApproveManager;
import com.zendaimoney.online.service.upload.FileUploadManager;

/*
 * 可选信用认证
 */

@Controller
@RequestMapping(value = "/borrowing/borrowing/")
public class OtherApproveController {
	private static Logger logger = LoggerFactory.getLogger(OtherApproveController.class);

	@Autowired
	private OtherApproveManager otherApproveManager;
	@Autowired
	private InfoApproveManager infoApproveManager;
	@Autowired
	private InfoApproveNewManager borrowingManager;
	@Autowired
	private FileUploadManager fileUploadManager;

	// 房产认证
	@RequestMapping(value = "updataHouseProperty")
	@ResponseBody
	public String updataHouseProperty(HttpServletRequest request, BorrowingUserInfoPerson userInfoPerson) throws Exception {
		try {
			long start = System.currentTimeMillis();
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));
			//设置有房产
			userInfoPerson.setIsapproveHaveHouse(BigDecimal.ONE);
			//如果已经购买完毕，设置按揭为0
			if(userInfoPerson.getIsapproveHouseMortgage()!=null && userInfoPerson.getIsapproveHouseMortgage().compareTo(BigDecimal.ZERO)==0){
				userInfoPerson.setHouseMonthMortgage(BigDecimal.ZERO);
			}
			String rtnStr = otherApproveManager.updataCommonExcute(userInfoPerson, user.getUserId(), Constants.FILEUPLOAD_TYPE_FCRZ);
			logger.info(rtnStr + "结束上传房产认证,action总耗时：" + (System.currentTimeMillis() - start));
			return rtnStr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	// 汽车认证
	@RequestMapping(value = "updataHaveCar")
	@ResponseBody
	public String updataHaveCar(HttpServletRequest request, BorrowingUserInfoPerson userInfoPerson) throws Exception {
		try {
			long start = System.currentTimeMillis();
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			//如果已经购买完毕，设置按揭为0
			if(userInfoPerson.getIsapproveCarMortgage()!=null && userInfoPerson.getIsapproveCarMortgage().compareTo(BigDecimal.ZERO)==0){
				userInfoPerson.setCarMonthMortgage(BigDecimal.ZERO);
			}
			
			String rtnStr = otherApproveManager.updataCommonExcute(userInfoPerson, user.getUserId(), Constants.FILEUPLOAD_TYPE_QCRZ);
			logger.info(rtnStr + "结束汽车认证,action总耗时：" + (System.currentTimeMillis() - start));
			return rtnStr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	// 结婚认证
	@RequestMapping(value = "updataMarryApprove")
	@ResponseBody
	public String updataMarryApprove(HttpServletRequest request, BorrowingUserInfoPerson userInfoPerson) throws Exception {
		try {
			long start = System.currentTimeMillis();
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			String rtnStr = otherApproveManager.updataCommonExcute(userInfoPerson, user.getUserId(), Constants.FILEUPLOAD_TYPE_JHRZ);
			logger.info(rtnStr + "结束结婚认证,action总耗时：" + (System.currentTimeMillis() - start));
			return rtnStr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	// 房产认证
	@RequestMapping(value = "updataHousePropertyJsp")
	public String updataHousePropertyJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();

		int count = fileUploadManager.update(userId + "", Constants.FILEUPLOAD_TYPE_FCRZ);
		logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);

		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		request.setAttribute("type", Constants.FILEUPLOAD_TYPE_FCRZ);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(5));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/fangchan";
	}

	// 汽车认证
	@RequestMapping(value = "updataHaveCarJsp")
	public String updataHaveCarJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();

		int count = fileUploadManager.update(userId + "", Constants.FILEUPLOAD_TYPE_QCRZ);
		logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);

		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		request.setAttribute("type", Constants.FILEUPLOAD_TYPE_QCRZ);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(7));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/gouche";
	}

	// 结婚认证
	@RequestMapping(value = "updataMarryApproveJsp")
	public String updataMarryApproveJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();

		int count = fileUploadManager.update(userId + "", Constants.FILEUPLOAD_TYPE_JHRZ);
		logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);

		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		request.setAttribute("type", Constants.FILEUPLOAD_TYPE_JHRZ);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(8));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/jiehun";
	}

	// 学历认证
	@RequestMapping(value = "updataDegeeApproveJsp")
	public String updataDegeeApproveJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();
		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(12));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/xueli";
	}

	// 学历认证
	@RequestMapping(value = "updataDegeeApprove")
	@ResponseBody
	public String updataDegeeApprove(@RequestParam("degreeNo") String degreeNo, @RequestParam("maxDegree") String maxDegree, @RequestParam("graduatSchool") String graduatSchool, @RequestParam("inschoolTime") String inschoolTime, @RequestParam("degreeToken") String degreeToken, HttpServletRequest request) {
		String flag = "false";
		try {
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			flag = otherApproveManager.updataDegeeApprove(degreeNo, maxDegree, graduatSchool, inschoolTime, degreeToken, request, user);
			logger.info("结束学历认证,manager返回结果：" + flag);
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
		}
		return flag;
	}

	// 居住地认证
	@RequestMapping(value = "updataLiveAddressApprove")
	@ResponseBody
	public String updataLiveAddressApprove(HttpServletRequest request, BorrowingUserInfoPerson userInfoPerson) throws Exception {
		try {
			long start = System.currentTimeMillis();
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			String rtnStr = otherApproveManager.updataCommonExcute(userInfoPerson, user.getUserId(), Constants.FILEUPLOAD_TYPE_JZDRZ);
			logger.info(rtnStr + "结束居住地认证,action总耗时：" + (System.currentTimeMillis() - start));
			return rtnStr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	// 居住地认证
	@RequestMapping(value = "updataLiveAddressApproveJsp")
	public String updataLiveAddressApprove(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();

		int count = fileUploadManager.update(userId + "", Constants.FILEUPLOAD_TYPE_JZDRZ);
		logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);

		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		request.setAttribute("type", Constants.FILEUPLOAD_TYPE_JZDRZ);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(9));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/juzhudi";
	}

	// 职称认证
	@RequestMapping(value = "updatajobTitleApproveJsp")
	public String updatajobTitleApproveJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();

		int count = fileUploadManager.update(userId + "", Constants.FILEUPLOAD_TYPE_JCRZ);
		logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);

		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		request.setAttribute("type", Constants.FILEUPLOAD_TYPE_JCRZ);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(6));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/zhicheng";
	}

	// 职称认证
	@RequestMapping(value = "updatajobTitleApprove")
	@ResponseBody
	public String updatajobTitleApprove(HttpServletRequest request, BorrowingUserInfoPerson userInfoPerson) throws Exception {
		try {
			long start = System.currentTimeMillis();
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			if(userInfoPerson.getJobTitleTwo()==null || userInfoPerson.getJobTitleTwo().equals("")){
				userInfoPerson.setJobTitleTwo(" ");
			}
			String rtnStr = otherApproveManager.updataCommonExcute(userInfoPerson, user.getUserId(), Constants.FILEUPLOAD_TYPE_JCRZ);
			logger.info(rtnStr + "结束职称认证,action总耗时：" + (System.currentTimeMillis() - start));
			return rtnStr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	// 视频认证
	@RequestMapping(value = "updataVideoApproveJsp")
	public String updataVideoApproveJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();
		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(10));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/shipin";
	}

	// 视频认证
	@RequestMapping(value = "updataVideoApprove")
	@ResponseBody
	public String updataVideoApprove(@RequestParam("VideoApprove") String videoApprove, @RequestParam("VideoToken") String VideoToken, HttpServletRequest request) {
		String flag = "false";
		try {
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			flag = otherApproveManager.updataVideoApprove(videoApprove, VideoToken, request, user);
			logger.info("结束上传视频认证 ,manager返回结果：" + flag);
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
		}
		return flag;
	}

	// 手机认证
	@RequestMapping(value = "updataPhoneApproveJsp")
	public String updataPhoneApproveJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();

		int count = fileUploadManager.update(userId + "", Constants.FILEUPLOAD_TYPE_SJRZ);
		logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);

		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		request.setAttribute("type", Constants.FILEUPLOAD_TYPE_SJRZ);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(13));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/shouji";
	}

	// 手机认证
	@RequestMapping(value = "updataPhoneApprove")
	@ResponseBody
	public String updataPhoneApprove(HttpServletRequest request, BorrowingUserInfoPerson userInfoPerson) throws Exception {
		try {
			long start = System.currentTimeMillis();
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			String rtnStr = otherApproveManager.updataCommonExcute(userInfoPerson, user.getUserId(), Constants.FILEUPLOAD_TYPE_SJRZ);
			logger.info(rtnStr + "结束手机认证,action总耗时：" + (System.currentTimeMillis() - start));
			return rtnStr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
			throw e;
		}
	}

	// 新浪微博认证
	@RequestMapping(value = "updataWeiBoApproveJsp")
	public String updataWeiBoApproveJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();
		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(14));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/weibo";
	}

	// 新浪微博认证
	@RequestMapping(value = "updataWeiBoApprove")
	@ResponseBody
	public String updataWeiBoApprove(@RequestParam("sinaWeiboAccount") String sinaWeiboAccount, @RequestParam("sinaWeiboToken") String sinaWeiboToken, HttpServletRequest request) {
		String flag = "false";
		try {
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));
			flag = otherApproveManager.updataWeiBoApprove(sinaWeiboAccount, sinaWeiboToken, request, user);
			logger.info("结束上传新浪微博认证,manager返回结果：" + flag);
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
		}
		return flag;
	}

	// 实地考察认证
	@RequestMapping(value = "updataRealInspectApproveJsp")
	public String updataRealInspectApproveJsp(HttpServletRequest request, Model model, String way) throws Exception {
		BigDecimal userId = getUsers(request).getUserId();
		BorrowingUserInfoPerson userInfo = borrowingManager.showInfo(userId);
		model.addAttribute("userInfo", userInfo);
		StringBuilder sb = new StringBuilder(10);
		if (way.equals("0")) {
			sb = getUserFile(request, getUsers(request).getUserId() + "", BigDecimal.valueOf(11));
		}
		model.addAttribute("sb", sb);
		model.addAttribute("way", way);
		return "/borrowing/shidikaocha";
	}

	// 实地考察认证
	@RequestMapping(value = "updataRealInspectApprove")
	@ResponseBody
	public String updataRealInspectApprove(@RequestParam("realApproveName") String realApproveName, @RequestParam("realApprovePhoneArea") String realApprovePhoneArea, @RequestParam("realApprovePhone") String realApprovePhone, @RequestParam("realApproveCorporationAdd") String realApproveCorporationAdd, @RequestParam("realApproveHomeAddress") String realApproveHomeAddress,
			@RequestParam("realApproveAssets") String realApproveAssets, @RequestParam("realApproveOperateTime") String realApproveOperateTime, @RequestParam("realApproveMonthWater") String realApproveMonthWater, @RequestParam("realApproveApplyMoney") String realApproveApplyMoney, @RequestParam("realApproveToken") String realApproveToken, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String flag = "false";

		try {
			// 1.获取登陆用户信息
			BorrowingUsers user = getUsers(request);
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));

			flag = otherApproveManager.updataRealInspectApprove(realApproveName, realApprovePhoneArea, realApprovePhone, realApproveCorporationAdd, realApproveHomeAddress, realApproveAssets, realApproveOperateTime, realApproveMonthWater, realApproveApplyMoney, realApproveToken, request, user);
			logger.info("结束上传实地考察认证,manager返回结果：" + flag);
		} catch (Exception ex) {
			logger.error(ex.toString(), ex);
		}
		return flag;
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
}
