<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

</script>
<div class="account_r_c_3c4">
	<table class="table4">
		<tr>
			<th>
				&nbsp;
			</th>
			<th>
				未来一个月
			</th>
			<th>
				未来三个月
			</th>
			<th>
				未来一年
			</th>
			<th>
				全部
			</th>
		</tr>
		<tr>
			<td>
				待收本息
			</td>
			<td>
				${oneMonthAmount}
			</td>
			<td>
				${threeMonthAmount}
			</td>
			<td>
				${oneYearAmount}
			</td>
			<td>
				${allAmount}
			</td>
		</tr>
	</table>
	<div class="select_all mar3">
		<a class="btn1 mar" href="#" onclick="searchList()">查询</a>&nbsp;起止日期：
		<input id="startD" name="startD" type="text" class="input_80" value="${startDate}"
			onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
		-
		<input id="endD" type="endD" class="input_80" value="${endDate}"
			onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
	</div>
	<p class="bold font_14 bor_t">
		&nbsp;查询期代收本息总额：
		<span class="col1">${allAmountSearch}</span>
	</p>
	<table class="table4">
		<tr>
			<th>
				还款日期
			</th>
			<th>
				借款人
			</th>
			<th>
				借款编号
			</th>
			<th>
				待收本息
			</th>
		</tr>

		<pg:pager url="${ctx}/myInvestment/myInvestment/showHzcxLoanSearch"
			items="${fn:length(hzcxList)}" index="center" maxPageItems="10"
			maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
			scope="request">
			<pg:param name="index" />
			<pg:param name="maxPageItems" />
			<pg:param name="maxIndexPages" />
			<ex:searchresults>
				<c:forEach items="${hzcxList}" var="hzcx">
					<pg:item>
						<tr>
							<td>
								${hzcx.returnDate}
							</td>
							<td>
								${hzcx.loanAcc}
							</td>
							<td>
								${hzcx.loanNo}
							</td>
							<td>
								${hzcx.monthPrincipalInterest}
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
				    	  共<%=total%>条&nbsp;<a href="#" onclick="searchfenye('<%=pageUrl%>')">首页</a>
								</pg:first> <pg:prev>&nbsp;<a href="#"
										onclick="searchfenye('<%=pageUrl%>')">上一页</a>
								</pg:prev> <pg:pages>
									<%
										if (pageNumber.intValue() < 10) {
									%>&nbsp;<%
										}
													if (pageNumber == currentPageNumber) {
									%><b><%=pageNumber%></b>
									<%
										} else {
									%><a href="#" onclick="searchfenye('<%=pageUrl%>')"><%=pageNumber%></a>
									<%
										}
									%>
								</pg:pages> <pg:next>&nbsp;<a href="#"
										onclick="searchfenye('<%=pageUrl%>')">下一页</a>
								</pg:next> <pg:last>
									<a href="#" onclick="searchfenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%=pageNumber%>页
				    </pg:last> <br> </font>
						</pg:index>
					</td>
				</tr>
			</table>
		</pg:pager>

	</table>
</div>
<div class="clear"></div>
