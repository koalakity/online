<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<title>邮箱验证失败</title>
		<link rel="stylesheet" href="${ctx}/static/css/login.css" type="text/css" />
	</head>
	<body>
<div class="wrapper">
	<div class="lost_psd">
		
		<table>
			<tr><td class="font_14 col1 tc">${showMsg}</td></tr>
		</table>
	</div>
	<div class="clear"></div>
</div>
	</body>
</html>
