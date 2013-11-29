package com.zendaimoney.online.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Copyright (c) 2013 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2013-1-31 下午1:40:37
 * operation by: Ray
 * description: 此类记录所有科目号，替代原有后科目号，优化后将弃用原来的ConstSubject类
 */
public class NewConstSubject {
	
	public static Map<String,String> subjectMap = null;
	/*充值科目号*/
	//出方
	public static final String recharge_fee_out="04090304143";//充值手续费_流出账户
	//04090304143

	//入方
	public static final String prepaid="01010104104";//充值金额_流入账户
	public static final String recharge_fee_in="04040304143";//充值手续费_流入账户
	
	/*提现科目号*/
	//出方
	public static final String withdraw_money="01010203140";//提现
	public static final String withdraw_freeze="01010603140";//冻结提现
	public static final String withdraw_fee_freeze ="01010603240";//冻结提现手续费
	public static final String withdraw_fee_out ="04090303143";//提现手续费(出方)
	public static final String withdraw_fee_out_fail ="04090303243";//提现手续费(出方,失败)
	
	//入方
	public static final String withdraw_unfreeze ="01010703104";//提现金额解冻
	public static final String withdraw_fee_unfreeze ="01010703204";//提现手续费解冻
	public static final String withdraw_fee_in ="04040303243";//提现手续费（入方）
	public static final String withdraw_money_out ="04040303143";//提现手续费(出方)
	
	public static final String withdraw_unfreeze_fail="01010703304";//提现金额(提现失败时使用)
	public static final String withdraw_fee_unfreeze_fail="01010703404";//提现手续费(提现失败时使用)
	
	
	
	/*ID5验证科目号*/
	public static final String id5_fee_out="04090307143";//ID5验证手续费_流出
	public static final String id5_fee_in="04040307143";//ID5验证手续费_流入
	
	/*投标科目号*/
	//出方
	public static final String bid_freeze_out="01010305145";//投标冻结_流出账户
	//入方
	public static final String bid_freeze_in="01010405145";//投标冻结_流入账户 
	
	/*流标科目号*/
	//出方
	public static final String bid_unfree_out="01010306154";//投标解冻_流出  
	//入方										
	public static final String bid_unfree_in="01010406154";//投标解冻_流入	
	
	/*放款科目号*/
	//出方
	public static final String bid_unfree_out_fk="01010301154";//放款解冻（理4到理1）
	public static final String adjust_account_out="01010301141";//调账_流出账户 理财现金账户转到理财账户（4到1）
	public static final String bid_succ_out="01010301112";//投标成功_流出账户    理财人账户1到借款人账户2（理1到借2）
	public static final String loan_succ_transfer_out="01020101121";//借款成功(债权关系转移)_流出账户 （借2到理1）
	public static final String loan_succ_pass_out="01010301124";//借款成功(放款)_流出账户	（2到4）
	public static final String loan_man_fee_out="04090301143";//借款管理费_流出账户 （借4到30000000000100000010010）
	public static final String loan_loss_provision_out="04090301243";//风险准备金_流出账户  （借4到30000000000100000010011）
	//入方
	public static final String bid_unfree_in_fk="01010401154";//放款解冻（理4到理1）
	public static final String adjust_account_in="01010401141";//调账_流入账户 理财现金账户转到理财账户（4到1）
	public static final String bid_succ_in="01010401112";//投标成功_流入账户	     理财人账户1到借款人账户2（理1到借2）
	public static final String loan_succ_transfer_in="01020201121";//借款成功(债权关系转移)_流入账户	（借2到理1）
	public static final String loan_succ_pass_in="01010401124";//借款成功(放款)_流入账户 （2到4）
	public static final String loan_man_fee_in="04040301143";//借款管理费_流入账户		（借4到30000000000100000010010）
	public static final String loan_loss_provision_in="04040301243";//风除准备金_流入账户	（借4到30000000000100000010011）
	
	
	/* 正常还款*/
	//出方
	public static final String adjust_repayprincipal_loan_out="01010302142";//调账_流出账户  （借款人4到2）
	public static final String recv_principal_out="02010102121";//回收本_流出账户	（借2到理1）
	public static final String adjust_repayprincipal_invest_out="01010302114";//调账_理财人理财账户到现金账户	（理1到理4）
	public static final String adjust_repaypinterest_loan_out="01010302242"; //调账_借款人利息4到2
	public static final String recv_interest_out="04050102121";//回收息_流出账户
	public static final String adjust_repaypinterest_invest_out="01010302214";//调账_理财人理财账户到现金账户	（理1到理4）
	public static final String month_manage_cost_out="04090302243";//月缴管理费_出方
	//入方
	public static final String adjust_repayprincipal_loan_in="01010402142";//调账_流入账户	（借款人4到2）
	public static final String recv_principal_in="01010502121";//回收本_流入账户	（借2到理1）
	public static final String adjust_repayprincipal_invest_in="01010402114";//调账_理财人理财账户到现金账户	（理1到理4）
	public static final String adjust_repaypinterest_loan_in="01010402242";//调账_借款人利息4到2
	public static final String recv_interest_in="04010102121";//回收息_流入账户
	public static final String adjust_repaypinterest_invest_in="01010402214";//调账_理财人理财账户到现金账户	（理1到理4）
	public static final String month_manage_cost_in="04040302243";//月缴管理费_入方
	
	
	/*风险金垫付科目号*/
	//出方
	public static final String advance_principal_out="04090609131";//风险金垫付本金_流出账户 
	public static final String advance_succ_transfer_out="01020109113";//风险金代偿（债权转出）
	public static final String advance_interest_out="04090609231";//风险金垫付利息_流出账户 
	//入方
	public static final String advance_principal_in="04040609131";//风险金垫付本金 _流入账户
	public static final String advance_succ_transfer_in="01020209113";//风险金代偿（债权转入）
	public static final String advance_interest_in="04040609231";//风险金垫付利息_流出账户 
	
	/*初期逾期*/
	//出方
	public static final String loaner_principal_adjust_junior_overdue_out="01010308142";//本金调账_流出账户  （借款人4到2）
	public static final String principal_junior_overdue_out="02010108121";//偿还本金_流出账户
	public static final String invester_principal_adjust_junior_overdue_out="01010308114";//本金调账（理1到理4）
	public static final String loaner_inverest_adjust_junior_overdue_out="01010308242";//利息调账
	public static final String inverest_junior_overdue_out="04050108121";//偿还利息
	public static final String invester_inverest_adjust_junior_overdue_out="01010308214";//利息调账
	public static final String loaner_overdueFree_adjust_junior_overdue_out="01010308342";//逾期罚息调账
	public static final String overdueFree_junior_overdue_out="04050208121";//偿还逾期罚息
	public static final String invester_overdueFree_adjust_junior_overdue_out="01010308314";//逾期罚息调账
	public static final String penalty_junior_overdue_out="04090408143";//逾期违约金
	public static final String junior_overdue_month_manage_cost_out = "04090308143";//初级逾期月缴纳管理费
	
	//入方
	public static final String loaner_principal_adjust_junior_overdue_in="01010408142";//本金调账_流出账户  （借款人4到2）
	public static final String principal_junior_overdue_in="01010508121";//偿还本金_流出账户
	public static final String invester_principal_adjust_junior_overdue_in="01010408114";//本金调账（理1到理4）
	public static final String loaner_inverest_adjust_junior_overdue_in="01010408242";//利息调账
	public static final String inverest_junior_overdue_in="04010108121";//偿还利息
	public static final String invester_inverest_adjust_junior_overdue_in="01010408214";//利息调账
	public static final String loaner_overdueFree_adjust_junior_overdue_in="01010408342";//逾期罚息调账
	public static final String overdueFree_junior_overdue_in="04010208121";//偿还逾期罚息
	public static final String invester_overdueFree_adjust_junior_overdue_in="01010408314";//逾期罚息调账
	public static final String penalty_junior_overdue_in="04040408143";//逾期违约金
	public static final String junior_overdue_month_manage_cost_in = "04040308143";//初级逾期月缴纳管理费
	
	/****************高级逾期**********************/
	//出方
	public static final String loaner_principal_adjust_senior_overdue_out="01010311142";//本金调账_流出账户  （借款人4到2）
	public static final String principal_senior_overdue_out="02010111121";//偿还本金_流出账户
	public static final String invester_principal_adjust_senior_overdue_out="01010311114";//本金调账（理1到理4）
	public static final String loaner_inverest_adjust_senior_overdue_out="01010311242";//利息调账
	public static final String inverest_senior_overdue_out="04050111121";//偿还利息
	public static final String invester_inverest_adjust_senior_overdue_out="01010311214";//利息调账
	public static final String loaner_overdueFree_adjust_senior_overdue_out="01010311342";//逾期罚息调账
	public static final String overdueFree_senior_overdue_out="04050211121";//偿还逾期罚息
	public static final String invester_overdueFree_adjust_senior_overdue_out="01010311314";//逾期罚息调账
	public static final String penalty_senior_overdue_out="04090411143";//逾期违约金
	public static final String senior_overdue_month_manage_cost_out = "04090311143";//高级逾期月缴纳管理费
	
	//入方
	public static final String loaner_principal_adjust_senior_overdue_in="01010411142";//本金调账_流出账户  （借款人4到2）
	public static final String principal_senior_overdue_in="01010511121";//偿还本金_流出账户
	public static final String invester_principal_adjust_senior_overdue_in="01010411114";//本金调账（理1到理4）
	public static final String loaner_inverest_adjust_senior_overdue_in="01010411242";//利息调账
	public static final String inverest_senior_overdue_in="04010111121";//偿还利息
	public static final String invester_inverest_adjust_senior_overdue_in="01010411214";//利息调账
	public static final String loaner_overdueFree_adjust_senior_overdue_in="01010411342";//逾期罚息调账
	public static final String overdueFree_senior_overdue_in="04010211121";//偿还逾期罚息
	public static final String invester_overdueFree_adjust_senior_overdue_in="01010411314";//逾期罚息调账
	public static final String penalty_senior_overdue_in="04040411143";//逾期违约金
	public static final String senior_overdue_month_manage_cost_in = "04040311143";//高级逾期月缴纳管理费
	/************风险金代偿科目号*************/
	//出方
	public static final String principal_advance_out="04090609131";//代偿本金
	public static final String principal_advance_adjust_out="01010309114";//本金调账
	public static final String change_claims_relationship_out="01020109113";//	债权关系转移
	public static final String inverest_advance_out="04090609231";//代偿利息
	public static final String inverest_advance_adjust_out="01010309214";//利息调账
	//入方
	public static final String principal_advance_in="04040609131";//代偿本金
	public static final String principal_advance_adjust_in="01010409114";//本金调账
	public static final String change_claims_relationship_in="01020209113";//	债权关系转移	
	public static final String inverest_advance_in="04040609231";//代偿利息
	public static final String inverest_advance_adjust_in="01010409214";//利息调账
	
	
	/* 风险金垫付后，还款给证大*/
	//出方
	public static final String pay_principal_to_zendai_out="02010110143";//偿还本金给证大
	public static final String pay_interest_to_zendai_out="04050110143";//偿还利息给证大
	public static final String pay_overdueinterest_to_zendai_out="04050210143";//偿还逾期罚息给证大
	public static final String pay_overduefine_to_zendai_out="04090410143";//逾期违约金
	public static final String pay_month_manage_to_zendai_out="04090310143";//偿还月缴管理费给证大
	
	//入方
	public static final String pay_principal_to_zendai_in="01010510143";//偿还本金给证大
	public static final String pay_interest_to_zendai_in="04010110143";//偿还利息给证大
	public static final String pay_overdueinterest_to_zendai_in="04010210143";//偿还逾期罚息给证大
	public static final String pay_overduefine_to_zendai_in="04040410143";//逾期违约金
	public static final String pay_month_manage_to_zendai_in="04040310143";//偿还月缴管理费给证大
	
	
	/*一次性提前还款*/
	//出方
	public static final String once_pay_loaner_surplusPrincipal_adjust_out="01010312142";//一次性提前还款
	public static final String once_pay_surplusPrincipal_out="02010112121";//剩余本金
	public static final String once_pay_invester_surplusPrincipal_adjust_out="01010312114";//剩余本金调账
	public static final String once_pay_loaner_principal_adjust_out="01010312242";//本金调账
	public static final String once_pay_loaner_principal_out="02010112221";//偿还本金/回收本金
	public static final String once_pay_invester_principal_adjust_out="01010312214";//本金调账
	public static final String once_pay_loaner_interest_adjust_out="01010312342";//利息调账
	public static final String once_pay_interest_out="04050112121";//偿还利息
	public static final String once_pay_invester_interest_adjust_out="01010312314";//利息调账
	public static final String once_pay_loaner_penalty_adjust_out="01010312442";//提前还款违约金调账
	public static final String once_pay_penalty_out="04040412121";//提前还款违约金
	public static final String once_pay_invester_penalty_adjust_out="01010312414";//提前还款违约金调账
	public static final String once_pay_month_manage_to_zendai_out="04090312143";//偿还月缴管理
	
	//入方
	public static final String once_pay_loaner_surplusPrincipal_adjust_in="01010412142";//一次性提前还款
	public static final String once_pay_surplusPrincipal_in="01010512121";//剩余本金
	public static final String once_pay_invester_surplusPrincipal_adjust_in="01010412114";//剩余本金调账
	public static final String once_pay_loaner_principal_adjust_in="01010412242";//本金调账
	public static final String once_pay_loaner_principal_in="01010512221";//偿还本金/回收本金
	public static final String once_pay_invester_principal_adjust_in="01010412214";//本金调账
	public static final String once_pay_loaner_interest_adjust_in="01010412342";//利息调账
	public static final String once_pay_interest_in="04010112121";//偿还利息
	public static final String once_pay_invester_interest_adjust_in="01010412314";//利息调账
	public static final String once_pay_loaner_penalty_adjust_in="01010412442";//提前还款违约金调账
	public static final String once_pay_penalty_in="04090412121";//提前还款违约金
	public static final String once_pay_invester_penalty_adjust_in="01010412414";//提前还款违约金调账
	public static final String once_pay_month_manage_to_zendai_in="04040312143";//偿还月缴管理费
		
	
	
	public static final String TZ_ZDGLF_OUT="04040313134";//调账，从证大的月缴管理费调给理财人
	public static final String TZ_ZDYQFX_OUT="04040313234";//调账，从证大的逾期罚息调给理财人
	public static final String TZ_ZDYQWYJ_OUT="04040313334";//调账，从证大的逾期违约金调给理财人
	public static final String TZ_ZDCZSXF_OUT="04040313434";//调账，从证大的充值手续费调给理财人
	public static final String TZ_ZDTXSXF_OUT="04040313534";//调账，从证大的提现手续费调给理财人
	public static final String TZ_ZDJKSXF_OUT="04040313634";//调账，从证大的借款手续费调给理财人
	public static final String TZ_ZDSFYZSXF_OUT="04040313834";//调账，从证大的身份验证手续费调给理财人
	public static final String TZ_ZDFXZBJ_OUT="04040313734";//调账，从证大的风险准备金调给理财人

	public static final String TZ_ZDGLF_IN="01010413134";//调账，从证大的月缴管理费调给理财人
	public static final String TZ_ZDYQFX_IN="01010413234";//调账，从证大的逾期罚息调给理财人
	public static final String TZ_ZDYQWYJ_IN="01010413334";//调账，从证大的逾期违约金调给理财人
	public static final String TZ_ZDCZSXF_IN="01010413434";//调账，从证大的充值手续费调给理财人
	public static final String TZ_ZDTXSXF_IN="01010413534";//调账，从证大的提现手续费调给理财人
	public static final String TZ_ZDJKSXF_IN="01010413634";//调账，从证大的借款手续费调给理财人
	public static final String TZ_ZDSFYZSXF_IN="01010413834";//调账，从证大的身份验证手续费调给理财人
	public static final String TZ_ZDFXZBJ_IN="01010413734";//调账，从证大的风险准备金调给理财人

	
	/**
	 * @param subject
	 * @return 
	 * descript：根据流出科目号获取科目名称
	 */
	public static String getNameBySubject(String subject){
		if(subjectMap==null){
			subjectMap=new HashMap<String,String>();
			subjectMap.put(TZ_ZDGLF_OUT, "证大的月缴管理费调给理财人");
			subjectMap.put(TZ_ZDYQFX_OUT, "证大的逾期罚息调给理财人");
			subjectMap.put(TZ_ZDYQWYJ_OUT, "证大的逾期违约金调给理财人");
			subjectMap.put(TZ_ZDCZSXF_OUT, "证大的充值手续费调给理财人");
			subjectMap.put(TZ_ZDTXSXF_OUT, "证大的提现手续费调给理财人");
			subjectMap.put(TZ_ZDJKSXF_OUT, "证大的借款手续费调给理财人");
			subjectMap.put(TZ_ZDSFYZSXF_OUT, "证大的身份验证手续费调给理财人");
			
			subjectMap.put(TZ_ZDFXZBJ_OUT, "证大的风险准备金调给理财人");
			/******************正常还款*************************/
			//偿还本金
			subjectMap.put(adjust_repayprincipal_loan_out, "调账");
			subjectMap.put(recv_principal_out, "偿还本金/回收本金");
			subjectMap.put(adjust_repayprincipal_invest_out, "调账");
			//偿还利息
			subjectMap.put(adjust_repaypinterest_loan_out, "调账");
			subjectMap.put(recv_interest_out, "偿还利息/回收利息");
			subjectMap.put(adjust_repaypinterest_invest_out,"调账");
			//月缴管理费
			subjectMap.put(month_manage_cost_out,"月缴管理费");
			
			/******************初级逾期还款*************************/
			//本金
			subjectMap.put(loaner_principal_adjust_junior_overdue_out, "调账");
			subjectMap.put(principal_junior_overdue_out, "偿还本金/回收本金");
			subjectMap.put(invester_principal_adjust_junior_overdue_out, "调账");
			//利息
			subjectMap.put(loaner_inverest_adjust_junior_overdue_out, "调账");
			subjectMap.put(inverest_junior_overdue_out, "偿还利息/回收利息");
			subjectMap.put(invester_inverest_adjust_junior_overdue_out,"调账");
			//逾期违约金
			subjectMap.put(penalty_junior_overdue_out,"逾期违约金");
			//逾期罚息
			subjectMap.put(loaner_overdueFree_adjust_junior_overdue_out, "调账");
			subjectMap.put(overdueFree_junior_overdue_out, "逾期罚息");
			subjectMap.put(invester_overdueFree_adjust_junior_overdue_out,"调账");
			//月缴管理费
			subjectMap.put(junior_overdue_month_manage_cost_out,"月缴管理费");
			/******************高级逾期还款*************************/
			//本金
			subjectMap.put(loaner_principal_adjust_senior_overdue_out, "调账");
			subjectMap.put(principal_senior_overdue_out, "偿还本金/回收本金");
			subjectMap.put(invester_principal_adjust_senior_overdue_out, "调账");
			//利息
			subjectMap.put(loaner_inverest_adjust_senior_overdue_out, "调账");
			subjectMap.put(inverest_senior_overdue_out, "偿还利息/回收利息");
			subjectMap.put(invester_inverest_adjust_senior_overdue_out,"调账");
			//逾期违约金
			subjectMap.put(penalty_senior_overdue_out,"逾期违约金");
			//逾期罚息
			subjectMap.put(loaner_overdueFree_adjust_senior_overdue_out, "调账");
			subjectMap.put(overdueFree_senior_overdue_out, "逾期罚息");
			subjectMap.put(invester_overdueFree_adjust_senior_overdue_out,"调账");
			//月缴管理费
			subjectMap.put(senior_overdue_month_manage_cost_out,"月缴管理费");
			/******************一次性提前还款************************/
			//剩余本金
			subjectMap.put(once_pay_loaner_surplusPrincipal_adjust_out,"调账");
			subjectMap.put(once_pay_surplusPrincipal_out,"偿还剩余本金");
			subjectMap.put(once_pay_invester_surplusPrincipal_adjust_out,"调账");
			//本金
			subjectMap.put(once_pay_loaner_principal_adjust_out,"调账");
			subjectMap.put(once_pay_loaner_principal_out,"偿还本金");
			subjectMap.put(once_pay_invester_principal_adjust_out,"调账");
			//利息
			subjectMap.put(once_pay_loaner_interest_adjust_out,"调账");
			subjectMap.put(once_pay_interest_out,"偿还利息");
			subjectMap.put(once_pay_invester_interest_adjust_out,"调账");
			//提前还款违约金
			subjectMap.put(once_pay_loaner_penalty_adjust_out,"调账");
			subjectMap.put(once_pay_penalty_out,"提前还款违约金");
			subjectMap.put(once_pay_invester_penalty_adjust_out,"调账");
			//月缴管理费
			subjectMap.put(once_pay_month_manage_to_zendai_out,"月缴管理费");
			/******************还款 end*************************/
			/******************充值、提现*************************/
			subjectMap.put(prepaid,"充值");
			subjectMap.put(recharge_fee_out,"充值手续费");
			subjectMap.put(withdraw_money,"提现");
			subjectMap.put(withdraw_freeze, "提现冻结");
			subjectMap.put(withdraw_fee_out, "提现手续费");
			subjectMap.put(withdraw_money_out, "提现手续费");
			subjectMap.put(withdraw_fee_out_fail, "提现手续费");
			
			subjectMap.put(withdraw_fee_freeze,"提现手续费冻结");
			subjectMap.put(id5_fee_in,"ID5验证手续费");
			subjectMap.put(id5_fee_out,"ID5验证手续费");
			subjectMap.put(withdraw_unfreeze_fail,"提现金额解冻");
			subjectMap.put(withdraw_unfreeze,"提现金额解冻");
			
			subjectMap.put(withdraw_fee_unfreeze_fail,"提现手续费解冻");
			subjectMap.put(withdraw_fee_unfreeze,"提现手续费解冻");
			subjectMap.put(withdraw_fee_in,"提现手续费");
			

			/******************风险金垫付还款*****************************/
			subjectMap.put(pay_principal_to_zendai_out,"偿还本金");
			subjectMap.put(pay_interest_to_zendai_out,"偿还利息");
			subjectMap.put(pay_overdueinterest_to_zendai_out,"逾期罚息");
			subjectMap.put(pay_month_manage_to_zendai_out,"月缴管理费");
			subjectMap.put(pay_overduefine_to_zendai_out, "逾期违约金");
			
			/******************风险金垫付*****************************/
			subjectMap.put(principal_advance_out,"风险金代偿（本金）");
			subjectMap.put(principal_advance_adjust_out,"调账");
			subjectMap.put(change_claims_relationship_out,"风险金代偿（债权转移）");
			subjectMap.put(inverest_advance_out,"风险金代偿（利息）");
			subjectMap.put(inverest_advance_adjust_out,"调账");
			
			//TODO
//			subjectMap.put(interest_overdue_out,"逾期罚息");
//			subjectMap.put(penalty_overdue_out,"逾期违约金");
//			subjectMap.put(withdraw_money_fee_out,"提现手续费");
//			subjectMap.put(recharge_fee_out,"充值手续费");
//			subjectMap.put(loan_man_fee_out,"借款管理费");
//			subjectMap.put(interest_overdue_out,"逾期罚息");
//			subjectMap.put(penalty_overdue_out,"逾期违约金");
//			subjectMap.put(advance_out,"风险金垫付");
//			subjectMap.put(adjust_account_out,"调账");
//			subjectMap.put(bid_unfree_out, "投标解冻");
//			subjectMap.put(id5_fee_out, "ID5验证手续费");
			
			
			//投标
			subjectMap.put(bid_freeze_out,"投标冻结");
			//流标
			subjectMap.put(bid_unfree_out,"投标解冻");
			//放款
			subjectMap.put(bid_unfree_out_fk,"放款投标解冻");
			subjectMap.put(adjust_account_out,"调账：理4-理1");
			subjectMap.put(bid_succ_out,"放款：理1-借2");
			subjectMap.put(loan_succ_transfer_out,"债权关系转移：借2-理1");
			subjectMap.put(loan_succ_pass_out,"调账：借2-借4");
			subjectMap.put(loan_man_fee_out,"借款管理费");
			subjectMap.put(loan_loss_provision_out,"风险准备金");
			
			
		}
		return (String)subjectMap.get(subject);
	}

}
