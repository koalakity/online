package com.zendaimoney.online.dao.message;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.message.MessageSysMsg;
import com.zendaimoney.online.entity.message.MessageUsers;

public interface MessageSysMsgDao extends PagingAndSortingRepository<MessageSysMsg,Long>{

	List<MessageSysMsg> findByUserIdAndIsDelOrderByHappenTimeDesc(MessageUsers user,String isDel);
	
	MessageSysMsg findById(BigDecimal id);
}
