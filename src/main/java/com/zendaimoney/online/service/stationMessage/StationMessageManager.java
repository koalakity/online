package com.zendaimoney.online.service.stationMessage;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.dao.borrowing.ReleaseLoanDao;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.dao.homepage.HomepageUserMovementDao;
import com.zendaimoney.online.dao.personal.PersonalUserMessageSetDao;
import com.zendaimoney.online.dao.stationMessage.StationMessageStationLetterDao;
import com.zendaimoney.online.dao.stationMessage.StationMessageUserApproveDao;
import com.zendaimoney.online.dao.stationMessage.StationMessageUserDao;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.financial.FinancialSysMsg;
import com.zendaimoney.online.entity.homepage.HomepageMovementWord;
import com.zendaimoney.online.entity.homepage.HomepageUserMovement;
import com.zendaimoney.online.entity.personal.PersonalUserMessageSet;
import com.zendaimoney.online.entity.stationMessage.StationMessageStationLetter;
import com.zendaimoney.online.entity.stationMessage.StationMessageUserApprove;
import com.zendaimoney.online.entity.stationMessage.StationMessageUsers;
import com.zendaimoney.online.oii.sms.SMSSender;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class StationMessageManager {
	private static Logger logger = LoggerFactory.getLogger(StationMessageManager.class);

	@Autowired
	private StationMessageStationLetterDao stationMessageStationLetterDao;

	@Autowired
	private StationMessageUserApproveDao stationMessageUserApproveDao;

	@Autowired
	private StationMessageUserDao stationMessageUserDao;

	@Autowired
	private ReleaseLoanDao releaseLoanDao;

	@Autowired
	MimeMailService mimeMailService;
	@Autowired
	FinancialSysMsgDao sysMsgDao;
	@Autowired
	PersonalUserMessageSetDao messageSetDao;

	@Autowired
	HomepageUserMovementDao movementDao;

	public List<StationMessageStationLetter> findMsg(BigDecimal loanId) {
		List<StationMessageStationLetter> msgList = stationMessageStationLetterDao.findByIsDelOrderByMsgPath(loanId);
		return msgList;

	}

	// 检查用户权限
	public String checkPower(BigDecimal userId) {
		List<StationMessageUserApprove> list = stationMessageUserApproveDao.findByUserIdAndProId(userId);
		if (list.size() >= 2) {
			return "true";
		} else {
			return "false";
		}
	}

	// 对标进行留言
	@Transactional(readOnly = false)
	public void leaveMsg(BigDecimal userId, String message, BigDecimal loanId, BigDecimal receiverId) {
		StationMessageStationLetter smsl = new StationMessageStationLetter();
		smsl.setMsgKind("2");
		StationMessageUsers user = new StationMessageUsers();
		user.setUserId(userId);
		smsl.setSenderId(user);
		StationMessageUsers user2 = stationMessageUserDao.findOne(userId);
		smsl.setReceiverId(user2);
		smsl.setParentId(BigDecimal.ZERO);
		smsl.setChildId(smsl.getLetterId());
		smsl.setLoanId(loanId);
		message = message.replace("<", "").replace(">", "");
		smsl.setMessage(message);
		smsl.setIsDel("0");
		Date date = new Date();
		smsl.setSenderTime(new Timestamp(date.getTime()));
		logger.info("SAVE: STATION_LETTER ||  userId=" + userId + " msgKind=2 LoanId=" + loanId + " ChildId" + smsl.getLetterId());
		stationMessageStationLetterDao.save(smsl);
		// 发送系统消息
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		StationMessageUsers userMsg = stationMessageUserDao.findByUserId(userId);
		BorrowingLoanInfo loanInfo = releaseLoanDao.findByLoanId(loanId);
		sysMsg.setUserId(loanInfo.getUser().getUserId());
		sysMsg.setWordId(BigDecimal.valueOf(3));
		sysMsg.setParameter1(userMsg.getLoginName());
		sysMsg.setParameter2(loanInfo.getLoanTitle());
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		logger.info("SAVE: SYS_MSG ||  userId=" + sysMsg.getUserId() + " WordId=3 " + userMsg.getLoginName() + "  " + loanInfo.getLoanTitle());
		sysMsgDao.save(sysMsg);
		// 最新动态
		HomepageUserMovement movement = new HomepageUserMovement();
		HomepageMovementWord moWord = new HomepageMovementWord();
		moWord.setWordId(new BigDecimal(3));
		movement.setUserId(loanInfo.getUser().getUserId());
		movement.setWordId(moWord);
		movement.setParameter1(userMsg.getLoginName());
		movement.setParameter2(loanInfo.getLoanTitle());
		movement.setUrl2("/borrowing/releaseLoan/redirectLoanInfo?loanId=" + loanInfo.getLoanId());
		movement.setMsgKind("1");
		movement.setIsDel("0");
		movement.setHappenTime(new Date());
		logger.info("SAVE: USER_MOVEMENT ||  userId=" + movement.getUserId() + " WordId=3 " + userMsg.getLoginName() + "  " + loanInfo.getLoanTitle());
		movementDao.save(movement);

		List<PersonalUserMessageSet> messageList = messageSetDao.findByUserIdAndKindId(loanInfo.getUser().getUserId(), new BigDecimal(1));
		for (PersonalUserMessageSet messages : messageList) {
			if (messages.getMannerId().equals(new BigDecimal(2))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("0", userMsg.getLoginName());
				map.put("1", loanInfo.getLoanTitle());
				String msg = mimeMailService.transferMailContent("mess", map);
				mimeMailService.sendNotifyMail(msg, loanInfo.getUser().getEmail(), "有人对我借款列表提问");
			}
			if (messages.getMannerId().equals(new BigDecimal(3))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("0", userMsg.getLoginName());
				map.put("1", loanInfo.getLoanTitle());
				SMSSender.sendMessage("mess", loanInfo.getUser().getUserInfoPerson().getPhoneNo(), map);
			}
		}
	}

	// 对用户进行留言回复
	@Transactional(readOnly = false)
	public void replyToLoanMessage(BigDecimal loanId, BigDecimal receiverId, BigDecimal letterId, String message, BigDecimal userId) {
		// TODO userId 从session中取得
		StationMessageStationLetter ss = stationMessageStationLetterDao.findByLetterId(letterId);
		ss.setChildId(ss.getLetterId());
		logger.info("SAVE: STATION_LETTER ||  userId=" + userId + " msgKind=2 LoanId=" + loanId + " ChildId" + ss.getLetterId());
		stationMessageStationLetterDao.save(ss);

		StationMessageStationLetter smsl = new StationMessageStationLetter();
		smsl.setMsgKind("2");
		StationMessageUsers user = stationMessageUserDao.findOne(userId);
		// user.setUserId(userId);
		smsl.setSenderId(user);

		StationMessageUsers user2 = stationMessageUserDao.findOne(receiverId);
		// user2.setUserId(receiverId);
		smsl.setReceiverId(user2);

		smsl.setParentId(ss.getLetterId());
		smsl.setChildId(ss.getLetterId());
		smsl.setLoanId(loanId);
		message = message.replace("<", "").replace(">", "");
		smsl.setMessage(message);
		smsl.setIsDel("0");
		Date date = new Date();
		smsl.setSenderTime(new Timestamp(date.getTime()));
		logger.info("SAVE: STATION_LETTER ||  userId=" + userId + " msgKind=2 LoanId=" + loanId + " ChildId" + smsl.getLetterId() + "message=" + message);
		stationMessageStationLetterDao.save(smsl);

		stationMessageStationLetterDao.save(smsl);
		// 发送系统消息
		FinancialSysMsg sysMsg = new FinancialSysMsg();
		StationMessageUsers userMsg = stationMessageUserDao.findByUserId(userId);
		BorrowingLoanInfo loanInfo = releaseLoanDao.findByLoanId(loanId);
		sysMsg.setUserId(receiverId);
		sysMsg.setWordId(BigDecimal.valueOf(3));
		sysMsg.setParameter1(userMsg.getLoginName());
		sysMsg.setParameter2(loanInfo.getLoanTitle());
		sysMsg.setHappenTime(new Date());
		sysMsg.setIsDel("0");
		logger.info("SAVE: SYS_MSG ||  userId=" + sysMsg.getUserId() + " WordId=3 " + userMsg.getLoginName() + "  " + loanInfo.getLoanTitle());
		sysMsgDao.save(sysMsg);
		// 最新动态
		// HomepageUserMovement movement = new HomepageUserMovement();
		// HomepageMovementWord moWord = new HomepageMovementWord();
		// moWord.setWordId(new BigDecimal(18));
		// movement.setUserId(loanInfo.getUser().getUserId());
		// movement.setWordId(moWord);
		// movement.setParameter1(userMsg.getLoginName());
		// movement.setParameter2(loanInfo.getLoanTitle());
		// movement.setUrl2("/borrowing/releaseLoan/redirectLoanInfo?loanId=" +
		// loanInfo.getLoanId());
		// movement.setMsgKind("1");
		// movement.setIsDel("0");
		// movement.setHappenTime(new Date());
		// movementDao.save(movement);

		List<PersonalUserMessageSet> messageList = messageSetDao.findByUserIdAndKindId(user2.getUserId(), new BigDecimal(8));
		for (PersonalUserMessageSet messages : messageList) {
			if (messages.getMannerId().equals(new BigDecimal(2))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("0", userMsg.getLoginName());
				map.put("1", loanInfo.getLoanTitle());
				String msg = mimeMailService.transferMailContent("repmess", map);
				mimeMailService.sendNotifyMail(msg, user2.getEmail(), "借入者回答了我对借款列表的提问");
			}
			if (messages.getMannerId().equals(new BigDecimal(3))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("0", userMsg.getLoginName());
				map.put("1", loanInfo.getLoanTitle());
				SMSSender.sendMessage("repmess", user2.getStationMessageUserInfoPerson().getPhoneNo(), map);
			}
		}

	}

	// 对用户进行留言
	@Transactional(readOnly = false)
	public void levaeMsgFroUser(BigDecimal loanId, BigDecimal receiverId, BigDecimal letterId, String message) {
		// TODO userId 从session中取得
		BigDecimal userId = new BigDecimal("1750");
		StationMessageStationLetter ss = stationMessageStationLetterDao.findByLetterId(letterId);
		ss.setChildId(ss.getLetterId());
		logger.info("SAVE: STATION_LETTER ||  userId=1750" + " ChildId" + ss.getLetterId());
		stationMessageStationLetterDao.save(ss);

		StationMessageStationLetter smsl = new StationMessageStationLetter();
		smsl.setMsgKind("2");
		StationMessageUsers user = new StationMessageUsers();
		user.setUserId(userId);
		smsl.setSenderId(user);

		StationMessageUsers user2 = new StationMessageUsers();
		user2.setUserId(receiverId);
		smsl.setReceiverId(user2);

		smsl.setParentId(ss.getLetterId());
		// smsl.setChildId(ss.getLetterId());
		smsl.setLoanId(loanId);
		smsl.setMessage(message);
		smsl.setIsDel("0");
		Date date = new Date();
		smsl.setSenderTime(new Timestamp(date.getTime()));
		logger.info("SAVE: STATION_LETTER ||  userId=" + receiverId + " loanId=" + loanId + " ChildId" + ss.getLetterId() + " message=" + message);
		stationMessageStationLetterDao.save(smsl);

	}

}
