package com.zendaimoney.online.vo.message;

import com.zendaimoney.online.entity.message.MessageStationLetter;



public class MessageSxxVo extends MessageStationLetter{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7088531892201474040L;
	private String sendDateStr;

	public String getSendDateStr() {
		return sendDateStr;
	}

	public void setSendDateStr(String sendDateStr) {
		this.sendDateStr = sendDateStr;
	}

}
