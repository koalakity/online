package com.zendaimoney.online.web.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.entity.RechargeNoteVO;
import com.zendaimoney.online.entity.pay.PayExtractNote;
import com.zendaimoney.online.oii.pay.gopay.GoPayReqObj;
import com.zendaimoney.online.oii.pay.shengpay.ShengPayReqObj;
import com.zendaimoney.online.oii.pay.yeepay.Configuration;
import com.zendaimoney.online.oii.pay.yeepay.PayResObj;
import com.zendaimoney.online.oii.pay.yeepay.PaymentForOnlineService;
import com.zendaimoney.online.oii.pay.yeepay.YeePayReqObj;
import com.zendaimoney.online.oii.pay.yeepay.YeePayResObj;
import com.zendaimoney.online.service.newPay.PayManagerNew;
import com.zendaimoney.online.service.pay.PayManager;
import com.zendaimoney.online.vo.pay.PayWithdrawVO;

@Controller
@RequestMapping(value = "/pay/pay/")
public class PayController {
	@Autowired
	PayManager payManager;
	
	@Autowired
	PayManagerNew payManagerNew;
	private static Logger logger = LoggerFactory.getLogger(PayController.class);
	
	private BigDecimal getUserId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal userId = ((BigDecimal) session.getAttribute("curr_login_user_id"));
		return userId;
	}

	@RequestMapping(value = "showPay")
	public String showPayHead(HttpServletRequest req, Model model) {
		BigDecimal userId = getUserId(req);
		model.addAttribute("payRechargeVO", payManager.getPayRechargeVO(userId));
		model.addAttribute("payWithdrawVO", payManager.getPayWithdrawVO(userId));
		List verifyStatus = new ArrayList();
		verifyStatus.add(new BigDecimal(0));
		verifyStatus.add(new BigDecimal(1));
		model.addAttribute("isCharged", payManager.isCharged(userId, verifyStatus));
		return "/pay/payHead";
	}

	@RequestMapping(value = "showPayRecharge")
	public String showPayRecharge(HttpServletRequest req, Model model) {
		BigDecimal userId = getUserId(req);
		model.addAttribute("payRechargeVO", payManager.getPayRechargeVO(userId));
		return "/pay/payRecharge";
	}

	@RequestMapping(value = "showPayWithdraw")
	public String showPayWithdraw(HttpServletRequest req, Model model) {
		BigDecimal userId = getUserId(req);
		model.addAttribute("payWithdrawVO", payManager.getPayWithdrawVO(userId));
		List<PayExtractNote> payWithdrawList = payManager.getPayWithdrawBank(userId);
		model.addAttribute("payWithdrawList", payWithdrawList);
		List verifyStatus = new ArrayList();
		verifyStatus.add(new BigDecimal(0));
		verifyStatus.add(new BigDecimal(1));
		model.addAttribute("isCharged", payManager.isCharged(userId, verifyStatus));
		model.addAttribute("flag", payManager.isApproveCard(userId));
		return "/pay/payWithdraw";
	}


	/**
	 * 提现
	 * 2013-2-17 下午2:03:19 by HuYaHui 
	 * @param payWithdrawVO
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "withdraw")
	public String withdraw(@ModelAttribute("payWithdrawVO") PayWithdrawVO payWithdrawVO, Model model, HttpServletRequest req) {
		BigDecimal userId = getUserId(req);
		payManager.withdrawApply(userId, payWithdrawVO);
		req.setAttribute("token", req.getParameter("token"));
		// payManager.withdrawSuccTest(userId);
		return "redirect:" + getHostUrl(req) + "/myAccount/myAccount/showMyAccount?strUrlType=fundDetail";
	}

	/**
	 * 充值
	 * 
	 * @param model
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "recharge")
	public String recharge(Model model, HttpServletRequest req, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		req.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		BigDecimal userId = getUserId(req);
		YeePayReqObj yeePayReqObj = null;
		GoPayReqObj goPayReqObj = null;
		String amount = req.getParameter("amount");
		String bank = req.getParameter("bank");
		req.setAttribute("token", req.getParameter("token"));
		String payType = req.getParameter("payType");
		
		//保存充值记录,状态：未支付
		RechargeNoteVO rechargeNoteVO = new RechargeNoteVO();
		rechargeNoteVO.setUserId(userId.longValue());
		rechargeNoteVO.setAmount(new BigDecimal(amount));
		BigDecimal fee=payManagerNew.calRechargeFee(userId.longValue(), new BigDecimal(amount));
		rechargeNoteVO.setFee(fee);
		rechargeNoteVO.setPayDate(new Date());
		rechargeNoteVO.setPayRs("0");
		rechargeNoteVO.setStatus("0");
		rechargeNoteVO.setFlowCode(" ");
		rechargeNoteVO.setBankCode(" ");
		if (payType.equalsIgnoreCase("01")) {
			rechargeNoteVO.setPayKind("0");
			yeePayReqObj = payManager.creYeePayReqObj(amount, bank, getHostUrl(req), userId);
			model.addAttribute("p0_Cmd", yeePayReqObj.getP0_Cmd());
			model.addAttribute("p1_MerId", yeePayReqObj.getP1_MerId());
			model.addAttribute("p2_Order", yeePayReqObj.getP2_Order());//把充值ID传给易宝
			model.addAttribute("p3_Amt", yeePayReqObj.getP3_Amt());
			model.addAttribute("p4_Cur", yeePayReqObj.getP4_Cur());
			model.addAttribute("p5_Pid", yeePayReqObj.getP5_Pid());
			model.addAttribute("p6_Pcat", yeePayReqObj.getP6_Pcat());
			model.addAttribute("p7_Pdesc", yeePayReqObj.getP7_Pdesc());
			model.addAttribute("p8_Url", yeePayReqObj.getP8_Url());
			model.addAttribute("p9_SAF", yeePayReqObj.getP9_SAF());
			model.addAttribute("pa_MP", yeePayReqObj.getPa_MP());
			model.addAttribute("pd_FrpId", yeePayReqObj.getPd_FrpId());
			model.addAttribute("r9_BType", yeePayReqObj.getR9_BType());
			model.addAttribute("pr_NeedResponse", yeePayReqObj.getPr_NeedResponse());
			model.addAttribute("hmac", yeePayReqObj.getHmac());
			model.addAttribute("formUrl", yeePayReqObj.getNodeAuthorizationURL());
			rechargeNoteVO.setOrderno(yeePayReqObj.getP2_Order());
			//保存充值信息
			long id=payManagerNew.savePayRechargeNote(rechargeNoteVO);
			logger.info("保存充值记录ID:"+id);
			// return "redirect:" + yeePayReqObj.getNodeAuthorizationURL();
			return "/pay/payWithYeePay";
		} else if (payType.equalsIgnoreCase("02")) {
			rechargeNoteVO.setPayKind("1");
			goPayReqObj = payManager.creGoPayReqObj(amount, bank, getHostUrl(req), userId, req);
			model.addAttribute("version", goPayReqObj.getVersion());
			model.addAttribute("language", goPayReqObj.getLanguage());
			model.addAttribute("tranCode", goPayReqObj.getTranCode());
			model.addAttribute("merchantID", goPayReqObj.getMerchantID());
			model.addAttribute("merOrderNum", goPayReqObj.getMerOrderNum());
			model.addAttribute("tranAmt", goPayReqObj.getTranAmt());
			model.addAttribute("currencyType", goPayReqObj.getCurrencyType());
			model.addAttribute("frontMerUrl", goPayReqObj.getFrontMerUrl());
			model.addAttribute("backgroundMerUrl", goPayReqObj.getBackgroundMerUrl());
			model.addAttribute("tranDateTime", goPayReqObj.getTranDateTime());
			model.addAttribute("virCardNoIn", goPayReqObj.getVirCardNoIn());
			model.addAttribute("tranIP", goPayReqObj.getTranIP());
			model.addAttribute("isRepeatSubmit", goPayReqObj.getIsRepeatSubmit());
			model.addAttribute("merRemark1", goPayReqObj.getMerRemark1());
			model.addAttribute("bankCode", goPayReqObj.getBankCode());
			model.addAttribute("userType", goPayReqObj.getUserType());
			model.addAttribute("goodsName", goPayReqObj.getGoodsName());
			model.addAttribute("signValue", goPayReqObj.getSignValue());
			model.addAttribute("formUrl", goPayReqObj.getNodeAuthorizationURL());
			
			rechargeNoteVO.setOrderno(goPayReqObj.getMerOrderNum());
			//保存充值信息
			long id=payManagerNew.savePayRechargeNote(rechargeNoteVO);
			logger.info("保存充值记录ID:"+id);
			return "/pay/payWithGoPay";
			// return "redirect:" + goPayReqObj.getNodeAuthorizationURL();
		}
		return null;
	}

	/**
	 * 易宝支付回调方法
	 * 
	 * @param model
	 * @param req
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "callback")
	public String callback(Model model, HttpServletRequest req, HttpServletResponse response, @RequestParam("p1_MerId") String p1_MerId, @RequestParam("r0_Cmd") String r0_Cmd, @RequestParam("r1_Code") String r1_Code, @RequestParam("r2_TrxId") String r2_TrxId, @RequestParam("r3_Amt") String r3_Amt, @RequestParam("r4_Cur") String r4_Cur,
			@RequestParam("r5_Pid") String r5_Pid, @RequestParam("r6_Order") String r6_Order, @RequestParam("r7_Uid") String r7_Uid, @RequestParam("r8_MP") String r8_MP, @RequestParam("r9_BType") String r9_BType, @RequestParam("rb_BankId") String rb_BankId, @RequestParam("ro_BankOrderId") String ro_BankOrderId, @RequestParam("rp_PayDate") String rp_PayDate,
			@RequestParam("rq_CardNo") String rq_CardNo, @RequestParam("ru_Trxtime") String ru_Trxtime, @RequestParam("hmac") String hmac) throws IOException {
		logger.info("进入易宝支付回调方法");
		try {
			r8_MP = r8_MP.replace(",", "");
			YeePayResObj yeePayResObj = new YeePayResObj();
			yeePayResObj.setP1_MerId(p1_MerId);
			yeePayResObj.setR0_Cmd(r0_Cmd);
			yeePayResObj.setR1_Code(r1_Code);
			yeePayResObj.setR2_TrxId(r2_TrxId);
			yeePayResObj.setR3_Amt(r3_Amt);
			yeePayResObj.setR4_Cur(r4_Cur);
			yeePayResObj.setR5_Pid(r5_Pid);
			yeePayResObj.setR6_Order(r6_Order);
			yeePayResObj.setR7_Uid(r7_Uid);
			yeePayResObj.setR8_MP(r8_MP);
			yeePayResObj.setR9_BType(r9_BType);
			yeePayResObj.setRb_BankId(rb_BankId);
			yeePayResObj.setRo_BankOrderId(ro_BankOrderId);
			yeePayResObj.setRp_PayDate(rp_PayDate);
			yeePayResObj.setRq_CardNo(rq_CardNo);
			yeePayResObj.setRu_Trxtime(ru_Trxtime);
			yeePayResObj.setHmac(hmac);
	
			PayResObj ro = new PayResObj();
			//充值流水号
			ro.setOrderno(yeePayResObj.getR6_Order());
			ro.setAmount(yeePayResObj.getR3_Amt());
			ro.setBankCode(yeePayResObj.getRb_BankId());
			ro.setFlowno(yeePayResObj.getR2_TrxId());
			ro.setPayRs(yeePayResObj.getR1_Code());
			ro.setUserId(r8_MP);
			ro.setPayKind("0");
			
			// 支付成功
			boolean isOK = PaymentForOnlineService.verifyCallback(formatString(hmac), formatString(p1_MerId), formatString(r0_Cmd), formatString(r1_Code), formatString(r2_TrxId), formatString(r3_Amt), formatString(r4_Cur), new String(formatString(r5_Pid).getBytes("iso-8859-1"), "gbk"), formatString(r6_Order), formatString(r7_Uid),
					new String(formatString(r8_MP).getBytes("iso-8859-1"), "gbk"), formatString(r9_BType), Configuration.getInstance().getValue("keyValue"));
			logger.info("验证参数有效性:"+isOK+" r1_code:"+yeePayResObj.getR1_Code());
			if (isOK && yeePayResObj.getR1_Code().equals("1")) {
				// if(r1_Code.equals("18")){
				// 资金账户记录
				if (r9_BType.equals("1")) {
					return "redirect:" + getHostUrl(req) + "/myAccount/myAccount/showMyAccount?strUrlType=fundDetail";
					// 浏览器重定向
				}
				if (r9_BType.equals("2")) {
					ro.setPayRs("1");
					payManager.recharge(ro);
					response.getWriter().write("success");
					// 点对点通讯
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			logger.info("结束易宝支付回调方法");
		}
		return null;
	}

	String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}

	/**
	 * 国付宝回调方法
	 * 
	 * @param model
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "callbackForGoPay")
	public String callbackForGoPay(Model model, HttpServletRequest req, HttpServletResponse response, @RequestParam("version") String version, @RequestParam("charset") String charset, @RequestParam("language") String language, @RequestParam("signType") String signType, @RequestParam("tranCode") String tranCode, @RequestParam("merchantID") String merchantID,
			@RequestParam("merOrderNum") String merOrderNum, @RequestParam("tranAmt") String tranAmt, @RequestParam("feeAmt") String feeAmt, @RequestParam("frontMerUrl") String frontMerUrl, @RequestParam("backgroundMerUrl") String backgroundMerUrl, @RequestParam("tranDateTime") String tranDateTime, @RequestParam("tranIP") String tranIP,
			@RequestParam("respCode") String respCode, @RequestParam("msgExt") String msgExt, @RequestParam("orderId") String orderId, @RequestParam("gopayOutOrderId") String gopayOutOrderId, @RequestParam("bankCode") String bankCode, @RequestParam("tranFinishTime") String tranFinishTime, @RequestParam("goodsName") String goodsName,
			@RequestParam("goodsDetail") String goodsDetail, @RequestParam("buyerName") String buyerName, @RequestParam("buyerContact") String buyerContact, @RequestParam("merRemark1") String merRemark1, @RequestParam("merRemark2") String merRemark2, @RequestParam("signValue") String signValue) throws IOException, NoSuchAlgorithmException {
		req.setCharacterEncoding("GBK");
		PayResObj ro = new PayResObj();
		ro.setOrderno(merOrderNum);
		ro.setAmount(tranAmt);
		ro.setBankCode(bankCode);
		ro.setFlowno(orderId);
		ro.setPayRs(respCode);
		ro.setUserId(merRemark1);
		ro.setPayKind("1");
		String plain = "version=[" + version + "]tranCode=[" + tranCode + "]merchantID=[" + merchantID + "]merOrderNum=[" + merOrderNum + "]tranAmt=[" + tranAmt + "]feeAmt=[" + feeAmt + "]tranDateTime=[" + tranDateTime + "]frontMerUrl=[" + frontMerUrl + "]backgroundMerUrl=[" + backgroundMerUrl + "]orderId=[" + orderId + "]gopayOutOrderId=[" + gopayOutOrderId + "]tranIP=["
				+ tranIP + "]respCode=[" + respCode + "]VerficationCode=[" + Configuration.getInstance().getValue("goPayVerficationCode") + "]";
		String md5 = PaymentForOnlineService.md5(plain.toString());
		if (md5.equals(signValue) && respCode.equals("0000")) {
			ro.setPayRs("1");
			payManager.recharge(ro);
			response.getWriter().write("RespCode=0000|JumpURL=" + getHostUrl(req) + "/myAccount/myAccount/showMyAccount?strUrlType=fundDetail");
		}
		return null;
	}

//	/**
//	 * 盛付通回调方法
//	 * 
//	 * @param model
//	 * @param req
//	 * @return
//	 * @throws IOException
//	 * @throws NoSuchAlgorithmException
//	 */
//	@RequestMapping(value = "callbackForShengPay")
//	public String callbackForShengPay(Model model, HttpServletRequest req, HttpServletResponse response, @RequestParam("Name") String name, @RequestParam("Version") String version, @RequestParam("Charset") String charset, @RequestParam("TraceNo") String traceNo, @RequestParam("MsgSender") String msgSender, @RequestParam("SendTime") String sendTime,
//			@RequestParam("InstCode") String instCode, @RequestParam("OrderNo") String orderNo, @RequestParam("OrderAmount") String orderAmount, @RequestParam("TransNo") String transNo, @RequestParam("TransAmount") String transAmount, @RequestParam("TransStatus") String transStatus, @RequestParam("TransType") String transType, @RequestParam("TransTime") String transTime,
//			@RequestParam("MerchantNo") String merchantNo, @RequestParam("ErrorCode") String errorCode, @RequestParam("ErrorMsg") String errorMsg, @RequestParam("Ext1") String ext1, @RequestParam("SignType") String signType, @RequestParam("SignMsg") String signMsg) throws IOException, NoSuchAlgorithmException {
//
//		PayResObj ro = new PayResObj();
//		ro.setOrderno(orderNo);
//		ro.setAmount(transAmount);
//		ro.setBankCode(instCode);
//		ro.setFlowno(transNo);
//		ro.setPayRs(transStatus);
//		ro.setUserId(ext1);
//		ro.setPayKind("3");
//		StringBuffer buf = new StringBuffer();
//		buf.append(name != null ? name : "");
//		buf.append(version != null ? version : "");
//		buf.append(charset != null ? charset : "");
//		buf.append(traceNo != null ? traceNo : "");
//		buf.append(msgSender != null ? msgSender : "");
//		buf.append(sendTime != null ? sendTime : "");
//		buf.append(instCode != null ? instCode : "");
//		buf.append(orderNo != null ? orderNo : "");
//		buf.append(orderAmount != null ? orderAmount : "");
//		buf.append(transNo != null ? transNo : "");
//		buf.append(transAmount != null ? transAmount : "");
//		buf.append(transStatus != null ? transStatus : "");
//		buf.append(transType != null ? transType : "");
//		buf.append(transTime != null ? transTime : "");
//		buf.append(merchantNo != null ? merchantNo : "");
//		buf.append(errorCode != null ? errorCode : "");
//		buf.append(errorMsg != null ? errorMsg : "");
//		buf.append(ext1 != null ? ext1 : "");
//		buf.append(signType != null ? signType : "");
//
//		String md5 = PaymentForOnlineService.md5(buf.toString() + Configuration.getInstance().getValue("shengPaykeyValue")).toUpperCase();
//		if (md5.equals(signMsg) && transStatus.equals("01")) {
//			ro.setPayRs("1");
//			payManager.recharge(ro);
//			response.getWriter().write("OK");
//		}
//
//		return null;
//	}

	/**
	 * 
	 * @param req
	 * @return
	 */
	private String getHostUrl(HttpServletRequest req) {
		String path = req.getContextPath();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String url = "http://" + serverName + ":" + serverPort + path;
		return url;
	}
}
