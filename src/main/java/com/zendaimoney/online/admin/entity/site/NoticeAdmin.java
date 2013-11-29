package com.zendaimoney.online.admin.entity.site;

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

@Entity
@Table(name = "NOTICE")
public class NoticeAdmin implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5164721414542935972L;
	private Long id;
	private String title;
	private String content;
	private BigDecimal orders;
	private Integer type;
	private BigDecimal isCommend;
	private BigDecimal isDel;
	private Date creDate;
	
	private Staff staff;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	public Staff getStaff() {
		return staff;
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

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	@Transient
	public String getStaffName(){
		return staff.getLoginName();
	}
	
	@Transient
	public String getIsCommendStr(){
		if(BigDecimal.ZERO.equals(isCommend)){
			return "不推荐";
		}
		return "推荐";
	}
}