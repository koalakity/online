<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/static/css/login.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/message.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>

<title>成为理财人</title>
<script type="text/javascript">
    $(function(){
    	//聚焦第一个输入框
   	$("#realName").focus();
    	//顶部菜单导航切换
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
    });
  
</script> 
<script type="text/javascript">
function mysubmit(){
	var status = $("#inputForm").valid();
	alert(status);
	if(status){
		return document.getElementById("inputForm").submit();
	}
}

//弹出层调用
function showfancy(gourl,wd,hg){
	$.ajax({
	    url: gourl,
		wd: wd,
        hg: hg,
		dataType: "html",
		error:function(html){
		},
		success : function(html) {
			$.fancybox(html, {
				modal : true,
				autoDimensions : false,
				width : wd,
				height : hg,
				padding : 0,
				margin : 0,
				centerOnScroll : true
			});
		}
	})
}
//关闭弹出层
function closefancy(){
    $.fancybox.close();
};
function redirectfancy(){
	window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay";
}
function nameRemind(mname) {
	mname.innerHTML = "4-20个字符，只能输入汉字";
}
function IdCardRemind(msgIdCard) {
	msgIdCard.innerHTML = "请输入15位或18位身份证号码。";
}
function phoneRemind(msgPhone){
	msgPhone.innerHTML = "请输入11位手机号码";
}
//姓名验证
function checkRealName() {
	var realName = document.getElementById("realName").value;
	var mt = document.getElementById("msgName");
	var reg =/[^\u4e00-\u9fa5•·]/; 
	if (realName == "") {
		mt.innerHTML = "姓名不能为空";
		return false;
	} else {
	if(checkstr(realName)>= 4 && checkstr(realName)<=20){
		if(reg.test(realName)){
			mt.innerHTML = "请输入汉字";
			return false;
		}else{
			mt.innerHTML = "";
			return true;
		}
	}else{
		mt.innerHTML = "4-20个字符，只能输入汉字";
		return false;
	}
	}
}
//身份证验证
function checkIdCard(){
	var idcard=$("#idCardNumber").val();	
	var mt = document.getElementById("msgIdCard");
	var value=$.trim(idcard);
    if(!checkId(idcard)){       
     	mt.innerHTML = "请正确填写您的身份证号码！";
      	return false;
    }else{
		mt.innerHTML = "";
        return true;
    }
}


//判别身份证号码是否合法,入口参数为身份证号码
function checkId(varInput){
if(varInput==null || varInput.trim()==""){
return false;
}

//varInput = varInput.trim();
if(varInput.length!=18 && varInput.length!=15){
return false;
}
var ret = convertID(varInput);
// alert(ret);
if(ret == false){
return false;
}
else if(varInput.length==18 && varInput!=ret){
return false;
}
else{
//返回值可以自动升级18位身份证号
//return ret;
// alert("正确");
return true;
}
}
/*
*15身份证号码升18位,入口参数0为15身份证号码,返回值为18位身份证号码
*如果证号错误则返回false
*Junsan Jin 20050902
*/
function convertID(varInput){


if(varInput==null || varInput.trim()==""){
return false;
}
var strOldID = new String(varInput.trim());
var strNewID = "";
if(strOldID.length==15){


for(i=0; i<15; i++){


//15位的身份证号必须全部由数字组成，否则，视为非法


if(checkZInt(strOldID.substring(i,1))){
return false;
}
}
//取得身份证中的年月日
var year = "19" + strOldID.substr(6,2);
// alert(year);
var month = strOldID.substr(8,2);
// alert(month);
var day = strOldID.substr(10,2);
// alert(day);
//校验日期是否正确
if(checkDate(year,month,day)){ 
return false;
} 
strNewID = strOldID.substring(0,6) + "19" + strOldID.substring(6,15);
}
else if(strOldID.length==18){
for(i=0; i<17; i++){
//15位的身份证号必须全部由数字组成，否则，视为非法
if(checkZInt(strOldID.substring(i,1))){
return false;
}
}
if(strOldID.substring(17,18).toUpperCase!="X" && checkZInt(strOldID.substring(17,18))){
return false;
}
//取得身份证中的年月日
var year = "19" + strOldID.substr(6,4);
var month = strOldID.substr(10,2);
var day = strOldID.substr(12,2);
//校验日期是否正确
if(checkDate(year,month,day)){
return false;
}
strNewID = strOldID.substring(0,17);
}
else if(strOldID.length==17){
for(i=0; i<17; i++){
//15位的身份证号必须全部由数字组成，否则，视为非法
if(checkZInt(strOldID.substring(i,1))){
return false;
}
}
//取得身份证中的年月日
var year = "19" + strOldID.substr(6,4);
var month = strOldID.substr(10,2);
var day = strOldID.substr(12,2);
//校验日期是否正确
if(checkDate(year,month,day)){
return false;
}
strNewID = strOldID;
}
return strNewID = strNewID + createCK(strNewID);


}
/*
*根据17位的身份证号得到最后一位校验码
*strID：身份证号前17位
*/
function createCK(strID){
var s = 0;
var WI = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
var AI = "10X98765432";
for(i=0; i<17; i++){
j = strID.substr(i,1) * WI[i];
s = s + j;
}
s = s % 11;
return AI.substr(s,1);
}

/*
*主要提供对日期的精确校验，验证日期是否合法
*非法返回true,合法返回false
*Junsan Jin 20050902
*参数说明：
*year：年
*month：月
*day：日
*/
function checkDate(year,month,day){ 
var flag=false;
var time=new Date(year,month-1,day);
// alert(time);
var e_year=time.getFullYear();
// alert(e_year);
var e_month=time.getMonth()+1;
// alert(e_month);
var e_day=time.getDate();
// alert(e_day);
if(year!=e_year||month!=e_month||day!=e_day)
{
flag=true;
}
return flag;
}
/*
*检查输入的串是否在0到9之间的字符组成
*不是则返回true，如果是则返回false
*Junsan Jin 20050902
*/
function checkZInt(str){
var reg = /^\d+$/;
if(arr=str.match(reg))
{
//全部是数字
return false;
}
else
{
//含有其他字符
return false;
}
}
/*


*字符串去掉左右空格的方法
*Junsan Jin 20050902
*/
String.prototype.trim = function(){
return this.replace(/(^\s*)|(\s*$)/g, "");
}
/*


*字符串去掉左空格的方法
*Junsan Jin 20050902
*/
String.prototype.ltrim = function(){
return this.replace(/(^\s*)/g, "");
}


/*
*字符串去掉右空格的方法
*Junsan Jin 20050902
*/
String.prototype.rtrim = function(){
return this.replace(/(\s*$)/g, "");
}



var bflg = true;	
//手机号码验证
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
	var mt = document.getElementById("verifyCode");
	var validateCode = $("#validateCode").val();
	var code = /^[0-9]*$/;
	if(validateCode.length!=6||!code.test(validateCode)){
		mt.innerHTML = "请输入6位数字手机验证码";
		return false;
	}else{
		mt.innerHTML = "";
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
	var mt = document.getElementById("msgPhone");
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
		    	mt.innerHTML="发送失败，请稍后重试";
				 obj.removeAttribute("disabled");
		    	return;
	        },
	       	success: function(response){
				if(response.sendResult=="ok"){
					mt.innerHTML="";
			    	alert("恭喜您，您的手机号码已成功发送，请注意查收验证码。");
					// obj.removeAttribute("disabled");
					updateinfo();
			    	return;
				}else{
					mt.innerHTML="发送失败，请稍后重试";
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

//判断汉字为2字节
function checkstr(str) {
	num=str.length;
	var arr=str.match(/[^\\\\\\\\\\\\\\\\x00-\\\\\\\\\\\\\\\\x80]/ig);
	if(arr!=null)num+=arr.length;
	return num;
} 
//提交表单
function licaiSubmit(){
	var obj = $("#beLenderbu").attr("disabled","disabled");
		//document.getElementById("beLenderbu");
	//obj.disabled="disabled";
if(checkRealName()&& checkIdCard()&& checkTelphone()&& checkVerifyCode()){
 	$.ajax({
 		 data: $("#inputForm").serialize(),
 		 url: "${ctx}/financial/beLender/validateIdCardAndPhone",
 	 	 type: "POST",
 		 dataType: 'json',
 		 timeout: 10000,
  	 	error: function(){
  	 		$("#beLenderbu").removeAttr("disabled");
  	 	 //obj.removeAttribute("disabled");
  	 	 return;
      },
      	error:function(data){
      		alert("验证超时！请稍后再试！");
      		$("#beLenderbu").removeAttr("disabled");
      	},
     	success: function(data){
         	if(data.status=="noSend"){
				alert("您还没有获得手机验证码,请先点击发送手机验证码获得手机验证码！");
				$("#beLenderbu").removeAttr("disabled");
				return;
            }else if(data.status=="error"){
				alert("手机验证码输入错误,请您确认后重新输入！");
				$("#beLenderbu").removeAttr("disabled");
				return;
			}else if(data.status=="idCardBind"){
				alert("验证失败，该身份证已被绑定！咨询热线：4008216888！");
				$("#beLenderbu").removeAttr("disabled");
				return;
			}else if(data.status=="fail"){
				alert("校验失败！请填写正确的姓名和身份证号码！");
				$("#beLenderbu").removeAttr("disabled");
				return;
			}else if(data.status=="noEnough"){
            	showfancy("${ctx}/financial/beLender/showfancy",'352','210');
            	
			}else if(data.status=="overtime"){
			   alert("校验失败！24小时内未验证，验证码失效！");
			   $("#beLenderbu").removeAttr("disabled");
			   return;
			}else{
				window.location.href="${ctx}/financial/beLender/redirectBelender";
				}

     	},
      beforeSend: function(){
      }
	})
}else{
	$("#beLenderbu").removeAttr("disabled");
	return;
}
	
}
</script>
	<div class="content login_bg3">
		<div class="login_box3">
			<h3>${!empty showInfo?showInfo:"成为理财人" }</h3>
			<form:form id="inputForm" name="inputForm" modelAttribute="licaiUser"  method="post" action="${ctx}/financial/beLender/validateIdCardAndPhone" >
			<table>
				<tr>
					<td class="td1"><label for="name">姓名：</label></td>
					<td><input ${!empty userInfoPerson.realName?"disabled='disabled'":'' }  value="${userInfoPerson.realName }" type="text" name="realName" id="realName" class="required" onclick="nameRemind(msgName)" onBlur="checkRealName(this.value)"/>
					<input value="${userInfoPerson.realName }" type="hidden" name="realNameCopy" id="realNameCopy"/>
					
					</td>
				</tr>
				<tr><td>&nbsp;</td><td class="col1"><div id="msgName"></div></td></tr>
				
				<tr>
				<td class="td1"><label for="email">身份证号：</label></td>
				<td><input ${!empty userInfoPerson.identityNo?"disabled='disabled'":'' } value="${userInfoPerson.identityNo }" type="text" name=idCardNumber id="idCardNumber" onclick="IdCardRemind(msgIdCard)" onBlur="checkIdCard()"/>
				<input value="${userInfoPerson.identityNo }" type="hidden" name="idCardNumberCopy" id="idCardNumberCopy"/>
				</td>
				</tr>
				<tr><td>&nbsp;</td><td class="col1"><div id="msgIdCard"></div></td></tr>
				
				<tr>
				<td class="td1"><label for="psd">手机号码：</label></td>
				<td><input value="${userInfoPerson.phoneNo }" type="text" name="phoneNumber" id="phoneNumber"  onclick="phoneRemind(msgPhone)" onBlur="checkTelphone()" /></td>
				</tr>
				<tr><td>&nbsp;</td><td class="col1"><div id="msgPhone"></div></td></tr>
				<tr><td class="td1"><label for="re_psd">验证码：</label></td><td><input type="text" name="validateCode" id="validateCode" style="width:80px;"/><input type="button" class="yzm" id="yzm" value="发送手机验证码" onclick="sendYzm()" style="float:none; margin-left:10px"></td></tr>
				<tr><td>&nbsp;</td><td class="col1"><div id="verifyCode"></div></td></tr>
				<tr class="tr1"><td>&nbsp;</td><td><input type="button" value="${!empty showInfo?showInfo:'成为理财用户' }" class="btn4" onclick="licaiSubmit()" name="beLenderbu" id="beLenderbu" />&nbsp;&nbsp;<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay">我要充值</a></td></tr>
			</table>
			<input type="hidden" name="token" id="token" value="${sessionScope.token}" />
			<input type="hidden" name="ptoken" id="ptoken" value="${context.token}" />
		</form:form>
			<p class="col3">尊敬的用户：请务必填写您的真实姓名及身份证号！身份验证系统审核成本为人民币5元/次，在提交认证前请确保您的账户余额不低于5元。 以上所填写的手机号将与您的账号绑定，本手机号将作为证大e贷与您确认交易内容的途径之一，请使用您的常用手机号码进行绑定，您的手机号码不会以任何形式被泄露。</p>
			<p class="col3">注：如果您收不到手机验证码，请联系<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=17&category=首页&sort=联系我们&c_sort=在线客服">证大e贷客服。</a></p>
		</div>
		</div>