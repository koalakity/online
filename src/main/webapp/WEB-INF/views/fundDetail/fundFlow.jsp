<%@ page language="java" import="java.util.*,com.zendaimoney.online.common.TypeConstants,com.zendaimoney.online.vo.fundDetail.FundFlowVO" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="select_all">
	<a class="btn1" href="#" onclick="queryFlow()">查询</a>资金类型：
						
                        <select id="type_fund">
                            <option ${fundFlowVO.type_fund eq 0?'selected="selected"':''} value ="<%=TypeConstants.SY %>"><%=TypeConstants.getValueByType(TypeConstants.SY) %></option>
                            <option ${fundFlowVO.type_fund eq 1?'selected="selected"':''} value ="<%=TypeConstants.TX %>"><%=TypeConstants.getValueByType(TypeConstants.TX) %></option>
                            <option ${fundFlowVO.type_fund eq 2?'selected="selected"':''} value ="<%=TypeConstants.CZ %>"><%=TypeConstants.getValueByType(TypeConstants.CZ) %></option>
                            <option ${fundFlowVO.type_fund eq 3?'selected="selected"':''} value ="<%=TypeConstants.ZJHS %>"><%=TypeConstants.getValueByType(TypeConstants.ZJHS) %></option>
                            <%--偿还借款
                            <option value ="<%=TypeConstants.CHJK %>"><%=TypeConstants.getValueByType(TypeConstants.CHJK) %></option>
                             --%>
                            <option ${fundFlowVO.type_fund eq 5?'selected="selected"':''} value ="<%=TypeConstants.JKCG %>"><%=TypeConstants.getValueByType(TypeConstants.JKCG) %></option>
                            <option ${fundFlowVO.type_fund eq 6?'selected="selected"':''} value ="<%=TypeConstants.XTJY%>"><%=TypeConstants.getValueByType(TypeConstants.XTJY) %></option>
                            <option ${fundFlowVO.type_fund eq 7?'selected="selected"':''} value ="<%=TypeConstants.SFYZ %>"><%=TypeConstants.getValueByType(TypeConstants.SFYZ) %></option>
                            <option ${fundFlowVO.type_fund eq 8?'selected="selected"':''} value ="<%=TypeConstants.TZ %>"><%=TypeConstants.getValueByType(TypeConstants.TZ) %></option>
                            <%--偿还借款
                            <option value ="<%=TypeConstants.CZH %>"><%=TypeConstants.getValueByType(TypeConstants.CZH) %></option>
                            --%>
                        </select>&nbsp;&nbsp;&nbsp;起止日期：                        
                        <input type="text" class="input_80" id="date_start"  value="${fundFlowVO.date_start}"  onClick="WdatePicker()"  onfocus="WdatePicker({startDate:'%y-%M-01',alwaysUseStartDate:true,maxDate:'%y-%M-%d'})"/>-<input type="text" class="input_80" id="date_end"  value="${fundFlowVO.date_end}"  onClick="WdatePicker()" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'date_start\')}',maxDate:'%y-%M-%d'})"/>
  </div>
							<table class="new_table1">
								<tr><th>时间</th><th>类型</th><th>存入（元）</th><th>支出（元）</th><th>结余</th><th>备注</th></tr>
								<c:if test="${empty fundFlowVO.flowList}">
									<tr>
										<td colspan="6" align="center">没有找到交易记录！</td>
									</tr>
								</c:if>
								 <pg:pager url="${ctx}/fundDetail/fundDetail/showFundFlow"
									items="${fundFlowVO.pageCount }" index="center" maxPageItems="10"
									maxIndexPages="10" isOffset = "<%=false%>" export="offset,currentPageNumber=pageNumber"
									scope="request">
									
									<pg:param name="index" />
									<pg:param name="maxPageItems" />
									<pg:param name="maxIndexPages" />
								<ex:searchresults>
									<c:forEach items="${fundFlowVO.flowList}" var="flow">
			                            <tr>
			                            <td><fmt:formatDate value="${flow.date}" type="both"/></td>
			                             <fmt:formatDate pattern="[yyyy-MM-dd]" value="${a}" type="both"/>
										<td>${flow.tradeType}</td>
										<td>
											<span class="new_col1 bold1">
												<c:if test="${!empty flow.in}">
													+<fmt:formatNumber  value="${flow.in+ 0.0001 }" pattern="#,###,###,###.##"/>
												</c:if>
											</span>
										</td>
										<td>
											<span class="new_col2 bold1">
												<c:if test="${!empty flow.out}">
													-<fmt:formatNumber  value="${flow.out+ 0.0001 }"  pattern="#,###,###,###.##"/>
												</c:if>
											</span>
										</td>
										<td>
											
											<span class="new_col3 bold1">
												￥<fmt:formatNumber  value='${flow.amount+ 0.0001 }'  pattern='#,###,###,###.##'/>
											</span>
										</td>
										<td>
											<c:choose>
												<c:when test="${flow.type eq 2}">
													充值单号：${flow.code}<br />
												</c:when>
												<c:when test="${flow.type eq 1}">
													提现单号：${flow.code}<br />
												</c:when>
												<c:when test="${flow.type eq 3}">
													借款编号：
													<a target="_blank" href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${flow.code}">${flow.code}</a>
													<br />
												</c:when>
												<c:when test="${flow.type eq 4}">
													借款编号：
													<a target="_blank" href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${flow.code}">${flow.code}</a>
													<br />
												</c:when>
												<c:when test="${flow.type eq 5}">
													借款编号：
													<a target="_blank" href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${flow.code}">${flow.code}</a>
													<br />
												</c:when>
											</c:choose>
										</td>
										</tr>
									</c:forEach>								
								</ex:searchresults>
							</table>
						<!-- new 资金流水 end -->
						<pg:index export="total=itemCount">
							<p class="pagelist" style="margin-bottom:15px;"> <pg:first>
				    	  共<%=total%>条&nbsp;<a href="#" onclick="searchFenye('<%=pageUrl%>')" >首页</a>
								</pg:first> <pg:prev>&nbsp;<a href="#" onclick="searchFenye('<%=pageUrl%>')" >上一页</a>
								</pg:prev> <pg:pages>
									<%
										if (pageNumber.intValue() < 10) {
									%>&nbsp;<%
										}
													if (pageNumber == currentPageNumber) {
									%><b><%=pageNumber%></b>
									<%
										} else {
									%><a href="#" onclick="searchFenye('<%=pageUrl%>')"><%=pageNumber%></a>
									<%
										}
									%>
								</pg:pages> 
								<pg:next>&nbsp;
									<a href="#" onclick="searchFenye('<%=pageUrl%>')" >下一页</a>
								</pg:next> 
								<pg:last>
									<a href="#" onclick="searchFenye('<%=pageUrl%>')" >末页 </a>&nbsp;&nbsp;共<%=pageNumber%>页
				    			</pg:last> <br>
							</p>
						</pg:index>
								</pg:pager>
						<!-- <p class="pagelist">
							共512条<a>首页</a><a>上一页</a><a>1</a><a>2</a><a>3</a>...<a>下一页</a><a>末页</a>共52页
						</p> -->