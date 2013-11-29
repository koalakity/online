package com.zendaimoney.online.admin.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.entity.AcTLedgerAdminVO;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.service.common.FlowUtils;
@Component
public class CheckUserInfo {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private FlowUtils flowUtils;
   /**
    * 根据用户email 查询用户个数
    * @param email
    * @return
    */
	public int findByEmail(String email){
		Query query=em.createNativeQuery("select count(1) from USERS u where u.email=?");
		query.setParameter(1, email);
		BigDecimal resultSize = (BigDecimal) query.getResultList().get(0);
		return resultSize.intValue();
	}
	/**
	 * 根据用户email查询用户身份证号
	 * @param email
	 * @return
	 */
	public String findByUserIdentityNoByEmail(String email){
		Query query = em.createNativeQuery("select t.identity_no from USER_INFO_PERSON t where t.user_id=(select u.user_id from USERS u where u.email=?)");
		query.setParameter(1, email);
		List<?> queryList = query.getResultList();
		if(queryList.size()==1){
			return (String) queryList.get(0);
		}else {
			return null;
		}
	}
	
	
	/** 
	 * @author 王腾飞
	 * @date 2013-3-19 上午9:16:15
	 * @param email
	 * @return
	 * description:查找用户冻结金额
	*/
	public AcTLedgerAdminVO findUserFrzeeAmount(BigDecimal userId,String busiType){
		Query query = em.createNativeQuery("select * from AC_T_LEDGER t where t.TOTAL_ACCOUNT_ID=(select u.t_customer_id from USERS u where u.user_id=?) and t.busi_type=?",AcTLedgerAdminVO.class);
		query.setParameter(1, userId).setParameter(2, busiType);
		List<AcTLedgerAdminVO> queryList = query.getResultList();
		if(queryList.size()==1){
			return (AcTLedgerAdminVO) queryList.get(0);
		}else {
			return null;
		}
	}
  
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-5 下午2:42:39
	 * @param email
	 * @param amount
	 * @return
	 * description:更新资金账户插入流水
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public Map<Integer,String> updateUserAcTLedgerAmount(String email,double amount){
		Query userQuery = em.createNativeQuery("select u.user_id from users u where u.email=?");
		userQuery.setParameter(1, email);
		if(userQuery.getResultList()==null||userQuery.getResultList().size()!=1){
			throw new RuntimeException("数据异常找不到用户id，用户email："+email);
		}
		BigDecimal userId = (BigDecimal) userQuery.getResultList().get(0);
		// 1:资金更新条数。2：插入流水ID。3：插入流水条数。4：更新前数据。5：更新后数据
		Map<Integer,String> updateResult = new HashMap<Integer, String>();
		//查询总账
		Query query = em.createNativeQuery("select act.total_acct from AC_T_CUSTOMER act where act.id=(select t.t_customer_id from USERS t where t.email=?)");
		query.setParameter(1, email);
		
		//总账
		String totalAcct = (String) query.getResultList().get(0);
		//更新前数据
		Query queryAmount =em.createNativeQuery("select amount from AC_T_LEDGER where account like '"+totalAcct+"%' and busi_type=4");
		String beforUpdateAmount =  ((BigDecimal) queryAmount.getResultList().get(0)).toString();
		//更新资金账户
		Query update = em.createNativeQuery("update AC_T_LEDGER set AMOUNT=amount+? where account like '"+totalAcct+"%' and busi_type=4");
		update.setParameter(1, amount);
		int excuteCount = update.executeUpdate();
		String afterUpdateAmount = ((BigDecimal) queryAmount.getResultList().get(0)).toString();
		updateResult.put(1, String.valueOf(excuteCount));
		//查询分账账号
		Query accountQuery = em.createNativeQuery("select account from  AC_T_LEDGER where account like '"+totalAcct+"%' and busi_type=4");
		String account = (String) accountQuery.getResultList().get(0);
		//流水号
		String tradeNo = DateUtil.getTransactionSerialNumber(commonDao.getFlowSeq());
		Date tradeDate = new Date();
		//查询分账账号
		Query seq = em.createNativeQuery("select ACTFLOW_SEQ.nextval from dual");
		BigDecimal flowSeq = (BigDecimal) seq.getResultList().get(0);
		
		//插入流水
		Query flowUpdate = em.createNativeQuery("insert into AC_T_FLOW(ID,ACCOUNT,ACCT_TITLE,TRADE_NO,TRADE_DATE,TRADE_AMOUNT,APPO_ACCT,TRADE_TYPE,APPO_ACCT_TITLE,MEMO) values(?,' ',' ',?,?,?,?,'1','01010114104', '操作科目：系统迁移')");
		flowUpdate.setParameter(1, flowSeq);
		flowUpdate.setParameter(2, tradeNo);
		flowUpdate.setParameter(3, tradeDate);
		flowUpdate.setParameter(4, amount);
		flowUpdate.setParameter(5, account);
		int flowInsertResult = flowUpdate.executeUpdate();
		updateResult.put(2, flowSeq.toString());
		updateResult.put(3, String.valueOf(flowInsertResult));
		updateResult.put(4, beforUpdateAmount);
		updateResult.put(5, afterUpdateAmount);
		//账户4
		AcTLedgerAdminVO act4 = findUserFrzeeAmount(userId, "4");
		//账户5
		AcTLedgerAdminVO act5 = findUserFrzeeAmount(userId, "5");
		
		//小表流水
		flowUtils.setAcTFlowClassify(TradeTypeConstants.XTQY, tradeNo, null, null, null, null, userId.longValue(), new BigDecimal(afterUpdateAmount) , act4.getPayBackAmt(), act5.getAmount(), new BigDecimal(amount), flowSeq.longValue(), TypeConstants.XTJY); 
		return updateResult;
		
	}
}
