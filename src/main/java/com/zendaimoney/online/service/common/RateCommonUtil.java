package com.zendaimoney.online.service.common;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.dao.LoanInfoDAO;
import com.zendaimoney.online.dao.UsersDAO;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.rate.ChannelInfoDao;
import com.zendaimoney.online.dao.rate.LoanRateDao;
import com.zendaimoney.online.dao.rate.RateCommonDao;
import com.zendaimoney.online.entity.ChannelVO;
import com.zendaimoney.online.entity.LoanInfoVO;
import com.zendaimoney.online.entity.UsersVO;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.entity.common.Rate;

/**
 * 
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author jihui
 * @date: 2013-3-11 下午2:05:38 operation by: description:费率调用common方法
 */
@Component
public class RateCommonUtil {
	@Autowired
	private UsersDAO usersDao;
	@Autowired
	private ChannelInfoDao channelInfoDao;
	@Autowired
	private RateCommonDao rateCommonDao;
	@Autowired
	private LoanRateDao loanRateDao;
	@Autowired
	private LoanInfoDAO loanInfoDao;
	@Autowired
	private CommonDao commonDao;

	// 根据用户获取充值费率
	public double reChargeRate(Long userId) {
		UsersVO user = usersDao.findByUserId(userId);
		Rate rate = new Rate();
		// 如果没有绑定渠道，则取默认费率
		if (user.getChannelInfoId() == null) {
			rate = rateCommonDao.findById(Long.valueOf(1));
		} else {
			Long channelId = user.getChannelInfoId();
			ChannelVO channel = channelInfoDao.findById(channelId);
			if (channel.getRateID() == null) {
				rate = rateCommonDao.findById(Long.valueOf(1));
			} else {
				rate = rateCommonDao.findById(channel.getRateID());
			}
		}
		return rate.getRecharge().doubleValue();
	}

	// 发布借款，关联对应的费率
	public Long setLoanRate(Long userId) {
		UsersVO user = usersDao.findByUserId(userId);
		Rate rate = new Rate();
		// 如果没有绑定渠道，则取默认费率
		if (user.getChannelInfoId() == null) {
			rate = rateCommonDao.findById(Long.valueOf(1));
		} else {
			Long channelId = user.getChannelInfoId();
			ChannelVO channel = channelInfoDao.findById(channelId);
			rate = rateCommonDao.findById(channel.getRateID());
		}
		LoanRateVO loanRate = new LoanRateVO();
		Long loanRateId = null;
		try {
			PropertyUtils.copyProperties(loanRate, rate);
			loanRate.setId(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		loanRateDao.save(loanRate);
		return loanRate.getId();
	}

	// 根据借款ID获取对应的费率信息
	public LoanRateVO getLoanCoRate(Long loanId) {
		LoanInfoVO loanInfo = loanInfoDao.findByLoanId(loanId);
		LoanRateVO loanRate = loanRateDao.findById(loanInfo.getLoanRateId());
		return loanRate;
	}

	// 根据Loan_rate _id获取费率信息
	public LoanRateVO getLoanRateByRateId(Long loanRateId) {
		LoanRateVO loanRate = loanRateDao.findById(loanRateId);
		return loanRate;
	}

	// ID5验证费用
	public double getId5Fee(Long channelId) {
		ChannelVO channel = channelInfoDao.findById(channelId);
		Long rateId = Long.valueOf(1);// 默认费率
		if (channel != null) {
			if (channel.getRateID() != null) {
				rateId = channel.getRateID();
			}
		}
		Rate rate = rateCommonDao.findById(rateId);
		return rate.getIdFee().doubleValue();
	}

	// 根据用户获取费率信息
	public Rate getRateByUser(Long userId) {
		UsersVO user = usersDao.findByUserId(userId);
		Rate rate = new Rate();
		if (user.getChannelInfoId() == null) {
			rate = rateCommonDao.findById(Long.valueOf(1));
		} else {
			Long channelId = user.getChannelInfoId();
			ChannelVO channel = channelInfoDao.findById(channelId);
			rate = rateCommonDao.findById(channel.getRateID());
		}
		return rate;
	}
}
