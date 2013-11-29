<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<title>忘记密码</title>
<link rel="stylesheet" href="/online/static/css/login.css" type="text/css" />
		<script>
$(document).ready(function() {

	//聚焦第一个输入框
		$("#remail").focus(function(){
  $(".col1").html("&nbsp;");
});
		//为inputForm注册validate函数
		$("#forgetpwdForm").validate( {
			rules : {
				remail : {
					email : true
				}
			},
			messages : {
				remail : {
					email : "邮箱格式不正确"
				}
			}

		});
	});

var emailFlg = false;
function checkEmail(){
	var email = document.getElementById("remail").value;
   	$.ajax({
   		url:"${ctx}/accountLogin/login/checkEmail?email="+email,
   		type:"POST",
   		dataType: "json",
   		success: function(response){
   			if(response.toString()=="true"){
   				emailFlg = true;
   				$(".col1").html("");
   			}else{
   				emailFlg = false;
   				$(".col1").html("该邮箱未注册，请填写正确邮箱");
   			}
  		 }
	});
		return emailFlg;
}
	
function subform(){
	var cd = $("#forgetpwdForm").validate().form();
	if(cd){
		var email = document.getElementById("remail").value;
		var validateCode=document.getElementById("validateCode").value;
     	$.ajax({
	   		url:"${ctx}/accountLogin/login/checkEmail?email="+email+"&validateCode="+validateCode,
	   		type:"POST",
	   		dataType: "text",
	   		success: function(response){
	   			if(response.toString()=="true"){
	   				$(".col1").html("");
	   				$("#forgetpwdForm").submit();
	   			}else{
	   				if(response.toString()=="codeError"){
	   					$("#validateCodeErrorInfo").html("<font color='red'>验证码错误，请重新输入！</font>");
	   					changeImg();
	   				}else{
	   					$(".col1").html("该邮箱未注册，请填写正确邮箱");
	   				}
	   			}
	  		}
		});
	}
}

var showFlg = "${showFlg}";
if(showFlg == 'true'){
	alert("密码重置成功,已经发送到您的邮箱中");
	window.location.href="${ctx}/accountLogin/login/show";
}

if(showFlg == 'false'){
	alert("该邮箱未注册，请填写正确邮箱。");
}
    function changeImg(){
		var obj = document.getElementById("imgObj");
		var timenow = new Date().getTime();
		//每次请求需要一个不同的参数，否则可能会返回同样的验证码   
		//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
		obj.src = "${ctx}/register/register/getValidatorImg?id="+timenow;
	}
	
   document.onkeydown=function(event){
       if(event.keyCode==13){
    	  subform();
       }
   }
</script>

	</head>
	<body>
		<form:form id="forgetpwdForm" modelAttribute="user"
			action="${ctx}/accountLogin/login/resetPwd" method="post">
			<div class="lost_psd">
				<a href="${ctx}/accountLogin/login/show">登录</a> > 找回密码
				<table style="width:auto;">
					<tr>
						<td class="font_14">
							邮箱地址：
						</td>
						<td>
							<input type="text" id="remail" name="remail" class="required">
							&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<td class="col1">
							&nbsp;	
						</td>
					</tr>
					<tr>
						<td class="font_14">
							输入验证码：
						</td>
						<td>
							<input type="text" id="validateCode" style="width:50px;" class="required" maxlength="4"/>
							<img id="imgObj" align="top" 
							alt="验证码图片" 
							src="${ctx}/register/register/getValidatorImg" 
							onclick="changeImg()" 
							width="80"/>
							<a href="#" onclick="changeImg()">换一张</a>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td id="validateCodeErrorInfo">&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<a href="#" class="btn2" onclick="subform()">找回密码</a>
						</td>
					</tr>
				</table>
			</div>
		</form:form>
	</body>
</html>
