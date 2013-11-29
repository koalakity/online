package com.zendaimoney.online.dao.homepage;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageAcTLedger;

public interface HomepageAcTLedgerDao  extends PagingAndSortingRepository<HomepageAcTLedger, Long>{
	List<HomepageAcTLedger> findByTotalAccountId(Long totalAccountId);
}
