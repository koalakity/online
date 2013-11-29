<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" /> 
<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/checkInput.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript">
	DD_belatedPNG.fix("ul.nav li a, h3, .other_login img, .p_login_btn input, .p_login_btn a");
</script>
<![endif]-->
<script type="text/javascript">
$(function(){
	//右侧内容tab切换
	$(".account_r_c_t").find("h3").click(function(){
			var m = $(this).index() + 1;
				$.ajaxSetup( {
					cache : false
					//关闭AJAX相应的缓存 
				});
				
				if (m == 1) {
					$(".account_r_c_4c").load(
							"${ctx}/personal/personal/personalBase",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".account_r_c_4c").html(responseText);
							});
				}
				if (m == 2) {
					
					$(".account_r_c_4c").load(
							"${ctx}/personal/personal/showEdu",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".account_r_c_4c").html(responseText);
							});
				}
				if (m == 3) {
					$(".account_r_c_4c").load(
							"${ctx}/personal/personal/showFixedAssets",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".account_r_c_4c").html(responseText);
							});
				}
				if (m == 4) {
					$(".account_r_c_4c").load(
							"${ctx}/personal/personal/personalFinance",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".account_r_c_4c").html(responseText);
							});
				}
				if (m == 5) {
					$(".account_r_c_4c").load(
							"${ctx}/personal/personal/showJob",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".account_r_c_4c").html(responseText);
							});
				}
				if (m == 6) {
					$(".account_r_c_4c").load(
							"${ctx}/personal/personal/showPersonalPrivate",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".account_r_c_4c").html(responseText);
							});
				}
		var m1 = ".account_r_c_4c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		$(m1).show().siblings().hide();
	});
});
</script>
<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">基本资料</h3>
					<h3>教育职称</h3>
					<h3>固定资产</h3>
					<h3>财务状况</h3>
					<h3>工作信息</h3>
					<h3>私营业主资料</h3>
				</div>
				<div class="account_r_c_4c">
					<div class="account_r_c_4c1">
					<%@ include file="/WEB-INF/views/personal/personalBase.jsp"%>
					</div>
					<div class="account_r_c_4c2" style="display:none;">
					<%@ include file="/WEB-INF/views/personal/personalEdu.jsp"%>
					</div>
					<div class="account_r_c_4c3" style="display:none;">
					<%@ include file="/WEB-INF/views/personal/personalFixed.jsp"%>
					</div>	
					<div class="account_r_c_4c4" style="display:none;">
					<%@ include file="/WEB-INF/views/personal/personalFinance.jsp"%>
					</div>
					<div class="account_r_c_4c5" style="display:none;">
					<%@ include file="/WEB-INF/views/personal/personalJob.jsp"%>
					</div>
					<div class="account_r_c_4c5" style="display:none;">
					<%@ include file="/WEB-INF/views/personal/personalPrivate.jsp"%>
					</div>													
				</div>
</div>
