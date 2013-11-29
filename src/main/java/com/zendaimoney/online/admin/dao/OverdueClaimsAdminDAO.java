package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.OverdueClaimsAdminId;
import com.zendaimoney.online.admin.entity.OverdueClaimsAdminVO;

public interface OverdueClaimsAdminDAO  extends PagingAndSortingRepository<OverdueClaimsAdminVO, Long>{
	/**
	 * @param investId
	 * @param num
	 * @return 根据投资id和还款期数查询 id 由investId和num组成
	 */
	public OverdueClaimsAdminVO findById(OverdueClaimsAdminId id);
}
