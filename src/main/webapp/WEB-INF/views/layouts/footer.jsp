<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/bdtj.js"></script>

<script type="text/javascript">
$(function(){
 	$.ajax({
  		 url: "${ctx}/getFooter",
  	 	 type: "POST",
  		 dataType: 'html',
  		 timeout: 10000,
   	 	error: function(data){
       },
      	success: function(data){
   	 	$(".bottom_box").html(data);
      	},
       beforeSend: function(){
       }
	});
	$(".c_service_open").click(function(){
		$(".c_service").hide();
		$(".c_service_close").show();
	});
	$(".c_service_close").click(function(){
		$(this).hide();
		$(".c_service").show();
	});
})


var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-37911368-1']);
_gaq.push(['_trackPageview']);

(function() {
  var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
  ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();

</script>
	<div class="bottom">
	 <%@ include file="/WEB-INF/views/layouts/footerChild.jsp"%>
	</div>
	<div class="footer">
	    <div class="footer_box">
			<img src="${ctx}/static/images/f_logo.jpg" />
		    © 2012证大财富 All rights reserved <a href="http://www.miibeian.gov.cn/" target="_blank">沪ICP备11047396号</a><br />
			联系我们：edaiservice@zendaimoney.com  商务合作：pr@zendaimoney.com  证大e贷微金融服务平台
		</div>
	</div>
	<div class="c_service" style="line-height:1em;">
	<p class="c_service_open"></p>
	 免费服务热线<br />
	<strong>400-821-6888</strong><br />
	<a class="weibo" href="http://weibo.com/onlinecredit" target="_blank" style="margin-left:28px; line-height:4.2em">新浪官方微博</a>
	</div>
	<div class="c_service_close" style="display:none;">
	
	</div>
