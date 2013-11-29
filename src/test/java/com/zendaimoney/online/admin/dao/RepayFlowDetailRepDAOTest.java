package com.zendaimoney.online.admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.entity.RepayFlowDetailRepVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:/applicationContext.xml"})
public class RepayFlowDetailRepDAOTest {
	
	@Autowired
	private RepayFlowDetailRepDAO repayFlowDetailRepDAO;
	

	/**
	 * 根据ID集合查询数据
	 * 2013-3-22 上午10:25:41 by HuYaHui
	 * @param idList
	 * @return
	 */
	@Test
	public void findByIdList(){
		List<Long> idList=new ArrayList<Long>();
		idList.add(2l);
		System.out.println(repayFlowDetailRepDAO.findByIdList(idList)==null);
	}
	/**
	 * 根据条件分页查询
	 * 2013-3-22 上午9:12:50 by HuYaHui
	 * @param name
	 * 			名字
	 * @param idCard
	 * 			身份证号码
	 * @param phone
	 * 			手机号码
	 * @param loadId
	 * 			借款编号
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @param page
	 * 			第几页
	 * @param rows
	 * 			每页显示数量
	 * @return
	 			总数
	 */
	@Test
	public void findByConditionTest(){
		String name=null;
		String idCard=null;
		String phone=null;
		long loadId=0;
		Date start=null;
		Date end=null;
		int page=1;
		int rows=10;
		repayFlowDetailRepDAO.findByCondition(name, idCard, phone, loadId, start, end, page, rows);
	}

	/**
	 * 根据条件统计查询结果数量
	 * 2013-3-22 上午10:08:24 by HuYaHui
	 * @param name
	 * 			名字
	 * @param idCard
	 * 			身份证号码
	 * @param phone
	 * 			手机号码
	 * @param loadId
	 * 			借款编号
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @return
	 			总数
	 */
	@Test
	public void countByConditionTest(){
		String name=null;
		String idCard=null;
		String phone=null;
		long loadId=0;
		Date start=null;
		Date end=null;
		int page=1;
		int rows=10;
		repayFlowDetailRepDAO.findByCondition(name, idCard, phone, loadId, start, end, page, rows);
	}

	@Test
	public void initRepayFlowDetailRepTest(){
		repayFlowDetailRepDAO.initRepayFlowDetailRep();
	}
}
