<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../adminHeader.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>个人信息查看</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/static/admin/js/utils/jquery.poshytip.js"></script>

<script language="javascript" type="text/javascript">

$(function(){
	$('#tip').poshytip({
		className: 'tip-skyblue',
		bgImageFrameSize: 9,
		offsetX: 0,
		offsetY: 20
	});
	$("#memoText").keyup(function(){
		if($(this).val().length>150){
			$(this).val($(this).val().substring(0,150));
			$.messager.alert('消息','字数不能超过150！');   
		}
		});
});

$(function(){
	$('#savePersonalRemarks').unbind('click');
	$("#savePersonalRemarks").click(function(){
		var remark=document.getElementById("remark").value;
		document.getElementById("userRemarks").value=remark;
		$.ajax({
					 data: $(".remark").serialize(),
		    		 url: "${ctx}/admin/user/savePersonalRemarks",
		    	 	 type: "POST",
		    		 dataType: 'html',
		    		 timeout: 10000,
		     	 error: function(){
		     	 		//alert('error');
		      },
			   	success: function(data){
			   			alert("提交成功");
			   			return;
			   	}
		});
		
	});
});




function noteSubmit(){
	var memo = $('#memoText').val();
	var memoLength = memo.length;
	if(memoLength>150){
		$.messager.alert('Message','字数不能超过150！');
		return;  
	}
	var memo = $('#memoText').val();
	$.post("${ctx}/admin/user/saveMemoNote", 
		  { userId: "${userbasic.userId}", memoText:memo},
			function (result){
				if (result.success) {
						$('#memoText').val('不超过150个汉字！');
						$('#d1').dialog('close');
						$('#noteContext').datagrid('reload');
					} else {
						$.messager.show({ // show error message
							title : 'Error',
							msg : result.msg
						});
					}
			}, "json");
}

function noteReset(){
	$('#memoText').val('不超过150个汉字！');
	$('#d1').dialog('close')
}

function memoFormatt(val,row){
	if(val.length>26){
		var innerValue = val.substring(0, 25)+'......'; 
		return '<a id="tip" title="'+innerValue+'">'+innerValue+'</a>';
	}else {
		return val;
	}
}

</script>


</head>
<body>
	<div id="tab" class="easyui-tabs" style="width:1000px;">  
		<div title="基本资料" style="padding:20px;">  
			<%@ include file="/WEB-INF/views/admin/user/rd/personalBaseRd.jsp"%>
		</div>
		
		<div title="教育职称" style="padding:20px;">  
			<%@ include file="/WEB-INF/views/admin/user/rd/personalEduRd.jsp"%>
		</div>
		<div title="固定资产" style="padding:20px;">  
			<%@ include file="/WEB-INF/views/admin/user/rd/personalFixedRd.jsp"%>
		</div>	
		<div title="财务状况" style="padding:20px;"> 
			<%@ include file="/WEB-INF/views/admin/user/rd/personalFinanceRd.jsp"%>
		</div>
		<div title="工作信息" style="padding:20px;">  
			<%@ include file="/WEB-INF/views/admin/user/rd/personalJobRd.jsp"%>
		</div>
		<div title="私营业主资料" style="padding:20px;">  
			<%@ include file="/WEB-INF/views/admin/user/rd/personalPrivateRd.jsp"%>
		</div>	
	</div>
	
	
	<div style="height:15px; overflow:hidden;"></div>
	<table nowrap="false" id="noteContext" name="noteContext" class="easyui-datagrid" url="${ctx}/admin/user/seachMemoNote?userId=${userbasic.userId}" title="备注" fitColumns="true" toolbar="#toolbar" style="width:1000px;">
		<thead>
			<tr>
				<th field="memoText" width="80"  >备注内容</th>
				<th field="opearateUser" width="80">操作人</th>
				<th field="operateTimeFormatt" width="80">操作时间</th>
			</tr>
		</thead>
	</table>
	<div id="d1" modal="true" class="easyui-dialog" title="添加备注" closed="true" style="padding:5px; width:400px; height:200px;" data-options="buttons:'#dlg-buttons'">
		<textarea id="memoText"  name="memoText" style="width:370px; height:110px; font-size:12px;" onfocus="if(this.value=='不超过150个汉字！')this.value=''" onblur="if(this.value=='')this.value='不超过150个汉字！'">不超过150个汉字！</textarea>
	</div>
	<div id="dlg-buttons">
		<a class="easyui-linkbutton" onclick="javascript:noteSubmit()">提交</a>
		<a  class="easyui-linkbutton" onclick="javascript:noteReset()">取消</a>
	</div>
</body>
</html>