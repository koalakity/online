package com.zendaimoney.online.admin.service;

import org.springframework.core.NestedRuntimeException;

public class BusinessException extends NestedRuntimeException {

	public BusinessException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -2660453006976608705L;

}
