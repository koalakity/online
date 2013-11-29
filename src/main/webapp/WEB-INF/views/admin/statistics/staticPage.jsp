<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style type="text/css">

.fitem {
	margin-bottom: 10px;
	padding: 10px 0 0 3%;
}

</style>
<script type="text/javascript">
</script>
<title></title>
</head>
<body >
<div class="easyui-tabs" id="tt" fit="true">
<div   height="500" title="统计管理">
<div class="fitem">
查询条件：<input  id="condition" class="easyui-combobox" value="${month}" width="200px"  data-options="valueField:'id', 
        textField: 'text',  
        url: '${ctx}/admin/static/initCombobox',  
        onChange: function(){
        var val = $('#condition').combobox('getValue');
          location.href='${ctx}/admin/static/getStaticData?month='+val;
  	}"/>
</div>
<div class="fitem">
  当前月份：<span id="regCntId">${month}月</span>
</div>
<div class="fitem">
  当前注册会员数：<span id="regCntId">${RegCnt}</span>
</div>
<div class="fitem">当前在库放款笔数：${loanCnt}</div>
<div class="fitem">当前在库合同金额：${loanAmount}</div>
<div class="fitem">
当前在库余额（剩余本金）：${remainPrincipal}
</div>
</div>
</div>
</body>
</html>
