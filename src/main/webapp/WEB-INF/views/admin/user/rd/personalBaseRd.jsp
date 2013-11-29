<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${ctx}/static/admin/js/upload.js" type="text/javascript"></script>






<style type="text/css">
.red{color:red;}
.promt-error{color:red;}
</style>



<script type="text/javascript">

/*
 头像上传 
 */
$(document).ready(function() {
  //建议在#imgDiv的父元素上加个overflow:hidden;的css样式
  $('#photo').uploadPreview({ width: 100, height: 100, imgDiv: "#imgDiv", imgType: ["bmp", "gif", "png", "jpg"] });
   });
        
        
function creShiOptionBase()
{
	var sheng=$("#hometownArea").val();
			$.ajax( {
				url : "${ctx}/admin/user/queryArea?code="+sheng,
				type : "POST",
				dataType : 'JSON',
				success : function(strFlg) {
					$("#hometownCity").empty();	
					for(var i=0;i<strFlg.length;i++)
					{
						$("#hometownCity").append("<option value="+strFlg[i].id+">"+strFlg[i].name+"</option>");
					}
				}
			});
}






function saveUser(){  
	 $('#test').form('submit',{  
	     url: "${ctx}/admin/user/savePersonalBase",  
	     onSubmit: function(){  
	     return $(this).form('validate');  
	     },  
	     success: function(result){  
	    	 var result = eval('('+result+')');  
	    	 if (result.errorMsg){  
	    		 $.messager.show({
	    			 title: 'Error',  
	    			 msg: result.errorMsg  
	    			 });  
	    		 } else {  
	    			 $('#dlg').dialog('close');      // close the dialog  
	    			 $('#dg').datagrid('reload');    // reload the user data  
	    			 }  
	    	 }  
	     }); 
	 }  






$(function(){
	$('#ID5').unbind('click');
	$("#ID5").click(function(){
		if(document.getElementById("credentialNum").value==""){
			alert("验证ID5，身份证不能为空！");
		}else{
		
		$.ajax({
					 data: $(".baseForm").serialize(),
		    		 url: "${ctx}/admin/user/getID5",
		    	 	 type: "POST",
		    		 dataType: 'html',
		    		 timeout: 10000,
		     	 error: function(){
		     	 		//alert('error');
		      },
			   	success: function(data){
			   		if(data=='"already"'){
			   			alert("已经验证通过，不必再次验证！");
			   		}
			   		if(data=='"noEnough"'){
			   			alert("账户余额不足，无法验证！");
			   		}
			   		if(data=='"success"'){
			   			alert("验证成功！");
			   		}
									   		
			   			//alert("已进行验证.");
			   			return;
			   	}
		});
		}
		
	});
	
});

</script>
<form:form class="baseForm"  enctype="multipart/form-data" method="post" style="margin-bottom:0;" id="baseForm" >
	<table>
				<tr>
					<td align="right" width="190" >昵称:</td>
					<td align="left">
						<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" />
						<input id="infoId" name="userInfoPerson.infoId" type="hidden" value="${userbasic.userInfoPerson.infoId}" />
						<input id="creditId" name="userCreditNote.creditId" type="hidden" value="${userbasic.userCreditNote.creditId}" />
						
		      			<input id="loginName" name="loginName" type="text" value="${userbasic.loginName}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >邮箱:</td>
					<td align="left">
		      			<input id="email" name="email" type="text" value="${userbasic.email}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >密码:</td>
					<td align="left">
		      			<input id="loginPassword" name="loginPassword" type="password" value="${userbasic.loginPassword}" disabled="disabled" />
		  			</td>
				</tr>
					<!-- 渠道信息，没有数据不显示渠道来源 2013-1-5增加渠道来源 -->
				    <tr>
				        <td align="right"  nowrap>
				                注册来源：
				        </td>
				        <td>
				            <!-- 一级渠道信息 -->
				            <select ${!empty channelInfoParentId } id="channelInfo_ParantID" name="channelInfo_ParantID" style="width: 100px" disabled="disabled">
				                <c:forEach items="${channelInfoParList}" var="channelInfoList">
				                    <option value="${channelInfoList.id }" ${channelInfoList.id==channelInfoParentId?
				                    "selected": "" }>
				                        ${channelInfoList.name }
				                    </option>
				                </c:forEach>
				            </select>
				            <!-- 二级渠道信息 -->
				            <select ${!empty childChannelId } style="width: 100px" id="channelInfo_ID" name="channelInfo_ID" disabled="disabled">
				                <c:if test="${!empty childChannelList }">
				                    <c:forEach items="${childChannelList}" var="childChannelList">
				                        <option value="${childChannelList.id}" ${childChannelList.id==childChannelId?"selected": ""}>
				                            ${childChannelList.name }
				                        </option>
				                    </c:forEach>
				                </c:if>
				            </select>
				        </td>
				    </tr>
				    <tr>
				        <td>
				            &nbsp;
				        </td>
				        <td class="col1">
				            <div id="channelInfoMsg" style="display: none">
				            </div>
				        </td>
				    </tr>
				<!--  此2属性暂时无对应数据库字段，后期添加
				<tr>
					<td align="right"  nowrap >是否推荐:</td>
					<td align="left">
		      			<input id="17" name="" type="radio" value="1" />是
		      			<input id="17" name="" type="radio" value="0" />否
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >推荐人:</td>
					<td align="left">
		      			<input id="18" name="creditGrade" type="text" value="${userAuditInfo.basicInfoApprove.creditScore}" />
		  			</td>
				</tr>
				 -->
				<tr>
					<td align="right"  nowrap >注册方式:</td>
					<td align="left">
						
			      		<input id="regType" name="regType" type="radio" value="1" <c:if test="${userbasic.regType==1}"> checked="checked" </c:if>  disabled="disabled" />前台注册
			      		<input id="regType" name="regType" type="radio" value="2" <c:if test="${userbasic.regType==2}"> checked="checked" </c:if>  disabled="disabled" />后台注册
		      			<!-- 暂时不显示此2选项，后期添加 
		      			<input id="regType" name="regType" type="radio" value="3" <c:if test="${userbasic.regType==3}"> checked="checked"</c:if>/>批量导入 
		      			<input id="regType" name="regType" type="radio" value="4" <c:if test="${userbasic.regType==4}"> checked="checked"</c:if>/>会员推荐 
		      			-->
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >用户状态:</td>
					<td align="left">
		      				<input id="userStatus" name="userStatus" type="radio" value="1" <c:if test="${userbasic.userStatus==1}">checked="checked"</c:if> disabled="disabled"/>未验证
		      				<input id="userStatus" name="userStatus" type="radio" value="2" <c:if test="${userbasic.userStatus==2}">checked="checked"</c:if> disabled="disabled"/>已验证
							<input id="userStatus" name="userStatus" type="radio" value="3" <c:if test="${userbasic.userStatus==3}">checked="checked"</c:if> disabled="disabled"/>已提交资料
		      				<input id="userStatus" name="userStatus" type="radio" value="4" <c:if test="${userbasic.userStatus==4}">checked="checked"</c:if> disabled="disabled"/>已进入审核
		      				<input id="userStatus" name="userStatus" type="radio" value="5" <c:if test="${userbasic.userStatus==5}">checked="checked"</c:if> disabled="disabled"/>正常
		      				<input id="userStatus" name="userStatus" type="radio" value="6" <c:if test="${userbasic.userStatus==6}">checked="checked"</c:if> disabled="disabled"/>锁定
		      				<input id="userStatus" name="userStatus" type="radio" value="7" <c:if test="${userbasic.userStatus==7}">checked="checked"</c:if> disabled="disabled"/>被举报
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >信用等级:</td>
					<td align="left">
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="0" <c:if test="${userbasic.creditGrade==0}">checked="checked"</c:if> disabled="disabled"/>普通会员
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="1" <c:if test="${userbasic.creditGrade==1}">checked="checked"</c:if> disabled="disabled"/><img src="${ctx}/static/images/img28.gif"/>
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="2" <c:if test="${userbasic.creditGrade==2}">checked="checked"</c:if> disabled="disabled"/><img src="${ctx}/static/images/img27.gif"/>
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="3" <c:if test="${userbasic.creditGrade==3}">checked="checked"</c:if> disabled="disabled"/><img src="${ctx}/static/images/img26.gif"/>
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="4" <c:if test="${userbasic.creditGrade==4}">checked="checked"</c:if> disabled="disabled"/><img src="${ctx}/static/images/img25.gif"/>
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="5" <c:if test="${userbasic.creditGrade==5}">checked="checked"</c:if> disabled="disabled"/><img src="${ctx}/static/images/img24.gif"/>
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="6" <c:if test="${userbasic.creditGrade==6}">checked="checked"</c:if> disabled="disabled"/><img src="${ctx}/static/images/img23.gif"/>
			      			<input id="creditGrade" name="userCreditNote.creditGrade" type="radio" value="7" <c:if test="${userbasic.creditGrade==7}">checked="checked"</c:if> disabled="disabled"/><img src="${ctx}/static/images/img22.gif"/>
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >信用额度:</td>
					<td align="left">
						<c:if test="${userbasic.creditAmount != '' }">
		      				<input id="creditAmount" name="userCreditNote.creditAmount" type="text" value="${userbasic.creditAmount}" disabled="disabled" />元
		      			</c:if>
		      			<c:if test="${userbasic.creditAmount == '' }">
		      				<input id="creditAmount" name="userCreditNote.creditAmount" type="text" value="0.00" disabled="disabled" />元
		      			</c:if>
		      		</td>
				</tr>
				</table>
	</form:form>
				
	<form:form  id="updataHeadPhoto" enctype="multipart/form-data" method="post"  action="${ctx}/admin/user/updataHeadPhoto" style="margin-bottom:0;">
	<table>
	<tr>
		<td align="right" width="190" valign="top" >会员头像:</td>
		<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" />
		<input id="email" name="userEmail" type="hidden" value="${userbasic.email}"/>
	<td>
    		<div style="width: 100px; height: 100px; overflow: hidden; border: 1px solid red;">
        		<div id="imgDiv">
        			<c:choose>
						<c:when test="${not empty userbasic.userInfoPerson.headPath && ('' ne userbasic.userInfoPerson.headPath) }">
							<img src="/pic/${userbasic.userInfoPerson.headPath }"  width="100" height="100"/>
						</c:when>
						<c:otherwise>
							<img src="${ctx}/static/images/nophoto.jpg" id="person" width="100" height="100" /> 
						</c:otherwise>
					</c:choose>
        		</div>
    		</div>
	</td>
	</tr>
	</table>
   </form:form>
	
	
				
				
				
		<form:form class="baseForm" style="margin-bottom:0;" id="baseForm">
		<table>
				<tr>
					<td align="right" width="190" ><span style="color:red;">*</span>真实姓名:</td>
					<td align="left">
		      			<input id="realName" name="userInfoPerson.realName" type="text" value="${userbasic.realName}"  class="easyui-validatebox" required="true" validType="length[2,8]" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>性别:</td>
					<td align="left">
			      			<input id="sex" name="userInfoPerson.sex" type="radio" value="1" disabled="disabled"  <c:if test="${userbasic.userInfoPerson.sex==1  || userbasic.userInfoPerson.sex==''|| userbasic.userInfoPerson.sex==null}"> checked="checked"</c:if>/>男
			      			<input id="sex" name="userInfoPerson.sex" type="radio" value="2" disabled="disabled"  <c:if test="${userbasic.userInfoPerson.sex==2}"> checked="checked"</c:if>/>女
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>证件类型:</td>
					<td align="left">
		      			<select id="credentialKind" name="userInfoPerson.credentialKind" disabled="disabled" >
							<option value="1" <c:if test="${userbasic.userInfoPerson.credentialKind==1}" >selected="selected"</c:if>>身份证</option>
							<option value="2" <c:if test="${userbasic.userInfoPerson.credentialKind==2}" >selected="selected"</c:if>>军官证</option>
						</select>
		      			
		  			</td>
				</tr>
				
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>证件号码:</td>
					<td align="left">
		      			<input id="credentialNum" name="userInfoPerson.identityNo" type="text" value="${userbasic.identityNo}" class="easyui-validatebox" required="true" validType="length[15,18]" disabled="disabled" />
		  			</td>
				</tr>
				
				
				
				<!-- 此处新增ID验证记录 -->
				<tr>
					<td colspan="2">
						<table style="background:#E0ECFF; margin-left:130px; padding:8px 10px 5px;">
							<tr><td colspan="2" style="color:red;">以下框中信息为ID5验证服务提供商返回的验证结果：</td></tr>
							<tr>
								<td align="right" width="70">姓名:</td>
								<td align="left">
					      			<input id="name" name="id5Check.name" type="text" value="${userbasic.id5Check.name}" disabled="disabled"/>
					  			</td>
							</tr>
							<tr>
								<td align="right"  nowrap >身份证号:</td>
								<td align="left">
					      			<input id="cardId" name="id5Check.cardId" type="text" value="${userbasic.id5Check.cardId}" disabled="disabled"/>
					  			</td>
							</tr>
							<tr>
								<td align="right"  nowrap >比对状态:</td>
								<td align="left">
									<c:if test="${userbasic.id5Check.checkStatusCode==3}" >
					      				<input id="checkStatus" name="id5Check.checkStatusCode" type="text" disabled="disabled" value="一致"/>
									</c:if>
									<c:if test="${userbasic.id5Check.checkStatusCode==2}" >
					      				<input id="checkStatus" name="id5Check.checkStatusCode" type="text" disabled="disabled" value="不一致"/>
									</c:if>
									<c:if test="${userbasic.id5Check.checkStatusCode==1}" >
					      				<input id="checkStatus" name="id5Check.checkStatusCode" type="text" disabled="disabled" value="库中无此号"/>
									</c:if>
									<c:if test="${userbasic.id5Check.checkStatusCode==null}" >
					      				<input id="checkStatus" name="id5Check.checkStatusCode" type="text" disabled="disabled" value=""/>
									</c:if>
					  			</td>
							</tr>
							<tr>
								<td align="right"  nowrap >原始发证地1:</td>
								<td align="left">
					      			<input id="location1" name="id5Check.location1" type="text" value="${userbasic.id5Check.location1}" disabled="disabled"/>
					  			</td>
							</tr>	
							<tr>
								<td align="right"  nowrap >原始发证地2:</td>
								<td align="left">
					      			<input id="location2" name="id5Check.location2" type="text" value="${userbasic.id5Check.location2}" disabled="disabled"/>
					  			</td>
							</tr>
							<tr>
								<td align="right"  nowrap >照片:</td>
								<td align="left">
					      			<img src= "data:image/jpg;base64,${userbasic.id5Check.photo}"/>
					  			</td>
							</tr>	
						
						</table>		
					</td>
				</tr>
				
				<!-- 此处新增ID验证记录 end-->
				
				
				
				
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>手机号码:</td>
					<td align="left">
		      			<input id="phoneNo" name="userInfoPerson.phoneNo" type="text" value="${userbasic.userInfoPerson.phoneNo}" class="easyui-validatebox" required="true" validType="length[11,11]" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >籍贯:</td>
					<td align="left">
					
						<select name="userInfoPerson.hometownArea" id="hometownArea" onchange="creShiOptionBase()" disabled="disabled" >
                             <c:forEach var="sheng" items="${shengList}">
                               	<option value="${sheng.id}"  <c:if test="${userbasic.userInfoPerson.hometownArea==sheng.id}" >selected="selected"</c:if>>${sheng.name}</option>
                             </c:forEach>
                       </select>
                       <select name="userInfoPerson.hometownCity" id="hometownCity" disabled="disabled" >
                       		<c:forEach var="shi" items="${shiList}">
                            	<option value="${shi.id}" <c:if test="${userbasic.userInfoPerson.hometownCity==shi.id}" >selected="selected"</c:if> >${shi.name}</option>
                             </c:forEach>
                       </select>
					
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>现居住地址:</td>
					<td align="left">
		      			<input id="currAddr" name="userInfoPerson.liveAddress" type="text" value="${userbasic.userInfoPerson.liveAddress}" class="easyui-validatebox" required="true" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >居住地电话:</td>
					<td align="left">
		      			<input id="livePhoneArea" name="userInfoPerson.livePhoneArea" type="text" value="${userbasic.userInfoPerson.livePhoneArea}" disabled="disabled" />
		      			<input id="livePhoneNo" name="userInfoPerson.livePhoneNo" type="text"  value="${userbasic.userInfoPerson.livePhoneNo}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >居住地邮编:</td>
					<td align="left">
		      			<input id="postCode" name="userInfoPerson.postCode" type="text" value="${userbasic.userInfoPerson.postCode}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>婚姻状况:</td>
					<td align="left">
		  				<select id="isapproveMarry" name="userInfoPerson.isapproveMarry">
							<option value="56" <c:if test="${userbasic.userInfoPerson.isapproveMarry==56}" >selected="selected"</c:if>>未婚</option>
							<option value="57" <c:if test="${userbasic.userInfoPerson.isapproveMarry==57}" >selected="selected"</c:if>>已婚</option>
							<option value="59" <c:if test="${userbasic.userInfoPerson.isapproveMarry==59}" >selected="selected"</c:if>>离异</option>
							<option value="58" <c:if test="${userbasic.userInfoPerson.isapproveMarry==58}" >selected="selected"</c:if>>丧偶</option>
						</select>
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >子女:</td>
					<td align="left">
		      			<select id="isapproveHavechild" name="userInfoPerson.isapproveHavechild" disabled="disabled" >
							<option value="0" <c:if test="${userbasic.userInfoPerson.isapproveHavechild==0}" >selected="selected"</c:if>>无</option>
							<option value="1" <c:if test="${userbasic.userInfoPerson.isapproveHavechild==1}" >selected="selected"</c:if>>有</option>
						</select>
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>月收入:</td>
					<td align="left">
		      			<input id="monthIncome" name="userInfoPerson.monthIncome" type="text" value="${userbasic.userInfoPerson.monthIncome}" class="easyui-validatebox" required="true" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>直系亲属姓名:</td>
					<td align="left">
		      			<input id="familyMembersName" name="userInfoPerson.familyMembersName" type="text" value="${userbasic.userInfoPerson.familyMembersName}" class="easyui-validatebox" required="true" validType="length[2,8]" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>关系:</td>
					<td align="left">
		      			<input id="immFamilyRela" name="userInfoPerson.familyRelation" type="text" value="${userbasic.userInfoPerson.familyRelation}" class="easyui-validatebox" required="true" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>手机:</td>
					<td align="left">
		      			<input id="familyMemberPhone" name="userInfoPerson.familyMemberPhone" type="text" value="${userbasic.userInfoPerson.familyMemberPhone}" class="easyui-validatebox" required="true" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>其他联系人姓名:</td>
					<td align="left">
		      			<input id="otherContactName" name="userInfoPerson.otherContactName" type="text" value="${userbasic.userInfoPerson.otherContactName}" class="easyui-validatebox" required="true"  validType="length[2,8]" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>关系:</td>
					<td align="left">
		      			<input id="otherRelation" name="userInfoPerson.otherRelation" type="text" value="${userbasic.userInfoPerson.otherRelation}" class="easyui-validatebox" required="true" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap ><span style="color:red;">*</span>手机:</td>
					<td align="left">
		      			<input id="otherContactPhone" name="userInfoPerson.otherContactPhone" type="text" value="${userbasic.userInfoPerson.otherContactPhone}" class="easyui-validatebox" required="true" disabled="disabled" />
		  			</td>
				</tr>
				
				
				<tr>
					<td align="right"  nowrap >联系人三姓名:</td>
					<td align="left">
		      			<input id="threeContactName" name="userInfoPerson.threeContactName" type="text" value="${userbasic.userInfoPerson.threeContactName}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >关系:</td>
					<td align="left">
		      			<input id="threeContactRelation" name="userInfoPerson.threeContactRelation" type="text" value="${userbasic.userInfoPerson.threeContactRelation}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >手机:</td>
					<td align="left">
		      			<input id="threeContactPhone" name="userInfoPerson.threeContactPhone" type="text" value="${userbasic.userInfoPerson.threeContactPhone}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >联系人四姓名:</td>
					<td align="left">
		      			<input id="fourthContactName" name="userInfoPerson.fourthContactName" type="text" value="${userbasic.userInfoPerson.fourthContactName}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >关系:</td>
					<td align="left">
		      			<input id="fourthContactRelation" name="userInfoPerson.fourthContactRelation" type="text" value="${userbasic.userInfoPerson.fourthContactRelation}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right"  nowrap >手机:</td>
					<td align="left">
		      			<input id="fourthContactPhone" name="userInfoPerson.fourthContactPhone" type="text" value="${userbasic.userInfoPerson.fourthContactPhone}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >QQ:</td>
					<td align="left">
		      			<input id="qqNo" name="userInfoPerson.qqNo" type="text" value="${userbasic.userInfoPerson.qqNo}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >MSN:</td>
					<td align="left">
		      			<input id="msnNo" name="userInfoPerson.msnNo" type="text" value="${userbasic.userInfoPerson.msnNo}" disabled="disabled" />
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >新浪微博账号:</td>
					<td align="left">
		      			<input id="sinaWeiboAccount" name="userInfoPerson.sinaWeiboAccount" type="text" value="${userbasic.userInfoPerson.sinaWeiboAccount}" disabled="disabled" />
		  			</td>
				</tr>
			</table>
</form:form>