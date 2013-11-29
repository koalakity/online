package com.zendaimoney.online.entity.borrowing;

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
public class BorrowingUserCreditNote implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5760421898021412233L;
	private BigDecimal creditId;
	private BorrowingUsers user;
	private BigDecimal infoCreditAuditStauts;
	private BigDecimal creditAmount;
	private BigDecimal creditScoreSum;
	private BigDecimal creditGrade;
	private BigDecimal overdueCount;
	private BigDecimal seriousOverdueCount;

	// Constructors

	/** default constructor */
	public BorrowingUserCreditNote() {
	}

	/** full constructor */
	public BorrowingUserCreditNote( BigDecimal infoCreditAuditStauts,
			BigDecimal creditAmount, BigDecimal creditGrade,
			BigDecimal overdueCount, BigDecimal seriousOverdueCount) {
		this.infoCreditAuditStauts = infoCreditAuditStauts;
		this.creditAmount = creditAmount;
		this.creditGrade = creditGrade;
		this.overdueCount = overdueCount;
		this.seriousOverdueCount = seriousOverdueCount;
	}

    // Property accessors
	@SequenceGenerator(name = "generator", sequenceName="USERCREDITNOTE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "CREDIT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCreditId() {
		return this.creditId;
	}

	public void setCreditId(BigDecimal creditId) {
		this.creditId = creditId;
	}

	@Column(name = "INFO_CREDIT_AUDIT_STAUTS", precision = 22, scale = 0)
	public BigDecimal getInfoCreditAuditStauts() {
		return this.infoCreditAuditStauts;
	}

   @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")       
    @OneToOne 
	public BorrowingUsers getUser() {
        return user;
    }

    public void setUser(BorrowingUsers user) {
        this.user = user;
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
	
	@Column(name = "CREDIT_SCORE_SUM", precision = 22, scale = 0)
    public BigDecimal getCreditScoreSum() {
        return creditScoreSum;
    }

    public void setCreditScoreSum(BigDecimal creditScoreSum) {
        this.creditScoreSum = creditScoreSum;
    }
	
	@Column(name = "CREDIT_GRADE", precision = 22, scale = 0)
	public BigDecimal getCreditGrade() {
		return this.creditGrade;
	}

	public void setCreditGrade(BigDecimal creditGrade) {
		this.creditGrade = creditGrade;
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