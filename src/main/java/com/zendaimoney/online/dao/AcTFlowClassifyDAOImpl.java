package com.zendaimoney.online.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.zendaimoney.online.dao.common.SqlStatement;
import com.zendaimoney.online.entity.AcTFlowClassifyVO;

public class AcTFlowClassifyDAOImpl implements AcTFlowClassifyCustomerDAO {

	@PersistenceContext
	private EntityManager em;

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
			int startPosition, int rows) {
		StringBuilder sb = new StringBuilder(
				"select ac.* from AC_T_FLOW_CLASSIFY ac where 1=1"
						+ " and (ac.out_user_id=? or ac.in_user_id=?)");
		if(type!=0){
			sb.append(" and ac.type=").append(type);
		}
		if(date_start!=null && !date_start.trim().equals("")){
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') >=to_char(to_date('"+date_start+"', 'yyyy-MM-dd'),'yyyy-MM-dd')");	
		}
		if(date_end!=null && !date_end.trim().equals("")){
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') <=to_char(to_date('"+date_end+"', 'yyyy-MM-dd'),'yyyy-MM-dd')");	
		}
		sb.append(" order by ac.greate_time desc,ac.trade_no desc");
		Query query=em.createNativeQuery(sb.toString(),AcTFlowClassifyVO.class);
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		if(rows!=0){
			query.setFirstResult(startPosition);
			query.setMaxResults(rows);	
		}
		List<AcTFlowClassifyVO> list=query.getResultList();
		return list;
	}
	/**
	 * 根据类型,用户ID,统计总数
	 * 2013-4-1 下午12:47:26 by HuYaHui
	 * @param date_start
	 * @param date_end
	 * @param type
	 * @param userId
	 * @return
	 */
	public long countTypeAndUserId(String date_start, String date_end,int type,long userId){
		StringBuilder sb = new StringBuilder(
				"select count(ac.id) from AC_T_FLOW_CLASSIFY ac where 1=1"
						+ " and (ac.out_user_id=? or ac.in_user_id=?)");
		
		if(type!=0){
			sb.append(" and ac.type=").append(type);
		}
		if(date_start!=null && !date_start.trim().equals("")){
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') >=to_char(to_date('"+date_start+"', 'yyyy-MM-dd'),'yyyy-MM-dd')");	
		}
		if(date_end!=null && !date_end.trim().equals("")){
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') <=to_char(to_date('"+date_end+"', 'yyyy-MM-dd'),'yyyy-MM-dd')");	
		}
		Query query=em.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		return Long.valueOf(query.getSingleResult()+"");
	}

	/**
	 * 根据type，userid和多个tradeCode查询数据 2013-3-1 下午4:15:40 by HuYaHui
	 * 
	 * @param map
	 *            key:code,value:type
	 * @param userId
	 *            用户ID
	 * @param flag
	 *            是否按照时间排序
	 * @return
	 */
	public List<AcTFlowClassifyVO> findTypeAndUserIdAndTradeCode(
			List<String> codeAndType, long userId, boolean flag) {
		/*
		 * select * from ac_t_flow_classify actflowcla0_ where 1=1 and
		 * actflowcla0_.type=3 and (actflowcla0_.out_user_id=106080 or
		 * actflowcla0_.in_user_id=106080) and (actflowcla0_.trade_code=6401 or
		 * actflowcla0_.trade_code=6400)
		 */
		StringBuilder sb = new StringBuilder(
				"select ac from AcTFlowClassifyVO ac where 1=1"
						+ " and (ac.outUserId=? or ac.inUserId=?) ");
		if (codeAndType != null && codeAndType.size() != 0) {
			int i = 0;
			for (String str : codeAndType) {
				String[] strArys = str.split(":");
				if (i == 0) {
					sb.append(" and ((ac.tradeCode=")
							.append(Long.valueOf(strArys[0]))
							.append(" and ac.type=")
							.append(Long.valueOf(strArys[1])).append(")");
				} else {
					sb.append(" or (ac.tradeCode=")
							.append(Long.valueOf(strArys[0]))
							.append(" and ac.type=")
							.append(Long.valueOf(strArys[1])).append(")");
				}
				++i;
			}
		}
		sb.append(")");
		if (flag) {
			sb.append(" order by ac.greateTime desc");
		}
		Query query = em.createQuery(sb.toString());
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		return query.getResultList();
	}

	/**
	 * 根据用户ID，时间,类型,用trade_code分组按照时间倒叙，统计数量 2013-2-28 上午9:48:12 by HuYaHui
	 * 
	 * @param userId
	 *            用户ID
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param type
	 *            类型
	 * @param page
	 *            页数
	 * @param rows
	 *            每页显示数量
	 * @return
	 */
	public long groupbyTradeCodeByDateAndTypeAndUserIdCount(Long userId,
			String start, String end, int type) {
		StringBuilder sb = new StringBuilder(
				"select count(*) from (SELECT trade_code, count(trade_code),max(greate_time) t "
						+ " FROM AC_T_FLOW_CLASSIFY"
						+ " where 1 = 1"
						+ "and (out_user_id = ? or in_user_id = ?)");
		if (type != 0) {
			sb.append(" and type=").append(type);
		}
		if (start != null && !start.equals("")) {
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') >=to_char(to_date('"
					+ start + "', 'yyyy-MM-dd'),'yyyy-MM-dd')");
		}
		if (end != null && !end.equals("")) {
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') <=to_char(to_date('"
					+ end + "', 'yyyy-MM-dd'),'yyyy-MM-dd') ");
		}

		sb.append(" group by trade_code,type order by t desc)");
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		return Long.valueOf(query.getSingleResult() + "");
	}

	/**
	 * 根据用户ID，时间,类型,用trade_code分组按照时间倒叙，分页查询 2013-2-28 上午9:48:12 by HuYaHui
	 * 
	 * @param userId
	 *            用户ID
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param type
	 *            类型
	 * @param page
	 *            页数
	 * @param rows
	 *            每页显示数量
	 * @return
	 */
	public List<Object[]> groupbyTradeCodeByDateAndTypeAndUserId(Long userId,
			String start, String end, int type, int page, int rows) {
		/*
		 * select * from ( select * from ( select row_.*,rownum rownum_ from
		 * (SELECT trade_code, count(trade_code),max(greate_time) t FROM
		 * AC_T_FLOW_CLASSIFY where 1 = 1 and to_char(greate_time, 'yyyy-MM-dd')
		 * >= to_char(to_date('2013-02-27', 'yyyy-MM-dd'), 'yyyy-MM-dd') and
		 * to_char(greate_time, 'yyyy-MM-dd') <= to_char(to_date('2013-02-27',
		 * 'yyyy-MM-dd'), 'yyyy-MM-dd') and (out_user_id = 5309850 or in_user_id
		 * = 5309850) group by trade_code) row_ order by t desc )where rownum_
		 * <= 4 ) where rownum_ > 2
		 */

		StringBuilder sb = new StringBuilder(
				"select * from ("
						+ "select * from ("
						+ "select row_.*,rownum rownum_"
						+ " from (SELECT trade_code, count(trade_code),min(greate_time) t,type "
						+ " FROM AC_T_FLOW_CLASSIFY" + " where 1 = 1"
						+ "and (out_user_id = ? or in_user_id = ?)");
		if (type != 0) {
			sb.append(" and type=").append(type);
		}
		if (start != null && !start.equals("")) {
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') >=to_char(to_date('"
					+ start + "', 'yyyy-MM-dd'),'yyyy-MM-dd')");
		}
		if (end != null && !end.equals("")) {
			sb.append(" and to_char(greate_time, 'yyyy-MM-dd') <=to_char(to_date('"
					+ end + "', 'yyyy-MM-dd'),'yyyy-MM-dd') ");
		}
		sb.append(" group by trade_code,type order by t desc ) row_"
				+ ") where rownum_ <= " + (page * rows) + ")"
				+ " where rownum_ > " + ((page - 1) * rows));
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		return query.getResultList();
	}

	/**
	 * 根据条件查询数据 2013-2-25 下午3:41:05 by HuYaHui
	 * 
	 * @param param
	 *            sql对象
	 * @return
	 */
	public List<AcTFlowClassifyVO> findByCondition(List<SqlStatement> param) {
		List<Object> value_list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select a from AcTFlowClassifyVO a where 1=1 ");
		if (param != null && param.size() > 0) {
			for (SqlStatement stat : param) {
				if (stat.getValue() instanceof List) {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(" ")
							.append(stat.getCondition()).append("(");
					List<Object> list = (List<Object>) stat.getValue();
					for (int i = 0; i < list.size(); i++) {
						if (i == 0) {
							sb.append("?");
							value_list.add(list.get(i));
						} else {
							sb.append(",?");
							value_list.add(list.get(i));
						}
					}
					sb.append(")");
				} else {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(stat.getCondition())
							.append("?");
					value_list.add(stat.getValue());
				}
			}
		}
		sb.append(" order by a.greateTime desc");
		Query query = em.createQuery(sb.toString());
		// 设置参数
		for (int i = 1; i <= value_list.size(); i++) {
			query.setParameter(i, value_list.get(i - 1));
		}
		List<AcTFlowClassifyVO> dataList = query.getResultList();
		return dataList;
	}

	/**
	 * 根据条件查询数据,统计总共多少数据 2013-2-25 下午3:41:05 by HuYaHui
	 * 
	 * @param param
	 *            sql对象
	 * @return
	 */
	public Long countByCondition(List<SqlStatement> param) {
		List<Object> value_list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select count(a.id) from AcTFlowClassifyVO a where 1=1 ");
		if (param != null && param.size() > 0) {
			for (SqlStatement stat : param) {
				if (stat.getValue() instanceof List) {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(" ")
							.append(stat.getCondition()).append("(");
					List<Object> list = (List<Object>) stat.getValue();
					for (int i = 0; i < list.size(); i++) {
						if (i == 0) {
							sb.append("?");
							value_list.add(list.get(i));
						} else {
							sb.append(",?");
							value_list.add(list.get(i));
						}
					}
					sb.append(")");
				} else {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(stat.getCondition())
							.append("?");
					value_list.add(stat.getValue());
				}
			}
		}
		Query query = em.createQuery(sb.toString());
		// 设置参数
		for (int i = 1; i <= value_list.size(); i++) {
			query.setParameter(i, value_list.get(i - 1));
		}
		return new Long(query.getSingleResult() + "");
	}

	/**
	 * 根据条件查询数据,统计支出金额总和 2013-2-25 下午3:41:05 by HuYaHui
	 * 
	 * @param param
	 *            sql对象
	 * @return
	 */
	public BigDecimal sumByConditionOut(List<SqlStatement> param) {
		ArrayList<Object> value_list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select sum(a.tradeAmount) from AcTFlowClassifyVO a where (a.outUserId is null or a.outUserId='') ");
		if (param != null && param.size() > 0) {
			for (SqlStatement stat : param) {
				if (stat.getValue() instanceof List) {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(" ")
							.append(stat.getCondition()).append("(");
					List<Object> list = (List<Object>) stat.getValue();
					for (int i = 0; i < list.size(); i++) {
						if (i == 0) {
							sb.append("?");
							value_list.add(list.get(i));
						} else {
							sb.append(",?");
							value_list.add(list.get(i));
						}
					}
					sb.append(")");
				} else {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(stat.getCondition())
							.append("?");
					value_list.add(stat.getValue());
				}
			}
		}
		Query query = em.createQuery(sb.toString());
		// 设置参数
		for (int i = 1; i <= value_list.size(); i++) {
			query.setParameter(i, value_list.get(i - 1));
		}
		Object rtnObj = query.getSingleResult();
		if (rtnObj == null) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(rtnObj + "");

	}

	/**
	 * 根据条件查询数据,统计存入金额总和 2013-2-25 下午3:41:05 by HuYaHui
	 * 
	 * @param param
	 *            sql对象
	 * @return
	 */
	public BigDecimal sumByConditionIn(List<SqlStatement> param) {
		ArrayList<Object> value_list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select sum(a.tradeAmount) from AcTFlowClassifyVO a where (a.inUserId is null or a.inUserId='') ");
		if (param != null && param.size() > 0) {
			for (SqlStatement stat : param) {
				if (stat.getValue() instanceof List) {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(" ")
							.append(stat.getCondition()).append("(");

					List<Object> list = (List<Object>) stat.getValue();
					for (int i = 0; i < list.size(); i++) {
						if (i == 0) {
							sb.append("?");
							value_list.add(list.get(i));
						} else {
							sb.append(",?");
							value_list.add(list.get(i));
						}
					}
					sb.append(")");
				} else {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(stat.getCondition())
							.append("?");
					value_list.add(stat.getValue());
				}
			}
		}
		Query query = em.createQuery(sb.toString());
		// 设置参数
		for (int i = 1; i <= value_list.size(); i++) {
			query.setParameter(i, value_list.get(i - 1));
		}
		Object rtnObj = query.getSingleResult();
		if (rtnObj == null) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(rtnObj + "");
	}

	/**
	 * 根据条件查询数据 2013-2-25 下午3:41:05 by HuYaHui
	 * 
	 * @param param
	 *            sql对象
	 * @param page
	 *            第几页
	 * @param rows
	 *            每页显示数量
	 * @return
	 */
	public List<AcTFlowClassifyVO> findByCondition(List<SqlStatement> param,
			int page, int rows) {
		List<Object> value_list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select a from AcTFlowClassifyVO a where 1=1 ");
		if (param != null && param.size() > 0) {
			for (SqlStatement stat : param) {
				if (stat.getValue() instanceof List) {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(" ")
							.append(stat.getCondition()).append("(");
					List<Object> list = (List<Object>) stat.getValue();
					for (int i = 0; i < list.size(); i++) {
						if (i == 0) {
							sb.append("?");
							value_list.add(list.get(i));
						} else {
							sb.append(",?");
							value_list.add(list.get(i));
						}
					}
					sb.append(")");
				} else {
					sb.append(" ").append(stat.getJoin()).append(" ")
							.append(stat.getName()).append(stat.getCondition())
							.append("?");
					value_list.add(stat.getValue());
				}
			}
		}
		sb.append(" order by a.greateTime desc");
		Query query = em.createQuery(sb.toString());
		// 设置参数
		for (int i = 1; i <= value_list.size(); i++) {
			query.setParameter(i, value_list.get(i - 1));
		}
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);

		List<AcTFlowClassifyVO> dataList = query.getResultList();
		return dataList;
	}

}
