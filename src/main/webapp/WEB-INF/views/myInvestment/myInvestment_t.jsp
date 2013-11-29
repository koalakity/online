<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

			
		<c:if test="${empty inverstInfoList}">
		投标中的借款为空
	</c:if>
	<c:if test="${not empty inverstInfoList}">
		<div class="account_r_c_3c1">
			<table class="table5">
				<tr>
					<th>
						借款类型
					</th>
					<th>
						标题/借款人/所在地
					</th>
					<th>
						借款期限
					</th>
					<th>
						年利率
					</th>
					<th>
						信用等级
					</th>
					<th>
						我的投标金额
					</th>
					<th>
						进度/剩余时间
					</th>
				</tr>
			<pg:pager
url="${ctx}/myInvestment/myInvestment/showPage"
   items="${fn:length(inverstInfoList)}"
   index="center"
   maxPageItems="6"
   maxIndexPages="10"
   export="offset,currentPageNumber=pageNumber"
   scope="request">
  <pg:param name="index"/>
  <pg:param name="maxPageItems"/>
  <pg:param name="maxIndexPages"/>
	<ex:searchresults>
				<c:forEach items="${inverstInfoList}" var="inverstInfo">
				<pg:item>
					<tr>
						<td>
							<c:if test="${inverstInfo.loanId.loanUse == 1}">
								<img src="${ctx}/static/images/img31.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 2}">
								<img src="${ctx}/static/images/img001.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 3}">
								<img src="${ctx}/static/images/img30.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 4}">
								<img src="${ctx}/static/images/img32.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 5}">
								<img src="${ctx}/static/images/img35.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 6}">
								<img src="${ctx}/static/images/img34.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 7}">
								<img src="${ctx}/static/images/img36.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 8}">
								<img src="${ctx}/static/images/img33.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 9}">
								<img src="${ctx}/static/img37.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${inverstInfo.loanId.loanUse == 10}">
								<img src="${ctx}/static/images/img38.jpg" width="58" height="56" />
							</c:if>
						</td>
						<td>
							<a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${inverstInfo.loanId.loanId}">${inverstInfo.loanId.loanTitle}</a>
							<br />
							${inverstInfo.loanId.userId.loginName}&nbsp;&nbsp;${inverstInfo.area}
						</td>
						<td class="col1">
							${inverstInfo.loanId.loanDuration}个月
						</td>
						<td class="col1">
						<fmt:formatNumber value="${inverstInfo.loanId.yearRate*100}" pattern="#0.00"/> 
							%
						</td>
						<td>
							<c:if test="${inverstInfo.loanId.userId.myInvestmentUserCreditNote.creditGrade == 1}">
								<img src="${ctx}/static/images/img28.gif" />
							</c:if>
							<c:if test="${inverstInfo.loanId.userId.myInvestmentUserCreditNote.creditGrade == 2}">
								<img src="${ctx}/static/images/img27.gif" />
							</c:if>
							<c:if test="${inverstInfo.loanId.userId.myInvestmentUserCreditNote.creditGrade == 3}">
								<img src="${ctx}/static/images/img26.gif" />
							</c:if>
							<c:if test="${inverstInfo.loanId.userId.myInvestmentUserCreditNote.creditGrade == 4}">
								<img src="${ctx}/static/images/img25.gif" />
							</c:if>
							<c:if test="${inverstInfo.loanId.userId.myInvestmentUserCreditNote.creditGrade == 5}">
								<img src="${ctx}/static/images/img24.gif" />
							</c:if>
							<c:if test="${inverstInfo.loanId.userId.myInvestmentUserCreditNote.creditGrade == 6}">
								<img src="${ctx}/static/images/img23.gif" />
							</c:if>
							<c:if test="${inverstInfo.loanId.userId.myInvestmentUserCreditNote.creditGrade == 7}">
								<img src="${ctx}/static/images/img22.gif" />
							</c:if>
						</td>
						<td class="col1">
							￥${inverstInfo.investAmount}
						</td>
						<td class="td11">
							<div class="raise_s mar9 fl">
								<span style="width: ${inverstInfo.tempo}%;"></span><em>${inverstInfo.tempo}%</em>
							</div>
							<span class="fl">&nbsp;已完成</span>
							<br />
							剩余时间:${inverstInfo.leaveDate}
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
			<p class="pagelist">



			</p>
		</div>
	</c:if>
				
	<div class="clear"></div>
