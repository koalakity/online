<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>auditing</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	var exam = [
		{value:'通过'},
		{value:'未通过'}
	];
	$(function(){
		$('#table1').datagrid({
			onBeforeEdit:function(index,row){
				row.editing = true;
				updateActions();
			},
			onAfterEdit:function(index,row){
				row.editing = false;
				updateActions();
			},
			onCancelEdit:function(index,row){
				row.editing = false;
				updateActions();
			}
		});
	});
	function updateActions(){
		var rowcount = $('#table1').datagrid('getRows').length;
		for(var i=0; i<rowcount; i++){
			$('#table1').datagrid('updateRow',{
				index:i,
				row:{action:''}
			});
		}
	}
	function editrow(index){
		$('#table1').datagrid('beginEdit', index);
	}
	function saverow(index){
		$('#table1').datagrid('endEdit', index);
	}
	function cancelrow(index){
		$('#table1').datagrid('cancelEdit', index);
	}
	function formatAction(value,row,index){
		if (row.editing){
			var s = '<a href="#" onclick="saverow('+index+')">保存</a> ';
			var c = '<a href="#" onclick="cancelrow('+index+')">取消</a>';
			return s+c;
		} else {
			var e = '<a href="#" onclick="editrow('+index+')">编辑</a> ';
			return e;
		}
	}
	function formatField3(val,row){
		if(val!=""){
			return '<a href="'+val+'.html" target="_blank">'+val+'</a>';
		}
	}
	function confirm1(){
		$.messager.confirm('确定', '审核通过后此信息将进入初定列表，您确定要提交吗？');
	}
	function prompt1(){
		$.messager.prompt('驳回', '驳回后此信息将进入驳回列表，请填写驳回原因：');
	}
</script>
</head>
<body>
	<table id="table1" class="easyui-datagrid" url="json/datagrid2_data.json" title="信息认证" fitColumns="true" style="width:1000px;">
		<thead>
			<tr>
				<th field="field1" width="30">认证类型</th>
				<th field="field2" width="60">认证项目</th>
				<th field="field3" width="60" formatter="formatField3">附件</th>
				<th field="field4" width="60" editor="{type:'numberbox'}">信用分数</th>
				<th field="field5" width="60" editor="{type:'combobox',options:{valueField:'value',textField:'value',data:exam,required:true}}">审核</th>
				<th field="field6" width="60">审核时间</th>
				<th field="action" width="20" formatter="formatAction">操作</th>
			</tr>
		</thead>
	</table>
	<div style="width:1000px; margin:15px 0; text-align:center;">
		<a class="easyui-linkbutton" onclick="confirm1();">审核通过</a>&nbsp;&nbsp;<a class="easyui-linkbutton" onclick="prompt1();">驳回</a>&nbsp;&nbsp;<a href="#" class="easyui-linkbutton">重置</a>&nbsp;&nbsp;<a href="#" class="easyui-linkbutton">保存并返回</a>
	</div>
	<table class="easyui-datagrid" url="json/datagrid3_data.json" title="备注" fitColumns="true" toolbar="#toolbar" style="width:1000px;">
		<thead>
			<tr>
				<th field="field1" width="80">备注内容</th>
				<th field="field2" width="80">操作人</th>
				<th field="field3" width="80">操作时间</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#d1').dialog('open')">添加备注</a>
	</div>
	<div style="height:15px; overflow:hidden;"></div>
	<table class="easyui-datagrid" url="json/datagrid4_data.json" title="退回" fitColumns="true" style="width:1000px;">
		<thead>
			<tr>
				<th field="field1" width="80">操作节点</th>
				<th field="field2" width="80">退回原因</th>
				<th field="field3" width="80">操作人</th>
				<th field="field4" width="80">操作时间</th>
			</tr>
		</thead>
	</table>
	<div style="height:15px; overflow:hidden;"></div>
	<table id="table1" class="easyui-datagrid" url="json/datagrid5_data.json" title="驳回" fitColumns="true" style="width:1000px;">
		<thead>
			<tr>
				<th field="field1" width="80">操作节点</th>
				<th field="field2" width="80">驳回原因</th>
				<th field="field3" width="80">操作人</th>
				<th field="field4" width="80">操作时间</th>
			</tr>
		</thead>
	</table>
	<div id="d1" modal="true" class="easyui-dialog" title="添加备注" closed="true" style="padding:5px; width:400px; height:200px;" data-options="buttons:'#dlg-buttons'">
		<textarea style="width:370px; height:110px; font-size:12px;" onfocus="if(this.value=='不超过500个汉字！')this.value=''" onblur="if(this.value=='')this.value='不超过500个汉字！'">不超过500个汉字！</textarea>
	</div>
	<div id="dlg-buttons">
		<a class="easyui-linkbutton" onclick="javascript:$('#d1').dialog('close')">提交</a>
		<a  class="easyui-linkbutton" onclick="javascript:$('#d1').dialog('close')">取消</a>
	</div>
</body>
</html>