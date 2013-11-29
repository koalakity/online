<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="keywords" content="网络借贷,网络贷款,借贷,网络平台,民间借贷,小额贷款,无抵押贷款,信用贷款,投资理财,微金融,证大e贷,上海证大,证大" />
<meta name="decription" content="证大e贷 - 最大、最安全的网络借款、网络理财微金融服务平台。提供便捷、安全、低门槛的个人信用贷款和个人理财服务"  />
<title>证大e贷 微金融服务平台</title>

<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/news.css" type="text/css" /> 
<link href="${ctx}/static/jquery-validation/1.9.0/validate.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/messages_cn.js" type="text/javascript"></script>
<!--[if lte IE 6]>
<script type="text/javascript" src="${ctx}/static/js/belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix("ul.nav li a, h3, .other_login img, .p_login_btn input, .p_login_btn a, .c_service");
</script>
<![endif]-->


<sitemesh:head/>
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/views/layouts/header.jsp"%>
		<div>
			<sitemesh:body/>
		</div>
		<%@ include file="/WEB-INF/views/layouts/footer.jsp"%>
	</div>
</body>
</html>