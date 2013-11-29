package com.zendaimoney.online.admin.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.entity.RepayFlowDetailRepVO;
import com.zendaimoney.online.admin.service.RepayFlowDetailRepService;
import com.zendaimoney.online.admin.util.FileOperateUtil;
import com.zendaimoney.online.admin.web.DTO.RepayFlowDetailRepDTO;
import com.zendaimoney.online.common.BeanUtilsEx;
import com.zendaimoney.online.common.DateUtil;

/**
 * 还款明细查询和导出报表
 * @author HuYaHui
 *
 */
@Controller
@RequestMapping("/admin/report")
public class RepayFlowDetailRepController {
	private static Logger logger = LoggerFactory.getLogger(RepayFlowDetailRepController.class);
	@Autowired
	private RepayFlowDetailRepService repayFlowDetailRepService;
	

	@RequestMapping("downLoadTemplate")
	public void downLoadTemplate(HttpServletRequest request,  
            HttpServletResponse response, String fileType) throws Exception{
		String filename = "资金迁移模板".concat(".xls");//设置下载时客户端Excel的名称     
		FileOperateUtil.download(request, response, "fundsMigrate", filename);
	}

	@RequestMapping("forWord")
	public String forWord(HttpServletRequest request){
		String pagePath=request.getParameter("pagePath");
		Date startDate=new Date();
		startDate.setDate(1);
		request.setAttribute("startDate", DateUtil.getYMDTime(startDate));
		Date endDate=new Date();
		request.setAttribute("endDate", DateUtil.getYMDTime(endDate));
		return pagePath;
	}
	/**
	 * 根据条件分页查询
	 * 2013-3-22 上午9:12:50 by HuYaHui
	 */
	@RequestMapping("findByCondition")
	@ResponseBody
	public String findByCondition(HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("realeName");
		String idCard=request.getParameter("identityNo");
		String phone=request.getParameter("phoneNo");
		long loadId=(request.getParameter("loanId")!=null&&!request.getParameter("loanId").equals(""))?Long.valueOf(request.getParameter("loanId")):0;
		Date start=null;
		Date end=null;
		int page=request.getParameter("page")!=null?Integer.valueOf(request.getParameter("page")):1;
		int rows=request.getParameter("rows")!=null?Integer.valueOf(request.getParameter("rows")):10;
		String startStr=request.getParameter("start");
		if(startStr!=null && !startStr.equals("")){
			start=DateUtil.getYYYYMMDDString(startStr);
		}else{
			start=DateUtil.getYYYYMMDDDate(new Date());
			start.setDate(1);
		}
		String endStr=request.getParameter("end");
		if(endStr!=null && !endStr.equals("")){
			end=DateUtil.getYYYYMMDDString(endStr);
		}else{
			end=DateUtil.getYYYYMMDDDate(new Date());
		}
		
		long count=repayFlowDetailRepService.countByCondition(name, idCard, phone, loadId, start, end);
		if(count<=0){
			JSONObject json=new JSONObject();
			json.put("total", count);
			json.put("rows", "");
			return json.toString();
		}
		
		//查询结果
		List<RepayFlowDetailRepVO> list=
				repayFlowDetailRepService.findByCondition(name, idCard, phone, loadId, start, end, page, rows);
		List<RepayFlowDetailRepDTO> dtoList=new ArrayList<RepayFlowDetailRepDTO>();
		for(RepayFlowDetailRepVO vo:list){
			RepayFlowDetailRepDTO dto=new RepayFlowDetailRepDTO();
			try {
				BeanUtilsEx.copyProperties(dto, vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setDkzl("个人信用贷款");
			dtoList.add(dto);
		}
		
		JSONArray array=JSONArray.fromObject(dtoList);
		JSONObject json=new JSONObject();
		json.put("total", count);
		json.put("rows", array);
		return json.toString();
	}

	
	/**
	 * 导出符合条件的所有数据
	 * 2013-3-22 上午9:12:50 by HuYaHui
	 */
	@RequestMapping("exportDataByCondition")
	public String exportDataByCondition(
			HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("realeName");
		String idCard=request.getParameter("identityNo");
		String phone=request.getParameter("phoneNo");
		long loadId=(request.getParameter("loanId")!=null&&!request.getParameter("loanId").equals(""))?Long.valueOf(request.getParameter("loadId")):0;;
		Date start=null;
		Date end=null;
		String startStr=request.getParameter("start");
		if(startStr!=null && !startStr.equals("")){
			start=DateUtil.getYYYYMMDDString(startStr);
		}else{
			start=DateUtil.getYYYYMMDDDate(new Date());
			start.setDate(1);
		}
		String endStr=request.getParameter("end");
		if(endStr!=null && !endStr.equals("")){
			end=DateUtil.getYYYYMMDDString(endStr);
		}else{
			end=DateUtil.getYYYYMMDDDate(new Date());
		}
		try {
			repayFlowDetailRepService.exportExcelByCondition(request,response,name, idCard, phone, loadId, start, end,"repayFlowDetail");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 根据ID集合导出数据
	 * 2013-3-22 上午10:25:41 by HuYaHui
	 * @param idList
	 * @return
	 */
	@RequestMapping("exportDataByIdList")
	public String exportDataByIdList(
			HttpServletRequest request,HttpServletResponse response){
		try {
			String idStr=request.getParameter("idList");
			if(idStr==null || idStr.length()==0){
				return "/admin/loan/repayFlowDetailRep";
			}
			List<Long> idList=new ArrayList<Long>();
			for(String id:idStr.split(",")){
				idList.add(Long.valueOf(id));
			}
			repayFlowDetailRepService.exportExcelByIdList(request,response,idList, "repayFlowDetail");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
}
