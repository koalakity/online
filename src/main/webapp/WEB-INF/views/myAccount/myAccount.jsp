<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>online_account</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" /> 
<script type="text/javascript" src="${ctx}/static/datePicker/WdatePicker.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript" src="${ctx}/static/js/belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix("ul.nav li a, h3, .other_login img, .p_login_btn input, .p_login_btn a, .c_service");
</script>
<![endif]-->
<script type="text/javascript">
$(function(){
	$.ajaxSetup ({
   		cache: false //关闭AJAX相应的缓存 
	});
	//顶部菜单导航切换
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
	//左侧导航切换
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
	//右侧内容tab切换
	$(".account_r_c_t").find("h3").click(function(){
		var m = $(this).index() + 1;
		var m1 = ".account_r_c_c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		$(m1).show().siblings().hide();
	});
	$(".account_l").find("a").click(function(){
		$(".account_l").find("a").removeClass("on");
		$(this).addClass("on");
	});
	if("${strUrl}"!=""&&"${strUrl}"!=null){
		$(".account_r").load("${ctx}"+"${strUrl}",function(responseText, textStatus, XMLHttpRequest) {
			$(".account_r").html(responseText);
		});
		}
});

function loanPage2(loadUrl){ 
	loadUrl += "&random=" + parseInt(100000*Math.random());
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
        if(data&&data=='redirectLogin'){ 
    		location.href='${ctx}/accountLogin/login/redirectLogin';
    	}else{
    		$(".account_r").html(data);
    	}
        	
         },
        beforeSend: function(){
        }
     });
}


function fenye(url){
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	$(".account_r").load(url,function(responseText, textStatus, XMLHttpRequest) {
		$(".account_r").html(responseText);
	});
}
</script>
</head> 
<body> 
<div class="wrapper">

	<div class="content">
		<div class="account_l">
			<dl class="dl_a">
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />我的账户</dt>
				<dd><a href="#" class="a1 on" onclick="loanPage2('${ctx}/homepage/homepage/preUserList?isFrame=true')">我的主页</a></dd>
				<dd><a href="#" class="a2"  onclick="loanPage2('${ctx}/homepage/homepage/showMyApprove?isFrame=true')">我的认证</a></dd>
				<dd><a href="#" class="a3" onclick="loanPage2('${ctx}/message/message/messagePageHead?isFrame=true')">消息管理</a></dd>
			</dl>
			<dl class="dl_b">
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />资金管理</dt>
				<dd><a href="#" class="a1" onclick="loanPage2('${ctx}/fundDetail/fundDetail/showFundDetailHead?isFrame=true')">资金详情</a></dd>
				<dd><a href="#" class="a2" onclick="loanPage2('${ctx}/pay/pay/showPay?isFrame=true&token=${sessionScope.token}')">充值提现</a></dd>
				<!--<dd><a href="#" class="a3">银行账户</a></dd>-->
			</dl>
			<dl class="dl_c">
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />借款管理</dt>
				<dd><a href="#" class="a1" onclick="loanPage2('${ctx}/loanManage/loanManage/showMyRepayment?pager.offset=0&isFrame=true')">偿还借款</a></dd>
				<dd><a href="#" class="a2" onclick="loanPage2('${ctx}/loanManage/loanManage/showLoanInfoListPage?pager.offset=0&isFrame=true')">我的借款</a></dd>
				<%-- <dd><a href="#" class="a3" onclick="loanPage2('${ctx}/loanManage/loanManage/loanStatistics')">借款统计</a></dd> --%>
			</dl>
			<dl class="dl_d">
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />理财管理</dt>
				<dd><a href="#" class="a1" onclick="loanPage2('${ctx}/myAccount/myAccount/showMyInvestment?isFrame=true')">我的投资</a></dd>
				<%-- <dd><a href="#" class="a2" onclick="loanPage2('${ctx}/myAccount/myAccount/showLctj')">理财统计</a></dd> --%>
			</dl>
			<dl class="dl_e">
				<dt><img src="${ctx}/static/images/account_l_dt_a1.gif" />个人设置</dt>
				<dd><a href="#" class="a1" onclick="loanPage2('${ctx}/myAccount/myAccount/showPersonal?isFrame=true')" >修改个人信息</a></dd>
				<dd><a href="#" class="a2" onclick="loanPage2('${ctx}/myAccount/myAccount/modifyPassword?isFrame=true')">修改密码</a></dd>
				<dd><a href="#" class="a3" onclick="loanPage2('${ctx}/personal/personal/showPhoneBind?isFrame=true')">手机绑定</a></dd>
				<dd><a href="#" class="a4" onclick="loanPage2('${ctx}/personal/personal/notifyMsgSet?isFrame=true')">通知设置</a></dd>
			</dl>
		</div>
		<div class="account_r">
		
		
		
		</div>
	</div>
	<div class="clear"></div>

</div>
</body>
</html>