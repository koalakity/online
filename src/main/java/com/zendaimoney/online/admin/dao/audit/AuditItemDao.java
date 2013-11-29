package com.zendaimoney.online.admin.dao.audit;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountUserApproveAdmin;

public interface AuditItemDao extends PagingAndSortingRepository<AccountUserApproveAdmin, BigDecimal>, JpaSpecificationExecutor<AccountUserApproveAdmin> {

	@Query("select l from AccountUserApproveAdmin  l where l.user.userId =?1 ")
	List<AccountUserApproveAdmin> getAccountUseApproveList(BigDecimal userId);
	
	@Query("select l from AccountUserApproveAdmin l where l.user.userId=? and l.proId=16")
    List<AccountUserApproveAdmin> getBasicInfoList(BigDecimal userId);
}
