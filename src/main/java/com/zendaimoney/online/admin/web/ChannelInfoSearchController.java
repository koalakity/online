package com.zendaimoney.online.admin.web;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.service.ChannelInfoService;
import com.zendaimoney.online.web.TextSearch;

@Controller
public class ChannelInfoSearchController implements TextSearch{
	@Autowired
	private ChannelInfoService channelInfoService;
	
	/**
	 * 所有二级渠道名称
	 * 2013-1-17 下午3:39:30 by HuYaHui
	 * @return
	 * @throws SQLException
	 */
	public String textSearch(HttpServletRequest request){
		Map<Long,String> parentMap=new HashMap<Long, String>();
		int row=channelInfoService.findByConditionCount(null, null, null);
		if(row>0){
			List<ChannelInfoVO> channelList=channelInfoService.findByCondition(null, null, null, 1, row);
			if(channelList!=null && channelList.size()>0){
				for(ChannelInfoVO vo:channelList){
					parentMap.put(vo.getId(), vo.getName());
				}
			}
		}
		return JSONObject.fromObject(parentMap).toString();
	}

}
