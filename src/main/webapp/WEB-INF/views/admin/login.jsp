<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>login</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css">
.login { width:300px; height:170px; margin:200px auto 0; padding:35px 0 0 425px; background:url(${ctx}/static/admin/css/images/login_logo.jpg) no-repeat; font-size:14px;}
table td { height:30px;}
.td1 { font-size:12px; color:red;}
.btn { width:71px; height:30px; border:none; background:url(${ctx}/static/admin/css/images/btn.jpg) no-repeat; text-align:center; line-height:30px; font-weight:bold; color:#fff; cursor:pointer;}
.btn:hover { background-position:0 -30px;}
.footer { position:absolute; bottom:0; left:0; width:100%; height:60px; line-height:60px; font-size:12px; text-align:center;}
</style>
<script language="JavaScript"> 
	if (window != top) 
		top.location.href = location.href; 
</script>
</head>
<body>
 
	<div class="login">
		<form:form id="inputForm" modelAttribute="user" action="doLogin" method="post"  target="_top" >
			<table>
				<tr><td width="70" align="right">账号：</td><td colspan="2" width="230"><input type="text" name="username" style="width:150px;" /></td></tr>
				<tr><td align="right">密码：</td><td colspan="2"><input type="password" name="password" style="width:150px;" /></td></tr>
				<c:if test="${not empty error}">
					<tr><td>&nbsp;</td><td class="td1" colspan="2">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</td></tr>
				</c:if>
				<tr><td>&nbsp;</td><td colspan="2"><input type="submit" value="登录" class="btn" /></td></tr>
			</table>
		</form:form>
	</div>
	<div class="footer">© 2012 证大财富 All rights reserved</div>
</body>
</html>