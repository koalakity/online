<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>查找借款</title>
		<script type="text/javascript">
function findLoan(flg) {
	window.location.href("${ctx}/financial/searchLoan/showLoan?status=" + flg);
}

function searchLoan(creditGrade,yearRate,loanDuration) {
	window.location.href("${ctx}/financial/searchLoan/searchLoanBycondition?creditGrade="+creditGrade.value
	+"&yearRate="+yearRate.value+"&loanDuration="+loanDuration.value);
}
</script>
	</head>
	<body>
		<table>
			<tr>
				<td>
					信用等级:
					<select id="creditGrade" name="creditGrade">
							<option value="0">
								全部
							</option>
							<option value="1">
								AA级
							</option>
							<option value="2">
								A级
							</option>
							<option value="3">
								B级
							</option>
							<option value="4">
								C级
							</option>
							<option value="5">
								D级
							</option>
							<option value="6">
								E级
							</option>
							<option value="7">
								HR级
							</option>
						</select>
					&nbsp&nbsp&nbsp 年利率:
					<select id="yearRate" name="yearRate">
							<option value="0" selected="selected">
								全部
							</option>
							<option value="1">
								<=15%
							</option>
							<option value="2">
								15%-20%
							</option>
							<option value="3">
								>=20%
							</option>
						</select>
					&nbsp&nbsp&nbsp 借款期限:
					<select id="loanDuration" name="loanDuration">
							<option value="0" selected="selected">
								全部
							</option>
							<option value="3">
								3个月
							</option>
							<option value="6">
								6个月
							</option>
							<option value="9">
								9个月
							</option>
							<option value="12">
								12个月
							</option>
							<option value="18">
								18个月
							</option>
							<option value="24">
								24个月
							</option>
						</select>
					&nbsp&nbsp&nbsp
					<input type="button" value="搜索" onclick="searchLoan(creditGrade,yearRate,loanDuration)">
				</td>
			</tr>
			<tr>
				<td>
					<table id="infoList">
						<tr>
							<td>
								<input type="button" value="全部借款列表[${loanInfo.allLoanCount }]"
									onclick="findLoan(0)">
							</td>
							<td>
								<input type="button" value="进行中的借款[${loanInfo.ingLoanCount }]"
									onclick="findLoan(2)">
							</td>
							<td>
								<input type="button"
									value="即将开始的借款[${loanInfo.futureLoanCount }]"
									onclick="findLoan(1)">
							</td>
							<td>
								<input type="button" value="已完成的借款[${loanInfo.oldLoanCount }]"
									onclick="findLoan(345)">
							</td>
							<td>
								<input type="button" value="逾期黑名单[${loanInfo.blackLoanCount }]"
									onclick="findLoan(6)">
							</td>
							<td>

							</td>
							<td>
								<input type="button" value="理财计算器">
							</td>
						</tr>
						<tr>
							<td colspan="7">
								初始投资:
								<input type="text">
								元 &nbsp&nbsp&nbsp年利率:
								<input type="text">
								%&nbsp&nbsp&nbsp投资期限:
								<input type="text">
								月&nbsp&nbsp&nbsp
								<input type="button" value="计算">
								&nbsp&nbsp&nbsp到期价值:
								<input type="text">
								元
							</td>
						</tr>
						<tr>
							<td colspan="7">
								信用
								<input type="button" value="排序">
								&nbsp&nbsp&nbsp&nbsp年利率
								<input type="button" value="排序">
								&nbsp&nbsp&nbsp&nbsp借款金额
								<input type="button" value="排序">
								&nbsp&nbsp&nbsp&nbsp时间
								<input type="button" value="排序">
							</td>
						</tr>
						<tr height="25">
							<td>
								图片
							</td>
							<td>
								标题/借款人/地区
							</td>
							<td>
								金额/期限/利率
							</td>
							<td>
								认证
							</td>
							<td>
								信用等级
							</td>
							<td>
								进度/剩余时间
							</td>
							<td>
								操作
							</td>
						</tr>
						<c:if test="${not empty loanInfo.loanList}">
							<c:forEach items="${loanInfo.loanList}" var="loan">
								<tr>
									<td>
										<img alt="图片" src="" height="30" width="30">
									</td>
									<td>
										<a href="#">${loan.loanTitleStr }</a>
										<br>
										${loan.name}
									</td>
									<td>
										￥${loan.loanAmount}
										<br>
										${loan.paymentMethod }个月,${loan.yearRate}%
									</td>
									<td>
										<img alt="认证" src="" width="15" height="15">
									</td>
									<td>
										<img alt="信用等级" src="" width="15" height="15">
									</td>
									<td>
										<img alt="进度" src="" width="15" height="15">
										<br>
										${loan.investmentCount}笔投资,还需￥${loan.leavingMoney }
										<br>
										${loan.leavingTime }
									</td>
									<td>
										<c:if test="${loan.status == 1}">
											<input type="button" value="等待资料" disabled="disabled">
										</c:if>
										<c:if test="${loan.status == 2}">
											<input type="button" value="立即投标">
										</c:if>
										<c:if test="${loan.status == 3}">
											<input type="button" value="已满标" disabled="disabled">
										</c:if>
										<c:if test="${loan.status == 4}">
											<input type="button" value="还款中" disabled="disabled">
										</c:if>
										<c:if test="${loan.status == 5}">
											<input type="button" value="还款完成" disabled="disabled">
										</c:if>
										<c:if test="${loan.status == 6}">
											<input type="button" value="坏账" disabled="disabled">
										</c:if>
										<c:if test="${loan.status == 7}">
											<input type="button" value="流标" disabled="disabled">
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>

						<tr>
							<td colspan="7">
								<center>
									共513条
									<a href="#">首页</a><a href="#">下一页</a><a href="#">上一页</a><a
										href="#">尾页</a>
								</center>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
