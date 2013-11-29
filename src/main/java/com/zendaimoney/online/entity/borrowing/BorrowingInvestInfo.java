package com.zendaimoney.online.entity.borrowing;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.zendaimoney.online.common.DateUtil;

/**
 * InvestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INVEST_INFO")
public class BorrowingInvestInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5035459192569949875L;
	private BigDecimal investId;
	private BorrowingUsers user;
	private Double investAmount;
	private Double havaScale;
	private Date investTime;
	private String investDate;
    private String description;
    private BorrowingLoanInfo loanInfo;
    private BigDecimal status;

	// Constructors

    @Transient
    public String getInvestDate() {
		if(investTime!=null){
			investDate = DateUtil.getYMDTime(investTime);
			return investDate;
		}else{
			return investDate;
		}
	}

	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}

	/** default constructor */
	public BorrowingInvestInfo() {
	}

	/** minimal constructor */
	public BorrowingInvestInfo(Double investAmount, Double havaScale, Date investTime) {
		this.user = user;
		this.investAmount = investAmount;
		this.havaScale = havaScale;
		this.investTime = investTime;
	}

	/** full constructor */
	public BorrowingInvestInfo(Double investAmount, Double havaScale, Date investTime,
			String description) {
		this.user = user;
		this.investAmount = investAmount;
		this.havaScale = havaScale;
		this.investTime = investTime;
		this.description = description;
	}

	// Property accessors
	@SequenceGenerator(name = "generator", sequenceName = "INVESTINFO_SEQ")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "INVEST_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getInvestId() {
		return this.investId;
	}

	public void setInvestId(BigDecimal investId) {
		this.investId = investId;
	}

    @OneToOne
    @JoinColumn(name = "USER_ID")
    public BorrowingUsers getUser() {
        return user;
    }

    public void setUser(BorrowingUsers user) {
        this.user = user;
    }

	@Column(name = "INVEST_AMOUNT", nullable = false, precision = 22, scale = 7)
	public Double getInvestAmount() {
		return this.investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	@Column(name = "HAVA_SCALE", nullable = false, precision = 22, scale = 18)
	public Double getHavaScale() {
		return this.havaScale;
	}

	public void setHavaScale(Double havaScale) {
		this.havaScale = havaScale;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVEST_TIME", nullable = false, length = 7)
	public Date getInvestTime() {
		return this.investTime;
	}

	public void setInvestTime(Timestamp investTime) {
		this.investTime = investTime;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="LOAN_ID") 
    public BorrowingLoanInfo getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(BorrowingLoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }
    
    @Column(name = "STATUS")
    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }


}