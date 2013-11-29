<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>enteredAuditUser</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	$(function(){
	  $('#searchBt').click(function(){
			search();
		});
	  $('searchAllBt').click(function(){
	        searchAll();
	  })	
	});
	var url;
	
	function search(){
		var queryParams = $('#auditDg').datagrid('options').queryParams;
		queryParams.loginName=$('#loginNameTxt').val();
		queryParams.identityNo=$('#cardIDTxt').val();
		queryParams.phoneNo=$('#phoneTxt').val();
		queryParams.email=$('#mailTxt').val();
		if($('#loginNameTxt').val()==""||$('#cardIDTxt').val()==""){
		  $.messager.alert('警告','姓名和身份证号不能为空！','warning');
		  return false;
		}
		if($('#phoneTxt').val()==""&&$('#mailTxt').val()==""){
		   $.messager.alert('警告','注册邮箱和手机号必需填写一个！','warning');
		   return false;
		}
		queryParams.isApproveCard=$('#approveCard').combobox('getValue');
		$('#auditDg').datagrid('options').queryParams = queryParams;
		$("#auditDg").datagrid('reload');
	}
	function searchAll(){
		var queryParams = $('#seeAllDg').datagrid('options').queryParams;
		queryParams.loginName=$('#loginNameAllTxt').val();
		queryParams.identityNo=$('#cardIDAllTxt').val();
		queryParams.phoneNo=$('#phoneAllTxt').val();
		queryParams.email=$('#mailAllTxt').val();
		queryParams.isApproveCard=$('#approveCardAll').combobox('getValue');
		$('#seeAllDg').datagrid('options').queryParams = queryParams;
		$("#seeAllDg").datagrid('reload');
	}
	function formatSex(val,row){
	   if(val=="男"){
	      return '<img src="${ctx}/static/admin/css/images/nan.png"/>';	   
	      }else{
          return '<img src="${ctx}/static/admin/css/images/nv.png"/>';	      
	      }
	}
	
	function formatGrade(val,row){
	    if(val=="7"){
	      return '<img src="${ctx}/static/images/img22.gif"/>' 
	    }else if(val=="6"){
	      return '<img src="${ctx}/static/images/img23.gif"/>' 
	    }else if(val=="5"){
	      return '<img src="${ctx}/static/images/img24.gif"/>' 
	    }else if(val=="4"){
	      return '<img src="${ctx}/static/images/img25.gif"/>' 
	    }else if(val=="3"){
	      return '<img src="${ctx}/static/images/img26.gif"/>' 
	    }else if(val=="2"){
	      return '<img src="${ctx}/static/images/img27.gif"/>' 
	    }else if(val=="1"){
	      return '<img src="${ctx}/static/images/img28.gif"/>' 
	    }else{
	      return "普通会员";
	    } 
	}
    function formatStatu(val,row){
	    if(val=="0"){
	     url='${ctx}/admin/audit/waitForAuditingUserPageJsp'
		   return '<a href="'+url+'" >待审</a>';
	    }else if(val=="1"){
	     url='${ctx}/admin/audit/firstForAuditingUserPageJsp'
	    return '<a href="'+url+'" >初定</a>';
	    }else if(val=="2"){
	    url='${ctx}/admin/audit/finalForAuditingUserPageJsp'
	    return '<a href="'+url+'" >终定</a>';
	    }else if(val=="3"){
	    url='${ctx}/admin/audit/refuseForAuditingUserPageJsp'
	    return '<a href="'+url+'" >驳回</a>';
	    }else if(val=="4"){
	    url='${ctx}/admin/audit/supplyDataPageJsp'
	    return '<a href="'+url+'" >补充资料</a>';
	    }
	}
	function formatApprove(val,row){
       if(val=="1"){
          return "已验证";
       }else {
          return "未验证";
       }
    }
    function removeUsers(){
		var ids = [];
		   var rows = $('#auditDg').datagrid('getSelections');
			for(var i=0; i<rows.length; i++){
				ids.push(rows[i].userId);
			}
			if(rows.length>0){
			  $.messager.confirm('确认','确认删除？',function(r){
			     if(r){
			        $.post('${ctx}/admin/user/removeUsers',{userIds:ids.join(',')},function(result){
			           if(result.success){
			              $('#auditDg').datagrid('reload');	// reload the user data
			           } else {
								$.messager.show({	// show error message
									title: 'Error',
									msg: result.msg
								});
					   }
			        },'json');
			     }
			  })
			}
	}
	function lockUsers(){
	   var ids = [];
	   var rows = $('#auditDg').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].userId);
		}
		if(rows.length>0){
		  $.messager.confirm('确认','确认锁定？',function(r){
		     if(r){
		        $.post('${ctx}/admin/user/lockUsers',{userIds:ids.join(',')},function(result){
		           if(result.success){
		              $('#auditDg').datagrid('reload');	// reload the user data
		           } else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: result.msg
							});
				   }
		        },'json');
		     }
		  })
		}
	}
	 function removeSeeUsers(){
		var ids = [];
		   var rows = $('#seeAllDg').datagrid('getSelections');
			for(var i=0; i<rows.length; i++){
				ids.push(rows[i].userId);
			}
			if(rows.length>0){
			  $.messager.confirm('确认','确认删除？',function(r){
			     if(r){
			        $.post('${ctx}/admin/user/removeUsers',{userIds:ids.join(',')},function(result){
			           if(result.success){
			              $('#seeAllDg').datagrid('reload');	// reload the user data
			           } else {
								$.messager.show({	// show error message
									title: 'Error',
									msg: result.msg
								});
					   }
			        },'json');
			     }
			  })
			}
	}
	function lockSeeUsers(){
	   var ids = [];
	   var rows = $('#seeAllDg').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].userId);
		}
		if(rows.length>0){
		  $.messager.confirm('确认','确认锁定？',function(r){
		     if(r){
		        $.post('${ctx}/admin/user/lockUsers',{userIds:ids.join(',')},function(result){
		           if(result.success){
		              $('#seeAllDg').datagrid('reload');	// reload the user data
		           } else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: result.msg
							});
				   }
		        },'json');
		     }
		  })
		}
	}
 function formatLoginName(val,row){
      url='${ctx}/admin/user/userInfoJsp?userId='+row.userId;
       return '<a href="'+url+'" target="_blank">'+val+'</a>';  
 }	
 function formatLoginNameAll(val,row){
      url='${ctx}/admin/user/userInfoJsp?userId='+row.userId;
       return '<a href="'+url+'" target="_blank">'+val+'</a>';  
 }
 
  function dateFormat(val,row){
       var date = new Date(parseInt(val));
       var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
       var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	   var hour=date.getHours()< 10 ? "0" + date.getHours() : date.getHours();
	   var mints=date.getMinutes()< 10 ? "0" + date.getMinutes() : date.getMinutes();
	   var sec=date.getSeconds()< 10 ? "0" + date.getSeconds() :date.getSeconds();
	//date.getFullYear() + "-" + month + "-" + currentDate+ " " +hour+ ":" + mints+ ":" +sec;
    return date.getFullYear() + "-" + month + "-" + currentDate+" "+hour+":"+mints;

    }
    
  function exportUsers(){
    	$('#serarchForm').submit(); 
    }
    
    function allExportUsers(){
    	$('#recycleSerarchForm').submit(); 
    }
    
    /**
	2012-10-16
	*新增用户详细信息跳转编辑按钮
	*新增人Ray
	*/
	function formatterOperation(val, row) {
		url='${ctx}/admin/user/userInfoPersonPageJsp?userId='+row.userId
		return '<a href="'+url+'" target="_blank" >编辑</a>';
	}
</script>
</head>
<body>
<div id="auditDiv" class="easyui-tabs">
  <div title="已进入审核">
    <table id="auditDg"  class="easyui-datagrid" url="${ctx}/admin/user/enterAuditPage"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="false" >
	   <thead>
		 <tr>
			<th field="ck" checkbox="true"></th>
			<th field="isApproveCard" formatter="formatApprove">ID5验证</th>
			<th field="loginName" formatter="formatLoginName">昵称</th>
			<th field="email" >邮箱</th>
			<th field="realName" >姓名</th>
			<th field="sex" formatter="formatSex">性别</th>
			<th field="phoneNo" >手机号码</th>
			<th field="identityNo" >身份证号码</th>
			<th field="creditGrade" formatter="formatGrade">信用等级</th>
			<th field="creditAmount" >信用额度</th>
			<th field="materialReviewStatus" formatter="formatStatu" >审核状态</th>
			<th field="action" title="操作" formatter="formatterOperation">操作</th>
		    <!--<th  >所属渠道</th> -->
		 </tr>
	  </thead>
    </table>
  </div>
  <div title="查看所有">
    <table id="seeAllDg" class="easyui-datagrid" url="${ctx}/admin/user/enterAllAuditPage" toolbar="#toolbarAll" pagination="true" rownumbers="true"   singleSelect="false" fitColumns="true">
      <thead>
        <tr>
         	<th field="ck" checkbox="true"></th>
          	<th field="isApproveCard" formatter="formatApprove" width="10">ID5验证</th>
			<th field="loginName" formatter="formatLoginNameAll" width="20">昵称</th>
			<th field="email" width="30">邮箱</th>
			<th field="realName" width="10">姓名</th>
			<th field="sex" formatter="formatSex" width="10">性别</th>
			<th field="phoneNo" width="20">手机号码</th>
			<th field="identityNo" width="30">身份证号码</th>
			<th field="creditGrade" formatter="formatGrade" width="5">信用等级</th>
			<th field="creditAmount" width="10">信用额度</th>
			<th field="materialReviewStatus" formatter="formatStatu" width="10">审核状态</th>
			<th field="action" title="操作" formatter="formatterOperation" width="10">操作</th>
        </tr>
      </thead>
    </table>
  </div>
</div>

<div id="toolbar" style="padding:5px;height:auto">
	<form id="serarchForm" action="${ctx}/admin/user/exportAuditUsers" method="post">
	<div style="margin-bottom:5px">
		<!--<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>-->
		<!--<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>-->
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUsers()">删除</a>
		<!-- <a href="#" class="easyui-linkbutton" plain="true" onclick="reportUsers()">举报</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="unReportUsers()">撤销举报</a> -->
		<a href="#" class="easyui-linkbutton" plain="true" onclick="lockUsers()" >锁定</a>
	</div>
	<table style="font-size:12px;">
		<tr>
		    <td align="right">*姓    名:</td>
		    <td><input id="loginNameTxt" name="loginName" type="text" style="width:120px" class="easyui-validatebox" required="true"/></td>
		    <td>*身份证号:</td>
		    <td><input id="cardIDTxt" name="isApproveCard" type="text" style="width:120px" class="easyui-validatebox" required="true"/></td>
		    <td align="right">邮    箱:</td>
		    <td><input id="mailTxt" name="email" type="text" style="width:120px"  class="easyui-validatebox" validType="email"/></td>
		    <td><a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:search()">查询</a></td>
		</tr>
		<tr>
			<td> 手机号码:</td>
			<td><input id="phoneTxt" name="phoneNo" type="text" style="width:120px"/></td>
			<td align="right">ID5 验证:</td>
			 <td>
		      <select id="approveCard" name="isApproveCard" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="0">未验证</option>
					<option value="1">已验证</option>
				</select>
		    </td>
		</tr>
	</table>
	</form>
</div>
<div id="toolbarAll" style="padding:5px;height:auto">
  <form id="recycleSerarchForm" action="${ctx}/admin/user/allExportAuditUsers" method="post">
	<div style="margin-bottom:5px">
	    <!--<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>-->
		<!--<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>-->
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeSeeUsers()">删除</a>
		<!-- <a href="#" class="easyui-linkbutton" plain="true" onclick="reportUsers()">举报</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="unReportUsers()">撤销举报</a> -->
		<a href="#" class="easyui-linkbutton" plain="true" onclick="lockSeeUsers()" >锁定</a>
		<sec:authorize access="hasAuthority('01010201')">
			<a href="#" class="easyui-linkbutton" plain="true" onclick="allExportUsers()">导出</a>
		</sec:authorize>
	</div>
		<table style="font-size:12px;">
		<tr>
		    <td align="right">姓    名:</td>
		    <td><input id="loginNameAllTxt" name="loginName" type="text" style="width:120px"/></td>
		    <td>身份证号:</td>
		    <td><input id="cardIDAllTxt" name="isApproveCard" type="text" style="width:120px" /></td>
		    <td align="right">邮    箱:</td>
		    <td><input id="mailAllTxt" name="email" type="text" style="width:120px" /></td>
		    <td><a href="#" id="searchAllBt" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:searchAll()">查询</a></td>
		</tr>
		<tr>
			<td> 手机号码:</td>
			<td><input id="phoneAllTxt" name="phoneNo" type="text" style="width:120px"/></td>
			<td align="right">ID5 验证:</td>
			 <td>
		      <select id="approveCardAll" name="isApproveCard" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="0">未验证</option>
					<option value="1">已验证</option>
				</select>
		    </td>
		</tr>
	</table>
	</form>
</div>

</body>
</html>