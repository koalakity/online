package com.zendaimoney.online.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserCreditNote entity. @author MyEclipse Persistence Tools
 */
@Entity(name="UserCreditNoteVO")
@Table(name = "USER_CREDIT_NOTE")
public class UserCreditNoteVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2222281077548836805L;
	private Long creditId;
	private Long userId;
	private Long infoCreditAuditStauts;
	private BigDecimal creditAmount;
	private Long creditGrade;
	private Long creditScoreSum;
	private Long overdueCount;
	private Long seriousOverdueCount;

	// Constructors

	/** default constructor */
	public UserCreditNoteVO() {
	}
	

	@SequenceGenerator(name = "generator",sequenceName="USERCREDITNOTE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "CREDIT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getCreditId() {
		return this.creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "INFO_CREDIT_AUDIT_STAUTS", precision = 22, scale = 0)
	public Long getInfoCreditAuditStauts() {
		return this.infoCreditAuditStauts;
	}

	public void setInfoCreditAuditStauts(Long infoCreditAuditStauts) {
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
	public Long getCreditGrade() {
		return this.creditGrade;
	}

	public void setCreditGrade(Long creditGrade) {
		this.creditGrade = creditGrade;
	}

	@Column(name = "CREDIT_SCORE_SUM", precision = 22, scale = 0)
	public Long getCreditScoreSum() {
		return this.creditScoreSum;
	}

	public void setCreditScoreSum(Long creditScoreSum) {
		this.creditScoreSum = creditScoreSum;
	}

	@Column(name = "OVERDUE_COUNT", precision = 22, scale = 0)
	public Long getOverdueCount() {
		return this.overdueCount;
	}

	public void setOverdueCount(Long overdueCount) {
		this.overdueCount = overdueCount;
	}

	@Column(name = "SERIOUS_OVERDUE_COUNT", precision = 22, scale = 0)
	public Long getSeriousOverdueCount() {
		return this.seriousOverdueCount;
	}

	public void setSeriousOverdueCount(Long seriousOverdueCount) {
		this.seriousOverdueCount = seriousOverdueCount;
	}

}