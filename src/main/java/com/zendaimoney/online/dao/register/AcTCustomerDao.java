package com.zendaimoney.online.dao.register;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.register.RegisterAcTCustomer;

public interface AcTCustomerDao  extends PagingAndSortingRepository<RegisterAcTCustomer, Long>{
	
	@Query("select max(a.customerNo) from RegisterAcTCustomer a")
	String findByCustomerNoMaxVal();
	
}
