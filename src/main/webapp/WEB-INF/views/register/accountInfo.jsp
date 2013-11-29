<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<html>
	<head>
		<title>注册</title>
		<link rel="stylesheet" href="${ctx}/static/css/login.css" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<script type="text/javascript" src="${ctx}/static/js/email.js">
</script>
		<script type="text/javascript">
		if("${registerFlag}"){
			alert("${registerFlag}");
		}
$(function() {
	$(".nav").find("a").hover(function() {
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});

	var inputSuggest = new InputSuggest( {
		input : document.getElementById('email'),
		data : ['sina.com', '163.com', 'qq.com', '126.com',
				'sohu.com', 'yahoo.com', 'hotmail.com', 'gmail.com',
				'139.com', '189.com' ]
	});
	
	if("${showFlg}" == "show"){
		
		document.getElementById('loginName').disabled=true;
		document.getElementById('loginPassword').disabled=true;
		document.getElementById('passwordConfirm').disabled=true;
		document.getElementById('register').disabled=true;
		document.getElementById('agreeProvision').disabled=true;
		$("#email").removeAttr("onBlur");
		$("#email").attr("onBlur","checkEmailAgain()");
	}
	
	//邮箱地址同步
	$("#email, .suggest-container").live('keyup mouseup',function(){
		$("#oemail").val($("#email").val());
	});
});
</script>
		<script type="text/javascript">

function nameRemind(mname) {
	mname.innerHTML = "4-20个字符，不可以使用汉字";
}

function passWrodRemind(pname) {
	pname.innerHTML = "6-16个字符，建议使用字母加数字或符号的组合密码";
	pname.style.display = '';
}
var bflg = true;
//用户名验证
function checkLoginName() {
	var loginName = document.getElementById("loginName").value;
	var mt = document.getElementById("msgName");
	if (loginName.replace(/[ ]/g,"") == "") {
		mt.innerHTML = "帐号不能为空";
		return false;
	} else {
		var re = new RegExp("[\\u4e00-\\u9fa5]", "");
		var yesorno = re.test(loginName);
		if(yesorno){
			mt.innerHTML = "帐号不能为中文";
		}else{
		if (loginName.length >= 4 && loginName.length <= 20) {
			
			mt.innerHTML = "";
			$.ajax( {
				url : "${ctx}/register/register/checkLoginName?loginName="
						+ loginName,
				type : "POST",
				dataType : 'html',
				success : function(strFlg) {
					if (strFlg == "false") {
						mt.innerHTML = "该昵称已被使用，请重新输入";
						bflg = false;
					} else {
						mt.innerHTML = "";
						bflg = true;
					}
				}
			});
			return bflg;
		} else {
			mt.innerHTML = "4-20个字符";
			return false;
		}
		}
	}
}



var eflg = true;
function checkEmail() {
	var email = document.getElementById("email").value;
	var mt = document.getElementById("msgEmail");
	if (email == "") {
		mt.innerHTML = "邮箱不能为空";
		return false;
	} else {
	var reMail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
		if (reMail.test(email)) {
			
			mt.innerHTML = "";
			$.ajax( {
				url : "${ctx}/register/register/checkEmail?email=" + email,
				type : "POST",
				dataType : 'html',
				success : function(strFlg) {
					if (strFlg == "false") {
						mt.innerHTML = "该邮箱已被使用，请重新输入";
						eflg = false;
					} else {
						mt.innerHTML = "";
						eflg = true;
					}
				}

			});
			return eflg;
		} else {
			mt.innerHTML = "邮箱格式不正确";
			return false;
		}
	}
}

var emailflg = true;
function checkEmailAgain() {

	var email = document.getElementById("email").value;
	var mt = document.getElementById("msgEmail");
	var loginName = document.getElementById("loginName").value;
	if (email == "") {
		mt.innerHTML = "邮箱不能为空";
		return false;
	} else {
		if (email.match(/[A-Za-z0-9_-]+[@](\S*)(net|com|cn|org|cc|tv|[0-9]{1,3})(\S*)/g) != null) {
			mt.innerHTML = "";
			$.ajax( {
				url : "${ctx}/register/register/checkEmailAgain?loginName="+loginName+"&email=" + email,
				type : "POST",
				dataType : 'json',
				success : function(strFlg) {

					if (strFlg == "false") {
						
						mt.innerHTML = "该邮箱已被使用，请重新输入";
						emailflg = false;
						return false;
					} else {
						mt.innerHTML = "";
						emailflg = true;
						
						return true;
					}
				}

			});
			return emailflg;
		} else {
			mt.innerHTML = "邮箱格式不正确";
			return false;
		}
	}
}


function safeClass(val, pw) {
	if ((/^\d+$/.test(val)) || (/^[A-Za-z]+$/.test(val))) {
		pw.innerHTML = "当前密码的安全级别低";
		pw.style.display = '';
	} else {
		pw.innerHTML = "当前密码的安全级别中";
		pw.style.display = '';
	}
	if (!/^\w{1,20}$/.test(val)) {
		pw.innerHTML = "当前密码的安全级别高";
		pw.style.display = '';
	}
}

function checkPassWrod() {

	var ps1 = document.getElementById("loginPassword").value;
	var mp = document.getElementById("safeLevel");

	if (ps1 == "") {
		mp.innerHTML = "密码不能为空";
		mp.style.display = '';
		return false;
	} else {
		if (ps1.length >= 6 && ps1.length <= 16) {
			mp.style.display = "none";
			return true;
		} else {
			mp.innerHTML = "6-16个字符";
			mp.style.display = '';
			return false;
		}

	}
}

function checkPassWrodEqual() {
	var ps1 = document.getElementById("loginPassword").value;
	var ps2 = document.getElementById("passwordConfirm").value;
	var mp = document.getElementById("msgPassEqual");
	if (ps1 == ps2) {
		mp.innerHTML = "";
		return true;
	} else {
		mp.innerHTML = "两次输入密码不一致";
		return false;
	}
}

function mysubmit() {
	var cb = document.getElementById("agreeProvision").checked;
	if (cb) {
		return true;
	} else {
		alert("请接受我们的条款");
		return false;
	}

}

function checkSubmit(){
	if(checkLoginName() && checkEmail() && checkPassWrod() && checkPassWrodEqual() && mysubmit() && checkValidatorImg()){
		return true;
	}else{
		return false;
	}
	
}


var emailflg = true;
function sendAgainEmail(){
	var email = document.getElementById("email").value;
	var mt = document.getElementById("msgEmail");
	var loginName = document.getElementById("loginName").value;
	if (email == "") {
		mt.innerHTML = "邮箱不能为空";
		return false;
	} else {
		var reMail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
		if (reMail.test(email)) {
			mt.innerHTML = "";
			$.ajax( {
				url : "${ctx}/register/register/checkEmailAgain?loginName="+loginName+"&email=" + email,
				type : "POST",
				dataType : 'json',
				success : function(strFlg) {
					if (strFlg == "false") {
						mt.innerHTML = "该邮箱已被使用，请重新输入";
						alert("邮箱不正确");
						emailflg = false;
					} else {
						mt.innerHTML = "";
						emailflg = true;
						var email = document.getElementById("email").value;
						var loginName = document.getElementById("loginName").value;
						var baspath = "<%=basePath%>";
						var url = "${ctx}/register/register/againSendEmail?loginName=${user.loginName}&email="+email+"&serverPath=<%=basePath %>";
						window.location.href(url);
					}
				}

			});
			return emailflg;
		} else {
			mt.innerHTML = "邮箱格式不正确";
			return false;
		}
	}
	
	
}

function changeImg(){  
		var obj = document.getElementById("imgObj");
		//获取当前的时间作为参数，无具体意义   
	var timenow = new Date().getTime();
//每次请求需要一个不同的参数，否则可能会返回同样的验证码   
//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
obj.src = "${ctx}/register/register/getValidatorImg?id=" + timenow;		
} 

//定时刷新验证码
function changeImg2(){
	changeImg();
}
window.setInterval(changeImg2,60000);






var validatorImgflg = true;
//验证码验证
function checkValidatorImg() {
	var validatorImgStr = document.getElementById("validatorImg").value;
	var gg = document.getElementById("validatorImgMsg");
	if	(validatorImgStr==""||validatorImgStr.length!=4){//验证不能为空
		gg.style.display = "block";
		gg.innerHTML ="验证码不能为空,且长度为4位";
		return false;
	} else {
		gg.style.display = "block";
		gg.innerHTML = "";
		$.ajax( {
			url : "${ctx}/register/register/CheckValidatorImg?validatorImg="+validatorImgStr,
			type : "POST",
			dataType : 'json',
			success : function(response) {
				if (response.toString() == "false") {
					
					gg.style.display = "block";
					gg.innerHTML = "验证码错误！";
					validatorImgflg = false;
				} else {
					gg.style.display = "block";
					gg.innerHTML = "";
					validatorImgflg = true;
				}
			}
		});
		return validatorImgflg;
	}
}

function appendChildChannel()
{
	var id=$("#channelInfo_ParantID").val();
			$.ajax( {
				url : "${ctx}/register/register/findByParentId?code="+id,
				type : "POST",
				dataType : 'JSON',
				success : function(strFlg) {
					$("#channelInfo_ID").empty();	
					for(var i=0;i<strFlg.length;i++)
					{
						$("#channelInfo_ID").append("<option value="+strFlg[i].id+">"+strFlg[i].name+"</option>");
					}
				}
			});
}

</script>
	</head>
	<body>

		<div class="content login_bg2">
			<div class="login_box2" style="height:510px;">
				<h3>
					证大e贷用户注册
				</h3>
				<form:form id="inputForm" modelAttribute="user"
					action="${ctx}/register/register/saveAccountInfo" method="post"
					onsubmit="return checkSubmit()">
					<input type="hidden" name="regType" value="1" />
					<input type="hidden" name="serverPath" value="<%=basePath%>" />
					<table>
						<tr>
							<td class="td1">
								<label for="name">
									用户昵称：
								</label>
							</td>
							<td>
								<input type="text" id="loginName" name="loginName" 	onclick="nameRemind(msgName)" value="${user.loginName}"	size="20" class="required" onBlur="checkLoginName(this.value)">
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="msgName"></div>
							</td>
						</tr>
						<tr>
							<td class="td1">
								<label for="email">
									常用邮箱：
								</label>
							</td>
							<td>
								<input type="text" id="email" name="email"	onBlur="checkEmail(this.value)"	value="${user.email}" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="msgEmail"></div>
							</td>
						</tr>
						<tr>
							<td class="td1">
								<label for="psd">
									登录密码：
								</label>
							</td>
							<td>
								<input type="password" id="loginPassword" name="loginPassword"	onclick="passWrodRemind(safeLevel)"		value="${user.loginPassword}" size="20" class="required" onBlur="checkPassWrod()"
									onpropertychange="safeClass(this.value,safeLevel)">
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="safeLevel" style="display: none"></div>
							</td>
						</tr>
						<tr>
							<td class="td1">
								<label for="re_psd">
									确认密码：
								</label>
							</td>
							<td>
								<input type="password" id="passwordConfirm"
									value="${user.loginPassword}" onBlur="checkPassWrodEqual()"
									size="20">
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="msgPassEqual"></div>
							</td>
						</tr>
												
						<!-- 渠道信息，没有数据不显示渠道来源	2013-1-5增加渠道来源 -->
						<c:if test="${!empty channelInfoParList}">
						<tr>
							<td class="td1">
								<label for="re_psd">
									注册来源：
								</label>
							</td>
							<td>
							
							
								<!-- 一级渠道信息 -->
								<select ${!empty channelInfoParentId?"disabled='disabled'":"" } id="channelInfo_ParantID" name="channelInfo_ParantID" style="width: 100px"  onchange="appendChildChannel()">
									<c:forEach items="${channelInfoParList}" var="channelInfoList">
										<option value="${channelInfoList.id }" ${channelInfoList.id==channelInfoParentId?"selected":"" }>${channelInfoList.name }</option>
									</c:forEach>
								</select>
								<!-- 二级渠道信息 -->
								<select ${!empty childChannelId?"disabled='disabled'":"" } style="width: 100px" id="channelInfo_ID" name="channelInfo_ID">
									<c:if test="${!empty childChannelList }">
										<c:forEach items="${childChannelList}" var="childChannelList">
											<option value="${childChannelList.id }" ${childChannelList.id==childChannelId?"selected":""}>${childChannelList.name }</option>
										</c:forEach>
									</c:if>
								</select>
								
								
								
								
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="channelInfoMsg" style="display: none"></div>
							</td>
						</tr>
						</c:if>
						
						<!-- 新增验证码 Ray 2012-11-1 -->
						<tr>
							<td class="td1">
								<label for="re_psd">
									验证码：
								</label>
							</td>
							<td>
								<input type="text" name="validatorImg" id="validatorImg"  onBlur="checkValidatorImg()" style="width:50px;"/>
								<img id="imgObj" alt="验证码图片" src="${ctx}/register/register/getValidatorImg" onclick="changeImg()" class="img_verify" />
								<a onclick="changeImg()">换一张</a>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="validatorImgMsg" style="display: none"></div>
							</td>
						</tr>
						
						<tr class="tr1">
							<td>
								&nbsp;
							</td>
							<td>
								<input id="agreeProvision" type="checkbox" class="checkbox"
									checked="checked" />
								我同意证大e贷
								<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=23" target="_blank">隐私条款</a>
								和
								<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=24" target="_blank">使用条款</a>
							</td>
						</tr>
						<tr class="tr1">
							<td>
								&nbsp;
							</td>
							<td>
								<input id="register" type="submit" value="免费注册" class="login_btn" />
							</td>
						</tr>
						<tr class="tr1">
							<td>
								&nbsp;
							</td>
							<td>
								您有证大e贷会员账号？
								<a href="${ctx}/register/register/login">立即登录</a>
							</td>
						</tr>
					</table>
				</form:form>
				<c:if test="${showFlg == 'show'}">
					<p style="line-height: 1.2em;">
						激活邮件已经发送至您的邮箱：${user.email}，请在24小时登录您的邮箱收件，并点击邮件中激活链接，完成激活。如果24小时内您未能登录邮箱激活账号，邮件将会失效，请您重新提交邮箱。
					</p>
					<a href="${ctx}/register/register/redireEmailSet?email=${user.email}" target="_blank">立即登录激活邮箱</a>
					<p>
						如果30分钟内没有收到激活邮件，请重新发送激活邮件。
					</p>
					<div class="clear"></div>
					<p>

						<input type="text" id="oemail" class="input1"
							value="${user.email}" disabled="disabled">
						&nbsp;&nbsp;
						<a href="#" onclick="sendAgainEmail()">重新发送</a>

					</p>

				</c:if>
			</div>
		</div>
		<div class="clear"></div>

	</body>
</html>