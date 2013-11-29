package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.fundDetail.LedgerDao;
import com.zendaimoney.online.admin.entity.fundDetail.AcTLedgerAdmin;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.pay.PayDao;
import com.zendaimoney.online.entity.AcTFlowVO;
import com.zendaimoney.online.service.common.FlowUtils;

@Service
@Transactional
public class LedgerService {
	
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired
	private FlowUtils flowUtils;
	@Autowired
	private PayDao payDao;
	
	
	/**
	 * 查询资金账户
	 * 2013-3-6 上午9:51:46 by HuYaHui
	 */
	public List<AcTLedgerAdmin> getAccountList(String[] accArys){
		List<AcTLedgerAdmin> list=ledgerDao.findByAccountIn(accArys);
		for(AcTLedgerAdmin admin:list){
			admin.setAmount(BigDecimalUtil.forScale(new BigDecimal(admin.getAmount()),2).doubleValue());
			admin.setMemo(ZendaiAccountBank.getName(admin.getAccount()));
		}
		return list;
	}
	
	public List<AcTLedgerAdmin> findRiskLedgers(){
		return ledgerDao.findByAccountIn(new String[]{ZendaiAccountBank.zendai_acct10,ZendaiAccountBank.zendai_acct2,ZendaiAccountBank.zendai_acct3});
	}

	/**
	 * 统计集合中账户所有amount总和
	 * 2013-3-6 上午10:48:41 by HuYaHui
	 * @param list
	 * @return
	 */
	private BigDecimal sumAccount(List<AcTLedgerAdmin> list){
		BigDecimal amount=new BigDecimal("0");
		for(AcTLedgerAdmin admin:list){
			amount=BigDecimalUtil.add(amount, admin.getAmount());
		}
		return amount;
	}
	
	/**
	 * 网站资金账户调账
	 * 		查询除风险金资金账户外的所有账户总额
	 * 		如果当前调账的金额小于等于总额，则进行调账，
	 * 			根据页面选择的账户，扣除调账金额(可以为负)
	 * 
	 * 		否则返回异常
	 * 2013-3-6 下午2:17:09 by HuYaHui
	 * @param payAmt
	 * 			调账金额
	 * @param receiverEmail
	 * 			用户邮箱
	 * @param memo
	 * 			备注
	 * @param account
	 * 			证大账号
	 */
	@LogInfo(operateKind = OperateKind.调账, operateContent = "资金管理中网站资金账户调账成功")
	public void doPay(Double payAmt, String receiverEmail, String memo,String account) {
		AcTLedgerAdmin userAccount=ledgerDao.findOneByFinancialAcTCustomerAdminAccountUsersAdminEmailAndBusiType(receiverEmail,"4");
		if(null==userAccount){
			throw new BusinessException("收款人账号不存在");
		}
		String[] accArys=new String[]{
				ZendaiAccountBank.zendai_acct1,ZendaiAccountBank.zendai_acct2,
				ZendaiAccountBank.zendai_acct3,ZendaiAccountBank.zendai_acct6,
				ZendaiAccountBank.zendai_acct7,ZendaiAccountBank.zendai_acct9,ZendaiAccountBank.zendai_acct11};
		//所有账户总额
		BigDecimal amount=sumAccount(getAccountList(accArys));
		if(BigDecimalUtil.compareTo(payAmt, amount)>0){
			throw new BusinessException("网站资金账户余额不足！");
		}		
		payMethod(userAccount, payAmt, memo, account,receiverEmail);
	}

	/**
	 * 网站风险金账户调账
	 * 		查询风险金资金账户总额
	 * 		如果当前调账的金额小于等于总额，则进行调账，
	 * 			根据页面选择的账户，扣除调账金额(可以为负)
	 * 
	 * 		否则返回异常
	 * 2013-3-6 下午2:17:41 by HuYaHui
	 * @param payAmt
	 * 			调账金额
	 * @param receiverEmail
	 * 			用户邮箱
	 * @param memo
	 * 			备注
	 * @param account
	 * 			证大账号
	 */
	@LogInfo(operateKind = OperateKind.调账, operateContent = "资金管理中风险金账户调账成功")
	public void doPayRisk(Double payAmt, String receiverEmail, String memo,String account) {
		AcTLedgerAdmin userAccount=ledgerDao.findOneByFinancialAcTCustomerAdminAccountUsersAdminEmailAndBusiType(receiverEmail,"4");
		if(null==userAccount){
			throw new BusinessException("收款人账号不存在");
		}
		String[] accArys=new String[]{ZendaiAccountBank.zendai_acct10};
		//所有账户总额
		BigDecimal amount=sumAccount(getAccountList(accArys));
		if(BigDecimalUtil.compareTo(payAmt, amount)>0){
			throw new BusinessException("网站资金账户余额不足！");
		}
		payMethod(userAccount, payAmt, memo, account,receiverEmail);

	}
	
	/**
	 * 修改个人账户金额
	 * 修改证大账户金额
	 * 保存资金流水
	 * 2013-3-6 上午11:51:19 by HuYaHui
	 * @param userAccount
	 * 			用户账户对象
	 * @param siteAmount
	 * 			调账金额
	 * @param memo
	 * 			备注
	 * @param account
	 * 			证大账号
	 * @param email
	 * 			用户邮箱
	 * @throws BusinessException
	 */
	private void payMethod(AcTLedgerAdmin userAccount,Double siteAmount, String memo,String account,String email)throws BusinessException{
		AcTLedgerAdmin siteAccount=ledgerDao.findByAccountIn(new String[]{account}).get(0);
		//证大账户金额(可以为负数)=数据库中证大账户金额		减去		调账金额
		BigDecimal remain=BigDecimalUtil.sub(siteAccount.getAmount(), siteAmount);
		
		BigDecimal userAmount=BigDecimalUtil.add(userAccount.getAmount(), siteAmount);
		//设置用户的账户金额
		userAccount.setAmount(userAmount.doubleValue());
		//设置证大账户余额
		siteAccount.setAmount(remain.doubleValue());
		
		//写流水
		long userId=payDao.getUserIdByEmail(email);
		if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct1)){
			//月缴管理费

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDGLF_OUT,NewConstSubject.TZ_ZDGLF_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.YCXDQGLF, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}else if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct2)){
			//逾期罚息

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDYQFX_OUT,NewConstSubject.TZ_ZDYQFX_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.DCGJYQCHYQFX, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}else if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct3)){
			//逾期违约金

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDYQWYJ_OUT,NewConstSubject.TZ_ZDYQWYJ_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.DCGJYQCHYQGLF, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}else if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct6)){
			//充值手续费

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDCZSXF_OUT,NewConstSubject.TZ_ZDCZSXF_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.CZSXF, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}else if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct7)){
			//提现手续费

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDTXSXF_OUT,NewConstSubject.TZ_ZDTXSXF_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.TXSXF, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}else if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct9)){
			//借款手续费

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDJKSXF_OUT,NewConstSubject.TZ_ZDJKSXF_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.FKFEE, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}else if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct11)){
			//身份验证手续费

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDSFYZSXF_OUT,NewConstSubject.TZ_ZDSFYZSXF_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.ID5, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}else if(siteAccount.getAccount().equals(ZendaiAccountBank.zendai_acct10)){
			//风险准备金手续费

			//保存流水记录
			AcTFlowVO vo=flowUtils.setActFlow(
					new BigDecimal(siteAmount), "", "", siteAccount.getAccount(), userAccount.getAccount(), 
					NewConstSubject.TZ_ZDFXZBJ_OUT,NewConstSubject.TZ_ZDFXZBJ_IN);
			//保存资金流水子表记录
			flowUtils.setAcTFlowClassify(TradeTypeConstants.FXJDCBJ, vo.getTradeNo(), null, null, null, null, 
					userId, userAmount, null, null, new BigDecimal(siteAmount), vo.getId(), TypeConstants.TZ,memo);
		}
		
	}
	
	public Double calculateRiskBalance() {
		Double balance=0.0D;
		for (AcTLedgerAdmin	acTLedgerAdmin : findRiskLedgers()) {
			balance=ArithUtil.add(balance, acTLedgerAdmin.getAmount());
		}
		return balance;
	}

}
