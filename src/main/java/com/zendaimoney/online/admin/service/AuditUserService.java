package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.ChannelDAO;
import com.zendaimoney.online.admin.dao.SysCodeDao;
import com.zendaimoney.online.admin.dao.account.AuditUserDao;
import com.zendaimoney.online.admin.dao.audit.AuditItemDao;
import com.zendaimoney.online.admin.dao.audit.AuditNoteDao;
import com.zendaimoney.online.admin.dao.audit.MaterialNoteDao;
import com.zendaimoney.online.admin.dao.audit.UserCreditNoteDao;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.admin.entity.account.AccountUserApproveAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.account.UserCreditNoteAdmin;
import com.zendaimoney.online.admin.entity.audit.MaterialReviewStatusChangeAdmin;
import com.zendaimoney.online.admin.entity.audit.ReviewMemoNoteAdmin;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.AuditNoteListForm;
import com.zendaimoney.online.admin.vo.AuidtListForm;
import com.zendaimoney.online.admin.vo.MaterialReviewStatusChangeForm;
import com.zendaimoney.online.admin.vo.UserListForm;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.entity.financial.FinancialSysMsg;
import com.zendaimoney.online.oii.sms.SMSSender;

@Service
@Transactional
public class AuditUserService {
	private static Logger logger = LoggerFactory.getLogger(AdminLogService.class);
	@Autowired
	private AuditUserDao auditUserDao;

	@Autowired
	private AuditNoteDao auditNoteDao;

	@Autowired
	private StaffService staffService;

	@Autowired
	private MaterialNoteDao materialNoteDao;

	@Autowired
	private AuditItemDao auditItemDao;

	@Autowired
	private UserCreditNoteDao userCreditNoteDao;

	@Autowired
	FinancialSysMsgDao sysMsgDao;
	
	@Autowired
	SysCodeDao sysCodeDao;
	
	@Autowired
	private ChannelDAO channelSelfDAO;

	public Page<AccountUsersAdmin> findAuditUsersPage(final UserListForm userListForm, Pageable pageable) {
		return auditUserDao.findAll(new Specification<AccountUsersAdmin>() {
			@Override
			public Predicate toPredicate(Root<AccountUsersAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Path<String> realName = root.get("userInfoPerson").get("realName");
				predicates.add(cb.equal(realName, userListForm.getLoginName()));
				Path<String> identityNo = root.get("userInfoPerson").get("identityNo");
				predicates.add(cb.equal(identityNo, userListForm.getIdentityNo()));
				if (!"".equals(userListForm.getEmail()) && userListForm.getEmail() != null) {
					predicates.add(cb.equal(root.get("email"), userListForm.getEmail()));
				}
				if (!"".equals(userListForm.getPhoneNo()) && userListForm.getPhoneNo() != null) {
					predicates.add(cb.equal(root.get("userInfoPerson").get("phoneNo"), userListForm.getPhoneNo()));
				}
				if (userListForm.getIsApproveCard() != null) {
					predicates.add(cb.equal(root.get("isApproveCard"), userListForm.getIsApproveCard()));
				}
				predicates.add(cb.equal(root.get("userStatus"), "4"));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	public Page<AccountUsersAdmin> findPicUsersPage(final UserListForm userListForm, Pageable pageable) {
		return auditUserDao.findAll(new Specification<AccountUsersAdmin>() {
			@Override
			public Predicate toPredicate(Root<AccountUsersAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (!"".equals(userListForm.getRealName()) && userListForm.getRealName() != null) {
					Path<String> realName = root.get("userInfoPerson").get("realName");
					predicates.add(cb.equal(realName, userListForm.getRealName()));
				}
				if (!"".equals(userListForm.getIdentityNo()) && userListForm.getIdentityNo() != null) {
					Path<String> identityNo = root.get("userInfoPerson").get("identityNo");
					predicates.add(cb.equal(identityNo, userListForm.getIdentityNo()));
				}

				if (!"".equals(userListForm.getEmail()) && userListForm.getEmail() != null) {
					Path<String> email = root.get("email");
					predicates.add(cb.equal(email, userListForm.getEmail()));
				}
				if (!"".equals(userListForm.getPhoneNo()) && userListForm.getPhoneNo() != null) {
					Path<String> phoneNo = root.get("userInfoPerson").get("phoneNo");
					predicates.add(cb.equal(phoneNo, userListForm.getPhoneNo()));
				}
				// 当没有条件的时候，查询结果为空，所以添加一个任意没有数据的条件
				if (userListForm.getRealName() == null && userListForm.getIdentityNo() == null && userListForm.getPhoneNo() == null && userListForm.getEmail() == null) {
					predicates.add(cb.equal(root.get("userStatus"), "9"));
				} else if (userListForm.getRealName() == "" && userListForm.getIdentityNo() == "" && userListForm.getPhoneNo() == "" && userListForm.getEmail() == "") {
					predicates.add(cb.equal(root.get("userStatus"), "9"));
				}
				// predicates.add(cb.equal(root.get("userStatus"), "4"));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	public Page<AccountUsersAdmin> findAllAuditUsersPage(final UserListForm userListForm, Pageable pageable) {
		return auditUserDao.findAll(new Specification<AccountUsersAdmin>() {
			@Override
			public Predicate toPredicate(Root<AccountUsersAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Path<String> realName = root.get("userInfoPerson").get("realName");
				if (!"".equals(userListForm.getLoginName()) && userListForm.getLoginName() != null) {
					predicates.add(cb.like(realName, "%" + userListForm.getLoginName() + "%"));
				}
				Path<String> identityNo = root.get("userInfoPerson").get("identityNo");
				if (!"".equals(userListForm.getIdentityNo()) && userListForm.getIdentityNo() != null) {
					predicates.add(cb.equal(identityNo, userListForm.getIdentityNo()));
				}
				if (!"".equals(userListForm.getEmail()) && userListForm.getEmail() != null) {
					Path<String> email = root.get("email");
					predicates.add(cb.like(email, "%" + userListForm.getEmail() + "%"));
				}
				if (!"".equals(userListForm.getPhoneNo()) && userListForm.getPhoneNo() != null) {
					predicates.add(cb.equal(root.get("userInfoPerson").get("phoneNo"), userListForm.getPhoneNo()));
				}
				if (userListForm.getIsApproveCard() != null) {
					predicates.add(cb.equal(root.get("isApproveCard"), userListForm.getIsApproveCard()));
				}
				predicates.add(cb.equal(root.get("userStatus"), "4"));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	public Page<AccountUsersAdmin> findWaitAuditUsersPage(final UserListForm userListForm, Pageable pageable, final BigDecimal auditId) {
		return auditUserDao.findAll(new Specification<AccountUsersAdmin>() {
			@Override
			public Predicate toPredicate(Root<AccountUsersAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Path<String> loginName = root.get("loginName");
				if (!"".equals(userListForm.getLoginName()) && userListForm.getLoginName() != null) {
					predicates.add(cb.like(loginName, "%" + userListForm.getLoginName() + "%"));
				}
				Path<String> realName = root.get("userInfoPerson").get("realName");
				if (!"".equals(userListForm.getRealName()) && userListForm.getRealName() != null) {
					predicates.add(cb.like(realName, "%" + userListForm.getRealName() + "%"));
				}
				// 一级渠道名称不为空
				if (!"".equals(userListForm.getChannelFId()) && userListForm.getChannelFId() != null) {
					// 二级渠道不为空，根据二级渠道查询
					if (!"".equals(userListForm.getChannelCId()) && userListForm.getChannelCId() != null) {
						predicates.add(cb.equal(root.get("channelInfo").get("id"), userListForm.getChannelCId()));
					} else {
						// 二级渠道为空，则查询一级渠道下所有二级渠道
						List<ChannelInfoVO> channelInfoList = channelSelfDAO.findChildListByParentId(Long.valueOf(userListForm.getChannelFId()));
						List<Long> idList = new ArrayList<Long>();
						for (ChannelInfoVO vo : channelInfoList) {
							idList.add(vo.getId());
						}
						predicates.add(root.get("channelInfo").get("id").in(idList));
					}
				}
				predicates.add(cb.equal(root.get("userStatus"), "4"));
				predicates.add(cb.equal(root.get("materialReviewStatus"), auditId));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	public AccountUsersAdmin findByUserId(BigDecimal userId) {
		AccountUsersAdmin aua = auditUserDao.findByUserId(userId);
		return aua;
	}

	public void addAuditMemoNote(AuditNoteListForm auditNoteListForm) {
		ReviewMemoNoteAdmin auditNote = new ReviewMemoNoteAdmin();
		Staff staff = new Staff();
		staff.setId(staffService.getCurrentStaff().getId());
		auditNote.setUserId(new BigDecimal(auditNoteListForm.getUserId()));
		auditNote.setMemo(auditNoteListForm.getMemo());
		auditNote.setOperateTime(new Date());
		auditNote.setStaff(staff);
		logger.info("SAVE: 审核备注 || REVIEW_MEMO " + " UserId=" + auditNoteListForm.getUserId() + " Memo=" + auditNoteListForm.getMemo() + " staff=" + staff.getId());
		auditNoteDao.save(auditNote);
	}

	public Page<ReviewMemoNoteAdmin> searchAuditMemoNote(PageRequest pageRequest, final BigDecimal userId) {
		Page<ReviewMemoNoteAdmin> auditMemoNotes = auditNoteDao.findAll(new Specification<ReviewMemoNoteAdmin>() {
			public Predicate toPredicate(Root<ReviewMemoNoteAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.equal(root.get("userId"), userId));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageRequest);
		return auditMemoNotes;
	}

	public Page<MaterialReviewStatusChangeAdmin> searchReturnMemoNote(PageRequest pageRequest, final BigDecimal userId) {
		Page<MaterialReviewStatusChangeAdmin> returnMemoNotes = materialNoteDao.findAll(new Specification<MaterialReviewStatusChangeAdmin>() {
			public Predicate toPredicate(Root<MaterialReviewStatusChangeAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.equal(root.get("userId"), userId));
				predicates.add(cb.equal(root.get("kind"), BigDecimal.ONE));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageRequest);
		return returnMemoNotes;
	}

	public Page<MaterialReviewStatusChangeAdmin> searchRejectMemoNote(PageRequest pageRequest, final BigDecimal userId) {
		Page<MaterialReviewStatusChangeAdmin> rejectMemoNotes = materialNoteDao.findAll(new Specification<MaterialReviewStatusChangeAdmin>() {
			public Predicate toPredicate(Root<MaterialReviewStatusChangeAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.equal(root.get("userId"), userId));
				predicates.add(cb.equal(root.get("kind"), new BigDecimal(2)));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageRequest);
		return rejectMemoNotes;
	}

	@LogInfo(operateKind = OperateKind.审核, operateContent = "用户资料审核通过")
	public void auditPass(List<AuidtListForm> auditList, BigDecimal userId, BigDecimal auditId, BigDecimal creditAmount) {
		AccountUsersAdmin accountUsers = auditUserDao.findOne(userId);
		accountUsers.setMaterialReviewStatus(auditId);
		if (auditId.equals(new BigDecimal(2))) {
			List<UserCreditNoteAdmin> userCreditNoteList = userCreditNoteDao.findByUserUserId(userId);
			if (userCreditNoteList.size() > 0) {
				userCreditNoteList.get(0).setTempCreditAmount(creditAmount);
			}
		}
		// 查看基本认证是否有提交信息
		List<AccountUserApproveAdmin> user = auditItemDao.getBasicInfoList(userId);
		if (user.size() == 0) {
			for (AuidtListForm audit : auditList) {
				if (audit.getProId().equals(new BigDecimal(16))) {
					AccountUserApproveAdmin accountUserApprove = new AccountUserApproveAdmin();
					accountUserApprove.setReviewStatus(audit.getReviewStatus());
					accountUserApprove.setTempCreditScore(audit.getCreditScore());
					// 终定后将信用分数
					if (auditId.equals(new BigDecimal(5))) {
						accountUserApprove.setCreditScore(audit.getCreditScore());
					}
					accountUserApprove.setAuditerId(new BigDecimal(staffService.getCurrentStaff().getId()));
					accountUserApprove.setAuditTime(new Date());
					accountUserApprove.setUser(accountUsers);
					accountUserApprove.setProId(new BigDecimal(16));
					accountUserApprove.setProStatus(BigDecimal.ONE);
					logger.info("SAVE: 审核备注 || USER_APPROVE " + " ProId=16" + " ProStatus=1" + " CreditScore=" + audit.getCreditScore() + " ReviewStatus=" + audit.getReviewStatus());
					auditItemDao.save(accountUserApprove);
					break;
				}
			}
		}
		// 所有认证
		int score = 0;
		List<AccountUserApproveAdmin> auaas = this.auditItemDao.getAccountUseApproveList(userId);
		for (AccountUserApproveAdmin auaa : auaas) {
			for (AuidtListForm audit : auditList) {
				if (audit.getProId().equals(auaa.getProId())) {
					AccountUserApproveAdmin accountUserApprove = auaa;
					accountUserApprove.setTempReviewStatus(audit.getReviewStatus());
					if (auditId.equals(new BigDecimal(5))) {
						accountUserApprove.setCreditScore(audit.getCreditScore());
						accountUserApprove.setReviewStatus(audit.getReviewStatus());
					}
					accountUserApprove.setTempCreditScore(audit.getCreditScore());
					accountUserApprove.setAuditerId(new BigDecimal(staffService.getCurrentStaff().getId()));
					accountUserApprove.setAuditTime(new Date());
					logger.info("SAVE: 审核备注 || USER_APPROVE " + " ProId=16" + " ProStatus=1" + " CreditScore=" + audit.getCreditScore() + " ReviewStatus=" + audit.getReviewStatus());
					auditItemDao.save(accountUserApprove);
					score += audit.getCreditScore().intValue();
					break;
				}
			}
		}

		// 插入
		List<UserCreditNoteAdmin> userCreditList = userCreditNoteDao.findByUserUserId(userId);
		if (userCreditList.size() == 0) {
			UserCreditNoteAdmin userCreditNote = new UserCreditNoteAdmin();
			userCreditNote.setUser(accountUsers);
			userCreditNote.setCreditAmount(BigDecimal.ZERO);
			userCreditNote.setCreditGrade(BigDecimal.ONE);
			userCreditNote.setCreditScoreSum(BigDecimal.ZERO);
			userCreditNote.setTempCreditAmount(creditAmount);
			userCreditNote.setTempCreditScoreSum(new BigDecimal(score));
			userCreditNote.setTempCreidtGrade(formmatCreditGrade(score));
			logger.info("SAVE: 审核备注 || User=" + accountUsers.getUserId() + " CreditAmount=" + creditAmount + " CreditScoreSum=" + score);
			userCreditNoteDao.save(userCreditNote);
		} else {
			// add by jihui 初定审核
			if (creditAmount.equals(BigDecimal.ZERO)) {
				creditAmount = userCreditList.get(0).getCreditAmount();
			}
			userCreditList.get(0).setTempCreditAmount(creditAmount);
			userCreditList.get(0).setTempCreditScoreSum(new BigDecimal(score));
			userCreditList.get(0).setTempCreidtGrade(formmatCreditGrade(score));
		}
		// 终定后将确定用户的信用分数和额度
		if (auditId.equals(new BigDecimal(5))) {
			accountUsers.setUserStatus(new BigDecimal(5));
			List<UserCreditNoteAdmin> userCreditNoteList = userCreditNoteDao.findByUserUserId(userId);
			userCreditNoteList.get(0).setTempCreditAmount(creditAmount);
			userCreditNoteList.get(0).setCreditAmount(creditAmount);
			userCreditNoteList.get(0).setCreditScoreSum(new BigDecimal(score));
			userCreditNoteList.get(0).setCreditGrade(formmatCreditGrade(score));
		}
	}

	public void auditSave(List<AuidtListForm> auditList, BigDecimal userId) {
		List<AccountUserApproveAdmin> auaas = this.auditItemDao.getAccountUseApproveList(userId);
		for (AccountUserApproveAdmin auaa : auaas) {
			for (AuidtListForm audit : auditList) {
				if (audit.getProId().equals(auaa.getProId())) {
					AccountUserApproveAdmin accountUserApprove = auaa;
					accountUserApprove.setReviewStatus(audit.getReviewStatus());
					accountUserApprove.setCreditScore(audit.getCreditScore());
					accountUserApprove.setAuditerId(new BigDecimal(staffService.getCurrentStaff().getId()));
					logger.info("SAVE: 审核备注 || ReviewStatus=" + audit.getReviewStatus() + " CreditScore=" + audit.getCreditScore());
					auditItemDao.save(accountUserApprove);
					break;
				}
			}
		}

	}

	@LogInfo(operateKind = OperateKind.审核, operateContent = "用户资料审核被驳回")
	public AjaxResult auditReject(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		AccountUsersAdmin accountUsers = auditUserDao.findOne(materialReviewStatusChangeForm.getUserId());
		accountUsers.setMaterialReviewStatus(new BigDecimal(3));
		MaterialReviewStatusChangeAdmin materialReviewStatus = new MaterialReviewStatusChangeAdmin();
		Staff staff = new Staff();
		staff.setId(staffService.getCurrentStaff().getId());
		materialReviewStatus.setUserId(materialReviewStatusChangeForm.getUserId());
		materialReviewStatus.setOperateNode(materialReviewStatusChangeForm.getOperateNode());
		materialReviewStatus.setOperateTime(new Date());
		materialReviewStatus.setStaff(staff);
		materialReviewStatus.setKind("2");
		materialReviewStatus.setCause(materialReviewStatusChangeForm.getCause());
		logger.info("SAVE: 审核备注 || MATERIAL_REVIEW_STATUS_CHANGE  userId= " + materialReviewStatusChangeForm.getUserId() + " OperateNode=" + materialReviewStatusChangeForm.getOperateNode() + " Kind=2 Cause=" + materialReviewStatusChangeForm.getCause());
		materialNoteDao.save(materialReviewStatus);
		AjaxResult result = new AjaxResult();
		String m = materialReviewStatusChangeForm.getCause();
		if (accountUsers.getIsApprovePhone() != null && !accountUsers.getIsApprovePhone().equals("")) {
			if (accountUsers.getIsApprovePhone().equals(BigDecimal.ONE)) {
				String tel = accountUsers.getUserInfoPerson().getPhoneNo();
				SMSSender.sendMessage(tel, m);
				result.setSuccess(true);
				result.setMsg("短信已经发送成功!");
			}
		} else {
			result.setSuccess(true);
			result.setMsg("此人手机未验证,短信发送失败!");
		}
		// 保存到数据库
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		sysMsg.setUserId(accountUsers.getUserId());
		sysMsg.setWordId(BigDecimal.valueOf(19));
		sysMsg.setParameter1(accountUsers.getLoginName());
		int index = m.indexOf("您好：");
		if (index == 0) {
			m = m.substring(3);
		}
		sysMsg.setParameter2(m);
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		sysMsgDao.save(sysMsg);
		return result;
	}

	@LogInfo(operateKind = OperateKind.审核, operateContent = "用户资料审核需补充资料")
	public AjaxResult auditReplenish(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		AccountUsersAdmin accountUsers = auditUserDao.findOne(materialReviewStatusChangeForm.getUserId());
		accountUsers.setMaterialReviewStatus(new BigDecimal(4));
		MaterialReviewStatusChangeAdmin materialReviewStatus = new MaterialReviewStatusChangeAdmin();
		Staff staff = new Staff();
		staff.setId(staffService.getCurrentStaff().getId());
		materialReviewStatus.setUserId(materialReviewStatusChangeForm.getUserId());
		materialReviewStatus.setOperateNode(materialReviewStatusChangeForm.getOperateNode());
		materialReviewStatus.setOperateTime(new Date());
		materialReviewStatus.setStaff(staff);
		materialReviewStatus.setKind("3");
		materialReviewStatus.setCause(materialReviewStatusChangeForm.getCause());
		AjaxResult result = new AjaxResult();
		logger.info("SAVE: 资料审核状态变更记录 || MATERIAL_REVIEW_STATUS_CHANGE  userId= " + materialReviewStatusChangeForm.getUserId() + " OperateNode=" + materialReviewStatusChangeForm.getOperateNode() + " Kind=3" + " Cause=" + materialReviewStatusChangeForm.getCause());
		materialNoteDao.save(materialReviewStatus);
		String m = materialReviewStatusChangeForm.getCause();
		if (accountUsers.getIsApprovePhone() != null && !accountUsers.getIsApprovePhone().equals("")) {
			if (accountUsers.getIsApprovePhone().equals(BigDecimal.ONE)) {
				String tel = accountUsers.getUserInfoPerson().getPhoneNo();
				SMSSender.sendMessage(tel, m);
				result.setSuccess(true);
				result.setMsg("短信已经发送成功!");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("此人手机未验证,短信发送失败!");
		}

		// 保存到数据库
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		sysMsg.setUserId(accountUsers.getUserId());
		sysMsg.setWordId(BigDecimal.valueOf(20));
		sysMsg.setParameter1(accountUsers.getLoginName());
		int index = m.indexOf("您好：");
		if (index == 0) {
			m = m.substring(3);
		}
		sysMsg.setParameter2(m);
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		sysMsgDao.save(sysMsg);

		return result;
	}

	// 退回至待审
	@LogInfo(operateKind = OperateKind.审核, operateContent = "用户资料审核被退回至待审")
	public void auditReturnToWait(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		AccountUsersAdmin accountUsers = auditUserDao.findOne(materialReviewStatusChangeForm.getUserId());
		accountUsers.setMaterialReviewStatus(new BigDecimal(0));
		MaterialReviewStatusChangeAdmin materialReviewStatus = new MaterialReviewStatusChangeAdmin();
		Staff staff = new Staff();
		staff.setId(staffService.getCurrentStaff().getId());
		materialReviewStatus.setUserId(materialReviewStatusChangeForm.getUserId());
		materialReviewStatus.setOperateNode(materialReviewStatusChangeForm.getOperateNode());
		materialReviewStatus.setOperateTime(new Date());
		materialReviewStatus.setStaff(staff);
		materialReviewStatus.setKind("1");
		materialReviewStatus.setCause(materialReviewStatusChangeForm.getCause());
		logger.info("SAVE: 资料审核状态变更记录 || MATERIAL_REVIEW_STATUS_CHANGE  userId= " + materialReviewStatusChangeForm.getUserId() + " OperateNode=" + materialReviewStatusChangeForm.getOperateNode() + " Kind=1" + " Cause=" + materialReviewStatusChangeForm.getCause());
		materialNoteDao.save(materialReviewStatus);
	}

	// 退回至初定
	@LogInfo(operateKind = OperateKind.审核, operateContent = "用户资料审核被退回至初定")
	public void auditReturnToFirst(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		AccountUsersAdmin accountUsers = auditUserDao.findOne(materialReviewStatusChangeForm.getUserId());
		accountUsers.setMaterialReviewStatus(new BigDecimal(1));
		MaterialReviewStatusChangeAdmin materialReviewStatus = new MaterialReviewStatusChangeAdmin();
		Staff staff = new Staff();
		staff.setId(staffService.getCurrentStaff().getId());
		materialReviewStatus.setUserId(materialReviewStatusChangeForm.getUserId());
		materialReviewStatus.setOperateNode(materialReviewStatusChangeForm.getOperateNode());
		materialReviewStatus.setOperateTime(new Date());
		materialReviewStatus.setStaff(staff);
		materialReviewStatus.setKind("1");
		materialReviewStatus.setCause(materialReviewStatusChangeForm.getCause());
		logger.info("SAVE: 资料审核状态变更记录 || MATERIAL_REVIEW_STATUS_CHANGE  userId= " + materialReviewStatusChangeForm.getUserId() + " OperateNode=" + materialReviewStatusChangeForm.getOperateNode() + " Kind=1" + " Cause=" + materialReviewStatusChangeForm.getCause());
		materialNoteDao.save(materialReviewStatus);
	}

	public String getSysCodeName(BigDecimal codeId) {
		String codeName = sysCodeDao.findByCodeId(codeId).getCodeName();
		return codeName;
	}

	private BigDecimal formmatCreditGrade(int score) {
		BigDecimal creditGrade;
		String grade = "";
		if (score >= 0 && score <= 99) {
			grade = "1";
		} else if (score >= 100 && score <= 109) {
			grade = "2";
		} else if (score >= 110 && score <= 119) {
			grade = "3";
		} else if (score >= 120 && score <= 129) {
			grade = "4";
		} else if (score >= 130 && score <= 144) {
			grade = "5";
		} else if (score >= 145 && score <= 159) {
			grade = "6";
		} else if (score >= 160) {
			grade = "7";
		}
		creditGrade = new BigDecimal(grade);
		return creditGrade;
	}
}
