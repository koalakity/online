package com.zendaimoney.online.admin.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.admin.service.FlowService;
import com.zendaimoney.online.admin.service.LedgerService;
import com.zendaimoney.online.admin.vo.AjaxResult;
import com.zendaimoney.online.admin.vo.UserListForm;
import com.zendaimoney.online.admin.web.DTO.ActFlowDTO;
import com.zendaimoney.online.common.BigDecimalUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.common.ZendaiAccountBank;
import com.zendaimoney.online.dao.common.SqlStatement;

@Controller
@RequestMapping("/admin/ledger")
public class LedgerController {

	@Autowired
	private LedgerService ledgerService;

	@Autowired
	private FlowService flowService;

	/**
	 * 网站资金账户页面，余额，收入和支出信息显示
			其他的对应不同账户
	 * 2013-3-8 下午1:36:22 by HuYaHui
	 * @param model
	 * @return
	 */
	@RequestMapping("siteMoneyJsp")
	public String siteMoneyJsp(Model model) {
		List<SqlStatement> param=new ArrayList<SqlStatement>();
		List<Object> valueList=new ArrayList<Object>();
		//借款手续费
    	valueList.add(TradeTypeConstants.FKFEE);
    	//充值手续费
    	valueList.add(TradeTypeConstants.CZSXF);
    	//ID5验证
    	valueList.add(TradeTypeConstants.ID5);
    	//提现手续费(成功)
    	valueList.add(TradeTypeConstants.TXSXF);
    	//提现手续费(失败)
    	valueList.add(TradeTypeConstants.TXSXFSB);
    	//月缴管理费
    	valueList.add(TradeTypeConstants.ZCHKCHGLF);
    	valueList.add(TradeTypeConstants.CJYQCHGLF);
    	valueList.add(TradeTypeConstants.GJYQCHGLF);
    	valueList.add(TradeTypeConstants.DCGJYQCHGLF);
    	valueList.add(TradeTypeConstants.YCXDQGLF);
    	//逾期违约金
    	valueList.add(TradeTypeConstants.CJYQCHYQGLF);
    	valueList.add(TradeTypeConstants.GJYQCHYQGLF);
    	valueList.add(TradeTypeConstants.DCGJYQCHYQGLF);
    	//逾期罚息
    	valueList.add(TradeTypeConstants.DCGJYQCHYQFX);
    	param.add(new SqlStatement("and","tradeType","in",valueList));

    	BigDecimal output = flowService.calculateOutAmount(param);		
		BigDecimal incoming = flowService.calculateInAmount(param);
		model.addAttribute("siteIncomming", ObjectFormatUtil.formatCurrency(incoming.doubleValue()));
		model.addAttribute("siteOutput", ObjectFormatUtil.formatCurrency(output.doubleValue()));
		model.addAttribute("siteBalance", BigDecimalUtil.formatCurrencyIgnoreSymbol(BigDecimalUtil.sub(incoming, output)));
		
		String[] accarys=new String[]{
				ZendaiAccountBank.zendai_acct1,ZendaiAccountBank.zendai_acct2,
				ZendaiAccountBank.zendai_acct3,ZendaiAccountBank.zendai_acct6,
				ZendaiAccountBank.zendai_acct7,ZendaiAccountBank.zendai_acct9,ZendaiAccountBank.zendai_acct11};
		model.addAttribute("accountList",ledgerService.getAccountList(accarys));
		return "admin/ledger/siteMoney";
	}

	/**
	 * 网站资金列表查询
		其他的对应不同账户
	 * 2013-3-8 上午11:50:22 by HuYaHui
	 * @param userListForm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("siteFlowPage")
	@ResponseBody
	public String siteFlowPage(UserListForm userListForm, Integer page, Integer rows) {
		
		List<SqlStatement> param=new ArrayList<SqlStatement>();
		List<Object> valueList=new ArrayList<Object>();
		//借款手续费
    	valueList.add(TradeTypeConstants.FKFEE);
    	//充值手续费
    	valueList.add(TradeTypeConstants.CZSXF);
    	//ID5验证
    	valueList.add(TradeTypeConstants.ID5);
    	//提现手续费(成功)
    	valueList.add(TradeTypeConstants.TXSXF);
    	//提现手续费(失败)
    	valueList.add(TradeTypeConstants.TXSXFSB);
    	//月缴管理费
    	valueList.add(TradeTypeConstants.ZCHKCHGLF);
    	valueList.add(TradeTypeConstants.CJYQCHGLF);
    	valueList.add(TradeTypeConstants.GJYQCHGLF);
    	valueList.add(TradeTypeConstants.DCGJYQCHGLF);
    	valueList.add(TradeTypeConstants.YCXDQGLF);
    	//逾期违约金
    	valueList.add(TradeTypeConstants.CJYQCHYQGLF);
    	valueList.add(TradeTypeConstants.GJYQCHYQGLF);
    	valueList.add(TradeTypeConstants.DCGJYQCHYQGLF);
    	//逾期罚息
    	valueList.add(TradeTypeConstants.DCGJYQCHYQFX);
    	param.add(new SqlStatement("and","tradeType","in",valueList));

		
    	//查询以上交易类型到证大账户的记录总数
		long count=flowService.countByCondition(param);
		List<ActFlowDTO> list=flowService.findRiskFlowsNew(param, page, rows);
		JSONArray array=JSONArray.fromObject(list);
		JSONObject json=new JSONObject();
		json.put("total", count);
		json.put("rows", array);
		return json.toString();
	}

	/**
	 * 风险金账户页面，余额，收入和支出信息显示
 		 * 如果tradeType是以下类型，页面显示风险准备金
		 * 	操作科目：风险金代偿（利息）tradeType：43 出
			操作科目：风险金代偿（本金）tradeType：42出
			操作科目：调账  tradeType：9出
	
			操作科目：交易手续费 tradeType：5入
			操作科目：偿还本金/回收本金tradeType：29入
			操作科目：偿还利息/回收利息tradeType：30入
			
		其他的对应不同账户
	 * 2013-3-7 上午11:43:00 by HuYaHui
	 * @param model
	 * @return
	 */
	@RequestMapping("riskMoneyJsp")
	public String riskMoneyJsp(Model model) {
		List<SqlStatement> param=new ArrayList<SqlStatement>();
    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.FXJDCLX));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.FXJDCBJ));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.TZ));
    	BigDecimal output = flowService.calculateOutAmount(param);
		
		param=new ArrayList<SqlStatement>();
    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.FKRESERVE));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.DCGJYQCHBJ));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.DCGJYQCHLX));
		BigDecimal incoming = flowService.calculateInAmount(param);
		
		model.addAttribute("siteIncomming", ObjectFormatUtil.formatCurrency(incoming.doubleValue()));
		model.addAttribute("siteOutput", ObjectFormatUtil.formatCurrency(output.doubleValue()));
		model.addAttribute("siteBalance", BigDecimalUtil.formatCurrencyIgnoreSymbol(BigDecimalUtil.sub(incoming, output)));
		String[] accarys=new String[]{ZendaiAccountBank.zendai_acct10};
		//获取证大的调账账户列表
		model.addAttribute("accountList",ledgerService.getAccountList(accarys));
		return "admin/ledger/riskMoney";
	}

	/**
	 * 风险金账户页面列表信息
 		 * 如果tradeType是以下类型，页面显示风险准备金
		 * 	操作科目：风险金代偿（利息）tradeType：43 出
			操作科目：风险金代偿（本金）tradeType：42出
			操作科目：调账  tradeType：9出
	
			操作科目：交易手续费 tradeType：6入
			操作科目：偿还本金/回收本金tradeType：29入
			操作科目：偿还利息/回收利息tradeType：30入
			
		其他的对应不同账户
	 * 2013-3-7 下午3:41:50 by HuYaHui
	 * @param userListForm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("riskFlowPage")
	@ResponseBody
	public String riskFlowPage(UserListForm userListForm, Integer page, Integer rows) {
    	List<SqlStatement> param=new ArrayList<SqlStatement>();
    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.FXJDCLX));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.FXJDCBJ));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.TZ));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.FKRESERVE));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.DCGJYQCHBJ));
    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.DCGJYQCHLX));		
		long count=flowService.countByCondition(param);
		List<ActFlowDTO> list=flowService.findRiskFlowsNew(param, page, rows);
		JSONArray array=JSONArray.fromObject(list);
		JSONObject json=new JSONObject();
		json.put("total", count);
		json.put("rows", array);
		return json.toString();
	}

	/**
	 * 风险金调账
	 * 2013-3-8 下午1:36:49 by HuYaHui
	 * @param payAmt
	 * @param receiverEmail
	 * @param memo
	 * @param account
	 * @return
	 */
	@RequestMapping("doPayRisk")
	@ResponseBody
	public AjaxResult doPayRisk(Double payAmt, String receiverEmail, String memo,String account) {
		ledgerService.doPayRisk(payAmt, receiverEmail, memo,account);
		return new AjaxResult();
	}

	/**
	 * 资金账户调账
	 * 2013-3-8 下午1:37:00 by HuYaHui
	 * @param payAmt
	 * @param receiverEmail
	 * @param memo
	 * @param account
	 * @return
	 */
	@RequestMapping("doPay")
	@ResponseBody
	public AjaxResult doPay(Double payAmt, String receiverEmail, String memo,String account) {
		ledgerService.doPay(payAmt, receiverEmail, memo,account);
		return new AjaxResult();
	}
	@ExceptionHandler
	@ResponseBody
	public AjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}
}
