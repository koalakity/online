package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;

public class FundDetailVOAdmin {
	
	public String getZhye() {
		return zhye;
	}
	public void setZhye(String zhye) {
		this.zhye = zhye;
	}
	public String getKyye() {
		return kyye;
	}
	public void setKyye(String kyye) {
		this.kyye = kyye;
	}
	public String getKyed() {
		return kyed;
	}
	public void setKyed(String kyed) {
		this.kyed = kyed;
	}
	public String getDjzy() {
		return djzy;
	}
	public void setDjzy(String djzy) {
		this.djzy = djzy;
	}
	public String getLcdjzy() {
		return lcdjzy;
	}
	public void setLcdjzy(String lcdjzy) {
		this.lcdjzy = lcdjzy;
	}
	public String getTxdjzy() {
		return txdjzy;
	}
	public void setTxdjzy(String txdjzy) {
		this.txdjzy = txdjzy;
	}
	public String getCgczze() {
		return cgczze;
	}
	public void setCgczze(String cgczze) {
		this.cgczze = cgczze;
	}
	public String getCgtxze() {
		return cgtxze;
	}
	public void setCgtxze(String cgtxze) {
		this.cgtxze = cgtxze;
	}
	public String getZjcje() {
		return zjcje;
	}
	public void setZjcje(String zjcje) {
		this.zjcje = zjcje;
	}
	public String getYshbx() {
		return yshbx;
	}
	public void setYshbx(String yshbx) {
		this.yshbx = yshbx;
	}
	public String getDshbx() {
		return dshbx;
	}
	public void setDshbx(String dshbx) {
		this.dshbx = dshbx;
	}
	public String getYhbx() {
		return yhbx;
	}
	public void setYhbx(String yhbx) {
		this.yhbx = yhbx;
	}
	public String getDhbx() {
		return dhbx;
	}
	public void setDhbx(String dhbx) {
		this.dhbx = dhbx;
	}
	private String zhye;//账户余额
	private String kyye;//可用余额
	private String kyed;//可用额度
	private String djzy;//冻结总额
	private String lcdjzy;//理财冻结总额
	private String txdjzy;//提现冻结总额
	private String cgczze;//成功充值总额
	private String cgtxze;//成功提现总额
	private String zjcje;//总借出金额
	private String yshbx;//已收回本息
	private String dshbx;//待收回本息
	private String zjkje;//总借款金额
	private String yhbx;//已还本息
	private String dhbx;//待还本息
	private String xyed;//信用额度
	private String loanTotle;//借款总额
	private String successLoanNum;//成功借款笔数
	private String successLoanOutNum;//成功借出笔数
	private String overdueCount;//逾期次数
	private String usedAmount;//已用信用额度
	private BigDecimal userId;
	private String loginName;
	private String realName;
	
	public String getSuccessLoanOutNum() {
		return successLoanOutNum;
	}
	public void setSuccessLoanOutNum(String successLoanOutNum) {
		this.successLoanOutNum = successLoanOutNum;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public String getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(String usedAmount) {
		this.usedAmount = usedAmount;
	}
	public String getOverdueCount() {
		return overdueCount;
	}
	public void setOverdueCount(String overdueCount) {
		this.overdueCount = overdueCount;
	}
	public String getSuccessLoanNum() {
		return successLoanNum;
	}
	public void setSuccessLoanNum(String successLoanNum) {
		this.successLoanNum = successLoanNum;
	}
	public String getLoanTotle() {
		return loanTotle;
	}
	public void setLoanTotle(String loanTotle) {
		this.loanTotle = loanTotle;
	}
	public String getXyed() {
		return xyed;
	}
	public void setXyed(String xyed) {
		this.xyed = xyed;
	}
	public String getZjkje() {
		return zjkje;
	}
	public void setZjkje(String zjkje) {
		this.zjkje = zjkje;
	}

}

