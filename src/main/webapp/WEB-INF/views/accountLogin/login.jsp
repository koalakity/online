<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/static/js/email.js"></script>
<link rel="stylesheet" href="${ctx}/static/css/login.css" type="text/css" /> 
<!--[if lte IE 6]>
<script type="text/javascript" src="${ctx}/static/js/belatedPNG.js"></script>
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
	//邮箱补全
	var inputSuggest = new InputSuggest({
		input: document.getElementById('loginAccount'),
		data: ['sina.com','163.com','qq.com','126.com','sohu.com','yahoo.com','hotmail.com','gmail.com','139.com','189.com']
	});
});
</script>
<script>
		$(document).ready(function() {
		var isSubmit = false;
		//聚焦第一个输入框
		$("#loginPassword").val("");
		$('#loginAccount').keyup(function(){
			var obj = $("#loginAccount").val();
			if(obj.indexOf('@')!=-1){
				//TODO 补全email
			}
		}); 
    	$("#loginAccount").bind("blur",function(){//用户名文本框失去焦点触发验证事件
    		var loginAccount = jQuery.trim($("#loginAccount").val());
       		if(loginAccount ==""|| loginAccount =="用户昵称/手机号码/邮箱")//验证不能为空
        	{
       			$(".col1").html("用户名不能为空");
       			return false;
        	}else{
        		$(".col1").html("");
        		return true;
        	}
		});
    	$("#loginPassword").bind("blur",function(){//密码文本框失去焦点触发验证事件
       		if(jQuery.trim($("#loginPassword").val())=="")//验证不能为空
        	{
       			$(".col1").html("密码不能为空");
        	}else{
        		$(".col1").html("");
        	}
		});
    	$("#validatorImg").bind("blur",function(){//验证码文本框失去焦点触发验证事件
        		$(".col1").html("");
    		var validatorImgStr = jQuery.trim($("#validatorImg").val());
       		if(validatorImgStr==""||validatorImgStr.length!=4)//验证不能为空
        	{
       			$(".col1").html("验证码不能为空,且长度为4位");
       			return false;
        	}else{
        		$(".col1").html("");
    			var validatorImg = $("#validatorImg").val();
    	    	$.ajax({
	        		url:"${ctx}/accountLogin/login/CheckValidatorImg?validatorImg="+validatorImg,
	        		type:"POST",
	        		dataType: "json",
	        		success: function(response){
	        			if(response.toString()=="true"){
	        				$(".col1").html("");
	        				return true;
	        			}else{
	        				$(".col1").html("验证码错误!");
	        				return false;
	        			}
	       		 	}
	       		 	
    			});
        	}
		});
  function loginFunc(){
      		var loginAccount = jQuery.trim($("#loginAccount").val());
    		var validatorImgStr = jQuery.trim($("#validatorImg").val());
       		if(loginAccount ==""|| loginAccount =="请输入邮箱")//验证不能为空
        	{
       			$(".col1").html("用户名不能为空");
       			$("#loginAccount").focus();
       			return false;
        	}
        	if(jQuery.trim($("#loginPassword").val())=="")//验证不能为空
        	{
       			$(".col1").html("密码不能为空");
       			$("#loginPassword").focus();
       			return false;
        	}
       		if(validatorImgStr==""||validatorImgStr.length!=4)//验证不能为空
        	{
       			$(".col1").html("验证码不能为空,且长度为4位");
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
	        			$(".col1").html("");
	        			}else{
	        				$(".col1").html("验证码错误!");
	        				changeImg();
	        				return false;
	        			}
	       		 	}
	       		 	
    			});
        	}
    };  	
  $("input[name='login_btn']").click(loginFunc);
document.onkeydown = function(event) {
    if (navigator.userAgent.indexOf("MSIE") > 0) { //IE
        if (window.event.keyCode == 13) {
            loginFunc();
        }
    } else { //firefox&&chrome
        if (event.keyCode == 13) {
            loginFunc();
        }
    }

}
		
	});		
</script>
<script type="text/javascript">
function checkLogin(){		//关闭AJAX相应的缓存 
		$.ajaxSetup ({ 
      		cache: false 
		});
   	$.ajax({
   		data: $("#inputForm").serialize(),
   		url:"${ctx}/accountLogin/login/loginValidator",
   		type:"POST",
   		dataType: "json",
   		success: function(response){
   			if(response.status=="lock"){
  					isSubmit = false;
   				$(".col1").html("24小时内登录失败超过3次(含),账号暂时被锁定!");
   			}else if(response.status=="fail"){
   				$(".col1").html("用户名或密码错误，登录失败！");
   			}else if(response.status=="notApproveEmail"){
   				$(".col1").html("该邮箱未激活，"+"<a href='${ctx}/register/register/redireEmailSet?email="+response.userEmail+"'>立即登录激活邮箱</a>");
   			}else if(response.status=="dealUserToRecycle"){
   			   $(".col1").html("该用户不存在，请核对!");
   			}else{
   				$(".col1").html("");
   				if(response.oldUrl.length>0){
   				if(response.oldUrl=="/online/accountLogin/login/show"){
   					window.location.href="${ctx}";
   				}else{
   					window.location.href=response.oldUrl;
 	   			}
   				}
   				else{
   					window.location.href="${ctx}";
   				}
   				
   				return false;
   			}
 		}
  		 	
	})
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
	<div class="content login_bg1">
		<div class="login_box1" id="login_box1">
			<h3>证大e贷用户登录</h3>
				<form:form id="inputForm" modelAttribute="account" name="inputForm"
		method="post" action="${ctx}/accountLogin/login/loginValidator">
			<table>
				<tr><td class="tr"><label for="username">账&nbsp;&nbsp;&nbsp;号：</label></td><td><input type="text" name="loginName" style="color:#999;" id="loginAccount"
						 onfocus="this.className='loginName';if (this.value=='请输入邮箱') {this.value='';this.style.color='#000'}" onkeypress="clearPwd()"
						 onblur="this.className='loginName';if (this.value=='') {this.value='请输入邮箱';this.style.color='#999'}" value="请输入邮箱" /></td></tr>
				<tr><td class="tr"><label for="password">密&nbsp;&nbsp;&nbsp;码：</label></td><td><input type="password" name="loginPassword" id="loginPassword" value="${account.loginPassword}"/></td></tr>
				<tr><td class="tr"><label for="verify">验证码：</label></td><td><div><input type="text" name="validatorImg" id="validatorImg" class="verify" style="width:50px;" />&nbsp;&nbsp;<img id="imgObj" alt="验证码图片" src="${ctx}/accountLogin/login/getValidatorImg" onclick="changeImg()" class="img_verify"/><a onclick="changeImg()">换一张</a></div></td></tr>
				<tr><td>&nbsp;</td><td class="col1"></td></tr>
				<tr class="tr1"><td>&nbsp;</td><td><input type="button" value="登录" class="login_btn" name="login_btn"/><a href="${ctx}/accountLogin/login/forgetPwd">忘记密码？</a></td></tr>
				<tr class="tr1"><td>&nbsp;</td><td>还未加入证大e贷？<a href="${ctx}/register/register/creatUser">免费注册</a></td></tr>
				<!-- 一期暂时不做 
				<tr><td colspan="2"><a href="#" class="qq_login">QQ登录</a><a href="#" class="sina_login">新浪微博登录</a></td></tr>
				-->
			</table>
			</form:form>
		</div>
	</div>
	<div class="clear"></div>
</div>
</body>
</html>