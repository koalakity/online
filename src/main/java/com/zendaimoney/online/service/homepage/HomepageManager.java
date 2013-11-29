package com.zendaimoney.online.service.homepage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.Calculator;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.dao.fundDetail.FundDao;
import com.zendaimoney.online.dao.homepage.HomepageAcTLedgerDao;
import com.zendaimoney.online.dao.homepage.HomepageInvestInfoDao;
import com.zendaimoney.online.dao.homepage.HomepageLoanInfoDao;
import com.zendaimoney.online.dao.homepage.HomepageStationLetterDao;
import com.zendaimoney.online.dao.homepage.HomepageUserDao;
import com.zendaimoney.online.dao.homepage.HomepageUserMovementDao;
import com.zendaimoney.online.entity.homepage.HomepageInvestInfo;
import com.zendaimoney.online.entity.homepage.HomepageLoanInfo;
import com.zendaimoney.online.entity.homepage.HomepageStationLetter;
import com.zendaimoney.online.entity.homepage.HomepageUserMovement;
import com.zendaimoney.online.entity.homepage.HomepageUsers;
import com.zendaimoney.online.vo.homepage.HomePageVO;
import com.zendaimoney.online.vo.homepage.HomepageLeaveMsgVO;
import com.zendaimoney.online.vo.homepage.HomepageLoanInfoTempVO;
import com.zendaimoney.online.vo.homepage.HomepageReturnPageInfoVO;
import com.zendaimoney.online.vo.homepage.HomepageUserMovementVO;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class HomepageManager {

	@Autowired
	HomepageUserDao userDao;
	@Autowired
	HomepageInvestInfoDao investInfoDao;
	@Autowired
	HomepageLoanInfoDao loanInfoDao;
	@Autowired
	HomepageAcTLedgerDao acTLedgerDao;
	@Autowired
	HomepageUserMovementDao movementDao;
	@Autowired
	HomepageStationLetterDao stationLetterDao;
	@Autowired
	private FundDao dao;

	public HomePageVO getHomePageVO(HttpServletRequest req) {

		HttpSession session = req.getSession();
		BigDecimal userId = ((BigDecimal) session.getAttribute("curr_login_user_id"));

		HomePageVO VO = new HomePageVO();
		HomepageUsers user = userDao.findByUserId(userId);// 用户信息
		// HomepageUserInfoPerson personInfo =
		// persionInfoDAO.findByUserId(userId);// 用户个人信息
		// HomepageUserCreditNote creditNote =
		// creditNoteDAO.findByUserId(userId);// 信用信息

		if (user != null) {
			if (user.getLoginName() != null) {
				VO.setUserName(user.getLoginName());// 昵称
			}
			if (user.getRegTime() != null) {
				// VO.setRegisterTime(user.getRegTime());// 注册时间
			}
		}
		// if (personInfo != null) {
		// VO.setCurrPlace(personInfo.getLiveAddress());// 现居住地
		// }
		// if (creditNote != null) {
		// VO.setGrade(creditNote.getCreditGrade() + "");// 信用等级
		// }
		// ----------------查询账户余额-------------
		// double amount_total = fundDAO.getBalance(userId);// 查询账户余额
		// double freeze_lc = fundDAO.getFreezeFundsFinancialByUserId(userId);//
		// 投标冻结金额
		// double freeze_tx = fundDAO.getFreezeFundsWithdrawByUserId(userId);//
		// 提现冻结金额
		// double amount_real = Calculator.sub(Calculator.sub(amount_total,
		// freeze_lc), freeze_tx);// 用户可用余额
		// // ----------------------------------------
		// VO.setAccAmount(ObjectFormatUtil.formatCurrency(amount_real));// 可用余额
		// VO.setFreezeAmount(ObjectFormatUtil.formatCurrency((Calculator.add(
		// freeze_lc, freeze_tx))));// 冻结金额
		// ----------------------------------------
		// VO.setLoanCount(homepageDaoImpl.querySuccLoanCount(userId) + "");//
		// 成功借款笔数
		// VO.setBidCount(homepageDaoImpl.querySuccBidCount(userId) + "");//
		// 成功理财笔数
		return VO;
	}

	// 点击我的主页 ,初始化用户个人信息
	public HomepageReturnPageInfoVO getUserInfo(HttpServletRequest req) {
		HomepageUsers currUser = getUsers(req);
		BigDecimal userId = currUser.getUserId();
		HomepageReturnPageInfoVO vo = new HomepageReturnPageInfoVO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setZcdate(sdf.format(currUser.getRegTime()));
		try {
			PropertyUtils.copyProperties(vo, currUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 成功借款笔数
		int successLoanNumber = 0;
		int successInvestNumber = 0;
		// 可用余额和冻结金额
		/*
		 * List<HomepageAcTLedger> actledger =
		 * acTLedgerDao.findByTotalAccountId(
		 * currUser.getTCustomerId().longValue()); for (HomepageAcTLedger ledger
		 * : actledger) { double freezeAmount = 0.0; if
		 * (ledger.getBusiType().equals("4")) { // TODO freezeAmount =
		 * ledger.getPayBackAmt(); double balance = 0.0; if (ledger.getAmount()
		 * != 0) { balance = ledger.getAmount(); }
		 * vo.setAccountBalance(ObjectFormatUtil.formatCurrency(balance)); } if
		 * (ledger.getBusiType().equals("5")) {
		 * 
		 * if (ledger.getAmount() != 0) { freezeAmount =
		 * Calculator.add(freezeAmount, ledger.getAmount()); }
		 * 
		 * }
		 * 
		 * }
		 */
		double amount_total = dao.getBalance(userId);// 查询账户余额
		double freeze_lc = dao.getFreezeFundsFinancialByUserId(userId);// 投标冻结金额
		double freeze_tx = dao.getFreezeFundsWithdrawByUserId(userId);// 提现冻结金额
		vo.setAccountBalance(ObjectFormatUtil.formatCurrency(Calculator.add(Calculator.add(freeze_lc, freeze_tx), amount_total)));// 账户余额
		vo.setFreezeAmount(ObjectFormatUtil.formatCurrency(Calculator.add(freeze_lc, freeze_tx)));// 冻结总额
		// 几款笔数and投资笔数

		List<HomepageLoanInfo> loanList = loanInfoDao.findByUserIdOrderByReleaseTimeDesc(userId);
		for (HomepageLoanInfo loanInfo : loanList) {
			// 发布借款笔数
			int status = loanInfo.getStatus().intValue();
			if (status == 4 || status == 5 || status == 6 || status == 7) {
				// 成功借款笔数
				successLoanNumber++;

			}
		}
		List<HomepageInvestInfo> investList = investInfoDao.findByUserIdOrderByInvestTimeDesc(userId);
		for (HomepageInvestInfo investInfo : investList) {
			// 投标笔数
			String status = investInfo.getStatus();
			if (status.equals("3") || status.equals("4")) {
				// 成功投标笔数
				successInvestNumber++;

			}
		}
		vo.setLoanItems(String.valueOf(successLoanNumber));
		vo.setTenderItems(String.valueOf(successInvestNumber));

		return vo;
	}

	// 最新动态
	public List<HomepageUserMovementVO> getUserMovement(BigDecimal userId, HttpServletRequest req) {
		List<HomepageUserMovement> mList = movementDao.findByUserIdOrderByHappenTimeDesc(userId);
		List<HomepageUserMovementVO> mvList = new ArrayList<HomepageUserMovementVO>();
		for (HomepageUserMovement movenment : mList) {
			HomepageUserMovementVO vo = new HomepageUserMovementVO();
			StringBuffer mw = new StringBuffer(movenment.getWordId().getWordContext());
			int si = mw.indexOf("{");
			if (movenment.getParameter2() == null || movenment.getParameter2().equals("")) {
				String word = "<a href=\"" + req.getContextPath() + "" + movenment.getUrl1() + "\">" + movenment.getParameter1() + "</a>";
				mw = mw.replace(si, si + 3, word);
				vo.setWordContext1(mw.toString());
				vo.setrDate(DateUtil.getStrDate(movenment.getHappenTime()));
				mvList.add(vo);
			} else {
				String word;
				String word2;
				if (movenment.getWordId().getWordId().equals(new BigDecimal(3))) {
					word = movenment.getParameter1();
				} else {
					word = "<a href=\"" + req.getContextPath() + "" + movenment.getUrl1() + "\">" + movenment.getParameter1() + "</a>";
				}
				mw = mw.replace(si, si + 3, word);
				int si2 = mw.indexOf("{");
				if (movenment.getWordId().getWordId().equals(new BigDecimal(6))) {
					word2 = movenment.getParameter2();
				} else {
					word2 = "<a href=\"" + req.getContextPath() + "" + movenment.getUrl2() + "\">" + movenment.getParameter2() + "</a>";
				}
				mw = mw.replace(si2, si2 + 3, word2);
				vo.setWordContext1(mw.toString());
				vo.setrDate(DateUtil.getStrDate(movenment.getHappenTime()));
				mvList.add(vo);
			}
		}

		return mvList;
	}

	// 借款记录
	public List<HomepageLoanInfoTempVO> getLoanNote(HttpServletRequest req) {
		List<HomepageLoanInfoTempVO> loanVoList = new ArrayList<HomepageLoanInfoTempVO>();
		HomepageUsers user = getUsers(req);
		List<HomepageLoanInfo> loanList = loanInfoDao.findByUserIdOrderByReleaseTimeDesc(user.getUserId());
		for (HomepageLoanInfo loan : loanList) {
			if (loan.getReleaseStatus().equals(BigDecimal.ONE)) {
				HomepageLoanInfoTempVO vo = new HomepageLoanInfoTempVO();
				vo.setLoanId(loan.getLoanId().toString());
				vo.setLoanUse(loan.getLoanUse().toString());
				vo.setLoanTitle(loan.getLoanTitle());
				vo.setLoanTitleStr(loan.getLoanTitleStr());
				vo.setLoanAmountStr(ObjectFormatUtil.formatCurrency(loan.getLoanAmount()));
				vo.setLilv(ObjectFormatUtil.formatPercent(loan.getYearRate(), "##,#0.00%"));
				vo.setLoanQx(loan.getLoanDuration().toString());

				List<HomepageInvestInfo> investList = investInfoDao.findByLoanId(loan.getLoanId());
				vo.setItems(String.valueOf(investList.size()));
				double iAmount = 0d;
				for (HomepageInvestInfo invest : investList) {
					iAmount = Calculator.add(iAmount, invest.getInvestAmount());
				}
				double jindu = 0d;
				jindu = Calculator.div(iAmount, loan.getLoanAmount());
				vo.setProgress(ObjectFormatUtil.formatPercent(jindu, "###0.00%"));
				vo.setReleaseDate(loan.getReleaseTime().toString().substring(0, 19));
				loanVoList.add(vo);
			}
		}
		return loanVoList;
	}

	// 投标记录
	public List<HomepageLoanInfoTempVO> getInvestNote(HttpServletRequest req) {
		List<HomepageLoanInfoTempVO> loanVoList = new ArrayList<HomepageLoanInfoTempVO>();
		HomepageUsers user = getUsers(req);
		List<HomepageInvestInfo> invList = investInfoDao.findByUserIdOrderByInvestTimeDesc(user.getUserId());
		for (HomepageInvestInfo inv : invList) {
			HomepageLoanInfoTempVO vo = new HomepageLoanInfoTempVO();
			HomepageLoanInfo loan = loanInfoDao.findByLoanId(inv.getLoanId());
			vo.setLoanId(inv.getLoanId().toString());
			vo.setLoanTitle(loan.getLoanTitleStr());
			vo.setLoanAmountStr(ObjectFormatUtil.formatCurrency(inv.getInvestAmount()));
			vo.setLilv(ObjectFormatUtil.formatPercent(loan.getYearRate(), "##,#0.00%"));
			vo.setLoanQx(loan.getLoanDuration().toString());
			vo.setLoanKind(loan.getStatus().toString());
			vo.setReleaseDate(inv.getInvestTime().toString().substring(0, 19));
			if (loan.getActTLedgerId().getInterestStart() != null) {
				vo.setLoanDate(loan.getActTLedgerId().getInterestStart().toString());
			} else {
				vo.setLoanDate("");
			}
			loanVoList.add(vo);
		}
		return loanVoList;
	}

	// 留言板
	public List<HomepageLeaveMsgVO> showLeaveMsg(HttpServletRequest req) {
		List<HomepageLeaveMsgVO> loanVoList = new ArrayList<HomepageLeaveMsgVO>();
		HomepageUsers user = getUsers(req);
		// 用户发布借款的留言数量
		List<HomepageLoanInfo> loanList = loanInfoDao.findByUserIdOrderByReleaseTimeDesc(user.getUserId());

		for (HomepageLoanInfo loan : loanList) {
			HomepageLeaveMsgVO vo = new HomepageLeaveMsgVO();
			List<HomepageStationLetter> letterList1 = stationLetterDao.findByLoanIdLoanIdAndMsgKindOrderBySenderTimeDesc(loan.getLoanId(), "2");
			int size = letterList1.size();
			if (letterList1.size() > 0) {
				vo.setLoanId(String.valueOf(loan.getLoanId()));
				vo.setLoanKind("1");
				vo.setTitle(loan.getLoanTitleStr());
				vo.setStatus(String.valueOf(letterList1.get(0).getLoanId().getStatus()));
				vo.setLeaveCount(String.valueOf(size));
				vo.setLastLeaveTime(letterList1.get(0).getSenderTime().toString().substring(0, 19));
				vo.setReleaseTime(loan.getReleaseTime().toString().substring(0, 19));
				loanVoList.add(vo);
			}
		}
		// 用户对别人借款的留言
		List<HomepageStationLetter> letterList2 = stationLetterDao.findBySenderIdAndMsgKindOrderBySenderTimeDesc(user.getUserId(), "2");
		List loanLetterList = stationLetterDao.getLetter(user.getUserId(), "2");
		for (int i = 0; i < loanLetterList.size(); i++) {
			BigDecimal loanId = new BigDecimal(loanLetterList.get(i).toString());
			List<HomepageStationLetter> letterList3 = stationLetterDao.findBySenderIdAndLoanIdLoanIdAndMsgKindOrderBySenderTimeDesc(user.getUserId(), loanId, "2");
			if (letterList3.size() > 0) {
				if (!letterList3.get(0).getLoanId().getUserId().equals(user.getUserId())) {
					HomepageLeaveMsgVO vo = new HomepageLeaveMsgVO();
					vo.setLoanId(String.valueOf(letterList3.get(0).getLoanId().getLoanId()));
					vo.setLoanKind("2");
					vo.setTitle(letterList3.get(0).getLoanId().getLoanTitle());
					vo.setStatus(String.valueOf(letterList3.get(0).getLoanId().getStatus()));
					vo.setLastLeaveTime(letterList3.get(0).getSenderTime().toString().substring(0, 19));
					List<HomepageStationLetter> list = stationLetterDao.findByLoanIdLoanIdAndMsgKindOrderBySenderTimeDesc(loanId, "2");
					vo.setLeaveCount(String.valueOf(list.size()));
					vo.setReleaseTime(letterList3.get(0).getLoanId().getReleaseTime().toString().substring(0, 19));
					loanVoList.add(vo);
				}
			}
		}
		Collections.sort(loanVoList, new Comparator<HomepageLeaveMsgVO>() {
			public int compare(HomepageLeaveMsgVO b1, HomepageLeaveMsgVO b2) {
				return b2.getReleaseTime().compareTo(b1.getReleaseTime());
			}
		});
		return loanVoList;
	}

	// public HomepageReturnPageInfoVO getHeadAndNewInfo(HttpServletRequest req)
	// {
	// HomepageUsers currUser = userDao.findByUserId(getUsers(req).getUserId());
	// HomepageReturnPageInfoVO vo = new HomepageReturnPageInfoVO();
	//
	// try {
	// PropertyUtils.copyProperties(vo, currUser);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // /** 用户信息 */
	// // HomepageAcTLedger acTLedgerlist=
	// // personalDao.getAcTLedgerInfo(currUserIdStr);
	// // HomepageUserCreditNote
	// // userCreditNote=personalDao.getUserCreditNoteinfo(currUserIdStr);
	// //
	// // returnPageInfoVO.setLoginName(currUser.getLoginName());//用户昵称
	// // returnPageInfoVO.setZcdate(currUser.getRegTime());//注册时间
	// String amount = "";
	// // if(null!=acTLedgerlist&&null!=acTLedgerlist.getAmount())
	// // {
	// // amount=acTLedgerlist.getAmount().toString()+"0";
	// // }
	// // returnPageInfoVO.setAccountBalance(amount);//账户余额
	// // String liveaddress="";
	// //
	// if(null!=currUser.getUserInfoPerson()&&null!=currUser.getUserInfoPerson().getLiveAddress())
	// // {
	// // liveaddress=currUser.getUserInfoPerson().getLiveAddress();
	// // }
	// // returnPageInfoVO.setLiveAddress(liveaddress);//现所在地
	// // String creditGrade="";
	// // if(null!=userCreditNote&&null!=userCreditNote.getCreditGrade())
	// // {
	// // creditGrade=userCreditNote.getCreditGrade().toString();
	// // }
	// // returnPageInfoVO.setCredit_grade(creditGrade);//信用等级
	// // /**冻结金额*/
	// //
	// returnPageInfoVO.setFreezeAmount(personalDao.getFreezeAmount(currUserIdStr!=null
	// // ?currUserIdStr:BigDecimal.ZERO).toString());
	// // Long loanItems=personalDao.getloanItems(currUserIdStr);
	// // if(null!=loanItems)
	// // {
	// // returnPageInfoVO.setLoanItems(new BigDecimal(loanItems.longValue()));
	// // }
	// // BigDecimal tenderer=personalDao.getSuccessTenderer(currUserIdStr);
	// // if(null!=tenderer)
	// // {
	// // returnPageInfoVO.setTenderItems(tenderer.toString());
	// // }
	// // /** 借款信息、进度、笔数*/
	// // List returnProList = (ArrayList)
	// // personalDao.getProgressLoan(currUserIdStr);
	// // returnPageInfoVO.setRetLoanInfoList(returnProList);
	// // /** 投标记录*/
	// // List<HomepageInvestInfoVO> tenderInfo =
	// // personalDao.getTenderInfo(currUserIdStr);//投标记录
	// // returnPageInfoVO.setTenderVo(tenderInfo);
	// // /** 我的最新动态*/
	// // List<HomepageUserMovementVO> usermovInfo=
	// //
	// (List<HomepageUserMovementVO>)personalDao.getNewsDynamicsInfo(currUserIdStr);
	// // returnPageInfoVO.setUsermovInfo(usermovInfo);
	// // /** 留言板*/
	// // List<HomepageLoanInfoTempVO> messageLoanInfo =
	// // personalDao.genMessageInfo(currUser.getUserId());
	// // returnPageInfoVO.setMessageInfo(messageLoanInfo);
	// // /** 个人头像*/
	// // String headPath = personalDao.getHeadPath(currUserIdStr);
	// // returnPageInfoVO.setHeadPath(headPath);
	// // return returnPageInfoVO;
	// return vo;
	// }

	// 取得userid
	public HomepageUsers getUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		HomepageUsers userInfo = userDao.findByUserId(userid);
		return userInfo;
	}

}
