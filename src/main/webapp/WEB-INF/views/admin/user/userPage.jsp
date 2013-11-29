<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="${ctx}/static/admin/js/validator.js" type="text/javascript"></script>
<title>3</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	
	var url;
	function search2(){
		var queryParams = $('#dg').datagrid('options').queryParams;
		queryParams.realName=$('#realNameTxt').val();
		queryParams.loginName=$('#loginNameTxt').val();
		queryParams.phoneNo=$('#phoneTxt').val();
		queryParams.email=$('#mailTxt').val();
		queryParams.userState = $('#userState').combobox('getValue');
		queryParams.regType = $('#regType').combobox('getValue');
		queryParams.regDateMin = $('#regDateMin').datebox('getValue');
		queryParams.regDateMax = $('#regDateMax').datebox('getValue');
		queryParams.isApproveCard=$('#approveCard').combobox('getValue');
		queryParams.channelFId=$('#channelFId').val();   //增加渠道ID
		queryParams.channelCId=$('#channelCId').val();   //增加渠道ID
		if(queryParams.regDateMax<queryParams.regDateMin){
		   $.messager.alert('Warning','开始日期不能大于结束日期!','warning');
		   return false;
		}
		$('#dg').datagrid('options').queryParams = queryParams;
		$("#dg").datagrid('reload');
	}

	
	function recycleSearch(){
		var queryParams = $('#recycleDg').datagrid('options').queryParams;
		queryParams.realName=$('#recycleRealNameTxt').val();
		queryParams.loginName=$('#recycleLoginNameTxt').val();
		queryParams.phoneNo=$('#recyclePhoneTxt').val();
		queryParams.email=$('#recycleMailTxt').val();
		queryParams.userState = $('#recycleUserState').combobox('getValue');
		queryParams.regType = $('#recycleRegType').combobox('getValue');
		queryParams.regDateMin = $('#recycleRegDateMin').datebox('getValue');
		queryParams.regDateMax = $('#recycleRegDateMax').datebox('getValue');
		queryParams.isApproveCard=$('#recycleApproveCard').combobox('getValue');
		queryParams.channelFId=$('#channelFId_r').val();   //增加渠道ID
		queryParams.channelCId=$('#channelCId_r').val();   //增加渠道ID
		if(queryParams.regDateMax<queryParams.regDateMin){
		   $.messager.alert('Warning','开始日期不能大于结束日期!','warning');
		   return false;
		}
		$('#recycleDg').datagrid('options').queryParams = queryParams;
		$("#recycleDg").datagrid('reload');
	}
	
	
	
	function removeUsers(){
		var ids = [];
		   var rows = $('#dg').datagrid('getSelections');
		   var user = "";
			for(var i=0; i<rows.length; i++){
				ids.push(rows[i].userId);
				user = user+rows[i].loginName+" ";
			}
			if(rows.length>0){
			  $.messager.confirm('确认','请确认要删除选中的用户'+user+'吗？',function(r){
			     if(r){
			        $.post('${ctx}/admin/user/removeUsers',{userIds:ids.join(',')},function(result){
			           if(result.success){
			              $('#dg').datagrid('reload');	// reload the user data
			              $('#recycleDg').datagrid('reload');
			           } else {
								
								$.messager.confirm('提示',result.msg)
					   }
			        },'json');
			     }
			  })
			}
	}
	
	/*新建一个用户*/
     function newUser(){  
         $('#dlg').dialog('open').dialog('setTitle','新建用户');  
         $('#fm').form('clear');
        $("#channelInfo_ParantID").val(1);
        $("#channelInfo_ID").val(2);
     }  
     
     function saveUser(){
         $('#fm').form('submit',{  
             url:  "${ctx}/admin/register/saveUser",  
             type: "POST",
    		 dataType: 'html',
             onSubmit: function(){  
            	var ss= $('#loginName').validatebox('isValid');
         		var ss1= $('#email').validatebox('isValid');
         		var ss2= $('#loginPassword').validatebox('isValid');
        		var ss3= $('#reloginPassword').validatebox('isValid');
        		//校验成功
        		if(!(ss&&ss1&&ss2&&ss3)){
                	return $(this).form('validate');  
        		}else{
        		   $("#saveuserBtn").linkbutton('disable');
        		}
             },  
             success: function(data){ 
            	 if(data == '"error"'){
 					alert("保存失败，请重新注册");
 				}else{
            	 alert("注册成功，请登录邮箱激活！");
            	 $('#saveuserBtn').linkbutton('enable'); 
            	// $("#saveuserBtn").removeAttr("disabled");
                 $('#dlg').dialog('close');  
                 //放跳转编辑页
                  var userId="";
                 if(navigator.userAgent.indexOf("MSIE") > 0){
                	 userId=data;
                 }else{
	                userId=data.substring(data.indexOf("\"")+1,data.lastIndexOf("\""));
                 }
                 window.open('${ctx}/admin/user/userInfoPersonPageJsp?userId='+userId)
 				}            	 
             }  
         });  
     }  
	
	function reportUsers(){
		var ids = [];
		var rows = $('#dg').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].userId);
		}
		if (rows.length>0){
			$.messager.confirm('确认','确认举报?',function(r){
				if (r){
					$.post('${ctx}/admin/user/reportUsers',{userIds:ids.join(',')},function(result){
						if (result.success){
							$('#dg').datagrid('reload');	// reload the user data
							
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: result.msg
							});
						}
					},'json');
				}
			});
		}
	}
	
	function unReportUsers(){
	
	}
	function lockUsers(){
	   var ids = [];
	   var rows = $('#dg').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].userId);
		}
		if(rows.length>0){
		  $.messager.confirm('确认','确认锁定？',function(r){
		     if(r){
		        $.post('${ctx}/admin/user/lockUsers',{userIds:ids.join(',')},function(result){
		           if(result.success){
		              $('#dg').datagrid('reload');	// reload the user data
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
	
	function unLockUsers(){
	     var ids = [];
	   var rows = $('#dg').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].userId);
		}
		if(rows.length>0){
		  $.messager.confirm('确认','确认解除锁定？',function(r){
		     if(r){
		        $.post('${ctx}/admin/user/unLockUsers',{userIds:ids.join(',')},function(result){
		           if(result.success){
		              $('#dg').datagrid('reload');	// reload the user data
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
	function formatSex(val,row){
	   if(val=="女"){
	      return '<img src="${ctx}/static/admin/css/images/nv.png"/>';	   
	      }else{
          return '<img src="${ctx}/static/admin/css/images/nan.png"/>';	      
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
	
    function formatApprove(val,row){
       if(val=="0"){
          return "未验证";
       }else if(val=="1"){
          return "已验证";
       }else{
          return "";
       }
    }
    
    function formatLoginName(val,row){
       url='${ctx}/admin/user/userInfoJsp?userId='+row.userId;
       return '<a href="'+url+'" target="_blank">'+val+'</a>';  
     }
   
    function formatRecycleLoginName(val,row){
       url='${ctx}/admin/user/userInfoJsp?userId='+row.userId;
       return '<a href="'+url+'" target="_blank">'+val+'</a>';  
    }  
    function recoverUser(val,row){
       var ids = [];
		   var rows = $('#recycleDg').datagrid('getSelections');
			for(var i=0; i<rows.length; i++){
				ids.push(rows[i].userId);
			}
			if(rows.length>0){
			  $.messager.confirm('确认','确认恢复？',function(r){
			     if(r){
			        $.post('${ctx}/admin/user/recoverUsers',{userIds:ids.join(',')},function(result){
			           if(result.success){
			              $('#recycleDg').datagrid('reload');	// reload the user data
			              $('#dg').datagrid('reload');
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
    
    function removeUsersForever(val,row){
         var ids = [];
		   var rows = $('#recycleDg').datagrid('getSelections');
		   var usr = "";
			for(var i=0; i<rows.length; i++){
				ids.push(rows[i].userId);
			    user = user+rows[i].loginName+" ";
			}
			if(rows.length>0){
			  $.messager.confirm('确认','请确认要彻底删除选中的用户'+user+'吗？',function(r){
			     if(r){
			        $.post('${ctx}/admin/user/removeUsersForever',{userIds:ids.join(',')},function(result){
			           if(result.success){
			              $('#recycleDg').datagrid('reload');	// reload the user data
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
    
    function exportUsers(){
    	$('#serarchForm').submit(); 
    }
    
    function recycleExportUsers(){
    	$('#recycleSerarchForm').submit(); 
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
    
    /**
	2012-10-16
	*新增用户详细信息跳转编辑按钮
	*新增人Ray
	*/
	function formatterOperation(val, row) {
		url='${ctx}/admin/user/userInfoPersonPageJsp?userId='+row.userId
		return '<a href="'+url+'" target="_blank" >编辑</a>';
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
	/*
		注册新用户
	*/
	function appendChildChannelAdd(){
		var id=$("#channelInfo_ParantID").val();
		$.ajax( {
			url : "${ctx}/register/register/findByParentId?code="+id,
			type : "POST",
			dataType : 'JSON',
			success : function(strFlg) {
				$("#channelInfo_ID").empty();
				if(strFlg=="" || strFlg==null){
				}else{
					for(var i=0;i<strFlg.length;i++)
					{
						$("#channelInfo_ID").append("<option value="+strFlg[i].id+">"+strFlg[i].name+"</option>");
					}
				}
				
			}
		});
		
	}
</script>
</head>
<body>
<div id="tt" class="easyui-tabs">
 <div title="会员查询">
    <table id="dg"  class="easyui-datagrid"
			url="${ctx}/admin/user/userPage"
			toolbar="#toolbar" pagination="true"
			rownumbers="true"  singleSelect="false"  fitColumns="true" >
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
			<th field="userStatusStr"  >用户状态</th>
			<!--<th field="channelCode"  width="10">渠道ID</th>-->
			<!-- 新增用户详细信息编辑按钮    新增人： Ray 2012-10-16-->
			<th field="action" title="操作" formatter="formatterOperation">操作</th>
		    <!--<th  >所属渠道</th> -->
		 </tr>
	  </thead>
    </table>
  </div>
  <sec:authorize access="hasAuthority('01010101')">
  <div title="回收站">
    <table id="recycleDg" class="easyui-datagrid" url="${ctx}/admin/user/recycleUserPage" toolbar="#toolbarCycle" pagination="true" rownumbers="true"   singleSelect="false"  fitColumns="true" >
      <thead>
        <tr>
         	<th field="ck" checkbox="true"></th>
          	<th field="isApproveCard" formatter="formatApprove" width="10">ID5验证</th>
			<th field="loginName" formatter="formatRecycleLoginName" width="30">昵称</th>
			<th field="email" width="30">邮箱</th>
			<th field="realName" width="20">姓名</th>
			<th field="sex" formatter="formatSex" width="10">性别</th>
			<th field="phoneNo" width="20">手机号码</th>
			<th field="identityNo" width="20">身份证号码</th>
			<th field="creditGrade" formatter="formatGrade" width="20">信用等级</th>
			<th field="creditAmount" width="20">信用额度</th>
			<th field="userStatusStr"  width="10">用户状态</th>
			<!-- 新增用户详细信息编辑按钮    新增人： Ray 2012-11-5-->
			<th field="action" title="操作" formatter="formatterOperation" width="10">操作</th>
			
        </tr>
      </thead>
    </table>
  </div>
  </sec:authorize>
</div>

<div id="toolbar" style="height:auto; padding:5px;">
	<form id="serarchForm" style="margin:0;" action="${ctx}/admin/user/exportUsers" method="post">
	<div>
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
		<!--<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>-->
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUsers()">删除</a>
		<!-- <a href="#" class="easyui-linkbutton" plain="true" onclick="reportUsers()">举报</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="unReportUsers()">撤销举报</a> -->
		<a href="#" class="easyui-linkbutton" plain="true" onclick="lockUsers()" >锁定</a>
		<a href="#" class="easyui-linkbutton" plain="true" onclick="unLockUsers()" >解锁</a>
		<sec:authorize access="hasAuthority('01010102')">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="exportUsers()">导出</a>
		</sec:authorize>
	</div>
	<table style="font-size:12px;">
		<tr>
		    <td align="right">姓    名:</td>
		    <td><input id="realNameTxt" name="realName" type="text" style="width:120px"/></td>
		    <td>手机号码:</td>
		    <td><input id="phoneTxt" name="phoneNo" type="text" style="width:120px"/></td>
			<td>注册方式:</td>
			<td><select id="regType" name="regType" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="1">前台注册</option>
					<option value="2">后台注册</option>
					<option value="3">批量导入</option>
					<option value="4">会员推荐</option>
				</select>
			</td>
			<td>用户状态:</td>
			<td><select id="userState" name="userState" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="1">未验证</option>
					<option value="2">已验证</option>
					<option value="3">已提交资料</option>
					<option value="5">正常</option>
					<option value="6">锁定</option>
					<option value="7">被举报</option>
				</select>
			</td>
		    <td align="right">ID5 验证:</td>
		    <td>
		      <select id="approveCard" name="isApproveCard" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="0">未验证</option>
					<option value="1">已验证</option>
				</select>
		    </td>
		</tr>
		<tr>
			<td align="right">邮    箱:</td>
		    <td><input id="mailTxt" name="email" type="text" style="width:120px"/></td>
		    <td>昵 称：</td>
				<td><input  id="loginNameTxt" name="loginName" type="text" style="width:120px"/></td>
			<td>注册时间:</td>
			<td colspan="7">
			<input id="regDateMin" name="regDateMin" class="easyui-datebox" style="width:100px" >-<input id="regDateMax" name="regDateMax" class="easyui-datebox" style="width:100px" >
			<!-- 渠道code  Ray 添加-->
		           渠道名称:
		    
				<!-- 一级渠道信息 -->
				<select id="channelFId" name="channelFId" style="width: 100px"  onchange="appendChildChannelR('channelFId','channelCId')">
					<option value="">全部</option>
					<c:forEach items="${channelInfoParList}" var="channelInfoList">
						<option value="${channelInfoList.id }" ${channelInfoList.id==channelInfoParentId?"selected":"" }>${channelInfoList.name }</option>
					</c:forEach>
				</select>
				<!-- 二级渠道信息 -->
				<select style="width: 100px" id="channelCId" name="channelCId">
					<option value="">全部</option>
					<c:if test="${!empty childChannelList }">
						<c:forEach items="${childChannelList}" var="childChannelList">
							<option value="${childChannelList.id }" ${childChannelList.id==childChannelId?"selected":""}>${childChannelList.name }</option>
						</c:forEach>
					</c:if>
				</select>
				<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:search2()">查询</a></td>
		</tr>
	</table>
	</form>
</div>
 <sec:authorize access="hasAuthority('01010101')">
<div id="toolbarCycle" style="padding:5px;height:auto">
  <form id="recycleSerarchForm" action="${ctx}/admin/user/recycleExportUsers" method="post">
	<div style="margin-bottom:5px">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="recoverUser()">恢复</a>
		<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUsersForever()">彻底删除</a> -->
		<sec:authorize access="hasAuthority('01010103')">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="recycleExportUsers()">导出</a>
		</sec:authorize>
	</div>
	<table style="font-size:12px;">
		<tr>
		    <td align="right">姓    名:</td>
		    <td><input id="recycleRealNameTxt" name="realName" type="text" style="width:120px"/></td>
		    <td>手机号码:</td>
		    <td><input id="recyclePhoneTxt" name="phoneNo" type="text" style="width:120px"/></td>
			<td>注册方式:</td>
			<td><select id="recycleRegType" name="regType" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="1">前台注册</option>
					<option value="2">后台注册</option>
					<option value="3">批量导入</option>
					<option value="4">会员推荐</option>
				</select>
			</td>
			<td>用户状态:</td>
			<td><select id="recycleUserState" name="userState" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="1">未验证</option>
					<option value="2">已验证</option>
					<option value="3">已提交资料</option>
					<option value="5">正常</option>
					<option value="6">锁定</option>
					<option value="7">被举报</option>
				</select>
			</td>
		    <td align="right">ID5 验证:</td>
		    <td>
		      <select id="recycleApproveCard" name="isApproveCard" class="easyui-combobox" editable="false">
					<option value="">全部</option>
					<option value="0">未验证</option>
					<option value="1">已验证</option>
				</select>
		    </td>
		</tr>
		<tr>
			<td align="right">邮    箱:</td>
		    <td><input id="recycleMailTxt" name="email" type="text" style="width:120px"/></td>
		    <td>昵 称：</td>
				<td><input  id="recycleLoginNameTxt" name="loginName" type="text" style="width:120px"/></td>
			<td>注册时间:</td>
			<td colspan="7">
				<input id="recycleRegDateMin" name="regDateMin" class="easyui-datebox" style="width:100px" >-<input id="recycleRegDateMax" name="regDateMax" class="easyui-datebox" style="width:100px">
				
			<!-- 渠道code  Ray 添加-->
		           渠道名称:
		    
				<!-- 一级渠道信息 -->
				<select id="channelFId_r" name="channelFId_r" style="width: 100px"  onchange="appendChildChannelR('channelFId_r','channelCId_r')">
					<option value="">全部</option>
					<c:forEach items="${channelInfoParList}" var="channelInfoList">
						<option value="${channelInfoList.id }" ${channelInfoList.id==channelInfoParentId?"selected":"" }>${channelInfoList.name }</option>
					</c:forEach>
				</select>
				<!-- 二级渠道信息 -->
				<select style="width: 100px" id="channelCId_r" name="channelCId_r">
					<option value="">全部</option>
					<c:if test="${!empty childChannelList }">
						<c:forEach items="${childChannelList}" var="childChannelList">
							<option value="${childChannelList.id }" ${childChannelList.id==childChannelId?"selected":""}>${childChannelList.name }</option>
						</c:forEach>
					</c:if>
				</select>
				<a href="#" id="recycleSearchBt" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:recycleSearch()">查询</a>
			</td>
		</tr>
	</table>
	</form>
</div>
<!-- ----------------start-----------------注册新用户所用的弹出框    2012.11.21增加，Ray ---------------------------------------------------------------->
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  closed="true" buttons="#dlg-buttons">  
		 <form id="fm" method="post" novalidate> 
		<table>
			<tr>
							<td class="td1">
								<label for="name">
									用户昵称：
								</label>
							</td>
							<td>
								<input type="text" id="loginName" name="loginName" size="20" value="${user.loginName}" class="easyui-validatebox" required="true" validType="Composite_validation['^[a-zA-Z\\d-]+$','昵称不能含有中文','${ctx}/admin/register/checkLoginName','loginName=','此昵称已经被占用']"/>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="msgName"></div>
							</td>
						</tr>
						<tr>
							<td class="td1">
								<label for="email">
									常用邮箱：
								</label>
							</td>
							<td>
								<input type="text" id="email" name="email" size="20" value="${user.email}" class="easyui-validatebox" required="true" validType="Composite_validation['^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$','请正确输入Email','${ctx}/admin/register/checkEmail','email=','此email已经被占用']"/>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="msgEmail"></div>
							</td>
						</tr>
						<tr>
							<td class="td1">
								<label for="psd">
									登录密码：
								</label>
							</td>
							<td>
								<input type="password" id="loginPassword" name="loginPassword" value="${user.loginPassword}" size="20" class="easyui-validatebox" required="true" validType="safepass"/>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="safeLevel" style="display: none"></div>
							</td>
						</tr>
						<tr>
							<td class="td1">
								<label for="re_psd">
									确认密码：
								</label>
							</td>
							<td>
								<input type="password" id="reloginPassword" name="reloginPassword" value="${user.loginPassword}" size="20" class="easyui-validatebox" required="true" validType=" equalTo['#fm input[name=loginPassword]']" />
							</td>
						</tr>

						<!-- 渠道信息，没有数据不显示渠道来源	2013-1-5增加渠道来源 -->
						<c:if test="${!empty channelInfoParList}">
						<tr>
							<td >
								<label>
									注册来源：
								</label>
							</td>
							<td>
								<!-- 一级渠道信息 -->
								<select id="channelInfo_ParantID" name="channelInfo_ParantID" style="width: 75px"  onchange="appendChildChannelAdd()">
									<c:forEach items="${channelInfoParList}" var="channelInfoList">
										<option value="${channelInfoList.id }">${channelInfoList.name }</option>
									</c:forEach>
								</select>
								<!-- 二级渠道信息 -->
								<select style="width: 75px" id="channelInfo_ID" name="channelInfo_ID">
									<c:if test="${!empty childChannelList }">
										<c:forEach items="${childChannelList}" var="childChannelList">
											<option value="${childChannelList.id }">${childChannelList.name }</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>
						</c:if>

						<tr>
							<td>
								&nbsp;
							</td>
							<td class="col1">
								<div id="msgPassEqual"></div>
							</td>
						</tr>
			
			
		</table>
		</form>  
    </div>  
    <div id="dlg-buttons">  
        <a href="javascript:void(0)" class="easyui-linkbutton" id="saveuserBtn" iconCls="icon-ok" onclick="saveUser()">注册</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>  
    </div>  
    
   
    
    <!-- ----------------end-----------------注册新用户所用的弹出框    2012.11.21增加，Ray ---------------------------------------------------------------->

 </sec:authorize>
</body>
</html>