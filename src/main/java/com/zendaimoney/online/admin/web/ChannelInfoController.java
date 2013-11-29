package com.zendaimoney.online.admin.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.admin.web.DTO.ChannelInfoDTO;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.web.TextSearch;

/**
 * 渠道信息
 * 
 * @author HuYaHui
 * 
 */
@Controller
@RequestMapping("/admin")
public class ChannelInfoController implements TextSearch {
	private static Logger logger = LoggerFactory.getLogger(ChannelInfoController.class);

	@Autowired
	private ChannelInfoService channelInfoService;

	/**
	 * 获取所有一级渠道信息
	 */
	public String textSearch(HttpServletRequest request) {
		JSONObject obj = JSONObject.fromObject(channelInfoService.parentMap);
		return obj.toString();
	}

	/**
	 * 批量删除 2012-12-27 上午8:41:43 by HuYaHui
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("delChannelInfoS")
	@ResponseBody
	public String delChannelInfoS(HttpServletRequest request, Long[] infoIds) {
		if (infoIds == null || infoIds.length == 0) {
			return "没有选中要删除的记录！";
		}

		StringBuilder sb = new StringBuilder();
		for (Long id : infoIds) {
			// 根据每个ID查询记录是否使用
			String code = channelInfoService.checkChannelInfoIsBeUse(id);
			if (code != "" && !code.equals("")) {
				// 如果已经使用，不能删除
				if (sb.length() == 0) {
					sb.append(code);
				} else {
					sb.append(",").append(code);
				}
			} else {
				channelInfoService.delChannel(id);
			}
		}
		// 不存在已经使用的渠道信息
		if (sb.length() == 0) {
			channelInfoService.deleteDirtyData();
			return "true";
		} else {
			return "已有用户通过ID为：" + sb.toString() + "的渠道注册，因此不能删除。";
		}
	}

	/**
	 * 删除 2012-12-27 上午8:41:43 by HuYaHui
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("delChannelInfo")
	@ResponseBody
	public String delChannelInfo(HttpServletRequest request) {
		String idStr = request.getParameter("id");
		if (idStr == null || idStr.equals("")) {
			return "没有选中要删除的记录！";
		}
		Long id = Long.valueOf(idStr);

		String code = channelInfoService.checkChannelInfoIsBeUse(id);
		if (code != "" && !code.equals("")) {
			// 如果已经使用，不能删除
			return "已有用户通过ID为：" + code + "的渠道注册，因此不能删除。";
		}
		channelInfoService.delChannel(id);
		channelInfoService.deleteDirtyData();
		return "true";
	}

	/**
	 * 增加 2012-12-27 上午8:41:31 by HuYaHui
	 * 
	 * @param request
	 * @param code
	 * @param name1
	 * @param name2
	 * @param desc
	 * @return
	 */
	@RequestMapping("addChannelInfo")
	@ResponseBody
	public String addChannelInfo(HttpServletRequest request, @RequestParam("code") String code, @RequestParam("name1") String name1, @RequestParam("name2") String name2, @RequestParam("description") String desc, @RequestParam("mode") Long mode) {
		try {
			code = code.trim();
			name1 = name1.trim();
			name2 = name2.trim();
			String id = request.getParameter("id");
			// 增加
			List<ChannelInfoVO> list = channelInfoService.findByName(name1);
			if (id == null || id.equals("")) {
				// 校验渠道ID是否存在
				List<ChannelInfoVO> codeList = channelInfoService.findByCode(code);
				if (codeList != null && codeList.size() > 0) {
					// 渠道ID存在不能保存（保证唯一性)
					return "渠道ID重复!";
				}
				channelInfoService.updateChannel(null, code, name1, name2, desc, mode, list);
			} else {
				channelInfoService.updateChannel(id, code, name1, name2, desc, mode, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "保存失败!";
		}
		return "true";
	}

	/**
	 * 根据id查询 2012-12-27 上午8:42:00 by HuYaHui
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("findById")
	public String findById(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (id == null || id.equals("")) {
			return "admin/channelInfo";
		} else {
			ChannelInfoDTO dto = channelInfoService.findById(Long.valueOf(id));
			// 一级渠道的名称
			String parName = channelInfoService.cacheMapForParentData(dto.getParentId());
			dto.setName1(parName);
			dto.setName2(dto.getName());
			request.setAttribute("dto", dto);
			return "admin/updateChannel";
		}
	}

	/**
	 * 根据条件查询 2012-12-26 下午2:20:15 by HuYaHui
	 * 
	 * @return
	 */
	@RequestMapping("findChannelInfo")
	@ResponseBody
	public String findChannelInfo(HttpServletRequest request) {
		String code = request.getParameter("code");
		String name1 = request.getParameter("name1");
		String name2 = request.getParameter("name2");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		int count = channelInfoService.findByConditionCount(code, name1, name2);
		logger.info("查询到的数据count：" + count);
		if (count < 0) {
			return null;
		}
		// 查询所有二级渠道信息,按照一级渠道升序，创建时间降序
		List<ChannelInfoVO> channelList = channelInfoService.findByCondition(code, name1, name2, page == null ? 1 : Integer.valueOf(page), rows == null ? 10 : Integer.valueOf(rows));
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

				dto.setName(f_name + ">>" + vo.getName());
				dto.setCreateDate(DateUtil.getYMDTime(vo.getCreateTime()));
				dto.setUpdateDate(DateUtil.getYMDTime(vo.getUpdateTime()));
				JSONObject jsonObj = JSONObject.fromObject(dto);
				jsonAry.add(jsonObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("转换json，查询到的数据大小为：" + (jsonAry != null ? jsonAry.size() : 0));
		JSONObject json = new JSONObject();
		json.put("total", count);
		json.put("rows", jsonAry);
		logger.info("返回的json内容为：" + json.toString());
		return json.toString();
	}

}
