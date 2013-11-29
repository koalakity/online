package com.zendaimoney.online.admin.vo;

import java.math.BigDecimal;

public class RateVO {
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
	private String note;
	private String rateName;
	private BigDecimal idFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}

	public BigDecimal getRecharge() {
		return recharge;
	}

	public void setRecharge(BigDecimal recharge) {
		this.recharge = recharge;
	}

	public BigDecimal getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public BigDecimal getOverdueSeriousInterest() {
		return overdueSeriousInterest;
	}

	public void setOverdueSeriousInterest(BigDecimal overdueSeriousInterest) {
		this.overdueSeriousInterest = overdueSeriousInterest;
	}

	public BigDecimal getOverdueFines() {
		return overdueFines;
	}

	public void setOverdueFines(BigDecimal overdueFines) {
		this.overdueFines = overdueFines;
	}

	public BigDecimal getEarlyFines() {
		return earlyFines;
	}

	public void setEarlyFines(BigDecimal earlyFines) {
		this.earlyFines = earlyFines;
	}

	public BigDecimal getLoan() {
		return loan;
	}

	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}

	public BigDecimal getReserveFoud() {
		return reserveFoud;
	}

	public void setReserveFoud(BigDecimal reserveFoud) {
		this.reserveFoud = reserveFoud;
	}

	public BigDecimal getMgmtFee() {
		return mgmtFee;
	}

	public void setMgmtFee(BigDecimal mgmtFee) {
		this.mgmtFee = mgmtFee;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public BigDecimal getIdFee() {
		return idFee;
	}

	public void setIdFee(BigDecimal idFee) {
		this.idFee = idFee;
	}

}
