package com.zendaimoney.online.dao.pay;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.pay.PayRechargeNote;

public interface PayRechargeDao extends PagingAndSortingRepository<PayRechargeNote, Long>{
	List<PayRechargeNote> findByOrderno(String orderNO);

}
