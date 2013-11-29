package com.zendaimoney.online.admin.entity.account;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "ID5_CHECK")
public class ID5Check implements java.io.Serializable{
	
	
	private static final long serialVersionUID = -4478886462067410416L;
	
	private BigDecimal userId;
	private AccountUsersAdmin user;
	private String name;
	private String cardId;
	private String checkStatusCode;
	private String location1;
	private String location2;
	private String photo;
	private String memo;
	
	
	
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}


	@Column(name = "NAME", length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CARD_ID", length = 30)
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "CHECK_STATUS_CODE", length = 2)
	public String getCheckStatusCode() {
		return checkStatusCode;
	}

	public void setCheckStatusCode(String checkStatusCode) {
		this.checkStatusCode = checkStatusCode;
	}

	@Column(name = "LOCATION1", length = 80)
	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	@Column(name = "LOCATION2", length = 80)
	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	@Column(name = "PHOTO")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "MEMO", length = 100)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)       
	@OneToOne      
	public AccountUsersAdmin getUser() {
	   return user;
	}

	public void setUser(AccountUsersAdmin user) {
	        this.user = user;
	    }


}
