package com.zendaimoney.online.admin.web.DTO;

//default package

import com.zendaimoney.online.admin.entity.ChannelInfoVO;

/**
 * Channelinfo entity. @author MyEclipse Persistence Tools
 */
public class ChannelInfoDTO extends ChannelInfoVO {
	private String name1;
	private String name2;
	private String createDate;
	private String updateDate;
	private String rateName;

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

}