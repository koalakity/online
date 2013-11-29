package com.zendaimoney.online.dao.fundDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.entity.fundDetail.AcTFlow;
import com.zendaimoney.online.vo.fundDetail.FundFlowVO;
@Component
public class OldFlowShowDao {
	@PersistenceContext
	private EntityManager em;
	public FundFlowVO getFlowInfo(BigDecimal userId, String type_fund, String date_start, String date_end, double crrunAmount) {
		String total_acc = getTotalAcctId(userId);
		double amount = 0;
		Map<String, String> acounts = initActLedgerAcounts(total_acc);
		String selectAcounts = "";
		for (Map.Entry<String, String> entry : acounts.entrySet()) {
			selectAcounts = selectAcounts + "'" + entry.getKey() + "',";
		}
		selectAcounts = selectAcounts.substring(0, selectAcounts.length() - 1);
		String sql = "select f from AcTFlow f where (" + "f.account in" + "(" + selectAcounts + ")" + " or f.appoAcct in" + "(" + selectAcounts + "))";
		if (date_start != null && date_end != null) {
			sql = sql + " and " + "f.tradeDate>=to_date('" + date_start + " 00-00-00','yyyy-MM-dd HH24:mi:ss') " + " and " + "f.tradeDate<=to_date('" + date_end + " 23-59-59','yyyy-MM-dd HH24:mi:ss')";
		}
		sql = sql + " order by f.tradeDate desc,f.id desc";
		Query query = em.createQuery(sql);
		List<AcTFlow> l = query.getResultList();
		List voList = new ArrayList();
		double temp = 0;
		boolean inOutFlag = false;
		if (l != null && l.size() > 0) {
			for (int i = 0; i < l.size(); i++) {
				AcTFlow flow = (AcTFlow) l.get(i);
				String flowAcount = flow.getAccount();
				String flowApoAcct = flow.getAppoAcct();
				if (flow.getAcctTitle().equals("010201") && flow.getAppoAcctTitle().equals("010202")) {
					// System.out.println(flow.getMemo());
					continue;
				}
				if(flow.getAcctTitle().equals("01020109113")&&flow.getAppoAcctTitle().equals("01020209113")){
					continue;
				}
				if(flow.getAcctTitle().equals("01020101121")&&flow.getAppoAcctTitle().equals("01020201121")){
					continue;
				}
				if (acounts.get(flowAcount) != null && acounts.get(flowApoAcct) != null) {
					if (acounts.get(flowAcount).equals("4")) {
						if (acounts.get(flowApoAcct).equals("1") || acounts.get(flowApoAcct).equals("2")) {
							// System.out.println(flow.getMemo());
							continue;

						}
					}
					if (acounts.get(flowAcount).equals("2") && acounts.get(flowApoAcct).equals("4")) {
						// System.out.println(flow.getMemo());
						continue;
					}

					if (acounts.get(flowAcount).equals("1") && acounts.get(flowApoAcct).equals("4")) {
						// System.out.println(flow.getMemo());
						continue;

					}
				}
				Map map = new HashMap();
				map.put("date", flow.getTradeDate().toString().substring(0, 19));// 日期
				if (flow.getMemo() == null || flow.getMemo().length() < 6) {
					System.out.println("===================>> 数据库流水表id为" + flow.getId() + "操作科目格式应该为：操作科目：XXXXXXX");
					map.put("type", "");// 科目号
				} else if(flow.getAcctTitle().equals("01010301112")){
						map.put("type", "借款成功（放款）");// 科目号
					
				} else{
					map.put("type", flow.getMemo() == null ? "" : flow.getMemo().substring(5, flow.getMemo().length()));// 科目号
				}

				if (i == 0) {
					if (checkIsOut(flow, acounts)) {
						map.put("out", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存出
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
					} else {
						map.put("in", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存入
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
					}
					temp = flow.getTradeAmount();
					inOutFlag = checkIsOut(flow, acounts);
				} else {

					if (checkIsOut(flow, acounts)) {
						map.put("out", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存出
					} else {
						map.put("in", ObjectFormatUtil.formatCurrency(flow.getTradeAmount()));// 存入
					}
					if (inOutFlag) {
						amount = Calculator.add(amount, temp);
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
						temp = flow.getTradeAmount();
						inOutFlag = checkIsOut(flow, acounts);

					} else {
						amount = Calculator.sub(amount, temp);
						map.put("amount", ObjectFormatUtil.formatCurrency(amount));
						temp = flow.getTradeAmount();
						inOutFlag = checkIsOut(flow, acounts);

					}
				}
				map.put("doubleAmount", amount);
				map.put("code", flow.getTradeNo());
				voList.add(map);

			}
		}
		FundFlowVO fundFlowVO = new FundFlowVO();
		fundFlowVO.setFlowList(voList);
		return fundFlowVO;
	}
	
	/**
	 * 根据总账号获取4个分账号
	 */
	private Map<String, String> initActLedgerAcounts(String total_acct) {
		Map<String, String> acounts = new HashMap<String, String>();
		String sql = "select act.account||'+'||act.busiType from AcTLedger act where act.totalAccountId=" + total_acct;
		Query query = em.createQuery(sql);
		List result = query.getResultList();
		for (int i = 0, len = result.size(); i < len; i++) {
			String temp = (String) result.get(i);
			acounts.put(temp.substring(0, temp.length() - 2), temp.substring(temp.length() - 1, temp.length()));
		}
		return acounts;
	}
	
	private String getTotalAcctId(BigDecimal userId) {
		// String
		// sql="select c.total_acct from USERS u,AC_T_CUSTOMER c where  u.t_customer_id=c.id and u.user_id=:userId";
		String sql = "select u.t_customer_id from users u where u.user_id='" + userId + "'";
		Query query = em.createNativeQuery(sql);
		List l = query.getResultList();
		if (l != null && l.size() > 0 && l.get(0) != null) {
			BigDecimal t_customer_id = (BigDecimal) l.get(0);
			if (t_customer_id == null) {
				// throw new RuntimeException("用户表与核心客户信息表数据不同步,用户ID："+userId);
			}
			return t_customer_id.toString();
		}
		return "";
	}
	
	/**
	 * 判断流水是否为 流出
	 */
	public boolean checkIsOut(AcTFlow flow, Map<String, String> acounts) {
		if (acounts.containsKey(flow.getAccount())) {
			if (acounts.containsKey(flow.getAppoAcct())) {
				// 4==>5操作科目：投标冻结
				if (acounts.get(flow.getAccount()).equals("4") && acounts.get(flow.getAppoAcct()).equals("5")) {
					return true;
					// 5==>4操作科目：投标解冻
				} else if (acounts.get(flow.getAccount()).equals("5") && acounts.get(flow.getAppoAcct()).equals("4")) {
					return false;
				}

			} else {
				// 包含提现冻结
				return true;

			}
		}
		// 包含提现解冻
		return false;
	}

}
