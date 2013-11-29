package com.zendaimoney.online.admin.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.ComponentScan;

import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.vo.FileVo;
import com.zendaimoney.online.admin.web.AuditController;

@ComponentScan
public class AuditUserTools {

	public static String getFilePath() {
		// String s1 =
		// InfoApproveManager.class.getClassLoader().getResource("").toString();
		// String s2 =
		// InfoApproveManager.class.getClassLoader().getResource("/").toString();

		// System.out.println("------------------------"+s1);
		// System.out.println("++++++++++++++++++++++++"+s2);
		// System.out.println("***********************"+filePath);
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = AuditUserTools.class.getResourceAsStream("/filePath.properties");
			;
			props.load(in);
			in.close();
			String value = props.getProperty("filePath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getCardPath(int cardType, AccountUsersAdmin aua) {
		String path = null;

		switch (cardType) {
		// 身份证
		case 1:
			path = getFilePath() + aua.getEmail() + "/idCardCertificate";
			break;
		case 2:
			path = getFilePath() + aua.getEmail() + "/userWorkImg";
			break;
		case 3:
			path = getFilePath() + aua.getEmail() + "/userInComImg";
			break;
		case 4:
			path = getFilePath() + aua.getEmail() + "/userCreditRepZxImg";
			break;
		case 5:
			path = getFilePath() + aua.getEmail() + "/houseProperty";
			break;
		case 6:
			path = getFilePath() + aua.getEmail() + "/jobTitle";
			break;
		case 7:
			path = getFilePath() + aua.getEmail() + "/haveCar";
			break;
		case 8:
			path = getFilePath() + aua.getEmail() + "/approveMarry";
			break;
		case 9:
			path = getFilePath() + aua.getEmail() + "/liveAddress";
			break;
		case 10:
			path = getFilePath() + aua.getEmail() + "/idCardCertificate";
			break;
		case 11:
			path = getFilePath() + aua.getEmail() + "/idCardCertificate";
			break;
		case 12:
			path = getFilePath() + aua.getEmail() + "/idCardCertificate";
			break;
		case 13:
			path = getFilePath() + aua.getEmail() + "/phoneApprove";
			break;
		case 14:
			path = getFilePath() + aua.getEmail() + "/idCardCertificate";
			break;
		}
		path = getFilesName(path);
		return path;
	}

	// 获取图片上传时间
	private static String getFileCreateTime(String filePath) {
		String file_dt = AuditController.cache_file_map.get(filePath).getFileTime();

		if (file_dt == null) {
			return "2012-12-01 09:00:00";
		}
		return file_dt;
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-2-28 上午9:56:49
	 * @param cardType
	 * @param email
	 * @return description:获取图片路径，组成json数组返回
	 */
	public static String getF(int cardType, String email) {

		String path = null;
		JSONArray jsonAry = new JSONArray();
		for (int i = 1; i <= 14; i++) {
			switch (i) {
			// 身份证
			case 1:
				path = getFilePath() + email + "/idCardCertificate";
				break;
			case 2:
				path = getFilePath() + email + "/userWorkImg";
				break;
			case 3:
				path = getFilePath() + email + "/userInComImg";
				break;
			case 4:
				path = getFilePath() + email + "/userCreditRepZxImg";
				break;
			case 5:
				path = getFilePath() + email + "/houseProperty";
				break;
			case 6:
				path = getFilePath() + email + "/jobTitle";
				break;
			case 7:
				path = getFilePath() + email + "/haveCar";
				break;
			case 8:
				path = getFilePath() + email + "/approveMarry";
				break;
			case 9:
				path = getFilePath() + email + "/liveAddress";
				break;
			case 10:
				path = getFilePath() + email + "/view";
				break;
			case 11:
				path = getFilePath() + email + "/field";
				break;
			case 12:
				path = getFilePath() + email + "/educate";
				break;
			case 13:
				path = getFilePath() + email + "/phoneApprove";
				break;
			case 14:
				path = getFilePath() + email + "/weibo";
				break;
			}
			String jsonS = getPic(i, path);
			if (!jsonS.equals("")) {
				JSONObject json = new JSONObject();
				json.put("id", i);
				json.put("text", getTypeName(i));
				json.put("children", jsonS);
				jsonAry.add(json);
			}
		}
		return jsonAry.toString();
	}

	public static String getFilesName(String dicpath) {
		StringBuilder sb = new StringBuilder(10);
		try {
			File directory = new File(dicpath);
			if (directory == null || !directory.exists()) {
				return "";
			}
			File[] files = directory.listFiles();
			List<FileVo> fileList = new ArrayList<FileVo>();
			for (File f : files) {
				FileVo fileV = new FileVo();
				fileV.setFileName(f.getName());
				// 数据库有图片的，user_approve_id为空，已经删除的，已经上传的不显示到后台
				if (AuditController.cache_file_map.get(f.getAbsolutePath()) != null) {
					String userAppID = AuditController.cache_file_map.get(f.getAbsolutePath()).getUserApproveId();
					String idDel = AuditController.cache_file_map.get(f.getAbsolutePath()).getIsDel();
					String status = AuditController.cache_file_map.get(f.getAbsolutePath()).getStatus();
					String fileTime = getFileCreateTime(f.getAbsolutePath());
					fileV.setFileTime(fileTime);
					fileList.add(fileV);
					if (userAppID.equals("empty") || idDel.equals("1") || status.equals("1")) {
						fileList.remove((fileList.size() - 1));
					}
				} else {
					// 数据库没有的，但是服务器里有图片的
					fileV.setFileTime("2012-12-01 09:00:00");
					fileList.add(fileV);
				}

			}
			// 按图片上传时间倒序排序
			Collections.sort(fileList, new Comparator<FileVo>() {
				public int compare(FileVo b1, FileVo b2) {
					return b2.getFileTime().compareTo(b1.getFileTime());
				}
			});
			for (FileVo filev : fileList) {
				sb.append(filev.getFileName());
				sb.append(";");
				sb.append(filev.getFileTime());
				sb.append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return sb.length() > 1 ? sb.toString().substring(0, sb.length() - 1) : "";
	}

	public static String getPic(int cardType, String dicpath) {
		JSONArray jsonAry = new JSONArray();
		try {
			File directory = new File(dicpath);
			if (directory == null || !directory.exists()) {
				return "";
			}
			File[] files = directory.listFiles();
			List<FileVo> fileList = new ArrayList<FileVo>();
			for (File f : files) {
				FileVo fileV = new FileVo();
				fileV.setFileName(f.getName());
				// 数据库有图片的，user_approve_id为空，已经删除的，已经上传的不显示到后台
				if (AuditController.cache_file_map.get(f.getAbsolutePath()) != null) {
					String userAppID = AuditController.cache_file_map.get(f.getAbsolutePath()).getUserApproveId();
					String idDel = AuditController.cache_file_map.get(f.getAbsolutePath()).getIsDel();
					String status = AuditController.cache_file_map.get(f.getAbsolutePath()).getStatus();
					String fileTime = getFileCreateTime(f.getAbsolutePath());
					fileV.setFileTime(fileTime);
					fileList.add(fileV);
					if (userAppID.equals("empty") || idDel.equals("1") || status.equals("1")) {
						fileList.remove((fileList.size() - 1));
					}
				} else {
					// 数据库没有的，但是服务器里有图片的
					fileV.setFileTime("2012-12-01 09:00:00");
					fileList.add(fileV);
				}

			}
			// 按图片上传时间倒序排序
			Collections.sort(fileList, new Comparator<FileVo>() {
				public int compare(FileVo b1, FileVo b2) {
					return b2.getFileTime().compareTo(b1.getFileTime());
				}
			});

			int i = getFilePath().length();
			int cou = 0;
			for (FileVo filev : fileList) {
				cou++;
				JSONObject json = new JSONObject();
				json.put("id", filev.getFileName());
				String name = getTypeName(cardType);
				json.put("text", getTypeName(cardType) + cou);
				JSONObject jsons = new JSONObject();
				jsons.put("src", "/pic1/" + dicpath.substring(i) + "/" + filev.getFileName());
				json.put("attributes", jsons);
				jsonAry.add(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return jsonAry.toString();
	}

	public static String getTypeName(int cardType) {
		Map<Integer, String> fieldMapping = new HashMap<Integer, String>();
		fieldMapping.put(1, "身份认证");
		fieldMapping.put(2, "工作认证");
		fieldMapping.put(3, "收入认证");
		fieldMapping.put(4, "信用报告认证");
		fieldMapping.put(5, "房产认证");
		fieldMapping.put(6, "技术职称认证");
		fieldMapping.put(7, "购车认证");
		fieldMapping.put(8, "结婚认证");
		fieldMapping.put(9, "居住地认证");
		fieldMapping.put(10, "视频认证");
		fieldMapping.put(11, "实地考察认证");
		fieldMapping.put(12, "学历认证");
		fieldMapping.put(13, "手机实名认证");
		fieldMapping.put(14, "微博认证");
		return fieldMapping.get(cardType);
	}
}
