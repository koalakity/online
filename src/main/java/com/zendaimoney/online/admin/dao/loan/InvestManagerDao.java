/**
 * 
 */
package com.zendaimoney.online.admin.dao.loan;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.loan.InvestInfoAdmin;

/**
 * @author 王腾飞
 * 实现对借款信息的管理
 */
public interface InvestManagerDao extends PagingAndSortingRepository<InvestInfoAdmin, BigDecimal> ,JpaSpecificationExecutor<InvestInfoAdmin>{
	
	public List<InvestInfoAdmin> findByLoanInfo_LoanId(BigDecimal loanId);
	
	
	@Query("select l from InvestInfoAdmin l where l.accountUser.userId=?")
	List<InvestInfoAdmin> getInvestInfo(BigDecimal userId);
}
