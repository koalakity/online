package com.zendaimoney.online.admin.dao.site;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.site.NoticeAdmin;

public interface NoticeDao extends PagingAndSortingRepository<NoticeAdmin, Long> ,JpaSpecificationExecutor<NoticeAdmin>{

	Page<NoticeAdmin> findByTypeAndIsDel(Integer type,BigDecimal isDel, Pageable pageable);
	List<NoticeAdmin> findByTypeAndIsDelAndIsCommend(Integer type,BigDecimal isDel,BigDecimal isCommend);

}
