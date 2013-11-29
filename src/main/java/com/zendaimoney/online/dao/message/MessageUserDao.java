package com.zendaimoney.online.dao.message;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.message.MessageUsers;

public interface MessageUserDao extends PagingAndSortingRepository<MessageUsers,Long>{

	MessageUsers findByUserId(BigDecimal usreId);
}
