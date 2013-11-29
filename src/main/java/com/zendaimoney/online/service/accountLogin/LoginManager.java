package com.zendaimoney.online.service.accountLogin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.service.AccountUsersService;
import com.zendaimoney.online.common.CipherUtil;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.dao.accountLogin.LoginDao;
import com.zendaimoney.online.dao.accountLogin.LoginOtherDao;
import com.zendaimoney.online.dao.accountLogin.UserloginlogDao;
import com.zendaimoney.online.entity.account.AccountUserloginlog;
import com.zendaimoney.online.entity.account.AccountUsers;
import com.zendaimoney.online.vo.WebAjaxResult;

/**
 * 用户登录相关实体的管理类
 * 
 * @author yijc
 */
// Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class LoginManager {
	private static Logger logger = LoggerFactory.getLogger(LoginManager.class);

	private String loginName;
	private int errCount;
	private String loginPassword;
	private String ipAdr;
	private HttpSession session;
	private String status;
	private AccountUsers currUser;
	private AccountUsers user;
	private String vcodeStr;

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private UserloginlogDao userloginlogDao;

	@Autowired
	private LoginOtherDao LoginOtherDao;

	@Autowired
	private MimeMailService mimeMailService;

	@Transactional(readOnly = false)
	public String loginValidator(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		// 获取session
		session = req.getSession();
		// 获取登录用户名，用户登录密码
		loginName = req.getParameter("loginName");
		if (loginName.length() > 200) {
			return "error";
		}
		loginPassword = req.getParameter("loginPassword");
		user = loginDao.findByLoginName(loginName);
		// 用户名存在
		if (user != null) {
			currUser = loginDao.getAccountInfo(loginName, CipherUtil.generatePassword(loginPassword));
			// 用户名密码正确
			if (currUser != null) {
				if (!currUser.getIsapproveEmail().equals(BigDecimal.ONE)) {
					userloginlogDao.save(setloginLog(user.getUserId(), BigDecimal.ONE, req));
					status = "notApproveEmail";
				} else if (currUser.getDelStatus().equals(BigDecimal.ONE)) {
					userloginlogDao.save(setloginLog(user.getUserId(), BigDecimal.ONE, req));
					status = "dealUserToRecycle";
				} else {
					// TODO 24小时内登录错误次数(目前设为当天)
					errCount = userloginlogDao.getErrCount(currUser.getUserId());
					// 当天登录错误次数3次及以上
					// TODO 登录锁定一期不做
					// **************if(currUser.getLockStatus()!=null&&currUser.getLockStatus().intValue()==1){
					// //记录登录日志信息
					// userloginlogDao.save(setloginLog(currUser.getUserId(),BigDecimal.ONE,req));
					// //当前用户锁定
					// status = "lock";
					//
					// }else{
					// if(errCount >= 3){
					// //记录登录日志信息
					// currUser.setLockStatus(BigDecimal.ONE);
					// loginDao.save(currUser);
					// userloginlogDao.save(setloginLog(currUser.getUserId(),BigDecimal.ONE,req));
					//
					// //当前用户锁定
					// status = "lock";
					// *****************}else{ TODO 登录锁定一期不做
					// 用户登录状态正常 记录用户登录信息
					userloginlogDao.save(setloginLog(currUser.getUserId(), BigDecimal.ZERO, req));
					saveLoginInfo(req, currUser);
					// 将当前用户记录到session中
					session.setAttribute("curr_login_user", currUser);
					session.setAttribute("curr_login_user_id", currUser.getUserId());
					status = "success";

					// 写cookie by jihui
					// cookie的用户名和密码加密
					Cookie cookie = new Cookie(CipherUtil.generatePassword(loginName), CipherUtil.generatePassword(loginPassword));
					cookie.setPath("/");
					cookie.setMaxAge(60 * 40);
					rep.addCookie(cookie);
					rep.flushBuffer();

					// *****************}TODO 登录锁定一期不做
					// **************}TODO 登录锁定一期不做
				}

			} else {
				// 用户存在,密码错误 记录异常登录信息
				errCount = userloginlogDao.getErrCount(user.getUserId());
				// **************if(errCount>=3){TODO 登录锁定一期不做
				// //记录登录日志信息
				// userloginlogDao.save(setloginLog(user.getUserId(),BigDecimal.ONE,req));
				// //当前用户锁定
				// status = "lock";
				// ***************}else{TODO 登录锁定一期不做
				userloginlogDao.save(setloginLog(user.getUserId(), BigDecimal.ONE, req));
				status = "error";
				// ***************}TODO 登录锁定一期不做
			}
		} else {
			// 用户不存在
			status = "error";
		}
		return status;
	}

	// 获取用户邮箱
	public String getUserEmail(HttpServletRequest req) {
		// 获取session
		session = req.getSession();
		// 获取登录用户名，用户登录密码
		loginName = req.getParameter("loginName");
		user = loginDao.findByLoginName(loginName);
		return user.getEmail();
	}

	// 更新用户登录信息
	public void saveLoginInfo(HttpServletRequest req, AccountUsers user) {
		session = req.getSession();
		// 提取用户IP地址
		if (req.getHeader("x-forwarded-for") == null) {
			ipAdr = req.getRemoteAddr();
		} else {
			ipAdr = req.getHeader("x-forwarded-for");
		}
		user.setLoginIpLast(ipAdr);
		// 用户登录时间的设定
		user.setLoginTimeLast(new Timestamp(DateUtil.getCurrentDate().getTime()));
		// 用户登录次数累计
		user.setLoginCount(user.getLoginCount() != null ? user.getLoginCount().add(BigDecimal.ONE) : BigDecimal.ONE);
		logger.info("SAVE: USERS || IP= "+ipAdr+" count="+user.getLoginCount()+" time="+user.getLoginTimeLast());
		loginDao.save(user);
	}

	// 获取验证码
	public String CheckValidatorImg(String inputVcodeStr, HttpServletRequest req, HttpServletResponse rep) throws IOException {
		// 读取session信息
		session = req.getSession();
		// 验证码存在时,读取session中的验证码
		if (session.getAttribute("validateCode") != null) {
			vcodeStr = session.getAttribute("validateCode").toString();
		}
		if (vcodeStr.equals(inputVcodeStr.toUpperCase())) {
			return "true";
		} else {
			return "false";
		}
	}

	// 退出登录
	public void logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		AccountUsers currUser = (AccountUsers) session.getAttribute("curr_login_user");
		BigDecimal currUserId = (BigDecimal) session.getAttribute("curr_login_user_id");
		if (currUser != null) {
			session.removeAttribute("curr_login_user");
		}
		if (currUserId != null) {
			session.removeAttribute("curr_login_user_id");
		}

		/*
		 * 退出登录后清除session和cookie Ray
		 */
		req.getSession().invalidate();// 清空session
		Cookie cookie = req.getCookies()[0];// 获取cookie
		cookie.setMaxAge(0);// 让cookie过期

	}

	public WebAjaxResult checkLogin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		AccountUsers currUser = (AccountUsers) session.getAttribute("curr_login_user");
		BigDecimal currUserId = (BigDecimal) session.getAttribute("curr_login_user_id");
		WebAjaxResult result = new WebAjaxResult();
		if (currUser == null || "".equals(currUserId)) {
			result.setSuccess(true);
			return result;
		} else {
			result.setSuccess(false);
			return result;
		}

	}

	// 用户是否被锁定
	public String checkIsLocked(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		// 用户输入的验证码
		loginName = req.getParameter("loginName");
		user = loginDao.findByLoginName(loginName);
		if (user != null) {
			errCount = userloginlogDao.getErrCount(user.getUserId());
			if (errCount >= 3) {
				return "false";
			} else {
				return "true";
			}
		} else {
			return "true";
		}
	}

	// 保存用户登录日志信息
	@Transactional(readOnly = false)
	public void saveLoginInfo(AccountUsers user, HttpServletRequest req) {
		user.setLoginIpLast(getIpAddr(req));
		user.setLoginCount(user.getLoginCount().add(BigDecimal.ONE));
		user.setLoginTimeLast(new Timestamp(DateUtil.getCurrentDate().getTime()));
		logger.info("SAVE: USERS || LoginIpLast= "+user.getLoginIpLast()+" count="+user.getLoginCount()+" time="+user.getLoginTimeLast());
		loginDao.save(user);
	}

	// 设置用户登录日志信息
	public AccountUserloginlog setloginLog(BigDecimal userId, BigDecimal loginStatus, HttpServletRequest req) {
		AccountUserloginlog ulog = new AccountUserloginlog();
		ulog.setLoginIp(getIpAddr(req));
		ulog.setLoginStatus(loginStatus);
		ulog.setLoginTime(new Timestamp(DateUtil.getCurrentDate().getTime()));
		ulog.setUserId(userId);
		return ulog;
	}

	// 获取用户IP
	public String getIpAddr(HttpServletRequest req) {
		session = req.getSession();
		if (req.getHeader("x-forwarded-for") == null) {
			ipAdr = req.getRemoteAddr();
		} else {
			ipAdr = req.getHeader("x-forwarded-for");
		}
		return ipAdr;
	}

	@Transactional(readOnly = false)
	public boolean resetPwd(String remail) {
		if (remail.length() > 200) {
			return false;
		}
		// Long i=Math.round(Math.random()*1000000);
		int i = (int) ((Math.random() * 9 + 1) * 100000);
		AccountUsers u = LoginOtherDao.findByEmail(remail);
		if (u == null) {
			return false;
		}
		u.setLoginPassword(CipherUtil.generatePassword(String.valueOf(i)));
		String emailHtml = String.valueOf(i);
		mimeMailService.sendNormalMail(emailHtml, remail);
		logger.info("SAVE: USERS || userId= "+u.getUserId()+" emailHtml="+emailHtml);
		LoginOtherDao.save(u);
		return true;
	}

	public boolean checkEmail(String email) {
		AccountUsers u = LoginOtherDao.findByEmail(email);
		if (u != null) {
			return true;
		} else {
			return false;
		}
	}

}
