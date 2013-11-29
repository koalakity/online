package com.zendaimoney.online.entity.financial;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserApprove entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_APPROVE")
public class FinanciaUserApprove implements java.io.Serializable {

	private static final long serialVersionUID = -3362546423909595306L;

	private BigDecimal userApproveId;
	private BigDecimal userId;
	private BigDecimal proId;
	private BigDecimal proStatus;
	private String auditTime;
	private BigDecimal creditScore;
	private BigDecimal auditerId;
	private BigDecimal reviewStatus;
	private String approveImgPath;
	private Date createTime;
	private Date updateTime;

	/** default constructor */
	public FinanciaUserApprove() {
	}

	@SequenceGenerator(name = "generator", sequenceName = "USERAPPROVE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "USER_APPROVE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserApproveId() {
		return this.userApproveId;
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

	@Column(name = "PRO_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getProId() {
		return this.proId;
	}

	public void setProId(BigDecimal proId) {
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

	@Column(name = "APPROVE_IMG_PATH", length = 200)
	public String getApproveImgPath() {
		return this.approveImgPath;
	}

	public void setApproveImgPath(String approveImgPath) {
		this.approveImgPath = approveImgPath;
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
}