package com.zendaimoney.online.entity.common;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LOAN_RATE")
public class LoanRateVO implements java.io.Serializable {
	private Long id;
	private BigDecimal overdueInterest;
	private BigDecimal overdueSeriousInterest;
	private BigDecimal overdueFines;
	private BigDecimal earlyFines;
	private BigDecimal loan;
	private BigDecimal reserveFoud;
	private BigDecimal mgmtFee;

	@SequenceGenerator(name = "generator", sequenceName = "LOANRATE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OVERDUE_INTEREST", precision = 22, scale = 0)
	public BigDecimal getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	@Column(name = "OVERDUE_SERIOUS_INTEREST", precision = 22, scale = 0)
	public BigDecimal getOverdueSeriousInterest() {
		return overdueSeriousInterest;
	}

	public void setOverdueSeriousInterest(BigDecimal overdueSeriousInterest) {
		this.overdueSeriousInterest = overdueSeriousInterest;
	}

	@Column(name = "OVERDUE_FINES", precision = 22, scale = 0)
	public BigDecimal getOverdueFines() {
		return overdueFines;
	}

	public void setOverdueFines(BigDecimal overdueFines) {
		this.overdueFines = overdueFines;
	}

	@Column(name = "EARLY_FINES", precision = 22, scale = 0)
	public BigDecimal getEarlyFines() {
		return earlyFines;
	}

	public void setEarlyFines(BigDecimal earlyFines) {
		this.earlyFines = earlyFines;
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

	@Column(name = "MGMT_FEE", precision = 22, scale = 0)
	public BigDecimal getMgmtFee() {
		return mgmtFee;
	}

	public void setMgmtFee(BigDecimal mgmtFee) {
		this.mgmtFee = mgmtFee;
	}

}
