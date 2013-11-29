<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>借款向导</title>
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
	<div class="content">
		<div class="lead_box">
			<div class="lead_l">
				<p>您可以在证大e贷发布借款需求列表，并获得理财人的资金支持。</p>
				<a href="${ctx }/borrowing/borrowing/publishLoanJsp" class="btn4 mar0">发布借款</a>
			</div>
			<div class="lead_c"></div>
			<div class="lead_r">
				<p>您可以在证大e贷申请信用额度，这将使您能在同一时间获得更多的借款。</p>
				<a href="${ctx}/borrowing/borrowing/showApprove" class="btn4 mar0">申请信用额度</a>
			</div>
		</div>
	</div>
<div class="clear"></div>
</div>
</body>
</html>