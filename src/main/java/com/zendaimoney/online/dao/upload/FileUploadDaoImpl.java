package com.zendaimoney.online.dao.upload;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;

import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.entity.upload.FileUploadVO;

/**
 * 文件上传表对应的dao
 * 
 * @author HuYaHui
 */
public class FileUploadDaoImpl implements FileUploadDao {

	@PersistenceContext
	private EntityManager em;

	// TODO 临时方法
	public List<FileUploadVO> findAllByUser(String userId) {
		return em.createQuery("select f from FileUploadVO f where 1=1 and f.userId=" + userId + "order by f.type,f.createTime asc").getResultList();
	}

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
	public int deleteByCondition(FileUploadVO whereObj) {
		// 获取修改条件
		String getWhereCondition = getWhereConditionFromObj(whereObj);
		if (getWhereCondition == null || getWhereCondition.equals("") || getWhereCondition.equals("where 1=1")) {
			return 0;
		}
		StringBuilder sb = new StringBuilder("delete FileUploadVO ").append((getWhereCondition));
		Query query = em.createQuery(sb.toString());
		return query.executeUpdate();
	}

	/**
	 * 修改记录 2012-12-20 上午10:05:13 by HuYaHui
	 * 
	 * @param setObj
	 *            更新对象
	 * @param whereObj
	 *            条件对象
	 * @return
	 * @throws Exception
	 */
	public int update(FileUploadVO setObj, FileUploadVO whereObj) {
		// 获取要修改的属性和值
		String getSetCondition = getSetConditionFromObj(setObj);
		// 获取修改条件
		String getWhereCondition = getWhereConditionFromObj(whereObj);
		if (getSetCondition == null || getSetCondition.trim().equals("updateTime=?") || getWhereCondition == null || getWhereCondition.equals("") || getWhereCondition.equals("where 1=1")) {
			return 0;
		}
		StringBuilder sb = new StringBuilder("update FileUploadVO set ").append(getSetCondition).append((getWhereCondition));
		Query query = em.createQuery(sb.toString()).setParameter(1, new Date());
		return query.executeUpdate();
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
	public List<FileUploadVO> findRecordByCondition(FileUploadVO whereObj) {
		StringBuilder sqlString = new StringBuilder("select f from FileUploadVO f ");
		// 获取修改条件
		String getWhereCondition = getWhereConditionFromObj(whereObj);
		sqlString.append(getWhereCondition).append(" order by createTime desc");
		return em.createQuery(sqlString.toString()).getResultList();
	}

	/**
	 * 查询已上传，未提交的文件记录 2013-1-21 上午10:09:09 by HuYaHui
	 * 
	 * @return
	 */
	public List<FileUploadVO> findDirtFile() {
		return em.createQuery("select f from FileUploadVO f where " + "(f.userApproveId is null or f.userApproveId='') " + "and f.status=1 and f.uploadDate<=?").setParameter(1, DateUtil.getPreviousDayyyyyMMdd(new Date())).getResultList();
	}

	/**
	 * 删除已上传，未提交的文件记录 2013-1-21 上午10:09:09 by HuYaHui
	 * 
	 * @return
	 */
	public void deleteDirtFile() {
		em.createQuery("delete from FileUploadVO f where " + "(f.userApproveId is null or f.userApproveId='') " + "and f.status=1 and f.uploadDate<=?").setParameter(1, DateUtil.getPreviousDayyyyyMMdd(new Date())).executeUpdate();
	}

	/**
	 * 保存 2012-12-19 下午2:42:38 by HuYaHui
	 * 
	 * @return
	 */
	public void save(FileUploadVO _fileUpload) {
		em.persist(_fileUpload);
	}

	/**
	 * 根据对象获取查询set条件 2012-12-20 上午10:36:26 by HuYaHui
	 * 
	 * @param whereObj
	 * @return
	 * @throws Exception
	 */
	private String getSetConditionFromObj(FileUploadVO setObj) {
		try {
			StringBuilder sb = new StringBuilder("updateTime=? ");
			Field[] fields = setObj.getClass().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				String value = BeanUtils.getProperty(setObj, fieldName);
				if (fieldName.equals("userApproveId") && value != null) {
					BigDecimal userApproveId = setObj.getUserApproveId().getUserApproveId();
					sb.append(" ,").append(fieldName).append("=").append(userApproveId).append(" ");
				} else if (value != null && !value.equals("")) {
					sb.append(" ,").append(fieldName).append("='").append(value).append("' ");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据对象获取查询where条件 2012-12-20 上午10:36:26 by HuYaHui
	 * 
	 * @param whereObj
	 * @return
	 * @throws Exception
	 */
	private String getWhereConditionFromObj(FileUploadVO whereObj) {
		try {
			StringBuilder sb = new StringBuilder("where 1=1");
			Field[] fields = whereObj.getClass().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				String value = BeanUtils.getProperty(whereObj, fieldName);
				if (fieldName.equals("userApproveId")) {
					if (value != null) {
						BigDecimal userApproveId = whereObj.getUserApproveId().getUserApproveId();
						sb.append(" and userApproveId=").append(userApproveId).append(" ");
					} else {
						sb.append(" and (userApproveId='' or userApproveId is null) ");
					}
				} else if (value != null && !value.equals("")) {
					sb.append(" and ").append(fieldName).append("='").append(value).append("' ");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
