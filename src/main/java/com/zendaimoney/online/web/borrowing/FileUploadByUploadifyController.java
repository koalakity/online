package com.zendaimoney.online.web.borrowing;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.common.Constants;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.FileUploadUtil;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.upload.FileUploadVO;
import com.zendaimoney.online.service.upload.FileUploadManager;

/**
 * 
 进入到图片上传页面(参数:用户ID，认证类型) 取消上传 删除已经上传的图片 提交上传
 * 保存已经上传的图片地址到数据库，必须关联用户和认证类型save(obj)
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/upload/")
public class FileUploadByUploadifyController {

	private static Logger logger = LoggerFactory.getLogger(FileUploadByUploadifyController.class);

	@Autowired
	private FileUploadManager fileUploadManager;

	private static Map<String, String> typeToPath = new HashMap<String, String>();
	static {
		// //上传身份证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_SFZ, Constants.FILEUPLOAD_PATH_SFZ);
		// //工作认证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_GZRZ, Constants.FILEUPLOAD_PATH_GZRZ);
		// //信用报告信息
		typeToPath.put(Constants.FILEUPLOAD_TYPE_XYBG, Constants.FILEUPLOAD_PATH_XYBG);
		// // 上传月收入
		typeToPath.put(Constants.FILEUPLOAD_TYPE_YSR, Constants.FILEUPLOAD_PATH_YSR);
		// // 房产认证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_FCRZ, Constants.FILEUPLOAD_PATH_FCRZ);
		// // 汽车认证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_QCRZ, Constants.FILEUPLOAD_PATH_QCRZ);
		// // 结婚认证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_JHRZ, Constants.FILEUPLOAD_PATH_JHRZ);
		// // 居住地认证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_JZDRZ, Constants.FILEUPLOAD_PATH_JZDRZ);
		// // 职称认证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_JCRZ, Constants.FILEUPLOAD_PATH_JCRZ);
		// // 手机认证
		typeToPath.put(Constants.FILEUPLOAD_TYPE_SJRZ, Constants.FILEUPLOAD_PATH_SJRZ);
		//
	}

	/**
	 * 上传图片 2012-12-18 下午1:26:21 by HuYaHui
	 * 
	 * @param request
	 *            type 类型，不能为空 remark 备注，可以为空
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "fileUpload")
	public String fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rtnInfo = "true";
		JSONObject object = new JSONObject();

		try {
			/*
			 * 1.必须是登陆用户 2.根据用户ID查询用户(必须在数据库中存在的用户)
			 */
			String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
			String[] param = userId.split(";");
			userId = param[0];
			String type = param[1].split("=")[1];
			if (userId.trim().equals("null")) {
				logger.info("用户未登录！userId==null ");
				throw new Exception("userIsNull");
			} else if (type.trim().equals("null")) {
				logger.info("类型为空！type==null");
				throw new Exception("typeIsNull");
			}
			BorrowingUsers user = fileUploadManager.getUsers(userId);
			if (user == null) {
				logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));
				// 用户未登录
				throw new Exception("userNotExists");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			File tmpFile = new File(getFilePath() + "tmp/" + sdf.format(new Date()) + "/");
			if (!(tmpFile.exists()) && !(tmpFile.isDirectory())) {
				// 如果目录不存在，删除上一天的文件记录
				logger.info("开始查询上一天已经上传未提交的文件记录集合");
				List<FileUploadVO> deleteList = fileUploadManager.findDirtFile();
				logger.info("查询上一天已经上传未提交的文件记录集合：" + (deleteList != null ? deleteList.size() : 0));
				if (deleteList != null && deleteList.size() > 0) {
					fileUploadManager.deleteDirtFile();
					for (FileUploadVO fileVO : deleteList) {
						new File(fileVO.getFilePath()).delete();
					}//
					logger.info("删除上一天文件记录总行数为：" + deleteList.size());
				}
			}

			// 上传文件
			List<FileItem> items = FileUploadUtil.uploadFile(request, FileUploadUtil.FILEMAXSIZ_1_5M.longValue());
			logger.info("获取上传文件信息：" + (items != null ? items.size() : "上传文件为空！"));

			/*
			 * 保存文件到数据库 进入到图片上传页面(参数:用户ID，认证类型) 提交上传
			 * 保存已经上传的图片地址到数据库，必须关联用户和认证类型save(obj)
			 */
			String filePath = getFilePath() + user.getEmail() + typeToPath.get(type);
			String remark = "";
			// 文件夹不存在就自动创建：
			File filea = new File(filePath);
			if (!(filea.exists()) && !(filea.isDirectory())) {
				filea.mkdirs();
			}
			for (FileItem item : items) {
				// 判读FileItem类对象封装的数据是否属于文件表单字段
				if (item.isFormField() == false) {
					String name = item.getName();// 获得文件上传字段中的文件名
					//验证文件后缀 开始
					String[] _names=name.split("\\.");
					//jpg、png、gif、bmp
					if(!_names[_names.length-1].trim().equalsIgnoreCase("jpg")
							&&!_names[_names.length-1].trim().equalsIgnoreCase("png")
							&&!_names[_names.length-1].trim().equalsIgnoreCase("gif")
							&&!_names[_names.length-1].trim().equalsIgnoreCase("bmp")){
						//不是图片后缀的文件
						throw new Exception("typeIsNotPic");
					}
					
					remark = name;
					if (item.getSize() == 0) {
						throw new Exception("zero");
					}
					String fileName = DateUtil.getStrDate2(new Date());
					int point = name.lastIndexOf(".");
					String hz = name.substring(point);
					File file = new File(filePath, fileName + UUID.randomUUID() + hz);
					item.write(file);// 直接保存文件
					item.delete();
					filePath = file.getAbsolutePath();
					break;
				}
			}
			FileUploadVO f = fileUploadManager.save(userId, type, filePath, remark);
			object.put("id", f.getId());
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			if (errorMsg != null && !errorMsg.trim().equals("")) {
				if (errorMsg.equals("userIsNull")) {
					rtnInfo = "userIsNull";
				} else if (errorMsg.equals("typeIsNull")) {
					rtnInfo = "typeIsNull";
				} else if (errorMsg.equals("userNotExists")) {
					rtnInfo = "userNotExists";
				} else if (errorMsg.equals("zero")) {
					rtnInfo = "zero";
				} else if (errorMsg.equals("typeIsNotPic")) {
					rtnInfo = "typeIsNotPic";
				} else {
					rtnInfo = "error";
				}
			} else {
				rtnInfo = "error";
			}
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		object.put("result", rtnInfo);

		// 如果不返回信息,成功后,页面不会隐藏当前文件的进度条
		response.getWriter().print(object.toString());
		return null;
	}

	/**
	 * 根据ID删除文件 2012-12-25 上午11:00:19 by HuYaHui
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteById")
	@ResponseBody
	public String deleteById(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		String userId = session.getAttribute("curr_login_user_id") == null ? "" : session.getAttribute("curr_login_user_id").toString();
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");

		BorrowingUsers user = fileUploadManager.getUsers(userId);
		if (user == null) {
			logger.info("获取用户信息：" + (user != null ? user.getUserId() : "用户为空！"));
			// 用户未登录
			throw new Exception("userNotExists");
		}
		// 查询某用户，类型，未删除并且是当天的上传成功的文件,ID
		List<FileUploadVO> deleteFileList = fileUploadManager.findRecordByCondition(Long.valueOf(id), userId, type, null, Constants.FILEUPLOAD_ISDEL_WSC, null, DateUtil.getCurrentDate("yyyy-MM-dd"), null, null);
		if (deleteFileList != null && deleteFileList.size() > 0) {

			// 删除数据记录
			int count = fileUploadManager.delete(Long.valueOf(id), userId, type, DateUtil.getCurrentDate("yyyy-MM-dd"), Constants.FILEUPLOAD_ISDEL_WSC);
			if (count > 0) {
				// 删除具体文件
				for (FileUploadVO fileVO : deleteFileList) {
					new File(fileVO.getFilePath()).delete();
				}
			}
		}
		return "true";
	}

	/**
	 * 获取配置文件的文件路径 2012-12-19 上午10:40:33 by HuYaHui
	 * 
	 * @return
	 */
	private String getFilePath() {
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = FileUploadByUploadifyController.class.getResourceAsStream("/filePath.properties");
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
