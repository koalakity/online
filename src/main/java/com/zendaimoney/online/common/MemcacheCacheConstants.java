package com.zendaimoney.online.common;

import java.util.Date;


/**
 * memcache缓存工具,定义相关使用缓存模块的key
 * @author HuYaHui
 *
 */
public class MemcacheCacheConstants {

	//10分钟
	public static Date TIME10M=new Date(10*60*1000l);
			
	//30分钟
	public static Date TIME30M=new Date(30*60*1000l);
	
	//借款查询模块使用的key
	public static final String USERAPPROVEKEY="searchKey_userapprove"; 

	//缓存用户
	public static final String USERKEY="userKey_";
	//缓存用户认证
	public static final String USERINFOPERSONKEY="userinfopersonKey_";
}

