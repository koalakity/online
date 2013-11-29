package com.zendaimoney.online.entity.myInvestment;

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
public class MyInvestmentUserCreditNote implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1356102619795807863L;
	private BigDecimal creditId;
	private MyInvestmentUsers userId;
	private BigDecimal infoCreditAuditStauts;
	private BigDecimal creditAmount;
	private BigDecimal creditGrade;
	private BigDecimal creditScoreSum;
	private BigDecimal overdueCount;
	private BigDecimal seriousOverdueCount;

	// Constructors

	/** default constructor */
	public MyInvestmentUserCreditNote() {
	}

	/** full constructor */
	public MyInvestmentUserCreditNote(MyInvestmentUsers userId, BigDecimal infoCreditAuditStauts,
			BigDecimal creditAmount, BigDecimal creditGrade,
			BigDecimal creditScoreSum, BigDecimal overdueCount,
			BigDecimal seriousOverdueCount) {
		this.userId = userId;
		this.infoCreditAuditStauts = infoCreditAuditStauts;
		this.creditAmount = creditAmount;
		this.creditGrade = creditGrade;
		this.creditScoreSum = creditScoreSum;
		this.overdueCount = overdueCount;
		this.seriousOverdueCount = seriousOverdueCount;
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
	@JoinColumn(name="USER_ID",referencedColumnName="USER_ID")
	public MyInvestmentUsers getUserId() {
		return this.userId;
	}

	public void setUserId(MyInvestmentUsers userId) {
		this.userId = userId;
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