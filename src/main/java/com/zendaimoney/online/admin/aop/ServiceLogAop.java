package com.zendaimoney.online.admin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.entity.IdEntity;
import com.zendaimoney.online.admin.service.AdminLogService;

@Aspect
@Component
public class ServiceLogAop {
	@Autowired
	private AdminLogService adminLogService;

	@Around("@annotation( logInfo )")
	public Object createLog(ProceedingJoinPoint proceedingJoinPoint,
			LogInfo logInfo) throws Throwable {
		Object object = proceedingJoinPoint.proceed();
		Long id=null;
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if(arg instanceof IdEntity){
				id=((IdEntity)arg).getId();
			}
		}
		adminLogService.createLog(logInfo,id);
		return object;
	}
}
