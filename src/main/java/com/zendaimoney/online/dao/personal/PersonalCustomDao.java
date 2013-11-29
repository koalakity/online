package com.zendaimoney.online.dao.personal;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.personal.PersonalUsers;

public interface PersonalCustomDao {
	/**
	 * 查询用户个人信息
	 * @param userId
	 * @return
	 */
	PersonalUsers fandUsersInfo(BigDecimal userId);
	/**
	 * 查询省信息
	 * @return
	 */
	List fandArea();
	/**
	 * 查询市信息
	 * @return
	 */
	public List fandCity(BigDecimal Provinceid);
	
}
