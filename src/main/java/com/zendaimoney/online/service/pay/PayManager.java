package com.zendaimoney.online.service.pay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.fundDetail.FundDao;
import com.zendaimoney.online.dao.pay.PayAcTFlowDao;
import com.zendaimoney.online.dao.pay.PayAcTLedgerDao;
import com.zendaimoney.online.dao.pay.PayDao;
import com.zendaimoney.online.dao.pay.PayExtractNoteDao;
import com.zendaimoney.online.dao.pay.PayFreezeFundsDao;
import com.zendaimoney.online.dao.pay.PayRechargeDao;
import com.zendaimoney.online.dao.pay.PayUserDao;
import com.zendaimoney.online.entity.account.AccountUsers;
import com.zendaimoney.online.entity.pay.PayAcTFlow;
import com.zendaimoney.online.entity.pay.PayExtractNote;
import com.zendaimoney.online.oii.pay.gopay.GoPayReqObj;
import com.zendaimoney.online.oii.pay.shengpay.ShengPayReqObj;
import com.zendaimoney.online.oii.pay.yeepay.Configuration;
import com.zendaimoney.online.oii.pay.yeepay.PayResObj;
import com.zendaimoney.online.oii.pay.yeepay.PaymentForOnlineService;
import com.zendaimoney.online.oii.pay.yeepay.YeePayReqObj;
import com.zendaimoney.online.service.common.RateCommonUtil;
import com.zendaimoney.online.service.newPay.PayManagerNew;
import com.zendaimoney.online.vo.pay.PayRechargeVO;
import com.zendaimoney.online.vo.pay.PayWithdrawVO;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-18 下午4:05:35 operation by: description:提现
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class PayManager {

	private static Logger logger = LoggerFactory.getLogger(PayManager.class);
	@Autowired
	private PayManagerNew payManagerNew;

	@Autowired
	private FundDao fundDao;

	@Autowired
	PayAcTLedgerDao payAcTLedgerDao;

	@Autowired
	PayAcTFlowDao payAcTFlowDao;

	@Autowired
	PayDao payDao;

	@Autowired
	PayFreezeFundsDao payFreezeFundsDao;

	@Autowired
	PayExtractNoteDao payExtractNoteDao;

	@Autowired
	CommonDao commonDao;

	@Autowired
	PayRechargeDao payRechargeDao;

	@Autowired
	PayUserDao payUserDao;
	@Autowired
	RateCommonUtil rateCommon;

	/**
	 * 根据用户ID查询手机是否绑定 2013-1-5 下午5:21:02 by HuYaHui
	 * 
	 * @param userId
	 *            用户ID
	 * @return false未绑定，true已绑定
	 */
	public boolean checkUserPhoneByUserId(BigDecimal userId) {
		Object obj = payDao.checkUserPhoneByUserId(userId);
		long count = Long.valueOf(obj + "");
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证身份证是否验证成功 2012-12-20 下午4:35:17 by HuYaHui
	 * 
	 * @return
	 */
	public boolean checkIdCard(BigDecimal userId) {
		long count = payDao.checkExistsByUserId(userId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public boolean isCharged(BigDecimal userId, List verifyStatus) {
		List ens = payExtractNoteDao.findByUserIdAndVerifyStatusIn(userId, verifyStatus);
		return ens.isEmpty() ? false : true;
	}

	/**
	 * 初始化提现页面
	 * 
	 * @param userId
	 * @return
	 */
	public PayWithdrawVO getPayWithdrawVO(BigDecimal userId) {
		PayWithdrawVO payWithdrawVO = new PayWithdrawVO();
		// 可提现金额只显示小数点后2位，后面的数据都不显示不四舍五入
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		String s = formater.format(getRealAmount(userId));
		payWithdrawVO.setReal_amount(s);// 用户可用余额
		payWithdrawVO.setRate_withdraw(commonDao.getRate(1L).getWithdraw().doubleValue() + "");// 提现手续费率
		payWithdrawVO.setAmount("");
		List<PayExtractNote> payBankList = null;
		AccountUsers user = payUserDao.findByUserId(userId);
		if (!(user.getIsApproveCard().equals("") && user.getIsApproveCard().equals(null))) {
			if (user.getIsApproveCard().equals(BigDecimal.ONE)) {
				payBankList = payExtractNoteDao.findByUserIdAndKaihuNameOrderByExtractTimeDesc(userId, user.getRealName());
				payWithdrawVO.setRealName(user.getRealName());
			} else {
				payBankList = payExtractNoteDao.findByUserIdOrderByExtractTimeDesc(userId);
			}
		} else {
			payBankList = payExtractNoteDao.findByUserIdOrderByExtractTimeDesc(userId);
		}
		if (payBankList.size() > 0) {
			payWithdrawVO.setBank_name(payBankList.get(0).getBankName());// 支行名称
			payWithdrawVO.setCard_num(payBankList.get(0).getBankCardNo()); // 银行卡号
			payWithdrawVO.setCustomer_name(payBankList.get(0).getKaihuName());// 开户名
		}
		return payWithdrawVO;
	}

	public List<PayExtractNote> getPayWithdrawBank(BigDecimal userId) {
		List<PayExtractNote> payExtractNoteList = new ArrayList<PayExtractNote>();
		AccountUsers user = payUserDao.findByUserId(userId);
		List<PayExtractNote> payBankList = null;
		if (!(user.getIsApproveCard().equals("") && user.getIsApproveCard().equals(null))) {
			if (user.getIsApproveCard().equals(BigDecimal.ONE)) {
				payBankList = payExtractNoteDao.findByUserIdAndKaihuNameOrderByExtractTimeDesc(userId, user.getRealName());
			} else {
				payBankList = payExtractNoteDao.findByUserIdOrderByExtractTimeDesc(userId);
			}
		} else {
			payBankList = payExtractNoteDao.findByUserIdOrderByExtractTimeDesc(userId);
		}
		for (PayExtractNote payBank : payBankList) {
			boolean flg = false;
			for (PayExtractNote pay : payExtractNoteList) {
				flg = pay.getBankCardNo().contains(payBank.getBankCardNo());
				if (flg) {
					break;
				}
			}
			if (!flg) {
				PayExtractNote payNote = new PayExtractNote();
				payNote.setBankCardNo(payBank.getBankCardNo());
				payNote.setBankName(payBank.getBankName());
				payNote.setKaihuName(payBank.getKaihuName());
				payNote.setDescription(payBank.getDescription());
				payExtractNoteList.add(payNote);
			}
		}
		return payExtractNoteList;
	}

	public boolean isApproveCard(BigDecimal userId) {
		AccountUsers user = payUserDao.findByUserId(userId);
		boolean flag;
		if (!(user.getIsApproveCard().equals("") && user.getIsApproveCard().equals(null))) {
			if (user.getIsApproveCard().equals(BigDecimal.ONE)) {
				flag = true;
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 初始化充值页面
	 * 
	 * @param userId
	 * @return
	 */
	public PayRechargeVO getPayRechargeVO(BigDecimal userId) {
		PayRechargeVO VO = new PayRechargeVO();
		VO.setReal_amount(getRealAmount(userId));
		// 根据渠道获取充值费率 modify by jihui 2013-03-21
		VO.setRate(rateCommon.reChargeRate(userId.longValue()));
		return VO;
	}

	/**
	 * 查询用户可用余额
	 * 
	 * @param userId
	 * @return
	 */
	public double getRealAmount(BigDecimal userId) {
		double amount_total = fundDao.getBalance(userId);// 查询账户余额
		// double freeze_lc = fundDao.getFreezeFundsFinancialByUserId(userId);//
		// 投标冻结金额
		// double freeze_tx = fundDao.getFreezeFundsWithdrawByUserId(userId);//
		// 提现冻结金额
		// double real_amount = Calculator.sub(
		// Calculator.sub(amount_total, freeze_lc), freeze_tx);// 可用余额
		// return real_amount = Calculator.round(real_amount, 2);
		return amount_total;
	}

	/**
	 * 提现成功测试
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public String withdrawSuccTest(BigDecimal userId) {
		PayExtractNote payExtractNote = payDao.queryDeaultExtractNote(userId);
		if (payExtractNote == null) {
			throw new RuntimeException("提现信息表与资金冻结表信息不一致!userId=" + userId);
		}
		return withdrawSucc(userId, payExtractNote.getExtractId());
	}

	/**
	 * 提现审核通过
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String withdrawSucc(BigDecimal userId, BigDecimal extract_id) {
		return payManagerNew.withdrawSucc(userId, extract_id);
	}

	/**
	 * 提现审核不通过
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String withdrawFail(BigDecimal userId, BigDecimal extract_id) {
		// PayAcTLedger act_4 = payDao.getPayAcTLedger(userId.longValue(),
		// "4");// 查询资金分账
		//
		// PayExtractNote payExtractNote =
		// payExtractNoteDao.findByExtractId(extract_id);// 查询提现信息
		// PayFreezeFunds payFreezeFunds =
		// payFreezeFundsDao.findByRechargeId(payExtractNote);// 查询冻结资金
		// if (payExtractNote == null) {
		// throw new RuntimeException("提现信息表与资金冻结表信息不一致!userId=" + userId);
		// }
		// // 提现失败，提现冻结资金减去当前提现申请金额
		// act_4.setPayBackAmt(ArithUtil.sub(act_4.getPayBackAmt(),ArithUtil.add(payExtractNote.getExtractAmount(),
		// payExtractNote.getExtractCost())));// 冻结资金清零
		// act_4.setAmount(act_4.getAmount() +
		// payExtractNote.getExtractAmount());// 体现失败,冻结资金流回资金账户。
		// // ----核心表结冻流水----
		// PayAcTFlow unFreeze_amount =
		// newFlow(payExtractNote.getExtractAmount(), "", "", " ",
		// act_4.getAccount(), " ", ConstSubject.withdraw_unfreeze,
		// "操作科目：提现解冻");
		// PayAcTFlow unFreeze_fee = newFlow(payExtractNote.getExtractCost(),
		// "", "", " ", act_4.getAccount(), " ", ConstSubject.withdraw_unfreeze,
		// "操作科目：提现解冻");
		// PayAcTFlow flow_zendai = newFlow(payExtractNote.getExtractCost(), "",
		// "", act_4.getAccount(), ZendaiAccountBank.zendai_acct7, "040903",
		// "040403", "操作科目：提现手续费");
		//
		// // 证大提现手续费账户收款
		// PayAcTLedger act_3 =
		// payAcTLedgerDao.findByBusiTypeAndAccountLike("3",
		// ZendaiAccountBank.zendai_acct7);
		// // PayAcTLedger act_3 =
		// //
		// payAcTLedgerDao.findByBusiTypeAndAccountLike("4",ZendaiAccountBank.zendai_acct7);
		// act_3.setAmount(ArithUtil.add(act_3.getAmount(),
		// payExtractNote.getExtractCost()));
		// // -------------业 务表解冻-----------------
		// payFreezeFunds.setFreezeStatus("0");
		// // -------------更新提现信息表-------------
		// payExtractNote.setVerifyStatus(new BigDecimal(3));// 审核状态:失败
		// // payExtractNote.setSysStatus(new BigDecimal(1));//系统状态：成功
		// // -------------------保存----------------------
		// // payExtractNoteDao.save(payExtractNote);//提现信息表保存
		//
		// logger.info("SAVE :AC_T_LEDGER || ID="+act_4.getId()+"  amount ="+act_4.getAmount());
		// payAcTLedgerDao.save(act_4);// 个人资金账户
		// logger.info("SAVE : AC_T_FLOW || ID="+unFreeze_amount.getId()+" ExtractAmount="+payExtractNote.getExtractAmount()+" account="+
		// act_4.getAccount()+" 010107 操作科目：提现解冻 ");
		// payAcTFlowDao.save(unFreeze_amount);// 解冻流水
		// logger.info("SAVE : AC_T_FLOW || ID="+unFreeze_fee.getId()+" ExtractCost="+payExtractNote.getExtractCost()+" account="+
		// act_4.getAccount()+" 010107   操作科目：提现解冻 ");
		// payAcTFlowDao.save(unFreeze_fee);// 解冻流水
		// logger.info("SAVE : AC_T_FLOW || ID="+flow_zendai.getId()+" ExtractNote="+payExtractNote.getExtractCost()+" account="+
		// act_4.getAccount()+" 30000000000100000010008    040903    040403    操作科目：提现手续费 ");
		// payAcTFlowDao.save(flow_zendai);//
		// logger.info("SAVE : FREEZE_FUNDS || ID= "+payFreezeFunds.getUserId()+" FreezeStatus=0 ");
		// payFreezeFundsDao.save(payFreezeFunds);// 业务冻结表保存
		// return "";
		return payManagerNew.withdrawFail(userId, extract_id);

	}

	/**
	 * 提现请求
	 * 
	 * @param payWithdrawVO
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String withdrawApply(BigDecimal userId, PayWithdrawVO payWithdrawVO) {
		return payManagerNew.withdrawApply(userId.longValue(), payWithdrawVO);

	}

	/**
	 * 充值回调
	 * @param amount
	 * @return
	 */
	@Transactional(readOnly = false)
	public String recharge(PayResObj ro) {
		return payManagerNew.recharge(ro);
	}

	// =============================================================
	private static final String tradType = "在线充值";

	public YeePayReqObj creYeePayReqObj(String amount, String bank, String url, BigDecimal userId) throws UnsupportedEncodingException {
		YeePayReqObj yeePayReqObj = new YeePayReqObj();
		yeePayReqObj.setKeyValue(Configuration.getInstance().getValue("keyValue"));// 商家密钥
		yeePayReqObj.setNodeAuthorizationURL(Configuration.getInstance().getValue("yeepayCommonReqURL"));
		yeePayReqObj.setP0_Cmd("Buy");
		yeePayReqObj.setP1_MerId(Configuration.getInstance().getValue("p1_MerId"));
		yeePayReqObj.setP2_Order(DateUtil.getTransactionSerialNumber((String.valueOf(Math.random())).substring(2, 8)));
		yeePayReqObj.setP3_Amt(amount);
		yeePayReqObj.setP4_Cur("CNY");

		yeePayReqObj.setP5_Pid("recharge");
		yeePayReqObj.setP6_Pcat("");
		yeePayReqObj.setP7_Pdesc("");
		yeePayReqObj.setP8_Url(url + "/pay/pay/callback");
		yeePayReqObj.setP9_SAF("0");
		yeePayReqObj.setPa_MP(userId.toString());
		yeePayReqObj.setPd_FrpId(bank.toUpperCase());// 银行编号必须大写
		yeePayReqObj.setPr_NeedResponse("1");
		yeePayReqObj.setR9_BType("2");
		yeePayReqObj.setHmac(PaymentForOnlineService.getReqMd5HmacForOnlinePayment(yeePayReqObj.getP0_Cmd(), yeePayReqObj.getP1_MerId(), yeePayReqObj.getP2_Order(), yeePayReqObj.getP3_Amt(), yeePayReqObj.getP4_Cur(), yeePayReqObj.getP5_Pid(), yeePayReqObj.getP6_Pcat(), yeePayReqObj.getP7_Pdesc(), yeePayReqObj.getP8_Url(), yeePayReqObj.getP9_SAF(), yeePayReqObj.getPa_MP(),
				yeePayReqObj.getPd_FrpId(), yeePayReqObj.getPr_NeedResponse(), yeePayReqObj.getKeyValue()));//
		return yeePayReqObj;
	}

	public GoPayReqObj creGoPayReqObj(
			String amount, String bank, String url, BigDecimal userId, HttpServletRequest req) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		GoPayReqObj goPayReqObj = new GoPayReqObj();
		goPayReqObj.setVersion("2.0");
		goPayReqObj.setLanguage("1");
		goPayReqObj.setTranCode("8888");
		goPayReqObj.setMerchantID(Configuration.getInstance().getValue("goPayMerchantID"));
		goPayReqObj.setMerOrderNum(DateUtil.getTransactionSerialNumber((String.valueOf(Math.random())).substring(2, 8)));
		goPayReqObj.setTranAmt(amount);
		goPayReqObj.setCurrencyType("156");
		goPayReqObj.setFrontMerUrl(url + "/myAccount/myAccount/showMyAccount?strUrlType=fundDetail");
		goPayReqObj.setBackgroundMerUrl(url + "/pay/pay/callbackForGoPay");
		goPayReqObj.setTranDateTime(DateUtil.getStrDate2(new Date()));
		goPayReqObj.setVirCardNoIn(Configuration.getInstance().getValue("goPayVirCardNoIn"));
		goPayReqObj.setTranIP(req.getRemoteAddr().equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : req.getRemoteAddr());
		goPayReqObj.setIsRepeatSubmit("1");
		goPayReqObj.setMerRemark1(userId.toString());
		goPayReqObj.setGoodsName(tradType);

		goPayReqObj.setNodeAuthorizationURL(Configuration.getInstance().getValue("goPayCommonReqURL"));

		goPayReqObj.setBankCode(bank.toUpperCase());
		goPayReqObj.setUserType("1");

		String plain = "version=[" + goPayReqObj.getVersion() + "]tranCode=[" + goPayReqObj.getTranCode() + "]merchantID=[" + goPayReqObj.getMerchantID() + "]merOrderNum=[" + goPayReqObj.getMerOrderNum() + "]tranAmt=[" + goPayReqObj.getTranAmt() + "]feeAmt=[" + goPayReqObj.getFeeAmt() + "]tranDateTime=[" + goPayReqObj.getTranDateTime() + "]frontMerUrl=["
				+ goPayReqObj.getFrontMerUrl() + "]backgroundMerUrl=[" + goPayReqObj.getBackgroundMerUrl() + "]orderId=[]gopayOutOrderId=[]tranIP=[" + goPayReqObj.getTranIP() + "]respCode=[]VerficationCode=[" + Configuration.getInstance().getValue("goPayVerficationCode") + "]";

		goPayReqObj.setSignValue(PaymentForOnlineService.md5(plain));

		return goPayReqObj;
	}

//	public ShengPayReqObj creShengPayReqObj(String amount, String bank, String url, BigDecimal userId, HttpServletRequest req,long orderNo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		ShengPayReqObj shengPayReqObj = new ShengPayReqObj();
//		shengPayReqObj.setMsgSender(Configuration.getInstance().getValue("shengPayMsgSender"));
//		shengPayReqObj.setOrderNo(orderNo+"");
//		shengPayReqObj.setOrderAmount(amount);
//		shengPayReqObj.setOrderTime(DateUtil.getStrDate2(new Date()));
//		shengPayReqObj.setInstCode(bank);
//		shengPayReqObj.setPageUrl(url + "/myAccount/myAccount/showMyAccount?strUrlType=fundDetail");
//		shengPayReqObj.setNotifyUrl(url + "/pay/pay/callbackForShengPay");
//		shengPayReqObj.setProductName(tradType);
//		shengPayReqObj.setBuyerIp(req.getRemoteAddr().equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : req.getRemoteAddr());
//		shengPayReqObj.setExt1(userId.toString());
//
//		shengPayReqObj.setNodeAuthorizationURL(Configuration.getInstance().getValue("shengPayCommonReqURL"));
//
//		shengPayReqObj.setSignMsg(PaymentForOnlineService.md5(shengPayReqObj.toSignString() + Configuration.getInstance().getValue("shengPaykeyValue")).toUpperCase());
//
//		return shengPayReqObj;
//	}
}
