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
		queryParams.interestStartMin = $('#interestStartMin').datebox('getValue');
		queryParams.interestStartMax = $('#interestStartMax').datebox('getValue');
		var interestStartMinDate=StringToDate(queryParams.interestStartMin);
		var interestStartMaxDate=StringToDate(queryParams.interestStartMax);
		//获取2个时间差天数
		var dates = Math.abs((interestStartMinDate.getTime() - interestStartMaxDate.getTime()))/(1000*60*60*24); 
		//获取起始月天数
		var numberDate=getNumberDaysOfMonth(interestStartMinDate.getFullYear(),interestStartMinDate.getMonth()+1);
		//alert("相隔天数："+dates+"  月天数："+numberDate);
		 if(dates>numberDate){
			 $.messager.alert('Warning','只能查询一个月!','warning');
			 return false;
		 }
		$('#dg').datagrid('options').queryParams = queryParams;
		$("#dg").datagrid('reload');
		//调用小计方法
		totalAmount();
	}
	
	/*新增时间段统计放款额*/
	function totalAmount(){
		var loginName = $('#loginName').val();
		var realName = $('#realName').val();
		var phoneNo = $('#phoneNo').val();
		var interestStartMin = $('#interestStartMin').datebox('getValue');
		var interestStartMax = $('#interestStartMax').datebox('getValue');
		//alert(loginName +" "+realName+" "+phoneNo+"  "+interestStartMin+"  "+interestStartMax);
		$.ajax({
			url: "${ctx}/admin/loan/loanTotalAmount",
			type: "POST",
			data: {loginName:loginName,realName:realName,phoneNo:phoneNo,interestStartMin:interestStartMin,interestStartMax:interestStartMax},
			dataType: 'html',
			timeout: 10000,
			error: function(){
    	 		//alert('error');
			},
			success: function(data){
				//alert(data);
				var totalAmount="";
				// if(navigator.userAgent.indexOf("MSIE") > 0){
					// alert(data);
					// totalAmount=data;
				// }else{
					 totalAmount= data.substring(data.indexOf("\"")+1,data.lastIndexOf("\""));
				// }
				$("#subtotal").attr("value", totalAmount);
	   			return;
			}
		});
	}
	
	
	//转日期
	function StringToDate(DateStr) {  
	    var converted = Date.parse(DateStr); 
	    var myDate = new Date(converted); 
	    if (isNaN(myDate))   {  
	        var arys= DateStr.split('-'); 
	        myDate = new Date(arys[0],--arys[1],arys[2]); 
	    } 
	    return myDate; 
	}
	
	//获取月天数
	function getNumberDaysOfMonth(year, month) {
		  var tmp = new Date(year, month, 0);
		  return tmp.getDate();
		}
	
	function editUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','编辑用户');
			$('#fm').form('load',row);
			url = '${ctx}/admin/user/updateUser?loanId='+row.loanId;
		}
	}
	
	
	

	
	function formatterOperation(val, row) {
		url='${ctx}/admin/loan/loanRepayingDetailPage?loanId='+row.loanId
		return '<a href="'+url+'" target="_blank"  >查看详情</a>';
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
<table id="dg" title="还款中借款列表" class="easyui-datagrid" 
			url="${ctx}/admin/loan/loanRepayingPage?status=4"
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
			<th field="yearRateFormatt">年利率</th>
			<th field="loanDuration" formatter="formattLoanDuration">借款期限</th>
			<th field="interestStart" >放款日期</th>
			<th field="nextExpiry" >还款日期</th>
			<th field="interestStart">合同签订日</th>
			<th field="formattMonthReturnPrincipalandinter">月还款额</th>
			<!-- <th>客户来源</th>
			<th>居间服务费率</th>
			<th>管理费率</th> TODO-->
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
		放款时间:<input id="interestStartMin" name="interestStartMin" value="${minDate}" class="easyui-datebox" style="width:100px" >-<input id="interestStartMax" name="interestStartMax" value="${maxDate}"  class="easyui-datebox" style="width:100px" >
		<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" >搜索</a></br>	
		渠道名称：<!-- 一级渠道信息 -->
				<select id="channelFId" name="channelFId"  onchange="appendChildChannelR('channelFId','channelCId')">
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
		当前查询时段放款小计:<input type="text" name="subtotal" id="subtotal" value="${totalValue}" size=16 disabled="disabled"/>
	</div>
	
	</form>
</div>



</body>
</html>