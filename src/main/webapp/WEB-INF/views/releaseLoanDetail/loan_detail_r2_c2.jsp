<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/loan.css" type="text/css" /> 
<p class="p1 mar6">*证大e贷将以客观、公正的原则，最大程度地核实借入者信息的真实性。但证大e贷不保证审核信息的百分百真实。如果借入者长期逾期，借入者提供的信息将被公布。</p> 
<c:if test="${not empty brCreditRecord.userApproveList}">
<table>
	<tr><th>审核项目</th><th>状态</th><th>通过时间</th></tr>
	<c:forEach items="${brCreditRecord.userApproveList}" var="approve">
	<tr>
		<td class="tl">${approve.proId.proName}</td>
		<td>
			<c:if test="${approve.proStatus==0}"><img src="${ctx}/static/images/img6.jpg" width="20" title="待上传" height="20" /></c:if>
			<c:if test="${approve.proStatus==1}">
				<c:if test="${approve.reviewStatus==0}"><img src="${ctx}/static/images/img4.jpg"  title="审核中" width="20" height="20" /></c:if>
				<c:if test="${approve.reviewStatus==2}"><img src="${ctx}/static/images/img4.jpg"  title="审核中" width="20" height="20" /></c:if>
				<c:if test="${approve.reviewStatus==1}"><img src="${ctx}/static/images/img5.jpg"  title="通过认证" width="20" height="20" /></c:if>
			</c:if>
		</td>
		<td>${approve.auditTimeStr}</td>
	</tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${empty brCreditRecord.userApproveList}">没有审核项目</c:if>