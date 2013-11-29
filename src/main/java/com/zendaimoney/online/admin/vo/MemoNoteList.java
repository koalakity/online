package com.zendaimoney.online.admin.vo;

import java.util.Date;

public class MemoNoteList {
	private String name;
	private String memoText;
	private Date operateTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
