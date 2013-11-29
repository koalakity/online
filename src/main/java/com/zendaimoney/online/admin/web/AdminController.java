package com.zendaimoney.online.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.annotation.OperateKindEditor;
import com.zendaimoney.online.admin.entity.AdminLog;
import com.zendaimoney.online.admin.entity.Function;
import com.zendaimoney.online.admin.entity.Role;
import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.admin.service.AdminLogService;
import com.zendaimoney.online.admin.service.StaffService;
import com.zendaimoney.online.admin.vo.AdminLogForm;
import com.zendaimoney.online.admin.vo.AjaxResult;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(OperateKind.class, new OperateKindEditor(OperateKind.class));
	}

	@Autowired
	private StaffService staffService;

	@Autowired
	private AdminLogService adminLogService;

	@RequestMapping("login")
	public String login(String error, Model model) {
		model.addAttribute("error", error);
		return "admin/login";
	}

	@RequestMapping(value = { "index", "" })
	public String index() {
		return "admin/index";
	}

	@RequestMapping("welcome")
	public String welcome() {
		return "admin/welcome";
	}

	@RequestMapping("rolePageJsp")
	public String rolePageJsp() {
		return "admin/rolePage";
	}

	@RequestMapping("logPageJsp")
	public String logPageJsp() {
		return "admin/logPage";
	}

	@RequestMapping("profileJsp")
	public String profileJsp(Model model) {
		model.addAttribute("staff", staffService.getCurrentStaffFromDb());
		return "admin/profile";
	}

	@RequestMapping("staffPageJsp")
	public String staffPageJsp() {
		return "admin/staffPage";
	}

	@RequestMapping("rolePage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<Role> rolePage(Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<Role>(staffService.findRolePage(new PageRequest(page - 1, rows, new Sort("id"))));
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

	@RequestMapping("staffPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<Staff> staffPage(Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<Staff>(staffService.findStaffPage(new PageRequest(page - 1, rows, new Sort("id"))));
	}

	@RequestMapping("findFunctions")
	@ResponseBody
	public Iterable<Function> findFunctions() {
		return staffService.findAllFunctions();
	}

	@RequestMapping("findUsedFunctions")
	@ResponseBody
	public Iterable<Function> findUsedFunctions(Long roleId) {
		return staffService.findUsedFunctions(roleId);
	}

	@RequestMapping("findRoles")
	@ResponseBody
	public Iterable<Role> findRoles(Long staffId) {
		return staffService.findRoles(staffId);
	}

	@RequestMapping("createRole")
	@ResponseBody
	public AjaxResult createRole(Role role, Long[] selectedFunctions) {
		staffService.createRole(role, selectedFunctions);
		return new AjaxResult();
	}

	@RequestMapping("updateRole")
	@ResponseBody
	public AjaxResult updateRole(Role role, Long[] selectedFunctions) {
		staffService.updateRole(role, selectedFunctions);
		return new AjaxResult();
	}

	@RequestMapping("removeRole")
	@ResponseBody
	public AjaxResult removeRole(Long[] ids) {
		staffService.removeRole(ids);
		return new AjaxResult();
	}

	@RequestMapping("createStaff")
	@ResponseBody
	public AjaxResult createStaff(Staff staff) {
		staffService.createStaff(staff);
		return new AjaxResult();
	}

	@RequestMapping("removeStaff")
	@ResponseBody
	public AjaxResult removeStaff(Long[] staffIds) {
		staffService.removeStaff(staffIds);
		return new AjaxResult();
	}

	@RequestMapping("closeStaffs")
	@ResponseBody
	public AjaxResult closeStaffs(Long[] staffIds) {
		staffService.closeStaffs(staffIds);
		return new AjaxResult();
	}

	@RequestMapping("openStaffs")
	@ResponseBody
	public AjaxResult openStaffs(Long[] staffIds) {
		staffService.openStaffs(staffIds);
		return new AjaxResult();
	}

	@RequestMapping("updateStaff")
	@ResponseBody
	public AjaxResult updateStaff(Staff staff) {
		staffService.updateStaff(staff);
		return new AjaxResult();
	}

	@RequestMapping("logPage")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<AdminLog> logPage(AdminLogForm adminLogForm, Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<AdminLog>(adminLogService.findAmdinLogPage(adminLogForm, new PageRequest(page - 1, rows, new Sort(Direction.DESC,"operateTime"))));
	}
	
}
