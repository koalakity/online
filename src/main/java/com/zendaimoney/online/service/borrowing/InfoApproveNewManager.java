package com.zendaimoney.online.service.borrowing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.Constants;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.IdcardInfoExtractor;
import com.zendaimoney.online.common.IdcardValidator;
import com.zendaimoney.online.dao.borrowing.BorrowingUserApproveDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserCreditNoteDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserInfoJobDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserInfoPersonDao;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprovePro;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoJob;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.upload.FileUploadVO;
import com.zendaimoney.online.service.upload.FileUploadManager;

/**
 * 
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author jihui
 * @date: 2012-12-21 上午10:25:35 operation by: description:
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class InfoApproveNewManager {
	private static Logger logger = LoggerFactory.getLogger(InfoApproveNewManager.class);
	@Autowired
	private BorrowingUserDao borrowingUserDao;
	@Autowired
	private BorrowingUserInfoPersonDao borrowinguserInfoPersonDao;
	@Autowired
	private BorrowingUserInfoJobDao borrowingUserInfoJobDao;
	@Autowired
	BorrowingUserCreditNoteDao borrowingUserCreditNoteDao;
	@Autowired
	private BorrowingUserApproveDao borrowingUserApproveDao;
	@Autowired
	private FileUploadManager fileUploadManager;

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-20 上午11:11:35
	 * @param userInfo
	 * @param users
	 *            description:上传用户身份证信息
	 */
	@Transactional(readOnly = false)
	public String updataIndenty(BorrowingUserInfoPerson userInfo, BorrowingUsers users) {
		BigDecimal userId = users.getUserId();
		Date date = new Date();
		String uploadDate = DateUtil.getYMDTime(date);
		BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);
		List<FileUploadVO> fileList = fileUploadManager.findRecordByCondition(
								userId + "", Constants.FILEUPLOAD_TYPE_SFZ, null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YSC, uploadDate, null, null);
		if (fileList.size() > 0) {
			userInfoPerson.setRealName(userInfo.getRealName());
			userInfoPerson.setPhoneNo(userInfo.getPhoneNo());
			String identityNo = userInfo.getIdentityNo();
			userInfoPerson.setIdentityNo(identityNo);
			IdcardValidator iv = new IdcardValidator();
			if (iv.isValidatedAllIdcard(identityNo)) {
				IdcardInfoExtractor ie = new IdcardInfoExtractor(identityNo);
				userInfoPerson.setSex(new BigDecimal("男".equals(ie.getGender()) ? "1" : "2"));
				userInfoPerson.setBirthday(ie.getBirthday());
			}
			logger.info("=============保存用户信息到userInfoPerson表！=============");
			borrowinguserInfoPersonDao.save(userInfoPerson);
			saveUserApprove(userId, BigDecimal.ONE);
			BorrowingUserApprove userApprove = searchApprove(userId, BigDecimal.ONE);
			FileUploadVO whereObj = new FileUploadVO();
			whereObj.setUserId(userId + "");
			whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
			FileUploadVO setObj = new FileUploadVO();
			setObj.setUserApproveId(userApprove);
			setObj.setStatus(Constants.FILEUPLOAD_STATUS_YTJ);
			fileUploadManager.update(whereObj, setObj);
			checkPro(userId);
			return "true";
		} else {
			return "noUpFile";
		}

	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-20 上午11:10:49
	 * @param jobInfo
	 * @param users
	 *            description:上传工作认证信息
	 */
	@Transactional(readOnly = false)
	public String updataWork(BorrowingUserInfoJob jobInfo, BorrowingUsers users) {
		BigDecimal userId = users.getUserId();
		List<BorrowingUserInfoJob> userJobInfoList = borrowingUserInfoJobDao.findByUserId(userId);
		BorrowingUserInfoJob userInfoJob = null;
		if (userJobInfoList.size() == 0) {
			BorrowingUsers user = new BorrowingUsers();
			user.setUserId(userId);
			userInfoJob = new BorrowingUserInfoJob();
			userInfoJob.setUser(user);
			user.setInclosureSubmitTime(new Date());
		} else {
			userInfoJob = userJobInfoList.get(0);
			userInfoJob.getUser().setInclosureSubmitTime(new Date());
		}
		Date date = new Date();
		String uploadDate = DateUtil.getYMDTime(date);
		List<FileUploadVO> fileList = fileUploadManager.findRecordByCondition(userId + "", Constants.FILEUPLOAD_TYPE_GZRZ, null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YSC, uploadDate, null, null);
		if (fileList.size() > 0) {
			userInfoJob.setCorporationName(jobInfo.getCorporationName());
			userInfoJob.setCorporationKind(jobInfo.getCorporationKind());
			userInfoJob.setCorporationIndustry(jobInfo.getCorporationIndustry());
			userInfoJob.setCorporationScale(jobInfo.getCorporationScale());
			userInfoJob.setPosition(jobInfo.getPosition());
			userInfoJob.setPresentCorporationWorkTime(jobInfo.getPresentCorporationWorkTime());
			userInfoJob.setCorporationPhoneArea(jobInfo.getCorporationPhoneArea());
			userInfoJob.setCorporationPhone(jobInfo.getCorporationPhone());
			userInfoJob.setJobEmail(jobInfo.getJobEmail());
			userInfoJob.setCorporationAddress(jobInfo.getCorporationAddress());
			BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);
			logger.info("=============保存用户信息到userInfoPerson表！=============");
			borrowinguserInfoPersonDao.save(userInfoPerson);
			logger.info("=============保存用户信息到userInfoJob表！=============");
			borrowingUserInfoJobDao.save(userInfoJob);
			saveUserApprove(userId, BigDecimal.valueOf(2L));
			BorrowingUserApprove userApprove = searchApprove(userId, BigDecimal.valueOf(2));
			FileUploadVO whereObj = new FileUploadVO();
			whereObj.setUserId(userId + "");
			whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
			FileUploadVO setObj = new FileUploadVO();
			setObj.setUserApproveId(userApprove);
			setObj.setStatus(Constants.FILEUPLOAD_STATUS_YTJ);
			fileUploadManager.update(whereObj, setObj);
			checkPro(userId);
			return "true";
		} else {
			return "noUpFile";
		}
	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-21 下午1:53:08
	 * @param users
	 * @return description:上传信用报告信息
	 */
	@Transactional(readOnly = false)
	public String updataCredit(BorrowingUsers users) {
		BigDecimal userId = users.getUserId();
		Date date = new Date();
		String uploadDate = DateUtil.getYMDTime(date);
		List<FileUploadVO> fileList = fileUploadManager.findRecordByCondition(userId + "", Constants.FILEUPLOAD_TYPE_XYBG, null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YSC, uploadDate, null, null);
		if (fileList.size() > 0) {
			BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);
			logger.info("=============保存用户信息到userInfoPerson表！=============");
			borrowinguserInfoPersonDao.save(userInfoPerson);
			logger.info("=============保存用户信息到userInfoJob表！=============");
			saveUserApprove(userId, BigDecimal.valueOf(4L));
			BorrowingUserApprove userApprove = searchApprove(userId, BigDecimal.valueOf(4));
			FileUploadVO whereObj = new FileUploadVO();
			whereObj.setUserId(userId + "");
			whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
			FileUploadVO setObj = new FileUploadVO();
			setObj.setUserApproveId(userApprove);
			setObj.setStatus(Constants.FILEUPLOAD_STATUS_YTJ);
			fileUploadManager.update(whereObj, setObj);
			checkPro(userId);
			return "true";
		} else {
			return "noUpFile";
		}

	}

	/**
	 * 
	 * @author jihui
	 * @date 2012-12-21 下午1:53:08
	 * @param users
	 * @return description:上传收入信息
	 */
	@Transactional(readOnly = false)
	public String updataIncome(String monthIncome, BorrowingUsers users) {
		BigDecimal userId = users.getUserId();
		Date date = new Date();
		String uploadDate = DateUtil.getYMDTime(date);
		List<FileUploadVO> fileList = fileUploadManager.findRecordByCondition(userId + "", Constants.FILEUPLOAD_TYPE_YSR, null, Constants.FILEUPLOAD_ISDEL_WSC, Constants.FILEUPLOAD_STATUS_YSC, uploadDate, null, null);
		if (fileList.size() > 0) {
			BorrowingUserInfoPerson userInfoPerson = findBorrowingUserInfoPersonByUserId(userId);
			userInfoPerson.setMonthIncome(new BigDecimal(monthIncome));
			logger.info("=============保存用户信息到userInfoPerson表！=============");
			borrowinguserInfoPersonDao.save(userInfoPerson);
			logger.info("=============保存用户信息到userInfoJob表！=============");
			saveUserApprove(userId, BigDecimal.valueOf(3L));
			BorrowingUserApprove userApprove = searchApprove(userId, BigDecimal.valueOf(3));
			FileUploadVO whereObj = new FileUploadVO();
			whereObj.setUserId(userId + "");
			whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
			FileUploadVO setObj = new FileUploadVO();
			setObj.setUserApproveId(userApprove);
			setObj.setStatus(Constants.FILEUPLOAD_STATUS_YTJ);
			fileUploadManager.update(whereObj, setObj);
			checkPro(userId);
			return "true";
		} else {
			return "noUpFile";
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
		}
		return userInfoPerson;
	}

	// 初始化页面
	public BorrowingUserInfoPerson showInfo(BigDecimal userid) {
		List<BorrowingUserInfoPerson> userInfoList = borrowinguserInfoPersonDao.findUserId(userid);
		if (userInfoList.size() != 0) {
			return userInfoList.get(0);
		} else {
			return null;
		}
	}

	// 初始化页面
	public BorrowingUserInfoJob showJobInfo(BigDecimal userid) {
		List<BorrowingUserInfoJob> userJobInfoList = borrowingUserInfoJobDao.findByUserId(userid);
		if (userJobInfoList.size() != 0) {
			return userJobInfoList.get(0);
		} else {
			return null;
		}
	}

	public BorrowingUsers getUsers(BigDecimal userid) {
		BorrowingUsers userInfo = borrowingUserDao.findByUserId(userid);
		return userInfo;
	}

	@Transactional(readOnly = false)
	private void checkPro(BigDecimal userId) {
		List<BorrowingUserApprove> proList = borrowingUserApproveDao.findPro(userId);
		logger.info("=============验证用户必要认证是否都提交=============");
		if (proList.size() == 4) {
			BorrowingUsers u = borrowingUserDao.findByUserId(userId);
			u.setUserStatus(BigDecimal.valueOf(4));
			u.setMaterialReviewStatus(BigDecimal.ZERO);
			borrowingUserDao.save(u);
		} else if (0 < proList.size() && proList.size() < 4) {
			BorrowingUsers u = borrowingUserDao.findByUserId(userId);
			u.setUserStatus(BigDecimal.valueOf(3));
			u.setMaterialReviewStatus(BigDecimal.ZERO);
			borrowingUserDao.save(u);
		}
	}

	@Transactional(readOnly = false)
	private void saveUserApprove(BigDecimal userId, BigDecimal proId) {
		BorrowingUserApprovePro upro = new BorrowingUserApprovePro();
		upro.setProId(proId);
		BorrowingUserApprove userApprove = null;
		List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, upro);
		if (list.size() != 0) {
			userApprove = list.get(0);
			userApprove.setUpdateTime(new Date());
			userApprove.setReviewStatus(BigDecimal.valueOf(2));// 审核中
		} else {
			userApprove = new BorrowingUserApprove();
			userApprove.setUserId(userId);
			userApprove.setProId(upro);
			userApprove.setProStatus(BigDecimal.ONE);
			userApprove.setReviewStatus(BigDecimal.valueOf(2));// 审核中
			Date curDate = new Date();
			userApprove.setCreateTime(curDate);
			userApprove.setUpdateTime(curDate);
		}
		logger.info("=============保存用户认证信息！=============");
		borrowingUserApproveDao.save(userApprove);
	}

	@Transactional(readOnly = false)
	private BorrowingUserApprove searchApprove(BigDecimal userId, BigDecimal proId) {
		BorrowingUserApprovePro upro = new BorrowingUserApprovePro();
		upro.setProId(proId);
		List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, upro);
		return list.get(0);
	}

	public BorrowingUserApprove getApproveId(BigDecimal userId, BigDecimal proId) {
		BorrowingUserApprovePro upro = new BorrowingUserApprovePro();
		upro.setProId(proId);
		BorrowingUserApprove userApprove = null;
		List<BorrowingUserApprove> list = borrowingUserApproveDao.findByUserIdAndProId(userId, upro);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
