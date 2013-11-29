<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>picShowUser</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
    $(function(){
	  $('#searchBt').click(function(){
			search();
		});
	});
	function formatSex(val,row){
	   if(val=="男"){
	      return '<img src="${ctx}/static/admin/css/images/nan.png"/>';	   
	      }else{
          return '<img src="${ctx}/static/admin/css/images/nv.png"/>';	      
	      }
	}
function search() {
    var queryParams = $('#picShowTbl').datagrid('options').queryParams;
    queryParams.realName = $('#realNameTxt').val();
    queryParams.identityNo = $('#cardIDTxt').val();
    queryParams.phoneNo = $('#phoneTxt').val();
    queryParams.email = $('#mailTxt').val();
    queryParams.channelFId=$('#channelFId').val();   //增加渠道ID
	queryParams.channelCId=$('#channelCId').val();   //增加渠道ID
    if ($('#realNameTxt').val() == "" && $('#cardIDTxt').val() == ""&& $('#phoneTxt').val() == "" && $('#mailTxt').val() == "") {
        $.messager.alert('警告', '请至少填写一个查询条件', 'warning');
        return false;
    }
    $('#picShowTbl').datagrid('options').queryParams = queryParams;
    //$('#picShowTbl').datagrid({url:''});
    $("#picShowTbl").datagrid('reload');
}
function formatOperation(val,row){
  var url="${ctx}/admin/audit/showImgJsp?proId=1&email="+row.email+"&userId="+row.userId;
 return '<a href="'+url+'" target="_blank">查看图片</a>'
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
<table id="picShowTbl" title="客户图片查看列表" class="easyui-datagrid"
			toolbar="#toolbar" pagination="true" url="${ctx}/admin/audit/showUserPicList"
			rownumbers="true" fitColumns="true" singleSelect="true">
	  <thead>
		 <tr>
			<th field="loginName" >用户昵称</th>
			<th field="email" >邮箱</th>
			<th field="realName" >姓名</th>
			<th field="sex" formatter="formatSex">性别</th>
			<th field="phoneNo" >手机号码</th>
			<th field="identityNo" >身份证号码</th>
			<th field="creditAmount" >信用额度</th>
			<th field="action" title="操作" formatter="formatOperation">操作</th>
		 </tr>
	  </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<table style="font-size:12px;">
		<tr>
		    <td align="right">姓    名:</td>
		    <td><input id="realNameTxt" name="realName" type="text" style="width:120px"/></td>
		    <td>身份证号:</td>
		    <td><input id="cardIDTxt" name="isApproveCard" type="text" style="width:120px" /></td>
		    <td align="right">邮    箱:</td>
		    <td><input id="mailTxt" name="email" type="text" style="width:120px"/></td>
		    <td>手机号码:</td>
			<td><input id="phoneTxt" name="phoneNo" type="text" style="width:120px"/></td>
			<td>渠道名称：<!-- 一级渠道信息 -->
				<select id="channelFId" name="channelFId"  onchange="appendChildChannelR('channelFId','channelCId')">
					<option value="">全部</option>
					<c:forEach items="${channelInfoParList}" var="channelInfoList">
						<option value="${channelInfoList.id}" ${channelInfoList.id==channelInfoParentId?"selected":""}>${channelInfoList.name }</option>
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
				</select></td>
			<td><a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:search()">查询</a></td>
		</tr>
	</table>
</div>
</body>
</html>
