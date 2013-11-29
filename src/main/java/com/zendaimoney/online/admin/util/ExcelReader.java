package com.zendaimoney.online.admin.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 操作Excel表格的功能类
 * 
 * @author：王腾飞
 * @version 1.0
 */
public class ExcelReader {
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	/**
	 * 读取excle
	 * @param is
	 */
	public ExcelReader(InputStream is){
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取sheet
	 * @param i
	 * @return
	 */
	public HSSFSheet getSheet(int i){
		return wb.getSheetAt(i);
	}
	/**
	 * 获取行
	 * @param sheet
	 * @param i
	 * @return
	 */
	public HSSFRow getRow(HSSFSheet sheet,int i){
		return sheet.getRow(i);
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	public String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		
		return strCell;
	}


}
