package com.zendaimoney.online.common;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zendaimoney.online.service.borrowing.InfoApproveManager;
/**
 * 图片上传工具类
 * @author HuYaHui
 *
 */
public class FileUploadUtil {
	public static final int CACHEFILEMAXSIZ=1024*100;//超过100K的文件缓存到硬盘
	public static final Double FILEMAXSIZ_1_5M=1024l*1024l*1.5;//1M
	public static final Double FILEMAXSIZ_100K=1024*100D;//100K
	private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	//上传身份证的form id
	public static final String UPDATAIDCARDFORMID="idform1";
	//工作认证form id
	public static final String UPDATAWROKINFOFORMID="workForm";
	//自行上传信用报告信息
	public static final String UPDATACREDITREPZXFORMID="updataCreditRepZx";
	// 上传月收入
	public static final String UPDATAREVENUEFORMID="revenue";
	// 房产认证
	public static final String UPDATAHOUSEPROPERTYFORMID="house";
	// 汽车认证
	public static final String UPDATAHAVECARFORMID="car";
	// 结婚认证
	public static final String UPDATAMARRYAPPROVEFORMID="marry";
	// 居住地认证
	public static final String UPDATALIVEADDRESSAPPROVEFORMID="address";
	// 职称认证 
	public static final String UPDATAJOBTITLEAPPROVEFORMID="jobTitle";
	// 手机认证
	public static final String UPDATAPHONEAPPROVEFORMID="PhoneApprove";

	/**
	 * 从文件路径中截取最后一个/或者\获取文件名
	 * 2012-12-5 下午1:32:37 by HuYaHui
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath){
		int index=filePath.lastIndexOf("\\");
		if(index==-1){
			index=filePath.lastIndexOf("/");
			if(index==-1){
				return filePath;
			}
		}
		return filePath.substring(index+1);
	}
	
	/**
	 * 返回json格式
	 * 2012-12-6 上午9:12:05 by HuYaHui
	 * @param flag
	 * 			true|false(必须有值)
	 * @param formId
	 * 			formId(必须有值)
	 * @param fileList
	 * 			文件列表(可以为空)
	 * @return
	 */
	public static String returnJsonFormat(String flag,String formId,List<String> fileList){
		JSONObject object = new JSONObject();  
        object.put("result", flag);  
        object.put("formId", formId);
        if(fileList!=null && fileList.size()>0){
        	object.put("fileList", fileList);
        }
		return object.toString();
	}
	
	/**
	 * 验证文件大小
	 * 2012-11-22 上午11:31:14 by HuYaHui
	 * @param items
	 * @return
	 */
	public static String checkFileSize(List<FileItem> items){
		String flg = "true";
		//验证文件必须大于0字节
		for (FileItem item : items) {
			//验证文件必须大于0字节
			if (item.isFormField() == false) {
				long size = item.getSize();
				if (size == 0) {
					flg = "fileSizeIsNull";
					return flg;
				}
			}
		}
		return flg;
	}
	/**
	 * 上传图片到服务器
	 * 2012-11-22 上午9:53:26 by HuYaHui
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<FileItem> uploadFile(HttpServletRequest request,Long picMaxSize) throws Exception{
		long start=System.currentTimeMillis();
		//2.上传文件
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(CACHEFILEMAXSIZ);// 超过100K的数据采用临时文件缓存
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//超过1.5M的文件用临时目录保存，不在内存中缓存(按照日期建立临时目录，防止未删除文件占用硬盘资源)
		File tmpFile=new File(getFilePath()+"tmp/"+sdf.format(new Date())+"/");
		if (!(tmpFile.exists()) && !(tmpFile.isDirectory())) {
			//创建目录
			tmpFile.mkdirs();
			
			//每次在创建目录的时候删除前一天的临时目录
			Date yesterdayDate=new Date();
			yesterdayDate.setDate(yesterdayDate.getDate()-1);
			String yesterday=sdf.format(yesterdayDate);
			File file=new File(getFilePath()+"tmp/"+yesterday);
			file.delete();
		}
		factory.setRepository(tmpFile);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(picMaxSize);
		// 设置整个request的最大值
		//upload.setSizeMax(MAX_LENGTH);
		logger.info("=============上传前耗时：" + (System.currentTimeMillis() - start) + "=============");
		long start1=System.currentTimeMillis();
		//上传文件到服务器
		List<FileItem> items = upload.parseRequest(request);
		logger.info("=============上传文件总耗时：" + (System.currentTimeMillis() - start1) + "=============");
		return items;
	}
	
	private static String getFilePath() {
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = InfoApproveManager.class.getResourceAsStream("/filePath.properties");
			props.load(in);
			in.close();
			String value = props.getProperty("filePath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
