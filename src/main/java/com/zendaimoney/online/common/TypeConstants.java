package com.zendaimoney.online.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author huyahui
 * @date: 2013-2-17 下午3:06:59
 * operation by: 
 * description:AcTFlowClassify对象中type所用的常量
 */
public class TypeConstants {
	//所有
	public static int SY=0;
	//提现
	public static int TX=1;
	//充值
	public static int CZ=2;
	//资金回收
	public static int ZJHS=3;
	//偿还借款
	public static int CHJK=4;
	//投标/借款成功
	public static int JKCG=5;
	//系统迁移
	public static int XTJY=6;
	//证件实名认证手续费
	public static int SFYZ=7;
	//调账
	public static int TZ=8;
	//冲账
	public static int CZH=9;
	private static Map<Integer,String> map=new HashMap<Integer, String>();
	static{
		map.put(SY, "所有");
		map.put(TX, "提现");
		map.put(CZ, "充值");
		map.put(ZJHS, "偿还借款/资金回收");
		map.put(CHJK, "偿还借款");
		map.put(JKCG, "投标/借款成功");
		map.put(XTJY, "系统迁移");
		map.put(SFYZ, "证件实名认证手续费");
		map.put(TZ, "调账");
		map.put(CZH, "冲账");
	}
	
	public static String getValueByType(int type){
		return map.get(type)!=""?map.get(type):type+"";
	}
	
}
