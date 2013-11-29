package com.zendaimoney.online.service.newPay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.dao.AcTFlowClassifyDAO;
import com.zendaimoney.online.entity.AcTFlowClassifyVO;
import com.zendaimoney.online.vo.fundDetail.FundFlowVO;
import com.zendaimoney.online.web.DTO.AcTFlowClassifyDTO;

/**
 * 查询资金详情信息
 * 
 * @author HuYaHui
 * 
 */
@Component
@Transactional(readOnly = true)
public class FundManagerNew {
	private static Logger logger = LoggerFactory.getLogger(FundManagerNew.class);
	
	@Autowired
	private AcTFlowClassifyDAO acTFlowClassifyDAO;
	public static void main(String[] args) {
		List<AcTFlowClassifyVO> _obj_list=new ArrayList<AcTFlowClassifyVO>();
		AcTFlowClassifyVO vo=new AcTFlowClassifyVO();
		vo.setId(1l);
		_obj_list.add(vo);
		
		
		List<AcTFlowClassifyVO> obj_list=new ArrayList<AcTFlowClassifyVO>();
		Collections.copy(_obj_list, obj_list);
		for(AcTFlowClassifyVO _vo:obj_list){
			_vo.setId(2l);
		}
		System.out.println(_obj_list.get(0).getId());
		
	}

	/**
	 * 带参数查询
	 * 2013-2-28 上午11:48:22 by HuYaHui 
	 * @param userId
	 * @param date_start
	 * @param date_end
	 * @param startPosition
	 * 			从第几行开始
	 * @param rows
	 * 			每页显示行数
	 * @param type
				1提现
				2充值(同提现)
				3资金回收
				4偿还借款
				5投标/借款成功
				6系统迁移
				7证件实名认证手续费
				8调账
				9冲账
	 * @return
	 */
	@Transactional(readOnly=true)
	public FundFlowVO queryFundFlow(Long userId,String date_start, String date_end,int startPosition, int rows,int type){
		logger.info("进入queryFundFlow,userId:"+userId+" type:"+type+" start:"+date_start+" end:"+date_end);
		// 初始化
		FundFlowVO fundFlowVO = new FundFlowVO();
		fundFlowVO.setDate_start(date_start);
		fundFlowVO.setDate_end(date_end);
		fundFlowVO.setType_fund(type+"");
		long pageCount=acTFlowClassifyDAO.countTypeAndUserId(date_start,date_end,type,userId);
		logger.info("-->根据条件，查询总记录数："+pageCount);
		if(pageCount==0){
			return fundFlowVO;
		}
		
		
		//分页查询
		List<AcTFlowClassifyVO> rtnList=acTFlowClassifyDAO.findTypeAndUserId(date_start,date_end,type,userId,startPosition,rows);
		
		Collection<AcTFlowClassifyDTO> dto_list=new ArrayList<AcTFlowClassifyDTO>();
		for(AcTFlowClassifyVO vo:rtnList){
			AcTFlowClassifyDTO dto=new AcTFlowClassifyDTO();
			//时间
			dto.setDate(vo.getGreateTime());
			//类型
			String tradeType=TradeTypeConstants.getValueByType(vo.getTradeType());

			if(tradeType.trim().equals("偿还本金")){
				tradeType="偿还本金/回收本金";
			}else if(tradeType.trim().equals("偿还利息")){
				tradeType="偿还利息/回收利息";
			}else if(TypeConstants.TZ==vo.getType()){
				tradeType="调账";
			}
			dto.setTradeType(tradeType);
			//投标/借款成功
			if(vo.getInUserId()!=null && vo.getInUserId().equals(userId)){
				//如果当前用户是收入用户
				dto.setIn(BigDecimalUtil.compareTo(vo.getTradeAmount(), new BigDecimal("0"))==0?new BigDecimal("0"):vo.getTradeAmount());

				//账户余额
				dto.setAmount(vo.getInCashAccount());
			}else if(vo.getOutUserId()!=null && vo.getOutUserId().equals(userId)){
				//如果当前用户是输出用户
				dto.setOut(BigDecimalUtil.compareTo(vo.getTradeAmount(), new BigDecimal("0"))==0?new BigDecimal("0"):vo.getTradeAmount());
				//账户余额
				dto.setAmount(vo.getOutCashAccount());
			}
			dto.setCode(vo.getTradeCode());
			dto.setType(vo.getType()+"");
			dto_list.add(dto);
		}
		fundFlowVO.setFlowList(dto_list);
		fundFlowVO.setPageCount(pageCount);
		logger.info("结束queryFundFlowDetail.处理后的list大小为："+fundFlowVO.getFlowList().size()+" 处理前的大小为："+rtnList.size());
		return fundFlowVO;		
	}
}
