package com.zendaimoney.online.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.RateAdminVO;

public interface RateAdminDao extends PagingAndSortingRepository<RateAdminVO, Long>, JpaSpecificationExecutor<RateAdminVO> {
	RateAdminVO findById(Long id);

	@Query("select r from RateAdminVO r where r.rateName=? and( r.isDel=0 or r.isDel is null)")
	List<RateAdminVO> findByRateNameAndIsDelNot(String rateName);

	@Query("select r from RateAdminVO r where r.isDel=0 or r.isDel is null")
	List<RateAdminVO> findByIsDel();
}
