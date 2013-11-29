package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.site.NoticeDao;
import com.zendaimoney.online.admin.entity.site.NoticeAdmin;

@Service
@Transactional
public class NoticeService {
	private static Logger logger = LoggerFactory.getLogger(NoticeService.class);

	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private StaffService staffService;

	public Page<NoticeAdmin> findNoticePage(Integer type, Pageable pageable) {
		return noticeDao.findByTypeAndIsDel(type, BigDecimal.ONE, pageable);
	}

	@LogInfo(operateKind = OperateKind.新增, operateContent = "内容管理中新增内容")
	public void createNotice(NoticeAdmin noticeAdmin) {
		noticeAdmin.setCreDate(new Date());
		noticeAdmin.setIsDel(BigDecimal.ONE);
		noticeAdmin.setStaff(staffService.getCurrentStaff());
		logger.info("SAVE: NOTICE ||  IsDel=1 noticeAdmin=" + noticeAdmin.getId());
		noticeDao.save(noticeAdmin);
	}

	@LogInfo(operateKind = OperateKind.删除, operateContent = "内容管理中删除内容")
	public void removeNotices(Long[] noticeIds) {
		for (Long id : noticeIds) {
			noticeDao.findOne(id).setIsDel(BigDecimal.ZERO);
		}
	}

	@LogInfo(operateKind = OperateKind.修改, operateContent = "内容管理中修改内容")
	public void updateNotice(NoticeAdmin noticeAdmin) {
		NoticeAdmin staffInDb = noticeDao.findOne(noticeAdmin.getId());
		staffInDb.setTitle(noticeAdmin.getTitle());
		staffInDb.setOrders(noticeAdmin.getOrders());
		staffInDb.setIsCommend(noticeAdmin.getIsCommend());
		staffInDb.setContent(noticeAdmin.getContent());
	}

	@LogInfo(operateKind = OperateKind.推荐, operateContent = "内容管理中推荐内容")
	public void commendNotices(Long[] noticeIds) {
		for (Long id : noticeIds) {
			NoticeAdmin noticeAdmin = noticeDao.findOne(id);
			if (BigDecimal.ONE.equals(noticeAdmin.getIsCommend())) {
				continue;
			}
			checkCount(noticeAdmin.getType());
			noticeAdmin.setIsCommend(BigDecimal.ONE);
		}
	}

	public void checkCount(Integer type) {
		if (Integer.valueOf(20).equals(type) || Integer.valueOf(21).equals(type) || Integer.valueOf(22).equals(type)) {
			List<NoticeAdmin> list = noticeDao.findByTypeAndIsDelAndIsCommend(type, BigDecimal.ONE, BigDecimal.ONE);
			if (list.size() >= 4) {
				throw new BusinessException("最多可以推荐4个");
			}
		}
	}

	@LogInfo(operateKind = OperateKind.不推荐, operateContent = "内容管理中取消推荐内容")
	public void unCommendNotices(Long[] noticeIds) {
		for (Long id : noticeIds) {
			NoticeAdmin noticeAdmin = noticeDao.findOne(id);
			noticeAdmin.setIsCommend(BigDecimal.ZERO);
		}
	}
}
