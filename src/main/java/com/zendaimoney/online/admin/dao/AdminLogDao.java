package com.zendaimoney.online.admin.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.AdminLog;

public interface AdminLogDao extends PagingAndSortingRepository<AdminLog, Long>,JpaSpecificationExecutor<AdminLog>{

}
