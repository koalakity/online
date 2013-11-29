package com.zendaimoney.online.entity.account;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserCreditNote entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_CREDIT_NOTE")
public class UserCreditNote implements java.io.Serializable {

	// Fields

	private BigDecimal creditId;
	private AccountUsers user;
	private BigDecimal infoCreditAuditStauts;
	private BigDecimal creditAmount;
	private BigDecimal creditGrade;
	private BigDecimal creditScoreSum;
	private BigDecimal overdueCount;
	private BigDecimal seriousOverdueCount;

	// Constructors

	/** default constructor */
	public UserCreditNote() {
	}

	 

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "CREDIT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCreditId() {
		return this.creditId;
	}

	public void setCreditId(BigDecimal creditId) {
		this.creditId = creditId;
	}

	@OneToOne
	@JoinColumn(name = "USER_ID")
	public AccountUsers getUser() {
		return user;
	}
	
	
	
	public void setUser(AccountUsers user) {
		this.user = user;
	}
	

	@Column(name = "INFO_CREDIT_AUDIT_STAUTS", precision = 22, scale = 0)
	public BigDecimal getInfoCreditAuditStauts() {
		return this.infoCreditAuditStauts;
	}




	public void setInfoCreditAuditStauts(BigDecimal infoCreditAuditStauts) {
		this.infoCreditAuditStauts = infoCreditAuditStauts;
	}

	@Column(name = "CREDIT_AMOUNT", precision = 22, scale = 0)
	public BigDecimal getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(name = "CREDIT_GRADE", precision = 22, scale = 0)
	public BigDecimal getCreditGrade() {
		return this.creditGrade;
	}

	public void setCreditGrade(BigDecimal creditGrade) {
		this.creditGrade = creditGrade;
	}

	@Column(name = "CREDIT_SCORE_SUM", precision = 22, scale = 0)
	public BigDecimal getCreditScoreSum() {
		return this.creditScoreSum;
	}

	public void setCreditScoreSum(BigDecimal creditScoreSum) {
		this.creditScoreSum = creditScoreSum;
	}

	@Column(name = "OVERDUE_COUNT", precision = 22, scale = 0)
	public BigDecimal getOverdueCount() {
		return this.overdueCount;
	}

	public void setOverdueCount(BigDecimal overdueCount) {
		this.overdueCount = overdueCount;
	}

	@Column(name = "SERIOUS_OVERDUE_COUNT", precision = 22, scale = 0)
	public BigDecimal getSeriousOverdueCount() {
		return this.seriousOverdueCount;
	}

	public void setSeriousOverdueCount(BigDecimal seriousOverdueCount) {
		this.seriousOverdueCount = seriousOverdueCount;
	}

}