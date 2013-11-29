package com.zendaimoney.online.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 根据身份证号判断地区
 * 
 * @author xjs
 */
public class AreaUtil {
	
	/**
	 * 
	 * 根据身份证号判断地区
	 * 
	 * @param cardNo 身份证号码
	 * 
	 * @return 地区
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String findArea(String cardNo){
		if(null == cardNo || "".equals(cardNo)){
			return "无法识别";
		}else{
			String areaNol = cardNo.substring(0, 2);
			Map<String,String> area = new HashMap<String,String>();
			area.put("11", "北京");
			area.put("12", "天津");
			area.put("13", "河北");
			area.put("14", "山西");
			area.put("15", "内蒙古");
			area.put("21", "辽宁");
			area.put("22", "吉林");
			area.put("23", "黑龙江");
			area.put("31", "上海");
			area.put("32", "江苏");
			area.put("33", "浙江");
			area.put("34", "安徽");
			area.put("35", "福建");
			area.put("36", "江西");
			area.put("37", "山东");
			area.put("41", "河南");
			area.put("42", "湖北");
			area.put("43", "湖南");
			area.put("44", "广东");
			area.put("45", "广西");
			area.put("46", "海南");
			area.put("50", "重庆");
			area.put("51", "四川");
			area.put("52", "贵州");
			area.put("53", "云南");
			area.put("54", "西藏");
			area.put("61", "陕西");
			area.put("62", "甘肃");
			area.put("63", "青海");
			area.put("64", "宁夏");
			area.put("65", "新疆");
			area.put("71", "台湾");
			area.put("81", "香港");
			area.put("82", "澳门");
			area.put("91", "国外");
			
			Iterator it = area.entrySet().iterator();
			while(it.hasNext()){
				 Map.Entry entry = (Map.Entry) it.next();
				 String key = String.valueOf(entry.getKey());
				 if(areaNol.equals(key)){
					 return String.valueOf(entry.getValue());
				 }
			}
			return "无法识别";
		}


	}
}
