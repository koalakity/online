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

import com.zendaimoney.online.admin.entity.Staff;


@Entity
@Table(name="MEMO_NOTE")
public class AccountMemoNoteAdmin implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2062242572636075604L;
	
	private BigDecimal id;
	private BigDecimal userId;
	private String memoText;
//	private BigDecimal operateUserId;
	private Date operateTime;
	private Staff staff;
	
	
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	
	@Column(name = "user_id", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	
	public String getMemoText() {
		return memoText;
	}
	public void setMemoText(String memoText) {
		this.memoText = memoText;
	}
	
//	public BigDecimal getOperateUserId() {
//		return operateUserId;
//	}
//	public void setOperateUserId(BigDecimal operateUserId) {
//		this.operateUserId = operateUserId;
//	}
	
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	@ManyToOne
	@JoinColumn(name="OPERATE_USER_ID")
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	@Transient
	public String getOpearateUser() {
		return staff.getName();
	}
	@Transient
	public String getOperateTimeFormatt() {
		return operateTime==null?"":operateTime.toString().substring(0, 19);
	}
	
	

}
