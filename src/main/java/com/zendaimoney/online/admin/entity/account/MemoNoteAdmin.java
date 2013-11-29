package com.zendaimoney.online.admin.entity.account;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.IdEntity;
import com.zendaimoney.online.admin.entity.Staff;

@Entity
@Table(name="MEMO_NOTE")
public class MemoNoteAdmin extends IdEntity{

	private Staff staff;
	private AccountUsersAdmin accountUsersAdmin;
	private String memoText;
	private Date operateTime;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public AccountUsersAdmin getAccountUsersAdmin() {
		return accountUsersAdmin;
	}
	public void setAccountUsersAdmin(AccountUsersAdmin accountUsersAdmin) {
		this.accountUsersAdmin = accountUsersAdmin;
	}
	
	@ManyToOne
	@JoinColumn(name="OPERATE_USER_ID")
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	public String getMemoText() {
		return memoText;
	}
	public void setMemoText(String memoText) {
		this.memoText = memoText;
	}
	
	
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	@Transient
	public String getName(){
		return staff.getName();
	}
	
}
