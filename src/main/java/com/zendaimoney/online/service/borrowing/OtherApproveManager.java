package com.zendaimoney.online.service.borrowing;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.Constants;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.FileUploadUtil;
import com.zendaimoney.online.dao.borrowing.BorrowingUserApproveDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserInfoPersonDao;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprovePro;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.upload.FileUploadVO;
import com.zendaimoney.online.oii.id5.common.Des2;
import com.zendaimoney.online.service.upload.FileUploadManager;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-18 下午2:36:16 operation by: description:非必要认证信息借
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class OtherApproveManager {
	private static Logger logger = LoggerFactory.getLogger(OtherApproveManager.class);
	@Autowired
	private BorrowingUserInfoPersonDao borrowinguserInfoPersonDao;
	@Autowired
	private BorrowingUserApproveDao borrowingUserApproveDao;

	@Autowired
	private FileUploadManager fileUploadManager;

	/**
	 * 抽取公共的处理流程 2012-11-23 下午2:33:46 by HuYaHui
	 * 
	 * @param items
	 * @param users
	 * @param filePath
	 * @param proId
	 * @return
	 * @throws Exception
	 *             TODO 文件上传重构，待删除
	 */
	@Deprecated
	@Transactional(readOnly = false)
	public List<String> updataCommonExcute(List<FileItem> items, BorrowingUsers users, String filePath, BigDecimal proId) throws Exception {
		logger.info("进入manager filePath：" + filePath + " proid:" + proId);
		List<String> rtnFileList = new ArrayList<String>();
		BigDecimal userId = users.getUserId();
		String userEmail = users.getEmail();
		BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);
		String path = getFilePath() + userEmail + filePath;
		logger.info("文件路径path：" + path);
		File filea = new File(path);
		if (!(filea.exists()) && !(filea.isDirectory())) {
			filea.mkdirs();
		}
		int index = 0;
		for (FileItem item : items) {
			if (item.isFormField() == false) {
				// IE可以获取文件全路径：C:\Documents and
				// Settings\Administrator\桌面\新建文件夹\100k-.jpg
				// 火狐只能获取文件名100k-.jpg
				String name = item.getName();
				int point = name.lastIndexOf(".");
				String hz = name.substring(point);
				String fileName = DateUtil.getStrDate2(new Date());
				File file = new File(path, fileName + index + hz);
				index++;
				item.write(file);// 直接保存文件
				rtnFileList.add(FileUploadUtil.getFileName(name));
				logger.info("=============保存认证附件！=============");
			} else {
				// 把界面输入框的name属性对应的值保存到userInfoPerson中
				userInfoPerson = setValueFromItem(userInfoPerson, item);
			}
		}
		logger.info("=============保存userInfoPerson表！=============");
		borrowinguserInfoPersonDao.save(userInfoPerson);
		saveUserApprove(userId, proId);
		return rtnFileList;
	}

	/**
	 * 抽取公共的处理流程 2012-11-23 下午2:33:46 by HuYaHui
	 * 
	 * @param items
	 * @param users
	 * @param filePath
	 * @param proId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public String updataCommonExcute(BorrowingUserInfoPerson paramObj, BigDecimal userId, String proId) throws Exception {
		logger.info("进入manager proid:" + proId);
		List<FileUploadVO> fileList = fileUploadManager.findRecordByCondition(userId + "", proId, null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YSC, DateUtil.getCurrentDate("yyyy-MM-dd"), null, null);
		if (fileList == null || fileList.size() == 0) {
			return "noUpFile";
		}
		BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);
		userInfoPerson = mergerObject(userInfoPerson, paramObj);
		logger.info("=============保存userInfoPerson表！=============");
		borrowinguserInfoPersonDao.save(userInfoPerson);
		BorrowingUserApprove saveObj = saveUserApprove(userId, new BigDecimal(proId));
		/*
		 * 1删除当天,某用户，类型，已上传，未关联，已删除的记录，和文件 TODO 2修改 where
		 * 当天,某用户，类型，已上传，未关联，未删除的记录， set 关联字段，status=已提交
		 * 
		 * int deleteCount=fileUploadManager.delete(userId+"", proId,
		 * DateUtil.getCurrentDate("yyyy-MM-dd"),
		 * Constants.FILEUPLOAD_ISDEL_YSC); logger.info("删除数据总行数:" +
		 * deleteCount);
		 */
		FileUploadVO whereObj = new FileUploadVO();
		whereObj.setUploadDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
		whereObj.setUserId(userId + "");
		whereObj.setType(proId);
		whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
		whereObj.setIsDel(Constants.FILEUPLOAD_ISDEL_WSC);

		FileUploadVO setObj = new FileUploadVO();
		setObj.setStatus(Constants.FILEUPLOAD_STATUS_YTJ);
		BorrowingUserApprove fileRefUserObj = new BorrowingUserApprove();
		fileRefUserObj.setUserApproveId(saveObj.getUserApproveId());
		setObj.setUserApproveId(fileRefUserObj);
		fileUploadManager.update(whereObj, setObj);
		return "true";
	}

	/**
	 * 合并俩个对象(把startObj对象里面非空的属性，替换endObj对应的属性) 2012-12-24 上午10:35:26 by HuYaHui
	 * 
	 * @param endObj
	 *            (到这里) 目标对象
	 * @param T
	 *            startObj(从这里) 数据源对象
	 * @return
	 * @throws Exception
	 */
	private static <T> T mergerObject(T endObj, T startObj) throws Exception {
		Field[] fields = startObj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.equals("serialVersionUID")) {
				continue;
			}
			String value = BeanUtils.getProperty(startObj, fieldName);
			if (null != value && !value.equals("")) {
				BeanUtils.setProperty(endObj, fieldName, value);
			}
		}
		return endObj;
	}

	public static void main(String[] args) {

	}

	@Transactional(readOnly = false)
	private BorrowingUserApprove saveUserApprove(BigDecimal userId, BigDecimal proId) {
		BorrowingUserApprovePro upro = new BorrowingUserApprovePro();
		upro.setProId(proId);
		BorrowingUserApprove userApprove = null;
		List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, upro);
		if (list.size() != 0) {
			userApprove = list.get(0);
			userApprove.setUpdateTime(new Date());
		} else {
			userApprove = new BorrowingUserApprove();
			userApprove.setUserId(userId);
			userApprove.setProId(upro);
			userApprove.setProStatus(BigDecimal.ONE);
			userApprove.setReviewStatus(BigDecimal.ZERO);
			Date curDate = new Date();
			userApprove.setCreateTime(curDate);
			userApprove.setUpdateTime(curDate);
		}
		userApprove.setReviewStatus(BigDecimal.valueOf(2));
		borrowingUserApproveDao.save(userApprove);
		return userApprove;
	}

	/*
	 * 2012-12-6修改，由updataCommonExcute方法替代 // 汽车认证
	 * 
	 * @Deprecated
	 * 
	 * @Transactional(readOnly = false) public String
	 * updataHaveCar(List<FileItem> items, BorrowingUsers users) throws
	 * Exception { return updataCommonExcute(items, users, "/haveCar",
	 * BigDecimal.valueOf(7)); }
	 * 
	 * // 结婚认证 updataMarryApprove
	 * 
	 * @Deprecated
	 * 
	 * @Transactional(readOnly = false) public String
	 * updataMarryApprove(List<FileItem> items, BorrowingUsers users) throws
	 * Exception { return updataCommonExcute(items, users, "/approveMarry",
	 * BigDecimal.valueOf(8)); }
	 * 
	 * // 居住地认证
	 * 
	 * @Deprecated
	 * 
	 * @Transactional(readOnly = false) public String
	 * updataLiveAddressApprove(List<FileItem> items, BorrowingUsers users)
	 * throws Exception { return updataCommonExcute(items, users,
	 * "/liveAddress", BigDecimal.valueOf(9)); }
	 * 
	 * // 职称认证
	 * 
	 * @Deprecated
	 * 
	 * @Transactional(readOnly = false) public String
	 * updatajobTitleApprove(List<FileItem> items, BorrowingUsers users) throws
	 * Exception { return updataCommonExcute(items, users, "/jobTitle",
	 * BigDecimal.valueOf(6)); }
	 * 
	 * // 手机认证
	 * 
	 * @Transactional(readOnly = false) public String
	 * updataPhoneApprove(List<FileItem> items, BorrowingUsers users) throws
	 * Exception { return updataCommonExcute(items, users, "/phoneApprove",
	 * BigDecimal.valueOf(13)); }
	 */
	// 学历认证
	@Transactional(readOnly = false)
	public String updataDegeeApprove(String degreeNo, String maxDegree, String graduatSchool, String inschoolTime, String degreeToken, HttpServletRequest request, BorrowingUsers users) {
		String flg = "true";
		BigDecimal userId = users.getUserId();
		BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);

		String sessionToken = (String) request.getSession().getAttribute("token");
		// session令牌解密
		sessionToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", sessionToken);
		degreeToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", degreeToken);
		if (sessionToken.equals(degreeToken)) {
			userInfoPerson.setDegreeNo(degreeNo);
			userInfoPerson.setMaxDegree(new BigDecimal(maxDegree));
			userInfoPerson.setGraduatSchool(graduatSchool);
			userInfoPerson.setInschoolTime(inschoolTime);
			userInfoPerson.getUser().setInclosureSubmitTime(new Date());
			borrowinguserInfoPersonDao.save(userInfoPerson);

			BorrowingUserApprovePro uap = new BorrowingUserApprovePro();
			uap.setProId(BigDecimal.valueOf(12));
			BorrowingUserApprove userApprove = null;
			List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, uap);
			if (list.size() != 0) {
				userApprove = list.get(0);
				userApprove.setUpdateTime(new Date());
			} else {
				userApprove = new BorrowingUserApprove();
				Date curDate = new Date();
				userApprove.setCreateTime(curDate);
				userApprove.setUpdateTime(curDate);
			}
			userApprove.setUserId(userId);
			userApprove.setProId(uap);
			userApprove.setProStatus(BigDecimal.ONE);
			userApprove.setReviewStatus(new BigDecimal("2"));
			borrowingUserApproveDao.save(userApprove);
			flg = "true";
			return flg;
		} else {
			flg = "false";
			return flg;
		}
	}

	// 视频认证
	@Transactional(readOnly = false)
	public String updataVideoApprove(String videoApprove, String VideoToken, HttpServletRequest request, BorrowingUsers users) {
		String flg = "true";

		BigDecimal userId = users.getUserId();
		BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);

		String sessionToken = (String) request.getSession().getAttribute("token");
		// session令牌解密
		sessionToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", sessionToken);
		VideoToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", VideoToken);
		if (sessionToken.equals(VideoToken)) {
			userInfoPerson.setVideoApprove(new BigDecimal(videoApprove));
			userInfoPerson.getUser().setInclosureSubmitTime(new Date());
			borrowinguserInfoPersonDao.save(userInfoPerson);
			BorrowingUserApprove userApprove = null;
			BorrowingUserApprovePro uap = new BorrowingUserApprovePro();
			uap.setProId(BigDecimal.valueOf(10));

			List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, uap);
			if (list.size() != 0) {
				userApprove = list.get(0);
				userApprove.setUpdateTime(new Date());
			} else {
				userApprove = new BorrowingUserApprove();
				Date curDate = new Date();
				userApprove.setCreateTime(curDate);
				userApprove.setUpdateTime(curDate);
			}
			userApprove.setUserId(userId);
			userApprove.setProId(uap);
			userApprove.setProStatus(BigDecimal.ONE);
			userApprove.setReviewStatus(BigDecimal.valueOf(2));
			borrowingUserApproveDao.save(userApprove);
			flg = "true";
			return flg;
		} else {
			flg = "false";
			return flg;
		}
	}

	// 新浪微博认证
	@Transactional(readOnly = false)
	public String updataWeiBoApprove(String updataWeiBoApprove, String sinaWeiboToken, HttpServletRequest request, BorrowingUsers users) {
		String flg = "true";
		BigDecimal userId = users.getUserId();
		BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);

		String sessionToken = (String) request.getSession().getAttribute("token");
		// session令牌解密
		sessionToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", sessionToken);
		sinaWeiboToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", sinaWeiboToken);
		if (sessionToken.equals(sinaWeiboToken)) {
			userInfoPerson.setSinaWeiboAccount(updataWeiBoApprove);
			userInfoPerson.getUser().setInclosureSubmitTime(new Date());
			borrowinguserInfoPersonDao.save(userInfoPerson);
			BorrowingUserApprove userApprove = null;

			BorrowingUserApprovePro uap = new BorrowingUserApprovePro();
			uap.setProId(BigDecimal.valueOf(14));

			List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, uap);
			if (list.size() != 0) {
				userApprove = list.get(0);
				userApprove.setUpdateTime(new Date());
			} else {
				userApprove = new BorrowingUserApprove();
				Date curDate = new Date();
				userApprove.setCreateTime(curDate);
				userApprove.setUpdateTime(curDate);
			}
			userApprove.setUserId(userId);
			userApprove.setProId(uap);
			userApprove.setProStatus(BigDecimal.ONE);
			userApprove.setReviewStatus(BigDecimal.valueOf(2));
			borrowingUserApproveDao.save(userApprove);
			flg = "true";
			return flg;
		} else {
			flg = "false";
			return flg;
		}
	}

	// 实地考察认证
	@Transactional(readOnly = false)
	public String updataRealInspectApprove(String realApproveName, String realApprovePhoneArea, String realApprovePhone, String realApproveCorporationAdd, String realApproveHomeAddress, String realApproveAssets, String realApproveOperateTime, String realApproveMonthWater, String realApproveApplyMoney, String realApproveToken, HttpServletRequest request, BorrowingUsers users) {
		String flg = "true";
		BigDecimal userId = users.getUserId();
		BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);

		String sessionToken = (String) request.getSession().getAttribute("token");
		// session令牌解密
		sessionToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", sessionToken);
		realApproveToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", realApproveToken);
		if (sessionToken.equals(realApproveToken)) {
			userInfoPerson.setRealApproveName(realApproveName);
			userInfoPerson.setRealApprovePhoneArea(realApprovePhoneArea);
			userInfoPerson.setRealApprovePhone(realApprovePhone);
			userInfoPerson.setRealApproveCorporationAdd(realApproveCorporationAdd);
			userInfoPerson.setRealApproveHomeAddress(realApproveHomeAddress);
			userInfoPerson.setRealApproveAssets(new BigDecimal(realApproveAssets));
			userInfoPerson.setRealApproveOperateTime(realApproveOperateTime);
			userInfoPerson.setRealApproveMonthWater(new BigDecimal(realApproveMonthWater));
			userInfoPerson.setRealApproveApplyMoney(new BigDecimal(realApproveApplyMoney));
			borrowinguserInfoPersonDao.save(userInfoPerson);

			BorrowingUserApprove userApprove = null;
			BorrowingUserApprovePro uap = new BorrowingUserApprovePro();
			uap.setProId(BigDecimal.valueOf(11));
			List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, uap);
			if (list.size() != 0) {
				userApprove = list.get(0);
				userApprove.setUpdateTime(new Date());
			} else {
				userApprove = new BorrowingUserApprove();
				Date curDate = new Date();
				userApprove.setCreateTime(curDate);
				userApprove.setUpdateTime(curDate);
			}
			userApprove.setUserId(userId);
			userApprove.setProId(uap);
			userApprove.setProStatus(BigDecimal.ONE);
			userApprove.setReviewStatus(BigDecimal.valueOf(2));
			borrowingUserApproveDao.save(userApprove);
			flg = "true";
			return flg;
		} else {
			flg = "false";
			return flg;
		}
	}

	private String getFilePath() {
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = OtherApproveManager.class.getResourceAsStream("/filePath.properties");
			props.load(in);
			in.close();
			String value = props.getProperty("filePath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据用户ID，查询BorrowingUserInfoPerson对象 2012-11-21 下午2:41:45 by HuYaHui
	 * 
	 * @param userid
	 *            用户ID
	 * @return
	 */
	private BorrowingUserInfoPerson findBorrowingUserInfoPersonByUserId(BigDecimal userid) {
		List<BorrowingUserInfoPerson> userInfoList = borrowinguserInfoPersonDao.findUserId(userid);
		// 用户详情信息
		BorrowingUserInfoPerson userInfoPerson = null;
		if (userInfoList.size() == 0) {
			userInfoPerson = new BorrowingUserInfoPerson();
			// 用户基本信息
			BorrowingUsers user = new BorrowingUsers();
			user.setUserId(userid);
			user.setInclosureSubmitTime(new Date());
			userInfoPerson.setUser(user);
		} else {
			userInfoPerson = userInfoList.get(0);
			userInfoPerson.getUser().setInclosureSubmitTime(new Date());
			/**
			 * Add By jihui 2012-12-07
			 * 当用户审核通过后，状态变为正常,资料审核状态变为终定，再上传非必要认证来获取信用额度
			 * ，则将用户状态再次改为：已进入审核，资料审核状态为待审
			 */
			BorrowingUsers user = userInfoPerson.getUser();
			List<BorrowingUserApprove> proList = borrowingUserApproveDao.findPro(userid);
			if (proList.size() == 4) {
				user.setUserStatus(BigDecimal.valueOf(4));
				user.setMaterialReviewStatus(BigDecimal.ZERO);
			}
		}
		return userInfoPerson;
	}

	/**
	 * 从items集合种获取文本信息，保存到对象中 2012-11-21 下午3:27:46 by HuYaHui
	 * 
	 * @param targetObj
	 *            要保存信息的对象
	 * @param items
	 *            数据来源集合
	 * @return 对象
	 * @throws Exception
	 */
	private static <T> T setValueFromItem(T targetObj, FileItem item) throws Exception {
		String fieldName = item.getFieldName();
		BufferedReader br = new BufferedReader(new InputStreamReader(item.getInputStream()));
		String value = br.readLine();
		if (value != null && !value.equals("")) {
			BeanUtils.setProperty(targetObj, fieldName, value);
		}
		br.close();
		return (T) targetObj;
	}
}
