package com.zendaimoney.online.entity.stationMessage;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * StationLetter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STATION_LETTER")
public class StationMessageStationLetter implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 831327353049475136L;
	private BigDecimal letterId;
	private String msgKind;
	//一对一单项关联
	private StationMessageUsers senderId;
	//一对一单项关联
	private StationMessageUsers receiverId;
	private BigDecimal parentId;
	private BigDecimal childId;
	private String msgPath;
	private BigDecimal loanId;
	private String message;
	private String viewStatus;
	private String isapproveReceive;
	private BigDecimal replyId;
	private String senderDelStatus;
	private String receiverDelStatus;
	private Date senderTime;
	private String description;
	private String isDel;

	// Constructors

	/** default constructor */
	public StationMessageStationLetter() {
	}


	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "STATIONLETTER_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LETTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLetterId() {
		return this.letterId;
	}

	public void setLetterId(BigDecimal letterId) {
		this.letterId = letterId;
	}

	@Column(name = "MSG_KIND", length = 1)
	public String getMsgKind() {
		return this.msgKind;
	}

	public void setMsgKind(String msgKind) {
		this.msgKind = msgKind;
	}

	@OneToOne
	@JoinColumn(name = "SENDER_ID",referencedColumnName = "USER_ID")
	public StationMessageUsers getSenderId() {
		return this.senderId;
	}

	public void setSenderId(StationMessageUsers senderId) {
		this.senderId = senderId;
	}

	@OneToOne
	@JoinColumn(name = "RECEIVER_ID",referencedColumnName = "USER_ID")
	public StationMessageUsers getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(StationMessageUsers receiverId) {
		this.receiverId = receiverId;
	}

	@Column(name = "PARENT_ID", precision = 22, scale = 0)
	public BigDecimal getParentId() {
		return this.parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	@Column(name = "CHILD_ID", precision = 22, scale = 0)
	public BigDecimal getChildId() {
		return this.childId;
	}

	public void setChildId(BigDecimal childId) {
		this.childId = childId;
	}

	@Column(name = "MSG_PATH", length = 100)
	public String getMsgPath() {
		return this.msgPath;
	}

	public void setMsgPath(String msgPath) {
		this.msgPath = msgPath;
	}

	@Column(name = "LOAN_ID", precision = 22, scale = 0)
	public BigDecimal getLoanId() {
		return this.loanId;
	}

	public void setLoanId(BigDecimal loanId) {
		this.loanId = loanId;
	}

	@Column(name = "MESSAGE", length = 1000)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "VIEW_STATUS", nullable = false, precision = 22, scale = 0)
	public String getViewStatus() {
		return this.viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	@Column(name = "ISAPPROVE_RECEIVE", precision = 22, scale = 0)
	public String getIsapproveReceive() {
		return this.isapproveReceive;
	}

	public void setIsapproveReceive(String isapproveReceive) {
		this.isapproveReceive = isapproveReceive;
	}

	@Column(name = "REPLY_ID", precision = 22, scale = 0)
	public BigDecimal getReplyId() {
		return this.replyId;
	}

	public void setReplyId(BigDecimal replyId) {
		this.replyId = replyId;
	}

	@Column(name = "SENDER_DEL_STATUS", precision = 22, scale = 0)
	public String getSenderDelStatus() {
		return this.senderDelStatus;
	}

	public void setSenderDelStatus(String senderDelStatus) {
		this.senderDelStatus = senderDelStatus;
	}

	@Column(name = "RECEIVER_DEL_STATUS", precision = 22, scale = 0)
	public String getReceiverDelStatus() {
		return this.receiverDelStatus;
	}

	public void setReceiverDelStatus(String receiverDelStatus) {
		this.receiverDelStatus = receiverDelStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SENDER_TIME", nullable = false)
	public Date getSenderTime() {
		return this.senderTime;
	}

	public void setSenderTime(Timestamp senderTime) {
		this.senderTime = senderTime;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "IS_DEL", length = 1)
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

}