package com.zendaimoney.online.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserApprove entity. @author MyEclipse Persistence Tools
 */
@Entity(name="UserApproveVO")
@Table(name = "USER_APPROVE")
public class UserApproveVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943457419790764463L;
	// Fields

	private Long userApproveId;
	private Long userId;
	private Long proId;
	private BigDecimal proStatus;
	private String auditTime;
	private BigDecimal creditScore;
	private Long auditerId;
	private BigDecimal reviewStatus;
	private String approveImgPath;


	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="USERAPPROVE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "USER_APPROVE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getUserApproveId() {
		return this.userApproveId;
	}

	public void setUserApproveId(Long userApproveId) {
		this.userApproveId = userApproveId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "PRO_ID", nullable = false, precision = 22, scale = 0)
	public Long getProId() {
		return this.proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	@Column(name = "PRO_STATUS", nullable = false, precision = 22, scale = 0)
	public BigDecimal getProStatus() {
		return this.proStatus;
	}

	public void setProStatus(BigDecimal proStatus) {
		this.proStatus = proStatus;
	}

	@Column(name = "AUDIT_TIME")
	public String getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "CREDIT_SCORE", precision = 22, scale = 0)
	public BigDecimal getCreditScore() {
		return this.creditScore;
	}

	public void setCreditScore(BigDecimal creditScore) {
		this.creditScore = creditScore;
	}

	@Column(name = "AUDITER_ID", precision = 22, scale = 0)
	public Long getAuditerId() {
		return this.auditerId;
	}

	public void setAuditerId(Long auditerId) {
		this.auditerId = auditerId;
	}

	@Column(name = "REVIEW_STATUS", precision = 22, scale = 0)
	public BigDecimal getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(BigDecimal reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "APPROVE_IMG_PATH", length = 200)
	public String getApproveImgPath() {
		return this.approveImgPath;
	}

	public void setApproveImgPath(String approveImgPath) {
		this.approveImgPath = approveImgPath;
	}

}