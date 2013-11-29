package com.zendaimoney.online.dao.index;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.notice.IndexNotice;

/**
 * 首页的Dao interface.
 * 
 * @author yijc
 */
public interface IndexNoticeDao extends PagingAndSortingRepository<IndexNotice, Long>, IndexNoticeCustomeDao {

	List<IndexNotice> findByTypeAndIsDelAndIsCommend(Integer type, BigDecimal isDel, BigDecimal isCommend);

	List<IndexNotice> findByTypeBetweenOrderByIdAsc(Integer type1, Integer type2);

	List<IndexNotice> findByTypeAndIsDelOrderByCreDateDesc(Integer type, BigDecimal isDel);

	IndexNotice findByType(Integer type);

	IndexNotice findById(Long id);

}
