package com.zendaimoney.online.service.personal;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.common.CipherUtil;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.dao.personal.PersonalDAO;
import com.zendaimoney.online.dao.personal.PersonalPrivateProprietorDao;
import com.zendaimoney.online.dao.personal.PersonalUserInfoJobDao;
import com.zendaimoney.online.dao.personal.PersonalUserInfoPersonDAO;
import com.zendaimoney.online.dao.personal.PersonalUserMessageSetDao;
import com.zendaimoney.online.dao.personal.PersonalUsersDAO;
import com.zendaimoney.online.entity.personal.PersonalPrivateProprietor;
import com.zendaimoney.online.entity.personal.PersonalUserInfoJob;
import com.zendaimoney.online.entity.personal.PersonalUserInfoPerson;
import com.zendaimoney.online.entity.personal.PersonalUserMessageSet;
import com.zendaimoney.online.entity.personal.PersonalUsers;
import com.zendaimoney.online.vo.personal.ModifyPwdVo;
import com.zendaimoney.online.vo.personal.PersonalBaseVO;
import com.zendaimoney.online.vo.personal.PrivateProprietorVo;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = false)
public class PersonalManager {

	private static Logger logger = LoggerFactory.getLogger(PersonalManager.class);
	@Autowired
	PersonalDAO personalDAO;
	@Autowired
	PersonalUsersDAO personalUsersDAO;
	@Autowired
	PersonalUserInfoPersonDAO personalUserInfoPersonDAO;
	@Autowired
	PersonalPrivateProprietorDao privateProprietorDao;
	@Autowired
	PersonalUserMessageSetDao userMessageSetDao;
	@Autowired
	PersonalUserInfoJobDao userInfoJobDao;
	@Autowired
	ChannelInfoService channelInfoService;

	@Transactional(readOnly = false)
	public void savePersonalFinance(PersonalUserInfoPerson entity, BigDecimal userId) {
		PersonalUserInfoPerson personal = personalUserInfoPersonDAO.findByUserId(userId);
		if (personal == null) {
			personal = new PersonalUserInfoPerson();
			personal.setUserId(userId);
		}
		personal.setMonthIncome(entity.getMonthIncome());
		personal.setMonthHousingLoan(entity.getMonthHousingLoan());
		personal.setMonthCarLoan(entity.getMonthCarLoan());
		personal.setMonthCreditCard(entity.getMonthCreditCard());
		personal.setMonthMortgage(entity.getMonthMortgage());
		logger.info("SAVE: USER_INFO_PERSON || userId=" + userId + " MonthIncome=" + entity.getMonthIncome() + " MonthHousingLoan=" + entity.getMonthHousingLoan() + "MonthCarLoan=" + entity.getMonthCarLoan() + " MonthCreditCard=" + entity.getMonthCreditCard() + " MonthMortgage=" + entity.getMonthMortgage());
		personalUserInfoPersonDAO.save(personal);
	}

	public PersonalUserInfoPerson queryPersonalUserInfoPerson(BigDecimal userId) {
		return personalUserInfoPersonDAO.findByUserId(userId);
	}

	public PersonalBaseVO queryPersonalBase(BigDecimal userId) {
		PersonalUsers users = personalUsersDAO.findByUserId(userId);
		if (users == null) {
			throw new RuntimeException("查询不到此用户!");
		}
		PersonalBaseVO baseVO = new PersonalBaseVO();
		// 渠道来源修改2013-1-8
		if (users.getChannelInfo() == null || users.getChannelInfo().getId() == null) {
			baseVO.setChannelFName("其他");
			baseVO.setChannelCName("无");
		} else {
			// 二级渠道名称
			String channelCName = users.getChannelInfo().getName();
			// 一级渠道名称
			String channelFName = channelInfoService.cacheMapForParentData(users.getChannelInfo().getParentId());
			baseVO.setChannelFName(channelFName);
			baseVO.setChannelCName(channelCName);
		}

		baseVO.setLoginName(users.getLoginName());
		baseVO.setEmail(users.getEmail());
		PersonalUserInfoPerson person = personalUserInfoPersonDAO.findByUserId(userId);
		if (person == null) {
			return baseVO;
		}

		if (StringUtils.isEmpty(person.getHeadPath()) || person.getHeadPath().indexOf("null") != -1) {
			baseVO.setIconPath("");
		} else {
			baseVO.setIconPath(person.getHeadPath());
		}
		baseVO.setName(person.getRealName());
		baseVO.setSex(person.getSex() == null ? "" : person.getSex() + "");
		baseVO.setCredentialType(person.getCredentialKind());
		baseVO.setCredentialNum(person.getIdentityNo());
		baseVO.setMobile(person.getPhoneNo());
		baseVO.setHometownArea(person.getHometownArea() == null ? "" : person.getHometownArea() + "");
		baseVO.setHometownCity(person.getHometownCity() == null ? "" : person.getHometownCity() + "");
		baseVO.setCurrAddr(person.getLiveAddress());
		baseVO.setLiveAreaCode(person.getLivePhoneArea());
		baseVO.setLiveTel(person.getLivePhoneNo());
		baseVO.setLivePost(person.getPostCode());
		baseVO.setMarriage(person.getIsapproveMarry() == null ? "" : person.getIsapproveMarry() + "");
		baseVO.setChild(person.getIsapproveHavechild() == null ? "" : person.getIsapproveHavechild() + "");
		baseVO.setMonIncome(person.getMonthIncome() == null ? "" : person.getMonthIncome() + "");
		baseVO.setImmFamilyName(person.getFamilyMembersName());
		baseVO.setImmFamilyRela(person.getFamilyRelation());
		baseVO.setImmFamilyMobile(person.getFamilyMemberPhone());
		baseVO.setOtherContactName(person.getOtherContactName());
		baseVO.setOtherContactRela(person.getOtherRelation());
		baseVO.setOtherContactMobile(person.getOtherContactPhone());
		baseVO.setQq(person.getQqNo());
		baseVO.setMsn(person.getMsnNo());
		baseVO.setWeibo(person.getSinaWeiboAccount());
		baseVO.setThreeContactName(person.getThreeContactName());
		baseVO.setThreeContactPhone(person.getThreeContactPhone());
		baseVO.setThreeContactRelation(person.getThreeContactRelation());
		baseVO.setFourthContactName(person.getFourthContactName());
		baseVO.setFourthContactPhone(person.getFourthContactPhone());
		baseVO.setFourthContactRelation(person.getFourthContactRelation());
		return baseVO;
	}

	@Transactional(readOnly = false)
	public void savePersonalBase(HttpServletRequest request) {
		BigDecimal userId = getUsers(request).getUserId();
		PersonalUserInfoPerson person = personalUserInfoPersonDAO.findByUserId(userId);
		PersonalUsers users = personalUsersDAO.findByUserId(userId);
		if (person == null) {
			person = new PersonalUserInfoPerson();

		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = null;
		try {
			items = upload.parseRequest(request);
			String fileName = users.getLoginName();
			String userEmail = users.getEmail();
			Iterator iter = items.iterator();
			String path = getIconPath() + userEmail;
			// 文件夹不存在就自动创建：
			int index = 0;
			File filea = new File(path);
			if (!(filea.exists()) && !(filea.isDirectory())) {
				filea.mkdirs();
			}
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField() == false) {
					// 或直接保存成文件
					String name = item.getName();
					int point = name.lastIndexOf(".");
					String hz = name.substring(point);
					File file = new File(path, fileName + index + hz);
					index++;
					item.write(file);// 直接保存文件
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(item.getInputStream())); // 用流获取表单元素，【注】这里把原文的item.getInputStream()改为了item.openStream()
					// 下面开始根据name值不同读取数据并复制给不同的变量。
					br.close(); // 释放资源
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = false)
	public void savePersonalBase(PersonalBaseVO baseVO, BigDecimal userId) {
		if (baseVO == null) {
			throw new RuntimeException("PersonalBaseVO对象为空!");
		}
		PersonalUserInfoPerson person = personalUserInfoPersonDAO.findByUserId(userId);
		if (person == null) {
			person = new PersonalUserInfoPerson();

		}
		// try {
		// BeanUtils.copyProperties(person, baseVO);
		// person.setUserId(userId);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		person.setUserId(userId);
		/*
		 * if(!StringUtils.isEmpty(getIconPath())){
		 * person.setHeadPath(baseVO.getIconPath()+"/icon.jpg"); }
		 */
		person.setRealName(baseVO.getName());
		person.setSex(new BigDecimal(baseVO.getSex()));
		person.setCredentialKind(baseVO.getCredentialType());
		person.setIdentityNo(baseVO.getCredentialNum());
		person.setPhoneNo(baseVO.getMobile());
		person.setHometownArea(new BigDecimal(baseVO.getHometownArea()));
		person.setHometownCity(new BigDecimal(baseVO.getHometownCity()));
		person.setLiveAddress(baseVO.getCurrAddr());
		person.setLivePhoneArea(baseVO.getLiveAreaCode());
		person.setLivePhoneNo(baseVO.getLiveTel());
		person.setPostCode(baseVO.getLivePost());
		person.setIsapproveMarry(new BigDecimal(baseVO.getMarriage()));
		if (baseVO.getMonIncome() == null) {
			person.setMonthIncome(new BigDecimal(0));
		} else {
			person.setMonthIncome(new BigDecimal(baseVO.getMonIncome()));
		}
		person.setFamilyMembersName(baseVO.getImmFamilyName());
		person.setFamilyRelation(baseVO.getImmFamilyRela());
		person.setFamilyMemberPhone(baseVO.getImmFamilyMobile());
		person.setOtherContactName(baseVO.getOtherContactName());
		person.setOtherRelation(baseVO.getOtherContactRela());
		person.setOtherContactPhone(baseVO.getOtherContactMobile());

		person.setLivePhoneArea(baseVO.getLiveAreaCode());// 区号
		person.setLivePhoneNo(baseVO.getLiveTel());// 电话号码
		person.setPostCode(baseVO.getLivePost());// 邮编
		person.setIsapproveHavechild(new BigDecimal(baseVO.getChild()));// 有无孩子
		person.setQqNo(baseVO.getQq());// QQ
		person.setMsnNo(baseVO.getMsn());// MSN
		person.setSinaWeiboAccount(baseVO.getWeibo());// 微博
		person.setThreeContactName(baseVO.getThreeContactName());
		person.setThreeContactPhone(baseVO.getThreeContactPhone());
		person.setThreeContactRelation(baseVO.getThreeContactRelation());
		person.setFourthContactName(baseVO.getFourthContactName());
		person.setFourthContactPhone(baseVO.getFourthContactPhone());
		person.setFourthContactRelation(baseVO.getFourthContactRelation());
		logger.info("SAVE:USER_INFO_PERSON || userId=" + userId + " realName=" + person.getRealName() + " IdentityNo=" + person.getIdentityNo() + " Sex=" + person.getSex() + " CredentialKind=" + person.getCredentialKind() + " PhoneNo=" + person.getPhoneNo() + " HometownArea=" + person.getHometownArea() + " HometownCity=" + person.getHometownCity() + " LiveAddress="
				+ person.getLiveAddress() + " LivePhoneArea=" + person.getLivePhoneArea() + " LivePhoneNo=" + person.getLivePhoneNo() + " PostCode=" + person.getPostCode() + " IsapproveMarry=" + person.getIsapproveMarry() + " FamilyMembersName=" + person.getFamilyMembersName() + " FamilyRelation=" + person.getFamilyRelation() + " FamilyMemberPhone="
				+ person.getFamilyMemberPhone() + " OtherContactName=" + person.getOtherContactName() + " OtherRelation=" + person.getOtherRelation() + " OtherContactPhone=" + person.getOtherContactPhone() + " setLivePhoneArea=" + person.getLivePhoneArea() + " LivePhoneNo=" + person.getLivePhoneNo() + " PostCode=" + person.getPostCode() + " IsapproveHavechild="
				+ person.getIsapproveHavechild() + " QqNo=" + person.getQqNo() + " MsnNo=" + person.getMsnNo() + " SinaWeiboAccount=" + person.getSinaWeiboAccount());
		personalUserInfoPersonDAO.save(person);
	}

	public List getAreaList(String code) {
		return personalDAO.queryAeraList(code);
	}

	public String getIconPath() {
		ResourceBundle bundle = ResourceBundle.getBundle("filePath");
		String root = bundle.getString("iconPath");
		if (StringUtils.isEmpty(root)) {
			throw new RuntimeException("用户头象保存路径未定义!");
		}
		return root;
		// File file=new File(root);
		// if(!file.exists()){
		// return "";
		// }else{
		// return root+path+"/icon.jpg";
		// }
	}

	// show私营业主信息
	public PrivateProprietorVo showPersonalPrivate(BigDecimal userId) {
		PersonalPrivateProprietor pp = privateProprietorDao.findByUserId(userId);
		if (pp == null) {
			return null;
		}
		PrivateProprietorVo ppvo = new PrivateProprietorVo();
		try {
			PropertyUtils.copyProperties(ppvo, pp);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		String dateformat = "yyyy/MM/dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		if (null != pp.getEstablishDate()) {
			ppvo.setDateStr(dateFormat.format(pp.getEstablishDate()));
		}
		return ppvo;
	}

	// 私营业主资料
	@Transactional(readOnly = false)
	public void saveSyyz(PersonalPrivateProprietor privateProprietor, HttpServletRequest req) {
		PersonalUsers user = getUsers(req);
		PersonalPrivateProprietor pp = privateProprietorDao.findByUserId(user.getUserId());
		if (null != pp) {
			privateProprietor.setId(pp.getId());
		}
		privateProprietor.setUserId(user.getUserId());
		privateProprietorDao.save(privateProprietor);
	}

	// show工作信息
	public PersonalUserInfoJob showJob(BigDecimal userId) {
		PersonalUserInfoJob pp = userInfoJobDao.findByUserId(userId);
		return pp;
	}

	// 工作信息保存
	@Transactional(readOnly = false)
	public void saveJobInfo(PersonalUserInfoJob userInfoJob, HttpServletRequest req) {
		PersonalUsers user = getUsers(req);
		PersonalUserInfoJob pp = userInfoJobDao.findByUserId(user.getUserId());
		if (null != pp) {
			userInfoJob.setWorkInfoId(pp.getWorkInfoId());
		}
		userInfoJob.setUserId(user.getUserId());
		userInfoJobDao.save(userInfoJob);
	}

	// 修改密码保存
	@Transactional(readOnly = false)
	public String savePassword(ModifyPwdVo modifyPwdVo, HttpServletRequest req) {
		PersonalUsers user = getUsers(req);
		String ypwd = CipherUtil.generatePassword(modifyPwdVo.getyPassWord());
		if (ypwd.equals(user.getLoginPassword())) {
			user.setLoginPassword(CipherUtil.generatePassword(modifyPwdVo.getnPassWrod()));
			logger.info("SAVE: USERS || userId=" + user.getUserId() + " pw=" + user.getLoginPassword());
			personalUsersDAO.save(user);
			return "same";
		} else {
			return "diff";
		}
	}

	// 取得userid
	public PersonalUsers getUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		PersonalUsers userInfo = personalUsersDAO.findByUserId(userid);
		return userInfo;
	}

	public void saveUserMessageSet(String checkId, HttpServletRequest request) {
		PersonalUsers user = getUsers(request);
		BigDecimal userid = user.getUserId();
		for (PersonalUserMessageSet s : userMessageSetDao.findByUserId(userid)) {
			logger.info("delete : USER_MESSAGE_SET || userId=" + userid);
			userMessageSetDao.delete(s);
		}
		;
		String[] args = new String[24];
		checkId.subSequence(0, checkId.length() - 1);
		args = checkId.split(";");
		Set<PersonalUserMessageSet> set = new HashSet<PersonalUserMessageSet>(0);
		for (String a : args) {
			PersonalUserMessageSet userMessageSet = new PersonalUserMessageSet();
			String kindId = a.substring(0, 1);
			String mannerId = a.substring(1);
			userMessageSet.setUserId(userid);
			userMessageSet.setMannerId(new BigDecimal(mannerId));
			userMessageSet.setKindId(new BigDecimal(kindId));
			set.add(userMessageSet);
		}
		logger.info("SAVE : USER_MESSAGE_SET || userId=" + userid);
		userMessageSetDao.save(set);
	}

	public String initNotifySet(HttpServletRequest request) {
		PersonalUsers user = getUsers(request);
		BigDecimal userId = user.getUserId();// =user.getUserId();
		List<PersonalUserMessageSet> userMessageList = userMessageSetDao.findByUserId(userId);
		String checkId = "";
		for (PersonalUserMessageSet userMessage : userMessageList) {
			checkId = checkId + (userMessage.getKindId().toString() + userMessage.getMannerId().toString()) + ";";
		}
		return checkId;

	}

	@Transactional(readOnly = false)
	public void saveNewFixedAssets(PersonalUserInfoPerson newUserInfoPerson, HttpServletRequest req) {
		PersonalUsers user = getUsers(req);
		PersonalUserInfoPerson userInfoPerson = personalUserInfoPersonDAO.findByUserId(user.getUserId());
		if (userInfoPerson != null) {
			if (userInfoPerson.getBirthday() != null) {
				userInfoPerson.setBirthday(DateUtil.setDateyyyyMMDD(userInfoPerson.getBirthday()));
			}
			userInfoPerson.setIsapproveHaveHouse(newUserInfoPerson.getIsapproveHaveHouse());
			userInfoPerson.setHousePropertyAddress(newUserInfoPerson.getHousePropertyAddress());
			userInfoPerson.setIsapproveHouseMortgage(newUserInfoPerson.getIsapproveHouseMortgage());
			userInfoPerson.setHouseMonthMortgage(newUserInfoPerson.getHouseMonthMortgage());
			userInfoPerson.setIsapproveHaveCar(newUserInfoPerson.getIsapproveHaveCar());
			userInfoPerson.setCarBrand(newUserInfoPerson.getCarBrand());
			userInfoPerson.setCarYears(newUserInfoPerson.getCarYears());
			userInfoPerson.setCarNo(newUserInfoPerson.getCarNo());
			userInfoPerson.setIsapproveCarMortgage(newUserInfoPerson.getIsapproveCarMortgage());
			userInfoPerson.setCarMonthMortgage(newUserInfoPerson.getCarMonthMortgage());
		} else {
			userInfoPerson = new PersonalUserInfoPerson();
			userInfoPerson.setUserId(user.getUserId());
			userInfoPerson.setIsapproveHaveHouse(newUserInfoPerson.getIsapproveHaveHouse());
			userInfoPerson.setHousePropertyAddress(newUserInfoPerson.getHousePropertyAddress());
			userInfoPerson.setIsapproveHouseMortgage(newUserInfoPerson.getIsapproveHouseMortgage());
			userInfoPerson.setHouseMonthMortgage(newUserInfoPerson.getHouseMonthMortgage());
			userInfoPerson.setIsapproveHaveCar(newUserInfoPerson.getIsapproveHaveCar());
			userInfoPerson.setCarBrand(newUserInfoPerson.getCarBrand());
			userInfoPerson.setCarYears(newUserInfoPerson.getCarYears());
			userInfoPerson.setCarNo(newUserInfoPerson.getCarNo());
			userInfoPerson.setIsapproveCarMortgage(newUserInfoPerson.getIsapproveCarMortgage());
			userInfoPerson.setCarMonthMortgage(newUserInfoPerson.getCarMonthMortgage());
		}
		logger.info("SAVE:USER_INFO_PERSON || userId=" + user.getUserId() + " IsapproveHaveHouse=" + userInfoPerson.getIsapproveHaveHouse() + " HousePropertyAddress=" + userInfoPerson.getHousePropertyAddress() + " IsapproveHouseMortgage=" + userInfoPerson.getIsapproveHouseMortgage() + " HouseMonthMortgage=" + userInfoPerson.getHouseMonthMortgage() + " IsapproveHaveCar="
				+ userInfoPerson.getIsapproveHaveCar() + " CarBrand=" + userInfoPerson.getCarBrand() + " CarYears=" + userInfoPerson.getCarYears() + " CarNo=" + userInfoPerson.getCarNo() + " IsapproveCarMortgage=" + userInfoPerson.getIsapproveCarMortgage() + " CarMonthMortgage=" + userInfoPerson.getCarMonthMortgage());
		personalUserInfoPersonDAO.save(userInfoPerson);
	}

	// show固定资产
	public PersonalUserInfoPerson showFixed(BigDecimal userId) {
		return personalUserInfoPersonDAO.findByUserId(userId);
	}

	@Transactional(readOnly = false)
	public void saveNewEduInfo(PersonalUserInfoPerson newUserInfoPerson, HttpServletRequest req) {
		PersonalUsers user = getUsers(req);
		PersonalUserInfoPerson userInfoPerson = personalUserInfoPersonDAO.findByUserId(user.getUserId());
		if (userInfoPerson != null) {
			userInfoPerson.setMaxDegree(newUserInfoPerson.getMaxDegree());
			userInfoPerson.setGraduatSchool(newUserInfoPerson.getGraduatSchool());
			userInfoPerson.setJobTitleOne(newUserInfoPerson.getJobTitleOne());
			userInfoPerson.setJobTitleTwo(newUserInfoPerson.getJobTitleTwo());
			userInfoPerson.setJobTitleThr(newUserInfoPerson.getJobTitleThr());
			userInfoPerson.setInschoolTime(newUserInfoPerson.getInschoolTime());
		} else {
			userInfoPerson = new PersonalUserInfoPerson();
			userInfoPerson.setUserId(user.getUserId());
			userInfoPerson.setMaxDegree(newUserInfoPerson.getMaxDegree());
			userInfoPerson.setGraduatSchool(newUserInfoPerson.getGraduatSchool());
			userInfoPerson.setJobTitleOne(newUserInfoPerson.getJobTitleOne());
			userInfoPerson.setJobTitleTwo(newUserInfoPerson.getJobTitleTwo());
			userInfoPerson.setJobTitleThr(newUserInfoPerson.getJobTitleThr());
			userInfoPerson.setInschoolTime(newUserInfoPerson.getInschoolTime());
		}

		logger.info("SAVE:USER_INFO_PERSON || userId=" + user.getUserId() + " MonthIncome=" + userInfoPerson.getMonthIncome() + " MonthHousingLoan=" + userInfoPerson.getMonthHousingLoan() + " MonthCarLoan=" + userInfoPerson.getMonthCarLoan() + "MonthCreditCard=" + userInfoPerson.getMonthCreditCard() + " MonthMortgage=" + userInfoPerson.getMonthMortgage());
		personalUserInfoPersonDAO.save(userInfoPerson);
	}

	public PersonalBaseVO showImg(HttpServletRequest request) {
		PersonalUsers user = getUsers(request);
		PersonalUserInfoPerson userInfoPerson = personalUserInfoPersonDAO.findByUserId(user.getUserId());
		PersonalBaseVO vo = new PersonalBaseVO();
		if (userInfoPerson != null) {
			String path = userInfoPerson.getHeadPath();
			// path=path.substring(15, path.length());
			vo.setIconPath(path);
		}
		return vo;
	}
}
