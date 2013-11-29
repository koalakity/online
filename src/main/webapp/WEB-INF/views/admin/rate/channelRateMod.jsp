<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<script>
function formatFee(val,row){
    var num =Math.round(parseFloat(val)*10000)/100.00+"%"; 
   return num;
}
function idFee(val,row){
   return val+"元";
}
function saveModify(){
    var val = $('#fateBind').combobox('getValue');
    if(val==''){
        $.messager.alert('Warning!','选择绑定的费率不能为空！','warning');
       return false;
    }
    $.messager.confirm('确定', '修改渠道费率将调整该渠道下，所有用户的费率，确定要进行修改吗？',
    function(r) {
        if (r) {
            $.post('${ctx}/admin/rate/saveModify?id=${channelId}&rateId='+val,
            function(data, textStatus) {
                location.href = '${ctx}/admin/rate/rateDesginJsp';
            },
            "json");
        }
    });
}
function cancel(){
     $.messager.confirm('确定', '将取消该渠道费率的修改？',
    function(r) {
        if (r) {
          location.href = '${ctx}/admin/rate/rateDesginJsp';
        }
    });
}
</script>
</head>
<body>
<div class="easyui-panel" style="width: 100%; height: 520px; padding: 10px" fit="true" title="渠道费率修改" >
   <div><span>一级渠道：${name1}</span>    <span>二级渠道：${name2}</span></div>
   <div style="height:15px; overflow:hidden;"></div>
   <table id="noRateTable" title="当前费率" class="easyui-datagrid" url="${ctx}/admin/rate/curRate?rateId=${rateId}">
	<thead>
		<tr>
			<th field="rateName">费率名称</th>
		    <th field="loan" formatter="formatFee">借款管理费</th>
			<th field="reserveFoud" formatter="formatFee">风险基金</th>
			<th field="mgmtFee"  formatter="formatFee">月缴管理费</th>
			<th field="overdueFines" formatter="formatFee">逾期违约金</th>
			<th field="overdueInterest" formatter="formatFee" >逾期罚息(<=30天)</th>
			<th field="overdueSeriousInterest" formatter="formatFee">逾期罚息(>30天)</th>
			<th field="earlyFines" formatter="formatFee">一次性提前还款违约金</th>
			<th field="recharge" formatter="formatFee">充值手续费</th>
			<th field="idFee" formatter="idFee" >ID5验证手续费</th>
		</tr>
	</thead>
</table>
<div style="height:15px; overflow:hidden;"></div>
<div> <span>选择需要绑定的费率:</span><input id="fateBind" class="easyui-combobox"  name="language" data-options="valueField:'id', 
        textField: 'text',  
        url: '${ctx}/admin/rate/initCombobox',  
        onChange: function(){
          var val = $('#fateBind').combobox('getValue');
           $('#afRateTable').datagrid({
					url:'${ctx}/admin/rate/curRate?rateId='+val
				});
  	}"/></div>
<div style="height:15px; overflow:hidden;"></div>
  <table id="afRateTable" title="调整后的费率" class="easyui-datagrid" >
	<thead>
		<tr>
			<th field="rateName">费率名称</th>
			 <th field="loan" formatter="formatFee">借款管理费</th>
			<th field="reserveFoud" formatter="formatFee">风险基金</th>
			<th field="mgmtFee"  formatter="formatFee">月缴管理费</th>
			<th field="overdueFines" formatter="formatFee">逾期违约金</th>
			<th field="overdueInterest" formatter="formatFee" >逾期罚息(<=30天)</th>
			<th field="overdueSeriousInterest" formatter="formatFee">逾期罚息(>30天)</th>
			<th field="earlyFines" formatter="formatFee">一次性提前还款违约金</th>
			<th field="recharge" formatter="formatFee">充值手续费</th>
			<th field="idFee" formatter="idFee" >ID5验证手续费</th> 
		</tr>
	</thead>
</table>
<div style="height:45px; overflow:hidden;"></div>
<div style="text-align:right;padding:5px 0;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveModify()" style="float:center">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" style="float:center">取消</a>
	</div>
</div>
</body>
</html>
