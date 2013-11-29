package com.zendaimoney.online.web.message;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.service.message.MessageManager;
import com.zendaimoney.online.vo.message.MessageSxxVo;
import com.zendaimoney.online.vo.message.MessageSysMsgVo;
import com.zendaimoney.online.vo.message.ReturnMessageInfoVO;

@Controller
@RequestMapping(value = "/message/message/")
public class MessageController {

	@Autowired
	MessageManager messageManager;

	@RequestMapping(value = "messagePageHead")
	public String messagePageHead(HttpServletRequest req, Model model) {
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		List<MessageSysMsgVo> sysMsgList = messageManager.systemMessageInfo(returnVo.getSysList());
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("sysMsgList", sysMsgList);
		model.addAttribute("sysMsgNum", sysMsgList.size());
		return "/message/messagePage_head";
	}

	// 系统消息
	@RequestMapping(value = "showXtxx")
	public String showXtxx(@RequestParam("pager.offset") String offset, HttpServletRequest req, Model model) {
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		List<MessageSysMsgVo> sysMsgList = messageManager.systemMessageInfo(returnVo.getSysList());
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("sysMsgList", sysMsgList);
		model.addAttribute("sysMsgNum", sysMsgList.size());
		return "/message/messagePage_xtxx";
	}

	// 收信箱
	@RequestMapping(value = "showSxx")
	public String showSxx(@RequestParam("pager.offset") String offset, HttpServletRequest req, Model model) {
		List<MessageSxxVo> msgList = messageManager.getSxx(req);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("msgList", msgList);
		model.addAttribute("msgNum", msgList.size());
		return "/message/messagePage_sxx";
	}

	// 发信箱
	@RequestMapping(value = "showFxx")
	public String showFxx(@RequestParam("pager.offset") String offset, HttpServletRequest req, Model model) {
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		List<MessageSxxVo> msgList = messageManager.getFxx(req);
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("msgList", msgList);
		model.addAttribute("msgNum", msgList.size());
		return "/message/messagePage_fxx";
	}

	// 收信箱批量删除信息
	@RequestMapping(value = "delCheck")
	public String delCheck(@RequestParam("msgIds") String msgIds, HttpServletRequest req, Model model) {
		messageManager.delCheck(msgIds);
		List<MessageSxxVo> msgList = messageManager.getSxx(req);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("msgList", msgList);
		return "/message/messagePage_sxx";
	}

	// 发信箱批量删除
	@RequestMapping(value = "delCheckFxx")
	public String delCheckFxx(@RequestParam("msgIds") String msgIds, HttpServletRequest req, Model model) {
		messageManager.delCheckFxx(msgIds);
		List<MessageSxxVo> msgList = messageManager.getFxx(req);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("msgList", msgList);
		return "/message/messagePage_fxx";
	}

	// 收信箱删除一条
	@RequestMapping(value = "delOne")
	public String delOne(@RequestParam("letterId") String letterId, HttpServletRequest req, Model model) {
		messageManager.delOne(letterId);
		List<MessageSxxVo> msgList = messageManager.getSxx(req);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("msgList", msgList);
		return "/message/messagePage_sxx";
	}

	// 发信箱删除一条
	@RequestMapping(value = "delOneFxx")
	public String delOneFxx(@RequestParam("letterId") String letterId, HttpServletRequest req, Model model) {
		messageManager.delOneFxx(letterId);
		List<MessageSxxVo> msgList = messageManager.getFxx(req);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("msgList", msgList);
		return "/message/messagePage_fxx";
	}

	// 信息回复
	@RequestMapping(value = "sendMsg")
	public String sendMsg(@RequestParam("msg") String msg, @RequestParam("senderId") String senderId, @RequestParam("receiverId") String receiverId, HttpServletRequest req, Model model) {
		String content = "";
		try {
			content = URLDecoder.decode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		messageManager.sendMsg(senderId, receiverId, content);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		List<MessageSxxVo> msgList = messageManager.getSxx(req);
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("msgList", msgList);
		return "/message/messagePage_sxx";
	}

	// 系统消息多选删除
	@RequestMapping(value = "sysDelCheck")
	public String sysDelCheck(@RequestParam("msgIds") String msgIds, HttpServletRequest req, Model model) {
		messageManager.sysDelCheck(msgIds);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		List<MessageSysMsgVo> sysMsgList = messageManager.systemMessageInfo(returnVo.getSysList());
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("sysMsgList", sysMsgList);
		return "/message/messagePage_xtxx";
	}

	// 系统消息删除一条
	@RequestMapping(value = "sysDelOne")
	public String sysDelOne(@RequestParam("msgId") String msgId, HttpServletRequest req, Model model) {
		messageManager.sysDelOne(msgId);
		ReturnMessageInfoVO returnVo = messageManager.msgCount(req);
		List<MessageSysMsgVo> sysMsgList = messageManager.systemMessageInfo(returnVo.getSysList());
		model.addAttribute("returnVo", returnVo);
		model.addAttribute("sysMsgList", sysMsgList);
		return "/message/messagePage_xtxx";
	}

}
