<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />



	<div class="account_r_c_c">
		<div class="account_r_c_c3">
			<table>
				<tr><th>借款类型</th><th>标题</th><th>金额</th><th>年利率</th><th>期限</th><th>进度</th><th>发布时间</th></tr>
				
				<pg:pager url="${ctx}/homepage/homepage/showLoanNote"
					items="${fn:length(loanList)}" index="center" maxPageItems="10"
					maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="index" />
					<pg:param name="maxPageItems" />
					<pg:param name="maxIndexPages" />
					<ex:searchresults>
				
				<c:forEach items="${loanList}" var="loanInfo">
				<pg:item>
				<tr>
					<td>
						<c:if test="${loanInfo.loanUse == 1}">
							<img src="${ctx}/static/images/img31.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 2}">
							<img src="${ctx}/static/images/img001.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 3}">
							<img src="${ctx}/static/images/img30.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 4}">
							<img src="${ctx}/static/images/img32.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 5}">
							<img src="${ctx}/static/images/img35.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 6}">
							<img src="${ctx}/static/images/img34.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 7}">
							<img src="${ctx}/static/images/img36.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 8}">
							<img src="${ctx}/static/images/img33.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 9}">
							<img src="${ctx}/static/images/img37.jpg" width="58" height="56" />
						</c:if>
						<c:if test="${loanInfo.loanUse == 10}">
							<img src="${ctx}/static/images/img38.jpg" width="58" height="56" />
						</c:if>
					</td>
					<td class="td3">
						<a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}">${loanInfo.loanTitleStr}</a>
					</td>
					<td class="col1">
						${loanInfo.loanAmountStr}
					</td>
					<td class="col1">
						${loanInfo.lilv}
					</td>
					<td class="col1">
						${loanInfo.loanQx}个月
					</td>
					<td>
						<span class="raise_s2"><em style="width: ${loanInfo.progress};"></em>
						</span>${loanInfo.progress}已完成，${loanInfo.items}笔投标
					</td>
					<td>
						${loanInfo.releaseDate}
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

