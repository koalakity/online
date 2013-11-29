package com.zendaimoney.online.admin.dao.account;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;

public interface AccountUsersDao extends PagingAndSortingRepository<AccountUsersAdmin, BigDecimal> ,JpaSpecificationExecutor<AccountUsersAdmin>{
   List<AccountUsersAdmin> findByUserId(BigDecimal userId);
  
}
