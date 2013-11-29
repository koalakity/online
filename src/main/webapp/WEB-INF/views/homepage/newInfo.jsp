<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


	<div class="account_r_c_c">
		<div class="account_r_c_c1">
			<ul>
				<pg:pager url="${ctx}/homepage/homepage/newInfo"
					items="${fn:length(mvList)}" index="center" maxPageItems="10"
					maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="index" />
					<pg:param name="maxPageItems" />
					<pg:param name="maxIndexPages" />
					<ex:searchresults>

						<c:forEach items="${mvList}" var="mw">
							<pg:item>
								<li>
									<span>${mw.rDate}</span>${mw.wordContext1}
								</li>
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
			</ul>

		</div>
	</div>
<div class="clear"></div>

