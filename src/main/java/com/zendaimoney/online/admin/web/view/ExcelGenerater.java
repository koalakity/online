package com.zendaimoney.online.admin.web.view;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.admin.util.FundsMigrate;
import com.zendaimoney.online.common.DateUtil;
/**
 * 
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author 王腾飞
 * @date: 2012-12-5 下午2:18:31
 * operation by:
 * description:
 */
public class ExcelGenerater extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,HSSFWorkbook workbook, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Staff staff = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String staffName = staff.getLoginName();
		String filename = DateUtil.getStrDate2(new Date()).concat(staffName).concat("updateResult.xls");//设置下载时客户端Excel的名称     
        filename = encodeFilename(filename, request);//处理中文文件名  
        workbook = initExcel(workbook);
        HSSFSheet sheet = workbook.getSheet("sheet1");
        @SuppressWarnings("unchecked")
		List<Map<Integer, String>> rowData = (List<Map<Integer, String>>) model.get("rowData");
        for(int i=0;i<rowData.size();i++){
        	Map<Integer, String> data = rowData.get(i);
        	for(int j=0;j<8;j++){
        		setText(getCell(sheet, i+1, j), data.get(j+1));
        	}
        }
        response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + filename);     
        OutputStream ouputStream = response.getOutputStream(); 
        FileOutputStream fOut  =   new  FileOutputStream(FundsMigrate.getFilePath("fundsMigrateResult")+filename);
        workbook.write(fOut);
        fOut.flush();
        fOut.close();
        workbook.write(ouputStream);     
        ouputStream.flush();     
        ouputStream.close();     
		
	}
	
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-5 下午4:12:46
	 * @param workbook
	 * @return
	 * description:初始化excle表格
	 */
	public HSSFWorkbook initExcel(HSSFWorkbook workbook){
		
		HSSFSheet sheet = workbook.createSheet("sheet1");
		sheet.setDefaultColumnWidth( 12);
		setText(getCell(sheet, 0, 0), "email");
		setText(getCell(sheet, 0, 1), "昵称");
		setText(getCell(sheet, 0, 2), "姓名");
		setText(getCell(sheet, 0, 3), "身份证号码");
		setText(getCell(sheet, 0, 4), "资金金额");
		setText(getCell(sheet, 0, 5), "迁移前金额");
		setText(getCell(sheet, 0, 6), "迁移后金额");
		setText(getCell(sheet, 0, 7), "流水号");
		return workbook;
	}
	
	public  String encodeFilename(String filename, HttpServletRequest request) {    
	      /**  
	       * 获取客户端浏览器和操作系统信息  
	       * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)  
	       * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6  
	       */    
	      String agent = request.getHeader("USER-AGENT");    
	      try {    
	        if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {    
	          String newFileName = URLEncoder.encode(filename, "UTF-8");    
	          newFileName = StringUtils.replace(newFileName, "+", "%20");    
	          if (newFileName.length() > 150) {    
	            newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");    
	            newFileName = StringUtils.replace(newFileName, " ", "%20");    
	          }    
	          return newFileName;    
	        }    
	        if ((agent != null) && (-1 != agent.indexOf("Mozilla")))    
	          return MimeUtility.encodeText(filename, "UTF-8", "B");    
	      
	        return filename;    
	      } catch (Exception ex) {    
	        return filename;    
	      }    
	    }   

}
