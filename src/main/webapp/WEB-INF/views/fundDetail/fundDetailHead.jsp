<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" /> 

<!--[if lte IE 6]>
<script type="text/javascript" src="${ctx}/static/js/belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix("ul.nav li a, h3, .other_login img, .p_login_btn input, .p_login_btn a");
</script>
<![endif]-->
<link rel="stylesheet" href="${ctx}/static/css/revision.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript">
function queryFlow()
{
	var date_start=$("#date_start").val()+"";
	var date_end=$("#date_end").val()+""; 
	var type_fund=$("#type_fund").val()+"";
	var url="${ctx}/fundDetail/fundDetail/showFundFlow?isFrame=true&date_start="+date_start+"&date_end="+date_end+"&type_fund="+type_fund;
	//var url="${ctx}/fundDetail/fundDetail/showFundFlow";
	//alert();
	$(".account_r_c_3c").load(
	url,
	function(responseText, textStatus, XMLHttpRequest) {
		if(responseText=='redirectLogin'){
			window.location.href='${pageContext.request.contextPath}/accountLogin/login/redirectLogin';
		}else{
			$(".account_r_c_3c").html(responseText);
		}
		
	});
	
	//type_fund=${fundFlowVO.type_fund}+"";
	//date_start=${fundFlowVO.date_start}+"";
	//date_end=${fundFlowVO.date_end}+"";
	
	//date_start=date_start.substring(0,4)+"-"+date_start.substring(4,6)+"-"+date_start.substring(6,8);
	//date_end=date_end.substring(0,4)+"-"+date_end.substring(4,6)+"-"+date_end.substring(6,8);
	
	//$("#type_fund").val(type_fund);
	//$("#date_start").val(date_start);
	//$("#date_end").val(date_end);
}


//搜索分页
function searchFenye(url){
	var date_start=$("#date_start").val()+"";
	var date_end=$("#date_end").val()+""; 
	var type_fund=$("#type_fund").val()+"";
	url = url+"&date_start="+date_start+"&date_end="+date_end+"&type_fund="+type_fund;

	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	$(".account_r_c_3c").load(
	url,
	function(responseText, textStatus, XMLHttpRequest) {
		$(".account_r_c_3c").html(responseText);
	});
	
}
$(function(){
	//右侧内容tab切换
	$(".account_r_c_t").find("h3").click(function(){
	   
		var m = $(this).index() + 1;
		var m1 = ".account_r_c_3c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		var loadUrl ="";
		if(m==1){
		loadUrl = "${ctx}/fundDetail/fundDetail/showFundDetail";
		}
		if(m==2){
		//loadUrl = "${ctx}/fundDetail/fundDetail/showFundFlow";
		var date_start=$("#date_start").val();
		var date_end=$("#date_end").val(); 
		var type_fund=$("#type_fund").val();
		loadUrl="${ctx}/fundDetail/fundDetail/showFundFlow?date_start="+date_start+"&date_end="+date_end+"&type_fund="+type_fund;
		}
		
		$.ajax({
       		 url: loadUrl,
       	 	 type: "POST",
       		 dataType: 'html',
       		 timeout: 10000,
        	 error: function(){
	        },
	       	success: function(data){
	        	$(".account_r_c_3c").html(data);
	       	},
	        beforeSend: function(){
	        }
	     	});
			$(m1).show().siblings().hide();
	});

});


</script>

			<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">资金详情</h3>
					<h3 >资金流水</h3>
				</div>
				<div class="account_r_c_3c">
					<div class="account_r_c_3c1">
						<%@ include file="/WEB-INF/views/fundDetail/fundDetail.jsp"%>						
					</div>
					<div class="account_r_c_3c2" style="display:none;">
						<%@ include file="/WEB-INF/views/fundDetail/fundFlow.jsp"%>
				</div>
			</div>
		</div>