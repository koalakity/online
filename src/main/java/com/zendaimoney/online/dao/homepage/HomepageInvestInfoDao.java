package com.zendaimoney.online.dao.homepage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageInvestInfo;

public interface HomepageInvestInfoDao extends PagingAndSortingRepository<HomepageInvestInfo, Long> {
	List<HomepageInvestInfo> findByUserIdOrderByInvestTimeDesc(BigDecimal userId);

	List<HomepageInvestInfo> findByLoanId(BigDecimal loanId);
}
