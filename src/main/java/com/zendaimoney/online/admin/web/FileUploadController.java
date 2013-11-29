package com.zendaimoney.online.admin.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;

/**
 * 后台管理，内容管理里文本编辑器ckeditor图片上传功能
 * 
 * @author JiHui
 * 
 *         add 2012-11-28
 * **/
@Controller
@RequestMapping(value = "/admin/site/")
public class FileUploadController {
	private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	/** ~~~ 上传文件的最大文件大小 */
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 4;

	@RequestMapping(value = "uploadImage")
	public void uploadImage(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		// 判断提交的请求是否包含文件
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart) {
			return;
		}

		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			// 上传文件的返回地址
			String fileUrl = "";

			DiskFileItemFactory factory = new DiskFileItemFactory();

			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			// 设置单个文件上传的最大值
			fileUpload.setFileSizeMax(MAX_FILE_SIZE);

			@SuppressWarnings("unchecked")
			List<FileItem> fileItem = fileUpload.parseRequest(request);

			if (null == fileItem || 0 == fileItem.size()) {
				return;
			}

			Iterator<FileItem> fileitemIndex = fileItem.iterator();
			if (fileitemIndex.hasNext()) {
				FileItem file = fileitemIndex.next();

				if (file.isFormField()) {
					logger.error("上传文件非法！isFormField=true");
				}
				// 获取文件名
				String fileName = DateUtil.getStrDate2(new Date());
				String fileClientName = getFileName(file.getName());
				// 获取文件属性
				String fileFix = StringUtils.substring(fileClientName, fileClientName.lastIndexOf(".") + 1);
				if (!StringUtils.equalsIgnoreCase(fileFix, "jpg") && !StringUtils.equalsIgnoreCase(fileFix, "jpeg") && !StringUtils.equalsIgnoreCase(fileFix, "bmp") && !StringUtils.equalsIgnoreCase(fileFix, "gif") && !StringUtils.equalsIgnoreCase(fileFix, "png")) {
					logger.error("上传文件的格式错误=" + fileFix);
					return;
				}

				if (logger.isInfoEnabled()) {
					logger.info("开始上传文件:" + file.getName());
				}
				// 这里的路径有可能变化
				String path = getFilePath() + "contentNotice";

				// 文件夹不存在就自动创建：
				File filea = new File(path);
				if (!(filea.exists()) && !(filea.isDirectory())) {
					filea.mkdirs();
					logger.info("=============创建文件夹=============");
				}

				// 为了客户端已经设置好了图片名称在服务器继续能够明确识别，这里不改名称
				File newFile = new File(path, fileName + '.' + fileFix);
				file.write(newFile);

				if (logger.isInfoEnabled()) {
					logger.info("上传文件结束，新名称:" + fileClientName + ".floder:" + newFile.getPath());
				}

				fileUrl = getFilePath() + newFile.getName();
				fileUrl = StringUtils.replace(fileUrl, "//", "/");

				// 将上传的图片的url返回给ckeditor
				out.println("<script language='javascript'>");
				// ie
				out.println("if (window.navigator.userAgent.indexOf('MSIE') >= 1) {");
				out.println("parent.document.getElementById('cke_105_textInput').value = '" + "/pic/contentNotice/" + newFile.getName() + "'");
				out.println("parent.document.getElementById('cke_101_previewImage').src = '" + "/pic/contentNotice/" + newFile.getName() + "'");
				out.println("parent.document.getElementById('cke_101_previewImage').style.display = ''");
				// firefox
				out.println("}else if (window.navigator.userAgent.indexOf('Firefox') >= 1) {");
				out.println("parent.document.getElementById('cke_107_textInput').value = '" + "/pic/contentNotice/" + newFile.getName() + "'");
				out.println("parent.document.getElementById('cke_103_previewImage').src = '" + "/pic/contentNotice/" + newFile.getName() + "'");
				out.println("parent.document.getElementById('cke_103_previewImage').style.display = ''");
				out.println("}");
				out.println("</script>");
			}
			out.flush();
			out.close();

		} catch (IOException e) {
			logger.error("上传文件发生异常！", e);
		} catch (FileUploadException e) {
			logger.error("上传文件发生异常！", e);
		} catch (Exception e) {
			logger.error("上传文件发生异常！", e);
		}

		return;
	}

	/**
	 * 获取文件名称
	 * 
	 * @param str
	 * @return
	 */
	private String getFileName(String str) {
		int index = str.lastIndexOf("//");
		if (-1 != index) {
			return str.substring(index);
		} else {
			return str;
		}
	}

	private static String getFilePath() {
		Properties props = new Properties();
		try {
			InputStream in = InfoApproveManager.class.getResourceAsStream("/filePath.properties");
			props.load(in);
			in.close();
			String value = props.getProperty("iconPath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
