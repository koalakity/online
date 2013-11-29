package com.zendaimoney.online.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class BigDecimalUtil {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	public static void main(String[] args) {
		System.out.println("0.05 + 0.01 = " + BigDecimalUtil.add(BigDecimal.valueOf(0.05d), 0.01));
		System.out.println("1.0 - 0.42 = " + BigDecimalUtil.sub(1820.1066667, 66886.9081953));
		System.out.println("4.015 * 100 = " + BigDecimalUtil.mul(4.015, 100));
		System.out.println("123.32 / 100 = " + BigDecimalUtil.div(153.3, 100));
		System.out.println(compareTo(BigDecimal.valueOf(1d), BigDecimal.valueOf(-1d)));
		System.out.println(round(new BigDecimal(1.236598877444),2));
		System.out.println(forScale(new BigDecimal("123.456"),2));
	}
	public static BigDecimal round(BigDecimal v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(

			"The scale must be a positive integer or zero");

		}
		BigDecimal one = new BigDecimal("1");

		return v.divide(one, scale, BigDecimal.ROUND_HALF_UP);

	}
	
	/**
	 * 除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static BigDecimal div(Number v1, Number v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1+"");
		BigDecimal b2 = new BigDecimal(v2+"");
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static BigDecimal div(Number v1, Number v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
	
	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public static BigDecimal add(Number v1, Number v2) {
		BigDecimal b1 = new BigDecimal(v1==null?"0":v1+"");
		BigDecimal b2 = new BigDecimal(v2==null?"0":v2+"");
		return b1.add(b2);
	}

	
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static BigDecimal sub(Number v1, Number v2) {
		BigDecimal b1 = new BigDecimal(v1==null?"0":v1+"");
		BigDecimal b2 = new BigDecimal(v2==null?"0":v2+"");
		return b1.subtract(b2);
	}
	
	
	/**
	 * 使用默认区域格式化百分数
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String formatPercent(BigDecimal d, String pattern) {
		return formatPercent(d, pattern, Locale.getDefault());
	}
	
	
	/**
	 * 按指定区域格式化百分数
	 * 
	 * @param d
	 * @param pattern
	 *            :"##,##.000%"-->不要忘记“%”
	 * @param l
	 * @return
	 */
	public static String formatPercent(BigDecimal d, String pattern, Locale l) {
		String s = "";
		try {
			DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance(l);
			df.applyPattern(pattern);
			if (d.compareTo(BigDecimal.ZERO) > 0) {
				s = df.format(d);
			} else {
				s = "0.00%";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(Number v1,Number v2) {
		BigDecimal b1 = new BigDecimal(v1+"");
		BigDecimal b2 = new BigDecimal(v2+"");
		return b1.multiply(b2);
	}

	/**
	 * 比较俩个数字大小
	 * 
	 * @param v1
	 *            数字1
	 * @param v2
	 *            数字2
	 * @return 
	 * 			0:相等
	 * 			1:大于
	 * 			-1:小于
	 */
	public static int compareTo(Number v1, Number v2) {
		BigDecimal b1 = new BigDecimal(v1==null?"0":v1+"");
		BigDecimal b2 = new BigDecimal(v2==null?"0":v2+"");
		return b1.compareTo(b2);
	}
	
	/**
	 * 使用默认方式显示货币： 例如:￥12,345.46 默认保留2位小数，四舍五入
	 * 
	 * @param d
	 *            double
	 * @return String
	 */
	public static String formatCurrency(BigDecimal d) {
		String s = "";
		try {
			DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
			if (d.compareTo(BigDecimal.ZERO) ==1) {
				s = nf.format(d);
			} else {
				s = "￥0.00";
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return s;
	}
	
	public static String formatCurrencyIgnoreSymbol(BigDecimal d) {
		String s = "";
		try {
			DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
			s = nf.format(d);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 4舍5入
	 * @param value
	 * @param scale
	 * @return
	 */
	public static BigDecimal forScale(BigDecimal value,int scale){
		if (scale < 0) {
			throw new IllegalArgumentException(
			"The scale must be a positive integer or zero");
		}
		return value.setScale(scale,BigDecimal.ROUND_HALF_UP);
	}
	
	
	/**
	 * 4舍5入
	 * @param value
	 * @param scale
	 * @return
	 */
	public static BigDecimal[] forScale(BigDecimal value[],int scale){
		if (scale < 0) {
			throw new IllegalArgumentException(
			"The scale must be a positive integer or zero");
		}
		if(value!=null&&value.length>0){
			for(int i=0;i<value.length;i++){
				value[i]=forScale(value[i],scale);
			}
		}
		return value;
	}
}
