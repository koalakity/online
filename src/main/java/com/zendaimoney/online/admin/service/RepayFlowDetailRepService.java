package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.dao.AcTVirtualCashFlowAdminDAO;
import com.zendaimoney.online.admin.dao.LoanInfoAdminDAO;
import com.zendaimoney.online.admin.dao.RepayFlowDetailRepDAO;
import com.zendaimoney.online.admin.entity.AcTVirtualCashFlowAdminVO;
import com.zendaimoney.online.admin.entity.LoanInfoAdminVO;
import com.zendaimoney.online.admin.entity.RepayFlowDetailRepVO;
import com.zendaimoney.online.admin.util.ExcelWriter;
import com.zendaimoney.online.admin.util.FileOperateUtil;
import com.zendaimoney.online.admin.util.FundsMigrate;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.BusinessCalculateUtils;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.FormulaSupportUtil;
import com.zendaimoney.online.constant.loanManagement.RepayStatus;
import com.zendaimoney.online.entity.common.LoanRateVO;
import com.zendaimoney.online.service.common.RateCommonUtil;


@Service
@Transactional(readOnly=true)
public class RepayFlowDetailRepService {
	@Autowired
	private RepayFlowDetailRepDAO repayFlowDetailRepDAO;

	@Autowired
	private AcTVirtualCashFlowAdminDAO acTVirtualCashFlowAdminDAO;
	@Autowired
	private LoanInfoAdminDAO loanInfoAdminDAO;
	@Autowired
	private RateCommonUtil rateCommonUtil;
	

	String[] str=new String[]{"渠道来源","贷款编号","用户ID","客户姓名","身份证",
			"手机号码","借款用途","贷款种类","合同金额","期限","当前期数","本月应还款日",
			"本期应总额","本期应还本金","本期应还利息","应还管理费",
			"实际还款日","实际还款总额","还款性质","逾期罚息",
			"逾期管理费","逾期利息","逾期本金","逾期违约金","当期管理费",
			"当期本金","当期利息","提前还款本金","提前还款违约金"
			};	

	/**
	 * 只运行一次，把原来所有数据整合到还款明细表
	 * 2013-3-26 上午11:15:11 by HuYaHui
	 */
	@Deprecated
	@Transactional(readOnly=false)
	public void initRepayFlowDetailRep(){
		repayFlowDetailRepDAO.initRepayFlowDetailRep();
	}
	
	
	/**
	 * 根据条件分页查询
	 * 2013-3-22 上午9:12:50 by HuYaHui
	 * @param name
	 * 			名字
	 * @param idCard
	 * 			身份证号码
	 * @param phone
	 * 			手机号码
	 * @param loadId
	 * 			借款编号
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @param page
	 * 			第几页
	 * @param rows
	 * 			每页显示数量
	 * @return
	 			总数
	 */
	public List<RepayFlowDetailRepVO> findByCondition(String name,String idCard,String phone,long loadId,Date start,Date end,int page,int rows){
		return repayFlowDetailRepDAO.findByCondition(name, idCard, phone, loadId, start, end, page, rows);
	}

	/**
	 * 根据条件统计查询结果数量
	 * 2013-3-22 上午10:08:24 by HuYaHui
	 * @param name
	 * 			名字
	 * @param idCard
	 * 			身份证号码
	 * @param phone
	 * 			手机号码
	 * @param loadId
	 * 			借款编号
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @return
	 			总数
	 */
	public long countByCondition(String name,String idCard,String phone,long loadId,Date start,Date end){
		return repayFlowDetailRepDAO.countByCondition(name, idCard, phone, loadId, start, end);
	}
	
	/**
	 * 根据ID集合导出数据
	 * 2013-3-22 上午10:25:41 by HuYaHui
	 * @param idList
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> exportExcelByIdList(HttpServletRequest request,HttpServletResponse response,List<Long> idList,String proPath) throws Exception{
		List<RepayFlowDetailRepVO> list=repayFlowDetailRepDAO.findByIdList(idList);
		Map<String,Object> dataMap=new HashMap<String, Object>();
		if(list==null || list.size()==0){
			return dataMap;
		}
		String fileName = DateUtil.formatDate(new Date(), DateUtil.YYYYMMDDHHMMSS)+"_还款明细".concat(".xls");//设置下载时客户端Excel的名称
		List<String[]> fileDataList=new ArrayList<String[]>();
		fileDataList.add(str);
		for(RepayFlowDetailRepVO vo:list){
			fileDataList.add(vo.toString().split(","));
		}
		String filePath = FundsMigrate.getFilePath(proPath);
		String path=(filePath + fileName);
		dataMap.put("path", path);
		dataMap.put("data", fileDataList);
		return dataMap;
	}
	
	/**
	 * 导出符合条件的所有数据
	 * 2013-3-22 上午9:12:50 by HuYaHui
	 * @param name
	 * 			名字
	 * @param idCard
	 * 			身份证号码
	 * @param phone
	 * 			手机号码
	 * @param loadId
	 * 			借款编号
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @param page
	 * 			第几页
	 * @param rows
	 * 			每页显示数量
	 * @return
	 			已经生成到服务器的文件名
	 * @throws Exception 
	 */
	public Map<String,Object> exportExcelByCondition(HttpServletRequest request,HttpServletResponse response,String name,String idCard,String phone,long loadId,Date start,Date end,String proPath) throws Exception{
		List<RepayFlowDetailRepVO> list=repayFlowDetailRepDAO.findByCondition(name, idCard, phone, loadId, start, end,0,0);
		Map<String,Object> dataMap=new HashMap<String, Object>();
		if(list==null || list.size()==0){
			return dataMap;
		}
		String fileName = DateUtil.formatDate(new Date(), DateUtil.YYYYMMDDHHMMSS)+"_还款明细".concat(".xls");//设置下载时客户端Excel的名称
		List<String[]> fileDataList=new ArrayList<String[]>();
		fileDataList.add(str);
		for(RepayFlowDetailRepVO vo:list){
			fileDataList.add(vo.toString().split(","));
		}
		String filePath = FundsMigrate.getFilePath(proPath);
		String path=(filePath + fileName);
		dataMap.put("path", path);
		dataMap.put("data", fileDataList);
		return dataMap;
	}


	/** 
	 * @author 王腾飞
	 * @date 2013-3-22 上午11:01:38
	 * description:每天刷新数据
	*/
	@Transactional(readOnly=false ,propagation = Propagation.REQUIRED)
	public void reFreshData(){
		//添加昨天新的放款
		addNewData();
		//未还款列表
		List<RepayFlowDetailRepVO> _list = repayFlowDetailRepDAO.findByRepayStatus("0");
		for(RepayFlowDetailRepVO vo : _list){
			reFreshProgress(vo);
		}
		
	}
	@Transactional(readOnly=false ,propagation = Propagation.REQUIRED)
	public void reFreshProgress(RepayFlowDetailRepVO vo){
		
		LoanInfoAdminVO loanInfo = loanInfoAdminDAO.findOne(vo.getLoanId());
		AcTVirtualCashFlowAdminVO actVirVo= acTVirtualCashFlowAdminDAO.findByActLedgerLoanAndCurrNum(loanInfo.getActLedgerLoan(), Long.valueOf(vo.getCurrNum()));
		//设置还款日期
		vo.setEditDate(actVirVo.getEditDate());
		Date temp = DateUtil.getDateyyyyMMdd();
		//逾期天数
		int overDueDays = FormulaSupportUtil.getOverdueDays(actVirVo.getRepayDay(), temp);			
		if(actVirVo.getRepayStatus().equals(RepayStatus.正常还款)){
			//本金
			vo.setPrincipal(actVirVo.getPrincipalAmt());
			//利息
			vo.setInterest(actVirVo.getInterestAmt());
			//月缴管理费
			vo.setCurrManagerFee(loanInfo.getMonthManageCost());
			//当期还款总额
			vo.setPayAmt(actVirVo.getAmt());
			//正常还款
			vo.setRepayStatus("1");
			BigDecimal amt = BigDecimalUtil.add(BigDecimalUtil.add(actVirVo.getPrincipalAmt(), actVirVo.getInterestAmt()), loanInfo.getMonthManageCost());
			if(actVirVo.getAmt().compareTo(amt)==1){
				//剩余本金
				vo.setOnceBreachPenalty(BigDecimalUtil.sub(actVirVo.getAmt(), amt));
			}
		}else if(actVirVo.getRepayStatus().equals(RepayStatus.逾期还款)){
			int _temp = (actVirVo.getEditDate().getYear()*12+actVirVo.getEditDate().getMonth())-(actVirVo.getRepayDay().getYear()*12+actVirVo.getRepayDay().getMonth());
			if(_temp>=1){
				//设置逾期本金
				vo.setOverduePrincipal(actVirVo.getPrincipalAmt());
				//逾期利息
				vo.setOverdueInterest(actVirVo.getInterestAmt());
				//逾期管理费
				vo.setOverdueManagerFee(loanInfo.getMonthManageCost());
			}else{
				//本金
				vo.setPrincipal(actVirVo.getPrincipalAmt());
				//利息
				vo.setInterest(actVirVo.getInterestAmt());
				//月缴管理费
				vo.setCurrManagerFee(loanInfo.getMonthManageCost());
			}
			//设置逾期罚息
			vo.setOverdueFineInterest(actVirVo.getOverDueInterestAmount());			
			//设置逾期违约金
			vo.setOverdueFineAmt(actVirVo.getOverDueFineAmount());
			//当期还款总额
			vo.setPayAmt(actVirVo.getAmt());
			if(actVirVo.getOverDueDays().intValue()>30){
				//高级逾期
				vo.setRepayStatus("3");
			}else{
				//初级逾期
				vo.setRepayStatus("2");
			}
		}else if(actVirVo.getRepayStatus().equals(RepayStatus.一次性提前还款)){
			//本金
			vo.setOncePayPrincipal(actVirVo.getPrincipalAmt());
			//还款总额
			vo.setPayAmt(actVirVo.getAmt());
			if(vo.getPayAmt().compareTo(vo.getCurrShouldPayPrincipe())==1){
				vo.setOnceBreachPenalty(BigDecimalUtil.sub(vo.getPayAmt(), vo.getCurrShouldPayPrincipe()));
			}
			vo.setRepayStatus("4");
			
		}else if(actVirVo.getRepayStatus().equals(RepayStatus.未还款)){
			vo.setEditDate(null);
			LoanRateVO rate = rateCommonUtil.getLoanCoRate(vo.getLoanId());
			vo.setTotalPayAmt(BigDecimalUtil.add(vo.getCurrManagerFee(), BigDecimalUtil.add(vo.getCurrShouldPayPrincipe(),vo.getCurrShouldPayInterest())));
			int term = loanInfo.getLoanDuration().intValue()-loanInfo.getActLedgerLoan().getCurrNum().intValue()+1;
			BigDecimal overdueRate = null;
			if(overDueDays>30){
				overdueRate = rate.getOverdueSeriousInterest();
			}else if(overDueDays>0){
				overdueRate = rate.getOverdueInterest();
			}
			if(overDueDays>0){
				//设置逾期罚息
				vo.setOverdueFineInterest(BusinessCalculateUtils.calOverdueInterest(overDueDays, term, BigDecimalUtil.add(actVirVo.getPrincipalAmt(), actVirVo.getInterestAmt()),  overdueRate));			
				//设置逾期违约金
				vo.setOverdueFineAmt(BusinessCalculateUtils.getOverdueFines(overDueDays, term, loanInfo.getMonthManageCost(), rate.getOverdueFines()));
				//设置逾期本金
				//vo.setOverduePrincipal(actVirVo.getPrincipalAmt());
				//逾期利息
				//vo.setOverdueInterest(actVirVo.getInterestAmt());
				//逾期管理费
				//vo.setOverdueManagerFee(loanInfo.getMonthManageCost());
			}
			
		} 
		repayFlowDetailRepDAO.save(vo);
	
}
	@Transactional(readOnly=false ,propagation = Propagation.REQUIRED)
	private void addNewData(){
			//添加昨天新的放款
			repayFlowDetailRepDAO.scanNewloan(DateUtil.getPreviousDayyyyyMMdd(new Date()));
}

}
