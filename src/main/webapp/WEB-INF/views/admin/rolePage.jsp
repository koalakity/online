<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>3</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	var url;
	function newRole(){
		$('#dlg').dialog('open').dialog('setTitle','新建角色');
		$('#fm').form('clear');
		$('#functionTree').tree({
				cascadeCheck:true,
				url:'${ctx}/admin/findFunctions'
			});
		$('#functionTree').tree('reload');
		url = '${ctx}/admin/createRole';
	}
	function editRole(){
		var row = $('#dg').datagrid('getSelections');
		if(row.length>1){
		  $.messager.alert('Warning!','只能选择一行进行编辑！','warning');
		  return false;
		}
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','编辑角色');
			$('#fm').form('load',row[0]);
			$('#functionTree').tree({
				cascadeCheck:false,
				url:'${ctx}/admin/findUsedFunctions?roleId='+row[0].id
			});
			url = '${ctx}/admin/updateRole?id='+row[0].id;
		}
	}
	function saveRole(){
		$('#fm').form('submit',{
			url: url,
			onSubmit: function(){
				var nodes = $('#functionTree').tree('getChecked');
			    var selectedFunctions = [];
			    if(nodes.length==0){
			      $.messager.alert('Warning','权限设置必选!','warning');
			      return false;
			    }
			    for (var i = 0; i < nodes.length; i++) {
			        selectedFunctions.push(nodes[i].id);
			    }
			    $('#selectedFunctions').val(selectedFunctions);
				return $(this).form('validate');
			},
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
					$('#dlg').dialog('close');		// close the dialog
					$('#dg').datagrid('reload');	// reload the user data
				} else {
					$.messager.show({
						title: '错误',
						msg: result.msg
					});
				}
			}
		});
	}
	
	function removeRole() {
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (rows.length > 0) {
			$.messager.confirm('确认', '确认删除?', function(r) {
				if (r) {
					$.post('${ctx}/admin/removeRole', {
						ids : ids.join(',')
					}, function(result) {
						if (result.success) {
							$('#dg').datagrid('reload'); // reload the user data
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : result.msg
							});
						}
					}, 'json');
				}
			});
		}
	}
	
</script>
</head>
<body>
<table id="dg" title="角色列表" class="easyui-datagrid"
			url="${ctx}/admin/rolePage"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="false">
	<thead>
		<tr>
			<th field="ck" checkbox="true"></th>
			<th field="roleName" width="50">角色名称</th>
			<th field="remarks" width="50">备注</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRole()">新建</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole()">编辑</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRole()">删除</a>
</div>
<div id="dlg" modal="true" class="easyui-dialog" style="width:400px;height:400px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post" novalidate>
	<table>
		<tr>
			<td>*角色名称：</td>
			<td><input type="text" class="easyui-validatebox" required="true" name="roleName"/></td>
		</tr>
		<tr>
			<td align="right" valign="top">备注：</td>
			<td><textarea style="width:200px; height:48px; font-size:12px;" name="remarks"></textarea></td>
		</tr>
		<tr>
			<td valign="top">*权限设置：</td>
			<td><input type="hidden" name="selectedFunctions" id="selectedFunctions"/>
				<ul id="functionTree" class="easyui-tree" 
						checkbox="true">
				<span>全选</span>
				</ul>
			</td>
		</tr>
	</table>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRole()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

	
</body>
</html>