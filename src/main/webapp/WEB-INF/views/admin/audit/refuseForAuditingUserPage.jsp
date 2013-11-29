<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>waitForAuditingUser</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	$(function(){
		$('#searchBt').click(function(){
			search();
		});
	});
	function formatterOperation(val, row) {
		url='${ctx}/admin/audit/refuseForAuditingDetailPageJsp?userId='+row.userId;
		return '<a href="'+url+'">操作</a>';
	}
	
	function search(){
		var queryParams = $('#firstAuditTbl').datagrid('options').queryParams;
		queryParams.loginName = $('#loginName').val();
		queryParams.realName = $('#realName').val();
		queryParams.channelFId=$('#channelFId').val();   //增加渠道ID
		queryParams.channelCId=$('#channelCId').val();   //增加渠道ID
		$('#firstAuditTbl').datagrid('options').queryParams = queryParams;
		$("#firstAuditTbl").datagrid('reload');
	}
	
	function formatStatu(val,row){
	    if(val=="3"){
		   return '被驳回';
	    }
	}
	function formatApprove(val,row){
      if(val=="0"){
          return "未验证";
       }else if(val=="1"){
          return "已验证";
       }else{
          return "";
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
<table id="firstAuditTbl" title="驳回列表" class="easyui-datagrid"
			url="${ctx}/admin/audit/waitAuditUserPage?auditId=3"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
	<thead>
		<tr>
			<th field="loginName" formatter="formatterUserDetailForInfoApp" >会员昵称</th>
			<th field="realName" >会员姓名</th>
			<th field="isApproveCard" formatter="formatApprove" >ID5验证</th>
			<th field="creditGrade" sortable="true">信用等级</th>
			<th field="creditAmount" >信用额度</th>
			<th field="materialReviewStatus" formatter="formatStatu"  >审核状态</th>
			<th field="inclosureSubmitTimeFormat"  sortable="true">提交时间</th>
			<th field="action" title="操作" formatter="formatterOperation">操作</th>
		</tr>
	</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<div>
		会员昵称：<input id="loginName"/> 
		会员姓名：<input id="realName"/>
		渠道名称：<!-- 一级渠道信息 -->
				<select id="channelFId" name="channelFId"   onchange="appendChildChannelR('channelFId','channelCId')">
					<option value="">全部</option>
					<c:forEach items="${channelInfoParList}" var="channelInfoList">
						<option value="${channelInfoList.id }" ${channelInfoList.id==channelInfoParentId?"selected":"" }>${channelInfoList.name }</option>
					</c:forEach>
				</select>
				<!-- 二级渠道信息 -->
				<select id="channelCId" name="channelCId">
					<option value="">全部</option>
					<c:if test="${!empty childChannelList }">
						<c:forEach items="${childChannelList}" var="childChannelList">
							<option value="${childChannelList.id }" ${childChannelList.id==childChannelId?"selected":""}>${childChannelList.name }</option>
						</c:forEach>
					</c:if>
				</select>
		<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>
</div>

</body>
</html>