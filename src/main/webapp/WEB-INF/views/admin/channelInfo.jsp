<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="adminHeader.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>waitForAuditingUser</title>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.autocomplete.css"></link>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.autocomplete.js"></script>
     <style type="text/css">
		div.ac > ul > li.selected {
			background-color: #DCDCDC;
		}
		div.ac > ul > li > div em {
			background-color: #B0E2FF;
			font-size: 16px;
		}
    </style>
    <script type="text/javascript">
	$(function () {
	    // Load countries then initialize plugin:
	    $.ajax({
	    	url: '${pageContext.request.contextPath}/search/textSearch?type=channelInfoController ',
	        dataType: 'json'
	    }).done(function (source) {
	        var countriesArray = $.map(source, function (value, key) { return { value: value, data: key }; }),
	            countries = $.map(source, function (value) { return value; });
	        $('#name1').autocomplete({
	            lookup: countriesArray,
	            onSelect: function (suggestion) {
	            	//alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
	            }
	        });
	    });
	    
	    
	    $.ajax({
	    	url: '${pageContext.request.contextPath}/search/textSearch?type=channelInfoSearchController',
	        dataType: 'json'
	    }).done(function (source) {
	        var countriesArray = $.map(source, function (value, key) { return { value: value, data: key }; }),
	            countries = $.map(source, function (value) { return value; });
	        $('#name2').autocomplete({
	            lookup: countriesArray,
	            onSelect: function (suggestion) {
	            	//alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
	            }
	        });
	    });
	
	});
	$(function(){
		$('#searchBt').click(function(){
			search();
		});
	});
	
	function search(){
		var queryParams = $('#channel_datagrid').datagrid('options').queryParams;
		queryParams.code=$('#code').val();
		queryParams.name1=$('#name1').val();
		queryParams.name2=$('#name2').val();
		if(($('#code').val()!=null && isNaN($('#code').val())) || $('#code').val().indexOf(".")!=-1){
			//是数字，不能包含小数点
			alert("渠道ID必须是数字！");
		}else{
			//alert("code="+queryParams.code+" name1="+queryParams.name1+"  name2="+queryParams.name2);
			$('#channel_datagrid').datagrid('options').queryParams = queryParams;
			$('#channel_datagrid').datagrid('reload');
		}
	}
	
	<!--批量删除-->
	function channelInfoDelete(){
		var ids = [];
		var rows = $('#channel_datagrid').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		if(rows==null || rows.length==0){
			alert("没有选中要删除的记录！");
		}
		if(rows.length>0){
		  $.messager.confirm('确认','请确认要删除选中信息吗?',function(r){
		     if(r){
		        $.post('${pageContext.request.contextPath}/admin/delChannelInfoS',{infoIds:ids.join(',')},function(result){
		           if(result=="true"){
		           		alert('删除成功！');
		              	window.location.href="${pageContext.request.contextPath}/admin/findById";
		           } else {
						$.messager.confirm('提示',result,function(r){
							window.location.href="${pageContext.request.contextPath}/admin/findById";
							
						});
						
						//$.messager.confirm('提示',result);
						//alert(result);
		              	//window.location.href="${pageContext.request.contextPath}/admin/findById";
						
				   }
		        },'text');
		     }
		  })
		}
	}

	<!--单个删除-->
	function channelInfoDeleteByid(rowId){
		$.messager.confirm('确认','请确认要删除选中信息吗?',function(r){
		     if(r){
		        $.post('${pageContext.request.contextPath}/admin/delChannelInfo',{id:rowId},function(result){
		           if(result=="true"){
		           		alert('删除成功！');
		              	window.location.href="${pageContext.request.contextPath}/admin/findById";
		           } else {
		           		$.messager.confirm('提示',result,function(r){
							window.location.href="${pageContext.request.contextPath}/admin/findById";	
						});
						
						//$.messager.confirm('提示',result);
						//alert(result);
		              	//window.location.href="${pageContext.request.contextPath}/admin/findById";
				   }
		        },'text');
		     }
		  })
	}
	<!-- 操作  -->
	function formatAction(val,row,index){
		return '<a href="javascript:updateChannels(\''+row.id+'\')">编辑</a>  <a href="javascript:channelInfoDeleteByid(\''+row.id+'\')">删除</a>';
	}
	
	<!-- 修改用户  -->
	function updateChannels(paramId){
		$('#channel_datagrid').datagrid('uncheckAll').datagrid('unselectAll');
		parent.$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/admin/findById?id='+paramId,
			width : 380,
			height : 250,
			modal : true,
			title : '修改渠道',
			buttons : [{
				text : '修改',
				handler : function(){
					if(parent.$("#code").val()=='' || isNaN(parent.$("#code").val())){
						alert("渠道ID必须是数字！");
					}else if(parent.$("#name1").val()==''){
						alert("渠道名称不能为空！");
					}else if(parent.$("#name2").val()==''){
						alert("渠道名称不能为空！");
					}else{
						$.ajax({
				               data:parent.$("#addChannelForm").serialize(),
						       url: "${pageContext.request.contextPath}/admin/addChannelInfo?id="+paramId,
						       type: "POST",
						       dataType: 'text',
							   success: function(data){
							     if(data=="true"){
									parent.$.messager.show({
										title : '提示',
										msg : '修改成功！'
									});
									window.location.href="${pageContext.request.contextPath}/admin/findById";
							     }else{
									parent.$.messager.show({
										title : '提示',
										msg : '修改失败！'
									});
									window.location.href="${pageContext.request.contextPath}/admin/findById";
							     }
							   	}
						});
						var d = parent.$(this).closest('.window-body');
						d.dialog('destroy');
					}
					
				}
			},
			{
				text : '取消',
				handler : function(){
						var d = parent.$(this).closest('.window-body');
						d.dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
		
	}
	
	<!-- 增加用户  -->
	function addChannels(){
		$('#channel_datagrid').datagrid('uncheckAll').datagrid('unselectAll');
		parent.$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/static/temp/admin/addChannel.jsp',
			width : 380,
			height : 250,
			modal : true,
			title : '新建渠道',
			buttons : [{
				text : '增加',
				handler : function(){
					if(parent.$("#code").val()=='' || isNaN(parent.$("#code").val())){
						alert("渠道ID必须是数字！");
					}else if(parent.$("#name1").val()==''){
						alert("渠道名称不能为空！");
					}else if(parent.$("#name2").val()==''){
						alert("渠道名称不能为空！");
					}else{
						if(!isNaN(parent.$("#code").val())){
							//是数字，不能包含小数点
							if(parent.$("#code").val().indexOf(".")!=-1){
								alert("渠道ID必须是数字！");
							}else{
								$.ajax({
						               data:parent.$("#addChannelForm").serialize(),
								       url: "${pageContext.request.contextPath}/admin/addChannelInfo",
								       type: "POST",
								       dataType: 'text',
									   success: function(data){
									     if(data=="true"){
											parent.$.messager.show({
												title : '提示',
												msg : '添加成功！'
											});
											window.location.href="${pageContext.request.contextPath}/admin/findById";
									     }else{
											parent.$.messager.show({
												title : '提示',
												msg : data
											});
											window.location.href="${pageContext.request.contextPath}/admin/findById";
									     }
									   	}
								});
								var d = parent.$(this).closest('.window-body');
								d.dialog('destroy');
							}
						}
					}
				}
			},
			{
				text : '取消',
				handler : function(){
						var d = parent.$(this).closest('.window-body');
						d.dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	};
	
</script>
</head>
<body>
<table id="channel_datagrid" title="渠道列表" class="easyui-datagrid"
			url="${ctx}/admin/findChannelInfo"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" >
	<thead>
		<tr>
			<th field="ck" checkbox="true" ></th>
			<th field="code" width="65">渠道ID</th>
			<th field="name" width="200">渠道名称</th>
			<th field="description" width="200">渠道描述</th>
			<th field="staffName" width="60">创建人</th>
			<th field="createDate" width="60">创建时间</th>
			<th field="action" title="操作" formatter="formatAction" width="125">操作</th>
		</tr>
	</thead>
</table>

<div id="toolbar" style="height:auto; padding:5px;">
	<form id="serarchForm" style="margin:0;" action="${ctx}/admin/user/exportUsers" method="post">
	<div>
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addChannels()">新建</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="channelInfoDelete()">批量删除</a>
	</div>
	<table style="font-size:12px;">
		<tr>
		    <td align="right">渠道ID：</td>
		    <td><input id="code" maxlength="6" name="code" type="text" style="width:120px"/></td>
		    <td>渠道名称：</td>
			 <td>
			 	<input  maxlength="10" id="name1" name="name1" type="text" style="width:120px"/>
			 </td>
			  <td><input  maxlength="10" id="name2" name="name2" type="text" style="width:120px"/></td>
			 
			<td>
			<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>