package com.zendaimoney.online.dao.personal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.personal.PersonalUsers;


public interface PersonalDao_ extends PagingAndSortingRepository<PersonalUsers,Long>,PersonalCustomDao {

}
