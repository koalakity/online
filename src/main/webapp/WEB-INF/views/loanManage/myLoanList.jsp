<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
 //分页 add by jihui 2012-11-23 
function fenye(url){
	$.ajaxSetup({
		cache : false
		//关闭AJAX相应的缓存 
	});
	$(".account_r_c").load(
	url,
	function(responseText, textStatus, XMLHttpRequest) {
		$(".account_r_c").html(responseText);
	});
};
</script>
<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">我的借款</h3>
				</div>
				<div class="account_r_c_c">
<c:if test="${empty loanInfoVO.loanInfoList}">
		没有借款！
</c:if>
<c:if test="${not empty loanInfoVO.loanInfoList}">
	<table>
		<tr>
			<th>借款类型</th>
			<th>标题</th>
			<th>金额</th>
			<th>年利率</th>
			<th>期限</th>
			<th>进度</th>
			<th>发布时间</th>
			<th>状态</th>
		</tr>	
	<pg:pager
	url="${ctx}/loanManage/loanManage/showLoanInfoListPage"
	   items="${fn:length(myRepayOffLoanInfoList.loanInfoList)}"
	   index="center"
	   maxPageItems="10"
	   maxIndexPages="10"
	   export="offset,currentPageNumber=pageNumber"
	   scope="request">
	  <pg:param name="index"/>
	  <pg:param name="maxPageItems"/>
	  <pg:param name="maxIndexPages"/>
	<ex:searchresults>
		<c:forEach items="${loanInfoVO.loanInfoList}"  var="loanInfo" varStatus="status">
		<pg:item>
		<tr>
			<td> <c:forEach items="${loanInfo.investInfoList}" var="invest"></c:forEach>						<c:if test="${loanInfo.loanUse==1}"><img src="${ctx}/static/images/img31.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==2}"><img src="${ctx}/static/images/img001.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==3}"><img src="${ctx}/static/images/img30.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==4}"><img src="${ctx}/static/images/img32.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==5}"><img src="${ctx}/static/images/img35.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==6}"><img src="${ctx}/static/images/img34.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==7}"><img src="${ctx}/static/images/img36.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==8}"><img src="${ctx}/static/images/img33.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==9}"><img src="${ctx}/static/images/img37.jpg" width="58" height="56"  /></c:if>
						<c:if test="${loanInfo.loanUse==10}"><img src="${ctx}/static/images/img38.jpg" width="58" height="56"  /></c:if></td>
			<td class="td3"><a target="_blank" href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}">${loanInfo.loanTitleStr }</a></td>
			<td class="col1">${loanInfo.amount }</td><td class="col1">${loanInfo.rate}</td>
			<td class="col1" width="50px">${loanInfo.loanDuration }个月</td>
			<td><span class="raise_s2"><em style="width:${loanInfo.speedProgress };"></em></span>${loanInfo.speedProgress }已完成，${loanInfo.bidNumber}笔投标</td>
			<td>${loanInfo.releaseDateStr} ${loanInfo.releaseTimeStr}</td>
			<td><c:choose><c:when test="${loanInfo.status==1}">招标中</c:when>
					<c:when test="${loanInfo.status==2}"> 招标中</c:when>
					<c:when test="${loanInfo.status==3}"> 流标</c:when>
					<c:when test="${loanInfo.status==4}"> 还款中</c:when>
					<c:when test="${loanInfo.status==5}"> 已结标</c:when>
					<c:when test="${loanInfo.status==8}"> 待审核</c:when>
					<c:when test="${loanInfo.status==6}"> 还款中</c:when>
					<c:when test="${loanInfo.status==7}"> 还款中</c:when>
				</c:choose>
			</td>
		</tr>
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
					</table>
					</c:if>
				</div>
			</div>
<div class="clear"></div>
