package com.zendaimoney.online.admin.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.common.SysCode;

public interface SysCodeDao extends PagingAndSortingRepository<SysCode, BigDecimal>, JpaSpecificationExecutor<SysCode> {
	SysCode findByCodeId(BigDecimal codeId);
}
