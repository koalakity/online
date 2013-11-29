<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>操作日志</title>
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
	$(function(){
		$('#searchBt').click(function(){
			search();
		});
	});
	function formatterdate(val, row) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+ ' ' + date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
	}
	function search(){
		var queryParams = $('#dg').datagrid('options').queryParams;
		queryParams.staffName=$('#staffName').val();
		queryParams.operateKind=$('#operateKind').combobox('getValue');
		queryParams.operateTimeMin = $('#operateTimeMin').datebox('getValue');
		queryParams.operateTimeMax = $('#operateTimeMax').datebox('getValue');
		$('#dg').datagrid('options').queryParams = queryParams;
		$("#dg").datagrid('reload');
	}
</script>
</head>
<body>
	<table id="dg" title="操作日志" class="easyui-datagrid" url="${ctx}/admin/logPage" toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="staffName" width="50">操作人</th>
				<th field="operateKind" width="50">操作类型</th>
				<th field="operateObjectId" width="50">操作对象ID</th>
				<th field="operateContent" width="50">操作内容</th>
				<th field="ipAdd" width="50">客户端IP</th>
				<th field="operateTime" width="50" formatter="formatterdate">操作时间</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding:5px;height:auto">
		<div>
		    操作人：<input id="staffName" name="staffName" type="text" style="width:120px"/>
		    操作类型：<select id="operateKind" name="operateKind" class="easyui-combobox" editable="false">
						<option value="">全部</option>
						<option value="新增">新增</option>
						<option value="删除">删除</option>
						<option value="修改">修改</option>
						<option value="导出">导出</option>
						<option value="推荐">推荐</option>
						<option value="不推荐">不推荐</option>
						<option value="登录">登录</option>
						<option value="锁定">锁定</option>
						<option value="解锁">解锁</option>
						<option value="开启">开启</option>
						<option value="关闭">关闭</option>
						<option value="审核">审核</option>
						<option value="调账">调账</option>
						<option value="初始化">初始化</option>
					  </select>
			操作时间: <input id="operateTimeMin" name="operateTimeMin" class="easyui-datebox" style="width:100px" editable="false">-<input id="operateTimeMax" name="operateTimeMax" class="easyui-datebox" style="width:100px" editable="false">
			<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:search()">查询</a>
		</div>
	</div>
</body>
</html>