package com.zendaimoney.online.common;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author HuYaHui
 *
 */
public class PropertiesUtil {
	public static String get(String key,String fileName){
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getResourceAsStream(fileName);
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取配置文件里面信息
	 * 2012-12-6 下午3:22:18 by HuYaHui
	 * @param key
	 * 			键
	 * @return
	 */
	public static String get(String key){
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getResourceAsStream("/config.properties");
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
