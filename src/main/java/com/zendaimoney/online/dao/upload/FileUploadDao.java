package com.zendaimoney.online.dao.upload;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.zendaimoney.online.entity.upload.FileUploadVO;

/**
 * 文件上传表对应的dao
 * 
 * @author HuYaHui
 */
public interface FileUploadDao extends Repository<FileUploadVO, Long> {

	public List<FileUploadVO> findAllByUser(String userId);

	/**
	 * 删除已上传，未提交的文件记录 2013-1-21 上午10:09:09 by HuYaHui
	 * 
	 * @return
	 */
	public void deleteDirtFile();

	/**
	 * 删除已上传，未提交的文件记录 2013-1-21 上午10:09:09 by HuYaHui
	 * 
	 * @return
	 */
	public List<FileUploadVO> findDirtFile();

	/**
	 * 删除记录 2012-12-20 上午9:45:58 by HuYaHui
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
	public int deleteByCondition(FileUploadVO whereObj);

	/**
	 * 修改记录 2012-12-20 上午10:05:13 by HuYaHui
	 * 
	 * @param setObj
	 *            更新对象
	 * @param whereObj
	 *            条件对象
	 * @return
	 */
	public int update(FileUploadVO setObj, FileUploadVO whereObj);

	/**
	 * 根据条件查询记录 2012-12-20 上午9:45:58 by HuYaHui
	 * 
	 * @param userId
	 *            用户ID
	 * @param type
	 *            认证类型
	 * @param filePath
	 *            文件路径
	 * @param idDel
	 *            是否删除
	 * @param status
	 *            提交状态
	 * @param uploadDate
	 *            提交时间
	 * @param remark
	 *            备注
	 * @return
	 */
	public List<FileUploadVO> findRecordByCondition(FileUploadVO whereObj);

	/**
	 * 保存 2012-12-19 下午2:42:38 by HuYaHui
	 * 
	 * @return
	 */
	public void save(FileUploadVO _fileUpload);

}
