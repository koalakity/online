<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<table class="m_defined" style="border:none; width:100%">
	<tr>
		<td>时间</td><td>类型</td><td>存入</td><td>支出</td>
	</tr>
	<c:forEach items="${fundFlowVO.flowList}" var="flow">
		
	<tr>
		
		<td><fmt:formatDate value="${flow.date}" type="both"/></td>
		<td>${flow.type}</td>
		<td>
			<c:if test="${!empty flow.in && flow.in!=0}">
				￥<fmt:formatNumber  value="${flow.in+ 0.0001 }"  pattern="#,###,###,###.##"/>
			</c:if>
		</td>
		<td>
			<c:if test="${!empty flow.out && flow.out!=0}">
				￥<fmt:formatNumber  value="${flow.out+ 0.0001 }"  pattern="#,###,###,###.##"/>
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
