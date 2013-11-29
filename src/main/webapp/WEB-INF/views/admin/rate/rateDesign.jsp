<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<script>
function modifyRate(){
    var row = $('#rateTable').datagrid('getSelections');
		if(row.length>1){
		  $.messager.alert('Warning!','只能选择一行进行编辑！','warning');
		  return false;
		}
		if (row.length>0){
		   $('#rateDlg').dialog('open').dialog('setTitle', '渠道费率修改');
		   window.location='${ctx}/admin/rate/modifyChannlRate?rateId=' + row[0].rateID+'&name1='+row[0].name1+'&name2='+row[0].name2+'&id='+row[0].id;
		}
}
function formatAction(val,row,index){
  return '<a href="javascript:modifyRowRate(\''+index+'\')">修改</a>';
}
function modifyRowRate(index){
      var row = $('#rateTable').datagrid('getSelections',index);
		if (row.length>0){
		   $('#rateDlg').dialog('open').dialog('setTitle', '渠道费率修改');
		   window.location='${ctx}/admin/rate/modifyChannlRate?rateId=' + row[0].rateID+'&name1='+row[0].name1+'&name2='+row[0].name2+'&id='+row[0].id;
		}
}
</script>
</head>
<body>
<table id="rateTable" title="渠道费率设定" class="easyui-datagrid"	rownumbers="true" fitColumns="true" singleSelect="true" 
    url="${ctx}/admin/rate/channelRate" pagination="true" rownumbers="true" toolbar="#toolbar">
	<thead>
		<tr>
		   <th field="ck" checkbox="true" ></th>
			<th field="name1">一级渠道</th>
			<th field="name2" >二级渠道</th>
			<th field="rateName">当前费率</th> 
			<th field="action" title="操作" formatter="formatAction" width="125">操作</th>
		</tr>
	</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<div>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="modifyRate()">修改费率</a>
	</div>
</div>
</body>
</html>
