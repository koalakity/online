<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>3</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	$(function(){
	
		$('#searchBt').click(function(){
			search();
		});
	});
	
	var url;
	function search(){
	    var queryParams = $('#dg').datagrid('options').queryParams;
		queryParams.loginName = $('#loginName').val();
		queryParams.realName = $('#realName').val();
		queryParams.phoneNo = $('#phoneNo').val();
		queryParams.channelFId=$('#channelFId').val();   //增加渠道ID
		queryParams.channelCId=$('#channelCId').val();   //增加渠道ID
		//queryParams.serchFlag='serch';
		$('#dg').datagrid('options').queryParams = queryParams;
		$("#dg").datagrid('reload');
	}
	
	
	function formatterOperation(val, row) {
		url='${ctx}/admin/loan/loanTenderOverDetailPage?loanId='+row.loanId
		return '<a href="'+url+'"  >查看详情</a>';
	}
	
	function formattLoanDuration(val,row){
		if(row.loanDuration){
		return row.loanDuration+"个月";
		}
	}
	function appendChildChannelR(f_id,c_id){
		var id=$("#"+f_id).val();
		$.ajax( {
			url : "${ctx}/register/register/findByParentId?code="+id,
			type : "POST",
			dataType : 'JSON',
			success : function(strFlg) {
				$("#"+c_id).empty();
				if(strFlg=="" || strFlg==null){
					$("#"+c_id).append("<option value=''>全部</option>");
				}else{
					$("#"+c_id).append("<option value=''>全部</option>");
					for(var i=0;i<strFlg.length;i++)
					{
						$("#"+c_id).append("<option value="+strFlg[i].id+">"+strFlg[i].name+"</option>");
					}
				}
				
			}
		});
	}
	
	
</script>
</head>
<body>
<table id="dg" title="已满标借款列表" class="easyui-datagrid"
			url="${ctx}/admin/loan/loanTenderOverPage?status=2"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="false">
	<thead>
		<tr>
			<th field="ck" checkbox="true"></th>
			<th field="loginName" formatter="formatterUserDetailForLoan">昵称</th>
			<th field="realName" >姓名</th>
			<th field="phoneNo" >手机号码</th>
			<th field="loanId" >借款编号</th>
			<th field="formattLoanAmount" >借款金额</th>
			<th field="yearRateFormatt" >年利率</th>
			<th field="loanDuration" formatter="formattLoanDuration">借款期限</th>
			<th field="releaseTimeFormatt" >发布日期</th>
			<th field="action" title="操作" formatter="formatterOperation">编辑</th>
		</tr>
	</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<form id="serarchForm" method="post">
	<div style="margin-bottom:5px">
		
	</div>
	
	<div>
		姓名:<input type="text" name="realName" id="realName" size=16/>
		昵称:<input type="text" name="loginName" id="loginName" size=16/>
		手机号码:<input type="text" name="phoneNo" id="phoneNo" size=16/>
		渠道名称：<!-- 一级渠道信息 -->
				<select id="channelFId" name="channelFId"  onchange="appendChildChannelR('channelFId','channelCId')">
					<option value="">全部</option>
					<c:forEach items="${channelInfoParList}" var="channelInfoList">
						<option value="${channelInfoList.id }" ${channelInfoList.id==channelInfoParentId?"selected":"" }>${channelInfoList.name }</option>
					</c:forEach>
				</select>
				<!-- 二级渠道信息 -->
				<select id="channelCId" name="channelCId" >
					<option value="">全部</option>
					<c:if test="${!empty childChannelList }">
						<c:forEach items="${childChannelList}" var="childChannelList">
							<option value="${childChannelList.id }" ${childChannelList.id==childChannelId?"selected":""}>${childChannelList.name }</option>
						</c:forEach>
					</c:if>
				</select>
		<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	
	</form>
</div>
</body>
</html>