package com.zendaimoney.online.admin.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "STATIC_INFO")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class StaticInfo implements java.io.Serializable {
	private BigDecimal id;
	private BigDecimal loanId;
	private BigDecimal status;
	private Double loanAmount;
	private BigDecimal ledgerLoanId;
	private BigDecimal currNum;
	private Date updateDate;
	private Date interestStart;

	/** default constructor */
	public StaticInfo() {
	}

	@SequenceGenerator(name = "generator", sequenceName = "STATIC_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getLoanId() {
		return loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	@Column(name = "LOAN_AMOUNT", nullable = false, precision = 22, scale = 7)
	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getLedgerLoanId() {
		return ledgerLoanId;
	}

	public void setLedgerLoanId(BigDecimal ledgerLoanId) {
		this.ledgerLoanId = ledgerLoanId;
	}

	public BigDecimal getCurrNum() {
		return currNum;
	}

	public void setCurrNum(BigDecimal currNum) {
		this.currNum = currNum;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getInterestStart() {
		return interestStart;
	}

	public void setInterestStart(Date interestStart) {
		this.interestStart = interestStart;
	}

}
