<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>bbs</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css" type="text/css" />
<script type="text/javascript">
$(function(){
	//顶部菜单导航切换
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
});
</script>
</head>
<body>
<div class="wrapper">
	<div class="content login_bg4">
		<h2 style="height:300px; text-align:center; font-weight:normal; color:red; margin-top:100px;">证大e贷论坛正在维护中，对您造成的不便，敬请谅解！</h2>		
	</div>
	<div class="clear"></div>
</div>
</body>
</html>