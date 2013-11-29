package com.zendaimoney.online.admin.dao.fundDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.fundDetail.AcTLedgerAdmin;
import com.zendaimoney.online.admin.entity.fundDetail.FinancialAcTCustomerAdmin;

public interface LedgerDao extends PagingAndSortingRepository<AcTLedgerAdmin, Long>, JpaSpecificationExecutor<AcTLedgerAdmin> {

	List<AcTLedgerAdmin> findByAccountIn(String[] strings);

	AcTLedgerAdmin findOneByFinancialAcTCustomerAdminAccountUsersAdminEmailAndBusiType(String email, String busiType);

	AcTLedgerAdmin findByFinancialAcTCustomerAdminAndBusiType(FinancialAcTCustomerAdmin financialAcTCustomerAdmin, String busiType);
}
