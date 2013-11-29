package com.zendaimoney.online.admin.dao.loan;

import java.util.List;

import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.admin.vo.LoanInfoListForm;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-3 下午3:08:27 operation by: description:
 */
public interface LoanManagerDaoP {

	/**
	 * @author Ray
	 * @date 2012-12-3 下午1:47:59
	 * @param loginName
	 * @param realName
	 * @param phoneNo
	 * @param interestStartMin
	 * @param interestStartMax
	 * @return description:
	 */
	String totalAmount(String interestStartMin, String interestStartMax, String loginName, String realName, String phoneNo);

	List<LoanInfoAdmin> findLoanRepay(LoanInfoListForm loanInfoListForm);

}
