package com.zendaimoney.online.admin.entity.fundDetail;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.common.ZendaiAccountBank;

/**
 * AcTFlow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_FLOW")
public class AcTFlowAdmin implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4475515891838685105L;
	private Long id;
	private Date tradeDate;
	private String tradeNo;
	private String tradeCode;
	private Double tradeAmount;
	private String dorc;
	private String tradeType;
	private String reversedNo;
	private String tradeKind;
	private String voucherKind;
	private String voucherCode;
	private String teller;
	private String delegationTeller;
	private String organ;
	private String account;
	private String acctTitle;
	private String appoAcct;
	private String appoAcctTitle;
	private String memo;
	private String memo2;
	private String memo3;

	// Constructors

	/** default constructor */
	public AcTFlowAdmin() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "ACTFLOW_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TRADE_DATE", length = 7)
	public Date getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	@Column(name = "TRADE_NO", length = 20)
	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name = "TRADE_CODE", length = 8)
	public String getTradeCode() {
		return this.tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	@Column(name = "TRADE_AMOUNT", precision = 33, scale = 18)
	public Double getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	@Column(name = "DORC", length = 1)
	public String getDorc() {
		return this.dorc;
	}

	public void setDorc(String dorc) {
		this.dorc = dorc;
	}

	@Column(name = "TRADE_TYPE", nullable = false, length = 1)
	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "REVERSED_NO", length = 20)
	public String getReversedNo() {
		return this.reversedNo;
	}

	public void setReversedNo(String reversedNo) {
		this.reversedNo = reversedNo;
	}

	@Column(name = "TRADE_KIND", length = 1)
	public String getTradeKind() {
		return this.tradeKind;
	}

	public void setTradeKind(String tradeKind) {
		this.tradeKind = tradeKind;
	}

	@Column(name = "VOUCHER_KIND", length = 1)
	public String getVoucherKind() {
		return this.voucherKind;
	}

	public void setVoucherKind(String voucherKind) {
		this.voucherKind = voucherKind;
	}

	@Column(name = "VOUCHER_CODE", length = 30)
	public String getVoucherCode() {
		return this.voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	@Column(name = "TELLER", length = 20)
	public String getTeller() {
		return this.teller;
	}

	public void setTeller(String teller) {
		this.teller = teller;
	}

	@Column(name = "DELEGATION_TELLER", length = 20)
	public String getDelegationTeller() {
		return this.delegationTeller;
	}

	public void setDelegationTeller(String delegationTeller) {
		this.delegationTeller = delegationTeller;
	}

	@Column(name = "ORGAN", length = 20)
	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	@Column(name = "ACCOUNT", nullable = false, length = 30)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "ACCT_TITLE", nullable = false, length = 6)
	public String getAcctTitle() {
		return this.acctTitle;
	}

	public void setAcctTitle(String acctTitle) {
		this.acctTitle = acctTitle;
	}

	@Column(name = "APPO_ACCT", length = 30)
	public String getAppoAcct() {
		return this.appoAcct;
	}

	public void setAppoAcct(String appoAcct) {
		this.appoAcct = appoAcct;
	}

	@Column(name = "APPO_ACCT_TITLE", length = 6)
	public String getAppoAcctTitle() {
		return this.appoAcctTitle;
	}

	public void setAppoAcctTitle(String appoAcctTitle) {
		this.appoAcctTitle = appoAcctTitle;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "MEMO2", length = 150)
	public String getMemo2() {
		return this.memo2;
	}

	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	@Column(name = "MEMO3", length = 150)
	public String getMemo3() {
		return this.memo3;
	}

	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}

	@Transient
	public String getTypeStr() {
		// 月缴管理费+借款手续费+充值手续费+提现手续费+调账
		if (null != account
				&& (account.equals(ZendaiAccountBank.zendai_acct1) || account.equals(ZendaiAccountBank.zendai_acct2) || account.equals(ZendaiAccountBank.zendai_acct3) || account.equals(ZendaiAccountBank.zendai_acct6) || account.equals(ZendaiAccountBank.zendai_acct7) || account.equals(ZendaiAccountBank.zendai_acct9) || account.equals(ZendaiAccountBank.zendai_acct10))
				&& "010103".equals(acctTitle) && "010104".equals(appoAcctTitle)) {
			return "调账";
		}
		if (null != appoAcct && appoAcct.equals(ZendaiAccountBank.zendai_acct7)) {
			return "提现手续费";
		}
		if (null != appoAcct && appoAcct.equals(ZendaiAccountBank.zendai_acct6)) {
			return "充值手续费";
		}
		if (null != appoAcct && appoAcct.equals(ZendaiAccountBank.zendai_acct9)) {
			return "借款手续费";
		}
		if (null != appoAcct && appoAcct.equals(ZendaiAccountBank.zendai_acct1)) {
			return "月缴管理费";
		}
		return "其他";
	}

	@Transient
	public String getRiskTypeStr() {
		// 月缴管理费+借款手续费+充值手续费+提现手续费+调账
		if (null != account
				&& (account.equals(ZendaiAccountBank.zendai_acct1) || account.equals(ZendaiAccountBank.zendai_acct2) || account.equals(ZendaiAccountBank.zendai_acct3) || account.equals(ZendaiAccountBank.zendai_acct6) || account.equals(ZendaiAccountBank.zendai_acct7) || account.equals(ZendaiAccountBank.zendai_acct9) || account.equals(ZendaiAccountBank.zendai_acct10))
				&& "010103".equals(acctTitle) && "010104".equals(appoAcctTitle)) {
			return "调账";
		}
		if (null != appoAcct && appoAcct.equals(ZendaiAccountBank.zendai_acct10)) {
			return "风险准备金";
		}
		if (null != appoAcct && appoAcct.equals(ZendaiAccountBank.zendai_acct2)) {
			return "逾期罚息";
		}
		if (null != appoAcct && appoAcct.equals(ZendaiAccountBank.zendai_acct3)) {
			return "逾期违约金";
		}
		return "其他";
	}

	@Transient
	public Double getIncoming() {
		if ((account.equals(ZendaiAccountBank.zendai_acct1) || account.equals(ZendaiAccountBank.zendai_acct2) || account.equals(ZendaiAccountBank.zendai_acct3) || account.equals(ZendaiAccountBank.zendai_acct6) || account.equals(ZendaiAccountBank.zendai_acct7) || account.equals(ZendaiAccountBank.zendai_acct9) || account.equals(ZendaiAccountBank.zendai_acct10)|| account.equals(ZendaiAccountBank.zendai_acct11))) {
			return null;
		} else if (acctTitle.equals("010201") || acctTitle.equals("010202")) {
			return null;
		}
		return ArithUtil.DoubleAccurate(tradeAmount, 10);
	}

	@Transient
	public Double getOutput() {
		if ((account.equals(ZendaiAccountBank.zendai_acct1) || account.equals(ZendaiAccountBank.zendai_acct2) || account.equals(ZendaiAccountBank.zendai_acct3) || account.equals(ZendaiAccountBank.zendai_acct6) || account.equals(ZendaiAccountBank.zendai_acct7) || account.equals(ZendaiAccountBank.zendai_acct9) || account.equals(ZendaiAccountBank.zendai_acct10)|| account.equals(ZendaiAccountBank.zendai_acct11))) {
			return ArithUtil.DoubleAccurate(tradeAmount, 10);
		}
		if (acctTitle.equals("010201") || acctTitle.equals("010202")) {
			return null;
		}
		return null;
	}

	@Transient
	public String getIncomingStr() {
		if (getIncoming() == null) {
			return null;
		}
		return ObjectFormatUtil.formatCurrency(getIncoming());
	}

	@Transient
	public String getOutputStr() {
		if (getOutput() == null) {
			return null;
		}
		return ObjectFormatUtil.formatCurrency(getOutput());
	}

	private Double amount;
	private Double payAmount;
	private Double storeAmount;
	private String type;

	// 交易类型
	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// 结余
	@Transient
	public Double getAmount() {

		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	// 支出
	@Transient
	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	// 收入
	@Transient
	public Double getStoreAmount() {
		return storeAmount;
	}

	public void setStoreAmount(Double storeAmount) {
		this.storeAmount = storeAmount;
	}

	@Transient
	public String getTradeNoAndMemo() {
		if (null == memo2) {
			return tradeNo + " ";
		}
		return tradeNo + " " + memo2;
	}
}