package com.zendaimoney.online.admin.dao.fundDetail;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.fundDetail.AcTFlowAdmin;

public interface FundFlowDao extends PagingAndSortingRepository<AcTFlowAdmin, Long>, JpaSpecificationExecutor<AcTFlowAdmin>, FundFlowDaoP {

	List<AcTFlowAdmin> findByAccountInOrAppoAcctInOrderByTradeDateDesc(String[] accounts, String[] appoAccounts);

	Page<AcTFlowAdmin> findByAccountInOrAppoAcctInOrderByTradeDateDesc(String[] accounts, String[] appoAccounts, Pageable pageable);
}
