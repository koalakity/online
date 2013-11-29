package com.zendaimoney.online.service.loanManagement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementInvestInfoDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementUserInfoPersonDao;
import com.zendaimoney.online.entity.financial.FinancialSysMsg;
import com.zendaimoney.online.entity.loanManagement.LoanManagementInvestInfo;
import com.zendaimoney.online.entity.loanManagement.LoanManagementUserInfoPerson;
import com.zendaimoney.online.entity.loanManagement.LoanManagementUsers;
import com.zendaimoney.online.entity.personal.PersonalUserMessageSet;
import com.zendaimoney.online.oii.sms.SMSSender;
import com.zendaimoney.online.vo.loanManagement.SendMessageToInvestVO;

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
@Component
public class MessageUtils {
	@Autowired
	private LoanManagementUserInfoPersonDao loanManagementUserInfoPersonDao;
	@Autowired
	private LoanManagementInvestInfoDao investInfoDao;
	@Autowired
	FinancialSysMsgDao sysMsgDao;
	
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-25 上午10:48:24
	 * @param messgeVo
	 * @param messagePayList
	 * @param mimeMailService
	 * description:理财人收到还款时以短信和邮件的形式通知理财人
	 */
	public void sendMessage(SendMessageToInvestVO messageVo,List<PersonalUserMessageSet> messagePayList,MimeMailService mimeMailService){
		LoanManagementInvestInfo investInfo = investInfoDao.findByInvestId(messageVo.getInvestId());
		LoanManagementUsers user = investInfo.getUser();
		LoanManagementUserInfoPerson userInfoPerson = loanManagementUserInfoPersonDao.findByUserId(user.getUserId());
		// 用户手机号码
		String loanUserphoneNo = userInfoPerson.getPhoneNo();
		// 获取通知内容
		Map<String,String> map = getMapMessage(messageVo,user,investInfo);
		//发送站内信
		sendSysMsgToInvest(messageVo, user, investInfo);
		//判断用户通知类型
		for (PersonalUserMessageSet message : messagePayList) {
			if (message.getMannerId().equals(new BigDecimal(2))) {
				// 邮件通知
				String messages = mimeMailService.transferMailContent("rev_pay_advanced", map);
				mimeMailService.sendNotifyMail(messages, user.getEmail(), "我收到一笔还款");
			}
			if (message.getMannerId().equals(new BigDecimal(3))) {
				// 短信通知
				SMSSender.sendMessage("rev_pay_advanced", loanUserphoneNo, map);
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
	public Map<String, String> getMapMessage(SendMessageToInvestVO messgeVo,LoanManagementUsers user,LoanManagementInvestInfo investInfo){
		String principanInterestMonth = messgeVo.getPrincipanInterestMonth();
		Map<String, String> map = new HashMap<String, String>();
		String loginName = user.getLoginName();
		String laonTitle = investInfo.getLoanInfo().getLoanTitle();
		//借款人昵称
		map.put("0", loginName);
		// 借款标题
		map.put("1", laonTitle);
		// 当期应还本息
		map.put("2", principanInterestMonth);
		// 一次性提前还款剩余本金
		map.put("3", messgeVo.getCurrSurplusPrincipal());
		// 一次性提前还款逾期违约金
		map.put("4", messgeVo.getCurrOverdueFines());
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
	public void sendSysMsgToInvest(SendMessageToInvestVO messageVo,LoanManagementUsers user,LoanManagementInvestInfo investInfo) {
		String principanInterestMonth = messageVo.getPrincipanInterestMonth();
		String loginName = user.getLoginName();
		String laonTitle = investInfo.getLoanInfo().getLoanTitle();
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		sysMsg.setUserId(user.getUserId());
		sysMsg.setWordId(BigDecimal.valueOf(21));
		sysMsg.setParameter1(loginName);
		sysMsg.setParameter2(laonTitle);
		sysMsg.setParameter3(principanInterestMonth);
		sysMsg.setParameter4(messageVo.getCurrSurplusPrincipal());
		sysMsg.setParameter5(messageVo.getCurrOverdueFines());
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		sysMsgDao.save(sysMsg);
	}

}
