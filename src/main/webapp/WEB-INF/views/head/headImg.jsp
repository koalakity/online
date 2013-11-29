<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="pragma" content="no-cache" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body>
 <c:if test="${empty baseVO.iconPath}" >
 <img src="${ctx}/static/images/nophoto.jpg" id="person" width="100" height="100" />  
 </c:if> 
  <c:if test="${not empty  baseVO.iconPath}" >
   <img src="/pic/${baseVO.iconPath}"  width="100" height="100"/>
   </c:if>   
  <input name="flag" id="flag" type="hidden" value="${baseVO.flag}">
</body>
</html>
