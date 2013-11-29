<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<script>
function formatFee(val,row){
    var num =Math.round(parseFloat(val)*10000)/100.00+"%"; 
   return num;
}
function idFee(val,row){
   return val+"元";
}
var message="";
  function addNewRate(){
     $('#rateDlg').dialog('open').dialog('setTitle','新增费率');
		$('#ff').form('clear');
		$('#rateName').removeClass("readonly").removeAttr("readonly", "readonly");
		url = '${ctx}/admin/rate/addRate';
		message ="确认保存？";
  }
  function modifyRate(){
     var row = $('#rateTable').datagrid('getSelections');
		if(row.length>1){
		  $.messager.alert('Warning!','只能选择一行进行编辑！','warning');
		  return false;
		}
		if(row[0].id==1){
		   $.messager.alert('Warning!','默认费率不能进行修改！','warning');
		    return false;
		}
		if (row.length>0){
		   $('#rateDlg').dialog('open').dialog('setTitle','修改费率');
		   $('#rateName').addClass("readonly").attr("readonly", "readonly");
			$('#ff').form('load',row[0]);
			$('#loan').numberbox('setValue',Math.round(parseFloat(row[0].loan)*10000)/100.00);
			$('#reserveFoud').numberbox('setValue',Math.round(parseFloat(row[0].reserveFoud)*10000)/100.00);
			$('#mgmtFee').numberbox('setValue',Math.round(parseFloat(row[0].mgmtFee)*10000)/100.00);
			$('#overdueFines').numberbox('setValue',Math.round(parseFloat(row[0].overdueFines)*10000)/100.00);
			$('#overdueInterest').numberbox('setValue',Math.round(parseFloat(row[0].overdueInterest)*10000)/100.00);
			$('#overdueSeriousInterest').numberbox('setValue',Math.round(parseFloat(row[0].overdueSeriousInterest)*10000)/100.00);
			$('#earlyFines').numberbox('setValue',Math.round(parseFloat(row[0].earlyFines)*10000)/100.00);
			$('#recharge').numberbox('setValue',Math.round(parseFloat(row[0].recharge)*10000)/100.00);
			url = '${ctx}/admin/rate/modifyRate?id=' + row[0].id;
			message ="修改费率将调整所有使用该费率的渠道客户费率，确定要进行修改吗?";
		}
  }
  function deleteRate(){
       var row = $('#rateTable').datagrid('getSelections');
		if(row.length>1){
		  $.messager.alert('Warning!','只能选择一行进行编辑！','warning');
		  return false;
		}
		if(row[0].id==1){
		   $.messager.alert('Warning!','默认费率不能进行删除！','warning');
		    return false;
		}
		if (row.length > 0) {
			$.messager.confirm('确认', '是否删除该费率？', function(r) {
				if (r) {
					$.post('${ctx}/admin/rate/deleteRate?rateId='+row[0].id, function(result) {
						if (result.success) {
							$('#rateTable').datagrid('reload');
						} else {
							$.messager.confirm('提示',result.msg);
						}
					}, 'json');
				}
			});
		}
  }
 
	function formatAction(val,row,index){
	  if(row.id==1){
	    return '';
	  }
		return '<a href="javascript:modifyRowRate(\''+index+'\')">编辑</a>  <a href="javascript:deleteRowRate(\''+row.id+'\')">删除</a>';
	}
	function modifyRowRate(index){
    var row = $('#rateTable').datagrid('getSelections',index);
	if(row[0].id==1){
		   $.messager.alert('Warning!','默认费率不能进行修改！','warning');
		    return false;
		}
		   $('#rateDlg').dialog('open').dialog('setTitle','修改费率');
		   $('#rateName').addClass("readonly").attr("readonly", "readonly");
			$('#ff').form('load',row[0]);
			$('#loan').numberbox('setValue',Math.round(parseFloat(row[0].loan)*10000)/100.00);
			$('#reserveFoud').numberbox('setValue',Math.round(parseFloat(row[0].reserveFoud)*10000)/100.00);
			$('#mgmtFee').numberbox('setValue',Math.round(parseFloat(row[0].mgmtFee)*10000)/100.00);
			$('#overdueFines').numberbox('setValue',Math.round(parseFloat(row[0].overdueFines)*10000)/100.00);
			$('#overdueInterest').numberbox('setValue',Math.round(parseFloat(row[0].overdueInterest)*10000)/100.00);
			$('#overdueSeriousInterest').numberbox('setValue',Math.round(parseFloat(row[0].overdueSeriousInterest)*10000)/100.00);
			$('#earlyFines').numberbox('setValue',Math.round(parseFloat(row[0].earlyFines)*10000)/100.00);
			$('#recharge').numberbox('setValue',Math.round(parseFloat(row[0].recharge)*10000)/100.00);
			url = '${ctx}/admin/rate/modifyRate?id=' + row[0].id;
			message ="修改费率将调整所有使用该费率的渠道客户费率，确定要进行修改吗?";
	}
	function deleteRowRate(rowId){
	if(rowId==1){
		   $.messager.alert('Warning!','默认费率不能进行删除！','warning');
		    return false;
		}
	  $.messager.confirm('确认', '是否删除该费率？', function(r) {
				if (r) {
					$.post('${ctx}/admin/rate/deleteRate?rateId='+rowId, function(result) {
						if (result.success) {
							$('#rateTable').datagrid('reload');
						} else {
							$.messager.confirm('提示',result.msg);
						}
					}, 'json');
				}
			});
	}
  function saveRate(){
  $.messager.confirm('确认', message, function(r) {
   if(r){
     $('#ff').form('submit',{
			url: url,
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
				$.messager.alert('消息',result.msg,'info');
					$('#rateDlg').dialog('close');		// close the dialog
					$('#rateTable').datagrid('reload');	// reload the user data
				} else {
					$.messager.show({
						title: '错误',
						msg: result.msg
					});
				}
			}
		});
   }
  })
    
  }
</script>
</head>
<body>
<table id="rateTable" title="费率列表" class="easyui-datagrid"	rownumbers="true" fitColumns="true" singleSelect="true" 
   toolbar="#toolbar" url="${ctx}/admin/rate/rateInfo" pagination="true" rownumbers="true">
	<thead>
		<tr>
		    <th field="ck" checkbox="true" ></th>
			<th field="rateName">费率名称</th>
			<th field="loan" formatter="formatFee">借款管理费</th>
			<th field="reserveFoud" formatter="formatFee">风险基金</th>
			<th field="mgmtFee"  formatter="formatFee">月缴管理费</th>
			<th field="overdueFines" formatter="formatFee">逾期违约金</th>
			<th field="overdueInterest" formatter="formatFee" >逾期罚息(<=30天)</th>
			<th field="overdueSeriousInterest" formatter="formatFee">逾期罚息(>30天)</th>
			<th field="earlyFines" formatter="formatFee">一次性提前还款违约金</th>
			<th field="recharge" formatter="formatFee">充值手续费</th>
			<th field="idFee" formatter="idFee" >ID5验证手续费</th> 
			<th field="action" title="操作" formatter="formatAction" width="125">操作</th>
		</tr>
	</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<div>
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addNewRate()">新增费率</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="modifyRate()">修改费率</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="deleteRate()">删除</a>
		<span style="float:right;">注：提现手续费为固定费用：2万以下1元/笔  2-5万3元/笔  5万1元/笔
		</span>
	</div>
</div>
<div id="rateDlg"  modal="true"  class="easyui-dialog" style="width:400px;height:480px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
   <span>说明：如费率为百分之一，则在文本框内填写数字1即可，无需填写百分号（%），且费率不可为负。</span>
		<form id="ff" method="post">
			<table>
				<tr>
					<td>*费率名称:</td>
					<td><input id="rateName" name="rateName"  class="easyui-validatebox" required="true" type="text"></input></td>
				</tr>
				<tr>
					<td>*借款管理费:</td>
					<td><input id="loan" name="loan" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*风险基金:</td>
					<td><input id="reserveFoud" name="reserveFoud" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*月缴管理费</td>
					<td><input id="mgmtFee" name="mgmtFee" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*逾期违约金</td>
					<td><input id="overdueFines" name="overdueFines" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*逾期罚息(<=30天)</td>
					<td><input id="overdueInterest" name="overdueInterest" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*逾期罚息(>30天)</td>
					<td><input id="overdueSeriousInterest" name="overdueSeriousInterest" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*一次性提前还款违约金</td>
					<td><input id="earlyFines" name="earlyFines" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*充值手续费</td>
					<td><input id="recharge" name="recharge" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>%</span></td>
				</tr>
				<tr>
					<td>*证件实名认证手续费</td>
					<td><input id="idFee" name="idFee" type="text" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required:true"></input><span>元</span></td>
				</tr>
				  <tr>
		    <td>提现手续费:</td>
		     <td>2万以下1元/笔</td>
		  </tr>
		  <tr>
		    <td></td>
		     <td> 2-5万 3元/笔</td>
		  </tr>
		  <tr>
		    <td></td>
		     <td> 5万 5元/笔</td>
		  </tr>
			</table>
		</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRate()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#rateDlg').dialog('close')">取消</a>
</div>
</body>
</html>
