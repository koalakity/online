package com.zendaimoney.online.admin.vo;



public class Page<T> {
	private org.springframework.data.domain.Page<T> sourcePage;

	public Page(org.springframework.data.domain.Page<T> page) {
		this.sourcePage=page;
	}

	public long getTotal(){
		return sourcePage.getTotalElements();
	}
	
	public Iterable<T> getRows(){
		return sourcePage.getContent();
	}
}
