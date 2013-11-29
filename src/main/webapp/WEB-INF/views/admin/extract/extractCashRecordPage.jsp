<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>3</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
$(function(){
var url;
var gridTitle="sssss";
var columnC = [[
    {field:'extractId',title:'提现编号'},
    {field:'realName',title:'申请人',formatter:formatterUserDetailForTakeMoney},
    {field:'extractAmountFormatt',title:'提现金额'},
    {field:'extractCostFormatt',title:'提现手续费'},
	{field:'availabelMoneyFomatt',title:'可用余额'},
	{field:'kaihuName',title:'开户名'},
	{field:'bankName',title:'开户行'},
	{field:'bankCardNo',title:'银行卡号'},
	{field:'extractTimeFormatt',title:'申请时间'},
	{field:'action',title:'操作',formatter:function(value,rowData){
		url='${ctx}/admin/extract/extractCashRecordDetailPage?extractId='+rowData.extractId+'&verifyStatus='+${status};
		return '<a href="'+url+'"  >查看详情</a>';
		}
	}
]];
var columnSucc = [[
    {field:'extractId',title:'提现编号'},
    {field:'realName',title:'申请人',formatter:formatterUserDetailForTakeMoney},
    {field:'extractAmountFormatt',title:'提现金额'},
    {field:'extractCostFormatt',title:'提现手续费'},
	{field:'extractTimeFormatt',title:'申请时间'},
	{field:'action',title:'操作',formatter:function(value,rowData){
		url='${ctx}/admin/extract/extractCashRecordDetailPage?extractId='+rowData.extractId+'&verifyStatus='+${status};
		return '<a href="'+url+'"  >查看详情</a>';
		}
	}
]];
var column=columnC;
var extractStatus=(${status});
		if(extractStatus==0){
			gridTitle="新申请提现列表";
			 column=columnC;
		}
		if(extractStatus==1){
			gridTitle="处理中提现列表";
			 column=columnC;
		}
		if(extractStatus==2){
			gridTitle="成功提现列表";
			 column=columnSucc;
		}
		if(extractStatus==3){
			gridTitle="失败提现列表";
			 column=columnSucc;
		}
$('#searchBt').click(function(){
			search();
		});

$('#extracList').datagrid({
						title:gridTitle,
						url:'${ctx}/admin/extract/extractCashRecordPage?verifyStatus='+${status},
						fitColumns:true,
						singleSelect:true,
						columns:column,
						onResize:function(){
							//$('#table1').datagrid('fixDetailRowHeight',index);
						},
						onLoadSuccess:function(){
							setTimeout(function(){
								//$('#table1').datagrid('fixDetailRowHeight',index);
							},0);
						}
					});
					});
					
					
	
	function search(){
	
		var queryParams = $('#extracList').datagrid('options').queryParams;
		queryParams.extractId = $('#extractId').val();
		queryParams.realName = $('#realName').val();
		queryParams.extractAmountFrom = $('#extractAmountFrom').val();
		queryParams.extractAmountTo = $('#extractAmountTo').val();
		$('#extracList').datagrid('options').queryParams = queryParams;
		$("#extracList").datagrid('reload');
	}
	
	function formatterOperation(val, row) {
		url='${ctx}/admin/extract/extractCashRecordDetailPage'
		return '<a href="'+url+'" target="_blank" >查看详情</a>';
	}
	
	
</script>
</head>
<body>
	<table id="extracList" 
		   class="easyui-datagrid" 
		   toolbar="#toolbar" 
		   pagination="true" 
		   rownumbers="true" 
		   fitColumns="true" 
		   singleSelect="false">
	</table>
	<div id="toolbar" style="height:auto; padding:5px;">
		<form id="serarchForm" method="post" style="margin:0;">
			<table style="font-size:12px;">
				<tr>
					<td>提现编号:&nbsp;</td>
					<td><input type="text" name="extractId" id="extractId" size=16/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>申请人:&nbsp;</td>
					<td><input type="text" name="realName" id="realName" size=16/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>提现金额:&nbsp;<input type="text" name="extractAmountFrom" id="extractAmountFrom" size=5/>--<input type="text" name="extractAmountTo" id="extractAmountTo" size=5/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:search()">搜索</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>