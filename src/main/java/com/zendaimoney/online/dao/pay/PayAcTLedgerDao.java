package com.zendaimoney.online.dao.pay;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.pay.PayAcTLedger;


public interface PayAcTLedgerDao extends PagingAndSortingRepository<PayAcTLedger, Long>{
	PayAcTLedger findByBusiTypeAndAccountLike(String busiType,String Account);
}
