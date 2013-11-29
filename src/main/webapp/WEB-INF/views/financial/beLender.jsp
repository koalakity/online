<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/login.css" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><!--[if lte IE 6]>
<script type="text/javascript" src="js/belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix("ul.nav li a, h3, .other_login img, .p_login_btn input, .p_login_btn a");
</script>
<![endif]-->
<title>成为理财人</title>
<script type="text/javascript">
    $(function(){
    
    	//顶部菜单导航切换
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
    });
  
</script> 
	<div class="content login_bg4">
		<h3 class="prompt8"><img src="${ctx}/static/images/img90.jpg" />&nbsp;&nbsp;&nbsp;恭喜您，您已成为借出者！</h3>
		<ul>
			<li class="li1">
				<a href="${ctx}/financial/searchLoan/showLoan" class="btn4">查看借款列表</a>
				<span class="font_16 bold1 col2">查看借款列表，寻找投资机会</span><br />
				<span class="font_14 bold1 col3">您可以得到借款列表，寻找投资机会，您的资金，不仅将获得稳定回报，同时也将帮助借入者。</span>
			</li>
			<li class="li2">
				<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay" class="btn4">在线充值</a>
				<span class="font_16 bold1 col2">充值资金，体验资金充值乐趣</span><br />
				<span class="font_14 bold1 col3">您可以充值一定的投标保证金，参与证大e贷的新兴理财方式，赚取稳定高额的投资回报。</span>
			</li>
		</ul>		
	</div>