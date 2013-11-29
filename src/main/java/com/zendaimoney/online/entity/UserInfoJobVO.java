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
 * UserInfoJob entity. @author MyEclipse Persistence Tools
 */
@Entity(name="UserInfoJobVO")
@Table(name = "USER_INFO_JOB")
public class UserInfoJobVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5922605826949739343L;
	private Long workInfoId;
	private Long userId;
	private String corporationName;
	private BigDecimal corporationKind;
	private BigDecimal corporationIndustry;
	private BigDecimal corporationScale;
	private String position;
	private BigDecimal jobProvince;
	private BigDecimal jobCity;
	private BigDecimal presentCorporationWorkTime;
	private String corporationPhoneArea;
	private String corporationPhone;
	private String jobEmail;
	private String corporationAddress;
	private String corporationWeb;

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="USERINFOJOB_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "WORK_INFO_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getWorkInfoId() {
		return this.workInfoId;
	}

	public void setWorkInfoId(Long workInfoId) {
		this.workInfoId = workInfoId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "CORPORATION_NAME", length = 100)
	public String getCorporationName() {
		return this.corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	@Column(name = "CORPORATION_KIND", precision = 22, scale = 0)
	public BigDecimal getCorporationKind() {
		return this.corporationKind;
	}

	public void setCorporationKind(BigDecimal corporationKind) {
		this.corporationKind = corporationKind;
	}

	@Column(name = "CORPORATION_INDUSTRY", precision = 22, scale = 0)
	public BigDecimal getCorporationIndustry() {
		return this.corporationIndustry;
	}

	public void setCorporationIndustry(BigDecimal corporationIndustry) {
		this.corporationIndustry = corporationIndustry;
	}

	@Column(name = "CORPORATION_SCALE", precision = 22, scale = 0)
	public BigDecimal getCorporationScale() {
		return this.corporationScale;
	}

	public void setCorporationScale(BigDecimal corporationScale) {
		this.corporationScale = corporationScale;
	}

	@Column(name = "POSITION", length = 60)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "JOB_PROVINCE", precision = 22, scale = 0)
	public BigDecimal getJobProvince() {
		return this.jobProvince;
	}

	public void setJobProvince(BigDecimal jobProvince) {
		this.jobProvince = jobProvince;
	}

	@Column(name = "JOB_CITY", precision = 22, scale = 0)
	public BigDecimal getJobCity() {
		return this.jobCity;
	}

	public void setJobCity(BigDecimal jobCity) {
		this.jobCity = jobCity;
	}

	@Column(name = "PRESENT_CORPORATION_WORK_TIME", precision = 22, scale = 0)
	public BigDecimal getPresentCorporationWorkTime() {
		return this.presentCorporationWorkTime;
	}

	public void setPresentCorporationWorkTime(
			BigDecimal presentCorporationWorkTime) {
		this.presentCorporationWorkTime = presentCorporationWorkTime;
	}

	@Column(name = "CORPORATION_PHONE_AREA", length = 6)
	public String getCorporationPhoneArea() {
		return this.corporationPhoneArea;
	}

	public void setCorporationPhoneArea(String corporationPhoneArea) {
		this.corporationPhoneArea = corporationPhoneArea;
	}

	@Column(name = "CORPORATION_PHONE", length = 12)
	public String getCorporationPhone() {
		return this.corporationPhone;
	}

	public void setCorporationPhone(String corporationPhone) {
		this.corporationPhone = corporationPhone;
	}

	@Column(name = "JOB_EMAIL", length = 50)
	public String getJobEmail() {
		return this.jobEmail;
	}

	public void setJobEmail(String jobEmail) {
		this.jobEmail = jobEmail;
	}

	@Column(name = "CORPORATION_ADDRESS", length = 200)
	public String getCorporationAddress() {
		return this.corporationAddress;
	}

	public void setCorporationAddress(String corporationAddress) {
		this.corporationAddress = corporationAddress;
	}

	@Column(name = "CORPORATION_WEB", length = 50)
	public String getCorporationWeb() {
		return this.corporationWeb;
	}

	public void setCorporationWeb(String corporationWeb) {
		this.corporationWeb = corporationWeb;
	}

}