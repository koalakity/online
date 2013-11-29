/**
 * 
 */
package com.zendaimoney.online.admin.dao.extract;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.extract.ExtractNoteAdmin;

/**
 * @author 王腾飞 实现对借款信息的管理
 */
public interface ExtractNoteDao extends PagingAndSortingRepository<ExtractNoteAdmin, BigDecimal>, JpaSpecificationExecutor<ExtractNoteAdmin> {
	// public List<ExtractNoteAdmin>
	public List<ExtractNoteAdmin> findByAccountUserUserId(BigDecimal userId);
}
