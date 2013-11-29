package com.zendaimoney.online.dao.financial;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.entity.financial.FinancialLoanInfo;
import com.zendaimoney.online.entity.financial.FinancialUserCreditNote;
import com.zendaimoney.online.entity.financial.FinancialUsers;

@Component
public class LoanInfoDaoImpl implements LoanInfoCustomDao {

	@PersistenceContext
	private EntityManager em;

	// key:属性在数组中的索引,value:对象的属性
	Map<Integer, String> fieldMapping = new HashMap<Integer, String>();

	public LoanInfoDaoImpl() {
		fieldMapping.put(0, "loanId");
		fieldMapping.put(1, "userId");
		fieldMapping.put(2, "loanTitle");
		fieldMapping.put(3, "loanUse");
		fieldMapping.put(4, "loanAmount");
		fieldMapping.put(5, "loanDuration");
		fieldMapping.put(6, "yearRate");
		fieldMapping.put(7, "paymentMethod");
		fieldMapping.put(8, "raiseDuration");
		fieldMapping.put(9, "monthReturnPrincipalandinter");
		fieldMapping.put(10, "monthManageCost");
		fieldMapping.put(11, "releaseTime");
		fieldMapping.put(12, "status");
		fieldMapping.put(13, "description");
		fieldMapping.put(14, "isShowAge");
		fieldMapping.put(15, "isShowSex");
		fieldMapping.put(16, "isShowDegree");
		fieldMapping.put(17, "isShowSchool");
		fieldMapping.put(18, "isShowEntranceYear");
		fieldMapping.put(19, "isShowWorkCity");
		fieldMapping.put(20, "isShowVocation");
		fieldMapping.put(21, "isShowCompanyScale");
		fieldMapping.put(22, "isShowOffice");
		fieldMapping.put(23, "isShowWorkYear");
		fieldMapping.put(24, "isShowMarry");
		fieldMapping.put(25, "isShowHaveHouse");
		fieldMapping.put(26, "isShowHouseLoan");
		fieldMapping.put(27, "isShowHaveCar");
		fieldMapping.put(28, "isShowCarLoan");
		fieldMapping.put(29, "releaseStatus");
		fieldMapping.put(30, "login_name");// 自定义字段
		fieldMapping.put(31, "credit_grade");// 自定义字段
		fieldMapping.put(32, "startInvestTime");// 自定义字段

	}

	/**
	 * 根据status或者releaseStatus统计记录数(支持或者，并且) 2012-12-5 上午10:54:15 by HuYaHui
	 * 
	 * @param status
	 * @param releaseStatus
	 * @return
	 */
	public Object countFinancialLoanInfoByStatusOrReleaseStatus(BigDecimal[] status, BigDecimal releaseStatus) {
		StringBuilder sb = new StringBuilder("select count(loan_id) from loan_info where 1=1");
		if (status != null) {
			if (status.length == 1) {
				sb.append(" and status=").append(status[0]);
			} else if (status.length > 1) {
				sb.append(" and status in(").append(status[0]);
				for (int i = 1; i < status.length; i++) {
					sb.append(",").append(status[i]);
				}
				sb.append(")");
			}
		}
		if (releaseStatus != null && !releaseStatus.equals(BigDecimal.ZERO)) {
			sb.append(" and release_status=").append(releaseStatus);
		}
		javax.persistence.Query query = em.createNativeQuery(sb.toString());
		return query.getSingleResult();
	}

	/**
	 * 根据status或者releaseStatus，分页查询(或者，并且都可以) 2012-12-5 上午9:47:28 by HuYaHui
	 * 
	 * @param offset
	 *            从第几行开始
	 * @param pagesize
	 *            每页显示的数量
	 * @param releaseStatus
	 *            select
	 *            info.loan_id,info.user_id,info.loan_title,info.loan_use,
	 *            info.loan_amount
	 *            ,info.loan_duration,info.year_rate,info.payment_method
	 *            ,info.raise_duration
	 *            ,info.month_return_principalandinter,info.month_manage_cost
	 *            ,info
	 *            .release_time,info.status,info.description,info.is_show_age
	 *            ,info
	 *            .is_show_sex,info.is_show_degree,info.is_show_school,info.
	 *            is_show_entrance_year
	 *            ,info.is_show_work_city,info.is_show_vocation
	 *            ,info.is_show_company_scale
	 *            ,info.is_show_office,info.is_show_work_year
	 *            ,info.is_show_marry,
	 *            info.is_show_hava_house,info.is_show_house_loan
	 *            ,info.is_show_hava_car
	 *            ,info.is_show_car_loan,info.release_status, u.login_name,
	 *            c.credit_grade from ( select * from ( select row_.*, rownum
	 *            rownum_ from (select * from loan_info where ???参数为动态组成 order
	 *            by release_time desc) row_ where rownum <= 10 )where rownum_ >
	 *            0 )info ,users u,user_credit_note c where
	 *            info.user_id=u.user_id and info.user_id=c.user_id
	 * 
	 * @return
	 */
	public List<FinancialLoanInfo> findByStatusAndReleaseStatusOrderByRTime(int offset, int pagesize, BigDecimal[] status, BigDecimal releaseStatus) {
		StringBuilder sb = new StringBuilder(
				"select info.loan_id,info.user_id,info.loan_title,info.loan_use,info.loan_amount,info.loan_duration,info.year_rate,info.payment_method,info.raise_duration,info.month_return_principalandinter,info.month_manage_cost,info.release_time,info.status,info.description,info.is_show_age,info.is_show_sex,info.is_show_degree,info.is_show_school,info.is_show_entrance_year,info.is_show_work_city,info.is_show_vocation,info.is_show_company_scale,info.is_show_office,info.is_show_work_year,info.is_show_marry,info.is_show_hava_house,info.is_show_house_loan,info.is_show_hava_car,info.is_show_car_loan,info.release_status,"
						+ "u.login_name," + "c.credit_grade," + "info.start_invest_time" + " from (" + "select * from (" + "select row_.*, rownum rownum_ from" + "(select * from loan_info where 1=1");
		// 拼接参数
		if (status != null) {
			if (status.length == 1) {
				sb.append(" and status=").append(status[0]);
			} else if (status.length > 1) {
				sb.append(" and status in(").append(status[0]);
				for (int i = 1; i < status.length; i++) {
					sb.append(",").append(status[i]);
				}
				sb.append(")");
			}
		}
		if (releaseStatus != null && !releaseStatus.equals(BigDecimal.ZERO)) {
			sb.append(" and release_status=").append(releaseStatus);
		}
		sb.append(" order by release_time desc)" + "row_ where rownum <= ?" + ")where rownum_ > ?" + ")info ,users u,user_credit_note c where info.user_id=u.user_id and info.user_id=c.user_id");
		javax.persistence.Query query = em.createNativeQuery(sb.toString());
		// 第一页
		if ((offset - pagesize) < 0) {
			query.setParameter(1, pagesize);
			query.setParameter(2, 0);
		} else {
			// 不是第一页
			query.setParameter(1, offset + pagesize);
			query.setParameter(2, offset);
		}
		List<Object[]> list = query.getResultList();
		return objectListToList(list);
	}

	/**
	 * 
	 * Description:将Clob对象转换为String对象,Blob处理方式与此相同
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 * @mail sunyujia@yahoo.cn
	 * @blog blog.csdn.ne t/sunyujia/
	 * @since：Oct 1, 2008 7:19:57 PM
	 */
	public static String oracleClob2Str(Clob clob) throws Exception {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
	}

	public List<FinancialLoanInfo> searchLoanBycondition(String creditGrade, String yearRate, String loanDuration, String column, String seq, String p, String tabFlag) {
		StringBuffer hql = new StringBuffer("select u from FinancialLoanInfo u where 1=1 and u.releaseStatus=1 ");
		if ("2".equals(tabFlag)) {
			hql.append(" and u.status =1 ");
			// 即将开始的借款隐藏 modify by jihui 2013-02-08
			// }else if("3".equals(tabFlag)){
			// hql.append(" and u.status =8 ");
		} else if ("3".equals(tabFlag)) {
			hql.append(" and u.status in (2,4,5,6,7) ");
		}
		if (!creditGrade.equals("0")) {
			hql.append("and u.userId.userCreditNote.creditGrade=" + creditGrade);
		}
		if (!yearRate.equals("0")) {
			if (yearRate.equals("1")) {
				hql.append("and u.yearRate <= 0.15");
			} else if (yearRate.equals("2")) {
				hql.append("and u.yearRate between 0.15 and 0.2");
			} else if (yearRate.equals("3")) {
				hql.append("and u.yearRate >= 0.2");
			}
		}
		if (!loanDuration.equals("0")) {
			hql.append("and u.loanDuration = " + loanDuration);
		}
		List<FinancialLoanInfo> list = (List<FinancialLoanInfo>) em.createQuery(hql.toString()).getResultList();
		return list;
	}

	// 排序
	public List<FinancialLoanInfo> findAndSeq(String whereStr, String column, String seq) {
		StringBuffer hql = new StringBuffer();
		if (whereStr.equals("all")) {
			hql.append("select u from FinancialLoanInfo u where u.releaseStatus=1 order by u.");
		} else if (whereStr.equals("loanIng")) {
			hql.append("select u from FinancialLoanInfo u where u.status=1 and u.releaseStatus=1 order by u.");
		} else if (whereStr.equals("future")) {
			hql.append("select u from FinancialLoanInfo u where status=8 order by u.");
		} else if (whereStr.equals("loanEd")) {
			hql.append("select u from FinancialLoanInfo u where u.status=2 or u.status=3 order by u.");
		}
		if (column.equals("loanAmount")) {
			hql.append("loanAmount");
		}
		if (column.equals("yearRate")) {
			hql.append("yearRate");
		}
		if (column.equals("creditGrade")) {
			hql.append("userId.userCreditNote.creditGrade");
		}

		if (seq.equals("asc")) {
			hql.append(" asc");
		}
		if (seq.equals("desc")) {
			hql.append(" desc");
		}

		List<FinancialLoanInfo> list = (List<FinancialLoanInfo>) em.createQuery(hql.toString()).getResultList();
		return list;
	}

	private List<FinancialLoanInfo> objectListToList(List<Object[]> list) {
		List<FinancialLoanInfo> rtnList = new ArrayList<FinancialLoanInfo>();
		for (Object[] obj : list) {
			// 每一行记录
			FinancialLoanInfo rtn = new FinancialLoanInfo();
			for (int i = 0; i < obj.length; i++) {
				Object value = obj[i];
				// 每一列对应到的属性名
				String fieldName = fieldMapping.get(i);
				if (value != null && !value.equals("") && fieldName != null && !fieldName.equals("")) {
					// System.out.println("类型："+value.getClass().getCanonicalName());
					// System.out.println("值："+value);
					// System.out.println("属性："+fieldName);
					try {
						if (fieldName.equals("userId")) {
							// 如果是用户ID，单独处理
							FinancialUsers user = rtn.getUserId();
							if (user == null) {
								user = new FinancialUsers();
							}
							user.setUserId(BigDecimal.valueOf(Long.valueOf(value + "")));
							rtn.setUserId(user);
						} else if (fieldName.equals("description")) {
							// clob类型单独处理
							Clob c = (Clob) value;
							rtn.setDescription(oracleClob2Str(c));
						} else if (fieldName.equals("login_name")) {
							// 登录名
							FinancialUsers user = rtn.getUserId();
							if (user == null) {
								user = new FinancialUsers();
							}
							user.setLoginName(value + "");
							rtn.setUserId(user);
						} else if (fieldName.equals("credit_grade")) {
							// 用户认证级别
							FinancialUsers user = rtn.getUserId();
							if (user == null) {
								user = new FinancialUsers();
							}
							FinancialUserCreditNote userCreditNote = new FinancialUserCreditNote();
							userCreditNote.setCreditGrade(BigDecimal.valueOf(Long.valueOf(value + "")));
							user.setUserCreditNote(userCreditNote);
							rtn.setUserId(user);
						} else {
							BeanUtils.setProperty(rtn, fieldName, value);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			rtnList.add(rtn);
		}
		return rtnList;
	}

}
