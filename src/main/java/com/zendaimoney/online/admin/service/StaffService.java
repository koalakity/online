package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.FunctionDao;
import com.zendaimoney.online.admin.dao.RoleDao;
import com.zendaimoney.online.admin.dao.RoleFunctionDao;
import com.zendaimoney.online.admin.dao.StaffDao;
import com.zendaimoney.online.admin.entity.Function;
import com.zendaimoney.online.admin.entity.Role;
import com.zendaimoney.online.admin.entity.RoleFunction;
import com.zendaimoney.online.admin.entity.Staff;

@Service
@Transactional
public class StaffService {
	private static Logger logger = LoggerFactory.getLogger(StaffService.class);
	@Autowired
	private StaffDao staffDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private FunctionDao functionDao;

	@Autowired
	private RoleFunctionDao rolefunctionDao;

	public Page<Role> findRolePage(PageRequest pageRequest) {
		return roleDao.findAll(pageRequest);
	}

	public Staff getStaff(String loginName) {
		return staffDao.findByLoginName(loginName);
	}

	public Iterable<Function> findAllFunctions() {
		return functionDao.findByFunctionCodeLikeOrderByIdAsc("__000000");
	}

	@LogInfo(operateKind = OperateKind.新增, operateContent = "系统管理中新增角色成功")
	public void createRole(Role role, Long[] selectedFunctions) {
		role.setCreateTime(new Date());
		roleDao.save(role);
		for (Long functionId : selectedFunctions) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setRole(role);
			roleFunction.setFunction(functionDao.findOne(functionId));
			logger.info("SAVE: ROLE_FUNCTION || roleFunction="+roleFunction.getId()+" role="+role);
			rolefunctionDao.save(roleFunction);
			Function parent = roleFunction.getFunction().getParent();
			while (parent != null) {
				roleFunction = new RoleFunction();
				roleFunction.setRole(role);
				roleFunction.setFunction(parent);
				logger.info("SAVE: ROLE_FUNCTION || roleFunction="+roleFunction.getId()+" role="+role+" parent="+parent);
				rolefunctionDao.save(roleFunction);
				parent = parent.getParent();
			}
		}
	}

	@LogInfo(operateKind = OperateKind.删除, operateContent = "系统管理中删除角色成功")
	public void removeRole(Long[] ids) {
		for (Long id : ids) {
			Role role = roleDao.findOne(id);
			if (role.getStaffs().size() > 0) {
				throw new BusinessException("角色[" + role.getRoleName() + "]正在使用,不能删除");
			}
			roleDao.delete(id);
		}
	}

	public Page<Staff> findStaffPage(PageRequest pageRequest) {
		return staffDao.findAll(pageRequest);
	}

	public Iterable<Role> findRoles(Long staffId) {
		if (null == staffId) {
			return roleDao.findAll();
		}
		Iterable<Role> result = roleDao.findAll();
		for (Role role : result) {
			if (staffDao.findOne(staffId).getRole().getId().equals(role.getId())) {
				role.setSelected(Boolean.TRUE);
			}
		}
		return result;
	}

	@LogInfo(operateKind = OperateKind.新增, operateContent = "账号管理中新建用户成功")
	public void createStaff(Staff staff) {
		if (staffDao.findByLoginName(staff.getLoginName()) != null) {
			throw new BusinessException("已存在相同用户名");
		}
		staff.setLoginPassword(new Md5PasswordEncoder().encodePassword(staff.getPassword(), null));
		staffDao.save(staff);
	}

	@LogInfo(operateKind = OperateKind.删除, operateContent = "账号管理中删除用户成功")
	public void removeStaff(Long[] staffIds) {
		for (Long id : staffIds) {
			staffDao.delete(id);
		}
	}

	@LogInfo(operateKind = OperateKind.修改, operateContent = "账号管理中修改用户成功")
	public void updateStaff(Staff staff) {
		Staff staffInDb = staffDao.findOne(staff.getId());
		if (!staff.getLoginPassword().equals(staffInDb.getLoginPassword())) {
			staffInDb.setLoginPassword(new Md5PasswordEncoder().encodePassword(staff.getLoginPassword(), null));
		}
		staffInDb.setRole(roleDao.findOne(staff.getRole().getId()));
		staffInDb.setLoginStatus(staff.getLoginStatus());
		staffInDb.setName(staff.getName());
		staffInDb.setPhone(staff.getPhone());
		staffInDb.setEmail(staff.getEmail());
		staffInDb.setMemo(staff.getMemo());
	}

	public Iterable<Function> findUsedFunctions(Long roleId) {
		Iterable<Function> all = findAllFunctions();
		ArrayList<Function> used = new ArrayList<Function>();
		for (RoleFunction roleFunction : roleDao.findOne(roleId).getRoleFunctions()) {
			used.add(roleFunction.getFunction());
		}

		for (Function usedFunction : used) {
			for (Function f : all) {
				if (f.getId().equals(usedFunction.getId())) {
					f.setChecked(Boolean.TRUE);
				}

				for (Function child : f.getChildren()) {
					if (child.getId().equals(usedFunction.getId())) {
						child.setChecked(Boolean.TRUE);
					}
					
					Set<Function> children=child.getChildren();
					for (Function function : children) {
						if (function.getId().equals(usedFunction.getId())) {
							function.setChecked(Boolean.TRUE);
						}
					}
				}

			}
		}
		return all;
	}

	
	@LogInfo(operateKind = OperateKind.修改, operateContent = "账号管理中修改角色成功")
	public void updateRole(Role role, Long[] selectedFunctions) {
		Role roleInDb = roleDao.findOne(role.getId());
		roleInDb.setRoleName(role.getRoleName());
		roleInDb.setRemarks(role.getRemarks());
		for (RoleFunction roleFunction : roleInDb.getRoleFunctions()) {
			rolefunctionDao.delete(roleFunction);
		}
		for (Long functionId : selectedFunctions) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setRole(roleInDb);
			roleFunction.setFunction(functionDao.findOne(functionId));
			rolefunctionDao.save(roleFunction);
			Function parent = roleFunction.getFunction().getParent();
			while (parent != null) {
				roleFunction = new RoleFunction();
				roleFunction.setRole(role);
				roleFunction.setFunction(parent);
				logger.info("SAVE: ROLE_FUNCTION || roleFunction="+roleFunction.getId()+" role="+role+" parent="+parent);
				rolefunctionDao.save(roleFunction);
				parent = parent.getParent();
			}
		}

	}

	@LogInfo(operateKind = OperateKind.关闭, operateContent = "账号管理中关闭用户成功")
	public void closeStaffs(Long[] staffIds) {
		for (Long id : staffIds) {
			Staff staff = staffDao.findOne(id);
			staff.setLoginStatus(BigDecimal.ZERO);
		}
	}

	@LogInfo(operateKind = OperateKind.开启, operateContent = "账号管理中开启用户成功")
	public void openStaffs(Long[] staffIds) {
		for (Long id : staffIds) {
			Staff staff = staffDao.findOne(id);
			staff.setLoginStatus(BigDecimal.ONE);
		}
	}

	public Staff getCurrentStaff() {
		return (Staff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public Staff getCurrentStaffFromDb() {
		return staffDao.findOne(getCurrentStaff().getId());
	}
}
