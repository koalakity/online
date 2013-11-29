package com.zendaimoney.online.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ArithUtil {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
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

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
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

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
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
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
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
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 补位运算。当要求保留小数位时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            补位前的值
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 补位后的值
	 */
	public static double formatScale(double v1, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		return (new BigDecimal(MarkUpZero(b1.toString(), 2))).doubleValue();
	}

	/**
	 * 字符串取小点后2位
	 * 
	 * @param str
	 *            字串(数字)
	 * @param precision
	 *            小数点位
	 * @return 取位后的字符串
	 */
	public static String MarkUpZero(String str, int precision) {
		String retVal = str;
		String maskup = "";
		if (precision == 0) {
			int idx = retVal.indexOf(".");
			if (idx == -1)
				return retVal;
			else
				return retVal.substring(0, idx);
		}
		if (precision > 0 && retVal.indexOf(".") == -1) {
			retVal += ".";
		}
		for (int i = 0; i < precision; i++) {
			maskup += "0";
		}
		int len = retVal.indexOf(".");

		return (retVal + maskup).substring(0, retVal.indexOf(".")) + (retVal + maskup).substring(retVal.indexOf("."), len + precision + 1);
	}

	/**
	 * 4位序号取自sequence
	 */
	public static String getSequenceStr(String sequence) {
		int bwStr = 4 - sequence.length();
		for (int i = 0; i < bwStr; i++) {
			sequence = "0".concat(sequence);
		}
		return sequence;
	}

	/**
	 * 舍去小数点后两位数据
	 * 
	 * @param v
	 * @return
	 */
	public static Double DoubleAccurate(Double v) {
		if (v == null) {
			return 0D;
		}
		DecimalFormat df = new DecimalFormat("####.#########################");
		String vs = df.format(v);
		String[] varr = vs.split("[.]");

		int l = 0;
		if (varr.length == 2) {
			l = varr[1].length() >= 2 ? 2 : varr[1].length();
			vs = varr[1].substring(0, l);
			return Double.valueOf(varr[0] + "." + vs);
		} else {
			return v;
		}
	}

	/**
	 * 舍去小数点后L位数据
	 * 
	 * @param v
	 * @return
	 */
	public static Double DoubleAccurate(Double v, int L) {
		if (v == null) {
			return 0D;
		}
		DecimalFormat df = new DecimalFormat("####.#########################");
		String vs = df.format(v);
		String[] varr = vs.split("[.]");

		int l = 0;
		if (varr.length == 2) {
			l = varr[1].length() >= L ? L : varr[1].length();
			vs = varr[1].substring(0, l);

			return Double.valueOf(varr[0] + "." + vs);
		} else {
			return v;
		}
	}

}
