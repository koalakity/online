/**
 * 
 */
package com.zendaimoney.online.admin.entity.extract;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.fundDetail.AcTLedgerAdmin;
import com.zendaimoney.online.common.ObjectFormatUtil;

/**
 * ExtractNote entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "EXTRACT_NOTE")
public class ExtractNoteAdmin implements java.io.Serializable {

	// Fields

	private BigDecimal extractId;
	// private BigDecimal userId;
	private AccountUsersAdmin accountUser;
	private Double extractAmount;
	private Double extractCost;
	private Double realAmount;
	private String kaihuName;
	private String bankName;
	private String bankCardNo;
	private Date extractTime;
	private BigDecimal sysStatus;
	private BigDecimal verifyStatus;
	private String description;

	// Constructors

	/** default constructor */
	public ExtractNoteAdmin() {
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "EXTRACT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getExtractId() {
		return this.extractId;
	}

	public void setExtractId(BigDecimal extractId) {
		this.extractId = extractId;
	}

	@Column(name = "EXTRACT_AMOUNT", nullable = false, precision = 126, scale = 0)
	public Double getExtractAmount() {
		return this.extractAmount;
	}

	public void setExtractAmount(Double extractAmount) {
		this.extractAmount = extractAmount;
	}

	@Column(name = "EXTRACT_COST", nullable = false, precision = 126, scale = 0)
	public Double getExtractCost() {
		return this.extractCost;
	}

	public void setExtractCost(Double extractCost) {
		this.extractCost = extractCost;
	}

	@Column(name = "REAL_AMOUNT", nullable = false, precision = 126, scale = 0)
	public Double getRealAmount() {
		return this.realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	@Column(name = "KAIHU_NAME", nullable = false, length = 50)
	public String getKaihuName() {
		return this.kaihuName;
	}

	public void setKaihuName(String kaihuName) {
		this.kaihuName = kaihuName;
	}

	@Column(name = "BANK_NAME", nullable = false, length = 100)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_CARD_NO", nullable = false, length = 40)
	public String getBankCardNo() {
		return this.bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	@Column(name = "EXTRACT_TIME", nullable = false)
	public Date getExtractTime() {
		return this.extractTime;
	}

	public void setExtractTime(Date extractTime) {
		this.extractTime = extractTime;
	}

	@Column(name = "SYS_STATUS", precision = 22, scale = 0)
	public BigDecimal getSysStatus() {
		return this.sysStatus;
	}

	public void setSysStatus(BigDecimal sysStatus) {
		this.sysStatus = sysStatus;
	}

	@Column(name = "VERIFY_STATUS", precision = 22, scale = 0)
	public BigDecimal getVerifyStatus() {
		return this.verifyStatus;
	}

	public void setVerifyStatus(BigDecimal verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public AccountUsersAdmin getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(AccountUsersAdmin accountUser) {
		this.accountUser = accountUser;
	}

	@Transient
	public String getRealName() {
		if (null == accountUser.getUserInfoPerson()) {
			// System.out.println("userInfoPerson对象为空，取不到用户名称，请完善数据库，users-userInfoPerson是一对一的关系");
			return null;
		}
		return accountUser.getUserInfoPerson().getRealName();
	}

	@Transient
	public BigDecimal getUserId() {
		return accountUser.getUserId();
	}

	@Transient
	public String getExtractTimeFormatt() {
		return extractTime.toString().substring(0, 19);
	}

	@Transient
	public String getExtractAmountFormatt() {
		return ObjectFormatUtil.formatCurrency(this.extractAmount);
	}

	@Transient
	public String getExtractCostFormatt() {
		return ObjectFormatUtil.formatCurrency(this.extractCost);
	}

	// 查看该用户提现的可用额度 add by jihui 2012-01-10
	@Transient
	public String getAvailabelMoneyFomatt() {
		double amount = 0.0;
		Set<AcTLedgerAdmin> actLedgerSet = this.accountUser.getFinancialAcTCustomerAdmin().getAcTLedgerAdmins();
		for (AcTLedgerAdmin actLedger : actLedgerSet) {
			if (actLedger.getBusiType().equals("4")) {
				amount = actLedger.getAmount() + this.getExtractAmount() + this.getExtractCost();
			}
		}
		return ObjectFormatUtil.formatMoney(amount);
	}
}