package com.zendaimoney.online.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.admin.entity.LoanInfoAdminVO;
import com.zendaimoney.online.admin.service.LoanprocessSupportService;
import com.zendaimoney.online.dao.LoanInfoDAO;
import com.zendaimoney.online.entity.LoanInfoVO;
import com.zendaimoney.online.service.newLoanManagement.PayBackService;

/**
 * @author 王腾飞
 *
 * description：自动任务，目前包含自动还款，自动流标
 */
@Component
public class AutomateTasks {
	@Autowired
	private PayBackService payBackService;
	@Autowired
	private LoanInfoDAO loanInfoDao;
	@Autowired
	private LoanprocessSupportService loanprocessSupportService;
	private static Logger logger = LoggerFactory.getLogger(AutomateTasks.class);
	
	/**
	 * 自动还款
	 */
	public void autoPayBack(){
		List<LoanInfoVO> loanInfoList = loanInfoDao.findByStatus(4L);
		for(LoanInfoVO loanInfo : loanInfoList){
			try {
				payBackService.payBackProcess(loanInfo.getLoanId(), null, "autoRepay", null);
			} catch (Exception e) {
				logger.info("自动还款失败，借款ID："+loanInfo.getLoanId());
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * 自动流标
	 */
	public void checkTrade(){
		// 获取投标中 借款列表
		List<LoanInfoVO> loanInfoList = loanInfoDao.findByStatus(1L);
		// 筹标期大于七天 设置为自动流标
		for(LoanInfoVO loanInfo : loanInfoList){
			long voerFlowFlag = new Date().getTime() - loanInfo.getStartInvestTime().getTime();
			if (voerFlowFlag > 86400000*7-60000*10) {
				try {
					LoanInfoAdminVO result = loanprocessSupportService.abortiveTenderHandle(loanInfo.getLoanId());
					// 执行流标处理 并发短信给借款人和投资人
					loanprocessSupportService.sendPhoneMessageToborrowerAndInvest(result,1);
				} catch (Exception e) {
					logger.info("流标失败，借款ID："+loanInfo.getLoanId());
					e.printStackTrace();
				}
				
			}
		}
	
		System.out.println("---------------------检查投标过期---------------------:"+java.util.Calendar.getInstance().getTime());
	}
}
