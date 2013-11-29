<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link rel="stylesheet" href="css/login.css" type="text/css" /> 
<title>注册成功</title>
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
		<h3 class="prompt8"><img src="${ctx}/static/images/img90.jpg" />&nbsp;&nbsp;&nbsp;恭喜您，您已注册成功！</h3>
		<ul>
			<li class="li1">
				<a href="${ctx}/financial/searchLoan/showLoan" class="btn4">查看借款列表</a>
				<span class="font_16 bold1 col2">查看借款列表，寻找投资机会</span><br />
				<span class="font_14 bold1 col3">您可以得到借款列表，寻找投资机会，您的资金，不仅将获得稳定回报，同时也将帮助借入者。</span>
			</li>
			<li class="li2">
				<a href="${ctx}/myAccount/myAccount/showMyAccount" class="btn4">在线充值</a>
				<span class="font_16 bold1 col2">充值资金，体验资金充值乐趣</span><br />
				<span class="font_14 bold1 col3">您可以充值一定的投标保证金，参与证大e贷的新兴理财方式，赚取稳定高额的投资回报。</span>
			</li>
			<li class="li3">
				<a href="${ctx}/borrowing/releaseLoan/show" class="btn4">发布借款申请</a>
				<span class="font_16 bold1 col2">发布借款申请，快速周转资金</span><br />
				<span class="font_14 bold1 col3">可以发布借款申请，快速周转资金，3-7个工作日资金即可到位，以解燃眉之急。</span>
			</li>
		</ul>		
	</div>
	<div class="clear"></div>
</div>
</body>
</html>