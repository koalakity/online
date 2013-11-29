package com.zendaimoney.online.admin.web;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.account.AccountMemoNoteAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUserInfoJobAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUserInfoPersonAdmin;
import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.service.AccountUsersService;
import com.zendaimoney.online.admin.service.AuditUserService;
import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.admin.service.UserInfoService;
import com.zendaimoney.online.admin.service.UserRegisterService;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.FundDetailVOAdmin;
import com.zendaimoney.online.admin.vo.MemoNoteList;
import com.zendaimoney.online.admin.vo.RepayLoanDetailLate;
import com.zendaimoney.online.admin.vo.UserListForm;
import com.zendaimoney.online.admin.web.view.AudtiUserViewExcel;
import com.zendaimoney.online.admin.web.view.UserViewExcel;
import com.zendaimoney.online.common.FileUploadUtil;
import com.zendaimoney.online.entity.common.Area;
import com.zendaimoney.online.service.borrowing.InfoApproveManager;
import com.zendaimoney.online.service.newPay.FundManagerNew;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private AccountUsersService accountUsersService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private AuditUserService auditUserService;

	@Autowired
	private InfoApproveManager infoApproveManager;

	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private ChannelInfoService channelInfoService;

	@Autowired
	private FundManagerNew fundManagerNew;

	@RequestMapping("userPageJsp")
	public String userPageJsp(HttpServletRequest request) {
		setChannelInfoToRequest(request);
		return "admin/user/userPage";
	}

	@RequestMapping("enteredAuditUserPageJsp")
	public String enteredAuditUserPageJsp() {
		return "admin/user/enteredAuditUserPage";
	}

	/**
	 * 设置渠道信息到请求对象 2013-1-5 上午10:27:58 by HuYaHui
	 * 
	 * @param request
	 */
	private void setChannelInfoToRequest(HttpServletRequest request) {
		// 所有一级渠道信息
		List<ChannelInfoVO> channelInfoParList = channelInfoService.findAllParentInfo();
		logger.info("查询一级渠道信息集合大小：" + (channelInfoParList != null ? channelInfoParList.size() : 0));
		// 设置到request返回对象
		request.setAttribute("channelInfoParList", channelInfoParList);

		// 查询一个一级渠道对应的二级集合
		if (channelInfoParList != null && channelInfoParList.size() > 0) {
			Long parentId = channelInfoParList.get(0).getId();
			logger.info("查询一级渠道ID：" + parentId);

			List<ChannelInfoVO> childChannelList = channelInfoService.findByParentIdOrderByCreateTimeDesc(parentId);
			logger.info("查询一级渠道对应的二级渠道集合大小为：" + (childChannelList != null ? childChannelList.size() : 0));
			request.setAttribute("childChannelList", childChannelList);
		}
	}

	/**
	 * @author Ray
	 * @date 2012-10-18 下午2:59:55
	 * @param userId
	 * @param model
	 * @return description:新增页面用户信息详细 由于userID的唯一性，只取第一个对象
	 */
	@RequestMapping("userInfoPersonPageJsp")
	public String userInfoPersonPageJsp(BigDecimal userId, Model model) {
		List<AccountUsersAdmin> userBasicList = accountUsersService.findByUserId(userId);
		AccountUsersAdmin accountUsersAdmin = null;
		if (userBasicList.size() > 0) {
			accountUsersAdmin = userBasicList.get(0);
			String code = "";
			if (accountUsersAdmin.getChannelInfo() != null) {
				code = accountUsersAdmin.getChannelInfo().getId().toString();
			} else {
				code = "2";
				model.addAttribute("channelInfoParentId", "1");
				model.addAttribute("childChannelId", code);
			}
			// 所有一级渠道信息
			List<ChannelInfoVO> channelInfoParList = channelInfoService.findAllParentInfo();
			logger.info("查询一级渠道信息集合大小：" + (channelInfoParList != null ? channelInfoParList.size() : 0));

			// 设置到request返回对象
			model.addAttribute("channelInfoParList", channelInfoParList);

			// 2013-1-5增加渠道来源

			// 根据二级ID，获取对应的二级集合和对应一级信息
			List<ChannelInfoVO> childList = channelInfoService.findChildListById(Long.valueOf(code));
			model.addAttribute("childChannelList", childList);

			Long parentId = 0l;
			// 获取选中的二级渠道对应一级渠道的ID
			for (ChannelInfoVO c_vo : childList) {
				if (code.trim().equals(c_vo.getId() + "")) {
					parentId = c_vo.getParentId();
				}
			}
			model.addAttribute("channelInfoParentId", parentId);
			model.addAttribute("childChannelId", code);
		}

		List shengList = accountUsersService.getAreaList("1");// 省级列表(含直辖市)
		List shiList = null;// 市级列表 (含直辖市下设区)
		List jobShiList = null;// 市级列表 (含直辖市下设区)
		if (accountUsersAdmin.getUserInfoJob() != null) {
			if (accountUsersAdmin.getUserInfoJob().getJobProvince() != BigDecimal.valueOf(0) && accountUsersAdmin.getUserInfoJob().getJobProvince() != null) {
				jobShiList = queryArea(String.valueOf(accountUsersAdmin.getUserInfoJob().getJobProvince()));
			} else {
				accountUsersAdmin.getUserInfoJob().setJobProvince(BigDecimal.valueOf(2));
				jobShiList = accountUsersService.getAreaList("2");
			}
		} else {
			AccountUserInfoJobAdmin userInfoJob = new AccountUserInfoJobAdmin();
			userInfoJob.setUserId(accountUsersAdmin.getUserId());
			userInfoJob.setJobProvince(BigDecimal.valueOf(2));
			accountUsersAdmin.setUserInfoJob(userInfoJob);
		}

		if (accountUsersAdmin.getUserInfoPerson() != null) {
			if (accountUsersAdmin.getUserInfoPerson().getHometownArea() != BigDecimal.valueOf(0) && accountUsersAdmin.getUserInfoPerson().getHometownArea() != null) {
				shiList = accountUsersService.getAreaList(String.valueOf(accountUsersAdmin.getUserInfoPerson().getHometownArea()));
			} else {
				accountUsersAdmin.getUserInfoPerson().setHometownArea(BigDecimal.valueOf(2));
				jobShiList = accountUsersService.getAreaList("2");
			}
		} else {
			AccountUserInfoPersonAdmin userInfoPerson = new AccountUserInfoPersonAdmin();
			userInfoPerson.setHometownArea(BigDecimal.valueOf(2));
			accountUsersAdmin.setUserInfoPerson(userInfoPerson);
		}

		model.addAttribute("userbasic", accountUsersAdmin);
		model.addAttribute("shengList", shengList);
		model.addAttribute("shiList", shiList);
		model.addAttribute("jobShiList", jobShiList);

		return "admin/user/userInfoPersonPage";
	}

	/**
	 * 调用上面的方法 userInfoPersonPageJsp 查询会员个人信息并返回只读页面中去 2012-11-5 下午1:56:47 by zy
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("userInfoPersonPageRdJsp")
	public String userInfoPersonPageRdJsp(BigDecimal userId, Model model) {
		userInfoPersonPageJsp(userId, model);
		return "admin/user/rd/userInfoPersonPageRd";
	}

	/**
	 * 查询借款信息备注
	 * 
	 * @param loanInfoListForm
	 * @return
	 */
	@RequestMapping("seachMemoNote")
	@ResponseBody
	public List<AccountMemoNoteAdmin> seachMemoNote(BigDecimal userId) {
		return accountUsersService.findMemoNoteByUserId(userId);

	}

	/**
	 * 保存用户备注信息
	 * 
	 * @param memoNoteListForm
	 */
	@RequestMapping("saveMemoNote")
	@ResponseBody
	public AjaxResult saveMemoNote(AccountMemoNoteAdmin memoNote) {
		accountUsersService.saveMemoNote(memoNote);
		return new AjaxResult();
	}

	/**
	 * @author Ray
	 * @date 2012-10-22 下午3:17:19
	 * @param code
	 * @return description:查询省市
	 */
	@ResponseBody
	@RequestMapping(value = "queryArea")
	public List<Area> queryArea(@ModelAttribute("code") String code) {
		return (List<Area>) accountUsersService.getAreaList(code);
	}

	@RequestMapping("userPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin> userPage(UserListForm userListForm, Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin>(accountUsersService.findAccountUsersPage(userListForm, new PageRequest(page - 1, rows, new Sort("userId"))));
	}

	@RequestMapping("recycleUserPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin> recycleUserPage(UserListForm userListForm, Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin>(accountUsersService.findRecycleUserPage(userListForm, new PageRequest(page - 1, rows, new Sort("userId"))));
	}

	@RequestMapping("reportUsers")
	@ResponseBody
	public AjaxResult reportUsers(Long[] userIds) {
		accountUsersService.reportUsers(userIds);
		return new AjaxResult();
	}

	@RequestMapping("removeUsers")
	@ResponseBody
	public AjaxResult removeUsers(Long[] userIds) {
		return accountUsersService.removeUsers(userIds);
	}

	@RequestMapping("removeUsersForever")
	@ResponseBody
	public AjaxResult removeUsersForever(Long[] userIds) {
		return new AjaxResult();
	}

	@RequestMapping("recoverUsers")
	@ResponseBody
	public AjaxResult recoverUsers(Long[] userIds) {
		accountUsersService.recoverUsers(userIds);
		return new AjaxResult();
	}

	@RequestMapping("lockUsers")
	@ResponseBody
	public AjaxResult lockUsers(Long[] userIds) {
		accountUsersService.lockUsers(userIds);
		return new AjaxResult();
	}

	@RequestMapping("unLockUsers")
	@ResponseBody
	public AjaxResult unLockUsers(Long[] userIds) {
		accountUsersService.unLockUsers(userIds);
		return new AjaxResult();
	}

	@RequestMapping("userInfo")
	@ResponseBody
	public FundDetailVOAdmin userInfo(Long userId, Model model) {
		FundDetailVOAdmin vo = new FundDetailVOAdmin();
		vo = userInfoService.userInfo(new BigDecimal(userId));
		model.addAttribute("vo1", vo);
		return vo;
	}

	@RequestMapping("prepareAuditing")
	public String prepareAuditing(Long id) {
		return "admin/user/auditing";

	}

	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}

	@RequestMapping("exportUsers")
	public ModelAndView exportUsers(UserListForm userListForm, HttpServletRequest request, HttpServletResponse response) {
		Page<AccountUsersAdmin> all = accountUsersService.findAccountUsersPage(userListForm, new PageRequest(0, Integer.MAX_VALUE, new Sort("userId")));
		Map<String, List<AccountUsersAdmin>> model = new HashMap<String, List<AccountUsersAdmin>>();
		model.put("userList", all.getContent());
		return new ModelAndView(new UserViewExcel(), model);
	}

	@RequestMapping("recycleExportUsers")
	public ModelAndView recycleExportUsers(UserListForm userListForm, HttpServletRequest request, HttpServletResponse response) {
		Page<AccountUsersAdmin> all = accountUsersService.findRecycleUserPage(userListForm, new PageRequest(0, Integer.MAX_VALUE, new Sort("userId")));
		Map<String, List<AccountUsersAdmin>> model = new HashMap<String, List<AccountUsersAdmin>>();
		model.put("userList", all.getContent());
		return new ModelAndView(new UserViewExcel(), model);
	}

	@RequestMapping("exportAuditUsers")
	public ModelAndView exportAuditUsers(UserListForm userListForm, HttpServletRequest request, HttpServletResponse response) {
		Page<AccountUsersAdmin> all = auditUserService.findAuditUsersPage(userListForm, new PageRequest(0, Integer.MAX_VALUE, new Sort("userId")));
		Map<String, List<AccountUsersAdmin>> model = new HashMap<String, List<AccountUsersAdmin>>();
		model.put("userList", all.getContent());
		return new ModelAndView(new AudtiUserViewExcel(), model);
	}

	@RequestMapping("allExportAuditUsers")
	public ModelAndView allExportAuditUsers(UserListForm userListForm, HttpServletRequest request, HttpServletResponse response) {
		Page<AccountUsersAdmin> all = auditUserService.findAllAuditUsersPage(userListForm, new PageRequest(0, Integer.MAX_VALUE, new Sort("userId")));
		Map<String, List<AccountUsersAdmin>> model = new HashMap<String, List<AccountUsersAdmin>>();
		model.put("userList", all.getContent());
		return new ModelAndView(new AudtiUserViewExcel(), model);
	}

	@RequestMapping("userInfoMemoRecd")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<MemoNoteList> userInfoMemoRead(Integer page, Integer rows, Long userId) {
		return new com.zendaimoney.online.admin.vo.Page<MemoNoteList>(userInfoService.findUserInfoMemoPag(new PageRequest(page - 1, rows, new Sort(Direction.DESC, "operateTime")), new BigDecimal(userId)));
	}

	@RequestMapping("userInfoHisLate")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<RepayLoanDetailLate> userInfoHisLate(Integer page, Integer rows, Long userId) {
		return new com.zendaimoney.online.admin.vo.Page<RepayLoanDetailLate>(userInfoService.findLoanHisLate(new PageRequest(page - 1, rows), new BigDecimal(userId)));
	}

	@RequestMapping("exportExcelFundFlowTbl")
	public void exportExcelFundFlowTbl(HttpServletRequest request,HttpServletResponse response) {
		try {
			String typestr = request.getParameter("type");
			int type = 0;
			if (typestr != null && !typestr.equals("")) {
				type = Integer.valueOf(typestr);
			}
			String date_start = request.getParameter("start");
			String date_end = request.getParameter("end");
			if(date_start==null || date_start.equals("") || date_start.trim().equals("undefined")){
				date_start=null;
			}
			if(date_end==null || date_end.equals("") || date_end.trim().equals("undefined")){
				date_end=null;
			}
			Long userId=0l;
			String userIdStr=request.getParameter("userId");
			if(userIdStr!=null && !userIdStr.equals("")){
				userId=Long.valueOf(userIdStr);
			}
			
			userInfoService.exportExcelFundFlowTbl(request, response, userId, type, date_start, date_end);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@RequestMapping("userInfoFundFlow")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<HashMap> userInfoFundFlow(HttpServletRequest req, Integer page, Integer rows, Long userId) {
		String typestr = req.getParameter("type");
		int type = 0;
		if (typestr != null && !typestr.equals("")) {
			type = Integer.valueOf(typestr);
		}
		String date_start = req.getParameter("start");
		String date_end = req.getParameter("end");
		if(date_start==null || date_start.equals("") || date_start.trim().equals("undefined")){
			date_start=null;
		}
		if(date_end==null || date_end.equals("") || date_end.trim().equals("undefined")){
			date_end=null;
		}
		
		com.zendaimoney.online.admin.vo.Page<HashMap> pageMap = new com.zendaimoney.online.admin.vo.Page<HashMap>(
				userInfoService.findFundFlow(new PageRequest(page, rows), userId, type, date_start, date_end));
		return pageMap;
	}


	@RequestMapping("enterAuditPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin> enterAuditPage(UserListForm userListForm, Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin>(auditUserService.findAuditUsersPage(userListForm, new PageRequest(page - 1, rows, new Sort("userId"))));
	}

	@RequestMapping("enterAllAuditPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin> enterAllAuditPage(UserListForm userListForm, Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<AccountUsersAdmin>(auditUserService.findAllAuditUsersPage(userListForm, new PageRequest(page - 1, rows, new Sort("userId"))));
	}

	@RequestMapping("userInfoJsp")
	public String userInfoJsp(Long userId, Model model) {
		FundDetailVOAdmin vo = new FundDetailVOAdmin();
		vo = userInfoService.userInfo(new BigDecimal(userId));
		model.addAttribute("vo1", vo);
		return "admin/user/userInfoSearch";
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 上午10:03:30
	 * @param baseVO
	 * @param model
	 * @param req
	 * @return description:保存后台个人信息数据
	 */
	@RequestMapping("savePersonalBase")
	@ResponseBody
	public String savePersonalBase(AccountUsersAdmin user, HttpServletRequest req) {
		// 2013-1-5增加渠道来源
		String channelInfo_ID = req.getParameter("channelInfo_ID");
		if (channelInfo_ID != null && !channelInfo_ID.equals("")) {
			ChannelInfoVO channelInfo = new ChannelInfoVO();
			channelInfo = channelInfoService.findById(Long.valueOf(channelInfo_ID));
			user.setChannelInfo(channelInfo);
		}
		AccountUserInfoPersonAdmin person = accountUsersService.savePersonalBase(user);
		if (null != person && !("".equals(person))) {
			return "success";
		} else {

			return "error";
		}
	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:32:53
	 * @param user
	 * @param req
	 * @return description:
	 */
	@RequestMapping("savePersonalFixed")
	@ResponseBody
	public String savePersonalFixed(AccountUsersAdmin user, HttpServletRequest req) {
		accountUsersService.savePersonalFixed(user);
		return null;

	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:32:53
	 * @param user
	 * @param req
	 * @return description:
	 */
	@RequestMapping("savePersonalEdu")
	@ResponseBody
	public String savePersonalEdu(AccountUsersAdmin user, HttpServletRequest req) {
		accountUsersService.savePersonalEdu(user);
		return null;

	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:32:53
	 * @param user
	 * @param req
	 * @return description:保存用户固定资产
	 */
	@RequestMapping("savePersonalFinance")
	@ResponseBody
	public String savePersonalFinance(AccountUsersAdmin user, HttpServletRequest req) {
		accountUsersService.savePersonalFinance(user);
		return null;

	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:32:53
	 * @param user
	 * @param req
	 * @return description:保存用户工作信息
	 */
	@RequestMapping("savePersonalJob")
	@ResponseBody
	public String savePersonalJob(AccountUsersAdmin user, HttpServletRequest req) {
		accountUsersService.savePersonalJob(user);
		return null;

	}

	/**
	 * @author Ray
	 * @date 2012-10-23 下午4:32:53
	 * @param user
	 * @param req
	 * @return description:保存私营业主系信息
	 */
	@RequestMapping("savePrivatePropritor")
	@ResponseBody
	public String savePrivatePropritor(AccountUsersAdmin user, HttpServletRequest req) {
		accountUsersService.savePersonalPrivate(user);
		return null;

	}

	/**
	 * @author Ray
	 * @date 2012-11-8 上午10:14:45
	 * @param user
	 * @param req
	 * @return description:
	 * @throws Exception
	 */
	@RequestMapping(value = "updataHeadPhoto")
	public String updataHeadPhoto(BigDecimal userId, String email, HttpServletRequest request) throws Exception {
		String flag = "";
		try {
		// 上传文件
		List<FileItem> items = FileUploadUtil.uploadFile(request, FileUploadUtil.FILEMAXSIZ_1_5M.longValue());
		// 验证文件未空的情况
		flag = FileUploadUtil.checkFileSize(items);
		if (flag != null && !flag.equals("")) {
			flag = accountUsersService.updataHeadPhoto(items, userId);
				return "redirect:/admin/user/showheadImg?userId=" + userId + "&flag=" + flag;
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "redirect:/admin/user/showheadImg?userId=" + userId + "&flag=" + flag;
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

	/**
	 * @author Ray
	 * @date 2012-10-25 下午4:50:51
	 * @param user
	 * @param req
	 * @return description:根据用户真实姓名、身份证号码获取比对
	 */
	@RequestMapping("getID5")
	@ResponseBody
	public AjaxResult getID5(AccountUsersAdmin user, HttpServletRequest req) {
		return accountUsersService.getID5(user);
	}

	@RequestMapping("exportExcelOldFundFlow")
	public void exportExcelOldFundFlow(HttpServletRequest request,HttpServletResponse response) {
		try {
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			if(start==null || start.equals("") || start.trim().equals("undefined")){
				start=null;
			}
			if(end==null || end.equals("") || end.trim().equals("undefined")){
				end=null;
			}
			BigDecimal userId=new BigDecimal("0");
			String userIdStr=request.getParameter("userId");
			if(userIdStr!=null && !userIdStr.equals("")){
				userId=new BigDecimal(userIdStr);
			}
			userInfoService.exportExcelFindOldFundFlow(request, response, userId, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/** 
	 * @author 王腾飞
	 * @date 2013-3-27 下午2:07:24
	 * @param req
	 * @param page
	 * @param rows
	 * @param userId
	 * @return
	 * description:旧版流水展示
	*/
	@RequestMapping("oldFundFlow")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<HashMap> oldFundFlow(HttpServletRequest req, Integer page, Integer rows, Long userId,String start,String end) {
		return new com.zendaimoney.online.admin.vo.Page<HashMap>(userInfoService.findOldFundFlow(new PageRequest(page - 1, rows), new BigDecimal(userId), start, end));

	}
	/**
	 * @author Ray
	 * @date 2012-11-22 下午3:32:38
	 * @param loginNameHid
	 * @param emailHid
	 * @param request
	 * @return
	 * @throws Exception
	 *             description:重新发送邮箱验证邮件
	 */
	@RequestMapping(value = "againSendEmail")
	@ResponseBody
	public String againSendEmail(String loginNameHid, String emailHid, HttpServletRequest request) throws Exception {
		String path = request.getContextPath();
		String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		userRegisterService.againSendEmail(loginNameHid, serverPath, emailHid);
		return "againSendSuccess";
	}

}
