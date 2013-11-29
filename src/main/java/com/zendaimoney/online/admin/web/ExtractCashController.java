/**
 * 
 */
package com.zendaimoney.online.admin.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.entity.extract.ExtractNoteAdmin;
import com.zendaimoney.online.admin.service.ExtractNoteService;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.ExtractNoteForm;

/**
 * @author 王腾飞
 * 
 */

@Controller
@RequestMapping("/admin/extract")
public class ExtractCashController {
	@Autowired
	private ExtractNoteService extractNoteService;
	
	@RequestMapping("extractCashRecordPageJsp")
	public String extractCashRecordPageJsp(BigDecimal verifyStatus,Model model) {
		model.addAttribute("status", verifyStatus);
		return "admin/extract/extractCashRecordPage";
	}
	/**
	 * 提现管理通用 查询 根据状态返回 不同的 提现信息
	 * @param extractNoteForm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("extractCashRecordPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<ExtractNoteAdmin> extractCashRecordPage(ExtractNoteForm extractNoteForm,Integer page,Integer rows){
		
		if(page == null){
			page = 1;
			}
		if(rows == null){
			rows = 10;
			}
		com.zendaimoney.online.admin.vo.Page<ExtractNoteAdmin> extractNotes = new com.zendaimoney.online.admin.vo.Page<ExtractNoteAdmin>(extractNoteService.findExtractPage(extractNoteForm,new PageRequest(page-1, rows,new Sort("extractId"))));
		return extractNotes;
	}
	
	/**
	 * 提现详细信息查看
	 * @return
	 */
	@RequestMapping("extractCashRecordDetailPage")
	public String extractCashRecordDetailPage(ExtractNoteForm extractNoteForm,Model model) {
		ExtractNoteAdmin extractNote = extractNoteService.findOne(extractNoteForm.getExtractId());
		model.addAttribute("extractNote", extractNote);

		if("0".equals(extractNoteForm.getVerifyStatus().toString())){
			return "admin/extract/newExtractApplyDetailPage";
		}
		else if("1".equals(extractNoteForm.getVerifyStatus().toString())){
			return "admin/extract/handlingExtractDetailPage";
		}
		else if("2".equals(extractNoteForm.getVerifyStatus().toString())){
			return "admin/extract/successExtractApplyDetailPage";
		}
		else if("3".equals(extractNoteForm.getVerifyStatus().toString())){
			return "admin/extract/failureExtractApplyDetailPage";
		}else{
		return null;}
		
	}
	@RequestMapping("saveExtractNote")
	@ResponseBody
	public AjaxResult saveExtractNote(ExtractNoteForm extractNoteForm ,Model model){
		AjaxResult ajaxMessage = new AjaxResult();
		String operateResult = extractNoteService.saveExtractNote(extractNoteForm);
		if(operateResult!=null){
			ajaxMessage.setMsg(operateResult);
			ajaxMessage.setSuccess(false);
		}
		return ajaxMessage;
	}
	
	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(RuntimeException e){
		e.printStackTrace();
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}
}
