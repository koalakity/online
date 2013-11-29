<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/news.css" type="text/css" /> 
<!--[if lte IE 6]>
<script type="text/javascript" src="js/belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix("ul.nav li a, h3, .other_login img, .p_login_btn input, .p_login_btn a");
</script>
<![endif]-->
<script type="text/javascript">
$(function(){
	//顶部菜单导航切换
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
	//顶部菜单导航切换
	$(".account_l").find("dt").click(function(){
		var m = $(this).siblings("dd");
		var m1 = $(this).find("img");
		if(m.is(":hidden")){
			m1.attr("src","images/account_l_dt_a1.gif");
	        m.slideDown("800");
		}else{
			m1.attr("src","images/account_l_dt_a2.gif");
		    m.slideUp("800");
		};
	});
});
</script>
	<div class="content">
		<div class="account_r_c account_r_c_add">
			<div class="news_t"><a href="${ctx}">首页</a> > <a href="${ctx}/footerDetail/footerDetail/showStoryList?type=22">微金融故事</a> > 正文</div>
			<div class="news_c2">
				<h3>${storyPage.title }</h3>
				<div class="news_detail">
				${storyPage.content }
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
