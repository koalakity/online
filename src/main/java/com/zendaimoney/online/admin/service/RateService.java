package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.ChannelDAO;
import com.zendaimoney.online.admin.dao.LoanInfoAdminDAO;
import com.zendaimoney.online.admin.dao.LoanRateAdminDao;
import com.zendaimoney.online.admin.dao.RateAdminDao;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.LoanInfoAdminVO;
import com.zendaimoney.online.admin.entity.LoanRateAdminVO;
import com.zendaimoney.online.admin.entity.RateAdminVO;
import com.zendaimoney.online.admin.vo.AjaxResult;

/**
 * 
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author jihui
 * @date: 2013-3-21 上午10:12:40 operation by: description: 活动费率添加，修改等方法
 */
@Service
@Transactional
public class RateService {
	@Autowired
	private RateAdminDao rateDao;
	@Autowired
	private ChannelDAO channelInfoDAO;
	@Autowired
	private LoanInfoAdminDAO loanAdminDao;
	@Autowired
	private LoanRateAdminDao loanRateDao;
	@Autowired
	private StaffService staffService;
	private static final BigDecimal ISDEL = BigDecimal.ONE;
	private static final BigDecimal NODEL = BigDecimal.ZERO;
	private static final BigDecimal SCALE = BigDecimal.valueOf(100);

	public Page<RateAdminVO> findRateList(PageRequest pageRequest) {
		return rateDao.findAll(new Specification<RateAdminVO>() {
			@Override
			public Predicate toPredicate(Root<RateAdminVO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.or(cb.isNull(root.get("isDel")), cb.notEqual(root.get("isDel"), ISDEL)));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageRequest);
	}

	public String checkRateName(String rateName) {
		if (rateDao.findByRateNameAndIsDelNot(rateName).size() > 0) {
			return "false";
		} else {
			return "true";
		}
	}

	@LogInfo(operateKind = OperateKind.新增, operateContent = "费率维护：新增费率")
	public AjaxResult addRate(RateAdminVO rateVo) {
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		if (rateDao.findByRateNameAndIsDelNot(rateVo.getRateName()).size() > 0) {
			result.setMsg("已经存在相同的费率名称！");
		} else {
			rateVo.setOpUserId(staffService.getCurrentStaff().getId());
			rateVo.setOpDate(new Date());
			rateVo.setIsDel(NODEL);
			RateAdminVO rate = percentConvert(rateVo);
			rateDao.save(rate);
			result.setMsg("新增成功！");
		}
		return result;
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-21 下午3:24:00
	 * @param rateVo
	 * @return description:修改费率，将原有的费率逻辑删除，重现生成一条新的
	 */
	@LogInfo(operateKind = OperateKind.修改, operateContent = "费率维护：修改费率")
	public RateAdminVO modifyRate(RateAdminVO rateVo) {
		RateAdminVO rate = rateDao.findById(rateVo.getId());
		rate.setIsDel(ISDEL);
		rate.setDeDate(new Date());
		rateDao.save(rate);
		rateVo.setId(null);
		RateAdminVO rateFin = percentConvert(rateVo);
		rateDao.save(rateFin);
		return rateFin;
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-7 下午4:41:07
	 * @param rateId
	 * @return description:删除费率，如果该费率与渠道有绑定将不能删除
	 */
	@LogInfo(operateKind = OperateKind.删除, operateContent = "费率维护：删除费率")
	public AjaxResult deleteRate(Long rateId) {
		List<ChannelInfoVO> channelList = channelInfoDAO.findByRateID(rateId);
		AjaxResult result = new AjaxResult();
		if (channelList.size() > 0) {
			result.setMsg("该费率已与渠道绑定，删除失败！");
			result.setSuccess(false);
		} else {
			RateAdminVO rate = rateDao.findById(rateId);
			rate.setOpUserId(staffService.getCurrentStaff().getId());
			rate.setIsDel(ISDEL);
			rate.setDeDate(new Date());
			rateDao.save(rate);
			result.setSuccess(true);
		}
		return result;
	}

	public List<ChannelInfoVO> findChannelRate(int page, int rows) {
		List<ChannelInfoVO> channelList = channelInfoDAO.findByCreateTime(page, rows);
		return channelList;
	}

	public RateAdminVO findCurRateByCond(Long rateId) {
		RateAdminVO rate = rateDao.findById(rateId);
		// if (channelList.size() > 0) {
		// rate = channelList.get(0).getRateAdmin();
		// }
		return rate;
	}

	public List<RateAdminVO> initCombobox() {
		return rateDao.findByIsDel();
	}

	// 根据借款ID获取对应的费率信息
	public LoanRateAdminVO getLoanCoRate(Long loanId) {
		LoanInfoAdminVO loanInfo = loanAdminDao.findByLoanId(loanId);
		LoanRateAdminVO loanRate = loanRateDao.findById(loanInfo.getLoanRateId());
		return loanRate;
	}

	// 将百分数转换为小数存入数据库中
	public RateAdminVO percentConvert(RateAdminVO rateVo) {
		RateAdminVO rate = new RateAdminVO();
		rate.setOpUserId(staffService.getCurrentStaff().getId());
		rate.setIdFee(rateVo.getIdFee());
		rate.setIsDel(rateVo.getIsDel());
		rate.setOpDate(new Date());
		rate.setRateName(rateVo.getRateName());
		rate.setWithdraw(rateVo.getWithdraw());
		rate.setLoan(rateVo.getLoan().divide(SCALE));
		rate.setOverdueFines(rateVo.getOverdueFines().divide(SCALE));
		rate.setOverdueInterest(rateVo.getOverdueInterest().divide(SCALE));
		rate.setOverdueSeriousInterest(rateVo.getOverdueSeriousInterest().divide(SCALE));
		rate.setEarlyFines(rateVo.getEarlyFines().divide(SCALE));
		rate.setMgmtFee(rateVo.getMgmtFee().divide(SCALE));
		rate.setReserveFoud(rateVo.getReserveFoud().divide(SCALE));
		rate.setRecharge(rateVo.getRecharge().divide(SCALE));
		return rate;
	}

	// 后台根据渠道获取对应费率的ID5验证费用
	public double getId5Fee(Long channelId) {
		ChannelInfoVO channel = channelInfoDAO.findById(channelId);
		Long rateId = Long.valueOf(1);// 默认费率
		if (channel.getRateID() != null) {
			rateId = channel.getRateID();
		}
		RateAdminVO rate = rateDao.findById(rateId);
		return rate.getIdFee().doubleValue();
	}
}
