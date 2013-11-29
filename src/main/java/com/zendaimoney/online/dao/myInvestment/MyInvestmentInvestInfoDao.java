package com.zendaimoney.online.dao.myInvestment;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentInvestInfo;

public interface MyInvestmentInvestInfoDao extends PagingAndSortingRepository<MyInvestmentInvestInfo, Long>{
	@Query("select m from MyInvestmentInvestInfo m where m.userId=? and m.status=? order by m.investTime desc")
	public List<MyInvestmentInvestInfo> findByUserIdAndStatus(BigDecimal userId, String status);
}
