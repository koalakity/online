<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function(){
	//聚焦第一个输入框
	$("#phoneNumber").focus();
});

</script> 
<script type="text/javascript">
/* function checkTelphone(){
	var phone=$("#phoneNumber").val();
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(phone.length!=11){
		alert("请输入11位手机号码!");
		return false;
	}
	if(!mobile.test(phone)){
		alert("请正确填写您的手机号码");
		return false;
	}else{
		return true;
	}
} */
var bflg = true;	
function checkTelphone(){
	var mt = document.getElementById("msgPhone");
	var phone=$("#phoneNumber").val();
	var mobile = /^0?(13|15|18|14)[0-9]{9}$/;
	if(phone.length!=11||!mobile.test(phone)){
		mt.innerHTML = "请正确填写您的手机号码";
      	return false;
	}else{
		$.ajax({
			 url: "${ctx}/financial/beLender/validatePhone?phoneNumber="+phone,
	   	 	 type: "POST",
	   		 dataType: 'html',
	   		 timeout: 10000,
	       	success: function(result){
				if (result == "false") {
					mt.innerHTML = "校验失败,当前手机号码已被绑定！咨询热线：4008216888！";
					bflg = false;
				} else {
					mt.innerHTML = "";
					bflg = true;
				}
	       	}
			});

		return bflg;
	}
}

//手机验证码
function checkVerifyCode(){
	var validateCode = $("#verifyCode").val();
	var code = /^[0-9]*$/;
	if(validateCode.length!=6||!code.test(validateCode)){
		alert("请输入6位数字手机验证码");
		return false;
	}else{
		return true;
	}
}
var speed = 1000; //速度
var wait = 0; //停留时间
function updateinfo(){
  if(wait == 0){
    document.getElementById("yzm").disabled = false;
    document.getElementById("yzm").value = "发送手机验证码";
  }
  else{
   document.getElementById("yzm").value = "发送手机验证码"+wait;
    wait--;
    window.setTimeout("updateinfo()",speed);
  }
}
//发送手机验证码
function sendYzm(){
	var obj = document.getElementById("yzm");
	obj.disabled="disabled";
	var phone=$("#phoneNumber").val(); 
	wait=60;
     if(checkTelphone()){
		$.ajax({
			 url: "${ctx}/financial/beLender/sendCode?phoneNumber="+phone,
	   	 	 type: "POST",
	   		 dataType: 'json',
	   		 timeout: 10000,
	    	 error: function(){
		    	alert("发送失败，请稍后重试");
				 obj.removeAttribute("disabled");
		    	return;
	        },
	       	success: function(response){
				if(response.sendResult=="ok"){
			    	alert("恭喜您，您的手机号码已成功发送，请注意查收验证码。");
			    	updateinfo();
					// obj.removeAttribute("disabled");
			    	return;
				}else if(response.sendResult=="binded"){
				    alert("校验失败,当前手机号码已被绑定！咨询热线：4008216888！");
				    obj.removeAttribute("disabled");
			    	return;
				}else{
					alert("发送失败，请稍后重试");
					 obj.removeAttribute("disabled");
					return;
					}
	       	}
			});	
         }else{
          //alert("手机号码已被占用，请重新发送手机号码。");
          obj.removeAttribute("disabled");
          return;
             }
}

//提交表单
function bindSubmit(){
	var obj = document.getElementById("bindSubmit");
	obj.disabled="disabled";
	if(checkTelphone()&&checkVerifyCode()){
		var phone=$("#phoneNumber").val();
		var validateCode = $("#verifyCode").val();
	 	$.ajax({
			 url: "${ctx}/financial/beLender/bindPhoneNo?phoneNumber="+phone+"&validateCode="+validateCode,
		 	 type: "POST",
			 dataType: 'json',
			 timeout: 10000,
	 	 	error: function(){
		    	alert("绑定失败，请稍后重试！");
				 obj.removeAttribute("disabled");
		    	return false;
	     },
	    	success: function(data){
				if(data.result=="error"){
					alert("手机验证码输入错误,请您确认后重新输入！");
					 obj.removeAttribute("disabled");
					return;
				}else if(data.result=="noMatch"){
					alert("绑定失败，手机号码和验证码不匹配！");
					 obj.removeAttribute("disabled");
					return;
				}else if(data.result=="resend"){
					alert("绑定失败，请发送手机验证码后再绑定手机！");
					 obj.removeAttribute("disabled");
					return;
				}else if(data.status=="overtime"){
			       alert("校验失败！24小时内未验证，验证码失效！");
			       obj.removeAttribute("disabled");
			      return;
			    }else{
					 alert("绑定成功！");
					 obj.removeAttribute("disabled");
					 return;
					}

	    	},
	     beforeSend: function(){
	     }
		})
	}else{
		 obj.removeAttribute("disabled");
	     return ;
		}
	
}
</script>
			<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">手机绑定</h3>
				</div>
				<div class="account_r_c_4c">
					<table>
						<tr><td class="td8">手机号码：</td><td><input type="text" name="phoneNumber" id="phoneNumber" />&nbsp;&nbsp;<input type="button"  id="yzm"  onclick="sendYzm()" class="btn_yzm" value="发送手机验证码" /><div id="msgPhone"></div></td></tr>
						<tr><td class="td8">验证码：</td><td><input type="text" name="verifyCode" id="verifyCode"/>&nbsp;&nbsp;请输入您获取的手机验证码</td></tr>
					</table>
					<input type="button" class="btn3 mar10"onclick="bindSubmit()" name="bindSubmit" id="bindSubmit"  value="提交" />
				</div>
			</div>