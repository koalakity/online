package com.zendaimoney.online.entity.notice;


import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.common.DateUtil;

@Entity
@Table(name = "NOTICE")
public class IndexNotice implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3092607304931275568L;
	private Long id;
	private String title;
	private String content;
	private BigDecimal orders;
	private Integer type;
	private BigDecimal isCommend;
	private BigDecimal isDel;
	private Date creDate;
	private BigDecimal userId;
	private String creDateStr;
	private String contentStr;

	@Transient
	public String getContentStr() {
		if(content!=null&&content.length()>160){
			if(content.indexOf("<br")<=160){
				return content.substring(0, content.indexOf("<br"));
			}
			contentStr = content.substring(0,160).concat("……");
			return contentStr;
		}else{
			contentStr = content;
			return contentStr;
		}
	}

	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}

	@Transient
	public String getCreDateStr() {
		if(creDate!=null){
			creDateStr = DateUtil.getYMDTime(creDate);
			return creDateStr;
		}else{
			return creDateStr;
		}
	}

	public void setCreDateStr(String creDateStr) {
		this.creDateStr = creDateStr;
	}

	@Column(name="USER_ID")
	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	@SequenceGenerator(name = "generator", sequenceName = "NOTICE_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getOrders() {
		return orders;
	}

	public void setOrders(BigDecimal orders) {
		this.orders = orders;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getIsCommend() {
		return isCommend;
	}

	public void setIsCommend(BigDecimal isCommend) {
		this.isCommend = isCommend;
	}

	public BigDecimal getIsDel() {
		return isDel;
	}

	public void setIsDel(BigDecimal isDel) {
		this.isDel = isDel;
	}

	public Date getCreDate() {
			return creDate;
	}

	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}
	
}