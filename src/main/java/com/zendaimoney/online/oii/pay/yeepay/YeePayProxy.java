package com.zendaimoney.online.oii.pay.yeepay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YeePayProxy {
	/**
	 * 
	 * @param amount 充值金额
	 * @param bankCode 银行代码
	 */
	public static void callYeePay(double amount,String bankCode){
//		String yeePayReqUrl=Configuration.getInstance().getValue("yeepayCommonReqURL");//易宝请求地址
//		Map map=new HashMap();//参数map
//		map.put(Const.p0_Cmd, "Buy");//业务类型
//		map.put(Const.p1_MerId, "");//商户编号
//		map.put("", "");
//		map.put("", "");
//		map.put("", "");
//		map.put("", "");
//		map.put("", "");
//		map.put("", "");
//		map.put("", "");
//		map.put("", "");
	}
	public static List callYeePayTest() 
	{		
		Map map = new HashMap();
		map.put("p0_Cmd", "BuyBuy");
		map.put("p1_MerId", "10000432521");
		map.put("p2_Order", "");
		map.put("p3_Amt", "0.01");
		map.put("p4_Cur", "CNY");
		map.put("p5_Pid", "商品名称");
		map.put("p6_Pcat", "商品种类");
		map.put("p7_Pdesc", "商品描述");
		map.put("p8_Url","http://localhost:8080/YeePay/callback.jsp");
		map.put("p9_SAF", "0");
		map.put("pa_MP", "商家扩展信息");
		map.put("pd_FrpId", "");
		map.put("pr_NeedResponse", "0");
		map.put("hmac", "96bf62fe7ecb78dc16cc1f943a4463e9");
		String url = "http://tech.yeepay.com:8080/robot/debug.action";
		List list=null;
		
		try {
			 list = HttpUtils.URLPost(url, map);
		} catch (Exception e) {
		}
		return list;
	}
	  /**
	   * 
	   * @param args
	   */
	  public static void main(String[] args) throws Exception{
//			Map map = new HashMap();
//			map.put("p0_Cmd", "Buy");
//			map.put("p1_MerId", "10000432521");
//			map.put("p2_Order", "");
//			map.put("p3_Amt", "0.01");
//			map.put("p4_Cur", "CNY");
//			map.put("p5_Pid", "商品名称");
//			map.put("p6_Pcat", "商品种类");
//			map.put("p7_Pdesc", "商品描述");
//			map.put("p8_Url","http://localhost:8080/YeePay/callback.jsp");
//			map.put("p9_SAF", "0");
//			map.put("pa_MP", "商家扩展信息");
//			map.put("pd_FrpId", "");
//			map.put("pr_NeedResponse", "0");
//			map.put("hmac", "96bf62fe7ecb78dc16cc1f943a4463e9");
//			String url = "http://tech.yeepay.com:8080/robot/debug.action";
//			List list = URLGet(url, map);
//		  System.out.print(getUrl(map));
	  }
}
