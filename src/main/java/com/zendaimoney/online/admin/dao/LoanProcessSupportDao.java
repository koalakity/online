package com.zendaimoney.online.admin.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.entity.AcTCustomerAdminVO;
import com.zendaimoney.online.admin.entity.AcTLedgerAdminVO;
import com.zendaimoney.online.dao.common.BeanTools;
import com.zendaimoney.online.dao.common.VO;


/**
 * 资金详情查询
 * @author mayb
 *
 */
@Component
public class LoanProcessSupportDao {
	@PersistenceContext
	private EntityManager em;
	
	private static Logger logger = LoggerFactory.getLogger(LoanProcessSupportDao.class);
	
	/**
	 * 根据条件更新某个对象
	 * 2013-1-22 下午2:38:44 by HuYaHui 
	 * @param obj
	 * 			要更新的对象（set参数）
	 * @param param
	 * 			更新条件(where条件)
	 * @return
	 * 			失败：0|成功：1
	 * update tableName set xxx=xxx where aaa=aaa
	 */
	public int update(Object obj,Object param){
		VO vo=BeanTools.getInstance().getVOColumnMap(obj);
		VO paramvo=BeanTools.getInstance().getVOColumnMap(param);
		Map<String, Object> setMap=vo.getMap();
		Map<String, Object> paramMap=paramvo.getMap();
		if(setMap==null || setMap.size()==0){
			logger.error("set参数为空！");
			return 0;
		}else if(paramMap==null || paramMap.size()==0){
			logger.error("where条件为空！");
			return 0;
		}
		List<Object> value_list=new ArrayList<Object>();
		StringBuilder sb=new StringBuilder("update ");
		sb.append(vo.getTableName()).append(" set ");
		int index=0;
		for(String key:setMap.keySet()){
			if(index++==0){
				sb.append("").append(key).append("=?");
				value_list.add(replaceStr(setMap.get(key)));
				continue;
			}
			sb.append(" ,").append(key).append("=?");
			value_list.add(replaceStr(setMap.get(key)));
		}
		sb.append(" where 1=1");
		for(String key:paramMap.keySet()){
			sb.append(" and ").append(key).append("=?");
			value_list.add(replaceStr(paramMap.get(key)));
		}
		Query query=em.createNativeQuery(sb.toString());
		for(int i=0;i<value_list.size();i++){
			query.setParameter(i+1, value_list.get(i));
		}
		logger.info("执行的sql："+sb.toString());
		return query.executeUpdate();
	}
	
	private Object replaceStr(Object str){
		if(str.getClass() != String.class){
			return str;
		}
		String rtnStr=(str+"").replace("'", "\"");
		return rtnStr;
	}

	/**
	 * 根据用户ID查询手机是否绑定
	 * 2013-1-5 下午5:21:02 by HuYaHui 
	 * @param userId
	 * 			用户ID
	 * @return
	 * 			0未绑定，1已绑定
	 */
	public Object checkUserPhoneByUserId(BigDecimal userId){
		String sql="select count(user_id) from users where user_id=? and isapprove_phone=1";
		return em.createNativeQuery(sql).setParameter(1, userId).getSingleResult();
	}
	
	
	

	
	/**
	 * 查询分账信息
	 * @param userId
	 * @return
	 *  
	 */
	public AcTLedgerAdminVO getAcTLedger(Long userId,String type){
		String sql="select * from AC_T_LEDGER l where l.BUSI_TYPE=? and l.TOTAL_ACCOUNT_ID=(select c.id from AC_T_CUSTOMER c where c.id=(select u.T_CUSTOMER_ID from users u where u.user_id=?))";
		Query query=em.createNativeQuery(sql,AcTLedgerAdminVO.class).setParameter(1, type).setParameter(2, userId);
		List<AcTLedgerAdminVO> l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			AcTLedgerAdminVO acTLedger=l.get(0);
			if(acTLedger==null){
				throw new RuntimeException("->网站用户表与分账信息表数据不同步!");
			}
			return acTLedger;
		}
		return null;
	}
	
	
	
	/**
	 * 根据原记录的PAY_BACK_AMT和id作为条件更新金额相关字段 ,返回修改几行
	 * 2012-11-15 下午1:05:59 by HuYaHui 
	 * @param act_4
	 * 			id
	 * 			amount:账户总额
	 * 			payBackAmt:冻结金额
	 * @return
	 * 			影响行数
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public int updateAmountAndPayBackAmtByIdAndAmount(Double payBackAmt,Double amount,Long id,double frzeeAmount){
		int count=0;
		Query update=em.createNativeQuery("update AC_T_LEDGER set AMOUNT=?,PAY_BACK_AMT=? where id=? and (PAY_BACK_AMT is null or PAY_BACK_AMT=?)");
		
		update.setParameter(1, amount);
		update.setParameter(2, payBackAmt);
		update.setParameter(3, id);
		update.setParameter(4, frzeeAmount);
		count=update.executeUpdate();
		return count;
	}
	
	
	/**
	 * @author Ray
	 * @date 2013-2-25 上午11:01:40
	 * @param loanId 借款ID，主键
	 * @param status借款状态
	 * @return 影响行数
	 * description:
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public int updateLoanInfoByIdAndStatus(Long loanId,Long status){
		int count=0;
		Query update=em.createNativeQuery("update LOAN_INFO l set l.status=? where l.loan_id=? and (l.status=1 or l.status=2)");
		update.setParameter(1, status);
		update.setParameter(2, loanId);
		count=update.executeUpdate();
		return count;
	}
	
	
	/**
	 * @author Ray
	 * @date 2013-2-26 上午10:29:40
	 * @param id  主键ID
	 * @param amount 修改成什么值
	 * @return
	 * description:
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public int updateAcTLedger(Long id,BigDecimal amount){
		int count=0;
		Query update=em.createNativeQuery("update AC_T_LEDGER set AMOUNT=AMOUNT-? where id=? and amount-?>=0");
		update.setParameter(1, amount);
		update.setParameter(2, id);
		update.setParameter(3, amount);
		count=update.executeUpdate();
		logger.info("分账5解冻操作执行影响行数："+count);
		return count;
	}
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public int updateLoanInfo(Long loanId,Long status){
		int count=0;
		Query update=em.createNativeQuery("update LOAN_INFO set STATUS = ? where LOAN_ID = ? and STATUS != 4");
		update.setParameter(1, status);
		update.setParameter(2, loanId);
		count=update.executeUpdate();
		return count;
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public AcTCustomerAdminVO findAcTCustomerByUserid(Long userId){
		String sql="select * from AC_T_CUSTOMER c where c.id=(select u.T_CUSTOMER_ID from users u where u.user_id=?)";
		Query query=em.createNativeQuery(sql,AcTCustomerAdminVO.class).setParameter(1, userId);
		List<AcTCustomerAdminVO> l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			AcTCustomerAdminVO acTCustomer=l.get(0);
			if(acTCustomer==null){
				throw new RuntimeException("用户不存在，用户ID:"+userId);
			}
			return acTCustomer;
		}
		return null;
	}
	
	
	
}
