package com.zendaimoney.online.entity.message;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * StationLetter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STATION_LETTER")
public class MessageStationLetter implements java.io.Serializable {

	private static final long serialVersionUID = 1949640367542147403L;
	private BigDecimal letterId;
	private String msgKind;
	private MessageUsers senderId;
	private MessageUsers receiverId;
	private BigDecimal parentId;
	private BigDecimal childId;
	private String msgPath;
	private BigDecimal loanId;
	private String message;
	private BigDecimal viewStatus;
	private BigDecimal isapproveReceive;
	private BigDecimal replyId;
	private BigDecimal senderDelStatus;
	private BigDecimal receiverDelStatus;
	private Date senderTime;
	private String description;
	private String isDel;

	public MessageStationLetter() {
	}

	@SequenceGenerator(name = "generator",sequenceName="STATIONLETTER_SEQ")
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
	@JoinColumn(name="SENDER_ID",referencedColumnName="USER_ID")
	public MessageUsers getSenderId() {
		return this.senderId;
	}

	public void setSenderId(MessageUsers senderId) {
		this.senderId = senderId;
	}

	@OneToOne
	@JoinColumn(name="RECEIVER_ID",referencedColumnName="USER_ID")
	public MessageUsers getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(MessageUsers receiverId) {
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

	@Column(name = "VIEW_STATUS", precision = 22, scale = 0)
	public BigDecimal getViewStatus() {
		return this.viewStatus;
	}

	public void setViewStatus(BigDecimal viewStatus) {
		this.viewStatus = viewStatus;
	}

	@Column(name = "ISAPPROVE_RECEIVE", precision = 22, scale = 0)
	public BigDecimal getIsapproveReceive() {
		return this.isapproveReceive;
	}

	public void setIsapproveReceive(BigDecimal isapproveReceive) {
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
	public BigDecimal getSenderDelStatus() {
		return this.senderDelStatus;
	}

	public void setSenderDelStatus(BigDecimal senderDelStatus) {
		this.senderDelStatus = senderDelStatus;
	}

	@Column(name = "RECEIVER_DEL_STATUS", precision = 22, scale = 0)
	public BigDecimal getReceiverDelStatus() {
		return this.receiverDelStatus;
	}

	public void setReceiverDelStatus(BigDecimal receiverDelStatus) {
		this.receiverDelStatus = receiverDelStatus;
	}

	@Column(name = "SENDER_TIME")
	public Date getSenderTime() {
		return this.senderTime;
	}

	public void setSenderTime(Date senderTime) {
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