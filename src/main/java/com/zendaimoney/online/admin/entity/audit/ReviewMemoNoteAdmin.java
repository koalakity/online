package com.zendaimoney.online.admin.entity.audit;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.IdEntity;
import com.zendaimoney.online.admin.entity.Staff;

@Entity
@Table(name="REVIEW_MEMO")
public class ReviewMemoNoteAdmin extends IdEntity {
	private Staff staff;
	private String memo;
	private BigDecimal userId;
	private Date operateTime;


	@ManyToOne
	@JoinColumn(name="OPERATE_USER_ID")
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
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
		return operateTime==null?"":operateTime.toString().substring(0, 16);
	}
}
