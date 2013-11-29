<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript" type="text/javascript">
function savePwd(){

var npwd = document.getElementById("nPassWrod").value;
var npwd2 = document.getElementById("nPassWrod2").value;
var ypwd = document.getElementById("yPassWord").value;
var f1 = false;
var f2 = false;
var f3 = false;

if(npwd!="" || npwd!=null){
	if(npwd.length>=6){
		f1=true;
	}
}
if(npwd2!="" || npwd2!=null){
	if(npwd2.length>=6){
		f2=true;
	}
}
if(ypwd!="" || ypwd!=null){
	if(ypwd.length>=6){
		f3=true;
	}
}
if(f1&&f2&&f3){
	if(npwd==npwd2){
		$.ajax({
					 data: $("#modifyPwdForm").serialize(),
		    		 url: "${ctx}/personal/personal/saveNewPwd",
		    	 	 type: "POST",
		    		 dataType: 'html',
		    		 timeout: 10000,
		      error: function(){
		     	 alert("哎呀,出错啦!");
		      },
			   	success: function(data){
			    		$(".account_r").html(data);
			   	}
		});
	}else{
		alert("两次新密码输入不一致");
	}
}else{
	alert("密码不能为空且长度不能小于6");
}


}
if("${show}"=="true"){
alert("保存成功.");
}
if("${show}"=="pwdFalse"){
alert("原密码输入错误");
}
</script>
<form:form id="modifyPwdForm" modelAttribute="modifyPwdVo"
	action="${ctx}/personal/personal/saveNewPwd" method="post">
<div class="account_r">
	<div class="account_r_c account_r_c_add">
		<div class="account_r_c_t">
			<h3 class="on">
				修改密码
			</h3>
		</div>
		<div class="account_r_c_4c">
			<div class="prompt6">
				<p>
					请填写以下基本资料：（注：带*为必填）
				</p>
			</div>
			<table>
				<tr>
					<td class="td8">
						*原密码：
					</td>
					<td>
						<input type="password" id="yPassWord" name="yPassWord" value="${modifyPwdVo.yPassWord}"/>
						&nbsp;&nbsp;请输入您现在的密码
					</td>
				</tr>
				<tr>
					<td class="td8">
						*新密码：
					</td>
					<td>
						<input type="password" id="nPassWrod" name="nPassWrod" value="${modifyPwdVo.nPassWrod}"/>
						&nbsp;&nbsp;请输入您的新密码
					</td>
				</tr>
				<tr>
					<td class="td8">
						*确认新密码：
					</td>
					<td>
						<input type="password" id="nPassWrod2" name="nPassWrod2" value="${modifyPwdVo.nPassWrod}"/>
						&nbsp;&nbsp;请再次输入您的新密码
					</td>
				</tr>
			</table>
			<input type="button" class="btn3 mar10" value="提交" onclick="savePwd()"/>
		</div>
	</div>
</div>
</form:form>
