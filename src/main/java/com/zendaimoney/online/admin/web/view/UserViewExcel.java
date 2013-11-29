package com.zendaimoney.online.admin.web.view;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;

public class UserViewExcel extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename = "会员.xls";//设置下载时客户端Excel的名称     
        filename = encodeFilename(filename, request);//处理中文文件名  
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
		HSSFSheet sheet = workbook.createSheet("会员");
		sheet.setDefaultColumnWidth( 12);
		setText(getCell(sheet, 0, 0), "ID5验证");
		setText(getCell(sheet, 0, 1), "昵称");
		setText(getCell(sheet, 0, 2), "邮箱");
		setText(getCell(sheet, 0, 3), "姓名");
		setText(getCell(sheet, 0, 4), "性别");
		setText(getCell(sheet, 0, 5), "手机号码");
		setText(getCell(sheet, 0, 6), "身份证号码");
		setText(getCell(sheet, 0, 7), "信用等级");
		setText(getCell(sheet, 0, 8), "信用额度");
		setText(getCell(sheet, 0, 9), "用户状态");

		List<AccountUsersAdmin> list = (List<AccountUsersAdmin>) model.get("userList");
		for (int i = 0; i < list.size(); i++) {
			setText(getCell(sheet, 1 + i, 0), BigDecimal.ZERO.equals(list.get(i).getIsApproveCard())?"未验证":"已验证");
			setText(getCell(sheet, 1 + i, 1), list.get(i).getLoginName());
			setText(getCell(sheet, 1 + i, 2), list.get(i).getEmail());
			setText(getCell(sheet, 1 + i, 3), list.get(i).getRealName());
			setText(getCell(sheet, 1 + i, 4), list.get(i).getSex());
			setText(getCell(sheet, 1 + i, 5), list.get(i).getPhoneNo());
			setText(getCell(sheet, 1 + i, 6), list.get(i).getIdentityNo());
			setText(getCell(sheet, 1 + i, 7), list.get(i).getCreditGrade());
			setText(getCell(sheet, 1 + i, 8), list.get(i).getCreditAmount());
			setText(getCell(sheet, 1 + i, 9), list.get(i).getUserStatusStr());
		}

	}
	
	/**  
     * 设置下载文件中文件的名称  
     *   
     * @param filename  
     * @param request  
     * @return  
     */    
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
