package com.zendaimoney.online.service.newPay;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.AcTLedgerDAO;
import com.zendaimoney.online.dao.ExtractNoteDAO;
import com.zendaimoney.online.dao.FreezeFundsDAO;
import com.zendaimoney.online.dao.RechargeNoteDAO;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.pay.PayDao;
import com.zendaimoney.online.entity.AcTFlowVO;
import com.zendaimoney.online.entity.AcTLedgerVO;
import com.zendaimoney.online.entity.ExtractNoteVO;
import com.zendaimoney.online.entity.FreezeFundsVO;
import com.zendaimoney.online.entity.RechargeNoteVO;
import com.zendaimoney.online.oii.pay.yeepay.PayResObj;
import com.zendaimoney.online.service.common.FlowUtils;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.vo.pay.PayWithdrawVO;

/**
 * 资金相关记录翻新模块
 * 
 * @author HuYaHui
 * 
 */
@Component
public class PayManagerNew {
	public static final String KG = " ";
	private static Logger logger = LoggerFactory.getLogger(PayManagerNew.class);

	@Autowired
	private PayDao payDao;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private RechargeNoteDAO rechargeNoteDAO;

	@Autowired
	private FlowUtils flowUtils;

	@Autowired
	private FreezeFundsDAO freezeFundsDAO;

	@Autowired
	private ExtractNoteDAO extractNoteDAO;

	@Autowired
	private AcTLedgerDAO acTLedgerDAO;
	@Autowired
	public RateCommonUtil rateCommonUtil;

	/**
	 * 提现审核不通过 修改账户4的payBackAmt 修改账户3的amount 修改提现记录表状态 修改业务冻结表状态 保存解冻金额流水
	 * 保存解冻手续费流水 保存证大提现手续费流水 保存证大提现手续费子表流水
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String withdrawFail(BigDecimal userId, BigDecimal extract_id) {
		// 查询资金分账
		AcTLedgerVO act_4 = payDao.getPayAcTLedgerNew(userId.longValue(), "4");
		// 查询证大账户
		AcTLedgerVO act_3 = acTLedgerDAO.findByBusiTypeAndAccount("3", ZendaiAccountBank.zendai_acct7);

		ExtractNoteVO extractNote = extractNoteDAO.findByExtractId(extract_id.longValue());// 查询提现信息
		if (extractNote == null) {
			throw new RuntimeException("提现信息表与资金冻结表信息不一致!userId=" + userId);
		}
		FreezeFundsVO freezeFunds = freezeFundsDAO.findByRechargeId(extractNote.getExtractId());// 查询冻结资金
		// 提现失败，提现冻结资金减去当前提现申请金额
		act_4.setPayBackAmt(BigDecimalUtil.sub(act_4.getPayBackAmt(), BigDecimalUtil.add(extractNote.getExtractAmount(), extractNote.getExtractCost())));
		// 还回在提现申请时候扣除的本金（不返还手续费）
		act_4.setAmount(BigDecimalUtil.add(act_4.getAmount(), extractNote.getExtractAmount()));
		// 1.提现金额解冻
		AcTFlowVO act_amount = flowUtils.setActFlow(extractNote.getExtractAmount(), "", "", KG, act_4.getAccount(), KG, NewConstSubject.withdraw_unfreeze_fail);
		logger.info("提现金额解冻，记录流水ID为：" + act_amount.getId());

		// 2.提现手续费解冻
		AcTFlowVO act_fee = flowUtils.setActFlow(extractNote.getExtractCost(), "", "", KG, act_4.getAccount(), KG, NewConstSubject.withdraw_fee_unfreeze_fail);
		logger.info("提现手续费解冻，记录流水ID为：" + act_fee.getId());

		// 3.将提现手续费从现金账户中转到证大提现手续费账户
		AcTFlowVO actZD = flowUtils.saveAcTFlowVOAndAcTFlowClassifyVOForZD(extractNote.getExtractId(), TradeTypeConstants.TXSXFSB, userId.longValue(), extractNote.getExtractCost(), "", "", act_4.getAccount(), NewConstSubject.withdraw_fee_out_fail, NewConstSubject.withdraw_fee_in, extractNote.getExtractCost(), act_3, act_4, TypeConstants.TX);
		logger.info("将提现手续费从现金账户中转到证大提现手续费账户，记录流水ID为：" + actZD.getId());

		// -------------更新提现信息表-------------
		extractNote.setVerifyStatus(new BigDecimal(3));// 审核状态:失败
		extractNoteDAO.save(extractNote);
		// -------------------保存----------------------
		acTLedgerDAO.save(act_4);// 个人资金账户
		logger.info("修改个人资金账户数据,ID:" + act_4.getId() + "  金额:" + act_4.getAmount() + " 冻结金额：" + act_4.getPayBackAmt());

		// -------------业 务表解冻-----------------
		freezeFunds.setFreezeStatus("0");
		freezeFundsDAO.save(freezeFunds);// 业务冻结表保存
		logger.info("修改业务冻结数据，ID= " + freezeFunds.getFreezeId());

		act_3.setAmount(BigDecimalUtil.add(act_3.getAmount(), extractNote.getExtractCost()));
		acTLedgerDAO.save(act_3);// 保存公司账户
		logger.info("证大提现手续费账户收款,金额为：" + act_3.getAmount());

		return "";
	}

	/**
	 * 提现审核通过 修改账户4的payBackAmt 修改账户3的amount 修改提现记录表状态 修改业务冻结表状态 保存解冻金额流水
	 * 保存解冻手续费流水 保存提现金额流水 保存提现金额子表流水 保存证大提现手续费流水 保存证大提现手续费子表流水
	 * 
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String withdrawSucc(BigDecimal userId, BigDecimal extract_id) {
		// ---------------------提现审核成功--------------------------
		// 查询资金分账
		AcTLedgerVO act_4 = payDao.getPayAcTLedgerNew(userId.longValue(), "4");
		// 查询证大账户
		AcTLedgerVO act_3 = acTLedgerDAO.findByBusiTypeAndAccount("3", ZendaiAccountBank.zendai_acct7);
		ExtractNoteVO extractNote = extractNoteDAO.findByExtractId(extract_id.longValue());// 查询提现信息
		if (extractNote == null) {
			throw new RuntimeException("提现信息表与资金冻结表信息不一致!userId=" + userId);
		}
		FreezeFundsVO payFreezeFunds = freezeFundsDAO.findByRechargeId(extractNote.getExtractId());// 查询冻结资金

		// ----核心表结冻流水----
		AcTFlowVO flow_unfreeze_amount = flowUtils.setActFlow(extractNote.getExtractAmount(), "", "", KG, act_4.getAccount(), KG, NewConstSubject.withdraw_unfreeze);
		logger.info("提现金额解冻，记录流水，ID为：" + flow_unfreeze_amount.getId());

		AcTFlowVO flow_unfreeze_fee = flowUtils.setActFlow(extractNote.getExtractCost(), "", "", KG, act_4.getAccount(), KG, NewConstSubject.withdraw_fee_unfreeze);
		logger.info("提现手续费解冻，记录流水，ID为：" + flow_unfreeze_fee.getId());

		// ------记录交易流水子表---------
		// 冻结手续费
		BigDecimal extractCost = extractNote.getExtractCost();
		// 冻结金额
		BigDecimal extractAmount = extractNote.getExtractAmount();
		// 账户4冻结金额
		BigDecimal amount_4 = act_4.getPayBackAmt();

		// 提现成功，冻结金额
		act_4.setPayBackAmt(extractCost);
		// 加回在提现申请的时候扣除的手续费
		act_4.setAmount(BigDecimalUtil.add(act_4.getAmount(), extractCost));

		// 扣除个人账户的提现金额
		AcTFlowVO flow_4 = flowUtils.saveAcTFlowVOAndAcTFlowClassifyVOForYH(extractNote.getExtractId(), TradeTypeConstants.TX, userId.longValue(), "out", extractNote.getExtractAmount(), "", "", act_4.getAccount(), NewConstSubject.withdraw_money, "", act_4, TypeConstants.TX);
		logger.info("将提现金额从现金账户中减掉，记录流水，ID为：" + flow_4.getId());

		// 提现成功，冻结手续费
		act_4.setPayBackAmt(BigDecimal.ZERO);
		// 扣除在提现申请的时候扣除的手续费
		act_4.setAmount(BigDecimalUtil.sub(act_4.getAmount(), extractCost));
		// 扣除个人账户的提现手续费
		AcTFlowVO flow_zendai = flowUtils.saveAcTFlowVOAndAcTFlowClassifyVOForZD(extractNote.getExtractId(), TradeTypeConstants.TXSXF, userId.longValue(), extractNote.getExtractCost(), "", "", act_4.getAccount(), NewConstSubject.withdraw_fee_out, NewConstSubject.withdraw_money_out, extractNote.getExtractCost(), act_3, act_4, TypeConstants.TX);
		logger.info("将提现手续费从现金账户中转到证大提现手续费账户，记录流水，ID为：" + flow_zendai.getId());

		// 提现成功，当前提现冻结资金减去当前提现申请金额
		act_4.setPayBackAmt(BigDecimalUtil.sub(amount_4, BigDecimalUtil.add(extractAmount, extractCost)));// 冻结资金清零

		act_3.setAmount(BigDecimalUtil.add(act_3.getAmount(), extractNote.getExtractCost()));
		// -------------业 务表解冻-----------------
		payFreezeFunds.setFreezeStatus("0");
		// -------------更新提现信息表,审核状态:成功-------------
		extractNote.setVerifyStatus(new BigDecimal(2));
		// -------------------保存----------------------

		extractNoteDAO.save(extractNote);
		logger.info("更新提现信息表,审核状态:成功：" + extractNote.getExtractId());

		acTLedgerDAO.save(act_4);// 个人资金账户
		logger.info("提现成功，当前提现冻结资金减去当前提现申请金额,冻结金额：" + act_4.getPayBackAmt());

		acTLedgerDAO.save(act_3);// 保存公司账户
		logger.info("证大提现手续费账户收款,金额为：" + act_3.getAmount());

		freezeFundsDAO.save(payFreezeFunds);// 业务冻结表保存
		logger.info("查询冻结资金，状态为：" + payFreezeFunds.getFreezeStatus());
		return "提现成功";
	}

	/**
	 * 提现请求 修改账户4的金额 保存提现金额流水 保存提现手续费流水 保存业务冻结表记录 保存提现表记录
	 * 
	 * @param payWithdrawVO
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String withdrawApply(Long userId, PayWithdrawVO payWithdrawVO) {
		// 保证一个用户只能存在一个提现记录
		Long payExtractNoteCount = payDao.findByUserId(new BigDecimal(userId));
		System.out.println("---------------------->用户对应提现记录总数:" + payExtractNoteCount);
		if (payExtractNoteCount != null && payExtractNoteCount.intValue() > 0) {
			return "正在核审中!";
		}
		
		// ------------------------------冻金冻结-----------------
		// 查询资金分账
		AcTLedgerVO act_4 = payDao.getPayAcTLedgerNew(userId.longValue(), "4");
		// 计算手续费
		BigDecimal amount = new BigDecimal(payWithdrawVO.getAmount());// 取出提现金额
		BigDecimal fee = calWithdraw(amount);
		BigDecimal totalPayBackAmt = BigDecimalUtil.add(amount, fee);// 实际金额=提现金额+手续费
		int count = payDao.updateAmountAndPayBackAmtByIdAndAmount(totalPayBackAmt.doubleValue(), BigDecimalUtil.sub(act_4.getAmount(), totalPayBackAmt).doubleValue(), act_4.getId(), act_4.getPayBackAmt().doubleValue());
		System.out.println("---------------------->更新影响行总数:" + count);
		if (count <= 0) {
			throw new RuntimeException("提现失败！");
		}
		AcTFlowVO flow_amount = flowUtils.setActFlow(amount, "", "", act_4.getAccount(), "", NewConstSubject.withdraw_freeze, "");
		logger.info("保存流水成功，提现金额：" + amount + " 流水ID为：" + flow_amount.getId());

		AcTFlowVO flow_fee = flowUtils.setActFlow(fee, "", "", act_4.getAccount(), "", NewConstSubject.withdraw_fee_freeze, "");
		logger.info("保存流水成功，提现手续费" + fee + " 流水ID为：" + flow_fee.getId());
		// -------------业务表提现冻结--------------
		FreezeFundsVO withdrawFreeze = new FreezeFundsVO();
		withdrawFreeze.setUserId(userId);
		withdrawFreeze.setFreezeKind("2");// 提现冻结
		withdrawFreeze.setFreezeMoney(totalPayBackAmt);// 冻结金额
		withdrawFreeze.setFreezeTime(new Timestamp(System.currentTimeMillis()));
		withdrawFreeze.setFreezeStatus("1");// 状态 1冻结 2解冻

		// ---------------提现信息表-------------------
		ExtractNoteVO extractNote = new ExtractNoteVO();
		extractNote.setUserId(userId);// 用户ID
		extractNote.setExtractAmount(amount);// 提现金额
		extractNote.setExtractCost(fee);// 提出费用
		extractNote.setRealAmount(totalPayBackAmt);
		extractNote.setKaihuName(payWithdrawVO.getCustomer_name());// 开户名
		extractNote.setBankName(payWithdrawVO.getBank_name());// 支行名称
		extractNote.setBankCardNo(payWithdrawVO.getCard_num());// 银行卡号
		extractNote.setExtractTime(new Timestamp(System.currentTimeMillis()));// 提现时间
		extractNote.setVerifyStatus(new BigDecimal(0));// 审核状态:审核中ing
		extractNote.setDescription("->提现");// 描述
		extractNote = extractNoteDAO.save(extractNote);
		logger.info("保存提现流水表成功,ID为：" + extractNote.getExtractId());
		withdrawFreeze.setRechargeId(extractNote.getExtractId());
		freezeFundsDAO.save(withdrawFreeze);// 保存冻结信息
		logger.info("保存业务冻结表记录,ID为：" + withdrawFreeze.getFreezeId());
		return "正在核审中!";
	}

	/**
	 * 充值,重构 修改账户3的amoun 修改账户4的amount 保存充值表记录 保存充值金额流水 保存充值金额子表流水 保存充值手续费流水
	 * 保存充值手续费子表流水
	 * 
	 * @param amount
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String recharge(PayResObj ro) throws RuntimeException {
		RechargeNoteVO setobj = new RechargeNoteVO();
		setobj.setStatus("0");
		RechargeNoteVO whereparam = new RechargeNoteVO();
		whereparam.setOrderno(ro.getOrderno());
		whereparam.setStatus("0");
		// 判断某个订单ID没有支付成功
		int hasRecharged = payDao.update(setobj, whereparam);
		logger.info("进入回调返回，返回的充值记录orderno:"+ro.getOrderno()+" 判断某个订单ID没有支付成功相应结果："+hasRecharged);
		if (hasRecharged == 1) {
			// 操作核心库
			setPayZendai(ro);
			return "充值成功!";
		} else {
			logger.info("重复充值,订单ID:" + ro.getOrderno());
			return "请勿重复充值！";
		}
	}

	/**
	 * 借款人收取各种管理费用 ,fee:手续费
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void setPayZendai(PayResObj ro) throws RuntimeException {
		long userId = Long.valueOf(ro.getUserId());

		BigDecimal tradeAmount = new BigDecimal(ro.getAmount());// 充值金额
		BigDecimal fee = calRechargeFee(userId, tradeAmount);
		// 修改充值记录
		String orderno = saveRechargeNote(ro, fee);
		long tradeCode=rechargeNoteDAO.findByOrderno(orderno).getId();
		logger.info("保存充值记录成功，ID为：" + tradeCode);

		// 查询资金分账
		AcTLedgerVO act4 = payDao.getPayAcTLedgerNew(userId, "4");
		// 账户金额加上充值金额
		act4.setAmount(BigDecimalUtil.add(act4.getAmount(), tradeAmount));
		// 账户金额，减去手续费
		act4.setAmount(BigDecimalUtil.sub(act4.getAmount(), fee));
		
		// 查询证大账户
		AcTLedgerVO act3 = acTLedgerDAO.findByBusiTypeAndAccount("3", ZendaiAccountBank.zendai_acct6);
		act3.setAmount(BigDecimalUtil.add(act3.getAmount(), fee));

		// 保存分账
		acTLedgerDAO.save(act4);
		logger.info("修改账户4的金额：" + act4.getAmount());

		// 证大公司账户收管理费--并记流水
		acTLedgerDAO.save(act3);
		logger.info("修改账户3的金额：" + act3.getAmount());

		// 保存充值流水和小表记录
		flowUtils.saveAcTFlowVOAndAcTFlowClassifyVOForYH(tradeCode, TradeTypeConstants.CZ, userId, "in", tradeAmount, null, null, KG, KG, NewConstSubject.prepaid, act4, TypeConstants.CZ);

		// 把充值手续费记录到证大账户下
		flowUtils.saveAcTFlowVOAndAcTFlowClassifyVOForZD(tradeCode, TradeTypeConstants.CZSXF, userId, fee, "", "", "", NewConstSubject.recharge_fee_out, NewConstSubject.recharge_fee_in, fee, act3, act4, TypeConstants.CZ);
	}

	/**
	 * 保存充值信息
	 * 
	 * @param yeePayResObj
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private String saveRechargeNote(PayResObj ro, BigDecimal fee) throws RuntimeException {
		// 根据订单ID更新记录，如果存在，视为重复支付,抛出异常
		RechargeNoteVO obj = new RechargeNoteVO();
		String orderno=ro.getOrderno();
		obj.setStatus("1");
		obj.setUpdateTime(new Date());
		obj.setPayRs(ro.getPayRs());
		obj.setFlowCode(ro.getFlowno());
		obj.setBankCode(ro.getBankCode());
		RechargeNoteVO param = new RechargeNoteVO();
		param.setOrderno(orderno);
		param.setStatus("0");
		int count = payDao.update(obj, param);
		logger.error("根据订单编号更新数据，影响行为："+count+" 第三方编号："+ro.getFlowno()+ " 金额：" + ro.getAmount());
		if (count ==0) {
			logger.error("RechargeNoteVO记录订单状态不为0，orderno："+orderno);
			throw new RuntimeException("RechargeNoteVO记录订单状态不为0，orderno："+orderno);
		}
		return orderno;		
	}

	/**
	 * 保存充值记录
	 * 2013-3-27 下午1:41:27 by HuYaHui
	 * @param rechargeNoteVO
	 * @return
	 * 			保存后的记录ID
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public long savePayRechargeNote(RechargeNoteVO rechargeNoteVO){
		return rechargeNoteDAO.save(rechargeNoteVO).getId();
	}
	/**
	 * 计算充值手续费
	 * 2013-3-27 下午1:33:06 by HuYaHui
	 * @param userId
	 * 			当前用户ID
	 * @param amount
	 * 			交易金额
	 * @return
	 */
	public BigDecimal calRechargeFee(long userId, BigDecimal amount) {
		// 充值费率
		double rateRecharge = rateCommonUtil.reChargeRate(userId);
		return BigDecimalUtil.mul(rateRecharge, amount);
	}

	/**
	 * 提现手续费 2013-2-21 下午1:48:44 by HuYaHui
	 * 
	 * @param amount
	 *            提现金额
	 * @return
	 */
	private BigDecimal calWithdraw(BigDecimal amount) {
		BigDecimal fee = new BigDecimal(0);
		if (amount.doubleValue() < 20000) {
			fee = new BigDecimal(1);
		} else if (amount.doubleValue() < 50000 && amount.doubleValue() >= 20000) {
			fee = new BigDecimal(3);
		} else {
			fee = new BigDecimal(5);
		}
		return fee;
	}
}