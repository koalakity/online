package com.zendaimoney.online.admin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtils {

	public static String format(Date date, String pattern) {
		if (null != date) {
			return org.apache.commons.lang3.time.DateFormatUtils.format(date, pattern);
		}
		return null;
	}
	/**
	 * 计算两个日期相差天数 bengin >=end 返回0  bengin < end返回相差天数 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int countDays(String begin,String end){
		  int days = 0;
		  
		  DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar beginDate = Calendar.getInstance();
		  Calendar endDate = Calendar.getInstance();
		  
		  try{
			  beginDate.setTime(dateFormate.parse(begin));
			  endDate.setTime(dateFormate.parse(end));
		   
		   while(beginDate.before(endDate)){
		    days++;
		    beginDate.add(Calendar.DAY_OF_YEAR, 1);
		   }
		   
		  }catch(ParseException pe){
		   //System.out.println("日期格式必须为：yyyy-MM-dd；如：2010-4-4.");
		  }
		  
		  return days; 
		} 
}
