package com.zendaimoney.online.admin.dao.audit;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.audit.ReviewMemoNoteAdmin;

public interface AuditNoteDao extends PagingAndSortingRepository<ReviewMemoNoteAdmin, BigDecimal>, JpaSpecificationExecutor<ReviewMemoNoteAdmin> {
	List<ReviewMemoNoteAdmin> findByUserIdOrderByOperateTimeDesc(BigDecimal userId);
}
