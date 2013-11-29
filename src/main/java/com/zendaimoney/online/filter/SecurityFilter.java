package com.zendaimoney.online.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zendaimoney.online.entity.account.AccountUsers;
import com.zendaimoney.online.oii.id5.common.Des2;

public class SecurityFilter implements Filter {
	
	protected static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getRequestURI();
		String isFrame = req.getParameter("isFrame");
		String contextPath = req.getContextPath();
		Cookie[] cookies = req.getCookies();
		HttpSession sessions = req.getSession(true);
		if (cookies != null) {
			AccountUsers users = (AccountUsers) sessions.getAttribute("curr_login_user");
			if (users != null) {
				/*
				 * 以下注释代码为去除手机登录验证所用 原因：手机号码不是唯一 修改人：Ray 修改时间2012.11.15
				 */
				// String photoNO=null;
				// if(users.getPhoneNo()!=null){
				// photoNO=CipherUtil.generatePassword(users.getPhoneNo());
				// }

			}
		}
		if (url.startsWith(contextPath + "/myAccount/myAccount/") || url.startsWith(contextPath + "/loanManage/loanManage/") || url.startsWith(contextPath + "/borrowing/releaseLoan") || url.startsWith(contextPath + "/borrowing/releaseLoan/show") || url.startsWith(contextPath + "/borrowing/releaseLoan/getShow")
				|| url.startsWith(contextPath + "/borrowing/releaseLoan/getReleaseStatus") || url.startsWith(contextPath + "/borrowing/releaseLoan/saveReleaseInfo") || url.startsWith(contextPath + "/borrowing/releaseLoan/reViewLoanInfo") || url.startsWith(contextPath + "/borrowing/releaseLoan/show") || url.startsWith(contextPath + "/borrowing/releaseLoan/show")
				|| url.startsWith(contextPath + "/borrowing/releaseLoan/show") || url.startsWith(contextPath + "/pay/pay/recharge") || url.startsWith(contextPath + "/pay/pay/withdraw") || url.startsWith(contextPath + "/pay/pay/showPayWithdraw") || url.startsWith(contextPath + "/pay/pay/showPayRecharge") || url.startsWith(contextPath + "/pay/pay/showPay")
				|| url.startsWith(contextPath + "/financial/beLender/") || url.startsWith(contextPath + "/borrowing/borrowing/") || url.startsWith(contextPath + "/accountLogin/login/redirectLogin") || url.startsWith(contextPath + "/accountLogin/login/show") || url.startsWith(contextPath + "/homepage/homepage")
				|| url.startsWith(contextPath + "/fundDetail/fundDetail/showFundDetailHead") || url.startsWith(contextPath + "/message/message/messagePageHead") || url.startsWith(contextPath + "/personal/personal/showPhoneBind") || url.startsWith(contextPath + "/personal/personal/notifyMsgSet") || url.startsWith(contextPath + "/infoApproveNew/")) {
			// 是否已登录
			HttpSession session = req.getSession(true);

			Object usersObj = session.getAttribute("curr_login_user_id");
			// 取得当前session中的token值
			String sessionToken = (String) req.getSession().getAttribute("token");
			if (sessionToken != null) {
				// session令牌解密
				sessionToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", sessionToken);
			}
			if (usersObj == null) {
				// session.setAttribute("oldUrl", "");
				if (url.startsWith(contextPath + "/accountLogin/login/redirectLogin")) {
					chain.doFilter(request, response);
					return;
				} else {
					if (url.startsWith(contextPath + "/myAccount/myAccount/showMyAccount")) {
						url += "?strUrlType=account";
					}
					// session.setAttribute("oldUrl", url);
					if (null != isFrame && isFrame.equals("true")) {
						resp.getWriter().print("redirectLogin");
					} else
						resp.sendRedirect(req.getContextPath() + "/accountLogin/login/redirectLogin");
				}

			} else {
				// token令牌验证 by jih
				if (sessionToken != null) {
					if (url.startsWith(contextPath + "/pay/pay/recharge") || url.startsWith(contextPath + "/pay/pay/withdraw") || url.equals(contextPath + "/borrowing/releaseLoan/saveReleaseInfo") || url.startsWith(contextPath + "/financial/beLender/validateIdCardAndPhone") || url.startsWith(contextPath + "/personal/personal/saveFixedAssets")
							|| url.startsWith(contextPath + "/personal/personal/saveEduInfo") || url.startsWith(contextPath + "/personal/personal/savePersonalFinance") || url.startsWith(contextPath + "/personal/personal/saveJobInfo")) {
						String requestToken = (String) req.getParameter("token");
						if (null == requestToken) {
							requestToken = (String) req.getAttribute("token");
						}
						requestToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", requestToken);
						if (!sessionToken.equals(requestToken)) {
							session.removeAttribute("curr_login_user_id");
							session.removeAttribute("curr_login_user");
							if (url.startsWith(contextPath + "/accountLogin/login/redirectLogin")) {
								chain.doFilter(request, response);
								return;
							} else {
								if (url.startsWith(contextPath + "/myAccount/myAccount/showMyAccount")) {
									url += "?strUrlType=account";
								}
								if (null != isFrame && isFrame.equals("true")) {
									resp.getWriter().print("redirectLogin");
									return;
								} else {
									resp.sendRedirect(req.getContextPath() + "/accountLogin/login/redirectLogin");
									return;
								}
							}
						} else {
							String reqStr[] = requestToken.split(";");
							try {
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String reqStrDate = reqStr[1];
								Date dateReq = formatter.parse(reqStrDate);
								Date date = new Date();
								long diff = date.getTime() - dateReq.getTime();
								long minute = diff / (1000 * 60);
								if (minute > 30) {
									session.removeAttribute("curr_login_user_id");
									session.removeAttribute("curr_login_user");
									if (url.startsWith(contextPath + "/accountLogin/login/redirectLogin")) {
										chain.doFilter(request, response);
										return;
									} else {
										if (url.startsWith(contextPath + "/myAccount/myAccount/showMyAccount")) {
											url += "?strUrlType=account";
										}
										if (null != isFrame && isFrame.equals("true")) {
											resp.getWriter().print("redirectLogin");
											return;
										} else {
											resp.sendRedirect(req.getContextPath() + "/accountLogin/login/redirectLogin");
											return;
										}
									}
								} else {
									chain.doFilter(request, response);
									return;
								}
							} catch (Exception e) {
								if (e instanceof TokenException) {
									throw (TokenException) e;
								} else {
									logger.error(e.getMessage(), e);
								}
							}
						}
					}
				}
				if (url.startsWith(contextPath + "/accountLogin/login/show") || url.startsWith(contextPath + "/accountLogin/login/redirectLogin")) {
					resp.sendRedirect(req.getContextPath().equals("") ? "/" : req.getContextPath());
				} else {
					chain.doFilter(request, response);
					return;
				}
			}

		} else {

			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("");
	}
}
