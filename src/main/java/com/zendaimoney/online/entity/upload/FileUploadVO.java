package com.zendaimoney.online.entity.upload;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;

/**
 * 文件上传表
 */
@Entity
@Table(name = "FILE_UPLOAD")
public class FileUploadVO implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private Long id;
	private BorrowingUserApprove userApproveId;
	private String userId;
	//认证类型
	private String type;
	//文件路径
	private String filePath;
	//是否删除
	private String isDel;
	//状态:1,已上传;2,已提交;2,已审核
	private String status;
	//提交时间
	private String uploadDate;
	private Date createTime;
	private Date updateTime;
	private String remark;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_APPROVE_ID")
	public BorrowingUserApprove getUserApproveId() {
		return userApproveId;
	}

	public void setUserApproveId(BorrowingUserApprove userApproveId) {
		this.userApproveId = userApproveId;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/** default constructor */
	public FileUploadVO() {
	}

	/** minimal constructor */
	public FileUploadVO(String userId, String type, String filePath,
			String uploadDate, Date createTime, Date updateTime) {
		this.userId = userId;
		this.type = type;
		this.filePath = filePath;
		this.uploadDate = uploadDate;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public FileUploadVO(String userId, String type, String filePath,
			String isDel, String uploadDate, Date createTime,
			Date updateTime, String remark) {
		this.userId = userId;
		this.type = type;
		this.filePath = filePath;
		this.isDel = isDel;
		this.uploadDate = uploadDate;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.remark = remark;
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0 )
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_ID", nullable = false, length = 11)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "TYPE", nullable = false, length = 22)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "FILE_PATH", nullable = false, length = 512)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "IS_DEL", length = 2)
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Column(name = "UPLOAD_DATE", nullable = false, length = 7)
	public String getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "CREATE_TIME", nullable = false)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", nullable = false)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "REMARK", length = 300)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}