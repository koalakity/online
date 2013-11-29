package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.online.entity.financial.FinancialLoanInfo;

public interface LoanInfoDao extends PagingAndSortingRepository<FinancialLoanInfo, Long>, LoanInfoCustomDao{
	public static final int PAGESIZE=10;
	FinancialLoanInfo findByLoanId(BigDecimal loanId);
	
	//TODO 求总和可以使用findCountByCondition
	List<FinancialLoanInfo> findByStatusAndReleaseStatus(BigDecimal status,BigDecimal releaseStatus);
	
	//求总和可以使用findCountByCondition
	@Deprecated
	@Query("select u from FinancialLoanInfo u where u.status=:status and u.releaseStatus=:releaseStatus order by u.releaseTime desc")
	List<FinancialLoanInfo> findByStatusAndReleaseStatusOrderByRTime(@Param("status")BigDecimal status, @Param("releaseStatus")BigDecimal releaseStatus);
	
	List<FinancialLoanInfo> findByStatusOrStatusOrderByReleaseTimeAsc(BigDecimal status1,BigDecimal status2);
	
	//替代方法findByStatus(int offset,int pagesize,BigDecimal status)
	@Deprecated 
	List<FinancialLoanInfo> findByStatus(BigDecimal status);
	
	//按照时间排序
	@Query("select u from FinancialLoanInfo u where u.releaseStatus=1 order by u.releaseTime desc")
	List<FinancialLoanInfo> findOrderByReTime();
	
	//查找已完成借款
	@Query("select u from FinancialLoanInfo u where u.status in (2,4,5,6,7) and u.releaseStatus=:releaseStatus order by u.releaseTime desc")
	List<FinancialLoanInfo> findLoanEd(@Param("releaseStatus")BigDecimal releaseStatus);
	
}
