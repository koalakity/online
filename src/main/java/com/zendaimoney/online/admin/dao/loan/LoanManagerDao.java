package com.zendaimoney.online.admin.dao.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.fundDetail.ActVirtualCashFlow;
import com.zendaimoney.online.admin.entity.loan.AcTLedgerLoanAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;

/**
 * @author 王腾飞 实现对借款信息的管理 2012-12-3 Ray 修改 修改内容：添加集成LoanManagerDaoP
 */
public interface LoanManagerDao extends PagingAndSortingRepository<LoanInfoAdmin, BigDecimal>, JpaSpecificationExecutor<LoanInfoAdmin>, LoanManagerDaoP {

	public LoanInfoAdmin findByLoanId(BigDecimal loanId);

	public List<LoanInfoAdmin> findByStatusIn(BigDecimal[] status);

	@Query("select l from LoanInfoAdmin  l where l.accountUsers.userId =?1  order by l.loanId")
	List<LoanInfoAdmin> getLoanInfoList(AccountUsersAdmin accountUsers);

	List<LoanInfoAdmin> findByAccountUsersUserId(BigDecimal userId);

	@Query("select l from ActVirtualCashFlow l where l.loanId=?1")
	List<ActVirtualCashFlow> getRepayLoanDetailByLoanid(Long loanId);

	@Query("select l from AcTLedgerLoanAdmin l where l.id=?")
	List<AcTLedgerLoanAdmin> getLoanInfoByLoanid(BigDecimal loanId);

	@Query("select l from LoanInfoAdmin l where  l.status=4  or l.status=6 or l.status=7")
	List<LoanInfoAdmin> getLoanInfoByStatus();

	@Query("select l from LoanInfoAdmin l where l.status=4 and l.loanAcTLedgerLoan.interestStart>=?1 and l.loanAcTLedgerLoan.interestStart<=?2 order by l.loanAcTLedgerLoan.interestStart desc ")
	List<LoanInfoAdmin> getLoanInfo(Date interestStartDateMin, Date interestStartDateMax);
}
