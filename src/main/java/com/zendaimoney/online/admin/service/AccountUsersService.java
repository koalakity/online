package com.zendaimoney.online.admin.service;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.fileupload.FileItem;
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
import com.zendaimoney.online.admin.dao.account.AccountUsersDao;
import com.zendaimoney.online.admin.dao.account.AdminID5CheckDao;
import com.zendaimoney.online.admin.dao.account.AdminMemoNoteDao;
import com.zendaimoney.online.admin.dao.account.AdminPrivatePropritorDao;
import com.zendaimoney.online.admin.dao.account.AdminUserInfoJobDao;
import com.zendaimoney.online.admin.dao.account.AdminUserInfoPersonDao;
import com.zendaimoney.online.admin.dao.account.AreaDao;
import com.zendaimoney.online.admin.dao.fundDetail.LedgerDao;
import com.zendaimoney.online.admin.dao.loan.InvestManagerDao;
import com.zendaimoney.online.admin.dao.loan.LoanManagerDao;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.admin.entity.account.AccountMemoNoteAdmin;
import com.zendaimoney.online.admin.entity.account.AccountPrivatePropritorAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUserInfoJobAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.account.ID5Check;
import com.zendaimoney.online.admin.entity.fundDetail.AcTLedgerAdmin;
import com.zendaimoney.online.admin.entity.fundDetail.FinancialAcTCustomerAdmin;
import com.zendaimoney.online.admin.entity.loan.InvestInfoAdmin;
import com.zendaimoney.online.admin.entity.loan.LoanInfoAdmin;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.UserListForm;
import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.ConstSubject;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.NewConstSubject;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.TypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.AcTCustomerDAO;
import com.zendaimoney.online.dao.AcTLedgerDAO;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.loanmanagement.LoanManagementAcTLedgerDao;
import com.zendaimoney.online.dao.loanmanagement.LoanmanagementAcTFlowDao;
import com.zendaimoney.online.dao.register.UserDao;
import com.zendaimoney.online.entity.AcTFlowVO;
import com.zendaimoney.online.entity.AcTLedgerVO;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTFlow;
import com.zendaimoney.online.entity.loanManagement.LoanManagementAcTLedger;
import com.zendaimoney.online.entity.register.RegisterUsers;
import com.zendaimoney.online.oii.id5.client.ID5WebServiceClient;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;
import com.zendaimoney.online.service.common.FlowUtils;

@Service
@Transactional
public class AccountUsersService {
	private static Logger logger = LoggerFactory.getLogger(AccountUsersService.class);
	@Autowired
	private AcTLedgerDAO acTLedgerDAO;
	@Autowired
	private AccountUsersDao accountUsersDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private AdminUserInfoPersonDao userInfoPersonDao;
	@Autowired
	private AcTCustomerDAO acTCustomerDAO;
	@Autowired
	private AdminUserInfoJobDao userInfoJobDao;
	@Autowired
	private AdminPrivatePropritorDao privatePropritorDao;
	@Autowired
	private AdminMemoNoteDao memoNoteDao;
	@Autowired
	private StaffService staffService;
	@Autowired
	private AdminID5CheckDao id5CheckDao;
	@Autowired
	private ChannelDAO channelSelfDAO;
	@Autowired
	private LoanManagerDao loanManagerDao;
	@Autowired
	UserDao userDao;
	@Autowired
	private InvestManagerDao investManagerDao;
	@Autowired
	private FlowUtils flowUtils;

	/**
	 * ID5验证需要，调用前台Dao
	 * */
	@Autowired
	private LoanManagementAcTLedgerDao loanManagementActLegerDao;
	@Autowired
	LoanmanagementAcTFlowDao actFlowDao;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private LedgerDao ledgerDao;
	@Autowired
	private RateService rateService;

	// public Page<AccountUsersAdmin> findAccountUsersPageByChannelInfos(final
	// String channelFId,final String channelCId,final UserListForm
	// userListForm, Pageable pageable) {
	// return accountUsersDao.findAll(new Specification<AccountUsersAdmin>() {
	// @Override
	// public Predicate toPredicate(Root<AccountUsersAdmin> root,
	// CriteriaQuery<?> query, CriteriaBuilder cb) {
	// List<Predicate> predicates = new ArrayList<Predicate>();
	//
	// if (!"".equals(userListForm.getLoginName()) &&
	// userListForm.getLoginName() != null) {
	// Path<String> realName = root.get("userInfoPerson").get("realName");
	// predicates.add(cb.like(realName, "%" + userListForm.getLoginName() +
	// "%"));
	// }
	// if (!"".equals(userListForm.getEmail()) && userListForm.getEmail() !=
	// null) {
	// Path<String> email = root.get("email");
	// predicates.add(cb.like(email, "%" + userListForm.getEmail() + "%"));
	// }
	// if (!"".equals(userListForm.getPhoneNo()) && userListForm.getPhoneNo() !=
	// null) {
	// predicates.add(cb.equal(root.get("userInfoPerson").get("phoneNo"),
	// userListForm.getPhoneNo()));
	// }
	// if (userListForm.getUserState() != null) {
	// predicates.add(cb.equal(root.get("userStatus"),
	// userListForm.getUserState()));
	// }
	//
	// if (userListForm.getRegType() != null) {
	// predicates.add(cb.equal(root.get("regType"), userListForm.getRegType()));
	// }
	// /*
	// * 如果一级渠道不为空
	// * 二级渠道不为空，根据二级渠道ID查询
	// * 二级渠道为空，根据一级渠道查询
	// * 如果一级渠道为空
	// * 查询所有
	// * */
	//
	// //一级渠道名称不为空
	// if (!"".equals(channelFId) && channelFId != null) {
	//
	// //二级渠道不为空，根据二级渠道查询
	// if(!"".equals(userListForm.getChannelCId()) &&
	// userListForm.getChannelCId() != null){
	// predicates.add(cb.equal(root.get("channelInfo").get("code"),
	// userListForm.getChannelCId()));
	// }else{//二级渠道为空，则查询一级渠道下所有二级渠道
	// // predicates.add(cb.equal(root.get("channelInfo").get("code"),
	// userListForm.getChannelCId()));
	// // In<Object> path=cb.in(root.get("channelInfo").get("code"));
	//
	// List<ChannelInfoVO> ChannelInfoList=channelSelfDAO.findChildListById();
	//
	//
	// List<String> name =new ArrayList<String>();
	// predicates.add(root.get("channelInfo").get("code").in(name));
	//
	//
	// }
	// }
	//
	//
	// // SELECT * FROM CHANNEL_INFO C WHERE C.NAME='二级名称' AND
	// C.PARAENT.ID=(SELECT * FROM CHANNEL_INFO CVO WHERE CVO.NAME='一级名称' AND
	// CODE IS NULL OR CODE ='' )
	//
	//
	// Path<Date> regTime = root.get("regTime");
	// if (userListForm.getRegDateMin() != null) {
	// predicates.add(cb.greaterThanOrEqualTo(regTime,
	// userListForm.getRegDateMin()));
	// }
	// if (userListForm.getRegDateMax() != null) {
	// userListForm.getRegDateMax().setHours(23);
	// userListForm.getRegDateMax().setMinutes(59);
	// userListForm.getRegDateMax().setSeconds(59);
	// predicates.add(cb.lessThanOrEqualTo(regTime,
	// userListForm.getRegDateMax()));
	// }
	// if (userListForm.getIsApproveCard() != null) {
	// predicates.add(cb.equal(root.get("isApproveCard"),
	// userListForm.getIsApproveCard()));
	// }
	// predicates.add(cb.notEqual(root.get("userStatus"), 4));
	// predicates.add(cb.equal(root.get("delStatus"), BigDecimal.ZERO));
	// query.where(predicates.toArray(new Predicate[predicates.size()]));
	// return null;
	// }
	// }, pageable);
	// }
	public Page<AccountUsersAdmin> findAccountUsersPage(final UserListForm userListForm, Pageable pageable) {
		return accountUsersDao.findAll(new Specification<AccountUsersAdmin>() {
			@Override
			public Predicate toPredicate(Root<AccountUsersAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (!"".equals(userListForm.getRealName()) && userListForm.getRealName() != null) {
					Path<String> realName = root.get("userInfoPerson").get("realName");
					predicates.add(cb.like(realName, "%" + userListForm.getRealName() + "%"));
				}
				if (!"".equals(userListForm.getEmail()) && userListForm.getEmail() != null) {
					Path<String> email = root.get("email");
					predicates.add(cb.like(email, "%" + userListForm.getEmail() + "%"));
				}
				if (!"".equals(userListForm.getPhoneNo()) && userListForm.getPhoneNo() != null) {
					predicates.add(cb.equal(root.get("userInfoPerson").get("phoneNo"), userListForm.getPhoneNo()));
				}
				if (userListForm.getUserState() != null) {
					predicates.add(cb.equal(root.get("userStatus"), userListForm.getUserState()));
				}

				if (userListForm.getRegType() != null) {
					predicates.add(cb.equal(root.get("regType"), userListForm.getRegType()));
				}

				if (!"".equals(userListForm.getLoginName()) && userListForm.getLoginName() != null) {
					Path<String> loginName = root.get("loginName");
					predicates.add(cb.like(loginName, "%" + userListForm.getLoginName() + "%"));
				}
				/*
				 * 如果一级渠道不为空 二级渠道不为空，根据二级渠道ID查询 二级渠道为空，根据一级渠道查询 如果一级渠道为空 查询所有
				 */

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

				Path<Date> regTime = root.get("regTime");
				if (userListForm.getRegDateMin() != null) {
					predicates.add(cb.greaterThanOrEqualTo(regTime, userListForm.getRegDateMin()));
				}
				if (userListForm.getRegDateMax() != null) {
					userListForm.getRegDateMax().setHours(23);
					userListForm.getRegDateMax().setMinutes(59);
					userListForm.getRegDateMax().setSeconds(59);
					predicates.add(cb.lessThanOrEqualTo(regTime, userListForm.getRegDateMax()));
				}
				if (userListForm.getIsApproveCard() != null) {
					predicates.add(cb.equal(root.get("isApproveCard"), userListForm.getIsApproveCard()));
				}
				predicates.add(cb.notEqual(root.get("userStatus"), 4));
				predicates.add(cb.equal(root.get("delStatus"), BigDecimal.ZERO));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	public void reportUsers(Long[] userIds) {
		for (Long id : userIds) {
			AccountUsersAdmin accountUsers = accountUsersDao.findOne(new BigDecimal(id));
			accountUsers.setUserStatus(new BigDecimal(7));
		}
	}

	@LogInfo(operateKind = OperateKind.锁定, operateContent = "锁定用户")
	public void lockUsers(Long[] userIds) {
		for (Long id : userIds) {
			AccountUsersAdmin accountUsers = accountUsersDao.findOne(new BigDecimal(id));
			BigDecimal returnStat = accountUsers.getUserStatus();
			accountUsers.setReturnStatus(returnStat);
			accountUsers.setUserStatus(new BigDecimal(6));
		}
	}

	@LogInfo(operateKind = OperateKind.解锁, operateContent = "解除锁定用户")
	public void unLockUsers(Long[] userIds) {
		for (Long id : userIds) {
			AccountUsersAdmin accountUsers = accountUsersDao.findOne(new BigDecimal(id));
			BigDecimal oldStat = accountUsers.getReturnStatus();
			accountUsers.setUserStatus(oldStat);
		}
	}

	@LogInfo(operateKind = OperateKind.删除, operateContent = "删除用户")
	public AjaxResult removeUsers(Long[] userIds) {
		boolean flag = true;
		boolean flagA = true;
		AjaxResult result = new AjaxResult();
		StringBuilder sb = new StringBuilder(10);
		for (Long id : userIds) {
			AccountUsersAdmin accountUsersAdmin = accountUsersDao.findOne(new BigDecimal(id));
			List<LoanInfoAdmin> loanInfoList = loanManagerDao.findByAccountUsersUserId(new BigDecimal(id));
			// 当前借款人相关的借款是否存在招标中、满标、还款中、逾期、高级逾期，若存在将不能删除
			for (LoanInfoAdmin loanInfo : loanInfoList) {
				if (loanInfo.getStatus().equals(new BigDecimal(1)) || loanInfo.getStatus().equals(new BigDecimal(2)) || loanInfo.getStatus().equals(new BigDecimal(4)) || loanInfo.getStatus().equals(new BigDecimal(6)) || loanInfo.getStatus().equals(new BigDecimal(7))) {
					flag = false;
					flagA = false;
				}
			}
			List<InvestInfoAdmin> investInfoList = investManagerDao.getInvestInfo(new BigDecimal(id));
			// 当前理财人相关的借款是否存在招标中、还款中，若存在将不能删除用户
			for (InvestInfoAdmin investInfo : investInfoList) {
				if (investInfo.getStatus().equals(new BigDecimal(3)) || investInfo.getStatus().equals(new BigDecimal(2))) {
					flag = false;
					flagA = false;
				}
			}
			AcTLedgerAdmin actLedger4 = ledgerDao.findByFinancialAcTCustomerAdminAndBusiType(accountUsersAdmin.getFinancialAcTCustomerAdmin(), "4");
			AcTLedgerAdmin actLedger5 = ledgerDao.findByFinancialAcTCustomerAdminAndBusiType(accountUsersAdmin.getFinancialAcTCustomerAdmin(), "5");
			double amount = actLedger4.getAmount() + actLedger5.getInvestAmount();
			if (amount > 0) {
				flag = false;
				flagA = false;
			}
			// 存在在途借款的用户是,资金相关操作
			if (!flag) {
				sb.append(accountUsersAdmin.getLoginName());
				sb.append(",");
				flag = true;
			}

		}
		if (flagA) {
			for (Long id : userIds) {
				AccountUsersAdmin accountUsers = accountUsersDao.findOne(new BigDecimal(id));
				accountUsers.setDelStatus(BigDecimal.ONE);
			}
			result.setSuccess(true);
			result.setMsg("删除成功！");
		} else {
			result.setSuccess(false);
			String msg = "";
			// if (sb.length() > 0) {
			// msg = "删除失败，昵称为" + sb.toString().substring(0, sb.length() - 1) +
			// "存在在途借款，不能删除！";
			// }
			if (sb.length() > 0) {
				msg = "删除失败，昵称为" + sb.toString().substring(0, sb.length() - 1) + "的账户目前还有资金相关操作未处理!";
				;
			}
			result.setMsg(msg);
		}

		return result;
	}

	public Page<AccountUsersAdmin> findRecycleUserPage(final UserListForm userListForm, PageRequest pageRequest) {
		return accountUsersDao.findAll(new Specification<AccountUsersAdmin>() {
			@Override
			public Predicate toPredicate(Root<AccountUsersAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (!"".equals(userListForm.getLoginName()) && userListForm.getLoginName() != null) {
					Path<String> realName = root.get("userInfoPerson").get("realName");
					predicates.add(cb.like(realName, "%" + userListForm.getLoginName() + "%"));
				}
				if (!"".equals(userListForm.getEmail()) && userListForm.getEmail() != null) {
					Path<String> email = root.get("email");
					predicates.add(cb.like(email, "%" + userListForm.getEmail() + "%"));
				}
				if (!"".equals(userListForm.getPhoneNo()) && userListForm.getPhoneNo() != null) {
					predicates.add(cb.equal(root.get("userInfoPerson").get("phoneNo"), userListForm.getPhoneNo()));
				}
				if (userListForm.getUserState() != null) {
					predicates.add(cb.equal(root.get("userStatus"), userListForm.getUserState()));
				}

				if (userListForm.getRegType() != null) {
					predicates.add(cb.equal(root.get("regType"), userListForm.getRegType()));
				}
				Path<Date> regTime = root.get("regTime");
				if (userListForm.getRegDateMin() != null) {
					predicates.add(cb.greaterThanOrEqualTo(regTime, userListForm.getRegDateMin()));
				}
				if (userListForm.getRegDateMax() != null) {
					userListForm.getRegDateMax().setHours(23);
					userListForm.getRegDateMax().setMinutes(59);
					userListForm.getRegDateMax().setSeconds(59);
					predicates.add(cb.lessThanOrEqualTo(regTime, userListForm.getRegDateMax()));
				}
				if (userListForm.getIsApproveCard() != null) {
					predicates.add(cb.equal(root.get("isApproveCard"), userListForm.getIsApproveCard()));
				}
				predicates.add(cb.equal(root.get("delStatus"), BigDecimal.ONE));

				/*
				 * 如果一级渠道不为空 二级渠道不为空，根据二级渠道ID查询 二级渠道为空，根据一级渠道查询 如果一级渠道为空 查询所有
				 */

				// 一级渠道名称不为空
				if (!"".equals(userListForm.getChannelFId()) && userListForm.getChannelFId() != null) {
					// 二级渠道不为空，根据二级渠道查询
					if (!"".equals(userListForm.getChannelCId()) && userListForm.getChannelCId() != null) {
						predicates.add(cb.equal(root.get("channelInfo").get("id"), userListForm.getChannelCId()));
					} else {
						logger.info("" + userListForm.getChannelFId());
						// 二级渠道为空，则查询一级渠道下所有二级渠道
						List<ChannelInfoVO> channelInfoList = channelSelfDAO.findChildListByParentId(Long.valueOf(userListForm.getChannelFId()));
						List<Long> idList = new ArrayList<Long>();
						for (ChannelInfoVO vo : channelInfoList) {
							idList.add(vo.getId());
						}
						predicates.add(root.get("channelInfo").get("id").in(idList));
					}
				}

				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageRequest);
	}

	public void recoverUsers(Long[] userIds) {
		for (Long id : userIds) {
			AccountUsersAdmin accountUsersAdmin = accountUsersDao.findOne(new BigDecimal(id));
			accountUsersAdmin.setDelStatus(BigDecimal.ZERO);
		}

	}

	/**
	 * @author Ray
	 * @date 2012-10-18 下午2:55:10
	 * @param userId
	 * @return description:根据userId查询对应的用户基础信息
	 */
	public List<AccountUsersAdmin> findByUserId(BigDecimal userId) {
		return accountUsersDao.findByUserId(userId);
	}

	/**
	 * @author Ray
	 * @date 2012-11-2 上午11:26:45
	 * @param user
	 * @return description:根据用户名查询备注资料
	 */
	public List<AccountMemoNoteAdmin> findMemoNoteByUserId(BigDecimal userId) {
		return memoNoteDao.findByUserId(userId);
	}

	/**
	 * @author Ray
	 * @date 2012-11-5 上午10:51:40
	 * @param memoNote
	 *            description:保存备注信息
	 */
	public void saveMemoNote(AccountMemoNoteAdmin memoNote) {
		AccountMemoNoteAdmin memoNote2 = new AccountMemoNoteAdmin();
		Staff staff = new Staff();
		staff.setId(staffService.getCurrentStaff().getId());
		memoNote2.setUserId(memoNote.getUserId());
		memoNote2.setMemoText(memoNote.getMemoText());
		memoNote2.setStaff(staff);
		memoNote2.setOperateTime(new Date());
		logger.info("SAVE:会员信息  || userId:" + memoNote.getUserId() + " memoText:" + memoNote.getMemoText() + "  staff.id:" + staff.getId());
		memoNoteDao.save(memoNote2);
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 上午10:06:57
	 * @param code
	 * @return description:
	 */
	public List getAreaList(String code) {
		return areaDao.queryAeraList(code);
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午2:10:42
	 * @param AccountUsersAdmin
	 *            user description:保存用户基础信息， USER_INFO_PERSON;
	 * @return
	 */
	public AccountUserInfoPersonAdmin savePersonalBase(AccountUsersAdmin user) {

		// BigDecimal userId= user.getUserId();
		if (user == null) {
			throw new RuntimeException("user对象为空!");
		}
		RegisterUsers users = userDao.findByUserId(user.getUserId());
		users.setChannelInfo(user.getChannelInfo());
		AccountUserInfoPersonAdmin userInfoPersion = user.getUserInfoPerson();
		AccountUserInfoPersonAdmin person = null;

		List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByInfoId(user.getUserInfoPerson().getInfoId());
		if (AccountUserInfoPersonList.size() > 0) {
			person = AccountUserInfoPersonList.get(0);
		}
		if (person == null) {
			person = new AccountUserInfoPersonAdmin();

		}
		/*
		 * if(!StringUtils.isEmpty(getIconPath())){
		 * person.setHeadPath(baseVO.getIconPath()+"/icon.jpg"); }
		 */
		person.setInfoId(person.getInfoId());
		person.setUserId(user.getUserId());
		// 如果已通过ID5验证，代表真实姓名、身份证、性别、证件种类已填
		if (!(BigDecimal.ONE).equals(user.getIsApproveCard())) {
			person.setRealName(userInfoPersion.getRealName());
			person.setIdentityNo(userInfoPersion.getIdentityNo());
		}
		person.setSex(userInfoPersion.getSex());
		person.setCredentialKind(userInfoPersion.getCredentialKind());
		person.setPhoneNo(userInfoPersion.getPhoneNo());
		person.setHometownArea(userInfoPersion.getHometownArea());
		person.setHometownCity(userInfoPersion.getHometownCity());
		person.setLiveAddress(userInfoPersion.getLiveAddress());
		person.setLivePhoneArea(userInfoPersion.getLivePhoneArea());
		person.setLivePhoneNo(userInfoPersion.getLivePhoneNo());
		person.setPostCode(userInfoPersion.getPostCode());
		person.setIsapproveMarry(userInfoPersion.getIsapproveMarry());
		person.setMonthIncome(userInfoPersion.getMonthIncome());
		person.setFamilyMembersName(userInfoPersion.getFamilyMembersName());
		person.setFamilyRelation(userInfoPersion.getFamilyRelation());
		person.setFamilyMemberPhone(userInfoPersion.getFamilyMemberPhone());
		person.setOtherContactName(userInfoPersion.getOtherContactName());
		person.setOtherRelation(userInfoPersion.getOtherRelation());
		person.setOtherContactPhone(userInfoPersion.getOtherContactPhone());
		person.setThreeContactName(userInfoPersion.getThreeContactName());
		person.setThreeContactRelation(userInfoPersion.getThreeContactRelation());
		person.setThreeContactPhone(userInfoPersion.getThreeContactPhone());
		person.setFourthContactName(userInfoPersion.getFourthContactName());
		person.setFourthContactRelation(userInfoPersion.getFourthContactRelation());
		person.setFourthContactPhone(userInfoPersion.getFourthContactPhone());
		person.setLivePhoneArea(userInfoPersion.getLivePhoneArea());// 区号
		person.setLivePhoneNo(userInfoPersion.getLivePhoneNo());// 电话号码
		person.setPostCode(userInfoPersion.getPostCode());// 邮编
		person.setIsapproveHavechild(userInfoPersion.getIsapproveHavechild());// 有无孩子
		person.setQqNo(userInfoPersion.getQqNo());// QQ
		person.setMsnNo(userInfoPersion.getMsnNo());// MSN
		person.setSinaWeiboAccount(userInfoPersion.getSinaWeiboAccount());// 微博
		userDao.save(users);
		logger.info("SAVE:USER_INFO_PERSON || userId=" + user.getUserId() + " infoId=" + person.getInfoId() + " realName=" + userInfoPersion.getRealName() + " IdentityNo=" + userInfoPersion.getIdentityNo() + " Sex=" + userInfoPersion.getSex() + " CredentialKind=" + userInfoPersion.getCredentialKind() + " PhoneNo=" + userInfoPersion.getPhoneNo() + " HometownArea="
				+ userInfoPersion.getHometownArea() + " HometownCity=" + userInfoPersion.getHometownCity() + " LiveAddress=" + userInfoPersion.getLiveAddress() + " LivePhoneArea=" + userInfoPersion.getLivePhoneArea() + " LivePhoneNo=" + userInfoPersion.getLivePhoneNo() + " PostCode=" + userInfoPersion.getPostCode() + " IsapproveMarry=" + userInfoPersion.getIsapproveMarry()
				+ " MonthIncome=" + userInfoPersion.getMonthIncome() + " FamilyMembersName=" + userInfoPersion.getFamilyMembersName() + " FamilyRelation=" + userInfoPersion.getFamilyRelation() + " FamilyMemberPhone=" + userInfoPersion.getFamilyMemberPhone() + " OtherContactName=" + userInfoPersion.getOtherContactName() + " OtherRelation=" + userInfoPersion.getOtherRelation()
				+ " OtherContactPhone=" + userInfoPersion.getOtherContactPhone() + " ThreeContactName=" + userInfoPersion.getThreeContactName() + " ThreeContactRelation=" + userInfoPersion.getThreeContactRelation() + " ThreeContactPhone=" + userInfoPersion.getThreeContactPhone() + " FourthContactName=" + userInfoPersion.getFourthContactName() + " FourthContactRelation="
				+ userInfoPersion.getFourthContactRelation() + " FourthContactPhone=" + userInfoPersion.getFourthContactPhone() + " setLivePhoneArea=" + userInfoPersion.getLivePhoneArea() + " LivePhoneNo=" + userInfoPersion.getLivePhoneNo() + " PostCode=" + userInfoPersion.getPostCode() + " IsapproveHavechild=" + userInfoPersion.getIsapproveHavechild() + " QqNo="
				+ userInfoPersion.getQqNo() + " MsnNo=" + userInfoPersion.getMsnNo() + " SinaWeiboAccount=" + userInfoPersion.getSinaWeiboAccount());
		return userInfoPersonDao.save(person);
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:33:32
	 * @param user
	 *            description:固定资产插库
	 */
	public void savePersonalFixed(AccountUsersAdmin user) {
		if (user == null) {
			throw new RuntimeException("user对象为空!");
		}
		AccountUserInfoPersonAdmin userInfoPersion = user.getUserInfoPerson();
		AccountUserInfoPersonAdmin person = null;

		List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByInfoId(user.getUserInfoPerson().getInfoId());
		if (AccountUserInfoPersonList.size() > 0) {
			person = AccountUserInfoPersonList.get(0);
		}
		if (person == null) {
			person = new AccountUserInfoPersonAdmin();
		}
		person.setInfoId(person.getInfoId());
		person.setUserId(user.getUserId());
		person.setIsapproveHaveHouse(userInfoPersion.getIsapproveHaveHouse());// 是否有房
		person.setHousePropertyAddress(userInfoPersion.getHousePropertyAddress());// 房产地址
		person.setIsapproveHouseMortgage(userInfoPersion.getIsapproveHouseMortgage());// 自有购房
		person.setHouseMonthMortgage(userInfoPersion.getHouseMonthMortgage());// 房产每月按揭金额
		person.setIsapproveHaveCar(userInfoPersion.getIsapproveHaveCar());// 是否有车
		person.setCarBrand(userInfoPersion.getCarBrand());// 汽车品牌
		person.setCarYears(userInfoPersion.getCarYears());// 购车年份
		person.setCarNo(userInfoPersion.getCarNo());// 车牌号码
		person.setIsapproveCarMortgage(userInfoPersion.getIsapproveCarMortgage());// 自有汽车
		person.setCarMonthMortgage(userInfoPersion.getCarMonthMortgage());// 汽车每月按揭金额

		logger.info("SAVE:USER_INFO_PERSON || userId=" + user.getUserId() + " infoId=" + person.getInfoId() + " IsapproveHaveHouse=" + userInfoPersion.getIsapproveHaveHouse() + " HousePropertyAddress=" + userInfoPersion.getHousePropertyAddress() + " IsapproveHouseMortgage=" + userInfoPersion.getIsapproveHouseMortgage() + " HouseMonthMortgage="
				+ userInfoPersion.getHouseMonthMortgage() + " IsapproveHaveCar=" + userInfoPersion.getIsapproveHaveCar() + " CarBrand=" + userInfoPersion.getCarBrand() + " CarYears=" + userInfoPersion.getCarYears() + " CarNo=" + userInfoPersion.getCarNo() + " IsapproveCarMortgage=" + userInfoPersion.getIsapproveCarMortgage() + " CarMonthMortgage="
				+ userInfoPersion.getCarMonthMortgage());
		userInfoPersonDao.save(person);
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:33:32
	 * @param user
	 *            description:保存头像路径
	 */
	public void saveHeadPath(BigDecimal userId, String headPath) {
		AccountUserInfoPersonAdmin person = null;
		List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByUserId(userId);
		if (AccountUserInfoPersonList.size() > 0) {
			person = AccountUserInfoPersonList.get(0);
		}
		if (person == null) {
			person = new AccountUserInfoPersonAdmin();
		}
		person.setUserId(userId);
		// person.setInfoId(person.getInfoId());
		person.setHeadPath(headPath);
		logger.info("SAVE:USER_INFO_PERSON || userId=" + userId + " HeadPath=" + headPath);
		userInfoPersonDao.save(person);
	}

	/**
	 * @author Ray
	 * @date 2012-11-8 上午10:36:19
	 * @param user
	 * @param request
	 * @return description:显示图片
	 */
	public AccountUserInfoPersonAdmin showImg(String userId) {
		AccountUserInfoPersonAdmin person = null;
		if ("".equals(userId) || userId == null) {
			return new AccountUserInfoPersonAdmin();
		}
		List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByUserId(BigDecimal.valueOf(Long.parseLong(userId)));
		if (AccountUserInfoPersonList.size() > 0) {
			person = AccountUserInfoPersonList.get(0);
		}
		return person;
	}

	/**
	 * @author Ray
	 * @date 2012-11-8 上午11:58:22
	 * @param userId
	 *            description:
	 */
	@Transactional(readOnly = false)
	public String updataHeadPhoto(List<FileItem> items, BigDecimal userId) {
		List<AccountUsersAdmin> userList = accountUsersDao.findByUserId(userId);
		AccountUsersAdmin user = null;
		if (userList.size() > 0) {
			user = userList.get(0);
		}
		String userEmail = user.getEmail();// 获取Email地址
		AccountUserInfoPersonAdmin person = user.getUserInfoPerson();// 获取userInfoPerson对象
		if (person == null) {
			person = new AccountUserInfoPersonAdmin();
		}
		person.setUserId(userId);
		File file = new File("");
		try {
			String fileName = DateUtil.getStrDate2(new Date());
			Iterator<FileItem> iter = items.iterator();
			// TODO 这里的路径有可能变化
			String path = getHeadIconPath() + userEmail + "/userHeadImg";

			// 文件夹不存在就自动创建：
			File filea = new File(path);
			if (!(filea.exists()) && !(filea.isDirectory())) {
				filea.mkdirs();
			}
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField() == false) {
					// 获得byte数组
					// byte[] bytes = item.get();
					// 或直接保存成文件
					String name = item.getName();
					// 验证文件后缀 开始
					String[] _names = name.split("\\.");
					// jpg、png、gif、bmp
					if (!_names[_names.length - 1].trim().equalsIgnoreCase("jpg") && !_names[_names.length - 1].trim().equalsIgnoreCase("png") && !_names[_names.length - 1].trim().equalsIgnoreCase("gif") && !_names[_names.length - 1].trim().equalsIgnoreCase("bmp")) {
						logger.info("上传的头像文件后缀不是图片格式，文件名为:" + name);
						// 不是图片后缀的文件
						return "false";
					}
					int point = name.lastIndexOf(".");
					String hz = name.substring(point);
					file = new File(path, fileName + hz);
					Pattern p = Pattern.compile("'/'");
					Matcher m = p.matcher(path);
					path = userEmail + "/userHeadImg" + "/" + fileName + hz;
					person.setHeadPath(path);
					item.write(file);// 直接保存文件
				} else {

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (file.isFile() && file.exists()) {
			logger.info("SAVE:USER_INFO_PERSON || userId=" + userId + " HeadPath=" + person.getHeadPath());
			userInfoPersonDao.save(person);
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:33:32
	 * @param user
	 *            description:教育职称插库
	 */
	public void savePersonalEdu(AccountUsersAdmin user) {
		if (user == null) {
			throw new RuntimeException("user对象为空!");
		}
		AccountUserInfoPersonAdmin userInfoPersion = user.getUserInfoPerson();
		AccountUserInfoPersonAdmin person = null;

		List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByInfoId(user.getUserInfoPerson().getInfoId());
		if (AccountUserInfoPersonList.size() > 0) {
			person = AccountUserInfoPersonList.get(0);
		}
		if (person == null) {
			person = new AccountUserInfoPersonAdmin();
		}
		person.setInfoId(person.getInfoId());
		person.setUserId(user.getUserId());
		person.setMaxDegree(userInfoPersion.getMaxDegree());// 最高学历
		person.setGraduatSchool(userInfoPersion.getGraduatSchool());// 毕业学校
		person.setInschoolTime(userInfoPersion.getInschoolTime());// 入学年份
		person.setJobTitleOne(userInfoPersion.getJobTitleOne());
		person.setJobTitleTwo(userInfoPersion.getJobTitleTwo());
		person.setJobTitleThr(userInfoPersion.getJobTitleThr());
		logger.info("SAVE:USER_INFO_PERSON || userId=" + user.getUserId() + " infoId=" + person.getInfoId() + " MaxDegree=" + userInfoPersion.getMaxDegree() + " GraduatSchool=" + userInfoPersion.getGraduatSchool() + " InschoolTime=" + userInfoPersion.getInschoolTime() + " JobTitleOne=" + userInfoPersion.getJobTitleOne() + " JobTitleTwo=" + userInfoPersion.getJobTitleTwo()
				+ " JobTitleThr=" + userInfoPersion.getJobTitleThr());
		userInfoPersonDao.save(person);
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:33:32
	 * @param user
	 *            description:财务状况
	 */
	public void savePersonalFinance(AccountUsersAdmin user) {
		if (user == null) {
			throw new RuntimeException("user对象为空!");
		}
		AccountUserInfoPersonAdmin userInfoPersion = user.getUserInfoPerson();
		AccountUserInfoPersonAdmin person = null;
		List<AccountUserInfoPersonAdmin> accountUserInfoPersonList = userInfoPersonDao.findByInfoId(user.getUserInfoPerson().getInfoId());
		if (accountUserInfoPersonList.size() > 0) {
			person = accountUserInfoPersonList.get(0);
		}
		if (person == null) {
			person = new AccountUserInfoPersonAdmin();
		}
		person.setInfoId(person.getInfoId());
		person.setUserId(user.getUserId());
		person.setMonthIncome(userInfoPersion.getMonthIncome());// 每月收入
		person.setMonthHousingLoan(userInfoPersion.getMonthHousingLoan());// 每月还房贷
		person.setMonthCarLoan(userInfoPersion.getMonthCarLoan());// 每月还车贷
		person.setMonthCreditCard(userInfoPersion.getMonthCreditCard());// 每月还信用卡
		person.setMonthMortgage(userInfoPersion.getMonthMortgage());// 每月抵押贷款还款金额
		logger.info("SAVE:USER_INFO_PERSON || userId=" + user.getUserId() + " infoId=" + person.getInfoId() + " MonthIncome=" + userInfoPersion.getMonthIncome() + " MonthHousingLoan=" + userInfoPersion.getMonthHousingLoan() + " MonthCarLoan=" + userInfoPersion.getMonthCarLoan() + "MonthCreditCard=" + userInfoPersion.getMonthCreditCard() + " MonthMortgage="
				+ userInfoPersion.getMonthMortgage());
		userInfoPersonDao.save(person);
	}

	/**
	 * @author Ray
	 * @date 2012-10-24 上午9:49:06
	 * @param user
	 *            description:用户工作情况保存
	 */
	public void savePersonalJob(AccountUsersAdmin user) {
		if (user == null) {
			throw new RuntimeException("user对象为空!");
		}
		AccountUserInfoJobAdmin userInfoJob = user.getUserInfoJob();
		AccountUserInfoJobAdmin userJob = null;
		List<AccountUserInfoJobAdmin> accountUserInfoJobAdmin = userInfoJobDao.findByWorkInfoId(user.getUserInfoJob().getWorkInfoId());
		if (accountUserInfoJobAdmin.size() > 0) {
			userJob = accountUserInfoJobAdmin.get(0);
		}
		if (userJob == null) {
			userJob = new AccountUserInfoJobAdmin();
		}
		userJob.setCorporationName(userInfoJob.getCorporationName());// 公司名称
		userJob.setCorporationKind(userInfoJob.getCorporationKind());// 公司类别
		userJob.setCorporationIndustry(userInfoJob.getCorporationIndustry());// 公司行业
		userJob.setCorporationScale(userInfoJob.getCorporationScale());// 公司规模
		userJob.setPosition(userInfoJob.getPosition());// 职位
		userJob.setJobProvince(userInfoJob.getJobProvince());// 市
		userJob.setJobCity(userInfoJob.getJobCity());// 省
		userJob.setPresentCorporationWorkTime(userInfoJob.getPresentCorporationWorkTime());// 工作年限
		userJob.setCorporationPhoneArea(userInfoJob.getCorporationPhoneArea());// 公司电话区号
		userJob.setCorporationPhone(userInfoJob.getCorporationPhone());// 公司电话
		userJob.setJobEmail(userInfoJob.getJobEmail());// 工作邮箱
		userJob.setCorporationAddress(userInfoJob.getCorporationAddress());// 公司地址
		userJob.setCorporationWeb(userInfoJob.getCorporationWeb());// 公司网址
		userJob.setWorkInfoId(userInfoJob.getWorkInfoId());
		userJob.setUserId(user.getUserId());
		logger.info("SAVE:USER_INFO_JOB || userId=" + user.getUserId() + " CorporationName=" + userInfoJob.getCorporationName() + " CorporationKind=" + userInfoJob.getCorporationKind() + " CorporationIndustry=" + userInfoJob.getCorporationIndustry() + " CorporationScale=" + userInfoJob.getCorporationScale() + " Position=" + userInfoJob.getPosition() + " JobProvince="
				+ userInfoJob.getJobProvince() + " JobCity=" + userInfoJob.getJobCity() + " PresentCorporationWorkTime=" + userInfoJob.getPresentCorporationWorkTime() + " CorporationPhoneArea=" + userInfoJob.getCorporationPhoneArea() + " CorporationPhone=" + userInfoJob.getCorporationPhone() + " JobEmail=" + userInfoJob.getJobEmail() + " CorporationAddress="
				+ userInfoJob.getCorporationAddress() + " CorporationWeb=" + userInfoJob.getCorporationWeb() + " WorkInfoId=" + userInfoJob.getWorkInfoId());
		userInfoJobDao.save(userJob);
	}

	/**
	 * @author Ray
	 * @date 2012-10-24 上午10:36:08
	 * @param user
	 *            description:保存私营业主
	 */
	public void savePersonalPrivate(AccountUsersAdmin user) {
		if (user == null) {
			throw new RuntimeException("user对象为空!");
		}
		AccountPrivatePropritorAdmin privatePropritor = user.getPrivatePropritor();
		AccountPrivatePropritorAdmin pp = null;
		List<AccountPrivatePropritorAdmin> accountPrivatePropritor = privatePropritorDao.findByUserId(user.getUserId());
		if (accountPrivatePropritor.size() > 0) {
			pp = accountPrivatePropritor.get(0);
		}
		if (pp == null) {
			pp = new AccountPrivatePropritorAdmin();
			pp.setUserId(user.getUserId());
		}
		pp.setEnterpriseKind(privatePropritor.getEnterpriseKind());// 经营类型
		// System.out.println(privatePropritor.getEstablishDate());
		pp.setEstablishDate(privatePropritor.getEstablishDate());// 成立日期
		pp.setEnterpriseAddress(privatePropritor.getEnterpriseAddress());// 经营场所
		pp.setRent(privatePropritor.getRent());// 租金
		pp.setLease(privatePropritor.getLease());// 租期
		pp.setTaxationNo(privatePropritor.getTaxationNo());// 税务编号
		pp.setGsBookNo(privatePropritor.getGsBookNo());// 工商登记号
		pp.setProfitOrLossAmount(privatePropritor.getProfitOrLossAmount());// 全年盈利
		pp.setEmployeeAmount(privatePropritor.getEmployeeAmount());// 雇员人数

		logger.info("SAVE:PersonalPrivate || userId=" + user.getUserId() + " ID=" + privatePropritor.getId() + " EnterpriseKind=" + privatePropritor.getEnterpriseKind() + " EstablishDate=" + privatePropritor.getEstablishDate() + " EnterpriseAddress=" + privatePropritor.getEnterpriseAddress() + " Rent=" + privatePropritor.getRent() + " Lease=" + privatePropritor.getLease()
				+ " TaxationNo=" + privatePropritor.getTaxationNo() + " GsBookNo=" + privatePropritor.getGsBookNo() + " ProfitOrLossAmount=" + privatePropritor.getProfitOrLossAmount() + " EmployeeAmount=" + privatePropritor.getEmployeeAmount());
		privatePropritorDao.save(pp);

	}

	/**
	 * @author Ray
	 * @date 2012-10-26 上午10:12:18
	 * @param _user
	 *            description:获取ID5认证信息
	 */
	@Transactional(readOnly = false)
	public AjaxResult getID5(AccountUsersAdmin _user) {
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		AccountUsersAdmin user = null;
		List<AccountUsersAdmin> userList = findByUserId(_user.getUserId());
		if (userList.size() > 0) {
			user = userList.get(0);
		}
		// 身份证验证不为空
		if (user.getIsApproveCard() != null) {
			// 身份证验证为1，已验证
			if (user.getIsApproveCard() == BigDecimal.ONE) {
				result.setMsg("已经验证通过，不必再次验证！");
				// return "already";
				return result;
			} else {
				String identityNo = _user.getIdentityNo();
				List<ID5Check> id5List = id5CheckDao.findByCardId(identityNo);
				// 查询数据库中此身份证是否已经验证通过，被占用
				boolean flag = true;
				if (id5List.size() > 0) {
					for (int i = 0; i < id5List.size(); i++) {
						if ("3".equals(id5List.get(i).getCheckStatusCode())) {
							flag = false;
							result.setMsg("校验失败，该身份证号码已被绑定！");
							// return "occupied";
						}
					}
				}
				if (flag) {/* /查询数据库中此身份证是否已经验证通过，未被占用 */
					Long channelId = user.getChannelInfo().getId();
					double idFee = rateService.getId5Fee(channelId);
					// 调用当前资金是否大于对应的ID5验证费用元 modify by jihui 2013-03-21
					if (getAvailableBalance(user) >= idFee) {
						ID5Check id5Check = ID5WebServiceClient.checkID5All(_user.getRealName(), _user.getIdentityNo());
						id5Check.setUserId(_user.getUserId());
						if ("3".equals(id5Check.getCheckStatusCode()) || "2".equals(id5Check.getCheckStatusCode()) || "1".equals(id5Check.getCheckStatusCode()) || "-2".equals(id5Check.getCheckStatusCode())) {
							// 减去客户身份证验证的钱
							LoanManagementAcTLedger actledger = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", user.getFinancialAcTCustomerAdmin().getTotalAcct() + "%");
							actledger.setAmount(ArithUtil.sub(actledger.getAmount(), idFee));
							loanManagementActLegerDao.save(actledger);

							// 证大收取id5手续费
							LoanManagementAcTLedger act = loanManagementActLegerDao.findByBusiTypeAndAccountLike("3", ZendaiAccountBank.zendai_acct11);
							act.setAmount(ArithUtil.add(act.getAmount(), idFee));
							loanManagementActLegerDao.save(act);

							AcTLedgerVO act_3 = new AcTLedgerVO();
							act_3.setAccount(act.getAccount());
							AcTLedgerVO act_4 = getAvailableBalance(_user.getUserId().longValue());

							// 记录交易流水
							FinancialAcTCustomerAdmin acTCustomerVO = user.getFinancialAcTCustomerAdmin();
							AcTFlowVO tmp_act = flowUtils.saveAcTFlowVOAndAcTFlowClassifyVOForZD(_user.getUserId().longValue(), TradeTypeConstants.ID5, _user.getUserId().longValue(), new BigDecimal(idFee), acTCustomerVO.getOpenacctTeller(), acTCustomerVO.getOpenacctOrgan(), actledger.getAccount(), NewConstSubject.id5_fee_out, NewConstSubject.id5_fee_in, new BigDecimal(idFee),
									act_3, act_4, TypeConstants.SFYZ);

						}
						if ("3".equals(id5Check.getCheckStatusCode())) {
							user.setIsApproveCard(BigDecimal.ONE);// users表变更身份验证状态为1
							AccountUserInfoPersonAdmin userInfoPersion = _user.getUserInfoPerson();
							AccountUserInfoPersonAdmin person = null;
							if (user.getUserInfoPerson() == null || user.getUserInfoPerson().getInfoId() == null) {
								person = new AccountUserInfoPersonAdmin();
							} else {
								List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByInfoId(user.getUserInfoPerson().getInfoId());
								if (AccountUserInfoPersonList.size() > 0) {
									person = AccountUserInfoPersonList.get(0);
								}
								if (person == null) {
									person = new AccountUserInfoPersonAdmin();
								}
							}

							person.setUserId(_user.getUserId());
							person.setRealName(userInfoPersion.getRealName());
							person.setCredentialKind(userInfoPersion.getCredentialKind());
							person.setIdentityNo(userInfoPersion.getIdentityNo());
							person.setSex(createSex(identityNo));
							user.setUserInfoPerson(person); // user_info_person
															// 插入真实姓名、身份证等信息
							user.setId5Check(id5Check); // 把对比一致的反馈状态插入数据库
							logger.info("SAVE:USERS,PersonalPrivate,ID5Check check success|| userId=" + user.getUserId() + " RealName=" + userInfoPersion.getRealName() + " CredentialKind=" + userInfoPersion.getCredentialKind() + " IdentityNo=" + userInfoPersion.getIdentityNo() + " id5Check=" + id5Check.getCheckStatusCode());
							accountUsersDao.save(user);
						} else if ("2".equals(id5Check.getCheckStatusCode()) || "1".equals(id5Check.getCheckStatusCode())) {
							AccountUserInfoPersonAdmin userInfoPersion = user.getUserInfoPerson();
							AccountUserInfoPersonAdmin person = null;

							List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByInfoId(user.getUserInfoPerson().getInfoId());
							if (AccountUserInfoPersonList.size() > 0) {
								person = AccountUserInfoPersonList.get(0);
							}
							if (person == null) {
								person = new AccountUserInfoPersonAdmin();
							}
							person.setUserId(user.getUserId());
							person.setRealName(userInfoPersion.getRealName());
							person.setCredentialKind(userInfoPersion.getCredentialKind());
							person.setIdentityNo(userInfoPersion.getIdentityNo());
							user.setUserInfoPerson(person);
							user.setId5Check(id5Check);// 把对比失败或者库中无数据的的反馈状态插入数据库
							logger.info("SAVE:USERS,PersonalPrivate,ID5Check check fail || userId=" + user.getUserId() + " RealName=" + userInfoPersion.getRealName() + " CredentialKind=" + userInfoPersion.getCredentialKind() + " IdentityNo=" + userInfoPersion.getIdentityNo() + " id5Check=" + id5Check.getCheckStatusCode());
							accountUsersDao.save(user);
							result.setMsg("验证失败");
							return result;
							// return "fail";
						} else {
							result.setMsg("验证失败");
							return result;
							// return "fail";
						}
					} else {
						result.setMsg("账户余额不足" + idFee + "，无法验证！");
						return result;
						// return "noEnough";
					}
				} else {
					result.setMsg("校验失败，该身份证号码已被绑定！");
					return result;
					// return "occupied";
				}
			}
		}
		result.setMsg("验证成功！");
		// return "success";
		return result;
	}

	// 当前用户的资金账户可用余额
	public Double getAvailableBalance(AccountUsersAdmin currentUser) {
		// 我的余额(可用余额)
		LoanManagementAcTLedger payPrincipalActledger1 = loanManagementActLegerDao.findByBusiTypeAndAccountLike("4", currentUser.getFinancialAcTCustomerAdmin().getTotalAcct() + "%");
		return payPrincipalActledger1 != null ? payPrincipalActledger1.getAmount() : 0;
	}

	/**
	 * 生成交易流水
	 */
	public LoanManagementAcTFlow setActFlow(double tradeAmount, String teller, String Organ, String account, String appoAcc, String acctTitle, String appoAcctTitle) {
		LoanManagementAcTFlow actFlow = new LoanManagementAcTFlow();
		actFlow.setTradeDate(new Timestamp(System.currentTimeMillis()));// 交易日期
		actFlow.setTradeNo(DateUtil.getTransactionSerialNumber((commonDao.getFlowSeq())));// 流水号
		actFlow.setTradeAmount(tradeAmount);// 交易金额
		actFlow.setTradeType("1");// 交易类型:现金
		actFlow.setTeller(teller);// 柜员号
		actFlow.setOrgan(Organ);// 营业网点
		actFlow.setAccount(account);// 账号
		actFlow.setAppoAcct(appoAcc);// 对方账户
		actFlow.setAcctTitle(acctTitle);// 科目号
		actFlow.setAppoAcctTitle(appoAcctTitle);// 对方科目号
		actFlow.setMemo("操作科目：" + ConstSubject.getNameBySubject(acctTitle));// 备注
		return actFlow;
	}

	/**
	 * @author Ray
	 * @date 2012-10-24 上午9:49:06
	 * @param user
	 *            description:用户工作情况保存
	 */
	public void saveIdCard(AccountUsersAdmin user) {
		if (user == null) {
			throw new RuntimeException("user对象为空!");
		}
		AccountUserInfoPersonAdmin userInfoPersion = user.getUserInfoPerson();
		AccountUserInfoPersonAdmin person = null;

		List<AccountUserInfoPersonAdmin> AccountUserInfoPersonList = userInfoPersonDao.findByInfoId(user.getUserInfoPerson().getInfoId());
		if (AccountUserInfoPersonList.size() > 0) {
			person = AccountUserInfoPersonList.get(0);
		}
		if (person == null) {
			person = new AccountUserInfoPersonAdmin();

		}
		/*
		 * if(!StringUtils.isEmpty(getIconPath())){
		 * person.setHeadPath(baseVO.getIconPath()+"/icon.jpg"); }
		 */
		person.setInfoId(person.getInfoId());
		person.setUserId(user.getUserId());
		person.setRealName(userInfoPersion.getRealName());
		person.setCredentialKind(userInfoPersion.getCredentialKind());
		person.setIdentityNo(userInfoPersion.getIdentityNo());
		userInfoPersonDao.save(person);

	}

	/**
	 * @author Ray
	 * @date 2012-11-7 下午3:26:20
	 * @param identityNo
	 * @return description:根据身份证生成性别
	 */
	private BigDecimal createSex(String identityNo) {
		BigDecimal sex = BigDecimal.valueOf(1);
		// 15位身份证
		if (identityNo.length() == 15) {
			String id15 = identityNo.substring(14, 15);
			if (Integer.parseInt(id15) % 2 != 0) {
				sex = BigDecimal.valueOf(1);
			} else {
				sex = BigDecimal.valueOf(2);
			}

		} else if (identityNo.length() == 18) {
			String id17 = identityNo.substring(16, 17);
			if (Integer.parseInt(id17) % 2 != 0) {
				sex = BigDecimal.valueOf(1);
			} else {
				sex = BigDecimal.valueOf(2);
			}
		} else {
			// 身份证长度不否，错误身份证
			sex = BigDecimal.valueOf(0);
		}
		return sex;
	}

	/**
	 * @author Ray
	 * @date 2012-10-25 下午4:02:33
	 * @return description:获取存放图片路径
	 */
	public String getHeadIconPath() {
		Properties props = new Properties();
		try {
			InputStream in = InfoApproveManager.class.getResourceAsStream("/filePath.properties");
			props.load(in);
			in.close();
			String value = props.getProperty("iconPath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 当前用户的资金账户可用余额
	private AcTLedgerVO getAvailableBalance(Long userId) {
		// 我的余额(可用余额)
		AcTLedgerVO actledger = acTLedgerDAO.findByBusiTypeAndAccountLike("4", userId);
		return actledger;
	}
}
