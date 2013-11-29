package com.zendaimoney.online.admin.entity.account;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_APPROVE")
public class AccountUserApproveAdmin implements java.io.Serializable {
	private static final long serialVersionUID = 382682890602989713L;
	private BigDecimal userApproveId;
	private AccountUsersAdmin user;
	private BigDecimal proId;
	private BigDecimal proStatus;
	private Date auditTime;
	private BigDecimal creditScore;
	private BigDecimal auditerId;
	private BigDecimal reviewStatus;
	private String approveImgPath;
	private Date createTime;
	private Date updateTime;
	private BigDecimal tempCreditScore;
	private BigDecimal tempReviewStatus;

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

	@Column(name = "APPROVE_IMG_PATH", length = 200)
	public String getApproveImgPath() {
		return approveImgPath;
	}

	public void setApproveImgPath(String approveImgPath) {
		this.approveImgPath = approveImgPath;
	}

	@Column(name = "PRO_ID")
	public BigDecimal getProId() {
		return proId;
	}

	public void setProId(BigDecimal proId) {
		this.proId = proId;
	}

	@Column(name = "PRO_STATUS")
	public BigDecimal getProStatus() {
		return proStatus;
	}

	public void setProStatus(BigDecimal proStatus) {
		this.proStatus = proStatus;
	}

	@Column(name = "AUDIT_TIME")
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "CREDIT_SCORE")
	public BigDecimal getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(BigDecimal creditScore) {
		this.creditScore = creditScore;
	}

	@Column(name = "AUDITER_ID")
	public BigDecimal getAuditerId() {
		return auditerId;
	}

	public void setAuditerId(BigDecimal auditerId) {
		this.auditerId = auditerId;
	}

	@Column(name = "REVIEW_STATUS")
	public BigDecimal getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(BigDecimal reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@JoinColumn(name = "USER_ID")
	@ManyToOne
	public AccountUsersAdmin getUser() {
		return user;
	}

	public void setUser(AccountUsersAdmin user) {
		this.user = user;
	}

	private String cardPath;

	public void setCardPath(String cardPath) {
		this.cardPath = cardPath;
	}

	@Transient
	public String getCardPath() {
		return cardPath;
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

	@Column(name = "TEMP_CREDIT_SCORE")
	public BigDecimal getTempCreditScore() {
		return tempCreditScore;
	}

	public void setTempCreditScore(BigDecimal tempCreditScore) {
		this.tempCreditScore = tempCreditScore;
	}

	@Column(name = "TEMP_REVIEW_STATUS")
	public BigDecimal getTempReviewStatus() {
		return tempReviewStatus;
	}

	public void setTempReviewStatus(BigDecimal tempReviewStatus) {
		this.tempReviewStatus = tempReviewStatus;
	}

}
