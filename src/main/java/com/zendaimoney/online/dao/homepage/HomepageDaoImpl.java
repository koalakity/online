package com.zendaimoney.online.dao.homepage;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.zendaimoney.online.entity.homepage.HomepageAcTLedger;
import com.zendaimoney.online.entity.homepage.HomepageInvestInfo;
import com.zendaimoney.online.entity.homepage.HomepageLoanInfo;
import com.zendaimoney.online.entity.homepage.HomepageUserCreditNote;
import com.zendaimoney.online.vo.homepage.HomepageInvestInfoVO;
import com.zendaimoney.online.vo.homepage.HomepageLoanInfoTempVO;
import com.zendaimoney.online.vo.homepage.HomepageUserMovementVO;



@Component
public class HomepageDaoImpl implements HomepageCustomDao{
	
	//==========================================================================================
	private String sql="";
	/**
	 * 查询用户成功借款笔数
	 * @param userId
	 * @return
	 */
	public int querySuccLoanCount(BigDecimal userId) {
		sql = "select count(*) from loan_info loan  where  loan.status='5' and  loan.user_id="+userId;
		Query query = em.createNativeQuery(sql);
		List list = query.getResultList();
		if (list == null || list.size() == 0 || list.get(0) == null) {
			return 0;
		}
		BigDecimal count = (BigDecimal) list.get(0);
		return (count == null) ? 0 :count.intValue();
	}
	/**
	 * 查询用户成功理财笔数
	 * @param userId
	 * @return
	 */
	public int querySuccBidCount(BigDecimal userId){
		sql = "select count(*) from invest_info info where  info.status='4'and info.user_id="+userId;
		Query query = em.createNativeQuery(sql);
		List list = query.getResultList();
		if (list == null || list.size() == 0 || list.get(0) == null) {
			return 0;
		}
		BigDecimal count = (BigDecimal) list.get(0);
		return (count == null) ? 0 :count.intValue() ;
	}
	//==========================================================================================
	
	private static String personalInfoQueryAcTLedger = "select a from HomepageAcTLedger a, HomepageUsers u where  u.userId=a.account and u.userId=:userId";
	private static String personalInfoQueryUserCreditNote=" select n from HomepageUserCreditNote n, HomepageUsers u where  u.userId=n.userId and u.userId=:userId ";
	private static String FreezeAmountQuery=" select sum(t.freeze_money) from freeze_funds t where  t.user_id=? ";
	private static String successBorrowers = " select count(*) from HomepageLoanInfo t where t.status=4 and t.user.userId=:userId ";//状态还款中 成功借款
	private static String successTenderer = " select count(*) from Invest_Info t,Loan_Info l where (l.status = 4 or l.status=5) and l.loan_Id=t.loan_Id and t.user_Id=? ";//成功投标
	private static String loanFinancialQueryInfo=" select t from HomepageLoanInfo t where t.user.userId=:userId order by t.releaseTime desc ";// 借款记录
	private static String tenderQueryInfo =" select a from HomepageInvestInfo a where a.userId=:userId ";
	
	private static String progressQueryLoan= "  select b.loan_id,b.loan_use,b.loan_title,b.loan_amount,b.year_rate,b.loan_duration,to_char(b.release_time,'yyyy-mm-dd HH:mm') release_time,(sum(a.invest_amount) / b.loan_amount) * 100  progress ,count(a.loan_id)  items ,b.status " +
			                                   "from invest_info a, loan_info b " +
			                                   "where a.loan_id(+) = b.loan_id and b.user_id =? group by b.loan_id,b.loan_use,b.loan_title,b.loan_amount,b.year_rate,b.loan_duration,b.release_time,b.status order by b.release_time desc ";//借款进度 笔数
	
	private static String newsQueryDynamics  =" select t.*, t1.word_context  from user_movement t, movement_word t1 where t.word_id = t1.word_id and t.msg_kind=1 and t.user_id =? order by t.happen_time desc ";
	
	private static String messageQuery =" select t.loan_id, t.loan_title, t.status, count(t.loan_id) items,to_char( max(s.sender_time),'yyyy-mm-dd hh:mm') dateTime " +
										" from loan_info t, station_letter s  where t.user_id =? and t.loan_id = s.loan_id and s.msg_kind=2 " +
										" group by t.loan_title, t.status, t.loan_id order by dateTime desc ";//留言板信息
	
	private static String headPath = " select t.headPath from HomepageUserInfoPerson t where t.user.userId=? ";
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 用户信息
	 * @param userId
	 * @return
	 */
	public HomepageAcTLedger getAcTLedgerInfo(BigDecimal userId){
		Query query = em.createQuery(personalInfoQueryAcTLedger);
		query.setParameter("userId", userId);
		List aclist=query.getResultList();
		HomepageAcTLedger  acTLedger=null;
		if(aclist.size()>0)
		{
			 acTLedger= (HomepageAcTLedger) query.getResultList().get(0);
		}
		return acTLedger;
	}
	/**
	 * 信用等级
	 * @param userId
	 * @return
	 */
	public HomepageUserCreditNote getUserCreditNoteinfo(BigDecimal userId){
		Query query = em.createQuery(personalInfoQueryUserCreditNote);
		query.setParameter("userId", userId);
		HomepageUserCreditNote  userCreditNote=null;
		List userCueditList=query.getResultList();
		if(userCueditList.size()>0)
		{
			userCreditNote=(HomepageUserCreditNote) query.getResultList().get(0);
		}
		return userCreditNote;
	}
	/**
	 * 冻结金额
	 * @param userId
	 * @return
	 */
	public BigDecimal getFreezeAmount(BigDecimal userId){
		Query query =em.createNativeQuery(FreezeAmountQuery);
		query.setParameter(1, userId);
		BigDecimal  freezeAmount= (BigDecimal) query.getResultList().get(0);
		return freezeAmount;
	}
	
	/**
	 * 借款笔数
	 * @param userId
	 * @return
	 */
	public Long getloanItems(BigDecimal userId){
		Query query = em.createQuery(successBorrowers);
		query.setParameter("userId", userId);
		Long  loanItems= (Long) query.getResultList().get(0);
		return loanItems;
	}
	
	/**
	 * 投标笔数
	 * @param userId
	 * @return
	 */
	public BigDecimal getSuccessTenderer(BigDecimal userId){
		Query query =em.createNativeQuery(successTenderer);
		query.setParameter(1, userId);
		BigDecimal  tenderer= (BigDecimal) query.getResultList().get(0);
		return tenderer ;
	}
	
	/**
	 * 借款记录
	 * @param userId
	 * @return
	 */
	public  List<HomepageLoanInfo>  getLoanInfo(BigDecimal userId){
		Query query = em.createQuery(loanFinancialQueryInfo);
		query.setParameter("userId", userId);
		List<HomepageLoanInfo>   loanInfo= query.getResultList();
		return loanInfo;
	}
	/**
	 * 投标记录
	 * @param userId
	 * @return
	 */
	public  List<HomepageInvestInfoVO>  getTenderInfo(BigDecimal userId){
		Query query = em.createQuery(tenderQueryInfo);
		query.setParameter("userId", userId);
		List<HomepageInvestInfoVO>   tenderInfoList= query.getResultList();
		
		List<HomepageInvestInfoVO> investList=new ArrayList<HomepageInvestInfoVO>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");//投标时间
		for(int i=0;i<tenderInfoList.size();i++)//循环取出时间格式做修改调整
		{
			
			HomepageInvestInfo investInfoVo = (HomepageInvestInfo)tenderInfoList.get(i);
			String time =format.format(investInfoVo.getInvestTime());
			HomepageInvestInfoVO vo=new HomepageInvestInfoVO();
			vo.setUserId(investInfoVo.getUserId());
//			vo.setLoanInfo(investInfoVo.getLoanInfo());
			vo.setInvestId(investInfoVo.getInvestId());
			vo.setInvestAmount(investInfoVo.getInvestAmount());
			vo.setDescription(investInfoVo.getDescription());
//			vo.setLoanInfo(investInfoVo.getLoanInfo());
			vo.setInvestDate(time);
			investList.add(vo);
		}
		return investList;
	}
	/** 借款信息及 进度和 笔数*/
	public List getProgressLoan(BigDecimal userId){
		Query query =em.createNativeQuery(progressQueryLoan);
		query.setParameter(1, userId);
		List  progressList= (List) query.getResultList();
		List returnProList =new ArrayList(); 
		for(int i=0;i<progressList.size();i++)
		{
			HomepageLoanInfoTempVO  loanvo = new HomepageLoanInfoTempVO();
			Object[] obj=(Object[]) progressList.get(i);
//			loanvo.setLoanId((BigDecimal) obj[0]);
//			loanvo.setLoanUse((BigDecimal) obj[1]);
//			loanvo.setLoanTitle((String) obj[2]);
//			loanvo.setLoanAmount( new Double(((BigDecimal) obj[3]).doubleValue()));
//			loanvo.setYearRate(new Double(((BigDecimal) obj[4]).doubleValue()));
//			loanvo.setLoanDuration((BigDecimal) obj[5]);
			loanvo.setReleaseDate((String) obj[6]);
			if(null == obj[7])//如果进度计算出null
			{
//				loanvo.setProgress(new BigDecimal(0));//赋值0
			}
			else
			{
//				loanvo.setProgress(((BigDecimal) obj[7]).setScale(0, BigDecimal.ROUND_HALF_UP));
			}
			
//			loanvo.setItems(new Integer(((BigDecimal) obj[8]).intValue()));
//			loanvo.setStatus((BigDecimal) obj[9]);
			returnProList.add(loanvo);
		}
		return returnProList;
	}
	/** 我的最新动态 */
	public List<HomepageUserMovementVO> getNewsDynamicsInfo(BigDecimal userId){
		Query query =em.createNativeQuery(newsQueryDynamics);
		query.setParameter(1, userId);
		List  progressList= (ArrayList) query.getResultList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");//投标时间
		List<HomepageUserMovementVO> userMoVo=new ArrayList<HomepageUserMovementVO>();
		for(int i=0;i<progressList.size();i++)
		{
			Object[] obj=(Object[])progressList.get(i);
//			HomepageUserMovementVO moVo = new HomepageUserMovementVO();
//			moVo.setMovementId((BigDecimal) obj[0]);
//			moVo.setUserId((BigDecimal) obj[1]);
////			moVo.setWordId((BigDecimal) obj[2]);
//			moVo.setParameter1((String) obj[3]);
//			moVo.setParameter2((String) obj[4]);
//			moVo.setUrl1((String) obj[5]);
//			moVo.setUrl2((String) obj[6]);
			String happenDate="";
			if(null!=obj[7])
			{
				 happenDate =format.format((Timestamp)obj[7]);
				
			}
//			moVo.setHappenTime(happenDate);
//		
//			String word = (String) obj[12];
//			BigDecimal wid=BigDecimal.ONE;
//			if(wid.intValue()==1)
//			{
//				String[] text=word.replace("[", "").replace("]", "").split("[0]");//以[0]为分隔符，转成数组  我发布了[0]
//				moVo.setWordContext1(text[0]);
//			}
//			else if(wid.intValue()==2||wid.intValue()==4||wid.intValue()==5)
//			{
//				String[] text=word.replace("[", "").replace("]", "").split("[0]");//以[0]为分隔符，转成数组  我发布的[0]已满标
//				moVo.setWordContext1(text[0]);
//				moVo.setWordContext3(text[1]);
//			}
//			else if(wid.intValue()==3)
//			{
//				String[] text=word.split("[0]");//以[0]为分隔符，转成数组   [0]在我发布的[1]留了言
//				String[] text2=text[1].replace("[", "").replace("]", "").split("[1]");
//				moVo.setWordContext2(text2[0]);
//				moVo.setWordContext4(text2[1]);
//			}
//			else if(wid.intValue()==6)
//			{
//				String[] text=word.split("[0]");//以[0]为分隔符，转成数组   我向[0]投了[1]元
//				String[] text2=text[1].replace("[", "").replace("]", "").split("[1]");
//				moVo.setWordContext1(text[0].replace("[", ""));
//				moVo.setWordContext3(text2[0]);
//				moVo.setWordContext5(text2[1]);
//			}
//			else{
//				try {
//					throw new Exception("查询最新动态时发生异常：文字id，不能识别的类型:"+wid.intValue());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			userMoVo.add(moVo);
		}
		return userMoVo;
	}
	/** 留言板 */
	public List<HomepageLoanInfoTempVO> genMessageInfo(BigDecimal userId){
		Query query =em.createNativeQuery(messageQuery);
		query.setParameter(1, userId);
		List  messageInfoList= (ArrayList) query.getResultList();
		List<HomepageLoanInfoTempVO> loanList = new ArrayList<HomepageLoanInfoTempVO>();
		for(int i=0;i<messageInfoList.size();i++)
		{
			HomepageLoanInfoTempVO loanvo=new HomepageLoanInfoTempVO();
			Object[] obj=(Object[])messageInfoList.get(i);
//			loanvo.setLoanId((BigDecimal) obj[0]);
//			loanvo.setLoanTitle((String) obj[1]);
//			loanvo.setStatus((BigDecimal) obj[2]);
//			loanvo.setItems(new Integer(((BigDecimal) obj[3]).intValue()));//留言条数
			loanvo.setReleaseDate((String) obj[4]);
			loanList.add(loanvo);
		}
		return loanList;
	}
	
	/** 个人头像 */
	public String getHeadPath(BigDecimal userId){
		Query query = em.createQuery(headPath);
		query.setParameter(1, userId);
		List l=query.getResultList();
		String  headPath="";
		if(l.size()>0)
		{
			 headPath= (String) query.getResultList().get(0);
		}
		return headPath;
	}
}
