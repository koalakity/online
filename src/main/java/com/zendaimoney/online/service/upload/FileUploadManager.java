package com.zendaimoney.online.service.upload;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.Constants;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.dao.borrowing.BorrowingUserDao;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.upload.FileUploadDao;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.entity.upload.FileUploadVO;
import com.zendaimoney.online.web.DTO.FileUploadDTO;

/**
 * 文件上传
 * 
 * @author HuYaHui
 */
@Component
public class FileUploadManager {

	private static Logger logger = LoggerFactory.getLogger(FileUploadManager.class);

	@Autowired
	private FileUploadDao fileUploadDao;

	@Autowired
	private BorrowingUserDao borrowingUserDao;

	@Autowired
	private CommonDao commonDao;

	public List<FileUploadVO> getAllByUser(String userId) {
		return fileUploadDao.findAllByUser(userId);
	}

	/**
	 * 根据条件查询记录,把查询到的文件转成byte数组返回 2012-12-20 上午9:45:58 by HuYaHui
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<FileUploadDTO> findRecordByConditionRtnByteList(String userId, String type, String isDel, String status, String uploadDate) {
		List<FileUploadDTO> rtnDTOList = new ArrayList<FileUploadDTO>();
		FileUploadVO whereObj = new FileUploadVO();
		whereObj.setUserId(userId);
		whereObj.setType(type);
		whereObj.setIsDel(isDel);
		whereObj.setStatus(status);
		whereObj.setUploadDate(uploadDate);
		List<FileUploadVO> dataList = fileUploadDao.findRecordByCondition(whereObj);
		for (FileUploadVO fileVO : dataList) {
			try {
				FileUploadDTO dto = new FileUploadDTO();
				BeanUtils.copyProperties(dto, fileVO);
				String filePath = fileVO.getFilePath();
				// TODO 把文件增加到缓存中
				File file = new File(filePath);
				byte[] imgByte = new byte[Integer.valueOf(file.length() + "")];
				new FileInputStream(file).read(imgByte);
				dto.setImgByte(imgByte);
				rtnDTOList.add(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtnDTOList;
	}

	/**
	 * 
	 * 修改 2012-12-20 下午1:05:44 by HuYaHui
	 * 
	 * @param whereObj
	 *            查询条件
	 * @param setObj
	 *            要修改的属性
	 */
	@Transactional(readOnly = false)
	public int update(FileUploadVO whereObj, FileUploadVO setObj) {
		int count = fileUploadDao.update(setObj, whereObj);
		logger.info("修改记录总数:" + count);
		return count;
	}

	/**
	 * 修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除 2012-12-24 下午1:14:51 by HuYaHui
	 * 
	 * @param userId
	 *            用户
	 * @param type
	 *            认证类型
	 * @return
	 */
	@Transactional(readOnly = false)
	public int update(String userId, String type) {
		FileUploadVO whereObj = new FileUploadVO();
		whereObj.setUserId(userId + "");
		whereObj.setType(type);
		whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
		whereObj.setIsDel(Constants.FILEUPLOAD_ISDEL_WSC);
		whereObj.setUploadDate(DateUtil.getCurrentDate("yyyy-MM-dd"));

		FileUploadVO setObj = new FileUploadVO();
		setObj.setIsDel(Constants.FILEUPLOAD_ISDEL_YSC);
		int count = fileUploadDao.update(setObj, whereObj);
		logger.info("修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除记录总数:" + count);
		return count;
	}

	/**
	 * 删除前一天的无效文件 2012-12-20 上午9:45:58 by HuYaHui
	 * 
	 * @param uploadDate
	 *            提交时间
	 * @return
	 */
	@Transactional(readOnly = false)
	public int delete(String uploadDate) {
		FileUploadVO whereObj = new FileUploadVO();
		whereObj.setUploadDate(uploadDate);
		whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
		int count = fileUploadDao.deleteByCondition(whereObj);
		logger.info("删除 已经上传未提交的文件记录，delete -->count:" + count);
		return count;
	}

	/**
	 * 在上传成功后的界面点击红叉叉删除文件和对应记录 2012-12-20 上午9:45:58 by HuYaHui
	 * 
	 * @param id
	 *            ID
	 * @param userId
	 *            用户ID
	 * @param type
	 *            认证类型
	 * @param uploadDate
	 *            提交时间
	 * @param isDel
	 *            是否删除
	 * @return
	 */
	@Transactional(readOnly = false)
	public int delete(Long id, String userId, String type, String uploadDate, String isDel) {
		FileUploadVO whereObj = new FileUploadVO();
		whereObj.setId(id);
		whereObj.setUserId(userId);
		whereObj.setType(type);
		whereObj.setUploadDate(uploadDate);
		whereObj.setIsDel(isDel);
		int count = fileUploadDao.deleteByCondition(whereObj);
		logger.info("删除 已经上传未提交的文件记录，delete -->count:" + count);
		return count;
	}

	/**
	 * 根据条件查询记录 2012-12-20 上午9:45:58 by HuYaHui
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<FileUploadVO> findRecordByCondition(FileUploadVO whereObj) {
		List<FileUploadVO> rtnList = fileUploadDao.findRecordByCondition(whereObj);
		logger.info("根据条件查询记录 findRecordByCondition size:" + (rtnList != null ? rtnList.size() : 0));
		return rtnList;
	}

	/**
	 * 根据条件查询记录 2012-12-20 上午9:45:58 by HuYaHui
	 * 
	 * @param userId
	 *            用户ID
	 * @param type
	 *            认证类型
	 * @param filePath
	 *            文件路径
	 * @param isDel
	 *            是否删除
	 * @param status
	 *            提交状态
	 * @param uploadDate
	 *            提交时间
	 * @param remark
	 *            备注
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<FileUploadVO> findRecordByCondition(Long id, String userId, String type, String filePath, String isDel, String status, String uploadDate, BorrowingUserApprove userApproveId, String remark) {
		logger.info("findRecordByCondition -->userId:" + userId + " type:" + type + " filePath:" + filePath + " isDel:" + isDel + " status:" + status + " uploadDate:" + uploadDate + " remark:" + remark);
		FileUploadVO whereObj = new FileUploadVO();
		whereObj.setId(id);
		whereObj.setUserId(userId);
		whereObj.setType(type);
		whereObj.setFilePath(filePath);
		whereObj.setIsDel(isDel);
		whereObj.setStatus(status);
		whereObj.setUploadDate(uploadDate);
		whereObj.setUserApproveId(userApproveId);
		whereObj.setRemark(remark);
		List<FileUploadVO> rtnList = findRecordByCondition(whereObj);
		logger.info("根据条件查询记录 findRecordByCondition size:" + (rtnList != null ? rtnList.size() : 0));
		return rtnList;
	}

	@Transactional(readOnly = true)
	public List<FileUploadVO> findDirtFile() {
		List<FileUploadVO> rtnList = fileUploadDao.findDirtFile();
		return rtnList;
	}

	/**
	 * 删除已上传，未提交的文件记录 2013-1-21 上午10:09:09 by HuYaHui
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void deleteDirtFile() {
		fileUploadDao.deleteDirtFile();
	}

	/**
	 * 根据条件查询记录 2012-12-20 上午9:45:58 by HuYaHui
	 * 
	 * @param userId
	 *            用户ID
	 * @param type
	 *            认证类型
	 * @param filePath
	 *            文件路径
	 * @param isDel
	 *            是否删除
	 * @param status
	 *            提交状态
	 * @param uploadDate
	 *            提交时间
	 * @param remark
	 *            备注
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<FileUploadVO> findRecordByCondition(String userId, String type, String filePath, String isDel, String status, String uploadDate, BorrowingUserApprove userApproveId, String remark) {
		logger.info("findRecordByCondition -->userId:" + userId + " type:" + type + " filePath:" + filePath + " isDel:" + isDel + " status:" + status + " uploadDate:" + uploadDate + " remark:" + remark);
		return findRecordByCondition(null, userId, type, filePath, isDel, status, uploadDate, userApproveId, remark);
	}

	/**
	 * 
	 * 修改当天，某用户，认证类型，已提交，未关联记录,未删除，改成已删除 保存新的文件记录 2012-12-20 下午1:05:44 by
	 * HuYaHui
	 * 
	 * @param userId
	 *            用户ID
	 * @param type
	 *            认证类型
	 * @param filePath
	 *            文件路径
	 * @param remark
	 *            备注
	 */
	@Transactional(readOnly = false)
	public FileUploadVO save(String userId, String type, String filePath, String remark) {
		logger.info("save -->userId:" + userId + " type:" + type + " filePath:" + filePath + " remark:" + remark);
		FileUploadVO _fileUpload = new FileUploadVO();
		_fileUpload.setId(commonDao.getSequenceByName("FILE_UPLOAD_SEQ"));
		_fileUpload.setUserId(userId);
		_fileUpload.setType(type);
		_fileUpload.setFilePath(filePath);
		_fileUpload.setUploadDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
		_fileUpload.setCreateTime(new Date());
		_fileUpload.setUpdateTime(new Date());
		_fileUpload.setRemark(remark);
		_fileUpload.setIsDel(Constants.FILEUPLOAD_ISDEL_WSC);
		_fileUpload.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
		fileUploadDao.save(_fileUpload);
		logger.info("保存新的文件记录成功.");
		return _fileUpload;
	}

	/**
	 * 验证用户是否存在 2012-11-21 下午4:24:08 by HuYaHui
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public BorrowingUsers getUsers(String userId) throws Exception {
		BorrowingUsers userInfo = borrowingUserDao.findByUserId(new BigDecimal(userId));
		return userInfo;
	}

}
