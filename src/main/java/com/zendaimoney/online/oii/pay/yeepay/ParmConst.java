package com.zendaimoney.online.oii.pay.yeepay;

public class ParmConst {
	// 请求代码
	public static final String p0_Cmd = "p0_Cmd";// 业务类型
	public static final String p1_MerId = "p1_MerId";// 商户编号
	public static final String p2_Order = "p2_Order";// 商户订单号
	public static final String p3_Amt = "p3_Amt";// 支付金额
	public static final String p4_Cur = "p4_Cur";// 交易币种
	public static final String p5_Pid = "p5_Pid";// 商品名称
	public static final String p6_Pcat = "p6_Pcat";// 商品种类
	public static final String p7_Pdesc = "p7_Pdesc";// 商品描述
	public static final String p8_Url = "p8_Url";// 商户接收支付成功数据的地址
	public static final String p9_SAF = "p9_SAF";// 送货地址
	public static final String pa_MP = "pa_MP";// 商户扩展信息
	public static final String pd_FrpId = "pd_FrpId";// 支付通道编码
	public static final String pr_NeedResponse = "pr_NeedResponse";// 应答机制
	public static final String hmac = "hmac";// 签名数据
	// 结果代码
	public static final String r0_Cmd = "r0_Cmd";// 业务类型
	public static final String r1_Code = "r1_Code";// 支付结果
	public static final String r2_TrxId = "r2_TrxId";// 易宝支付交易流水号
	public static final String r3_Amt = "r3_Amt";// 支付金额
	public static final String r4_Cur = "r4_Cur";// 交易币种
	public static final String r5_Pid = "r5_Pid";// 商品名称
	public static final String r6_Order = "r6_Order";// 商户订单号
	public static final String r7_Uid = "r7_Uid";// 易宝支付会员ID
	public static final String r8_MP = "r8_MP";// 商户扩展信息
	public static final String r9_BType = "r9_BType";// 交易结果返回类型
	public static final String rb_BankId = "rb_BankId";// 银行编码
	public static final String ro_BankOrderId = "ro_BankOrderId";// 银行订单号
	public static final String rp_PayDate = "rp_PayDate";// 支付成功时间
	public static final String rq_CardNo = "rq_CardNo";// 神州行充值卡序列号
	public static final String ru_Trxtime = "ru_Trxtime";// 交易结果通知时间

}
