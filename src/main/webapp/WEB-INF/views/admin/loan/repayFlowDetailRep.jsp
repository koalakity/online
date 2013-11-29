<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>还款明细报表</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	$(function(){
		$('#searchBt').click(function(){
			search();
		});
	});
	//查询
	function search(){
		var queryParams = $('#repayFlowDetail').datagrid('options').queryParams;
		queryParams.realeName=$('#realeName').val();
		queryParams.identityNo=$('#identityNo').val();
		queryParams.phoneNo=$('#phoneNo').val();
		queryParams.loanId=$('#loanId').val();
		queryParams.start=$('#start').datebox('getValue');
		queryParams.end=$('#end').datebox('getValue');
		if(queryParams.end<queryParams.start){
		   $.messager.alert('Warning','开始日期不能大于结束日期!','warning');
		   return false;
		}
		$('#repayFlowDetail').datagrid('options').queryParams = queryParams;
		$('#repayFlowDetail').datagrid('reload');
	}
	
	//导出查询到的数据
	function exportDataByCondition(){
		$('#serarchForm').attr('action','${ctx}/admin/report/exportDataByCondition');
		$('#serarchForm').submit();
	}
	//导出选中记录数据
	function exportDataByIdList(){
		var ids = [];
		var rows = $('#repayFlowDetail').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		
		if(rows==null || rows.length==0){
			alert("没有选中的记录！");
		}
		
		if(rows.length>0){
		  $.messager.confirm('确认','请确认要导出选中信息吗?',function(r){
		     if(r){
		     	$('#serarchForm').attr('action','${ctx}/admin/report/exportDataByIdList');
		     	$('#idList').val(ids);
		     	$('#serarchForm').submit();
		     }
		  })
		}
	}
	//借款用途转换
	function loanUseFormatter(val,row){
		if(val==1){
			return "短期周转";
		}else if(val==2){
			return "教育培训";
		}else if(val==3){
			return "购房借款";
		}else if(val==4){
			return "购车借款";
		}else if(val==5){
			return "装修基金";
		}else if(val==6){
			return "婚礼筹备";
		}else if(val==7){
			return "投资创业";
		}else if(val==8){
			return "医疗支出";
		}else if(val==9){
			return "个人消费";
		}else if(val==10){
			return "其他借款";
		}else{
			return val;
		}
	}
	//还款性质
	function repayStatusFormatter(val,row){
		if(val==0){
			return "未还款";
		}else if(val==1){
			return "正常还款";
		}else if(val==2){
			return "初级逾期还款";
		}else if(val==3){
			return "高级逾期还款";
		}else if(val==4){
			return "一次性提前还款";
		}else if(val==5){
			return "证大垫付";
		}else{
			return val;
		}
	}
	//还款日
	function shouldRepayDayFormatter(val,row){
		if(val==null){
			return "";
		}
		var dt = new Date();
		dt.setTime(val.time);
		return dt.toLocaleString();
	}
	//实际还款日
	function editDateFormatter(val,row){
		if(val==null){
			return "";
		}
		var dt = new Date();
		dt.setTime(val.time);
		return dt.toLocaleString();
	}
	
</script>
</head>
<body>
<table id="repayFlowDetail" title="还款明细报表" class="easyui-datagrid"
			url="${ctx}/admin/report/findByCondition"
			toolbar="#toolbar" pagination="true"
			  singleSelect="false">
	<thead data-options="frozen:true">  
		<tr>  
			<th field="id" checkbox="true"></th>
		</tr>  
	</thead>  
	<thead>
		<tr>
			<th field="channelName" width="100">渠道</th>
			<th field="loanId" width="60" >贷款编号</th>
			<th field="realeName" width="100" >客户姓名</th>
			<th field="identityNo" width="120" >身份证</th>
			<th field="phoneNo" width="80" >手机号码</th>
			<th field="loanUse" formatter="loanUseFormatter" width="60" >借款用途</th>
			<th field="dkzl" width="80" >贷款种类</th>
			<th field="loanAmount" width="100" >借款金额</th>
			<th field="loanDuration" width="60" >借款期限</th>
			<th field="currNum" width="60" >当前期数</th>
			<th field="shouldRepayDay" formatter="shouldRepayDayFormatter" width="170" >本期应还款日</th>
			<th field="totalPayAmt" width="100">本期应还总额</th>
			<th field="currShouldPayPrincipe" width="100">本期应还本金</th>
			<th field="currShouldPayInterest" width="100">本期应还利息</th>
			<th field="shouldPayManagerFee" width="100">应还管理费</th>
			<th field="editDate" formatter="editDateFormatter" width="170">实际还款日</th>
			<th field="payAmt" width="100">实际还款总额</th>
			<th field="repayStatus" formatter="repayStatusFormatter" width="80">还款性质</th>
			<th field="overdueFineInterest" width="100">逾期罚息</th>
			<th field="overdueManagerFee" width="100">逾期管理费</th>
			<th field="overdueInterest" width="100">逾期利息</th>
			<th field="overduePrincipal" width="100">逾期本金</th>
			<th field="overdueFineAmt" width="100">逾期违约金</th>
			<th field="currManagerFee" width="100">当期管理费</th>
			<th field="principal" width="100">当期本金</th>
			<th field="interest" width="100">当期利息</th>
			<th field="oncePayPrincipal" width="100">提前还款本金</th>
			<th field="onceBreachPenalty" width="100">提前还款违约金</th>
		</tr>
	</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<form id="serarchForm" method="post" style="margin-bottom:0;">
		<input type="hidden" id="idList" name="idList"/>
		<div style="margin-bottom:5px">
			<a href="#" onclick="exportDataByCondition()" class="easyui-linkbutton" >导出所有检索结果</a>
			<a href="#" onclick="exportDataByIdList()" class="easyui-linkbutton" >导出选中的检索结果</a>
		</div>
		<div>
			身份证:<input type="text" name="identityNo" id="identityNo" size=16/>
			姓名:<input type="text" name="realeName" id="realeName" size=10/>
			手机号码:<input type="text" name="phoneNo" id="phoneNo" size=16/>
			贷款编号:<input type="text" name="loanId" id="loanId" size=16/>
			还款日期:<input value="${startDate }" id="start" name="start" class="easyui-datebox" style="width:100px" >-<input value="${endDate }" id="end" name="end" class="easyui-datebox" style="width:100px" >
			<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
		</div>
	</form>
</div>
</body>
</html>
