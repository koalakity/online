package com.zendaimoney.online.admin.dao.loan;

import java.math.BigDecimal;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.loan.AcTLedgerLoanAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.admin.vo.LoanInfoListForm;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-3 下午2:54:43 operation by:
 *        description:此类实现LoanManagerDaoP，用于还款中列表
 */
public class LoanManagerDaoImpl implements LoanManagerDaoP {

	@PersistenceContext
	private EntityManager em;
	// key:属性在数组中的索引,value:对象的属性
	Map<Integer, String> fieldMapping = new HashMap<Integer, String>();

	public LoanManagerDaoImpl() {
		fieldMapping.put(0, "login_name");
		fieldMapping.put(1, "real_name");
		fieldMapping.put(2, "phone_no");
		fieldMapping.put(3, "accountUsers");
		fieldMapping.put(4, "loanId");
		fieldMapping.put(5, "loanAmount");
		fieldMapping.put(6, "yearRate");
		fieldMapping.put(7, "loanDuration");
		fieldMapping.put(8, "monthReturnPrincipalandinter");
		fieldMapping.put(9, "monthManageCost");
		fieldMapping.put(10, "releaseTime");
		fieldMapping.put(11, "status");
		fieldMapping.put(12, "description");
		fieldMapping.put(13, "releaseStatus");
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
		fieldMapping.put(29, "paymentMethod");
		fieldMapping.put(30, "startInvestTime");
		fieldMapping.put(31, "interest_start");
		fieldMapping.put(32, "next_expiry");

	}

	/**
	 * @author Ray
	 * @date 2012-12-3 下午1:47:59
	 * @param loginName
	 * @param realName
	 * @param phoneNo
	 * @param interestStartMin
	 * @param interestStartMax
	 * @return description:
	 */
	public String totalAmount(String interestStartMin, String interestStartMax, String loginName, String realName, String phoneNo) {
		StringBuilder sb = new StringBuilder("select sum(l.loan_amount) as totalAmount  from LOAN_INFO l,USERS u,User_Info_Person up,AC_T_LEDGER_LOAN a where l.user_id=u.user_id(+) 	and l.user_id=up.user_id(+)	and  a.id=l.ledger_loan_id	and l.status=4 and a.interest_start >=  to_timestamp('" + interestStartMin
				+ "00:00:00', 'yyyy-mm-dd HH24-MI-SS') and a.interest_start <= to_timestamp('" + interestStartMax + "23:59:59', 'yyyy-mm-dd HH24-MI-SS')");
		if (loginName != null && !"".equals(loginName)) {
			sb.append(" and  u.LOGIN_NAME like  '%" + loginName + "%'");
		}
		if (realName != null && !"".endsWith(realName)) {
			sb.append(" and up.REAL_NAME like '%" + realName + "%'");
		}
		if (phoneNo != null && !"".equals(phoneNo)) {
			sb.append(" and up.PHONE_NO ='" + phoneNo + "'");
		}
		Query query = em.createNativeQuery(sb.toString());
		List l = query.getResultList();
		if (l != null && l.size() > 0 && l.get(0) != null) {
			BigDecimal totalAmount = (BigDecimal) l.get(0);
			if (totalAmount == null) {
				// throw new RuntimeException("用户表与核心客户信息表数据不同步,用户ID："+userId);
			}
			return totalAmount.toString();
		}
		return "";
	}

	public List<LoanInfoAdmin> findLoanRepay(LoanInfoListForm loanInfoListForm) {
		if (loanInfoListForm.getInterestStartMin() != null && loanInfoListForm.getInterestStartMax() != null) {
			loanInfoListForm.getInterestStartMax().setHours(23);
			loanInfoListForm.getInterestStartMax().setMinutes(59);
			loanInfoListForm.getInterestStartMax().setSeconds(59);
			// start不为空，end为空
		} else if (loanInfoListForm.getInterestStartMin() != null && loanInfoListForm.getInterestStartMax() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(loanInfoListForm.getInterestStartMin());
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			Date interestStartDate = cal.getTime();
			interestStartDate.setHours(23);
			interestStartDate.setMinutes(59);
			interestStartDate.setSeconds(59);
			// start为空，end不为空
		} else if (loanInfoListForm.getInterestStartMin() == null && loanInfoListForm.getInterestStartMax() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(loanInfoListForm.getInterestStartMax());
			cal.add(Calendar.MONTH, -1);
			cal.add(Calendar.DATE, 1);
			// 2个都为空
		} else {
			// 获取当前时间，将当前时间作为最大时间
			Calendar cal = new GregorianCalendar();
			Date interestStartDateMax = cal.getTime();
			loanInfoListForm.setInterestStartMax(interestStartDateMax);
			// 将当前时间减一个月，并且日期加上一天
			cal.add(Calendar.MONTH, -1);
			cal.add(Calendar.DATE, 1);
			Date interestStartDateMin = cal.getTime();
			interestStartDateMin.setHours(00);
			interestStartDateMin.setMinutes(00);
			interestStartDateMin.setSeconds(00);
			loanInfoListForm.setInterestStartMin(interestStartDateMin);
		}
		if (loanInfoListForm.getInterestStartMin() != null && loanInfoListForm.getInterestStartMax() != null) {
			loanInfoListForm.getInterestStartMax().setHours(23);
			loanInfoListForm.getInterestStartMax().setMinutes(59);
			loanInfoListForm.getInterestStartMax().setSeconds(59);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String interestStartMin = formatter.format(loanInfoListForm.getInterestStartMin());
		String interestStartMax = formatter.format(loanInfoListForm.getInterestStartMax());
		StringBuilder hql = new StringBuilder("select u.login_name,up.real_name,up.phone_no,u.user_id,l.loan_id,l.loan_amount,l.year_rate,l.loan_duration,l.month_return_principalandinter,l.month_manage_cost,l.release_time,l.status,l.description,l.release_status,"
				+ "l.is_show_age,l.is_show_sex,l.is_show_degree,l.is_show_school,l.is_show_entrance_year,l.is_show_work_city,l.is_show_vocation,l.is_show_company_scale,l.is_show_office,l.is_show_work_year,l.is_show_marry,l.is_show_hava_house,l.is_show_house_loan,l.is_show_hava_car,l.is_show_car_loan," + "l.payment_method,l.start_invest_time,ac.interest_start,ac.next_expiry"
				+ " from loan_info l, users u, user_info_person up, ac_t_ledger_loan ac where l.status = 4" + " and l.ledger_loan_id = ac.id " + "and l.user_id = u.user_id(+) and l.user_id=up.user_id(+) " + "and ac.interest_start>=to_date('" + interestStartMin + "','yyyy-mm-dd hh24:mi:ss') " + "and ac.interest_start<=to_date('" + interestStartMax + "','yyyy-mm-dd hh24:mi:ss')");
		if (StringUtils.isNotEmpty(loanInfoListForm.getLoginName())) {
			hql.append(" and u.login_name='" + loanInfoListForm.getLoginName() + "'");
		}
		if (StringUtils.isNotEmpty(loanInfoListForm.getRealName())) {
			hql.append(" and up.real_name='" + loanInfoListForm.getRealName() + "'");
		}
		if (StringUtils.isNotEmpty(loanInfoListForm.getPhoneNo())) {
			hql.append(" and up.phone_no='" + loanInfoListForm.getPhoneNo() + "'");
		}
		hql.append("order by ac.interest_start desc");
		Query query = em.createNativeQuery(hql.toString());
		List<Object[]> list = query.getResultList();
		return objectListToList(list);
	}

	private List<LoanInfoAdmin> objectListToList(List<Object[]> list) {
		List<LoanInfoAdmin> rtnList = new ArrayList<LoanInfoAdmin>();
		for (Object[] obj : list) {
			// 每一行记录
			LoanInfoAdmin rtn = new LoanInfoAdmin();
			for (int i = 0; i < obj.length; i++) {
				Object value = obj[i];
				// 每一列对应到的属性名
				String fieldName = fieldMapping.get(i);
				if (value != null && !value.equals("") && fieldName != null && !fieldName.equals("")) {
					// System.out.println("类型："+value.getClass().getCanonicalName());
					// System.out.println("值："+value);
					// System.out.println("属性："+fieldName);
					try {
						if (fieldName.equals("accountUsers")) {
							// 如果是用户ID，单独处理
							AccountUsersAdmin user = rtn.getAccountUsers();
							if (user == null) {
								user = new AccountUsersAdmin();
							}
							user.setUserId(BigDecimal.valueOf(Long.valueOf(value + "")));
							rtn.setAccountUsers(user);
						} else if (fieldName.equals("description")) {
							// clob类型单独处理
							Clob c = (Clob) value;
							rtn.setDescription(oracleClob2Str(c));
						} else if (fieldName.equals("login_name")) {
							// 登录名
							AccountUsersAdmin user = rtn.getAccountUsers();
							if (user == null) {
								user = new AccountUsersAdmin();
							}
							user.setLoginName(value + "");
							rtn.setAccountUsers(user);
						} else if (fieldName.equals("real_name")) {
							// 用户认证级别
							AccountUsersAdmin user = rtn.getAccountUsers();
							if (user == null) {
								user = new AccountUsersAdmin();
							}
							AccountUserInfoPersonAdmin userInfoPerson = rtn.getAccountUsers().getUserInfoPerson();
							if (userInfoPerson == null) {
								userInfoPerson = new AccountUserInfoPersonAdmin();
							}
							userInfoPerson.setRealName(value + "");
							user.setUserInfoPerson(userInfoPerson);
							rtn.setAccountUsers(user);
						} else if (fieldName.equals("phone_no")) {
							// 用户认证级别
							AccountUsersAdmin user = rtn.getAccountUsers();
							if (user == null) {
								user = new AccountUsersAdmin();
							}
							AccountUserInfoPersonAdmin userInfoPerson = rtn.getAccountUsers().getUserInfoPerson();
							if (userInfoPerson == null) {
								userInfoPerson = new AccountUserInfoPersonAdmin();
							}
							userInfoPerson.setPhoneNo(value + "");
							user.setUserInfoPerson(userInfoPerson);
							rtn.setAccountUsers(user);
						} else if (fieldName.equals("description")) {
							// clob类型单独处理
							Clob c = (Clob) value;
							rtn.setDescription(oracleClob2Str(c));
						} else if (fieldName.equals("interest_start")) {
							AcTLedgerLoanAdmin loanAcTLedgerLoan = rtn.getLoanAcTLedgerLoan();
							if (loanAcTLedgerLoan == null) {
								loanAcTLedgerLoan = new AcTLedgerLoanAdmin();
							}
							loanAcTLedgerLoan.setInterestStart((Date) value);
							rtn.setLoanAcTLedgerLoan(loanAcTLedgerLoan);
						} else if (fieldName.equals("next_expiry")) {
							AcTLedgerLoanAdmin loanAcTLedgerLoan = rtn.getLoanAcTLedgerLoan();
							if (loanAcTLedgerLoan == null) {
								loanAcTLedgerLoan = new AcTLedgerLoanAdmin();
							}
							loanAcTLedgerLoan.setNextExpiry((Date) value);
							rtn.setLoanAcTLedgerLoan(loanAcTLedgerLoan);
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
}
