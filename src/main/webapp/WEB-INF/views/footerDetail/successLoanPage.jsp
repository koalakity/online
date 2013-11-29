<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<table>
			<c:forEach items="${inLoanList.indexLoanList}" var="loanInfo" varStatus="status">
				<c:choose>
					<c:when test="${status.count %2 ==1 }">
						<tr>
							<td width="50" align="center"><img src="${ctx}/static/images/img2.gif" /></td>
							<td width="210"><a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}" target="_blank" class="a_n">${loanInfo.loanTitle }</a></td>
							<td width="50">信用等级</td>
							<td width="50"><c:if test="${loanInfo.creditGrade==1}"><img src="${ctx}/static/images/img28.gif" class="img1"/></c:if>
								<c:if test="${loanInfo.creditGrade==2}"><img src="${ctx}/static/images/img27.gif" class="img1"/></c:if>
								<c:if test="${loanInfo.creditGrade==3}"><img src="${ctx}/static/images/img26.gif" class="img1"/></c:if>
								<c:if test="${loanInfo.creditGrade==4}"><img src="${ctx}/static/images/img25.gif" class="img1"/></c:if>
								<c:if test="${loanInfo.creditGrade==5}"><img src="${ctx}/static/images/img24.gif" class="img1"/></c:if>
								<c:if test="${loanInfo.creditGrade==6}"><img src="${ctx}/static/images/img23.gif" class="img1"/></c:if>
								<c:if test="${loanInfo.creditGrade==7}"><img src="${ctx}/static/images/img22.gif" class="img1"/></c:if>
							</td>
							<td width="150">借款金额：<span class="col1">${loanInfo.loanAmount }</span></td>
							<td width="110">年利率：<span class="col1">${loanInfo.yearRate }</span></td>
							<td width="110">借款期限：<span class="col1">${loanInfo.loanPeriod }</span></td>
							<td width="40">已筹集</td>
							<td width="70"><div class="raise_s"><span style="width:${loanInfo.speedProgress };"></span><em>${loanInfo.speedProgress }</em></div></td>
							<td width="70">${loanInfo.bidNumber }笔投标</td>
							<td width="90" align="center">${loanInfo.releaseYMD}</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="tr_col">
						<td width="50" align="center"><img src="${ctx}/static/images/img2.gif" /></td>
						<td width="210"><a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}" target="_blank" class="a_n">${loanInfo.loanTitle }</a></td>
						<td width="50">信用等级</td>
						<td width="50">
							<c:if test="${loanInfo.creditGrade==1}"><img src="${ctx}/static/images/img28.gif" class="img1"/></c:if>
							<c:if test="${loanInfo.creditGrade==2}"><img src="${ctx}/static/images/img27.gif" class="img1"/></c:if>
							<c:if test="${loanInfo.creditGrade==3}"><img src="${ctx}/static/images/img26.gif" class="img1"/></c:if>
							<c:if test="${loanInfo.creditGrade==4}"><img src="${ctx}/static/images/img25.gif" class="img1"/></c:if>
							<c:if test="${loanInfo.creditGrade==5}"><img src="${ctx}/static/images/img24.gif" class="img1"/></c:if>
							<c:if test="${loanInfo.creditGrade==6}"><img src="${ctx}/static/images/img23.gif" class="img1"/></c:if>
							<c:if test="${loanInfo.creditGrade==7}"><img src="${ctx}/static/images/img22.gif" class="img1"/></c:if>
						</td>
						<td width="150">借款金额：<span class="col1">${loanInfo.loanAmount }</span></td>
						<td width="110">年利率：<span class="col1">${loanInfo.yearRate }</span></td>
						<td width="110">借款期限：<span class="col1">${loanInfo.loanPeriod }</span></td>
						<td width="40">已筹集</td>
						<td width="70"><div class="raise_s"><span style="width:${loanInfo.speedProgress };"></span><em>${loanInfo.speedProgress }</em></div></td>
						<td width="70">${loanInfo.bidNumber }笔投标</td>
						<td width="90" align="center">${loanInfo.releaseYMD}</td>
				</tr>
				</c:otherwise>
			</c:choose>
			</c:forEach>
</table>