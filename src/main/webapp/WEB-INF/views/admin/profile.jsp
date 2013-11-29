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
	var url = '${ctx}/admin/updateStaff?id=' + ${staff.id};
	$(function(){
		$('#loginName').addClass("readonly").attr("readonly", "readonly");
		$('input[name=loginStatus]').addClass("readonly").attr("readonly", "readonly");
	});
	 
	function saveStaff() {
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$.messager.alert('消息','更新成功!','info');
				} else {
					$.messager.show({
						title : '错误',
						msg : result.msg
					});
				}
			}
		});
	}
	 
</script>
</head>
<body>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>用户名:</label> <input id="loginName" name="loginName" class="easyui-validatebox" required="true" validType="length[4,20]" value="${staff.loginName}">
			</div>
			<div class="fitem">
				<label>密码:</label> <input id="password" type="password" name="loginPassword" class="easyui-validatebox" required="true" value="${staff.loginPassword}">
			</div>
			<div class="fitem">
				<label>所属角色:</label> <input type="hidden" name="role.id" value="${staff.role.id }"/> ${staff.roleName }
			</div>

			<div class="fitem">
				<label>状态:</label> <input type="hidden" name="loginStatus" value="${staff.loginStatus }" /><c:if test="${staff.loginStatus==1}">开启 </c:if>   <c:if test="${staff.loginStatus==0}">关闭 </c:if>
			</div>
			<div class="fitem">
				<label>姓名:</label> <input name="name" class="easyui-validatebox" value="${staff.name}">
			</div>
			<div class="fitem">
				<label>电话:</label> <input name="phone" class="easyui-validatebox" value="${staff.phone}">
			</div>
			<div class="fitem">
				<label>邮箱:</label> <input name="email" class="easyui-validatebox" validType="email" value="${staff.email}">
			</div>
			<div class="fitem">
				<label>备注:</label>
				<textarea name="memo" style="height: 60px;" class="easyui-validatebox" validType="length[0,250]">${staff.memo}</textarea>
			</div>

		</form>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveStaff()">保存</a>
	</div>
</body>
</html>