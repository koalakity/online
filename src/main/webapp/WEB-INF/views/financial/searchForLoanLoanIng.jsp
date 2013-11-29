<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var ca = document.getElementById("cLoanIng");
	ca.innerText = "进行中的借款[${count.ingLoanCount}]";
</script>
<div class="invest_c2">
	<table>
		<tr>
			<th>
				借款类型
			</th>
			<th>
				标题/借款人
			</th>
			<th>金额
				<!--<c:if test="${empty loanAmountseq}">
					<a href="#" onclick="jineSequence('loanAmount','asc',1)">金额↓↑</a>
				</c:if>
				<c:if test="${loanAmountseq=='none'}">
					<a href="#" onclick="jineSequence('loanAmount','asc',1)">金额↓↑</a>
				</c:if>
				<c:if test="${loanAmountseq=='asc'}">
					<a href="#" onclick="jineSequence('loanAmount','desc',1)">金额↓</a>
				</c:if>
				<c:if test="${loanAmountseq=='desc'}">
					<a href="#" onclick="jineSequence('none','none',1)">金额↑</a>
				</c:if>-->
			</th>
			<th>年利率
				<!--<c:if test="${empty yearRateseq}">
					<a href="#" onclick="jineSequence('yearRate','asc',1)">年利率↓↑</a>
				</c:if>
				<c:if test="${yearRateseq=='none'}">
					<a href="#" onclick="jineSequence('yearRate','asc',1)">年利率↓↑</a>
				</c:if>
				<c:if test="${yearRateseq=='asc'}">
					<a href="#" onclick="jineSequence('yearRate','desc',1)">年利率↓</a>
				</c:if>
				<c:if test="${yearRateseq=='desc'}">
					<a href="#" onclick="jineSequence('none','none',1)">年利率↑</a>
				</c:if>-->
			</th>
			<th>
				期限
			</th>
			<th>
				认证
			</th>
			<th>信用等级
				<!--<c:if test="${empty creditGradeseq}">
					<a href="#" onclick="jineSequence('creditGrade','asc',1)">信用等级↓↑</a>
				</c:if>
				<c:if test="${creditGradeseq=='none'}">
					<a href="#" onclick="jineSequence('creditGrade','asc',1)">信用等级↓↑</a>
				</c:if>
				<c:if test="${creditGradeseq=='asc'}">
					<a href="#" onclick="jineSequence('creditGrade','desc',1)">信用等级↓</a>
				</c:if>
				<c:if test="${creditGradeseq=='desc'}">
					<a href="#" onclick="jineSequence('none','none',1)">信用等级↑</a>
				</c:if>-->
			</th>
			<th>
				进度/剩余时间
			</th>
			<th>
				操作
			</th>
		</tr>
		
		<pg:pager url="${ctx}/financial/searchLoan/searchLoanList"
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
			<td class="td2">
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
			<td class="td1 tl">
				<a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}">${loanInfo.loanTitleStr}</a>
				<br />
				${loanInfo.userId.loginName}
			</td>
			<td>
				￥${loanInfo.loanAmount}
			</td>
			<td>
				${loanInfo.yearRateStr}
			</td>
			<td>
				${loanInfo.loanDuration }个月
			</td>
			<td>
					<c:forEach items="${loanInfo.userApproveList}" var="approve" varStatus="s">
						<c:if test="${approve.proId == 1}">
							
							<img src="${ctx}/static/images/img8.png" class="img1" alt="身份证认证"
								title="身份证认证" />
						</c:if>
						<c:if test="${approve.proId == 2}">
							<img src="${ctx}/static/images/img9.png" class="img1" alt="工作认证"
								title="工作认证" />
						</c:if>
						<c:if test="${approve.proId == 3}">
							<img src="${ctx}/static/images/img10.png" class="img1" alt="收入认证"
								title="收入认证" />
						</c:if>
						<c:if test="${approve.proId == 4}">
							<img src="${ctx}/static/images/img11.png" class="img1"
								alt="信用报告认证" title="信用报告认证" />
						</c:if>
						<c:if test="${approve.proId == 5}">
							<img src="${ctx}/static/images/img12.png" class="img1" alt="房产认证"
								title="房产认证" />
						</c:if>
						
						<c:if test="${approve.proId == 6}">
							<img src="${ctx}/static/images/img20.png" class="img1" alt="技术职称认证"
								title="技术职称认证" />
						</c:if>
						
						<c:if test="${approve.proId == 7}">
							<img src="${ctx}/static/images/img13.png" class="img1" alt="购车证明"
								title="购车证明" />
						</c:if>
						<c:if test="${approve.proId == 8}">
							<img src="${ctx}/static/images/img21.png" class="img1" alt="结婚认证"
								title="结婚认证" />
						</c:if>
						<c:if test="${approve.proId == 9}">
							<img src="${ctx}/static/images/img14.png" class="img1"
								alt="居住地证明" title="居住地证明" />
						</c:if>
		
						<c:if test="${approve.proId == 10}">
							<img src="${ctx}/static/images/img15.png" class="img1" alt="视频认证"
								title="视频认证" />
						</c:if>
						<c:if test="${approve.proId ==11 }">
							<img src="${ctx}/static/images/img16.png" class="img1" alt="实地考察认证"
								title="实地考察认证" />
						</c:if>
						<c:if test="${approve.proId ==12 }">
							<img src="${ctx}/static/images/img17.png" class="img1" alt="学历认证"
								title="学历认证" />
						</c:if>
						<c:if test="${approve.proId ==13 }">
							<img src="${ctx}/static/images/img18.png" class="img1" alt="手机实名认证"
								title="手机实名认证" />
						</c:if>
						<c:if test="${approve.proId ==14 }">
							<img src="${ctx}/static/images/img19.png" class="img1" alt="微博认证"
								title="微博认证" />
						</c:if>
						<c:if test="${s.count%5==0}">
							<br/>
						</c:if>
						<c:if test="${approve.proId ==15 }">
							<img src="${ctx}/static/images/img18.png" class="img1" alt="手机认证"
								title="手机认证" />
						</c:if>
					</c:forEach>
				</td>
			<td>
				<c:if test="${loanInfo.userId.userCreditNote.creditGrade==1}">
					<img src="${ctx}/static/images/img28.gif" />
				</c:if>
				<c:if test="${loanInfo.userId.userCreditNote.creditGrade==2}">
					<img src="${ctx}/static/images/img27.gif" />
				</c:if>
				<c:if test="${loanInfo.userId.userCreditNote.creditGrade==3}">
					<img src="${ctx}/static/images/img26.gif" />
				</c:if>
				<c:if test="${loanInfo.userId.userCreditNote.creditGrade==4}">
					<img src="${ctx}/static/images/img25.gif" />
				</c:if>
				<c:if test="${loanInfo.userId.userCreditNote.creditGrade==5}">
					<img src="${ctx}/static/images/img24.gif" />
				</c:if>
				<c:if test="${loanInfo.userId.userCreditNote.creditGrade==6}">
					<img src="${ctx}/static/images/img23.gif" />
				</c:if>
				<c:if test="${loanInfo.userId.userCreditNote.creditGrade==7}">
					<img src="${ctx}/static/images/img22.gif" />
				</c:if>
			</td>
			<td>
				<div class="raise_s4">
					<span style="width: ${loanInfo.schedule};"></span><em>${loanInfo.schedule}</em>
				</div>
				${loanInfo.investmentCount}笔投标，还需${loanInfo.leavingMoney}
				<br />
				<c:if test="${loanInfo.status == 1 || loanInfo.status == 8}">
					剩余时间：${loanInfo.leavingTime}
				</c:if>
				<c:if test="${loanInfo.status != 1 && loanInfo.status != 8}">
					剩余时间：0 天
				</c:if>
			</td>
			<td>
				<c:if test="${loanInfo.status == 1}">
					<a onclick="bidImmediately('${ctx}/financial/searchLoan/confirmInvest?loanId=${loanInfo.loanId}','602','419')" class="btn1 mar0">马上投标</a>
				</c:if>
				<c:if test="${loanInfo.status == 2}">
					<a href="#" class="btn6 mar0">已经满标</a>
				</c:if>
				<c:if test="${loanInfo.status == 3}">
					<a href="#" class="btn6 mar0">流标</a>
				</c:if>
				<c:if test="${loanInfo.status == 4}">
					<a href="#" class="btn6 mar0">还款中</a>
				</c:if>
				<c:if test="${loanInfo.status == 5}">
					<a href="#" class="btn6 mar0">成功</a>
				</c:if>
				<c:if test="${loanInfo.status == 6}">
					<a href="#" class="btn6 mar0">逾期</a>
				</c:if>
				<c:if test="${loanInfo.status == 7}">
					<a href="#" class="btn6 mar0">高级逾期</a>
				</c:if>
				<c:if test="${loanInfo.status == 8}">
					<a href="#" class="btn6 mar0">等待资料</a>
				</c:if>
			</td>
		</tr>
		</pg:item>
		</c:forEach>
		
		</ex:searchresults>
			</table>
						<pg:index export="total=itemCount">
							<p class="pagelist" style="margin-bottom:15px;"> <pg:first>
				    	  共<%=total%>条&nbsp;<a href="#"
										onclick="searchFenye('<%=pageUrl%>')">首页</a>
								</pg:first> <pg:prev>&nbsp;<a href="#"
										onclick="searchFenye('<%=pageUrl%>')">上一页</a>
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
								</pg:pages> <pg:next>&nbsp;<a href="#"
										onclick="searchFenye('<%=pageUrl%>')">下一页</a>
								</pg:next> <pg:last>
									<a href="#" onclick="searchFenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%=pageNumber%>页
				    </pg:last> <br>
							</p>
						</pg:index>
		</pg:pager>
	
</div>