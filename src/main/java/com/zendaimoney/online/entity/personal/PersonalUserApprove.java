package com.zendaimoney.online.entity.personal;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 * UserApprove entity. @author MyEclipse Persistence Tools
 * 
 * @Entity
 * @Table(name = "USER_APPROVE")
 */
public class PersonalUserApprove implements java.io.Serializable {

	// Fields

	private BigDecimal userApproveId;
	private PersonalUsers user;
	private BigDecimal proId;
	private BigDecimal proStatus;
	private String auditTime;
	private BigDecimal creditScore;
	private BigDecimal auditerId;
	private BigDecimal reviewStatus;
	private String approveImgPath;
	private Date createTime;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public PersonalUserApprove() {
	}

	/** minimal constructor */
	public PersonalUserApprove(PersonalUsers user, BigDecimal proId, BigDecimal proStatus) {
		this.user = user;
		this.proId = proId;
		this.proStatus = proStatus;
	}

	/** full constructor */
	public PersonalUserApprove(PersonalUsers user, BigDecimal proId, BigDecimal proStatus, String auditTime, BigDecimal creditScore, BigDecimal auditerId, BigDecimal reviewStatus, String approveImgPath, Date createTime, Date updateTime) {
		this.user = user;
		this.proId = proId;
		this.proStatus = proStatus;
		this.auditTime = auditTime;
		this.creditScore = creditScore;
		this.auditerId = auditerId;
		this.reviewStatus = reviewStatus;
		this.approveImgPath = approveImgPath;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
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

	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@OneToOne
	public PersonalUsers getUser() {
		return user;
	}

	public void setUser(PersonalUsers user) {
		this.user = user;
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