package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zendaimoney.online.entity.financial.FinancialInvestInfo;

public class FinancialInvestInfoDaoTest {
	public static void main(String[] args) {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		FinancialInvestInfoDao financialInvestInfoDao=applicationContext.getBean(FinancialInvestInfoDao.class);
		System.out.println(financialInvestInfoDao);
		
		FinancialInvestInfo investInfo = new FinancialInvestInfo();
		investInfo.setLoanId(BigDecimal.valueOf(150));
		investInfo.setUserId(BigDecimal.valueOf(2450));
		investInfo.setInvestAmount(0.0D);
		investInfo.setHavaScale(0.0D);
		investInfo.setInvestTime(new Date());
		investInfo.setStatus("2");
//		investInfo.setLedgerFinanceId(alf);
		//保存理财信息
		financialInvestInfoDao.save(investInfo);
	}
}
