package com.zendaimoney.online.entity.borrowing;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * StationLetter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STATION_LETTER")
public class BorrowingStationLetter implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1873496555248874381L;
	private BigDecimal letterId;
	private BigDecimal senderId;
	private BigDecimal receiverId;
	private String message;
	private BigDecimal viewStatus;
	private BigDecimal isapproveReceive;
	private BigDecimal replyId;
	private BigDecimal senderDelStatus;
	private BigDecimal receiverDelStatus;
	private Date senderTime;
	private String description;

	// Constructors

	/** default constructor */
	public BorrowingStationLetter() {
	}

	/** minimal constructor */
	public BorrowingStationLetter(BigDecimal senderId, BigDecimal receiverId,
			BigDecimal viewStatus, Date senderTime) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.viewStatus = viewStatus;
		this.senderTime = senderTime;
	}

	/** full constructor */
	public BorrowingStationLetter(BigDecimal senderId, BigDecimal receiverId,
			String message, BigDecimal viewStatus, BigDecimal isapproveReceive,
			BigDecimal replyId, BigDecimal senderDelStatus,
			BigDecimal receiverDelStatus, Date senderTime, String description) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.viewStatus = viewStatus;
		this.isapproveReceive = isapproveReceive;
		this.replyId = replyId;
		this.senderDelStatus = senderDelStatus;
		this.receiverDelStatus = receiverDelStatus;
		this.senderTime = senderTime;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName="USERCREDITNOTE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LETTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLetterId() {
		return this.letterId;
	}

	public void setLetterId(BigDecimal letterId) {
		this.letterId = letterId;
	}

	@Column(name = "SENDER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getSenderId() {
		return this.senderId;
	}

	public void setSenderId(BigDecimal senderId) {
		this.senderId = senderId;
	}

	@Column(name = "RECEIVER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(BigDecimal receiverId) {
		this.receiverId = receiverId;
	}

	@Column(name = "MESSAGE", length = 600)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "VIEW_STATUS", nullable = false, precision = 22, scale = 0)
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

	@Column(name = "SENDER_TIME", nullable = false, length = 7)
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

}