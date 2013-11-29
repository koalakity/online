package com.zendaimoney.online.admin.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zendaimoney.online.admin.entity.account.AccountMemoNoteAdmin;
import com.zendaimoney.online.admin.entity.audit.MaterialReviewStatusChangeAdmin;
import com.zendaimoney.online.admin.entity.audit.ReviewMemoNoteAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanNoteAdmin;

@Entity
public class Staff extends IdEntity implements UserDetails {

	private static final long serialVersionUID = -9404921226296552L;
	private String loginName;
	private String loginPassword;
	private String name;
	private String email;
	private BigDecimal isapproveEmail;
	private BigDecimal isapprovePhone;
	private BigDecimal isapproveCard;
	private String regIp;
	private Date regTime;
	private BigDecimal loginCount;
	private BigDecimal loginStatus;
	private String loginTimeLast;
	private String loginIpLast;
	private BigDecimal statusView;
	private BigDecimal regType;
	private String memo;
	private Role role;
	private String phone;
	private Set<LoanNoteAdmin> loanNotes = new HashSet<LoanNoteAdmin>();
	private Set<ReviewMemoNoteAdmin> reviewNotes = new HashSet<ReviewMemoNoteAdmin>();
	private Set<MaterialReviewStatusChangeAdmin>  materialReviewStatusNotes = new HashSet<MaterialReviewStatusChangeAdmin>();
	
	/**
	 * 2012.11.5
	 * Ray添加
	 * 关联后台用户信息备注表
	 */
	private Set<AccountMemoNoteAdmin> memoNote =  new HashSet<AccountMemoNoteAdmin>();
	
	@OneToMany(mappedBy="staff",fetch=FetchType.LAZY)
	public Set<AccountMemoNoteAdmin> getMemoNote() {
		return memoNote;
	}

	public void setMemoNote(Set<AccountMemoNoteAdmin> memoNote) {
		this.memoNote = memoNote;
	}

	@OneToMany(mappedBy="staff",fetch=FetchType.LAZY)
	public Set<MaterialReviewStatusChangeAdmin> getMaterialReviewStatusNotes() {
		return materialReviewStatusNotes;
	}

	public void setMaterialReviewStatusNotes(
			Set<MaterialReviewStatusChangeAdmin> materialReviewStatusNotes) {
		this.materialReviewStatusNotes = materialReviewStatusNotes;
	}

	@OneToMany(mappedBy="staff",fetch=FetchType.LAZY)
	public Set<ReviewMemoNoteAdmin> getReviewNotes() {
		return reviewNotes;
	}

	public void setReviewNotes(Set<ReviewMemoNoteAdmin> reviewNotes) {
		this.reviewNotes = reviewNotes;
	}
	
	@OneToMany(mappedBy="staff",fetch=FetchType.LAZY)
	public Set<LoanNoteAdmin> getLoanNotes() {
		return loanNotes;
	}

	

	public void setLoanNotes(Set<LoanNoteAdmin> loanNotes) {
		this.loanNotes = loanNotes;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "LOGIN_NAME", nullable = false, length = 50)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "LOGIN_PASSWORD", nullable = false, length = 50)
	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ISAPPROVE_EMAIL", precision = 22, scale = 0)
	public BigDecimal getIsapproveEmail() {
		return this.isapproveEmail;
	}

	public void setIsapproveEmail(BigDecimal isapproveEmail) {
		this.isapproveEmail = isapproveEmail;
	}

	@Column(name = "ISAPPROVE_PHONE", precision = 22, scale = 0)
	public BigDecimal getIsapprovePhone() {
		return this.isapprovePhone;
	}

	public void setIsapprovePhone(BigDecimal isapprovePhone) {
		this.isapprovePhone = isapprovePhone;
	}

	@Column(name = "ISAPPROVE_CARD", precision = 22, scale = 0)
	public BigDecimal getIsapproveCard() {
		return this.isapproveCard;
	}

	public void setIsapproveCard(BigDecimal isapproveCard) {
		this.isapproveCard = isapproveCard;
	}

	@Column(name = "REG_IP", length = 40)
	public String getRegIp() {
		return this.regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	@Column(name = "REG_TIME", length = 7)
	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column(name = "LOGIN_COUNT", precision = 22, scale = 0)
	public BigDecimal getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(BigDecimal loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "LOGIN_STATUS", precision = 22, scale = 0)
	public BigDecimal getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(BigDecimal loginStatus) {
		this.loginStatus = loginStatus;
	}

	@Column(name = "LOGIN_TIME_LAST")
	public String getLoginTimeLast() {
		return this.loginTimeLast;
	}

	public void setLoginTimeLast(String loginTimeLast) {
		this.loginTimeLast = loginTimeLast;
	}

	@Column(name = "LOGIN_IP_LAST", length = 50)
	public String getLoginIpLast() {
		return this.loginIpLast;
	}

	public void setLoginIpLast(String loginIpLast) {
		this.loginIpLast = loginIpLast;
	}

	@Column(name = "STATUS_VIEW", precision = 22, scale = 0)
	public BigDecimal getStatusView() {
		return this.statusView;
	}

	public void setStatusView(BigDecimal statusView) {
		this.statusView = statusView;
	}

	@Column(name = "REG_TYPE", precision = 22, scale = 0)
	public BigDecimal getRegType() {
		return this.regType;
	}

	public void setRegType(BigDecimal regType) {
		this.regType = regType;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> combinedAuthorities = new ArrayList<GrantedAuthority>();
		if(role!=null){
			for (RoleFunction roleFunction : role.getRoleFunctions()) {
				combinedAuthorities.add(new SimpleGrantedAuthority(roleFunction.getFunction().getFunctionCode()));
			}
		}
		return combinedAuthorities;
	}


	@Transient
	@Override
	public String getPassword() {
		return loginPassword;
	}

	@Transient
	@Override
	public String getUsername() {
		return loginName;
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return BigDecimal.ONE.equals(loginStatus);
	}
	
	@Transient
	public String getRoleName() {
		if(role!=null){
			return role.getRoleName();
		}
		return null;
	}
	
	@Transient
	public String getLoginStatusStr(){
		if(BigDecimal.ZERO.equals(loginStatus)){
			return "关闭";
		}
		return "开启";
	}
}