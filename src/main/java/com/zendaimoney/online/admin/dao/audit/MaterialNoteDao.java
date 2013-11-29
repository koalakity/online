package com.zendaimoney.online.admin.dao.audit;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.audit.MaterialReviewStatusChangeAdmin;


public interface MaterialNoteDao extends PagingAndSortingRepository<MaterialReviewStatusChangeAdmin,BigDecimal>,JpaSpecificationExecutor<MaterialReviewStatusChangeAdmin> {

}
