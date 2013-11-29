package com.zendaimoney.online.admin.entity.account;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER_APPROVE_PRO")
public class AccountUserApprovePro implements java.io.Serializable{
	private static final long serialVersionUID = 1352386869804779407L;
	private BigDecimal proId;
	private String proName;

	@SequenceGenerator(name="generator")
	@Id
	@GeneratedValue(strategy  = SEQUENCE,generator="generator")
	@Column(name="PRO_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public BigDecimal getProId() {
		return proId;
	}

	public void setProId(BigDecimal proId) {
		this.proId = proId;
	}
    
	@Column(name ="PRO_NAME")
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
  
}
