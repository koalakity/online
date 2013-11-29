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
<script type="text/javascript">
$(function(){
	//右侧内容tab切换
	$(".account_r_c_t").find("h3").click(function(){
	
		var m = $(this).index() + 1;
		var m1 = ".account_r_c_3c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		var loadUrl ="";
		if(m==1){
		   loadUrl = "${ctx}/pay/pay/showPayRecharge?token=${sessionScope.token}&isFrame=true";
		}
		if(m==2){
		   loadUrl = "${ctx}/pay/pay/showPayWithdraw?token=${sessionScope.token}&isFrame=true";
		}
			 $.ajax({
       		 url: loadUrl,
       	 	 type: "POST",
       		 dataType: 'html',
       		 timeout: 10000,
        	 error: function(){
	        },
	       	success: function(data){
	       	if(data&&data=='redirectLogin'){ 
    		location.href='${ctx}/accountLogin/login/redirectLogin';
	    	}else{
	    		$(".account_r_c_3c").html(data);
	    	}
	       	
	        	
	       	},
	        beforeSend: function(){
	        }
	     	});
			$(m1).show().siblings().hide();
	});
	//提现中银行列表显示隐藏切换
	$(".select_bank").click(function(){
		$(".sub_table").slideToggle();
		return false;
	});
});
</script>
			<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">充值</h3>
					<h3>提现</h3>
				</div>
				<div class="account_r_c_3c">
					<div class="account_r_c_3c1">
						<%@ include file="/WEB-INF/views/pay/payRecharge.jsp"%>				
					</div>
					<div class="account_r_c_3c2" style="display:none;">
						<%@ include file="/WEB-INF/views/pay/payWithdraw.jsp"%>				
					</div>
				</div>
			</div>
