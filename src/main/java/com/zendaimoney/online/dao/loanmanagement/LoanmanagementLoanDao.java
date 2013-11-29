package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.loan.AcTVirtualCashFlowAdmin;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedgerLoan;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTVirtualCashFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementLoanInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagementUsers;
import com.zendaimoney.online.entity.loanManagement.LoanmanagementUserCreditNote;

/**
 * 借贷管理的Dao interface.
 * 
 * @author yijc
 */
public interface LoanmanagementLoanDao extends PagingAndSortingRepository<LoanManagementUsers, Long>, LoanmanagementLoanCustomDao {

	LoanManagementUsers findByUserId(BigDecimal userId);

	@Query("select l from LoanmanagementUserCreditNote  l where l.userId =?1")
	LoanmanagementUserCreditNote getLoanmanagementUsercreditNote(BigDecimal userId);

	@Query("select l from LoanManagementLoanInfo  l where l.user =?1 and l.releaseStatus = 1 and ( l.status =4 or l.status = 6 or l.status = 7) order by l.loanId")
	List<LoanManagementLoanInfo> getLoanInfoList(LoanManagementUsers user);

	@Query("select l from LoanManagementLoanInfo  l where l.user =?1 and l.releaseStatus = 1 and (l.status =1  or l.status = 2  or l.status = 3 or l.status = 4 or l.status = 5  or l.status = 8 or l.status = 6 or l.status = 7) order by l.releaseTime desc")
	List<LoanManagementLoanInfo> getLoanInfoListByUser(LoanManagementUsers user);

	@Query("select l from LoanManagementLoanInfo  l where l.user =?1 and l.releaseStatus = 1 and l.status = 5 order by l.loanId")
	List<LoanManagementLoanInfo> getRepayOffLoanInfoList(LoanManagementUsers user);

	@Query("select l from LoanManagementAcTVirtualCashFlow l where l.actLedgerLoan =?1 order by l.id")
	List<LoanManagementAcTVirtualCashFlow> getRepayLoanDetailByLoanId(LoanManagementAcTLedgerLoan ledgerLoan);

	@Query("select l from AcTVirtualCashFlowAdmin l where l.acTLedgerLoanAdmin.id =?1 order by l.id")
	List<AcTVirtualCashFlowAdmin> getRepayLoanDetailByLoanId2(BigDecimal ledgerLoan);

	@Query("select l from LoanManagementLoanInfo l where l.loanId =?1 and l.user =?2")
	List<LoanManagementLoanInfo> getLoanInfoByLoanId(BigDecimal loanId, LoanManagementUsers user);

	@Query("select l from LoanManagementAcTLedgerLoan l where l.id =?1")
	List<LoanManagementAcTLedgerLoan> getRepayLoanDetailByLedgerLoanId(long ledgerLoanId);

}