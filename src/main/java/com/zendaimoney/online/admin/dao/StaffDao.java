package com.zendaimoney.online.admin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.Staff;

public interface StaffDao extends PagingAndSortingRepository<Staff, Long>{

	Staff findByLoginName(String loginName);

}
