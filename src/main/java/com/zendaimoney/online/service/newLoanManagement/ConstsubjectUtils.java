package com.zendaimoney.online.service.newLoanManagement;

import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.dto.ConstsubjectDTO;

public class ConstsubjectUtils {
	public static ConstsubjectDTO getSubjectNO(int overdueDays){
		ConstsubjectDTO subjectNO = new ConstsubjectDTO();
		if(overdueDays<=0){
			//出方
			// 本金
			subjectNO.setLoanerPrincipalAdjustOut(NewConstSubject.adjust_repayprincipal_loan_out);
			subjectNO.setPrincipalOut(NewConstSubject.recv_principal_out);
			subjectNO.setInvesterPrincipalAdjustOut(NewConstSubject.adjust_repayprincipal_invest_out);
			// 利息
			subjectNO.setLoanerInverestAdjustOut(NewConstSubject.adjust_repaypinterest_loan_out);
			subjectNO.setInverestOut(NewConstSubject.recv_interest_out);
			subjectNO.setInvesterInverestAdjustOut(NewConstSubject.adjust_repaypinterest_invest_out);
			//月缴管理费
			subjectNO.setMonthManageCostOut(NewConstSubject.month_manage_cost_out);
			//入方
			// 本金
			subjectNO.setLoanerPrincipalAdjustIn(NewConstSubject.adjust_repayprincipal_loan_in);
			subjectNO.setPrincipalIn(NewConstSubject.recv_principal_in);
			subjectNO.setInvesterPrincipalAdjustIn(NewConstSubject.adjust_repayprincipal_invest_in);
			// 利息
			subjectNO.setLoanerInverestAdjustIn(NewConstSubject.adjust_repaypinterest_loan_in);
			subjectNO.setInverestIn(NewConstSubject.recv_interest_in);
			subjectNO.setInvesterInverestAdjustIn(NewConstSubject.adjust_repaypinterest_invest_in);
			//月缴管理费
			subjectNO.setMonthManageCostIn(NewConstSubject.month_manage_cost_in);
			
			//交易类型
			subjectNO.setPrincipalTradeType(TradeTypeConstants.ZCHKCHBJ);
			subjectNO.setInverestTradeType(TradeTypeConstants.ZCHKCHLX);
			subjectNO.setMonthManagerTradeTpye(TradeTypeConstants.ZCHKCHGLF);
			
		}else if(overdueDays<=30){
			//出方
			// 本金
			subjectNO.setLoanerPrincipalAdjustOut(NewConstSubject.loaner_principal_adjust_junior_overdue_out);
			subjectNO.setPrincipalOut(NewConstSubject.principal_junior_overdue_out);
			subjectNO.setInvesterPrincipalAdjustOut(NewConstSubject.invester_principal_adjust_junior_overdue_out);
			// 利息
			subjectNO.setLoanerInverestAdjustOut(NewConstSubject.loaner_inverest_adjust_junior_overdue_out);
			subjectNO.setInverestOut(NewConstSubject.inverest_junior_overdue_out);
			subjectNO.setInvesterInverestAdjustOut(NewConstSubject.invester_inverest_adjust_junior_overdue_out);
			//逾期罚息
			subjectNO.setLoanerOverdueFreeAdjustOut(NewConstSubject.loaner_overdueFree_adjust_junior_overdue_out);
			subjectNO.setOverdueFreeOut(NewConstSubject.overdueFree_junior_overdue_out);
			subjectNO.setInvesterOverdueFreeAdjustOut(NewConstSubject.invester_overdueFree_adjust_junior_overdue_out);
			//逾期违约金
			subjectNO.setPenaltyOut(NewConstSubject.penalty_junior_overdue_out);
			//月缴管理费
			subjectNO.setMonthManageCostOut(NewConstSubject.junior_overdue_month_manage_cost_out);
			
			
			//入方
			subjectNO.setLoanerPrincipalAdjustIn(NewConstSubject.loaner_principal_adjust_junior_overdue_in);
			subjectNO.setPrincipalIn(NewConstSubject.principal_junior_overdue_in);
			subjectNO.setInvesterPrincipalAdjustIn(NewConstSubject.invester_principal_adjust_junior_overdue_in);
			// 利息
			subjectNO.setLoanerInverestAdjustIn(NewConstSubject.loaner_inverest_adjust_junior_overdue_in);
			subjectNO.setInverestIn(NewConstSubject.inverest_junior_overdue_in);
			subjectNO.setInvesterInverestAdjustIn(NewConstSubject.invester_inverest_adjust_junior_overdue_in);
			//逾期罚息
			subjectNO.setLoanerOverdueFreeAdjustIn(NewConstSubject.loaner_overdueFree_adjust_junior_overdue_in);
			subjectNO.setOverdueFreeIn(NewConstSubject.overdueFree_junior_overdue_in);
			subjectNO.setInvesterOverdueFreeAdjustIn(NewConstSubject.invester_overdueFree_adjust_junior_overdue_in);
			//逾期违约金
			subjectNO.setPenaltyIn(NewConstSubject.penalty_junior_overdue_in);
			//月缴管理费
			subjectNO.setMonthManageCostIn(NewConstSubject.junior_overdue_month_manage_cost_in);
			
			
			//交易类型
			subjectNO.setPrincipalTradeType(TradeTypeConstants.CJYQCHBJ);
			subjectNO.setInverestTradeType(TradeTypeConstants.CJYQCHLX);
			subjectNO.setMonthManagerTradeTpye(TradeTypeConstants.CJYQCHGLF);
			subjectNO.setOverdueFreeTradeTpye(TradeTypeConstants.CJYQCHYQFX);
			subjectNO.setPenaltyTradeType(TradeTypeConstants.CJYQCHYQGLF);
		}else{
			//出方
			// 本金
			subjectNO.setLoanerPrincipalAdjustOut(NewConstSubject.loaner_principal_adjust_senior_overdue_out);
			subjectNO.setPrincipalOut(NewConstSubject.principal_senior_overdue_out);
			subjectNO.setInvesterPrincipalAdjustOut(NewConstSubject.invester_principal_adjust_senior_overdue_out);
			// 利息
			subjectNO.setLoanerInverestAdjustOut(NewConstSubject.loaner_inverest_adjust_senior_overdue_out);
			subjectNO.setInverestOut(NewConstSubject.inverest_senior_overdue_out);
			subjectNO.setInvesterInverestAdjustOut(NewConstSubject.invester_inverest_adjust_senior_overdue_out);
			//逾期罚息
			subjectNO.setLoanerOverdueFreeAdjustOut(NewConstSubject.loaner_overdueFree_adjust_senior_overdue_out);
			subjectNO.setOverdueFreeOut(NewConstSubject.overdueFree_senior_overdue_out);
			subjectNO.setInvesterOverdueFreeAdjustOut(NewConstSubject.invester_overdueFree_adjust_senior_overdue_out);
			//逾期违约金
			subjectNO.setPenaltyOut(NewConstSubject.penalty_senior_overdue_out);
			//月缴管理费
			subjectNO.setMonthManageCostOut(NewConstSubject.senior_overdue_month_manage_cost_out);
			
			//入方
			
			// 本金
			subjectNO.setLoanerPrincipalAdjustIn(NewConstSubject.loaner_principal_adjust_senior_overdue_in);
			subjectNO.setPrincipalIn(NewConstSubject.principal_senior_overdue_in);
			subjectNO.setInvesterPrincipalAdjustIn(NewConstSubject.invester_principal_adjust_senior_overdue_in);
			// 利息
			subjectNO.setLoanerInverestAdjustIn(NewConstSubject.loaner_inverest_adjust_senior_overdue_in);
			subjectNO.setInverestIn(NewConstSubject.inverest_senior_overdue_in);
			subjectNO.setInvesterInverestAdjustIn(NewConstSubject.invester_inverest_adjust_senior_overdue_in);
			//逾期罚息
			subjectNO.setLoanerOverdueFreeAdjustIn(NewConstSubject.loaner_overdueFree_adjust_senior_overdue_in);
			subjectNO.setOverdueFreeIn(NewConstSubject.overdueFree_senior_overdue_in);
			subjectNO.setInvesterOverdueFreeAdjustIn(NewConstSubject.invester_overdueFree_adjust_senior_overdue_in);
			//逾期违约金
			subjectNO.setPenaltyIn(NewConstSubject.penalty_senior_overdue_in);
			//月缴管理费
			subjectNO.setMonthManageCostIn(NewConstSubject.senior_overdue_month_manage_cost_in);
			
			
			//交易类型
			subjectNO.setPrincipalTradeType(TradeTypeConstants.GJYQCHBJ);
			subjectNO.setInverestTradeType(TradeTypeConstants.GJYQCHLX);
			subjectNO.setMonthManagerTradeTpye(TradeTypeConstants.GJYQCHGLF);
			subjectNO.setOverdueFreeTradeTpye(TradeTypeConstants.GJYQCHYQFX);
			subjectNO.setPenaltyTradeType(TradeTypeConstants.GJYQCHYQGLF);
		}
		return subjectNO;
		
	}
}
