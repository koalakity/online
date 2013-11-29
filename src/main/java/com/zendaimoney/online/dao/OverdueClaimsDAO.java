/**
 * 
 */
package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.OverdueClaimsId;
import com.zendaimoney.online.entity.OverdueClaimsVO;

/**
 * @author 王腾飞
 *
 * description：
 */
public interface OverdueClaimsDAO  extends PagingAndSortingRepository<OverdueClaimsVO, Long>{
	
	/**
	 * @param investId
	 * @param num
	 * @return 根据投资id和还款期数查询 id 由investId和num组成
	 */
	public OverdueClaimsVO findById(OverdueClaimsId id);
}
