<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>风险金资金账户</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	color: #666;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
<script type="text/javascript">
	function formatterdate(val, row) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+ ' ' + date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
	}
	var url;
	function pay() {
		$('#dlg').dialog('open').dialog('setTitle', '调账');
		//$('#fm').form('clear');
		url = '${ctx}/admin/ledger/doPayRisk';
	}
	 
	function doPay() {
		var fromFlag=$('#fm').form('validate');
		if(!fromFlag){
			return false;
		}
		$.messager.confirm('确认','您确定要调账给用户：'+$('#receiverEmail').val(),function(r){
		     if(r){
				$('#fm').form('submit', {
					url : url,
					onSubmit : function() {
						//出现   
						$(".mMask, .mLoadMsg").show();
					},
					success : function(result) {
						var result = eval('(' + result + ')');
						if (result.success) {
							window.location='${ctx}/admin/ledger/riskMoneyJsp';
						} else {
							$.messager.show({
								title : '错误',
								msg : result.msg
							});
						}
						
						//去掉
						$(".mMask, .mLoadMsg").hide();
					}
				});
		  	}
		 })
		
	}
	
	function selectOnchange(account){
		var amount=$('#'+account).val();
		$('#accountAmount').text(amount);
	}
	
</script>
</head>
<body>
	<table id="dg" title="风险金资金账户" class="easyui-datagrid" url="${ctx}/admin/ledger/riskFlowPage" toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
			<tr>
			<th field="loginName" width="50px;">昵称</th>
				<th field="realName" width="40px;">姓名</th>
				<th field="phoneNo" width="40px;">手机号</th>
				<th field="email" width="50px;">email</th>
				<th field="tradeDate" width="50px;" >流水时间</th>
				<th field="riskTypeStr" width="50px;">类型</th>
				<th field="incomingStr" width="50px;">存入</th>
				<th field="outputStr" width="50px;">支出</th>
				<th field="tradeNoAndMemo" width="50px;">备注</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="height:42px; padding:10px 5px 5px; line-height:1.5em;">
		&nbsp;<label>风险金资金账户余额:</label>&nbsp;${siteBalance}&nbsp;&nbsp;
		<label>总收入:</label>&nbsp;${siteIncomming}&nbsp;&nbsp;
		<label>总支出:</label>&nbsp;${siteOutput}<br />
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="pay()">调账</a>
	</div>
	<div id="dlg" modal="true" class="easyui-dialog" style="width: 400px; height: 380px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<%-- 初始化所有账户的金额和账户ID --%>
				<c:if test="${!empty accountList }">
					<c:forEach items="${accountList }" var="acc">
						<input type="hidden" id="${acc.account }" name="${acc.account }" value="<fmt:formatNumber  value='${acc.amount+ 0.0001}'  type='currency' pattern='0.00'/>"/>
					</c:forEach>
				</c:if>
				
				<label style="text-align:right;">账户:</label> 
				<select id="account" name="account" onchange="selectOnchange(this.value);">
					<c:if test="${!empty accountList }">
						<c:forEach items="${accountList }" var="acc">
							<option value="${acc.account }">
								${acc.memo }
							</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
			<div class="fitem">
				<label style="text-align:right;">账户余额:</label>
				<label style="text-align:left;" id="accountAmount">${accountList[0].amount }</label> 
			</div>
			<div class="fitem">
				<label>调账金额:</label> <input id="payAmt" name="payAmt" class="easyui-numberbox" data-options="min:0.01,precision:2,required:true">
			</div>
			<div class="fitem">
				<label>收款人:</label> <input id="receiverEmail"  name="receiverEmail" class="easyui-validatebox" required="true" validType="email">
			</div>
			<div class="fitem">
				<label>备注:</label>
				<textarea name="memo" style="height: 60px;" class="easyui-validatebox" validType="length[0,30]"></textarea>
			</div>
			
			<div class="mMask" style="width: 400px; height: 380px; display: none; position:absolute; top:0; left:0; background:  none repeat scroll 0 0 #CCCCCC; opacity: 0.4; filter:alpha(opacity=40);"></div>
			<div class="mLoadMsg"  style=" display: none;position:absolute; left:115px; top:160px; width:132px; border:2px solid #6593cf; padding:5px 10px  5px 35px; background:#fff url(${ctx}/static/admin/css/images/panel_loading.gif) no-repeat;">正在处理，请等待。。。 </div>
			
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doPay()">保存</a> <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>


</body>
</html>