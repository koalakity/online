package com.zendaimoney.online.listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zendaimoney.online.common.MemcacheCacheHelper;

/**
 * 缓存初始化
 * @author HuYaHui
 *
 */
public class MemcacheInitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//初始化memcache缓存
		MemcacheCacheHelper.set("key","1",new Date());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	 
	}

}
