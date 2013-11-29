package com.zendaimoney.online.admin.vo;

public class FileVo {
	// 文件名
	private String fileName;
	private String fileTime;
	private String userApproveId;
	// 是否删除
	private String isDel;
	// 状态:1,已上传;2,已提交;2,已审核
	private String status;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileTime() {
		return fileTime;
	}

	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	public String getUserApproveId() {
		return userApproveId;
	}

	public void setUserApproveId(String userApproveId) {
		this.userApproveId = userApproveId;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
