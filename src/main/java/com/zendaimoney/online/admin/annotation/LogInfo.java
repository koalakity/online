package com.zendaimoney.online.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface LogInfo {
	OperateKind operateKind();
	String operateContent();
}
