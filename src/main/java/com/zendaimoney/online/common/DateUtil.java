package com.zendaimoney.online.common;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

/*
 * 时间日期计算
 * */
public class DateUtil {
	// 一天的毫秒数 60*60*1000*24
	private final static long DAY_MILLIS = 86400000;

	// 一小时的毫秒数 60*60*1000
	private final static long HOUR_MILLIS = 3600000;

	// 一分钟的毫秒数 60*1000
	private final static long MINUTE_MILLIS = 60000;

	// 随机数个数
	private final static int RANDOM_COUNT = 3;
	public static final String YYYYMMDDHHMMSS="yyyy-MM-ddHHmmss";
	public static final String YYYYMMDDHH_MM_SS="yyyy-MM-dd HH:mm:ss";
	/**
	 * 返回某个日期的yyyy-MM-dd格式
	 * 2013-3-21 下午3:07:19 by HuYaHui
	 * @param dt
	 * @return
	 * @throws ParseException 
	 */
	public static Date getYYYYMMDDDate(Date dt){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(dt));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 返回某个日期的yyyy-MM-dd格式
	 * 2013-3-21 下午3:07:19 by HuYaHui
	 * @param dt
	 * @return
	 * @throws ParseException 
	 */
	public static Date getYYYYMMDDString(String dtstr){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dtstr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算两个日期之差的间隔天数,开始时间必须大于结束时间。
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getDays(Date start, Date end) {
		if (start.getTime() > end.getTime()) {
			throw new RuntimeException("开始时间必须大于结束时间!");
		}
		long temp = end.getTime() - start.getTime();
		return (int) (temp / DAY_MILLIS);
	}
	
	public static int subDays(Date start, Date end) {
		long temp = end.getTime() - start.getTime();
		return (int) (temp / DAY_MILLIS);
	}

	/**
	 * 计算两个日期相差的天、小时、分钟
	 * 
	 * @param start
	 * @param end
	 */
	public static String show(Date start, Date end) {
		long temp = end.getTime() - start.getTime();
		String leavingTime = temp / DAY_MILLIS + "天" + (temp % DAY_MILLIS) / HOUR_MILLIS + "小时" + ((temp % DAY_MILLIS) % HOUR_MILLIS) / MINUTE_MILLIS + "分";
		return leavingTime;
	}

	/**
	 * 计算当前24小时前的时间
	 * 
	 */
	public static Date getDateBeforeOneDay() {
		// 当前时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 24小时之前的时间
		currentTime.setDate(currentTime.getDate() - 1);
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 计算当前时间
	 * 
	 */
	public static Date getCurrentDate() {
		// 当前时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-14 下午1:10:59
	 * @param date       日期
	 * @param formatType 日期格式
	 * @return
	 * description:根据输入日期格式 获取日期
	 */
	public static Date getDateYYYYMMDD(Date date,String formatType) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		String strDate = formatter.format(date);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}
	
	public static String formatDate(Date dt,String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		String strDate = sdf.format(dt);
		return strDate ;
	}

	/**
	 * 定自义格式当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(Calendar.getInstance().getTime());
	}

	/**
	 * 计算的7天后时间
	 * 
	 */
	public static Date getSevenDaysDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setDate(date.getDate() + 7);
		String strDate = formatter.format(date);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}
	
	/**
	 * 计算的7天后时间,不改变参数的值，返回新的日期对象
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * 
	 */
	public static Date getSevenDaysDateNoReplace(Date date){
		try {
			Date newDt=(Date)BeanUtils.cloneBean(date);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			newDt.setDate(date.getDate() + 7);
			String strDate = formatter.format(newDt);
			ParsePosition pos = new ParsePosition(0);
			return formatter.parse(strDate, pos);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算N个月后的日期
	 * 
	 * @param date
	 * @param par
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getAfterMonth(Date date, int par) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		date.setMonth(date.getMonth() + par);
		String strDate = formatter.format(date);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 获得yyyy/MM/dd日期格式
	 * 
	 */
	public static Date getDateyyyyMMdd() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String d = formatter.format(date);
		Date r = null;
		try {
			r = formatter.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * 获取某个日期的上一个月日期
	 * 2012-12-13 上午11:06:24 by HuYaHui
	 * @param argDate
	 * @return
	 */
	public static Date getPreviousMonthDateyyyyMMdd(Date date){
		Date r=new Date();
		r.setYear(date.getYear());
		r.setMonth(date.getMonth()-1);
		r.setDate(date.getDate());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String d = formatter.format(r);
		try {
			r = formatter.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return r;
	}
	/**
	 * 获取某个日期的上一个天日期
	 * 2012-12-13 上午11:06:24 by HuYaHui
	 * @param argDate
	 * @return
	 */
	public static String getPreviousDayyyyyMMdd(Date date){
		Date r=new Date();
		r.setYear(date.getYear());
		r.setMonth(date.getMonth());
		r.setDate(date.getDate()-1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(r);
	}
	
	/**
	 * 获得yyyy/MM/dd日期格式
	 * 
	 */
	public static Date setDateyyyyMMDD(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String d = formatter.format(date);
		Date r = null;
		try {
			r = formatter.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 获取时间,年月日格式（yyyy-MM-dd）
	 */
	// 获取时间（yyyy-MM-dd）
	public static String getYMDTime(Date date) {
		String dateformat = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		return dateFormat.format(date);
	}

	/**
	 * 获取时间,时分秒格式（HH:mm:ss）
	 */
	// 获取时间（HH:mm:ss）
	public static String getHMSTime(Date date) {
		String dateformat = "HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		return dateFormat.format(date);
	}

	/**
	 * 生成交易流水号前17位
	 */
	public static String getTransactionSerialNumber(String flowSeq) {
		String dateformat = "yyyyMMddHHmmssSSS";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
		if (flowSeq.length() < 3) {
			for (int i = 0; i <= 3 - flowSeq.length(); i++) {
				flowSeq = "0".concat(flowSeq);
			}
		}
		return dateFormat.format(System.currentTimeMillis()).concat(flowSeq);
	}

	/**
	 * 获得字符串格式的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getStrDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String getStrDate2(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	/**
	 * 根据日前判断年龄
	 * 
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}

		return age;
	}
	
	/** 
	 * @author 王腾飞
	 * @date 2013-3-22 下午3:50:29
	 * @return
	 * description:获取昨天的日期
	*/
	public static Date getYestodayDate(){
		Date date = new Date(new Date().getTime()-DAY_MILLIS);
		return getDateYYYYMMDD(date, "yyyy/MM/dd");
	}
}
