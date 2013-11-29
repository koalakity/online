/**
 * 
 */
package com.zendaimoney.online.admin.dao.loan;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.loan.LoanNoteAdmin;

/**
 * @author Administrator
 * 
 */
public interface LoanNoteMangerDao extends PagingAndSortingRepository<LoanNoteAdmin, BigDecimal>, JpaSpecificationExecutor<LoanNoteAdmin> {
	@Query("select loanNote from LoanNoteAdmin  loanNote where loanNote.loanInfo.loanId =?1  order by loanNote.operateTime desc")
	public List<LoanNoteAdmin> findByLoanInfo_LoanId(BigDecimal loanId);

	List<LoanNoteAdmin> findByLoanInfoAccountUsersUserId(BigDecimal userId);
}
