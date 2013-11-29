package com.zendaimoney.online.dao.financial;

import java.util.List;

import com.zendaimoney.online.entity.financial.FinancialUserInfoPerson;
import com.zendaimoney.online.entity.financial.FinancialUsers;

/**
 * 用户个人信息表关联的扩展Dao interface.
 * 
 * @author yijc
 */
public interface FinancialUserInfoPersonDaoCustom {

	FinancialUserInfoPerson getUserPhoneInof(String phoneNumber);

	List<FinancialUserInfoPerson> getUserPhoneBakInof(String phoneNumber);

	FinancialUserInfoPerson getUserIdCardInfo(String idcard);

	FinancialUserInfoPerson findByUserId(FinancialUsers user);

}
