package com.zendaimoney.online.dao.financial;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.financial.FinancialUserInfoPerson;

/**
 * 用户个人信息表的Dao interface.
 * 
 * @author yijc
 */
public interface FinancialUserInfoPersonDao extends PagingAndSortingRepository<FinancialUserInfoPerson, Long>, FinancialUserInfoPersonDaoCustom {

	// UserInfoPerson findByUserId(BigDecimal userId);

}
