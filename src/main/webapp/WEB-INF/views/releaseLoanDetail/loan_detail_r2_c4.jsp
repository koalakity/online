<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/loan.css" type="text/css" />
<c:if test="${not empty currInvestInfo.investInfoList}">

	<tr>
		<td>
			<div class="div1">
				目前投标总额：<span class="col1">${currInvestInfo.currentBidAmount}</span>&nbsp;&nbsp;
				剩余投标金额：<span class="col1">${currInvestInfo.surplusAmount}</span>&nbsp;&nbsp;		
			</div>
		</td>
		
	</tr>

<table>
	<tr><th>投标人</th><th>投标金额</th><th>投标时间</th></tr>
	<c:forEach items="${currInvestInfo.investInfoList}"  var="invest">
	<tr><td>${invest.user.loginName}</td><td>${invest.investAmount}</td><td>${invest.investDate}</td></tr>
	</c:forEach>
</table>
</c:if>	
<c:if test="${empty currInvestInfo.investInfoList}">还没有投过此标</c:if>
