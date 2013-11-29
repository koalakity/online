package com.zendaimoney.online.vo.message;

import java.util.List;

import com.zendaimoney.online.entity.message.MessageSysMsg;


public class ReturnMessageInfoVO {
	/**发件箱条数 */
	private String items;
	/** 系统消息条数 */
	private String systemItems;
	/** 收信箱信息条数 */
	private String inboxItems;
	private List<MessageSysMsg> sysList;
	
	public List<MessageSysMsg> getSysList() {
		return sysList;
	}

	public void setSysList(List<MessageSysMsg> sysList) {
		this.sysList = sysList;
	}

	//	/** 系统消息信息 */
//	List<MessageUserMovementTemp> userMovementTemp;
//	/**发信箱的临时实体类 */
//	List<MessageStationLetterTemp>  mesStaTemp;
//	/** 收信箱信息*/
//	List<MessageStationLetterTemp> inboxInfoTemp;
	/**个人头像 */
	private String headPath;
	
	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

//	public List<MessageStationLetterTemp> getInboxInfoTemp() {
//		return inboxInfoTemp;
//	}
//
//	public void setInboxInfoTemp(List<MessageStationLetterTemp> inboxInfoTemp) {
//		this.inboxInfoTemp = inboxInfoTemp;
//	}

	public String getInboxItems() {
		return inboxItems;
	}

	public void setInboxItems(String inboxItems) {
		this.inboxItems = inboxItems;
	}

//	public List<MessageStationLetterTemp> getMesStaTemp() {
//		return mesStaTemp;
//	}
//
//	public List<MessageUserMovementTemp> getUserMovementTemp() {
//		return userMovementTemp;
//	}
//
//	public void setUserMovementTemp(List<MessageUserMovementTemp> userMovementTemp) {
//		this.userMovementTemp = userMovementTemp;
//	}
//
//	public void setMesStaTemp(List<MessageStationLetterTemp> mesStaTemp) {
//		this.mesStaTemp = mesStaTemp;
//	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getSystemItems() {
		return systemItems;
	}

	public void setSystemItems(String systemItems) {
		this.systemItems = systemItems;
	}



	
	
}
