package com.zendaimoney.online.admin.vo;

public class FundsMigrateResultVo {
	private String fileAbsolutePath;
	private String fileName;
	private String staffName;
	private String generateTime;
	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}
	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}
}
