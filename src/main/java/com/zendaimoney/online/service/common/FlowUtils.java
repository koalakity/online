package com.zendaimoney.online.service.common;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.dao.AcTFlowClassifyDAO;
import com.zendaimoney.online.dao.AcTFlowDAO;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.pay.PayDao;
import com.zendaimoney.online.entity.AcTFlowClassifyVO;
import com.zendaimoney.online.entity.AcTFlowVO;
import com.zendaimoney.online.entity.AcTLedgerVO;

/**
 * Copyright (c) 2013 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author wangtf
 * @date: 2013-2-19 上午10:10:51 operation by: description:记录流水工具类
 *        2个方法，一个记录流水表，一个记录流水归类表
 */
@Component
public class FlowUtils {
	public static final String KG = " ";
	private static Logger logger = LoggerFactory.getLogger(FlowUtils.class);
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private AcTFlowDAO acTFlowDao;
	@Autowired
	private AcTFlowClassifyDAO acTFlowClassifyDao;
	@Autowired
	private PayDao payDao;
	@Autowired
	private AcTFlowClassifyDAO acTFlowClassifyDAO;
	public static int count = 0;

	/**
	 * 保存AcTFlowVO和AcTFlowClassifyVO,用户相关流水 新增AC_T_FLOW记录 新增AC_T_FLOW_CLASSIFY记录
	 * 修改资金账户4的amount
	 * 
	 * 2013-2-19 下午7:44:15 by HuYaHui
	 * 
	 * @param tradeCode
	 *            交易记录ID
	 * @param tradeType
	 *            TradeTypeConstants中常量
	 * @param userId
	 *            用户ID
	 * @param inOrOut
	 *            设置AcTFlowClassifyVO的in和out相关属性 in|out|inOut
	 * @param tradeAmount
	 *            交易金额
	 * @param teller
	 *            官员号
	 * @param organ
	 *            营业网点
	 * @param account
	 *            账号
	 * @param acctTitle
	 *            科目号
	 * @param appoAcctTitle
	 *            对方科目号
	 * @return AcTFlowVO 流水对象
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public AcTFlowVO saveAcTFlowVOAndAcTFlowClassifyVOForYH(Long tradeCode, String tradeType, long userId, String inOrOut, BigDecimal tradeAmount, String teller, String organ, String account, String acctTitle, String appoAcctTitle, AcTLedgerVO act4, int type) {
		logger.info("进入---saveAcTFlowVOAndAcTFlowClassifyVOForYH---");
		AcTLedgerVO act5 = payDao.getPayAcTLedgerNew(userId, "5");// 冻结账户

		AcTFlowVO actFlow = new AcTFlowVO();
		actFlow.setTradeDate(new Date());// 交易日期
		actFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
		actFlow.setTradeAmount(tradeAmount);// 交易金额
		actFlow.setTradeType("1");// 交易类型:现金
		actFlow.setTeller(teller);// 柜员号
		actFlow.setOrgan(organ);// 营业网点
		actFlow.setAccount(account);// 账号
		actFlow.setAcctTitle(acctTitle);// 科目号
		// 如果是提现，设置appoacct字段为空
		if (NewConstSubject.withdraw_money.equals(acctTitle)) {
			actFlow.setAppoAcct(KG);// 对方账户
		} else {
			actFlow.setAppoAcct(act4.getAccount());// 对方账户
		}

		actFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
		if (acctTitle != null && !acctTitle.trim().equals("")) {
			actFlow.setMemo("操作科目：" + NewConstSubject.getNameBySubject(acctTitle));// 备注
		} else {
			actFlow.setMemo("操作科目：" + NewConstSubject.getNameBySubject(appoAcctTitle));// 备注
		}
		acTFlowDao.save(actFlow);
		logger.info("保存交易流水成功,操作科目为：" + actFlow.getMemo() + " 流水号为：" + actFlow.getTradeNo());

		AcTFlowClassifyVO vo = new AcTFlowClassifyVO();
		// 类型
		vo.setType(type);
		// 充值类型
		vo.setTradeType(tradeType);
		// 对应充值流水号actFlow0.getTradeNo()
		vo.setTradeNo(actFlow.getTradeNo());
		// 交易金额,充值金额
		vo.setTradeAmount(tradeAmount);
		// 充值记录ID
		vo.setTradeCode(tradeCode);
		// 时间
		vo.setGreateTime(new Date());
		if (inOrOut.equals("in")) {
			// 入方用户ID，当前用户
			vo.setInUserId(userId);
			// 现金账户，资金分账4的amount
			vo.setInCashAccount(act4.getAmount());
			// 冻结金额,资金分账4的冻结金额payBackAmt
			vo.setInCashFreeze(act4.getPayBackAmt());
			// 投标冻结，分账5的amount
			vo.setInTenderFreeze(act5.getAmount());
		} else if (inOrOut.equals("out")) {
			// 出方用户ID，当前用户
			vo.setOutUserId(userId);
			// 现金账户，资金分账4的amount
			vo.setOutCashAccount(act4.getAmount());
			// 冻结金额,资金分账4的冻结金额payBackAmt
			vo.setOutCashFreeze(act4.getPayBackAmt());
			// 投标冻结，分账5的amount
			vo.setOutTenderFreeze(act5.getAmount());
		} else {
			// in 和out TODO
		}
		acTFlowClassifyDAO.save(vo);
		logger.info("保存对象AcTFlowClassifyVO成功，类型为：" + tradeType);

		logger.info("结束---saveAcTFlowVOAndAcTFlowClassifyVOForYH---");
		return actFlow;
	}

	/**
	 * 保存AcTFlowVO和AcTFlowClassifyVO,证大相关流水 新增AC_T_FLOW记录 新增AC_T_FLOW_CLASSIFY记录
	 * 2013-2-22 上午11:19:45 by HuYaHui
	 * 
	 * @param tradeCode
	 *            交易记录ID
	 * @param tradeType
	 *            TradeTypeConstants中常量
	 * @param userId
	 *            用户ID
	 * @param tradeAmount
	 *            交易金额
	 * @param teller
	 *            柜员号
	 * @param organ
	 *            营业网点
	 * @param account
	 *            账号
	 * @param acctTitle
	 *            科目号
	 * @param appoAcctTitle
	 *            对方科目号
	 * @param fee
	 *            费用（流水表tradeAmount字段） 1.充值费率从数据库获取 2.提现按照比例计算
	 *            3.身份证验证从费率表获取ID5_FEE
	 * @param act3
	 *            类型为3的账户对象
	 * @param act4
	 *            类型为4的账户对象
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public AcTFlowVO saveAcTFlowVOAndAcTFlowClassifyVOForZD(Long tradeCode, String tradeType, long userId, BigDecimal tradeAmount, String teller, String organ, String account, String acctTitle, String appoAcctTitle, BigDecimal fee, AcTLedgerVO act3, AcTLedgerVO act4, int type) {
		logger.info("开始---saveAcTFlowVOAndAcTFlowClassifyVOForZD---");
		AcTLedgerVO act5 = payDao.getPayAcTLedgerNew(userId, "5");// 冻结账户
		AcTFlowVO actFlow = new AcTFlowVO();
		actFlow.setTradeDate(new Timestamp(System.currentTimeMillis()));
		actFlow.setTradeNo(DateUtil.getTransactionSerialNumber(commonDao.getFlowSeq()));
		actFlow.setTradeAmount(fee);
		actFlow.setTradeType("1");
		if (account != null && !account.trim().equals("")) {
			actFlow.setAccount(account);// 账号
		} else {
			actFlow.setAccount(act4.getAccount());
		}
		actFlow.setTeller(teller);// 柜员号
		actFlow.setOrgan(organ);// 营业网点
		actFlow.setAppoAcct(act3.getAccount());
		actFlow.setAcctTitle(acctTitle);
		actFlow.setAppoAcctTitle(appoAcctTitle);

		if (acctTitle != null && !acctTitle.trim().equals("")) {
			actFlow.setMemo("操作科目：" + NewConstSubject.getNameBySubject(acctTitle));
		} else {
			actFlow.setMemo("操作科目：" + NewConstSubject.getNameBySubject(appoAcctTitle));
		}

		// ===================保存=======================
		acTFlowDao.save(actFlow);// 记录流水
		logger.info("保存证大账户金额流水成功,操作科目为：" + NewConstSubject.getNameBySubject(acctTitle) + " 流水号为：" + actFlow.getTradeNo());

		AcTFlowClassifyVO vo = new AcTFlowClassifyVO();
		vo.setType(type);
		vo.setTradeType(tradeType);
		// 对应充值手续费流水号
		vo.setTradeNo(actFlow.getTradeNo());
		// 出方用户ID，当前用户
		vo.setOutUserId(userId);
		// 现金账户，资金分账4的amount
		vo.setOutCashAccount(act4.getAmount());
		// 冻结金额,资金分账4的冻结金额payBackAmt
		vo.setOutCashFreeze(act4.getPayBackAmt());
		// 投标冻结，分账5的amount
		vo.setOutTenderFreeze(act5.getAmount());
		// 交易金额,充值金额
		vo.setTradeAmount(tradeAmount);

		if (TypeConstants.SFYZ == type) {
			// 如果是身份验证，code保存主表的流水
			vo.setTradeCode(actFlow.getId());
		} else {
			// 充值记录ID
			vo.setTradeCode(tradeCode);
		}

		vo.setGreateTime(new Date());
		acTFlowClassifyDAO.save(vo);
		logger.info("保存AcTFlowClassifyVO成功，类型为：" + tradeType);

		logger.info("结束---saveAcTFlowVOAndAcTFlowClassifyVOForZD---");
		return actFlow;
	}

	/**
	 * @author wangtf
	 * @date 2013-2-19 上午10:07:19
	 * @param tradeAmount
	 *            交易金额
	 * @param teller
	 *            官员号
	 * @param Organ
	 *            营业网点
	 * @param account
	 *            账号
	 * @param appoAcct
	 *            对方账号
	 * @param acctTitle
	 *            科目号
	 * @param appoAcctTitle
	 *            对方科目号
	 * @return AcTFlowVO 流水对象 description:流水公用记录
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public AcTFlowVO setActFlow(BigDecimal tradeAmount, String teller, String Organ, String account, String appoAcct, String acctTitle, String appoAcctTitle) {
		AcTFlowVO actFlow = new AcTFlowVO();
		actFlow.setTradeDate(new Date());// 交易日期
		actFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
		actFlow.setTradeAmount(tradeAmount);// 交易金额
		actFlow.setTradeType("1");// 交易类型:现金
		actFlow.setTeller(teller);// 柜员号
		actFlow.setOrgan(Organ);// 营业网点
		actFlow.setAccount(account);// 账号
		actFlow.setAppoAcct(appoAcct);// 对方账户
		actFlow.setAcctTitle(acctTitle);// 科目号
		actFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
		if (acctTitle != null && !acctTitle.trim().equals("")) {
			actFlow.setMemo("操作科目：" + NewConstSubject.getNameBySubject(acctTitle));// 备注
		} else {
			actFlow.setMemo("操作科目：" + NewConstSubject.getNameBySubject(appoAcctTitle));// 备注
		}
		actFlow = acTFlowDao.save(actFlow);
		logger.info("保存资金流水记录，ID：" + actFlow.getId() + " 科目号：" + actFlow.getAcctTitle() + " 对方科目号：" + actFlow.getAppoAcctTitle() + " 账户：" + actFlow.getAccount());
		return actFlow;
	}

	/**
	 * @author Ray
	 * @date 2013-2-19 上午10:39:37
	 * @param tradeType
	 *            引用TradeTypeConstants中的常量
	 * @param tradeNo
	 *            流水号
	 * @param outUserId
	 *            出方用户ID
	 * @param outCashAmount
	 *            出方账户
	 * @param outCashFreeze
	 *            出方提现冻结金额
	 * @param outTenderFreeze
	 *            出方投标冻结
	 * @param inUserId
	 *            入方用户ID
	 * @param inCashAmount
	 *            入方账户
	 * @param inCashFreeze
	 *            入方提现冻结金额
	 * @param inTenderFreeze
	 *            入方投标冻结
	 * @param tradeAmount
	 *            交易金额
	 * @param tradeCode
	 *            交易记录ID(充值表ID、提现表ID、借款编号ID，根据tradeType选择填写)
	 * @param type
	 *            引用TypeConstants中的常量
	 * @return description: 此方法用于持久化流水归类表，对应的流水号于流水表流水号同
	 */
	public AcTFlowClassifyVO setAcTFlowClassify(String tradeType, String tradeNo, Long outUserId, BigDecimal outCashAmount, BigDecimal outCashFreeze, BigDecimal outTenderFreeze, Long inUserId, BigDecimal inCashAmount, BigDecimal inCashFreeze, BigDecimal inTenderFreeze, BigDecimal tradeAmount, Long tradeCode, int type) {
		AcTFlowClassifyVO acTFlowClass = new AcTFlowClassifyVO();
		acTFlowClass.setType(type);
		acTFlowClass.setTradeType(tradeType);
		acTFlowClass.setTradeNo(tradeNo);
		acTFlowClass.setOutUserId(outUserId);
		acTFlowClass.setOutCashAccount(outCashAmount);
		acTFlowClass.setOutCashFreeze(outCashFreeze);
		acTFlowClass.setOutTenderFreeze(outTenderFreeze);
		acTFlowClass.setInUserId(inUserId);
		acTFlowClass.setInCashAccount(inCashAmount);
		acTFlowClass.setInCashFreeze(inCashFreeze);
		acTFlowClass.setInTenderFreeze(inTenderFreeze);
		acTFlowClass.setTradeAmount(tradeAmount);
		acTFlowClass.setTradeCode(tradeCode);
		acTFlowClass.setGreateTime(new Date());
		acTFlowClass = acTFlowClassifyDao.save(acTFlowClass);
		return acTFlowClass;
	}

	public AcTFlowClassifyVO setAcTFlowClassify(String tradeType, String tradeNo, Long outUserId, BigDecimal outCashAmount, BigDecimal outCashFreeze, BigDecimal outTenderFreeze, Long inUserId, BigDecimal inCashAmount, BigDecimal inCashFreeze, BigDecimal inTenderFreeze, BigDecimal tradeAmount, Long tradeCode, int type, String memo) {
		AcTFlowClassifyVO acTFlowClass = new AcTFlowClassifyVO();
		acTFlowClass.setMemo(memo);
		acTFlowClass.setType(type);
		acTFlowClass.setTradeType(tradeType);
		acTFlowClass.setTradeNo(tradeNo);
		acTFlowClass.setOutUserId(outUserId);
		acTFlowClass.setOutCashAccount(outCashAmount);
		acTFlowClass.setOutCashFreeze(outCashFreeze);
		acTFlowClass.setOutTenderFreeze(outTenderFreeze);
		acTFlowClass.setInUserId(inUserId);
		acTFlowClass.setInCashAccount(inCashAmount);
		acTFlowClass.setInCashFreeze(inCashFreeze);
		acTFlowClass.setInTenderFreeze(inTenderFreeze);
		acTFlowClass.setTradeAmount(tradeAmount);
		acTFlowClass.setTradeCode(tradeCode);
		acTFlowClass.setGreateTime(new Date());
		acTFlowClass = acTFlowClassifyDao.save(acTFlowClass);
		return acTFlowClass;
	}

	/**
	 * 小表数据刷新使用 传入生成时间
	 */
	public AcTFlowClassifyVO setAcTFlowClassify(String tradeType, String tradeNo, Long outUserId, BigDecimal outCashAmount, BigDecimal outCashFreeze, BigDecimal outTenderFreeze, Long inUserId, BigDecimal inCashAmount, BigDecimal inCashFreeze, BigDecimal inTenderFreeze, BigDecimal tradeAmount, Long tradeCode, int type, Date createTime, String memo) {
		count++;
		AcTFlowClassifyVO acTFlowClass = new AcTFlowClassifyVO();
		acTFlowClass.setType(type);
		acTFlowClass.setTradeType(tradeType);
		acTFlowClass.setTradeNo(tradeNo);
		acTFlowClass.setOutUserId(outUserId);
		acTFlowClass.setOutCashAccount(outCashAmount);
		acTFlowClass.setOutCashFreeze(outCashFreeze);
		acTFlowClass.setOutTenderFreeze(outTenderFreeze);
		acTFlowClass.setInUserId(inUserId);
		acTFlowClass.setInCashAccount(inCashAmount);
		acTFlowClass.setInCashFreeze(inCashFreeze);
		acTFlowClass.setInTenderFreeze(inTenderFreeze);
		acTFlowClass.setTradeAmount(tradeAmount);
		acTFlowClass.setTradeCode(tradeCode);
		acTFlowClass.setGreateTime(createTime);
		acTFlowClass.setMemo(memo);
		acTFlowClass = acTFlowClassifyDao.save(acTFlowClass);
		return acTFlowClass;
	}

	/**
	 * 小表数据刷新使用 传入生成时间
	 */
	public AcTFlowClassifyVO setAcTFlowClassify(String tradeType, String tradeNo, Long outUserId, BigDecimal outCashAmount, BigDecimal outCashFreeze, BigDecimal outTenderFreeze, Long inUserId, BigDecimal inCashAmount, BigDecimal inCashFreeze, BigDecimal inTenderFreeze, BigDecimal tradeAmount, Long tradeCode, int type, Date createTime) {
		count++;
		AcTFlowClassifyVO acTFlowClass = new AcTFlowClassifyVO();
		acTFlowClass.setType(type);
		acTFlowClass.setTradeType(tradeType);
		acTFlowClass.setTradeNo(tradeNo);
		acTFlowClass.setOutUserId(outUserId);
		acTFlowClass.setOutCashAccount(outCashAmount);
		acTFlowClass.setOutCashFreeze(outCashFreeze);
		acTFlowClass.setOutTenderFreeze(outTenderFreeze);
		acTFlowClass.setInUserId(inUserId);
		acTFlowClass.setInCashAccount(inCashAmount);
		acTFlowClass.setInCashFreeze(inCashFreeze);
		acTFlowClass.setInTenderFreeze(inTenderFreeze);
		acTFlowClass.setTradeAmount(tradeAmount);
		acTFlowClass.setTradeCode(tradeCode);
		acTFlowClass.setGreateTime(createTime);
		acTFlowClass = acTFlowClassifyDao.save(acTFlowClass);
		return acTFlowClass;
	}
}
