package com.zendaimoney.online.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.entity.site.NoticeAdmin;
import com.zendaimoney.online.admin.service.NoticeService;
import com.zendaimoney.online.admin.vo.AjaxResult;

@Controller
@RequestMapping("/admin/site")
public class SiteController {
	@Autowired
	private NoticeService noticeService;
	
	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}
	
	@RequestMapping(value = { "noticeJsp" })
	public String noticeJsp(Integer type,Model model) {
		model.addAttribute("type", type);
		return "admin/site/notice";
	}
	
	@RequestMapping("noticePage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<NoticeAdmin> noticePage(Integer type,Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<NoticeAdmin>(noticeService.findNoticePage(type,new PageRequest(page - 1, rows, new Sort(Direction.DESC,"creDate"))));
	}
	
	@RequestMapping("createNotice")
	@ResponseBody
	public AjaxResult createStaff(NoticeAdmin noticeAdmin) {
		noticeService.createNotice(noticeAdmin);
		return new AjaxResult();
	}
	
	@RequestMapping("removeNotices")
	@ResponseBody
	public AjaxResult removeNotices(Long[] noticeIds){
		noticeService.removeNotices(noticeIds);
		return new AjaxResult();
	}
	
	@RequestMapping("commendNotices")
	@ResponseBody
	public AjaxResult commendNotices(Long[] noticeIds){
		noticeService.commendNotices(noticeIds);
		return new AjaxResult();
	}
	
	@RequestMapping("unCommendNotices")
	@ResponseBody
	public AjaxResult unCommendNotices(Long[] noticeIds){
		noticeService.unCommendNotices(noticeIds);
		return new AjaxResult();
	}
	
	
	@RequestMapping("updateNotice")
	@ResponseBody
	public AjaxResult updateStaff(NoticeAdmin noticeAdmin) {
		noticeService.updateNotice(noticeAdmin);
		return new AjaxResult();
	}
}
