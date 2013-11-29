<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>确定投标金额</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<style type="text/css">
			.overlay{position:fixed;top:0;right:0;bottom:0;left:0;z-index:998;width:100%;height:100%;_padding:0 20px 0 0;background:#f6f4f5;display:none;}
			
			.showbox{position:fixed;top:0;left:50%;z-index:9999;opacity:0;filter:alpha(opacity=0);margin-left:-80px;}
			#AjaxLoading{border:1px solid #8CBEDA;color:#37a;font-size:12px;font-weight:bold;}
			
			#AjaxLoading div.loadingWord{width:180px;height:50px;line-height:50px;border:2px solid #D6E7F2;background:#fff;}
			
			#AjaxLoading img{margin:10px 15px;float:left;display:inline;}
		</style>
	</head>
	<body>
		<div class="msg_box width3 height4">
			<div class="msg_t">
				<a onclick="closefancy()" class="msg_close"></a>
				<img src="${ctx}/static/images/img94.gif" />
				确定投标金额
			</div>
			<div class="msg_c">
				<table class="table1">
					<tr>
						<td class="td1">
							借款标题：
						</td>
						<td>
							${loanInfo.loanTitle}
						</td>
					</tr>
					<tr>
						<td class="td1">
							借款金额：
						</td>
						<td>
							<span class="col1">${loanInfo.loanAmountStr}</span>
						</td>
					</tr>
					<tr>
						<td class="td1">
							借款年利率：
						</td>
						<td>
							<span class="col1">${loanInfo.yearRateStr}</span>
						</td>
					</tr>
					<tr>
						<td class="td1">
							借款期限：
						</td>
						<td>
							<span class="col1">${loanInfo.loanDuration}个月</span>
						</td>
					</tr>
					<tr>
						<td class="td1">
							还款周期：
						</td>
						<td>
							按月还款
						</td>
					</tr>
					<tr>
						<td class="td1">
							还款方式：
						</td>
						<td>
						<c:if test="${loanInfo.paymentMethod == 1}">
							等额本息
						</c:if>
						</td>
					</tr>
					<tr>
						<td class="td1">
							已完成：
						</td>
						<td>
							<span class="raise_s3"><em style="width: ${loanInfo.schedule};"></em>
							</span>${loanInfo.schedule}
						</td>
					</tr>
					<tr>
						<td class="td1">
							剩余投标金额：
						</td>
						<td>
							<span class="col1">${loanInfo.leavingMoney}</span>
						</td>
					</tr>
				</table>
				<table class="table1 mar7">
						<tr>
							<td class="td1">
								我的可用余额：
							</td>
							<td>
								<span class="col1">${loanInfo.myMoney}</span>&nbsp;&nbsp;
								<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay" class="a_pay">我要充值</a>
							</td>
						</tr>
						<tr>
							<td class="td1">
								投标金额：
							</td>
							<td>
								<input type="text" name="investAmount" id="investAmount" value="500"/>
								<span class="col1">&nbsp;元</span>&nbsp;&nbsp;&nbsp;(最低投标金额为50元,并且是50的倍数)
								<input type="hidden" id="loanId" name="loanId" value="${loanInfo.loanId}">
							</td>
						</tr>
				</table>
				<input type="hidden" id="requestToken" value="${tokenValue}">
				<input type="button" id="confirmInvest"  class="btn4 btn4_msg btn4_add" value="确认投标"
					onclick="confirmInvest()" />
			</div>
		</div>
		<div class="overlay">&nbsp;</div>

		<div class="showbox" id="AjaxLoading">
		
			<div class="loadingWord"><img src="${ctx}/static/images/loading/waiting.gif" alt="" />投标进行中...</div>

		</div>
	</body>
</html>