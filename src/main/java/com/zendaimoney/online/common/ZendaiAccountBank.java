package com.zendaimoney.online.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 证大账号定义
 * @author yijc
 *
 */
public class ZendaiAccountBank {
	
	public static final Long zendai_id=1L; 
	public static final String zendai_total_acct="3000000000010000001";//总账号
	
	
	public static final String zendai_acct0="30000000000100000010001";//调账账户
	public static final String zendai_acct5="30000000000100000010006";//其他费用	
	public static final String zendai_acct8="30000000000100000010009";//初始化金额     

	
	public static final String zendai_acct1="30000000000100000010002";//月缴管理费	1
	public static final String zendai_acct2="30000000000100000010003";//逾期罚息		2
	public static final String zendai_acct3="30000000000100000010004";//逾期违约金	3
	public static final String zendai_acct6="30000000000100000010007";//充值手续费	4
	public static final String zendai_acct7="30000000000100000010008";//提现手续费	5
	public static final String zendai_acct9="30000000000100000010010";//借款手续费     	6
	public static final String zendai_acct11="30000000000100000010012";//ID5验证手续费  7
	
	
	
	//public static final String zendai_acct4="30000000000100000010005";//风险金垫付
	public static final String zendai_acct10="30000000000100000010011";//风险准备金   add by mayb 20120828

	private static Map<String,String> map=new HashMap<String, String>();
	static{
		map.put(zendai_acct1, "月缴管理费账户");
		map.put(zendai_acct2, "逾期罚息账户");
		map.put(zendai_acct3, "逾期违约金账户");
		map.put(zendai_acct6, "充值手续费账户");
		map.put(zendai_acct7, "提现手续费账户");
		map.put(zendai_acct9, "借款手续费账户");
		map.put(zendai_acct11, "ID5验证手续费账户");
		map.put(zendai_acct10, "风险准备金账户");
	}
	
	public static String getName(String acc){
		return map.get(acc);
	}
}
