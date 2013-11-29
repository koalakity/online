package com.zendaimoney.online.vo.message;


import java.math.BigDecimal;

import com.zendaimoney.online.entity.message.MessageStationLetter;

public class MessageStationLetterTemp extends MessageStationLetter{

	private BigDecimal items;
	
	private BigDecimal id;

	private String name;

	private String senderDate;
	
	private String headPath;
	
	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getSenderDate() {
		return senderDate;
	}

	public void setSenderDate(String senderDate) {
		this.senderDate = senderDate;
	}

	public BigDecimal getItems() {
		return items;
	}

	public void setItems(BigDecimal items) {
		this.items = items;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}
}