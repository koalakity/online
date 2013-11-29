/**
 * 
 */
package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.UsersVO;

/**
 * @author 王腾飞
 *
 * description：
 */
public interface UsersDAO extends PagingAndSortingRepository<UsersVO, Long>,UsersDAOCustomer{
	
	UsersVO findByUserId(Long userId);
}
