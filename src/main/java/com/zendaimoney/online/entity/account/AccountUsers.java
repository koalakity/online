package com.zendaimoney.online.entity.account;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class AccountUsers implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2066519516630808311L;
	private BigDecimal userId;
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
	private AccountUserInfoPerson userInfoPerson;
	private UserCreditNote userCreditNote;
	private BigDecimal userStatus;
	private BigDecimal lockStatus;
	private BigDecimal returnStatus;
	private BigDecimal  delStatus;
	private String emailacCode;

	// Constructors
	

	/** default constructor */
	public AccountUsers() {
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_TIME_LAST", nullable = false, length = 7)
	public Date getLoginTimeLast() {
		return this.loginTimeLast;
	}

	public void setLoginTimeLast(Timestamp loginTimeLast) {
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
	
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")                          
    public AccountUserInfoPerson getUserInfoPerson() {                                      
        return userInfoPerson;                                                         
    }                                                                                
                                                                                   
    public void setUserInfoPerson(AccountUserInfoPerson userInfoPerson) {                   
        this.userInfoPerson = userInfoPerson;                                          
    }         
    
    @OneToOne(mappedBy="user")
    public UserCreditNote getUserCreditNote() {
		return userCreditNote;
	}

	public void setUserCreditNote(UserCreditNote userCreditNote) {
		this.userCreditNote = userCreditNote;
	}
	
	
	@Column(name = "USER_STATUS" )
    public BigDecimal getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(BigDecimal userStatus) {
		this.userStatus = userStatus;
	}
	
	@Column(name = "LOCK_STATUS" )
	public BigDecimal getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(BigDecimal lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	@Column(name = "RETURN_STATUS" )
	public BigDecimal getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(BigDecimal returnStatus) {
		this.returnStatus = returnStatus;
	}
	
	@Column(name = "EMAILAC_CODE" )
	public String getEmailacCode() {
		return emailacCode;
	}

	public void setEmailacCode(String emailacCode) {
		this.emailacCode = emailacCode;
	}

	public BigDecimal getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(BigDecimal delStatus) {
		this.delStatus = delStatus;
	}

	@Transient
    public String getSex(){
    	if(null==getUserInfoPerson()){
    		return "";
    	}
		if(new BigDecimal(1).equals(getUserInfoPerson().getSex())){
			return "男";
		}
		return "女";
	}
    
    @Transient
    public String getRealName(){
		return getUserInfoPerson()==null?"":getUserInfoPerson().getRealName();
	}
    
    @Transient
    public String getPhoneNo(){
    	return getUserInfoPerson()==null?"":getUserInfoPerson().getPhoneNo();
    }
    
    @Transient
    public String getIdentityNo(){
    	return getUserInfoPerson()==null?"":getUserInfoPerson().getIdentityNo();
    }
    
    @Transient
    public String getCreditGrade(){
    	if(getUserCreditNote()==null){
    		return "";
    	}
    	else{
    		return getUserCreditNote().getCreditGrade().toString();
    	}
    }
    
    @Transient
    public String getCreditAmount(){
    	if(getUserCreditNote()==null){
    		return "";
    	}
    	else{
    		return getUserCreditNote().getCreditAmount().toString();
    	}
    }
    
    @Transient
    public BigDecimal getCreditScoreSum(){
    	if(getUserCreditNote()==null){
    		return BigDecimal.ZERO;
    	}
    	else{
    		return getUserCreditNote().getCreditScoreSum();
    	} 
    }
}