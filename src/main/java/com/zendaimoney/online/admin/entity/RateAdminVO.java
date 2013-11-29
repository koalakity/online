package com.zendaimoney.online.admin.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Rate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RATE")
public class RateAdminVO implements java.io.Serializable {

	// Fields

	private Long id;
	private BigDecimal withdraw;
	private BigDecimal recharge;
	private BigDecimal overdueInterest;
	private BigDecimal overdueSeriousInterest;
	private BigDecimal overdueFines;
	private BigDecimal earlyFines;
	private BigDecimal loan;
	private BigDecimal reserveFoud;
	private BigDecimal mgmtFee;
	private Date opDate;
	private String note;
	private String rateName;
	private BigDecimal idFee;
	private Date deDate;
	private BigDecimal isDel;
	private Long opUserId;

	// Constructors

	/** default constructor */
	public RateAdminVO() {
	}

	/** full constructor */
	public RateAdminVO(BigDecimal withdraw, BigDecimal recharge, BigDecimal overdueInterest, BigDecimal overdueFines, BigDecimal earlyFines, BigDecimal mgmtFee, Date opDate, String note) {
		this.withdraw = withdraw;
		this.recharge = recharge;
		this.overdueInterest = overdueInterest;
		this.overdueFines = overdueFines;
		this.earlyFines = earlyFines;
		this.mgmtFee = mgmtFee;
		this.opDate = opDate;
		this.note = note;
	}

	@Column(name = "OVERDUE_SERIOUS_INTEREST", precision = 22, scale = 0)
	public BigDecimal getOverdueSeriousInterest() {
		return overdueSeriousInterest;
	}

	public void setOverdueSeriousInterest(BigDecimal overdueSeriousInterest) {
		this.overdueSeriousInterest = overdueSeriousInterest;
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "RATE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "WITHDRAW", precision = 22, scale = 0)
	public BigDecimal getWithdraw() {
		return this.withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}

	@Column(name = "RECHARGE", precision = 22, scale = 0)
	public BigDecimal getRecharge() {
		return this.recharge;
	}

	public void setRecharge(BigDecimal recharge) {
		this.recharge = recharge;
	}

	@Column(name = "OVERDUE_INTEREST", precision = 22, scale = 0)
	public BigDecimal getOverdueInterest() {
		return this.overdueInterest;
	}

	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	@Column(name = "OVERDUE_FINES", precision = 22, scale = 0)
	public BigDecimal getOverdueFines() {
		return this.overdueFines;
	}

	public void setOverdueFines(BigDecimal overdueFines) {
		this.overdueFines = overdueFines;
	}

	@Column(name = "EARLY_FINES", precision = 22, scale = 0)
	public BigDecimal getEarlyFines() {
		return this.earlyFines;
	}

	public void setEarlyFines(BigDecimal earlyFines) {
		this.earlyFines = earlyFines;
	}

	@Column(name = "MGMT_FEE", precision = 22, scale = 0)
	public BigDecimal getMgmtFee() {
		return this.mgmtFee;
	}

	public void setMgmtFee(BigDecimal mgmtFee) {
		this.mgmtFee = mgmtFee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OP_DATE", length = 7)
	public Date getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	@Column(name = "NOTE", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "LOAN")
	public BigDecimal getLoan() {
		return loan;
	}

	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}

	@Column(name = "RESERVE_FUND")
	public BigDecimal getReserveFoud() {
		return reserveFoud;
	}

	public void setReserveFoud(BigDecimal reserveFoud) {
		this.reserveFoud = reserveFoud;
	}

	@Column(name = "RATE_NAME")
	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	@Column(name = "ID_FEE")
	public BigDecimal getIdFee() {
		return idFee;
	}

	public void setIdFee(BigDecimal idFee) {
		this.idFee = idFee;
	}

	@Column(name = "DE_DATE")
	public Date getDeDate() {
		return deDate;
	}

	public void setDeDate(Date deDate) {
		this.deDate = deDate;
	}

	@Column(name = "IS_DELETE")
	public BigDecimal getIsDel() {
		return isDel;
	}

	public void setIsDel(BigDecimal isDel) {
		this.isDel = isDel;
	}

	@Column(name = "OP_USER_ID")
	public Long getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Long opUserId) {
		this.opUserId = opUserId;
	}

}
