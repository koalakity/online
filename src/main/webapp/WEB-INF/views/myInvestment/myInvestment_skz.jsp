<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="account_r_c_3c2">
	<table class="table4">
		<tr>
			<th>
				借款编号
			</th>
			<th>
				借款人
			</th>
			<th>
				最近还款日期
			</th>
			<th>
				月收本息
			</th>
			<th>
				剩余本金
			</th>
			<th>
				逾期天数
			</th>
			<th>
				罚息
			</th>
			<th>
				合同
			</th>
		</tr>

		<pg:pager url="${ctx}/myInvestment/myInvestment/showSkzLoan"
			items="${fn:length(skzVoList)}" index="center" maxPageItems="10"
			maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
			scope="request">
			<pg:param name="index" />
			<pg:param name="maxPageItems" />
			<pg:param name="maxIndexPages" />
			<ex:searchresults>
				<c:forEach items="${skzVoList}" var="skz">
					<pg:item>
						<tr>
							<td>
								${skz.loanNo }
							</td>
							<td>
								${skz.loanAcc }
							</td>
							<td>
								${skz.returnDate }
							</td>
							<td>
								${skz.monthPrincipalInterest }
							</td>
							<td>
								${skz.surplusPrincipal }
							</td>
							<td>
								${skz.lateDate }
							</td>
							<td>
								${skz.lateRate }
							</td>
							<td>
								<a href="${ctx}/myAccount/myAccount/showContract?loanId=${skz.loanNo}" target="_blank"><img src="${ctx}/static/images/img91.gif" /> </a>
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
				    	  共<%=total%>条&nbsp;<a href="#"
										onclick="fenye('<%=pageUrl%>')">首页</a>
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
				    </pg:last> <br>
							</font>
						</pg:index>
					</td>
				</tr>
			</table>
		</pg:pager>
	</table>
</div>
<div class="clear"></div>
