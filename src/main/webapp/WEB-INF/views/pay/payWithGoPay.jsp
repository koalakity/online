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
 <input name="version" value="${version }" type="hidden">
 <input name="language" value="${language }" type="hidden">
 <input name="tranCode" value="${tranCode }" type="hidden">
 <input name="merchantID" value="${merchantID }" type="hidden">
 <input name="merOrderNum" value="${merOrderNum }" type="hidden">
 <input name="tranAmt" value="${tranAmt }" type="hidden">
 <input name="currencyType" value="${currencyType }" type="hidden">
 <input name="frontMerUrl" value="${frontMerUrl }" type="hidden">
 <input name="backgroundMerUrl" value="${backgroundMerUrl }" type="hidden">
 <input name="tranDateTime" value="${tranDateTime }" type="hidden">
 <input name="virCardNoIn" value="${virCardNoIn }" type="hidden">
 <input name="tranIP" value="${tranIP }" type="hidden">
 <input name="isRepeatSubmit" value="${isRepeatSubmit }" type="hidden">
 <input name="merRemark1" value="${merRemark1 }" type="hidden">
 <input name="bankCode" value="${bankCode }" type="hidden">
 <input name="userType" value="${userType }" type="hidden">
 <input name="goodsName" value="${goodsName }" type="hidden">
 <input name="signValue" value="${signValue }" type="hidden">
</form>
</body>
</html>