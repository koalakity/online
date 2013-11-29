package com.zendaimoney.online.oii.sms;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSSender {
	private static Logger logger = LoggerFactory.getLogger(SMSSender.class);
	private static ResourceBundle bundle= ResourceBundle.getBundle("sms");
	private static String errorMessage="";
	
	/**
	 * 
	 * @param para
	 * @return 0成功 -1失败
	 */
	public static int sendMessage(String key,String tel,Map<String, String> paraMap) {
		String m=bundle.getString(key);
		if(StringUtils.isEmpty(m)){
			errorMessage="sms.properties文件中无此键值对应,key="+key;
			logger.error(errorMessage);
			//throw new RuntimeException(errorMessage);
			return -1;
		}
		try {
			m=new String(m.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			errorMessage="sms.properties转码发生错误,错误信息："+e.getMessage();
			logger.error(errorMessage);
			return -1;
		}
		if(StringUtils.isEmpty(tel)||tel.length()!=11){
			errorMessage="手机号码格式错误,tel="+tel;
			logger.error(errorMessage);
			return -1;
		}
		if(paraMap==null||paraMap.size()==0){
			errorMessage="短信发送参数不能为空,参数名：paraMap!";
			logger.error(errorMessage);
			return -1;
		}
//		if(m.split("{").length!=paraMap.size())
//		{
//			errorMessage="短信发送参数与模板参数不匹配,模板参数count="+m.split("{").length;
//			logger.error(errorMessage);
//			return -1;
//		}

		for (int i = 0; i < paraMap.size(); i++) {
			m = m.replace("{" + i + "}",paraMap.get(String.valueOf(i)));
		}
		System.out.println(m);
		return send(tel,m);
//		return 0;
	}
	public static int sendMessage(String tel,String m) {
		return send(tel,m);
//		return -1;
	}
	/**
	 * 
	 * @param tel:手机号码
	 * @param m：短信内容
	 * @return 0成功 -1失败
	 */
	private static int send(String tel,String m){
		if(StringUtils.isEmpty(tel)||StringUtils.isEmpty(m)){
			errorMessage="请检查参数的正常确性,tel="+tel+",m="+m;
			logger.error(errorMessage);
			return -1;
		}
		final SimpleClient sm = new SimpleClient();
		String username="shzhengda2";
		String password="zd0716";
		sm.initialize(username, password, "61.152.167.46", 8088);
		long msgId = System.currentTimeMillis() + new Random().nextInt();
		int rs;
		try {
			rs=sm.send(msgId, tel, m);
		} catch (Exception e1) {
			rs=-1;
			errorMessage="短信发送失败,tel="+tel+",m="+m+",系统错误信息："+e1.getMessage();
			logger.error(errorMessage);
			//throw new RuntimeException(errorMessage);
			e1.printStackTrace();
		}
//		try {
//			Thread.sleep(1000000);  // 用来暂停主线程，等待接受上行MO和状态报告
//		} catch (Exception e) {
//			rs=-1;
//			errorMessage="短信发送失败,tel="+tel+",m="+m+",系统错误信息："+e.getMessage();
//			logger.error(errorMessage);
//			e.printStackTrace();
//		}
		sm.close(); 
		return rs;
	}
	
	public static void main(String[] args){
		Map<String,String> map=new HashMap<String,String>();
		map.put("0", "[测试中文乱码]");
		sendMessage("tel_bind", "18629176032", map);
		//map.put("0", "123456");
//		for(int i=0;i<5;i++){
//		}
		//sendMessage("18629176032", "")
		
		//==================================
	}
}
