package com.zendaimoney.online.entity.borrowing;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * UserApproveId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_APPROVE")
public class BorrowingUserApprove implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 647690744076313941L;
	// Fields
	private BigDecimal userApproveId;
	private BigDecimal userId;
	private BorrowingUserApprovePro proId;
	private BigDecimal proStatus;
	private Date auditTime;
	private String auditTimeStr;
	private BigDecimal creditScore;
	private BigDecimal auditerId;
	private BigDecimal reviewStatus;
	private Date createTime;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public BorrowingUserApprove() {
	}

	/** minimal constructor */
	public BorrowingUserApprove(BigDecimal userApproveId, BigDecimal userId, BorrowingUserApprovePro proId, BigDecimal proStatus, Date auditTime, BigDecimal creditScore, BigDecimal auditerId) {
		this.userApproveId = userApproveId;
		this.userId = userId;
		this.proId = proId;
		this.proStatus = proStatus;
		this.auditTime = auditTime;
		this.creditScore = creditScore;
		this.auditerId = auditerId;
	}

	/** full constructor */
	public BorrowingUserApprove(BigDecimal userApproveId, BigDecimal userId, BorrowingUserApprovePro proId, BigDecimal proStatus, Date auditTime, BigDecimal creditScore, BigDecimal auditerId, BigDecimal reviewStatus, Date createTime, Date updateTime) {
		this.userApproveId = userApproveId;
		this.userId = userId;
		this.proId = proId;
		this.proStatus = proStatus;
		this.auditTime = auditTime;
		this.creditScore = creditScore;
		this.auditerId = auditerId;
		this.reviewStatus = reviewStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "USERAPPROVE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "USER_APPROVE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserApproveId() {
		return userApproveId;
	}

	public void setUserApproveId(BigDecimal userApproveId) {
		this.userApproveId = userApproveId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@OneToOne
	@JoinColumn(name = "PRO_ID")
	public BorrowingUserApprovePro getProId() {
		return proId;
	}

	public void setProId(BorrowingUserApprovePro proId) {
		this.proId = proId;
	}

	@Column(name = "PRO_STATUS", nullable = false, precision = 22, scale = 0)
	public BigDecimal getProStatus() {
		return this.proStatus;
	}

	public void setProStatus(BigDecimal proStatus) {
		this.proStatus = proStatus;
	}

	@Column(name = "AUDIT_TIME", nullable = false, length = 7)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "CREDIT_SCORE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getCreditScore() {
		return this.creditScore;
	}

	public void setCreditScore(BigDecimal creditScore) {
		this.creditScore = creditScore;
	}

	@Column(name = "AUDITER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getAuditerId() {
		return this.auditerId;
	}

	public void setAuditerId(BigDecimal auditerId) {
		this.auditerId = auditerId;
	}

	@Column(name = "REVIEW_STATUS", precision = 22, scale = 0)
	public BigDecimal getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(BigDecimal reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", nullable = false, length = 7)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Transient
	public String getAuditTimeStr() {
		if (auditTime != null) {
			auditTimeStr = auditTime.toString().substring(0, 19);
			return auditTimeStr;
		} else {
			return "";
		}
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BorrowingUserApprove))
			return false;
		BorrowingUserApprove castOther = (BorrowingUserApprove) other;

		return ((this.getUserId() == castOther.getUserId()) || (this.getUserId() != null && castOther.getUserId() != null && this.getUserId().equals(castOther.getUserId()))) && ((this.getProId() == castOther.getProId()) || (this.getProId() != null && castOther.getProId() != null && this.getProId().equals(castOther.getProId())))
				&& ((this.getProStatus() == castOther.getProStatus()) || (this.getProStatus() != null && castOther.getProStatus() != null && this.getProStatus().equals(castOther.getProStatus()))) && ((this.getAuditTime() == castOther.getAuditTime()) || (this.getAuditTime() != null && castOther.getAuditTime() != null && this.getAuditTime().equals(castOther.getAuditTime())))
				&& ((this.getCreditScore() == castOther.getCreditScore()) || (this.getCreditScore() != null && castOther.getCreditScore() != null && this.getCreditScore().equals(castOther.getCreditScore())))
				&& ((this.getAuditerId() == castOther.getAuditerId()) || (this.getAuditerId() != null && castOther.getAuditerId() != null && this.getAuditerId().equals(castOther.getAuditerId())))
				&& ((this.getReviewStatus() == castOther.getReviewStatus()) || (this.getReviewStatus() != null && castOther.getReviewStatus() != null && this.getReviewStatus().equals(castOther.getReviewStatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result + (getProId() == null ? 0 : this.getProId().hashCode());
		result = 37 * result + (getProStatus() == null ? 0 : this.getProStatus().hashCode());
		result = 37 * result + (getAuditTime() == null ? 0 : this.getAuditTime().hashCode());
		result = 37 * result + (getCreditScore() == null ? 0 : this.getCreditScore().hashCode());
		result = 37 * result + (getAuditerId() == null ? 0 : this.getAuditerId().hashCode());
		result = 37 * result + (getReviewStatus() == null ? 0 : this.getReviewStatus().hashCode());
		return result;
	}

}