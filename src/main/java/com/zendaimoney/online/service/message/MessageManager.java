package com.zendaimoney.online.service.message;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.dao.message.MessageStationLetterDao;
import com.zendaimoney.online.dao.message.MessageSysMsgDao;
import com.zendaimoney.online.dao.message.MessageUserDao;
import com.zendaimoney.online.entity.message.MessageStationLetter;
import com.zendaimoney.online.entity.message.MessageSysMsg;
import com.zendaimoney.online.entity.message.MessageUsers;
import com.zendaimoney.online.vo.message.MessageSxxVo;
import com.zendaimoney.online.vo.message.MessageSysMsgVo;
import com.zendaimoney.online.vo.message.ReturnMessageInfoVO;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class MessageManager {
	private static Logger logger = LoggerFactory.getLogger(MessageManager.class);

	@Autowired
	MessageStationLetterDao messageDao;
	@Autowired
	MessageStationLetterDao stationLetterDao;
	@Autowired
	MessageUserDao userDao;
	@Autowired
	MessageSysMsgDao sysMsgDao;

	// 消息条数
	public ReturnMessageInfoVO msgCount(HttpServletRequest req) {
		MessageUsers user = getUsers(req);
		ReturnMessageInfoVO returnvo = new ReturnMessageInfoVO();
		List<MessageSysMsg> sysList = sysMsgDao.findByUserIdAndIsDelOrderByHappenTimeDesc(user, "0");
		List<MessageStationLetter> sxxList = stationLetterDao.findByReceiverIdAndMsgKindAndReceiverDelStatus(user, "1", BigDecimal.ZERO);
		List<MessageStationLetter> fxxList = stationLetterDao.findBySenderIdAndMsgKindAndSenderDelStatus(user, "1", BigDecimal.ZERO);
		returnvo.setSystemItems(String.valueOf(sysList.size()));
		returnvo.setInboxItems(String.valueOf(sxxList.size()));
		returnvo.setItems(String.valueOf(fxxList.size()));
		returnvo.setSysList(sysList);
		return returnvo;
	}

	public static void main(String[] args) {
//		String pig = "hyh";  
//		Object[] array = new Object[]{"A","B","C","D"};  
//		String value = MessageFormat.format(pig, array);  
//		System.out.println(value);
		
		System.out.println("[1]".replaceAll("^\\[.\\]$", "{}"));
	}
	
	// 系统消息
//	public List<MessageSysMsgVo> systemMessageInfo(List<MessageSysMsg> sysList) {
//		
//		List<MessageSysMsgVo> sysVoList = new ArrayList<MessageSysMsgVo>();
//		if(sysList==null || sysList.size()==0){
//			return sysVoList;
//		}
//		
//		for (MessageSysMsg msg : sysList) {
//			MessageSysMsgVo vo = new MessageSysMsgVo();
//			StringBuffer mw = new StringBuffer(msg.getWordId().getWordContext());
//			int si = mw.indexOf("[");
//			if (si == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl1()+"\">"+msg.getParameter1()+"</a>";
//				mw.replace(si, si + 3, msg.getParameter1());
//			}
//			int si2 = mw.indexOf("[");
//			if (si2 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl2()+"\">"+msg.getParameter2()+"</a>";
//				mw.replace(si2, si2 + 3, "“" + msg.getParameter2() + "”");
//			}
//
//			int si3 = mw.indexOf("[");
//			if (si3 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl3()+"\">"+msg.getParameter3()+"</a>";
//				mw.replace(si3, si3 + 3, msg.getParameter3());
//			}
//
//			int si4 = mw.indexOf("[");
//			if (si4 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl4()+"\">"+msg.getParameter4()+"</a>";
//				mw.replace(si4, si4 + 3, msg.getParameter4());
//			}
//
//			int si5 = mw.indexOf("[");
//			if (si5 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl5()+"\">"+msg.getParameter5()+"</a>";
//				mw.replace(si5, si5 + 3, msg.getParameter5());
//			}
//			vo.setMsgId(msg.getId().toString());
//			vo.setMsgContent(mw.toString());
//			vo.setSendTime(msg.getHappenTime().toString().substring(0, 19));
//			sysVoList.add(vo);
//		}
//
//		return sysVoList;
//	}
	
	// 系统消息
	public List<MessageSysMsgVo> systemMessageInfo(List<MessageSysMsg> sysList) {
		List<MessageSysMsgVo> sysVoList = new ArrayList<MessageSysMsgVo>();
		for (MessageSysMsg msg : sysList) {
			MessageSysMsgVo vo = new MessageSysMsgVo();
			
			//模板内容:xxx{0}ssdaf{1}
			String wordContext=msg.getWordId().getWordContext();
			
			//一直是未使用的字段,可考虑把下面的参数字段统一保存到desc中用特定字符区分
			//String desc=msg.getWordId().getDescription();
			
			//消息主表的参数，对应上面的{0}{1}
			Object[] array = new Object[]{msg.getParameter1(),msg.getParameter2(),msg.getParameter3(),msg.getParameter4(),msg.getParameter5()};  
			String value = MessageFormat.format(wordContext, array);  
			
//			2012-11-26注释，用上面方法代替，下次发版本可以删除 TODO 			
//			StringBuffer mw = new StringBuffer(msg.getWordId().getWordContext());
//			int si = mw.indexOf("[");
//			if (si == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl1()+"\">"+msg.getParameter1()+"</a>";
//				mw.replace(si, si + 3, msg.getParameter1());
//			}
//			int si2 = mw.indexOf("[");
//			if (si2 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl2()+"\">"+msg.getParameter2()+"</a>";
//				mw.replace(si2, si2 + 3, "“" + msg.getParameter2() + "”");
//			}
//
//			int si3 = mw.indexOf("[");
//			if (si3 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl3()+"\">"+msg.getParameter3()+"</a>";
//				mw.replace(si3, si3 + 3, msg.getParameter3());
//			}
//
//			int si4 = mw.indexOf("[");
//			if (si4 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl4()+"\">"+msg.getParameter4()+"</a>";
//				mw.replace(si4, si4 + 3, msg.getParameter4());
//			}
//
//			int si5 = mw.indexOf("[");
//			if (si5 == -1) {
//				vo.setMsgContent(mw.toString());
//			} else {
//				// String word =
//				// "<a href=\""+msg.getUrl5()+"\">"+msg.getParameter5()+"</a>";
//				mw.replace(si5, si5 + 3, msg.getParameter5());
//			}
//			vo.setMsgContent(mw.toString());
			vo.setMsgContent(value);
			
			vo.setMsgId(msg.getId().toString());
			vo.setSendTime(msg.getHappenTime().toString().substring(0, 19));
			sysVoList.add(vo);
		}

		return sysVoList;
	}

	// 收信箱
	public List<MessageSxxVo> getSxx(HttpServletRequest req) {
		MessageUsers user = getUsers(req);
		List<MessageStationLetter> sxxList = stationLetterDao.findByReceiverIdAndMsgKindAndReceiverDelStatus(user, "1", BigDecimal.ZERO);
		List<MessageSxxVo> voList = new ArrayList<MessageSxxVo>();
		for (MessageStationLetter sxx : sxxList) {
			MessageSxxVo vo = new MessageSxxVo();
			try {
				PropertyUtils.copyProperties(vo, sxx);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vo.setSendDateStr(DateUtil.getStrDate(sxx.getSenderTime()));
			voList.add(vo);
		}
		return voList;
	}

	// 发信箱
	public List<MessageSxxVo> getFxx(HttpServletRequest req) {
		MessageUsers user = getUsers(req);
		List<MessageStationLetter> fxxList = stationLetterDao.findBySenderIdAndMsgKindAndSenderDelStatus(user, "1", BigDecimal.ZERO);
		List<MessageSxxVo> voList = new ArrayList<MessageSxxVo>();
		for (MessageStationLetter fxx : fxxList) {
			MessageSxxVo vo = new MessageSxxVo();
			try {
				PropertyUtils.copyProperties(vo, fxx);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vo.setSendDateStr(DateUtil.getStrDate(fxx.getSenderTime()));
			voList.add(vo);
		}
		return voList;
	}

	// 收信箱批量删除
	@Transactional(readOnly = false)
	public boolean delCheck(String msgIds) {
		String[] msgIdArr = msgIds.split(",");
		for (int i = 0; i < msgIdArr.length; i++) {
			MessageStationLetter letter = stationLetterDao.findByLetterId(BigDecimal.valueOf(Long.parseLong(msgIdArr[i])));
			if (null != letter) {
				letter.setReceiverDelStatus(BigDecimal.ONE);
				logger.info("DELETE  STATION_LETTER | letterId="+letter.getLetterId());
				stationLetterDao.save(letter);
			}
		}
		return true;
	}

	// 发信箱批量删除
	@Transactional(readOnly = false)
	public boolean delCheckFxx(String msgIds) {
		String[] msgIdArr = msgIds.split(",");
		for (int i = 0; i < msgIdArr.length; i++) {
			MessageStationLetter letter = stationLetterDao.findByLetterId(BigDecimal.valueOf(Long.parseLong(msgIdArr[i])));
			if (null != letter) {
				letter.setSenderDelStatus(BigDecimal.ONE);
				stationLetterDao.save(letter);
			}
		}
		return true;
	}

	// 系统消息批量删除
	@Transactional(readOnly = false)
	public boolean sysDelCheck(String msgIds) {
		String[] msgIdArr = msgIds.split(",");
		for (int i = 0; i < msgIdArr.length; i++) {
			MessageSysMsg sysMsg = sysMsgDao.findById(BigDecimal.valueOf(Long.parseLong(msgIdArr[i])));
			if (null != sysMsg) {
				sysMsg.setIsDel("1");
				sysMsgDao.save(sysMsg);
			}
		}
		return true;
	}

	// 收信箱删除一条
	@Transactional(readOnly = false)
	public boolean delOne(String letterId) {
		MessageStationLetter letter = stationLetterDao.findByLetterId(BigDecimal.valueOf(Long.parseLong(letterId)));
		if (null != letter) {
			letter.setReceiverDelStatus(BigDecimal.ONE);
			stationLetterDao.save(letter);
			return true;
		} else {
			return false;
		}

	}

	// 发信箱删除一条
	@Transactional(readOnly = false)
	public boolean delOneFxx(String letterId) {
		MessageStationLetter letter = stationLetterDao.findByLetterId(BigDecimal.valueOf(Long.parseLong(letterId)));
		if (null != letter) {
			letter.setSenderDelStatus(BigDecimal.ONE);
			stationLetterDao.save(letter);
			logger.info("DELETE  STATION_LETTER | letterId="+letter.getLetterId());
			return true;
		} else {
			return false;
		}

	}

	// 系统消息删除一条
	@Transactional(readOnly = false)
	public boolean sysDelOne(String letterId) {
		MessageSysMsg msg = sysMsgDao.findById(BigDecimal.valueOf(Long.parseLong(letterId)));
		if (null != msg) {
			msg.setIsDel("1");
			logger.info("DELETE  SYS_MSG | msgId="+msg.getId()+" isDEl="+msg.getIsDel());
			sysMsgDao.save(msg);
			return true;
		} else {
			return false;
		}

	}

	// 回复
	@Transactional(readOnly = false)
	public void sendMsg(String senderId, String receiverId, String msg) {
		MessageUsers sender = new MessageUsers();
		sender.setUserId(BigDecimal.valueOf(Long.parseLong(senderId)));
		MessageUsers receiver = new MessageUsers();
		receiver.setUserId(BigDecimal.valueOf(Long.parseLong(receiverId)));
		MessageStationLetter letter = new MessageStationLetter();
		letter.setMsgKind("1");
		letter.setSenderId(sender);
		letter.setReceiverId(receiver);
		letter.setMessage(msg);
		letter.setViewStatus(BigDecimal.ZERO);
		letter.setSenderDelStatus(BigDecimal.ZERO);
		letter.setReceiverDelStatus(BigDecimal.ZERO);
		letter.setSenderTime(new Date());
		letter.setIsDel("0");
		logger.info("SAVE  STATION_LETTER |letterId ="+letter.getLetterId()+" MsgKind=1 sender="+sender.getUserId());
		stationLetterDao.save(letter);
	}

	// 取得userid
	public MessageUsers getUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		BigDecimal userid = (BigDecimal) session.getAttribute("curr_login_user_id");
		MessageUsers userInfo = userDao.findByUserId(userid);
		return userInfo;
	}

}
