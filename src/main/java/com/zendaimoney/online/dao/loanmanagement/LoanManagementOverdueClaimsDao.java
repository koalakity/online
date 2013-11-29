package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagentOverdueClaims;


/**
 * 
 * 
 * @author wangtf
 */
public interface LoanManagementOverdueClaimsDao extends PagingAndSortingRepository<LoanManagentOverdueClaims, BigDecimal> {
	//更加理财信息ID和分债权期数查询分债权信息
	LoanManagentOverdueClaims findByInvestIdAndNum(BigDecimal investId,Long num);
	//根据借款ID债权期数 已经状态查询
		List<LoanManagentOverdueClaims> findByLoanIdAndNumAndStatus(BigDecimal loanId,Long num,BigDecimal isAdvanced);
	//根据借款ID债权期数 已经状态查询
	List<LoanManagentOverdueClaims> findByLoanIdAndNumAndIsAdvanced(BigDecimal loanId,Long num,BigDecimal isAdvanced);

}
