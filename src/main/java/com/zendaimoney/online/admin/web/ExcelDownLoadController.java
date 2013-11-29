package com.zendaimoney.online.admin.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.util.ExcelReader;
import com.zendaimoney.online.admin.util.FileOperateUtil;
import com.zendaimoney.online.admin.util.FundsMigrate;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.FundsMigrateResultVo;
import com.zendaimoney.online.common.FileUploadUtil;
/**
 * 
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author 王腾飞
 * @date: 2012-12-5 下午3:34:41
 * operation by:
 * description:
 */
@Controller
@RequestMapping("/admin/fundsMigrate")
public class ExcelDownLoadController {
	@Autowired
	private FundsMigrate fundsMigrate;
	
	@RequestMapping("/")
	public String fundsMigrate(){
		return"admin/fundsMigrate/fundsMigrate";
	}
	
	@RequestMapping("downLoadResult")
	@ResponseBody
	public AjaxResult toDcExcel(HttpServletRequest request,String filePath) {
		FileInputStream is = null;
		try {
			is = new FileInputStream(filePath);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExcelReader excelReader = new ExcelReader(is);
		String fileName = fundsMigrate
				.updateProgress(excelReader);
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxResult resultMessage = new AjaxResult();
		if(fileName==null){
			resultMessage.setSuccess(false);
			resultMessage.setMsg("迁移失败！");
		}else if(fileName.endsWith("updateResult.xls")){
			resultMessage.setMsg(fileName);
		}else if(fileName.length()==0){
			resultMessage.setSuccess(false);
			resultMessage.setMsg("迁移成功，文件生成失败！");
		}
		
		return resultMessage;
	}
	@RequestMapping("checkData")
	@ResponseBody
	public AjaxResult checkData(String filePath){
		FileInputStream is = null;
		try {
			is = new FileInputStream(filePath);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExcelReader excelReader = new ExcelReader(is);
		List<String> checkResult = fundsMigrate.checkData(excelReader,filePath);
		AjaxResult checkMessage = new AjaxResult();
		if(checkResult.size()==0){
			checkMessage.setMsg(filePath);
			return checkMessage;
		}else{
			String message = checkResult.toString().replaceAll(",", "<div><br/></div>");
			message = message.substring(1, message.length()-1);
			checkMessage.setMsg(message);
		}
		
		return checkMessage;
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-6 下午12:54:34
	 * @param request
	 * @param model
	 * @return
	 * description:上传文件
	 */
	@RequestMapping("uploadExcel")
	public String uploadExcel(HttpServletRequest request,Model model) {
		String path = null;
		String flag = null;
		// 上传文件
		try {
			List<FileItem> items = FileUploadUtil.uploadFile(request, FileUploadUtil.FILEMAXSIZ_1_5M.longValue());
			// 验证文件未空的情况
			flag = FileUploadUtil.checkFileSize(items);
			if (flag != null && !flag.equals("true")) {
				model.addAttribute("uploadMessage", "没有选择文件！");
			}else if(!items.get(0).getName().endsWith(".xls")){
				model.addAttribute("uploadMessage", "请上传xls格式的文件！");
			}else{
				path = fundsMigrate.uploadFile(items.get(0));
				model.addAttribute("path", path);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/admin/fundsMigrate/fundsMigrate";
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2013-3-11 下午2:18:19
	 * @return
	 * description:
	 */
	@RequestMapping("showUpload")
	@ResponseBody
	public AjaxResult showUpload(String filePath){
		AjaxResult message = new AjaxResult();
		if(StringUtils.isNotEmpty(filePath)){
			message.setMsg(filePath);
		}else {
			message.setMsg("上传失败,请重新上传！");
		}
		return new AjaxResult();
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-11 上午10:31:55
	 * @return
	 * description:显示资金迁移历史结果
	 */
	@RequestMapping("showHistoryResult")
	@ResponseBody
	public List<FundsMigrateResultVo> showHistoryResult(){
		List<FundsMigrateResultVo> resultList =  fundsMigrate.getHistoryResultList();
		return resultList;
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-11 上午10:34:39
	 * @param request
	 * @param response
	 * @param fileType
	 * @param contentType
	 * @param fileName
	 * description:资金迁移模板下载
	 * @throws Exception 
	 */
	@RequestMapping("downLoadTemplate")
	public void downLoadTemplate(HttpServletRequest request,  
            HttpServletResponse response, String fileType) throws Exception{
		String filename = "资金迁移模板".concat(".xls");//设置下载时客户端Excel的名称     
		FileOperateUtil.download(request, response, "fundsMigrate", filename);
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-11 上午11:01:31
	 * @param request
	 * @param response
	 * @param fileType
	 * @param fileName
	 * @throws Exception
	 * description:历史迁移结果下载
	 */
	@RequestMapping("downLoadHistoryResult")
	public void downLoadHistoryResult(HttpServletRequest request,
			HttpServletResponse response, String fileType, String fileName)
			throws Exception {
		FileOperateUtil.download(request, response, "fundsMigrateResult",
				fileName);
	}

	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}

}
