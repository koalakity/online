package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.dao.AdminLogDao;
import com.zendaimoney.online.admin.entity.AdminLog;
import com.zendaimoney.online.admin.vo.AdminLogForm;

@Service
@Transactional
public class AdminLogService {
	private static Logger logger = LoggerFactory.getLogger(AdminLogService.class);
	@Autowired
	private AdminLogDao adminLogDao;
	
	@Autowired
	private StaffService staffService;
	
	public void createLog(LogInfo logInfo, Long id){
		AdminLog adminLog=new AdminLog();
		if(null!=id){
			adminLog.setOperateObjectId(new BigDecimal(id));
		}
		adminLog.setOperateKind(logInfo.operateKind());
		adminLog.setOperateContent(logInfo.operateContent());
		ServletRequestAttributes sra = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes());
		adminLog.setIpAdd(sra.getRequest().getRemoteAddr());
		adminLog.setStaff(staffService.getCurrentStaff());
		logger.info("SAVE: 操作日志 || "+" OperateKind="+logInfo.operateKind()+" OperateContent="+logInfo.operateContent()
				+" OperateContent="+logInfo.operateContent()+" Staff="+staffService.getCurrentStaff().getName());
		adminLogDao.save(adminLog);
	}

	public Page<AdminLog> findAmdinLogPage(final AdminLogForm adminLogForm, Pageable pageable) {
		return adminLogDao.findAll(new Specification<AdminLog>() {
			@Override
			public Predicate toPredicate(Root<AdminLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates=new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(adminLogForm.getStaffName())){
					Path<String> loginName = root.get("staff").get("loginName");
					predicates.add(cb.like(cb.lower(loginName),"%"+adminLogForm.getStaffName().toLowerCase()+"%"));
				}
				if(null!=adminLogForm.getOperateKind()){
					predicates.add(cb.equal(root.get("operateKind"), adminLogForm.getOperateKind()));
				}
				 
				Path<Date> regTime=root.get("operateTime");
				if(null!=adminLogForm.getOperateTimeMin()){
					predicates.add(cb.greaterThanOrEqualTo(regTime, adminLogForm.getOperateTimeMin()));
				}
				if(null!=adminLogForm.getOperateTimeMax()){
					adminLogForm.getOperateTimeMax().setHours(23);
					adminLogForm.getOperateTimeMax().setMinutes(59);
					adminLogForm.getOperateTimeMax().setSeconds(59);
					predicates.add(cb.lessThanOrEqualTo(regTime, adminLogForm.getOperateTimeMax()));
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}
}
