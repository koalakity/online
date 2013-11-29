package com.zendaimoney.online.dao.myInvestment;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentLateInfo;

public interface MyInvestmentLateInfoDao extends PagingAndSortingRepository<MyInvestmentLateInfo, Long>{

	public List<MyInvestmentLateInfo> findByLoanIdAndKind(BigDecimal loanId,String kind);
}
