package com.zendaimoney.online.service;

import java.math.BigDecimal;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.online.service.newPay.FundManagerNew;
import com.zendaimoney.online.vo.fundDetail.FundFlowVO;
import com.zendaimoney.online.web.DTO.AcTFlowClassifyDTO;

/**
 * FundManagerNew测试类
 * @author HuYaHui
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath*:/applicationContext.xml"})
public class FundManagerNewTest {
	
	@Autowired
	private FundManagerNew fundManagerNew;
	

	
	
	@Test
	public void queryFundFlowTest(){
		//FundFlowVO vo=fundManagerNew.queryFundFlow(5308250l, "2013-02-27","2013-02-27",1, 10, 0);

		t(5308250);
	}
	private void t(long userId){
		FundFlowVO vo=fundManagerNew.queryFundFlow(userId, "2013-02-28","2013-02-28",1, 10, 0);
		
		if(vo==null){
			System.err.println("没有找到数据");
		}else{
			System.out.println("数据大小："+vo.getFlowList().size());
		}
		System.out.println("用户:"+userId);
		for (Iterator iterator = vo.getFlowList().iterator(); iterator.hasNext();) {
			AcTFlowClassifyDTO type = (AcTFlowClassifyDTO) iterator.next();
			System.out.println(type);
		}
	}
	
}
