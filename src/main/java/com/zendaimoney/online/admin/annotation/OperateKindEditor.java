package com.zendaimoney.online.admin.annotation;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

public class OperateKindEditor extends PropertyEditorSupport {
	private Class<OperateKind> clazz;

	public OperateKindEditor(Class<OperateKind> clazz) {
		this.clazz = clazz;
	};

	public String getAsText() {
		return (getValue() == null ? "" : ((OperateKind) getValue()).name());
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isBlank(text)){
			setValue(null);
		}
		else{
			setValue(Enum.valueOf(clazz, text));
		}
	}
}