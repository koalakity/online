package com.zendaimoney.online.admin.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {
	
	
	/**
	 * 写入excel文件
	 * 2013-3-22 上午11:58:59 by HuYaHui
	 * @param excelList
	 * 			第一行为表头，其他行为数据
	 * @param sheetName
	 * 			工作薄名称
	 * @param path
	 * 			要写入的路径
	 * @return
	 * 			写入成功后的路径
	 * @throws Exception
	 */
	public static String writeExcel(List<String[]> excelList, String sheetName,
			String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
			file.createNewFile();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		// 設置表格名
		HSSFSheet sheet = wb.createSheet(sheetName);
		   
		for (int i = 0; i < excelList.size(); i++) {
			sheet.setColumnWidth(i,5000);
			HSSFRow row = sheet.createRow(i);
			String[] strs = excelList.get(i);
			for (int j = 0; j < strs.length; j++) {
				createCellTitle(wb, row, j, strs[j]);
			}
		}
		FileOutputStream fOut = new FileOutputStream(file.getAbsolutePath());
		wb.write(fOut);
		fOut.flush();
		fOut.close();
		return file.getAbsolutePath();
	}

	private static void createCellTitle(HSSFWorkbook wb, HSSFRow row,
			int column, String value) {
		HSSFCell cell = row.createCell(column);
		//样式
		//HSSFCellStyle cellStyle = wb.createCellStyle();
		//cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// HSSFFont font = wb.createFont();
		// font.setFontHeightInPoints( (short) 11); ;
		// font.setFontName("宋体");
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// cellStyle.setFont(font);
		//cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

}
