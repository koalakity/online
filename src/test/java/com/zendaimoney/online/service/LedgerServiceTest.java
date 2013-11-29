package com.zendaimoney.online.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.online.admin.service.LedgerService;
import com.zendaimoney.online.common.ZendaiAccountBank;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath*:/applicationContext.xml"})
public class LedgerServiceTest {

	@Autowired
	private LedgerService ledgerService;
	

	@Test
	public void getAccountListTest(){
		
		ledgerService.getAccountList(new String[]{
				ZendaiAccountBank.zendai_acct1,ZendaiAccountBank.zendai_acct2,
				ZendaiAccountBank.zendai_acct3,ZendaiAccountBank.zendai_acct6,
				ZendaiAccountBank.zendai_acct7,ZendaiAccountBank.zendai_acct9,ZendaiAccountBank.zendai_acct11});
	}


}
