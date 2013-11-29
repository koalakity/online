<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function(){

	//右侧内容tab切换
	$(".account_r_c_t").find("h3").click(function(){
		$.ajaxSetup ({ 
      		cache: false //关闭AJAX相应的缓存 
		});
		var m = $(this).index() + 1;
		var m1 = ".account_r_c_5c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		var loadUrl ="";
		if(m==1){
		loadUrl = "${ctx}/loanManage/loanManage/findMyRepaymentList?pager.offset=0";
		}
		if(m==2){
		loadUrl = "${ctx}/loanManage/loanManage/findMyRepayOffListPage?pager.offset=0";
		}
		$.ajax({
       		 url: loadUrl,
       	 	 type: "POST",
       		 dataType: 'html',
       		 timeout: 10000,
        	 error: function(){
	        },
	       	success: function(data){
	        	$(".account_r_c_5c").html(data);
	       	},
	        beforeSend: function(){
	        }
	     	});
			$(m1).show().siblings().hide();
	});
});
function fenye(url){
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	$(".account_r_c_5c").load(url,function(responseText, textStatus, XMLHttpRequest) {
		$(".account_r_c_5c").html(responseText);
	});
}
</script>
			<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">还款列表</h3>
					<h3>已还清借款</h3>
				</div>
				<div class="account_r_c_5c" >
					<div class="account_r_c_5c1" id="account_r_c_5c1">
						<%@ include file="/WEB-INF/views/loanManage/repaymentList.jsp"%>
					</div>
					<div class="account_r_c_5c2" id="account_r_c_5c2" style="display:none;">
						<%@ include file="/WEB-INF/views/loanManage/repayOffLoanList.jsp"%>
					</div>
				</div>
			</div>
<div class="clear"></div>
