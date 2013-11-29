package com.zendaimoney.online.dao.accountLogin;

import java.math.BigDecimal;

import com.zendaimoney.online.entity.account.AccountUsers;

/**
 * 登录验证的Dao interface.
 * 
 * @author yijc
 */
public interface LoginDaoCustom {

	/**
	 * 身份证验证，手机没验证
	 * 2013-1-6 上午9:41:31 by HuYaHui 
	 * @param userId
	 * @return
	 * 			true|false
	 */
	public boolean queryUserIsLenderExceptPhone(BigDecimal userId);
	
    AccountUsers getAccountInfo(String loginName,String loginPassword);

	AccountUsers findByLoginName(String loginName);
	
	boolean findIsLender(BigDecimal userId);

}