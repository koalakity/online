<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

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
			m1.attr("src","${ctx}/static/images/account_l_dt_a1.gif");
	        m.slideDown("800");
		}else{
			m1.attr("src","${ctx}/static/images/account_l_dt_a2.gif");
		    m.slideUp("800");
		};
	});
	$(".account_l").find("a").click(function(){
		$(".account_l").find("a").removeClass("on");
		$(this).addClass("on");
	});	
	if("${twoGradePage}"!=null){
        for (var i = 1; i < 19; i++) {
            $("#load_page_" + i).children().each(function(){
                $("#load_page_" + i).children().removeClass();
            });
        }
		$("#load_page_"+"${twoGradePage.id}").addClass("on");
		}
});
function loadPage(loadUrl){
	loadUrl=encodeURI(loadUrl);
	$.ajaxSetup ({
   		cache: false //关闭AJAX相应的缓存 
	});
	 $.ajax({
        url: loadUrl,
        type: "POST",
        dataType: 'html',
        timeout: 10000,
        error: function(){
         },
        success: function(data){
        	$(".account_r").html(data);
         },
        beforeSend: function(){
        }
     });
}
</script>
	<div class="content">
		<div class="account_l">
			<dl>
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />使用帮助
			</dt>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=1&category=首页&sort=使用帮助&c_sort=常见问题')" id="load_page_1">常见问题</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=2&category=首页&sort=使用帮助&c_sort=平台原理')" id="load_page_2" >平台原理</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=4&category=首页&sort=使用帮助&c_sort=如何借款')" id="load_page_4">如何借款</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=5&category=首页&sort=使用帮助&c_sort=如何理财')" id="load_page_5">如何理财</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=6&category=首页&sort=使用帮助&c_sort=使用技巧')" id="load_page_6">使用技巧</a></dd>
			</dl>
			<dl>
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />安全保障</dt>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=7&category=首页&sort=安全保障&c_sort=风险金代偿')" id="load_page_7">风险金代偿</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=8&category=首页&sort=安全保障&c_sort=风险管控')" id="load_page_8">风险管控</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=9&category=首页&sort=安全保障&c_sort=交易安全保障')" id="load_page_9">交易安全保障</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=10&category=首页&sort=安全保障&c_sort=网络安全保障')" id="load_page_10">网络安全保障</a></dd>
			</dl>
			<dl>
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />资费说明</dt>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=11&category=首页&sort=资费说明&c_sort=收费标准')" id="load_page_11">收费标准</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=12&category=首页&sort=资费说明&c_sort=提现标准')" id="load_page_12">提现标准</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=13&category=首页&sort=资费说明&c_sort=代理收费')" id="load_page_13">代理收费</a></dd>
			</dl>
			<dl>
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />关于我们</dt>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=14&category=首页&sort=关于我们&c_sort=公司简介')" id="load_page_14">公司简介</a></dd>
				<dd><a href="${ctx}/footerDetail/footerDetail/showWordList?type=21"  id="load_page_14">媒体报道</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=15&category=首页&sort=关于我们&c_sort=专家顾问')" id="load_page_15">专家顾问</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=16&category=首页&sort=关于我们&c_sort=合作伙伴')" id="load_page_16">合作伙伴</a></dd>
			</dl>
			<dl>
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />联系我们</dt>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=17&category=首页&sort=联系我们&c_sort=证大微博')" id="load_page_17">证大微博</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=17&category=首页&sort=联系我们&c_sort=证大博客')" id="load_page_17">证大博客</a></dd>
				<dd><a href="#" onclick="loadPage('${ctx}/footerDetail/footerDetail/loadTwoGradePage?id=17&category=首页&sort=联系我们&c_sort=在线客服')" id="load_page_17">在线客服</a></dd>
			</dl>
		</div>
		<div class="account_r">
			<%@ include file="/WEB-INF/views/footerDetail/footerDetailChild.jsp"%>
		</div>
	</div>
	<div class="clear"></div>
	
