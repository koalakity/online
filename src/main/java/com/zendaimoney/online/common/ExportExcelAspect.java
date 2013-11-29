package com.zendaimoney.online.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zendaimoney.online.admin.util.ExcelWriter;
import com.zendaimoney.online.admin.util.FileOperateUtil;

/**
 * 必须要在com.zendaimoney.online.admin.service目录下
 * 方法必须以exportExcel开头 
 * 方法必须包含request,response对象 
 * 方法必须返回Map集合
 *  	path:服务器文件临时地址,
 * 		data:要写入的数据内容，第一行是表头，其他是数据内容List<String[]>)
 * @author HuYaHui
 * 
 */
public class ExportExcelAspect {
	private static Logger logger = LoggerFactory.getLogger(ExportExcelAspect.class);

	public Object runOnAround(ProceedingJoinPoint point) throws Throwable {
		logger.info("调用导出excel方法："+point.getSignature().toString());
		Object object = point.proceed();
		if (object instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>) object;
			if (dataMap == null || dataMap.size() != 2) {
				return object;
			}
			HttpServletRequest request = null;
			HttpServletResponse response = null;
			Object[] args = point.getArgs();
			for (Object arg : args) {
				if (arg instanceof HttpServletRequest) {
					request = (HttpServletRequest) arg;
				} else if (arg instanceof HttpServletResponse) {
					response = (HttpServletResponse) arg;
				}
			}

			String path = dataMap.get("path").toString();
			List<String[]> fileDataList = (List<String[]>) dataMap.get("data");
			if (request == null || response == null || path == null
					|| fileDataList == null) {
				return object;
			}
			ExcelWriter.writeExcel(fileDataList, "sheet", path);
			if (path != null && !path.equals("")) {
				FileOperateUtil.download(request, response, path);
			}
		}
		return object;
	}
}
