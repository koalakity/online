package com.zendaimoney.online.vo.personal;

import java.util.List;

import com.zendaimoney.online.entity.personal.PersonalUsers;

public class ReturnPersonalUsersVo extends PersonalUsers{
	
	private List provinceList;
	private List cityList;
	
	public List getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}
	public List getCityList() {
		return cityList;
	}
	public void setCityList(List cityList) {
		this.cityList = cityList;
	}
	

}
