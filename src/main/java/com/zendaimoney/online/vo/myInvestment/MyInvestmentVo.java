package com.zendaimoney.online.vo.myInvestment;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentInvestInfo;

public class MyInvestmentVo extends MyInvestmentInvestInfo{
	
	//剩余天数
	private String leaveDate;

	//借款进度
	private String tempo;
	
	//地区
	private String area;

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}
