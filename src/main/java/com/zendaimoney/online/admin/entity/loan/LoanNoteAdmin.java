package com.zendaimoney.online.admin.entity.loan;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.IdEntity;
import com.zendaimoney.online.admin.entity.Staff;

@Entity
@Table(name="LOAN_NOTE")
public class LoanNoteAdmin extends IdEntity{

	private Staff staff;
	private LoanInfoAdmin loanInfo;
	private String memoText;
	private Date operateTime;
	
	@ManyToOne
	@JoinColumn(name="LOAN_ID")
	public LoanInfoAdmin getLoanInfo() {
		return loanInfo;
	}
	public void setLoanInfo(LoanInfoAdmin loanInfo) {
		this.loanInfo = loanInfo;
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
	public String getOpearateUser() {
		return staff.getName();
	}
	@Transient
	public String getOperateTimeFormatt() {
		return operateTime==null?"":operateTime.toString().substring(0, 19);
	}
}
