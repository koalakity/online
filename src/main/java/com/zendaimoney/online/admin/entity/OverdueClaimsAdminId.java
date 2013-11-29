package com.zendaimoney.online.admin.entity;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OverdueClaimsId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class OverdueClaimsAdminId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8632364666924639518L;
	private Long investId;
	private Long num;

	// Constructors

	/** default constructor */
	public OverdueClaimsAdminId() {
	}

	/** full constructor */
	public OverdueClaimsAdminId(Long investId, Long num) {
		this.investId = investId;
		this.num = num;
	}

	// Property accessors

	@Column(name = "INVEST_ID", nullable = false, precision = 11, scale = 0)
	public Long getInvestId() {
		return this.investId;
	}

	public void setInvestId(Long investId) {
		this.investId = investId;
	}

	@Column(name = "NUM", nullable = false, precision = 3, scale = 0)
	public Long getNum() {
		return this.num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OverdueClaimsAdminId))
			return false;
		OverdueClaimsAdminId castOther = (OverdueClaimsAdminId) other;

		return ((this.getInvestId() == castOther.getInvestId()) || (this
				.getInvestId() != null && castOther.getInvestId() != null && this
				.getInvestId().equals(castOther.getInvestId())))
				&& ((this.getNum() == castOther.getNum()) || (this.getNum() != null
						&& castOther.getNum() != null && this.getNum().equals(
						castOther.getNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getInvestId() == null ? 0 : this.getInvestId().hashCode());
		result = 37 * result
				+ (getNum() == null ? 0 : this.getNum().hashCode());
		return result;
	}

}