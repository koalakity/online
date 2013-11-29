package com.zendaimoney.online.admin.dao.loan;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.loan.AcTLedgerLoanAdmin;

public interface AcTLedgerLoanDao extends PagingAndSortingRepository<AcTLedgerLoanAdmin, BigDecimal> ,JpaSpecificationExecutor<AcTLedgerLoanAdmin>{

}