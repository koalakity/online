package com.zendaimoney.online.admin.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.dao.personal.BaseDAO;

/**
 * 
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author jihui
 * @date: 2012-12-03 上午10:05:15 operation by: description:
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StaticDao extends BaseDAO {
	@PersistenceContext
	private EntityManager em;

	/**
	 * 当前注册的会员数
	 * 
	 * */
	public long getStatRegister() {
		String sql = "select count(*) from AccountUsersAdmin u where u.delStatus=0  and regTime<=Sysdate";
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		long count = 0;
		if (l != null && l.size() > 0 && l.get(0) != null) {
			count = (Long) l.get(0);
		}
		return count;
	}

	public long getStatRegisterCond(int month) {
		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(Calendar.MONTH) + 1;
		String day_last = dateCond(month);
		String sql = "";
		if (currMonth == month) {
			sql = "select count(*) from AccountUsersAdmin u where u.delStatus=0 and regTime<=Sysdate";
		} else {
			sql = "select count(*) from AccountUsersAdmin u where u.delStatus=0 and regTime<=to_date('" + day_last + "','yyyy-mm-dd hh24:mi:ss')";
		}
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		long count = 0;
		if (l != null && l.size() > 0 && l.get(0) != null) {
			count = (Long) l.get(0);
		}
		return count;
	}

	/**
	 * 当前在库放款笔数
	 * 
	 * */
	public int getStatLoan() {
		String sql = "select count(*) from AcTVirtualCashFlowAdmin ac where ac.createDate<=Sysdate group by ac.acTLedgerLoanAdmin.id";
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		int count = 0;
		if (l != null && l.size() > 0 && l.get(0) != null) {
			count = l.size();
		}
		return count;
	}

	public int getStatLoanCount(int month) {
		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(Calendar.MONTH) + 1;
		String condition = dateCond(month);
		String sql = "";
		if (currMonth == month) {
			sql = "select count(*) from AcTVirtualCashFlowAdmin s where s.createDate<=Sysdate group by s.acTLedgerLoanAdmin.id";
		} else {
			sql = "select count(*) from AcTVirtualCashFlowAdmin s where to_char(s.createDate,'yyyy-mm-dd hh24:mi:ss')<=to_char(to_date('" + condition + "','yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')  group by s.acTLedgerLoanAdmin.id";
		}

		Query query = em.createQuery(sql);
		List l = query.getResultList();
		int count = 0;
		if (l != null && l.size() > 0) {
			count = l.size();
		}
		return count;
	}

	/**
	 * 查询在库的合同金额
	 * 
	 * */
	public double getStatLoanAmount() {
		String sql = "select sum(l.principalAmt) from AcTVirtualCashFlowAdmin l where l.createDate<=Sysdate";
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		double count = 0;
		if (l != null && l.size() > 0 && l.get(0) != null) {
			count = (Double) l.get(0);
		}
		return count;
	}

	public double getStatLoanAmountCond(int month) {
		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(Calendar.MONTH) + 1;
		String condition = dateCond(month);
		String sql = "";
		if (currMonth == month) {
			sql = "select sum(l.principalAmt) from AcTVirtualCashFlowAdmin l where l.createDate<=Sysdate";
		} else {
			sql = "select sum(l.principalAmt) from AcTVirtualCashFlowAdmin l where to_char(l.createDate,'yyyy-mm-dd hh24:mi:ss')<=to_char(to_date('" + condition + "','yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')";
		}
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		double count = 0;
		if (l != null && l.size() > 0 && l.get(0) != null) {
			count = (Double) l.get(0);
		}
		return count;
	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-12 上午11:00:05
	 * @param month
	 * @return description:在库余额
	 */
	public double statRemainPricipal() {
		String sql = "select sum(l.principalAmt) from AcTVirtualCashFlowAdmin l where l.editDate<=Sysdate and l.editDate is not null";
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		double count = 0;
		if (l != null && l.size() > 0 && l.get(0) != null) {
			count = (Double) l.get(0);
		}
		return count;
	}

	public double getRemainPricipal(int month) {
		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(Calendar.MONTH) + 1;
		String condition = dateCond(month);
		String sql = "";
		if (currMonth == month) {
			sql = "select sum(l.principalAmt) from AcTVirtualCashFlowAdmin l where l.editDate<=Sysdate and l.editDate is not null";
		} else {
			sql = "select sum(l.principalAmt) from AcTVirtualCashFlowAdmin l where to_char(l.editDate,'yyyy-mm-dd hh24:mi:ss')<=to_char(to_date('" + condition + "','yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') and l.editDate is not null and to_char(l.createDate,'yyyy-mm-dd hh24:mi:ss')<=to_char(to_date('" + condition + "','yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')";
		}
		Query query = em.createQuery(sql);
		List l = query.getResultList();
		double count = 0;
		if (l != null && l.size() > 0 && l.get(0) != null) {
			count = (Double) l.get(0);
		}
		return count;
	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-12 下午2:40:38
	 * @param month
	 * @return description:日期格式化
	 */
	private String dateCond(int month) {
		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(Calendar.MONTH) + 1;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		int y = d.getYear();
		Date firstDay = new Date();
		if (currMonth < month) {
			firstDay = new Date(y - 1, month, 1);
		} else {
			firstDay = new Date(y, month, 1);
		}
		int min = 24 * 60 * 60 * 1000;
		Date to = new Date(firstDay.getTime() - min);
		String condition = df.format(to);
		StringBuffer endStr = new StringBuffer().append(condition).append(" 23:59:59");
		condition = endStr.toString();
		return condition;
	}
}
