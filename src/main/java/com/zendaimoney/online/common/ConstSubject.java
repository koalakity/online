package com.zendaimoney.online.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 科目号定义
 * @author mayb
 *
 */
public class ConstSubject {
	/**
	 * 通过流出科目号获取得科目名
	 * @return
	 */
	public static String getNameBySubject(String subject){
		if(subjectMap==null){
			subjectMap=new HashMap();
			subjectMap.put(prepaid, "充值");
			subjectMap.put(withdraw_money, "提现");
			subjectMap.put(bid_succ_out, "投标成功");
			subjectMap.put(loan_succ_pass_out, "借款成功(放款)");
			subjectMap.put(loan_succ_transfer_out, "借款成功(债权关系转移)");
			subjectMap.put(pay_principal_out, "偿还本金");
			subjectMap.put(pay_interest_out, "偿还利息");
			subjectMap.put(pay_interest_out, "偿还利息");
			subjectMap.put(recv_principal_out, "回收本金");
			subjectMap.put(recv_interest_out, "回收利息");
			subjectMap.put(repayment_early_out,"提前还款");
			subjectMap.put(recv_early_out,"提前回收");
			subjectMap.put(withdraw_money_fee_out,"提现手续费");
			subjectMap.put(recharge_fee_out,"充值手续费");
			subjectMap.put(loan_man_fee_out,"借款管理费");
			subjectMap.put(interest_overdue_out,"逾期罚息");
			subjectMap.put(penalty_overdue_out,"逾期违约金");
			subjectMap.put(advance_out,"风险金垫付");
			subjectMap.put(adjust_account_out,"调账");
			subjectMap.put(bid_unfree_out, "投标解冻");
			subjectMap.put(id5_fee_out, "ID5验证手续费");
		}
		return (String)subjectMap.get(subject);
	}
	//存入
	public static final String prepaid="010101";//充值
	public static final String loan_succ_pass_out="010103";//借款成功(放款)_流出账户
	public static final String loan_succ_pass_in="010104";//借款成功(放款)_流入账户
	public static final String loan_succ_transfer_out="010201";//借款成功(债权关系转移)_流出账户
	public static final String loan_succ_transfer_in="010202";//借款成功(债权关系转移)_流入账户
	public static final String recv_principal_out="020101";//回收本_流出账户
	public static final String recv_principal_in="010105";//回收本_流入账户
	public static final String recv_interest_out="040501";//回收息_流出账户
	public static final String recv_interest_in="040101";//回收息_流入账户
	public static final String recv_early_out="040404";//提前回收(违约金)_流出账户 
	public static final String recv_early_in="040904";//提前回收(违约金)_流入账户
	public static final String adjust_account_out="010103";//调账_流出账户
	public static final String adjust_account_in="010104";//调账_流入账户
	public static final String advance_out="040906";//风险金垫付_流出账户 
	public static final String advance_in="040406";//风险金垫付 _流入账户
	public static final String advance_succ_transfer_out="010201";//风险金代偿（债权转出）
	public static final String advance_succ_transfer_in="010202";//风险金代偿（债权转入）
	//支出
	public static final String withdraw_money="010102";//提现
	public static final String bid_succ_out=loan_succ_pass_out;//投标成功_流出账户
	public static final String bid_succ_in=loan_succ_pass_in;//投标成功_流入账户
	public static final String pay_principal_out=recv_principal_out;//偿还本_流出账户
	public static final String pay_principal_in=recv_principal_in;//偿还本_流入账户
	public static final String pay_interest_out=recv_interest_out;//偿还息_流出账户
	public static final String pay_interest_in=recv_interest_in;//偿还息_流入账户
	public static final String repayment_early_out=recv_early_out;//提前还款_流出账户
	public static final String repayment_early_in=recv_early_in;//提前还款_流入账户
	public static final String withdraw_money_fee_out="040903";//提现手续费_流出账户
	public static final String withdraw_money_fee_in="040403";//提现手续费_流入账户
	public static final String recharge_fee_out="040903";//充值手续费_流出账户
	public static final String recharge_fee_in="040403";//充值手续费_流入账户
	public static final String id5_fee_out="040903";//ID5验证手续费_流出
	public static final String id5_fee_in="040403";//ID5验证手续费_流入
	public static final String loan_man_fee_out="040903";//借款管理费_流出账户
	public static final String loan_man_fee_in="040403";//借款管理费_流入账户
	public static final String loan_loss_provision_out="040903";//风险准备金_流出账户
	public static final String loan_loss_provision_in="040403";//风除准备金_流入账户
	public static final String interest_overdue_out="040502";//逾期罚息_流出账户
	public static final String interest_overdue_in="040102";//逾期罚息_流入账户
	public static final String penalty_overdue_out="040904";//逾期违约金_流出账户
	public static final String penalty_overdue_in="040404";//逾期违约金_流入账户
	//
	public static final String withdraw_freeze="010106";//提现冻结(待回收字段)
	public static final String withdraw_unfreeze="010107";//提现冻结_解冻: 解冻和提现合并为一个动作
	public static final String withdraw_out="010102";//提现(取出)
	
	public static final String bid_freeze_out="010103";//投标冻结_流出账户
	public static final String bid_freeze_in="010104";//投标冻结_流入账户 
	
	public static final String bid_unfree_out="010103";//投标结冻_流出
	public static final String bid_unfree_in="010104";//投标结冻_流入
	
	
	public static Map subjectMap;
	
	public static final String[] deposit_subject_out=
	{
		prepaid,
		loan_succ_pass_out,
		loan_succ_transfer_out,
		recv_principal_out,
		recv_interest_out,
		recv_early_out,
		adjust_account_out,
		advance_out
	};//存入_科目号_流出_集合
	public static final String[] deposit_subject_in=
	{
		loan_succ_pass_in,
		loan_succ_transfer_in,
		recv_principal_in,
		recv_interest_in,
		recv_early_in,
		adjust_account_in,
		advance_in
	};//存入_科目号_流入_集合
	public static final String[] outlay_subject_out=
	{
		withdraw_money,
		bid_succ_out,
		pay_principal_out,
		pay_interest_out,
		repayment_early_out,
		withdraw_money_fee_out,
		recharge_fee_out,
		loan_man_fee_out,
		loan_loss_provision_out,
		interest_overdue_out,
		penalty_overdue_out
	};//支出_科目号_流出_集合
	public static final String[] outlay_subject_in=
	{
		bid_succ_in,
		pay_principal_in,
		pay_interest_in,
		repayment_early_in,
		withdraw_money_fee_in,
		recharge_fee_in,
		loan_man_fee_in,
		loan_loss_provision_in,
		interest_overdue_in,
		penalty_overdue_in
	};//支出_科目号_流入_集合
	/**
	 * 判断流出科目号是否属于存入
	 * @param subject
	 * @return
	 */
	public static boolean checkIn(String subject){
		if(subject==null||subject.equals("")){
			return false;
		}
		for(String item:deposit_subject_in){
			if(subject.equals(item)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断流出科目号是否属于支出
	 * @param subject
	 * @return
	 */
	public static boolean checkOut(String subject){
		if(subject==null||subject.equals("")){
			return false;
		}
		for(String item:outlay_subject_out){
			if(subject.equals(item)){
				return true;
			}
		}
		return false;
	}

}
