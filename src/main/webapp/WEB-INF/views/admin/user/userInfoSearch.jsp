<%@ page language="java" import="java.util.*,com.zendaimoney.online.common.TypeConstants" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员信息查看</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
$(function(){
	$("#tt").tabs({
		  onSelect: function(title){
		  	if(title=="旧版流水展示"){
		  		$('#oldFundFlowTbl').datagrid({
		  		    url:'${ctx}/admin/user/oldFundFlow?userId=${vo1.userId}'
		  		});
		  		$("#oldFundFlowTbl").url='${ctx}/admin/user/oldFundFlow?userId=${vo1.userId}'
		  		$("#oldFundFlowTbl").datagrid('reload');
		  		}
		  }
	});	
});

  function dateFormat(val,row){
       var date = new Date(parseInt(val));
       var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
       var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	   var hour=date.getHours()< 10 ? "0" + date.getHours() : date.getHours();
	   var mints=date.getMinutes()< 10 ? "0" + date.getMinutes() : date.getMinutes();
	   var sec=date.getSeconds()< 10 ? "0" + date.getSeconds() :date.getSeconds();
	//date.getFullYear() + "-" + month + "-" + currentDate+ " " +hour+ ":" + mints+ ":" +sec;
    return date.getFullYear() + "-" + month + "-" + currentDate+" "+hour+":"+mints;

    }
    function memoFormatt(val,row){
		if(val.length>26){
			var innerValue = val.substring(0, 25)+'......'; 
			return '<a id="tip" title="'+val+'">'+innerValue+'</a>';
		}else {
			return val;
		}
	}
	
	function usersearch(){
		var queryParams = $('#fundFlowTbl').datagrid('options').queryParams;
		queryParams.type= $('#type').combobox('getValue');
		queryParams.start = $('#start').datebox('getValue');
		queryParams.end = $('#end').datebox('getValue');
		if(queryParams.end<queryParams.start){
		   $.messager.alert('Warning','开始日期不能大于结束日期!','warning');
		   return false;
		}
		
		$('#fundFlowTbl').datagrid('options').queryParams = queryParams;
		$("#fundFlowTbl").datagrid('reload');
	}
	//导出查询数据
	function exportExcelFundFlowTbl(userId){
		$.messager.confirm('确认', '确认要导出查询数据吗?', function(r) {
				if (r) {
					var action='${ctx}/admin/user/exportExcelFundFlowTbl?userId='+userId;
					
					var type= $('#type').combobox('getValue');
					var start = $('#start').datebox('getValue');
					var end = $('#end').datebox('getValue');
					if (end < start) {
						$.messager.alert('Warning', '开始日期不能大于结束日期!', 'warning');
						return false;
					}
					if(type!=''){
						action=action+'&type='+type;
					}
					if(start!=''){
						action=action+'&start='+start;
					}
					if(end!=''){
						action=action+'&end='+end;
					}
					$('#fundFlowTblForm').attr('action',action);
					$('#fundFlowTblForm').submit();
				}
			})
	}
	
	//旧流水查询，导出数据
	function exportExcelOldFundFlow(userId){
		$.messager.confirm('确认', '确认要导出查询数据吗?', function(r) {
				if (r) {
					var action='${ctx}/admin/user/exportExcelOldFundFlow?userId='+userId;
					
					var start = $('#oldFlowSstart').datebox('getValue');
					var end = $('#oldFlowEnd').datebox('getValue');
					if (end < start) {
						$.messager.alert('Warning', '开始日期不能大于结束日期!', 'warning');
						return false;
					}
					if(start!=''){
						action=action+'&start='+start;
					}
					if(end!=''){
						action=action+'&end='+end;
					}
					$('#fundFlowTblForm').attr('action',action);
					$('#fundFlowTblForm').submit();
				}
			})
	} 
	

	function oldFlowSearch() {
		var queryParams = $('#oldFundFlowTbl').datagrid('options').queryParams;
		queryParams.start = $('#oldFlowSstart').datebox('getValue');
		queryParams.end = $('#oldFlowEnd').datebox('getValue');
		if (queryParams.end < queryParams.start) {
			$.messager.alert('Warning', '开始日期不能大于结束日期!', 'warning');
			return false;
		}

		$('#oldFundFlowTbl').datagrid('options').queryParams = queryParams;
		$("#oldFundFlowTbl").datagrid('reload');
	}

	//备注
	function formatCode(val, row) {
		if (row.type == 2) {
			return '充值单号：' + val + '<br/>';
		} else if (row.type == 1) {
			return '提现单号：' + val + '<br/>';
		} else if (row.type == 3) {
			return '借款编号：<a href="${ctx}/admin/loan/loanRepayingDetailPage?loanId='
					+ val + '">' + val + '</a><br/>';
		} else if (row.type == 4) {
			return '借款编号：<a href="${ctx}/admin/loan/loanRepayingDetailPage?loanId='
					+ val + '">' + val + '</a><br/>';
		} else if (row.type == 5) {
			return '借款编号：<a href="${ctx}/admin/loan/loanRepayingDetailPage?loanId='
					+ val + '">' + val + '</a><br/>';
		}
	};
</script>
</head>
<body>
<div>
  <div><table><tr><td style="width:80px">用户昵称：</td><td style="width:80px">${vo1.loginName}</td><td style="width:80px">（姓名：</td><td style="width:80px">${vo1.realName}）</td></tr></table></div>
  <form id="fmUserInfo" method="post" novalidate >
   <div>
    <table class="m_defined" style="width:100%;" pagination="true" >
		<tr>
		  <th colspan="6">资金基本信息</th></tr>
		<tr>
		   <td style="width:80px">账户余额</td><td><input value="${vo1.zhye}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		   <td style="width:100px">可用余额</td><td><input value="${vo1.kyye}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		   <td style="width:80px">冻结总额</td><td><input value="${vo1.djzy}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		</tr>
		<tr>
		  <td>信用额度</td><td><input value="${vo1.xyed}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		  <td>已使用信用额度</td><td><input value="${vo1.usedAmount}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		  <td>可用额度</td><td><input value="${vo1.kyed}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td></tr>
		<tr>
		  <td>成功借入总额</td><td><input value="${vo1.loanTotle}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		  <td>成功借款笔数</td><td><input value="${vo1.successLoanNum}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		  <td>逾期总期数</td><td><input value="${vo1.overdueCount}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td></tr>
		<tr><td>成功借出总额</td><td><input value="${vo1.zjcje}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		<td>成功投标笔数</td><td><input value="${vo1.successLoanOutNum}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td>
		<td>待收本息</td><td><input value="${vo1.dhbx}" style="width:100px;border:0;background:#FFFFFF;color:#000" disabled="true"/></td></tr>
	</table>
    </div>
  </form>
	<!-- new 资金流水 start-->
	<form id="fundFlowTblForm" name="fundFlowTblForm" action="" method="post">
		<div class="easyui-tabs" id="tt">
		  <div title="资金流水">
		<div id="toolbar1">&nbsp;资金类型：
		<select id="type" class="easyui-combobox" editable="false">
			<option value ="<%=TypeConstants.SY %>"><%=TypeConstants.getValueByType(TypeConstants.SY) %></option>
	        <option value ="<%=TypeConstants.TX %>"><%=TypeConstants.getValueByType(TypeConstants.TX) %></option>
	        <option value ="<%=TypeConstants.CZ %>"><%=TypeConstants.getValueByType(TypeConstants.CZ) %></option>
	        <option value ="<%=TypeConstants.ZJHS %>"><%=TypeConstants.getValueByType(TypeConstants.ZJHS) %></option>
	        <option value ="<%=TypeConstants.JKCG %>"><%=TypeConstants.getValueByType(TypeConstants.JKCG) %></option>
	        <option value ="<%=TypeConstants.XTJY%>"><%=TypeConstants.getValueByType(TypeConstants.XTJY) %></option>
	        <option value ="<%=TypeConstants.SFYZ %>"><%=TypeConstants.getValueByType(TypeConstants.SFYZ) %></option>
	        <option value ="<%=TypeConstants.TZ %>"><%=TypeConstants.getValueByType(TypeConstants.TZ) %></option>
		</select>
		&nbsp;起止日期：
		<input class="easyui-datebox" id="start"/>-<input class="easyui-datebox" id="end"/>
		&nbsp;<a href="#" class="easyui-linkbutton" onclick="javascript:usersearch()">查询</a>
		&nbsp;<a href="#" class="easyui-linkbutton" onclick="javascript:exportExcelFundFlowTbl('${vo1.userId}')">导出查询结果</a></div>
		<table id="fundFlowTbl" toolbar="#toolbar1" class="easyui-datagrid" url='${ctx}/admin/user/userInfoFundFlow?userId=${vo1.userId}' pagination="true" rownumbers="true"   singleSelect="false">
	       <thead> 
			 <tr>
				<th field="date" width="150px" >时间</th>
				<th field="tradeType" width="120px">类型</th>
				<th field="in" width="120px">存入</th>
				<th field="out" width="120px">支出</th>
				<th field="amount" width="150px">结余</th>
				<th field="outUserName" width="150px">支出方昵称</th>
				<th field="inUserName" width="150px">收入方昵称</th>
				<th data-options="field:'code',width:150,formatter:formatCode">备注</th>
			 </tr>
		  </thead>
	      </table>
	    </div>
	    <div title="旧版流水展示">
	    		<div id="toolbar2">
		    	&nbsp;起止日期：<input class="easyui-datebox" id="oldFlowSstart"/>-<input class="easyui-datebox" id="oldFlowEnd"/>
				&nbsp;<a href="#" class="easyui-linkbutton" onclick="javascript:oldFlowSearch()">查询</a>
				&nbsp;<a href="#" class="easyui-linkbutton" onclick="javascript:exportExcelOldFundFlow('${vo1.userId}')">导出查询结果</a>
				</div>
				<table id="oldFundFlowTbl" toolbar="#toolbar2" class="easyui-datagrid" url='' pagination="true" rownumbers="true"   singleSelect="false">
	       <thead> 
			 <tr>
				<th field="date" width="150px" >时间</th>
				<th field="type" width="120px">类型</th>
				<th field="in" width="120px">存入</th>
				<th field="out" width="120px">支出</th>
	
				
				<th field="code" width="150px">备注</th>
				<!-- <th field="amount" width="120px">结余</th> 
				<th field="amount" width="150px">结余</th>
				<th field="outUserName" width="150px">支出方昵称</th>
				<th field="inUserName" width="150px">收入方昵称</th>-->
	
			 </tr>
		  </thead>
	      </table>
		    </div>
   </form>    
    </div>
   </div>

	<!-- new 资金流水 end-->
      <div style="height:15px; overflow:hidden;"></div>
    <div>
     <table nowrap="false" id="memoTbl" title="备注记录" class="easyui-datagrid"  
     		pagination="true" url='${ctx}/admin/user/userInfoMemoRecd?userId=${vo1.userId}'
			rownumbers="true" fitColumns="true" singleSelect="false">
       <thead>
		 <tr>
			<th field="memoText" width="450" >备注记录</th>
			<th field="name" >操作人</th>
			<th field="operateTime" width="120" formatter="dateFormat">操作时间</th>
		 </tr>
	  </thead>
      </table>
    </div>
    <div style="height:15px; overflow:hidden;"></div>
    <div>
      <table id="loanHisLateTbl" title="历史逾期" class="easyui-datagrid"  pagination="true" url='${ctx}/admin/user/userInfoHisLate?userId=${vo1.userId}'
			rownumbers="true" fitColumns="true" singleSelect="false">
       <thead>
		 <tr>
			<th field="loanId" width="100px">借款编号</th>
			<th field="repayLoanDate" width="150px">还款日期</th>
			<th field="principanInterestMonth" >月还本息</th>
			<th field="overdueDays" >逾期天数</th>
			<th field="overdueInterest" >逾期罚息</th>
			<th field="overdueFines" width="70">逾期违约金</th>
			<th field="repayLoanStatus" >状态</th>
		 </tr>
	  </thead>
      </table>
    </div>
</div> 
</body>
</html>
