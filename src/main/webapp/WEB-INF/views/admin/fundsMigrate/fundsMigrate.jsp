<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>3</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" href="${ctx}/static/admin/css/mask.css"type="text/css" />
<script type="text/javascript"src="${ctx}/static/admin/js/utils/mask.js"></script>
<script type="text/javascript">
$(function(){
	$("#tt").tabs({
		  onSelect: function(title){
		  	if(title=="历史结果"){
		  		$("#historyResult").datagrid('reload');
		  		}
		  }
	});	
});

var filePath = "${path}";
var message ="${uploadMessage}"
if(message.length>0){
	alert(message);
}
  if(filePath.length>0){
	  $.post("${ctx}/admin/fundsMigrate/showUpload", 
			  {},
				function (result){
					if (result.success) {
							if(result.msg=='上传失败,请重新上传！'){
								$('#checkData').append('<div>上传失败,请重新上传！</div>');
							}else{
								filePath=result.msg;
								$('#check').removeAttr("disabled");
								//$('#pid').append('<input  id="check"  type="button" onclick="addTab()" value="数据检测"/>&nbsp;');
								$('#fileInput').attr('disabled', 'disabled');
								$('#fileUpload').attr('disabled', 'disabled');
								$('#checkData').append('<div>上传成功，请点击确认迁移按钮，执行数据迁移。</div>');
							}
						} else {
							
						}
				}, "json");
  }
	function addTab(){
			popMask('${ctx}/static/images/m-loading.gif');
         	$.post("${ctx}/admin/fundsMigrate/checkData", 
				  {filePath:"${path}"},
					function (result){
						if (result.success) {
							if(result.msg.indexOf('/opt') == 0||result.msg.indexOf('/') == 2){
								filePath=result.msg;
								$('#ensure').removeAttr("disabled");
								//$('#pid').append('<input id="ensure" type="button" onclick=update() value="确认调账" />');
								//$('#checkData').append('<div>检测通过,请执行调账操作！</div>');
								update();
							}else {
								cancleMask();
								alert("数据校验未通过，请查看详细信息。");
								$('#checkData').append('<div><div style="color:red">check失败列表,请修改数据重新上传：</div><br/>'+result.msg+'</div>');
							 	$('#fileInput').removeAttr("disabled");
								$('#fileUpload').removeAttr("disabled");
							}
								 		
						} else {
								cancleMask();
								alert(result.msg);
								$('#fileInput').removeAttr("disabled");
								$('#fileUpload').removeAttr("disabled");
							}
					}, "json");
         	$('#check').attr('disabled', 'disabled');
    }
	
	function update(){
		var fileName =null;
		var ajaxResult = false;
		//popMask('${ctx}/static/images/m-loading.gif');
		$.post("${ctx}/admin/fundsMigrate/downLoadResult", 
			  {filePath:filePath},
				function (result){
					if(result.success){
						fileName = result.msg;
						cancleMask();
						//location.href='${ctx}/admin/fundsMigrate/downLoadHistoryResult?fileName='+fileName;
						$('#downLoad').val(fileName);
						$('#ensure').removeAttr("disabled");
						alert("数据迁移完成，点击下载按钮进行本次更新结果下载！");
					}else{
						cancleMask();
						alert(result.msg)
					}
					ajaxResult = result.success;
				},"json");
		filePath=null;
		$('#ensure').attr('disabled', 'disabled');
		$('#fileInput').removeAttr("disabled");
		$('#fileUpload').removeAttr("disabled");
	}
	
	function downLoadResult(val,row){
		var url ='${ctx}/admin/fundsMigrate/downLoadHistoryResult?fileName='+row.fileName;
		return '<a href="'+url+'">下载</a>'
	}
	function fileDownLoad(){
		$('#ensure').attr('disabled', 'disabled');
		var downLoadFileName = $('#downLoad').val();
		location.href='${ctx}/admin/fundsMigrate/downLoadHistoryResult?fileName='+downLoadFileName;
	}
</script>
</head>
<body>
	<div class="easyui-tabs" id="tt" fit="true">
		<div   height="500" title="数据检测">
			<form:form id="idform1" name="idform1" action="${ctx}/admin/fundsMigrate/uploadExcel" enctype="multipart/form-data" method="post" >
				<p id="pid">请上传资金迁移文件（注:格式为xls）  <a href="${ctx}/admin/fundsMigrate/downLoadTemplate">模板下载</a><br/><br/>
				 &nbsp;<input id="fileInput" type="file" name="picture" />
				  &nbsp;<input id="fileUpload" type="submit" value="上传" id="upload"/>
				  &nbsp;<input  id="check" disabled="disable" type="button" onclick="addTab()" value="确认迁移"/>
				  &nbsp;<input id="ensure" disabled="disable" type="button" onclick="fileDownLoad()" value="下载本次更新结果" />
				</p>
			</form:form>
			<input type="hidden" id="downLoad" />
			<div id="checkData"></div>
			<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
			<div style="position:relative;position:absolute;">
					历史资金迁移步骤说明：<br/>
					第一步：按照资金迁移模板整理好迁移数据，并上传excel格式文件<br/>
					第二步：点击确认迁移，执行数据检测和数据迁移<br/>
					第三步：根据提示信息选择是否下载本次更新结果<br/>
					<p style="color:red">注：一次只能上传一个附件，一个附件最多放二百条数据。</p>
				
			</div>			
		</div>
		<div  id="result" height="500" title="历史结果">
			<table nowrap="false" id="historyResult" name="historyResult" class="easyui-datagrid" url="${ctx}/admin/fundsMigrate/showHistoryResult" title="历史迁移结果"   style="width:1000px;">
		
			<thead>
				<tr>
					<th field="fileName" width="250">文件名</th>
					<th field="generateTime"  width="150">生成时间</th>
					<th field="staffName" width="150">操作人</th>
					<th field="opearate" width="60" formatter="downLoadResult">按钮</th>
				
				</tr>
			</thead>
	</table>
		</div>
	</div>
	
	
</body>
</html>