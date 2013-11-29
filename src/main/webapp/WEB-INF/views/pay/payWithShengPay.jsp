<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
$("#form1").submit();
});
</script>
</head>
<body>
<form id="form1" action="${formUrl }" method="POST">
 <input name="Name" value="${Name }" type="hidden">
 <input name="Version" value="${Version }" type="hidden">
 <input name="Charset" value="${Charset }" type="hidden">
 <input name="MsgSender" value="${MsgSender }" type="hidden">
 <input name="SendTime" value="${SendTime }" type="hidden">
 <input name="OrderNo" value="${OrderNo }" type="hidden">
 <input name="OrderAmount" value="${OrderAmount }" type="hidden">
 <input name="OrderTime" value="${OrderTime }" type="hidden">
 <input name="PayType" value="${PayType }" type="hidden">
 <input name="InstCode" value="${InstCode }" type="hidden">
 <input name="PageUrl" value="${PageUrl }" type="hidden">
 <input name="NotifyUrl" value="${NotifyUrl }" type="hidden">
 <input name="ProductName" value="${ProductName }" type="hidden">
 <input name="BuyerContact" value="${BuyerContact }" type="hidden">
 <input name="BuyerIp" value="${BuyerIp }" type="hidden">
 <input name="Ext1" value="${Ext1 }" type="hidden">
 <input name="SignType" value="${SignType }" type="hidden">
 <input name="SignMsg" value="${SignMsg }" type="hidden">
</form>
</body>
</html>