<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>内容</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script language="javascript" type="text/javascript"
			src="${ctx}/static/ckeditor/ckeditor.js" mce_src="${ctx}/static/ckeditor/ckeditor.js">
</script>
<script language="javascript" type="text/javascript"
			src="${ctx}/static/ckeditor/config.js" mce_src="${ctx}/static/ckeditor/config.js">
</script>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 10px;
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
	width: 60px;
}
</style>
<script type="text/javascript">
	$(function(){
		 CKEDITOR.replace("content");
		$('#dg').datagrid({
			onDblClickRow : function(rowIndex, rowData) {
				editNotice();
			},
			onSelect : function() {
				enableEditablButton();
			},
			onUnselect:function(){
				var rows = $('#dg').datagrid('getSelections');
				if (rows.length ==0){
					disableEditablButton();
				}
				else{
					enableEditablButton();
				}
			},
			onUnselectAll:function(){
				disableEditablButton();
			},
			onCheckAll:function(){
				var rows = $('#dg').datagrid('getSelections');
				if (rows.length ==0){
					disableEditablButton();
				}
				else{
					enableEditablButton();
				}
			},
			onUncheck:function(){
				var rows = $('#dg').datagrid('getSelections');
				if (rows.length ==0){
					disableEditablButton();
				}
				else{
					enableEditablButton();
				}
			}
		});
	});
	
	function disableEditablButton(){
		$('#editBt').linkbutton({
			disabled : true
		});
		$('#removeBt').linkbutton({
			disabled : true
		});
		$('#commendBt').linkbutton({
			disabled : true
		});
		$('#unCommendBt').linkbutton({
			disabled : true
		});
	}
	
	function enableEditablButton(){
		$('#editBt').linkbutton({
			disabled : false
		});
		$('#removeBt').linkbutton({
			disabled : false
		});
		$('#commendBt').linkbutton({
			disabled : false
		});
		$('#unCommendBt').linkbutton({
			disabled : false
		});
	}
	
	
	var url;
	function newNotice() {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		CKEDITOR.instances.content.setData("");
		$("input[name=isCommend][value=0]").attr("checked", true);
		url = '${ctx}/admin/site/createNotice';
	}
	function editNotice() {
		var row = $('#dg').datagrid('getSelections');
		if(row.length>1){
		  $.messager.alert('Warning!','只能选择一行进行编辑！','warning');
		  return false;
		}
		if (row.length>0) {
			$('#fm').form('load', row[0]);
			if(row[0].isCommend==1){
				$("input[name=isCommend][value=1]").attr("checked", true);
			}
			else{
				$("input[name=isCommend][value=0]").attr("checked", true);
			}
			CKEDITOR.instances.content.setData(row[0].content);
			$('#dlg').dialog('open').dialog('setTitle', '编辑');
			url = '${ctx}/admin/site/updateNotice?id=' + row[0].id;
		}
	}
	function saveNotice() {
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				$('#type').val(${type});
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
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
	function removeNotice() {
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (rows.length > 0) {
			$.messager.confirm('确认', '确认删除?', function(r) {
				if (r) {
					$.post('${ctx}/admin/site/removeNotices', {
						noticeIds : ids.join(',')
					}, function(result) {
						if (result.success) {
							$('#dg').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
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

	function commendNotices() {
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (rows.length > 0) {
			$.post('${ctx}/admin/site/commendNotices', {
				noticeIds : ids.join(',')
			}, function(result) {
				if (result.success) {
				$('#dg').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
					//$('#dg').datagrid('uncheckAll');
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
	
	function unCommendNotices() {
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		if (rows.length > 0) {
			$.post('${ctx}/admin/site/unCommendNotices', {
				noticeIds : ids.join(',')
			}, function(result) {
				if (result.success) {
					$('#dg').parent().find("div.datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);
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
	
	function formatterdate(val, row) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+ ' ' + date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
	}
</script>
</head>
<body>
	<table id="dg" title="内容列表" class="easyui-datagrid" url="${ctx}/admin/site/noticePage?type=${type}" toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<!-- <th field="loginName" width="50">图片</th> -->
				<th field="title" width="50">标题</th>
				<th field="isCommendStr" width="50">是否推荐</th>
				<th field="staffName" width="50">创建人</th>
				<th field="creDate" width="50" formatter="formatterdate">创建时间</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newNotice()">新建</a> <a href="#" id="editBt" disabled="true" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editNotice()">编辑</a> <a href="#" id="removeBt" disabled="true"  class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="removeNotice()">删除</a> <a href="#" id="commendBt" disabled="true"  class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="commendNotices()">推荐</a> <a href="#" id="unCommendBt" disabled="true"  class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="unCommendNotices()">不推荐</a>
	</div>
	<div id="dlg" modal="true" class="easyui-dialog" style="width: 920px; height: 520px; padding: 10px" maximized="true" closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" >
			<input id="type" name="type" type="hidden"></input>
			<div class="fitem">
				<label>标题名称:</label><input id="title" name="title" class="easyui-validatebox" required="true" validType="length[0,100]">
			</div>
			<div class="fitem">
				<label>排序:</label><input id="orders" name="orders" value="1" class="easyui-numberspinner" data-options="min:0,max:100,editable: false">
			</div>

			<div class="fitem">
				<label>是否推荐:</label><input type="radio" name="isCommend" value="0"/>不推荐 <input type="radio" name="isCommend" value="1" />推荐
			</div>
			<div class="fitem" style="float:left;">
				<label>详细:</label>
			</div>
			<div class="fitem" style="float:left;">
				<textarea  id="content" name="content"></textarea>
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveNotice()">保存</a> <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>