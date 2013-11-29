package com.zendaimoney.online.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 金钱，百分数格式化
 */
public class ObjectFormatUtil {

	public ObjectFormatUtil() {
	}

	/**
	 * 格式化货币
	 * 
	 * @param d
	 *            double
	 * @param pattern
	 *            String "¤#,###.00" :显示为 ￥1，234，234.10
	 * @param l
	 *            Locale
	 * @return String
	 */
	public static String formatCurrency(double d, String pattern, Locale l) {
		String s = "";
		try {
			DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(l);
			nf.applyPattern(pattern);
			if (d > 0) {
				s = nf.format(d);
			} else {
				s = "￥0.00";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;

	}

	/**
	 * 使用默认区域的指定方式显示货币
	 * 
	 * @param d
	 *            double
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static String formatCurrency(double d, String pattern) {
		return formatCurrency(d, pattern, Locale.getDefault());
	}

	/**
	 * 使用默认方式显示货币： 例如:￥12,345.46 默认保留2位小数，四舍五入
	 * 
	 * @param d
	 *            double
	 * @return String
	 */
	public static String formatCurrency(double d) {
		String s = "";
		try {
			DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
			if (d > 0) {
				s = nf.format(d);
			} else {
				s = "￥0.00";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;

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
	public static String formatPercent(double d, String pattern, Locale l) {
		String s = "";
		try {
			DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance(l);
			df.applyPattern(pattern);
			if (d > 0) {
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
	 * 使用默认区域格式化百分数
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String formatPercent(double d, String pattern) {
		return formatPercent(d, pattern, Locale.getDefault());
	}

	/**
	 * 格式化百分数
	 * 
	 * @param d
	 * @return
	 */
	public static String formatPercent(double d) {
		String s = "";
		try {
			DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance();

			if (d > 0) {
				df.setMinimumFractionDigits(2);
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
	 * 输出数字的格式,如:1,234,567.89
	 * 
	 * @param bd
	 *            BigDecimal 要格式华的数字
	 * @param format
	 *            String 格式 "###,##0"
	 * @return String
	 */
	public static String numberFormat(BigDecimal bd, String format) {

		if (bd == null || "0".equals(bd.toString())) {
			return "";
		}

		DecimalFormat bf = new DecimalFormat(format);
		return bf.format(bd);
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-1-17 上午10:36:21
	 * @param d
	 * @return description: 格式货币，保留小数点后两位，不进行四舍五入
	 */
	public static String formatMoney(double d) {
		String s = "";
		try {
			DecimalFormat formater = new DecimalFormat();
			formater.setMaximumFractionDigits(2);
			formater.setGroupingSize(0);
			formater.setRoundingMode(RoundingMode.FLOOR);
			if (d > 0) {
				s = "￥" + formater.format(d);
			} else {
				s = "￥0.00";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

}
