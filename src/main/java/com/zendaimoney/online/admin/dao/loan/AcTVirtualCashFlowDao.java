package com.zendaimoney.online.admin.dao.loan;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.loan.AcTVirtualCashFlowAdmin;
/**
 * 虚拟现金流水操作
 * @author Administrator
 *
 */
public interface AcTVirtualCashFlowDao extends PagingAndSortingRepository<AcTVirtualCashFlowAdmin, BigDecimal> ,JpaSpecificationExecutor<AcTVirtualCashFlowAdmin>{
	public List<AcTVirtualCashFlowAdmin> findByAcTLedgerLoanAdmin_id(BigDecimal loanId);
}
