package com.zendaimoney.online.admin.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.online.admin.entity.RepayFlowDetailRepVO;
import com.zendaimoney.online.admin.service.RepayFlowDetailRepService;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.dto.PayBackInfoDTO;
import com.zendaimoney.online.service.newLoanManagement.PayBackSupporService;

public class RepayFlowDetailRepDAOImpl implements RepayFlowDetailRepDAOCustom{

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PayBackSupporService payBackSupporService;
	private Date dt=null;
	@Autowired
	private RepayFlowDetailRepService repayFlowDetailRepService;

	/**
	 * 根据ID集合查询数据
	 * 2013-3-22 上午10:25:41 by HuYaHui
	 * @param idList
	 * @return
	 */
	public List<RepayFlowDetailRepVO> findByIdList(List<Long> idList){
		StringBuilder sb=new StringBuilder("select u from RepayFlowDetailRepVO u where 1=1");
		for(int i=0;i<idList.size();i++){
			if(i==0){
				sb.append(" and id in("+idList.get(i));
			}else{
				sb.append(" ,").append(idList.get(i));
			}
		}
		sb.append(")");
		return em.createQuery(sb.toString()).getResultList();
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
	public List<RepayFlowDetailRepVO> findByCondition(String name,String idCard,String phone,long loadId,Date start,Date end,int page,int rows){
		StringBuilder sb=new StringBuilder("select u from RepayFlowDetailRepVO u where 1=1");
		List<Object> valueList=new ArrayList<Object>();
		if(name!=null && !name.equals("")){
			sb.append(" and realeName=?");
			valueList.add(name);
		}
		if(idCard!=null && !idCard.equals("")){
			sb.append(" and identityNo=?");
			valueList.add(idCard);
		}
		if(phone!=null && !phone.equals("")){
			sb.append(" and phoneNo=?");
			valueList.add(phone);
		}
		if(loadId!=0){
			sb.append(" and loanId=?");
			valueList.add(loadId);
		}
		if(start!=null && !start.equals("")){
			start.setHours(0);
			start.setMinutes(0);
			start.setSeconds(0);
			sb.append(" and shouldRepayDay>= ?");
			valueList.add(start);
		}
		if(end!=null && !end.equals("")){
			end.setHours(0);
			end.setMinutes(0);
			end.setSeconds(0);
			sb.append(" and shouldRepayDay<= ?");
			valueList.add(end);
		}
		sb.append("order by editDate,channelName");
		Query query=em.createQuery(sb.toString());
		if(valueList.size()>0){
			for(int i=1;i<=valueList.size();i++){
				Object value=valueList.get(i-1);
				query.setParameter(i, value);
			}
		}
		
		if(rows!=0){
			query.setFirstResult((page-1)*rows);
			query.setMaxResults(rows);
		}
		return query.getResultList();
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
	public long countByCondition(String name,String idCard,String phone,long loadId,Date start,Date end){
		StringBuilder sb=new StringBuilder("select count(id) from RepayFlowDetailRepVO where 1=1");
		List<Object> valueList=new ArrayList<Object>();
		if(name!=null && !name.equals("")){
			sb.append(" and realeName=?");
			valueList.add(name);
		}
		if(idCard!=null && !idCard.equals("")){
			sb.append(" and identityNo=?");
			valueList.add(idCard);
		}
		if(phone!=null && !phone.equals("")){
			sb.append(" and phoneNo=?");
			valueList.add(phone);
		}
		if(loadId!=0){
			sb.append(" and loanId=?");
			valueList.add(loadId);
		}
		if(start!=null && !start.equals("")){
			sb.append(" and shouldRepayDay>= ?");
			valueList.add(start);
		}
		if(end!=null && !end.equals("")){
			sb.append(" and shouldRepayDay<= ?");
			valueList.add(end);
		}
		Query query=em.createQuery(sb.toString());
		if(valueList.size()>0){
			for(int i=1;i<=valueList.size();i++){
				Object value=valueList.get(i-1);
				query.setParameter(i, value);
			}
		}
		return Long.valueOf(query.getSingleResult()+"");
	}
	
	/**
	 * 根据用户ID，获取对应俩级渠道信息
	 * 2013-3-21 下午4:23:12 by HuYaHui
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getChannelNameByUserId(Long userId){
		Map<Long,String> userIdToChannelName=new HashMap<Long, String>();
		if(dt==null){
			dt=new Date();
		}
		if(DateUtil.subDays(new Date(), dt)<=0){
			String name=userIdToChannelName.get(userId);
			if(name!=null && !name.equals("")){
				//如果用户渠道缓存时间没有超过1天，并且已经查询到某用户对应渠道信息,直接返回
				return name;
			}
		}
		
		//如果保存超过1天，重新查询
		dt=new Date();
		
		//查询所有一级渠道信息
		List<Object[]> parChannelList=
				em.createNativeQuery("select id,name from channel_info where parent_id is null or parent_id =''").getResultList();
		//key:父类渠道ID，value:父渠道名称
		Map<Object,Object> map=new HashMap<Object, Object>();
		for(Object[] obj:parChannelList){
			map.put(obj[0], obj[1]);
		}
		
		List<Object[]> chList=em.createNativeQuery("select parent_id,name from channel_info where id=(select channelinfo_id from users where user_id=?)")
					.setParameter(1, userId).getResultList();
		if(chList==null || chList.size()==0){
			return "";
		}
		//根据一级渠道名称获取对应名称
		String parName=map.get(chList.get(0)[0])+"";
		//二级渠道名称
		String chName=chList.get(0)[1]+"";
		userIdToChannelName.put(userId, parName+">>"+chName);
		return userIdToChannelName.get(userId);
	}
	private List<RepayFlowDetailRepVO> getRepayFlowDetailRepList(List<Object[]> objList){
		
		List<RepayFlowDetailRepVO> rtnList=new ArrayList<RepayFlowDetailRepVO>();
		for(Object[] _obj:objList){
			RepayFlowDetailRepVO vo=new RepayFlowDetailRepVO();
			vo.setLoanId(Long.valueOf(_obj[0]+""));
			vo.setUserId(Long.valueOf(_obj[1]+""));
			vo.setRealeName(_obj[2]+"");
			vo.setIdentityNo(_obj[3]+"");
			
			if(_obj[4]!=null){
				vo.setPhoneNo(_obj[4]+"");
			}
			vo.setLoanUse(_obj[5]+"");
			vo.setLoanAmount(new BigDecimal(_obj[6]+""));
			vo.setLoanDuration(_obj[7]+"");
			vo.setCurrNum(_obj[8]+"");
			
			if(_obj[9]!=null){
				vo.setShouldRepayDay(DateUtil.getDateYYYYMMDD((Date)_obj[9],DateUtil.YYYYMMDDHH_MM_SS));	
			}
			
			vo.setTotalPayAmt(new BigDecimal(_obj[10]+""));
			vo.setCurrShouldPayPrincipe(new BigDecimal(_obj[11]+""));
			vo.setCurrShouldPayInterest(new BigDecimal(_obj[12]+""));
			vo.setShouldPayManagerFee(new BigDecimal(_obj[13]+""));
			
			if(_obj[14]!=null){
				vo.setEditDate(DateUtil.getDateYYYYMMDD((Date)_obj[14],DateUtil.YYYYMMDDHH_MM_SS));
			}
			
			vo.setPayAmt(new BigDecimal(_obj[15]+""));
			if(_obj[16]!=null){
				if(_obj[16].toString().equals("2")){
					vo.setRepayStatus("4");	
				}else{
					vo.setRepayStatus(_obj[16].toString());
				}
			}
			int tempStatus = ((BigDecimal) (_obj[16]==null?BigDecimal.ZERO:_obj[16])).intValue();
//			if(tempStatus==1){
//				BigDecimal shouldPayAmt = BigDecimalUtil.add(BigDecimalUtil.add(vo.getCurrShouldPayPrincipe(), vo.getCurrShouldPayInterest()),vo.getShouldPayManagerFee());
//				BigDecimal onceBreachPenalty = BigDecimalUtil.sub(vo.getTotalPayAmt(), shouldPayAmt);
//				//提前还款违约金
//				if(onceBreachPenalty.compareTo(BigDecimal.ZERO)==1){
//					vo.setOnceBreachPenalty(onceBreachPenalty);
//				}
//			}else if (tempStatus==2){
//				if(vo.getPayAmt().compareTo(vo.getCurrShouldPayPrincipe())==1){
//					vo.setOnceBreachPenalty(BigDecimalUtil.sub(vo.getPayAmt(), vo.getCurrShouldPayPrincipe()));
//				}
//			}
			vo.setOverdueFineInterest(new BigDecimal(_obj[17]+""));
			vo.setOverdueManagerFee(new BigDecimal(_obj[18]+""));
			vo.setOverdueInterest(new BigDecimal(_obj[19]+""));
			vo.setOverduePrincipal(new BigDecimal(_obj[20]+""));
			vo.setOverdueFineAmt(new BigDecimal(_obj[21]+""));
			vo.setCurrManagerFee(new BigDecimal(_obj[22]+""));			
			vo.setPrincipal(new BigDecimal(_obj[23]+""));
			vo.setInterest(new BigDecimal(_obj[24]+""));
			vo.setOncePayPrincipal(new BigDecimal(_obj[25]+""));
			vo.setOnceBreachPenalty(new BigDecimal(_obj[26]+""));
			vo.setChannelName(getChannelNameByUserId(vo.getUserId()));
			rtnList.add(vo);
		}
		return rtnList;
	}
	
	 private void step1(){
		//查询所有正常还款的集合
			StringBuffer normalRepayDetailSql = new StringBuffer("select "
					+ "l.loan_id  \"贷款编号\"," 
					+ "u.user_id           \"用户ID\","
					+ "u.real_name         \"客户姓名\","
					+ "u.identity_no       \"身份证\","
					+ "u.Phone_No          \"手机号码\","
					+ "l.loan_use          \"贷款种类\","
					+ "l.loan_amount       \"合同金额\","
					+ "l.loan_duration     \"期限\","
					+ "acv.curr_num         \"当前期数\","
					+ "acv.repay_day        \"本月应还款日\","
					+ "acv.amt             \"本期应还总额\","
					+ "acv.principal_amt   \"本期应还本金\","
					+ "acv.interest_amt    \"本期应还利息\","
					+ "l.month_manage_cost \"应还管理费\","
					+ "acv.edit_date       \"实际还款日\","
					+ "acv.amt             \"实际还款总额\","
					+ "acv.repay_status   \"还款性质(正常，未还)\","
					+ "0                   \"逾期罚息\","
					+ "0                   \"逾期管理费\","
					+ "0                   \"逾期利息\","
					+ "0                   \"逾期本金\","
					+ "0                   \"逾期违约金\","
					+ "l.month_manage_cost \"当期管理费\","
					+ "acv.principal_amt   \"当期本金\","
					+ "acv.interest_amt    \"当期利息\","
					+ "0                   \"提前还款本金\","
					+ "0                   \"提前还款违约金\""
					+ " from ac_t_virtual_cash_flow acv,"
					+ "ac_t_ledger_loan       ac," + "loan_info              l,"
					+ "user_info_person       u" + " where acv.loan_id = ac.id(+)"
					+ " and ac.id = l.ledger_loan_id(+)"
					+ " and l.user_id = u.user_id(+)" + " and acv.id in"
					+ "   (select acv.id"
					+ "       from ac_t_virtual_cash_flow acv"
					+ "     where acv.repay_status=1" + ")");
			List<Object[]> normalRepayDetailList=em.createNativeQuery(normalRepayDetailSql.toString()).getResultList();
			System.out.println("查询已经还款的记录,所有正常还款的集合"+(normalRepayDetailList==null?0:normalRepayDetailList.size()));
			
			List<RepayFlowDetailRepVO> normalRepayDetail=getRepayFlowDetailRepList(normalRepayDetailList);
			for(RepayFlowDetailRepVO obj:normalRepayDetail){
				BigDecimal tmpnum=BigDecimalUtil.add(obj.getCurrShouldPayPrincipe(), obj.getCurrShouldPayInterest());
				BigDecimal _num=BigDecimalUtil.add(tmpnum, obj.getCurrShouldPayInterest());
				//本期应还总额
				BigDecimal amt=obj.getTotalPayAmt();
				if(BigDecimalUtil.compareTo(amt, _num)>0){
					//本期应还总额 > (本期应还本金+本期应还利息+应还管理费)
					
					//设置 提前还款违约金=  vo.getAmt()- (acv.principal_amt+acv.interest_amt+ l.month_manage_cost);
					obj.setOncePayPrincipal(BigDecimalUtil.sub(amt, _num));
				}
				em.persist(obj);
			}
			System.out.println("保存已经还款的记录,所有正常还款的集合!");
	 }

	private void step2() {
		// 查询非正常还款的集合
		// 查询所有一次性提前还款的集合信息,一次性提前还款
		StringBuffer oneTimeEarylyRepaymentSql = new StringBuffer(
				"select l.loan_id           \"贷款编号\","
						+ "u.user_id           \"用户ID\","
						+ "u.real_name         \"客户姓名\","
						+ "u.identity_no       \"身份证\","
						+ "u.Phone_No          \"手机号码\","
						+ "l.loan_use          \"贷款种类\","
						+ "l.loan_amount       \"合同金额\","
						+ "l.loan_duration     \"期限\","
						+ "acv.curr_num         \"当前期数\","
						+ "acv.repay_day        \"本月应还款日\","
						+ "acv.amt             \"本期应还总额\","
						+ "acv.principal_amt   \"本期应还本金\","
						+ "0    \"本期应还利息\"," + "0 \"应还管理费\","
						+ "acv.edit_date       \"实际还款日\","
						+ "acv.amt             \"实际还款总额\","
						+ "acv.repay_status    \"还款性质(正常，未还)\","
						+ "0                   \"逾期罚息\","
						+ "0                   \"逾期管理费\","
						+ "0                   \"逾期利息\","
						+ "0                   \"逾期本金\","
						+ "0                   \"逾期违约金\"," + "0 \"当期管理费\","
						+ "acv.principal_amt   \"当期本金\"," + "0    \"当期利息\","
						+ "acv.principal_amt   \"提前还款本金\","
						+ "0                   \"提前还款违约金\""
						+ " from ac_t_virtual_cash_flow acv,"
						+ "ac_t_ledger_loan       ac,"
						+ "loan_info              l,"
						+ "user_info_person       u"
						+ " where acv.loan_id = ac.id(+)"
						+ " and ac.id = l.ledger_loan_id(+)"
						+ " and l.user_id = u.user_id(+)" + " and acv.id in"
						+ " (select acv.id"
						+ " from ac_t_virtual_cash_flow acv"
						+ " where acv.repay_status=2)");
		List<Object[]> oneTimeEarylyRepaymentList = em.createNativeQuery(
				oneTimeEarylyRepaymentSql.toString()).getResultList();
		System.out.println("查询已经还款的记录,所有一次性提前还款的集合信息"
				+ (oneTimeEarylyRepaymentList == null ? 0
						: oneTimeEarylyRepaymentList.size()));
		
		List<RepayFlowDetailRepVO> list=getRepayFlowDetailRepList(oneTimeEarylyRepaymentList);
		for(RepayFlowDetailRepVO vo:list){
			em.persist(vo);
		}
		System.out.println("保存已经还款的记录,所有一次性提前还款的集合信息");
	}
	private void step3(){
		//逾期还款,初级逾期还款
		StringBuffer primaryOverdueSql = new StringBuffer(
				"select l.loan_id           \"贷款编号\","
						+ "u.user_id           \"用户ID\","
						+ "u.real_name         \"客户姓名\","
						+ "u.identity_no       \"身份证\","
						+ "u.Phone_No          \"手机号码\","
						+ "l.loan_use          \"贷款种类\","
						+ "l.loan_amount       \"合同金额\","
						+ "l.loan_duration     \"期限\","
						+ "acv.curr_num         \"当前期数\","
						+ "acv.repay_day        \"本月应还款日\","
						+ "acv.amt             \"本期应还总额\","
						+ "acv.principal_amt   \"本期应还本金\","
						+ "acv.interest_amt    \"本期应还利息\","
						+ "l.month_manage_cost \"应还管理费\","
						+ "acv.edit_date       \"实际还款日\","
						+ "acv.amt             \"实际还款总额\","
						+ "acv.repay_status    \"还款性质(正常，未还)\","
						+ "acv.over_due_interest_amount           \"逾期罚息\","
						+ "l.month_manage_cost                  \"逾期管理费\","
						+ "acv.interest_amt            \"逾期利息\","
						+ "acv.principal_amt           \"逾期本金\","
						+ "acv.over_due_fine_amount      \"逾期违约金\","
						+ "0 \"当期管理费\"," + "0   \"当期本金\","
						+ "0    \"当期利息\","
						+ "0                   \"提前还款本金\","
						+ "0                   \"提前还款违约金\""
						+ " from ac_t_virtual_cash_flow acv,"
						+ "ac_t_ledger_loan       ac,"
						+ "loan_info              l,"
						+ "user_info_person       u"
						+ " where acv.loan_id = ac.id(+)"
						+ " and ac.id = l.ledger_loan_id(+)"
						+ " and l.user_id = u.user_id(+)" + " and acv.id in"
						+ " (select acv.id"
						+ " from ac_t_virtual_cash_flow acv"
						+ " where acv.repay_status=4"
						+ " and acv.over_due_days>0"
						+ " and acv.over_due_days<=30" + ")"

		);
		List<Object[]> primaryOverdueList=em.createNativeQuery(primaryOverdueSql.toString()).getResultList();
		System.out.println("查询已经还款的记录，初级逾期"+(primaryOverdueList==null?0:primaryOverdueList.size()));

		List<RepayFlowDetailRepVO> list=getRepayFlowDetailRepList(primaryOverdueList);
		for(RepayFlowDetailRepVO vo:list){
			BigDecimal bx=BigDecimalUtil.add(vo.getCurrShouldPayPrincipe(), vo.getCurrShouldPayInterest());
			//设置本月应还总额
			vo.setTotalPayAmt(BigDecimalUtil.add(bx, vo.getShouldPayManagerFee()));
			
			vo.setRepayStatus("2");
			em.persist(vo);
		}
		System.out.println("保存已经还款的记录，初级逾期");
	}
	private void step4(){
		//高级逾期还款
				StringBuffer seniorOverdueSql = new StringBuffer(
						"select l.loan_id           \"贷款编号\","
								+ "u.user_id           \"用户ID\","
								+ "u.real_name         \"客户姓名\","
								+ "u.identity_no       \"身份证\","
								+ "u.Phone_No          \"手机号码\","
								+ "l.loan_use          \"贷款种类\","
								+ "l.loan_amount       \"合同金额\","
								+ "l.loan_duration     \"期限\","
								+ "acv.curr_num         \"当前期数\","
								+ "acv.repay_day        \"本月应还款日\","
								+ "acv.amt             \"本期应还总额\","
								+ "acv.principal_amt   \"本期应还本金\","
								+ "acv.interest_amt    \"本期应还利息\","
								+ "l.month_manage_cost \"应还管理费\","
								+ "acv.edit_date       \"实际还款日\","
								+ "acv.amt             \"实际还款总额\","
								+ "acv.repay_status    \"还款性质(正常，未还)\","
								+ "acv.over_due_interest_amount           \"逾期罚息\","
								+ "l.month_manage_cost                  \"逾期管理费\","
								+ "acv.interest_amt            \"逾期利息\","
								+ "acv.principal_amt           \"逾期本金\","
								+ "acv.over_due_fine_amount      \"逾期违约金\","
								+ "0 \"当期管理费\"," + "0   \"当期本金\","
								+ "0    \"当期利息\","
								+ "0                   \"提前还款本金\","
								+ "0                   \"提前还款违约金\""
								+ " from ac_t_virtual_cash_flow acv,"
								+ "ac_t_ledger_loan       ac,"
								+ "loan_info              l,"
								+ "user_info_person       u"
								+ " where acv.loan_id = ac.id(+)"
								+ " and ac.id = l.ledger_loan_id(+)"
								+ " and l.user_id = u.user_id(+)" + " and acv.id in"
								+ " (select acv.id"
								+ " from ac_t_virtual_cash_flow acv"
								+ " where acv.repay_status=4"
								+ " and acv.over_due_days>30)");
				List<Object[]> seniorOverdueList=em.createNativeQuery(seniorOverdueSql.toString()).getResultList();
				System.out.println("查询已经还款的记录，高级逾期"+(seniorOverdueList==null?0:seniorOverdueList.size()));
			
				List<RepayFlowDetailRepVO> list=getRepayFlowDetailRepList(seniorOverdueList);
				for(RepayFlowDetailRepVO vo:list){
					BigDecimal bx=BigDecimalUtil.add(vo.getCurrShouldPayPrincipe(), vo.getCurrShouldPayInterest());
					//设置本月应还总额
					vo.setTotalPayAmt(BigDecimalUtil.add(bx, vo.getShouldPayManagerFee()));
					vo.setRepayStatus("3");
					em.persist(vo);
				}
				System.out.println("保存已经还款的记录，高级逾期");
	}
	
	private void step5(){
		//2，查询未还款的记录,初级逾期
		StringBuffer notRepaymentSql = new StringBuffer(
				"select l.loan_id           \"贷款编号\","
						+ "u.user_id           \"用户ID\","
						+ "u.real_name         \"客户姓名\","
						+ "u.identity_no       \"身份证\","
						+ "u.Phone_No          \"手机号码\","
						+ "l.loan_use          \"贷款种类\","
						+ "l.loan_amount       \"合同金额\","
						+ "l.loan_duration     \"期限\","
						+ "acv.curr_num         \"当前期数\","
						+ "acv.repay_day        \"本月应还款日\","
						+ "acv.amt             \"本期应还总额\","
						+ "acv.principal_amt   \"本期应还本金\","
						+ "acv.interest_amt    \"本期应还利息\","
						+ "l.month_manage_cost \"应还管理费\","
						+ "''       \"实际还款日\"," + "0            \"实际还款总额\","
						+ "acv.repay_status    \"还款性质(正常，未还)\","
						+ "0           \"逾期罚息\","
						+ "0			                \"逾期管理费\","
						+ "0				            \"逾期利息\","
						+ "0					        \"逾期本金\","
						+ "0     \"逾期违约金\"," + " 0 \"当期管理费\","
						+ " 0   \"当期本金\"," + "0    \"当期利息\","
						+ "0                   \"提前还款本金\","
						+ "0                   \"提前还款违约金\""
						+ " from ac_t_virtual_cash_flow acv,"
						+ " ac_t_ledger_loan       ac,"
						+ " loan_info              l,"
						+ " user_info_person       u"
						+ " where acv.loan_id = ac.id(+)"
						+ " and ac.id = l.ledger_loan_id(+)"
						+ " and l.user_id = u.user_id(+)" + " and acv.id in"
						+ " (select acv.id"
						+ "  from ac_t_virtual_cash_flow acv"
						+ "  where 1=1)");
		List<Object[]> notRepaymentList=em.createNativeQuery(notRepaymentSql.toString()).getResultList();
		System.out.println("查询未还款的记录："+(notRepaymentList==null?0:notRepaymentList.size()));
		List<RepayFlowDetailRepVO> list=getRepayFlowDetailRepList(notRepaymentList);
		
		for(RepayFlowDetailRepVO vo:list){
			
//			Date repayDayObj=vo.getShouldRepayDay();
//			//当前日期
//			Date currdate=DateUtil.getYYYYMMDDDate(new Date());
//			//本月应还款日期
//			Date repayDay=DateUtil.getYYYYMMDDDate(repayDayObj);
//			if(DateUtil.subDays(repayDay, currdate)>0 && DateUtil.subDays(repayDay, currdate)<=30){
//				//30=>本期应还款日期-当前日期>0
//				PayBackInfoDTO dto=payBackSupporService.getPayBackDetailInfo(vo.getLoanId());
//				//逾期罚息
//				vo.setOverdueFineInterest(dto.getOverdueInterest());
//				//逾期违约金
//				vo.setOverdueFineAmt(dto.getOverdueFines());
//			}else if(DateUtil.subDays(repayDay, currdate)>30){
//				PayBackInfoDTO dto=payBackSupporService.getPayBackDetailInfo(vo.getLoanId());
//				//逾期罚息
//				vo.setOverdueFineInterest(dto.getOverdueInterest());
//				//逾期违约金
//				vo.setOverdueFineAmt(dto.getOverdueFines());
//			}
			
			

			BigDecimal bx=BigDecimalUtil.add(vo.getCurrShouldPayPrincipe(), vo.getCurrShouldPayInterest());
			//设置本月应还总额
			vo.setTotalPayAmt(BigDecimalUtil.add(bx, vo.getShouldPayManagerFee()));
			repayFlowDetailRepService.reFreshProgress(vo);
		}
		System.out.println("保存未还款的记录！");
	}
	
	private void delete(){
		em.createNativeQuery("delete from REPAY_FLOW_DETAIL_REP").executeUpdate();
	}
	
	/**
	 * 初始化所有数据
	 * 2013-3-21 下午1:01:29 by HuYaHui 
	 */
	public void initRepayFlowDetailRep(){
		delete();
		
//		step1();
//		
//		step2();
//	
//		step3();
//		
//		step4();
		
		step5();
	}
	
	/*
	 *刷新当日产生的新数据
	 *@paramer 当前日期的前一天
	 */
	public void scanNewloan(String date){


		//2，查询未还款的记录,初级逾期
		StringBuffer notRepaymentSql = new StringBuffer(
				"select l.loan_id           \"贷款编号\","
						+ "u.user_id           \"用户ID\","
						+ "u.real_name         \"客户姓名\","
						+ "u.identity_no       \"身份证\","
						+ "u.Phone_No          \"手机号码\","
						+ "l.loan_use          \"贷款种类\","
						+ "l.loan_amount       \"合同金额\","
						+ "l.loan_duration     \"期限\","
						+ "acv.curr_num         \"当前期数\","
						+ "acv.repay_day        \"本月应还款日\","
						+ "acv.amt             \"本期应还总额\","
						+ "acv.principal_amt   \"本期应还本金\","
						+ "acv.interest_amt    \"本期应还利息\","
						+ "l.month_manage_cost \"应还管理费\","
						+ "acv.edit_date       \"实际还款日\"," + "0            \"实际还款总额\","
						+ "acv.repay_status    \"还款性质(正常，未还)\","
						+ "0           \"逾期罚息\","
						+ "0                 \"逾期管理费\","
						+ "0            \"逾期利息\","
						+ "0        \"逾期本金\","
						+ "0     \"逾期违约金\"," + " 0 \"当期管理费\","
						+ "0   \"当期本金\"," + "0    \"当期利息\","
						+ "0                   \"提前还款本金\","
						+ "0                   \"提前还款违约金\""
						+ " from ac_t_virtual_cash_flow acv,"
						+ " ac_t_ledger_loan       ac,"
						+ " loan_info              l,"
						+ " user_info_person       u"
						+ " where acv.loan_id = ac.id(+)"
						+ " and ac.id = l.ledger_loan_id(+)"
						+ " and l.user_id = u.user_id(+)" + " and acv.id in"
						+ " (select acv.id"
						+ "  from ac_t_virtual_cash_flow acv"
						+ "  where acv.repay_status=0" +
						" and to_char(acv.create_date,'yyyy-MM-dd')='"+date+"')");
		List<Object[]> notRepaymentList=em.createNativeQuery(notRepaymentSql.toString()).getResultList();
		List<RepayFlowDetailRepVO> notRepaymentDetial=getRepayFlowDetailRepList(notRepaymentList);
		System.out.println("查询未还款的记录："+(notRepaymentList==null?0:notRepaymentList.size()));
		for(RepayFlowDetailRepVO obj:notRepaymentDetial){
			em.persist(obj);
		}
	
		
	}
}
