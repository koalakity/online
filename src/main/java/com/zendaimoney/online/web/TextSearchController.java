package com.zendaimoney.online.web;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TextSearchController{
	@Autowired
	private ApplicationContext context;
	private static Logger logger = LoggerFactory.getLogger(TextSearchController.class);
	@ResponseBody
	@RequestMapping(value = "/search/textSearch")
	public String textSearch(HttpServletRequest request){
		String type=request.getParameter("type");
		if(type==null || type.equals("")){
			return "";
		}
		Object obj="";
		try {
			Object bean=this.context.getBean(type);
			Class<?> clsObj = bean.getClass();
			Class<?>[] clsObjImplInter=clsObj.getInterfaces();
			for(Class<?> _cls:clsObjImplInter){
				if(_cls==TextSearch.class){
					Method method=_cls.getDeclaredMethods()[0];
					obj=method.invoke(bean, request);
					logger.info(request.getRequestURI()+"获取数据内容为："+obj);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return obj.toString();
	}
	
	
}
