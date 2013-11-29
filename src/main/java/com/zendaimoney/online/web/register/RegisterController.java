package com.zendaimoney.online.web.register;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.common.CipherUtil;
import com.zendaimoney.online.entity.register.RegisterUsers;
import com.zendaimoney.online.service.register.RegisterManager;

@Controller
@RequestMapping(value = "/register/register/")
public class RegisterController {

	private static Logger logger = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	RegisterManager registerManager;

	@Autowired
	private ChannelInfoService channelInfoService;

	/**
	 * 用户注册提交 2013-1-4 下午5:53:40 by HuYaHui
	 * 
	 * @param users
	 * @param serverPath
	 * @param redirectAttributes
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveAccountInfo")
	public synchronized String save(RegisterUsers users, @RequestParam("serverPath") String serverPath, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
		String email = users.getEmail();

		// 2013-1-5增加渠道来源
		String channelInfo_ID = request.getParameter("channelInfo_ID");
		if (channelInfo_ID != null && !channelInfo_ID.equals("")) {
			ChannelInfoVO channelInfo = new ChannelInfoVO();
			channelInfo.setId(Long.valueOf(channelInfo_ID));
			users.setChannelInfo(channelInfo);
		}

		List<RegisterUsers> userList = registerManager.findByEmail(email);
		RegisterUsers registerUser = registerManager.findByLoginName(users.getLoginName());
		if (userList.size() > 0) {
			setChannelInfoToRequest(request);
			model.addAttribute("registerFlag", "该邮箱已经被注册，请更换邮箱！");
			return "register/accountInfo";
		}
		if (registerUser != null) {
			setChannelInfoToRequest(request);
			model.addAttribute("registerFlag", "用户名已经存在！");
			return "register/accountInfo";
		}
		users.setLoginPassword(CipherUtil.generatePassword(users.getLoginPassword()));
		users.setRegIp(getIpAddr(request));
		users.setIsApprovePhone(BigDecimal.ZERO);
		registerManager.save(users, serverPath);
		users.setEmail(email);
		// redirectAttributes.addAttribute("users", users);
		// model.addAttribute("user", users);
		// model.addAttribute("showFlg", "show");
		return "redirect:/register/register/showAccountInfo?code=" + channelInfo_ID + "&email=" + email;
	}

	@RequestMapping(value = "showAccountInfo", method = RequestMethod.GET)
	public String showAccountInfo(String email, String code, Model model, HttpServletRequest request) {
		// 2013-1-5增加渠道来源
		if (code != null && !code.equals("")) {
			// 所有一级渠道信息
			List<ChannelInfoVO> channelInfoParList = channelInfoService.findAllParentInfo();
			logger.info("查询一级渠道信息集合大小：" + (channelInfoParList != null ? channelInfoParList.size() : 0));
			// 设置到request返回对象
			request.setAttribute("channelInfoParList", channelInfoParList);

			// 根据二级ID，获取对应的二级集合和对应一级信息
			List<ChannelInfoVO> childList = channelInfoService.findChildListById(Long.valueOf(code));
			request.setAttribute("childChannelList", childList);

			Long parentId = 0l;
			// 获取选中的二级渠道对应一级渠道的ID
			for (ChannelInfoVO c_vo : childList) {
				if (code.trim().equals(c_vo.getId() + "")) {
					parentId = c_vo.getParentId();
				}
			}
			request.setAttribute("channelInfoParentId", parentId);
			request.setAttribute("childChannelId", code);
		}

		List<RegisterUsers> userList = registerManager.findByEmail(email);
		if (userList.size() > 0) {
			model.addAttribute("user", userList.get(0));
			model.addAttribute("showFlg", "show");
		}
		return "register/accountInfo";

	}

	// 获取用户IP
	public static String getIpAddr(HttpServletRequest request) {
		String ip = null;
		Enumeration enu = request.getHeaderNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			if (name.equalsIgnoreCase("X-Forwarded-For")) {
				ip = request.getHeader(name);
			} else if (name.equalsIgnoreCase("Proxy-Client-IP")) {
				ip = request.getHeader(name);
			} else if (name.equalsIgnoreCase("WL-Proxy-Client-IP")) {
				ip = request.getHeader(name);
			}
			if ((ip != null) && (ip.length() != 0))
				break;
		}

		if ((ip == null) || (ip.length() == 0))
			ip = request.getRemoteAddr();

		return ip;
	}

	// 跳转到用户邮箱登陆网站 redireEmailSet
	@RequestMapping(value = "redireEmailSet")
	public String redireEmailSet(@RequestParam("email") String email) {
		String webset = email.substring(email.lastIndexOf("@"), email.length());
		if ("@sina.com".equals(webset)) {
			return "redirect:http://mail.sina.com";
		} else if ("@163.com".equals(webset)) {
			return "redirect:http://mail.163.com";
		} else if ("@qq.com".equals(webset)) {
			return "redirect:http://mail.qq.com";
		} else if ("@126.com".equals(webset)) {
			return "redirect:http://mail.126.com";
		} else if ("@sina.cn".equals(webset)) {
			return "redirect:http://mail.sina.cn";
		} else if ("@hotmail.com".equals(webset)) {
			return "redirect:http://mail.hotmail.com";
		} else if ("@gmail.com".equals(webset)) {
			return "redirect:https://mail.google.com/mail";
		} else if ("@sohu.com".equals(webset)) {
			return "redirect:http://mail.sohu.com";
		} else if ("@yahoo.cn".equals(webset)) {
			return "redirect:http://mail.yahoo.cn";
		} else if ("@139.com".equals(webset)) {
			return "redirect:http://mail.139.com";
		} else if ("@vip.qq.com".equals(webset)) {
			return "redirect:http://mail.qq.com";
		} else if ("@189.cn".equals(webset)) {
			return "redirect:http://mail.189.cn";
		} else {
			return "redirect:http://www.hao123.com/mail";
		}
	}

	// 重新发送
	@RequestMapping(value = "againSendEmail")
	public String againSendEmail(@RequestParam("loginName") String loginName, @RequestParam("serverPath") String serverPath, @RequestParam("email") String email, Model model) {
		RegisterUsers users = null;
		try {
			users = registerManager.againSendEmail(loginName, serverPath, email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String[] emailArr = users.getEmail().split(" ");
		users.setEmail(email);
		model.addAttribute("user", users);
		model.addAttribute("showFlg", "show");
		return "register/accountInfo";
	}

	@RequestMapping(value = "creatUser")
	public String create(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "register/accountInfo";
	}

	/**
	 * 根据一级渠道ID查询二级渠道集合,按创建时间降序 2013-1-5 上午10:56:11 by HuYaHui
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findByParentId")
	@ResponseBody
	public String findByParentId(HttpServletRequest request) {
		String id = request.getParameter("code");
		logger.info("查询一级渠道信息ID：" + id);
		if (id == null || id.equals("")) {
			return "";
		}

		List<ChannelInfoVO> rtnList = channelInfoService.findByParentIdOrderByCreateTimeDesc(Long.valueOf(id));
		logger.info("查询二级渠道集合大小：" + (rtnList != null ? rtnList.size() : 0));
		JSONArray jsonAry = new JSONArray();
		for (ChannelInfoVO vo : rtnList) {
			try {
				JSONObject jsonObj = JSONObject.fromObject(vo);
				jsonAry.add(jsonObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonAry.toString();
	}

	/**
	 * 设置渠道信息到请求对象 2013-1-5 上午10:27:58 by HuYaHui
	 * 
	 * @param request
	 */
	private void setChannelInfoToRequest(HttpServletRequest request) {
		// 所有一级渠道信息
		List<ChannelInfoVO> channelInfoParList = channelInfoService.findParentInfoByCond();
		logger.info("查询一级渠道信息集合大小：" + (channelInfoParList != null ? channelInfoParList.size() : 0));
		// 设置到request返回对象
		request.setAttribute("channelInfoParList", channelInfoParList);

		// 查询一个一级渠道对应的二级集合
		if (channelInfoParList != null && channelInfoParList.size() > 0) {
			Long parentId = channelInfoParList.get(0).getId();
			logger.info("查询一级渠道ID：" + parentId);
			Long mode = 1L;// 渠道显示在前台的标识
			List<ChannelInfoVO> childChannelList = channelInfoService.findByParentIdAndIsShowFrontOrderByCreateTimeDesc(parentId, mode);
			logger.info("查询一级渠道对应的二级渠道集合大小为：" + (childChannelList != null ? childChannelList.size() : 0));
			request.setAttribute("childChannelList", childChannelList);
		}
	}

	// @RequestMapping( value = "saveEmail")
	// public String saveEmail(RegisterUsers users,@RequestParam("serverPath")
	// String serverPath){
	//
	// try {
	// // registerManager.sendEmail(users,serverPath);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// //TODO 不跳转任何页面
	// return "";
	// }

	// 邮箱激活
	@RequestMapping(value = "activationEmail")
	public String activationEmail(@RequestParam("username") String username, @RequestParam("activationId") String activationId, RedirectAttributes redirectAttributes, Model model) {
		String rFlg = registerManager.activationEmail(username, activationId);
		if (rFlg.equals("registerSucc")) {
			// redirectAttributes.addFlashAttribute("邮箱验证成功!");
			RegisterUsers u = registerManager.findByLoginName(username);
			model.addAttribute("userId", u.getUserId());
			return "register/registerSuccess";
		} else if (rFlg.equals("vCodeWrong")) {
			model.addAttribute("showMsg", "验证码错误");
			return "register/activationDefeated";
		} else if (rFlg.equals("activEd")) {
			model.addAttribute("showMsg", "邮箱已经验证");
			return "register/activationDefeated";
		} else {
			model.addAttribute("showMsg", "验证失败");
			return "register/activationDefeated";
		}
	}

	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public void checkName(@RequestParam("loginName") String loginName, Model model, HttpServletResponse rep) {
		if (registerManager.findByLoginName(loginName) != null) {
			try {
				rep.getWriter().write("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "checkEmail")
	@ResponseBody
	public void checkEmail(@RequestParam("email") String email, Model model, HttpServletResponse rep) {
		if (registerManager.findByEmail(email).size() != 0) {
			try {
				rep.getWriter().write("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "checkEmailAgain")
	@ResponseBody
	public String checkEmailAgain(@RequestParam("email") String email, @RequestParam("loginName") String loginName, Model model, HttpServletResponse rep) {
		if (registerManager.findByEmailAndLoginName(email, loginName)) {
			return "true";
		} else {
			if (registerManager.findByEmail(email).size() != 0) {
				return "false";
			} else {
				return "true";
			}

		}
	}

	// //保存用户身份信息
	// @RequestMapping( value="saveIdentityInfo")
	// public String saveUserInfo(RegisterUserInfoPerson userInfoPerson,
	// RedirectAttributes redirectAttributes) {
	// registerManager.saveUserInfo(userInfoPerson);
	// return "register/registerSuccess";
	// }

	// 显示隐私条款
	@RequestMapping(value = "showYstk")
	public String showYstk() {
		return "register/ystk";
	}

	// 跳转到登陆页面
	@RequestMapping(value = "login")
	public String login() {
		return "redirect:/accountLogin/login/show";
	}

	// TODO 测试用 过后删除
	@RequestMapping(value = "email")
	public String remail() {

		return "register/emailactivation";
	}

	// //TODO 测试用 过后删除
	// @RequestMapping( value = "userinfo")
	// public String userinfo(){
	//
	// return "register/identityAuthenticate";
	// }

	/**
	 * @author Ray
	 * @date 2012-11-1 上午11:01:51
	 * @param req
	 * @param rep
	 * @return
	 * @throws IOException
	 *             description:注册信息新增验证码校验
	 */
	@RequestMapping(value = "getValidatorImg")
	public String getValidatorImg(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		return "/register/randomCode";
	}

	/**
	 * @author Ray
	 * @date 2012-11-1 上午11:23:46
	 * @param validatorImg
	 * @param req
	 * @param rep
	 * @return
	 * @throws IOException
	 *             description:
	 */
	@RequestMapping(value = "CheckValidatorImg")
	@ResponseBody
	public String checkValidateCode(@RequestParam("validatorImg") String validatorImg, HttpServletRequest req, HttpServletResponse rep) throws IOException {
		return registerManager.CheckValidatorImg(validatorImg, req, rep);
	}

}
