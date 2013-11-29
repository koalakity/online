package com.zendaimoney.online.dao.homepage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageLoanInfo;

public interface HomepageLoanInfoDao extends PagingAndSortingRepository<HomepageLoanInfo, Long> {
	List<HomepageLoanInfo> findByUserIdOrderByReleaseTimeDesc(BigDecimal userId);

	HomepageLoanInfo findByLoanId(BigDecimal loanId);

}
