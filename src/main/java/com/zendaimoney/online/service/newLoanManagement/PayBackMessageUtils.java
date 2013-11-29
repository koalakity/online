package com.zendaimoney.online.service.newLoanManagement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.dao.SysMsgDAO;
import com.zendaimoney.online.dao.UserMessageSetDAO;
import com.zendaimoney.online.dto.PayBackMessageDTO;
import com.zendaimoney.online.entity.SysMsgVO;
import com.zendaimoney.online.entity.UserMessageSetVO;
import com.zendaimoney.online.oii.sms.SMSSender;
import com.zendaimoney.online.threadPool.Task;

/**
 * 
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author 王腾飞
 * @date: 2012-12-25 上午9:55:01
 * operation by:
 * description: 消息通知 包含 短信通知，邮件通知 和站内信通知
 */

public class PayBackMessageUtils  extends Task{
	private static Logger logger = LoggerFactory.getLogger(PayBackService.class);
	
	private MimeMailService mimeMailService;
	
	private UserMessageSetDAO userMessageSetDao;
	
	private SysMsgDAO sysMsgDao;
	private List<PayBackMessageDTO> payBackMessageList;
	private String loanTitle;
	private String payTpye;
	public PayBackMessageUtils(){}
	public PayBackMessageUtils(List<PayBackMessageDTO> payBackMessageList,String loanTitle,String payTpye,MimeMailService mimeMailService,UserMessageSetDAO userMessageSetDao,SysMsgDAO sysMsgDao){
		this.payBackMessageList = payBackMessageList;
		this.loanTitle = loanTitle;
		this.payTpye = payTpye;
		this.mimeMailService = mimeMailService;
		this.userMessageSetDao = userMessageSetDao;
		this.sysMsgDao = sysMsgDao;
	}

	@Override
	public void run() {
		logger.info("开始发短信................................");
		sendMessage(payBackMessageList, loanTitle, payTpye);
		logger.info("开始发短信................................");
	}
	
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-25 上午10:48:24
	 * @param messgeVo
	 * @param messagePayList
	 * @param mimeMailService
	 * description:理财人收到还款时以短信和邮件的形式通知理财人
	 */
	private void sendMessage(List<PayBackMessageDTO> payBackMessageList,String loanTitle,String payTpye){
		for (PayBackMessageDTO payBackMessage :payBackMessageList){
			List<UserMessageSetVO> messagePayList = userMessageSetDao.findByUserIdAndKindId(payBackMessage.getUserId(), 7L);
			// 用户手机号码
			String loanUserphoneNo = payBackMessage.getPhoneNO();
			// 获取通知内容
			Map<String,String> map = getMapMessage(payBackMessage,loanTitle,payTpye);
			//发送站内信
			sendSysMsgToInvest(payBackMessage, loanTitle,payTpye);
			//判断用户通知类型
			for (UserMessageSetVO message : messagePayList) {
				if (message.getMannerId()==2) {
					// 邮件通知
					String messages = mimeMailService.transferMailContent(payTpye, map);
					mimeMailService.sendNotifyMail(messages, payBackMessage.getEmail(), "我收到一笔还款");
				}
				if (message.getMannerId()==3) {
					// 短信通知
					SMSSender.sendMessage(payTpye, loanUserphoneNo, map);
				}

			}
		}
		
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-25 上午10:22:36
	 * @param messgeVo
	 * @return
	 * description:根据通知消息生成map
	 */
	public Map<String, String> getMapMessage(PayBackMessageDTO payBackMessage,String loanTitle,String payBackType){
		String principanInterestMonth = payBackMessage.getPrincipanInterestMonth();
		Map<String, String> map = new HashMap<String, String>();
		String loginName = payBackMessage.getLoginName();
		//借款人昵称
		map.put("0", loginName);
		// 借款标题
		map.put("1", loanTitle);
		// 当期应还本息
		map.put("2", principanInterestMonth);
		//正常分期还款
		if("rev_pay".equals(payBackType)){
			// 逾期罚息
			map.put("3", payBackMessage.getOverdueInterest());
		}else if("rev_pay_advanced".equals(payBackType)){
			// 一次性提前还款剩余本金
			map.put("3", payBackMessage.getSurplusPrincipal());
			// 一次性提前还款逾期违约金
			map.put("4", payBackMessage.getAdvanceOncePayBreachPenalty());
		}
		
		return map;
	}
	
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-25 下午1:41:25
	 * @param investVO
	 * description:还款成功,发送系统消息给投标人
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void sendSysMsgToInvest(PayBackMessageDTO payBackMessage,String loanTitle,String payBackType) {
		String principanInterestMonth = payBackMessage.getPrincipanInterestMonth();
		String loginName = payBackMessage.getLoginName();
		SysMsgVO sysMsg = new SysMsgVO();
		sysMsg.setUserId(payBackMessage.getUserId());
		//14 代表正常还款  21 代表一次性提前
		if("rev_pay".equals(payBackType)){
			sysMsg.setWordId(14L);
		}else{
			sysMsg.setWordId(21L);
		}
		sysMsg.setParameter1(loginName);
		sysMsg.setParameter2(loanTitle);
		sysMsg.setParameter3(principanInterestMonth);
		if(sysMsg.getWordId()==21){
			sysMsg.setParameter4(payBackMessage.getSurplusPrincipal());
			sysMsg.setParameter5(payBackMessage.getAdvanceOncePayBreachPenalty());
		}else {
			sysMsg.setParameter4(payBackMessage.getOverdueInterest());
		}
		
		
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		sysMsgDao.save(sysMsg);
	}
	@Override
	public Task[] taskCore() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected boolean useDb() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean needExecuteImmediate() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String info() {
		// TODO Auto-generated method stub
		return "还款成功发送消息";
	}

}
