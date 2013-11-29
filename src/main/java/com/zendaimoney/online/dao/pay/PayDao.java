package com.zendaimoney.online.dao.pay;

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

import com.zendaimoney.online.dao.common.BeanTools;
import com.zendaimoney.online.dao.common.VO;
import com.zendaimoney.online.entity.AcTLedgerVO;
import com.zendaimoney.online.entity.pay.PayAcTLedger;
import com.zendaimoney.online.entity.pay.PayExtractNote;


/**
 * 资金详情查询
 * @author mayb
 *
 */
@Component
public class PayDao {
	@PersistenceContext
	private EntityManager em;
	
	private static Logger logger = LoggerFactory.getLogger(PayDao.class);

	/**
	 * 根据邮箱获取用户ID
	 * 2013-3-6 下午2:00:08 by HuYaHui
	 * @param email
	 * @return
	 */
	public long getUserIdByEmail(String email){
		Object obj= em.createNativeQuery("select user_id from USERS where email=?").setParameter(1, email).getSingleResult();
		return Long.valueOf(obj+"");
	}
	
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
	 * 根据用户ID，验证是否已经通过身份证校验
	 * 2012-12-21 上午9:56:51 by HuYaHui
	 * @param userId
	 * 			用户ID
	 * @return
	 */
	public long checkExistsByUserId(BigDecimal userId){
		StringBuilder sql=new StringBuilder("select count(user_id) from id5_check where check_status_code=3 and user_id=("+
				"select user_id from users where isapprove_card=1 and user_id=?)");
		Query query=em.createNativeQuery(sql.toString());
		query.setParameter(1, userId);
		Object rtnObj=query.getSingleResult();
		if(rtnObj==null){
			return 0;
		}
		return Long.valueOf(rtnObj+"");
	}
	
	public PayExtractNote queryDeaultExtractNote(BigDecimal userId)
	{
		String sql="select pay from PayExtractNote pay where pay.userId="+userId+" and pay.verifyStatus=1";
		Query query=em.createQuery(sql);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			PayExtractNote payExtractNote=(PayExtractNote)l.get(0);
			return payExtractNote;
		}
		return null;
	}
	/**
	 * 查询核心客户信息
	 * @param userId
	 * @return
	 */
//	public PayAcTCustomer getPayAcTCustomer(BigDecimal userId){
//		String sql="select c from Users u,PayAcTCustomer c where u.TCustomerId=c.id and u.userId="+userId;
//		Query query=em.createQuery(sql);
//		List l=query.getResultList();
//		if(l!=null&&l.size()>0&&l.get(0)!=null){
//			PayAcTCustomer payAcTCustomer=(PayAcTCustomer)l.get(0);
//			if(payAcTCustomer==null){
//				//throw new RuntimeException("->网站用户表与核心客户表数据不同步!");
//			}
//			return payAcTCustomer;
//		}
//		return null;
//	}
	

	/**
	 * 查询分账信息
	 * @param userId
	 * @return
	 */
	public PayAcTLedger getPayAcTLedger(Long userId,String type){
		String sql="select l from PayAcTLedger l where  l.busiType='"+type+"' and l.totalAccountId=(select c.id from PayAcTCustomer c where c.id=(select u.TCustomerId from Users u where u.userId="+userId+"))";
		Query query=em.createQuery(sql);
		List l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			PayAcTLedger payAcTLedger=(PayAcTLedger)l.get(0);
			if(payAcTLedger==null){
				throw new RuntimeException("->网站用户表与分账信息表数据不同步!");
			}
			return payAcTLedger;
		}
		return null;
	}	
	
	/**
	 * 查询分账信息
	 * @param userId
	 * @return
	 *  
	 */
	public AcTLedgerVO getPayAcTLedgerNew(Long userId,String type){
		String sql="select * from AC_T_LEDGER l where l.BUSI_TYPE=? and l.TOTAL_ACCOUNT_ID=(select u.T_CUSTOMER_ID from users u where u.user_id=?)";
		Query query=em.createNativeQuery(sql,AcTLedgerVO.class).setParameter(1, type).setParameter(2, userId);
		List<AcTLedgerVO> l=query.getResultList();
		if(l!=null&&l.size()>0&&l.get(0)!=null){
			AcTLedgerVO payAcTLedger=l.get(0);
			if(payAcTLedger==null){
				throw new RuntimeException("->网站用户表与分账信息表数据不同步!");
			}
			return payAcTLedger;
		}
		return null;
	}
	
	/**
	 * 用户对应的提款记录
	 * 2012-11-15 下午2:00:50 by HuYaHui 
	 * @param userId
	 * @return
	 */
	public Long findByUserId(BigDecimal userId){
		Long rtnCount=0l;
		try {
			String sql="select count(l.userId) from PayExtractNote l where l.verifyStatus=0 and l.userId=?";
			Query query=em.createQuery(sql);
			query.setParameter(1,  userId);
			Object rtnObj=query.getSingleResult();
			if(rtnObj!=null){
				rtnCount=(Long)query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnCount;
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
}
