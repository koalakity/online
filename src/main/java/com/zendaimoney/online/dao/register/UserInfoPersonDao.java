package com.zendaimoney.online.dao.register;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.register.RegisterUserInfoPerson;

public interface UserInfoPersonDao extends PagingAndSortingRepository<RegisterUserInfoPerson, Long>{

}
