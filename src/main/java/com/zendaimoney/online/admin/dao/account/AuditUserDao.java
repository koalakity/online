package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;

public interface AuditUserDao extends PagingAndSortingRepository <AccountUsersAdmin,BigDecimal>,JpaSpecificationExecutor<AccountUsersAdmin>{
   public AccountUsersAdmin findByUserId(BigDecimal userId);
}
