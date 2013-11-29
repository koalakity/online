<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>3</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	color: #666;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
<script type="text/javascript">
	var url;
	function newStaff() {
		$('#dlg').dialog('open').dialog('setTitle', '新建帐号');
		$('#loginName').removeAttr("readonly").removeClass("readonly");
		$('#fm').form('clear');
		$('#roleCombox').combobox({
			url : '${ctx}/admin/findRoles',
			editable:false
		});
		$("input[name=loginStatus][value=1]").attr("checked", true);
		url = '${ctx}/admin/createStaff';
	}
	function editStaff() {
		var row = $('#dg').datagrid('getSelections');
		if(row.length>1){
		  $.messager.alert('Warning!','只能选择一行进行编辑！','warning');
		  return false;
		}
		if (row) {
			$('#loginName').addClass("readonly").attr("readonly", "readonly");
			$('#fm').form('load', row[0]);
			$('#roleCombox').combobox('reload','${ctx}/admin/findRoles?staffId=' + row[0].id);
			$('#dlg').dialog('open').dialog('setTitle', '编辑帐号');
			url = '${ctx}/admin/updateStaff?id=' + row[0].id;
		}
	}
	function saveStaff() {
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				var nodes = $('#functionTree').tree('getChecked');
				var selectedFunctions = [];
				for ( var i = 0; i < nodes.length; i++) {
					selectedFunctions.push(nodes[i].id);
				}
				$('#selectedFunctions').val(selectedFunctions);
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
				} else {
					$.messager.show({
						title : '错误',
						msg : result.msg
					});
				}
			}
		});
	}
	function removeStaff() {
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (rows.length > 0) {
			$.messager.confirm('确认', '确认删除?', function(r) {
				if (r) {
					$.post('${ctx}/admin/removeStaff', {
						staffIds : ids.join(',')
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

	function openStaffs() {
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (rows.length > 0) {
			$.post('${ctx}/admin/openStaffs', {
				staffIds : ids.join(',')
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
	}
	
	function closeStaffs() {
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (rows.length > 0) {
			$.post('${ctx}/admin/closeStaffs', {
				staffIds : ids.join(',')
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
	}
	
</script>
</head>
<body>

	<table id="dg" title="帐号列表" class="easyui-datagrid" url="${ctx}/admin/staffPage" toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="loginName" width="50">用户名</th>
				<th field="name" width="50">姓名</th>
				<th field="roleName" width="50">所属角色</th>
				<th field="loginStatusStr" width="50">状态</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newStaff()">新建</a> <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editStaff()">编辑</a> <!-- <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="removeStaff()">删除</a> --> <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="openStaffs()">开启</a> <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeStaffs()">关闭</a>
	</div>
	<div id="dlg" modal="true" class="easyui-dialog" style="width: 400px; height: 380px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" >
			<div class="fitem">
				<label>用户名:</label> <input id="loginName" name="loginName" class="easyui-validatebox" required="true" validType="length[4,20]">
			</div>
			<div class="fitem">
				<label>密码:</label> <input id="password" type="password" name="loginPassword" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>所属角色:</label> <input id="roleCombox" name="role.id" required="true" class="easyui-combobox" data-options="
				width:100,
		        valueField: 'id',  
		        textField: 'roleName'" />
			</div>

			<div class="fitem">
				<label>状态:</label> <input type="radio" name="loginStatus" value="1" checked />开启 <input type="radio" name="loginStatus" value="0" />关闭
			</div>
			<div class="fitem">
				<label>姓名:</label> <input name="name" class="easyui-validatebox" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>电话:</label> <input name="phone" class="easyui-validatebox">
			</div>
			<div class="fitem">
				<label>邮箱:</label> <input name="email" class="easyui-validatebox" validType="email">
			</div>
			<div class="fitem">
				<label>备注:</label>
				<textarea name="memo" style="height: 60px;" class="easyui-validatebox" validType="length[0,250]"></textarea>
			</div>

		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveStaff()">保存</a> <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

</body>
</html>