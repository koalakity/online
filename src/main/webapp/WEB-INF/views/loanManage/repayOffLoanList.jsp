<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:if test="${empty myRepayOffLoanInfoList.repayOffLoanInfoList}">
		没有已还清的借款！
</c:if>
<c:if test="${not empty myRepayOffLoanInfoList.repayOffLoanInfoList}">
<ul>
<pg:pager
url="${ctx}/loanManage/loanManage/findMyRepayOffListPage"
   items="${fn:length(myRepayOffLoanInfoList.repayOffLoanInfoList)}"
   index="center"
   maxPageItems="10"
   maxIndexPages="10"
   export="offset,currentPageNumber=pageNumber"
   scope="request">
  <pg:param name="index"/>
  <pg:param name="maxPageItems"/>
  <pg:param name="maxIndexPages"/>
	<ex:searchresults>
	<c:forEach items="${myRepayOffLoanInfoList.repayOffLoanInfoList}" var="repayOffLoanInfo" varStatus="status">
	<pg:item>
		<c:choose>
			<c:when test="${status.count %2 ==1 }">
				<li>
					<table>
						<tr><th colspan="3" class="tl">${repayOffLoanInfo.loanTitle }<strong class="col5">[已结标]</strong></th><th class="tr"><a href="${ctx}/myAccount/myAccount/showContract?loanId=${repayOffLoanInfo.loanId}" target="_blank">查看借款协议</a> <a href="${ctx}/myAccount/myAccount/showRiskContract?loanId=${repayOffLoanInfo.loanId}" target="_blank">查看风险基金协议</a></th></tr>
						<tr><td>借款金额：<span class="col1">${repayOffLoanInfo.loanAmount }</span></td><td>年利率：<span class="col1">${repayOffLoanInfo.yearRate }</span></td><td>期限：<span class="col1">${repayOffLoanInfo.loanDuration }</span></td></tr>
						<tr><td>已还款总额：<span class="col1">${repayOffLoanInfo.repayOffLoanAmount }</span></td><td colspan="3">待还余额：<span class="col1">${repayOffLoanInfo.waitRepayBalanceAmount}</span></td></tr>
					</table>
				</li>
			</c:when >
			<c:otherwise>
				<li  class="even">
					<table>
						<tr><th colspan="3" class="tl">${repayOffLoanInfo.loanTitle }<strong class="col5">[已结标]</strong></th><th class="tr"><a href="${ctx}/myAccount/myAccount/showContract?loanId=${repayOffLoanInfo.loanId}" target="_blank">查看借款协议</a> <a href="${ctx}/myAccount/myAccount/showRiskContract?loanId=${repayOffLoanInfo.loanId}" target="_blank">查看风险基金协议</a></th></tr>
						<tr><td>借款金额：<span class="col1">${repayOffLoanInfo.loanAmount }</span></td><td>年利率：<span class="col1">${repayOffLoanInfo.yearRate }</span></td><td>期限：<span class="col1">${repayOffLoanInfo.loanDuration }</span></td></tr>
						<tr><td>已还款总额：<span class="col1">${repayOffLoanInfo.repayOffLoanAmount }</span></td><td colspan="3">待还余额：<span class="col1">${repayOffLoanInfo.waitRepayBalanceAmount}</span></td></tr>
					</table>
				</li>
			</c:otherwise>
		</c:choose>
		</pg:item>
	</c:forEach>
	
			</ex:searchresults>
				<table border=0 cellpadding=0 width=10% cellspacing=0>
				<tr align=center valign=top>
				<td valign=bottom>
				  <pg:index export="total=itemCount">
				    <font face=arial,sans-serif size=-1 class="pagelist">
				    <pg:first>
				    	  共<%= total %>条&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">首页</a>
				    </pg:first>
				      <pg:prev>&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">上一页</a></pg:prev>
				    <pg:pages><%
				      if (pageNumber.intValue() < 10) { 
				        %>&nbsp;<%
				      }
				      if (pageNumber == currentPageNumber) { 
				        %><b><%= pageNumber %></b><%
				      } else { 
				        %><a href="#" onclick="fenye('<%=pageUrl%>')"><%= pageNumber %></a><%
				      }
				    %>
				    </pg:pages>
				    <pg:next>&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">下一页</a></pg:next>
				    
					<pg:last>
				      <a href="#" onclick="fenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%= pageNumber %>页
				    </pg:last>
				    
				    <br></font>
				  </pg:index>
				</td>
				</tr>
				</table>
		    </pg:pager>
</ul>
</c:if>

