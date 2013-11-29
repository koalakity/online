package com.zendaimoney.online.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTimeOutFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String isFrame = req.getParameter("isFrame");
		HttpSession session = req.getSession(true);
		Object usersObj = session.getAttribute("curr_login_user_id");
		if(usersObj==null){
			if(isFrame!=null && !isFrame.equals("")){
				//子窗口，转发到页面重定向.
				resp.getWriter().print("redirectLogin");
				return ;
			}else{
				resp.sendRedirect(req.getContextPath() + "/accountLogin/login/redirectLogin");
				return ;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("");
	}
}
