package com.zendaimoney.online.admin.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
  
/** 
 *  
 * @author geloin 
 * @date 2012-5-5 下午12:05:57 
 */  
public class FileOperateUtil {  
    private static final String REALNAME = "realName";  
    private static final String STORENAME = "storeName";  
    private static final String SIZE = "size";  
    private static final String SUFFIX = "suffix";  
    private static final String CONTENTTYPE = "contentType";  
    private static final String CREATETIME = "createTime";  
    private static final String UPLOADDIR = "uploadDir/";  

    /** 
     * 将上传的文件进行重命名 
     *  
     * @author geloin 
     * @date 2012-3-29 下午3:39:53 
     * @param name 
     * @return 
     */  
    private static String rename(String name) {  
  
        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss")  
                .format(new Date()));  
        Long random = (long) (Math.random() * now);  
        String fileName = now + "" + random;  
  
        if (name.indexOf(".") != -1) {  
            fileName += name.substring(name.lastIndexOf("."));  
        }  
  
        return fileName;  
    }  
  
    /** 
     * 压缩后的文件名 
     *  
     * @author geloin 
     * @date 2012-3-29 下午6:21:32 
     * @param name 
     * @return 
     */  
    private static String zipName(String name) {  
        String prefix = "";  
        if (name.indexOf(".") != -1) {  
            prefix = name.substring(0, name.lastIndexOf("."));  
        } else {  
            prefix = name;  
        }  
        return prefix + ".zip";  
    }  
  
    /**
     *  
     * @author 王腾飞
     * @date 2012-12-11 上午10:21:54
     * @param request
     * @param params
     * @param values
     * @return
     * @throws Exception
     * description:上传文件
     */
    public static List<Map<String, Object>> upload(HttpServletRequest request,  
            String[] params, Map<String, Object[]> values) throws Exception {  
  
//        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();  
//  
//        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;  
//        Map<String, MultipartFile> fileMap = mRequest.getFileMap();  
//  
//        String uploadDir = request.getSession().getServletContext()  
//                .getRealPath("/")  
//                + FileOperateUtil.UPLOADDIR;  
//        File file = new File(uploadDir);  
//  
//        if (!file.exists()) {  
//            file.mkdir();  
//        }  
//  
//        String fileName = null;  
//        int i = 0;  
//        for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()  
//                .iterator(); it.hasNext(); i++) {  
//  
//            Map.Entry<String, MultipartFile> entry = it.next();  
//            MultipartFile mFile = entry.getValue();  
//  
//            fileName = mFile.getOriginalFilename();  
//  
//            String storeName = rename(fileName);  
//  
//            String noZipName = uploadDir + storeName;  
//            String zipName = zipName(noZipName);  
//  
//            // 上传成为压缩文件   
//            ZipOutputStream outputStream = new ZipOutputStream(  
//                    new BufferedOutputStream(new FileOutputStream(zipName)));  
//            outputStream.putNextEntry(new ZipEntry(fileName));  
//            outputStream.setEncoding("GBK");  
//  
//            FileCopyUtils.copy(mFile.getInputStream(), outputStream);  
//  
//            Map<String, Object> map = new HashMap<String, Object>();  
//            // 固定参数值对   
//            map.put(FileOperateUtil.REALNAME, zipName(fileName));  
//            map.put(FileOperateUtil.STORENAME, zipName(storeName));  
//            map.put(FileOperateUtil.SIZE, new File(zipName).length());  
//            map.put(FileOperateUtil.SUFFIX, "zip");  
//            map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");  
//            map.put(FileOperateUtil.CREATETIME, new Date());  
//  
//            // 自定义参数值对   
//            for (String param : params) {  
//                map.put(param, values.get(param)[i]);  
//            }  
//  
//            result.add(map);  
//        }  
//        return result;
    	return null;
    }  

    
    /**
     * 下载文件
     * 2013-3-22 下午12:37:44 by HuYaHui
     * @param request
     * @param response
     * @param downLoadPath
     * 			文件全路径
     * @throws Exception
     */
    public static void download(HttpServletRequest request,  
            HttpServletResponse response, 
            String downLoadPath) throws Exception {  
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;
        File file=new File(downLoadPath);
        long fileLength = file.length();  
        response.setContentType("application/msexcel;charset=UTF-8");  
        response.setHeader("Content-disposition", "attachment; filename="+encodeFilename(file.getName(),request));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }
        bis.close();
        bos.close();
        file.delete();
    }
 /**
  * 
  * @author 王腾飞
  * @date 2012-12-11 上午10:22:18
  * @param request
  * @param response
  * @param storeName
  * @param contentType
  * @param realName
  * @throws Exception
  * description:下载文件
  */
    public static void download(HttpServletRequest request,  
            HttpServletResponse response, String fileType,
            String fileName) throws Exception {  
        response.setContentType("text/html;charset=UTF-8");  
        request.setCharacterEncoding("UTF-8");  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;
        String filePath = FundsMigrate.getFilePath(fileType);
        String downLoadPath = filePath + fileName;  
  
        long fileLength = new File(downLoadPath).length();  
  
        response.setContentType("text/html;charset=UTF-8");  
        response.setHeader("Content-disposition", "attachment; filename="  
                +encodeFilename(fileName,request));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        bis.close();  
        bos.close();  
    }
    /**
     * 读取文件列表
     */
    public static File[] getFiles(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		return files;
	}
    public static List<File> sortFileByName(String strPath){
    	List<File> files = Arrays.asList(new File(strPath).listFiles());
    	Collections.sort(files, new Comparator<File>(){
    	    @Override
    	    public int compare(File o1, File o2) {
    		if(o1.isDirectory() && o2.isFile())
    		    return -1;
    		if(o1.isFile() && o2.isDirectory())
    	    	    return 1;
    		return o1.getName().compareTo(o2.getName());
    	    }
    	});
    	return files;
    }
    /**
     * 
     * @author 王腾飞
     * @date 2012-12-13 下午5:11:12
     * @param filename
     * @param request
     * @return
     * description:中文文件名
     */
    public  static String encodeFilename(String filename, HttpServletRequest request) {    
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
