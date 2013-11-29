package com.zendaimoney.online.service.borrowing;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.dao.borrowing.BorrowingFreezeFundsDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserApproveDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserCreditNoteDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserInfoJobDao;
import com.zendaimoney.online.dao.borrowing.BorrowingUserInfoPersonDao;
import com.zendaimoney.online.dao.borrowing.ReleaseLoanDao;
import com.zendaimoney.online.entity.borrowing.BorrowingInvestInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.borrowing.BorrowingUserCreditNote;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoJob;
import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;
import com.zendaimoney.online.entity.borrowing.BorrowingUsers;
import com.zendaimoney.online.vo.borrowing.BorrowingProCheckVO;
import com.zendaimoney.online.vo.borrowing.BorrowingProStatusVO;
import com.zendaimoney.online.vo.borrowing.BorrowingProVO;
import com.zendaimoney.online.vo.releaseLoan.LoanInfoDetailVO;
import com.zendaimoney.online.vo.releaseLoan.LoanInfoListVO;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author Ray
 * @date: 2012-12-18 下午2:35:54 operation by: description:必要认证信息借款信息查询
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class InfoApproveManager {
	private static Logger logger = LoggerFactory.getLogger(InfoApproveManager.class);
	@Autowired
	private BorrowingUserInfoPersonDao borrowinguserInfoPersonDao;
	@Autowired
	private BorrowingUserApproveDao borrowingUserApproveDao;
	@Autowired
	private BorrowingUserInfoJobDao borrowingUserInfoJobDao;
	@Autowired
	private BorrowingUserDao borrowingUserDao;
	@Autowired
	BorrowingUserCreditNoteDao borrowingUserCreditNoteDao;
	@Autowired
	BorrowingFreezeFundsDao borrowingFreezeFundsDao;
	@Autowired
	ReleaseLoanDao releaseLoanDao;

	// 点击发布借款 判定跳转方向
	public String releaseLoan(HttpServletRequest request) {
		BorrowingUsers user = getUsers(request);
		BigDecimal kyed = showAvailableMoney(request);
		if (user.getUserStatus().equals(BigDecimal.valueOf(5)) && (kyed.compareTo(BigDecimal.ZERO) == 1)) {
			return "releaseLoan";
		} else if (user.getUserStatus().equals(BigDecimal.valueOf(1)) || user.getUserStatus().equals(BigDecimal.valueOf(2)) || user.getUserStatus().equals(BigDecimal.valueOf(3))) {
			return "updataImg";
		} else if (user.getUserStatus().equals(BigDecimal.valueOf(5)) && (kyed.compareTo(BigDecimal.ZERO) == 0 || kyed.compareTo(BigDecimal.ZERO) == -1)) {
			return "noLimit";
		} else if (user.getUserStatus().equals(BigDecimal.valueOf(4))) {
			return "inAudit";
		} else if (user.getUserStatus().equals(BigDecimal.valueOf(6))) {
			return "lock";
		} else if (user.getUserStatus().equals(BigDecimal.valueOf(7))) {
			return "report";
		} else {
			return "updataImg";
		}
	}

	// 发布借款时 是否有在途借款
	public String isReleaseLoanInfo(HttpServletRequest req) {
		List<BorrowingLoanInfo> list1 = releaseLoanDao.findByUserAndStatus(getUsers(req), BigDecimal.ONE);
		List<BorrowingLoanInfo> list2 = releaseLoanDao.findByUserAndStatus(getUsers(req), new BigDecimal(8));
		if ((list1 != null && list1.size() != 0) || (list2 != null && list2.size() != 0)) {
			return "yes";
		} else {
			return "no";
		}
	}

	// 获取当前正在进行的借款信息
	public LoanInfoListVO getLoanInfoList(HttpServletRequest req) {
		LoanInfoListVO loanList = new LoanInfoListVO();
		List<BorrowingLoanInfo> list1 = releaseLoanDao.findByUserAndStatus(getUsers(req), BigDecimal.ONE);
		List<BorrowingLoanInfo> list2 = releaseLoanDao.findByUserAndStatus(getUsers(req), new BigDecimal(8));
		List<LoanInfoDetailVO> loanInfoList = new ArrayList<LoanInfoDetailVO>();
		for (BorrowingLoanInfo loanInfo1 : list1) {
			LoanInfoDetailVO vo1 = new LoanInfoDetailVO();
			double speedProgress = 0;
			try {
				PropertyUtils.copyProperties(vo1, loanInfo1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (BorrowingInvestInfo brInvestInfo1 : loanInfo1.getInvestInfoList()) {
				speedProgress += brInvestInfo1.getHavaScale();
			}
			vo1.setReleaseDateStr(DateUtil.getYMDTime(loanInfo1.getReleaseTime()));
			vo1.setReleaseTimeStr(DateUtil.getHMSTime(loanInfo1.getReleaseTime()));
			vo1.setSpeedProgress(ObjectFormatUtil.formatPercent(speedProgress, "##,#0.00%"));
			vo1.setAmount(ObjectFormatUtil.formatCurrency(loanInfo1.getLoanAmount()));
			vo1.setRate(ObjectFormatUtil.formatPercent(loanInfo1.getYearRate(), "##,#0.00%"));
			vo1.setBidNumber(loanInfo1.getInvestInfoList().size());
			loanInfoList.add(vo1);
		}
		for (BorrowingLoanInfo loanInfo2 : list2) {
			LoanInfoDetailVO vo2 = new LoanInfoDetailVO();
			double speedProgress = 0;
			try {
				PropertyUtils.copyProperties(vo2, loanInfo2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (BorrowingInvestInfo brInvestInfo2 : loanInfo2.getInvestInfoList()) {
				speedProgress += brInvestInfo2.getHavaScale();
			}
			vo2.setReleaseDateStr(DateUtil.getYMDTime(loanInfo2.getReleaseTime()));
			vo2.setReleaseTimeStr(DateUtil.getHMSTime(loanInfo2.getReleaseTime()));
			vo2.setSpeedProgress(ObjectFormatUtil.formatPercent(speedProgress, "##,#0.00%"));
			vo2.setAmount(ObjectFormatUtil.formatCurrency(loanInfo2.getLoanAmount()));
			vo2.setRate(ObjectFormatUtil.formatPercent(loanInfo2.getYearRate(), "##,#0.00%"));
			vo2.setBidNumber(loanInfo2.getInvestInfoList().size());
			loanInfoList.add(vo2);
		}
		loanList.setLoanInfoList(loanInfoList);
		return loanList;
	}

	// 初始化页面
	public BorrowingUserInfoPerson showInfo(HttpServletRequest request) {
		BigDecimal userid = getUsers(request).getUserId();
		List<BorrowingUserInfoPerson> userInfoList = borrowinguserInfoPersonDao.findUserId(userid);
		if (userInfoList.size() != 0) {
			return userInfoList.get(0);
		} else {
			return null;
		}
	}

	// 初始化页面
	public BorrowingUserInfoJob showJobInfo(HttpServletRequest request) {
		BigDecimal userid = getUsers(request).getUserId();
		List<BorrowingUserInfoJob> userJobInfoList = borrowingUserInfoJobDao.findByUserId(userid);
		if (userJobInfoList.size() != 0) {
			return userJobInfoList.get(0);
		} else {
			return null;
		}
	}

	// 初始化页面
	public BorrowingUserCreditNote showCreditInfo(HttpServletRequest request) {
		BigDecimal userid = getUsers(request).getUserId();
		BorrowingUserCreditNote creditNote = borrowingUserCreditNoteDao.findByUserId(userid);
		return creditNote;
	}

	// 获取可用额度
	public BigDecimal showAvailableMoney(HttpServletRequest request) {
		BigDecimal userid = getUsers(request).getUserId();
		// List<BorrowingFreezeFunds> freezefundList =
		// borrowingFreezeFundsDao.findByUserIdAndFreezeStatus(userid,"3");
		List<BorrowingLoanInfo> loanList = releaseLoanDao.findByUserUserId(userid);
		double freezeSum = 0;
		// 查询冻结资金
		for (BorrowingLoanInfo loan : loanList) {
			// 借款状态为招标、满标、还款、逾期、高级逾期的资金为冻结资金
			if (loan.getStatus().equals(BigDecimal.ONE) || loan.getStatus().equals(new BigDecimal(2)) || loan.getStatus().equals(new BigDecimal(4)) || loan.getStatus().equals(new BigDecimal(6)) || loan.getStatus().equals(new BigDecimal(7))) {
				freezeSum += loan.getLoanAmount();
			}
		}
		BorrowingUserCreditNote creditNote = borrowingUserCreditNoteDao.findByUserId(userid);
		BigDecimal edu = BigDecimal.ZERO;
		if (creditNote != null) {
			/*
			 * if(freezefundList.size() != 0){ BigDecimal djzj =
			 * BigDecimal.ZERO; for(BorrowingFreezeFunds ff:freezefundList){
			 * djzj.add(ff.getFreezeMoney()); } edu =
			 * creditNote.getCreditAmount().subtract(djzj); }else{ edu =
			 * creditNote.getCreditAmount(); }
			 */
			// 可用额度由总额度减去冻结资金
			edu = creditNote.getCreditAmount().subtract(new BigDecimal(freezeSum));
		}
		return edu;
	}

	// 上传头像
	@Transactional(readOnly = false)
	public String updataHeadPhoto(List<FileItem> items, BorrowingUsers users) throws Exception {
		BigDecimal userId = users.getUserId();
		String userEmail = users.getEmail();
		BorrowingUserInfoPerson userInfo = findBorrowingUserInfoPersonByUserId(userId);
		File file = new File("");
		logger.info("=============开始获取数据！=============");
		String fileName = DateUtil.getStrDate2(new Date());
		Iterator iter = items.iterator();
		// 这里的路径有可能变化
		String path = getHeadIconPath() + userEmail + "/userHeadImg";

		// 文件夹不存在就自动创建：
		File filea = new File(path);
		if (!(filea.exists()) && !(filea.isDirectory())) {
			filea.mkdirs();
			logger.info("=============创建文件夹=============");
		}
		int index = 0;
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.isFormField() == false) {
				// 或直接保存成文件
				String name = item.getName();
				//验证文件后缀 开始
				String[] _names=name.split("\\.");
				//jpg、png、gif、bmp
				if(!_names[_names.length-1].trim().equalsIgnoreCase("jpg")
						&&!_names[_names.length-1].trim().equalsIgnoreCase("png")
						&&!_names[_names.length-1].trim().equalsIgnoreCase("gif")
						&&!_names[_names.length-1].trim().equalsIgnoreCase("bmp")){
					logger.info("上传的头像文件后缀不是图片格式，文件名为:"+name);	
					//不是图片后缀的文件
					return "false";
				}
				
				
				logger.info("头像的等等等等");
				int point = name.lastIndexOf(".");
				String hz = name.substring(point);
				file = new File(path, fileName + index + hz);
				path = userEmail + "/userHeadImg" + "/" + fileName + index + hz;
				logger.info("=============设置图像路径=============");
				userInfo.setHeadPath(path);
				index++;
				logger.info("=============保存头像！=============");
				item.write(file);// 直接保存文件
			}
		}
		if (file.isFile() && file.exists()) {
			borrowinguserInfoPersonDao.save(userInfo);
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			return "true";
		} else {
			logger.info("=============头像没有成功上传！=============");
			return "false";
		}
	}

	@Deprecated
	public BorrowingUsers getUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session == null) {
			return null;
		} else {
			BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
			BorrowingUsers userInfo = borrowingUserDao.findByUserId(userid);
			return userInfo;
		}
	}

	public BorrowingUsers getUsers(BigDecimal userid) {
		BorrowingUsers userInfo = borrowingUserDao.findByUserId(userid);
		return userInfo;
	}

	private String getFilePath() {
		// String s1 =
		// InfoApproveManager.class.getClassLoader().getResource("").toString();
		// String s2 =
		// InfoApproveManager.class.getClassLoader().getResource("/").toString();

		// System.out.println("------------------------"+s1);
		// System.out.println("++++++++++++++++++++++++"+s2);

		// System.out.println("***********************"+filePath);
		// 截掉路径的”file:“前缀
		Properties props = new Properties();
		try {
			InputStream in = InfoApproveManager.class.getResourceAsStream("/filePath.properties");
			props.load(in);
			in.close();
			String value = props.getProperty("filePath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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

	// 用户认证项目的状态
	public BorrowingProVO getUserAppro(BigDecimal userid) {
		List<BorrowingUserApprove> userApproveList = borrowingUserApproveDao.findByUserId(userid);
		BorrowingProVO vo = new BorrowingProVO();

		BorrowingProStatusVO jiben = new BorrowingProStatusVO();
		jiben.setCreditScore("0");
		jiben.setOperate("0");
		jiben.setProStatus("0");
		jiben.setReviewStatus("0");
		BorrowingProStatusVO shenfenzheng = new BorrowingProStatusVO();
		shenfenzheng.setCreditScore("0");
		shenfenzheng.setOperate("0");
		shenfenzheng.setProStatus("0");
		shenfenzheng.setReviewStatus("0");
		BorrowingProStatusVO gongzuozheng = new BorrowingProStatusVO();
		gongzuozheng.setCreditScore("0");
		gongzuozheng.setOperate("0");
		gongzuozheng.setProStatus("0");
		gongzuozheng.setReviewStatus("0");
		BorrowingProStatusVO shouru = new BorrowingProStatusVO();
		shouru.setCreditScore("0");
		shouru.setOperate("0");
		shouru.setProStatus("0");
		shouru.setReviewStatus("0");
		BorrowingProStatusVO xinyong = new BorrowingProStatusVO();
		xinyong.setCreditScore("0");
		xinyong.setOperate("0");
		xinyong.setProStatus("0");
		xinyong.setReviewStatus("0");
		BorrowingProStatusVO fangchan = new BorrowingProStatusVO();
		fangchan.setCreditScore("0");
		fangchan.setOperate("0");
		fangchan.setProStatus("0");
		fangchan.setReviewStatus("0");
		BorrowingProStatusVO jishu = new BorrowingProStatusVO();
		jishu.setCreditScore("0");
		jishu.setOperate("0");
		jishu.setProStatus("0");
		jishu.setReviewStatus("0");
		BorrowingProStatusVO gouche = new BorrowingProStatusVO();
		gouche.setCreditScore("0");
		gouche.setOperate("0");
		gouche.setProStatus("0");
		gouche.setReviewStatus("0");
		BorrowingProStatusVO jiehun = new BorrowingProStatusVO();
		jiehun.setCreditScore("0");
		jiehun.setOperate("0");
		jiehun.setProStatus("0");
		jiehun.setReviewStatus("0");
		BorrowingProStatusVO juzhudi = new BorrowingProStatusVO();
		juzhudi.setCreditScore("0");
		juzhudi.setOperate("0");
		juzhudi.setProStatus("0");
		juzhudi.setReviewStatus("0");
		BorrowingProStatusVO shipin = new BorrowingProStatusVO();
		shipin.setCreditScore("0");
		shipin.setOperate("0");
		shipin.setProStatus("0");
		shipin.setReviewStatus("0");
		BorrowingProStatusVO shidi = new BorrowingProStatusVO();
		shidi.setCreditScore("0");
		shidi.setOperate("0");
		shidi.setProStatus("0");
		shidi.setReviewStatus("0");
		BorrowingProStatusVO xueli = new BorrowingProStatusVO();
		xueli.setCreditScore("0");
		xueli.setOperate("0");
		xueli.setProStatus("0");
		xueli.setReviewStatus("0");
		BorrowingProStatusVO shoujishiming = new BorrowingProStatusVO();
		shoujishiming.setCreditScore("0");
		shoujishiming.setOperate("0");
		shoujishiming.setProStatus("0");
		shoujishiming.setReviewStatus("0");
		BorrowingProStatusVO weibo = new BorrowingProStatusVO();
		weibo.setCreditScore("0");
		weibo.setOperate("0");
		weibo.setProStatus("0");
		weibo.setReviewStatus("0");

		vo.setJiben(jiben);
		vo.setShenfenzheng(shenfenzheng);
		vo.setFangchan(fangchan);
		vo.setGongzuozheng(gongzuozheng);
		vo.setGouche(gouche);
		vo.setJiehun(jiehun);
		vo.setJishu(jishu);
		vo.setJuzhudi(juzhudi);
		vo.setShidi(shidi);
		vo.setShipin(shipin);
		vo.setShoujishiming(shoujishiming);
		vo.setShouru(shouru);
		vo.setWeibo(weibo);
		vo.setXinyong(xinyong);
		vo.setXueli(xueli);

		for (BorrowingUserApprove app : userApproveList) {
			BorrowingProStatusVO svo = new BorrowingProStatusVO();
			svo.setProStatus(app.getProStatus().toString());
			svo.setOperate(app.getProStatus().toString());
			if (null == app.getCreditScore()) {
				svo.setCreditScore("0");
			} else {
				svo.setCreditScore(app.getCreditScore().toString());
			}
			if (null == app.getReviewStatus()) {
				svo.setReviewStatus("0");
			} else {
				svo.setReviewStatus(app.getReviewStatus().toString());
			}
			if (app.getProId().getProId().equals(BigDecimal.valueOf(1))) {
				vo.setShenfenzheng(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(2))) {
				vo.setGongzuozheng(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(3))) {
				vo.setShouru(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(4))) {
				vo.setXinyong(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(5))) {
				vo.setFangchan(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(6))) {
				vo.setJishu(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(7))) {
				vo.setGouche(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(8))) {
				vo.setJiehun(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(9))) {
				vo.setJuzhudi(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(10))) {
				vo.setShipin(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(11))) {
				vo.setShidi(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(12))) {
				vo.setXueli(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(13))) {
				vo.setShoujishiming(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(14))) {
				vo.setWeibo(svo);
			} else if (app.getProId().getProId().equals(BigDecimal.valueOf(16))) {
				vo.setJiben(svo);
			}
		}
		return vo;
	}

	@Transactional(readOnly = false)
	private void checkPro(BigDecimal userId) {
		List<BorrowingUserApprove> proList = borrowingUserApproveDao.findPro(userId);
		logger.info("=============验证用户必要认证是否都提交=============");
		if (proList.size() == 4) {
			BorrowingUsers u = borrowingUserDao.findByUserId(userId);
			u.setUserStatus(BigDecimal.valueOf(4));
			u.setMaterialReviewStatus(BigDecimal.ZERO);
			borrowingUserDao.save(u);
		} else if (0 < proList.size() && proList.size() < 4) {
			BorrowingUsers u = borrowingUserDao.findByUserId(userId);
			u.setUserStatus(BigDecimal.valueOf(3));
			u.setMaterialReviewStatus(BigDecimal.ZERO);
			borrowingUserDao.save(u);
		}
	}

	public BorrowingProCheckVO userProCheck(HttpServletRequest request) {
		BorrowingUsers user = getUsers(request);
		List<BorrowingUserApprove> proList = borrowingUserApproveDao.findAllPro(user.getUserId());
		BorrowingProCheckVO proVo = new BorrowingProCheckVO();
		for (BorrowingUserApprove pro : proList) {
			if (pro.getProId().getProId().equals(BigDecimal.ONE)) {
				proVo.setShenfenzheng("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(2))) {
				proVo.setGongzuozheng("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(3))) {
				proVo.setShouru("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(4))) {
				proVo.setXinyong("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(5))) {
				proVo.setFangchan("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(6))) {
				proVo.setJishu("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(7))) {
				proVo.setGouche("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(8))) {
				proVo.setJiehun("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(9))) {
				proVo.setJuzhudi("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(10))) {
				proVo.setShipin("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(11))) {
				proVo.setShidi("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(12))) {
				proVo.setXueli("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(13))) {
				proVo.setShoujishiming("true");
			} else if (pro.getProId().getProId().equals(new BigDecimal(14))) {
				proVo.setWeibo("true");
			}
		}
		return proVo;
	}

	public BorrowingProCheckVO userProStatus(HttpServletRequest req) {
		BorrowingUsers user = getUsers(req);
		List<BorrowingUserApprove> proList = borrowingUserApproveDao.findAllPro(user.getUserId());
		BorrowingProCheckVO proVo = new BorrowingProCheckVO();
		proVo.setShenfenzheng("待上传");
		proVo.setGongzuozheng("待上传");
		proVo.setShouru("待上传");
		proVo.setXinyong("待上传");
		proVo.setFangchan("待上传");
		proVo.setJishu("待上传");
		proVo.setGouche("待上传");
		proVo.setJiehun("待上传");
		proVo.setJuzhudi("待上传");
		proVo.setShipin("待上传");
		proVo.setShidi("待上传");
		proVo.setXueli("待上传");
		proVo.setShoujishiming("待上传");
		proVo.setWeibo("待上传");
		for (BorrowingUserApprove pro : proList) {
			if (pro.getProId().getProId().equals(BigDecimal.ONE)) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setShenfenzheng("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setShenfenzheng("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setShenfenzheng("审核中");
					}
				} else {
					proVo.setShenfenzheng("待上传");
				}

			} else if (pro.getProId().getProId().equals(new BigDecimal(2))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setGongzuozheng("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setGongzuozheng("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setGongzuozheng("审核中");
					}
				} else {
					proVo.setGongzuozheng("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(3))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setShouru("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setShouru("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setShouru("审核中");
					}
				} else {
					proVo.setShouru("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(4))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setXinyong("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setXinyong("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setXinyong("审核中");
					}
				} else {
					proVo.setXinyong("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(5))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setFangchan("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setFangchan("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setFangchan("审核中");
					}
				} else {
					proVo.setFangchan("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(6))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setJishu("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setJishu("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setJishu("审核中");
					}
				} else {
					proVo.setJishu("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(7))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setGouche("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setGouche("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setGouche("审核中");
					}
				} else {
					proVo.setGouche("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(8))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setJiehun("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setJiehun("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setJiehun("审核中");
					}
				} else {
					proVo.setJiehun("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(9))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setJuzhudi("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setJuzhudi("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setJuzhudi("审核中");
					}
				} else {
					proVo.setJuzhudi("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(10))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setShipin("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setShipin("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setShipin("审核中");
					}
				} else {
					proVo.setShipin("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(11))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setShidi("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setShidi("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setShidi("审核中");
					}
				} else {
					proVo.setShidi("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(12))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setXueli("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setXueli("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setXueli("审核中");
					}
				} else {
					proVo.setXueli("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(13))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setShoujishiming("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setShoujishiming("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setShoujishiming("审核中");
					}
				} else {
					proVo.setShoujishiming("待上传");
				}
			} else if (pro.getProId().getProId().equals(new BigDecimal(14))) {
				if (pro.getReviewStatus().equals(BigDecimal.ONE)) {
					proVo.setWeibo("审核通过" + pro.getAuditTimeStr());
				} else if (pro.getProStatus().equals(BigDecimal.ONE)) {
					if (pro.getReviewStatus().equals(BigDecimal.ZERO)) {
						proVo.setWeibo("审核未通过" + pro.getAuditTimeStr());
					} else {
						proVo.setWeibo("审核中");
					}
				} else {
					proVo.setWeibo("待上传");
				}
			}
		}
		return proVo;

	}

	/**
	 * 根据用户ID，查询BorrowingUserInfoPerson对象 2012-11-21 下午2:41:45 by HuYaHui
	 * 
	 * @param userid
	 *            用户ID
	 * @return
	 */
	private BorrowingUserInfoPerson findBorrowingUserInfoPersonByUserId(BigDecimal userid) {
		List<BorrowingUserInfoPerson> userInfoList = borrowinguserInfoPersonDao.findUserId(userid);
		// 用户详情信息
		BorrowingUserInfoPerson userInfoPerson = null;
		if (userInfoList.size() == 0) {
			userInfoPerson = new BorrowingUserInfoPerson();
			// 用户基本信息
			BorrowingUsers user = new BorrowingUsers();
			user.setUserId(userid);
			user.setInclosureSubmitTime(new Date());
			userInfoPerson.setUser(user);
		} else {
			userInfoPerson = userInfoList.get(0);
			userInfoPerson.getUser().setInclosureSubmitTime(new Date());
		}
		return userInfoPerson;
	}
}
