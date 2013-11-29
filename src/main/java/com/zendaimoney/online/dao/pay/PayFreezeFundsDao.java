package com.zendaimoney.online.dao.pay;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.pay.PayExtractNote;
import com.zendaimoney.online.entity.pay.PayFreezeFunds;

public interface PayFreezeFundsDao extends PagingAndSortingRepository<PayFreezeFunds, Long>{
	PayFreezeFunds findByRechargeId(PayExtractNote  rechargeId);
}
