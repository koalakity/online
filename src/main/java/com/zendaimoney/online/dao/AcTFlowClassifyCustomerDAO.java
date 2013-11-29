package com.zendaimoney.online.dao;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.dao.common.SqlStatement;
import com.zendaimoney.online.entity.AcTFlowClassifyVO;



public interface AcTFlowClassifyCustomerDAO{

	/**
	 * 根据类型,用户ID,分页查询信息 2013-3-28 上午11:42:54 by HuYaHui
	 * 2013-4-1 上午11:23:58 by HuYaHui
	 * @param date_start
	 * @param date_end
	 * @param type
	 * @param userId
	 * @param startPosition
	 * 			从多少行开始
	 * @param rows
	 * 			每页显示行数
	 * @return
	 */
	public List<AcTFlowClassifyVO> findTypeAndUserId(String date_start, String date_end,int type, long userId,
			int startPosition, int rows) ;
	
	/**
	 * 根据类型,用户ID,统计总数
	 * 2013-4-1 下午12:47:26 by HuYaHui
	 * @param date_start
	 * @param date_end
	 * @param type
	 * @param userId
	 * @return
	 */
	public long countTypeAndUserId(String date_start, String date_end,int type,long userId);
	
	/**
	 * 根据用户ID，时间,类型,用trade_code分组按照时间倒叙，统计数量
	 * 2013-2-28 上午9:48:12 by HuYaHui
	 * @param userId
	 * 			用户ID
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @param type
	 * 			类型
	 * @param page
	 * 			页数
	 * @param rows
	 * 			每页显示数量
	 * @return
	 */
	public long groupbyTradeCodeByDateAndTypeAndUserIdCount(Long userId,String start,String end,int type);
	
	/**
	 * 根据用户ID，时间,类型,用trade_code分组，分页查询
	 * 2013-2-28 上午9:48:12 by HuYaHui
	 * @param userId
	 * 			用户ID
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @param type
	 * 			类型
	 * @param page
	 * 			页数
	 * @param rows
	 * 			每页显示数量
	 * @return
	 */
	public List<Object[]> groupbyTradeCodeByDateAndTypeAndUserId(Long userId,String start,String end,int type,int page,int rows);


	/**
	 * 根据type，userid和多个tradeCode查询数据
	 * 2013-3-1 下午4:15:40 by HuYaHui 
	 * @param map
	 * 			key:code,value:type
	 * @param userId
	 * 			用户ID
	 * @param flag
	 * 			是否按照时间排序
	 * @return
	 */
	public List<AcTFlowClassifyVO> findTypeAndUserIdAndTradeCode(List<String> codeAndType,long userId,boolean flag);

	/**
	 * 根据条件查询数据
	 * 2013-2-25 下午3:41:05 by HuYaHui 
	 * @param param
	 * 			sql对象
	 * @return
	 */
	public List<AcTFlowClassifyVO> findByCondition(List<SqlStatement> param);
	
	/**
	 * 根据条件查询数据,统计总共多少数据
	 * 2013-2-25 下午3:41:05 by HuYaHui 
	 * @param param
	 * 			sql对象
	 * @return
	 */
	public Long countByCondition(List<SqlStatement> param);
	

	/**
	 * 根据条件查询数据,统计支出金额总和
	 * 2013-2-25 下午3:41:05 by HuYaHui 
	 * @param param
	 * 			sql对象
	 * @return
	 */
	public BigDecimal sumByConditionOut(List<SqlStatement> param);
	
	/**
	 * 根据条件查询数据,统计存入金额总和
	 * 2013-2-25 下午3:41:05 by HuYaHui 
	 * @param param
	 * 			sql对象
	 * @return
	 */
	public BigDecimal sumByConditionIn(List<SqlStatement> param);
	
	/**
	 * 根据条件查询数据
	 * 2013-2-25 下午3:41:05 by HuYaHui 
	 * @param param
	 * 			sql对象
	 * @param first
	 * 			从第几条开始
	 * @param rows
	 * 			每页显示数量
	 * @return
	 */
	public List<AcTFlowClassifyVO> findByCondition(List<SqlStatement> param,int first,int rows);
}
