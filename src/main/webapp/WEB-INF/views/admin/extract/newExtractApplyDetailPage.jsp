<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>提现信息查看</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
		$(function(){
			setRedioValue();
		});
	function noteSubmit(){
		var verifyStatus = getSelVal();
		var message;
		if(verifyStatus==0){
			var description = $('#description').val();
			if(description == '${extractNote.description}'){
				$.messager.confirm("确认","没有更改内容不能提交！",function(r){  });
				return;
			}    
			    $.post("${ctx}/admin/extract/saveExtractNote", 
					  { extractId: "${extractNote.extractId}", description:description},
						function (result){
							history.back();
							
						}, "json");
						return;
		}
		if(verifyStatus==1){
			message = "此信息将提交至处理中提现申请列表，确认提交吗？";
		}else if(verifyStatus==2){
			message = "提现成功后，相应金额将会在会员账号扣除！";
		}else if(verifyStatus==3){
			message = "此信息将提交至失败的提现申请列表，确认提交吗？";
		}
		
			$.messager.confirm("确认",message,function(r){  
		    if (r){
		    	var description = $('#description').val();  
			    $.post("${ctx}/admin/extract/saveExtractNote", 
					  { extractId: "${extractNote.extractId}", description:description,verifyStatus:verifyStatus},
						function (result){
							if (result.success) {
								history.back();
							} else {
								alert(result.msg);
							}
							
							
						}, "json");
		    }  
			});  
		
		}
		
		function   getSelVal() { 
			var   obj; 
			obj   =   document.getElementsByName("verifyStatus");
			for(var   i=0;i <obj.length;i++) { 
				if(obj[i].checked   ==   true) { 
					return   obj[i].value;	//返回选中的值 
					break;	//退出for循环 
				} 
			} 
		} 
		
		function   setRedioValue() { 
			var   obj; 
			obj   =   document.getElementsByName("verifyStatus");
			var selected = ${extractNote.verifyStatus};
			obj[selected].checked=true;
			obj[selected].disabled=true;
		} 
		
		function enableSubmitBtn(){
			$('#submitBtn').linkbutton({  
			    disabled: false  
			});
		}
</script>
</head>
<body>
		<div id="tab" class="easyui-tabs" style="width:1000px;">  
		<div title="提现信息查看" style="padding:20px;">  
	<form>
			<table>
				<tr>
					<td nowrap >提现编号:</td>
					<td>${extractNote.extractId}</td>
				</tr>
				<tr>
					<td>申请人:</td>
					<td><a href="${ctx}/admin/user/userInfoPersonPageRdJsp?userId=${extractNote.accountUser.userId}">${extractNote.realName}</a></td>
				</tr>
				<tr>
					<td>提现金额:</td>
					<td>${extractNote.extractAmountFormatt}</td>
				</tr>
				<tr>
					<td>提现手续费:</td>
					<td>${extractNote.extractCostFormatt}</td>
				</tr>
				<tr>
					<td>开户名:</td>
					<td>${extractNote.kaihuName}</td>
				</tr>
				<tr>
					<td>支行名称:</td>
					<td>${extractNote.bankName}</td>
				</tr>
				<tr>
					<td>银行账号:</td>
					<td>${extractNote.bankCardNo}</td>
				</tr>
				<tr>
					<td>申请时间:</td>
					<td>${extractNote.extractTimeFormatt}</td>
				</tr>
				<tr>
					<td>提现状态:</td>
					<td><input type="radio" name="verifyStatus" value="0"> 新申请
						<input type="radio" name="verifyStatus" value="1" onclick="enableSubmitBtn();"> 处理中
						<input type="radio" name="verifyStatus" value="2" onclick="enableSubmitBtn();"> 成功
						<input type="radio" name="verifyStatus" value="3" onclick="enableSubmitBtn();"> 失败
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td><textarea id="description" name="description"  style="width:370px; height:110px; font-size:12px;">${extractNote.description}</textarea></td>
				</tr>
				<tr>
					<td>
						<a id="submitBtn" class="easyui-linkbutton" disabled="true" onclick="javascript:noteSubmit()">提交</a>
				    </td>
				    <td>
				    	<a  class="easyui-linkbutton" onClick="history.back();">返回</a>	
				    </td>
				</tr>
			</table> 
			</form>
	</div>
	</div>
</body>
</html>