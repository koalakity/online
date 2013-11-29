<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">借款统计</h3>
				</div>
				<div class="account_r_c_3c">
					<table class="table3">
						<tr><th colspan="6">还款统计</th></tr>
						<tr><td class="even">总借款额</td><td>${loanStatisticsInfo.loanTotle}</td><td class="even">发布借款笔数</td><td>${loanStatisticsInfo.releaseLoanNumber}</td></tr>
						<tr><td class="even">已还本息</td><td>${loanStatisticsInfo.repayOffPrincipalInterestTotal}</td><td class="even">成功借款笔数</td><td>${loanStatisticsInfo.successLoanNumber}</td></tr>
						<tr><td class="even">待还本息</td><td>${loanStatisticsInfo.waitRepayPrincipalInterestTotal}</td><td class="even">正常还清笔数</td><td>${loanStatisticsInfo.normalPayOffNumber}</td></tr>
						<tr><td class="even">待还管理费</td><td>${loanStatisticsInfo.waitRepayManagementFee}</td><td class="even">提前还清笔数</td><td>${loanStatisticsInfo.earlyRepayOffNumber}</td></tr>
						<tr><td class="even">&nbsp;</td><td>&nbsp;</td><td class="even">未还清笔数</td><td>${loanStatisticsInfo.notRepayOffNumber}</td></tr>
					</table>
					<table class="table3">
						<tr><th colspan="4">逾期统计</th></tr>
						<tr><td class="even">逾期次数</td><td>${loanStatisticsInfo.overdueCount}</td><td class="even">严重逾期次数</td><td>${loanStatisticsInfo.seriousOverdueCount}</td></tr>
					</table>
					<table class="table3">
						<tr><th colspan="4">借款数据分析</th></tr>
						<tr><td class="even">加权平均借款利率</td><td>${loanStatisticsInfo.weightedAverageLoanRate}</td><td class="even">平均每笔借款金额</td><td>${loanStatisticsInfo.averageEachLoanAmount}</td></tr>
					</table>
				</div>
			</div>
<div class="clear"></div>
