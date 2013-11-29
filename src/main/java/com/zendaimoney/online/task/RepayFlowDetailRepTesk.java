package com.zendaimoney.online.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.admin.service.RepayFlowDetailRepService;

/**
 * 还款明细报表自动任务
 * @author HuYaHui
 *
 */
@Component
public class RepayFlowDetailRepTesk {
	@Autowired
	RepayFlowDetailRepService repayFlowDetailRepService;
	
	public void reFreshData(){
		repayFlowDetailRepService.reFreshData();
	}

	
	
}
