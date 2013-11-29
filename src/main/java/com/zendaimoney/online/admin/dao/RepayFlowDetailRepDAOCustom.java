package com.zendaimoney.online.admin.dao;

import java.util.Date;
import java.util.List;

import com.zendaimoney.online.admin.entity.RepayFlowDetailRepVO;

public interface RepayFlowDetailRepDAOCustom {


	/**
	 * 根据ID集合查询数据
	 * 2013-3-22 上午10:25:41 by HuYaHui
	 * @param idList
	 * @return
	 */
	public List<RepayFlowDetailRepVO> findByIdList(List<Long> idList);

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
	public List<RepayFlowDetailRepVO> findByCondition(String name,String idCard,String phone,long loadId,Date start,Date end,int page,int rows);

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
	public long countByCondition(String name,String idCard,String phone,long loadId,Date start,Date end);
	
	/**
	 * 初始化所有数据
	 * 2013-3-21 下午1:01:29 by HuYaHui 
	 */
	public void initRepayFlowDetailRep();
	
	/** 
	 * @author 王腾飞
	 * @date 2013-3-22 下午3:39:58
	 * @param date
	 * description: 添加每日新的放款
	*/
	public void scanNewloan(String date);
}
