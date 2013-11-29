/**
 * 
 */
package com.zendaimoney.online.admin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.admin.dao.CheckUserInfo;
import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.admin.vo.FundsMigrateResultVo;
import com.zendaimoney.online.common.DateUtil;

/**
 * 资金迁移
 * @author 王腾飞
 *
 */
@Component
public class FundsMigrate {
	@Autowired
	private CheckUserInfo checkUserInfo;
	
	public List<String> checkData(ExcelReader excelReader,String filePath){
		//Map<String, String> checkMessage = new HashMap<String, String>();
		List<String> checkMessage = new ArrayList<String>();
		HSSFSheet sheet = excelReader.getSheet(0);
		int rows = sheet.getPhysicalNumberOfRows();
		if(rows>1){
			for (int i=1; i<rows;i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell emialCell = row.getCell(0);
				HSSFCell idCardCell = row.getCell(3);
				HSSFCell amountCell = row.getCell(4);
//				if(emialCell==null||idCardCell==null){
//					checkMessage.add("第"+(i+1)+"行数据email为空 ！请在excel中右键删除该行或者完善数据。");
//					continue;
//				}
				String email = excelReader.getStringCellValue(emialCell);
				String idCardNo = excelReader.getStringCellValue(idCardCell);
				String amount =excelReader.getStringCellValue(amountCell);
				if(StringUtils.isEmpty(email)){
					checkMessage.add("第"+(i+1)+"行数据email为空！ 请在excel中右键删除该行或者完善数据。");
					continue;
				}
				//StringUtils.isEmpty(excelReader.getStringCellValue(row.getCell(0)));
				String checkResult = checkEmailAndIdCardNo(email,idCardNo,amount);
				if(checkResult!=null){
					//checkMessage.put("第"+(i+1)+"行数据:", checkResult);
					checkMessage.add("第"+(i+1)+"行数据:"+checkResult);
				}
			}
		}else {
			checkMessage.add("没有数据！");
		}
		
		if(checkMessage.size()>0){
			DeleteFolder(filePath);
		}
		return checkMessage;
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-5 下午4:52:31
	 * @param email
	 * @param idCardNo
	 * @return
	 * description:检查数据是否合法
	 */
	private String checkEmailAndIdCardNo(String email,String idCardNo,String amount){
		int resultSize = checkUserInfo.findByEmail(email);
		String userIdentityNo = checkUserInfo.findByUserIdentityNoByEmail(email);
		if(resultSize==0){
			return "用户"+email+"不存在！";
		}else if(resultSize>1){
			return  "用户"+email+"不唯一！";
		} else if(!isNumeric(amount)){
			return "迁移金额必需为数字类型，当前值为"+amount;
		}else if(Double.valueOf(amount)<0){
			return "迁移金额必需大于0!，当前值为："+amount;
		}else if(StringUtils.isEmpty(idCardNo)||userIdentityNo.equals(idCardNo)){
			return null;
		}else{
			if(!userIdentityNo.equals(idCardNo)){
				return "用户注册邮箱和身份证号码不匹配！";
			}else{
				return null;
			}
			
		}
	}
	
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-5 下午4:22:33
	 * @param excelReader
	 * @return
	 * description:更新数据
	 * @throws Exception 
	 */
	public String  updateProgress(ExcelReader excelReader){
		List<Map<Integer,String>> resultData = new ArrayList<Map<Integer,String>>();
		HSSFSheet sheet = excelReader.getSheet(0);
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i=1; i<rows;i++){
			HSSFRow row = sheet.getRow(i);
			String email = excelReader.getStringCellValue(row.getCell(0));
			String idCardNo = excelReader.getStringCellValue(row.getCell(3));
			String amount = excelReader.getStringCellValue(row.getCell(4));
			Map<Integer, String> rowData = new HashMap<Integer, String>();
			for(int cells=0;cells<row.getPhysicalNumberOfCells();cells++){
				if(row.getCell(cells)==null){
					continue;
				}
				rowData.put(cells+1, excelReader.getStringCellValue(row.getCell(cells)));
			}
			String checkResult = checkEmailAndIdCardNo(email,idCardNo,amount);
			if(checkResult==null){
				Map<Integer,String> result = updateFunds(email,Double.valueOf(amount));
				rowData.put(6, result.get(4));
				rowData.put(7, result.get(5));
				rowData.put(8, result.get(2));
			}
			resultData.add(rowData);
		}
		if(resultData.size()==0){
			return null;
		}
		String fileName = generaterUpdateResult(resultData);
		return fileName;
	}
	
	public String generaterUpdateResult(List<Map<Integer, String>> resultData){
		Staff staff = (Staff) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String staffName = staff.getLoginName();
		String filename = DateUtil.getStrDate2(new Date()).concat(staffName)
				.concat("updateResult.xls");// 设置下载时客户端Excel的名称
		HSSFWorkbook workBook = new HSSFWorkbook();
		workBook = initExcel(workBook);
		HSSFSheet sheet = workBook.getSheet("sheet1");
		for (int rowNum = 0, rowSize = resultData.size(); rowNum < rowSize; rowNum++) {
			Map<Integer, String> data = resultData.get(rowNum);
			HSSFRow row = sheet.createRow(rowNum + 1);
			for (int j = 0; j < 8; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(data.get(j + 1));
			}
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(
					FundsMigrate.getFilePath("fundsMigrateResult") + filename);
		} catch (FileNotFoundException e) {
			filename = "";
			System.out.println("没有找到上传的文件！");
			e.printStackTrace();
		}
		try {
			workBook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			filename = "";
			System.out.println("数据生成失败！");
			e.printStackTrace();
		}
		
		return filename;
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-5 下午3:00:19
	 * @param email
	 * @param amount
	 * description:更加email和amount更新用户资金账户
	 */
	public Map<Integer,String> updateFunds(String email,double amount){
		// 1:资金更新条数。2：插入流ID。3：插入流水条数。4：更新前数据。5：更新后数据
		Map<Integer,String> result = checkUserInfo.updateUserAcTLedgerAmount(email, amount);
		String excuteCount = result.get(1);
		String flowInsertCount = result.get(3);
		if("1".equals(excuteCount) && "1".equals(flowInsertCount)){
			return result;
		}else {
			return null;
		}
	}
	
	public String uploadFile(FileItem item){
		long start = System.currentTimeMillis();
		String flg = "false";
		

		// 这里的路径有可能变化
		String path = getFilePath("fundsMigrateUpload");
		// 文件夹不存在就自动创建：
		File filea = new File(path);
		if (!(filea.exists()) && !(filea.isDirectory())) {
			filea.mkdirs();
		}

		String name = null;
		
			// 判读FileItem类对象封装的数据是否属于文件表单字段
		if (item.isFormField() == false) {
			Staff staff = (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String staffName = staff.getLoginName();
			name = item.getName();// 获得文件上传字段中的文件名
			String fileName = DateUtil.getStrDate2(new Date());
			File file = new File(path, fileName.concat(staffName).concat(".xls"));
			try {
				item.write(file);
				item.delete();
				return path+fileName.concat(staffName).concat(".xls");
			} catch (Exception e) {
				
				e.printStackTrace();
				return null;
			}// 直接保存文件
			
		} else {
//				userInfoPerson = setValueFromItem(userInfoPerson, item);
//				logger.info("=============释放资源！=============");
		}
		
		return null;
		
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2013-3-11 下午2:53:42
	 * @return
	 * description: 获取文件路径
	 */
	public static String getFilePath(String propertiesName) {
		Properties props = new Properties();
		try {
			InputStream in = FundsMigrate.class.getResourceAsStream("/filePath.properties");
			props.load(in);
			in.close();
			String value = props.getProperty(propertiesName);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @author 王腾飞
	 * @date 2012-12-11 上午11:11:50
	 * @return
	 * description:获取历史资金迁移结果
	 */
	public List<FundsMigrateResultVo> getHistoryResultList(){
		List<File> files = FileOperateUtil.sortFileByName(getFilePath("fundsMigrateResult"));
		List<FundsMigrateResultVo> resultListVo = new ArrayList<FundsMigrateResultVo>();
		if(files!=null&&files.size()>0){
			int begin = 0;
			if(files.size()>20){
				begin = files.size()-20;
			}
			for(int i=files.size()-1;i>=begin;i--){
				FundsMigrateResultVo resultVo = new FundsMigrateResultVo();
				String fileName = files.get(i).getName();
				String fileAbsolutePath = files.get(i).getAbsolutePath();
				resultVo.setFileName(fileName);
				resultVo.setFileAbsolutePath(fileAbsolutePath);
				resultVo.setStaffName(fileName.substring(14, fileName.length()-16));
				resultVo.setGenerateTime(fileName.substring(0, 4)+"-"+fileName.substring(4, 6)+"-"+fileName.substring(6, 8)+" "+fileName.substring(8, 10)+":"+fileName.substring(10, 12)+":"+fileName.substring(12,14));
				resultListVo.add(resultVo);
			}
		}
		return resultListVo;
	}
	
	/** 
	 *  根据路径删除指定的目录或文件，无论存在与否 
	 *@param sPath  要删除的目录或文件 
	 *@return 删除成功返回 true，否则返回 false。 
	 */  
	public boolean DeleteFolder(String sPath) {  
		boolean flag = false;  
	    File file = new File(sPath);  
	    // 判断目录或文件是否存在  
	    if (!file.exists()) {  // 不存在返回 false  
	        return flag;  
	    } else {  
	        // 判断是否为文件  
	        if (file.isFile()) {  // 为文件时调用删除文件方法  
	            return deleteFile(sPath);  
	        } else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(sPath);  
	        }  
	    }  
	}  
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public boolean deleteFile(String sPath) {  
		boolean flag = false;  
	   File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	} 
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
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
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
		sheet.setDefaultColumnWidth( 12);
		HSSFRow row = sheet.createRow(0);
		row.setRowStyle(cellStyle);
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue("email");
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("昵称");
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("姓名");
		HSSFCell cell3 = row.createCell(3);
		cell3.setCellValue("身份证号码");
		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue("资金金额");
		HSSFCell cell5 = row.createCell(5);
		cell5.setCellValue("迁移前金额");
		HSSFCell cell6 = row.createCell(6);
		cell6.setCellValue("迁移后金额");
		HSSFCell cell7 = row.createCell(7);
		cell7.setCellValue("流水号");
//		setText(getCell(sheet, 0, 0), "email");
//		setText(getCell(sheet, 0, 1), "昵称");
//		setText(getCell(sheet, 0, 2), "姓名");
//		setText(getCell(sheet, 0, 3), "身份证号码");
//		setText(getCell(sheet, 0, 4), "资金金额");
//		setText(getCell(sheet, 0, 5), "迁移前金额");
//		setText(getCell(sheet, 0, 6), "迁移后金额");
//		setText(getCell(sheet, 0, 7), "流水号");
		return workbook;
	}
	/**
	 * 判断字符串是否为数字
	 * @author 王腾飞
	 * @date 2013-1-14 上午10:51:42
	 * @param value
	 * @return
	 * description:
	 */
	public Boolean isNumeric(String value){
		try {
			Double.valueOf(value);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
}
