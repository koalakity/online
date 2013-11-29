package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.web.DTO.ActFlowDTO;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.MemcacheCacheConstants;
import com.zendaimoney.online.common.MemcacheCacheHelper;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.AcTFlowClassifyDAO;
import com.zendaimoney.online.dao.UserInfoPersonDAO;
import com.zendaimoney.online.dao.UsersDAO;
import com.zendaimoney.online.dao.common.SqlStatement;
import com.zendaimoney.online.entity.AcTFlowClassifyVO;
import com.zendaimoney.online.entity.UserInfoPersonVO;
import com.zendaimoney.online.entity.UsersVO;

@Service
@Transactional
public class FlowService {
	private static Logger logger = LoggerFactory.getLogger(FlowService.class);

	@Autowired
	private UserInfoPersonDAO userInfoPersonDAO;
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
    private AcTFlowClassifyDAO acTFlowClassifyDAO;

	public static final String[] SITE_ACCOUNTS = new String[] { ZendaiAccountBank.zendai_acct0, ZendaiAccountBank.zendai_acct1, ZendaiAccountBank.zendai_acct6, ZendaiAccountBank.zendai_acct7, ZendaiAccountBank.zendai_acct9,ZendaiAccountBank.zendai_acct11 };
	public static final String[] RISK_ACCOUNTS = new String[] { ZendaiAccountBank.zendai_acct10, ZendaiAccountBank.zendai_acct2, ZendaiAccountBank.zendai_acct3 };

	
	/**
	 * 根据条件统计风险金账户，支出金额
	 * 2013-3-7 下午5:25:25 by HuYaHui
	 * @param flows
	 * @return
	 */
	public BigDecimal calculateOutAmount(List<SqlStatement> param) {
		BigDecimal out_amount=acTFlowClassifyDAO.sumByConditionOut(param);
		logger.info("支出总金额："+out_amount);
		return out_amount;
	}
	
	/**
	 * 根据条件统计风险金账户，存入金额
	 * 2013-3-7 下午5:25:25 by HuYaHui
	 * @param flows
	 * @return
	 */
	public BigDecimal calculateInAmount(List<SqlStatement> param) {
		BigDecimal out_amount=acTFlowClassifyDAO.sumByConditionIn(param);
		logger.info("存入总金额："+out_amount);
		return out_amount;
	}
	
	/**
	 * 统计总数量
	 * 2013-3-8 上午10:01:39 by HuYaHui
	 * @param param
	 * @return
	 */
	public Long countByCondition(List<SqlStatement> param){
		//总数量
		Long count=acTFlowClassifyDAO.countByCondition(param);
		return count;
	}
	
	
	/**
	 * 根据条件分页
	 * 2013-3-8 上午10:02:45 by HuYaHui
	 * @param param
	 * @param page
	 * 			第几页
	 * @param size
	 * 			页面显示数量
	 * @return
	 */
	public List<ActFlowDTO> findRiskFlowsNew(List<SqlStatement> param,int page,int size){
		List<ActFlowDTO> dtoList=new ArrayList<ActFlowDTO>();
		//根据条件查询
		List<AcTFlowClassifyVO> list=acTFlowClassifyDAO.findByCondition(param,page,size);
		for(AcTFlowClassifyVO vo:list){
			ActFlowDTO dto=new ActFlowDTO();
			dto.setTradeNoAndMemo(vo.getMemo());
			//流水时间
			dto.setTradeDate(DateUtil.getStrDate(vo.getGreateTime()));
			/*
			 * 
			 * 如果tradeType是以下类型，页面显示风险准备金
				 * 	操作科目：风险金代偿（利息）tradeType：43 出
					操作科目：风险金代偿（本金）tradeType：42出
					操作科目：调账  tradeType：9出
	
					操作科目：交易手续费 tradeType：5入
					操作科目：偿还本金/回收本金tradeType：29入
					操作科目：偿还利息/回收利息tradeType：30入
					
				其他的对应不同账户
			 * */
			if(TradeTypeConstants.FXJDCBJ.equals(vo.getTradeType()) 
					|| TradeTypeConstants.FXJDCLX.equals(vo.getTradeType())
					|| TradeTypeConstants.TZ.equals(vo.getTradeType())
					|| TradeTypeConstants.FKRESERVE .equals(vo.getTradeType())
					|| TradeTypeConstants.DCGJYQCHBJ.equals(vo.getTradeType())
					|| TradeTypeConstants.DCGJYQCHLX.equals(vo.getTradeType())){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账显示风险准备金调账
					dto.setRiskTypeStr("风险准备金（调账）");
				}else{
					//不是调账的数据
					if(TradeTypeConstants.FXJDCBJ.equals(vo.getTradeType())){
						//出
						dto.setRiskTypeStr("风险金代偿（本金）");
					}else if(TradeTypeConstants.FXJDCLX.equals(vo.getTradeType())){
						//出
						dto.setRiskTypeStr("风险金代偿（利息）");
					}else{
						//类型,入
						dto.setRiskTypeStr("风险准备金");	
					}
				}
			}else if(TradeTypeConstants.FKFEE.equals(vo.getTradeType())){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账
					dto.setRiskTypeStr("借款手续费（调账）");
				}else{
					dto.setRiskTypeStr("借款手续费");
				}
			}else if(TradeTypeConstants.CZSXF.equals(vo.getTradeType())){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账
					dto.setRiskTypeStr("充值手续费（调账）");
				}else{
					dto.setRiskTypeStr("充值手续费");
				}
			}else if(TradeTypeConstants.ID5.equals(vo.getTradeType())){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账
					dto.setRiskTypeStr("ID5验证手续费（调账）");
				}else {
					dto.setRiskTypeStr("ID5验证手续费");
				}
			}else if(TradeTypeConstants.TXSXF.equals(vo.getTradeType())
					||TradeTypeConstants.TXSXFSB.equals(vo.getTradeType())){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账
					dto.setRiskTypeStr("提现手续费（调账）");
				}else{
					dto.setRiskTypeStr("提现手续费");
				}
			}else if(TradeTypeConstants.ZCHKCHGLF.equals(vo.getTradeType())
					|| TradeTypeConstants.CJYQCHGLF.equals(vo.getTradeType()) 
					|| TradeTypeConstants.GJYQCHGLF.equals(vo.getTradeType())
					|| TradeTypeConstants.DCGJYQCHGLF.equals(vo.getTradeType())
					||TradeTypeConstants.YCXDQGLF.equals(vo.getTradeType()) 
					){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账
					dto.setRiskTypeStr("月缴管理费（调账）");
				}else {
					dto.setRiskTypeStr("月缴管理费");
				}
			}else if(TradeTypeConstants.CJYQCHYQGLF.equals(vo.getTradeType())
					|| TradeTypeConstants.GJYQCHYQGLF.equals(vo.getTradeType()) 
					||TradeTypeConstants.DCGJYQCHYQGLF.equals(vo.getTradeType()) 
					){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账
					dto.setRiskTypeStr("逾期违约金（调账）");
				}else{
					dto.setRiskTypeStr("逾期违约金");
				}
			}else if(TradeTypeConstants.DCGJYQCHYQFX.equals(vo.getTradeType())){
				if(TypeConstants.TZ==vo.getType()){
					//如果是调账
					dto.setRiskTypeStr("逾期罚息（调账）");
				}else {
					dto.setRiskTypeStr("逾期罚息");
				}
			}else{
				dto.setRiskTypeStr("其他类型"+vo.getTradeType());
			}
			if(vo.getInUserId()!=null && !vo.getInUserId().equals("")){
				//证大支出
				dto.setOutputStr(BigDecimalUtil.formatCurrency(vo.getTradeAmount()));
//				<th field="loginName" width="50px;">昵称</th>
//				<th field="realName" width="40px;">姓名</th>
//				<th field="phoneNo" width="40px;">手机号</th>
//				<th field="email" width="50px;">email</th>
				long userId=vo.getInUserId();
				String userKey=MemcacheCacheConstants.USERKEY+userId;
				String userinfopersonKey=MemcacheCacheConstants.USERINFOPERSONKEY+userId;
				UsersVO user=MemcacheCacheHelper.get(userKey,new UsersVO());
				UserInfoPersonVO userInfoPerson=MemcacheCacheHelper.get(userinfopersonKey,new UserInfoPersonVO());
				if(user==null || userInfoPerson==null){
					user=usersDAO.findByUserId(userId);
					MemcacheCacheHelper.set(userKey, user, MemcacheCacheConstants.TIME30M);
					
					userInfoPerson=userInfoPersonDAO.findByUserId(userId);
					MemcacheCacheHelper.set(userinfopersonKey, userInfoPerson, MemcacheCacheConstants.TIME30M);
				}
				dto.setLoginName(user.getLoginName());
				dto.setEmail(user.getEmail());
				dto.setRealName(userInfoPerson==null?"":userInfoPerson.getRealName());
				dto.setPhoneNo(userInfoPerson==null?"":userInfoPerson.getPhoneNo());
			}else if(vo.getOutUserId()!=null && !vo.getOutUserId().equals("")){
				//证大收入
				dto.setIncomingStr(BigDecimalUtil.formatCurrency(vo.getTradeAmount()));
//				<th field="loginName" width="50px;">昵称</th>
//				<th field="realName" width="40px;">姓名</th>
//				<th field="phoneNo" width="40px;">手机号</th>
//				<th field="email" width="50px;">email</th>
				long userId=vo.getOutUserId();
				String userKey=MemcacheCacheConstants.USERKEY+userId;
				String userinfopersonKey=MemcacheCacheConstants.USERINFOPERSONKEY+userId;
				UsersVO user=MemcacheCacheHelper.get(userKey,new UsersVO());
				UserInfoPersonVO userInfoPerson=MemcacheCacheHelper.get(userinfopersonKey,new UserInfoPersonVO());
				if(user==null || userInfoPerson==null){
					user=usersDAO.findByUserId(userId);
					MemcacheCacheHelper.set(userKey, user, MemcacheCacheConstants.TIME30M);
					
					userInfoPerson=userInfoPersonDAO.findByUserId(userId);
					MemcacheCacheHelper.set(userinfopersonKey, userInfoPerson, MemcacheCacheConstants.TIME30M);
				}
				dto.setLoginName(user.getLoginName());
				dto.setEmail(user.getEmail());
				dto.setRealName(userInfoPerson==null?"":userInfoPerson.getRealName());
				dto.setPhoneNo(userInfoPerson==null?"":userInfoPerson.getPhoneNo());
			}
			dto.setTradeNo(vo.getTradeNo());
			dtoList.add(dto);
		}
		return dtoList;
	}
}
