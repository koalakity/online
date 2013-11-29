package com.zendaimoney.online.admin.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.dao.ChannelDAO;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.entity.RateAdminVO;
import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.admin.service.RateService;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.RateVO;
import com.zendaimoney.online.admin.web.DTO.ChannelInfoDTO;

/**
 * \
 * 
 * Copyright (c) 2012 ZENDAI. All Rights Reserved. This software is published
 * under the terms of the ZENDAI Software
 * 
 * @author jihui
 * @date: 2013-3-6 下午1:36:13 operation by: description:费率管理
 */
@Controller
@RequestMapping(value = "/admin/rate")
public class RateController {
	@Autowired
	private RateService rateService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private ChannelDAO channelDao;

	@RequestMapping(value = "rateMaintainJsp")
	public String rateMaintain() {
		return "admin/rate/rateMaintain";
	}

	@RequestMapping(value = "rateInfo")
	@ResponseBody
	public com.zendaimoney.online.admin.vo.Page<RateAdminVO> rateInfo(Integer page, Integer rows) {
		return new com.zendaimoney.online.admin.vo.Page<RateAdminVO>(rateService.findRateList(new PageRequest(page - 1, rows, new Sort(Direction.ASC, "opDate"))));
	}

	@RequestMapping(value = "checkRateName")
	@ResponseBody
	public String checkRateName(String rateName, Model model, HttpServletResponse rep) {
		return rateService.checkRateName(rateName);
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-7 下午2:42:37
	 * @param rateVo
	 * @return description:新增费率
	 */
	@RequestMapping(value = "addRate")
	@ResponseBody
	public AjaxResult addRate(RateAdminVO rateVo) {
		return rateService.addRate(rateVo);
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-7 下午4:10:52
	 * @param rateVo
	 * @return description:修改费率，将要修改的费率逻辑删除，然后新增一条新的费率，如果该费率与渠道有绑定将不能修改
	 */
	@RequestMapping(value = "modifyRate")
	@ResponseBody
	public AjaxResult modifyRate(RateAdminVO rateVo) {
		Long oldRateID = rateVo.getId();
		List<ChannelInfoVO> channelInfoList = channelInfoService.findByRateID(oldRateID);
		AjaxResult result = new AjaxResult();
		if (channelInfoList.size() > 0) {
			result.setSuccess(true);
			result.setMsg("该费率已与渠道绑定，不能修改！");
		} else {
			RateAdminVO rateNew = rateService.modifyRate(rateVo);
			// for (ChannelInfoVO channelInfo : channelInfoList) {
			// channelInfo.setRateID(rateNew.getId());
			// }
			// channelDao.save(channelInfoList);
			result.setSuccess(true);
			result.setMsg("成功修改");
		}

		return result;
	}

	@RequestMapping(value = "deleteRate")
	@ResponseBody
	public AjaxResult deleteRate(Long rateId) {
		return rateService.deleteRate(rateId);

	}

	@RequestMapping(value = "rateDesginJsp")
	public String rateDesginJsp() {
		return "admin/rate/rateDesign";
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-11 上午10:54:42
	 * @param page
	 * @param rows
	 * @return description:渠道费率列表
	 */
	@RequestMapping(value = "channelRate")
	@ResponseBody
	public String channelRate(int page, int rows) {
		List<ChannelInfoVO> channelList = rateService.findChannelRate((page - 1) * rows, rows);
		JSONArray jsonAry = new JSONArray();
		for (ChannelInfoVO vo : channelList) {
			try {
				String _code = vo.getCode();
				Long parentId = vo.getParentId();
				if ((_code == null || _code.equals("")) && (parentId == null || parentId == 0)) {
					// 一级渠道不显示
					continue;
				}
				ChannelInfoDTO dto = new ChannelInfoDTO();
				BeanUtils.copyProperties(dto, vo);
				// 一级渠道的名称
				String f_name = channelInfoService.cacheMapForParentData(parentId);
				RateAdminVO rate = rateService.findCurRateByCond(vo.getRateID());
				dto.setName1(f_name);
				dto.setName2(vo.getName());
				if (rate != null) {
					dto.setRateName(rate.getRateName());
				} else {
					dto.setRateName("");
				}
				JSONObject jsonObj = JSONObject.fromObject(dto);
				jsonAry.add(jsonObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int count = channelInfoService.findByConditionCount("", "", "");
		JSONObject json = new JSONObject();
		json.put("total", count);
		json.put("rows", jsonAry);
		return json.toString();
	}

	@RequestMapping(value = "modifyChannlRate")
	public String modifyChannlRate(Long id, Long rateId, String name1, String name2, Model model) {
		model.addAttribute("channelId", id);
		model.addAttribute("rateId", rateId);
		model.addAttribute("name1", name1);
		model.addAttribute("name2", name2);
		return "admin/rate/channelRateMod";
	}

	@RequestMapping(value = "curRate")
	@ResponseBody
	public String curRate(Long rateId) {
		RateAdminVO rate = rateService.findCurRateByCond(rateId);
		RateVO rateVo = new RateVO();
		try {
			PropertyUtils.copyProperties(rateVo, rate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(rateVo);
		JSONObject json = new JSONObject();
		JSONArray jsonAry = new JSONArray();
		jsonAry.add(jsonObj);
		json.put("rows", jsonAry);
		return json.toString();
	}

	@RequestMapping(value = "initCombobox")
	@ResponseBody
	public JSONArray initCombobox() {
		List<RateAdminVO> rateList = rateService.initCombobox();
		JSONArray jsonArry = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		for (RateAdminVO rate : rateList) {
			jsonObj.put("id", rate.getId());
			jsonObj.put("text", rate.getRateName());
			jsonArry.add(jsonObj);
		}
		return jsonArry;
	}

	@RequestMapping(value = "saveModify")
	@ResponseBody
	public void saveModify(Long id, Long rateId) {
		channelInfoService.saveModify(id, rateId);
	}
}
