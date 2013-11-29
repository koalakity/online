<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="account_r_c_c">
	<div class="account_r_c_c3">
		<table>
			<tr><th>投标标题</th><th>年利率</th><th>投标金额</th><th>借款期限</th><th>状态</th><th>投标时间</th><th>放款时间</th></tr>


				<pg:pager url="${ctx}/homepage/homepage/showInvestNote"
					items="${fn:length(invList)}" index="center" maxPageItems="10"
					maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="index" />
					<pg:param name="maxPageItems" />
					<pg:param name="maxIndexPages" />
					<ex:searchresults>
				
				<c:forEach items="${invList}" var="loanInfo">
				<pg:item>
				<tr>
					<td class="td3">
						<a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}">${loanInfo.loanTitle}</a>
					</td>
					<td class="col1">
						${loanInfo.lilv}
					</td>
					<td class="col1">
						${loanInfo.loanAmountStr}
					</td>
					<td class="col1">
						${loanInfo.loanQx}个月
					</td>
					<td>
						<c:if test="${loanInfo.loanKind == 1}">
							正在进行中
						</c:if>
						<c:if test="${loanInfo.loanKind == 2}">
							正在进行中
						</c:if>
						<c:if test="${loanInfo.loanKind == 3}">
							流标
						</c:if>
						<c:if test="${loanInfo.loanKind == 4}">
							成功
						</c:if>
						<c:if test="${loanInfo.loanKind == 5}">
							成功
						</c:if>
						<c:if test="${loanInfo.loanKind == 6}">
							成功
						</c:if>
						<c:if test="${loanInfo.loanKind == 7}">
							成功
						</c:if>
					</td>
					<td>
						${loanInfo.releaseDate}
					</td>
					<td>
						${loanInfo.loanDate}
					</td>
				</tr>
				</pg:item>
				</c:forEach>
				
					</ex:searchresults>
					<table border=0 cellpadding=0 width=10% cellspacing=0>
						<tr align=center valign=top>
							<td valign=bottom>
								<pg:index export="total=itemCount">
									<font face=arial,sans-serif size=-1 class="pagelist"> <pg:first>
				    	  共<%=total%>条&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">首页</a>
										</pg:first> <pg:prev>&nbsp;<a href="#"
												onclick="fenye('<%=pageUrl%>')">上一页</a>
										</pg:prev> <pg:pages>
											<%
												if (pageNumber.intValue() < 10) {
											%>&nbsp;<%
												}
															if (pageNumber == currentPageNumber) {
											%><b><%=pageNumber%></b>
											<%
												} else {
											%><a href="#" onclick="fenye('<%=pageUrl%>')"><%=pageNumber%></a>
											<%
												}
											%>
										</pg:pages> <pg:next>&nbsp;<a href="#"
												onclick="fenye('<%=pageUrl%>')">下一页</a>
										</pg:next> <pg:last>
											<a href="#" onclick="fenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%=pageNumber%>页
				    </pg:last> <br> </font>
								</pg:index>
							</td>
						</tr>
					</table>
				</pg:pager>







		</table>
	</div>
</div>
	<div class="clear"></div>

