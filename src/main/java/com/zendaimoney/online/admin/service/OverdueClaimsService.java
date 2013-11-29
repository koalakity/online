package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementOverdueClaimsDao;
import com.zendaimoney.online.entity.loanManagement.LoanManagentOverdueClaims;

@Service
@Transactional
public class OverdueClaimsService {
	@Autowired
	private LoanManagementOverdueClaimsDao loanManagementOverdueClaimsDao;
	/**
	 * 获取已垫付金额 逾期未还已经垫付的金额
	 * @param loanId
	 * @param num
	 * @return
	 */
	public double getAdvancedAmount(BigDecimal loanId,Long num){
		double advancedAmount = 0;
		List<LoanManagentOverdueClaims> overdueClaims = loanManagementOverdueClaimsDao.findByLoanIdAndNumAndStatus(loanId, num, new BigDecimal(2));
		for(LoanManagentOverdueClaims overdueClaim:overdueClaims){
			advancedAmount = ArithUtil.add(advancedAmount, overdueClaim.getPayAmount());
		}
		return advancedAmount;
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-13 下午4:18:44
	 * @param loanId
	 * @param num
	 * @return
	 * description: 分债权垫付金额 包含“逾期还款”和未还款的
	 */
	public double getAllAdvancedAmount(BigDecimal loanId,Long num){
		double advancedAmount = 0;
		List<LoanManagentOverdueClaims> overdueClaims = loanManagementOverdueClaimsDao.findByLoanIdAndNumAndIsAdvanced(loanId, num, BigDecimal.ONE);
		for(LoanManagentOverdueClaims overdueClaim:overdueClaims){
			advancedAmount = ArithUtil.add(advancedAmount, overdueClaim.getPayAmount());
		}
		return advancedAmount;
	}
}
