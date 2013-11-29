package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.financial.FinancialLoanInfo;


public interface LoanInfoCustomDao{

	/**
	 * 根据status或者releaseStatus统计记录数(或者，并且都可以)
	 * 2012-12-5 上午10:54:15 by HuYaHui
	 * @param status
	 * @param releaseStatus
	 * @return
	 */
	public Object countFinancialLoanInfoByStatusOrReleaseStatus(BigDecimal[] status,BigDecimal releaseStatus);
	
	/**
	 * 根据status或者releaseStatus，分页查询(或者，并且都可以)
	 * 2012-12-5 上午9:47:28 by HuYaHui
	 * @param offset
	 * 			从第几行开始
	 * @param pagesize
	 * 			每页显示的数量
	 * @param releaseStatus
	 * @return
	 */
	public List<FinancialLoanInfo> findByStatusAndReleaseStatusOrderByRTime(int offset,int pagesize,BigDecimal[] status,BigDecimal releaseStatus);
	
	List<FinancialLoanInfo> searchLoanBycondition(String creditGrade, String yearRate, String loanDuration,String column,String seq,String p,String tabFlag);
	
	List<FinancialLoanInfo> findAndSeq(String whereStr,String column,String seq);

}
