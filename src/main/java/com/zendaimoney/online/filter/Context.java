package com.zendaimoney.online.filter;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zendaimoney.online.common.MemcacheCacheHelper;
import com.zendaimoney.online.entity.account.AccountUsers;

/**
 * 上下文
 *
 * @author ultrafrog
 * @version 1.0, 2013-4-23
 * @since 1.0
 */
public class Context {

	protected static final Logger logger = LoggerFactory.getLogger(Context.class);
	
	protected static final String CACHE_TOKEN_SUFFIX	= ".TOKEN";
	protected static final String SESSION_USER 			= "curr_login_user";
	protected static final String SESSION_USER_ID		= "curr_login_user_id";
	
	/** 线程 */
	private static ThreadLocal<Context> current = new ThreadLocal<Context>();
	
	/** 上下文路径 */
	private static String contextPath;
	
	/** 容器上下文 */
	private static ApplicationContext applicationContext;
	
	/** 请求 */
	private HttpServletRequest request;
	
	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public Context(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
	}
	
	/**
	 * 读取上下文路径
	 * 
	 * @return
	 */
	public String getPath() {
		return contextPath;
	}
	
	/**
	 * 读取令牌值
	 * 
	 * @return
	 */
	public String getToken() {
		String token = (String) MemcacheCacheHelper.get(getTokenKey());
		if (token == null || token.length() <= 0) {
			token = UUID.randomUUID().toString();
			MemcacheCacheHelper.setCacheHour(getTokenKey(), token, 24l);
		}
		return token;
	}
	
	/**
	 * 更新令牌
	 */
	public String nextToken() {
		String token = UUID.randomUUID().toString();
		MemcacheCacheHelper.setCacheHour(getTokenKey(), token, 24l);
		return token;
	}
	
	/**
	 * 读取用户
	 * 
	 * @return
	 */
	public AccountUsers getUser() {
		return (AccountUsers) this.request.getSession().getAttribute(SESSION_USER);
	}
	
	/**
	 * 读取用户编号
	 * 
	 * @return
	 */
	public Number getUserId() {
		return (Number) this.request.getSession().getAttribute(SESSION_USER_ID);
	}
	
	/**
	 * 读取令牌键值
	 * 
	 * @return
	 */
	protected String getTokenKey() {
		if (getUserId() == null) {
			return this.request.getSession().getId() + CACHE_TOKEN_SUFFIX;
		}
		return getUserId() + CACHE_TOKEN_SUFFIX;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPath();
	}
	
	/**
	 * 读取实例对象
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	/**
	 * 读取实例对象
	 * 
	 * @param name
	 * @param args
	 * @return
	 */
	public static Object getBean(String name, Object... args) {
		return applicationContext.getBean(name, args);
	}
	
	/**
	 * 读取实例对象
	 * 
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
	
	/**
	 * 读取实例对象
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}
	
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void initialize(ServletContext context) {
		contextPath = context.getContextPath();
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
	}
	
	/**
	 * 当前线程
	 * 
	 * @return
	 */
	public static Context current() {
		return current.get();
	}
	
	/**
	 * 创建线程实例
	 */
	public static Context create(HttpServletRequest request, HttpServletResponse response) {
		Context context = new Context(request, response);
		current.set(context);
		return context;
	}
}
