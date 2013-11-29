package com.zendaimoney.online.admin.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zendaimoney.online.admin.dao.account.AccountUsersDao;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.audit.MaterialReviewStatusChangeAdmin;
import com.zendaimoney.online.admin.entity.audit.ReviewMemoNoteAdmin;
import com.zendaimoney.online.admin.service.AuditUserService;
import com.zendaimoney.online.admin.service.BusinessException;
import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.admin.util.AuditUserTools;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.AuditNoteListForm;
import com.zendaimoney.online.admin.vo.AuidtListForm;
import com.zendaimoney.online.admin.vo.FileVo;
import com.zendaimoney.online.admin.vo.MaterialReviewStatusChangeForm;
import com.zendaimoney.online.admin.vo.UserListForm;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.entity.upload.FileUploadVO;
import com.zendaimoney.online.service.upload.FileUploadManager;

/**
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author
 * @date: 2012-12-19 上午9:41:08 operation by: description: 后台客户审核控制
 */
@Controller
@RequestMapping("/admin/audit")
public class AuditController {
	private static Logger logger = LoggerFactory.getLogger(AuditController.class);
	@Autowired
	private AuditUserService auditUserService;

	@Autowired
	private AccountUsersDao accountUsersDao;

	@Autowired
	private FileUploadManager fileUploadManager;
	
	@Autowired
	private ChannelInfoService channelInfoService;

	@RequestMapping("waitForAuditingUserPageJsp")
	public String waitForAuditingUserPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/audit/waitForAuditingUserPage";
	}

	public static Map<String, FileVo> cache_file_map = new LinkedHashMap<String, FileVo>();

	/**
	 * 设置渠道信息到请求对象 2013-1-5 上午10:27:58 
	 * 
	 * @param request
	 */
	private void setChannelInfoToRequest(HttpServletRequest request) {
		// 所有一级渠道信息
		List<ChannelInfoVO> channelInfoParList = channelInfoService.findAllParentInfo();
		logger.info("查询一级渠道信息集合大小：" + channelInfoParList);
		// 设置到request返回对象
		request.setAttribute("channelInfoParList", channelInfoParList);

		// 查询一个一级渠道对应的二级集合
		if (channelInfoParList != null && channelInfoParList.size() > 0) {
			Long parentId = channelInfoParList.get(0).getId();
			logger.info("查询一级渠道ID：" + parentId);

			List<ChannelInfoVO> childChannelList = channelInfoService.findByParentIdOrderByCreateTimeDesc(parentId);
			logger.info("查询一级渠道对应的二级渠道集合大小为：" + childChannelList );
			request.setAttribute("childChannelList", childChannelList);
		}
	}
	
	@RequestMapping("waitAuditUserPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin> waitAuditUserPage(UserListForm userListForm, Integer page, Integer rows, Long auditId) {
		return new com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin>(auditUserService.findWaitAuditUsersPage(userListForm, new PageRequest(page - 1, rows, new Sort(Direction.DESC, "inclosureSubmitTime")), new BigDecimal(auditId)));
	}

	@RequestMapping("waitForAuditingDetailPageJsp")
	public String waitForAudtingDetailPageJsp(Long userId, Model model) {
		fileMap(userId);
		AccountUsersAdmin userAuditInfo = auditUserService.findByUserId(new BigDecimal(userId));
		model.addAttribute("userAuditInfo", userAuditInfo);
		String codeName = "";
		if (userAuditInfo.getUserInfoPerson() != null) {
			if (userAuditInfo.getUserInfoPerson().getMaxDegree() != null) {
				codeName = auditUserService.getSysCodeName(userAuditInfo.getUserInfoPerson().getMaxDegree());
			}
		}
		model.addAttribute("codeName", codeName);
		model.addAttribute("codeName", codeName);
		return "admin/audit/waitForAuditingDetailPage";
	}

	@RequestMapping("firstForAuditingDetailPageJsp")
	public String firstForAuditingDetailPageJsp(Long userId, Model model) {
		fileMap(userId);
		AccountUsersAdmin userAuditInfo = auditUserService.findByUserId(new BigDecimal(userId));
		model.addAttribute("userAuditInfo", userAuditInfo);
		String codeName = "";
		if (userAuditInfo.getUserInfoPerson() != null) {
			if (userAuditInfo.getUserInfoPerson().getMaxDegree() != null) {
				codeName = auditUserService.getSysCodeName(userAuditInfo.getUserInfoPerson().getMaxDegree());
			}
		}

		model.addAttribute("codeName", codeName);
		return "admin/audit/firstForAuditingDetailPage";
	}

	@RequestMapping("finalForAuditingDetailPageJsp")
	public String finalForAuditingDetailPageJsp(Long userId, Model model) {
		fileMap(userId);
		AccountUsersAdmin userAuditInfo = auditUserService.findByUserId(new BigDecimal(userId));
		model.addAttribute("userAuditInfo", userAuditInfo);
		String codeName = "";
		if (userAuditInfo.getUserInfoPerson() != null) {
			if (userAuditInfo.getUserInfoPerson().getMaxDegree() != null) {
				codeName = auditUserService.getSysCodeName(userAuditInfo.getUserInfoPerson().getMaxDegree());
			}
		}
		model.addAttribute("codeName", codeName);
		model.addAttribute("codeName", codeName);
		return "admin/audit/finalForAuditingDetailPage";
	}

	@RequestMapping("refuseForAuditingDetailPageJsp")
	public String refuseForAuditingDetailPageJsp(Long userId, Model model) {
		fileMap(userId);
		AccountUsersAdmin userAuditInfo = auditUserService.findByUserId(new BigDecimal(userId));
		model.addAttribute("userAuditInfo", userAuditInfo);
		String codeName = "";
		if (userAuditInfo.getUserInfoPerson() != null) {
			if (userAuditInfo.getUserInfoPerson().getMaxDegree() != null) {
				codeName = auditUserService.getSysCodeName(userAuditInfo.getUserInfoPerson().getMaxDegree());
			}
		}
		model.addAttribute("codeName", codeName);
		model.addAttribute("codeName", codeName);
		return "admin/audit/refuseForAuditingDetailPage";
	}

	@RequestMapping("supplyDataDetailPageJsp")
	public String supplyDataDetailPageJsp(Long userId, Model model) {
		fileMap(userId);
		AccountUsersAdmin userAuditInfo = auditUserService.findByUserId(new BigDecimal(userId));
		model.addAttribute("userAuditInfo", userAuditInfo);
		String codeName = "";
		if (userAuditInfo.getUserInfoPerson() != null) {
			if (userAuditInfo.getUserInfoPerson().getMaxDegree() != null) {
				codeName = auditUserService.getSysCodeName(userAuditInfo.getUserInfoPerson().getMaxDegree());
			}
		}
		model.addAttribute("codeName", codeName);
		model.addAttribute("codeName", codeName);
		return "admin/audit/supplyDataDetailPage";
	}

	@RequestMapping("firstForAuditingUserPageJsp")
	public String firstForAuditingUserPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/audit/firstForAuditingUserPage";
	}

	@RequestMapping("finalForAuditingUserPageJsp")
	public String finalForAuditingUserPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/audit/finalForAuditingUserPage";
	}

	@RequestMapping("refuseForAuditingUserPageJsp")
	public String refuseForAuditingUserPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/audit/refuseForAuditingUserPage";
	}

	@RequestMapping("supplyDataPageJsp")
	public String supplyDataPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/audit/supplyDataPage";
	}

	@RequestMapping("addAuditMemoNote")
	@ResponseBody
	public void addAuditMemoNote(AuditNoteListForm auditNoteListForm) {
		auditUserService.addAuditMemoNote(auditNoteListForm);
	}

	@RequestMapping("searchAuditMemoNote")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<ReviewMemoNoteAdmin> searchAuditMemoNote(Integer page, Integer rows, Long userId) {
		return new com.zendaimoney.online.admin.vo.Page<ReviewMemoNoteAdmin>(auditUserService.searchAuditMemoNote(new PageRequest(page - 1, rows, new Sort(Direction.DESC, "operateTime")), new BigDecimal(userId)));
	}

	@RequestMapping("searchReturnMemoNote")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<MaterialReviewStatusChangeAdmin> searchReturnMemoNote(Integer page, Integer rows, Long userId) {
		return new com.zendaimoney.online.admin.vo.Page<MaterialReviewStatusChangeAdmin>(auditUserService.searchReturnMemoNote(new PageRequest(page - 1, rows, new Sort(Direction.DESC, "operateTime")), new BigDecimal(userId)));
	}

	@RequestMapping("searchRejectMemoNote")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<MaterialReviewStatusChangeAdmin> searchRejectMemoNote(Integer page, Integer rows, Long userId) {
		return new com.zendaimoney.online.admin.vo.Page<MaterialReviewStatusChangeAdmin>(auditUserService.searchRejectMemoNote(new PageRequest(page - 1, rows, new Sort(Direction.DESC, "operateTime")), new BigDecimal(userId)));
	}

	@RequestMapping("downloadFile")
	public void downloadFile(String downfilePath, String downfileName, Long userId, HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String(downfileName.getBytes(), "ISO-8859-1"));
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			List<AccountUsersAdmin> userList = accountUsersDao.findByUserId(new BigDecimal(userId));
			String userEmail = "";
			if (userList.size() > 0) {
				userEmail = userList.get(0).getEmail();
			}
			downfilePath = AuditUserTools.getFilePath() + userEmail + "/" + downfilePath + "/";
			File file = new File(downfilePath + downfileName);
			inputStream = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (Exception e) {
			if (inputStream != null) {
				inputStream.close();
			}
			if (os != null) {
				os.close();
			}
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-2-4 上午10:09:47
	 * @param openFilePath
	 * @param openFileName
	 * @param userId
	 * @return
	 * @throws Exception
	 *             description:图片在线打开
	 */
	@RequestMapping(value = "openFile")
	@ResponseBody
	public String openFile(String openFilePath, String openFileName, Long userId) throws Exception {
		try {
			List<AccountUsersAdmin> userList = accountUsersDao.findByUserId(new BigDecimal(userId));
			String userEmail = "";
			if (userList.size() > 0) {
				userEmail = userList.get(0).getEmail();
			}
			openFilePath = AuditUserTools.getFilePath() + userEmail + "/" + openFilePath + "/";
			File file = new File(openFilePath + openFileName);
			if (file.isFile() && file.exists()) {
				return "true";
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("auditPass")
	@ResponseBody
	public void auditPass(String dataAry, Long userId, Long auditId, String creditAmount) throws JsonParseException, JsonMappingException, IOException {
		List<AuidtListForm> auditList = new ArrayList<AuidtListForm>();
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		List<Map> list = mapper.readValue(dataAry, List.class);
		for (Map map : list) {
			AuidtListForm aform = new AuidtListForm();
			aform.setProId(new BigDecimal(NumberUtils.toLong(map.get("proId") + "")));
			aform.setCreditScore(new BigDecimal(NumberUtils.toLong(map.get("creditScore") + "")));
			aform.setReviewStatus(new BigDecimal(NumberUtils.toLong(map.get("reviewStatus") + "")));
			auditList.add(aform);
		}
		auditUserService.auditPass(auditList, new BigDecimal(userId), new BigDecimal(auditId), new BigDecimal(creditAmount));
	}

	@RequestMapping("auditReject")
	@ResponseBody
	public AjaxResult auditReject(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		return auditUserService.auditReject(materialReviewStatusChangeForm);
	}

	@RequestMapping("auditReplenish")
	@ResponseBody
	public AjaxResult auditReplenish(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		return auditUserService.auditReplenish(materialReviewStatusChangeForm);
	}

	@RequestMapping("auditSave")
	@ResponseBody
	public void auditSave(String dataAry, Long userId) throws JsonParseException, JsonMappingException, IOException {
		List<AuidtListForm> auditList = new ArrayList<AuidtListForm>();
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		List<Map> list = mapper.readValue(dataAry, List.class);
		for (Map map : list) {
			AuidtListForm aform = new AuidtListForm();
			aform.setProId(new BigDecimal(NumberUtils.toLong(map.get("proId") + "")));
			aform.setCreditScore(new BigDecimal(NumberUtils.toLong(map.get("creditScore") + "")));
			aform.setReviewStatus(new BigDecimal(NumberUtils.toLong(map.get("reviewStatus") + "")));
			auditList.add(aform);
		}
		auditUserService.auditSave(auditList, new BigDecimal(userId));
	}

	@RequestMapping("auditReturnToWait")
	@ResponseBody
	public void auditReturnToWait(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		auditUserService.auditReturnToWait(materialReviewStatusChangeForm);
	}

	@RequestMapping("auditReturnToFirst")
	@ResponseBody
	public void auditReturnToFirst(MaterialReviewStatusChangeForm materialReviewStatusChangeForm) {
		auditUserService.auditReturnToFirst(materialReviewStatusChangeForm);
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-2-28 上午9:57:20
	 * @param userId
	 * @param imgPath
	 * @param proId
	 * @param email
	 * @param typeName
	 * @param model
	 * @return description:打开图片浏览窗口
	 */
	@RequestMapping("showImgJsp")
	public String showImgJsp(Long userId, String imgPath, Long proId, String email, String typeName, Model model) {
		model.addAttribute("userId", userId);
		model.addAttribute("imgPath", imgPath);
		model.addAttribute("proId", proId);
		model.addAttribute("email", email);
		model.addAttribute("typeName", typeName);
		if (imgPath == null) {
			model.addAttribute("flag", "false");
		} else {
			model.addAttribute("flag", "true");
		}
		return "admin/audit/showImg";
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-2-28 上午9:57:52
	 * @param userId
	 * @param imgPath
	 * @param proId
	 * @param email
	 * @param model
	 * @return description:返回图片路径组成的json数据
	 */
	@RequestMapping("showImgJson")
	@ResponseBody
	public String showImgJson(Long userId, String imgPath, Long proId, String email, Model model) {
		fileMap(userId);
		String jsonS = AuditUserTools.getF(proId.intValue(), email);
		return jsonS;
	}

	private Map<String, FileVo> fileMap(Long userId) {
		cache_file_map.clear();
		List<FileUploadVO> file_list = fileUploadManager.getAllByUser(userId.toString());
		for (FileUploadVO file : file_list) {
			FileVo fileVo = new FileVo();
			fileVo.setFileTime(DateUtil.getStrDate(file.getCreateTime()));
			if (file.getUserApproveId() == null || file.getUserApproveId().getUserApproveId() == null) {
				fileVo.setUserApproveId("empty");
			} else {
				fileVo.setUserApproveId(file.getUserApproveId().getUserApproveId().toString());
			}
			fileVo.setIsDel(file.getIsDel());
			fileVo.setStatus(file.getStatus());
			cache_file_map.put(file.getFilePath(), fileVo);
		}

		return cache_file_map;
	}

	@RequestMapping(value = "picShowUserJsp")
	public String picShowUserJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/audit/picShowUserPage";
	}

	@RequestMapping(value = "showUserPicList")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin> showUserPicList(UserListForm userListForm, Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin>(auditUserService.findPicUsersPage(userListForm, new PageRequest(page - 1, rows, new Sort("userId"))));
	}
}
