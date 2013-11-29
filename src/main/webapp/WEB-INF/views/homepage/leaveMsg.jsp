<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<div class="account_r_c_c">
	<div class="account_r_c_c4">
		<table>
			<tr>
				<th>
					标题
				</th>
				<th>
					状态
				</th>
				<th>
					留言条数
				</th>
				<th>
					最后留言时间
				</th>
			</tr>



			<pg:pager url="${ctx}/homepage/homepage/showLeaveMsg"
				items="${fn:length(msgList)}" index="center" maxPageItems="10"
				maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
				scope="request">
				<pg:param name="index" />
				<pg:param name="maxPageItems" />
				<pg:param name="maxIndexPages" />
				<ex:searchresults>

					<c:forEach items="${msgList}" var="msg">
						<pg:item>

							<tr>
								<td class="td2">
									<c:if test="${msg.loanKind == 1}">
										[借款标]&nbsp;
									</c:if>
									<c:if test="${msg.loanKind == 2}">
										[理财标]&nbsp;
									</c:if>
									<a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${msg.loanId}">${msg.title}</a>
								</td>
								<td class="font_14">
									<c:if test="${msg.status == 3}">
										流标
									</c:if>
									<c:if test="${msg.status == 1 ||msg.status == 2}">
										招标中
									</c:if>
									<c:if test="${msg.status == 4 || msg.status == 6 ||msg.status == 7}">
										还款中
									</c:if>
									<c:if test="${msg.status == 5}">
										已结标
									</c:if>
								</td>
								<td>
									${msg.leaveCount}
								</td>
								<td>
									${msg.lastLeaveTime}
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

