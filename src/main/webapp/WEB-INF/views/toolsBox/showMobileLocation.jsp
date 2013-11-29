<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

//手机号码信息显示
function checkMobile(){
var phone = $("#m").val();
var isMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
var length = phone.length;
if (length != 11 || !isMobile.test(phone)) {
    alert("请正确填写您的手机号码");
    return false;
}else{
	$("#formLocating").submit();
}
}
</script>

<div class="prompt5">
	<strong class="col2 font_14">手机号码查询</strong><br />
					在下面的输入框输入您要查询的手机号码，点查询就会显示该手机号码所在地区。
</div>
<table class="table9">
					<tr><th colspan="2">查询</th></tr>
					<tr><td width="300"><form name="formLocating" id="formLocating" action="http://guishu.showji.com/search.htm" method="GET" target="_blank"><a href="#" id="query_phone" class="btn1 fr" onclick="checkMobile()">查询</a>手机号码：<input type="text" name="m" id="m"/></form></td><td>&nbsp;</td></tr>
</table>
