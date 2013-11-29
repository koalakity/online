package com.zendaimoney.online.web.DTO;

import com.zendaimoney.online.entity.upload.FileUploadVO;

public class FileUploadDTO extends FileUploadVO{
	private byte[] imgByte;

	public byte[] getImgByte() {
		return imgByte;
	}

	public void setImgByte(byte[] imgByte) {
		this.imgByte = imgByte;
	}
	
}
