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
 <input name="p0_Cmd" value="${p0_Cmd }" type="hidden">
 <input name="p1_MerId" value="${p1_MerId }" type="hidden">
 <input name="p2_Order" value="${p2_Order }" type="hidden">
 <input name="p3_Amt" value="${p3_Amt }" type="hidden">
 <input name="p4_Cur" value="${p4_Cur }" type="hidden">
 <input name="p5_Pid" value="${p5_Pid }" type="hidden">
 <input name="p6_Pcat" value="${p6_Pcat }" type="hidden">
 <input name="p7_Pdesc" value="${p7_Pdesc }" type="hidden">
 <input name="p8_Url" value="${p8_Url }" type="hidden">
 <input name="p9_SAF" value="${p9_SAF }" type="hidden">
 <input name="pa_MP" value="${pa_MP }" type="hidden">
 <input name="pd_FrpId" value="${pd_FrpId }" type="hidden">
 <input name="r9_BType" value="${r9_BType }" type="hidden">
 <input name="pr_NeedResponse" value="${pr_NeedResponse }" type="hidden">
 <input name="hmac" value="${hmac }" type="hidden">
</form>
</body>
</html>