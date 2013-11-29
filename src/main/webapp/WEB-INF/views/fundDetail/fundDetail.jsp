<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<table class="table2">
	<tr><th colspan="6">个人资金详情</th></tr>
	<tr><td class="even">账户余额</td><td>${vo.zhye}</td><td class="even">可用余额</td><td>${vo.kyye}</td><td class="even">可用额度</td><td>${vo.kyed}</td></tr>
	<tr><td class="even">冻结总额</td><td>${vo.djzy}</td><td class="even">理财冻结总额</td><td>${vo.lcdjzy}</td><td class="even">提现冻结总额</td><td>${vo.txdjzy}</td></tr>
	<tr><td class="even">成功充值总额</td><td>${vo.cgczze}</td><td class="even">成功提现总额</td><td>${vo.cgtxze}</td><td class="even">&nbsp;</td><td>&nbsp;</td></tr>
</table>
<table class="table1">
	<tr><th colspan="3">理财资金详情</th></tr>
	<tr class="even"><td>总借出金额</td><td>已收回本息</td><td>待收回本息</td></tr>
	<tr><td>${vo.zjcje}</td><td>${vo.yshbx}</td><td>${vo.dshbx}</td></tr>
</table>
<table class="table1">
	<tr><th colspan="3">借款资金详情</th></tr>
	<tr class="even"><td>总借款金额</td><td>已还本息</td><td>待还本息</td></tr>
	<tr><td>${vo.zjkje}</td><td>${vo.yhbx}</td><td>${vo.dhbx}</td></tr>
</table>