package com.zendaimoney.online.common;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
/**
 * memcache缓存工具
 * @author HuYaHui
 *
 */
public class MemcacheCacheHelper {

	private static Logger logger = LoggerFactory.getLogger(MemcacheCacheHelper.class);
	/* 单例模式 */
	private static MemCachedClient mcc = new MemCachedClient();
	
	private MemcacheCacheHelper() {
		
	}
	
	/* 配置服务器组 */
	static {
		/* 定义IP地址和端口 */
		String[] propServers=PropertiesUtil.get("memcache.addr").split(",");
		int serversLength=propServers.length;
		String[] servers=new String[serversLength];
		for(int i=0;i<serversLength;i++){
			String url=propServers[i];
			if(testMemcache(url)){
				logger.info("--------------------缓存初始化成功,url:"+url);
				servers[i]=url;
			}else{
				logger.info("--------------------缓存初始化失败!!!,url:"+url);
			}
		}
		
		/* 拿到一个连接池的实例 */
		SockIOPool pool = SockIOPool.getInstance();
		/* 设置缓存大小 */
		Integer[] weights = { Integer.valueOf(PropertiesUtil.get("memcache.weights")) };
		/* 注入服务器组信息 */
		pool.setServers(servers);
		pool.setWeights(weights);
		/* 配置缓冲池的一些基础信息 */
		pool.setInitConn(Integer.valueOf(PropertiesUtil.get("memcache.initConn")));
		pool.setMinConn(Integer.valueOf(PropertiesUtil.get("memcache.minConn")));
		pool.setMaxConn(Integer.valueOf(PropertiesUtil.get("memcache.maxConn")));
		pool.setMaxIdle(Long.valueOf(PropertiesUtil.get("memcache.maxIdle")));
		/* 设置线程休眠时间 */
		pool.setMaintSleep(Long.valueOf(PropertiesUtil.get("memcache.maintSleep")));
		//线程最长占用时间
		pool.setMaxBusyTime(Long.valueOf(PropertiesUtil.get("memcache.maintSleep")));
		/* 设置关于TCP连接 */
		pool.setNagle(false);// 禁用nagle算法
		pool.setSocketConnectTO(3);
		pool.setSocketTO(3000);// 3秒超时
		pool.setAliveCheck(false);
		/* 初始化 */
		pool.initialize();
	}
	
	public static boolean testMemcache(String url){
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[]{url});
		pool.initialize();
		return mcc.set("hyh1", "1",MemcacheCacheConstants.TIME30M);
	}
	
	
	/**
	 * 设置缓存
	 * 2013-1-9 上午11:53:09 by HuYaHui 
	 * @param key
	 * 			使用的key必须在MemcacheCacheConstants定义
	 * @param value
	 * 			要缓存的对象
	 * @param timeOutDate
	 * 			时间
	 * @return
	 */
	public static boolean set(String key, Object value,Date timeOutDate) {
		return mcc.set(key, value,timeOutDate);
	}
	
	/**
	 * 设置缓存，分钟
	 * 2013-1-9 上午11:53:09 by HuYaHui 
	 * @param key
	 * 			使用的key必须在MemcacheCacheConstants定义
	 * @param value
	 * 			要缓存的对象
	 * @param timeOutDate
	 * 			分钟
	 * @return
	 */
	public static boolean setCacheMinute(String key, Object value,Long timeOutDate) {
		Date cacheDate=new Date(timeOutDate*60*1000);
		return mcc.set(key, value,cacheDate);
	}
	/**
	 * 设置缓存
	 * 2013-1-9 上午11:53:09 by HuYaHui 
	 * @param key
	 * 			使用的key必须在MemcacheCacheConstants定义
	 * @param value
	 * 			要缓存的对象
	 * @param timeOutDate
	 * 			秒
	 * @return
	 */
	public static boolean setCacheSecond(String key, Object value,Long timeOutDate) {
		Date cacheDate=new Date(timeOutDate*1000);
		return mcc.set(key, value,cacheDate);
	}
	/**
	 * 设置缓存
	 * 2013-1-9 上午11:53:09 by HuYaHui 
	 * @param key
	 * 			使用的key必须在MemcacheCacheConstants定义
	 * @param value
	 * 			要缓存的对象
	 * @param timeOutDate
	 * 			小时
	 * @return
	 */
	public static boolean setCacheHour(String key, Object value,Long timeOutDate) {
		Date cacheDate=new Date(timeOutDate*60*60*1000);
		return mcc.set(key, value,cacheDate);
	}
	
	/**
	 * 获取缓存
	 * 2013-1-9 上午11:54:07 by HuYaHui 
	 * @param key
	 * 			key
	 * @param t
	 * 			要返回的类型
	 * @return
	 * 			返回的对象
	 */
	public static <T> T get(String key,T t) {
		return (T)mcc.get(key);
	}
	
	
	public static Object get(String key) {
		return mcc.get(key);
	}
	
	/* 测试 */
	public static void main(String[] args) {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[]{"172.16.199.12:11211"});
		pool.initialize();
		MemCachedClient mcc = new MemCachedClient();
		mcc.set("hyh1", "1",MemcacheCacheConstants.TIME30M);
		System.out.println(mcc.get("hyh1")==null);

	}
	
}

