<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="com.zendaimoney.online.entity.account.AccountUsers"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>index</title>
<link rel="stylesheet" href="${ctx}/static/css/message.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/static/css/index.css" type="text/css" /> 
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="${ctx}/static/js/slides.min.jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/js/email.js"></script>

<script type="text/javascript">
$(function(){
	//顶部菜单导航切换
    $(".nav").find("a").mouseover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
    $(".nav_box").bind("mouseleave",function(){
		$(".sub_nav").find("span").hide();	
	});
	//邮箱补全
	var inputSuggest = new InputSuggest({
		input: document.getElementById('loginAccount'),
		data: ['sina.com','163.com','qq.com','126.com','sohu.com','yahoo.com','hotmail.com','gmail.com','139.com','189.com']
	});
	//登录框tab切换
	$(".login_box_t").find("p").click(function(){
		var m = $(this).index();
		$(this).addClass("on").siblings().removeClass("on");
		$(".login_box").eq(m).show().siblings().hide();
	});
	//借款tab切换+滚动调用
	var m3 = $("#loan_table1");
	var m4 = $("#loan_table2");
	var scrollTimer0;
	var scrollTimer1;
	$.ajaxSetup ({ 
  		cache: false //关闭AJAX相应的缓存 
	});
 	$.ajax({
  		 url: "${ctx}/footerDetail/footerDetail/loadLoanList?status=1",
  	 	 type: "POST",
  		 dataType: 'html',
  		 timeout: 10000,
   	 error: function(){
   },
  	success: function(data){
		if(data.indexOf("tr")==-1){
			$(".loan_list_t").find("h3").eq(1).click();
		}else{
			m3.html(data);
			m3.show();
			m4.hide();
		};
  	},
   beforeSend: function(){
   }
	});
	$(".loan_list_t").find("h3").eq(0).click(function(){
		$(this).addClass("on").siblings("h3").removeClass("on");
		$(this).siblings("a").hide().eq(0).show();
		$(".loan_list_c").find("table").eq(0).show().siblings().hide();
		$.ajaxSetup ({ 
      		cache: false //关闭AJAX相应的缓存 
		});
	 	$.ajax({
      		 url: "${ctx}/footerDetail/footerDetail/loadLoanList?status=1",
      	 	 type: "POST",
      		 dataType: 'html',
      		 timeout: 10000,
       	 error: function(){
       },
      	success: function(data){
			if(data.indexOf("tr")==-1){
				m3.html("<div style='margin-top:50px;'><img style='display:block; margin:0 auto;' src='${ctx}/static/images/loanover.jpg' /><a href='${ctx}/financial/searchLoan/showLoan?tabFlg=3' style='display:block; width:127px; height:35px; margin:0 auto; background:url(${ctx}/static/images/loanover_btn.png) no-repeat;'></a></div>");
			}else{
				m3.html(data);
			};
			m3.show();
			m4.hide();
      	},
       beforeSend: function(){
       }
    	});
		clearInterval(scrollTimer0);
		clearInterval(scrollTimer1);
		scrollTimer0 = setInterval(function(){scrollLoan(m3)},3000);
	});
	$(".loan_list_t").find("h3").eq(1).click(function(){
		$(this).addClass("on").siblings("h3").removeClass("on");
		$(this).siblings("a").hide().eq(1).show();
		$.ajaxSetup ({ 
      		cache: false //关闭AJAX相应的缓存 
		});
	 	$.ajax({
      		 url: "${ctx}/footerDetail/footerDetail/loadLoanList?status=2",
      	 	 type: "POST",
      		 dataType: 'html',
      		 timeout: 10000,
       	 error: function(){
       	},
      	success: function(data){
       	m4.html(data);
       	m4.show();
       	m3.hide();
      	},
       beforeSend: function(){
       }
    	});
		$(".loan_list_c").find("table").eq(1).show().siblings().hide();
		clearInterval(scrollTimer0);
		clearInterval(scrollTimer1);
		scrollTimer1 = setInterval(function(){scrollLoan(m4)},3000);
	});
	m3.hover(function(){
		clearInterval(scrollTimer0);
		},function(){
			scrollTimer0 = setInterval(function(){
				scrollLoan(m3);
			},3000);
	}).trigger("mouseleave");
	m4.hover(function(){
		clearInterval(scrollTimer1);
		},function(){
			scrollTimer1 = setInterval(function(){
				scrollLoan(m4);
			},3000);
	});
	//banner滚动效果
	$('.banner').slides({
		preload: true,
		preloadImage: '${ctx}/static/images/loading.gif',
		effect: 'fade',
		crossfade: true,
		fadeSpeed: 500,
		play: 3000,
		pause: 1500,
		hoverPause: true
	});
	//登录前后状态切换
	$("#login_btn").click(function(){
		$(this).parents("#login_b").hide().siblings().show();
	});

});
//借款滚动
function scrollLoan(obj){
	var m1 = $(obj).find("table");
	var hg = m1.find("tr:first").height();
	m1.animate({
		"margin-top":-hg
		},1000,function(){
			m1.css("margin-top","0").find("tr:first").appendTo(m1);
		});
}
//新闻滚动
$(function(){
	var m2 = $(".notice_scroll ul");
	var scrollTimer;
	m2.hover(function(){
		clearInterval(scrollTimer);
		},function(){
			scrollTimer = setInterval(function(){
				scrollNews(m2);
			},3000);
	}).trigger("mouseleave");
	
	var isSubmit = false;
		//聚焦第一个输入框
		$("#loginPassword").val("");
		$("#loginName").focus();
		$('#loginAccount').keyup(function(){
			var obj = $("#loginAccount").val();
			if(obj.indexOf('@')!=-1){
			 
			}
		}); 
   	$("#loginAccount").bind("blur",function(){//用户名文本框失去焦点触发验证事件
    		var loginAccount = jQuery.trim($("#loginAccount").val());
       		if(loginAccount ==""|| loginAccount =="请输入邮箱")//验证不能为空
        	{
       			$(".login_p_add").html("用户名不能为空");
       			return false;
        	}else{
        		$(".login_p_add").html("");
        		return true;
        	}
		});
    	$("#loginPassword").bind("blur",function(){//密码文本框失去焦点触发验证事件
       		if(jQuery.trim($("#loginPassword").val())=="")//验证不能为空
        	{
       			$(".login_p_add").html("密码不能为空");
        	}else{
        		$(".login_p_add").html("");
        	}
		});
    	$("#validatorImg").bind("blur",function(){//验证码文本框失去焦点触发验证事件
        		$(".login_p_add").html("");
    		var validatorImgStr = jQuery.trim($("#validatorImg").val());
       		if(validatorImgStr==""||validatorImgStr.length!=4)//验证不能为空
        	{
       			$(".login_p_add").html("验证码不能为空,且长度为4位");
       			return false;
        	}else{
        		$(".login_p_add").html("");
    			var validatorImg = $("#validatorImg").val();
    	    	$.ajax({
	        		url:"${ctx}/accountLogin/login/CheckValidatorImg?validatorImg="+validatorImg,
	        		type:"POST",
	        		dataType: "json",
	        		success: function(response){
	        			if(response.toString()=="true"){
	        				$(".login_p_add").html("");
	        				return true;
	        			}else{
	        				$(".login_p_add").html("验证码错误!");
	        				return false;
	        			}
	       		 	}
	       		 	
    			})
        	}
		});
 function loginFunction(){
      		var loginAccount = jQuery.trim($("#loginAccount").val());
    		var validatorImgStr = jQuery.trim($("#validatorImg").val());
       		if(loginAccount ==""|| loginAccount =="请输入邮箱")//验证不能为空
        	{
       			$(".login_p_add").html("用户名不能为空");
       			$("#loginAccount").focus();
       			return false;
        	}
        	if(jQuery.trim($("#loginPassword").val())=="")//验证不能为空
        	{
       			$(".login_p_add").html("密码不能为空");
       			$("#loginPassword").focus();
       			return false;
        	}
       		if(validatorImgStr==""||validatorImgStr.length!=4)//验证不能为空
        	{
       			$(".login_p_add").html("验证码不能为空,且长度为4位");
       			return false;
        	}else{
    			var validatorImg = $("#validatorImg").val();
    	    	$.ajax({
	        		url:"${ctx}/accountLogin/login/CheckValidatorImg?validatorImg="+validatorImg,
	        		type:"POST",
	        		dataType: "json",
	        		success: function(response){
	        			if(response.toString()=="true"){
	        			checkLogin();
	        			$(".login_p_add").html("");
	        			}else{
	        				$(".login_p_add").html("验证码错误!");
	        				changeImg();
	        				return false;
	        			}
	       		 	}
	       		 	
    			});
        	}
    };   	
$("input[name='login_btn']").click(loginFunction);
document.onkeydown = function(event) {
    if (navigator.userAgent.indexOf("MSIE") > 0) { //IE
        if (window.event.keyCode == 13) {
            loginFunction();
        }
    } else { //firefox&&chrome
        if (event.keyCode == 13) {
            loginFunction();
        }
    }

}
});

function scrollNews(obj){
	var m1 = $(obj);
	var wd = m1.find("li:first").width();
	m1.animate({
		"margin-left":-wd
		},500,function(){
			m1.css("margin-left","0").find("li:first").appendTo(m1);
		});
}


//关闭弹出层
function closefancy(){
    $.fancybox.close();
}
function confirmInvest(){
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	var loanId = document.getElementById("loanId").value;
	var investAmount = document.getElementById("investAmount").value;
	var tokenValue = document.getElementById("requestToken").value;
	if(!isNaN(investAmount)){
		
		if(investAmount >= 50 && investAmount%50 == 0){
			$.ajax( {
				url : "${ctx}/financial/searchLoan/checkUserAomount?investAmount="+investAmount+"&loanId="+loanId,
				dataType : "text",
				success : function(data) {
					
					if(data == "true"){
						//$('#confirmInvest').attr("disabled","disabled").addClass("btn4_disabled");
						//$(".overlay").css({'display':'block','opacity':'0.8'});
						//$(".showbox").stop(true).animate({'margin-top':'350px','opacity':'1'},200);
						var abc = $("<div class='m-overlay'></div><div class='m-outer'><br/><img src='${ctx}/static/images/m-loading.gif' height='15' /><br/>投标进行中......</div>");
						var lay_h = $("#fancybox-wrap").height();
						var lay_w = $("#fancybox-wrap").width();
						abc.appendTo("#fancybox-wrap");
						var outer_h = $(".m-outer").height();
						var outer_w = $(".m-outer").width();
						$(".m-overlay").height(lay_h);
						$(".m-outer").css({"top":lay_h/2-outer_h/2,"left":lay_w/2-outer_w/2});
						//进入投标操作
						$.ajax( {
							url : "${ctx}/financial/searchLoan/invest?investAmount="+investAmount+"&loanId="+loanId+"&requestToken="+tokenValue,
							dataType : "text",
							success : function(data) {
								//$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
								//$(".overlay").css({'display':'none','opacity':'0'});
								if(data == "cantInvest"){
									alert("你不能对自己发布的借款进行投标");
									closefancy();
								}else{
									if(data == "true"){
										
										alert("投标成功");
										closefancy();
										window.location.href = "${ctx}";
									}else{
										alert("投标失败");
									}
								}
								$(".m-overlay, .m-outer").remove();
							}
						});
						
					}else if(data == "false"){
						//TODO      跳转到充值页面
						if(confirm(" 您的余额不足， 请充值！")){
							window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=myAccount";
						}
						
					}else if(data == "full"){
						//投资金额大于  投资剩余金额
						alert("你的投资金额大于剩余投标金额");
						
					}
				}
			});
		}else{
			alert("最低投标金额为50元，并且是50的倍数");
		}
	}else {
		alert("请输入数字");
	}
}
</script>
<script type="text/javascript">
function checkLogin(){
   	$.ajax({
   		data: $("#inputForm").serialize(),
   		url:"${ctx}/accountLogin/login/loginValidator",
   		type:"POST",
   		dataType: "json",
   		success: function(response){
   			if(response.status=="lock"){
  					isSubmit = false;
   				$(".login_p_add").html("24小时内登录失败超过3次(含),账号暂时被锁定！");
   			}else if(response.status=="fail"){
   				$(".login_p_add").html("用户名或密码错误，登录失败！");
   			}else if(response.status=="notApproveEmail"){
   				$(".login_p_add").html("该邮箱未激活，"+"<a href='${ctx}/register/register/redireEmailSet?email="+response.userEmail+"'>立即登录激活邮箱</a>");
   			}else if(response.status=="dealUserToRecycle"){
   			    $(".login_p_add").html("该用户不存在，请核对！");
   			}else{
   				$(".login_p_add").html("");
   				if(response.oldUrl.length>0){
   					window.location.href=response.oldUrl;
   				}
   				else{
   					window.location.href="${ctx}";
   				}
   				return false;
   			}
 		}
  		 	
	});
}
function changeImg(){  
 			var obj = document.getElementById("imgObj");
 			//获取当前的时间作为参数，无具体意义   
	var timenow = new Date().getTime();
	//每次请求需要一个不同的参数，否则可能会返回同样的验证码   
	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
	obj.src = "${ctx}/accountLogin/login/getValidatorImg?id=" + timenow;		
} 
function clearPwd(){
            $("#loginPassword").val("");
        }

</script>
</head>
<body>
<div class="wrapper">
	<div class="banner">
	    <div class="slides_container">
	        <a><img src="${ctx}/static/images/banner4.jpg"/></a>
			<a><img src="${ctx}/static/images/banner3.jpg"/></a>
			<a><img src="${ctx}/static/images/banner2.jpg"/></a>
		</div>
		<div class="login">
		    <div class="login_bg"></div>
			<div class="login_box_t"><p class="on">登录</p><!--1版暂时拿掉<p>推荐</p>--></div>
			<div class="login_box_c">
				<div class="login_box">
			
			<%if(null != session.getAttribute("curr_login_user")){
			   AccountUsers user = (AccountUsers) session.getAttribute("curr_login_user");
			   String loginName = user.getLoginName();
			   if(loginName.length()>9){
				   loginName = loginName.substring(0,9).concat("...");
			   }	
			%>
			<div id="login_b" style="display:none; padding:0 10px;">
					<p class="login_p_add"></p>
					<form:form id="inputForm" modelAttribute="account" name="inputForm"
			method="post" action="${ctx}/accountLogin/login/loginValidator">
					<p><label for="username">账&nbsp;&nbsp;&nbsp;号：</label><input type="text" name="loginName" style="color:#999;" id="loginAccount" 
							 onfocus="this.className='loginName';if (this.value=='请输入邮箱') {this.value='';this.style.color='#000'}" onkeypress="clearPwd()"
							 onblur="this.className='loginName';if (this.value=='') {this.value='请输入邮箱';this.style.color='#999'}" value="请输入邮箱"/></p>
					<p><label for="password">密&nbsp;&nbsp;&nbsp;码：</label><input type="password" name="loginPassword" id="loginPassword" value="${account.loginPassword}"/></p>
					<p><a onclick="changeImg()" class="fr">换一张</a><img id="imgObj" alt="验证码图片" src="${ctx}/accountLogin/login/getValidatorImg" onclick="changeImg()" class="img_verify" /><label for="verify">验证码：</label><input type="text" name="validatorImg" id="validatorImg" class="verify" style="width:50px;"/><a onclick="changeImg()">换一张</a></p>
					<p class="p_login_btn"><a href="${ctx}/register/register/creatUser" class="login_btn">注册</a><input type="button" value="登录" class="login_btn" name="login_btn" id="login_btn"/><a href="${ctx}/accountLogin/login/forgetPwd" class="fl">忘记密码？</a></p>
					<!--1版暂时拿掉
					<p class="other_login tc"><img src="${ctx}/static/images/qq_login.png" /><a href="#">QQ登录</a> | <img src="${ctx}/static/images/sina_login.png" /><a href="#">新浪微博登录</a></p>
					-->
				</form:form>
				</div>
				<div id="login_a" class="tc bold1" >
						<p class="mar13 font_14">您已登录证大e贷，</p>
						<p class="font_16"><%=loginName%>欢迎您！</p>
						<p style="height:auto; margin-top:10px;"><a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=myAccount&token=${sessionScope.token}" class="btn2 mar0">进入我的账户</a></p>
						<p style="font-size:14px; margin-top:25px;">免费服务热线：400-821-6888</p>
				</div>
				
			<%}else{%>
				<div id="login_b" style="padding:0 10px;">
					<p class="login_p_add"></p>
					<form:form id="inputForm" modelAttribute="account" name="inputForm"
			method="post" action="${ctx}/accountLogin/login/loginValidator">
					<p><label for="username">账&nbsp;&nbsp;&nbsp;号：</label><input type="text" name="loginName" style="color:#999;" id="loginAccount" 
							 onfocus="this.className='loginName';if (this.value=='请输入邮箱') {this.value='';this.style.color='#000'}" onkeypress="clearPwd()"
							 onblur="this.className='loginName';if (this.value=='') {this.value='请输入邮箱';this.style.color='#999'}" value="请输入邮箱" /></p>
					<p><label for="password">密&nbsp;&nbsp;&nbsp;码：</label><input type="password" name="loginPassword" id="loginPassword" value="${account.loginPassword}"/></p>
					<p><a onclick="changeImg()" class="fr">换一张</a><img id="imgObj" alt="验证码图片" src="${ctx}/accountLogin/login/getValidatorImg" onclick="changeImg()" class="img_verify" /><label for="verify">验证码：</label><input type="text" name="validatorImg" id="validatorImg" class="verify" style="width:50px;"/></p>
					<p class="p_login_btn"><a href="${ctx}/register/register/creatUser" class="login_btn">注册</a><input type="button" value="登录" class="login_btn" name="login_btn"/><a href="${ctx}/accountLogin/login/forgetPwd" class="fl">忘记密码？</a></p>
					<!--1版暂时拿掉
					<p class="other_login tc"><img src="${ctx}/static/images/qq_login.png" /><a href="#">QQ登录</a> | <img src="${ctx}/static/images/sina_login.png" /><a href="#">新浪微博登录</a></p>
					-->
				</form:form>
				</div>
				<div id="login_a" class="tc bold1" style="display:none;">
						<p class="mar13 font_14">您已登录证大e贷，</p>
						<p class="font_16">欢迎您！</p>
						<p class="mar7"><a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=myAccount&token=${sessionScope.token}" class="btn2 mar0">进入我的账户</a></p>
						<p class="font_14 mar12">免费服务热线：400-821-6888</p>
				</div>
			<%}%>
			</div>
				<!--1版暂时拿掉
				<div class="login_box" style="display:none;">
					<p class="login_p_add">请输入正确的手机号码！</p>
					<p><label for="name">姓&nbsp;&nbsp;&nbsp;名：</label><input type="text" name="name"/></p>
					<p><label for="phone">电&nbsp;&nbsp;&nbsp;话：</label><input type="text" name="phone" /></p>
					<p><label for="email">邮&nbsp;&nbsp;&nbsp;箱：</label><input type="text" name="email" id="email" /></p>
					<p><label for="name2">推荐人：</label><input type="text" name="name2" /></p>
					<p class="p_login_btn"><input type="submit" value="提交" class="login_btn fr" /></p>
				</div>
				-->
			</div>
		</div>
	</div>
 

	<div class="notice">
        <img src="${ctx}/static/images/notice.jpg"/>    
		<span class="fl">最新公告：</span>
		<div class="notice_scroll">
			<ul>
			<c:forEach items="${indexPage.websiteList}" var="website">
				<li id="ali" style="width: 250px"><a href="${ctx }/footerDetail/footerDetail/showWordListPage?id=${website.id}">${website.title}</a></li>
			</c:forEach>
			</ul>
		</div>
		<a href="${ctx}/footerDetail/footerDetail/showWordList?type=19" class="fr">更多>></a>
	</div>
	<div class="content">
		<div class="loan_invest">
			<div class="loan_box">
			    <h3></h3>
				<div class="loan_c">
					<span><a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=2&category=首页&sort=使用帮助&c_sort=平台原理">平台原理 》</a>&nbsp;&nbsp;<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=6&category=首页&sort=使用帮助&c_sort=使用技巧">提高信用 》</a>&nbsp;&nbsp;<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=6">使用技巧 》</a></span>
					<div class="release_loan"> 
						<input type="text" value="输入借款金额" onfocus="if(this.value=='输入借款金额')this.value=''" onblur="if(this.value=='')this.value='输入借款金额'" />
						<input type="text" value="输入借款用途" onfocus="if(this.value=='输入借款用途')this.value=''" onblur="if(this.value=='')this.value='输入借款用途'" />
						<input type="text" value="输入借款年利率" onfocus="if(this.value=='输入借款年利率')this.value=''" onblur="if(this.value=='')this.value='输入借款年利率'" />
						<a href="${ctx}/borrowing/releaseLoan/show">立即发布借款</a>
					</div>
				</div>
			</div>
			<div class="invest_box">
				<h3></h3>
				<div class="invest_c">
					<span><a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=7&category=首页&sort=安全保障&c_sort=风险金代偿">安全保障 》</a>&nbsp;&nbsp;<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=6&category=首页&sort=使用帮助&c_sort=使用技巧">投资技巧 》</a></span>
					<a href="#" onclick="checkBalanceAmount()" class="release_invest">成为理财人</a>
				</div>
			</div>
		</div>
				<div class="loan_list">
		    <div class="loan_list_t">
				<div class="line"></div>
			    <h3 class="on">进行中的借款</h1>
				<h3>已完成的借款</h1>
				<a href="${ctx}/financial/searchLoan/showLoan" class="fr">更多>></a>
				<a href="${ctx}/financial/searchLoan/showLoan" class="fr" style="display:none;">更多>></a>
			</div>
			<div class="loan_list_c">
				<div class="loan_table" id="loan_table1">
					<%@ include file="/WEB-INF/views/footerDetail/inLoanPage.jsp"%>
				</div>
			    <div class="loan_table" id="loan_table2" style="display:none;">
			    	<%@ include file="/WEB-INF/views/footerDetail/successLoanPage.jsp"%>
				</div>
			</div>
		</div>
		<div class="news">
		    <div class="industry">
			    <h3>行业新闻</h3>
				<ul>
				<c:forEach items="${indexPage.industryNews}" var="news">
				    <li><a href="${ctx }/footerDetail/footerDetail/showWordListPage?id=${news.id}" >${news.title }</a></li>
				</c:forEach>
				</ul>
				<a href="${ctx}/footerDetail/footerDetail/showWordList?type=20"  class="fr">更多>></a>
			</div>
			<div class="media">
			    <h3>媒体报道</h3>
				<ul>
				<c:forEach items="${indexPage.mediaReports}" var="reports">
				    <li><a href="${ctx }/footerDetail/footerDetail/showWordListPage?id=${reports.id}" >${reports.title }</a></li>
				</c:forEach>
				</ul>
				<a href="${ctx}/footerDetail/footerDetail/showWordList?type=21"  class="fr">更多>></a>
			</div>
			<div class="story">
			    <h3>微金融故事</h3>
				<ul>
				<c:forEach items="${indexPage.microFinancialStory}" var="financialStory">
				    <li><a href="${ctx }/footerDetail/footerDetail/showStoryListPage?id=${financialStory.id}" >${financialStory.title }</a></li>
				</c:forEach>
				</ul>
				<a href="${ctx}/footerDetail/footerDetail/showStoryList?type=22"  class="fr">更多>></a>
			</div>
		</div>
	</div>
	</div>
	<div class="clear"></div>
</div>
</body>
</html>

