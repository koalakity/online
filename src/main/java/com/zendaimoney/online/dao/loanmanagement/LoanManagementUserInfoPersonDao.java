package com.zendaimoney.online.dao.loanmanagement;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.loanManagement.LoanManagementUserInfoPerson;


/**
 * 用户个人信息表的Dao interface.
 * 
 * @author wangtf
 */
public interface LoanManagementUserInfoPersonDao extends PagingAndSortingRepository<LoanManagementUserInfoPerson, BigDecimal> {

	LoanManagementUserInfoPerson findByUserId(BigDecimal userId);

}
