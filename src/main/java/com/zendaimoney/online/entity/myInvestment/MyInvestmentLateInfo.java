package com.zendaimoney.online.entity.myInvestment;

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
 * LateInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LATE_INFO")
public class MyInvestmentLateInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6546431718754424161L;
	private BigDecimal lateId;
	private BigDecimal loanId;
	private BigDecimal amount;
	private BigDecimal currNum;
	private String kind;
	private Date createTime;

	public MyInvestmentLateInfo() {
	}

	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LATE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLateId() {
		return this.lateId;
	}

	public void setLateId(BigDecimal lateId) {
		this.lateId = lateId;
	}

	@Column(name = "LOAN_ID")
	public BigDecimal getLoanId() {
		return this.loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@Column(name = "AMOUNT", precision = 22, scale = 0)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "CURR_NUM", precision = 22, scale = 0)
	public BigDecimal getCurrNum() {
		return this.currNum;
	}

	public void setCurrNum(BigDecimal currNum) {
		this.currNum = currNum;
	}

	@Column(name = "KIND", length = 2)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}