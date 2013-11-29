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
 * description:AcTFlowClassify对象中tradeType所用的常量
 */
public class TradeTypeConstants {

	// 充值
	public static final String CZ = "1";
	
	//系统迁移
	public static final String XTQY = "2";

	// 提现
	public static final String TX = "3";
	
	// 放款
	public static final String FK = "4";
	
	//借款手续费
	public static final String FKFEE = "5";
	
	// 风险准备金
	public static final String FKRESERVE = "6";
	
	// 一次性提前还款
	public static final String TQ = "7";
	
	// ID5验证
	public static final String ID5 = "8";
	
	// 调账
	public static final String TZ = "9";
	
	// 冲账
	public static final String CZH = "10";
	
	// 充值手续费
	public static final String CZSXF = "11";
	
	//提现手续费(成功)
	public static final String TXSXF = "15";

	/********************还款相关*********************/
	//正常还款
	//偿还本金
	public static final String ZCHKCHBJ = "16";
	//偿还利息
	public static final String ZCHKCHLX = "17";
	// 月缴管理费
	public static final String ZCHKCHGLF = "18";
	
	//初级逾期还款
	//偿还本金
	public static final String CJYQCHBJ = "19";
	//偿还利息
	public static final String CJYQCHLX = "20";
	//月缴管理费
	public static final String CJYQCHGLF = "21";
	//偿还逾期罚息
	public static final String CJYQCHYQFX = "22";
	//偿还逾期违约金
	public static final String CJYQCHYQGLF = "23";
	
	
	//高级逾期(未代偿)
	//偿还本金
	public static final String GJYQCHBJ = "24";
	//偿还利息
	public static final String GJYQCHLX = "25";
	//月缴管理费
	public static final String GJYQCHGLF = "26";
	//偿还逾期罚息
	public static final String GJYQCHYQFX = "27";
	//偿还逾期违约金
	public static final String GJYQCHYQGLF = "28";
	
	//高级逾期(已代偿)
	//偿还本金
	public static final String DCGJYQCHBJ = "29";
	//偿还利息
	public static final String DCGJYQCHLX = "30";
	//月缴管理费
	public static final String DCGJYQCHGLF = "31";
	//偿还逾期罚息
	public static final String DCGJYQCHYQFX = "32";
	//偿还逾期违约金
	public static final String DCGJYQCHYQGLF = "33";
	
	
	//一次性提前还款
	//偿还本金/回收本金
	public static final String CHDQBJ = "36";
	//偿还利息/回收利息
	public static final String CHDQLX = "37";
	//月缴管理费
	public static final String YCXDQGLF = "38";
	//剩余本金
	public static final String CHSYBJ = "39";
	//提前还款违约金
	public static final String TQHKWYJ = "40";
	
	//提现手续费(失败)
	public static final String TXSXFSB = "41";
	
	/*风险金代偿*/
	//代偿本金
	public static final String FXJDCBJ = "42";
	//代偿利息
	public static final String FXJDCLX = "43";
	private static Map<String,String> map=new HashMap<String, String>();
	static{
		map.put(TradeTypeConstants.CZ, "充值");
		map.put(TradeTypeConstants.XTQY, "系统迁移");
		map.put(TradeTypeConstants.TX , "提现");
		map.put(TradeTypeConstants.FK , "投标/借款成功");
		map.put(TradeTypeConstants.FKFEE , "借款手续费");
		map.put(TradeTypeConstants.FKRESERVE, "风险准备金");
		map.put(TradeTypeConstants.TQ, "一次性提前还款");
		map.put(TradeTypeConstants.ID5, "ID5验证");
		map.put(TradeTypeConstants.TZ , "调账");
		map.put(TradeTypeConstants.CZH , "冲账");
		map.put(TradeTypeConstants.CZSXF , "充值手续费");
		map.put(TradeTypeConstants.TXSXF , "提现手续费(成功)");

/********************还款相关*********************/
//正常还款
		map.put(TradeTypeConstants.ZCHKCHBJ , "偿还本金");
		map.put(TradeTypeConstants.ZCHKCHLX , "偿还利息");
		map.put(TradeTypeConstants.ZCHKCHGLF , "月缴管理费");

//初级逾期还款
		map.put(TradeTypeConstants.CJYQCHBJ , "偿还本金");
		map.put(TradeTypeConstants.CJYQCHLX , "偿还利息");
		map.put(TradeTypeConstants.CJYQCHGLF , "月缴管理费");
		map.put(TradeTypeConstants.CJYQCHYQFX , "偿还逾期罚息");
		map.put(TradeTypeConstants.CJYQCHYQGLF , "偿还逾期违约金");


//高级逾期(未代偿)
		map.put(TradeTypeConstants.GJYQCHBJ , "偿还本金");
		map.put(TradeTypeConstants.GJYQCHLX , "偿还利息");
		map.put(TradeTypeConstants.GJYQCHGLF , "月缴管理费");
		map.put(TradeTypeConstants.GJYQCHYQFX , "偿还逾期罚息");
		map.put(TradeTypeConstants.GJYQCHYQGLF , "偿还逾期违约金");

//高级逾期(已代偿)
		map.put(TradeTypeConstants.DCGJYQCHBJ , "偿还本金");
		map.put(TradeTypeConstants.DCGJYQCHLX , "偿还利息");
		map.put(TradeTypeConstants.DCGJYQCHGLF , "月缴管理费");
		map.put(TradeTypeConstants.DCGJYQCHYQFX , "偿还逾期罚息");
		map.put(TradeTypeConstants.DCGJYQCHYQGLF , "偿还逾期违约金");


//一次性提前还款
		map.put(TradeTypeConstants.CHDQBJ , "偿还本金/回收本金");
		map.put(TradeTypeConstants.CHDQLX , "偿还利息/回收利息");
		map.put(TradeTypeConstants.YCXDQGLF , "月缴管理费");
		map.put(TradeTypeConstants.CHSYBJ , "剩余本金");
		map.put(TradeTypeConstants.TQHKWYJ , "提前还款违约金");
		map.put(TradeTypeConstants.TXSXFSB , "提现手续费(失败)");
/*风险金代偿*/
		map.put(TradeTypeConstants.FXJDCBJ , "代偿本金");
		map.put(TradeTypeConstants.FXJDCLX , "代偿利息");
	}
	
	public static String getValueByType(String type){
		return map.get(type)!=""?map.get(type):type+"";
	}
}
