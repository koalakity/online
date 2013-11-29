<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/admin/css/m_style.css">
<script type="text/javascript">
$(function(){
  if(${dto.isShowFront}==0){
     $("#noRadio").attr("checked",true);
  }
});
	function testFun(){
		alert("ok");
	}
</script>
<form id="addChannelForm" action="${pageContext.request.contextPath}/admin/addChannelInfo" method="post">
	<input type="hidden" id="execFlag" name="execFlag" value="true">
	<input type="hidden" id="id" name="id" value="true">
	<table class="m0" width="100%">
		<tr>
			<td align="right" width="97">渠道ID：</td>
			<td><input id="code" name="code" maxlength="6" type="text" value="${dto.code }"/></td>
		</tr>
		<tr>
			<td align="right">渠道名称：</td>
			<td>
				<input id="name1" name="name1" maxlength="10" type="text" style="width:80px;" value="${dto.name1 }"/>
				<input id="name2" name="name2" maxlength="10" type="text" style="width:80px;" value="${dto.name2 }"/>
			</td>
		</tr>
		<tr>
			<td align="right">渠道描述：</td>
			<td><textarea id="description" name="description" style="width:250px; height:60px; overflow:auto;" >${dto.description }</textarea></td>
		</tr>
		<tr >
		    <td align="right" >是否在前台显示：</td>
		    <td><input name="mode" id="yesRadio" type="radio" value="1" checked="true"/>是 <input name="mode" id="noRadio" type="radio" value="0"/>否</td>
		</tr>
	</table>
</form>