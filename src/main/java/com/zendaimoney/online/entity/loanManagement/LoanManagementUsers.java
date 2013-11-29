package com.zendaimoney.online.entity.loanManagement;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERS")
public class LoanManagementUsers implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2066519516630808311L;
	private BigDecimal userId;
	private Long tCustomerId;
	private String loginName;
	private String loginPassword;
	private String email;
	private BigDecimal isapproveEmail;
	private String regIp;
	private Date regTime;
	private BigDecimal loginCount;
	private BigDecimal loginStatus;
	private Date loginTimeLast;
	private String loginIpLast;
	private BigDecimal statusView;
	private BigDecimal regType;
	private BigDecimal  isApprovePhone;
	private BigDecimal  isApproveCard;
	private List<LoanManagementLoanInfo> loanInfoes;
	private BigDecimal  delStatus;

	// Constructors
	
    /** default constructor */
	public LoanManagementUsers() {
	}
	public BigDecimal getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(BigDecimal delStatus) {
		this.delStatus = delStatus;
	}
	/** minimal constructor */
	public LoanManagementUsers(String loginName, String loginPassword, String email,
			BigDecimal isapproveEmail, String regIp, Date regTime,
			BigDecimal loginCount, BigDecimal loginStatus, Date loginTimeLast,
			String loginIpLast) {
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.email = email;
		this.isapproveEmail = isapproveEmail;
		this.regIp = regIp;
		this.regTime = regTime;
		this.loginCount = loginCount;
		this.loginStatus = loginStatus;
		this.loginTimeLast = loginTimeLast;
		this.loginIpLast = loginIpLast;
	}

	/** full constructor */
	public LoanManagementUsers(String loginName, String loginPassword, String email,
			BigDecimal isapproveEmail, String regIp, Date regTime,
			BigDecimal loginCount, BigDecimal loginStatus, Date loginTimeLast,
			String loginIpLast, BigDecimal statusView, BigDecimal regType,BigDecimal isApprovePhone,BigDecimal isApproveCard) {
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.email = email;
		this.isapproveEmail = isapproveEmail;
		this.regIp = regIp;
		this.regTime = regTime;
		this.loginCount = loginCount;
		this.loginStatus = loginStatus;
		this.loginTimeLast = loginTimeLast;
		this.loginIpLast = loginIpLast;
		this.statusView = statusView;
		this.regType = regType;
		this.isApprovePhone = isApprovePhone;
		this.isApproveCard = isApproveCard;
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName="USERS_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	
	@Column(name = "T_CUSTOMER_ID")
	public Long gettCustomerId() {
		return tCustomerId;
	}

	public void settCustomerId(Long tCustomerId) {
		this.tCustomerId = tCustomerId;
	}


	@Column(name = "LOGIN_NAME", nullable = false, length = 50)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "LOGIN_PASSWORD", nullable = false, length = 50)
	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name = "EMAIL", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ISAPPROVE_EMAIL", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIsapproveEmail() {
		return this.isapproveEmail;
	}

	public void setIsapproveEmail(BigDecimal isapproveEmail) {
		this.isapproveEmail = isapproveEmail;
	}

	@Column(name = "REG_IP", nullable = false, length = 40)
	public String getRegIp() {
		return this.regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_TIME", nullable = false, length = 7)
	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}

	@Column(name = "LOGIN_COUNT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(BigDecimal loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "LOGIN_STATUS", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(BigDecimal loginStatus) {
		this.loginStatus = loginStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LOGIN_TIME_LAST", nullable = false, length = 7)
	public Date getLoginTimeLast() {
		return this.loginTimeLast;
	}

	public void setLoginTimeLast(Date loginTimeLast) {
		this.loginTimeLast = loginTimeLast;
	}

	@Column(name = "LOGIN_IP_LAST", nullable = false, length = 50)
	public String getLoginIpLast() {
		return this.loginIpLast;
	}

	public void setLoginIpLast(String loginIpLast) {
		this.loginIpLast = loginIpLast;
	}

	@Column(name = "STATUS_VIEW", precision = 22, scale = 0)
	public BigDecimal getStatusView() {
		return this.statusView;
	}

	public void setStatusView(BigDecimal statusView) {
		this.statusView = statusView;
	}

	@Column(name = "REG_TYPE", precision = 22, scale = 0)
	public BigDecimal getRegType() {
		return this.regType;
	}

	public void setRegType(BigDecimal regType) {
		this.regType = regType;
	}
	

	@Column(name = "ISAPPROVE_PHONE" , precision = 22, scale = 0)
	public BigDecimal getIsApprovePhone() {
		return isApprovePhone;
	}

	public void setIsApprovePhone(BigDecimal isApprovePhone) {
		this.isApprovePhone = isApprovePhone;
	}

	@Column(name = "ISAPPROVE_CARD" , precision = 22, scale = 0)
	public BigDecimal getIsApproveCard() {
		return isApproveCard;
	}

	public void setIsApproveCard(BigDecimal isApproveCard) {
		this.isApproveCard = isApproveCard;
	}

    @OneToMany(cascade={CascadeType.ALL},mappedBy="user")
    public List<LoanManagementLoanInfo> getLoanInfoes() {
        return loanInfoes;
    }

    public void setLoanInfoes(List<LoanManagementLoanInfo> loanInfoes) {
        this.loanInfoes = loanInfoes;
    }

}