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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 令牌过滤器
 *
 * @author ultrafrog
 * @version 1.0, 2013-4-23
 * @since 1.0
 */
public class TokenFilter implements Filter {

	protected static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);
	
	public static final String PARAM_TOKEN 		= "ptoken";
	public static final String PARAM_CONTEXT 	= "context";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Context.initialize(config.getServletContext());
		logger.info("token filter is ready!");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// 初始化
		Context context = Context.create((HttpServletRequest) req, (HttpServletResponse) resp);
		String token = req.getParameter(PARAM_TOKEN);
		
		// 定义上下文参数
		req.setAttribute(PARAM_CONTEXT, context);
		
		// 判断是否提交令牌参数
		// 仅当提交令牌时进行
		if (token != null && token.length() > 0) {
			if (!context.getToken().equals(token)) {
				logger.warn("can not match between '{}' and '{}'!", new String[] {token, context.getToken()});
				throw new TokenException();
			}
			context.nextToken();
		}
		
		// 继续链表
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		logger.info("token filter is destroyed!");
	}
}
