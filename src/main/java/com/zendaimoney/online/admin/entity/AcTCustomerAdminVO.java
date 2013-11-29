package com.zendaimoney.online.admin.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AcTCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AC_T_CUSTOMER")
public class AcTCustomerAdminVO implements java.io.Serializable {

	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6085842668688920659L;
	private Long id;
	private String customerNo;
	private String type;
	private String name;
	private String cardId;
	private String cardType;
	private Date signDate;
	private Date invalidDate;
	private String signOrgan;
	private String gender;
	private Date birthday;
	private String address;
	private String password1;
	private String password2;
	private String password3;
	private Date pwdDate;
	private Date pwdDate2;
	private Date pwdDate3;
	private String totalAcct;
	private String acctStatus;
	private String openacctOrgan;
	private String openacctTeller;
	private Date openacctDate;
	private String authTeller;
	private String memo;

	// Constructors

	/** default constructor */
	public AcTCustomerAdminVO() {
	}

	/** minimal constructor */
	public AcTCustomerAdminVO(String customerNo, String name, String totalAcct) {
		this.customerNo = customerNo;
		this.name = name;
		this.totalAcct = totalAcct;
	}

	/** full constructor */
	public AcTCustomerAdminVO(String customerNo, String type, String name,
			String cardId, String cardType, Date signDate, Date invalidDate,
			String signOrgan, String gender, Date birthday, String address,
			String password1, String password2, String password3, Date pwdDate,
			Date pwdDate2, Date pwdDate3, String totalAcct, String acctStatus,
			String openacctOrgan, String openacctTeller, Date openacctDate,
			String authTeller, String memo) {
		this.customerNo = customerNo;
		this.type = type;
		this.name = name;
		this.cardId = cardId;
		this.cardType = cardType;
		this.signDate = signDate;
		this.invalidDate = invalidDate;
		this.signOrgan = signOrgan;
		this.gender = gender;
		this.birthday = birthday;
		this.address = address;
		this.password1 = password1;
		this.password2 = password2;
		this.password3 = password3;
		this.pwdDate = pwdDate;
		this.pwdDate2 = pwdDate2;
		this.pwdDate3 = pwdDate3;
		this.totalAcct = totalAcct;
		this.acctStatus = acctStatus;
		this.openacctOrgan = openacctOrgan;
		this.openacctTeller = openacctTeller;
		this.openacctDate = openacctDate;
		this.authTeller = authTeller;
		this.memo = memo;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_NO", nullable = false, length = 30)
	public String getCustomerNo() {
		return this.customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "NAME", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CARD_ID", length = 30)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "CARD_TYPE", length = 2)
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SIGN_DATE", length = 7)
	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INVALID_DATE", length = 7)
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "SIGN_ORGAN", length = 150)
	public String getSignOrgan() {
		return this.signOrgan;
	}

	public void setSignOrgan(String signOrgan) {
		this.signOrgan = signOrgan;
	}

	@Column(name = "GENDER", length = 1)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "ADDRESS", length = 150)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PASSWORD1", length = 64)
	public String getPassword1() {
		return this.password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	@Column(name = "PASSWORD2", length = 64)
	public String getPassword2() {
		return this.password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@Column(name = "PASSWORD3", length = 64)
	public String getPassword3() {
		return this.password3;
	}

	public void setPassword3(String password3) {
		this.password3 = password3;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PWD_DATE", length = 7)
	public Date getPwdDate() {
		return this.pwdDate;
	}

	public void setPwdDate(Date pwdDate) {
		this.pwdDate = pwdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PWD_DATE2", length = 7)
	public Date getPwdDate2() {
		return this.pwdDate2;
	}

	public void setPwdDate2(Date pwdDate2) {
		this.pwdDate2 = pwdDate2;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PWD_DATE3", length = 7)
	public Date getPwdDate3() {
		return this.pwdDate3;
	}

	public void setPwdDate3(Date pwdDate3) {
		this.pwdDate3 = pwdDate3;
	}

	@Column(name = "TOTAL_ACCT", nullable = false, length = 30)
	public String getTotalAcct() {
		return this.totalAcct;
	}

	public void setTotalAcct(String totalAcct) {
		this.totalAcct = totalAcct;
	}

	@Column(name = "ACCT_STATUS", length = 1)
	public String getAcctStatus() {
		return this.acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	@Column(name = "OPENACCT_ORGAN", length = 20)
	public String getOpenacctOrgan() {
		return this.openacctOrgan;
	}

	public void setOpenacctOrgan(String openacctOrgan) {
		this.openacctOrgan = openacctOrgan;
	}

	@Column(name = "OPENACCT_TELLER", length = 20)
	public String getOpenacctTeller() {
		return this.openacctTeller;
	}

	public void setOpenacctTeller(String openacctTeller) {
		this.openacctTeller = openacctTeller;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OPENACCT_DATE", length = 7)
	public Date getOpenacctDate() {
		return this.openacctDate;
	}

	public void setOpenacctDate(Date openacctDate) {
		this.openacctDate = openacctDate;
	}

	@Column(name = "AUTH_TELLER", length = 20)
	public String getAuthTeller() {
		return this.authTeller;
	}

	public void setAuthTeller(String authTeller) {
		this.authTeller = authTeller;
	}

	@Column(name = "MEMO", length = 150)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}