package com.zendaimoney.online.common;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/*
 * 公共的字段
 * */
@Component
public class CodeUtil {
	public static BigDecimal ALLLOANCOUNT = BigDecimal.valueOf(0l);
	public static BigDecimal INGLOANCOUNT = BigDecimal.valueOf(2l);
	public static BigDecimal FUTURELOANCOUNT = BigDecimal.valueOf(1l);
	public static BigDecimal OLDLOANCOUNT3 = BigDecimal.valueOf(3l);
	public static BigDecimal OLDLOANCOUNT4 = BigDecimal.valueOf(4l);
	public static BigDecimal OLDLOANCOUNT5 = BigDecimal.valueOf(5l);
	public static BigDecimal BLACKLOANCOUNT = BigDecimal.valueOf(6l);
	

}
