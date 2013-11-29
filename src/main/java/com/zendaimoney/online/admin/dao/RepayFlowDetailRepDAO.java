package com.zendaimoney.online.admin.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.RepayFlowDetailRepVO;

public interface RepayFlowDetailRepDAO extends PagingAndSortingRepository<RepayFlowDetailRepVO,Integer>,RepayFlowDetailRepDAOCustom{
	public List<RepayFlowDetailRepVO> findByRepayStatus(String repayStatus);
}
