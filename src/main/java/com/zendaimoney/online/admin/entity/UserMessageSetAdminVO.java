package com.zendaimoney.online.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserMessageSet entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_MESSAGE_SET")
public class UserMessageSetAdminVO implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1417405764862843807L;
	private Long messagesetId;
	private Long userId;
	private Long mannerId;
	private Long kindId;
	private String description;

	// Constructors

	/** default constructor */
	public UserMessageSetAdminVO() {
	}

	/** full constructor */
	public UserMessageSetAdminVO(Long userId, Long mannerId,Long kindId,
			String description) {
		this.userId = userId;
		this.mannerId = mannerId;
		this.kindId=kindId;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator",sequenceName="USERMESSAGESET_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "MESSAGESET_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getMessagesetId() {
		return this.messagesetId;
	}

	public void setMessagesetId(Long messagesetId) {
		this.messagesetId = messagesetId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "MANNER_ID", precision = 22, scale = 0)
	public Long getMannerId() {
		return this.mannerId;
	}

	public void setMannerId(Long mannerId) {
		this.mannerId = mannerId;
	}
	
	@Column(name = "KIND_ID")
	public Long getKindId() {
		return kindId;
	}

	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}